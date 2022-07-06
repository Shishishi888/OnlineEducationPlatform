<template>
  <div class="app-container">
    添加讲师

    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name" />
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number
          v-model="teacher.sort"
          controls-position="right"
          :min="0"
        />
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <el-option :value="1" label="高级讲师" />
          <el-option :value="2" label="首席讲师" />
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career" />
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea" />
      </el-form-item>

      <!-- 讲师头像：TODO -->
       <!-- 讲师头像 -->
      <el-form-item label="讲师头像">

          <!-- 头衔缩略图 -->
          <pan-thumb :image="teacher.avatar"/>
          <!-- 文件上传按钮 -->
          <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
          </el-button>

          <!--
      v-show：是否显示上传组件
      :key：类似于id，如果一个页面多个图片上传控件，可以做区分
      :url：后台上传的url地址
      @close：关闭上传组件
      @crop-upload-success：上传成功后的回调 
        <input type="file" name="file"/>
      -->
          <image-cropper
                        v-show="imagecropperShow"
                        :width="300"
                        :height="300"
                        :key="imagecropperKey"
                        :url="BASE_API+'/ossservice/ossfile'"
                        field="file"
                        @close="close"
                        @crop-upload-success="cropSuccess"/>
      </el-form-item>

      <el-form-item>
        <el-button
          :disabled="addBtnDisabled"
          type="primary"
          @click="addOrUpdate"
          >保存</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import teacherApi from '@/api/edu/teacher'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default {
  components: { ImageCropper, PanThumb },
  data() {
    return {
      teacher: {
        name: '',
        sort: 0,
        level: 1,
        career: '',
        intro: '',
        avatar: '',
      },
      imagecropperShow: false,  // 是否显示上传弹框组件  
      imagecropperKey: 0,  // 上传组件的唯一标识
      BASE_API:process.env.BASE_API,  // 获取dev.env.js文件中的后端访问地址
      addBtnDisabled: false  // 是否禁用保存按钮
    };
  },
  created() {  // 在页面渲染之前执行；当多次路由跳转到同一个页面时，该方法只执行第一次
    this.init();
  },
  watch: {  // 监听
    $route(to, from) {  // 当路由发生变化时，该方法就会执行
      this.init();
    }
  },
  methods: {
    // 关闭上传头像的弹框
    close() {
      this.imagecropperShow = false;
      this.imagecropperKey = this.imagecropperKey + 1;  // 上传组件初始化
    },

    // 上传头像成功
    cropSuccess(data) {
      this.teacher.avatar = data.url;
      this.imagecropperShow = false;  // 关闭上传头像的弹框
      this.imagecropperKey = this.imagecropperKey + 1;  // 上传组件初始化
    },

    init() {
      // 判断路径中是否有讲师的ID值
      if(this.$route.params && this.$route.params.id) {  // 路径中有讲师的ID值，说明用户正在进行修改讲师操作
        // 从路径获取讲师的ID值
        const id = this.$route.params.id;
        // 调用根据讲师ID值查询讲师信息的方法
        this.getTeacherInfo(id);
      }
      else {  // 路径中没有讲师的ID值，说明用户正在进行添加讲师操作
        // 清空表单
        this.teacher = {};
        this.teacher.avatar = 'https://tjulab-online-education-platform.oss-cn-beijing.aliyuncs.com/2022/07/06/3755fcfb7e8c492aa0268ef9d12bbaf8file.png';  // 设置默认讲师头像
      }
    },

    addOrUpdate() {
      // 判断当前的操作是添加讲师还是修改讲师信息：根据this.teacher中是否有id值进行判断
      if(!this.teacher.id) {
        this.addTeacher();  // 添加讲师
      }
      else {
        this.updateTeacher();  // 修改讲师信息
      }
    },

    // 1. 添加讲师
    addTeacher() {
      teacherApi
        .addTeacher(this.teacher)
        .then((response) => {
          // 提示信息
          this.$message({
            type: "success",
            message: "添加成功!",
          });

          // 路由跳转（重定向）
          this.$router.push({path:'/teacher/teacherlist'})
        })
        .catch((error) => {
            console.log(error);
        });
    },

    // 2. 查询单个讲师（根据讲师ID查询）
    getTeacherInfo(id) {
      teacherApi.getTeacherInfo(id)
                .then(response => {
                  this.teacher = response.data.teacher;
                })
    },

    // 3. 修改讲师
    updateTeacher() {
      teacherApi.updateTeacherInfo(this.teacher)
                .then(response => {
                  // 提示信息
                  this.$message({
                    type: "success",
                    message: "修改成功!",
                  });

                  // 路由跳转（重定向）
                  this.$router.push({path:'/teacher/teacherlist'})
                })
                .catch((error) => {
                  console.log(error);
                });
    }
  },
};
</script>