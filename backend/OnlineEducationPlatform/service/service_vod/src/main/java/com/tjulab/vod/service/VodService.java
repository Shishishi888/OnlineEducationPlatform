package com.tjulab.vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface VodService {
    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */
    String uploadVideo(MultipartFile file);

    /**
     * 删除阿里云视频（根据视频ID删除）
     * @param videoId
     */
    void deleteVideoFromAliyun(String videoId);
}
