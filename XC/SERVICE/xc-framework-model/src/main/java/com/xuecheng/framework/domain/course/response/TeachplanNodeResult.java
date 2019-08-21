package com.xuecheng.framework.domain.course.response;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TeachplanNodeResult extends ResponseResult {
    TeachplanNode teachplanNode;
    public TeachplanNodeResult(ResultCode resultCode,TeachplanNode teachplanNode){
        super(resultCode);
        this.teachplanNode=teachplanNode;
    }
}
