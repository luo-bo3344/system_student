package com.lb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lb.entity.StudentClass;
import com.lb.entity.User;
import com.lb.service.StudentClassService;
import com.lb.service.UserService;
import com.lb.utils.DeleteCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luobo
 * @date 2021-04-04 19:27
 * @description:班级信息管理
 */
@Api(tags = "学生班级类")
@RequestMapping("/stuClass")
@Controller
public class StudentClassController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentClassController.class);
    @Autowired
    private StudentClassService classService;
    @Autowired
    private UserService userService;

    @ApiOperation("查询班级相关信息用于页面展示")
    @GetMapping("/classList")
    public ModelAndView stuClassList(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                                     @RequestParam(defaultValue = "8", value = "pageSize") Integer pageSize) {
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        System.out.println("当前页是：" + pageNum + "显示条数是：" + pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<StudentClass> studentClassIPage = classService.queryStuClassAll();
        PageInfo<StudentClass> pageInfo = new PageInfo<StudentClass>(studentClassIPage, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        if (pageInfo.getPages() > 0) {
            modelAndView.addObject("pageInfo", pageInfo);
            modelAndView.setViewName("class/classList");
        } else {
            modelAndView.setViewName("error/error");
            modelAndView.addObject("msg", "查询失败");
        }
        return modelAndView;
    }

    /**
     * 不修改或添加直接返回list页面
     */
    @GetMapping("/returnToList")
    public String returnToList() {
        return "redirect:classList";
    }

    /**
     * 添加班级路由跳转
     */
    @GetMapping("/addstuclass")
    public String addTeacher() {
        return "class/addStuClass";
    }

    @ApiOperation("添加班级信息")
    @PostMapping("/addStuClass")
    public ModelAndView aaddStuClass(StudentClass studentClass) {
        LOGGER.info("接收的班级信息值{}", studentClass);
        ModelAndView modelAndView = new ModelAndView();
        String className = studentClass.getClassName();
        StudentClass isExist = classService.queryIsExist(className);
        LOGGER.info("查询重复班级的值{}", isExist);
        System.out.println("**********" + StringUtils.isEmpty(isExist));
        if (StringUtils.isEmpty(isExist)) {
            int i = classService.addStuClass(studentClass);
            if (i > 0) {
                modelAndView.addObject("addClass", "添加成功");
                //modelAndView.setViewName("class/addStuClass");
                modelAndView.setViewName("redirect:classList");
            }
            if (i < 0) {
                modelAndView.addObject("addClass", "添加失败！");
                modelAndView.setViewName("error/error");
            }
        } else {
            modelAndView.addObject("addClass", "该班级已经存在");
            modelAndView.setViewName("class/addStuClass");
        }

        return modelAndView;
    }
    /**
     * 查询信息用于页面展示
     * */
    @GetMapping("/updateStuClass/{id}")
    public ModelAndView queryStuClassById(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        StudentClass studentClass=new StudentClass();
        studentClass.setId(id);
        StudentClass queryResult = classService.queryStuClassById(studentClass);
        LOGGER.info("根据id查询出的班级值{}",queryResult);
        modelAndView.addObject("queryResult",queryResult);
        modelAndView.setViewName("class/updateStuClass");
        return modelAndView;
    }

    /**
     * 修改信息后保存到数据库
     * */
    @PostMapping("/saveStuClass")
    public ModelAndView updateStuClassInformation(StudentClass studentClass) {
        LOGGER.info("接收班级修改的值{}",studentClass);
        ModelAndView modelAndView = new ModelAndView();
        classService.updateStuClassInformation(studentClass);
        modelAndView.setViewName("redirect:classList");
        return modelAndView;
    }

    /**
     * 逻辑删除班级信息
     * */
    @ApiOperation("删除班级信息")
    @GetMapping("/delete/{id}")
    @ResponseBody
    public String deleteClass(@PathVariable Integer id) {
        StudentClass studentClass = new StudentClass();
        studentClass.setId(id);
        int delete = classService.deleteById(studentClass);
        LOGGER.info("student删除信息:" + delete);
        return delete > 0 ? DeleteCode.DELETE_SUCCESS.getMessage() : DeleteCode.DELETE_FAIL.getMessage();
    }
    @ApiOperation("通过班级名称查询该班级学生信息")
    @PostMapping(value = "/queryStudentByClassName")
    public ModelAndView queryStudentByClassName(@RequestParam String className){
        LOGGER.info("********接收的班级名字"+className);
        String AfterClassNameTrim = className.trim();//去除空格
        ModelAndView modelAndView = new ModelAndView();
        List<StudentClass> studentClasses = classService.queryStuClassAll();
        List<StudentClass> classQueryName =
                studentClasses.stream()
                        .filter(name -> name.getClassName().equals(AfterClassNameTrim))
                        .collect(Collectors.toList());
        if(classQueryName.size()==0){
            modelAndView.addObject("NotFound","班级名称"+"“"+AfterClassNameTrim+"“"+" 不存在请检查输入是否正确！");
            modelAndView.setViewName("class/classList::showStudent");
        }else {
            List<User> studentByClassName = userService.queryStudentByClassName(AfterClassNameTrim);
            if (CollectionUtils.isEmpty(studentByClassName)){
                modelAndView.addObject("NotFound","该班级暂无数据");
                modelAndView.setViewName("class/classList::showStudent");
            }
            modelAndView.addObject("class_stu",studentByClassName);
            //modelAndView.setViewName("class/showClassStu::showStudent");
            modelAndView.setViewName("class/classList::showStudent");
        }

        return modelAndView;
    }


    /**
     * 模糊查询班级信息
     */
    @ApiOperation("模糊查询班级信息")
    @PostMapping("/queryClass")
    public ModelAndView selectTeacherByPage( @RequestParam String className) {
        ModelAndView modelAndView = new ModelAndView();
        String afterClassNameTirm = className.trim();
        List<StudentClass> studentClasses = classService.queryClassByClassName(afterClassNameTirm);
        if (CollectionUtils.isEmpty(studentClasses)){
            modelAndView.addObject("NotFound","暂无数据，请检查输入是否正确");
        }else {
            modelAndView.addObject("queryStudentClasses",studentClasses);
        }
        modelAndView.setViewName("class/queryResult");
        return modelAndView;
    }
}
