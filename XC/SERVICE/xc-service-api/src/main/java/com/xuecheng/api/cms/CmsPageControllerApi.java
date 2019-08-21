package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by Administrator on 2019/7/14.
 */
@Api(value="CMS管理页面",description = "完成CMS管理页面的增、删、改、查")
public interface CmsPageControllerApi {
    @ApiOperation("分页查询")
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);
    @ApiOperation("根据页面ID查询页面")
    public CmsPageResult findById(String id);
    @ApiOperation("添加页面")
    public CmsPageResult add(CmsPage cmsPage);
    @ApiOperation("修改页面")
    public CmsPageResult edit(String id,CmsPage cmsPage);
    @ApiOperation("删除页面")
    public ResponseResult delete(String id);
    @ApiOperation("发布页面")
    public ResponseResult postPage(String id);
}
