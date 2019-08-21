package com.xuecheng.manage_course.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("xc-service-mange-cms")
public interface CMSClient {
//    @PostMapping("")

}
