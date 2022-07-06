package com.tjulab.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DemoData {

    @ExcelProperty("学生编号")  // 设置Excel文件的表头名称
    private Integer sno;

    @ExcelProperty("学生姓名")
    private String sname;
}
