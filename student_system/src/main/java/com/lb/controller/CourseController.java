package com.lb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lb.entity.Course;
import com.lb.entity.StudentClass;
import com.lb.entity.User;
import com.lb.service.CourseService;
import com.lb.service.StudentClassService;
import com.lb.service.UserService;
import com.lb.utils.DeleteCode;
import com.lb.vo.CourseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luobo
 * @date 2021-04-05 21:47
 * @description
 */
@RequestMapping("/course")
@Controller
public class CourseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @Autowired
    private StudentClassService studentClassService;

    @Autowired
    private StudentClassService classService;

    @ApiOperation("课程信息列表")
    @GetMapping("/courseList")
    public ModelAndView courseList(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                                   @RequestParam(defaultValue = "8", value = "pageSize") Integer pageSize) {
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        PageHelper.startPage(pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        List<Course> courses = courseService.queryCourseAll();
        PageInfo<Course> pageInfo = new PageInfo<Course>(courses, pageSize);
        modelAndView.addObject("courses", courses);
        modelAndView.addObject("pageInfo", pageInfo);
        modelAndView.setViewName("course/courseList");
        return modelAndView;
    }


    @ApiOperation("查询教师名字下拉列表")
    @GetMapping("/addCourse")
    public ModelAndView queryTeacherNameAndClassName() {
        ModelAndView modelAndView = new ModelAndView();
        List<User> users = userService.queryTeacherName();
        List<String> teacherName = users.stream().map(User::getName).collect(Collectors.toList());
        //获取所有的班级信息
        List<StudentClass> studentClasses = studentClassService.queryStuClassAll();
        //获取所有的班级名称
        List<String> className = studentClasses
                .stream()
                .map(StudentClass::getClassName)
                .collect(Collectors.toList());
        modelAndView.addObject("className", className);
        modelAndView.addObject("teacherName", teacherName);
        modelAndView.setViewName("course/addCourse");
        return modelAndView;
    }

    @ApiOperation("添加课程信息到数据库")
    @PostMapping("/addCourseInfo")
    public ModelAndView addCourseInfo(CourseVo coursevo) {
        System.out.println(coursevo);
        ModelAndView modelAndView = new ModelAndView();
        Course course = new Course();
        User user = new User();
        user.setName(coursevo.getTeacherId());//获取教师名字
        List<User> teacherName = userService.queryTeacherName();
        List<User> result =
                teacherName
                        .stream()
                        .filter(name -> name.getName().equals(coursevo.getTeacherId()))
                        .collect(Collectors.toList());
        if (result.size() == 0) {
            modelAndView.addObject("NotFound", "该教师不存在请检查输入是否正确！");
            modelAndView.setViewName("course/addCourse");
        } else {
            int userId = userService.queryIdByTeacherName(user);//查询教师id
            course.setCourseName(coursevo.getCourseName());//获取课程名称
            course.setCourseDate(coursevo.getCourseDate());//获取上课时间
            course.setTeacherId(String.valueOf(userId));//转化为字符串
            course.setCreateTime(new Date());//课程创建时间
            int addResult = courseService.addCourseInfo(course, coursevo.getClassName());
            if (addResult > 0) {
                modelAndView.setViewName("redirect:courseList");
            } else {
                modelAndView.addObject("NotFound", "该班级不存在");
                modelAndView.setViewName("course/addCourse");
            }
        }
        return modelAndView;
    }

    //不修改信息直接返回
    @GetMapping("/returnToList")
    public String returnToList() {
        return "redirect:courseList";
    }

    @ApiOperation("根据ID查询教师的信息")
    @GetMapping("/updateCourse/{id}")
    public ModelAndView updateCourseQuery(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Course courseUpdate = courseService.queryCourseById1(id);
        LOGGER.info("查询的课程信息用于展示在页面{}", courseUpdate);
        List<User> users = userService.queryTeacherName();
        List<String> teacherName = users.stream().map(User::getName).collect(Collectors.toList());
        modelAndView.addObject("courseUpdate", courseUpdate);
        modelAndView.addObject("teacherName", teacherName);
        modelAndView.setViewName("course/updateCourse");
        return modelAndView;
    }

    @ApiOperation("修改课程信息,保存到数据库")
    @PostMapping("/updateCourseInformation")
    public ModelAndView updateStudentInformation(Course course) {
        LOGGER.info("接收修改课程的信息{},{}", course, course.getTeacherId());
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        user.setName(course.getTeacherId());
        int userId = userService.queryIdByTeacherName(user);
        course.setTeacherId(String.valueOf(userId));
        courseService.updateCourseInformation(course);

        modelAndView.setViewName("redirect:courseList");
        return modelAndView;
    }

    @ApiOperation("修改课程信息,保存到数据库")
    @GetMapping("/delete/{id}")
    @ResponseBody
    public String deleteCourse(@PathVariable Integer id) {
        Course course = new Course();
        course.setId(id);
        int delete = courseService.deleteById(course);
        LOGGER.info("course删除信息:" + delete);
        return delete > 0 ? DeleteCode.DELETE_SUCCESS.getMessage() : DeleteCode.DELETE_FAIL.getMessage();
    }

    @ApiOperation("通过班级名称查询该班级课表")
    @PostMapping(value = "/queryCourseByClassName")
    public ModelAndView queryCourseByClassName(@RequestParam String className) {
        LOGGER.info("********接收的班级名字" + className);
        String AfterClassNameTrim = className.trim();
        ModelAndView modelAndView = new ModelAndView();
        List<StudentClass> studentClasses = classService.queryStuClassAll();
        List<StudentClass> classQueryName =
                studentClasses.stream()
                        .filter(name -> name.getClassName().equals(AfterClassNameTrim))
                        .collect(Collectors.toList());
        if (classQueryName.size() == 0) {
            modelAndView.addObject("NotFound", "班级名称" + "“" + AfterClassNameTrim + "“" + " 不存在请检查输入是否正确！");
            modelAndView.setViewName("course/courseList::showCourse");
        } else {
            List<Course> courseList = courseService.queryClassCourseByClassName(AfterClassNameTrim);
            if (CollectionUtils.isEmpty(courseList)) {
                modelAndView.addObject("NotFound", "暂无数据");
                modelAndView.setViewName("course/courseList::showCourse");
            }
            modelAndView.addObject("courseList", courseList);
            //modelAndView.setViewName("class/showClassStu::showStudent");
            modelAndView.setViewName("course/courseList::showCourse");
        }
        return modelAndView;
    }

    /**
     * 模糊查询学生课程信息
     */
    @ApiOperation("模糊查询学生课程信息")
    @PostMapping("/queryCourse")
    public ModelAndView selectTeacherByPage( @RequestParam String courseName) {
        ModelAndView modelAndView = new ModelAndView();
        String afterCourseNameTrim = courseName.trim();
        List<Course> courses = courseService.queryCourseByCourseName(afterCourseNameTrim);
        if (CollectionUtils.isEmpty(courses)){
            modelAndView.addObject("NotFound","暂无数据，请检查输入是否正确");
        }else {
            modelAndView.addObject("queryCourse",courses);
        }
        modelAndView.setViewName("course/queryResult");
        return modelAndView;
    }
}
