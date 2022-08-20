package com.tjulab.orderservice.client;

import com.tjulab.commonvo.ordervo.UcenterMemberForOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    /**
     * 查询用户信息（根据用户ID查询）
     * @param userId
     * @return
     */
    @PostMapping("/ucenterservice/user/getUserInfoForOrder/{userId}")
    public UcenterMemberForOrder getUserInfoForOrder(@PathVariable("userId") String userId);
}
