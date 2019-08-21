package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.domain.course.response.CourseResult;
import com.xuecheng.framework.domain.course.response.TeachplanNodeResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController implements CourseControllerApi {
    @Autowired
    private CourseService courseService;
    @Override
    @GetMapping("teachplan/{courseId}")
    public TeachplanNodeResult findTeachplanList(@PathVariable("courseId") String courseId) {
        TeachplanNode teachplanList = courseService.findTeachplanList(courseId);
        return new TeachplanNodeResult(CommonCode.SUCCESS,teachplanList);
    }
    @PostMapping("teachplan")
    @Override
    public ResponseResult saveTeachplan(@RequestBody Teachplan teachplan) {
        boolean success = courseService.saveTeachplan(teachplan);
        if(success)return ResponseResult.SUCCESS();
        return ResponseResult.FAIL();
    }
    @GetMapping("/list/{page}/{size}")
    @Override
    public QueryResponseResult findCourseList(@PathVariable("page") int page, @PathVariable("size") int size, CourseListRequest courseListRequest) {
        QueryResult<CourseInfo> courseList = courseService.findCourseList(page, size, courseListRequest);
        return new QueryResponseResult(CommonCode.SUCCESS,courseList);
    }

    @PostMapping
    @Override
    public AddCourseResult addCourse(@RequestBody CourseBase courseBase) {
        String id = courseService.addCourse(courseBase);
        if(!StringUtils.isEmpty(id))
            return new AddCourseResult(CommonCode.SUCCESS,id);
        return new AddCourseResult(CommonCode.FAIL,null);
    }
    @GetMapping("/{id}")
    @Override
    public CourseResult getCourseBaseById(@PathVariable("id") String courseId) {
        CourseBase courseBase = courseService.getCourseBaseById(courseId);
        return new CourseResult(CommonCode.SUCCESS,courseBase);
    }
    @PutMapping("/{id}")
    @Override
    public ResponseResult editCourseBase(@PathVariable("id") String courseId, @RequestBody CourseBase courseBase) {
        boolean b = courseService.editCourseBase(courseId, courseBase);
        return b?ResponseResult.SUCCESS():ResponseResult.FAIL();
    }
}
