package com.tjulab.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.tjulab.eduservice.entity.EduSubject;
import com.tjulab.eduservice.entity.excel.SubjectData;
import com.tjulab.eduservice.listener.SubjectExcelListener;
import com.tjulab.eduservice.mapper.EduSubjectMapper;
import com.tjulab.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author ShiYang
 * @since 2022-07-06
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 添加课程分类
     * @param file
     */
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            // 获取Excel文件的文件输入流
            InputStream inputStream = file.getInputStream();

            // 读取课程分类Excel文件中的内容
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
