package com.tjulab.eduservice.service;

import com.tjulab.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author ShiYang
 * @since 2022-07-06
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 添加课程分类
     * @param file
     */
    void saveSubject(MultipartFile file, EduSubjectService subjectService);
}
