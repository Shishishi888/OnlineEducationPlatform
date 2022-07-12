package com.tjulab.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tjulab.eduservice.entity.EduChapter;
import com.tjulab.eduservice.entity.EduVideo;
import com.tjulab.eduservice.entity.vo.chapter.ChapterVo;
import com.tjulab.eduservice.entity.vo.chapter.VideoVo;
import com.tjulab.eduservice.mapper.EduChapterMapper;
import com.tjulab.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tjulab.eduservice.service.EduVideoService;
import com.tjulab.servicebase.exceptionhandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author ShiYang
 * @since 2022-07-08
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    /**
     * 查询课程大纲列表
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVo> getChapterVideoListByCourseId(String courseId) {
        // 1. 根据课程id查询课程章节
        QueryWrapper<EduChapter> eduChapterQueryWrapper = new QueryWrapper<>();
        eduChapterQueryWrapper.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(eduChapterQueryWrapper);

        // 2. 根据课程id查询课程小节
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id", courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(eduVideoQueryWrapper);

        List<ChapterVo> finalChapterList = new ArrayList<>();

        // 3. 遍历章节list进行封装
        for (int i = 0; i < eduChapterList.size(); ++i) {
            EduChapter eduChapter = eduChapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);

            List<VideoVo> videoVoList = new ArrayList<>();

            // 4. 遍历小节list进行封装
            for (int j = 0; j < eduVideoList.size(); ++j) {
                EduVideo eduVideo = eduVideoList.get(j);
                if(eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVoList.add(videoVo);
                }
            }

            chapterVo.setChildren(videoVoList);
            finalChapterList.add(chapterVo);
        }

        return finalChapterList;
    }

    /**
     * 删除章节
     * @param chapterId
     * @return
     */
    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = eduVideoService.count(wrapper);  // 返回符合条件的记录数
        if(count > 0) {  // 该章节下有小节，不允许删除
            throw new MyException(20001, "该章节下有小节，不允许删除");
        }
        else {  // 该章节下没有小节，允许删除
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }
    }
}
