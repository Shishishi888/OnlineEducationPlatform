import request from '@/utils/request'

export default {
  // 1. 生成课程订单
  createCourseOrder(courseId) {
    return request({
      url: '/orderservice/order/createCourseOrder/' + courseId,
      method: 'post'
    });
  },

  // 2. 查询课程订单信息
  getCourseOrderInfo(orderId) {
    return request({
        url: '/orderservice/order/getCourseOrderInfo/' + orderId,
        method: 'get'
      });
  }
}