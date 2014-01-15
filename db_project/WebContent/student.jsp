<%-- 
    Document   : student
    Created on : 2014-1-1, 22:24:14
    Author     : fengchaoyi
--%>

<%@ page contentType="text/html"
         pageEncoding="UTF-8"
         import="java.util.*"
         import="bll.*"
         language="java"%>
<% //得到登录进去的用户，并防止直接访问该网址
   Student student = (Student)request.getSession().getAttribute("user");
   if(student == null){
       response.sendRedirect("login.jsp");
       return;
   }
   
   String id_type = "未知";
   if(student.getID_Type() == 1){
       id_type = "身份证";
   }else if(student.getID_Type() == 2){
       id_type = "护照";
   }
%>
<!DOCTYPE html>
<html>
<script src="javascript/jQuery.js"></script>
<script>
$(document).ready(function(){
  $("#result").css("display","none");
  $("#but").click(function(){
    $("#result").toggle();
  });
});
</script>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>student</title>
        <style type="text/css">
        	#logOut:hover{
        		cursor:pointer;
        	}
        </style>
    </head>
    <body>
        <div align="right"  onclick="window.location.href='login.jsp?type=remove'" id="logOut">退出</div>
        <div align="center">欢迎使用本系统！</div>
        <br />
        
        <!--共四部分，第一部分为个人信息，第二部分为申请信息，第三部分为约考信息，第四部分为成绩信息-->
        <!--第一部分为个人信息-->
        <fieldset class="info">
    		<legend accesskey="p"><p>个人信息</p></legend>
    		<div>姓名：<%=student.getStu_Name() %></div>
    		<div>性别: <%=student.getStu_Gender() %></div>
    		<div>出生日期： <%=student.getBirthday() %></div>
    		<div>国籍：<%=student.getNationality() %></div>
    		<div>住址：<%=student.getHome_Address() %></div>
    		<div>证件类型：<%=id_type %></div>
    		<div>证件号码：<%=student.getID() %></div>
    		<div><button onclick="window.location.href='changeKey.jsp?username=<%=student.getID() %>'">修改密码</button></div>
    	</fieldset>
    	<br />
    	
    	<!--第二部分为申请信息-->
    	<fieldset class="info">
    		<legend accesskey="l"><p>申请信息</p></legend>
    		<% String apply_State = "";
    		   String appointment_State = "";
    		   if(student.getPass().equals("Y")){
    		       apply_State = "通过";
    		   }else {
    		       apply_State = "不通过";
    		   }
    		%>
    		<div>申请状态：<%=apply_State %></div>
    		<% if(apply_State.equals("不通过")){ %>
    		<div>原因：<%=student.getReject_Reason() %></div>
    		<% } %>
    		<div>申请时间：<%=student.getApply_Time() %></div>
    		<div>审核人员ID：<%=student.getWorker_ID() %></div>
    	</fieldset>
    	<br />
    	
    	<!--第三部分为约考信息-->
    	<% if(apply_State.equals("通过")) {%>
    	<fieldset class="info">
    		<legend accesskey="l"><p>约考信息</p></legend>
    		<% appointment_State = UserOperator.getPass(student.getID());
    		   if(appointment_State==null){
    		       appointment_State = "未约考";
    		   }else{
    		   	   if(appointment_State.equals("Y")){
    		           appointment_State = "通过";
    		       }else {
    		           appointment_State = "不通过";
    		       }
    		   }
    		   
    		%>
    		<div>约考状态：<%=appointment_State %></div>
    		<% if(appointment_State.equals("不通过")) {%>
    		<div>原因：<%=UserOperator.getReason(student.getID()) %></div>
    		<% } %>
    	</fieldset>
    	<br />
    	<% } %>
    	
    	<!--第四部分为成绩信息-->
    	<% if(appointment_State.equals("通过")) { %>
    	<button id="but">查看成绩</button>
    	<div id="result">
    		<fieldset class="info">
    			<legend accesskey="l"><p>成绩信息</p></legend>
    			<div>申请状态</div>
    		</fieldset>
    	</div>
    	<% } %>
    </body>
</html>
