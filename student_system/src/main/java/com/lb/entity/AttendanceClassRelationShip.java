package com.lb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author luobo
 * @date 2021-04-24 18:19
 * @description
 */
@ApiModel("考勤班级关系表")
@Data
@TableName(value = "t_attendance_class")
public class AttendanceClassRelationShip {
    @ApiModelProperty("班级考勤中间表id")
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    @ApiModelProperty("用户表id")
    private Integer classId;
    @ApiModelProperty("角色表id")
    private Integer attendanceId;
}
