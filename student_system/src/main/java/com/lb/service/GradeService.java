package com.lb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lb.entity.Course;
import com.lb.entity.Grade;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author luobo
 * @date 2021-04-26 16:45
 * @description
 */
public interface GradeService extends IService<Grade> {

    /**
     * 查询班级成绩列表
     * */
    List<Grade> queryGradeByCourseAndClass(Integer courseId,Integer classId);

    /**
     * 通过ID查询学生的成绩，用于页面的展示
     * */
    Grade queryGradeById(Grade grade);

    /**
     * 添加成绩信息
     * */
    int addGradeById(Grade grade);

    /**
     * 逻辑删除学生成绩
     * */
    int deleteGradeById(Grade grade);

    /**
     *查询学生的成绩用于页面展示，根据学生的名字（学生权限）
     * */
    List<Grade> queryStudentGradeByStudentName(String studentName);
}
