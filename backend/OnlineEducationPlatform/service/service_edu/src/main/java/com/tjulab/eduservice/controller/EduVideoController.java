package com.tjulab.eduservice.controller;


import com.tjulab.commonutils.R;
import com.tjulab.eduservice.entity.EduVideo;
import com.tjulab.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author ShiYang
 * @since 2022-07-08
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    /**
     * 添加课程小节
     * @param eduVideo
     * @return
     */
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    /**
     * 添加课程小节 TODO
     */

    /**
     * 删除课程小节（根据小节ID删除）
     * @param videoId
     * @return
     */
    @DeleteMapping("deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable String videoId) {
        eduVideoService.removeById(videoId);
        return R.ok();
    }

}

