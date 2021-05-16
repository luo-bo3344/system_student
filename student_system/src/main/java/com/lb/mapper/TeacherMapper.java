package com.lb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lb.entity.Teacher;
import com.lb.entity.User;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import sun.awt.SunHints;

import java.util.List;

/**
 * 教师信息
 * */
@Repository
@Mapper
public interface TeacherMapper extends BaseMapper<User> {
    /**
     * 查询教师信息
     * */
    List<Teacher> queryTeacherAll();

  /*  @Select("SELECT * FROM teacher")
    List<Teacher> queryTeacherAll1();*/

    /**
     * 通过id查询单个教师信息用于修改
     * */
    Teacher queryTeacherById(@Param("id")Integer id);


    /**
     * 修改教师信息
     * */
    int updateTeacherById(User teacher);
    /**
     * 添加教师信息
     * */
    int insertTeacher(User teacher);

    /**
     * 模糊查询教师信息
     * */
    List<User> queryTeacherByCondition(@Param("str") String str);

    /**
     * 查询所有的教师总数
     * */
    Integer queryCountAllTeacher();

}
