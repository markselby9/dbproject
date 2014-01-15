<%--  
    Document   : admin
    Created on : 2013-12-22, 15:55:51
    Author     : FengChaoyi & WangYuanbin
--%>

<%@ page contentType="text/html"
         pageEncoding="UTF-8"
         import="java.util.*"
         import="bll.*"
         language="java"%>
<% 
     String path = request.getContextPath(); 
     String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
%>
<% //得到登录进去的用户，并防止直接访问该网址
   Worker admin = (Worker)request.getSession().getAttribute("user");
   if(admin == null){
       response.sendRedirect("login.jsp");
       return;
   }
%>
<% //如果有id，则说明有人被删除
   String id = request.getParameter("delete");
   if(id != null){
       UserOperator.deletePeople(Integer.parseInt(id));
   }
%>
<% //得到该管理员所管理的office中的人员信息
   List<Worker> workers = UserOperator.getAllWorkers();
%>
<%
   //上传状态
   String uploadState = "";
   if (request.getParameter("upload") != null)
       uploadState = request.getParameter("upload");
   
   //导入状态
   String importState1 = "";
   String importState2 = "";
   if (request.getParameter("daoru") != null){
       if(request.getParameter("daoru").equals("success1")){
           importState1 = "success1";
           if(LicenceOperator.haveVioLicences()){
               importState2 = "attention2";
           }
       }else {
           importState2 = "success2";
           if(LicenceOperator.haveLicences()){
               importState1 = "attention1";
       	   }
       }
   }else {
       if(LicenceOperator.haveLicences()){
           importState1 = "attention1";
       }
       if(LicenceOperator.haveVioLicences()){
           importState2 = "attention2";
       }
   }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>administer</title>
        <style type="text/css">
        	#logOut:hover{
        		cursor:pointer;
        	}
        </style>
    </head>
    <body>
    	<!--共三部分，第一部分为个人信息，第二部分为导入操作，第三部分增改本交管所管理员-->
    	<div>
    		<div align="right" onclick="window.location.href='login.jsp?type=remove'" id="logOut">退出</div>
    		<div align="center">欢迎进入系统管理员页面！</div>
    		<br />
    		<!--第一部分为个人信息-->
    		<fieldset id="info">
    			<legend accesskey="i"><p>个人信息</p></legend>
    			<div>姓名：系统管理员</div>
    		</fieldset>
    		<br />
    		<!--第二部分为导入操作-->
    		<fieldset id="operation">
    			<legend accesskey="o"><p>导入操作</p></legend>
    			<div>
            		<form method="post" action="ImportForAdmin" enctype="multipart/form-data">
            			<div>系统初始化时先导入初始驾照信息和违章信息作为初始数据,需要excel2003版本(.xls格式)</div>
            			<br />
            			<div>驾照信息作为初始数据:  驾照.excel</div>
                		<input id="excel" type="file" name="jiazhao" /> <br />
                		<input type="submit" value="导入"/>
                		<% if(importState1.equals("success1")) { %>
                		<span>导入成功</span>
                		<% }else if(importState1.equals("attention1")){ %>
                		<span>已导入！</span>
                		<% }else { %>
                		<span>等待导入...</span>
                		<% } %>
            		</form>
            	</div>
            	<br />
            	<div>
            		<form method="post" action="ImportForAdmin" enctype="multipart/form-data">
            			<div>违章信息作为初始数据:  违规.excel</div>  
                		<input id="excel" type="file" name="weizhang" /> <br />
                		<input type="submit" value="导入"/>
                		<% if(importState2.equals("success2")) { %>
                		<span>导入成功</span>
                		<% }else if(importState2.equals("attention2")){ %>
                		<span>已导入！</span>
                		<% }else { %>
                		<span>等待导入...</span>
                		<% } %>
            		</form>
                </div>
    		</fieldset>
    		<br />
    		<!--第三部分为增改本交管所管理员操作-->
    		<fieldset id="operation">
    			<legend accesskey="o"><p>增改本交管所管理员</p></legend>
    			<!--添加-->
    			<div><input type="button" value="添加" onClick="window.location.href='register.jsp'"/></div>
    			<!--修改，删除-->
    			<table border="1px" cellpadding="1px" align="center">
  					<tr bgcolor="#FF6666">
    					<td><b>序号</b></td>
    					<td><b>姓名</b></td>
    					<td><b>所属交管所</b></td>
    					<td><b>职务</b></td>
    					<td><b>修改|删除</b></td>
  					</tr>
  					<%
  					for (Worker p : workers){
  					%>
  					<tr>
    					<td><%=p.getWorker_ID() %></td>
    					<td><%=p.getWorker_Name() %></td>
    					<td><%=p.getOffice_ID() %></td>
    					<td><%=p.getJob() %></td>
    					<td><a href="modify.jsp?id=<%=p.getWorker_ID()%>">修改</a>|<a href="admin.jsp?delete=<%=p.getWorker_ID()%>">删除</a></td>
  					</tr>
  					<%
  					}
  					%>
				</table>
    		</fieldset>
    	</div>
    </body>
	<% if(uploadState.equals("failed")){ %>
	<script type="text/javascript" language="javascript">
	<!--
	alert("导入失败！请选择正确文件！");
	//-->
	</script>
	<% } %>
</html>
