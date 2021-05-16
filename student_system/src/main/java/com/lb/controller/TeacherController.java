package com.lb.controller;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lb.entity.Teacher;
import com.lb.entity.User;
import com.lb.mapper.TeacherMapper;
import com.lb.service.TeacherService;
import com.lb.utils.DeleteCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Api("教师信息")
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherController.class);
    //注入service层
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherMapper teacherMapper;


    //教师列表测试页面
    @RequestMapping("/toTeacherList")
    public String teacherList() {
        return "teacher/teacherList";
    }

    @ApiOperation("分页查询教师信息")
    @RequestMapping("/teacherlist")
    public String showTeacher(Model model,
                              @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                              @RequestParam(defaultValue = "8", value = "pageSize") Integer pageSize) {
        //为了程序的严谨性，判断非空：
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        System.out.println("当前页是：" + pageNum + "显示条数是：" + pageSize);
        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum, pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            List<Teacher> teachers = teacherService.queryTeacherAll();
            System.out.println("分页数据：" + teachers);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<Teacher> pageInfo = new PageInfo<Teacher>(teachers, pageSize);
            pageInfo.getPages();
            //4.使用model将值等带回前端
            model.addAttribute("teacherAll", teachers);
            model.addAttribute("pageInfo", pageInfo);

        } finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        //5.设置返回的jsp/html等前端页面
        // thymeleaf默认就会拼串classpath:/templates/xxxx.html
        return "teacher/teacherList";
    }


    /**
     * 查询所有教师信息用于页面展示
     */
    @ApiOperation("查询教师信息进行首页展示")
    @GetMapping("/teacherA11")
    public String queryTeacherAll(Model model) {
        List<Teacher> teachers = teacherService.queryTeacherAll();
        System.out.println(teachers);
        model.addAttribute("teacherAll", teachers);
        return "admin/index";
    }

    /**
     * 查询所有教师信息用于页面展示
     */
    @ApiOperation("查询教师信息进行展示")
    @GetMapping("/teacherA11Show")
    public String queryTeacherAll2(Model model) {
        List<Teacher> teachers = teacherService.queryTeacherAll();
        System.out.println(teachers);
        model.addAttribute("teacherAll", teachers);
        //return "redirect:toTeacherList";
        return "teacher/teacherList";
    }

    @ApiOperation("删除教师信息")
    @GetMapping("/delete/{id}")
    @ResponseBody
    public String deleteTeacher(@PathVariable Integer id) {
        System.out.println("接收的值id="+id);
        User teacher = new User();
        teacher.setId(id);
        int delete = teacherService.deleteTeacherById(teacher);
        LOGGER.info(teacher+"删除信息:" + delete);
        return delete > 0 ? DeleteCode.DELETE_SUCCESS.getMessage() : DeleteCode.DELETE_FAIL.getMessage();
    }

    @ApiOperation("修改教师信息,获取信息进行页面的跳转")
    @GetMapping("/update/{id}")
    public String updateTeacher(@PathVariable Integer id, Model model) {
        //从数据库查询出教师信息用于修改
        Teacher teacher = teacherService.queryTeacherById(id);
        LOGGER.info("修改教师查询的结果：{}", teacher);
        model.addAttribute("updateTeacher", teacher);
        return "teacher/updateTeacher";
    }


    //从页面上接收修改的信息保存保存在数据库中
    @PostMapping("/updateToList")
    public ModelAndView update(User teacher) {
        ModelAndView modelAndView = new ModelAndView();
        LOGGER.info("接收处理前的用户信息:" + teacher);
       /* List<Teacher> teachers = teacherService.queryTeacherAll();
        Set<String> usernames = teachers.stream().map(Teacher::getUsername).collect(Collectors.toSet());
        if (usernames.contains(teacher.getUsername())){
            modelAndView.addObject("exist","用户名已存在请重新输入！");
            modelAndView.setViewName("teacher/updateTeacher");
        }else {

        }*/
        teacher.setUpdateTime(new Date());
        LOGGER.info("接收处理后的用户信息:" + teacher);
        teacherService.updateTeacherById(teacher);
        modelAndView.setViewName("redirect:teacherlist");
        return modelAndView;
    }


    //不修改信息直接返回
    @GetMapping("/returnToList")
    public String returnToList() {
        return "redirect:teacherlist";
    }

    @ApiOperation("添加教师信息")
    @PostMapping("/insert")
    public String insertTeacher(User teacher){
        LOGGER.info("接收输入的教师信息进行添加first{}", teacher);
        teacher.setCreateTime(new Date());
        teacher.setRelationship(2);
        teacherService.insertTeacher(teacher);
        LOGGER.info("接收输入的教师信息进行添加second{}", teacher);
        return "redirect:teacherlist";
    }

    /**
     * 点击新增跳转教师信息添加页面
     */
    @RequestMapping("/addteacher")
    public String addTeacher() {
        return "teacher/addTeacher";
    }

    /**
     * 模糊查询教师信息
     */
    @ApiOperation("模糊查询教师信息")
    @PostMapping("/selectTeacher")
    public ModelAndView selectTeacherByPage( @RequestParam String userName) {
        ModelAndView modelAndView = new ModelAndView();
        String afterUserNameTrim = userName.trim();
        List<User> teachers = teacherService.queryTeacherByCondition(afterUserNameTrim);
        if (CollectionUtils.isEmpty(teachers)){
            modelAndView.addObject("NotFound","暂无数据，请检查输入是否正确");
        }else {
            modelAndView.addObject("queryTeacher",teachers);
        }
        modelAndView.setViewName("teacher/queryResult");
        return modelAndView;
    }
}
