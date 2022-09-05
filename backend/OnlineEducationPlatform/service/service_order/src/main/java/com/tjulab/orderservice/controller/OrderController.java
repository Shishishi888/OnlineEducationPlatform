package com.tjulab.orderservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tjulab.commonutils.R.R;
import com.tjulab.commonutils.jwt.JwtUtils;
import com.tjulab.orderservice.entity.Order;
import com.tjulab.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author ShiYang
 * @since 2022-08-17
 */
@RestController
@RequestMapping("/orderservice/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 生成课程订单
     * @param courseId
     * @param request
     * @return
     */
    @PostMapping("createCourseOrder/{courseId}")
    public R createCourseOrder(@PathVariable String courseId, HttpServletRequest request) {
        // 获取用户id
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        // 生成课程订单，返回课程订单号
        String orderNo = orderService.createCourseOrder(courseId, userId);
        return R.ok().data("orderNo", orderNo);
    }

    /**
     * 查询课程订单信息（根据课程订单ID查询）
     * @param orderId
     * @return
     */
    @GetMapping("getCourseOrderInfo/{orderId}")
    public R getCourseOrderInfo(@PathVariable String orderId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderId);
        Order order = orderService.getOne(queryWrapper);
        return R.ok().data("order", order);
    }

    /**
     * 查询课程订单支付状态（根据课程ID和用户ID查询）
     * @param courseId
     * @param userId
     * @return
     */
    @GetMapping("isPay/{courseId}/{userId}")
    public boolean isPay(@PathVariable String courseId, @PathVariable String userId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.eq("member_id", userId);
        queryWrapper.eq("status", 1);  // 支付状态：0 未支付；1 已支付
        int count = orderService.count(queryWrapper);
        if(count > 0) {
            return true;
        }
        else {
            return false;
        }
    }
}
