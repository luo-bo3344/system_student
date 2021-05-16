package com.lb.mapper.mappervo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author luobo
 * @date 2021-04-26 22:19
 * @description
 */
@Data
@ApiModel("用于学生成绩查询使用")
public class CourseClassVo {

    @ApiModelProperty("课程名称")
    private String courseName;

    @ApiModelProperty("班级名称")
    private String className;
}
