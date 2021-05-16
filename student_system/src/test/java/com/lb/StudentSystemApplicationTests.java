package com.lb;

import com.lb.controller.CourseController;
import com.lb.entity.*;
import com.lb.mapper.CourseAndClassNameVoMapper;
import com.lb.mapper.CourseClassVoMapper;
import com.lb.mapper.StudentClassMapper;
import com.lb.mapper.TeacherMapper;
import com.lb.mapper.mappervo.CourseAndClassNameVo;
import com.lb.service.*;

import com.lb.mapper.mappervo.CourseClassVo;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class StudentSystemApplicationTests {


   /* @Autowired
    private UserService userService;*/
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private PersonInfoService personInfoService;

    @Test
    void contextLoads() {
        String name = "student";
        User user = userService.loginByUsername(name);
        System.out.println(user);
    }

    @Test
    public void test() {
        System.out.println(userService.loginByUsername("student"));
    }

    @Test
    public void testTeacherAll() {
        List<Teacher> teachers = teacherService.queryTeacherAll();
        System.out.println(teachers);

        System.out.println(userService);
    }

    /**
     * 测试增加教师信息
     */
    @Test
    public void testAddTeacher() throws JSONException {
        User teacher = new User();
        teacher.setUsername("燕儿");
        teacher.setPassword("123456");
        teacher.setName("love");
        teacher.setAge(22);
        teacher.setSex("女");
        teacher.setBirthday(new Date());
        teacher.setAddress("中国");
        teacher.setPhone("12358497658");
        teacher.setCreateTime(new Date());
        teacher.setRelationship(2);
        int i = teacherService.insertTeacher(teacher);
        System.out.println("添加成功" + i);
    }

    @Test
    public void testQueryPersonInfo() {
        System.out.println(personInfoService.editPersonInformation("admin"));
    }

    @Autowired
    private CourseService courseService;

    @Test
    public void testQueryCourseAll() {
        System.out.println(courseService.queryCourseAll());
    }

    @Autowired
    private CourseController courseController;

    @Test
    public void testQueryCourseAllTeacherName() {
        System.out.println(courseController.queryTeacherNameAndClassName());
    }
    @Autowired
    private UserService userService;
    @Test
    public void queryStudentByClassName() {
        String className="一年级一班";
        List<User> studentByClassName = userService.queryStudentByClassName(className);
        for (User student : studentByClassName) {
            System.out.println(student);
        }
    }

    @Autowired
    private AuthorizationInfoService infoService;

    @Test
    public void queryAuthClassName() {
        AuthorizationInfoUser admin = infoService.queryUserForAuthorization("admin");
        System.out.println(admin);
    }

    @Autowired
    private AttendanceService attendanceService;

    @Test
    public void queryAllAttendance() {
        List<Attendance> attendances = attendanceService.queryAllAttendance();
        System.out.println(attendances);
    }

    @Autowired
    private StudentClassMapper studentClassMapper;

    @Test
    public void queryClassId() {
        StudentClass studentClass = studentClassMapper.queryClassIdByStuName("阿飞飞");
        System.out.println(studentClass);

    }

    @Autowired
    private CourseClassVoMapper courseClassVoMapper;

    @Test
    public void queryStuClassByCourseName() {
        List<CourseClassVo> name = courseClassVoMapper.queryTeacherWithCourseAndStuClass("test");
        System.out.println(name);

    }

    @Autowired
    private CourseAndClassNameVoMapper courseAndClassNameVoMapper;

    @Test
    public void queryCourseAndClassNameVoMapper() {
        CourseAndClassNameVo courseAndClassNameVo = courseAndClassNameVoMapper.queryIdsByCourseAndClassName("数学",
                "一年级一班");
        System.out.println(courseAndClassNameVo);

    }
}
