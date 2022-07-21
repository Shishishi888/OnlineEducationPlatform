import request from '@/utils/request'

export default {
  // 1. 查询热门课程和热门名师
  getHotCourseAndTeacherList() {
    return request({
      url: '/eduservice/indexfront/getHotCourseAndTeacherList',
      method: 'get'
    })
  }
}