import request from '@/utils/request'

export default {
    // 1. 生成某一天的统计数据
    generateStatistics(date) {
        return request({
            url: '/statisticsservice/statistics/generateStatistics/' + date,
            method: 'post'
        });
    }
}