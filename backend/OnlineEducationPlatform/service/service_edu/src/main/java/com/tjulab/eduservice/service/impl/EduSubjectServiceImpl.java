package com.tjulab.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tjulab.eduservice.entity.EduSubject;
import com.tjulab.eduservice.entity.excel.SubjectData;
import com.tjulab.eduservice.entity.subject.FirstSubject;
import com.tjulab.eduservice.entity.subject.SecondSubject;
import com.tjulab.eduservice.listener.SubjectExcelListener;
import com.tjulab.eduservice.mapper.EduSubjectMapper;
import com.tjulab.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
     * 添加课程分类（通过读取Excel文件）
     * @param file
     */
    @Override
    public void addSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            // 获取Excel文件的文件输入流
            InputStream inputStream = file.getInputStream();

            // 读取课程分类Excel文件中的内容
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询课程分类列表（按树形型结构显示）
     * @return
     */
    @Override
    public List<FirstSubject> getFirstAndSecondSubject() {
        // 1. 查询所有的一级分类课程
        QueryWrapper<EduSubject> wrapperForFirstSubject = new QueryWrapper<>();
        wrapperForFirstSubject.eq("parent_id", "0");  // parent_id == 0
        List<EduSubject> firstSubjectList = baseMapper.selectList(wrapperForFirstSubject);
        // this.list(wrapperForFirstSubject);

        // 2. 查询所有的二级分类课程
        QueryWrapper<EduSubject> wrapperForSecondSubject = new QueryWrapper<>();
        wrapperForSecondSubject.ne("parent_id", "0");  // parent_id != 0
        List<EduSubject> secondSubjectList = baseMapper.selectList(wrapperForSecondSubject);

        // 3. 封装一级分类课程
        List<FirstSubject> firstFinalSubjectList = new ArrayList<>();

        for(int i = 0; i < firstSubjectList.size(); ++i) {
            EduSubject eduSubjectFromFirstSubjectList = firstSubjectList.get(i);

            FirstSubject firstSubject = new FirstSubject();
            // firstSubject.setId(eduSubject.getId());
            // firstSubject.setTitle(eduSubject.getTitle());
            BeanUtils.copyProperties(eduSubjectFromFirstSubjectList, firstSubject);  // 将eduSubject中的属性值复制给firstSubject中对应的属性

            firstFinalSubjectList.add(firstSubject);

            // 4. 封装二级分类课程
            List<SecondSubject> secondFinalSubjectList = new ArrayList<>();

            for(int j = 0; j < secondSubjectList.size(); ++j) {
                EduSubject eduSubjectFromSecondSubjectList = secondSubjectList.get(j);
                if(eduSubjectFromSecondSubjectList.getParentId().equals(eduSubjectFromFirstSubjectList.getId())) {
                    SecondSubject secondSubject = new SecondSubject();
                    BeanUtils.copyProperties(eduSubjectFromSecondSubjectList, secondSubject);
                    secondFinalSubjectList.add(secondSubject);
                }
            }

            firstSubject.setChildren(secondFinalSubjectList);
        }

        return firstFinalSubjectList;
    }
}
