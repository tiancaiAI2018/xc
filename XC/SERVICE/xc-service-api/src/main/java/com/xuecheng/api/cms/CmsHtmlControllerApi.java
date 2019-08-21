package com.xuecheng.api.cms;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("页面发布与静态化预览")
public interface CmsHtmlControllerApi {
    @ApiOperation("预览静态化页面")
    void previewHtml(String pageId);
}
