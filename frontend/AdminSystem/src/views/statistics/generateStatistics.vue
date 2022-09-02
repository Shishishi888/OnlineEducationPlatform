<template>
  <div class="app-container">
    <!-- 表单 -->
    <el-form :inline="true" class="demo-form-inline">

      <el-form-item label="日期">
        <el-date-picker
          v-model="date"
          type="date"
          placeholder="选择要统计的日期"
          value-format="yyyy-MM-dd" />
      </el-form-item>


      <el-button
        :disabled="btnDisabled"
        type="primary"
        @click="generateStatistics()">生成</el-button>
    </el-form>

  </div>
</template>

<script>
import statistics from '@/api/statistics'
export default {
    data() {
        return {
            date: '',
            btnDisabled: false
        }
    },
    created() {

    },
    methods: {
        // 生成某一天的统计数据
        generateStatistics() {
            statistics.generateStatistics(this.date)
                        .then(response => {
                            // 提示信息
                            this.$message({
                                type: 'success',
                                message: '生成统计数据成功！'
                            });

                            // 跳转到图表显示页面
                            this.$router.push({path:'/statistics/showChart'});
                        });
        }
    }
}
</script>