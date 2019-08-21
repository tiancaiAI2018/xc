package com.xuecheng.mange_cms.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RabbitMQProperties.class)
public class RabbitMQConfig {
    @Value("${xc.mq.rabbitmq.exchange}")
    public static final String EXCHANGE="ex_routing_cms_postpage";
    private RabbitMQProperties rabbitMQProperties;
    public RabbitMQConfig(RabbitMQProperties rabbitMQProperties){
        this.rabbitMQProperties=rabbitMQProperties;
    }
    @Bean(EXCHANGE)
    /**
     * 创建一个持久化的交换机
     */
    public Exchange exchange(){
        return ExchangeBuilder.directExchange(this.rabbitMQProperties.getExchange()).durable(true).build();
    }
}
