package com.lb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lb.entity.Role;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 查询角色信息
     * */
    Role queryRoleByName(@Param("roleName")String roleName);

    /**
     * 通过id查询角色信息
     * */
    Role queryRoleById(@Param("id") Integer roleId);
}
