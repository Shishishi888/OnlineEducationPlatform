package com.tjulab.msmservice.controller;

import com.tjulab.commonutils.R.R;
import com.tjulab.msmservice.service.MsmService;
import com.tjulab.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/msmservice/msm")
@CrossOrigin
public class MemController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 发送验证码短信
     * 生成4位随机验证码，并将验证码和目标手机号发送给阿里云作后续验证码短信发送；
     * 将验证码的有效时间设置为5分钟；
     * @param phoneNumber
     * @return
     */
    @GetMapping("sendCodeToAliyun/{phoneNumber}")
    public R sendSms(@PathVariable String phoneNumber) {
        // 1. 从redis中获取验证码：获取成功
        String code = redisTemplate.opsForValue().get(phoneNumber);
        if(!StringUtils.isEmpty(code)) {
            return R.ok();
        }

        // 2. 从redis中获取验证码：获取失败
        code = RandomUtil.getFourBitRandom();  // 生成4位随机验证码
        Map<String, Object> codeMap = new HashMap<>();
        codeMap.put("code", code);
        boolean isSend = msmService.sendCodeToAliyun(codeMap, phoneNumber);  // 将验证码和目标手机号发送给阿里云作后续短信发送
        if(isSend) {
            redisTemplate.opsForValue().set(phoneNumber, code, 5, TimeUnit.MINUTES);  // 将验证码存入redis中，并将数据过期时间设置为5分钟
            return R.ok();
        } else {
            return R.error().message("短信发送失败");
        }
    }
}
