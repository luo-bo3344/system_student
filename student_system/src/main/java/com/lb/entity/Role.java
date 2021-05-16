package com.lb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("角色表")
public class Role implements Serializable {
    @ApiModelProperty("角色id")
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    @ApiModelProperty("角色名")
    private String roleName;
    @ApiModelProperty("角色描述")
    private String description;
}
