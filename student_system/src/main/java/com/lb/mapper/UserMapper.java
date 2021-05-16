package com.lb.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lb.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
     * 登录查询
     * */
    User loginByUsername(@Param("username") String userName);
    /**
    * 查询用户信息
    * */
    User login(@Param("name") String name,@Param("password") String password);

    /**
     * 增加用户信息
     * */
    int insertUser(User user);


    /**
     * 根据班级名称查询班级人员(用于班级管理)
     * */
    List<User> queryStudentByClassName(String className);


}
