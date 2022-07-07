package com.tjulab.eduservice.service;

import com.tjulab.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tjulab.eduservice.entity.subject.FirstSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
     * 添加课程分类（通过读取Excel文件）
     * @param file
     */
    void addSubject(MultipartFile file, EduSubjectService subjectService);

    /**
     * 获取课程分类列表（按树形型结构显示）
     * @return
     */
    List<FirstSubject> getFirstAndSecondSubject();
}
