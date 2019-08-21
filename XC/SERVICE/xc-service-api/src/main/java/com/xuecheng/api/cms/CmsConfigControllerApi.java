package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.response.CmsConfigResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="CMS数据模型的管理",description = "完成数据模型的增、删、改、查")
public interface CmsConfigControllerApi {
    @ApiOperation("获得数据模型的内容")
    CmsConfigResult findById(String id);
}
