<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>四川移动云管理平台自服务门户</title>
 <link href="styles/portal.css" rel="stylesheet" type="text/css" />
<link href="styles/base.css" rel="stylesheet" type="text/css" />
<link href="styles/bindApp.css" rel="stylesheet" type="text/css" />
<link href="styles/formStyle.css" rel="stylesheet" type="text/css" />
<link href="styles/detailStyles.css" rel="stylesheet"  type="text/css" /> 
<script type="text/javascript" src="scripts/lib/jquery-1.8.3.js"></script>
<script type="text/javascript" src="scripts/lib/jquery.mooko.tabs.js"></script>
<script type="text/javascript" src="scripts/lib/jquery.uniform.js"></script>
<script type="text/javascript" src="scripts/lib/dialog/jquery.artDialog.js?skin=default"></script>
<style>
#detail_tab_infos{
	overflow:auto;
	padding:8px 0px;
}
#detail_tab_infos div{
	padding: 6px 10px;
}
.label{
	width:96%;
}
.result{
	margin-top:0;
}
#login{
	border:1px solid #C0C2C3;
	width : 1030px;
	background:#EBEEF0;
	padding:5px;
	/*margin-bottom:150px;*/
}
.red-font{
	color:#F00;
}
.login-content div{
	height: 23px;
    padding: 6px 20px;
}
.btnReg{
	text-align:center;
}
.btnReg input{
    margin: 0 auto;
    padding: 5px 30px;
    text-align: center;
    border-radius: 3px;
}
.btnTrue{
	/*background: url('./images/ico_login_btn_bg.png') repeat-x ;border-radius: 3px 3px 3px 3px;*/
	border: 1px solid #349dd2;
    color: #fff;
    background: none repeat scroll 0 0 #3cb4f0;
    cursor: pointer;
}
.btnFalse{
	/*background: url('./images/ico_login_btn_bg_disable.png') repeat-x ;border-radius: 3px 3px 3px 3px;*/
	border: 1px solid #999;
    color: #333;
}
.msgContent{
position: absolute;
}
</style>
<script>
/*提示信息样式*/
jQuery.compMsg = function(options){
	var defaults = {
		msg 	: '',	
		type	: 'info',//success\error\warn\loading	
		parent	: 'body',	
		top  	: "70px",
		left 	: "40%",
		timeout : 5000,//显示时间
		close	: true,//显示关闭
		closeText: 'x',
		closeClassName: 'msgClose',//关闭样式
		infoClassName:'msgInfo',//提示样式
		successClassName:'msgSuccess',//成功样式
		errClassName:'msgError',//错误样式
		warnClassName:'msgWarn',//警告样式
		loadingClassName:'msgLoading',//loading样式
		delay: 0,//延时出现
		callback: function(){}
	}; 
	if(typeof options == 'string') defaults.text = options;
	var opt = jQuery.extend(defaults, options); 
	
	function show(){
	    // 清除之前的弹出消息
		if($(".msgContent") != null && $(".msgContent")[0] != undefined){
			$(".msgContent").hide();
		}
		
		// 创建新的弹出消息
		var container = $('<div class="msgContent" style="left:' + opt.left +';top:' + opt.top + '"></div>');
		$(opt.parent).prepend(container);

		//判断是否大于顶端距离改变显示位置
		if($(window).scrollTop() > 70){
			container.css({position: "fixed",top:"10px"});
		} else {
			container.css({position:"absolute",top:opt.top});
		}
		var msgStyle;
		if (opt.type == 'success') {
			msgStyle = opt.successClassName;
		}else if (opt.type == 'error') {
			msgStyle = opt.errClassName;
		}else if (opt.type == 'warn') {
			msgStyle = opt.warnClassName;
		}else if (opt.type == 'loading') {
			msgStyle = opt.loadingClassName;
			opt.close = false;
		}else {
			msgStyle = opt.infoClassName;
		} 
		
		var msgBox = $('<div class="msgBase '+ msgStyle +'" >'+ opt.msg +'</div>');
		container.prepend(msgBox);
		if (opt.close) {
			$('<span class="' + opt.closeClassName + '">' + opt.closeText + '</span>').click(function(){
				hide(msgBox);
			}).appendTo(msgBox);
		}
		
		msgBox.delay(opt.timeout).fadeOut(function(){
	      hide(msgBox);
	    });
	};
	
	setTimeout(show,opt.delay);
	
	function hide(msgBox){
		msgBox.remove();
		opt.callback();	
	}
};


$(function() {
	//自定义form样式
	$(":text,:password,.nl :checkbox").uniform();
	$('#detail_tab').MookoTabs({
		eventName:'click'
	});	
	
	 // 业务列表全选
	$("#selectAllApp").click(function(event) {
	    checkAllApp(this);
	});
	
});	

//点击确认申请
function submitform(){
	var f9 = validateSecurity();
	var f8 = validateRead();
	//var f7 = validateApp();
	var f6 = validateEmail();
	var f5 = validateMobile();
	var f4 = validateConfirmPassword();
	var f3 = validatePassword();
	var f2 = validateUserName();
	var f1 = validateUserId();
	if (f1 && f2 && f3 && f4 && f5 && f6  && f8 && f9) {
		var f10 = validateRegisterUserId();
		if(f10){
			var appIdStr = "";
		    $('input[name="appIds"]').each(function() {
		    	appIdStr += $(this).val()+",";
		    });
			var userObj = {
				userId : $('#userId').attr("value"),
				userName : $('#userName').attr("value"),
				password : $('#password').attr("value"),
				mobile : $('#mobile').attr("value"),
				email : $('#email').attr("value"),
				address : $('#address').attr("value"),
				telphone : $('#telphone').attr("value"),
				fax : "",//$('#fax').attr("value"),
				desc : $('#desc').attr("value"),
				departmentName : $('#departmentName').attr("value"),
				security_question : $('#security_question').attr("value"),
				security_answer : $('#security_answer').attr("value")
			};
			var da_val = {
				userObj : JSON.stringify(userObj)
			};
			$.ajax( {
				type : "POST",
				url : 'register.action',
				data : da_val,
				dataType : "JSON",
				cache : false,
				success : function(data) {
					if(data.resultPath=="error"){
						$.compMsg( {
							type : 'error',
							msg : "注册申请提交失败！"
						});
						return;
					}
					$.dialog({
						title: "申请结果",
						content: document.getElementById('securityQuestionDIV'),
						init:function(){ },
						button:[{
							id:'notpass',
				            name: '确定',
				            callback: function () {},
							focus: true
						},{
							id:'notpass',
				            name: '返回首页',
				            callback: function () {
				            	window.location.href = 'index.jsp';
							}
						}],
						cancelVal: "确定",
						cancel:true,
						lock: true
					});
				}
			});
		}else {
			return false;
		}
	} else {
		return false;
	}
}
//*************************************验证**************************************
function validateRead() {//服务条款
	var checked = $('#read').is(':checked');
	if(checked){
		return true;
	};
	//$('#errParam').text("请阅读自服务门户服务条款！");
	$.compMsg( {
		type : 'error',
		msg : "请阅读自服务门户服务条款！"
	});
	return false;
	
}
/* function validateApp() {//绑定业务 需要修改by zyf
	var appValue=$("#app").val();
	var appRegx= /^(?!_)(?!.*?_$)[a-zA-Z0-9 \-_\u4e00-\u9fa5]+$/;
	if(appValue==null || appValue==""){
		$.compMsg( {
			type : 'error',
			msg : "请输入绑定的业务名称！"
		});
		return false;
	}else if(!appRegx.test(appValue)){
		$.compMsg( {
			type : 'error',
			msg : "绑定的业务由汉字、数字、字母、横线或下划线组成！<br>且横线和下划线不能在名称的开始和结尾！<br>"
		});
		return false;
	}else{
		return true;
	}
}   */
function validateUserId() {//用户账号
	var userId = $('#userId').attr("value");
	var rexName = /[0-9a-zA-Z_]/;
	if(null == userId || userId==""){
		//$('#errParam').text("用户账号不能为空，请重新输入！");
		$.compMsg( {
			type : 'error',
			msg : "用户账号不能为空，请重新输入！"
		});
		return false;
	} else if(!rexName.test(userId)){
		//$('#errParam').text("用户账号只能由数字字母下划线组成！");
		$.compMsg( {
			type : 'error',
			msg : "用户账号只能由数字字母下划线组成，请重新输入！"
		});
		return false;
	} else {
		return true;
	}
}
function validateUserName() {//用户姓名
	var userName = $('#userName').attr("value");
	if (null == userName || userName=="") {
		//$('#errParam').text("用户姓名不能为空，请重新输入！");
		$.compMsg( {
			type : 'error',
			msg : "用户姓名不能为空，请重新输入！"
		});
		return false;
	} else {
		return true;
	}
}
function validatePassword() {//密码
	var password = $('#password').attr("value");
	if (null == password || password=="") {
		//$('#errParam').text("密码不能为空，请重新输入！");
		$.compMsg( {
			
			type : 'error',
			msg : "密码不能为空，请重新输入！"
		});
		return false;
	} else {
		return true;
	}
}
function validateConfirmPassword() {//确认密码
	var password = $('#password').attr("value");
	var confirmPassword = $('#confirmPassword').attr("value");
	if (null == confirmPassword || confirmPassword=="") {
		//$('#errParam').text("确认密码不能为空，请重新输入！");
		$.compMsg( {
			type : 'error',
			msg : "确认密码不能为空，请重新输入！"
		});
		return false;
	} else {
		if (password != confirmPassword) {
			//$('#errParam').text("两次输入密码不一致，请重新输入！");
			$.compMsg( {
				type : 'error',
				msg : "两次输入密码不一致，请重新输入！"
			});
			return false;
		} else {
			return true;
		}
	}
}
function validateMobile() {//手机号码
	var mobile = $('#mobile').attr("value");
	var rexNum = /^[0-9]*[1-9][0-9]*$/;
	if (null == mobile || mobile=="") {
		//$('#errParam').text("手机号码不能为空，请重新输入！");
		$.compMsg( {
			type : 'error',
			msg : "手机号码不能为空，请重新输入！"
		});
		return false;
	} else if(!rexNum.test(mobile)){
		//$('#errParam').text("手机号码只能为数字！");
		$.compMsg( {
			type : 'error',
			msg : "手机号码只能为数字，请重新输入！"
		});
		return false;
	} else if($.trim(mobile).length != 11){
		//$('#errParam').text("手机号码长度错误！");
		$.compMsg( {
			type : 'error',
			msg : "手机号码长度错误，请重新输入！"
		});
		return false;
	} else {
		return true;
	}
}
function validateEmail() {//邮箱
	var email = $('#email').attr("value");
	var rexMail = /^([a-zA-Z0-9\\._-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
	if (null == email || email=="") {
		//$('#errParam').text("邮箱不能为空，请重新输入！");
		$.compMsg( {
			type : 'error',
			msg : "邮箱不能为空，请重新输入！"
		});
	} else if(!rexMail.test(email)){
		//$('#errParam').text("邮箱格式不正确！");
		$.compMsg( {
			type : 'error',
			msg : "邮箱格式不正确，请重新输入！"
		});
	} else {
		return true;
	}
}
function validateSecurity() {//密保问题和答案
	var question = $('#security_question').attr("value");
	var answer = $('#security_answer').attr("value");

	if(null == question || question=="" || question=="例如：您的生日是？"){
		//$('#errParam').text("密保问题不能为空，请重新输入！");
		$.compMsg( {
			type : 'error',
			msg : "密保问题不能为空，请重新输入！"
		});
		return false;
	}
	if(null == answer || answer==""){
		//$('#errParam').text("答案不能为空，请重新输入！");
		$.compMsg( {
			type : 'error',
			msg : "密保答案不能为空，请重新输入！"
		});
		return false;
	} 
	return true;
}
function validateRegisterUserId(){//用户账号是否唯一
	var userId = $('#userId').attr("value");
	var mobile = $('#mobile').attr("value");
	var email = $('#email').attr("value");
	var flag = true;
	$.ajax({
		type : "POST",
		async:false,
		url : 'registerValidateUserIdAction.action?struts.enableJSONValidation=true',
		data : { 
			userId : userId, 
			mobile : mobile,
			email : email,
			isCheckId : "true"
		},
		dataType : "JSON",
		cache : false,
		success : function(data) {
			if(data.errMsg != null && data.errMsg!= ""){
				//$('#errParam').text(data.errMsg);
				$.compMsg( {
					type : 'error',
					msg : data.errMsg
				});
				flag = false;
			}
			
		}
    });
	if(flag){
		return true;
	}else{
		return false;
	}
};

//**************************绑定业务***************************


function checkRedBox(obj){
	if(obj.checked){
		document.getElementById('btnRegister').disabled = false;
		$('#btnRegister').addClass("btnTrue");
		$('#btnRegister').removeClass("btnFalse");
	}else{
		document.getElementById('btnRegister').disabled = true;
		$('#btnRegister').removeClass("btnTrue");
		$('#btnRegister').addClass("btnFalse");
	}
}
</script>
</head>

<body onload= "document.formSubmit.userId.select() ">
<div id="message" style="display: none;"><span id="error"
	class="error"><s:actionerror /></span> <span class="error"><s:fielderror /></span>
<span class="success"><s:actionmessage /></span>
</div>
<!-- #BeginLibraryItem "/Library/head_portal.lbi" -->
<div id="header">
    <div class="head-logo"></div>
    <div>
        <ul id="head-menu" class="head-btn-noa">
		   	<li class="head-btn-sel" ><a href="index.jsp">首页</a></li>
		   	<li>产品</li>
		   	<li>控制台</li>
		   	<li>用户中心</li>
		</ul>
    </div>
</div>
<!-- #EndLibraryItem -->
<div id="main">
    <div id="indexRight">
        <div id="login">
            <div class="login-content-bg" id="detail_tab" >
                <s:if test="#session.userInfo!=null">
                	<div id="userInfoDiv">
						<div id="userInfoImg">&nbsp;</div>
						<div id="userInfo"><s:property value="#session.userInfo.userName" />
						<a href="console/logout.action">[退出]</a>
						</div>
					</div>
                </s:if>
                <s:else>
                    <s:form id="formSubmit"  name="formSubmit" method="post" action="register.action" >
                        <div id="detail_tab_infos" class="login-tab">
	                        <div >
		                        <h3>注册用户</h3>
	                        </div>
						    <div id="basicInfoDIV" class="contentDetail" style="margin-top: 10px;width:640px">
						    	<div class="result">
						        	<h6 class="label">
						            	<span class="label-bg" style="width:75px;margin:0px 1px;">基本信息</span>
						            </h6>
							            <ul class="result-list">
							            	<li><span class="result-name"><font class="red-font">*</font>用户账号：</span><s:textfield id="userId" name="user.userId" maxlength="20"/></li>
							                <li><span class="result-name"><font class="red-font">*</font>密码：</span><s:password autocomplete="off" id="password" name="user.password" maxlength="1024"/></li>
							                <%-- <li><span class="result-name1">描述信息：<textarea id="desc" name="user.desc" class="uniform"  rows="2" cols="82" style="margin-right: 299px;"/></textarea></span>
							                </li> --%>
							            </ul>
							            <ul class="result-list">
							            	<li><span class="result-name"><font class="red-font">*</font>用户姓名：</span><s:textfield id="userName" name="user.userName" maxlength="10"/></li>
							            	<li><span class="result-name"><font class="red-font">*</font>确认密码：</span><s:password autocomplete="off" id="confirmPassword" name="user.confirmPassword" maxlength="1024"/></li>
							            </ul>
							            <ul class="result-list" style="width: 100%;">
							            	<li><span class="result-name">描述信息：</span><s:textfield id="desc" name="user.desc" maxlength="1024" cssStyle="width: 448px;"/></li>
							            </ul>
						        </div>
						        <div class="result">
						        	<h6 class="label">
						            	<span class="label-bg" style="width:75px;margin:0px 1px;">联系信息</span>
						            </h6>
							            <ul class="result-list">
							            	<li><span class="result-name"><font class="red-font">*</font>手机号码：</span><s:textfield id="mobile" name="user.mobile" maxlength="14"/></li>
							                <li><span class="result-name"><font class="red-font">*</font>邮箱：</span><s:textfield id="email" name="user.email" maxlength="64"/></li>
							            </ul>
							            <ul class="result-list">
							            	<li><span class="result-name">固定电话：</span><s:textfield id="telphone" name="user.telphone" maxlength="20"/></li>
							            	<%--<li><span class="result-name">传真号码：</span><s:textfield id="fax" name="user.fax" maxlength="20"/></li> --%>
							            	<li><span class="result-name">部门名称：</span><s:textfield id="departmentName" name="user.departmentName" maxlength="50"/></li>
							            </ul>
							            <ul class="result-list" style="width: 100%;">
							            	<li><span class="result-name">通讯地址：</span><s:textfield id="address" name="user.address" maxlength="64" cssStyle="width: 448px;"/></li>
							            </ul>
						        </div>
						      <%--   <div class="result">
						        	<h6 class="label">
						            	<span class="label-bg" style="width:75px;margin:0px 1px;">业务信息</span>
						            </h6>
							            <ul class="result-list" style="width: 100%;">
							            	<li>
							            			<span class="result-name"><font class="red-font">*</font>绑定的业务：</span>
							            			<s:textfield  id="app" name="appname" 
							            			  maxlength="64" cssStyle="width:448px;"/>
							            	</li>
							            </ul>
						        </div> --%>
						        <div class="result">
						        	<h6 class="label">
						            	<span class="label-bg" style="width:75px;margin:0px 1px;">密保问题</span>
						            </h6>
						            	<ul class="result-list" style="width:100%">
							            	<li><span class="result-name"><font class="red-font">*</font>密保问题：</span><s:textfield id="security_question" name="user.security_question" maxlength="128" style="color:#aaaaaa" value="例如：您的生日是？"
						            	      onblur="if(this.value==''){this.value='例如：您的生日是？';this.style.color='#aaaaaa'}" onfocus="if(this.value==this.defaultValue){this.value='';this.style.color='#777'}"/></li>
							            </ul>
							            <ul class="result-list" style="width:100%">
							                <li><span class="result-name"><font class="red-font">*</font>密保答案：</span>
						                   		<s:textfield id="security_answer" name="user.security_answer" maxlength="128"/>
						                	</li>
							            </ul>
						        </div>
						        <div class="result" style="margin-top: 16px;">
			                        <input id='read' type="checkbox" onclick="checkRedBox(this)">
			                        &nbsp;&nbsp;&nbsp;我已阅读并同意《自服务门户服务条款》</input>	
		                        </div>
								<div class="btnReg">
		                            <input type="button" id="btnRegister" class="btnFalse" onclick="submitform();return false;" value="提交" disabled="disabled"/>
		                        </div>
						    </div>
                        </div>
                    </s:form>
                </s:else>
            </div>
        </div>
        <!-- login end -->
    </div>
    
	<!-- 业务列表-->
	<s:include value="userBindAppList.jsp" />

	<!-- 服务条款begin -->
	<div id="serviceRule" style="margin-left:730px;margin-top:100px;width:270px;height:430px; position: absolute;border: 1px solid #7f9db9;padding:8px;line-height:20px;overflow:auto; ">
		<table>
			<tr>
				<td align="center">
					<span style="font-size:14px;font-weight:bold;text-align:center"><s:property value="getText('regedit.developer.protocol')" escape="false"/></span>
				</td>
			</tr>
			<tr>
				<td style="font-size:12px;">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本服务条款是四川政企云网站的经营者中国移动通信集团四川有限公司，与用户共同缔结的对双方具有约束力的有效契约。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;四川政企云向用户提供本网站上所展示的产品与服务，并将不断更新服务内容，最新的云服务以本网站上的相关产品及服务介绍的页面展示以及向用户实际提供的为准。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为切实加强移动云接入客户的安全使用和规范管理，促进互联网信息服务活动健康有序发展，维护国家安全和社会稳定，保障社会公众利益和公民合法权益，保障其它客户的合法权益，
					中国移动通信集团公司及中国移动通信有限公司（下文统称中国移动）根据《中华人民共和国计算机信息系统安全保护条例》、
					《中华人民共和国计算机信息网络国际联网管理暂行规定》、《计算机信息网络国际联网安全保护管理办法》和其他相关法律、行政法规的规定，
					用户申请、使用云平台服务时需承诺如下内容：<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一、遵守国家有关法律法规，提供的信息符合国家法律、法规与工业和信息化部等政府部门的各项管理规定以及中国移动《IDC信息安全管理规范》的相关规定，并严格执行上述信息安全管理规定。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;二、不得利用中国移动互联网（CMNET）从事危害国家安全、泄露国家机密等违法犯罪活动，不得利用中国移动互联网（CMNET）制作、查阅、复制和传播违反宪法和法律、妨碍社会治安、破坏国家统一、
					破坏民族团结、色情、暴力等的信息，不得利用中国移动互联网（CMNET）发布任何含有下列内容之一的信息：<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、危害国家安全、泄露国家秘密，侵犯国家的、社会的、集体的荣誉、利益和公民的合法权益，从事违法犯罪活动；<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、煽动抗拒、破坏宪法和法律、行政法规实施的；<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、煽动颠覆国家政权，推翻社会主义制度的；<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4、煽动分裂国家、破坏国家统一的；<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5、煽动民族仇恨、民族歧视，破坏民族团结的；<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6、捏造或者歪曲事实，散布谣言，扰乱社会秩序的；<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7、破坏国家宗教政策，宣扬邪教、封建迷信、淫秽、色情、赌博、暴力、凶杀、恐怖，教唆犯罪的；<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;8、公然侮辱他人或者捏造事实诽谤他人的；<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;9、损害国家机关信誉的；<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;10、其他违反宪法和法律、行政法规的；<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;11、侵犯他人知识产权，传播虚假信息的。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;12、违背社会主义精神文明建设要求、违背中华民族优良文化传统与习惯、以及其它违背社会公德的各类低俗信息。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;13、工业和信息化部、公安部、工商总局、国家互联网信息办公室等国家职能机构、部门禁止发布的各类信息，如银行卡网上非法买卖等。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;三、不得利用中国移动互联网（CMNET）从事以下破坏计算机系统及互联网安全的行为：<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、网络攻击行为<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1）利用自己或他人的机器设备，未经他人允许，通过非法手段取得他人机器设备的控制权；<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	2）非授权访问、窃取、篡改、滥用他人机器设备上的信息，对他人机器设备功能进行删除、修改或者增加；<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3）向其他机器设备发送大量信息包，干扰其他机器设备的正常运行甚至无法工作；或引起网络流量大幅度增加，造成网络拥塞，而损害他人利益的行为；<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4）资源被利用进行网络攻击的行为或由于机器设备被计算机病毒侵染而造成攻击等一切攻击行为。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、淫秽色情信息危害行为<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	1）在网站或业务平台等相关系统发布、传播淫秽色情和低俗信息的行为；<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	2）网站或业务平台引入、分发其他的网站、应用等内容，由于管理、手段缺失，而导致引入、分发内容包含色情、低俗等不良信息的行为。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、计算机病毒危害行为<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	1）有意通过互联网络传播计算机病毒；<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	2）因感染计算机病毒进而影响网络和其它客户正常使用的行为。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4、发送或协助发送垃圾邮件行为<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	1）故意向未主动请求的客户发送或中转电子邮件广告刊物或其他资料；<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	2）故意发送没有明确的退信方法、发信人、回信地址等的邮件；<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	3）邮件服务器存在安全漏洞或开放OPENRELAY功能导致被利用转发垃圾邮件；<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	4）冒名发送恶意邮件（违反国家法律法规的）；<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	5）其它任何协助垃圾邮件发送行为、可能会导致影响网络正常运营及其它客户正常使用的邮件等。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5、手机恶意软件危害行为<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	1）服务器作为手机应用软件下载源所提供的下载列表中含有违规恶意软件，或者直接作为手机恶意软件的下载源，通过互联网传播手机恶意软件；<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	2）作为手机恶意软件控制服务器，对中毒用户手机进行远程控制，进行下发远程指令、收集用户隐私、不知情定制业务造成扣费等恶意行为。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中国移动通信集团四川有限公司作为云服务经营者，同时负有监管责任，具有以下管理权力：<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	一、中国移动发现上述违法犯罪活动和有害信息，将立即采取措施制止并及时向有关主管部门报告。对于其它破坏中国移动网络信息安全或违反国家相关法律法规规定的行为，本服务条款同样适用。<br>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	二、中国移动有权在中国移动接入的业务网站、即时通信工具、网盘、视频等进行全面检查、逐一过滤，彻底清理涉暴恐音视频等存量有害信息，关闭传播恐怖宣传视频的注册账户，并建立清理暴恐音视频工作台账。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;三、中国移动采取必要手段加强网站/平台内容监测、审核、拦截，一经发现涉暴恐音视频等有害信息，立即清理，并及时上报国家相关部门。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;四、中国移动作为电信运营商，有按要求配合国家有关部门处理互联网网络与信息安全工作的义务，包括但不限于提供客户基础资料、采取技术措施处理等。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;五、“依据《非经营性互联网备案管理办法》第二十三条规定，如备案信息不真实，将关闭网站并注销备案。入驻客户承诺并确认：提交的所有备案信息真实有效，
					当备案信息发生变化时（包含在二级域名的使用过程中，对应的一级域名发生变更或取消接入等）请及时与中国移动备案部门联系并提交更新信息，
					如因未及时更新而导致备案信息不准确，我公司有权依法对接入网站进行关闭处理。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;六、如有其它影响网络安全和信息安全的突发事件，中国移动有权采取紧急措施，以保证网络安全。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本服务条款之效力、解释、变更、执行与争议解决均适用中华人民共和国法律。因本服务条款产生之争议，均应依照中华人民共和国法律予以处理。<br>
				</td>
			</tr>
		</table>
	</div>
	<!-- 服务条款end -->
</div>
<div class="page-form" id="securityQuestionDIV" style="display:none;">
    <div class="field">
       		 注册申请已提交，请等待审批！
    </div>
</div>
<div id="footer">版权所有&copy;中国移动通信集团四川有限公司</div>
</body>
</html>