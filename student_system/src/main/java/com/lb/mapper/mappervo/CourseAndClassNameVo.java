package com.lb.mapper.mappervo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author luobo
 * @date 2021-04-27 15:26
 * @description 通过课程名称和班级名称查询到对应的id,用于学生分数的查询
 */
@Data
@ApiModel("接受前端的课程名:班级名并获取他们的ID")
public class CourseAndClassNameVo implements Serializable {
    @ApiModelProperty("课程id")
    private Integer courseId;

    @ApiModelProperty("班级id")
    private Integer classId;
}
