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
 * @date 2021-04-22 14:45
 * @description
 */

@Data
@ApiModel("学生考勤实体类")
@TableName(value = "attendance")
public class Attendance implements Serializable {

    @ApiModelProperty("学生考勤id")
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    @ApiModelProperty("学生id,用于获取学姓名")
    private Integer studentId;

    @ApiModelProperty("学生id,用于获取学姓名")
    private String description;

    @ApiModelProperty("用户出生日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date attendanceDate;

    @ApiModelProperty("考勤人员状态是否删除（逻辑删除）")
    private Boolean status;

    @ApiModelProperty("用户（学生）")
    private User user;

    @ApiModelProperty("学生班级")
    private StudentClass studentClass;

}
