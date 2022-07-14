<template>
  <div class="app-container">
    课程列表

    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="courseQuery.title" placeholder="课程名称"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="courseQuery.status" clearable placeholder="课程状态">
          <el-option value="Normal" label="已发布"/>
          <el-option value="Draft" label="未发布"/>
        </el-select>
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getCourseList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table
      :data="courseList"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="title" label="课程名称" width="80" />

      <el-table-column label="头衔" width="80">
        <template slot-scope="scope">
          {{ scope.row.status === "Normal" ? "已发布" : "未发布" }}
        </template>
      </el-table-column>

      <el-table-column prop="lessonNum" label="课时数" />

      <el-table-column prop="gmtCreate" label="添加时间" width="160" />

      <el-table-column prop="viewCount" label="浏览数量" width="60" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'' + scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程信息</el-button>
          </router-link>
          <router-link :to="'' + scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程大纲</el-button>
          </router-link>
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="deleteCourseById(scope.row.id)"
            >删除课程信息</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getCourseList"
    />
  </div>
</template>

<script>
import course from "@/api/edu/course";
export default {
  data() {
    // 定义变量和初始值
    return {
      courseList: null, // 通过后端接口查询得到的讲师列表
      page: 1,          // 当前页
      limit: 3,         // 每页记录数
      total: 0,         // 总记录数
      courseQuery: {},  // 条件封装对象
    };
  },
  created() {
    // 页面渲染之前执行，一般调用methods中定义的方法
    this.getCourseList();
  },
  methods: {
    // 1. 查询课程列表
    getCourseList() {
      course.getCourseList()
        .then((response) => {
          this.courseList = response.data.courseList;
        })
        .catch((error) => {
          console.log(error);
        });
    },

    // 2. 查询课程列表（条件查询+分页）
    // TODO

    // 3. 重置课程列表
    resetData() {
        // 清空表单中的数据
        this.courseQuery = {}
        // 查询所有的课程数据
        this.getCourseList();
    },

    // 4. 编辑课程信息
    // TODO

    // 5. 编辑课程大纲
    // TODO

    // 4. 删除课程
    deleteCourseById(courseId) {
        this.$confirm('此操作将永久删除该课程记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {  // 点击“确定”执行
          course.deleteCourseById(courseId)
          .then((response) => {
            // 提示信息
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            // 刷新列表界面
            this.getCourseList();
          })
          .catch((error) => {
            console.log(error);
          });
        }).catch(() => {  // 点击“取消”执行
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
    }
  }
};
</script>