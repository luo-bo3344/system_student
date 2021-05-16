package com.lb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lb.entity.StudentClass;
import com.lb.entity.User;
import com.lb.service.StudentClassService;
import com.lb.service.StudentService;
import com.lb.service.UserService;
import com.lb.utils.DeleteCode;

import com.lb.vo.StudentVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.servlet.ModelAndView;

/**
 * 学生信息管理
 */
@Controller
@RequestMapping("/student")
public class StudentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentClassService studentClassService;
    @Autowired
    private UserService userService;

    @ApiOperation("分页查询学生信息")
    @RequestMapping("/studentList")
    public String showTeacher(Model model,
                              @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                              @RequestParam(defaultValue = "8", value = "pageSize") Integer pageSize) {
        //为了程序的严谨性，判断非空：
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        System.out.println("当前页是：" + pageNum + "显示条数是：" + pageSize);
        PageHelper.startPage(pageNum, pageSize);
        try {
            List<User> studentList = studentService.queryStudentAll();
            System.out.println("学生分页数据：" + studentList);
            PageInfo<User> pageInfo = new PageInfo<User>(studentList, pageSize);
            model.addAttribute("studentList", studentList);
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage();
        }
        return "student/studentList";
    }

    @ApiOperation("添加学生信息")
    @PostMapping("/insertStudent")
    public ModelAndView addStudent(StudentVo studentvo){
        LOGGER.info("接收的信息**********{}",studentvo);
        ModelAndView modelAndView = new ModelAndView();
        User student = new User();
        //接收前端的值做处理
        student.setUsername(studentvo.getUsername());
        student.setPassword(studentvo.getPassword());
        student.setName(studentvo.getName());
        student.setAge(studentvo.getAge());
        student.setSex(studentvo.getSex());
        student.setBirthday(studentvo.getBirthday());
        student.setAddress(studentvo.getAddress());
        student.setPhone(studentvo.getPhone());
        //后台自动处理的值
        student.setCreateTime(new Date());
        student.setRelationship(3);
        LOGGER.info("*********接收的信息处理后{}",student);
        //获取所有的班级信息
        List<StudentClass> studentClasses = studentClassService.queryStuClassAll();
        //获取所有的班级名称
        List<StudentClass> className = studentClasses
                .stream()
                .filter(name->name.getClassName().equals(studentvo.getClassName()))
                .collect(Collectors.toList());
        if (className.size()==0){
            modelAndView.addObject("NotFound","班级名称"+"“"+studentvo.getClassName()+"“"+" 不存在请检查输入是否正确！");
            modelAndView.setViewName("student/addStudent");
        }else {
            int addStudent = studentService.insertStudent(student,studentvo.getClassName());
            LOGGER.info("添加是否成功{},{}",addStudent,student);
            modelAndView.setViewName("redirect:studentList");
        }

        return modelAndView;
    }
    //不修改信息直接返回
    @GetMapping("/returnToList")
    public String returnToList() {
        return "redirect:studentList";
    }

    //添加学生信息跳转路由
    @GetMapping("/addStudent")
    public ModelAndView addTeacher() {
        ModelAndView modelAndView = new ModelAndView();
        //获取所有的班级信息
        List<StudentClass> studentClasses = studentClassService.queryStuClassAll();
        //获取所有的班级名称
        List<String> className = studentClasses
                .stream()
                .map(StudentClass::getClassName)
                .collect(Collectors.toList());
        modelAndView.addObject("className",className);
        modelAndView.setViewName("student/addStudent");
        return modelAndView;
    }

    @ApiOperation("删除学生信息")
    @GetMapping("/delete/{id}")
    @ResponseBody
    public String deleteTeacher(@PathVariable Integer id) {
        User student = new User();
        student.setId(id);
        int delete =studentService.deleteStudentById(student);
        LOGGER.info("student删除信息:" + delete);
        return delete > 0 ? DeleteCode.DELETE_SUCCESS.getMessage() : DeleteCode.DELETE_FAIL.getMessage();
    }


    @ApiOperation("修改学生信息,获取信息进行页面的跳转")
    @GetMapping("/update/{id}")
    public String updateTeacher(@PathVariable Integer id, Model model) {
        User student = new User();
        student.setId(id);
        User studentInformation = studentService.queryStudentById(student);
        model.addAttribute("studentInformation",studentInformation);
        return "student/updateStudent";
    }
    @ApiOperation("修改学生信息,保存到数据库")
    @PostMapping("/updateStudentInformation")
    public String updateStudentInformation(User student,Model model) {
        student.setUpdateTime(new Date());
        LOGGER.info("接收的修改学生信息{}",student);
        int updateStu = studentService.updateStudentInformation(student);
        return "redirect:studentList";
    }


    @ApiOperation("批量删除学生信息")
    @PostMapping("/batchDelete")
    @ResponseBody
    public String batchDelete(@RequestParam String ids){
        System.out.println("接收前端的所有批量删除的id"+ids);
        return ids;
    }

    /**
     * 模糊查询学生信息信息
     */
    @ApiOperation("模糊查询教师信息")
    @PostMapping("/queryStudent")
    public ModelAndView selectTeacherByPage( @RequestParam String studentName) {
        ModelAndView modelAndView = new ModelAndView();
        String afterStudentNameTrim = studentName.trim();
        List<User> student = studentService.queryStudentByCondition(afterStudentNameTrim);
        if (CollectionUtils.isEmpty(student)){
            modelAndView.addObject("NotFound","暂无数据，请检查输入是否正确");
        }else {
            modelAndView.addObject("queryStudent",student);
        }
        modelAndView.setViewName("student/queryResult");
        return modelAndView;
    }
}
