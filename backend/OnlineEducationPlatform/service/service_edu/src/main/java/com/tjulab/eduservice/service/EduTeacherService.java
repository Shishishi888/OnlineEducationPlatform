package com.tjulab.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tjulab.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author ShiYang
 * @since 2022-06-14
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 查询前4名热门讲师
     * @return
     */
    List<EduTeacher> getHotTeacherList();

    /**
     * 查询讲师（分页查询）
     * @param teacherPage
     * @return
     */
    Map<String, Object> getFrontTeacherList(Page<EduTeacher> teacherPage);
}
