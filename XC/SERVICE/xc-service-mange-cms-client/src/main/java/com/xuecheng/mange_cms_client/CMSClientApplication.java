package com.xuecheng.mange_cms_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("com.xuecheng.framework.domain.cms")
@ComponentScan(basePackages ={"com.xuecheng.mange_cms_client","com.xuecheng.framework.exception"})
public class CMSClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(CMSClientApplication.class,args);
    }
}
