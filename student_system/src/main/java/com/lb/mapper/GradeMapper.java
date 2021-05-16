package com.lb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lb.entity.Grade;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author luobo
 * @date 2021-04-26 16:44
 * @description
 */
@Repository
@Mapper
public interface GradeMapper extends BaseMapper<Grade> {

    /**
     * 查询成绩列表(为了简单此处直接使用注解查询班级成绩)
     * */
    @Select("SELECT *from grade where course_id=#{courseId} and class_id=#{classId} and `status`=1")
    List<Grade> queryGradeByCourseAndClass(@Param("courseId")Integer courseId,@Param("classId")Integer classId);

    /**
     *查询学生的成绩用于页面展示，根据学生的名字（学生权限）
     * */
    List<Grade> queryStudentGradeByStudentName(@Param("studentName")String studentName);
}
