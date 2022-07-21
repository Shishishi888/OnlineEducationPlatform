package com.tjulab.eduservice.controller.front;

import com.tjulab.commonutils.R.R;
import com.tjulab.eduservice.entity.EduCourse;
import com.tjulab.eduservice.entity.EduTeacher;
import com.tjulab.eduservice.service.EduCourseService;
import com.tjulab.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 获取热门课程和热门讲师列表
     * @return
     */
    @GetMapping("getHotCourseAndTeacherList")
    public R getHotCourseAndTeacherList() {
        // 1. 查询前8门热门课程
        List<EduCourse> hotCourseList = eduCourseService.getHotCourseList();
        // 2. 查询前4名热门讲师
        List<EduTeacher> hotTeacherList = eduTeacherService.getHotTeacherList();
        return R.ok().data("hotCourseList", hotCourseList).data("hotTeacherList", hotTeacherList);
    }

}
