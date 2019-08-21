package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CoursePic;

public interface CoursePicService {
    boolean addPic(CoursePic coursePic);
    CoursePic getPic(String courseId);
    boolean delPic(String courseId);
}
