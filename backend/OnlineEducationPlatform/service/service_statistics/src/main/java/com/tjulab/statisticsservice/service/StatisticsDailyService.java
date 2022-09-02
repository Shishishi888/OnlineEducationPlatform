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
     * 统计某天的注册人数
     * @param date
     */
    void countRegisterUser(String date);
}
