package com.tjulab.vod;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class VodTest {
    public static void main(String[] args) throws Exception {

        /**
         * 根据视频ID获取视频播放凭证
         */

        // 1. 创建初始化对象
        String keyId = "LTAI5t6BHF7kadVLNcgF2R2s";
        String keySecret = "pIu0tqPxN7sMi1QTVZlurm3Onuf0NP";
        DefaultAcsClient client = InitObject.initVodClient(keyId, keySecret);

        // 2. 创建获取视频凭证的request和response对象
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        // 3. 向request对象设置视频id
        String videoId = "af1b53e67a45491cb8cb1bc0c63ee2c8";
        request.setVideoId(videoId);

        // 4. 调用初始化对象的方法传递request，获取数据
        response = client.getAcsResponse(request);

        System.out.println("playAuth: " + response.getPlayAuth());

    }

    public static void getPlayUrl() throws Exception {

        /**
         * 根据视频ID获取播放视频地址
         * 适用于非加密视频
         */

        // 1. 创建初始化对象
        String keyId = "LTAI5t6BHF7kadVLNcgF2R2s";
        String keySecret = "pIu0tqPxN7sMi1QTVZlurm3Onuf0NP";
        DefaultAcsClient client = InitObject.initVodClient(keyId, keySecret);

        // 2. 创建获取视频地址的request和response对象
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        // 3. 向request对象设置视频id
        String videoId = "af1b53e67a45491cb8cb1bc0c63ee2c8";
        request.setVideoId(videoId);

        // 4. 调用初始化对象的方法传递request，获取数据
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");

    }
}
