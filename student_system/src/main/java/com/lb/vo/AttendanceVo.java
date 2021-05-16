package com.lb.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author luobo
 * @date 2021-04-24 21:59
 * @description
 */
@Data
@ApiModel("考勤信息转换类")
public class AttendanceVo implements Serializable {
    @ApiModelProperty("学生姓名")
    private String name;

    @ApiModelProperty("迟到原因")
    private String description;

    @ApiModelProperty("迟到日期")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String attendanceDate;
}
