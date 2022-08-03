<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部课程</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-999 fsize14">课程类别</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li>
                  <a title="全部" href="#">全部</a>
                </li>
                <li v-for="(firstSubject, index) in subjectNestedList" :key="index">
                  <a title="数据库" href="#">{{firstSubject.title}}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <dl>
            <dt>
              <span class="c-999 fsize14"></span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li v-for="(secondSubject, index) in subSubjectList" :key="index">
                  <a title="职称英语" href="#">{{secondSubject.title}}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <div class="clear"></div>
        </section>
        <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <section class="fl">
            <ol class="js-tap clearfix">
              <li>
                <a title="关注度" href="#">关注度</a>
              </li>
              <li>
                <a title="最新" href="#">最新</a>
              </li>
              <li class="current bg-orange">
                <a title="价格" href="#">价格&nbsp;
                  <span>↓</span>
                </a>
              </li>
            </ol>
          </section>
        </div>
        <div class="mt40">
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="courseFrontPageListData.total==0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article class="comm-course-list" v-if="courseFrontPageListData.total>0">
            <ul class="of" id="bna">
              <li v-for="course in courseFrontPageListData.items" :key="course.id">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img :src="course.cover" class="img-responsive" :alt="course.title">
                    <div class="cc-mask">
                      <a href="/course/1" title="开始学习" class="comm-btn c-btn-1">开始学习</a>
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a href="/course/1" :title="course.title" class="course-title fsize18 c-333">{{course.title}}</a>
                  </h3>
                  <section class="mt10 hLh20 of">
                    <span class="fr jgTag bg-green">
                      <i class="c-fff fsize12 f-fA">免费</i>
                    </span>
                    <span class="fl jgAttr c-ccc f-fA">
                      <i class="c-999 f-fA">9634人学习</i>
                      |
                      <i class="c-999 f-fA">9634评论</i>
                    </span>
                  </section>
                </div>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
        </div>
        <!-- 公共分页 开始 -->
        <div>
          <div class="paging">
            <a class="undisable" title>首</a>
            <a id="backpage" class="undisable" href="#" title>&lt;</a>
            <a href="#" title class="current undisable">1</a>
            <a href="#" title>2</a>
            <a id="nextpage" href="#" title>&gt;</a>
            <a href="#" title>末</a>
            <div class="clear"></div>
          </div>
        </div>
        <!-- 公共分页 结束 -->
      </section>
    </section>
    <!-- /课程列表 结束 -->
  </div>
</template>
<script>
import courseApi from '@/api/course'
export default {
  data() {
    return {
      page: 1,                      // 当前页
      courseFrontPageListData: {},  // 课程列表数据
      subjectNestedList: [],        // 一级课程分类列表
      subSubjectList: [],           // 二级课程分类列表
      courseFrontVo: {},            // 查询表单对象
      firstIndex: -1,               // 一级课程分类选中效果
      secondIndex: -1,              // 二级课程分类选中效果
      salesVolumeSort: "",
      gmtCreatSort: "",
      priceSort:""
    }
  },
  created() {
    this.initCourseFrontPageList();
    this.initSubject();
  },
  methods: {
    // 查询第一页课程列表
    initCourseFrontPageList() {
      courseApi.getCourseFrontPageList(1, 8, this.courseFrontVo)
                .then(response => {
                  this.courseFrontPageListData = response.data.data;
                });
    },
    // 查询所有的课程分类
    initSubject() {
      courseApi.getSubjectList()
                .then(response => {
                  this.subjectNestedList = response.data.data.subjectList;
                });
    },
    // 切换分页
    gotoPage(page) {
      teacherApi.getCourseFrontPageList(page, 8)
                .then(response => {
                  this.courseFrontPageListData = response.data.data;
                });
    }
  }
};
</script>