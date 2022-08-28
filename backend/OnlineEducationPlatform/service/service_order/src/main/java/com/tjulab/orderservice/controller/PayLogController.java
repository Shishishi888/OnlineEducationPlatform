package com.tjulab.orderservice.controller;


import com.tjulab.commonutils.R.R;
import com.tjulab.orderservice.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author ShiYang
 * @since 2022-08-17
 */
@RestController
@RequestMapping("/orderservice/paylog")
@CrossOrigin
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    /**
     * 生成微信支付二维码
     * @param orderNo
     * @return
     */
    @GetMapping("createWxQRCode/{orderNo}")
    public R createWxQRCode(@PathVariable String orderNo) {
        Map QRCodeMap = payLogService.createWxQRCode(orderNo);
        System.out.println(QRCodeMap);
        return R.ok().data(QRCodeMap);
    }

    /**
     * 查询课程订单支付状态（根据课程订单号查询）
     * @param orderNo
     * @return
     */
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        Map<String, String> resultMap = payLogService.queryPayStatus(orderNo);  // 查询微信支付状态
        System.out.println(resultMap);
        if(resultMap == null) {
            return R.error().message("支付失败");
        }
        if(resultMap.get("trade_state").equals("SUCCESS")) {
            payLogService.updateOrderStatus(resultMap);  // 添加课程购买记录，并更新课程订单支付状态
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }
}

