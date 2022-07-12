import request from '@/utils/request'

export default {
    // 1. 查询课程大纲列表
    getAllChapterVideo(courseId) {
        return request({
            url: '/eduservice/chapter/getChapterVideo/' + courseId,
            method: 'get'
        })
    },

    // 2. 添加章节
    addChapter(chapter) {
        return request({
            url: '/eduservice/chapter/addChapter',
            method: 'post',
            data: chapter
        })
    },

    // 3. 查询章节信息（根据章节ID查询）
    getChapterInfo(chapterId) {
        return request({
            url: '/eduservice/chapter/getChapterInfo/' + chapterId,
            method: 'get'
        })
    },

    // 4. 修改章节信息
    updateChapterInfo(chapter) {
        return request({
            url: '/eduservice/chapter/updateChapter',
            method: 'post',
            data: chapter
        })
    },

    // 5. 删除章节
    deleteChapter(chapterId) {
        return request({
            url: '/eduservice/chapter/deleteChapter/' + chapterId,
            method: 'delete'
        })
    },
}