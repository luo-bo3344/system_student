package com.lb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lb.mapper.mappervo.CourseAndClassNameVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author luobo
 * @date 2021-04-27 15:31
 * @description
 */
@Repository
@Mapper
public interface CourseAndClassNameVoMapper extends BaseMapper<CourseAndClassNameVo> {

    /**
     * 查询班级的ID和课程的ID(这样写有问题，未使用该方法)
     * */
    @Select("SELECT c.id,sc.id from student_class sc INNER JOIN t_class_course tcc on sc.id=tcc.class_id\n" +
            "INNER JOIN course c on tcc.course_id=c.id where sc.`status`=1 and c.`status`=1 and c.course_name=#{courseName}" +
            "and sc.class_name=#{className}")
    CourseAndClassNameVo queryIdsByCourseAndClassName(@Param("courseName")String courseName,@Param("className")String className);

    /**
     * 查询班级的ID和课程的ID(这样写才行，使用了此方法)
     * */
    CourseAndClassNameVo queryIdsByCourseAndClassName1(@Param("courseName")String courseName,@Param("className")String className);
}
