package com.xuecheng.mange_cms_client.service.impl;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.mange_cms_client.dao.CmsPageRepository;
import com.xuecheng.mange_cms_client.dao.CmsSiteRepository;
import com.xuecheng.mange_cms_client.service.CmsPageService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
@Service
public class CmsPageServiceImpl implements CmsPageService {
    @Autowired
    private GridFSBucket gridFSBucket;
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private CmsPageRepository cmsPageRepository;
    @Autowired
    private CmsSiteRepository cmsSiteRepository;
    @Override
    public void savePageToServerPath(String pageId) {
        //得到页面信息
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if(!optional.isPresent())ExceptionCast.cast(CmsCode.CMS_PAGE_NO);
        CmsPage cmsPage = optional.get();
        if(StringUtils.isEmpty(cmsPage.getSiteId()))ExceptionCast.cast(CmsCode.CMS_SITE_NO);
        if(StringUtils.isEmpty(cmsPage.getHtmlFileId()))ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        //根据页面信息的站点ID获取站点信息
        CmsSite cmsSite = getCmsSite(cmsPage.getSiteId());
        //拼服务路径
        String path = cmsSite.getSitePhysicalPath()+cmsPage.getPagePhysicalPath()+cmsPage.getPageName();
        //获取文件输入流
        try(
                InputStream inputStream = getHtml(cmsPage.getHtmlFileId());
                OutputStream fileOutputStream = new FileOutputStream(path)
        ) {
            //将文件输出到服务器的指定位置
            IOUtils.copy(inputStream,fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 功能描述: 获得页面的主站信息
     * 〈〉
     * @Param: [siteId]
     * @Return: com.xuecheng.framework.domain.cms.CmsSite
     * @Author: Administrator
     * @Date: 2019/8/3 15:06
     */
    private CmsSite getCmsSite(String siteId){
        Optional<CmsSite> optional = cmsSiteRepository.findById(siteId);
        if(!optional.isPresent()) ExceptionCast.cast(CmsCode.CMS_SITE_NO);
        return optional.get();
    }
    /**
     * 功能描述: 在MongoDB的GridFS上得到静态页面的输入流
     * 〈〉
     * @Param: [htmlFileId]
     * @Return: java.io.InputStream
     * @Author: Administrator
     * @Date: 2019/8/3 15:07
     */
    private InputStream getHtml(String htmlFileId) throws IOException {
        GridFSFile fsFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(htmlFileId)));
        if(fsFile==null)ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(fsFile.getObjectId());
        GridFsResource gridFsResource = new GridFsResource(fsFile, gridFSDownloadStream);
        return gridFsResource.getInputStream();
    }
}
