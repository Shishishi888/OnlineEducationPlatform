import request from '@/utils/request'

export default {
    // 1. 查询课程大纲列表
    getAllChapterVideo(courseId) {
        return request({
            url: '/eduservice/chapter/getChapterVideo/' + courseId,
            method: 'get',
        })
    }
}