import request from '@/utils/request'

export default {
  // 1. 提交用户登录信息
  submitLogin(loginInfo) {
    return request({
      url: '/ucenterservice/user/login',
      method: 'post',
      data: loginInfo
    });
  },

  // 2. 获取用户信息（根据token获取）
  getUserInfo() {
    return request({
      url: '/ucenterservice/user/getUserInfo',
      method: 'get'
    });
  }
}