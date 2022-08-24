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
  },

  // 3. 生成微信支付二维码
  createWxQRCode(orderNo) {
    return request({
      url: '/orderservice/paylog/createWxQRCode/' + orderNo,
      method: 'get'
    });
  },

  // 4. 查询课程订单支付状态
  queryPayStatus(orderNo) {
    return request({
      url: '/orderservice/paylog/queryPayStatus/' + orderNo,
      method: 'get'
    });
  },
}