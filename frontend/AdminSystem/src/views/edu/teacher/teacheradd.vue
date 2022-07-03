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
export default {
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
      addBtnDisabled: false, // 是否禁用保存按钮
    };
  },
  created() {  // 在页面渲染之前执行
    // 判断路径中是否有讲师的ID值
    if(this.$route.params && this.$route.params.id) {
      // 从路径获取讲师的ID值
      const id = this.$route.params.id;
      // 调用根据讲师ID值查询讲师信息的方法
      this.getTeacherInfo(id);
    }
  },
  methods: {
    addOrUpdate() {
      this.addTeacher();  // 添加讲师
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
    }
  },
};
</script>