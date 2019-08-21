package com.xuecheng.framework.domain.cms.request;

import lombok.Data;

/**
 * Created by Administrator on 2019/7/14.
 */
@Data
public class QueryPageRequest {
    //站点ID
    private String siteId;
    //页面ID
    private String pageId;
    //模板ID
    private String templateId;
    //页面名称
    private String pageName;
    //别名
    private String pageAliase;
}
