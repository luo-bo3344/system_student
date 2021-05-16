package com.lb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author luobo
 * @date 2021-04-04 18:43
 * @description
 */
@Data
@ApiModel("学生班级")
public class StudentClass implements Serializable {
    @ApiModelProperty("班级id")
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    @ApiModelProperty("班级名称")
    private String className;

    @ApiModelProperty("班级口号/简介")
    private String description;


    @ApiModelProperty("班级信息修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("是否删除")
    private  Boolean status;

   /* @ApiModelProperty("课程")
    private  Course course;*/

   /* @ApiModelProperty("班级对应的学生，一对多")
    private List<User> student;*/

}
