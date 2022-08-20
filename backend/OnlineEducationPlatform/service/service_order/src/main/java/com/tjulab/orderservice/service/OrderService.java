package com.tjulab.orderservice.service;

import com.tjulab.orderservice.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author ShiYang
 * @since 2022-08-17
 */
public interface OrderService extends IService<Order> {

    /**
     * 生成课程订单
     * @param courseId
     * @param userId
     * @return
     */
    String createCourseOrder(String courseId, String userId);
}
