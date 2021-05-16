package com.lb.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lb.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author luobo
 * @date 2021-04-20 9:53
 * @description
 */
@ApiModel("添加学生课程转换类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseVo implements Serializable {
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

    @ApiModelProperty("班级名称")
    private String className;

}
