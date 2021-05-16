package com.lb.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lb.entity.AuthorizationInfoUser;
import com.lb.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * 权限查询
 * */
@Repository
public interface AuthorizationInfoUserMapper extends BaseMapper<AuthorizationInfoUser> {
    /**
     * 用于授权
     * */
    AuthorizationInfoUser queryUserForAuthorization(@Param("username") String name);
}
