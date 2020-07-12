<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>四川移动云管理平台自服务门户</title>
<link href="styles/portal.css" rel="stylesheet" type="text/css" />
<link href="styles/formStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="scripts/lib/jquery-1.8.3.js"></script>
<script type="text/javascript" src="scripts/lib/jquery.mooko.tabs.js"></script>
<script type="text/javascript" src="scripts/lib/jquery.uniform.js"></script>

<style>
#detail_tab_infos{
	height:490px;
	overflow:auto;
}
#login{
	border:1px solid #C0C2C3;
	height:520px;
	width : 500px;
	background:#EBEEF0;
	padding:5px;
	margin-bottom:150px;
}
.red-font{
	color:#F00;
}
.login-content div{
	height: 23px;
    padding: 6px 20px;
}
</style>
<script>
$(function() {
	//自定义form样式
	$(":text,:password").uniform();
	$('#detail_tab').MookoTabs({
		eventName:'click'
	});	
});	

//提交按钮
function submitform(){
	$('#formSubmit').submit();
}
</script>
</head>

<body onload= "document.formSubmit.userId.select() ">
<!-- #BeginLibraryItem "/Library/head_portal.lbi" -->
<div id="header">
<div class="head-logo"></div>
<div>
		<ul id="head-menu" class="head-btn">
	   		<li class="head-btn-sel"><a href="home.jsp">首页</a></li>
	   		<li><a href="product/hostPrice.action">产品</a></li>
		   	<li><a href="console/resourcesOverviewAction.action">控制台</a></li>
		   	<li><a href="userCenter/orderQuery.action;">用户中心</a></li>
		   	<li><a href="business/businessList.action">业务管理</a></li>
		</ul>
	</div>
</div>
<div id="message" style="display: none;">
		<span class="error"><s:actionerror /></span>
		<span class="error"><s:fielderror /></span>
		<span class="success"><s:actionmessage /></span>
</div>
<!-- #EndLibraryItem -->
<div id="main">
<div id="indexRight">
<div id="login" style="margin-left:300px;width:380px;">
<div class="login-content-bg" id="detail_tab">

<s:form id="formSubmit"  name="formSubmit" method="post" action="forgetPassword.action" >
<div id="detail_tab_infos" class="login-content login-tab">
<div class="detail-title">
	<h3>找回密码</h3>
</div>
<font class="red-font"><div><s:property value="errMsg" /></div></font>
<s:hidden name="userId" />
</div>
</s:form>
</div>
</div>
<!-- login end -->
</div>

</div>
<div id="footer">版权所有&copy;中国移动通信集团四川有限公司</div>
</body>
</html>