import request from '@/utils/request'

export default {
    // 查询讲师列表（条件查询+分页）
    getTeacherListPage(current, limit, teacherQuery) {
        return request({
            // url: '/eduservice/teacher/pageTeacherCondition/' + current + '/' + limit,
            url: `/eduservice/teacher/pageTeacherCondition/${current}/${limit}`,
            method: 'post',
            // teacherQuery 条件对象
            // 后端使用 RequestBody 获取数据
            // data表示将对象以json的形式传递到后端接口
            data: teacherQuery
          })
    }
}