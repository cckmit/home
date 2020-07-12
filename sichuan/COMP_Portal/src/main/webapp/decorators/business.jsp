<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title> <decorator:title default="四川移动云管理平台自服务门户" /></title>
<link href="../styles/base.css" rel="stylesheet" type="text/css" />
<link href="../styles/formStyle.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="../scripts/lib/datepicker/css/redmond/jquery-ui-1.9.2.datepicker.css" type="text/css" />
<script type="text/javascript" src="../scripts/lib/jquery-1.8.3.js"></script>
<script type="text/javascript" src="../scripts/lib/jquery.mooko.tabs.js"></script>
<script type="text/javascript" src="../scripts/lib/left-menu.js"></script>
<script type="text/javascript" src="../scripts/lib/float-menu.js"></script>
<script type="text/javascript"
	src="../scripts/lib/dialog/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript" src="../scripts/lib/jquery.uniform.js"></script>
<script type="text/javascript" src="../scripts/common.js"></script>
<script type="text/javascript" src="../scripts/lib/datepicker/js/jquery-ui-1.9.2.datepicker.min.js"></script>
<decorator:head />
</head>

<body>
<div id="header">
<div class="head-logo">
</div>
<div>
	<ul id="head-menu" class="head-btn">
   		<li><a href="../home.jsp">首页</a></li>
   		<li><a href="../product/hostPrice.action">产品目录</a></li>
	   	<li><a href="../console/resourcesOverviewAction.action">控制台</a></li>
	   	<li><a href="../userCenter/orderQuery.action;">用户中心</a></li>
	   	<li><a href="../business/businessList.action">业务管理</a></li>
	</ul>
</div>
</div>
<div id="main">
	<%--左侧--%>
	<div id="left" class="float-menu">
    	<s:if test="#session.userInfo!=null">
    		<div id="userInfoDiv">
				<div id="userInfoImg">&nbsp;</div>
				<div id="userInfo"><s:property value="#session.userInfo.userName" />
				<a href="../logout.action">[退出]</a>
				</div>
			</div>
    	</s:if>
    	<s:if test="#session.userInfo==null"><div id="userInfo">欢迎&nbsp;&nbsp;&nbsp;&nbsp;<a href="../login.jsp">登录</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="../register.html">注册</a></div></s:if>
        <ul class="left-menu">
			<li><span class="ico_business"></span><a href="businessList.action">业务管理</a></li>
            <li class="menu-child">
                <dl><a href="../business/businessList.action">业务列表</a></dl>
            </li>
            <%--
            <li><span class="ico-ebs"></span><a href="vmbakPrice.action">虚拟机备份 </a></li>
           	<li class="menu-child">
                <dl><a href="#vmbak_recommend">虚拟机备份推荐</a></dl>
                <dl><a href="#vmbak_price">虚拟备份机总览</a></dl>
            </li>
             --%>
            <%--<li><span class="ico-net"></span><a href="javascript:void(0);">网络资源</a></li>
        --%></ul>
    </div>
	<%--右侧--%>
    <div id="right" class="float-menu-right">
		<decorator:body />
   </div>
</div>
</body>
</html>