<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>登录</title>
<link rel="stylesheet" type="text/css" href="css/zg.css" />
<script src="js/jquery-1.12.4.js" type="text/javascript" charset="utf-8"></script>
<script src="js/browserDetect.js" type="text/javascript" charset="utf-8"></script>
<script src="scripts/common.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	if (BrowserDetect.version <= 8 && BrowserDetect.browser == "Explorer") {
		window.location.href = "404.html";
	}
</script>
</head>

<body>
	<header class="bg-black bottom-border">
		<div>
			<a class="logo-link" href="home.jsp"><img width="155px" height="50px"
				class="logo" src="img/logo.png" alt="" /><span>四川政企云服务</span></a>
			<nav>
				<ul class="login">
					<li class="login-btn"><a href="#">登录</a></li>
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
				<h3>账号登录</h3>
				<div id="message" style="display: none;">
					<span id="error" class="error"><s:actionerror /></span> <span
						class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
				</div>
				<form id="formSubmit" name="formSubmit" method="post"
					action="login.action">
					<div id="userDiv">
						<i></i><input type="text" id="userId" value="" placeholder="用户"
							autocomplete="off">
						<s:hidden id="aesUserId" name="aesUserId" />
					</div>
					<div id="passwordDiv">
						<i></i><input type="password" name="password" id="password"
							value="" placeholder="密码" autocomplete="off">
					</div>
					<div id="verifyDiv">
						<input type="text" name="verify" id="verify" size="4"
							maxlength="4" autocomplete="off" placeholder="验证码">
						<div id="code"></div>
					</div>
					<a id="submit" href="javascript:void(0)">登 录</a>
					<div id="regDiv">
						<a href="forgetPassword.jsp ">忘记密码？</a> <a href="myregister.jsp ">注册用户</a>
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
	<%-- 	<script src="js/gVerify.js"></script>
 --%>
	<!--[if lte IE 9]>
		<script src="js/jquery.placeholder.min.js" type="text/javascript" charset="utf-8"></script>
		<script>
				$(function() {
				$('input').placeholder();
			});
		</script>
		<![endif]-->
	<script type="text/javascript" src="scripts/lib/md5.js"></script>
	<script type="text/javascript" src="scripts/lib/aes.js"></script>
	<script type="text/javascript">
		$(function() {
			init();
			freshCode();
		});
		//刷新验证码
		function freshCode() {
			$.get('createVerifyTextServlet?type=blend', function(res) {
				var txtArr = [];
				txtArr = res.split("");
				drawCode(txtArr);
			});
		}

		//提交按钮
		$('#submit').click(
				function() {
					var userId = $('#userId').val();
					var password = $('#password').val();
					var verify = $('#verify').val();

					if (null == verify || verify == "") {
						$.compMsg({
							type : 'error',
							msg : "验证码不能为空，请重新输入！"
						});
						$('#verify').focus().select();
						return false;
					} else {
						if (null == userId || null == password || userId == ""
								|| password == "") {
							$.compMsg({
								type : 'error',
								msg : "用户账号和密码不能为空，请重新输入！"
							});
							return false;
						} else {
							$('#password').val(hex_md5(password));
							$("#aesUserId").val(Encrypt(userId));
						}
					}
					$('#formSubmit').submit();
				});

		function init() {
			var con = document.getElementById('code');
			var canvas = document.createElement("canvas");
			canvas.id = 'verifyCanvas';
			canvas.width = 150;
			canvas.height = 50;
			canvas.style.cursor = "pointer";
			canvas.innerHTML = "您的浏览器版本不支持canvas";
			con.appendChild(canvas);
		}

		$('#code').click(function() {
			freshCode();
		});

		function drawCode(txtArr) {

			var canvas = document.getElementById('verifyCanvas');
			if (canvas.getContext) {
				var ctx = canvas.getContext('2d');
			}
			ctx.textBaseline = "middle";

			ctx.fillStyle = randomColor(180, 240);
			ctx.fillRect(0, 0, 150, 50);

			for (var i = 1; i <= 4; i++) {
				var txt = txtArr[i - 1];
				ctx.font = randomNum(50 / 2, 50) + 'px SimHei'; //随机生成字体大小
				ctx.fillStyle = randomColor(50, 160); //随机生成字体颜色		
				ctx.shadowOffsetX = randomNum(-3, 3);
				ctx.shadowOffsetY = randomNum(-3, 3);
				ctx.shadowBlur = randomNum(-3, 3);
				ctx.shadowColor = "rgba(0, 0, 0, 0.3)";
				var x = 150 / 5 * i;
				var y = 50 / 2;
				var deg = randomNum(-30, 30);
				/**设置旋转角度和坐标原点**/
				ctx.translate(x, y);
				ctx.rotate(deg * Math.PI / 180);
				ctx.fillText(txt, 0, 0);
				/**恢复旋转角度和坐标原点**/
				ctx.rotate(-deg * Math.PI / 180);
				ctx.translate(-x, -y);
			}
			/**绘制干扰线**/
			for (var i = 0; i < 4; i++) {
				ctx.strokeStyle = randomColor(40, 180);
				ctx.beginPath();
				ctx.moveTo(randomNum(0, 150), randomNum(0, 50));
				ctx.lineTo(randomNum(0, 150), randomNum(0, 50));
				ctx.stroke();
			}
			/**绘制干扰点**/
			for (var i = 0; i < 150 / 4; i++) {
				ctx.fillStyle = randomColor(0, 255);
				ctx.beginPath();
				ctx.arc(randomNum(0, 150), randomNum(0, 50), 1, 0, 2 * Math.PI);
				ctx.fill();
			}
		}

		/**生成一个随机数**/
		function randomNum(min, max) {
			return Math.floor(Math.random() * (max - min) + min);
		}

		/**生成一个随机色**/
		function randomColor(min, max) {
			var r = randomNum(min, max);
			var g = randomNum(min, max);
			var b = randomNum(min, max);
			return "rgb(" + r + "," + g + "," + b + ")";
		}

		$('input').bind('keyup', function(event) {
			if (event.keyCode == "13") {
				//回车执行查询
				$('#submit').click();
			}
		});
	</script>
</body>

</html>