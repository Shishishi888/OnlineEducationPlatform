import request from '@/utils/request'

export default {
    // 1. 添加课程信息
    addCourseInfo(courseInfo) {
        return request({
            url: `/eduservice/course/addCourseInfo`,
            method: 'post',
            data: courseInfo
        });
    },

    // 2. 查询所有讲师
    getAllTeacher() {
        return request({
            url: `/eduservice/teacher/getAllTeacher`,
            method: 'get'
        });
    },

    // 3. 查询课程信息（根据课程id查询）
    getCourseInfoById(id) {
        return request({
            url: '/eduservice/course/getCourseInfo/' + id,
            method: 'get'
        });
    },

    // 4. 修改课程信息
    updateCourseInfo(courseInfo) {
        return request({
            url: '/eduservice/course/updateCourseInfo',
            method: 'post',
            data: courseInfo
        });
    }
}