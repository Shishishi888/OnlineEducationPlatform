<template>
  <div class="app-container">
    <h2 style="text-align: center">发布新课程</h2>

    <el-steps
      :active="2"
      process-status="wait"
      align-center
      style="margin-bottom: 40px"
    >
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="最终发布" />
    </el-steps>

    <!-- 章节 -->
    <ul class="chapterVideoList">
      <li v-for="chapter in chapterVideoList" :key="chapter.id">
        <p>
          {{ chapter.title }}
        </p>
        <!-- 视频 -->
        <ul class="chanpterList videoList">
          <li v-for="video in chapter.children" :key="video.id">
            <p>
              {{ video.title }}
            </p>
          </li>
        </ul>
      </li>
    </ul>

    <div>
      <el-button @click="previous">上一步</el-button>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="next"
        >下一步</el-button
      >
    </div>
  </div>
</template>
<script>
import chapter from "@/api/edu/chapter";

export default {
  data() {
    return {
      saveBtnDisabled: false,
      courseId: "",
      chapterVideoList: [],
    };
  },
  created() {
    console.log("chapter created.");
    // 从路由跳转路径中获取课程的id值
    if (this.$route.params && this.$route.params.id) {
      this.courseId = this.$route.params.id;
      this.getChapterVideo();
    }
  },
  methods: {
    // 查询课程大纲列表
    getChapterVideo() {
      chapter
        .getAllChapterVideo(this.courseId)
        .then((response) => {
          this.chapterVideoList = response.data.chapterVideoList;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    previous() {
      this.$router.push({ path: "/course/courseinfo/1" });
    },
    next() {
      this.$router.push({ path: "/course/coursepublish/1" });
    },
  },
};
</script>

<style scoped>
  .chapterVideoList {
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
  }
  .chapterVideoList li {
    position: relative;
  }
  .chapterVideoList p {
    float: left;
    font-size: 20px;
    margin: 10px 0;
    padding: 10px;
    height: 70px;
    line-height: 50px;
    width: 100%;
    border: 1px solid #ddd;
  }
  .chapterVideoList .acts {
    float: right;
    font-size: 14px;
  }

  .videoList {
    padding-left: 50px;
  }
  .videoList p {
    float: left;
    font-size: 14px;
    margin: 10px 0;
    padding: 10px;
    height: 50px;
    line-height: 30px;
    width: 100%;
    border: 1px dotted #ddd;
  }
</style>