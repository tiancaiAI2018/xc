package com.xuecheng.mange_cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan("com.xuecheng.framework.domain.cms")
@ComponentScan(basePackages ={"com.xuecheng.api","com.xuecheng.mange_cms","com.xuecheng.framework.exception"})
public class CMSApplication {
    public static void main(String[] args) {
        SpringApplication.run(CMSApplication.class,args);
    }
}
