package com.tjulab.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tjulab.commonutils.R.R;
import com.tjulab.commonutils.jwt.JwtUtils;
import com.tjulab.commonvo.ordervo.CourseFrontIntroVoForOrder;
import com.tjulab.eduservice.client.OrderClient;
import com.tjulab.eduservice.entity.EduCourse;
import com.tjulab.eduservice.entity.frontvo.CourseFrontIntroVo;
import com.tjulab.eduservice.entity.frontvo.CourseFrontVo;
import com.tjulab.eduservice.entity.vo.chapter.ChapterVo;
import com.tjulab.eduservice.service.EduChapterService;
import com.tjulab.eduservice.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private OrderClient orderClient;

    /**
     * 查询课程（条件查询+分页查询）
     * @param page
     * @param limit
     * @param courseFrontVo
     * @return
     */
    @PostMapping("getCourseFrontPageList/{page}/{limit}")
    public R getCourseFrontPageList(@PathVariable long page,
                                @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> coursePage = new Page<>(page, limit);
        Map<String, Object> coursePageMap = eduCourseService.getCourseFrontPageList(coursePage, courseFrontVo);
        return R.ok().data(coursePageMap);
    }

    /**
     * 查询课程详情页信息
     * @param courseId
     * @return
     */
    @GetMapping("getCourseFrontInfo/{courseId}")
    public R getCourseFrontInfo(@PathVariable String courseId, HttpServletRequest request) {
        // 根据课程id查询课程的基本信息
        CourseFrontIntroVo courseFrontIntroVo = eduCourseService.getCourseBaseInfo(courseId);

        // 根据课程id查询课程的章节和小节
        List<ChapterVo> chapterVideoList = eduChapterService.getChapterVideoListByCourseId(courseId);

        // 根据课程id和用户id查询课程订单支付状态
        // TODO 处理用户未登录
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        boolean isPay = orderClient.isPay(courseId, userId);

        return R.ok().data("courseFrontIntroVo", courseFrontIntroVo).data("chapterVideoList", chapterVideoList).data("isPay", isPay);
    }

    /**
     * 查询课程信息（根据课程ID查询）
     * @param courseId
     * @return
     */
    @PostMapping("getCourseInfoForOrder/{courseId}")
    public CourseFrontIntroVoForOrder getCourseInfoForOrder(@PathVariable String courseId) {
        CourseFrontIntroVoForOrder courseFrontIntroVoForOrder = new CourseFrontIntroVoForOrder();
        CourseFrontIntroVo courseFrontIntroVo = eduCourseService.getCourseBaseInfo(courseId);
        BeanUtils.copyProperties(courseFrontIntroVo, courseFrontIntroVoForOrder);
        return courseFrontIntroVoForOrder;
    }

}
