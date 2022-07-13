package com.tjulab.eduservice.entity.vo;

import lombok.Data;

@Data
public class CoursePublishInfoVo {
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String firstSubjectLevel;
    private String secondSubjectLevel;
    private String teacherName;
    private String price;
}
