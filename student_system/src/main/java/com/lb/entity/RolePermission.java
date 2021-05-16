package com.lb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("角色权限中间表")
public class RolePermission implements Serializable {

    @ApiModelProperty("用户角色中间表id")
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    @ApiModelProperty("角色表id")
    private Integer roleId;

    @ApiModelProperty("权限表id")
    private Integer permissionId;

}
