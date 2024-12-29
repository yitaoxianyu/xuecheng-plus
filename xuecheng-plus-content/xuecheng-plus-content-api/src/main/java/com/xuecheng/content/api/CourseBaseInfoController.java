package com.xuecheng.content.api;


import com.xuecheng.base.exception.ValidateGroup;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@Api(value = "课程信息管理接口", tags = "课程信息管理接口")
public class CourseBaseInfoController {

    @Autowired
    private CourseBaseInfoService courseBaseInfoServiceImpl;


    //实际开发中,对于参数的校验一般是在controller层进行校验使用@Validated注解进行
    //但是可能一个dto类可能会在不同情况下使用,对应的校验功能也可能不同所以说需要有分组校验
    @PostMapping("/course/list")
    @ApiOperation(value = "课程查询")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDto queryCourseParamsDto) {
        return courseBaseInfoServiceImpl.queryCourseBaseList(pageParams, queryCourseParamsDto);
    }

    @PostMapping("/course")
    @ApiOperation(value = "添加课程")
    public CourseBaseInfoDto addCourse(@RequestBody @Validated(ValidateGroup.insert.class) AddCourseDto addCourseDto) {
        Long companyId = 1L;
        return courseBaseInfoServiceImpl.createCourse(addCourseDto,companyId);
    }

}
