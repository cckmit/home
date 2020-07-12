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
	<s:form action="vlanApplyInfoAction.action" method="post">
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
			<s:hidden id="userId" name="userId"></s:hidden>
			<div>
				<span class="col-name">所属企业客户</span><span class="col-content"
					id="bindDiv"><a class="" href="javascript:bindBusiness();">绑定企业客户</a></span>
			</div>


			<div>
				<span class="col-name">名称</span><span class="col-content"><input
					type="text" size="25" id="vlanName" name="vlanName" value="" /></span>
			</div>

			<div>
				<span class="col-name">IP类型</span>
				<input type="hidden" id="ipType" value="0" />
				<div class="col-content">
					<input id="ipType_ipv4" name="ipType" type="radio" checked="checked" value="0" onchange="chargeIpTypeChange(this)">
					<a id="chargeIpType_ipv4" class="text" href="javascript:;">IPV4</a>
					
					<input id="ipType_ipv6" name="ipType" type="radio" value="1" onchange="chargeIpTypeChange(this)">
					<a id="chargeIpType_ipv6" class="text" href="javascript:;">IPV6</a>
				</div>
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
	src="../scripts/pagesjs/console/vlan/vlan_apply.js"></script>

