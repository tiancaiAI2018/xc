package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.response.CategoryNodeResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("课程分类管理")
public interface CategoryControllerApi {
    @ApiOperation("查看所有的分类")
    CategoryNodeResult selectList();
}
