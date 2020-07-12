<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<link href="../styles/vh.css" rel="stylesheet" type="text/css" />

</head>
<div id="right">
	<h1>IP段控制台</h1>
	<div id="message" style="display: none;">
		<span id="error" class="error"><s:actionerror /></span> <span
			class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
	</div>


	<s:form id="IpSegmentCreateInfoAction"
		action="IpSegmentCreateInfoAction.action" method="post">
		
		<s:hidden id="nodeType" name="nodeType"></s:hidden>
		<s:hidden id="nodeId" name="nodeId"></s:hidden>
		<s:hidden id="treeNodeName" name="treeNodeName"></s:hidden>
		<s:hidden id="pnodeId" name="pnodeId"></s:hidden>
		<s:hidden id="pnodeName" name="pnodeName"></s:hidden>
		<s:hidden id="curFun" name="curFun"></s:hidden>
		<span class="region"> 选择资源池：<select class="select-max nf"
			id="respool" name="respoolId"></select>
			<!--  分区：<select
			class="select-max nf" id="respoolpart" name="respoolPartId"></select>
			 -->
			<input type="hidden" id="respoolName" name="respoolName"
			value="<s:property value="respool.respoolName"/>" />
			<!-- 
			 <input
			type="hidden" id="respoolPartName" name="respoolPartName"
			value="<s:property value="respoolPart.respoolPartName"/>" /> -->
		
		</span>

		<!-- 主机详情 -->
		<div class="details-con">
			<div class="detail-title">
				<a
					href="ipSegmentQueryListAction.action?queryBusinessId=<s:property value='queryBusinessId' />&nodeType=<s:property value="nodeType" />
					&nodeId=<s:property value="nodeId" />&treeNodeName=<s:property value="treeNodeName" />&pnodeId=<s:property value="pnodeId" />
					&pnodeName=<s:property value="pnodeName" />&appId=<s:property value="appId" />&curFun=<s:property value="curFun" />"
					title="返回"> <span class="back"></span>
				</a>
				<h3 class="apply-title">创建IP段</h3>
			</div>
			<div class="detail-info apply-info-l apply-info">
				<div class="apply-info-top">
					<span class="apply-span-name">名称：</span> <input
						class="vh-input-max" type="text" size="25" id="IpSegmentDesc"
						name="IpSegmentDesc" value="" />
				</div>
			
				<div id="bindIpSegmentDiv" class="apply-info-line-top">
					<span class="apply-span-name">选择IP段：</span> <a
						href='javascript:ipSegment.bindIpSegment();'>绑定</a>
				</div>
				
				<div id="bindDiv" class="apply-info-line-top">
					<span class="apply-span-name">所属业务：</span> 
					<span class="apply-span-name" style="width:auto;"><s:property value="businessName" /></span>
					<input type="hidden" name="appId"
						value="<s:property value='queryBusinessId' />"> <input
						type="hidden" name="appName"
						value="<s:property
							value="businessName" />">
				</div>
	
				<div class="apply-btn-commit">
					<span> <a href="javascript:submitform()">提交</a>
					</span>
				</div>
			</div>
			<div class="detail-time"></div>
		</div>
	</s:form>
</div>
<div id="bindBusiness" class="float-div"
	style="display: none; width: 700px;">
	<div class="float-toolsbar">
		<span class="keyName">业务名称：</span> <input type="text"
			class="float-input-long" id="queryBusinessName" />
		<ul class="opt-btn vh-btn-r">
			<li><a class="search"
				href="javascript:loadBusinessList('businessListJson.action')">查询</a></li>
		</ul>
	</div>
	<div class="float-div-center">
		<div class="table-content">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="businessListTab">
				<thead>
					<tr>
						<col style="width: 10%;" />
						<col style="width: 50%;" />
						<col style="width: 40%;" />
					</tr>
					<tr>
						<th class="nl"></th>
						<th>业务名称</th>
						<th>业务描述</th>
					</tr>
				</thead>
				<tbody id="businessListTbody">
				</tbody>
			</table>
		</div>
	</div>
	<div class="pageBar" id="businessListPageBarDiv"
		style="text-align: center"></div>
</div>

<!-- IP段查询,选择dialog -->
<div id="bindIpSegment" class="float-div"
	style="display: none; width: 700px;">
	<%-- <div class="float-toolsbar">
		<span class="keyName">业务名称：</span> <input type="text"
			class="float-input-long" id="queryBusinessName" />
		<ul class="opt-btn vh-btn-r">
			<li><a class="search"
				href="javascript:loadBusinessList('businessListJson.action')">查询</a></li>
		</ul>
	</div> --%>
	<div class="float-div-center">
		<div class="table-content">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="text-align: center;" id="ipSegmentListTab">
				<thead>
					<tr>
						<col style="width: 10%;" />
						<col style="width: 50%;" />
						<col style="width: 40%;" />
					</tr>
					<tr>
						<th class="nl"></th>
						<th>IP段地址</th>
						<th>IP个数</th>
					</tr>
				</thead>
				<tbody id="ipSegmentListTbody">
				</tbody>
			</table>
		</div>
	</div>
	<div class="pageBar" id="ipSegmentListPageBarDiv"
		style="text-align: center"></div>
</div>

<script type="text/javascript"
	src="../scripts/pagesjs/console/business/ipSegment/ipSegment_apply.js"></script>