package com.tjulab.eduservice.controller;


import com.tjulab.commonutils.R;
import com.tjulab.eduservice.entity.EduCourse;
import com.tjulab.eduservice.entity.vo.CoursePublishInfoVo;
import com.tjulab.eduservice.entity.vo.course.CourseInfoVo;
import com.tjulab.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author ShiYang
 * @since 2022-07-08
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 查询课程列表
     * @return
     */
    @GetMapping("getCourseList")
    public R getCourseList() {
        List<EduCourse> eduCourseList = eduCourseService.list(null);
        return R.ok().data("courseList", eduCourseList);
    }

    /**
     * 查询课程列表（条件查询 + 分页） TODO
     */

    /**
     * 添加课程信息
     * @param courseInfoVo
     * @return
     */
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String courseId = eduCourseService.addCourseInfo(courseInfoVo);
        return R.ok().data("courseId", courseId);
    }

    /**
     * 查询课程信息（根据课程ID查询）
     * @param courseId
     * @return
     */
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    /**
     * 修改课程信息
     * @param courseInfoVo
     * @return
     */
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    /**
     * 查询课程发布确认信息（根据课程ID查询）
     * @param courseId
     * @return
     */
    @GetMapping("getCoursePublishInfo/{courseId}")
    public R getCoursePublishInfo(@PathVariable String courseId) {
        CoursePublishInfoVo coursePublishInfoVo = eduCourseService.getCoursePublishInfo(courseId);
        return R.ok().data("coursePublishInfo", coursePublishInfoVo);
    }

    /**
     * 课程发布
     * @param courseId
     * @return
     */
    @PostMapping("publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");  // 将 status 设置成 "Normal" 表示该课程已经发布
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    /**
     * 删除课程（根据课程ID删除，物理删除）
     * @param courseId
     * @return
     */
    @DeleteMapping("deleteCourse/{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        System.out.println(courseId);
        eduCourseService.deleteCourse(courseId);
        return R.ok();
    }
}

