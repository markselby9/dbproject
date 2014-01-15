<%-- 
    Document   : chengjidaoru
    Created on : 2013-12-28, 16:21:07
    Author     : fengchaoyi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
     String path = request.getContextPath(); 
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
%>
<%
    
    String success = "";
    if (request.getParameter("daoru") != null)
        success = request.getParameter("daoru");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>成绩导入</title>
    </head>
    <body>
        <div>
            <form method="post" action="importtestServlet" enctype="multipart/form-data">
            交管所管理员导入 考试科目时间excel
                <input id="excel" type="file" name="kaoshi" /> <br />
                <input type="submit" value="导入"/>
            </form>
             <%if (success.equals("success")){
                %>
                <p>状态：导入成功！</p>
            <%
            }
            else{
                %>
                <p>状态：等待导入</p>
            <%
            }
            %>
        </div>
        <div>
            <form method="post" action="importtestServlet" enctype="multipart/form-data">
            交管所管理员导入 约考excel（还没写）
                <input id="excel" type="file" name="约考" /> <br />
                <input type="submit" value="导入"/>
            </form>
        </div>
    </body>
</html>
