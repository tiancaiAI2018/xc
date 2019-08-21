package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseMarket;

public interface CourseMarketService {
    boolean saveCourseMarket(String courseId, CourseMarket courseMarket);
    CourseMarket getCourseMarketByCourseId(String courseId);
}
