package com.lb.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Api(tags = "用于集成swagger测试类")
@RequestMapping("/route")
@Controller
public class RouteController {

    @RequiresPermissions("")
    @ApiOperation("swagger测试")
    @RequestMapping(value = "/swagger",method = RequestMethod.POST)
    public String testSwagger(@ApiParam(value = "用户名",required = true) @RequestBody String name){
        return "毕业设计"+name+"集成swagger成功";
    }

    //登录页面
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    //测试页面
    @RequestMapping("/haha")
    public String index(){
        return "test/haha";
    }

    //主页面
    @RequestMapping("/index")
    public String index1(){
        return "test/index";
    }

    //主页面
    @RequestMapping("/admin")
    public String admin(){
        return "admin/index";
    }

    //主页面
    @RequestMapping("/login1")
    public String login1(){
        return "login1";
    }

    //教师列表测试页面
    @RequestMapping("/teacher")
    public String teacher(){
        return "test/teacherlist";
    }

    //测试教师新野添加页面
    @RequestMapping("/addteacher")
    public String addTeacher(){
        return "teacher/addTeacher";
    }

    @RequestMapping("/error")
    public String error(){
        return "error/error";
    }


}
