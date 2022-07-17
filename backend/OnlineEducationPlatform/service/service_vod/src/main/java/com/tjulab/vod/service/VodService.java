package com.tjulab.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */
    String uploadVideo(MultipartFile file);

    /**
     * 删除单个阿里云视频（根据视频ID删除）
     * @param videoId
     */
    void deleteAliyunVideo(String videoId);


    /**
     * 删除多个阿里云视频（根据视频ID删除）
     * @param videoIdList
     */
    void deleteAliyunVideos(List<String> videoIdList);
}
