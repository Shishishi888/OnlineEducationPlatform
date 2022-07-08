package com.tjulab.eduservice.service.impl;

import com.tjulab.eduservice.entity.EduCourse;
import com.tjulab.eduservice.entity.EduCourseDescription;
import com.tjulab.eduservice.entity.vo.CourseInfoVo;
import com.tjulab.eduservice.mapper.EduCourseMapper;
import com.tjulab.eduservice.service.EduCourseDescriptionService;
import com.tjulab.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tjulab.servicebase.exceptionhandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author ShiYang
 * @since 2022-07-08
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    /**
     * 添加课程的基本信息
     * @param courseInfoVo
     */
    @Override
    public void addCourseInfo(CourseInfoVo courseInfoVo) {
        // 1. 向课程表添加课程的基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insertResult = baseMapper.insert(eduCourse);  // 返回插入数据库的行数
        if(insertResult == 0) {
            throw new MyException(20001, "添加课程基本信息失败");
        }

        // 获取添加课程基本信息之后的课程id
        String course_id = eduCourse.getId();

        // 2. 向课程简介表添加课程的简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(course_id);  // 课程的基本信息与课程的描述信息要通过id一一对应起来
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.save(eduCourseDescription);
    }
}
