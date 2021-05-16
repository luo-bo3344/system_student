<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%

    String path = request.getContextPath();

    String basePath = request.getScheme() + "://" +

            request.getServerName() + ":" + request.getServerPort() + path + "/";

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>登录</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/login/css/pintuer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/login/css/admin.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/login/js/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/login/js/pintuer.js"></script>
    <link href="<%=basePath%>static/css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script type="text/javascript" src="<%=basePath%>static/js/jquery-1.11.3.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script type="text/javascript" src="<%=basePath%>static/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        //切换验证码
        function refreshCode() {
            //1.获取验证码图片对象
            var vcode = document.getElementById("vcode");
            //2.设置其src属性，加时间戳
            vcode.src = "<%=basePath%>checkCodeServlet/doCheckCode?time=" + new Date().getTime();
        }
    </script>
    <%--背景图--%>
    <style>
        body {
            background: url("<%=basePath%>static/images/aisi.jpg") top left;
            background-size: 100%;
        }
    </style>
    <%--字体倾斜--%>
    <style>
        .divcss5_italic {
            font-style: italic
        }

        .divcss5_oblique {
            font-style: oblique
        }
    </style>
</head>
<body>


<%--
<form action="">
    <p id="errorMessage"></p>
    账号：<input id="userName" type="text" name="userName">
    <br>
    密码：<input id="pwd" type="password" name="password">
    <br>
    <label for="vcode">验证码：</label>
    <input type="text" name="verifycode" class="form-control" id="code" placeholder="请输入验证码" style="width: 120px;"/>
    <a href="javascript:refreshCode();">
        <img src="<%=basePath%>checkCodeServlet/doCheckCode" title="看不清点击刷新" id="vcode"/>
    </a>
    <br>
    <input type="radio" id="Director" name="Teacher" value="Director" checked="checked">主任
    <input type="radio" id="Teacher" name="Teacher" value="Teacher">教师
    <input type="radio" id="Melody" name="Teacher" value="Melody">班主任
    <br>
    <button type="button" onclick="login()">登录</button>
    <button type="reset">重置</button>
</form>
--%>


<div class="line bouncein">
    <div class="xs6 xm4 xs3-move xm4-move">
        <div style="height:150px;"></div>
        <div class="media media-y margin-big-bottom">
        </div>
        <form method="">
            <div class="panel login">
                <h1 class="divcss5_oblique " style="text-align: center ;">用户/<small class="divcss5_italic">login</small>
                </h1>

                <div class="panel-body" style="padding: 10px 30px;">
                    <div class="form-group">
                        <div class="field field-icon-right">
                            <p id="errorMessage" style="color:red"></p>
                            <input type="text" class="input input-big" name="teacherNum" id="teacherNum"
                                   placeholder="登录账号" data-validate="required:请填写账号"/>
                            <span class="icon icon-user margin-small"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="field field-icon-right">
                            <input type="password" class="input input-big" name="password" id="password"
                                   placeholder="登录密码" data-validate="required:请填写密码"/>
                            <span class="icon icon-key margin-small"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="field">
                            <input type="text" class="input input-big" name="verifycode" id="code"
                                   placeholder="填写右侧的验证码" data-validate="required:请填写右侧的验证码"/>
                            <img src="<%=basePath%>checkCodeServlet/doCheckCode" alt="" width="80" height="32"
                                 class="passcode" style="height:43px;cursor:pointer;" onclick="this.src=this.src+'?'">
                        </div>
                    </div>
                </div>
                                <div style="padding:30px;" align="center">
                                    <input type="radio" id="Director" name="Teacher" value="Director" checked="checked">主任
                                    <input type="radio" id="Teacher" name="Teacher" value="Teacher">教师
                                    <input type="radio" id="Melody" name="Teacher" value="Melody">班主任
                                </div>
                <div style="padding:30px;">
                    <input type="button" class="button button-block  text-big input-big" onclick="login()" value="登录">
                    <input type="reset" class="button button-block  text-big input-big" style="margin-top: 10px"
                           value="取消">
                </div>
            </div>
        </form>
    </div>
</div>
</div>

<script type="text/javascript">
    function login() {
        var teacherNum = $("[name='teacherNum']").val();//获取输入的账号
        var password = $("[name='password']").val();//获取输入的密码
        var verifycode = $("[name='verifycode']").val();//获取输入的验证码
        //通过元素列表的名字获取元素列表
        var obj = document.getElementsByName("Teacher");
        //传参到后台
        for(var i=0; i<obj.length; i ++) {
            if (obj[i].checked) {
                var choose = obj[i].value;
            }
        }
        $.ajax({
            url: "<%=basePath%>sys/login",
            type: "post",
            data: {
                "password": password,
                "verifycode": verifycode,
                "teacherNum": teacherNum,
                "choose":choose,
            },
            success: function (data) {
                console.log(data);
                if (data.status == 200) {
                    if (choose == "Director"&&data.message == "教务主任") {
                        location.href = "<%=basePath%>sys/index";
                    } else if (choose == "Teacher"&&data.message == "教师") {
                        location.href = "<%=basePath%>jsp/teacher.jsp";
                    } else if (choose == "Melody"&&data.message == "班主任") {
                        location.href = "<%=basePath%>jsp/melody.jsp";
                    }else {
                        $("#errorMessage").text("身份选择不符");

                    }
                }
                    else {
                        $("#errorMessage").text(data.message);
                }
            },
            error: function (data) {
                $("#errorMessage").text(data.responseText);
            }
        });
    }
</script>

</body>
<%--<script type="text/javascript">
    function login() {
        var userName = $("[name='userName']").val();//获取输入的账号
        var userPwd = $("[name='password']").val();//获取输入的密码
        var verifycode = $("[name='verifycode']").val();//获取输入的验证码
        $.ajax({
            url: "<%=basePath%>sys/login",
            type: "post",
            data: {"userName": userName, "password": userPwd, "verifycode": verifycode},
            success: function (data) {
                console.log(data);
                if (data.status == 200) {
                    location.href = "<%=basePath%>sys/index";
                } else {
                    $("#errorMessage").text(data.message);
                }
            },
            error: function (data) {
                $("#errorMessage").text(data.responseText);
            }
        });
    }


</script>--%>

</html>