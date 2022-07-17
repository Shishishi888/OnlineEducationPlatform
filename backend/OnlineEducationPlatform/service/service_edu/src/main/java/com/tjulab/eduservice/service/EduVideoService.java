package com.tjulab.eduservice.service;

import com.tjulab.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author ShiYang
 * @since 2022-07-08
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 删除课程小节（根据课程ID删除）
     * @param courseId
     */
    void deleteVideoByCourseId(String courseId);

    /**
     * 删除单个阿里云视频（根据视频ID删除）
     * @param videoId
     */
    void deleteAliyunVideo(String videoId);
}
