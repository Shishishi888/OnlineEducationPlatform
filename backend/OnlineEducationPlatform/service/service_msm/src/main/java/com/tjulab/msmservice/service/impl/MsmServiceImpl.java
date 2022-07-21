package com.tjulab.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.tjulab.msmservice.service.MsmService;
import com.tjulab.msmservice.utils.ConstantPropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    /**
     * 将4位随机验证码和目标手机号发送给阿里云作后续验证码短信发送
     * @param codeMap
     * @param phoneNumber
     * @return
     */
    @Override
    public boolean sendCodeToAliyun(Map<String, Object> codeMap, String phoneNumber) {
        if(StringUtils.isEmpty(phoneNumber))
            return false;

        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        // 设置相关的固定参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        // 设置与发送短信相关的参数
        request.putQueryParameter("PhoneNumbers", phoneNumber);                        // 设置目标手机号
        request.putQueryParameter("SignName", "阿里云短信测试");                   // 设置申请的阿里云短信业务的签名名称
        request.putQueryParameter("TemplateCode", "SMS_154950909");              // 设置申请的阿里云短信业务的模版code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(codeMap));  // 设置验证码，要求以json的格式进行传递

        // 将相关信息发送给阿里云
        try {
            CommonResponse commonResponse = client.getCommonResponse(request);
            boolean isSuccess = commonResponse.getHttpResponse().isSuccess();
            return isSuccess;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }
}
