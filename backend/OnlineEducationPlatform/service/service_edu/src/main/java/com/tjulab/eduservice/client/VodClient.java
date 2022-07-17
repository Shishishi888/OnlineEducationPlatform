package com.tjulab.eduservice.client;

import com.tjulab.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-vod")  // 设置需要调用的服务
@Component
public interface VodClient {

    @DeleteMapping("/vodservice/video/deleteVideoFromAliyun/{videoId}")
    public R deleteVideoFromAliyun(@PathVariable("videoId") String videoId);  // @PathVariable一定要指明参数的名称

}