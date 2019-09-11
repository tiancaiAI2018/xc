package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Category;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

@Mapper
public interface CategoryMapper {
    CategoryNode selectList();
}
