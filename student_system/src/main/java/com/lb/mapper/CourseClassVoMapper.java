package com.lb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lb.mapper.mappervo.CourseClassVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author luobo
 * @date 2021-04-26 22:21
 * @description 用于学生成绩的查询（教师）
 */
@Repository
@Mapper
public interface CourseClassVoMapper extends BaseMapper<CourseClassVo> {
    /**
     * 查询课程对应的班级，用于学生成绩
     * */
    @Select("SELECT c.course_name,sc.class_name from course c \n" +
            "INNER JOIN t_class_course tcc on c.id=tcc.course_id\n" +
            "INNER JOIN student_class sc on tcc.class_id=sc.id \n" +
            "INNER JOIN user u on c.teacher_id=u.id \n" +
            "WHERE u.relationship=2 AND u.`status`=1 and c.`status`=1 and u.name=#{name};")
    List<CourseClassVo> queryTeacherWithCourseAndStuClass(@Param("name")String teacherName);

}
