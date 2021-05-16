package com.lb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lb.entity.UserRole;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 新增用户角色关系
     * */
    int insertUserRole(UserRole userRole);

}
