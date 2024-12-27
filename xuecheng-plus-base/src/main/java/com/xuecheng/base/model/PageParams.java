package com.xuecheng.base.model;


import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageParams {

    @ApiModelProperty("当前页码")
    private Long pageNo;

    @ApiModelProperty("每页记录默认值")
    private Long pageSize;
}

