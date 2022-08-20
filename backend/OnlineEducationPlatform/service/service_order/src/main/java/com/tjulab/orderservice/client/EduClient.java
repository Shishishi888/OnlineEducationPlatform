package com.tjulab.orderservice.client;

import com.tjulab.commonvo.ordervo.CourseFrontIntroVoForOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-edu")
public interface EduClient {
    /**
     * 查询课程信息（根据课程ID查询）
     * @param courseId
     * @return
     */
    @PostMapping("/eduservice/coursefront/getCourseInfoForOrder/{courseId}")
    public CourseFrontIntroVoForOrder getCourseInfoForOrder(@PathVariable("courseId") String courseId);
}
