package com.tjulab.eduservice.mapper;

import com.tjulab.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tjulab.eduservice.entity.frontvo.CourseFrontIntroVo;
import com.tjulab.eduservice.entity.vo.CoursePublishInfoVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author ShiYang
 * @since 2022-07-08
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    /**
     * 查询课程发布确认信息（根据课程ID查询）
     * @param courseId
     * @return
     */
    public CoursePublishInfoVo getPublishCourseInfo(String courseId);

    /**
     * 查询课程基本信息（用于课程详情页展示，根据课程ID查询）
     * @param courseId
     * @return
     */
    CourseFrontIntroVo getCourseBaseInfo(String courseId);
}
