import request from '@/utils/request'

export default {
  // 1. 向目标手机号发送手机验证码
  sendCode(phoneNumber) {
    return request({
      url: `/msmservice/msm/sendCms/${phoneNumber}`,
      method: 'get'
    });
  },

  // 2. 用户注册
  registerMember(registerInfo) {
    return request({
        url: `/ucenterservice/member/register`,
        method: 'post',
        data: registerInfo
      })
  }
}