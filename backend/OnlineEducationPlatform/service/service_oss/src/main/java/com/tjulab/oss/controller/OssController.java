package com.tjulab.oss.controller;

import com.tjulab.commonutils.R;
import com.tjulab.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ossservice/ossfile")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    /**
     * 上传文件到OSS
     * @param file
     * @return
     */
    @PostMapping
    public R uploadFile(MultipartFile file) {
        String url = ossService.uploadFile(file);
        return R.ok().data("url", url);
    }
    
}
