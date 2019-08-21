package com.xuecheng.framework.domain.course.response;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CourseResult extends ResponseResult {
    private CourseBase courseBase;
    public CourseResult(ResultCode resultCode,CourseBase courseBase){
        super(resultCode);
        this.courseBase=courseBase;
    }
    public CourseResult(){}
}
