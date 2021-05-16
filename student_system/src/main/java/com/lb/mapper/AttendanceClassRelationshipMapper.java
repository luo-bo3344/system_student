package com.lb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lb.entity.AttendanceClassRelationShip;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author luobo
 * @date 2021-04-24 22:34
 * @description 考勤班级关系
 */
@Repository
@Mapper
public interface AttendanceClassRelationshipMapper extends BaseMapper<AttendanceClassRelationShip> {

}
