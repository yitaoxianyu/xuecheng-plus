package com.xuecheng.base.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class PageResult<T> {
    private List<T> items;
    private Long count; //总数
    private Long page; //页数
    private Long pageSize; //一页有多少

}
