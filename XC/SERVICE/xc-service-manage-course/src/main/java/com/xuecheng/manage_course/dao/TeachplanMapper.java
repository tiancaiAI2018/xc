package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TeachplanMapper {
    /**
     * 功能描述: 通过courseId（课程ID）查询课程计划（树形数据）
     * 〈〉
     * @Param: [courseId]
     * @Return: com.xuecheng.framework.domain.course.ext.TeachplanNode
     * @Author: Administrator
     * @Date: 2019/8/3 21:31
     */
    TeachplanNode selectList(String courseId);
}
