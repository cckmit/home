<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>忘记密码</title>
<link rel="stylesheet" type="text/css" href="css/zg.css" />
<script src="js/jquery-1.12.4.js" type="text/javascript" charset="utf-8"></script>
<script src="js/browserDetect.js" type="text/javascript" charset="utf-8"></script>
<script src="scripts/common.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript"
	src="scripts/pagesjs/forgetPassword/forgetPassword.js"></script>
<script>
	if (BrowserDetect.version <= 8 && BrowserDetect.browser == "Explorer") {
		window.location.href = "404.html";
	}
	$(function() {
		$("#userId").select();
	});
</script>
<style>
.btn {
	display: block;
	text-align: center;
	width: 500px;
	height: 50px;
	line-height: 50px;
	color: #fff;
	background-color: #009DD9;
	text-decoration: none;
	font-size: 24px;
	margin-bottom: 30px;
}

.code {
	width: 150px;
	height: 50px;
	float: right;
	font-size: 28px;
}

#navBar ul {
	list-style: none;
}

#navBar ul li {
	float: left;
	margin-top: 30px;
	margin-right: 50px;
}
</style>
</head>

<body>
	<header class="bg-black bottom-border">
		<div>
			<a class="logo-link" href="home.jsp"><img width="155px"
				height="50px" class="logo" src="img/logo.png" alt="" /><span>四川政企云服务</span></a>
			<nav>
				<ul class="login">
					<li class="login-btn"><a href="login.jsp">登录</a></li>
				</ul>
			</nav>
		</div>
	</header>
	<main>
	<section class="login-sec">
		<div class="login-bg">
			<div class="index-content">
				<h2>先进的公有云服务提供平台</h2>
				<p>快速、稳定、可靠</p>
				<p>简单上手，轻轻松松搭建应用</p>
				<p>价格更低，效果更高</p>
			</div>
			<div class="login-panel">
				<h3>忘记密码</h3>
				<div id="message" style="display: none;">
					<span class="error"><s:actionerror /></span> <span class="error"><s:fielderror /></span>
					<span class="success"><s:actionmessage /></span>
				</div>
				<form id="formSubmit" name="formSubmit" method="post" action="">

					<div id="validateUserIdDiv" style="display: bolock;">
						<div id="userDiv">
							<i></i><input type="text" id="userId" value="" placeholder="用户"
								autocomplete="off">
							<s:hidden id="aesUserId" name="aesUserId" />
						</div>
						<div id="verifyDiv">
							<input type="text" name="verify" id="verify" size="4"
								maxlength="4" autocomplete="off" placeholder="验证码"> <a
								href="javascript:show()"> <img class="code" id="hcode"
								alt="刷新" />
							</a>
						</div>
						<a class="btn" href="javascript:void(0)"
							onclick="nextStep();return false;">下一步</a>
					</div>



					<div id="validateQuestionDiv" style="display: none;">
						<div id="userDiv">
							<span>密保问题：</span><span id="questionSpan"></span>
						</div>
						<div id="passwordDiv">
							<i></i><input type="text" id="answer" name="answer" value=""
								placeholder="密保答案" autocomplete="off">
						</div>
						<div style="text-align: center; padding-bottom: 20px;">
							<font class="red-font" style="margin-left: 87px;"><s:property
									value="errMsg" /><label id="errQuestion"></label></font>
						</div>
						<a class="btn" href="javascript:void(0)"
							onclick="findPassword();return false;">下一步</a>
					</div>

					<div id="resetPWDiv" style="display: none;">

						<div id="passwordDiv">
							<i></i><input type="password" id="userPassword"
								name="userInfo.password" value="" placeholder="新密码"
								autocomplete="off">
						</div>
						<div id="passwordDiv">
							<i></i><input type="password" id="confirmUserPassword"
								name="confirmUserPassword" value="" placeholder="确认密码"
								autocomplete="off">
						</div>
						<div id="verifyDiv">
							<input type="text" id="verify2" name="verify2" size="5"
								maxlength="4" autocomplete="off" placeholder="验证码"> <a
								href="javascript:show()"> <img class="code" id="hcode1"
								alt="刷新" />
							</a>
						</div>
						<a class="btn" href="javascript:void(0)"
							onclick="submitform();return false;">提交</a>
					</div>

					<div id="editSuccessDiv" style="display: none;">
						<div>
							<span style="margin-left: 13px;">&nbsp;</span>
							<div id="skipDiv"></div>
						</div>
						<div>
							<font class="red-font" style="margin-left: 87px;"><label
								id="errEdit"></label></font>
						</div>
						<div class="btn">
							<input type="submit" style="cursor: pointer;"
								onclick="login();return false;" value="立即登录" />
						</div>
					</div>
					<div id="navBar">
						<ul>
							<li id="loginPwLi">1、输入账号</li>
							<li id="questionLi">2、密保问题</li>
							<li id="resetPwLi">3、密码重置</li>
						</ul>
					</div>
				</form>
			</div>
		</div>
	</section>
	</main>
	<footer>
		<div class="container">
			<div class="link">
				<div class="call-center">
					<img src="img/center.png" alt="" />
					<h3>
						售前咨询电话<br />4001099199
					</h3>
					<h3 class="worktime">工作时间</h3>
					<p>工作日 8:30～17：30</p>
				</div>
				<div class="linkdoc">
					<dl>
						<dt>
							<img src="img/help.png" />新手指南
						</dt>
						<dd>
							<ul>
								<li><a href="">注册/登录</a></li>
								<li><a href="">常见问题</a></li>
								<li><a href="">了解产品</a></li>
							</ul>
						</dd>
					</dl>
					<dl>
						<dt>
							<img src="img/about.png" />用户相关
						</dt>
						<dd>
							<ul>
								<li><a href="">控制台</a></li>
								<li><a href="">订单管理</a></li>
								<li><a href="">ICP备案</a></li>
							</ul>
						</dd>
					</dl>
					<dl>
						<dt>
							<img src="img/partner.png" />合作伙伴
						</dt>
						<dd>
							<ul>
								<li><a href="">合作伙伴登录</a></li>
								<li><a href="">申请加入</a></li>
								<li><a href="">合作流程</a></li>
								<li><a href="">合作条件</a></li>
							</ul>
						</dd>
					</dl>
					<dl>
						<dt>
							<img src="img/support.png" />支持与帮助
						</dt>
						<dd>
							<ul>
								<li><a href="">平台使用手册</a></li>
								<li><a href="">人工在线帮助</a></li>
								<li><a href="">常见问题</a></li>
								<li><a href="">通知公告</a></li>
							</ul>
						</dd>
					</dl>
				</div>

			</div>
			<div class="warning">
				<h3>
					法律声明<br />再您开始访问，浏览和使用本网站，请仔细阅读本声明的所有条款。一旦浏览，使用本网站表示您已经同意接受本声明条款。<a
						href="">了解更多</a>
				</h3>
				<p1>中国移动通信集团四川有限公司.保留所有权利.|蜀ICP备09015892号-31</p1>
			</div>
		</div>
	</footer>
</body>

</html>