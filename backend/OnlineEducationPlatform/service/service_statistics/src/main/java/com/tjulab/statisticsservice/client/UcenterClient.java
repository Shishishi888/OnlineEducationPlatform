package com.tjulab.statisticsservice.client;

import com.tjulab.commonutils.R.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    /**
     * 查询某一天的注册人数
     * @param date
     * @return
     */
    @GetMapping("/ucenterservice/user/countRegisterUser/{date}")
    public R countRegisterUser(@PathVariable("date") String date);
}
