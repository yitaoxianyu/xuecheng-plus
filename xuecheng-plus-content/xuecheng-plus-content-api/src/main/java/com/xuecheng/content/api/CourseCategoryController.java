package com.xuecheng.content.api;


import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@Api(value = "课程分类管理接口",tags = "课程分类管理接口")
public class CourseCategoryController {

    @Autowired
    private CourseCategoryService categoryServiceImpl;

    @GetMapping("/course-category/tree-nodes")
    public List<CourseCategoryTreeDto> queryTreeNodes(){
        return categoryServiceImpl.queryTreeNodes("1");
    }

}
