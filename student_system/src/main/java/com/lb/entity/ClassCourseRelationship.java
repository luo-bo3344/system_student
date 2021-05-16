package com.lb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author luobo
 * @date 2021-04-10 21:18
 * @description
 */
@ApiModel("学生班级关系实体")
@Data
@TableName(value = "t_class_course")
public class ClassCourseRelationship {
    @ApiModelProperty("用户角色中间表id")
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    @ApiModelProperty("用户表id")
    private Integer classId;
    @ApiModelProperty("角色表id")
    private Integer courseId;
}
