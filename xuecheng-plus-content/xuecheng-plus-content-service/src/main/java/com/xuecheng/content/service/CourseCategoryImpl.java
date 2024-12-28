package com.xuecheng.content.service;


import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.po.CourseCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseCategoryImpl implements CourseCategoryService{

    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    public List<CourseCategoryTreeDto> queryTreeNodes(String id){
        List<CourseCategoryTreeDto> queryResult = courseCategoryMapper.selectTreeNodes(id);
        Map<String, CourseCategoryTreeDto> map = queryResult.stream().filter((item) -> !id.equals(item.getId())).collect(Collectors.toMap(CourseCategory::getId, value -> value, (key1, key2) -> key2));

        List<CourseCategoryTreeDto> result = new ArrayList<>();
        queryResult.stream().filter((item) -> !id.equals(item.getId())).forEach((item) -> {
            if(item.getParentid().equals(id)){
                result.add(item);
            }else{
                CourseCategoryTreeDto parentNode = map.get(item.getParentid());
                if(parentNode.getChildTreeNodes() == null) {
                    parentNode.setChildTreeNodes(new ArrayList<>());
                }
                parentNode.getChildTreeNodes().add(item);
            }
        });
        return result;
    }

}
