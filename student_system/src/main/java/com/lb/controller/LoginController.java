package com.lb.controller;

import com.lb.entity.AuthorizationInfoUser;
import com.lb.entity.StudentClass;
import com.lb.entity.Teacher;
import com.lb.entity.User;
import com.lb.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

@Api("用户登录")
@Controller
@RequestMapping("/user")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    //主页面
    @RequestMapping("/index")
    public String index1() {
        return "admin/index";
    }

    /**
     * 查询所有教师信息用于页面展示
     */
    //注入service层
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private AuthorizationInfoService infoService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentClassService studentClassService;
    @Autowired
    private UserService userService;

    @ApiOperation("查询教师信息进行首页展示")
    @GetMapping("/teacherA11")
    public String queryTeacherAll(Model model) {
        //查询所有教师信息
        List<Teacher> teachers = teacherService.queryTeacherAll();
        Integer teacherNumbers = teacherService.queryCountAllTeacher();
        Integer studentNumbers = studentService.queryCountAllStudent();
        Integer classNumbers = studentClassService.queryCountAllClass();
        model.addAttribute("teacherNumbers", teacherNumbers);
        model.addAttribute("studentNumbers", studentNumbers);
        model.addAttribute("classNumbers", classNumbers);
        model.addAttribute("teacherAll", teachers);
        return "admin/index";
    }

    @ApiOperation("展示柱形Echarts图表")
    @PostMapping("/echarts")
    @ResponseBody
    public Map<String, Integer> echarts(){
        //Echars图
        //获取所有的班级信息
        List<StudentClass> studentClasses = studentClassService.queryStuClassAll();
        List<String> className = studentClasses
                .stream()
                .map(StudentClass::getClassName)
                .collect(Collectors.toList());
        List<User> studentByClassName = null;
        Map<String, Integer> classAndCount = new HashMap<>();
        //获取班级名字和班级人数
        for (int i = 0; i < className.size(); i++) {
            studentByClassName=userService.queryStudentByClassName(className.get(i));
            classAndCount.put(className.get(i),studentByClassName.size());
        }
        return classAndCount;
    }

    @ApiOperation("展示柱形Echarts图表")
    @PostMapping("/echartspie")
    @ResponseBody
    public Map<String, Integer> echartsPie(){
        //Echars图
        //获取所有的班级信息
        Integer boy = studentService.queryBoyStudentBySex();
        Integer girl = studentService.queryGirlStudentBySex();
        Map<String, Integer> countBoyOrGirl = new HashMap<>();
        countBoyOrGirl.put("1",boy);
        countBoyOrGirl.put("2",girl);
        return countBoyOrGirl;
    }
    //登录
    @ApiOperation("用户登录")
    @PostMapping("/loginuser")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
                        Model model, HttpServletRequest req) {
        System.out.println("接受的前端的值" + username + "   " + password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        AuthorizationInfoUser infoUser = null;
        try {
            infoUser = infoService.queryUserForAuthorization(username);
            if (infoUser.getUsername() != null && infoUser.getRelationship() != null) {
                String showUsername = infoUser.getUsername();
                Integer roleName = infoUser.getRelationship();
                String name = infoUser.getName();
                req.getSession().setAttribute("showUsername", showUsername);
                req.getSession().setAttribute("username", name);
                req.getSession().setAttribute("roleName", roleName);
            }
        } catch (NullPointerException e) {
            LOGGER.info("登录参数出现异常");
        }
        try {
            subject.login(token);
            System.out.println("登录成功");
            LOGGER.info("登录成功");
            model.addAttribute("message", "登录成功");
        } catch (UnknownAccountException e) {
            model.addAttribute("message", "账号错误");
            System.out.println("账号错误");
            return "login1";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("message", "密码错误");
            System.out.println("密码错误");
            return "login1";
        } catch (AuthenticationException e) {
            model.addAttribute("message", "用户不存在");
            System.out.println("用户不存在");
            return "login1";
        }
        return "redirect:teacherA11";
    }

    @ApiOperation("用户登出，退出系统")
    @GetMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        LOGGER.info("用户退出成功");
        return "login1";
    }

}
