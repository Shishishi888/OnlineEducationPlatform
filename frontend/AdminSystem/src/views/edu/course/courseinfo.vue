<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <el-form label-width="120px">

        <el-form-item label="课程标题">
            <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
        </el-form-item>

        <!-- 所属分类 TODO -->
        <!-- 一级课程分类 -->
        <el-form-item label="课程分类">
            <el-select
                v-model="courseInfo.subjectParentId"
                placeholder="一级课程分类" @change="getSecondSubject">

                <el-option
                    v-for="subject in firstSubjectList"
                    :key="subject.id" 
                    :label="subject.title"
                    :value="subject.id"/>

            </el-select>

            <!-- 二级课程分类 -->
            <el-select
                v-model="courseInfo.subjectId"
                placeholder="二级课程分类">
                
                <el-option
                    v-for="subject in secondSubjectList"
                    :key="subject.id" 
                    :label="subject.title"
                    :value="subject.id"/>
                    
            </el-select>
        </el-form-item>

        <!-- 课程讲师 TODO -->
        <!-- 课程讲师 -->
        <el-form-item label="课程讲师">
        <el-select
            v-model="courseInfo.teacherId"
            placeholder="请选择">

            <el-option
                v-for="teacher in teacherList"
                :key="teacher.id" 
                :label="teacher.name"
                :value="teacher.id"/>

        </el-select>
        </el-form-item>

        <el-form-item label="总课时">
            <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
        </el-form-item>

        <!-- 课程简介 TODO -->
        <!-- 课程简介-->
        <el-form-item label="课程简介">
            <tinymce :height="300" v-model="courseInfo.description"/>
        </el-form-item>

        <!-- 课程封面 TODO -->
        <!-- 课程封面 -->
        <el-form-item label="课程封面">

            <el-upload
                :show-file-list="false"
                :on-success="handleCoverSuccess"
                :before-upload="beforeCoverUpload"
                :action="BASE_API+'/ossservice/ossfile'"
                class="avatar-uploader">
                <img :src="courseInfo.cover">
            </el-upload>

        </el-form-item>

        <el-form-item label="课程价格">
            <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
        </el-form-item>

        <el-form-item>
            <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
        </el-form-item>
        </el-form>
  </div>
</template>
<script>
import course from '@/api/edu/course';
import subject from '@/api/edu/subject';
import Tinymce from '@/components/Tinymce';  // 引入组件

export default {
    // 声明组件
    components: { Tinymce },
    data() {
        return {
            saveBtnDisabled: false,
            courseInfo: {
                title: '',
                subjectParentId:'',  // 一级课程分类ID
                subjectId: '',  // 二级课程分类ID
                teacherId: '',
                lessonNum: 0,
                description: '',
                cover: '/static/images/default_course_cover.jpg',
                price: 0
            },
            courseId: '',
            BASE_API: process.env.BASE_API,  // 接口API地址
            teacherList: [],
            firstSubjectList: [],  // 一级课程分类
            secondSubjectList: [],  // 二级课程分类
        }
    },
    created() {
        console.log('info created.');
        this.init();

    },
    watch: {
        $route(to, from) {
            this.init();
        }
    },
    methods: {
        init() {
            // 判断路由跳转路径中是否有课程的id值
            if(this.$route.params && this.$route.params.id) {  // 修改课程信息操作
                this.courseId = this.$route.params.id;
                // 数据回显
                this.getCourseInfo();
                this.getAllTeacher();
            }
            else {  // 添加课程信息操作
                // 清空表单
                // this.courseInfo.title = '';
                // this.courseInfo.subjectParentId = '';
                // this.courseInfo.subjectId = '';
                // this.courseInfo.teacherId = '';
                // this.courseInfo.lessonNum = 0;
                // this.courseInfo.description = '';
                // this.courseInfo.cover = '/static/images/default_course_cover.jpg';
                // this.courseInfo.price = 0;
                this.courseInfo = {};
                this.courseInfo.lessonNum = 0;
                this.courseInfo.cover = '/static/images/default_course_cover.jpg';
                this.courseInfo.price = 0;

                this.getAllTeacher();
                this.getFirstSubject();
            }
        },

        // 查询课程信息（根据课程id查询）
        getCourseInfo() {
            course.getCourseInfoByCourseId(this.courseId) 
                    .then(response => {
                        this.courseInfo = response.data.courseInfoVo;
                        // 解决修改课程信息（数据回显）时，无法显示二级课程分类的问题
                        subject.getSubjectList() 
                                .then(response => {
                                    this.firstSubjectList = response.data.subjectList;  // 获取所有的一级课程分类
                                    for(var i = 0; i < this.firstSubjectList.length; ++i) {  // 遍历所有的一级课程分类
                                        var firstSubject = this.firstSubjectList[i];
                                        if(firstSubject.id == this.courseInfo.subjectParentId) {  // 找到当前的一级课程分类
                                            this.secondSubjectList = firstSubject.children;  // 获取当前的一级课程分类对应的二级课程分类
                                        }
                                    }
                                });
                    });
        },

        // 上传课程封面之前调用
        beforeCoverUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isLt2M = file.size / 1024 / 1024 < 2;
            if(!isJPG) {
                this.$message.error('上传的图片只能是JPG格式！');
            }
            if(!isLt2M) {
                this.$message.error('上传的图片大小不能超过2MB！');
            }
            return isJPG && isLt2M;
        },

        // 上传课程封面成功之后调用
        handleCoverSuccess(res, file) {
            this.courseInfo.cover = res.data.url;
        },

        // 查询某一级课程分类对应的二级课程分类
        getSecondSubject(value) {
            console.log(value);  // value值就是点击的一级课程分类的id
            for(var i = 0; i < this.firstSubjectList.length; ++i) {
                var firstSubject = this.firstSubjectList[i];
                if(firstSubject.id === value) {
                    this.secondSubjectList = firstSubject.children;
                    this.courseInfo.subjectId = '';  // 清空二级课程分类的输入框 
                }
            }
        },
        
        // 查询所有的一级课程分类
        getFirstSubject() {
            subject.getSubjectList()
                    .then(response => {
                        this.firstSubjectList = response.data.subjectList;
                    });
        },

        // 查询所有的讲师
        getAllTeacher() {
            course.getAllTeacher()
                    .then(response => {
                        this.teacherList = response.data.eduTeacherList;
                    });
        },

        // 添加课程信息
        addCourseInfo() {
             course.addCourseInfo(this.courseInfo)
                    .then(response => {
                        this.$message({
                            type: 'success',
                            message: '添加课程信息成功！'
                        });
                        this.$router.push({path:'/course/coursechapter/' + response.data.courseId});
                    });
        },

        // 修改课程信息
        updateCourseInfo() {
            course.updateCourseInfo(this.courseInfo)
                    .then(response => {
                        this.$message({
                            type: 'success',
                            message: '修改课程信息成功！'
                        })
                        this.$router.push({path:'/course/coursechapter/' + this.courseId});
                    })
        },

        // 添加或修改课程信息
        saveOrUpdate() {
           if(!this.courseInfo.id) {  // 添加课程信息操作
                this.addCourseInfo();
           }
           else {  // 修改课程信息操作
                this.updateCourseInfo();
           }
        }
    }
}
</script>

<style scoped>
    .tinymce-container {
        line-height: 29px;
    }
</style>