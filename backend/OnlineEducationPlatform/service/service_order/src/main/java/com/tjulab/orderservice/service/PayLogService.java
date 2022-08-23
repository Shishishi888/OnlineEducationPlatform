package com.tjulab.orderservice.service;

import com.tjulab.orderservice.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author ShiYang
 * @since 2022-08-17
 */
public interface PayLogService extends IService<PayLog> {

    /**
     * 生成微信支付二维码
     * @param orderNo
     * @return
     */
    Map createWxQRCode(String orderNo);

    /**
     * 查询微信支付状态（根据课程订单号查询）
     * @param orderNo
     * @return
     */
    Map<String, String> queryPayStatus(String orderNo);

    /**
     * 添加课程购买记录，并且更新课程订单支付状态
     * @param paramMap
     */
    void updateOrderStatus(Map<String, String> paramMap);
}
