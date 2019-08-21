package com.xuecheng.mange_cms_client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "xc.mq.rabbitmq")
@Data
public class RabbitMQProperties {
    private String queue;
    private String routingKey;
    private String exchange;
}
