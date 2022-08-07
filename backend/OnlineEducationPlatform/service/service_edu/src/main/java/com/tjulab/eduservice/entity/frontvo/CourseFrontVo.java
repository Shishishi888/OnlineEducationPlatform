package com.tjulab.eduservice.entity.frontvo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseFrontVo {
    @ApiModelProperty(value="课程名称")
    private String title;

    @ApiModelProperty(value="讲师ID")
    private String teacherId;

    @ApiModelProperty(value="一级课程分类ID")
    private String subjectParentId;

    @ApiModelProperty(value="二级课程分类ID")
    private String subjectId;

    @ApiModelProperty(value="课程销量排序")
    private String salesVolumeSort;

    @ApiModelProperty(value="课程上架时间排序")
    private String gmtCreateSort;

    @ApiModelProperty(value="课程价格排序")
    private String priceSort;
}
