package com.lb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lb.entity.Attendance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author luobo
 * @date 2021-04-22 14:57
 * @description
 */
@Repository
@Mapper
public interface AttendanceMapper extends BaseMapper<Attendance> {

    /**
     * 查询所有的考勤信息
     * */
    List<Attendance>  queryAllAttendance();

    /**
     * 通过班级名称查询学生考勤信息
     * */
    List<Attendance> queryAttendanceByClassName(@Param("className") String className);

    /**
     * 模糊查询学生考勤信息
     * */
    List<Attendance> queryAttendanceByStuName(@Param("name") String name);
}
