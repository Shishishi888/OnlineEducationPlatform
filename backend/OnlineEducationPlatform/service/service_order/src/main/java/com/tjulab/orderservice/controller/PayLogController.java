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
        return R.ok().data(QRCodeMap);
    }
}

