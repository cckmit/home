<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>应用场景--教育</title>
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
								href="situation_banking.jsp">金融行业解决方案</a><!--  <a
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
		style="background: url(img/situation-bg01.png) no-repeat; background-size: cover;">
		<div class="title-detail title-left">
			<div>
				<img src="img/situation-icon01.png" width="65px" height="58px"
					class="title-icon" />
				<h1 class="title ">教育行业解决方案</h1>
			</div>

			<p>基于移动云系列产品，可构建在线教育平台，整合教育信息化资源，部署课程点播、课程直播、课程教材资料共享等业务，能够实现平台的快速部署和弹性扩展，提升使用效率。</p>

		</div>
		<img src="img/banner-06.png" class="title-right" />
	</section>
	<section class="content dark-section">
		<div class="content-detail">
			<div class="title-dash">
				<h2>架构描述</h2>
				<i></i>
			</div>
			<div class="situation-content">
				<a class="desc active" href="javascript:void(0);" data-id="a">课程点播</a>
				<a class="desc" href="javascript:void(0);" data-id="b">课程直播</a> <a
					class="desc" href="javascript:void(0);" data-id="c">资料分享</a>
				<div id="a" class="">
					<p>在线教育的课程点播业务，除了向用户提供基本的Web服务外(参考互联网通用架构)，还具备文件上传、视频文件转码、课程点播服务等功能：</p>
					<ul>
						<li><i></i>弹性负载均衡、文件上传服务集群（云主机产品）和原始文件存储（云存储产品）组成了文件上传功能模块，提供高效、高可用的文件上传服务和大容量、可靠的原始文件存储服务。</li>
						<li><i></i>媒体文件转码（云主机产品）、和索引/切片文件存储（云存储产品）组成了媒体文件转码功能模块，提供高效的媒体文件转码和大容量存储服物。</li>
						<li><i></i>媒体服务集群（云主机产品）、弹性负载均衡和CDN组成了媒体服务功能模块，提供高效、高可用的媒体服务，并通过CDN将媒体文件分发至靠近用户的网络边缘，提高了媒体服务质量。</li>
					</ul>
					<img style="width: 100%;" src="img/education.png" />
				</div>
				<div id="b" class="none">
					<p>在线教育的课程点播业务，除了向用户提供基本的Web服务外(参考互联网通用架构)，还具备直播课程媒体采集与上传、媒体文件实时转码、直播媒体服务等逻辑功能模块：</p>
					<ul>
						<li><i></i>课程视频实时采集设备将采集到的视频信号通过弹性负载均衡和媒体转码集群实现视频的上传和实时转码。</li>
						<li><i></i>媒体服务集群（云主机产品）和弹性负载均衡组成了媒体服务功能模块，提供高效、高可用的媒体直播服务。</li>
						<li><i></i>对于采用RTMP协议的直播方案，通过实时流媒服务集群将媒体流直接推送给CDN为用户提供服务；对于采用hls协议的直播方案，将转码后的索引和切片使用云存储产品进行存储后，向用户提供直播服务。</li>
					</ul>
					<img style="width: 100%;" src="img/education02.png" />
				</div>
				<div id="c" class="none">
					<p>资料分享服务主要包括资料文件上传和资料文件下载服务等逻辑功能模块：</p>
					<ul>
						<li><i></i>由弹性负载均衡产品和云主机产品构建的文件上传服务集群具备高效、高可用的特点。</li>
						<li><i></i>使用云存储产品对资料文件进行线上存储。</li>
					</ul>
					<img style="width: 100%;" src="img/education03.png" />
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
				<i class="ebs-icon"></i>
				<h3>云储存</h3>
				<p>移动云弹性负载均衡产品，提供负载均衡服务。</p>
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