<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>四川移动云管理平台自服务门户</title>
<link href="styles/portal.css" rel="stylesheet" type="text/css" />
<link href="styles/bindApp.css" rel="stylesheet" type="text/css" />
<link href="styles/formStyle.css" rel="stylesheet" type="text/css" />
<link href="styles/detailStyles.css" rel="stylesheet"  type="text/css" />

<script type="text/javascript" src="scripts/lib/jquery-1.8.3.js"></script>
<script type="text/javascript" src="scripts/lib/jquery.mooko.tabs.js"></script>
<script type="text/javascript" src="scripts/lib/jquery.uniform.js"></script>

<script type="text/javascript" src="scripts/pagesjs/forgetPassword/forgetPassword.js"></script>
<script>
$(function() {
	$("#userId").select();
});
</script>
<style>
#detail_tab_infos{
	overflow:auto;
}
#login{
	border:1px solid #C0C2C3;
	background:#EBEEF0;
	padding:5px;
}
.red-font{
	color:#F00;
}
.login-content div{
	height: 23px;
    padding: 6px 20px;
}
ul{
	list-style-type: none;
}
ul li{
	float: left;
}
.login-content .btn{
	text-align:left;
	margin-left: 87px;
}
</style>
</head>

<body>
<!-- #BeginLibraryItem "/Library/head_portal.lbi" -->
<div id="header">
<div class="head-logo"></div>
<div>
		<ul id="head-menu" class="head-btn-noa">
		   	<li class="head-btn-sel" ><a href="login.jsp">首页</a></li>
		   	<li>产品</li>
		   	<li>控制台</li>
		   	<li>用户中心</li>
		</ul>
	</div>
</div>
<div id="message" style="display: none;">
		<span class="error"><s:actionerror /></span>
		<span class="error"><s:fielderror /></span>
		<span class="success"><s:actionmessage /></span>
</div>
<!-- #EndLibraryItem -->
<div id="main" style="background:#fff;">
<div >
<div id="login" >
<div class="login-content-bg" id="detail_tab">

<div id="detail_tab_infos" class="login-content login-tab">
<div class="detail-title" >
	<h3>密码重置</h3>
</div>
<!--导航 -->
<div id="navBar">
	<ul>
		<li id="loginPwLi" style="margin-left:180px;">1、输入账号 </li>
		<li id="questionLi" style="margin-left:150px;">2、密保问题</li>
		<li id="resetPwLi" style="margin-left:150px;">3、密码重置</li>
	</ul>
</div>
<div  style="margin-left:260px;margin-top:80px;width:auto;height:370px;display:block;">
	<div id="validateUserIdDiv" style="display:bolock;">
		<div><span><font class="red-font">*</font>用户账号:</span>
			<s:textfield id="userId" name="userInfo.userId"/>
		</div>
		<div><span><font class="red-font">*</font>验证码：</span>
			<s:textfield id="verify" name="verify" size="5" maxlength="4"
			cssStyle="width:80px;float:left;margin-right:5px;" />
			<a href="javascript:show()"> <img id="hcode" alt="刷新" /> </a> 
		</div>
		<div><font class="red-font" style="margin-left:87px;"><s:property value="errMsg" /><label id="errParam"></label></font></div>
		<div class="btn"><input style="cursor:pointer;" type="submit" onclick="nextStep();return false;" value="下一步" /></div>
    </div>
	
	<div id="validateQuestionDiv" style="display:none;">
		<div><span style="margin-left:15px;">密保问题:</span>
			<span id="questionSpan" style="margin-left:-8px"></span>
		</div>
		<div><span><font class="red-font">*</font>密保答案:</span>
			<s:textfield id="answer" name="answer"  maxlength="64" />
			<s:hidden id="correctAnswer" name="correctAnswer" />
		</div>
		<div><font class="red-font" style="margin-left:87px;"><s:property value="errMsg" /><label id="errQuestion"></label></font></div>
		<div class="btn" ><input style="cursor:pointer;" type="submit" onclick="findPassword();return false;" value="下一步" /></div>
	</div>
	
	<div id="resetPWDiv" style="display:none;">
		<div><span><font class="red-font">*</font>新密码:</span>
			<s:password id="userPassword" name="userInfo.password"/>
		</div>
		<div><span><font class="red-font">*</font>确认密码:</span>
			<s:password id="confirmUserPassword" name="confirmUserPassword"/>
		</div>
		<div><span><font class="red-font">*</font>验证码：</span>
			<s:textfield id="verify2" name="verify2" size="5" maxlength="4"
			cssStyle="width:80px;float:left;margin-right:5px;"/>
			<a href="javascript:show()"> <img id="hcode1" alt="刷新" /> </a> 
		</div>
		<div><font class="red-font" style="margin-left:87px;"><s:property value="errMsg" /><label id="errPassword"></label></font></div>
		<div class="btn" ><input type="submit" style="cursor:pointer;" onclick="submitform();return false;" value="提交" /></div>
	</div>
	
	<div id="editSuccessDiv" style="display:none;">
		<div><span style="margin-left:13px;">&nbsp;</span>
			 <div id="skipDiv"></div>
		</div>
		<div><font class="red-font" style="margin-left:87px;"><label id="errEdit"></label></font></div>
		<div class="btn" ><input type="submit" style="cursor:pointer;" onclick="login();return false;"  value="立即登录" /></div>
	</div>
</div>

</div>
</div>
</div>
<!-- login end -->
</div>

</div>
<div id="footer">版权所有&copy;中国移动通信集团四川有限公司</div>
</body>
</html>