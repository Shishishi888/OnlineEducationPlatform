import request from '@/utils/request'

export default {
    // 1. 添加课程小节
    addVideo(video) {
        return request({
            url: '/eduservice/video/addVideo',
            method: 'post',
            data: video
        });
    },

    // 2. 修改课程小节
    updateVideo(video) {
        return request({
            url: '/eduservice/video/updateVideo',
            method: 'post',
            data: video
        });
    },

    // 3. 删除课程小节
    deleteVideo(videoId) {
        return request({
            url: '/eduservice/video/deleteVideo/' + videoId,
            method: 'delete'
        });
    },

    // 4. 删除单个阿里云视频
    deleteAliyunVideo(videoId) {
        return request({
            url: '/vodservice/video/deleteAliyunVideo/' + videoId,
            method: 'delete'
        });
    }
}