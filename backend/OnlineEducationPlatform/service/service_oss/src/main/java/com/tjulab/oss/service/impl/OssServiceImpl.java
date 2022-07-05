package com.tjulab.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.tjulab.oss.service.OssService;
import com.tjulab.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFile(MultipartFile file) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 获取文件输入流。
            InputStream inputStream = file.getInputStream();
            // 获取文件名称。
            String fileName = file.getOriginalFilename();
            // 在文件名称中添加唯一的随机值。
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;
            // 按照文件上传日期对文件进行分类。
            String datePath = new DateTime().toString("yyyy/MM/dd");  // 获取当前日期
            fileName = datePath + "/" + fileName;
            // 创建PutObject请求。
            ossClient.putObject(bucketName, fileName, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            // 返回文件上传到OSS之后的存储路径。
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
