package com.xuecheng.content.api;


import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@Api(value = "课程信息管理接口",tags = "课程信息管理接口")
public class CourseBaseInfoController {

    @Autowired
    private CourseBaseInfoService courseBaseInfoServiceImpl;

    @PostMapping("/course/list")
    @ApiOperation(value = "课程查询接口")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDto queryCourseParamsDto){

        return courseBaseInfoServiceImpl.queryCourseBaseList(pageParams,queryCourseParamsDto);

    }
}