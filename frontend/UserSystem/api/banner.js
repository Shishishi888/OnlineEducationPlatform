import request from '@/utils/request'

export default {
  // 1. 查询banner
  getBannerList() {
    return request({
      url: '/cmsservice/cmsbannerfront/getBannerList',
      method: 'get'
    })
  }
}