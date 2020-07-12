<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="self-config">
	<div class="config-title">
		<h2>创建Vlan</h2>
		<div id="message" style="display: none;">
			<span id="error" class="error"><s:actionerror /></span> <span
				class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
		</div>
	</div>
	<s:form action="vlanApplyInfoAction3Phase.action" method="post">
	<s:hidden id="nodeType" name="nodeType"></s:hidden>
		<s:hidden id="nodeId" name="nodeId"></s:hidden>
		<s:hidden id="treeNodeName" name="treeNodeName"></s:hidden>
		<s:hidden id="pnodeId" name="pnodeId"></s:hidden>
		<s:hidden id="pnodeName" name="pnodeName"></s:hidden>
		<s:hidden id="curFun" name="curFun"></s:hidden>
		<div class="config-content">
			<h3>基础配置</h3>
			<div>
				<span class="col-name">资源池</span>
				<div class="col-content">
					<div style="position: relative; height: 100%;">
						<select id="respool" name="respoolId"></select> <input
							type="hidden" id="respoolName" name="respoolName"
							value="<s:property value="respool.respoolName"/>" />
					</div>
				</div>
			</div>

			<div>
				<span class="col-name">所属企业客户</span><span id="bindDiv"
					class="col-content"><span style="margin-right: 30px">${businessName}</span><a
					class="" href="javascript:bindBusiness();">重新绑定</a></span> <input
					type="hidden" name="appId" id="appId" value="${queryBusinessId}" />
			</div>

			<div>
				<span class="col-name">名称</span><span class="col-content"><input
					type="text" size="25" id="vlanName" name="vlanName" value="" /></span>
			</div>
			
			<div>
				<span class="col-name">起始Vlan_ID</span><span class="col-content"><input
					type="text" size="25" id="startId" name="startId" value="" maxlength="4" />
					<font size="2">（填写范围为1～4094）</font></span>
			</div>
			
			<div>
				<span class="col-name">终止Vlan_ID</span><span class="col-content"><input
					type="text" size="25" id="endId" name="endId" value="" maxlength="4" />
					<font size="2">（填写范围为1～4094）</font></span>
			</div>

			<div class="submit">
				<a class="btn fr" href="javascript:submitform()">提交</a>

			</div>
		</div>
	</s:form>
</div>

<div id="bindBusiness" style="display: none;">

	<div class="toolbar">
		<span class="keyName">企业客户名称：</span><input type="text" size="30"
			id="queryBusinessName" /> <a class="search btn fr"
			href="javascript:loadBusinessList('businessListJson.action')">查询</a>
	</div>

	<table class="table" id="businessListTab">
		<thead>
			<tr>
				<th></th>
				<th>企业客户名称</th>
				<th>企业客户描述</th>
			</tr>
		</thead>
		<tbody id="businessListTbody">
		</tbody>
	</table>

	<div class="pageBar" id="businessListPageBarDiv"
		style="text-align: center"></div>
</div>

<script type="text/javascript"
	src="../scripts/pagesjs/console/business/vlan3Phase/vlan_apply.js"></script>

