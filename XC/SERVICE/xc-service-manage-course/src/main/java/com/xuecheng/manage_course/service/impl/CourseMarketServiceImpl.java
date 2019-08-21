package com.xuecheng.manage_course.service.impl;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import com.xuecheng.manage_course.dao.CourseMarketRepository;
import com.xuecheng.manage_course.service.CourseMarketService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CourseMarketServiceImpl implements CourseMarketService {
    @Autowired
    private CourseMarketRepository courseMarketRepository;
    /**
     * 获取该课程的营销信息
     * @param courseId
     * @return
     */
    @Override
    public CourseMarket getCourseMarketByCourseId(String courseId){
        if(StringUtils.isEmpty(courseId))ExceptionCast.cast(CommonCode.INVALID_PARAM);
        Optional<CourseMarket> optional = courseMarketRepository.findById(courseId);
        if(!optional.isPresent())return null;
        return optional.get();
    }

    /**
     * 添加或修改营销信息
     * @param courseId
     * @param courseMarket
     * @return
     */
    @Override
    @Transactional
    public boolean saveCourseMarket(String courseId,CourseMarket courseMarket){
        CourseMarket market = getCourseMarketByCourseId(courseId);
        if(market!=null){
            market.setPrice(courseMarket.getPrice());
            market.setCharge(courseMarket.getCharge());
            market.setEndTime(courseMarket.getEndTime());
            market.setPrice_old(courseMarket.getPrice());
            market.setQq(courseMarket.getQq());
            market.setStartTime(courseMarket.getStartTime());
            market.setValid(courseMarket.getValid());
            courseMarketRepository.save(market);
        }else{
            courseMarket.setId(courseId);
            courseMarketRepository.save(courseMarket);
        }
        return true;
    }
}
