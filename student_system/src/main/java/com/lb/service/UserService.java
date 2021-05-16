package com.lb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lb.entity.User;
import org.omg.CORBA.INTERNAL;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService extends IService<User> {
    /**
     * 用户登录测试
     * */
    User loginByUsername(String username);

    /**
     * 查询教师名字用于添加课程
     * */
    List<User> queryTeacherName();

    /**
     * 查询教师id用于课程修改
     * */
    int queryIdByTeacherName(User user);

    /**
     * 根据班级名称查询班级人员(用于班级管理)
     * */
    List<User> queryStudentByClassName(String className);


}
