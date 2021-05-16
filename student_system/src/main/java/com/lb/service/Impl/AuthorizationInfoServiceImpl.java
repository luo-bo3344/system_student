package com.lb.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lb.entity.AuthorizationInfoUser;
import com.lb.entity.Course;
import com.lb.entity.Role;
import com.lb.entity.User;
import com.lb.mapper.AuthorizationInfoUserMapper;
import com.lb.mapper.CourseMapper;
import com.lb.service.AuthorizationInfoService;
import com.lb.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author luobo
 * @date 2021-04-14 22:47
 * @description
 */
@Service
public class AuthorizationInfoServiceImpl extends ServiceImpl<AuthorizationInfoUserMapper, AuthorizationInfoUser> implements AuthorizationInfoService {

   @Autowired
   private AuthorizationInfoUserMapper infoUserMapper;
    /**
     * 用于授权
     *
     * @param userName
     */
    @Override
    public AuthorizationInfoUser queryUserForAuthorization(String userName) {
        return infoUserMapper.queryUserForAuthorization(userName);
    }

}
