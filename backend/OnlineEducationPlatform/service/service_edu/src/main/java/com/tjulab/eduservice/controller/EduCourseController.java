package com.tjulab.eduservice.controller;


import com.tjulab.commonutils.R;
import com.tjulab.eduservice.entity.vo.course.CourseInfoVo;
import com.tjulab.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 查询课程信息（根据课程id查询）
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
}

