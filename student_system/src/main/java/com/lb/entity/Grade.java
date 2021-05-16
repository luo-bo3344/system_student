package com.lb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author luobo
 * @date 2021-04-26 16:39
 * @description
 */
@Data
@ApiModel("学生成绩实体")
@TableName(value = "grade")
public class Grade implements Serializable {
    @ApiModelProperty("成绩id")
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    @ApiModelProperty("学生id")
    private Integer studentId;

    @ApiModelProperty("课程id")
    private Integer courseId;

    @ApiModelProperty("班级id")
    private Integer classId;

    @ApiModelProperty("学生成绩")
    private Integer grade;

    @ApiModelProperty("信息修改时间")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("成绩状态状态")
    private Integer status;

   /* @ApiModelProperty("获取学生姓名")
    private User user;

    @ApiModelProperty("获取课程id")
    private Course course;*/

}
