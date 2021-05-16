package com.lb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lb.entity.Attendance;
import com.lb.entity.Course;
import com.lb.entity.StudentClass;
import com.lb.entity.User;
import com.lb.service.AttendanceService;
import com.lb.service.StudentClassService;
import com.lb.service.StudentService;
import com.lb.utils.DeleteCode;
import com.lb.vo.AttendanceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luobo
 * @date 2021-04-22 16:34
 * @description
 */
@Api(tags = "学生考勤")
@RequestMapping("/attendance")
@Controller
public class AttendanceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttendanceController.class);
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentClassService classService;

    @ApiOperation("考勤信息列表")
    @GetMapping("/attendanceList")
    public ModelAndView courseList(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                                   @RequestParam(defaultValue = "8", value = "pageSize") Integer pageSize) {
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        PageHelper.startPage(pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        List<Attendance> attendances = attendanceService.queryAllAttendance();
        PageInfo<Attendance> pageInfo = new PageInfo<Attendance>(attendances, pageSize);
        modelAndView.addObject("attendances", attendances);
        modelAndView.addObject("pageInfo", pageInfo);
        modelAndView.setViewName("attendance/attendanceList");
        return modelAndView;
    }

    @ApiOperation("添加学生考勤路由跳转")
    @GetMapping("/addAttendance")
    public ModelAndView addAttendance() {
        ModelAndView modelAndView = new ModelAndView();
        List<User> student = studentService.queryStudentAll();
        List<String> studentName = student
                .stream()
                .map(User::getName)
                .collect(Collectors.toList());
        modelAndView.addObject("studentName", studentName);
        modelAndView.setViewName("/attendance/addAttendance");
        return modelAndView;
    }

    //不添加信息直接返回列表
    @GetMapping("/returnToList")
    public String returnToList() {
        return "redirect:attendanceList";
    }

    @ApiOperation("添加学生考勤信息")
    @PostMapping("/addAttendance")
    public ModelAndView addAttendanceInfo(AttendanceVo attendanceVo) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();
        User user = studentService.queryStudentIdByStudentName(attendanceVo.getName());
        if (StringUtils.isEmpty(user)){
            modelAndView.addObject("info","attendance");
            modelAndView.addObject("attendanceInfo","学生"+attendanceVo.getName()+"不存在！请检查选择或输入是否正确！");
            modelAndView.setViewName("error/error");
        }else {
            Attendance attendance = new Attendance();
            attendance.setStudentId(user.getId());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            attendance.setDescription(attendanceVo.getDescription());
            Date attendanceDate = simpleDateFormat.parse(attendanceVo.getAttendanceDate());
            attendance.setAttendanceDate(attendanceDate);
            attendanceService.addAttendance(attendance, attendanceVo.getName());
            modelAndView.setViewName("redirect:attendanceList");
        }
        return modelAndView;
    }

    @ApiOperation("删除学生考勤信息")
    @GetMapping("/delete/{id}")
    @ResponseBody
    public String deleteAttendance(@PathVariable Integer id) {
        Attendance attendance = new Attendance();
        attendance.setId(id);
        int delete = attendanceService.deleteAttendanceById(attendance);
        LOGGER.info("attendance信息删除:" + delete);
        return delete > 0 ? DeleteCode.DELETE_SUCCESS.getMessage() : DeleteCode.DELETE_FAIL.getMessage();
    }

    @ApiOperation("通过班级名称查询该班级考勤信息")
    @PostMapping(value = "/queryAttendanceByClassName")
    public ModelAndView queryAttendanceByClassName(@RequestParam String className) {
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
            modelAndView.setViewName("attendance/attendanceList::showAttendance");
        } else {
            List<Attendance> queryAttendance = attendanceService.queryAttendanceByClassName(AfterClassNameTrim);
            if (CollectionUtils.isEmpty(queryAttendance)) {
                modelAndView.addObject("NotFound", "暂无数据");
                modelAndView.setViewName("attendance/attendanceList::showAttendance");
            }
            modelAndView.addObject("queryAttendance", queryAttendance);
            modelAndView.setViewName("attendance/attendanceList::showAttendance");
        }
        return modelAndView;
    }


    /**
     * 模糊查询学生考勤信息
     */
    @ApiOperation("模糊查询学生信息")
    @PostMapping("/queryAttendance")
    public ModelAndView selectTeacherByPage( @RequestParam String name) {
        ModelAndView modelAndView = new ModelAndView();
        String afterNameTrim = name.trim();
        List<Attendance> attendances = attendanceService.queryAttendanceByStuName(afterNameTrim);
        if (CollectionUtils.isEmpty(attendances)){
            modelAndView.addObject("NotFound","暂无数据，请检查输入是否正确");
        }else {
            modelAndView.addObject("queryAttendance",attendances);
        }
        modelAndView.setViewName("attendance/queryResult");
        return modelAndView;
    }
}
