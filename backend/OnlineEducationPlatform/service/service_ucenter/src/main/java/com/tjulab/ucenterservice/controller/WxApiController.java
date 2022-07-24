package com.tjulab.ucenterservice.controller;

import com.google.gson.Gson;
import com.tjulab.servicebase.exceptionhandler.MyException;
import com.tjulab.ucenterservice.entity.UcenterMember;
import com.tjulab.ucenterservice.service.UcenterMemberService;
import com.tjulab.ucenterservice.utils.ConstantWxUtils;
import com.tjulab.ucenterservice.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("/ucenterservice/wx")
@CrossOrigin
public class WxApiController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    /**
     * 通过扫描微信二维码登录
     * @return
     */
    @GetMapping("loginByWxQRCode")
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

    /**
     * 获取扫码用户的个人信息
     * @param code
     * @param state
     * @return
     */
    @GetMapping("callback")
    public String callback(String code, String state) {
        // System.out.println("code: " +  code);
        // System.out.println("state: " + state);

        try {
            // 1. 获取code（临时票据，类似于手机验证码）

            // 2. 请求微信开放平台提供的固定地址，获取access_token和openid
            String baseAccessTokenURL = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            String accessTokenURL = String.format(
                    baseAccessTokenURL,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );

            // 通过httpclient发送http请求（通过httpclient可以模拟出浏览器的请求和响应过程）
            String accessTokenInfo = HttpClientUtils.get(accessTokenURL);

            // 将json字符串格式的accessTokenInfo转换成map集合
            Gson gson = new Gson();
            HashMap accessTokenInfoMap = gson.fromJson(accessTokenInfo, HashMap.class);

            // 获取access_token和openid
            String accessToken = (String) accessTokenInfoMap.get("access_token");
            String openId = (String) accessTokenInfoMap.get("openid");

            // 3. 请求微信开放平台提供的固定地址，获取扫码人的个人信息
            String baseUserInfoURL = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";

            String userInfoURL = String.format(
                    baseUserInfoURL,
                    accessToken,
                    openId
            );

            // 通过httpclient发送http请求
            String userInfo = HttpClientUtils.get(userInfoURL);

            // 将json字符串格式的accessTokenInfo转换成map集合
            HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);

            // 获取扫码用户的个人信息
            String nickName = (String) userInfoMap.get("nickname");      // 微信昵称
            String headImgURL = (String) userInfoMap.get("headimgurl");  // 微信头像

            // 4. 将扫码用户的个人信息添加到数据库中
            UcenterMember member = ucenterMemberService.getMemBerByOpenId(openId);
            if(member == null) {
                member = new UcenterMember();
                member.setOpenid(openId);
                member.setNickname(nickName);
                member.setAvatar(headImgURL);
                ucenterMemberService.save(member);
            }

            // 5. 跳转到首页面
            return "redirect:http://localhost:3000";

        } catch (Exception e) {
            throw new MyException(20001, "用户登录失败");
        }

    }
}
