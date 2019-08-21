package com.xuecheng.framework.domain.course.response;

import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CourseMarketResult extends ResponseResult {
    private CourseMarket courseMarket;
    public CourseMarketResult(ResultCode resultCode,CourseMarket courseMarket){
        super(resultCode);
        this.courseMarket=courseMarket;
    }
}
