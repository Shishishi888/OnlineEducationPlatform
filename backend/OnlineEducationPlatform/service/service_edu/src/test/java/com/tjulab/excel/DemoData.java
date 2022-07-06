package com.tjulab.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DemoData {

    @ExcelProperty(value = "学生编号", index = 0)  // 设置Excel文件的表头名称；index = 0 表示对应Excel文件中的第1列
    private Integer sno;

    @ExcelProperty(value = "学生姓名", index = 1)
    private String sname;
}
