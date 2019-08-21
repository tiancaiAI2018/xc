package com.xuecheng.mange_cms.controller;

import com.xuecheng.api.cms.SysDicthinaryControllerApi;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.framework.domain.system.response.SysDicthinaryResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.mange_cms.service.SysDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/dictionary")
public class SysDicthinaryController implements SysDicthinaryControllerApi {
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @GetMapping("/{type}")
    @Override
    public SysDicthinaryResult getByType(@PathVariable("type") String type) {
        SysDictionary sysDictionary = sysDictionaryService.getByType(type);
        return new SysDicthinaryResult(CommonCode.SUCCESS,sysDictionary);
    }
}
