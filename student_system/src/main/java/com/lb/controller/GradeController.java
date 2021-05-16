package com.lb.controller;

import com.google.common.collect.Lists;
import com.lb.entity.Course;
import com.lb.entity.Grade;
import com.lb.entity.StudentClass;
import com.lb.entity.User;
import com.lb.mapper.CourseAndClassNameVoMapper;
import com.lb.mapper.CourseClassVoMapper;
import com.lb.mapper.mappervo.CourseAndClassNameVo;
import com.lb.service.CourseService;
import com.lb.service.GradeService;
import com.lb.mapper.mappervo.CourseClassVo;
import com.lb.service.StudentClassService;
import com.lb.service.StudentService;
import com.lb.utils.DeleteCode;
import com.lb.vo.GradeVo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luobo
 * @date 2021-04-26 16:47
 * @description 学生成绩管理
 */
@Controller
@RequestMapping("/grade")
public class GradeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GradeController.class);
    @Autowired
    private GradeService gradeService;
    //由于时间原因直接从mapper拿数据了。
    @Autowired
    private CourseClassVoMapper courseClassVoMapper;
    @Autowired
    private CourseAndClassNameVoMapper courseAndClassNameVoMapper;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentClassService studentClassService;

    @ApiOperation("用于显示学生成绩教师所拥有的的课程")
    @GetMapping("/showCourseName")
    public ModelAndView showCourseName(HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView();
        //获取用户姓名
        String username = (String) req.getSession().getAttribute("username");
        List<CourseClassVo> courseClassVos = courseClassVoMapper.queryTeacherWithCourseAndStuClass(username);
        List<StringBuilder> showCourseNameAndClassName = Lists.newArrayList();
        for (CourseClassVo courseClassVo : courseClassVos) {
            StringBuilder courseNameAndClassName = new StringBuilder();
            courseNameAndClassName.append(courseClassVo.getCourseName());
            courseNameAndClassName.append(":");
            courseNameAndClassName.append(courseClassVo.getClassName());
            showCourseNameAndClassName.add(courseNameAndClassName);
        }
        modelAndView.addObject("showCourseNameAndClassName", showCourseNameAndClassName);
        modelAndView.setViewName("grade/gradeTeacher");
        return modelAndView;
    }

    @ApiOperation("通过班级名称查询该班级成绩")
    @GetMapping(value = "/queryGradeList")
    public ModelAndView queryCourseByClassName(@RequestParam String courseNameAndClassName) {
        LOGGER.info("********接收查询课程的名字" + courseNameAndClassName);
        ModelAndView modelAndView = new ModelAndView();
        String AfterClassNameTrim = courseNameAndClassName.trim();
        //截取字符串转化为数组，数组转化为集合最后获取班级名称和课程名称
        String[] courseAndClass = AfterClassNameTrim.split(":");
        List<String> courseAndClassList = Arrays.asList(courseAndClass);
        String courseName = courseAndClassList.get(0);
        String className = courseAndClassList.get(1);
        CourseAndClassNameVo courseAndClassNameVo =
                courseAndClassNameVoMapper.queryIdsByCourseAndClassName1(courseName, className);
        List<Grade> grades = gradeService.queryGradeByCourseAndClass(courseAndClassNameVo.getCourseId(),
                courseAndClassNameVo.getClassId());
        if (CollectionUtils.isEmpty(grades)){
            modelAndView.addObject("NotFound", "暂无数据");
            modelAndView.setViewName("grade/gradeTeacher::showCourseGrade");
        }
        List<GradeVo> gradeVos = Lists.newArrayList();

        for (Grade grade : grades) {
            Integer studentId = grade.getStudentId();
            Integer courseId = grade.getCourseId();
            //获取学生名字
            User user = studentService.queryStudentNameById(studentId);
            String gradeStudentName = user.getName();
            //获取班级名称
            Course course = courseService.queryCourseNameById(courseId);
            String gradeCourseName = course.getCourseName();
            //获取班级名称
            StudentClass studentClass = studentClassService.queryStudentClassNameById(grade.getClassId());
            String gradeClassName = studentClass.getClassName();
            //将查询出来的数据库表实体转换传到前端页面
            GradeVo gradeVo = new GradeVo();
            gradeVo.setId(grade.getId());
            gradeVo.setStudentName(gradeStudentName);
            gradeVo.setCourseName(gradeCourseName);
            gradeVo.setClassName(gradeClassName);
            gradeVo.setGrade(grade.getGrade());
            gradeVo.setUpdateTime(grade.getUpdateTime());
            gradeVo.setStatus(grade.getStatus());
            //转换的添加集合
            gradeVos.add(gradeVo);
        }
        modelAndView.addObject("gradeVos", gradeVos);
        modelAndView.setViewName("grade/gradeTeacher::showCourseGrade");
        return modelAndView;
    }

    @ApiOperation("根据ID查询学生的成绩信息")
    @GetMapping("/updateGrade/{id}")
    public ModelAndView updateCourseQuery(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Grade grade = new Grade();
        grade.setId(id);
        Grade editGrade = gradeService.queryGradeById(grade);
        Integer studentId = editGrade.getStudentId();
        Integer courseId = editGrade.getCourseId();
        Integer classId = editGrade.getClassId();
        //获取学生名字
        User user = studentService.queryStudentNameById(studentId);
        String gradeStudentName = user.getName();
        //获取班级名称
        Course course = courseService.queryCourseNameById(courseId);
        String gradeCourseName = course.getCourseName();
        //获取班级名称
        StudentClass studentClass = studentClassService.queryStudentClassNameById(classId);
        String gradeClassName = studentClass.getClassName();
        //将查询出来的数据库表实体转换传到前端页面
        GradeVo gradeVo = new GradeVo();
        gradeVo.setId(editGrade.getId());
        gradeVo.setStudentName(gradeStudentName);
        gradeVo.setCourseName(gradeCourseName);
        gradeVo.setClassName(gradeClassName);
        gradeVo.setGrade(editGrade.getGrade());
        gradeVo.setUpdateTime(editGrade.getUpdateTime());
        gradeVo.setStatus(editGrade.getStatus());
        modelAndView.addObject("gradeVo", gradeVo);
        modelAndView.setViewName("grade/addGrade");
        //查询学生的信息
        return modelAndView;
    }


    //不修改信息直接返回
    @GetMapping("/returnToList")
    public String returnToList() {
        return "redirect:showCourseName";
    }

    @ApiOperation("学生成绩添加")
    @PostMapping("/addGrade")
    public ModelAndView addGrade(GradeVo gradeVo) {
        ModelAndView modelAndView = new ModelAndView();
        Grade grade = new Grade();
        grade.setId(gradeVo.getId());
        grade.setGrade(gradeVo.getGrade());
        grade.setUpdateTime(new Date());
        gradeService.addGradeById(grade);
        modelAndView.setViewName("redirect:showCourseName");
        return modelAndView;
    }

    @ApiOperation("删除学生成绩信息")
    @GetMapping("/delete/{id}")
    @ResponseBody
    public String deleteCourse(@PathVariable Integer id) {
        Grade grade = new Grade();
        grade.setId(id);
        int delete = gradeService.deleteGradeById(grade);
        return delete > 0 ? DeleteCode.DELETE_SUCCESS.getMessage() : DeleteCode.DELETE_FAIL.getMessage();
    }

    @ApiOperation("学生查看成绩(只针对学生)")
    @GetMapping("/showStudentGrade")
    public ModelAndView showStudentGrade(HttpServletRequest req){
        ModelAndView modelAndView = new ModelAndView();
        String name = (String) req.getSession().getAttribute("username");
        List<Grade> grades = gradeService.queryStudentGradeByStudentName(name);
        if (CollectionUtils.isEmpty(grades)){
            modelAndView.addObject("NotFound", "暂无数据");
            modelAndView.setViewName("grade/gradeStudent");
        }
        List<GradeVo> StudentGradeVos = Lists.newArrayList();
        for (Grade grade : grades) {
            Integer studentId = grade.getStudentId();
            Integer courseId = grade.getCourseId();
            //获取学生名字
            User user = studentService.queryStudentNameById(studentId);
            String gradeStudentName = user.getName();
            //获取班级名称
            Course course = courseService.queryCourseNameById(courseId);
            String gradeCourseName = course.getCourseName();
            //获取班级名称
            StudentClass studentClass = studentClassService.queryStudentClassNameById(grade.getClassId());
            String gradeClassName = studentClass.getClassName();
            //将查询出来的数据库表实体转换传到前端页面
            GradeVo gradeVo = new GradeVo();
            gradeVo.setId(grade.getId());
            gradeVo.setStudentName(gradeStudentName);
            gradeVo.setCourseName(gradeCourseName);
            gradeVo.setClassName(gradeClassName);
            gradeVo.setGrade(grade.getGrade());
            gradeVo.setUpdateTime(grade.getUpdateTime());
            gradeVo.setStatus(grade.getStatus());
            //转换的添加集合
            StudentGradeVos.add(gradeVo);
        }
        modelAndView.addObject("StudentGradeVos",StudentGradeVos);
        modelAndView.setViewName("grade/gradeStudent");
        return modelAndView;

    }

}
