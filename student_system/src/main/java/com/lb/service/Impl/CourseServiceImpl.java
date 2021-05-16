package com.lb.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lb.entity.*;
import com.lb.mapper.ClassCourseRelationshipMapper;
import com.lb.mapper.CourseMapper;
import com.lb.mapper.GradeMapper;
import com.lb.mapper.UserMapper;
import com.lb.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author luobo
 * @date 2021-04-05 21:33
 * @description
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentClassServiceImpl.class);
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private StudentClassService studentClassService;
    @Autowired
    private ClassCourseRelationshipMapper classCourseRelationshipMapper;
    @Autowired
    private UserService  userService;
    @Autowired
    private GradeMapper gradeMapper;
    /**
     * 查询所有教师列表
     */
    @Override
    public List<Course> queryCourseAll() {
        return courseMapper.queryCourseAll();
    }

    /**
     * 添加课程信息
     *
     * @param course
     */
    @Transactional
    @Override
    public int addCourseInfo(Course course,String className) {
            StudentClass classId = studentClassService.queryIsExist(className);
            if (!StringUtils.isEmpty(classId)){
                int addCourse = courseMapper.insert(course);
                ClassCourseRelationship classCourseRelationship = new ClassCourseRelationship();
                classCourseRelationship.setCourseId(course.getId());
                classCourseRelationship.setClassId(classId.getId());
                int addRelationship = classCourseRelationshipMapper.insert(classCourseRelationship);
                //获取该班级下的所有学生
                List<User> allStudent = userService.queryStudentByClassName(className);
                Grade grade = new Grade();
                //获取学生信息在添加课程的时候将该课程设置给每一个学生(班级已有学生)
                //后面来的学生为他需要手动添加
                for (User student : allStudent) {
                    grade.setStudentId(student.getId());
                    grade.setCourseId(course.getId());
                    grade.setClassId(classId.getId());
                    grade.setGrade(null);
                    grade.setUpdateTime(new Date());
                    grade.setStatus(1);
                    gradeMapper.insert(grade);
                }
                return addCourse+addRelationship;
            }
        return 0;
    }

    /**
     * 根据id查询教师信息,用于修改操作
     *
     * @param id
     */
    @Override
    public Course queryCourseById1(Integer id) {
        return courseMapper.queryCourseById(id);
    }

    /**
     * 根据id查询教师信息,用于修改操作
     *
     *
     */
    @Override
    public List<Course> queryCourseById() {
        return courseMapper.queryCourseAll();
    }

    /**
     * 保存信息到数据库
     *
     * @param course
     */
    @Override
    public int updateCourseInformation(Course course) {
        return courseMapper.updateById(course);
    }

    /**
     * 逻辑删课程师信息
     *
     * @param course
     */
    @Override
    public int deleteById(Course course) {
        course.setStatus(false);
        //课程信息删除后，对应的学生成绩也应该没有了
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",course.getId());
        queryWrapper.eq("status",1);
        List<Grade> grades = gradeMapper.selectList(queryWrapper);
        for (Grade grade1 : grades) {
            grade1.setStatus(0);
            gradeMapper.updateById(grade1);
        }
        return courseMapper.updateById(course);
    }

    /**
     * 通过班级名称查询课表
     *
     * @param className
     */
    @Override
    public List<Course> queryClassCourseByClassName(String className) {
        return courseMapper.queryClassCourseByClassName(className);
    }

    /**
     * 通过课程ID查询课程名字,用于成绩页面展示
     *
     * @param id
     */
    @Override
    public Course queryCourseNameById(Integer id) {
        return courseMapper.queryCourseNameById(id);
    }

    /**
     * 模糊查询所有的课程信息
     *
     * @param courseName
     */
    @Override
    public List<Course> queryCourseByCourseName(String courseName) {
        return courseMapper.queryCourseByCourseName(courseName);
    }
}
