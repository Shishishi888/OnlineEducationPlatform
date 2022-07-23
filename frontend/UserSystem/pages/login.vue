<template>
  <div class="main">
    <div class="title">
      <a class="active" href="/login">登录</a>
      <span>·</span>
      <a href="/register">注册</a>
    </div>

    <div class="sign-up-container">
      <el-form ref="userForm" :model="loginInfo">

        <el-form-item class="input-prepend restyle" prop="mobile" :rules="[{ required: true, message: '请输入手机号码', trigger: 'blur' },{validator: checkPhone, trigger: 'blur'}]">
          <div >
            <el-input type="text" placeholder="手机号" v-model="loginInfo.mobile"/>
            <i class="iconfont icon-phone" />
          </div>
        </el-form-item>

        <el-form-item class="input-prepend" prop="password" :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]">
          <div>
            <el-input type="password" placeholder="密码" v-model="loginInfo.password"/>
            <i class="iconfont icon-password"/>
          </div>
        </el-form-item>

        <div class="btn">
          <input type="button" class="sign-in-button" value="登录" @click="submitLogin()">
        </div>
      </el-form>
      <!-- 更多登录方式 -->
      <div class="more-sign">
        <h6>社交帐号登录</h6>
        <ul>
          <li><a id="weixin" class="weixin" target="_blank" href="http://qy.free.idcfengye.com/api/ucenter/weixinLogin/login"><i class="iconfont icon-weixin"/></a></li>
          <li><a id="qq" class="qq" target="_blank" href="#"><i class="iconfont icon-qq"/></a></li>
        </ul>
      </div>
    </div>

  </div>
</template>

<script>
  import '~/assets/css/sign.css';
  import '~/assets/css/iconfont.css';

  import cookie from 'js-cookie';
  import loginApi from '@/api/login';

  export default {
    layout: 'sign',

    data () {
      return {
        loginInfo:{  // 封装用户登录信息
          mobile:'',
          password:''
        },
        token: '',
        userInfo: {}
      }
    },

    methods: {
      // 提交用户登录信息
      submitLogin() {
        loginApi.submitLogin(this.loginInfo)
                .then(response => {
                  // 获取token字符串，并将token字符串放入cookie中
                  this.token = response.data.data.token;
                  cookie.set("user_token", this.token, {domain: "localhost"});

                  // 经过拦截器处理后调用后端接口，根据token字符串获取用户信息，并将获取的用户信息放入cookie中
                  loginApi.getUserInfo()
                          .then(response => {
                            this.userInfo = response.data.data.userInfo;
                            var userInfoStr = JSON.stringify(this.userInfo);
                            cookie.set("user_info", userInfoStr, {domain: "localhost"});
                            // cookie.set("user_info", JSON.stringify(this.userInfo), {domain: "localhost"});
                          });
                  
                  // 跳转到首页面
                  window.location.href = "/";
                });
      },

      checkPhone (rule, value, callback) {
        // debugger
        if (!(/^1[34578]\d{9}$/.test(value))) {
          return callback(new Error('手机号码格式不正确'))
        }
        return callback()
      }
    }
  }
</script>
<style>
   .el-form-item__error{
    z-index: 9999999;
  }
</style>