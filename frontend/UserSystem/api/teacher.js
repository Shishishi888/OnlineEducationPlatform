import request from '@/utils/request'

export default {
  // 1. 查询讲师（分页查询）
  getFrontTeacherPageList(page, limit) {
    return request({
      url: `/eduservice/teacherfront/getFrontTeacherPageList/${page}/${limit}`,
      method: 'post',
    });
  },

  // 2. 查询讲师详情（根据讲师ID查询）
  getFrontTeacherInfo(teacherId) {
    return request({
      url: `/eduservice/teacherfront/getFrontTeacherInfo/${teacherId}`,
      method: 'get',
    });
  }
}