package com.tjulab.eduservice.service;

import com.tjulab.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tjulab.eduservice.entity.vo.CoursePublishInfoVo;
import com.tjulab.eduservice.entity.vo.course.CourseInfoVo;

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
     * 添加课程信息
     * @param courseInfoVo
     * @return
     */
    String addCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 查询课程信息（根据课程id查询）
     * @param courseId
     * @return
     */
    CourseInfoVo getCourseInfo(String courseId);

    /**
     * 修改课程信息
     * @param courseInfoVo
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 查询课程发布确认信息（根据课程ID查询）
     * @param courseId
     * @return
     */
    CoursePublishInfoVo getCoursePublishInfo(String courseId);

    /**
     * 删除课程（根据课程ID删除）
     * @param courseId
     */
    void deleteCourse(String courseId);
}
