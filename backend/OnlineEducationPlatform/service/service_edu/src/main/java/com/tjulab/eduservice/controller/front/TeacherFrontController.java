package com.tjulab.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tjulab.commonutils.R.R;
import com.tjulab.eduservice.entity.EduCourse;
import com.tjulab.eduservice.entity.EduTeacher;
import com.tjulab.eduservice.service.EduCourseService;
import com.tjulab.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 查询讲师（分页查询）
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("getTeacherFrontPageList/{page}/{limit}")
    public R getTeacherFrontPageList(@PathVariable long page, @PathVariable long limit) {
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        Map<String, Object> teacherPageMap = eduTeacherService.getFrontTeacherPageList(teacherPage);
        return R.ok().data(teacherPageMap);
    }

    /**
     * 查询讲师详情（根据讲师ID查询）
     * @param teacherId
     * @return
     */
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    private R getTeacherFrontInfo(@PathVariable String teacherId) {
        // 1. 根据讲师id查询讲师的基本信息
        EduTeacher eduTeacher = eduTeacherService.getById(teacherId);

        // 2. 根据讲师id查询讲师所授的课程
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", teacherId);
        List<EduCourse> eduCourseList = eduCourseService.list(queryWrapper);

        return R.ok().data("teacherInfo", eduTeacher).data("courseList", eduCourseList);
    }

}
