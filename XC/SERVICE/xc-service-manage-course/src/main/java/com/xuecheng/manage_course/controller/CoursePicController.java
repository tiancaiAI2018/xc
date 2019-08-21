package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CoursePicControllerApi;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.response.CoursePicResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CoursePicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course/pic")
public class CoursePicController implements CoursePicControllerApi {
    @Autowired
    private CoursePicService coursePicService;

    /**
     * 保存图片
     * @param coursePic
     * @return
     */
    @PostMapping
    @Override
    public ResponseResult addPic(@RequestBody CoursePic coursePic) {
        boolean b = coursePicService.addPic(coursePic);
        return b?ResponseResult.SUCCESS():ResponseResult.FAIL();
    }

    /**
     * 查看图片
     * @param courseId
     * @return
     */
    @GetMapping("/{id}")
    @Override
    public CoursePicResult getPic(@PathVariable("id") String courseId) {
        CoursePic pic = coursePicService.getPic(courseId);
        return new CoursePicResult(CommonCode.SUCCESS,pic);
    }

    /**
     * 删除图片
     * @param courseId
     * @return
     */
    @DeleteMapping("/{id}")
    @Override
    public ResponseResult delPic(@PathVariable("id") String courseId) {
        boolean b = coursePicService.delPic(courseId);
        return b?ResponseResult.SUCCESS():ResponseResult.FAIL();
    }
}
