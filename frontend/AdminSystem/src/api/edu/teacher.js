import request from '@/utils/request'

export default {
    // 1. 查询讲师列表（条件查询+分页）
    getTeacherListPage(current, limit, teacherQuery) {
        return request({
            // url: '/eduservice/teacher/pageTeacherCondition/' + current + '/' + limit,
            url: `/eduservice/teacher/pageTeacherCondition/${current}/${limit}`,
            method: 'post',
            // teacherQuery 条件对象
            // 后端使用 RequestBody 获取数据
            // data表示将对象以json的形式传递到后端接口
            data: teacherQuery
        });
    },

    // 2. 删除讲师（逻辑删除）
    deleteTeacherById(id) {
        return request({
            url: `/eduservice/teacher/${id}`,
            method: 'delete',
        });
    },

    // 3. 添加讲师
    addTeacher(teacher) {
        return request({
            url: `/eduservice/teacher/addTeacher`,
            method: 'post',
            data: teacher
        });
    },

    // 4. 查询单个讲师（根据讲师ID查询）
    getTeacherInfo(teacherId) {
        return request({
            url: `/eduservice/teacher/getTeacher/${teacherId}`,
            method: 'get'
        });
    },

    // 5. 修改讲师
    updateTeacherInfo(teacher) {
        return request({
            url: `/eduservice/teacher/updateTeacher`,
            method: 'post',
            data: teacher
        });
    }
}