<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<link href="../styles/vh.css" rel="stylesheet" type="text/css" />
</head>
<h1>IP段控制台</h1>
<div id="message" style="display: none;">
	<span id="error" class="error"><s:actionerror /></span> <span
		class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
</div>
<s:form id="gotoForm">
	<input type="hidden" value="" id="diskId" name="diskId">
	<s:hidden id="nodeType" name="nodeType"></s:hidden>
	<s:hidden id="nodeId" name="nodeId"></s:hidden>
	<s:hidden id="treeNodeName" name="treeNodeName"></s:hidden>
	<s:hidden id="pnodeId" name="pnodeId"></s:hidden>
	<s:hidden id="pnodeName" name="pnodeName"></s:hidden>
	<s:hidden id="appId" name="appId"></s:hidden>
	<s:hidden id="curFun" name="curFun"></s:hidden>
</s:form>
<span class="apply"><a
	href="createIpSegmentAction.action?queryBusinessId=<s:property value='queryBusinessId' />&businessName=<s:property value="businessName" />
	&nodeType=<s:property value="nodeType" />&nodeId=<s:property value="nodeId" />&treeNodeName=<s:property value="treeNodeName" />
	&pnodeId=<s:property value="pnodeId" />&pnodeName=<s:property value="pnodeName" />&appId=<s:property value="appId" />
	&curFun=<s:property value="curFun" />">申请IP段</a></span>

<div class="details-con">
	<div class="detail-title">
		<h3 class="apply-title">IP段列表</h3>
	</div>
	<div class="detail-center cs-ak-center">
		<!-- vlan列表 -->
		<div class="table-content cs-tab-list-op">
			<table>
				<tr>
					<th class="nl">IP段名称</th>
					<th>地址</th>
					<th>所属业务</th>
					<th>绑定Vlan</th>
					<th>状态</th>
					<th>申请时间</th>
					<th style="width: 7%;">操作</th>
				</tr>
				<s:if test="ipSegmentList.size() > 0">
					<s:iterator value="ipSegmentList">
						<tr>
							<td>
								<s:property value="ipSegmentDesc" />
							</td>
							<td><s:property value="startIp" /> - <s:property
									value="endIp" /></td>
							<td><s:property value="appName" /></td>
							<td>
								<s:if test='vlanName==null || vlanName == ""'>
									-
								</s:if>
								<s:else>
									<s:property value="vlanName" />
								</s:else>
							</td>
							<td>
								<s:if test='status.equals("2")'>
									待创建
								</s:if>
								<s:else>
									已创建
								</s:else>
							</td>
							<td><s:property value="createTime" /></td>
							<td class="table-opt-block">
								<a
								href="ipSegmentDetailAction.action?ipSegmentId=<s:property value='ipSegmentId' />&resPoolId=<s:property value='resPoolId' />
								&resPoolPartId=<s:property value='resPoolPartId' />&queryBusinessId=<s:property value='queryBusinessId' />
								&nodeType=<s:property value="nodeType" />&nodeId=<s:property value="nodeId" />&treeNodeName=<s:property value="treeNodeName" />
								&pnodeId=<s:property value="pnodeId" />&pnodeName=<s:property value="pnodeName" />&appId=<s:property value="appId" />
								&curFun=<s:property value="curFun" />">
									详情
								</a>
							</td>
						</tr>
					</s:iterator>
				</s:if>
				<s:else>
					<tr>
						<td colspan="6">暂无符合条件数据</td>
					</tr>
				</s:else>
			</table>
		</div>
	</div>

	<!-- 翻页 -->
	<div class="pageBar">
		<s:property value="pageBar" escape="false" />
	</div>
</div>

<script type="text/javascript"
	src="../scripts/pagesjs/console/business/ipSegment/ipSegment_list.js"></script>