package com.lb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("用户角色中间表")
public class UserRole implements Serializable {

    @ApiModelProperty("用户角色中间表id")
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    @ApiModelProperty("用户表id")
    private Integer userId;
    @ApiModelProperty("角色表id")
    private Integer roleId;

}
