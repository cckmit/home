<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>四川移动云管理平台运营管理系统</title>
<link href="styles/login.css" rel="stylesheet" type="text/css" />
<link href="styles/formStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="scripts/lib/jquery-1.8.3.js"></script>
<script type="text/javascript" src="scripts/lib/jquery.uniform.js"></script>
<script type="text/javascript" src="scripts/lib/jquery.mooko.tabs.js"></script>
<script type="text/javascript" src="scripts/lib/md5.js"></script>
<script type="text/javascript" src="scripts/lib/aes.js"></script>
<script type="text/javascript" src="scripts/common.js"></script>

<script>
$(function() {
	//自定义form样式
	$(":text,:password").uniform();
	var oInput = document.getElementById("userId");
    oInput.focus();
	var hcode = document.getElementById('hcode');
	hcode.src = "createVerifyImageServlet";
	
	if('${errMsg}'!=''&&'${errMsg}'!=null){
		$.compMsg({type:'error',msg:'${errMsg}'});
	}
});	

function show() {//重新载入验证码
	var timenow = new Date().getTime();
	var hcode = document.getElementById('hcode');
    hcode.src = "createVerifyImageServlet?datetime=" + timenow;
}
//提交按钮
function submitform(){
	var userId = $('#userId').val();
	var password = $('#password').val();
	var verify = $('#verify').val();
	
	if(null == verify || verify==""){
		$('#errParam').text("验证码不能为空，请重新输入！");
		$.compMsg({type:'error',msg:"验证码不能为空，请重新输入！"});
		return false;
	}else{
		if(null == userId|| null == password || userId==""|| password==""){
			$('#errParam').text("用户账号和密码不能为空，请重新输入！");
			$.compMsg({type:'error',msg:"用户账号和密码不能为空，请重新输入！"});
			return false;
		}else{
			var thisForm = document.forms["formSubmit"];
			thisForm.md5Password.value=hex_md5(password);
			thisForm.aesUserId.value=Encrypt(userId);
            $('#formSubmit').submit();
		}
	}
	
}

</script>
</head>

<body>
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
                		<span>用户登录</span>
                	</div>
                	<hr class="login-line"/>
            		<form id="formSubmit" name="formSubmit" method="post" action="login.action" >
						<div id="detail_tab_info" class="login-detail-font">
							<div><span>用户账号:</span><input type="text" id="userId"  value="<s:property value="userId"/>" />
								   <s:hidden name="aesUserId"  id="aesUserId"/>
							</div>
							
							<div><span style="width:49px;">密</span><span style="width:28px;">码:</span><input class="fixed" type="password" autocomplete="off" id="password"  value="<s:property value="password"/>" />
							<s:hidden id="md5Password" name="md5Password"/> </div>
							<div class="login-check-input"><span style="width:28px;">验</span><span style="width:28px;">证</span><span style="width:28px;">码:</span>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td valign="middle" style="height: 10px; line-height: 10px"><s:textfield id="verify" name="verify" size="5" maxlength="4" cssStyle="width:53px;" /> &nbsp;</td>
									<td valign="middle"><a href="javascript:show()"><img id="hcode" alt="刷新" style="text-decoration: none; border: 0" /> </a></td>
								</tr>
							</table>
							</div>
                    		<%-- <div class="login-found-pwd">
                    			<span><a href="forgetPassword.jsp">忘记密码？</a></span>
                    		</div>--%>
							<div style="display: none;"><s:property value="errMsg" /><label id="errParam"></label></div>
							<div class="login-detail-btn"><input type="submit" class="submit" value="登录" onclick="return submitform();"/><!--<span ><a href="javascript:submitform()">登录</a></span>--> <!--<input type="submit" value="登录" />--> </div>
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