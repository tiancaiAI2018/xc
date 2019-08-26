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

    /**
     * 保存教学计划
     * @param teachplan
     * @return
     */
    boolean saveTeachplan(Teachplan teachplan);

    /**
     * 分页查询课程基本信息包含图片
     * @param page
     * @param size
     * @param courseListRequest
     * @return
     */
    QueryResult<CourseInfo> findCourseList(int page, int size, CourseListRequest courseListRequest);

    /**
     * 添加课程基本信息
     * @param courseBase
     * @return
     */
    String addCourse(CourseBase courseBase);

    /**
     * 得到课程基本信息
     * @param courseId
     * @return
     */
    CourseBase getCourseBaseById(String courseId);

    /**
     * 修改课程基本信息
     * @param courseId
     * @param courseBase
     * @return
     */
    boolean editCourseBase(String courseId, CourseBase courseBase);

    /**
     * 获得课程的详细信息（基础，营销，图片，计划）
     * @param courseId
     * @return
     */
    CourseView getCourseView(String courseId);

    /**
     * 预览页面
     * @param courseId
     * @return
     */
    String preview(String courseId);

    /**
     * 一键发布页面
     * @param courseId
     * @return
     */
    String postPageQuick(String courseId);
}
