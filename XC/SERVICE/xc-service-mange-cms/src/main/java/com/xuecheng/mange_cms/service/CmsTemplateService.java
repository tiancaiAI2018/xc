package com.xuecheng.mange_cms.service;

import com.xuecheng.framework.domain.cms.CmsTemplate;

import java.util.List;

public interface CmsTemplateService {
    CmsTemplate findById(String id);
    List<CmsTemplate> findAll();
}
