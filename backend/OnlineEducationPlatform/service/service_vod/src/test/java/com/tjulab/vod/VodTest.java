package com.tjulab.vod;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class VodTest {
    public static void main(String[] args) throws Exception {

        // 上传视频
        String accessKeyId = "LTAI5t6BHF7kadVLNcgF2R2s";
        String accessKeySecret = "pIu0tqPxN7sMi1QTVZlurm3Onuf0NP";
        String title = "6 - What If I Want to Move Faster.mp4";  // 视频文件上传后显示的名称
        String fileName = "/Users/shiyang/Projects/learn/OnlineEducationPlatform/data/video/6 - What If I Want to Move Faster.mp4";  // 本地视频文件的路径和名称
        uploadVideo(accessKeyId, accessKeySecret, title, fileName);

    }

    /**
     * 本地文件上传接口
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param fileName
     */
    private static void uploadVideo(String accessKeyId, String accessKeySecret, String title, String fileName) {
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        // System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }


    /**
     * 根据视频ID获取视频播放凭证
     * @throws Exception
     */
    public static void getPlayAuth() throws Exception {

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

    /**
     * 根据视频ID获取播放视频地址
     * 适用于非加密视频
     * @throws Exception
     */
    public static void getPlayUrl() throws Exception {

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
