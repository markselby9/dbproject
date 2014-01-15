<%--  
    Document   : shenqing
    Created on : 2013-12-22, 15:55:51
    Author     : fengchaoyi
--%>

<%@ page contentType="text/html"
         pageEncoding="UTF-8"
         import="java.util.*"
         import="bll.*"
         language="java"%>
<% //得到登录进去的用户，并防止直接访问该网址
   Worker worker = (Worker)request.getSession().getAttribute("user");
   if(worker == null){
       response.sendRedirect("login.jsp");
       return;
   }
%>
<% Account acc = (Account)request.getSession().getAttribute("acc");
   ArrayList<Fenshu> res = (ArrayList<Fenshu>)request.getSession().getAttribute("res");
%>
<% //上传状态
   String uploadState = "";
   if (request.getParameter("upload") != null)
       uploadState = request.getParameter("upload");
%>
<%
    String str = "", nameset = "", idset = "";
    if (request.getParameter("daoru") != null)
        str = request.getParameter("daoru");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Worker</title>
        <style type="text/css">
        	#logOut:hover{
        		cursor:pointer;
        	}
        </style>
    </head>
    <body>
    	<div align="right"  onclick="window.location.href='login.jsp?type=remove'" id="logOut">退出</div>
        <div align="center">欢迎进入交管所系统！</div>
        <br />
        <!--个人信息部分-->
        <fieldset class="info">
    		<legend accesskey="p"><p>个人信息</p></legend>
    		<div>姓名：<%=worker.getWorker_Name() %></div>
    		<div>ID: <%=worker.getWorker_ID() %></div>
    		<div>所属交管所： <%=worker.getOffice_ID() %></div>
    		<div>职务：<%=worker.getJob() %></div>
    		<div><button onclick="window.location.href='changeKey.jsp?username=<%=worker.getWorker_ID() %>'">修改密码</button></div>
    	</fieldset>
    	<br />
    	
    	<!--如果是管理员-->
    	<% if(worker.getJob().equals("管理员")) {
    	       Office office = UserOperator.getOffice(worker.getWorker_ID());
    	%>
    	<!--交管所信息部分-->
        <fieldset class="info">
    		<legend accesskey="p"><p>交管所信息</p></legend>
    		<div>交管所ID： <%=office.getOffice_ID() %></div>
    		<div>地址： <%=office.getProvince()%>省 <%=office.getCity()%>市 <%=office.getDistrict()%>区 <%=office.getStreet()%>街</div>
    	</fieldset>
    	<br />
    	<!--可执行操作部分-->
    	<fieldset class="info">
    		<legend accesskey="p"><p>可执行操作</p></legend>
    		<form method="post" action="ImportForWorker" enctype="multipart/form-data">
    			<div>导入指定考试科目及时间 考试科目时间.xls：</div>
    			<span><input id="excel" type="file" name="kemushijian" /></span>
                <div><input type="submit" value="导入科目时间"/></div>
        	</form>
            <form method="post" action="ImportForWorker" enctype="multipart/form-data">
            	<div>导入约考时间 约考.xls：</div>
                <span><input id="excel" type="file" name="yuekao" /></span>
                <div><input type="submit" value="导入约考"/></div>
            </form>
            <%
            if (str.equals("success")){
            %>
                <div>导入成功！</div>
            <%
            }
            else{
            %>
                <div>等待导入</div>
            <%
            }
            if (request.getParameter("nameset") != null)
        	nameset = "叶小天才";
            
    		if (request.getParameter("idset") != null){
        	str = "chengji";
        	idset = "663277199204080028";}
            %>
            
            <div>==============================以下操作需导入约考信息之后再进行=====================================</div>
            <form method="post" action="YuekaoCheck" enctype="multipart/form-data">
            	<div>一个考生所有次数不得超过10次，月考时间距离申请成功时间不得超过3年。以下考生违反该条例：</div>
            	<span><input type="submit" value="一键检测！" /></span>
            </form>
            
            <% if (nameset.length() != 0){ %>
                <div><%= "发现 "+ nameset + "超过该限制，后台已经删除该考生，并且存入了系统记录信息。"%></div>
            <% } %>
            <div> 交管所管理员一键发证 </div>
            <form method="post" action="ToLicenceServlet" enctype="multipart/form-data">
            	<div>一键发证：</div>
            	<input type="submit" value="一键发证"/>
            </form>
           
            <div> 交管所管理员驾照记分功能 </div>
            <form method="post" action="Jiazhaojifen" enctype="multipart/form-data">
               驾照计分：
                <input type="submit" value="驾照记分"/>
           </form>
           <%
                   if (res!=null){
                       %>
            <table border="1">
                <tr>
            <td>扣分分值</td>
            <td>驾照号码</td>
            <td>驾照所属交通局</td>
            <td>扣分原因</td>
            <td>扣分时间</td>
            <td>剩余分数</td>
            </tr>
                <%
                       System.out.println("res.size = " + res.size());
                       Iterator it = res.iterator();
                       while (it.hasNext()){
                           Fenshu t = (Fenshu)it.next();
                           System.out.println(t.getKoufen() + " " + t.getLicenceID() + " " + t.getOfficeID() + " " + t.getReason());
                       }
                       for (int i = 0; i < res.size(); i++){
                           Fenshu t = res.get(i);
                           System.out.println(t.getKoufen() + " " + t.getLicenceID() + " " + t.getOfficeID() + " " + t.getReason());
                           String shengyu = t.getShengyufen();
                           if (Integer.parseInt(shengyu) <= 0){
                               shengyu = "喜被吊销";
                           }
           %>
            <tr>
            <td><%=t.getKoufen()%></td>
            <td><%=t.getLicenceID()%></td>
            <td><%=t.getOfficeID()%></td>
            <td><%=t.getReason()%></td>
            <td><%=t.getTime()%></td>
            <td><%=shengyu%></td>
            </tr>
           <%
                
                       }
                    %>   
            </table>
            <%
                   }
           %>
           <%
          // }
           %>
    	</fieldset>
    	<% } %>
    	
    	<!--如果是审核申请人员-->
    	<% if(worker.getJob().equals("申请审核")) {
    	%>
    	<fieldset class="info">
    		<legend accesskey="p"><p>可执行操作</p></legend>
    		<form method="post" action="ImportForWorker" enctype="multipart/form-data">
                <div>一键导入申请数据处理 申请.xls：</div>
                <span><input id="excel" type="file" name="shenqing" /></span>
                <div><input type="submit" value="一箭导入"/></div>
            </form>
            <form method="post" action="ImportForWorker" enctype="multipart/form-data">
            	<div>抽取两个申请人登录信息，进行人工审核</div>
            	<span><button>抽取</button></span>
            	<%
               if (acc!=null){
               %>
               <p>随机抽取两个考生账号：</p>
               账号：<%=acc.getUsername()%> 密码：<%=acc.getPassword()%>
               <%
               acc = acc.getNext();
               %>
               账号：<%=acc.getUsername()%> 密码：<%=acc.getPassword()%>
               
               <%
               }
               %>
            </form>
    	</fieldset>
    	<br />
    	<% } %>
    	
    	<!--如果是具体科目人员-->
    	<% if(!worker.getJob().equals("申请审核") && !worker.getJob().equals("管理员")) {
    	%>
    	<fieldset class="info">
    		<legend accesskey="p"><p>可执行操作</p></legend>
    		<!--如果是理论一-->
    		<% if(worker.getJob().equals("理论一")) {%>
    			<form method="post" action="ImportGrade" enctype="multipart/form-data">
    				<div>请导入理论一的考试成绩   理论一.xls</div>
    				<span><input id="excel" type="file" name="lilunyi" /></span>
                    <div><input type="submit" value="导入"/></div>
                    <% if(str.equals("chengji")){ %>
                    <span>导入成功！</span>
                    <% } %>
           		</form>
           		<br />
           		<form method="post" action="DuokaoCheck" enctype="multipart/form-data">
           			<div> 一个考生每次约考一个场次时只能考两次，以下考生有两次以上成绩：</div>
           			<% if(idset.length() != 0){ %>
           				<div>发现ID为<%=idset + " 在 " + "科目三"+ " 成绩的超过该限制，后台已经存入了系统记录信息。"%></div>
           			<% } %>
           			<div>//////////////////TODO，存入系统记录信息 ==========做好删掉我</div>
           			<span><input type="submit" value="一键检测！"/></span>
           		</form>
           		<br />
           		<form method="post" action="ImportGrade" enctype="multipart/form-data">
           			<div>我的打分</div>
                	<input id="excel" type="file" name="liluner" /> <br />
                	<input type="submit" value="导入"/>
           		</form>
    		<% } %>
    		<!--如果是理论二-->
    		<% if(worker.getJob().equals("理论二")) {%>
    			<form method="post" action="ImportGrade" enctype="multipart/form-data">
    				<div>请导入理论二的考试成绩   理论二.slx</div>
    				<span><input id="excel" type="file" name="liluner" /></span>
                    <div><input type="submit" value="导入"/></div>
                    <% if(str.equals("chengji")){ %>
                    <span>导入成功！</span>
                    <% } %>
           		</form>
           		<br />
           		<form method="post" action="DuokaoCheck" enctype="multipart/form-data">
           			<div> 一个考生每次约考一个场次时只能考两次，以下考生有两次以上成绩：</div>
           			<% if(idset.length() != 0){ %>
           				<div>发现ID为<%=idset + " 在 " + "科目三"+ " 成绩的超过该限制，后台已经存入了系统记录信息。"%></div>
           			<% } %>
           			<div>//////////////////TODO，存入系统记录信息 ==========做好删掉我</div>
           			<span><input type="submit" value="一键检测！"/></span>
           		</form>
           		<br />
           		<form method="post" action="ImportGrade" enctype="multipart/form-data">
           			<div>我的打分</div>
                	<input id="excel" type="file" name="liluner" /> <br />
                	<input type="submit" value="导入"/>
           		</form>
    		<% } %>
    		<!--如果是科目二-->
    		<% if(worker.getJob().equals("科目二")) {%>
    			<form method="post" action="ImportGrade" enctype="multipart/form-data">
    				<div>请导入科目二的考试成绩   科目二.slx</div>
    				<span><input id="excel" type="file" name="kemuer" /></span>
                    <div><input type="submit" value="导入"/></div>
                    <% if(str.equals("chengji")){ %>
                    <span>导入成功！</span>
                    <% } %>
           		</form>
           		<br />
           		<form method="post" action="DuokaoCheck" enctype="multipart/form-data">
           			<div> 一个考生每次约考一个场次时只能考两次，以下考生有两次以上成绩：</div>
           			<% if(idset.length() != 0){ %>
           				<div>发现ID为<%=idset + " 在 " + "科目三"+ " 成绩的超过该限制，后台已经存入了系统记录信息。"%></div>
           			<% } %>
           			<div>//////////////////TODO，存入系统记录信息 ==========做好删掉我</div>
           			<span><input type="submit" value="一键检测！"/></span>
           		</form>
           		<br />
           		<form method="post" action="ImportGrade" enctype="multipart/form-data">
           			<div>我的打分</div>
                	<input id="excel" type="file" name="liluner" /> <br />
                	<input type="submit" value="导入"/>
           		</form>
    		<% } %>
    		<!--如果是科目三-->
    		<% if(worker.getJob().equals("科目三")) {%>
    			<form method="post" action="ImportGrade" enctype="multipart/form-data">
    				<div>请导入科目三的考试成绩   科目三.slx</div>
    				<span><input id="excel" type="file" name="kemusan" /></span>
                    <div><input type="submit" value="导入"/></div>
                    <% if(str.equals("chengji")){ %>
                    <span>导入成功！</span>
                    <% } %>
           		</form>
           		<br />
           		<form method="post" action="DuokaoCheck" enctype="multipart/form-data">
           			<div> 一个考生每次约考一个场次时只能考两次，以下考生有两次以上成绩：</div>
           			<% if(idset.length() != 0){ %>
           				<div>发现ID为<%=idset + " 在 " + "科目三"+ " 成绩的超过该限制，后台已经存入了系统记录信息。"%></div>
           			<% } %>
           			<div>//////////////////TODO，存入系统记录信息 ==========做好删掉我</div>
           			<span><input type="submit" value="一键检测！"/></span>
           		</form>
           		<br />
           		<form method="post" action="ImportGrade" enctype="multipart/form-data">
           			<div>我的打分</div>
                	<input id="excel" type="file" name="liluner" /> <br />
                	<input type="submit" value="导入"/>
           		</form>
    		<% } %>
    	</fieldset>
    	<br />
    	<% } %>
    	
    </body>
    <% if(uploadState.equals("failed")){ %>
	<script type="text/javascript">
	<!--
	alert("导入失败！请选择正确文件！");
	//-->
	</script>
	<% } %>
</html>
