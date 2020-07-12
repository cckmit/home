<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>应用场景--金融</title>
<link rel="stylesheet" type="text/css" href="css/zg.css" />
<style type="text/css">
ul.content-blocks {
	clear: both;
	width: 100%;
}

ul.content-blocks>li.block {
	background-color: #3d465d;
	display: inline-block;
	float: left;
	width: 19%;
	height: 55px;
	line-height: 55px;
	color: #fff;
	text-align: center;
	margin-right: 1%;
	padding: 0;
}

ul.content-blocks>li:first-child {
	background-color: #009DD9;
}

h3.title-square {
	font-size: 18px;
	line-height: 36px;
	color: #009DD9;
	margin-bottom: 10px;
}

h3.title-square:before {
	content: '';
	width: 10px;
	height: 10px;
	display: inline-block;
	margin-right: 5px;
	background-color: #009DD9;
	-webkit-transform: rotate(45deg);
	-moz-transform: rotate(45deg);
	-ms-transform: rotate(45deg);
	-o-transform: rotate(45deg);
	transform: rotate(45deg);
}

.row {
	margin-left: -15px;
	margin-right: -15px;
	overflow: hidden;
	margin-bottom: 40px;
}

.col {
	width: 25%;
	padding: 0 15px;
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
	height: 100px;
	padding: 24px 20px;
	box-sizing: border-box;
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
		style="background: url(img/situation-bg04.png) no-repeat; background-size: cover;">
		<div class="title-detail title-left">
			<div>
				<img src="img/situation-icon04.png" width="65px" height="58px"
					class="title-icon" />
				<h1 class="title ">金融行业解决方案</h1>
			</div>

			<p>针对金融行业现状，构建从物理部署、基础服务、增值服务到客户服务的金融云专属技术方案，帮助金融客户构建低成本、高弹性、高可用、高可靠、安全合规的云计算IT系统，实现业务互联网化，助力金融客户业务创新。</p>

		</div>
		<img src="img/banner-09.png" class="title-right"
			style="right: 0; bottom: 30px;" />
	</section>
	<section class="content dark-section">
		<div class="content-detail">
			<div class="title-dash">
				<h2>金融信息化痛点及需求</h2>
				<i style="width: 70%;"></i>
			</div>
			<div class="situation-content bg-transparent height-auto">
				<ul>
					<li><i></i>互联网业务飞速发展，用户行为模式发生了巨大转变，业务增速难以预测，现有IT架构难以支撑互联网业务发展。</li>
					<li><i></i>互联网业务波动范围大，IT系统面临业务峰值时难以扩容，业务低谷时设备闲置，IT资源利用率低。</li>
					<li><i></i>金融业务系统网点众多，业务系统复杂，网点互联和用户接入要求高且复杂。</li>
					<li><i></i>金融业务整体IT建设标准高，对安全要求严格，需具备异地容灾、安全合规的监管要求。</li>
				</ul>
			</div>
			<div class="title-dash">
				<h2>金融解决方案架构</h2>
				<i style="width: 70%;"></i>
			</div>

			<div class="situation-content bg-transparent height-auto pt0">
				<p style="font-size: 18px;">移动云针对金融行业现状，从物理部署、基础服务、增值服务、客户服务方面构建了从下至上的金融云专属解决方案：</p>
				<ul class="content-blocks">
					<li class="block">客户服务</li>
					<li class="block">银行</li>
					<li class="block">保险</li>
					<li class="block">基金</li>
					<li class="block">证券</li>
				</ul>
				<ul class="content-blocks">
					<li class="block">基础服务</li>
					<li class="block" style="width: 79%;">移动云云计算服务</li>
				</ul>
				<ul class="content-blocks">
					<li class="block">物理部署</li>
					<li class="block" style="width: 39%;">成都高新西区中心</li>
					<li class="block" style="width: 39%;">成都双流南区中心</li>
				</ul>
				<div style="clear: both;"></div>
			</div>

			<div class="title-dash">
				<h2>金融行业云案例</h2>
				<i style="width: 70%;"></i>
			</div>

			<div class="situation-content bg-transparent height-auto pt0">
				<h3 class="title-square">客户需求特点</h3>
				<p style="font-size: 18px;">随着金融互联网化及业务快速发展，金融行业对IT系统的安全稳定是首要考虑因素，同时对运算性能、网络连接、快速部署能力有越来越高的要求。</p>
				<h3 class="title-square">移动云解决方案</h3>
				<p style="font-size: 18px;">基于移动云产品，通过灵活利用混合云模式，在保证核心业务数据的高度安全性的同时，又保证业务系统访问的便捷性，解决安全性、运算性能、网络连接、快速部署需求。
				</p>
				<img style="width: 100%;" src="img/banking.png" />
			</div>

			<div class="title-dash">
				<h2>方案优势</h2>
				<i style="width: 70%;"></i>
			</div>

			<div class="situation-content bg-transparent height-auto pt0">
				<div class="row">
					<div class="col">
						<div class="box">
							<div class="block-title">安全性</div>
							<div class="block-content">多副本数据拷贝，提供国际专线连接自有机房和移动云。</div>
						</div>
					</div>
					<div class="col">
						<div class="box">
							<div class="block-title">运算性能</div>
							<div class="block-content">高性能云主机搭建客户业务系统，提供庞大的运算群组。</div>
						</div>
					</div>
					<div class="col">
						<div class="box">
							<div class="block-title">网络连接</div>
							<div class="block-content">提供1M-1G不同规格带宽产品，解决庞大数据传输和交互问题。</div>
						</div>
					</div>
					<div class="col">
						<div class="box">
							<div class="block-title">快速部署</div>
							<div class="block-content">云主机从购买到部署只需几分钟，公网IP、带宽即买即用，快速便捷。</div>
						</div>
					</div>
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
				<p>移动云主机产品，用户可以根据业务需求，租用不同规格云主机搭建Web服务集群和应用服务集群，也可以用来部署缓存服务器，提升网站性能</p>
			</div>
			<div class="situation-box">
				<i class="disk-icon"></i>
				<h3>云硬盘</h3>
				<p>移动云主机产品，基于高性能的磁盘阵列设备，为用户提供高性能存储服务，用户可购买块存储产品挂载载移动云主机上实现云主机的存储扩展，并可构建相应业务系统，如“文件系统”等。</p>
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