<%-- 
    Document   : ShowLicence
    Created on : 2014-1-3, 13:54:07
    Author     : fengchaoyi
--%>

<%@ page contentType="text/html"
         pageEncoding="UTF-8"
         import="java.util.*"
         import="bll.*"
         language="java"%>
<% //得到登录进去的用户，并防止直接访问该网址
   Licence licence = (Licence)request.getSession().getAttribute("user");
   if(licence == null){
       response.sendRedirect("login.jsp");
       return;
   }
   String id_type = "未知";
   if(licence.getID_Type() == 1){
       id_type = "身份证";
   }else if(licence.getID_Type() == 2){
       id_type = "护照";
   }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>showLicence</title>
        <style type="text/css">
        	#logOut:hover{
        		cursor:pointer;
        	}
        </style>
    </head>
    <body>
    	<div align="right"  onclick="window.location.href='login.jsp?type=remove'" id="logOut">退出</div>
        <div align="center">恭喜！您现在拥有一个合格的驾照！</div>
        <br />
        <fieldset class="info">
    		<legend accesskey="p"><p>个人信息</p></legend>
    		<div>姓名：<%=licence.getStu_Name() %></div>
    		<div>性别: <%=licence.getStu_Gender() %></div>
    		<div>出生日期： <%=licence.getBirthday() %></div>
    		<div>国籍：<%=licence.getNationality() %></div>
    		<div>住址：<%=licence.gethome_Address() %></div>
    		<div>证件类型：<%=id_type %></div>
    		<div>证件号码：<%=licence.getID() %></div>
    		<div>照片：<br /><img id="myhead" alt="我的照片" src=<%=licence.getphoto_Address() %> /></div>
    		<div><button onclick="window.location.href='changeKey.jsp?username=<%=licence.getID() %>'">修改密码</button></div>
    	</fieldset>
    	<br />
    	<fieldset class="info">
    		<legend accesskey="l"><p>驾照信息</p></legend>
    		<div><b>驾照号：<%=licence.getLicence_ID() %></b></div>
    		<div>颁证交管所：<%=licence.getOffice_ID() %></div>
    		<div>领证时间：<%=licence.getget_Licence_Time() %></div>
    		<div>准考车型：<%=licence.getallowed_Car_Type() %></div>
    		<div>有效期限：<%=licence.getvalid_Time() %></div>
    		<div>剩余分数（满分12分）：<%=licence.getScore() %></div>
    	</fieldset>
    </body>
</html>
