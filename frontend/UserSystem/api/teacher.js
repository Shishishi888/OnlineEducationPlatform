import request from '@/utils/request'

export default {
  // 1. 查询讲师（分页查询）
  getFrontTeacherPageList(page, limit) {
    return request({
      url: `/eduservice/teacherfront/getFrontTeacherPageList/${page}/${limit}`,
      method: 'post',
    });
  }
}