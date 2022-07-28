import request from '@/utils/request'

export default {
  // 1. 查询讲师（分页查询）
  getTeacherFrontPageList(page, limit) {
    return request({
      url: `/eduservice/teacherfront/getTeacherFrontPageList/${page}/${limit}`,
      method: 'post',
    });
  },

  // 2. 查询讲师详情（根据讲师ID查询）
  getTeacherFrontInfo(teacherId) {
    return request({
      url: `/eduservice/teacherfront/getTeacherFrontInfo/${teacherId}`,
      method: 'get',
    });
  }
}