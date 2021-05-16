package com.lb.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lb.controller.StudentController;
import com.lb.entity.StudentClass;
import com.lb.mapper.StudentClassMapper;
import com.lb.service.StudentClassService;
import com.lb.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author luobo
 * @date 2021-04-04 18:56
 * @description
 */
@Service
public class StudentClassServiceImpl extends ServiceImpl<StudentClassMapper, StudentClass> implements StudentClassService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentClassServiceImpl.class);
    @Autowired
    private StudentClassMapper classMapper;
    /**
     * 查询所有班级信息用于页面列表展示
     */
    @Override
    public List<StudentClass> queryStuClassAll() {
        QueryWrapper<StudentClass> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("status",1);
        List<StudentClass> studentClasses = classMapper.selectList(queryWrapper);
        return studentClasses;
    }

    @Override
    public IPage<StudentClass> selectAllByPage(Integer pageNum, Integer pageSize) {
        Page<StudentClass> userPage = new Page<>(pageNum, pageSize);
        IPage<StudentClass> studentClassIPage = classMapper.selectPage(userPage, null);
        return studentClassIPage;
    }

    /**
     * 添加班级的信息
     *
     * @param addStuClass
     */
    @Override
    public int addStuClass(StudentClass addStuClass) {
        int insert = classMapper.insert(addStuClass);
        return insert;
    }

    /**
     * 查询该班级是否添加过
     *
     * @param className
     */
    @Override
    public StudentClass queryIsExist(String className) {
        QueryWrapper<StudentClass> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("class_name",className);
        queryWrapper.eq("status",1);
        StudentClass studentClasses = classMapper.selectOne(queryWrapper);
        return studentClasses;
    }

    /**
     * 根据id查询班级信息,用于修改操作
     *
     * @param studentClass
     */
    @Override
    public StudentClass queryStuClassById(StudentClass studentClass) {
        LOGGER.info("service接收的班级ID{}",studentClass.getId());
        return classMapper.selectById(studentClass.getId());
    }

    /**
     * 修改后保存信息到数据库
     *
     * @param studentClass
     */
    @Override
    public int updateStuClassInformation(StudentClass studentClass) {
        studentClass.setUpdateTime(new Date());
        return classMapper.updateById(studentClass);
    }

    /**
     * 删除班级信息
     *
     * @param studentClass
     */
    @Override
    public int deleteById(StudentClass studentClass) {
        studentClass.setStatus(false);
        int deleteStuClass = classMapper.updateById(studentClass);
        return deleteStuClass;
    }

    /**
     * 查询所有的班级数量
     */
    @Override
    public Integer queryCountAllClass() {
        QueryWrapper<StudentClass> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1);
        Integer classNumbers = classMapper.selectCount(queryWrapper);
        return classNumbers;
    }

    /**
     * 查询班级名字通过ID用于成绩页面的展示
     *
     * @param id
     */
    @Override
    public StudentClass queryStudentClassNameById(Integer id) {
        return classMapper.queryStudentClassNameById(id);
    }

    /**
     * 模糊查询班级信息
     *
     * @param className
     */
    @Override
    public List<StudentClass> queryClassByClassName(String className) {
        return classMapper.queryClassByClassName(className);
    }
}
