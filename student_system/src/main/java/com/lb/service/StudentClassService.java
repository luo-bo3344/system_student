package com.lb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lb.entity.StudentClass;
import com.lb.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author luobo
 * @date 2021-04-04 18:55
 * @description
 */
public interface StudentClassService extends IService<StudentClass> {

    /**
     * 查询所有班级信息用于页面列表展示
     * */
    List<StudentClass> queryStuClassAll();
    //未使用
    IPage<StudentClass> selectAllByPage(Integer pageNum, Integer pageSize);

    /**
     * 添加班级的信息
     * */
    int addStuClass(StudentClass addStuClass);

    /**
     * 查询该班级是否添加过
     * */
    StudentClass queryIsExist(String className);

    /**
     * 根据id查询班级信息,用于修改操作
     * */
    StudentClass queryStuClassById(StudentClass studentClass);
    /**
     * 修改后保存信息到数据库
     * */
    int updateStuClassInformation(StudentClass studentClass);
    /**
     * 删除班级信息
     * */
    int deleteById(StudentClass studentClass);

    /**
     * 查询所有的班级数量
     * */
    Integer queryCountAllClass();


    /**
     * 查询班级名字通过ID用于成绩页面的展示
     * */
    StudentClass queryStudentClassNameById(Integer id);

    /**
     * 模糊查询班级信息
     * */
    List<StudentClass> queryClassByClassName(String className);

}

