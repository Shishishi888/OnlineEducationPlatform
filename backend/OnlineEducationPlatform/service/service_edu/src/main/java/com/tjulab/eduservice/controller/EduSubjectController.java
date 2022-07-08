package com.tjulab.eduservice.controller;


import com.tjulab.commonutils.R;
import com.tjulab.eduservice.entity.subject.FirstSubject;
import com.tjulab.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author ShiYang
 * @since 2022-07-06
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

    /**
     * 添加课程分类（通过读取Excel文件）
     * @param file
     * @return
     */
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        subjectService.addSubject(file, subjectService);
        return R.ok();
    }

    /**
     * 查询课程分类列表（按树形型结构显示）
     * @return
     */
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        List<FirstSubject> list = subjectService.getFirstAndSecondSubject();
        return R.ok().data("list", list);
    }
}

