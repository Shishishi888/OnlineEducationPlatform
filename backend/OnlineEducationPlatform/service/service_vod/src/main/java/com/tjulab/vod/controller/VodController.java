package com.tjulab.vod.controller;

import com.tjulab.commonutils.R;
import com.tjulab.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
     * 删除阿里云视频（根据视频ID删除）
     * @param videoId
     * @return
     */
    @DeleteMapping("deleteVideoFromAliyun/{videoId}")
    public R deleteVideoFromAliyun(@PathVariable String videoId) {
        vodService.deleteVideoFromAliyun(videoId);
        return R.ok();
    }
}
