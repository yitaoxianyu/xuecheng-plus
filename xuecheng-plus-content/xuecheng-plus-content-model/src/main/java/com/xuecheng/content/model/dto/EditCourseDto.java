package com.xuecheng.content.model.dto;

import com.xuecheng.base.exception.ValidateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
@Data
public class EditCourseDto extends AddCourseDto{

    @ApiModelProperty(value = "课程id")
    @NotEmpty(groups = ValidateGroup.update.class,message = "id不能为空")
    private Long id;
}
