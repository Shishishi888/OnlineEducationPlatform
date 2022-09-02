package com.tjulab.statisticsservice.controller;


import com.tjulab.commonutils.R.R;
import com.tjulab.statisticsservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author ShiYang
 * @since 2022-08-31
 */
@RestController
@RequestMapping("/statisticsservice/statistics")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    /**
     * 生成某一天的统计数据
     * @param date
     * @return
     */
    @PostMapping("generateStatistics/{date}")
    public R generateStatistics(@PathVariable String date) {
        statisticsDailyService.generateStatistics(date);
        return R.ok();
    }
}

