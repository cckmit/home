<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="comp-identify" uri="/WEB-INF/taglib/comp-identify"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title> <decorator:title default="四川移动云管理平台运营管理系统" /></title>
	<link rel="stylesheet" href="../styles/base.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="../styles/formStyle.css" />
	<link rel="stylesheet" href="../scripts/My97DatePicker/skin/WdatePicker.css" type="text/css" />
    <link rel="stylesheet" href="../styles/resourceManagementDetail.css"  type="text/css" />

    <script type="text/javascript" src="../scripts/lib/jquery-1.3.2.js"></script>
    <script type="text/javascript" src="../scripts/lib/charts/charts.js"></script>

	<script type="text/javascript" src="../scripts/lib/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="../scripts/lib/jquery.mooko.tabs.js"></script>
	<script type="text/javascript" src="../scripts/lib/left-menu.js"></script>
	<script type="text/javascript" src="../scripts/lib/dialog/jquery.artDialog.js?skin=default"></script>
	<script type="text/javascript" src="../scripts/lib/jquery.uniform.js"></script>
	<script type="text/javascript" src="../scripts/common.js"></script>
	<script type="text/javascript" src="../scripts/My97DatePicker/WdatePicker.js"></script>

    <link rel="stylesheet" href="../styles/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="../scripts/lib/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="../scripts/pagesjs/resourceManagement/zTreeOnload.js"></script>
	
	<decorator:head />
</head>
<body>
<div class="head-logo"></div>
<%-- 头 --%>
<div id="header">
	<div>
		<ul id="head-menu" class="head-btn">
	    <comp-identify:COMPIdentify permissionname="dbrw">	
			<li><a href="../task/taskOverviewAction.action?t=1">待办任务</a><%--<span>10</span> --%></li>
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
<%-- 主 --%>
<div id="main">
    <s:form method="post" id="submitForm">
        <s:hidden id="nodeType" name="nodeType"></s:hidden>
        <s:hidden id="nodeId" name="nodeId"></s:hidden>
        <s:hidden id="treeNodeName" name="treeNodeName"></s:hidden>
        <s:hidden id="pnodeId" name="pnodeId"></s:hidden>
        <s:hidden id="pnodeName" name="pnodeName"></s:hidden>
        <s:hidden id="appId" name="appId"></s:hidden>
        <s:hidden id="curFun" name="curFun"></s:hidden>
    </s:form>
	<%-- 左侧 --%>
	<div id="left">
    	<s:if test="#session.userInfo!=null">
    		<div id="userInfoDiv">
				<div id="userInfoImg">&nbsp;</div>
				<div id="userInfo"><s:property value="#session.userInfo.userName" />
				<a href="../logout.action">[退出]</a>
				</div>
			</div>
    	</s:if> 
        <ul  id="tab">
            <li><a href="../resourceManagement_resView/allResourceAction.action">资源视图</a></li>
            <li class="selected fr">业务视图</li>
        </ul>
        <div class="left-menu-resourceManagement">
            <ul id="appView" class="ztree" curFun="ywst"></ul>
        </div>
    </div>
    <%-- 右侧 --%>
	<div id="right">
		<decorator:body />
	</div>
</div>
</body>
</html>	