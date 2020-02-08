<%--
  Created by IntelliJ IDEA.
  User: 13698
  Date: 2019/12/30
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>APP开发者平台</title>

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath }/statics/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="${pageContext.request.contextPath }/statics/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="${pageContext.request.contextPath }/statics/css/nprogress.css" rel="stylesheet">
    <!-- Animate.css -->
    <%--<link href="https://colorlib.com/polygon/gentelella/css/animate.min.css" rel="stylesheet">--%>
    <!-- Custom Theme Style -->
    <link href="statics/css/custom.min.css" rel="stylesheet">

    <%--jquery--%>
    <%--<script src="statics/jquery-1.12.4.min.js"></script>--%>

    <script src="statics/js/jquery.min.js"></script>

    <script type="text/javascript">
        $(function () {
           $('#devName').blur(function () {
               var username = $.trim($('#devName').val());
               $.ajax({
                   type:'post',
                   url:'checkUserName.do',
                   data:{
                       devName:username
                   },
                   dataType:'text',
                   success:function (data) {
                       if (data === 'ok') {
                           $('#username_tishi').html("用户名可用").css("color","green");
                       } else {
                           $('#username_tishi').html("用户名不可用").css("color","red");
                       }
                   }
               })
           })
        })
    </script>
</head>

<body class="login">
<div>
    <a class="hiddenanchor" id="signup"></a>
    <a class="hiddenanchor" id="signin"></a>

    <div class="login_wrapper">
        <div class="animate form login_form">
            <section class="login_content">
                <form:form action="register.do" method="post">
                    <h1>APP开发者平台注册页面</h1>
                    <div>
                        <input id="devName" type="text" class="form-control" name="devName" placeholder="请输入用户名" required="" />
                    </div>
                    <div>
                        <input type="password" class="form-control" name="devPassword" placeholder="请输入密码" required="" />
                    </div>
                    <div>
                        <span id="username_tishi"></span>
                    </div>
                    <div>
                        <button type="submit" class="btn btn-success">提     交</button>
                        <button type="reset" class="btn btn-default">重　填</button>
                    </div>

                    <div class="clearfix"></div>

                    <div class="separator">
                        <div>
                            <p>©2019 All Rights Reserved. </p>
                        </div>
                    </div>
                </form:form>
            </section>
        </div>
    </div>
</div>
</body>
</html>
