import request from '@/utils/request'

export default {
    // 1. 生成某一天的统计数据
    generateStatistics(date) {
        return request({
            url: '/statisticsservice/statistics/generateStatistics/' + date,
            method: 'post'
        });
    },

    // 2. 获取用于图表显示的统计数据
    getDataForChart(searchObj) {
        return request({
            url: `/statisticsservice/statistics/getDataForChart/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
            method: 'get'
        });
    }
}