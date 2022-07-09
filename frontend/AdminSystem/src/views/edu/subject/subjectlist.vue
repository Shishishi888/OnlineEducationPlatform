<template>
  <div class="app-container">
    <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:30px;" />

    <el-tree
      ref="tree2"
      :data="subjectList"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all
    />

  </div>
</template>

<script>
import subject from '@/api/edu/subject'

export default {
  data() {
    return {
      filterText: '',
      subjectList: [],  // 返回的所有课程分类数据
      defaultProps: {
        children: 'children',
        label: 'title'
      }
    }
  },
  created() {
    this.getAllSubject();
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  methods: {
    // 查询课程分类列表
    getAllSubject() {
      subject.getSubjectList()
              .then(response => {
                this.subjectList = response.data.subjectList;
              })
    },

    // 文本索引（不区分字母大小写）
    filterNode(value, data) {
      if (!value) return true
      return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1
    }
  }
}
</script>

