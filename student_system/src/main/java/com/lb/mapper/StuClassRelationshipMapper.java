package com.lb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lb.entity.Role;
import com.lb.entity.StuClassRelationship;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StuClassRelationshipMapper extends BaseMapper<StuClassRelationship> {

    /**
     * 根据班级名字查询Id
     * */
    Integer queryIdByName(@Param("className") String className);
}
