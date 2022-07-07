package com.tjulab.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tjulab.eduservice.entity.EduSubject;
import com.tjulab.eduservice.entity.excel.SubjectData;
import com.tjulab.eduservice.service.EduSubjectService;
import com.tjulab.servicebase.exceptionhandler.MyException;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    // 因为SubjectExcelListener已经在EduSubjectServiceImp中进行了new操作，
    // 所以SubjectExcelListener不能再交给Spring进行管理，
    // 因此在SubjectExcelListener中不能通过@Autowired自动注入EduSubjectService等对象，
    // 需要通过有参构造函数将EduSubjectService注入进来

    public EduSubjectService eduSubjectService;

    public SubjectExcelListener() {}

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    /**
     * 按行读取Excel文件中的内容
     * @param data
     * @param context
     */
    @Override
    public void invoke(SubjectData data, AnalysisContext context) {
        if(data == null) {  // Excel文件内容为空
            throw new MyException(20001, "Excel文件内容为空");
        }

        // 将一级分类添加到数据库，且不能重复添加
        EduSubject existFirstSubject = this.existFirstSubject(eduSubjectService, data.getFirstSubjectName());
        if(existFirstSubject == null) {
            existFirstSubject = new EduSubject();
            existFirstSubject.setTitle(data.getFirstSubjectName());
            existFirstSubject.setParentId("0");
            eduSubjectService.save(existFirstSubject);
        }

        // 将二级分类添加到数据库，且不能重复添加
        String pid = existFirstSubject.getId();
        EduSubject existSecondSubject = this.existSecondSubject(eduSubjectService, data.getSecondSubjectName(), pid);
        if(existSecondSubject == null) {
            existSecondSubject = new EduSubject();
            existSecondSubject.setTitle(data.getSecondSubjectName());
            existSecondSubject.setParentId(pid);
            eduSubjectService.save(existSecondSubject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    // 用于判断是否重复添加一级分类
    private EduSubject existFirstSubject(EduSubjectService eduSubjectService, String subjectName) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", subjectName);
        wrapper.eq("parent_id", "0");
        EduSubject firstSubject = eduSubjectService.getOne(wrapper);
        return firstSubject;
    }

    // 用于判断是否重复添加二级分类
    private EduSubject existSecondSubject(EduSubjectService eduSubjectService, String subjectName, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", subjectName);
        wrapper.eq("parent_id", pid);
        EduSubject secondSubject = eduSubjectService.getOne(wrapper);
        return secondSubject;
    }

}
