package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.response.CoursePicResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("课程图片管理")
public interface CoursePicControllerApi {
    @ApiOperation("添加图片")
    ResponseResult addPic(CoursePic coursePic);
    @ApiOperation("获得图片")
    CoursePicResult getPic(String courseId);
    @ApiOperation("删除图片")
    ResponseResult delPic( String courseId);
}
