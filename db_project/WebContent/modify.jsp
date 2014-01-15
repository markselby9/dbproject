<%@ page contentType="text/html"
         pageEncoding="UTF-8"
         import="java.util.*"
         import="bll.*"
         language="java"%>
<% //得到登录进去的用户，并防止直接访问该网址
   Worker admin = (Worker)request.getSession().getAttribute("user");
   if(admin == null){
       response.sendRedirect("login.jsp");
       return;
   }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Modify</title>
</head>
<body>
  <%
  String id = request.getParameter("id");
  Worker person = UserOperator.getOnePerson(Integer.parseInt(id));
  %>
  <div>请输入您要修改的内容，完毕后点击提交按钮</div>
  <form method="post" action="ModifyServlet?id=<%=id%>" name="modform">
    <div>
      <span>姓名：</span>
      <span><%=person.getWorker_Name() %></span>
    </div>
    <div>
      <span>所属交管所：</span>
      <input type="text" name="office" id="office" value="<%=person.getOffice_ID()%>">
    </div>
    <div>
      <span>职务：</span>
      <input type="text" name="job" id="job" value="<%=person.getJob()%>">
    </div>
    <div>
      <input type="submit" value="提交">
    </div>
  </form>
</body>
</html>