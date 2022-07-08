package com.tjulab.eduservice.controller;


import com.tjulab.commonutils.R;
import com.tjulab.eduservice.entity.vo.CourseInfoVo;
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
     * 添加课程的基本信息
     * @param courseInfoVo
     * @return
     */
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.addCourseInfo(courseInfoVo);
        return R.ok();
    }
}

