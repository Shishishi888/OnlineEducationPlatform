import request from '@/utils/request'

export default {
  // 1. 查询banner的前两条记录
  getTopTwoBanner() {
    return request({
      url: '/cmsservice/cmsbannerfront/getTopTwoBanner',
      method: 'get'
    })
  }
}