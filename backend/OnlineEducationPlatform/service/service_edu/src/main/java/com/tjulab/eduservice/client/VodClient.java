package com.tjulab.eduservice.client;

import com.tjulab.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod")  // 设置需要调用的服务
@Component
public interface VodClient {

    @DeleteMapping("/vodservice/video/deleteAliyunVideo/{videoId}")    // 完全路径
    public R deleteAliyunVideo(@PathVariable("videoId") String videoId);  // @PathVariable一定要指明参数的名称

    @DeleteMapping("/vodservice/video/deleteAliyunVideos")
    public R deleteAliyunVideos(@RequestParam("videoIdList") List<String> videoIdList);

}