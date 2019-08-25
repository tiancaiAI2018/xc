package com.xuecheng.mange_cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.mange_cms.config.RabbitMQConfig;
import com.xuecheng.mange_cms.dao.CmsPageRepository;
import com.xuecheng.mange_cms.dao.CmsSiteRepository;
import com.xuecheng.mange_cms.dao.CmsTemplateRespository;
import com.xuecheng.mange_cms.service.CmsPageService;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CmsPageServiceImpl implements CmsPageService {
    @Autowired
    private CmsPageRepository cmsRepository;
    @Autowired
    private CmsTemplateRespository cmsTemplateRespository;
    @Autowired
    private GridFSBucket gridFSBucket;
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private CmsSiteRepository cmsSiteRepository;
    @Override
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        CmsPage cmsPage = new CmsPage();
        if(StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        if(StringUtils.isNotEmpty(queryPageRequest.getSiteId())) cmsPage.setSiteId(queryPageRequest.getSiteId());
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        if(page<=0)page=1;
        page-=1;
        if(size<=0)size=10;
        Page<CmsPage> all = cmsRepository.findAll(example, PageRequest.of(page, size));
        QueryResult<CmsPage> queryResult = new QueryResult<>();
        queryResult.setTotal(all.getTotalElements());
        queryResult.setList(all.getContent());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }

    @Override
    public CmsPageResult findById(String id) {
        Optional<CmsPage> optional = cmsRepository.findById(id);
        if(optional.isPresent()){
            CmsPage cmsPage = optional.get();
            return new CmsPageResult(CommonCode.SUCCESS,cmsPage);
        }
        return new CmsPageResult(CommonCode.FAIL,null);
    }

    @Override
    public CmsPageResult add(CmsPage cmsPage) {
        CmsPage page = cmsRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageAliase(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if(page!=null)ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        //自动生成主键
        cmsPage.setPageId(null);
        CmsPage save = cmsRepository.save(cmsPage);
        if(save!=null)
            return new CmsPageResult(CommonCode.SUCCESS,save);
        return new CmsPageResult(CommonCode.FAIL,null);
    }

    @Override
    public CmsPageResult edit(String id, CmsPage cmsPage) {
        Optional<CmsPage> optional = cmsRepository.findById(id);
        if(optional.isPresent()){
            CmsPage one = optional.get();
            //更新页面类型
            one.setPageType(cmsPage.getPageType());
            //更新模板Id
            one.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            one.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            one.setPageName(cmsPage.getPageName());
            //更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //更新数据模型来源
            one.setDataUrl(cmsPage.getDataUrl());
            CmsPage save = cmsRepository.save(one);
            if(save!=null)return new CmsPageResult(CommonCode.SUCCESS,save);
        }
        return new CmsPageResult(CommonCode.FAIL,null);
    }

    @Override
    public ResponseResult delete(String id) {
        Optional<CmsPage> optional = cmsRepository.findById(id);
        if(optional.isPresent()){
            cmsRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    @Override
    public String generateHtml(String id){
        if(id==null)ExceptionCast.cast(CommonCode.INVALID_PARAM);
        Optional<CmsPage> optional = cmsRepository.findById(id);
        if(!optional.isPresent())ExceptionCast.cast(CmsCode.CMS_PAGE_NO);
        CmsPage cmsPage = optional.get();
        try {
            String template = getTemplate(cmsPage.getTemplateId());
            Map model = getModel(cmsPage.getDataUrl());
            String html = generateHtml(template, model);
            return html;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 功能描述: 生成静态页面并发布
     * 〈〉
     * @Param: [id]
     * @Return: boolean
     * @Author: Administrator
     * @Date: 2019/8/3 15:51
     */
    @Override
    public boolean postPage(String id) {
        //获取页面信息
        Optional<CmsPage> optional = cmsRepository.findById(id);
        if(!optional.isPresent())ExceptionCast.cast(CmsCode.CMS_PAGE_NO);
        CmsPage cmsPage = optional.get();
        //生成一个静态并保存静态化页面
        saveHtml(cmsPage);
        //设定消息
        Map map = new HashMap();
        map.put("pageId",cmsPage.getPageId());
        String msg = JSON.toJSONString(map);
        //发送消息到指定的额消费者
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE,cmsPage.getSiteId(),msg);
        return true;
    }
    @Override
    public CmsPageResult save(CmsPage cmsPage){
        if(cmsPage==null)ExceptionCast.cast(CommonCode.INVALID_PARAM);
        CmsPage cmsPage1 = cmsRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if(cmsPage1!=null){
            return this.edit(cmsPage1.getPageId(),cmsPage);
        }else{
            return this.add(cmsPage);
        }

    }

    /**
     * 一键发布页面
     * @param cmsPage
     * @return
     */
    @Override
    public String postPageQuick(CmsPage cmsPage) {
        //添加或修改页面
        CmsPageResult save = this.save(cmsPage);
        if(!save.isSuccess())ExceptionCast.cast(CmsCode.CMS_PUBLISH_PAGE);
        //得到页面ID
        String pageId = save.getCmsPage().getPageId();
        CmsSite cmsSite = this.getCmsSiteById(save.getCmsPage().getSiteId());
        //发布页面
        boolean b = this.postPage(pageId);
        if(!b)ExceptionCast.cast(CmsCode.CMS_PUBLISH_PAGE);
        //拼接页面发布后的url
        String url = cmsSite.getSiteDomain()+cmsSite.getSiteWebPath()+cmsPage.getPageWebPath()+cmsPage.getPageName();
        return url;
    }

    private CmsSite getCmsSiteById(String siteId){
        if(StringUtils.isEmpty(siteId))ExceptionCast.cast(CmsCode.CMS_SITE_NO);
        Optional<CmsSite> optional = cmsSiteRepository.findById(siteId);
        if(!optional.isPresent())ExceptionCast.cast(CmsCode.CMS_SITE_NO);
        return optional.get();
    }
    /**
     * 根据页面模板和数据模型生成页面内容
     * @param template
     * @param model
     * @return
     */
    private String generateHtml(String template,Map model) throws IOException, TemplateException {
        if(StringUtils.isEmpty(template)||model==null)ExceptionCast.cast(CommonCode.FAIL);
        //freeMark的配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        //创建字符串模板加载对象
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        //加载一个模板
        stringTemplateLoader.putTemplate("template",template);
        configuration.setTemplateLoader(stringTemplateLoader);
        Template template1 = configuration.getTemplate("template");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template1, model);
        return html;
    }
    /**
     * 根据页面模板id或的页面模板的内容
     * @param templateId
     * @return
     */
    private String getTemplate(String templateId) throws IOException {
        if(templateId==null)ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEIDISNULL);
        Optional<CmsTemplate> optional = cmsTemplateRespository.findById(templateId);
        if(!optional.isPresent())ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        CmsTemplate cmsTemplate = optional.get();
        GridFSFile fsFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(cmsTemplate.getTemplateFileId())));
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(fsFile.getObjectId());
        GridFsResource gridFsResource = new GridFsResource(fsFile, gridFSDownloadStream);
        String content = IOUtils.toString(gridFsResource.getInputStream(), "UTF-8");
        return content;
    }

    /**
     * 根据地址获得数据模型
     * @param dataUrl
     * @return
     */
    private Map getModel(String dataUrl){
        if(dataUrl==null)ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        ResponseEntity<Map> entity = restTemplate.getForEntity(dataUrl, Map.class);
        if(entity!=null)return entity.getBody();
        return null;
    }
    /**
     * 功能描述: 将page的页面静态化，并保存到MongoDB的GridFS中
     * 〈〉
     * @Param: [pageId]
     * @Return: void
     * @Author: Administrator
     * @Date: 2019/8/3 15:57
     */
    private void saveHtml(CmsPage cmsPage){
        //得到静态化页面的字符串
        String html = generateHtml(cmsPage.getPageId());
        //创建输入流
        try (InputStream inputStream = IOUtils.toInputStream(html, "utf-8")){
            gridFsTemplate.delete(Query.query(Criteria.where("_id").is(cmsPage.getHtmlFileId())));
            ObjectId objectId = gridFsTemplate.store(inputStream, cmsPage.getPageName());
            cmsPage.setHtmlFileId(objectId.toString());
            cmsRepository.save(cmsPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
