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
}
