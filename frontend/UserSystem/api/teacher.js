import request from '@/utils/request'

export default {
  // 1. 查询讲师（分页查询）
  getFrontTeacherList(page, limit) {
    return request({
      url: `/eduservice/teacherfront/getFrontTeacherList/${page}/${limit}`,
      method: 'post',
    });
  }
}