package com.tjulab.msmservice.controller;

import com.tjulab.commonutils.R.R;
import com.tjulab.msmservice.service.MsmService;
import com.tjulab.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/msmservice/msm")
@CrossOrigin
public class MemController {

    @Autowired
    private MsmService msmService;

    /**
     * 将4位随机验证码和目标手机号发送给阿里云
     * @param phoneNumber
     * @return
     */
    @GetMapping("sendCodeToAliyun/{phoneNumber}")
    public R sendCodeToAliyun(@PathVariable String phoneNumber) {
        // 生成4位随机验证码，并发送给阿里云
        String code = RandomUtil.getFourBitRandom();
        Map<String, Object> codeMap = new HashMap<>();
        codeMap.put("code", code);
        boolean isSend = msmService.sendCodeToAliyun(codeMap, phoneNumber);
        if(isSend) {
            return R.ok();
        } else {
            return R.error().message("短信发送失败");
        }
    }
}
