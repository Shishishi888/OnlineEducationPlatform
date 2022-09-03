package com.tjulab.statisticsservice.schedule;

import com.tjulab.statisticsservice.service.StatisticsDailyService;
import com.tjulab.statisticsservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {

    @Autowired
    StatisticsDailyService statisticsDailyService;

    /**
     * task1
     * 在每天的上午1:00自动生成前一天的统计数据
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task1() {
        String date = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        statisticsDailyService.generateStatistics(date);
    }
}
