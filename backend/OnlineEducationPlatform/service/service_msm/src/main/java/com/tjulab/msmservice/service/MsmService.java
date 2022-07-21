package com.tjulab.msmservice.service;

import java.util.Map;

public interface MsmService {
    /**
     * 将4位随机验证码和目标手机号发送给阿里云
     * @param codeMap
     * @param phoneNumber
     * @return
     */
    boolean sendCodeToAliyun(Map<String, Object> codeMap, String phoneNumber);
}
