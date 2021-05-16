package com.lb.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lb.entity.*;
import com.lb.mapper.*;
import com.lb.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 学生信息Service层
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, User> implements StudentService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private StuClassRelationshipMapper stuClassRelationshipMapper;
    @Autowired
    private StudentClassMapper  studentClassMapper;

    /**
     * 查询学生信息
     */
    @Override
    public List<User> queryStudentAll() {
        List<User> student = studentMapper.queryStudentAll();
        return student;
    }

    /**
     * 添加学生信息
     *
     * @param student
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertStudent(User student,String className) {
        //利用mybatis-plus进行插入数据
        int addStudent = studentMapper.insert(student);
        System.out.println("studentService获取添加数据后的ID：" + student.getId());
        Role role = roleMapper.queryRoleByName("student");
        //添加信息的角色关系表
        UserRole userRole = new UserRole();
        userRole.setUserId(student.getId());
        userRole.setRoleId(role.getId());
        int addUserRole = userRoleMapper.insertUserRole(userRole);
        //添加信息的班级学生关系表
        QueryWrapper<StudentClass> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_name",className);
        StudentClass studentClass = studentClassMapper.selectOne(queryWrapper);
        StuClassRelationship relationship=new StuClassRelationship();
        relationship.setStudentId(student.getId());
        relationship.setClassId(studentClass.getId());
        int relationshipTable = stuClassRelationshipMapper.insert(relationship);
        return addStudent + addUserRole+relationshipTable;
    }

    /**
     * 删除学生信息，逻辑删除
     *
     * @param student
     */
    @Override
    public int deleteStudentById(User student) {
        student.setStatus(0);
        int delete = studentMapper.updateById(student);
        return delete;
    }


    /**
     * 根据id查询教师信息,用于修改操作
     *
     * @param student
     */
    @Override
    public User queryStudentById(User student) {

        return studentMapper.selectById(student);
    }

    /**
     * 保存信息到数据库
     *
     * @param student
     */
    @Override
    public int updateStudentInformation(User student) {
        //使用mybatis-plus
        int updateStu = studentMapper.updateById(student);
        return updateStu;
    }

    /**
     * 查询所有的学生总数
     */
    @Override
    public Integer queryCountAllStudent() {
        return studentMapper.queryCountAllStudent();
    }

    /**
     * 根据名字查询学生ID,用于学生的考勤
     *
     * @param stuName
     */
    @Override
    public User queryStudentIdByStudentName(String stuName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",stuName);
        queryWrapper.eq("status",1);
        queryWrapper.eq("relationship",3);
        User user = studentMapper.selectOne(queryWrapper);
        return user;
    }

    /**
     * 根据ID查询学生姓名用于成绩页面名字的展示
     *
     * @param id
     */
    @Override
    public User queryStudentNameById(Integer id) {
        return studentMapper.queryStudentNameById(id);
    }

    /**
     * 查询所有男同学数量,用于首页的饼图
     */
    @Override
    public Integer queryBoyStudentBySex() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sex","男");
        queryWrapper.eq("relationship",3);
        queryWrapper.eq("status",1);
        Integer boy = studentMapper.selectCount(queryWrapper);
        return boy;
    }

    /**
     * 查询所有女同学数量,用于首页的饼图
     */
    @Override
    public Integer queryGirlStudentBySex() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sex","女");
        queryWrapper.eq("relationship",3);
        queryWrapper.eq("status",1);
        Integer girl = studentMapper.selectCount(queryWrapper);
        return girl;
    }

    /**
     * 模糊查询学学生信息
     *
     * @param userName
     */
    @Override
    public List<User> queryStudentByCondition(String userName) {
        return studentMapper.queryStudentByCondition(userName);
    }
}
