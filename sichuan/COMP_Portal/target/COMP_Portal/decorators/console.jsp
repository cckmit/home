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
<%-- <link rel="stylesheet" href="../styles/base.css" type="text/css" />
<link href="../styles/vh.css" rel="stylesheet" type="text/css" />
<link href="../styles/cs.css" rel="stylesheet" type="text/css" />
<link href="../scripts/My97DatePicker/skin/WdatePicker.css"
	rel="stylesheet" type="text/css" />

<script type="text/javascript" src="../scripts/lib/jquery-1.8.3.js"></script>
<script type="text/javascript" src="../scripts/lib/jquery.mooko.tabs.js"></script>
<script type="text/javascript" src="../scripts/lib/left-menu.js"></script>
<script type="text/javascript"
	src="../scripts/lib/dialog/jquery.artDialog.js?skin=default"></script>

<script type="text/javascript" src="../scripts/common.js"></script>
<script type="text/javascript"
	src="../scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="../scripts/pagesjs/console/console.js"></script> --%>
<!--  老版本部分end -->

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
		<a class="mini-logo" href="../home.jsp"><img height='40px'
			width='40px' src="../img/chinamobile.png" alt=" " /></a> <a
			href="resourcesOverviewAction.action" class="mini-logo"> <img
			height='25px' width='25px' src="../img/ico-console.png" />
			<p>控制台</p>
		</a>
		<nav>
		<ul class="login">
			<li class="resource"><a
				href="../console/resourcesOverviewAction.action" class="active">资源视图</a>
			</li>
		 	<li class="resource"><a
				href="../business_console/businessOverview.action">业务视图</a>
			</li>	
			<li class="resource"><a href="../alarmCenter/alarmAction.action">告警</a></li>
			<li class="resource"><a
				href="../performance_console/performanceOverview.action">性能视图</a>
			</li>				 
			<li class="app"><a
				href="../userCenter/orderQuery.action" >用户中心</a></li> 
			<li class="user"><a href="#" class="user-head"> <img
					style="width: 50px; height: 50px;" src="../img/head.png" />
			</a></li>
			<div class="user-info-tab" style="display:none;"></div>
		</ul>
		</nav>
	</div>
	</header>
	<main> <section id="main" class="control dark-section pt80 ">

	<div id="left" class="left-menu">
		<div id="overview" class="menu-item ">
			<i></i> <a href="resourcesOverviewAction.action">概况总览</a><i
				class="arrow"></i>
		</div>
		<div id="book" class="menu-item ">
			<div class="menu-item-parent">
				<i></i> <a href="vmPreApplyQueryListAction.action">预订购</a><i class="arrow"></i>
			</div>
			<div id="vm_book" class="menu-item-child none">
				<i></i> <a href="vmPreApplyQueryListAction.action">云主机预订购</a><i class=""></i>
			</div>
			<div id="ebs_book" class="menu-item-child none">
				<i></i> <a href="ebsPreApplyQueryListAction.action">云硬盘预订购</a><i class=""></i>
			</div>
		</div>
		<div id="server" class="menu-item">
			<i></i> <a href="vmQueryListAction.action">云主机 </a><i class="arrow"></i>
		</div>

		<div id="ebs" class="menu-item">
			<i></i> <a href="ebsQueryListAction.action">云硬盘</a><i class="arrow"></i>
		</div>
		<s:if test="#session.userInfo.userId=='admin'">
			<div id="lb" class="menu-item">
				<i></i> <a href="loadBalanceAction.action">负载均衡</a><i class="arrow"></i>
			</div>
		</s:if>
		<div id="batch" class="menu-item">
			<i></i> <a href="vmBatchQueryListAction.action">云主机批量修改</a><i
				class="arrow"></i>
		</div>
		<%-- 	<div class="menu-item">
			<span class="ico-pm"></span><img src="img/ico-pm.png" /> <a
				href="pmQueryListAction.action">物理机</a>
		</div> --%>
		<s:if test="#session.userInfo.userId=='admin'">
			<div id="vlan" class="menu-item">
				<i></i> <a href="vlanQueryListAction.action">Vlan</a><i
					class="arrow"></i>
			</div>
			<!-- 
			<div id="vlan3" class="menu-item">
				<i></i> <a href="vlanQueryList3PhaseAction.action">SDN_Vlan</a><i
					class="arrow"></i>
			</div>
			 -->
			<div id="ips" class="menu-item">
				<i></i> <a href="ipSegmentQueryListAction.action">IP段</a><i
					class="arrow"></i>
			</div>
		</s:if>

		<!-- <div id="topo" class="menu-item">
			<i></i> <a href="topoAction.action">拓扑管理</a><i class="arrow"></i>
		</div> -->
		<!-- 二期 -->
		<!-- 二三期合并后，统一用三期的拓扑
		<div id="pzhtopo" class="menu-item">
			<i></i> <a href="pzhtopoAction.action">拓扑管理</a><i class="arrow"></i>
		</div>
		 -->
		<!-- 三，四期 -->
		<div id="pzhtopo3" class="menu-item">
			<i></i> <a href="pzhtopoAction3.action">拓扑管理</a><i class="arrow"></i>
		</div>
		
		<s:if test="#session.userInfo.userId=='admin'">
			<div id="ip" class="menu-item">
				<i></i> <a href="ipListAction.action">IP地址管理</a><i class="arrow"></i>
			</div>
			<div id="fileStore" class="menu-item">
				<i></i> <a href="fileStoreListAction.action">分布式文件存储</a><i
					class="arrow"></i>
			</div>
			<div id="vFW" class="menu-item">
				<i></i> <a href="vFWListAction.action">虚拟防火墙</a><i class="arrow"></i>
			</div>
			<div id="backup" class="menu-item">
				<i></i>
				 <a href="dataBackupList.action">数据备份</a>
				<i class="arrow"></i>
			</div>
		</s:if>
		
		<div id="bossOrder" class="menu-item">
			<i></i> <a href="bossOrderList.action">BOSS订单</a><i
				class="arrow"></i>
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
