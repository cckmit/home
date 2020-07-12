<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>云硬盘</title>
<link rel="stylesheet" type="text/css" href="css/zg.css" />
<script src="js/jquery-1.12.4.js" type="text/javascript" charset="utf-8"></script>
<script src="js/browserDetect.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	if (BrowserDetect.version <= 8 && BrowserDetect.browser == "Explorer") {
		window.location.href = "404.html";
	}
	$(function() {
		$('.user-info-tab').load('userInfoTab.jsp');
	});
</script>
</head>

<body>
	<header class="header-bgcolor bottom-border">
		<div>
			<a class="logo-link" href="home.jsp"><img width="155px" height="50px"
				class="logo" src="img/logo.png" alt="" /><span>四川政企云服务</span></a>
			<nav>
				<ul class="header-list">
					<li class="list-nav"><a href="home.jsp">首页</a></li>
					<li class="list-nav product-nav"><a href="">产品</a>
						<div class="sub-nav">
							<a href="host.jsp">云主机</a> <a href="ebs.jsp">云硬盘</a> <a href="">云网络</a>
						</div></li>
					<li class="list-nav solution-nav"><a href="">应用场景</a>
						<div class="sub-nav">
						<a href="situation_education.jsp">教育行业解决方案</a>
								<a href="situation_company.jsp">政企云解决方案</a>
								<a href="situation_medical.jsp">医疗行业解决方案</a>
								<a href="situation_banking.jsp">金融行业解决方案</a>
								<a href="situation_factory.jsp">工业行业解决方案</a>
						</div></li>
				</ul>

				<ul class="login">
					<a href="console/resourcesOverviewAction.action" class="mini-logo">
						<img height='25px' width='25px' src="img/ico-console.png" />
						<p>控制台</p>
					</a>
					<s:if test="#session.userInfo!=null">
						<li class="user"><a href="#" class="user-head"> <img
								style="width: 50px; height: 50px;" src="img/head.png" />
						</a></li>
						<div class="user-info-tab none"></div>
					</s:if>
					<s:else>
						<li class="login-btn"><a href="login.jsp">登录</a></li>
					</s:else>
				</ul>
			</nav>
		</div>
	</header>
	<main>
	<section class="title">
		<div class="title-detail title-left">
			<div>
				<img src="img/ebs-icon01.png" width="65px" height="58px"
					class="title-icon" />
				<h1 class="title">云硬盘</h1>
			</div>

			<p>云硬盘是提供持久性数据块级存储。每个云硬盘在其可用区内自动复制，云硬盘中的数据在可用区内以多副本冗余方式存储，避免数据的单点故障风险。云硬盘为您提供处理工作所需的稳定可靠低延迟存储，通过云硬盘，您可在几分钟内调整存储容量，且所有这些您只需为配置的资源量支付低廉的价格。</p>
			<a href="host_recommend.jsp#ebs-recommend" class="blue-btn">推荐套餐</a> <a href="console/ebsApplyAction.action" class="white-btn">立即购买</a>
		</div>
		<img src="img/banner-04.png" class="title-right" />
		</div>
	</section>
	<section class="content dark-section">
		<div class="content-detail">
			<div class="title-dash">
				<h2>云硬盘概要</h2>
				<i></i>
			</div>

			<p>云硬盘是提供持久性数据块级存储。每个云硬盘在其可用区内自动复制，云硬盘中的数据在可用区内以多副本冗余方式存储，避免数据的单点故障风险。云硬盘为您提供处理工作所需的稳定可靠低延迟存储，通过云硬盘，您可在几分钟内调整存储容量，且所有这些您只需为配置的资源量支付低廉的价格。</p>
		</div>
		<div class="content-detail">
			<div class="title-dash">
				<h2>产品优势</h2>
				<i></i>
			</div>
			<div class="content-box">
				<h3>高可靠性</h3>
				<p>具有更高的数据可靠性，更高的I/O吞吐。</p>
			</div>
			<div class="content-box">
				<h3>灵活快速</h3>
				<p>根据用户需求，有效预测业务负载的变化趋势，只能、快速的应对各种业务变化场景。</p>
			</div>
			<div class="content-box">
				<h3>稳定使用</h3>
				<p>提供实时监控工具，对发生故障的服务实例进行快速恢复和切换。</p>
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