package com.tjulab.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.util.ConverterUtils;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<DemoData> {
    @Override
    public void invoke(DemoData data, AnalysisContext context) {  // 按行读取Excel文件中的内容；跳过表头
        System.out.println("表中内容：" + data);
    }

    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {  // 读取Excel文件中的表头
        System.out.println("表头内容：" + headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {  // Excel文件读取完成之后执行该方法

    }
}
