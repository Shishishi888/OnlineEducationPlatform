package com.tjulab.excel;


import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {

        /**
         * 实现对Excel文件的写操作
         */

        // 设置要写入的Excel文件：文件路径 + 文件名称
        String fileName = "D:/testEasyExcel.xlsx";

        // 调用EasyEcel的方法
        EasyExcel.write(fileName, DemoData.class)
                .sheet("学生列表")  // 设置sheet的名称
                .doWrite(getDemoDataList());  // 以List的形式传入要写入Excel文件的数据
    }

    private static List<DemoData> getDemoDataList() {
        List<DemoData> demoDataList = new ArrayList<>();
        for(int i = 0; i < 10; ++i) {
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname("Lucy_"+i);
            demoDataList.add(demoData);
        }
        return demoDataList;
    }

}
