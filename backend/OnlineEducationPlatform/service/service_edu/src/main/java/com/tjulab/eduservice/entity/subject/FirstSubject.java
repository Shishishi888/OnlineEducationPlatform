package com.tjulab.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程列表的一级分类
 */
@Data
public class FirstSubject {
    private String id;
    private String title;

    // 一个一级课程分类可以包含多个二级课程分类
    private List<SecondSubject> children = new ArrayList<>();
}
