<%-- 
    Document   : login.jsp
    Created on : 2013-12-10, 19:38:17
    Author     : fengchaoyi
--%>
<%@ page contentType="text/html"
         pageEncoding="UTF-8"
         import="java.util.*"
         import="bll.*"
         language="java"%>
<% //如果点击的是退出，则将session移除
   String type = request.getParameter("type");
   if(type!=null){
       if(type.equals("remove")){
           request.getSession().removeAttribute("user");
       }
   }
%>
<% //开始时将交管所信息和人员信息导入,若已存在，则 return -1
   UserOperator.addOfficeInfo();
   UserOperator.addPeopleIntoWorker();
%>

<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html lang="en">
<head>
<link rel="stylesheet" type="text/css" href="css/login.css" />
<script src="javascript/login.js"></script>

    <!-- Basic Page Needs
  ================================================== -->
    <meta charset="utf-8">
    <title>Login</title>
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Mobile Specific Metas
  ================================================== -->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <!-- CSS
  ================================================== -->

    <!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->
</head>
<body>
    <div class="login">
	<h1>驾照管理系统</h1>
    <form action="LoginServlet" name="logform" method="post">
    	<input type="text" name="logUsername" placeholder="Username" required="required" />
        <input type="password" name="logPassword" placeholder="Password" required="required" />
        <button type="submit" class="btn btn-primary btn-block btn-large">登陆</button>
    </form>
        <a href="register.jsp" class="btn btn-primary btn-block btn-large">注册</a>
    </div>
    <script class="cssdeck" src="javascript/jQuery.js"></script>
</body>

<% if(request.getSession().getAttribute("info") == "usernameWrong"){ %>
<script type="text/javascript" >
<!--
alert("用户名不存在！");
//-->
</script>
<% } else if(request.getSession().getAttribute("info") == "passwordWrong") {%>
<script type="text/javascript">
<!--
alert("密码错误！");
//-->
</script>
<% } %>

</html>
