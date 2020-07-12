<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="comp-identify" uri="/WEB-INF/taglib/comp-identify"%>
<%@ taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><decorator:title default="四川移动云管理平台运营管理系统" /></title>
<link rel="stylesheet" href="../styles/base.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="../styles/formStyle.css" />
<link rel="stylesheet" href="../scripts/My97DatePicker/skin/WdatePicker.css" type="text/css" />
<link rel="stylesheet" href="../styles/resourceManagementDetail.css" type="text/css"/>

<script type="text/javascript" src="../scripts/lib/jquery-1.8.3.js"></script>
<script type="text/javascript" src="../scripts/lib/jquery.mooko.tabs.js"></script>
<script type="text/javascript" src="../scripts/lib/left-menu.js"></script>
<script type="text/javascript" src="../scripts/lib/dialog/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript" src="../scripts/lib/jquery.uniform.js"></script>
<script type="text/javascript" src="../scripts/common.js"></script>
<script type="text/javascript" src="../scripts/My97DatePicker/WdatePicker.js"></script>
<decorator:head />
</head>
<body>
<div class="head-logo"></div>
	<%-- 头 --%>
	<div id="header">
		<div>
			<ul id="head-menu" class="head-btn">
				<comp-identify:COMPIdentify permissionname="dbrw">
					<li class="head-btn-sel"><a href="taskOverviewAction.action?t=1">待办任务</a>
						<%--<span>10</span> --%></li>
				</comp-identify:COMPIdentify>
				<comp-identify:COMPIdentify permissionname="ddgl">
					<li><a href="../userCenter/orderQuery.action?t=1">订单管理</a></li>
				</comp-identify:COMPIdentify>
				<comp-identify:COMPIdentify permissionname="cpgl">
					<li><a href="../product/VMStandardListQuery.action?t=1">产品管理</a></li>
				</comp-identify:COMPIdentify>
				<comp-identify:COMPIdentify permissionname="zygl">
					<li><a href="../resourceManagement_resView/allResourceAction.action">资源管理</a></li>
				</comp-identify:COMPIdentify>
				<comp-identify:COMPIdentify permissionname="ywgl">
				 	<li><a href="../app/appListQuery.action?t=1">业务管理</a></li>
				</comp-identify:COMPIdentify>
				<comp-identify:COMPIdentify permissionname="bzyzc">
					<li><a href="javascript:void(0);">帮助与支持</a></li>
				</comp-identify:COMPIdentify>
				<comp-identify:COMPIdentify permissionname="xtjk">
					<li><a href="javascript:void(0);">系统监控</a></li>
				</comp-identify:COMPIdentify>
				<comp-identify:COMPIdentify permissionname="tjfx">
					<li><a href="../report/cpuDeviceTop10PerformanceAction.action?devicePerformanceInfo.deviceType=0&devicePerformanceInfo.timeType=1">统计分析</a></li>
				</comp-identify:COMPIdentify>
				<comp-identify:COMPIdentify permissionname="zfgl">
					<li><a href="../charges/queryChargesAction.action?chargesType=0">资费管理</a> </li>
				</comp-identify:COMPIdentify>
				<comp-identify:COMPIdentify permissionname="xtgl">
					<li><a href="../system/resource.action?t=1">系统管理</a></li>
				</comp-identify:COMPIdentify>
				<comp-identify:COMPIdentify permissionname="jlgl">
					<li><a href="../meterage/resourceMeterage.action?t=1">计量管理</a></li>
				</comp-identify:COMPIdentify>
			</ul>
		</div>
	</div>
	<!-- #EndLibraryItem -->
	<div id="main">
		<!-- #BeginLibraryItem "/Library/left_todo.lbi" -->
		<!--左侧-->
		<div id="left">
			<s:if test="#session.userInfo!=null">
				<div id="userInfoDiv">
					<div id="userInfoImg">&nbsp;</div>
					<div id="userInfo"><s:property value="#session.userInfo.userName" />
					<a href="../logout.action">[退出]</a>
					</div>
				</div>
			</s:if>
			<ul class="left-menu">
				<li><span class="ico_overview"></span><a
						href="taskOverviewAction.action">概况</a></li>
				<%--<li><span class="ico_products"></span><a href="javascript:void(0);">订单审批</a></li>
	<li><span class="ico_products"></span><a href="javascript:void(0);">服务目录审批</a></li>
	--%>
				<comp-identify:COMPIdentify permissionname="fwtmsp">
					<li><span class="ico_products"></span><a
						href="auditItems.action">服务条目审批</a></li>
				</comp-identify:COMPIdentify>
				<li class="menu-child"><comp-identify:COMPIdentify
						permissionname="tmsp">
						<dl>
							<a href="auditItems.action">条目审批</a>
						</dl>
					</comp-identify:COMPIdentify> <comp-identify:COMPIdentify permissionname="fbsp">
						<dl>
							<a href="publishItems.action">发布审批</a>
						</dl>
					</comp-identify:COMPIdentify></li>
				<comp-identify:COMPIdentify permissionname="user_sp">
					<li><span class="ico_products"></span><a
						href="userApprovalList.action">用户审批 </a></li>
				</comp-identify:COMPIdentify>

				<comp-identify:COMPIdentify permissionname="order_sp">
					<li><span class="ico_products"></span><a
						href="ordersAuditList.action">订单审批</a></li>
				</comp-identify:COMPIdentify>

			</ul>
		</div>
		<%-- 右侧 --%>
		<div id="right">
			<decorator:body />
		</div>
	</div>
</body>
</html>
