package com.tjulab.vod.controller;

import com.tjulab.commonutils.R.R;
import com.tjulab.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/vodservice/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */
    @PostMapping("uploadVideoToAliyun")
    public R uploadVideoToAliyun(MultipartFile file) {
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId", videoId);
    }

    /**
     * 删除单个阿里云视频（根据视频ID删除）
     * @param videoId
     * @return
     */
    @DeleteMapping("deleteAliyunVideo/{videoId}")
    public R deleteAliyunVideo(@PathVariable String videoId) {
        vodService.deleteAliyunVideo(videoId);
        return R.ok();
    }

    /**
     * 删除多个阿里云视频（根据视频ID删除）
     * @param videoIdList
     * @return
     */
    @DeleteMapping("deleteAliyunVideos")
    public R deleteAliyunVideos(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.deleteAliyunVideos(videoIdList);
        return R.ok();
    }
}
