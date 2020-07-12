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
<title><decorator:title default="四川移动云管理平台自服务门户" /></title>
<link rel="stylesheet" type="text/css" href="../css/zg.css" />
<link rel="stylesheet" type="text/css" href="../css/pageswitch.css" />
<link rel="stylesheet" href="../styles/zTreeStyle.css" type="text/css">

<script src="../js/jquery-1.6.2.js" type="text/javascript" charset="utf-8"></script>
<script src="../js/browserDetect.js" type="text/javascript" charset="utf-8"></script>

<script type="text/javascript" src="../scripts/lib/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="../scripts/pagesjs/performance/zTreeOnload.js"></script>

<!-- 图形组件charts -->
<script src="../scripts/charts/charts.js"></script>
<script src="../scripts/charts/grid.js"></script>
<link href="../scripts/charts/jquery.ui.all.css" rel="stylesheet" type="text/css">

<%-- 性能单独的css文件--%>
<link href="../themes/performance/stylesheets/global.css" rel="stylesheet" type="text/css"/>
<link href="../themes/performance/stylesheets/header.css" rel="stylesheet" type="text/css" />
<link href="../themes/performance/stylesheets/fotter.css" rel="stylesheet" type="text/css" />

<script src="../scripts/jquery-ui-1.8.16.custom/development-bundle/ui/jquery.ui.core.js"></script>
<script src="../scripts/jquery-ui-1.8.16.custom/development-bundle/ui/jquery.ui.widget.js"></script>
<script src="../scripts/jquery-ui-1.8.16.custom/development-bundle/ui/jquery.ui.progressbar.js"></script>

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
		<a class="mini-logo" href="../home.jsp"><img height='40px'
			width='40px' src="../img/chinamobile.png" alt=" " /></a> 
<!-- 			<a href="businessOverview.action" class="mini-logo"> <img -->
<!-- 			height='25px' width='25px' src="../img/ico-console.png" /> -->
<!-- 			<p>控制台</p></a> -->
		<a href="../console/resourcesOverviewAction.action" class="mini-logo"> <img
			height='25px' width='25px' src="../img/ico-console.png" />
			<p>控制台</p></a>
		<nav>
		<ul class="login">
			<li class="resource"><a
				href="../console/resourcesOverviewAction.action">资源视图</a></li>
			<li class="resource"><a
				href="../business_console/businessOverview.action">业务视图</a></li>
			<li class="resource"><a href="../alarmCenter/alarmAction.action">告警</a></li>
			<li class="resource"><a
				href="../performance_console/performanceOverview.action" class="active">性能视图</a>
			</li>		
			<li class="app"><a
				href="../userCenter/orderQuery.action" >用户中心</a></li> 
			<li class="user"><a href="#" class="user-head"> <img
					style="width: 50px; height: 50px;" src="../img/head.png" />
			</a></li>
						<div class="user-info-tab none"></div>
		</ul>
		</nav>
	</div>
	</header>
	<main> <section id="main" class="control dark-section pt80 ">
	<s:form method="post" id="submitForm">
		<s:hidden id="nodeType" name="nodeType"></s:hidden>
		<s:hidden id="nodeId" name="nodeId"></s:hidden>
		<s:hidden id="treeNodeName" name="treeNodeName"></s:hidden>
		<s:hidden id="pnodeId" name="pnodeId"></s:hidden>
		<s:hidden id="pnodeName" name="pnodeName"></s:hidden>
		<s:hidden id="appId" name="appId"></s:hidden>
		<s:hidden id="curFun" name="curFun"></s:hidden>
	</s:form>
	<div id="left" class="left-menu business">
		<div class="left-menu-resourceManagement" style="margin-top:20px;">
			<ul id="appView" class="ztree" curFun="ywst"></ul>
		</div>
	</div>

	<div id="right" class="right">
		<decorator:body />
	</div>
	</section>
	<div class="cart-bar none"></div>
	</main>


	<!--[if lte IE 9]>
		<script src="../js/jquery.placeholder.min.js" type="text/javascript" charset="utf-8"></script>
		<script>
				$(function() {
				$('input').placeholder();
			});
		</script>
		<![endif]-->
</body>
</html>












