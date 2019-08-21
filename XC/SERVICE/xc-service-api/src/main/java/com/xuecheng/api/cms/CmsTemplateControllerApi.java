package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.response.CmsTemplateResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "CMS的页面模板管理")
public interface CmsTemplateControllerApi {
    @ApiOperation("根据id获得页面模板")
    CmsTemplateResult findById(String id);
    @ApiOperation("获得说有的页面模板")
    QueryResponseResult findAll();
}
