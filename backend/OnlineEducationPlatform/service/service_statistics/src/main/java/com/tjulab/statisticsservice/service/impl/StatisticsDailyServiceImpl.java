package com.tjulab.statisticsservice.service.impl;

import com.tjulab.commonutils.R.R;
import com.tjulab.statisticsservice.client.UcenterClient;
import com.tjulab.statisticsservice.entity.StatisticsDaily;
import com.tjulab.statisticsservice.mapper.StatisticsDailyMapper;
import com.tjulab.statisticsservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author ShiYang
 * @since 2022-08-31
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    // 统计某天的注册人数
    @Override
    public void countRegisterUser(String date) {
        R r = ucenterClient.countRegisterUser((date));
        Integer registerNum = (Integer) r.getData().get("registerNum");

        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(registerNum);  // 注册人数
        statisticsDaily.setDateCalculated(date);      // 统计日期
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100, 200));

        baseMapper.insert(statisticsDaily);
    }
}
