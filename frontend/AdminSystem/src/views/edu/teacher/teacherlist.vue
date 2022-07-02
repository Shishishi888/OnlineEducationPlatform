<template>
  <div class="app-container">
    讲师列表
  </div>
</template>

<script>
// 引入teacher.js
import teacher from '@/api/edu/teacher'
export default {
//    data: {

//    },
    data() {  // 定义变量和初始值
        return {
            list: null,       // 通过后端接口查询得到的讲师列表
            page: 1,          // 当前页
            limit: 10,        // 每页记录数
            total: 0,         // 总记录数
            teacherQuery: {}  // 条件封装对象 
        }
    },
   created() {  // 页面渲染之前执行，一般调用methods中定义的方法
        this.getTeacherList();
   },
   methods: {  // 创建具体的方法，调用teahcer.js中定义的方法
        getTeacherList() {
            // 查询讲师列表（条件查询+分页）
            teacher.getTeacherListPage(this.page, this.limit, this.teacherQuery)
                .then(response => {  // 请求成功执行
                    // console.log(response);  // response是后端接口返回的数据
                    this.list = response.data.records;
                    this.total = response.data.total;
                    console.log(this.list);
                    console.log(this.total);
                })
                .catch(error => {  // 请求失败执行
                    console.log(error);
                })
        } 
   }
}
</script>