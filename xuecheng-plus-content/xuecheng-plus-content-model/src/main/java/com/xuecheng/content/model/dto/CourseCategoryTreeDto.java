package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.CourseCategory;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseCategoryTreeDto extends CourseCategory implements java.io.Serializable{

    List<CourseCategoryTreeDto> childrenTreeNodes;

}
