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
    getCourseInfoByCourseId(courseId) {
        return request({
            url: '/eduservice/course/getCourseInfo/' + courseId,
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
    },

    // 5. 查询课程发布确认信息（根据课程ID查询）
    getCoursePublishInfoByCourseId(courseId) {
        return request({
            url: '/eduservice/course/getCoursePublishInfo/' + courseId,
            method: 'get'
        });
    },

    // 6. 课程发布
    publishCourse(courseId) {
        return request({
            url: '/eduservice/course/publishCourse/' + courseId,
            method: 'post'
        });
    },

    // 7. 查询课程列表
    getCourseList() {
        return request({
            url: '/eduservice/course/getCourseList',
            method: 'get'
        });
    },

    // 8. 查询课程列表（条件查询 + 分页）TODO

    // 9. 删除课程（根据课程ID删除，物理删除）
    deleteCourseById(courseId) {
        return request({
            url: '/eduservice/course/deleteCourse/' + courseId,
            method: 'delete',
        });
    },
}