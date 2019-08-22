package com.xuecheng.manage_course.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "xc.cms")
@Data
public class CmsProperties {
    private String publish_siteId;
    private String publish_templateId;
    private String publish_page_webpath;
    private String publish_page_physicalpath;
    private String publish_dataUrlPre;
    private String previewUrl;
}
