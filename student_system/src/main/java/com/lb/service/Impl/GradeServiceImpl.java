package com.lb.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lb.entity.Course;
import com.lb.entity.Grade;
import com.lb.mapper.CourseMapper;
import com.lb.mapper.GradeMapper;
import com.lb.service.CourseService;
import com.lb.service.GradeService;
import io.swagger.annotations.ApiModel;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author luobo
 * @date 2021-04-26 16:46
 * @description 学生成绩service层
 */
@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {


    @Autowired
    private GradeMapper gradeMapper;
    @Autowired
    private  CourseMapper courseMapper;

    /**
     * 查询班级成绩列表
     *
     * @param courseId
     * @param classId
     */
    @Override
    public List<Grade> queryGradeByCourseAndClass(Integer courseId, Integer classId) {
        return gradeMapper.queryGradeByCourseAndClass(courseId,classId);
    }

    @Override
    public Grade queryGradeById(Grade grade) {
        return gradeMapper.selectById(grade.getId());
    }

    @Override
    public int addGradeById(Grade grade) {
        return gradeMapper.updateById(grade);
    }

    /**
     * 逻辑删除学生成绩
     *
     * @param grade
     */
    @Override
    public int deleteGradeById(Grade grade) {
        grade.setStatus(0);
        return gradeMapper.updateById(grade);
    }

    /**
     * 查询学生的成绩用于页面展示，根据学生的名字（学生权限）
     * @param studentName
     */
    @Override
    public List<Grade> queryStudentGradeByStudentName(String studentName) {
        return gradeMapper.queryStudentGradeByStudentName(studentName);
    }


}
