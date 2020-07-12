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
.login-detail-font span{
	float:left;
	display:block;
	width:55px;
	margin-left:5px;
	line-height:23px;
	/*圆角*/
	-moz-border-radius: 3px;
    -webkit-border-radius: 3px;
    border-radius:3px;
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
	var userId = $('#userId').attr("value");
	if (null == userId|| userId=="") {
		$('#errParam').text("用户名不能为空，请重新输入！");
	} 
// 	else if (!($('#shortMessage').attr("checked")) && (!$('#email').attr("checked"))) {
// 		$('#errParam').text("请选择密码找回方式！");
// 	} 
	else {
		$('#formSubmit').submit();
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
            		<form id="formSubmit" name="formSubmit" method="post" action="forgetPassword.action" >
						<div id="detail_tab_info" class="login-detail-font">
							<div><s:property value="errMsg" />系统已向您注册的邮箱发送一封邮件，请注意查收！</div>
							<s:hidden name="userId" />
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