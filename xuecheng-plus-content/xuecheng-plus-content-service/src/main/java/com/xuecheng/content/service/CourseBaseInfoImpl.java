package com.xuecheng.content.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;


import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.model.po.CourseMarket;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class CourseBaseInfoImpl implements CourseBaseInfoService {

    @Autowired
    private CourseBaseMapper courseBaseMapper;

    @Autowired
    private CourseMarketMapper courseMarketMapper;

    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto) {
        LambdaQueryWrapper<CourseBase> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName()), CourseBase::getName, queryCourseParamsDto.getCourseName());
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getAuditStatus()), CourseBase::getAuditStatus, queryCourseParamsDto.getAuditStatus());
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getPublishStatus()), CourseBase::getStatus, queryCourseParamsDto.getPublishStatus());

        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        Page<CourseBase> queryResult = courseBaseMapper.selectPage(page, lambdaQueryWrapper);

        return new PageResult<>(queryResult.getRecords(), queryResult.getTotal(), pageParams.getPageNo(), pageParams.getPageSize());
    }

    @Transactional
    @Override
    public CourseBaseInfoDto createCourse(AddCourseDto addCourseDto, Long companyId) {
        //todo:这里加上参数校验
        CourseBase courseBase = new CourseBase();
        BeanUtils.copyProperties(addCourseDto,courseBase);
        courseBase.setCompanyId(companyId);
        courseBase.setAuditStatus("202002");
        courseBase.setStatus("203001");
        courseBase.setCreateDate(LocalDateTime.now());
        courseBase.setChangeDate(LocalDateTime.now());
        courseBaseMapper.insert(courseBase);

        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(addCourseDto,courseMarket);
        courseMarket.setId(courseBase.getId());
        if(addOrUpdateCourseMarket(courseMarket) <= 0) throw new RuntimeException("更新失败");

        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseMarket,courseBaseInfoDto);
        BeanUtils.copyProperties(courseBase,courseBaseInfoDto);

        CourseCategory stCourseCategory = courseCategoryMapper.selectById(courseBase.getSt());
        CourseCategory mtCourseCategory = courseCategoryMapper.selectById(courseBase.getMt());
        courseBaseInfoDto.setMtName(mtCourseCategory.getName());
        courseBaseInfoDto.setStName(stCourseCategory.getName());

        return courseBaseInfoDto;
    }

    private int addOrUpdateCourseMarket(CourseMarket courseMarket){
        String charge = courseMarket.getCharge();
        if(charge == null || charge.isEmpty()){
            throw new RuntimeException("收费字段不能为空");
        }

        //这里不用做空指针判断,因为他获取的为基本类型,基本类型不会有null
        if(courseMarket.getPrice() <= 0){
            throw new RuntimeException("课程收费不合理");
        }

        CourseMarket selectedById = courseMarketMapper.selectById(courseMarket.getId());
        int res = 0;
        if(selectedById == null){
            res = courseMarketMapper.insert(courseMarket);
        }else{
            res = courseMarketMapper.updateById(courseMarket);
        }
        return res;
    }

    @Override
    public CourseBaseInfoDto queryCourseBase(Long courseId) {
        //查询课程信息
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if(courseBase == null){
            return null;
        }
        //查询营销信息
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        //要返回的对象
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase,courseBaseInfoDto);
        if(courseMarket != null){
            BeanUtils.copyProperties(courseMarket,courseBaseInfoDto);
        }
        //查询分类名称
        CourseCategory courseCategoryBySt = courseCategoryMapper.selectById(courseBase.getSt());
        courseBaseInfoDto.setStName(courseCategoryBySt.getName());
        CourseCategory courseCategoryByMt = courseCategoryMapper.selectById(courseBase.getMt());
        courseBaseInfoDto.setMtName(courseCategoryByMt.getName());

        return courseBaseInfoDto;
    }

    @Override
    public CourseBaseInfoDto editCourseBase(EditCourseDto editCourseDto, Long companyId) {
        Long courseId = editCourseDto.getId();
        CourseBase courseBaseInDB = courseBaseMapper.selectById(editCourseDto.getId());

        if(courseBaseInDB == null) throw new XueChengPlusException("课程不存在");

        //查看数据库中的这个课程是否属于这个企业
        if(!companyId.equals(courseBaseInDB.getCompanyId())) {
            throw new XueChengPlusException("该课程不是您的企业所管理的");
        }
        BeanUtils.copyProperties(editCourseDto,courseBaseInDB);

        int res = courseBaseMapper.updateById(courseBaseInDB);
        if(res <= 0) throw new XueChengPlusException("更新失败");


        return queryCourseBase(courseId);
    }

}
