package com.lb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lb.entity.Course;
import com.lb.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author luobo
 * @date 2021-04-05 21:26
 * @description
 */
@Repository
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    List<Course> queryCourseAll();

    Course queryCourseById(Integer id);

    /**
     * 查询课表
     * */
    List<Course> queryClassCourseByClassName(String className);

    /**
     * 根据教师名字查询教师所有的课程
     * */
    List<Course> queryCourseNameByTeacherName(@Param("name") String name);

    /**
     * 通过课程ID查询课程名字
     * */
    Course queryCourseNameById(@Param("id")Integer id);

    /**
     * 模糊查询所有的课程信息
     * */
    List<Course> queryCourseByCourseName(@Param("courseName") String courseName);
}
