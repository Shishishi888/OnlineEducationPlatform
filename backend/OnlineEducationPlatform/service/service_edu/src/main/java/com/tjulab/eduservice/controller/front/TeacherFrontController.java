package com.tjulab.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tjulab.commonutils.R.R;
import com.tjulab.eduservice.entity.EduTeacher;
import com.tjulab.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {
    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 查询讲师（分页查询）
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("getFrontTeacherPageList/{page}/{limit}")
    public R getFrontTeacherPageList(@PathVariable long page, @PathVariable long limit) {
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        Map<String, Object> teacherPageMap = eduTeacherService.getFrontTeacherPageList(teacherPage);
        return R.ok().data(teacherPageMap);
    }

}
