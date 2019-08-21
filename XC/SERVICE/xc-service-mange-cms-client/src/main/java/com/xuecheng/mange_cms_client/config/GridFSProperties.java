package com.xuecheng.mange_cms_client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.data.mongodb")
@Data
public class GridFSProperties {
    //数据源
    private String database;
}
