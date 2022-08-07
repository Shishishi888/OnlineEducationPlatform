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
                  <a title="全部" href="#" @click="searchAllCource()" :class="{active:firstIndex==-1}">全部</a>
                </li>
                <li v-for="(firstSubject, index) in firstSubjectList" :key="index" :class="{active:firstIndex==index}">
                  <a :title="firstSubject.title" href="#" @click="searchFirstSubject(firstSubject.id, index)">{{firstSubject.title}}</a>
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
                <li v-for="(secondSubject, index) in secondSubjectList" :key="index" :class="{active:secondIndex==index}">
                  <a :title="secondSubject.title" href="#" @click="searchSecondSubject(secondSubject.id, index)">{{secondSubject.title}}</a>
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
               <li :class="{'current bg-orange':salesVolumeSort!=''}">
                <a title="销量" href="javascript:void(0);" @click="searchCourceBySalesVolumeSort()">销量
                <span :class="{hide:salesVolumeSort==''}">↓</span>
                </a>
              </li>
              <li :class="{'current bg-orange':gmtCreatSort!=''}">
                <a title="最新" href="javascript:void(0);" @click="searchCourceByGmtCreatSort()">最新
                <span :class="{hide:gmtCreatSort==''}">↓</span>
                </a>
              </li>
              <li :class="{'current bg-orange':priceSort!=''}">
                <a title="价格" href="javascript:void(0);" @click="searchCourceByPriceSort()">价格&nbsp;
                  <span :class="{hide:priceSort==''}">↓</span>
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
                    <span class="fr jgTag bg-green" v-if="Number(course.price)===0">
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
            <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
            <a
              :class="{undisable: !courseFrontPageListData.hasPrevious}"
              href="#"
              title="首页"
              @click.prevent="gotoPage(1)">首页</a>

            <a
              :class="{undisable: !courseFrontPageListData.hasPrevious}"
              href="#"
              title="前一页"
              @click.prevent="gotoPage(courseFrontPageListData.current-1)">&lt;</a>

            <a
              v-for="page in courseFrontPageListData.pages"
              :key="page"
              :class="{current: courseFrontPageListData.current == page, undisable: courseFrontPageListData.current == page}"
              :title="'第'+page+'页'"
              href="#"
              @click.prevent="gotoPage(page)">{{ page }}</a>

            <a
              :class="{undisable: !courseFrontPageListData.hasNext}"
              href="#"
              title="后一页"
              @click.prevent="gotoPage(courseFrontPageListData.current+1)">&gt;</a>

            <a
              :class="{undisable: !courseFrontPageListData.hasNext}"
              href="#"
              title="尾页"
              @click.prevent="gotoPage(teacherFrontPageListData.pages)">尾页</a>

            <div class="clear"/>
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
      firstSubjectList: [],         // 一级课程分类列表
      secondSubjectList: [],        // 二级课程分类列表
      courseSearchLimit: {},        // 课程查询条件
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
    // 初始化课程列表
    initCourseFrontPageList() {
      courseApi.getCourseFrontPageList(1, 8, this.courseSearchLimit)
                .then(response => {
                  this.courseFrontPageListData = response.data.data;
                });
    },
    // 初始化课程分类
    initSubject() {
      courseApi.getSubjectList()
                .then(response => {
                  this.firstSubjectList = response.data.data.subjectList;
                });
    },
    // 切换分页
    gotoPage(page) {
      courseApi.getCourseFrontPageList(page, 8, this.courseSearchLimit)
                .then(response => {
                  this.courseFrontPageListData = response.data.data;
                });
    },
    // 查询一级课程分类对应的课程
    searchFirstSubject(subjectParentId, index) {
      // 实现一级课程分类选中效果
      this.firstIndex = index;

      this.secondIndex = -1;
      this.courseSearchLimit.subjectId = "";
      this.secondSubjectList = [];

      // 实现一级课程分类与二级课程分类联动效果
      for(let i = 0; i < this.firstSubjectList.length; ++i) {
        var firstSubject = this.firstSubjectList[i];
        if(subjectParentId == firstSubject.id) {
          this.secondSubjectList = firstSubject.children;
        }
      }

      // 显示一级课程分类对应的课程列表
      this.courseSearchLimit.subjectParentId = subjectParentId;
      this.gotoPage(1);
    },
    // 查询所有的课程
    searchAllCource() {
      this.firstIndex = -1;

      this.secondIndex = -1;
      this.courseSearchLimit = {};
      this.secondSubjectList = [];

      this.initCourseFrontPageList();
    },
    // 查询二级课程分类对应的课程
    searchSecondSubject(subjectId, index) {
      // 实现二级课程分类选中效果
      this.secondIndex = index;
      // 显示二级课程分类对应的课程列表
      this.courseSearchLimit.subjectId = subjectId;
      this.gotoPage(1);
    },
    // 根据课程销量排序查询课程
    searchCourceBySalesVolumeSort() {
      this.salesVolumeSort = "1";
      this.gmtCreatSort = "";
      this.priceSort = "";

      this.courseSearchLimit.salesVolumeSort = this.salesVolumeSort;
      this.courseSearchLimit.gmtCreatSort = this.gmtCreatSort;
      this.courseSearchLimit.priceSort = this.priceSort;

      this.gotoPage(1);
    },
    // 根据课程上架时间排序查询课程
    searchCourceByGmtCreatSort() {
      this.salesVolumeSort = "";
      this.gmtCreatSort = "1";
      this.priceSort = "";

      this.courseSearchLimit.salesVolumeSort = this.salesVolumeSort;
      this.courseSearchLimit.gmtCreatSort = this.gmtCreatSort;
      this.courseSearchLimit.priceSort = this.priceSort;

      this.gotoPage(1);
    },
    // 根据课程价格排序查询课程
    searchCourceByPriceSort() {
      this.salesVolumeSort = "";
      this.gmtCreatSort = "";
      this.priceSort = "1";

      this.courseSearchLimit.salesVolumeSort = this.salesVolumeSort;
      this.courseSearchLimit.gmtCreatSort = this.gmtCreatSort;
      this.courseSearchLimit.priceSort = this.priceSort;

      this.gotoPage(1);
    }
  }
};
</script>

<style scoped>
  .active {
    background: #bdbdbd;
  }
  .hide {
    display: none;
  }
  .show {
    display: block;
  }
</style>