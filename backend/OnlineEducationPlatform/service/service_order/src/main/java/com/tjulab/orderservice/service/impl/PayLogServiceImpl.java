package com.tjulab.orderservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.tjulab.orderservice.entity.Order;
import com.tjulab.orderservice.entity.PayLog;
import com.tjulab.orderservice.mapper.PayLogMapper;
import com.tjulab.orderservice.service.OrderService;
import com.tjulab.orderservice.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tjulab.orderservice.utils.ConstantPropertiesUtil;
import com.tjulab.orderservice.utils.HttpClient;
import com.tjulab.servicebase.exceptionhandler.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author ShiYang
 * @since 2022-08-17
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderService orderService;

//    @Autowired
//    private ConstantPropertiesUtil constantPropertiesUtil;

//    private String appId = constantPropertiesUtil.APPID;
//    private String mchId = constantPropertiesUtil.MCH_ID;

//    private String partnerKey = constantPropertiesUtil.PARTNER_KEY;

    private final String WX_PAY_IP = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    private final String WX_PAY_QUERY_IP = "https://api.mch.weixin.qq.com/pay/orderquery";

    /**
     * 生成微信支付二维码
     *
     * @param orderNo
     * @return
     */
    @Override
    public Map createWxQRCode(String orderNo) {
        try {
            // 根据课程订单id查询课程订单信息
            QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("order_no", orderNo);
            Order order = orderService.getOne(queryWrapper);

            // 封装生成微信支付二维码所需要的参数
            String appId = ConstantPropertiesUtil.APPID;
            String mchId = ConstantPropertiesUtil.MCH_ID;
            String nonceStr = WXPayUtil.generateNonceStr();
            String courseTitle = order.getCourseTitle();
            String totalFee = order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "";
            String spbillCreateIp = ConstantPropertiesUtil.SPBILL_CREATE_IP;
            String notifyUrl = ConstantPropertiesUtil.NOTIFY_URL + "\n";
            String tradeType = ConstantPropertiesUtil.TRADE_TYPE;

            Map paramMap = new HashMap<>();
            paramMap.put("appid", appId);
            paramMap.put("mch_id", mchId);
            paramMap.put("nonce_str", nonceStr);
            paramMap.put("body", courseTitle);
            paramMap.put("out_trade_no", orderNo);
            paramMap.put("total_fee", totalFee);
            paramMap.put("spbill_create_ip", spbillCreateIp);  // 进行支付的项目的ip地址（或者域名）
            paramMap.put("notify_url", notifyUrl);             // 支付之后的回调地址
            paramMap.put("trade_type", tradeType);

            // 发送httpclient请求，向微信官方提供的固定地址传递xml参数
             String partnerKey = ConstantPropertiesUtil.PARTNER_KEY;
            HttpClient client = new HttpClient(WX_PAY_IP);
            String xmlParam = WXPayUtil.generateSignedXml(paramMap, partnerKey);
            client.setXmlParam(xmlParam);
            client.setHttps(true);
            client.post();

            // 获取httpclient请求结果，并解析
            String xmlResult = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xmlResult);

            // 返回最终的数据封装
            Map QRCodeMap = new HashMap<>();
            QRCodeMap.put("out_trade_no", orderNo);
            QRCodeMap.put("course_id", order.getCourseId());
            QRCodeMap.put("total_fee", order.getTotalFee());
            QRCodeMap.put("result_code", resultMap.get("result_code"));  // 操作二维码返回的状态码
            QRCodeMap.put("code_url", resultMap.get("code_url"));        // 二维码地址

            return QRCodeMap;

        } catch (Exception e) {
            throw new MyException(20001, "生成微信支付二维码失败");
        }
    }

    /**
     * 查询微信支付状态（根据课程订单号查询）
     *
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            // 封装参数
            String appId = ConstantPropertiesUtil.APPID;
            String mchId = ConstantPropertiesUtil.MCH_ID;
            String nonceStr = WXPayUtil.generateNonceStr();
            Map paramMap = new HashMap<>();
            paramMap.put("appid", appId);
            paramMap.put("mch_id", mchId);
            paramMap.put("out_trade_no", orderNo);
            paramMap.put("nonce_str", nonceStr);

            // 发送httpclient请求，向微信官方提供的固定地址传递xml参数
             String partnerKey = ConstantPropertiesUtil.PARTNER_KEY;
            HttpClient client = new HttpClient(WX_PAY_QUERY_IP);
            String xmlParam = WXPayUtil.generateSignedXml(paramMap, partnerKey);
            client.setXmlParam(xmlParam);
            client.setHttps(true);
            client.post();

            // 获取httpclient请求结果，并解析
            String xmlResult = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xmlResult);

            // 返回查询数据
            return resultMap;
        } catch (Exception e) {
            throw new MyException(20001, "查询课程支付状态失败");
        }
    }

    /**
     * 添加课程购买记录，并更新课程订单支付状态
     * @param paramMap
     */
    @Override
    public void updateOrderStatus(Map<String, String> paramMap) {
        // 获取课程订单号
        String orderNo = paramMap.get("out_trade_no");

        // 根据课程订单id查询课程订单信息
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(queryWrapper);

        // 更新课程订单支付状态
        if (order.getStatus().intValue() == 1) {  // 课程订单已经支付成功
            return;
        }
        order.setStatus(1);
        orderService.updateById(order);

        // 添加课程购买记录
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);  // 课程订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1);  // 支付类型
        payLog.setTotalFee(order.getTotalFee());
        payLog.setTradeState(paramMap.get("trade_state"));      // 支付状态
        payLog.setTransactionId(paramMap.get("tranction_id"));  // 课程订单流水号
        payLog.setAttr(JSONObject.toJSONString(paramMap));      // 其他属性

        baseMapper.insert(payLog);
    }
}
