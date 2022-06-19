package com.tjulab.eduservice.controller;


import com.tjulab.eduservice.entity.EduTeacher;
import com.tjulab.eduservice.mapper.EduTeacherMapper;
import com.tjulab.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author ShiYang
 * @since 2022-06-14
 */
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {
    // 注入service
    @Autowired
    private EduTeacherService eduTeacherService;

    // rest风格
    // 1. 查询讲师表所有的数据
    @GetMapping("findAll")
    public List<EduTeacher> findAllTeacher() {
        List<EduTeacher> eduTeacherList = eduTeacherService.list(null);
        return eduTeacherList;
    }

    // 2. 逻辑删除讲师
    @DeleteMapping("{id}")
    public boolean removeTeacher(@PathVariable String id) {
        return eduTeacherService.removeById(id);
    }
}

