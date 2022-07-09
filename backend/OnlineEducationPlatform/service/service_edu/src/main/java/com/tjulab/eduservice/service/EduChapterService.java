package com.tjulab.eduservice.service;

import com.tjulab.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tjulab.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author ShiYang
 * @since 2022-07-08
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 查询课程大纲列表
     * @param courseId
     * @return
     */
    List<ChapterVo> getChapterVideoListByCourseId(String courseId);
}
