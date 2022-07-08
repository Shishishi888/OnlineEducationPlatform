<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="信息描述">
        <el-tag type="info">Excel模版说明</el-tag>
        <el-tag>
          <i class="el-icon-download"/>
          <a :href="'/static/excel/edu_subject.xlsx'">点击下载模版</a>
        </el-tag>

      </el-form-item>

      <el-form-item label="选择Excel">
        <el-upload
          ref="upload"
          :auto-upload="false"
          :on-success="fileUploadSuccess"
          :on-error="fileUploadError"
          :disabled="importBtnDisabled"
          :limit="1"
          :action="BASE_API+'/eduservice/subject/addSubject'"
          name="file"
          accept="application/vnd.excel">
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button
            :loading="loading"
            style="margin-left: 10px;"
            size="small"
            type="success"
            @click="submitUpload">上传到服务器</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
    data() {
        return {
            BASE_API: process.env.BASE_API,  // 接口API地址
            // OSS_API: process.env.OSS_PATH,  // 阿里云OSS地址
            importBtnDisabled: false,  // 是否禁用按钮 
            loading: false
        }
    },
    created() {

    },
    methods: {
        // 点击Excel文件上传按钮
        submitUpload() {
            this.importBtnDisabled = true;
            this.loading = true;
            this.$refs.upload.submit();  // 对应的js原生代码：document.getElementById("upload").submit()
        },

        // Excel文件上传成功
        fileUploadSuccess(response) {
            this.loading = false;
            // 提示信息
            this.$message({
                type: 'success',
                message: '添加课程分类成功！'
            })

            // 跳转到课程分类列表页面
            this.$router.push({path:'/subject/subjectlist'});
        },

        // Excel文件上传失败
        fileUploadError(response) {
            // 提示信息
            this.loading = false;
            this.$message({
                type: 'error',
                message: '添加课程分类失败！'
            })
        }
    }
}
</script>