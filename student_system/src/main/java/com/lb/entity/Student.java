package com.lb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("学生实体")
@Data
public class Student {
    @ApiModelProperty("用户id")
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("用户姓名")
    private String name;

    @ApiModelProperty("用户年龄")
    private Integer age;

    @ApiModelProperty("用户性别")
    private String sex;

    @ApiModelProperty("用户出生日期")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @ApiModelProperty("用户家庭住址")
    private String address;

    @ApiModelProperty("用户电话")
    private String phone;

    @ApiModelProperty("信息创建时间")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("信息修改时间")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    //教师是否在职（逻辑删除 1表示未删除，0表示删除）
    @ApiModelProperty("用户状态")
    private Integer status;

    @ApiModelProperty("用户的关系连接字段")
    private Integer relationship;


}
