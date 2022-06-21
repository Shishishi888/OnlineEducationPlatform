package com.tjulab.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tjulab.commonutils.R;
import com.tjulab.eduservice.entity.EduTeacher;
import com.tjulab.eduservice.entity.vo.TeacherQuery;
import com.tjulab.eduservice.mapper.EduTeacherMapper;
import com.tjulab.eduservice.service.EduTeacherService;
import com.tjulab.servicebase.exceptionhandler.MyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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

        // Integer a = 10 / 0;  // 异常

        try {
            Integer a = 10 / 0;
        }catch (Exception e) {
            // 执行自定义异常
            throw new MyException(20001, "执行MyException异常处理");
        }

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

    // 4. 条件查询讲师 + 分页
    // @GetMapping("pageTeacherCondition/{current}/{limit}")
    // public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit, TeacherQuery teacherQuery) {
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {  // RequestBody 使用json传递数据，把json数据封装到对应的对象中；必须使用post提交方式
                                                                                               // ResponseBody 返回数据，一般是json数据
                                                                                               // required = false 表示该参数值可以为空
        // 创建Page对象
        Page<EduTeacher> teacherPage = new Page<>(current, limit);

        // 构建条件：多条件组合查询
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        // 动态SQL
        // 判断条件值是否为空，如果不为空拼接条件
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();;
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name", name);  // name teacherQuery中的字段名称
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level", level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create", begin);  // gmt_create 表中的属性名称
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create", end);
        }

        // 分页查询
        eduTeacherService.page(teacherPage, wrapper);
        long total = teacherPage.getTotal();  // 总记录数
        List<EduTeacher> records = teacherPage.getRecords();  // 数据list集合
        Map map = new HashMap();
        map.put("total", total);
        map.put("records", records);
        return R.ok().data(map);
    }

    // 5. 添加讲师
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        if(save)
            return R.ok();
        else
            return R.error();
    }

    // 6. 根据id查询讲师
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

    // 6. 修改讲师
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if(flag){
            return R.ok();
        }
        else {
            return R.error();
        }
    }
}

