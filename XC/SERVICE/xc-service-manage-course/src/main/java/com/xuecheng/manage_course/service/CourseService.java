package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.QueryResult;

public interface CourseService {
    /**
     * 功能描述: 查询该课程下的课程计划
     * 〈〉
     * @Param: [courseId]
     * @Return: com.xuecheng.framework.domain.course.ext.TeachplanNode
     * @Author: Administrator
     * @Date: 2019/8/3 22:39
     */
    TeachplanNode findTeachplanList(String courseId);

    boolean saveTeachplan(Teachplan teachplan);

    QueryResult<CourseInfo> findCourseList(int page, int size, CourseListRequest courseListRequest);

    String addCourse(CourseBase courseBase);

    CourseBase getCourseBaseById(String courseId);

    boolean editCourseBase(String courseId, CourseBase courseBase);

    CourseView getCourseView(String courseId);

    String preview(String courseId);
}
