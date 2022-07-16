package com.tjulab.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.tjulab.servicebase.exceptionhandler.MyException;
import com.tjulab.vod.service.VodService;
import com.tjulab.vod.utils.ConstantPropertiesUtils;
import com.tjulab.vod.utils.InitVodClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class VodServiceImpl implements VodService {
    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */
    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
            String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
            // fileName：视频文件的原名称
            String fileName = file.getOriginalFilename();
            // title：视频文件上传之后显示的名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));  // 去掉文件名后缀
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除阿里云视频（根据视频ID删除）
     * @param videoId
     */
    @Override
    public void deleteVideoFromAliyun(String videoId) {
        try {
            // 1. 初始化对象
            String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
            String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
            DefaultAcsClient client = InitVodClient.initVodClient(accessKeyId, accessKeySecret);

            // 2. 创建删除视频的request对象
            DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest();

            // 3. 向request设置视频id
            deleteVideoRequest.setVideoIds(videoId);

            // 4. 调用初始化对象的方法删除视频
            client.getAcsResponse(deleteVideoRequest);

       } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(20001, "删除视频失败");
        }
    }
}
