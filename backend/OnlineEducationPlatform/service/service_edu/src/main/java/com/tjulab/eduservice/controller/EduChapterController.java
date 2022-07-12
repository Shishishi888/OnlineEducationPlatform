package com.tjulab.eduservice.controller;


import com.tjulab.commonutils.R;
import com.tjulab.eduservice.entity.EduChapter;
import com.tjulab.eduservice.entity.vo.chapter.ChapterVo;
import com.tjulab.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author ShiYang
 * @since 2022-07-08
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 查询课程大纲列表
     * @param courseId
     * @return
     */
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> chapterVideoList = eduChapterService.getChapterVideoListByCourseId(courseId);
        return R.ok().data("chapterVideoList", chapterVideoList);
    }

    /**
     * 添加章节
     * @param eduChapter
     * @return
     */
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    /**
     * 查询章节信息（根据章节ID查询）
     * @param chapterId
     * @return
     */
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("eduChapter", eduChapter);
    }

    /**
     * 修改章节信息
     * @param eduChapter
     * @return
     */
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    /**
     * 删除章节
     * @param chapterId
     * @return
     */
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean flag = eduChapterService.deleteChapter(chapterId);
        if(flag) {
            return R.ok();
        }
        else {
            return R.error();
        }
    }
}

