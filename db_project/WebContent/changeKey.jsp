<%@ page contentType="text/html; charset=utf-8"
         pageEncoding="GBK"
         import="java.util.*"
         import="bll.*"
         language="java"%>
<% //�õ�Ҫ�������username�����ж���ѧ�����ǹ�����Ա
   String username = request.getParameter("username");
   //˵���ǹ�����Ա,��ֱֹ�ӷ��ʸ���ַ
   if(username != null){
       if(username.length()<=4){
           Worker worker = (Worker)request.getSession().getAttribute("user");
           if(worker == null){
               response.sendRedirect("login.jsp");
               return;
           }
       }
       //˵���ǿ���,��ֱֹ�ӷ��ʸ���ַ
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
<% //�õ��޸���Ϣ
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
	<div>��������������������룬�ٴ�ȷ�Ϻ����ύ��ť</div>
	<br />
  	<form method="post" action="ChangeKeyServlet?username=<%=username%>" name="modform">
    	<div>
      		<span>�����룺</span>
      		<input type="text" name="old" id="old">
    	</div>
    	<div>
      		<span>�����룺</span>
      		<input type="text" name="new" id="new">
    	</div>
    	<div>
      		<span>�ٴ�ȷ�ϣ�</span>
      		<input type="text" name="confirm" id="confirm"><span class="alert" id="alert"></span>
    	</div>
    </form>
    	<div>
    		<button onclick="check()">�ύ</button>
    	</div>
    	<br />
    	<% if(state != null) {
    	       if(state.equals("right")){
    	%>
    	<div>�޸ĳɹ�!</div>
    	<div><a href="login.jsp?type=remove">���ص�¼ҳ��</a></div>
    	<%     }else { %>
    	<div>���������!����������!</div>
    	<%     } %> 
    	<% } %>
  	
</body>
</html>