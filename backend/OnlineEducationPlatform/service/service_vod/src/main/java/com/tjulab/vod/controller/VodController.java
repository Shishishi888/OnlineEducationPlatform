package com.tjulab.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.tjulab.commonutils.R.R;
import com.tjulab.servicebase.exceptionhandler.MyException;
import com.tjulab.vod.service.VodService;
import com.tjulab.vod.utils.ConstantPropertiesUtils;
import com.tjulab.vod.utils.InitVodClient;
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

    /**
     * 获取视频凭证（根据视频ID获取）
     * @param videoId
     * @return
     */
    @GetMapping("getPlayAuth/{videoId}")
    public R getPlayAuth(@PathVariable String videoId) {
        try {
            // 1. 创建初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);

            // 2. 创建获取视频凭证的request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            // 3. 向request对象设置视频id
            request.setVideoId(videoId);

            // 4. 调用初始化对象的方法，获取视频播放凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth", playAuth);
        } catch (Exception e) {
            throw new MyException(20001, "获取视频播放凭证失败");
        }
    }
}
