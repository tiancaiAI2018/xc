package com.xuecheng.mange_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;

public interface CmsPageService {

    /**
     * 分页查询
     * @param page
     * @param size
     * @param queryPageRequest 查询参数
     * @return
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);
    /**
     * 根据页面ID查询页面
     * @param id
     * @return
     */
    public CmsPageResult findById(String id);
    /**
     *  //添加页面
     * @param cmsPage
     * @return
     */
    public CmsPageResult add(CmsPage cmsPage);
    /**
     * //修改页面
     * @param id
     * @param cmsPage
     * @return
     */
    public CmsPageResult edit(String id,CmsPage cmsPage);

    /**
     * //删除页面
     * @param id
     * @return
     */
    public ResponseResult delete(String id);

    /**
     * 根据页面ID生成静态化的页面
     * @param id
     * @return
     */
    public String generateHtml(String id);
    /**
     * 功能描述: 根据页面ID发布页面
     * 〈〉
     * @Param: [id]
     * @Return: boolean 是否发布成功（只关心生产方，不关系消费方）
     * @Author: Administrator
     * @Date: 2019/8/3 15:49
     */
    public boolean postPage(String id);

    CmsPageResult save(CmsPage cmsPage);
}
