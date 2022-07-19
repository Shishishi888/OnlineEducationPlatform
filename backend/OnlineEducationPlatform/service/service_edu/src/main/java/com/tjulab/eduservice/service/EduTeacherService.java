package com.tjulab.eduservice.service;

import com.tjulab.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
    List<EduTeacher> getTopFourPopularTeacher();
}
