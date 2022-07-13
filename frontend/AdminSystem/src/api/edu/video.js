import request from '@/utils/request'

export default {
    // 1. 添加小节
    addVideo(video) {
        return request({
            url: '/eduservice/video/addVideo',
            method: 'post',
            data: video
        });
    },

    // 2. 修改小节
    updateVideo(video) {
        return request({
            url: '/eduservice/video/updateVideo',
            method: 'post',
            data: video
        });
    },

    // 3. 删除小节
    deleteVideo(videoId) {
        return request({
            url: '/eduservice/video/deleteVideo/' + videoId,
            method: 'delete'
        });
    }
}