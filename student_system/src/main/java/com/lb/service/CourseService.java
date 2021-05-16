package com.lb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lb.entity.Course;
import com.lb.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author luobo
 * @date 2021-04-05 21:32
 * @description
 */
public interface CourseService extends IService<Course> {
    /**
     * 查询所有教师列表
     */
    List<Course> queryCourseAll();

    /**
     * 添加教师信息
     * */
    int addCourseInfo(Course course,String className);

    /**
     * 根据id查询教师信息,用于修改操作
     * */
    Course queryCourseById1(Integer id);
    List<Course> queryCourseById();
    /**
     * 保存信息到数据库
     * */
    int updateCourseInformation(Course course);

    /**
     * 逻辑删除教师信息
     * */
    int deleteById(Course course);

    /**
     * 通过班级名称查询课表
     * */
    List<Course> queryClassCourseByClassName(String className);

    /**
     * 通过课程ID查询课程名字,用于成绩页面展示
     * */
    Course queryCourseNameById(@Param("id")Integer id);

    /**
     * 模糊查询所有的课程信息
     * */
    List<Course> queryCourseByCourseName(String courseName);
}
