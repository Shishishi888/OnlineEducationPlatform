package com.tjulab.eduservice.client;

import com.tjulab.commonutils.R.R;
import com.tjulab.eduservice.client.impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)  // 设置需要调用的服务；设置熔断需要调用的处理类
@Component
public interface VodClient {

    /**
     * 删除单个阿里云视频（根据视频ID删除）
     * @param videoId
     * @return
     */
    @DeleteMapping("/vodservice/video/deleteAliyunVideo/{videoId}")    // 完全路径
    public R deleteAliyunVideo(@PathVariable("videoId") String videoId);  // @PathVariable一定要指明参数的名称

    /**
     * 删除多个阿里云视频（根据视频ID删除）
     * @param videoIdList
     * @return
     */
    @DeleteMapping("/vodservice/video/deleteAliyunVideos")
    public R deleteAliyunVideos(@RequestParam("videoIdList") List<String> videoIdList);

}