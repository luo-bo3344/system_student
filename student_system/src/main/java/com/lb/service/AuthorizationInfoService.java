package com.lb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lb.entity.AuthorizationInfoUser;
import com.lb.entity.Course;
import com.lb.entity.Role;
import com.lb.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author luobo
 * @date 2021-04-14 22:47
 * @description
 */
public interface AuthorizationInfoService extends IService<AuthorizationInfoUser> {
    /**
     * 用于授权
     * */
    AuthorizationInfoUser queryUserForAuthorization(String userName);

}
