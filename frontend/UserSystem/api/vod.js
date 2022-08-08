import request from '@/utils/request'

export default {
  // 1. 获取视频播放凭证
  getPlayAuth(videoId) {
    return request({
      url: `/vodservice/video/getPlayAuth/${videoId}`,
      method: 'get'
    });
  }
}