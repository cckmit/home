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
<link rel="stylesheet"
	href="../scripts/My97DatePicker/skin/WdatePicker.css" type="text/css" />

<script type="text/javascript" src="../scripts/lib/jquery-1.3.2.js"></script>
<script type="text/javascript" src="../scripts/lib/charts/charts.js"></script>

<script type="text/javascript" src="../scripts/lib/jquery-1.8.3.js"></script>
<script type="text/javascript" src="../scripts/lib/jquery.mooko.tabs.js"></script>
<script type="text/javascript" src="../scripts/lib/left-menu.js"></script>
<script type="text/javascript"
	src="../scripts/lib/dialog/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript" src="../scripts/lib/jquery.uniform.js"></script>
<script type="text/javascript" src="../scripts/common.js"></script>
<script type="text/javascript" src="../scripts/lib/jquery.form.js"></script>
<script type="text/javascript"
	src="../scripts/My97DatePicker/WdatePicker.js"></script>

<decorator:head />
</head>
<body>
	<div class="head-logo"></div>
	<%-- 头 --%>
	<div id="header">
		<div>
			<ul id="head-menu" class="head-btn">
				<comp-identify:COMPIdentify permissionname="dbrw">
					<li><a href="../task/taskOverviewAction.action?t=1">待办任务</a> <%--<span>10</span> --%></li>
				</comp-identify:COMPIdentify>
				<comp-identify:COMPIdentify permissionname="ddgl">
					<li><a href="../userCenter/orderQuery.action?t=1">订单管理</a></li>
				</comp-identify:COMPIdentify>
				<comp-identify:COMPIdentify permissionname="cpgl">
					<li><a href="../product/VMStandardListQuery.action?t=1">产品管理</a></li>
				</comp-identify:COMPIdentify>
				<comp-identify:COMPIdentify permissionname="zygl">
					<li><a
						href="../resourceManagement_resView/allResourceAction.action">资源管理</a></li>
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
					<li><a
						href="../report/cpuDeviceTop10PerformanceAction.action?devicePerformanceInfo.deviceType=0&devicePerformanceInfo.timeType=1">统计分析</a></li>
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
	<%-- 主 --%>
	<div id="main">
		<%--左侧--%>
		<div id="left">
			<s:if test="#session.userInfo!=null">
				<div id="userInfoDiv">
					<div id="userInfoImg">&nbsp;</div>
					<div id="userInfo">
						<s:property value="#session.userInfo.userName" />
						<a href="../logout.action">[退出]</a>
					</div>
				</div>
			</s:if>


			<ul class="left-menu">
				<comp-identify:COMPIdentify permissionname="sbxntj">
					<li><span class="ico-watch"></span><a
						href="javascript:void(0);">设备性能统计</a></li>
				</comp-identify:COMPIdentify>
				<li class="menu-child"><comp-identify:COMPIdentify
						permissionname="sbxntjwlj">
						<dl>
							<a href="cpuDeviceTop10PerformanceAction.action?devicePerformanceInfo.deviceType=0&devicePerformanceInfo.timeType=1">物理机</a>
						</dl>
					</comp-identify:COMPIdentify> <comp-identify:COMPIdentify permissionname="sbxntjxnj">
						<dl>
							<a href="cpuDeviceTop10PerformanceAction.action?devicePerformanceInfo.deviceType=1&devicePerformanceInfo.timeType=1">虚拟机</a>
						</dl>
					</comp-identify:COMPIdentify> <!--  
				<comp-identify:COMPIdentify permissionname="sbxntjxxj">	
				    <dl><a href="cpuDevicePerformanceAction.action?historyPerformanceInfo.deviceType=6&historyPerformanceInfo.performanceType=cpu">小型机</a></dl>
				</comp-identify:COMPIdentify>
				<comp-identify:COMPIdentify permissionname="sbxntjxxjfq">	
				    <dl><a href="cpuDevicePerformanceAction.action?historyPerformanceInfo.deviceType=7&historyPerformanceInfo.performanceType=cpu">小型机分区</a></dl>
				</comp-identify:COMPIdentify>
				--> <comp-identify:COMPIdentify permissionname="sbxntjzl">
						<dl>
							<a href="cpuDeviceTop10PerformanceAction.action?devicePerformanceInfo.deviceType=3&devicePerformanceInfo.timeType=1">阵列</a>
						</dl>
					</comp-identify:COMPIdentify> <comp-identify:COMPIdentify permissionname="sbxntjfhq">
						<dl>
							<a href="cpuDeviceTop10PerformanceAction.action?devicePerformanceInfo.deviceType=2&devicePerformanceInfo.timeType=1">防火墙</a>
						</dl>
					</comp-identify:COMPIdentify> <comp-identify:COMPIdentify permissionname="sbxntjjhj">
						<dl>
							<a href="cpuDeviceTop10PerformanceAction.action?devicePerformanceInfo.deviceType=4&devicePerformanceInfo.timeType=1">交换机</a>
						</dl>
					</comp-identify:COMPIdentify> <comp-identify:COMPIdentify permissionname="sbxntjlyq">
						<dl>
							<a href="cpuDeviceTop10PerformanceAction.action?devicePerformanceInfo.deviceType=5&devicePerformanceInfo.timeType=1">路由器</a>
						</dl>
					</comp-identify:COMPIdentify></li>
				<comp-identify:COMPIdentify permissionname="ywxntj">
					<li><span class="ico-watch"></span><a
						href="javascript:void(0);">业务性能统计</a></li>
				</comp-identify:COMPIdentify>
				<li class="menu-child"><comp-identify:COMPIdentify
						permissionname="ywxntjwlj">
						<dl>
							<a href="cpuHistoryPerformanceAction.action?historyPerformanceInfo.deviceType=2&historyPerformanceInfo.performanceType=cpu">物理机</a>
						</dl>
					</comp-identify:COMPIdentify> <comp-identify:COMPIdentify permissionname="ywxntjxnj">
						<dl>
							<a href="cpuHistoryPerformanceAction.action?historyPerformanceInfo.deviceType=3&historyPerformanceInfo.performanceType=cpu">虚拟机</a>
						</dl>
					</comp-identify:COMPIdentify> <comp-identify:COMPIdentify permissionname="ywxntjxxj">
						<dl>
							<a href="cpuHistoryPerformanceAction.action?historyPerformanceInfo.deviceType=0&historyPerformanceInfo.performanceType=cpu">小型机
							</a>
						</dl>
					</comp-identify:COMPIdentify> <comp-identify:COMPIdentify permissionname="ywxntjxxjfq">
						<dl>
							<a href="cpuHistoryPerformanceAction.action?historyPerformanceInfo.deviceType=1&historyPerformanceInfo.performanceType=cpu">小型机分区</a>
						</dl>
					</comp-identify:COMPIdentify> <comp-identify:COMPIdentify permissionname="ywxntjdc">
						<div
							style="height: 22px; line-height: 22px; margin: 9px 0px; text-indent: 15px; border: 1px solid #E6E9EB;">
							<a onclick="exportDiv();" href="javascript:void(0);">统计数据导出</a>
						</div>
						<s:url id="exportHistoryReportUrl"
							action="exportHistoryReportAction"></s:url>
						<a id="export" href="${exportHistoryReportUrl}"></a>
						<div class="page-form" id="exportDiv" style="display: none;width:400px;">
							<div class="caption" style="width: auto; padding-left: 10px;">业务组名称：</div>
							<div class="content" style="width: 200px;">
								<s:select id="appIdExport" name="appIdExport" list="appList"
									listKey="appId" listValue="appName" headerKey=""
									headerValue="-请选择-" />
							</div>
							<br /><br />
							<div class="caption" style="width: auto; padding-left: 10px;">
								查询日期：</div>
							<div class="content">
								<s:textfield id="startDateExport" name="startDateExport"
									onClick="WdatePicker({dateFmt:'yyyy-MM-dd'});"
									cssClass="Wdate wdatelong" size="15" readonly="true" />
							</div>
							<div class="caption" style="width: auto; padding-left: 10px;">至</div>
							<div class="content">
								<s:textfield id="endDateExport" name="endDateExport"
									onClick="WdatePicker({dateFmt:'yyyy-MM-dd'});"
									cssClass="Wdate wdatelong" size="15" readonly="true" />
							</div>
						</div>
					</comp-identify:COMPIdentify></li>

				<comp-identify:COMPIdentify permissionname="ydxntj">
					<li><span class="ico-watch"></span><a
						href="javascript:void(0);">月度性能统计</a></li>
				</comp-identify:COMPIdentify>
				<li class="menu-child">
				<comp-identify:COMPIdentify permissionname="ydxntjwlj">
						<dl>
							<a href="cpuDevicePerformanceAction.action?monthPerformanceInfo.deviceType=0&monthPerformanceInfo.orderType=0&monthPerformanceInfo.dateType=0">物理机</a>
						</dl>
					</comp-identify:COMPIdentify> 
						
					<comp-identify:COMPIdentify permissionname="ydxntjxnj">
						<dl>
							<a href="cpuDevicePerformanceAction.action?monthPerformanceInfo.deviceType=1&monthPerformanceInfo.orderType=0&monthPerformanceInfo.dateType=0">虚拟机</a>
						</dl>
					</comp-identify:COMPIdentify> 
					<comp-identify:COMPIdentify permissionname="ydxntjdc">
						<div
							style="height: 22px; line-height: 22px; margin: 9px 0px; text-indent: 15px; border: 1px solid #E6E9EB;">
							<a onclick="exportMonthDiv();" href="javascript:void(0);">统计数据导出</a>
						</div>
						<s:url id="exportMonthReportUrl"
							action="exportMonthReportAction"></s:url>
						<a id="exportMonth" href="${exportMonthReportUrl}"></a>
						<div class="page-form" id="exportMonthDiv" style="display: none;width:500px;">
							<div class="caption" style="width: auto; padding-left: 10px;">
							资源池：
							<s:select id="resMonthPoolId" name="resPoolId"
								list="resPoolList" listKey="resPoolId" listValue="resPoolName"
								headerKey="" headerValue="-请选择 -"
								onchange="selectMonthResPool(this.value)" />
							分区： <select id="partMonthId" name="poolPartId">
							<option value="" selected="selected">-请选择 -</option>
								</select>
							</div>
							<br />
							<br/>
							<div class="content" style="padding-left:10px;">查询日期：
								<s:textfield id="startMonthDateExport" name="startMonthDateExport"
									onClick="WdatePicker({dateFmt:'yyyy-MM-dd'});"
									cssClass="Wdate wdatelong" size="15" readonly="true" />
							</div>
							<div class="caption" style="width: auto; padding-left: 10px;">至</div>
							<div class="content">
								<s:textfield id="endMonthDateExport" name="endMonthDateExport"
									onClick="WdatePicker({dateFmt:'yyyy-MM-dd'});"
									cssClass="Wdate wdatelong" size="15" readonly="true" />
								<s:radio list="#{'0':' 全天','1':' 白天','2':' 夜间'}"
									name="dateType" value="0" />
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</div>
							
						
						</div>
					</comp-identify:COMPIdentify></li>
			</ul>
		</div>
		<%-- 右侧 --%>
		<div id="right">
			<decorator:body />
		</div>
	</div>
</body>
</html>
