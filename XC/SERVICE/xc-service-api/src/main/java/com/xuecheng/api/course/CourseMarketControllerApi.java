package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.response.CourseMarketResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("课程营销管理")
public interface CourseMarketControllerApi {
    @ApiOperation("查看当前课程营销信息")
    CourseMarketResult getCourseMarketByCourseId( String courseId);
    @ApiOperation("保存课程营销信息")
    ResponseResult saveCourseMarket(String courseId, CourseMarket courseMarket);
}
