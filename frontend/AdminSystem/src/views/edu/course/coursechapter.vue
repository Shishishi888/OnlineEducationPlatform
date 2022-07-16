<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <el-button type="text" @click="openAddChapterDialog()">添加章节</el-button>

     <!-- 章节 -->
    <ul class="chapterVideoList">
        <li
            v-for="chapter in chapterVideoList"
            :key="chapter.id">
            <p>
                {{ chapter.title }}
                <span class="acts">
                  <el-button style="" type="text" @click="openAddVideoDialog(chapter.id)">添加小节</el-button>
                  <el-button style="" type="text" @click="openEditChatperDialog(chapter.id)">编辑</el-button>
                  <el-button type="text" @click="removeChapter(chapter.id)">删除</el-button>
                </span>
            </p>
            <!-- 视频 -->
            <ul class="chanpterList videoList">
                <li
                    v-for="video in chapter.children"
                    :key="video.id"
                    >
                    <p>
                      {{ video.title }}
                      <span class="acts">
                        <el-button style="" type="text">编辑</el-button>
                        <el-button type="text" @click="removeVideo(video.id)">删除</el-button>
                      </span>
                    </p>
                </li>
            </ul>
        </li>
    </ul>

    <div>
        <el-button @click="previous">上一步</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
    </div>

     <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
        <el-form :model="chapter" label-width="120px">
            <el-form-item label="章节标题">
                <el-input v-model="chapter.title"/>
            </el-form-item>
            <el-form-item label="章节排序">
                <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="addOrUpdateChapter">确 定</el-button>
        </div>
    </el-dialog>

    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title"/>
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.free">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传视频">
          <el-upload
              :on-success="handleVodUploadSuccess"
              :on-remove="handleVodRemove"
              :before-remove="beforeVodRemove"
              :on-exceed="handleUploadExceed"
              :file-list="fileList"
              :action="BASE_API+'/vodservice/video/uploadVideoToAliyun'"
              :limit="1"
              class="upload-demo">
          <el-button size="small" type="primary">上传视频</el-button>
          <el-tooltip placement="right-end">
              <div slot="content">最大支持1G，<br>
                  支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                  GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                  MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                  SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
              <i class="el-icon-question"/>
          </el-tooltip>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button :disabled="saveVideoBtnDisabled" type="primary" @click="addOrUpdateVideo">确 定</el-button>
      </div>
    </el-dialog>
  </div>

</template>
<script>
import chapter from '@/api/edu/chapter';
import video from '@/api/edu/video';

export default {
    data() {
        return {
            saveBtnDisabled: false,
            courseId: '',
            chapterVideoList: [],
            chapter: {  // 封装章节数据
              title: '',
              sort: 0
            },
            video: {  // 封装小节数据
              title: '',
              sort: 0,
              free: 0,
              videoSourceId: '',  // 视频id
              videoOriginalName: '',  // 视频文件名称
            },
            saveVideoBtnDisabled: false,
            dialogChapterFormVisible: false,  // 是否显示章节弹框
            dialogVideoFormVisible: false,     // 是否显示小节弹框

            fileList: [],  // 上传文件列表
            BASE_API: process.env.BASE_API  // 接口API地址
        }
    },
    created() {
        console.log('chapter created.');
        // 从路由跳转路径中获取课程的id值
        if(this.$route.params && this.$route.params.id) {
          this.courseId = this.$route.params.id;
          this.getChapterVideo();
        }
    },
    methods: {
      // 上传视频点击“确定”按钮调用的方法
      handleVodRemove() {
        // 删除视频
      },

      // 上传视频点击“x”调用的方法
      beforeVodRemove(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }？`);
      },

      // 上传视频成功之后调用的方法
      handleVodUploadSuccess(response, file, fileList) {
        this.video.videoSourceId = response.data.videoId;
        this.video.videoOriginalName = file.name;
      },

      // 上传视频之前调用的方法
      handUploadExceed() {
        this.$message.warning("如果想要重新上传视频，请先删除已经上传的视频");
      },

      // *** *** 操作课程小节 *** ***
      // 删除课程小节
      removeVideo(videoId) {
         this.$confirm('此操作将删除小节，是否继续？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'    
          }).then(() => {  // 点击“确认”执行
            video.deleteVideo(videoId)
                    .then(response => {
                        this.$message({
                          type: 'success',
                          message: '删除小节成功！'
                        });
                        // 回到课程章节列表页面
                        this.getChapterVideo();
                      });
          }).catch();  // 点击“取消”执行
      },

      // 修改小节 TODO

      // 打开添加课程小节弹框
      openAddVideoDialog(chapterId) {
        // 显示弹框
        this.dialogVideoFormVisible = true;
        // 设置课程ID
        this.video.courseId = this.courseId;
        // 设置章节ID
        this.video.chapterId = chapterId;

        // 清空添加小节弹框
        this.video.title = ''; 
        this.video.sort = 0;
        this.video.free = 0;
      },
      
      // 添加课程小节
      addVideo() {
        video.addVideo(this.video)
              .then(response => {
                // 关闭添加课程小节弹框
                this.dialogVideoFormVisible = false;
                // 提示信息
                this.$message({
                  type: 'success',
                  message: '添加小节成功！'
                });
              });
              // 回到课程章节列表页面
              this.getChapterVideo();
      },

      // 添加或修改课程小节
      addOrUpdateVideo() {
        // 添加课程小节
        this.addVideo();  

        // 修改课程小节
        // TODO
      },

      // *** *** 操作课程章节 *** ***
      // 删除课程章节
      removeChapter(chapterId) {
        this.$confirm('此操作将删除章节，是否继续？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'    
        }).then(() => {  // 点击“确认”执行
          chapter.deleteChapterById(chapterId)
                  .then(response => {
                      this.$message({
                        type: 'success',
                        message: '删除章节成功！'
                      });
                      // 回到课程章节列表页面
                      this.getChapterVideo();
                    });
        }).catch();  // 点击“取消”执行
      },

      // 打开修改课程章节弹框
      openEditChatperDialog(chapterId) {
        // 显示弹框
        this.dialogChapterFormVisible = true;
        // 数据回显
        chapter.getChapterInfo(chapterId)
                .then(response => {
                  this.chapter = response.data.eduChapter;
                });
      },

      // 打开添加课程章节弹框
      openAddChapterDialog() {
        // 显示弹框
        this.dialogChapterFormVisible = true;
        // 清空添加课程章节弹框
        this.chapter.title = '';
        this.chapter.sort = 0;
      },

      // 添加课程章节
      addChapter() {
        this.chapter.courseId = this.courseId;
        chapter.addChapter(this.chapter)
                .then(response => {
                  // 关闭弹框
                  this.dialogChapterFormVisible = false;

                  // 提示信息
                  this.$message({
                    type: "success",
                    message: "添加章节成功!",
                  });

                  // 刷新页面
                  this.getChapterVideo();
                });
      },

      // 修改课程章节信息
      updateChapterInfo() {
        chapter.updateChapterInfo(this.chapter)
                .then(response => {
                  // 关闭弹框
                  this.dialogChapterFormVisible = false;

                  // 提示信息
                  this.$message({
                    type: "success",
                    message: "修改章节信息成功!",
                  });

                  // 刷新页面
                  this.getChapterVideo();
                })
      },

      // 添加或修改课程章节
      addOrUpdateChapter() {
        if(this.chapter.id) {  // 修改课程章节信息
          this.updateChapterInfo();
        }
        else {
          this.addChapter();  // 添加章节
        }
      },

      // 查询课程大纲列表
      getChapterVideo() {
        chapter.getAllChapterVideo(this.courseId)
                .then(response => {
                  this.chapterVideoList = response.data.chapterVideoList;
                })
                .catch((error) => {
                  console.log(error);
                });
      },
      previous() {
          this.$router.push({path:'/course/courseinfo/'+this.courseId}); 
      },
      next() {
          this.$router.push({path:'/course/coursepublish/'+this.courseId});
      }
    }
}
</script>
<style scoped>
  .chapterVideoList{
      position: relative;
      list-style: none;
      margin: 0;
      padding: 0;
  }
  .chapterVideoList li{
    position: relative;
  }
  .chapterVideoList p{
    float: left;
    font-size: 20px;
    margin: 10px 0;
    padding: 10px;
    height: 70px;
    line-height: 50px;
    width: 100%;
    border: 1px solid #DDD;
  }
  .chapterVideoList .acts {
    float: right;
    font-size: 14px;
  }

  .videoList{
    padding-left: 50px;
  }
  .videoList p{
    float: left;
    font-size: 14px;
    margin: 10px 0;
    padding: 10px;
    height: 50px;
    line-height: 30px;
    width: 100%;
    border: 1px dotted #DDD;
  }
</style>