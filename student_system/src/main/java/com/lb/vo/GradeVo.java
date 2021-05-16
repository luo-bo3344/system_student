package com.lb.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author luobo
 * @date 2021-04-27 17:45
 * @description 学生成绩
 */
@Data
@ApiModel("成绩前端也面实体")
public class GradeVo {
    @ApiModelProperty("成绩id")
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    @ApiModelProperty("学生名字")
    private String studentName;

    @ApiModelProperty("课程名字")
    private String courseName;

    @ApiModelProperty("班级名字")
    private String className;

    @ApiModelProperty("学生成绩")
    private Integer grade;

    @ApiModelProperty("信息修改时间")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("成绩状态状态")
    private Integer status;
}
