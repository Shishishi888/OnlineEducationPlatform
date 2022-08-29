package com.tjulab.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-order")
public interface OrderClient {

    /**
     * 查询课程订单支付状态（根据课程ID和用户ID查询）
     * @param courseId
     * @param userId
     * @return
     */
    @GetMapping("/orderservice/order/isPay/{courseId}/{userId}")
    public boolean isPay(@PathVariable("courseId") String courseId, @PathVariable("userId") String userId);
}
