package com.lb.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lb.entity.Role;
import com.lb.entity.Teacher;
import com.lb.entity.User;
import com.lb.entity.UserRole;
import com.lb.mapper.RoleMapper;
import com.lb.mapper.TeacherMapper;
import com.lb.mapper.UserMapper;
import com.lb.mapper.UserRoleMapper;
import com.lb.service.TeacherService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Teacher的service层
 * */
@Service
public class TeacherServiceImpl extends ServiceImpl<UserMapper,User> implements TeacherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherServiceImpl.class);
    //注入持久层
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private UserRoleMapper  userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;


    //分页查询教师信息
    @Override
    public PageInfo<Teacher> queryTeacherByPage(int pageNum, int pageSize) {
        /**
         * 开始分页
         * @param pageNum  页码
         * @param pageSize 每页显示数量
         */
        PageHelper.startPage(pageNum,pageSize);
        //查询教师信息
        List<Teacher> teachers = teacherMapper.queryTeacherAll();
        //打印日志信息
        LOGGER.info(JSONObject.toJSONString(teachers));
        //查询出的信息进行分页处理
        PageInfo pageInfo = new PageInfo(teachers);
        return pageInfo;
    }

    /**
     * 查询所有教师信息,用于主页面展示
     */
    @Override
    public List<Teacher> queryTeacherAll() {
        List<Teacher> teachers = teacherMapper.queryTeacherAll();
        return teachers;
    }

    //删除教师信息
    @Override
    public int deleteTeacherById(User teacher) {
        //采用mybatis—plus
        teacher.setStatus(0);
        return teacherMapper.updateById(teacher);
    }

    //修改教师信息
    @Override
    public int updateTeacherById(User teacher) {
        return teacherMapper.updateTeacherById(teacher);
    }



    /**
     * 查询单个教师信息用于页面修改教师信息
     *
     * @param id
     */
    @Override
    public Teacher queryTeacherById(Integer id) {
        return teacherMapper.queryTeacherById(id);
    }

    /**
     * 在教师页面搜索查询模糊查询
     */
    @Override
    public List<User> queryTeacherInfo(Integer pageNum, Integer pageSize,String input) {
        User user = new User();
        Page<User> page1= PageHelper.startPage(pageNum, pageSize);
        Page<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> query=new QueryWrapper<>();
        if(!StringUtils.isEmpty(input)){
            query.eq("name",input);
            query.eq("username",input);
        }
        IPage<User> userIPage = baseMapper.selectPage((IPage<User>) page, query);
        List<User> list = ((IPage<User>) page).getRecords();
        return list;
    }



    /**
     * 添加教师信息
     *
     * @param teacher
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertTeacher(User teacher){
   /*     Map<String,Object> map = new HashMap<>();
        map.put("username",teacher.getUsername().trim());
        List<User> list = super.baseMapper.selectByMap(map);*/
        LOGGER.info("添加教师接收的数据{}",teacher);
        //向表中插入数据
        int insert1 = userMapper.insert(teacher);
        System.out.println("获取的ID值"+teacher.getId());
       // int i = teacherMapper.insertTeacher(teacher);
        //查询教师角色的ID
        Role role = roleMapper.queryRoleByName("teacher");
        //将插入数据的user表中的ID和查询出来的角色ID村如关联表中
        UserRole userRole = new UserRole();
        userRole.setUserId(teacher.getId());
        userRole.setRoleId(role.getId());
        int insert = userRoleMapper.insertUserRole(userRole);
        return insert1;
    }

   /* @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertTeacher(Teacher teacher) throws Exception {
        int i = teacherMapper.insertTeacher(teacher);
        LOGGER.info("insert database's primary key{}" ,teacher.getId());
        if (i<=0){
            throw new Exception();
        }
        User user = new User();
        //user.setUserName(teacher.getName());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date=new Date();
        String format = sdf.format(date);
        //user.setCreateTime(format);
        user.setRelationship(teacher.getId());
        int  i1 = userMapper.insertUser(user);
        if (i1<=0){
            throw new Exception();
        }
    }*/

    /**
     * 模糊查询教师信息
     *
     * @param input
     */
    @Override
    public List<User> queryTeacherByCondition(String input) {
        return teacherMapper.queryTeacherByCondition(input);
    }

    /**
     * 查询所有的教师总数
     */
    @Override
    public Integer queryCountAllTeacher() {
        return teacherMapper.queryCountAllTeacher();
    }

}
