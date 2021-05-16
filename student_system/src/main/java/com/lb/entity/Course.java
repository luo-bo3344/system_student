package com.lb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author luobo
 * @date 2021-04-05 21:22
 * @description
 */
@Data
public class Course implements Serializable {
    @ApiModelProperty("课程id")
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    @ApiModelProperty("课程名称")
    private String courseName;

    @ApiModelProperty("教师id")
    private String teacherId;

    @ApiModelProperty("每周上课时间")
    private String courseDate;

    @ApiModelProperty("班级信息修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("是否删除")
    private  Boolean status;

    @ApiModelProperty("教师")
    private User user;
}
