package com.lb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lb.entity.User;

import java.util.List;

import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

public interface StudentService extends IService<User> {

    /**
     * 查询学生信息
     * */
    List<User> queryStudentAll();
    /**
     * 添加学生信息
     * */
    int insertStudent(User student,String classNmae);

    /**
     * 删除学生信息
     * */
    int deleteStudentById(User student);


    /**
     * 根据id查询学生信息,用于修改操作
     * */
    User queryStudentById(User student);
    /**
     * 保存信息到数据库
     * */
    int updateStudentInformation(User student);

    /**
     * 查询所有的学生总数
     * */
    Integer queryCountAllStudent();

    /**
     * 根据名字查询学生ID,用于学生的考勤
     * */
    User queryStudentIdByStudentName(String stuName);


    /**
     * 根据ID查询学生姓名用于成绩页面名字的展示
     * */
    User queryStudentNameById(Integer id);

    /**
     * 查询所有男同学数量,用于首页的饼图
     * */
    Integer queryBoyStudentBySex();
    /**
     * 查询所有女同学数量,用于首页的饼图
     * */
    Integer queryGirlStudentBySex();

    /**
     * 模糊查询学学生信息
     * */
    List<User> queryStudentByCondition(String userName);
}
