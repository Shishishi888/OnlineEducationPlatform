package com.tjulab.orderservice.controller;


import com.tjulab.commonutils.R.R;
import com.tjulab.commonutils.jwt.JwtUtils;
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

}

