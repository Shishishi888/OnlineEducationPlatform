package com.tjulab.ucenterservice.controller;

import com.tjulab.ucenterservice.utils.ConstantWxUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/ucenterservice/wx")
@CrossOrigin
public class WxAPiController {

    /**
     * 获取微信二维码
     * @return
     */
    @GetMapping("getWxQRCode")
    public String getWxQRCode() {

//        String baseURL = "https://open.weixin.qq.com/connect/qrconnect?appid="
//                            + ConstantWxUtils.WX_OPEN_APP_ID
//                            + "&response_type=code"
//                            + "...";

        String baseURL = "https://open.weixin.qq.com/connect/qrconnect"
                            + "?appid=%s"  // %s代表占位符
                            + "&redirect_uri=%s"
                            + "&response_type=code"
                            + "&scope=snsapi_login"
                            + "&state=%s"
                            + "#wechat_redirect";

        String redirectURL = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        // 对redirectURL进行URLEncoder编码
        try {
            redirectURL = URLEncoder.encode(redirectURL, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = String.format(
                    baseURL,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    redirectURL,
                    "tjulab"
                );

        return "redirect:" + url;
    }
}
