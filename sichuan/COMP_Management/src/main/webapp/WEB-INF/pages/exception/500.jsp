<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>您的访问出错了(500)</title>
<style>
#errorPage{
	position: absolute;
	margin-left:300px;
	top: 260px;
	color: #158AE4;
}
#errorPage a{
	font-size: 14px;
	color: #158AE4;
	text-decoration: none;
}
#errorPage a:HOVER{
	text-decoration: underline;
	cursor: pointer;
}
</style>
</head>
<body>
<div id="main">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="left" valign="top">
		  <div>
			<div id="list">
			     <div id="title"><img src="<%=basePath%>/images/error500.gif" title="error"></img></div>
			     <div id="errorPage">
			     <%if(session != null){%>
			     	<a href="<%=basePath%>index.jsp">&nbsp;返回首页</a>&nbsp;&nbsp;
			     <% }else{%>
			     	<a href="<%=basePath%>">&nbsp;返回首页</a>&nbsp;&nbsp;
			     <%} %>
			     	<a onclick="history.back()">&nbsp;返回上一页</a>
			     </div>
		    </div>
		  </div>		
		</td>
      </tr>
	</table>
</div>
</body>
</html>