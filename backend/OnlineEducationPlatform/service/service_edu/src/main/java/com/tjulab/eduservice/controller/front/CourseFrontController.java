package com.tjulab.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tjulab.commonutils.R.R;
import com.tjulab.eduservice.entity.EduCourse;
import com.tjulab.eduservice.entity.frontvo.CourseFrontVo;
import com.tjulab.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 查询课程（条件查询+分页查询）
     * @param page
     * @param limit
     * @param courseFrontVo
     * @return
     */
    @PostMapping("getCourseFrontPageList/{page}/{limit}")
    public R getCourseFrontPageList(@PathVariable long page,
                                @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> coursePage = new Page<>(page, limit);
        Map<String, Object> coursePageMap = eduCourseService.getCourseFrontPageList(coursePage, courseFrontVo);
        return R.ok().data(coursePageMap);
    }

}
