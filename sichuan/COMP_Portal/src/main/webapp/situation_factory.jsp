<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>应用场景--工业</title>
<link rel="stylesheet" type="text/css" href="css/zg.css" />
<style type="text/css">
.row {
	margin-left: -30px;
	margin-right: -30px;
	overflow: hidden;
	margin-bottom: 40px;
}

.col {
	width: 33%;
	padding: 0 30px;
	float: left;
	box-sizing: border-box;
}

.box {
	height: auto;
	background: #fff;
}

.block-title {
	text-align: center;
	background-color: #9ca0ac;
	font-size: 20px;
	font-weight: bold;
	line-height: 55px;
	height: 55px;
}

.block-content {
	text-align: left;
	font-size: 14px;
	line-height: 20px;
	min-height: 160px;
	padding: 24px 20px;
	box-sizing: border-box;
}

.situation-content h4 {
	font-size: 18px;
	line-height: 36px;
	margin-bottom: 20px;
}

.situation-content h4:before {
	content: "";
	width: 10px;
	height: 10px;
	background-color: #009dd9;
	display: inline-block;
	margin-top: 8px;
	margin-right: 35px;
}

.content-text {
	padding-left: 45px;
}

.content-text span {
	display: inline-block;
	font-size: 18px;
	margin-right: 100px;
	margin-bottom: 40px;
}

.content-text>div {
	width: 130px;
	text-align: left;
	float: left;
	margin-right: 70px;
	height: 70px;
	margin-bottom: 40px;
	border-right: 1px solid #919191;
}

.content-text>div>p {
	font-size: 20px;
	line-height: 20px;
}

.content-text>div>p:first-child {
	font-size: 18px;
	line-height: 20px;
	margin-bottom: 30px;
	color: #9a9a9a;
}
</style>
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
	<header class=" bottom-border">
		<div>
			<a class="logo-link" href="home.jsp"><img width="155px"
				height="50px" class="logo" src="img/logo.png" alt="" /><span>四川政企云服务</span></a>
			<nav>
				<ul class="header-list">
					<li class="list-nav"><a href="home.jsp">首页</a></li>
					<li class="list-nav product-nav"><a href="">产品</a>
						<div class="sub-nav">
							<a href="host.jsp">云主机</a> <a href="ebs.jsp">云硬盘</a> <a href="">云网络</a>
						</div></li>
					<li class="list-nav solution-nav"><a href="">应用场景</a>
						<div class="sub-nav">
							<a href="situation_education.jsp">教育行业解决方案</a> <a
								href="situation_company.jsp">政企云解决方案</a> <a
								href="situation_medical.jsp">医疗行业解决方案</a> <a
								href="situation_banking.jsp">金融行业解决方案</a> <!-- <a
								href="situation_factory.jsp">工业行业解决方案</a> -->
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
	<section class="title situation-title"
		style="background: url(img/situation-bg05.png) no-repeat; background-size: cover;">
		<div class="title-detail title-left">
			<div>
				<img src="img/situation-icon05.png" width="65px" height="58px"
					class="title-icon" />
				<h1 class="title ">工业行业解决方案</h1>
			</div>

			<p>工业、制造业客户SCADA系统云上解决方案。</p>

		</div>
		<img src="img/banner-10.png" class="title-right"
			style="right: 60px; bottom: 30px; width: 40%;" />
	</section>
	<section class="content dark-section">
		<div class="content-detail">
			<div class="title-dash">
				<h2>架构描述</h2>
				<i></i>
			</div>
			<div class="situation-content">
				<h3>工业控制SCADA系统云上解决方案</h3>
				<p style="font-size: 18px; margin-top: 20px; padding-left: 45px;">按需购买、快速搭建SCADA系统，实现远程采集与集中调度</p>
				<h4>本架构能够解决</h4>
				<div class="content-text">
					<span>远程运维</span><span>统一监控/升级</span><span>数据采集量增加实时扩展</span><span>OPC
						UA总线</span>
				</div>
				<h4>支持最高性能</h4>
				<div class="content-text">
					<div>
						<p>开通</p>
						<p>数分钟</p>
					</div>
					<div>
						<p>配置扩容</p>
						<p>弹性伸缩</p>
					</div>
					<div>
						<p>并发</p>
						<p>百万级</p>
					</div>

				</div>
				<div style="clear: both;"></div>

				<h4>主要云产品</h4>
				<div class="content-text">
					<span>云主机</span><span>弹性块存储</span><span>云储存</span><span>负载均衡</span>
				</div>
				<img style="width: 100%;" src="img/factory.png" />
			</div>
			<div class="title-dash">
				<h2>方案优势</h2>
				<i></i>
			</div>
			<div class="situation-content bg-transparent height-auto pt0">
				<div class="row">
					<div class="col">
						<div class="box">
							<div class="block-title">稳定可靠</div>
							<div class="block-content">云服务器自动宕机迁移，云监控实时监控系统性能并以多种方式及时通知运维人员，最大程度的保障系统的高可用。</div>
						</div>
					</div>
					<div class="col">
						<div class="box">
							<div class="block-title">弹性伸缩</div>
							<div class="block-content">利用弹性扩展能力，根据数据采集量增加（传感器系统升级）而实时扩展
								免去传统系统IT采购部署环节</div>
						</div>
					</div>
					<div class="col">
						<div class="box">
							<div class="block-title">快速部署</div>
							<div class="block-content">利用自定义镜像实现快速部署
								基于阿里云平台构建的SCADA系统，将传统部署、运维工作放在云上，方便客户统一管理 实现远程运维，统一监控，统一升级</div>
						</div>
					</div>
				</div>
			</div>
			<div style="clear: both;"></div>
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