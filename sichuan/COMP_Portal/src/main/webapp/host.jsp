<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>云主机</title>
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
								<!-- <a href="situation_factory.jsp">工业行业解决方案</a> -->
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
				<img src="img/server-icon01.png" width="65px" class="title-icon" />
				<h1 class="title">云主机</h1>
			</div>

			<p>云主机是在一组集群主机上虚拟出多个类似独立主机的部分，云服务器具有自助管理、数据安全保障、自动故障恢复和防网络攻击等高级功能，能够帮您简化开发部署过程，降低运维成本，构建按需扩展的应用，从而更适应快速多变的业务需求。
			</p>
			<a href="host_recommend.jsp" class="blue-btn">推荐套餐</a> <a
				href="console/vmApplyAction.action" class="white-btn">立即购买</a>
		</div>
		<img src="img/banner-03.png" class="title-right" />
		</div>
	</section>
	<section class="content dark-section">
		<div class="content-detail">
			<div class="title-dash">
				<h2>云主机概要</h2>
				<i></i>
			</div>
			<p style="margin-bottom: 50px;">云主机的概念：</p>
			<p style="margin-bottom: 50px;">云主机是新一代的主机租用服务，它整合了高性能服务器与优质网络带宽，有效解决了传统主机租用价格偏高、服务品参差不齐等缺点，可全面满足中小企业、个人站长用户对主机租用服务低成本，高可靠，易管理的需求。</p>
			<p style="margin-bottom: 50px;">云主机的在互联网的地位：</p>
			<p style="margin-bottom: 50px;">云主机是云计算在基础设施应用上的重要组成部分，位于云计算产业链金字塔底层，产品源自云计算平台。云计算平台整合了互联网应用三大核心要素：计算、存储、网络，面向用户提供公用化的互联网基础设施服务。</p>
		</div>
			<div class="content-detail">
				<div class="title-dash">
					<h2>产品优势</h2>
					<i></i>
				</div>
				<div class="content-box">
					<h3>快速部署</h3>
					<p>数秒内便可创建云主机并投入使用，提高业务部署及上线速度。</p>
				</div>
				<div class="content-box">
					<h3>安全保障</h3>
					<p>提供密钥认证、安全组防护、防火墙防护、多用户隔离等手段，确保业务安全。</p>
				</div>
				<div class="content-box">
					<h3>稳定可靠</h3>
					<p>云主机可用性不低于99.95%，提供宕机迁移、数据备份和恢复等功能，确保业务稳定。</p>
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