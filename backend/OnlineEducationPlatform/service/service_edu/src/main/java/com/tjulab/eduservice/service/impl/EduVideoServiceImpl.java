package com.tjulab.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tjulab.commonutils.R;
import com.tjulab.eduservice.client.VodClient;
import com.tjulab.eduservice.entity.EduVideo;
import com.tjulab.eduservice.mapper.EduVideoMapper;
import com.tjulab.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tjulab.servicebase.exceptionhandler.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
     */
    @Override
    public void deleteVideoByCourseId(String courseId) {
        // 1. 删除课程视频
        // 根据课程ID查询出所有的课程视频，并删除
        QueryWrapper<EduVideo> queryWrapperForAliyunVideo = new QueryWrapper<>();
        queryWrapperForAliyunVideo.eq("course_id", courseId);
        queryWrapperForAliyunVideo.select("video_source_id");  // 指定要查询的列
        List<EduVideo> eduVideoList = baseMapper.selectList(queryWrapperForAliyunVideo);

        List<String> vidoSourceIdList = new ArrayList<>();
        for (int i = 0; i < eduVideoList.size(); ++i) {
            EduVideo eduVideo = eduVideoList.get(i);
            String videoSourceId = eduVideo.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)) {
                vidoSourceIdList.add(videoSourceId);
            }
        }

        System.out.println(123);
        if(vidoSourceIdList.size() > 0) {
            vodClient.deleteAliyunVideos(vidoSourceIdList);
        }

        // 2. 删除课程小节
        QueryWrapper<EduVideo> queryWrapperForVideo = new QueryWrapper<>();
        queryWrapperForVideo.eq("course_id", courseId);
        baseMapper.delete(queryWrapperForVideo);
    }

    /**
     * 删除单个阿里云视频（根据视频ID删除）
     * @param videoId
     */
    @Override
    public void deleteAliyunVideo(String videoId) {
        R result = vodClient.deleteAliyunVideo(videoId);
        if(result.getCode() == 20001) {
            throw new MyException(20001, "删除视频失败，触发熔断器");
        }
    }
}
