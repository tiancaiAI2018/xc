package com.xuecheng.framework.domain.course.response;

import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CoursePicResult extends ResponseResult {
    private CoursePic coursePic;
    public CoursePicResult(ResultCode resultCode,CoursePic coursePic){
        super(resultCode);
        this.coursePic=coursePic;
    }
}
