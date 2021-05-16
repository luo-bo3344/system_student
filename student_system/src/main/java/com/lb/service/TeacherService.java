package com.lb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.lb.entity.Teacher;
import com.lb.entity.User;

import java.util.List;

/**
 * TeacherService接口
 * */
public interface TeacherService extends IService<User> {


    /**
     * 分页查询教师信息
     * */
    PageInfo<Teacher> queryTeacherByPage(int pageNum,int pageSize);

    /**
     * 分页查询教师信息
     * */
    List<Teacher> queryTeacherAll();

    /**
     * 删除教师信息(逻辑删除)
     *
     * @param teacher*/
    int deleteTeacherById(User teacher);

    /**
     * 修改教师信息
     * */
    int updateTeacherById(User teacher);


    /**
     * 添加教师信息
     * */
    //int insertTeacher(Teacher teacher);
    int insertTeacher(User teacher);


    /**
     * 查询单个教师信息用于页面修改教师信息
     * */
    Teacher queryTeacherById(Integer id);

    /**
     * 在教师页面搜索
     * */
    List<User> queryTeacherInfo(Integer pageNum, Integer pageSize,String input);

    /**
     * 模糊查询教师信息
     * */
    List<User> queryTeacherByCondition(String str);

    /**
     * 查询所有的教师总数
     * */
    Integer queryCountAllTeacher();
}
