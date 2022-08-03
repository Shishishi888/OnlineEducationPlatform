import request from '@/utils/request'

export default {
  // 1. 查询课程（条件查询+分页查询）
  getCourseFrontPageList(page, limit, courseFrontVo) {
    return request({
      url: `/eduservice/coursefront/getCourseFrontPageList/${page}/${limit}`,
      method: 'post',
      data: courseFrontVo
    });
  },

  // 2. 查询课程分类
  getSubjectList() {
    return request({
      url: '/eduservice/subject/getSubjectList',
      method: 'get',
    });
  }
}