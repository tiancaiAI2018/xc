package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CourseMarketControllerApi;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.response.CourseMarketResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CourseMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course/market")
public class CourseMarketController implements CourseMarketControllerApi {
    @Autowired
    private CourseMarketService courseMarketService;
    @GetMapping("/{courseId}")
    @Override
    public CourseMarketResult getCourseMarketByCourseId(@PathVariable("courseId") String courseId) {
        CourseMarket market = courseMarketService.getCourseMarketByCourseId(courseId);
        return market!=null?new CourseMarketResult(CommonCode.SUCCESS,market):new CourseMarketResult(CommonCode.FAIL,null);
    }
    @PutMapping("/{courseId}")
    @Override
    public ResponseResult saveCourseMarket(@PathVariable("courseId") String courseId, @RequestBody CourseMarket courseMarket) {
        boolean b = courseMarketService.saveCourseMarket(courseId, courseMarket);
        return b?ResponseResult.SUCCESS():ResponseResult.FAIL();
    }
}
