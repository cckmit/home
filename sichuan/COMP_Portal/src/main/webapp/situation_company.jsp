<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>应用场景--政企</title>
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
		style="background: url(img/situation-bg02.png) no-repeat; background-size: cover;">
		<div class="title-detail title-left">
			<div>
				<img src="img/situation-icon02.png" width="65px" height="58px"
					class="title-icon" />
				<h1 class="title ">政企云解决方案</h1>
			</div>

			<p>在整合IT和通信能力的基础上，移动云以强安全性和高稳定性作为平台发展目标，推出了电子政务云解决方案，通过移动云降低政府信息化建成本，加强信息资源整合，促进资源共享，加快服务转型。</p>
		</div>
		<img src="img/banner-07.png" class="title-right"
			style="right: 60px; bottom: 80px; width: 40%;" />
	</section>
	<section class="content dark-section">
		<div class="content-detail">
			<div class="title-dash">
				<h2>架构描述</h2>
				<i></i>
			</div>
			<div class="situation-content">
				<ul>
					<li><i></i>建设政府多场景模块化机房，形成数据中心容灾解决方案。</li>
					<li><i></i>开放政务云平台，业务快速云化迁移。</li>
					<li><i></i>政务云管理平台，租户自服务自管理。</li>
					<li><i></i>虚拟安全方案，部委租户差异化安全服务。</li>
				</ul>
				<img style="width: 100%;" src="img/company.png" />
			</div>

			<div class="title-dash">
				<h2>整体交付能力</h2>
				<i></i>
			</div>
			<p style="font-size: 18px; padding-left: 40px; margin-bottom: 100px;">移动云聚合资源，为政府部门提供整合交付能力：</p>
			<div class="situation-box">
				<i class="lb-icon"></i>
				<h3>弹性负载均衡</h3>
				<p>移动云弹性负载均衡产品，提供负载均衡服务，将应用请求分布到多台云主机上，并检查云主机健康状况，提高业务处理能力，实现网站HA</p>
			</div>
			<div class="situation-box">
				<i class="host-icon"></i>
				<h3>云主机</h3>
				<p>移动云主机产品，用户可以根据业务需求，租用不同规格云主机搭建Web服务集群和应用服务集群，也可以用来部署缓存服务器，提升网站性能</p>
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