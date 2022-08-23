package com.tjulab.orderservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${wx.pay.appid}")
    private String appId;

    @Value("${wx.pay.mch-id}")
    private String mchId;

    @Value("${wx.pay.partner-key}")
    private String partnerKey;

    @Value("${wx.pay.spbill-create-ip}")
    private String spbillCreateIp;

    @Value("${wx.pay.notify-url}")
    private String notifyUrl;

    @Value("${wx.pay.trade-type}")
    private String tradeType;

    public static String APPID;
    public static String MCH_ID;
    public static String PARTNER_KEY;
    public static String SPBILL_CREATE_IP;
    public static String NOTIFY_URL;
    public static String TRADE_TYPE;

    @Override
    public void afterPropertiesSet() throws Exception {
        APPID = appId;
        MCH_ID = mchId;
        PARTNER_KEY = partnerKey;
        SPBILL_CREATE_IP = spbillCreateIp;
        NOTIFY_URL = notifyUrl;
        TRADE_TYPE = tradeType;
    }

}
