package com.lb.controller;

import com.lb.entity.Teacher;
import com.lb.entity.User;
import com.lb.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author luobo
 * @date 2021-04-04 15:00
 * @description
 */
@RequestMapping("/person")
@Controller
public class PersonInformationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private PersonInfoService personInfoService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentClassService studentClassService;
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
    /**
     * 个人信息修改操作
     * */
    @ApiOperation("获取登录用户相关信息")
    @GetMapping("/update")
    public String updatePersonInfo(Model model, HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("showUsername");
        User queryResult = personInfoService.editPersonInformation(username);
        model.addAttribute("queryResult",queryResult);
        return "common/editPersonInfo";
    }

    /**
     * 点击个人信息跳转页面
     * */
    @Autowired
    private StudentService studentService;
    @ApiOperation("修改用户登录信息保存数据库")
    @PostMapping("/savePersonInfo")
    public String savePersonInfo(User user){
        user.setUpdateTime(new Date());
        LOGGER.info("接收的用户信息的修改{}",user);
        studentService.updateStudentInformation(user);
        return "redirect:teacherA11";
    }

    @ApiOperation("用户登出，退出系统")
    @GetMapping("/logout")
    public String logout(Model model){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        LOGGER.info("用户退出成功");
        model.addAttribute("success","密码修改成功,请重新登录！");
        return "login1";
    }

    @GetMapping("/toUpdatePassword")
    public ModelAndView toUpdatePassword(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("common/updatePassword");
        return modelAndView;
    }

    @ApiOperation("密码修改后，重新登录系统")
    @PostMapping("/updatePassword")
    public ModelAndView updatePassword(@RequestParam String password, @RequestParam String surePassword, HttpServletRequest req){
        ModelAndView modelAndView = new ModelAndView();
        Assert.notNull(password,"password can not null");
        Assert.notNull(password,"surePassword can not null");
        //获取用户名
        String showUsername = (String) req.getSession().getAttribute("showUsername");
        if (password.equals(surePassword)){
            //修改密码
            User user = new User();
            user.setUsername(showUsername);
            user.setPassword(surePassword);
            personInfoService.updatePassword(user);
            modelAndView.setViewName("redirect:logout");
        }else {
            modelAndView.addObject("surePassword","两次输入密码不一致，请重新输入");
            modelAndView.setViewName("common/updatePassword");
        }
        return modelAndView;
    }

}
