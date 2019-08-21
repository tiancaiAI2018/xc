package com.xuecheng.manage_course.service.impl;

import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.manage_course.dao.CoursePicRepository;
import com.xuecheng.manage_course.service.CoursePicService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CoursePicServiceImpl implements CoursePicService {
    @Autowired
    private CoursePicRepository coursePicRepository;

    /**
     * 增加课程图片
     * @param coursePic
     * @return
     */
    @Override
    @Transactional
    public boolean addPic(CoursePic coursePic){
        if(coursePic==null|| StringUtils.isEmpty(coursePic.getCourseid())||StringUtils.isEmpty(coursePic.getPic()))
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        //根据课程id得到图片（一个课程只有一张图片）
        Optional<CoursePic> optional = coursePicRepository.findById(coursePic.getCourseid());
        //是否存在图片，没有则新建
        CoursePic coursePicNew = optional.isPresent()? optional.get():new CoursePic();
        coursePicNew.setCourseid(coursePic.getCourseid());
        coursePicNew.setPic(coursePic.getPic());
        //保存图片
        coursePicRepository.save(coursePicNew);
        return true;
    }

    /**
     * 查看图片
     * @param courseId
     * @return
     */
    @Override
    public CoursePic getPic(String courseId){
        if(StringUtils.isEmpty(courseId))ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        Optional<CoursePic> optional = coursePicRepository.findById(courseId);
        return optional.isPresent()?optional.get():null;
    }

    /**
     * 删除图片
     * @param courseId
     * @return
     */
    @Override
    @Transactional
    public boolean delPic(String courseId){
        if(StringUtils.isEmpty(courseId))ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        long delete = coursePicRepository.deleteByCourseid(courseId);
        return delete>=1?true:false;
    }
}
