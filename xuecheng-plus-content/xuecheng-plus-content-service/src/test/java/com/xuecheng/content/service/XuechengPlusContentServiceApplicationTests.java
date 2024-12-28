package com.xuecheng.content.service;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.config.MybatisPlusConfig;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class XuechengPlusContentServiceApplicationTests {

    @Autowired
    private CourseBaseMapper courseBaseMapper;

    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    @Autowired
    private CourseBaseInfoService courseBaseInfoServiceImpl;


    @Autowired
    private CourseCategoryService courseCategoryServiceImpl;

    @Test
    void contextLoads() {
    }

    @Test
    public void testCourseBaseMapper(){
        CourseBase courseBase = courseBaseMapper.selectById(18);
        Assertions.assertNotNull(courseBase);
    }

    @Test
    public void testCourseBaseService(){
        //查询条件
        QueryCourseParamsDto queryCourseParamsDto = new QueryCourseParamsDto();
        queryCourseParamsDto.setCourseName("java");
        queryCourseParamsDto.setAuditStatus("202004");
        queryCourseParamsDto.setPublishStatus("203001");

        //分页参数
        PageParams pageParams = new PageParams();
        pageParams.setPageNo(1L);//页码
        pageParams.setPageSize(3L);//每页记录数

        PageResult<CourseBase> courseBasePageResult = courseBaseInfoServiceImpl.queryCourseBaseList(pageParams, queryCourseParamsDto);
        System.out.println(courseBasePageResult);
    }

    @Test
    public void testCourseCategoryMapper(){
        List<CourseCategoryTreeDto> list = courseCategoryMapper.selectTreeNodes("1");
    }

    @Test
    public void testCourseCategoryService(){
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryServiceImpl.queryTreeNodes("1");
        System.out.println(courseCategoryTreeDtos);
    }

}
