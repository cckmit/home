<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>应用场景--医疗</title>
<link rel="stylesheet" type="text/css" href="css/zg.css" />
<script src="js/jquery-1.12.4.js" type="text/javascript" charset="utf-8"></script>
<script src="js/browserDetect.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	if (BrowserDetect.version <= 8 && BrowserDetect.browser == "Explorer") {
		window.location.href = "404.html";
	}
	$(function() {
		$('div.situation-content >a').click(function() {
			$(this).addClass("active").siblings().removeClass("active");
			var $content = $('#' + $(this).data("id"));
			$content.removeClass('none').siblings('div').addClass('none');
		});
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
		style="background: url(img/situation-bg03.png) no-repeat; background-size: cover;">
		<div class="title-detail title-left">
			<div>
				<img src="img/situation-icon03.png" width="65px" height="58px"
					class="title-icon" />
				<h1 class="title ">医疗行业解决方案</h1>
			</div>

			<p>基于计算、存储、网络、安全和大数据服务，构建医疗服务平台，加快医疗信息化规模建设，促进医疗机构与患者、以及医疗机构之间的深度连接，助力医疗创新。</p>

		</div>
		<img src="img/banner-08.png" class="title-right"
			style="right: 60px; bottom: 30px; width: 40%;" />
	</section>
	<section class="content dark-section">
		<div class="content-detail">
			<div class="title-dash">
				<h2>架构描述</h2>
				<i></i>
			</div>
			<div class="situation-content">
				<a class="desc active" href="javascript:void(0);" data-id="a">在线挂号与问诊</a>
				<a class="desc" href="javascript:void(0);" data-id="b">医疗影像海量存储</a>
				<a class="desc" href="javascript:void(0);" data-id="c">医疗智能硬件与医疗大数据</a>
				<div id="a" class="">
					<p>用户使用移动云产品搭建在线挂号与远程问诊系统，患者可以通过该系统进行在线预约挂号，并支持在线问诊：</p>
					<ul>
						<li><i></i>购买弹性负载均衡产品，实现系统业务在不同云主机之间分担，构建web服务和应用服务集群，并部署弹性伸缩服务，应对突发业务流量。</li>
						<li><i></i>对网站进行动静分离，动态关系型数据使用RDS集群进行存储和处理，并部署缓存服务器，将热点数据进行缓存，提高热点数据读取性能。</li>
						<li><i></i>系统将图片、视频等静态文件存储在移动云云存储产品。</li>
					</ul>
					<img style="width: 100%;" src="img/medical.png" />
				</div>
				<div id="b" class="none">
					<ul>
						<li><i></i>在移动云上为医院用户部署一个隔离的虚拟私有云（VPC）环境，客户可在虚拟私有云内规划 IP
							地址范围、划分子网、配置网络访问等。</li>
						<li><i></i>通过专线/VPN等方式完成与医院内部管理系统连接，实现对医疗影像文件的管理。</li>
						<li><i></i>使用移动云的云存储产品，实现影像文件的海量存储空间，与传统存储相比，移动云云存储具备海量空间、弹性扩展等特点，另外云存储使用三副本冗余机制，具备高可靠性。</li>
					</ul>
					<img style="width: 100%;" src="img/medical02.png" />
				</div>
				<div id="c" class="none">
					<ul>
						<li><i></i>智能硬件终端通过各种网络环境（4G、WiFi）接入移动云大规模数据计算与分析服务集群，将收集到的人体健康数据存储和分析。</li>
						<li><i></i>专业医疗人员通过数据提取及业务应用集群，对数据进行分析，评价人体健康状态，并及时反馈给终端用户。</li>
					</ul>
					<img style="width: 100%;" src="img/medical03.png" />
				</div>
			</div>
			<div class="situation-box">
				<i class="lb-icon"></i>
				<h3>弹性负载均衡</h3>
				<p>移动云弹性负载均衡产品，提供负载均衡服务，将应用请求分布到多台云主机上，并检查云主机健康状况，提高业务处理能力，实现网站HA</p>
			</div>
			<div class="situation-box">
				<i class="host-icon"></i>
				<h3>云主机</h3>
				<p>移动云主机产品，用户可以根据业务需求租用不同规格云主机搭建Web服务集群和应用服务器</p>
			</div>
			<div class="situation-box">
				<i class="ebs-icon"></i>
				<h3>云储存</h3>
				<p>移动云云储存，为用户提供海量的可扩展的文件存储空间服务。</p>
			</div>
			<div class="situation-box">

				<i class="disk-icon"></i>
				<h3>云硬盘</h3>
				<p>移动云主机产品，基于高性能的磁盘阵列设备，为用户提供高性能存储服务，用户可购买块存储产品挂载载移动云主机上实现云主机的存储扩展，并可构建相应业务系统，如“文件系统”等</p>
			</div>
			<div class="situation-box">
				<i class="monitor-icon"></i>
				<h3>云监控</h3>
				<p>移动云主机产品，用户可以根据业务需求租用不同规格云主机搭建Web服务集群和应用服务器</p>
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