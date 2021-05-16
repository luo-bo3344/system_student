package com.lb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lb.entity.StudentClass;
import com.lb.entity.Teacher;
import com.lb.entity.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 学生信息管理
 * */
@Repository
@Mapper
public interface StudentMapper extends BaseMapper<User> {
    /**
     * 查询学生信息（使用中）
     * */
    List<User> queryStudentAll();


    /**
     * 添加学生信息（未用此方法）
     * */
    int insertStudent(User student);

    /**
     * 查询所有的学生总数
     * */
    Integer queryCountAllStudent();

    /**
     * 根据ID查询学生姓名用于成绩页面名字的展示
     * */
    User queryStudentNameById(@Param("id") Integer id);

    /**
     * 模糊查询学生信息
     * */
    List<User> queryStudentByCondition(@Param("str") String str);
}
