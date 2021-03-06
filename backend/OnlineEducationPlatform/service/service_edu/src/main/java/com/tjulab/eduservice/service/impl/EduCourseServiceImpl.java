package com.tjulab.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tjulab.eduservice.entity.EduCourse;
import com.tjulab.eduservice.entity.EduCourseDescription;
import com.tjulab.eduservice.entity.EduTeacher;
import com.tjulab.eduservice.entity.frontvo.CourseFrontVo;
import com.tjulab.eduservice.entity.vo.CoursePublishInfoVo;
import com.tjulab.eduservice.entity.vo.course.CourseInfoVo;
import com.tjulab.eduservice.mapper.EduCourseMapper;
import com.tjulab.eduservice.service.EduChapterService;
import com.tjulab.eduservice.service.EduCourseDescriptionService;
import com.tjulab.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tjulab.eduservice.service.EduVideoService;
import com.tjulab.servicebase.exceptionhandler.MyException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduVideoService eduVideoService;

    /**
     * 添加课程信息
     * @param courseInfoVo
     * @return
     */
    @Override
    public String addCourseInfo(CourseInfoVo courseInfoVo) {
        // 1. 向课程表添加课程的基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insertResult = baseMapper.insert(eduCourse);  // 返回插入数据库的行数
        if(insertResult == 0) {
            throw new MyException(20001, "添加课程基本信息失败");
        }

        // 获取添加课程基本信息之后的课程id
        String courseId = eduCourse.getId();

        // 2. 向课程简介表添加课程的简介信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseId);  // 课程的基本信息与课程的描述信息要通过id一一对应起来
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.save(eduCourseDescription);

        return courseId;
    }

    /**
     * 查询课程信息（根据课程id查询）
     * @param courseId
     * @return
     */
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        // 1. 查询课程的基本信息
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        // 2. 查询课程的描述信息
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());

        return courseInfoVo;
    }

    /**
     * 修改课程信息
     * @param courseInfoVo
     */
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        // 1. 修改课程的基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int result = baseMapper.updateById(eduCourse);
        if(result == 0) {
            throw new MyException(20001, "修改课程信息失败！");
        }

        // 2. 修改课程的描述信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    /**
     * 查询课程发布确认信息（根据课程ID查询）
     * @param courseId
     * @return
     */
    @Override
    public CoursePublishInfoVo getCoursePublishInfo(String courseId) {
        CoursePublishInfoVo coursePublishInfoVo = baseMapper.getPublishCourseInfo(courseId);
        return coursePublishInfoVo;
    }

    /**
     * 删除课程（根据课程ID删除）
     * @param courseId
     */
    @Override
    public void deleteCourse(String courseId) {
        // 1. 根据课程id删除课程小节
        eduVideoService.deleteVideoByCourseId(courseId);

        // 2. 根据课程id删除课程章节
        eduChapterService.deleteChapterByCourseId(courseId);

        // 3. 根据课程id删除课程描述信息
        eduCourseDescriptionService.removeById(courseId);

        // 4. 根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);

        if(result == 0) {
            throw new MyException(20001, "删除失败");
        }
    }

    /**
     * 查询前8门热门课程
     * @return
     */
    @Cacheable(key = "'hotCourseList'", value = "eduCourseList")
    @Override
    public List<EduCourse> getHotCourseList() {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 8");
        List<EduCourse> eduCourseList = baseMapper.selectList(queryWrapper);
        return eduCourseList;
    }

    /**
     * 查询课程（条件查询+分页查询）
     * @param coursePage
     * @param courseFrontVo
     * @return
     */
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        // 拼接查询条件
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {  // 一级课程分类
            queryWrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {  // 二级课程分类
            queryWrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSalesVolumeSort())) {  // 课程销售量排序
            queryWrapper.orderByDesc("buy_count");
        }
        if(!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {  // 课程上架时间排序
            queryWrapper.orderByDesc("gmt_create");
        }
        if(!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {  // 课程价格排序
            queryWrapper.orderByDesc("price");
        }
        baseMapper.selectPage(coursePage, queryWrapper);

        List<EduCourse> records = coursePage.getRecords();  // 当前页的课程列表
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();

        Map<String, Object> coursePageMap = new HashMap<>();
        coursePageMap.put("items", records);
        coursePageMap.put("current", current);
        coursePageMap.put("pages", pages);
        coursePageMap.put("size", size);
        coursePageMap.put("total", total);
        coursePageMap.put("hasNext", hasNext);
        coursePageMap.put("hasPrevious", hasPrevious);

        return coursePageMap;
    }

}
