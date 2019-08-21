package com.xuecheng.mange_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.mange_cms.dao.CmsTemplateRespository;
import com.xuecheng.mange_cms.service.CmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CmsTemplateServiceImpl implements CmsTemplateService {
    @Autowired
    private CmsTemplateRespository cmsTemplateRespository;
    @Override
    public CmsTemplate findById(String id) {
        if(id==null) ExceptionCast.cast(CommonCode.INVALID_PARAM);
        Optional<CmsTemplate> optional = cmsTemplateRespository.findById(id);
        if(!optional.isPresent())ExceptionCast.cast(CommonCode.INVALID_PARAM);
        return optional.get();
    }

    @Override
    public List<CmsTemplate> findAll() {
        return cmsTemplateRespository.findAll();
    }
}
