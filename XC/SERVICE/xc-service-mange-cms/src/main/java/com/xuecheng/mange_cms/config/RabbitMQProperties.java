package com.xuecheng.mange_cms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "xc.mq.rabbitmq")
@Data
public class RabbitMQProperties {
    private String exchange;
}
