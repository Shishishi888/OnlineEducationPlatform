package com.tjulab.eduservice.service;

import com.tjulab.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tjulab.eduservice.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author ShiYang
 * @since 2022-07-08
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程的基本信息
     * @param courseInfoVo
     */
    void addCourseInfo(CourseInfoVo courseInfoVo);
}
