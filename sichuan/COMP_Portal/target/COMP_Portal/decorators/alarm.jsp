<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- 老版本部分start -->
<title><decorator:title default="四川移动云管理平台自服务门户" /></title>
<link rel="stylesheet" type="text/css" href="../css/zg.css" />
<link rel="stylesheet" type="text/css" href="../css/pageswitch.css" />
<script src="../js/jquery-1.12.4.js" type="text/javascript"
	charset="utf-8"></script>
<script src="../js/browserDetect.js" type="text/javascript"
	charset="utf-8"></script>
<script type="text/javascript"
	src="../scripts/lib/dialog/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript" src="../scripts/common.js"></script>
<script type="text/javascript" src="../scripts/lib/jquery.blockUI.js"></script>
<script type="text/javascript">
	if (BrowserDetect.version <= 8 && BrowserDetect.browser == "Explorer") {
		window.location.href = "404.html";
	}
	$(function() {
		$('.user-info-tab').load('../userInfoTab.jsp');
	});
</script>
</head>
<body>
	<%-- 头 --%>
	<header class=" header-bgcolor">
	<div>
		<a class="mini-logo" href="../home.jsp"><img height='40px' width='40px' src="../img/chinamobile.png" alt=" " /></a> 
		<a href="../console/resourcesOverviewAction.action" class="mini-logo">
			<img height='25px' width='25px' src="../img/ico-console.png" />
			<p>控制台</p>
		</a>
		<nav>
			<ul class="login">
				<li class="resource"><a href="../console/resourcesOverviewAction.action">资源视图</a></li>
				<li class="resource"><a	href="../business_console/businessOverview.action">业务视图</a></li>
				<li class="resource"><a	href="../alarmCenter/alarmAction.action" class="active">告警</a></li>
				<li class="resource"><a
					href="../performance_console/performanceOverview.action">性能视图</a>
				</li>	
				<li class="app"><a href="../userCenter/orderQuery.action">用户中心</a></li>
				<li class="user">
					<a href="#" class="user-head"> 
						<img style="width: 50px; height: 50px;" src="../img/head.png" />
					</a>
				</li>
				<div class="user-info-tab" style="display: none;"></div>
			</ul>
		</nav>
	</div>
	</header>
	<%-- 主 --%>
	<main> 
		<section id="main" class="control dark-section pt80 ">
			<div id="left" class="left-menu">
				
				<div id="alarmAction" class="menu-item">
					<i></i> <a href="../alarmCenter/alarmAction.action">告警列表</a><i class="arrow"></i>
				</div>
				
			</div>
			<div id="right" class="right">
				<decorator:body />
			</div>
		</section> 
	</main>
</body>
</html>
