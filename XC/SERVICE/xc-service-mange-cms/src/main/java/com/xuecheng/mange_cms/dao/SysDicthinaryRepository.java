package com.xuecheng.mange_cms.dao;

import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SysDicthinaryRepository extends MongoRepository<SysDictionary,String> {
    SysDictionary findByDType(String type);
}
