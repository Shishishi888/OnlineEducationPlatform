package com.tjulab.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tjulab.eduservice.entity.EduTeacher;
import com.tjulab.eduservice.mapper.EduTeacherMapper;
import com.tjulab.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author ShiYang
 * @since 2022-06-14
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    /**
     * 查询前4名热门讲师
     * @return
     */
    @Cacheable(key = "'hotTeacherList'", value = "eduTeacherList")
    @Override
    public List<EduTeacher> getHotTeacherList() {
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 4");
        List<EduTeacher> eduTeacherList = baseMapper.selectList(queryWrapper);
        return eduTeacherList;
    }

    /**
     * 查询讲师（分页查询）
     * @param teacherPage
     * @return
     */
    @Override
    public Map<String, Object> getFrontTeacherPageList(Page<EduTeacher> teacherPage) {
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");  // 按照讲师id降序排列
        baseMapper.selectPage(teacherPage, queryWrapper);

        List<EduTeacher> records = teacherPage.getRecords();  // 当前页的讲师列表
        long current = teacherPage.getCurrent();
        long pages = teacherPage.getPages();
        long size = teacherPage.getSize();
        long total = teacherPage.getTotal();
        boolean hasNext = teacherPage.hasNext();
        boolean hasPrevious = teacherPage.hasPrevious();

        Map<String, Object> teacherPageMap = new HashMap<>();
        teacherPageMap.put("items", records);
        teacherPageMap.put("current", current);
        teacherPageMap.put("pages", pages);
        teacherPageMap.put("size", size);
        teacherPageMap.put("total", total);
        teacherPageMap.put("hasNext", hasNext);
        teacherPageMap.put("hasPrevious", hasPrevious);

        return teacherPageMap;
    }
}
