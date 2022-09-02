package com.tjulab.statisticsservice.service;

import com.tjulab.statisticsservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
