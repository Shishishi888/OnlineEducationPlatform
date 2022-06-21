package com.tjulab.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tjulab.commonutils.R;
import com.tjulab.eduservice.entity.EduTeacher;
import com.tjulab.eduservice.mapper.EduTeacherMapper;
import com.tjulab.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author ShiYang
 * @since 2022-06-14
 */
@Api(description="讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {
    // 注入service
    @Autowired
    private EduTeacherService eduTeacherService;

    // rest风格
    // 1. 查询讲师表所有的数据
    @ApiOperation(value = "查询所有讲师")
    @GetMapping("findAll")
    public R findAllTeacher() {
        List<EduTeacher> eduTeacherList = eduTeacherService.list(null);
        return R.ok().data("items", eduTeacherList);
    }

    // 2. 逻辑删除讲师
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        if(flag){
            return R.ok();
        }
        else {
            return R.error();
        }
    }

    // 3. 分页查询讲师
    // current 当前页
    // limit   每页记录数
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit){
        Page<EduTeacher> teacherPage = new Page<>(current, limit);
        eduTeacherService.page(teacherPage, null);
        long total = teacherPage.getTotal();  // 总记录数
        List<EduTeacher> records = teacherPage.getRecords();  // 数据list集合
        Map map = new HashMap();
        map.put("total", total);
        map.put("records", records);
        return R.ok().data(map);
        //return R.ok().data("total", total).data("records", records);
    }
}

