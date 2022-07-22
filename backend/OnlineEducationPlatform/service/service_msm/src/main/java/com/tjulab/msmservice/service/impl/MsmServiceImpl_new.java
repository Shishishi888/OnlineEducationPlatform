//package com.tjulab.msmservice.service.impl;
//
//import com.alibaba.fastjson.JSONObject;
//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.IAcsClient;
//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.exceptions.ServerException;
//import com.aliyuncs.profile.DefaultProfile;
//import com.google.gson.Gson;
//import java.util.*;
//import com.aliyuncs.dysmsapi.model.v20170525.*;
//import com.tjulab.msmservice.service.MsmService;
//import com.tjulab.msmservice.utils.ConstantPropertiesUtils;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//
//@Service
//public class MsmServiceImpl_new implements MsmService {
//
//    /**
//     * 将4位随机验证码和目标手机号发送给阿里云作后续验证码短信发送
//     * @param codeMap
//     * @param phoneNumber
//     * @return
//     */
//    @Override
//    public boolean sendSms(Map<String, Object> codeMap, String phoneNumber) {
//        if(StringUtils.isEmpty(phoneNumber))
//            return false;
//
//        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
//        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
//        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
//
//        IAcsClient client = new DefaultAcsClient(profile);
//
//        SendSmsRequest request = new SendSmsRequest();
//        request.setSignName("阿里云短信测试");
//        request.setTemplateCode("SMS_154950909");
//        request.setPhoneNumbers(phoneNumber);
//        request.setTemplateParam(JSONObject.toJSONString(codeMap));
//
//        try {
//            SendSmsResponse response = client.getAcsResponse(request);
//            System.out.println(new Gson().toJson(response));
//            return true;
//        } catch (ServerException e) {
//            e.printStackTrace();
//            return false;
//        } catch (ClientException e) {
//            System.out.println("ErrCode:" + e.getErrCode());
//            System.out.println("ErrMsg:" + e.getErrMsg());
//            System.out.println("RequestId:" + e.getRequestId());
//            return false;
//        }
//    }
//
//}
