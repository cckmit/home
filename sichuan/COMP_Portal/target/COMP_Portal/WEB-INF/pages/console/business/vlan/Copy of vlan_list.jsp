<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<link href="../styles/vh.css" rel="stylesheet" type="text/css" />
</head>
<h1>Vlan控制台</h1>
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
	href="createVlanAction.action?queryBusinessId=<s:property value='queryBusinessId' />&businessName=<s:property value='businessName' />
	&nodeType=<s:property value="nodeType" />&nodeId=<s:property value="nodeId" />&treeNodeName=<s:property value="treeNodeName" />
	&pnodeId=<s:property value="pnodeId" />&pnodeName=<s:property value="pnodeName" />&appId=<s:property value="appId" />
	&curFun=<s:property value="curFun" />">申请Vlan</a></span>


<div class="details-con">
	<div class="detail-title">
		<h3 class="apply-title">Vlan列表</h3>
	</div>
	<div class="detail-center cs-ak-center">
		<!-- vlan列表 -->
		<div class="table-content cs-tab-list-op" id="VlanList">
			<table>
				<tr>
					<th class="nl">Vlan名称</th>
					<th>绑定IP段</th>
					<th>所属业务</th>
					<th>状态</th>
					<th>申请时间</th>
					<th style="width: 15%;">操作</th>
				</tr>
				<s:if test="vlanList.size() > 0">
					<s:iterator value="vlanList">
						<tr>
							<td><s:property value="vlanName" /></td>
							<td><s:property value="startIp" /> - <s:property
									value="endIp" /></td>
							<td><s:property value="appName" /></td>
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
								<%-- <s:if test='status.equals("2")'>
									<a style="background-color: #ACB0B4;border-color: #ACB0B4;" title="待创建资源不能释放">
										释放
									</a>
								</s:if>
								<s:elseif test='cancelable == "false"'>
									<a style="background-color: #ACB0B4;border-color: #ACB0B4;" title="该Vlan有在用的Ip，不能释放">
										释放
									</a>
								</s:elseif> --%>
								<a href='javascript:EditVlan("<s:property value="vlanName" />","<s:property value="caseId" />");'>修改</a>
								<s:if test="cancelable != null">
									<a style="background-color: #ACB0B4;border-color: #ACB0B4;" title='<s:property value="cancelable.msg" />'>
										释放
									</a>
								</s:if>
								<s:else>
									<a href='javascript:deleteVlan("<s:property value="vlanName" />","<s:property value="vlanId" />",
									"<s:property value="resPoolId" />","<s:property value="resPoolPartId" />");'>释放</a>
								</s:else>
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
	src="../scripts/pagesjs/console/business/vlan/vlan_list.js"></script>
<div class="page-form" id="vlanEditDialog"  style="display: none;">
		<div class="field">
			<div class="caption"><font class="red-font">*</font>Vlan名称：</div>
			<div class="content">
			<input type="text" size="28" maxlength="25" id="caseId" hidden="hidden"/>
			<input type="text" size="28" maxlength="25" id="vlanId" hidden="hidden"/>
			<input type="text" size="28" maxlength="25" id="edit_vlanName" name="edit_vlanName"  />
			</div>
			<div class="point"></div>
		</div>
</div>