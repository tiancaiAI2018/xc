package com.xuecheng.mange_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.mange_cms.dao.CmsConfigRepository;
import com.xuecheng.mange_cms.service.CmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CmsConfigServiceImpl implements CmsConfigService {
    @Autowired
    private CmsConfigRepository cmsConfigRepository;
    @Override
    public CmsConfig findById(String id) {
        if(id==null) ExceptionCast.cast(CommonCode.INVALID_PARAM);
        Optional<CmsConfig> optional = cmsConfigRepository.findById(id);
        if(!optional.isPresent())ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        return optional.get();
    }
}
