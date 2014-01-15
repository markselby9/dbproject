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
<title>register</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
  <div>请输入新来人员的相关信息！</div>
  <form method="post" action="RegisterServlet" name="regform">
    <div>
      <span>姓名：</span>
      <input type="text" name="name" id="name">
    </div>
    <div>
      <span>职务：</span>
      <span>交管所管理员</span>
    </div>
    <div>
      <span>所属交管所：</span>
      <input type="text" name="office" id="office">
    </div>
    <div>
      <button type="submit" id="go">提交</button>
    </div>
  </form>
</body>
</html>