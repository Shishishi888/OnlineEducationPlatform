package com.tjulab.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tjulab.eduservice.client.VodClient;
import com.tjulab.eduservice.entity.EduVideo;
import com.tjulab.eduservice.mapper.EduVideoMapper;
import com.tjulab.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author ShiYang
 * @since 2022-07-08
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    /**
     * 删除课程小节（根据课程ID删除）
     * @param courseId
     * TODO 删除课程小节对应的课程视频
     */
    @Override
    public void deleteVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        baseMapper.delete(queryWrapper);
    }

    /**
     * 删除阿里云视频（根据视频ID删除）
     * @param videoId
     */
    @Override
    public void deleteVideoFromAliyun(String videoId) {
        vodClient.deleteVideoFromAliyun(videoId);
    }
}
