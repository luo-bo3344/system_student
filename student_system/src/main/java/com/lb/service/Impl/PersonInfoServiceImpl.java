package com.lb.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lb.entity.User;
import com.lb.mapper.StudentMapper;
import com.lb.mapper.UserMapper;
import com.lb.service.PersonInfoService;
import com.lb.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author luobo
 * @date 2021-04-04 15:09
 * @description
 */
@Service
public class PersonInfoServiceImpl extends ServiceImpl<UserMapper, User> implements PersonInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonInfoServiceImpl.class);
    @Autowired
    private UserMapper userMapper;


    /**
     * 修改个人信息操作
     *
     * @param username
     */
    @Override
    public User editPersonInformation(Object username) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<User>();
        queryWrapper.eq("username",username);
        queryWrapper.eq("status",1);
        User user = userMapper.selectOne(queryWrapper);
        LOGGER.info("修改个人信息service查询结果{}",user);
        return user;
    }

    /**
     * 修改密码
     *
     * @param user
     */
    @Override
    public int updatePassword(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
        int updatePassword = userMapper.update(user, queryWrapper);
        return updatePassword;
    }

}
