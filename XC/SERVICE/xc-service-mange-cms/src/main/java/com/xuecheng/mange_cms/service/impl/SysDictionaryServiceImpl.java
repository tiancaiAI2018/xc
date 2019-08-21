package com.xuecheng.mange_cms.service.impl;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.mange_cms.dao.SysDicthinaryRepository;
import com.xuecheng.mange_cms.service.SysDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysDictionaryServiceImpl implements SysDictionaryService {
    @Autowired
    private SysDicthinaryRepository sysDicthinaryRepository;
    @Override
    public SysDictionary getByType(String type){
        SysDictionary sysDictionary = sysDicthinaryRepository.findByDType(type);
        return sysDictionary;
    }
}
