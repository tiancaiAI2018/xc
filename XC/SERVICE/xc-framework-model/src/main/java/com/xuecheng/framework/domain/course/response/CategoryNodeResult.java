package com.xuecheng.framework.domain.course.response;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CategoryNodeResult extends ResponseResult {
    private CategoryNode categoryNode;
    public CategoryNodeResult(ResultCode resultCode,CategoryNode categoryNode){
        super(resultCode);
        this.categoryNode=categoryNode;
    }
}
