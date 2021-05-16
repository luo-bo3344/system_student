package com.lb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lb.entity.Course;
import com.lb.entity.StudentClass;
import com.lb.entity.User;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author luobo
 * @date 2021-04-04 18:53
 * @description
 */
@Repository
@Mapper
public interface StudentClassMapper extends BaseMapper<StudentClass> {


    /**
     * 查询班级id通过学生名字(此处通过注解查询)
     * */
    @Select("SELECT sc.id,sc.class_name ,sc.description,sc.update_time,sc.`status` FROM student_class sc LEFT JOIN " +
            "t_stu_class tsc on sc.id=tsc.class_id\n" +
            "LEFT JOIN user u on tsc.student_id=u.id where u.relationship=3 AND u.`status`=1 AND sc.`status`=1 and u" +
            ".`name`=#{stuName}")
    StudentClass queryClassIdByStuName(@Param("stuName") String stuName);


    /**
     * 查询班级名称根据ID(用于成绩页面的展示)
     * */
    @Select("SELECT * from student_class where `status`=1 and id=#{id}")
    StudentClass queryStudentClassNameById(@Param("id") Integer id);


    /**
     * 模糊查询班级信息
     * */
    @Select("SELECT * FROM student_class WHERE `status`=1 and class_name like CONCAT('%',#{className},'%')")
    List<StudentClass> queryClassByClassName(@Param("className") String className);
}
