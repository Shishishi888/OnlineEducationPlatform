import axios from 'axios';
import { MessageBox, Message } from 'element-ui';
import cookie from 'js-cookie';

// 创建axios实例
const service = axios.create({
  baseURL: 'http://localhost:9001',  // api的base_url
  timeout: 20000  // 请求超时时间
});

// 设置request拦截器
service.interceptors.request.use(
  config => {
    // debugger
    if(cookie.get("user_token")) {
      // 将获取的token值放入request的header中
      config.headers["token"] = cookie.get("user_token");
    }
    return config;
  },
  err => {
    return Promise.reject(err);
  }
);

export default service;