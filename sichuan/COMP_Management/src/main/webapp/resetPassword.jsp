<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>四川移动云管理平台运营管理系统</title>
<link href="styles/portal.css" rel="stylesheet" type="text/css" />
<link href="styles/login.css" rel="stylesheet" type="text/css" />
<link href="styles/formStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="scripts/lib/jquery-1.8.3.js"></script>
<script type="text/javascript" src="scripts/lib/jquery.mooko.tabs.js"></script>
<script type="text/javascript" src="scripts/lib/jquery.uniform.js"></script>

<style>
#detail_tab_info{
	height:490px;
	overflow:auto;
}
.red-font{
	color:#F00;
}
.login-detail-font label{
	float:left;
	margin:5px;
}
.login-detail-font input{
	width:150px;
}
.login-detail-font .radio{
	padding:5px 0;
}
.login-detail-font span{
	width:80px;
}
.green-font{
	color:#147800;
}
</style>

<script>
$(function() {
	//自定义form样式
	$(":text,:password,:radio").uniform();
	$('#detail_tab').MookoTabs({
		eventName:'click'
	});	
});	

//提交按钮
function submitform(){
	var f1 = validateConfirmPassword();
	var f2 = validatePassword();
	if (f1 && f2) {
		$('#formSubmit').submit();
	} else {
		return false;
	}
}
function validatePassword() {
	var password = $('#password').attr("value");
	if (null == password || password=="") {
		$('#errParams').text("密码不能为空，请重新输入！");
		return false;
	} else {
		return true;
	}
}
function validateConfirmPassword() {
	var password = $('#password').attr("value");
	var confirmPassword = $('#confirmPassword').attr("value");
	if (null == confirmPassword || confirmPassword=="") {
		$('#errParams').text("确认密码不能为空，请重新输入！");
		return false;
	} else {
		if (password != confirmPassword) {
			$('#errParams').text("两次输入密码不一致，请重新输入！");
			return false;
		} else {
			return true;
		}
	}
}
</script>
</head>

<body onload= "document.formSubmit.userId.select() ">
<!-- head -->
<div class="login-head-bg">
	<div class="login-head">
        <div class="login-head-logo">
        </div>
        <div class="login-head-font">
            <span>| 四川移动云管理平台运营管理系统</span>
        </div>
	</div>
</div>
<!-- info -->
<div class="login-info-bg">
	<div class="login-info">
    	<div class="login-info-logo"></div>
        <div class="login-float-bg">
            <div class="login-float-top">
        			<div class="login-float-head"></div>
                	<div class="login-float-title">
                		<span>找回密码</span>
                	</div>
                	<hr class="login-line"/>
            		<form id="formSubmit" name="formSubmit" method="post" action="resetPassword.action" >
						<div id="detail_tab_info" class="login-detail-font">
							<s:hidden name="userId" />
							<div><span><font class="red-font">*</font>新密码:</span><s:password id="password" name="user.password"/></div>
							<div><span><font class="red-font">*</font>确认新密码:</span><s:password id="confirmPassword" name="user.comfirmPassword"/></div>
							<div>
								<font class="red-font"><s:property value="errMsgs" /><label id="errParams"></label></font>
								<s:if test="user.status == resetSuc" >
								<font class="green-font"><s:property value="errMsg" /><label id="errParam"></label></font>
								</s:if>
								<s:elseif test="user.status == resetError">
								<font class="red-font"><s:property value="errMsg" /><label id="errParam"></label></font>
								</s:elseif>
							</div>
							<div class="login-detail-btn"><input type="submit" class="submit" onclick="submitform();return false;" value="确认" />
						</div>
					</form>
        	</div>
        </div>
    </div>
</div>
<!-- bottom -->
<div class="login-bottom">
	<div>
    	<span >版权所有&nbsp;&copy&nbsp;中国移动通信集团四川有限公司</span>
    </div>
</div>
</body>
</html>