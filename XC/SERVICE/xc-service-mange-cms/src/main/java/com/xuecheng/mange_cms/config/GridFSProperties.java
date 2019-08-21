package com.xuecheng.mange_cms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class GridFSProperties {
    private String database;
}
