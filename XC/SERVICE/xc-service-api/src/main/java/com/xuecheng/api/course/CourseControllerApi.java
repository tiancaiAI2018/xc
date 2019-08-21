package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.domain.course.response.CourseResult;
import com.xuecheng.framework.domain.course.response.TeachplanNodeResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("课程管理")
public interface CourseControllerApi {
    @ApiOperation("展示课程计划")
    TeachplanNodeResult  findTeachplanList(String courseId);
    @ApiOperation("添加课程计划")
    ResponseResult saveTeachplan(Teachplan teachplan);
    @ApiOperation("展示课程列表")
    QueryResponseResult findCourseList( int page, int size, CourseListRequest courseListRequest);
    @ApiOperation("添加课程")
    AddCourseResult addCourse( CourseBase courseBase);
    @ApiOperation("基础课程详情")
    CourseResult getCourseBaseById(String courseId);
    @ApiOperation("修改基础课程")
    ResponseResult editCourseBase( String courseId, CourseBase courseBase);
}
