import request from '@/utils/request'

export default {
  // 1. 查询热门课程和热门名师
  getHotCourseAndTeacher() {
    return request({
      url: '/eduservice/indexfront/getHotCourseAndTeacher',
      method: 'get'
    })
  }
}