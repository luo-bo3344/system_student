package com.lb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lb.entity.Attendance;
import com.lb.entity.AuthorizationInfoUser;
import com.lb.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author luobo
 * @date 2021-04-14 22:47
 * @description
 */
public interface AttendanceService extends IService<Attendance> {

    /**
     * 查询所有的考勤信息
     * */
    List<Attendance> queryAllAttendance();

    /**
     * 新增缺勤信息
     * */
     int addAttendance(Attendance attendance,String stuName);

    /**
     * 删除考勤信息
     * */
    int deleteAttendanceById(Attendance attendance);
    /**
     * 通过班级名称查询学生考勤信息
     * */
    List<Attendance> queryAttendanceByClassName(String className);


    /**
     * 模糊查询学生考勤信息
     * */
    List<Attendance> queryAttendanceByStuName(String name);
}
