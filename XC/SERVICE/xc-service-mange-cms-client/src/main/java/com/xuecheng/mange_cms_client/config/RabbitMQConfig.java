package com.xuecheng.mange_cms_client.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RabbitMQProperties.class)
public class RabbitMQConfig {
    @Value("${xc.mq.rabbitmq.queue}")
    private static final String QUEUE="queue_cms_postpage";
    private static final String EXCHANGE="ex_routing_cms_postpage";
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
    @Bean(QUEUE)
    /**
     * 创建一个队列
     */
    public Queue queue(){
        return new Queue(this.rabbitMQProperties.getQueue());
    }
    @Bean
    /**
     * 功能描述: 将队列和交换机按照RoutingKey的方式绑定
     * 〈〉
     * @Param: [queue, exchange]
     * @Return: org.springframework.amqp.core.Binding
     * @Author: Administrator
     * @Date: 2019/8/3 14:51
     */
    public Binding binding(@Qualifier(QUEUE)Queue queue,@Qualifier(EXCHANGE)Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(this.rabbitMQProperties.getRoutingKey()).noargs();
    }
}
