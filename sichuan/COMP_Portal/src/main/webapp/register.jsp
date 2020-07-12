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
	height:525px;
	overflow:auto;
}
#login{
	border:1px solid #C0C2C3;
	height:555px;
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
	var hcode = document.getElementById('hcode');
	hcode.src = "createVerifyImageServlet";
});	

//提交按钮
function submitform(){
	var f7 = validateRead();
	var f6 = validateEmail();
	var f5 = validateMobile();
	var f4 = validateConfirmPassword();
	var f3 = validatePassword();
	var f2 = validateUserName();
	var f1 = validateUserId();
	
	if (f1 && f2 && f3 && f4 && f5 && f6 && f7) {
		$('#formSubmit').submit();
	} else {
		return false;
	}
}
function validateRead() {
	var checked = $('#read').is(':checked');
	if(checked){
		return true;
	};
	$('#errParam').text("请阅读自服务门户服务条款！");
	return false;
	
}
function validateUserId() {
	var userId = $('#userId').attr("value");
	var rexName = /[0-9a-zA-Z_]/;
	if(null == userId || userId==""){
		$('#errParam').text("用户账号不能为空，请重新输入！");
		return false;
	} else if(!rexName.test(userId)){
		$('#errParam').text("用户账号只能由数字字母下划线组成！");
		return false;
	} else {
		return true;
	}
}
function validateUserName() {
	var userName = $('#userName').attr("value");
	if (null == userName || userName=="") {
		$('#errParam').text("用户姓名不能为空，请重新输入！");
		return false;
	} else {
		return true;
	}
}
function validatePassword() {
	var password = $('#password').attr("value");
	if (null == password || password=="") {
		$('#errParam').text("密码不能为空，请重新输入！");
		return false;
	} else {
		return true;
	}
}
function validateConfirmPassword() {
	var password = $('#password').attr("value");
	var confirmPassword = $('#confirmPassword').attr("value");
	if (null == confirmPassword || confirmPassword=="") {
		$('#errParam').text("确认密码不能为空，请重新输入！");
		return false;
	} else {
		if (password != confirmPassword) {
			$('#errParam').text("两次输入密码不一致，请重新输入！");
			return false;
		} else {
			return true;
		}
	}
}
function validateMobile() {
	var mobile = $('#mobile').attr("value");
	var rexNum = /^[0-9]*[1-9][0-9]*$/;
	if (null == mobile || mobile=="") {
		$('#errParam').text("手机号码不能为空，请重新输入！");
		return false;
	} else if(!rexNum.test(mobile)){
		$('#errParam').text("手机号码只能为数字！！");
		return false;
	} else if($.trim(mobile).length != 11){
		$('#errParam').text("手机号码长度错误！！");
		return false;
	} else {
		return true;
	}
}
function validateEmail() {
	var email = $('#email').attr("value");
	var rexMail = /^([a-zA-Z0-9\\._-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
	if (null == email || email=="") {
		$('#errParam').text("邮箱不能为空，请重新输入！");
	} else if(!rexMail.test(email)){
		$('#errParam').text("邮箱格式不正确！");
	} else {
		return true;
	}
}

</script>
</head>

<body onload= "document.formSubmit.userId.select() ">
<!-- #BeginLibraryItem "/Library/head_portal.lbi" -->
<div id="header">
<div class="head-logo"></div>
<div>
		<ul id="head-menu" class="head-btn">
	   		<li class="head-btn-sel"><a href="index.jsp">首页</a></li>
	   		<li><a href="product/hostPrice.action">产品</a></li>
		   	<li><a href="console/resourcesOverviewAction.action">控制台</a></li>
		   	<li><a href="userCenter/orderQuery.action;">用户中心</a></li>
		   	<%--<li><a href="business/businessList.action">业务管理</a></li> --%>
		</ul>
	</div>
</div>
<!-- #EndLibraryItem -->
<div id="main">
<div id="indexRight">
<div id="login">
<div class="login-content-bg" id="detail_tab" style="height: 544px;">
<s:if test="#session.userInfo!=null"><div id="userInfo"><s:property value="#session.userInfo.userName" /> <a href="../logout.action">[退出]</a></div></s:if>
<s:else>

<s:form id="formSubmit"  name="formSubmit" method="post" action="register.action" >
<div id="detail_tab_infos" class="login-content login-tab">
<div class="detail-title">
	<h3>注册用户</h3>
</div>
<div><span><font class="red-font">*</font>用户账号:</span><s:textfield id="userId" name="user.userId" autocomplete="off" /></div>
<div><span><font class="red-font">*</font>用户姓名:</span><s:textfield id="userName" name="user.userName" autocomplete="off" /></div>
<div><span><font class="red-font">*</font>密&nbsp;&nbsp;&nbsp;码:</span>
<s:password autocomplete="off" id="password" name="user.password" />
</div>
<div><span><font class="red-font">*</font>确认密码:</span>
<s:password autocomplete="off" id="confirmPassword" name="user.confirmPassword"/>
</div>
<div><span><font class="red-font">*</font>手机号码:</span>
<s:textfield id="mobile" name="user.mobile" autocomplete="off" /></div>
<div><span><font class="red-font">*</font>邮箱:</span>
<s:textfield id="email" name="user.email" autocomplete="off" /></div>
<div><span>用户地址:</span>
<s:textfield id="address" name="user.address" autocomplete="off" /></div>
<div><span>固话号码:</span>
<s:textfield id="telphone" name="user.telphone" autocomplete="off" /></div>
<div><span>传真号码:</span>
<s:textfield id="fax" name="user.fax" autocomplete="off" /></div>
<div><span>部门名称:</span>
<s:textfield id="departmentName" name="user.departmentName" /></div>
<div><span>描述信息:</span>
<s:textfield id="desc" name="user.desc" autocomplete="off" /></div>
<div>
	<input id='read' type="checkbox" onclick="if(this.checked){document.getElementById('btnRegister').disabled = false;}else{document.getElementById('btnRegister').disabled = true;}">&nbsp;&nbsp;&nbsp;我已阅读并同意《自服务门户服务条款》</input></td>	
</div>
<div><font class="red-font">
<%
	String errMsg=(String) session.getAttribute("errMsg");
	if(errMsg!=null){
		out.print(errMsg);
		session.removeAttribute("errMsg");
	}
%>
<s:property value="errMsg" />
<label id="errParam"></label></font></div> 
<div class="btn"><input type="button" id="btnRegister" onclick="submitform();return false;" value="申请注册" /></div>
</div>
</s:form>
</s:else>
</div>
</div>
<!-- login end -->
</div>


<div style="margin-left:570px;width:380px;position: absolute;top:15px;border: 1px solid #7f9db9;padding:15px;line-height:20px">
				<table>
					<tr>
						<td align="center">
							<span style="font-size:14px;font-weight:bold;text-align:center"><s:property value="getText('regedit.developer.protocol')" escape="false"/></span>
						</td>
					</tr>
					<tr>
						<td style="font-size:12px;">
							注册用户，请您认真阅读本协议，审阅并接受或不接受本协议（未成年人应在法定监护人陪同下审阅）。<br><br>一、 只有符合下列条件之一的自然人或法人才能申请成为本网站用户，可以使用本网站的服务。<br>（一）、年满十八岁，并具有民事权利能力和民事行为能力的自然人；<br>（二）、无民事行为能力人或限制民事行为能力人应经过其监护人的同意；<br>（三）、根据中国法律、法规、行政规章成立并合法存在的机关、企事业单位、社团组织和其他组织。无法人资格的单位或组织不当注册为本网站用户的，其与本网站之间的协议自始无效，本网站一经发现，有权立即注销该开发者，并追究其使用本网站服务的一切法律责任。　 <br><br>二、开发者完成注册申请后，获得账号的使用权。开发者应提供及时、详尽及准确的个人资料，并不断更新其用户信息，符合及时、详尽准确的要求。
						</td>
					</tr>
				</table>
				</div>
</div>
<div id="footer">版权所有&copy;中国移动通信集团四川有限公司</div>
</body>
</html>