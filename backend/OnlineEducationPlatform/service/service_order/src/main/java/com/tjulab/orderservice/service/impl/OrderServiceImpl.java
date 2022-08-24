package com.tjulab.orderservice.service.impl;

import com.tjulab.commonvo.ordervo.CourseFrontIntroVoForOrder;
import com.tjulab.commonvo.ordervo.UcenterMemberForOrder;
import com.tjulab.orderservice.client.EduClient;
import com.tjulab.orderservice.client.UcenterClient;
import com.tjulab.orderservice.entity.Order;
import com.tjulab.orderservice.mapper.OrderMapper;
import com.tjulab.orderservice.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tjulab.orderservice.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 生成课程订单
     * @param courseId
     * @param userId
     * @return
     */
    @Override
    public String createCourseOrder(String courseId, String userId) {
        // 通过远程调用获取用户信息
       UcenterMemberForOrder ucenterMemberForOrder = ucenterClient.getUserInfoForOrder(userId);

        // 通过远程调用获取课程信息
        CourseFrontIntroVoForOrder courseFrontIntroVoForOrder = eduClient.getCourseInfoForOrder(courseId);

        // 创建Order对象
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseFrontIntroVoForOrder.getTitle());
        order.setCourseCover(courseFrontIntroVoForOrder.getCover());
        order.setTeacherName(courseFrontIntroVoForOrder.getTeacherName());
        order.setTotalFee(courseFrontIntroVoForOrder.getPrice());
        order.setMemberId(userId);
        order.setMobile(ucenterMemberForOrder.getMobile());
        order.setNickname(ucenterMemberForOrder.getNickname());
        order.setStatus(0);   // 支付状态
        order.setPayType(1);  // 支付方式：1表示微信支付；2表示支付宝支付

        baseMapper.insert(order);

        // 返回课程订单号
        return order.getOrderNo();
    }
}
