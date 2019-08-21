package com.xuecheng.mange_cms.controller;

import com.xuecheng.api.cms.CmsConfigControllerApi;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.response.CmsConfigResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.mange_cms.service.CmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cms/config")
public class CmsConfigController implements CmsConfigControllerApi {
    @Autowired
    private CmsConfigService cmsConfigService;
    @GetMapping("/modeal/{id}")
    @Override
    public CmsConfigResult findById(@PathVariable("id") String id) {
        CmsConfig cmsConfig = cmsConfigService.findById(id);
        if(cmsConfig==null)return new CmsConfigResult(CommonCode.FAIL,null);
        return new CmsConfigResult(CommonCode.SUCCESS,cmsConfig);
    }
}
