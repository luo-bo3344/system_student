package com.lb.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lb.entity.Attendance;
import com.lb.entity.AttendanceClassRelationShip;
import com.lb.entity.StudentClass;
import com.lb.mapper.AttendanceClassRelationshipMapper;
import com.lb.mapper.AttendanceMapper;
import com.lb.mapper.StudentClassMapper;
import com.lb.service.AttendanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author luobo
 * @date 2021-04-22 16:30
 * @description
 */
@Service
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance> implements AttendanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttendanceServiceImpl.class);
    @Autowired
    private AttendanceMapper attendanceMapper;
    @Autowired
    private StudentClassMapper studentClassMapper;
    @Autowired
    private AttendanceClassRelationshipMapper attendanceClassRelationshipMapper;

    /**
     * 查询所有的考勤信息
     */
    @Override
    public List<Attendance> queryAllAttendance() {

        return attendanceMapper.queryAllAttendance();
    }

    /**
     * 新增缺勤信息
     * @param attendance
     */
    @Override
    public int addAttendance(Attendance attendance,String stuName) {
        int insert = attendanceMapper.insert(attendance);
        AttendanceClassRelationShip attendanceClassRelationShip = new AttendanceClassRelationShip();
        //添加关系表信息（此处需要肩添加的考勤信息ID和学生所对应的班级id）
        StudentClass studentClass = studentClassMapper.queryClassIdByStuName(stuName);
        attendanceClassRelationShip.setAttendanceId(attendance.getId());
        attendanceClassRelationShip.setClassId(studentClass.getId());
        int attendanceClassRelationship = attendanceClassRelationshipMapper.insert(attendanceClassRelationShip);
        return attendanceClassRelationship+insert;
    }

    /**
     * 删除考勤信息
     *
     * @param attendance
     */
    @Override
    public int deleteAttendanceById(Attendance attendance) {
        attendance.setStatus(false);
        int deleteAttendance = attendanceMapper.updateById(attendance);
        return deleteAttendance;
    }

    /**
     * 通过班级名称查询学生考勤信息
     *
     * @param className
     */
    @Override
    public List<Attendance> queryAttendanceByClassName(String className) {
        return attendanceMapper.queryAttendanceByClassName(className);
    }

    /**
     * 模糊查询学生考勤信息
     *
     * @param name
     */
    @Override
    public List<Attendance> queryAttendanceByStuName(String name) {
        return attendanceMapper.queryAttendanceByStuName(name);
    }
}
