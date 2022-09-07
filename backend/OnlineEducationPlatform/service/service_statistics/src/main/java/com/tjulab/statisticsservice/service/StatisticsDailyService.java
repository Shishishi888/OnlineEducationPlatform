package com.tjulab.statisticsservice.service;

import com.tjulab.statisticsservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author ShiYang
 * @since 2022-08-31
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    /**
     * 生成某一天的统计数据
     * @param date
     */
    void generateStatistics(String date);

    /**
     * 获取用于图表显示的统计数据（根据数据类型、日期范围查询）
     * @param type
     * @param begin
     * @param end
     * @return
     */
    Map<String, Object> getDataForChart(String type, String begin, String end);
}
