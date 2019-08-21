package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CategoryControllerApi;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.domain.course.response.CategoryNodeResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.manage_course.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController implements CategoryControllerApi {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("list")
    @Override
    public CategoryNodeResult selectList() {
        CategoryNode categoryNode = categoryService.selectList();
        return new CategoryNodeResult(CommonCode.SUCCESS,categoryNode);
    }
}
