package com.tjulab.orderservice.service.impl;

import com.tjulab.orderservice.entity.Order;
import com.tjulab.orderservice.mapper.OrderMapper;
import com.tjulab.orderservice.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author ShiYang
 * @since 2022-08-17
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
