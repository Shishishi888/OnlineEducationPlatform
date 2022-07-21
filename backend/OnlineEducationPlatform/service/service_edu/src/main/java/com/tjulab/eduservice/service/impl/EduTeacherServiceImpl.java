package com.tjulab.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tjulab.eduservice.entity.EduTeacher;
import com.tjulab.eduservice.mapper.EduTeacherMapper;
import com.tjulab.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
