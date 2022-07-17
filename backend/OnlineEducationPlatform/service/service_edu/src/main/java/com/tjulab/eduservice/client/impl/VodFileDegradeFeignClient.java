package com.tjulab.eduservice.client.impl;

import com.tjulab.commonutils.R;
import com.tjulab.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 实现hystrix熔断处理
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R deleteAliyunVideo(String videoId) {
        return R.error().message("删除视频失败");
    }

    @Override
    public R deleteAliyunVideos(List<String> videoIdList) {
        return R.error().message("删除视频失败");
    }
}
