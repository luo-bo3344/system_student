package com.lb.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lb.entity.User;
import com.lb.mapper.UserMapper;
import com.lb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public User loginByUsername(String username) {
        System.out.println("结果"+userMapper.loginByUsername(username));
        return userMapper.loginByUsername(username);
    }

    /**
     * 查询教师名字用于添加课程
     */
    @Override
    public List<User> queryTeacherName() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1);
        queryWrapper.eq("relationship",2);
        List<User> users = userMapper.selectList(queryWrapper);
        return users;
    }

    /**
     * 查询教师id用于课程修改
     *
     * @param user
     */
    @Override
    public int queryIdByTeacherName(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("relationship",2);
        queryWrapper.eq("status",1);
        queryWrapper.eq("name",user.getName());
        User queryTeacherId = userMapper.selectOne(queryWrapper);
        return queryTeacherId.getId();
    }

    /**
     * 根据班级名称查询班级人员(用于班级管理)
     *
     * @param className
     */
    @Override
    public List<User> queryStudentByClassName(String className) {
        return userMapper.queryStudentByClassName(className);
    }
}
