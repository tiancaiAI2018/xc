package com.xuecheng.mange_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;

public interface CmsConfigService {
    /**
     * 根据id获得数据模型
     * @param id
     * @return
     */
    CmsConfig findById(String id);
}
