<%@ page contentType="text/html; charset=utf-8"
         pageEncoding="GBK"
         import="java.util.*"
         import="bll.*"
         language="java"%>
<% //得到要改密码的username，并判断是学生还是工作人员
   String username = request.getParameter("username");
   //说明是工作人员,防止直接访问该网址
   if(username != null){
       if(username.length()<=4){
           Worker worker = (Worker)request.getSession().getAttribute("user");
           if(worker == null){
               response.sendRedirect("login.jsp");
               return;
           }
       }
       //说明是考生,防止直接访问该网址
       else {
           Student student = (Student)request.getSession().getAttribute("user");
           if(student == null){
               response.sendRedirect("login.jsp");
               return;
           }
       }
   }else{
       response.sendRedirect("login.jsp");
       return;
   }
%>
<% //得到修改信息
   String state = request.getParameter("state");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>changeKey</title>
<script language="javascript"  charset="UTF-8" src="javascript/changeKey.js"></script>
</head>
<body>
	<div>请输入您的密码和新密码，再次确认后点击提交按钮</div>
	<br />
  	<form method="post" action="ChangeKeyServlet?username=<%=username%>" name="modform">
    	<div>
      		<span>旧密码：</span>
      		<input type="text" name="old" id="old">
    	</div>
    	<div>
      		<span>新密码：</span>
      		<input type="text" name="new" id="new">
    	</div>
    	<div>
      		<span>再次确认：</span>
      		<input type="text" name="confirm" id="confirm"><span class="alert" id="alert"></span>
    	</div>
    </form>
    	<div>
    		<button onclick="check()">提交</button>
    	</div>
    	<br />
    	<% if(state != null) {
    	       if(state.equals("right")){
    	%>
    	<div>修改成功!</div>
    	<div><a href="login.jsp?type=remove">返回登录页面</a></div>
    	<%     }else { %>
    	<div>旧密码错误!请重新输入!</div>
    	<%     } %> 
    	<% } %>
  	
</body>
</html>