<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="self-config">
	<div class="config-title">
		<h2>申请IP段</h2>
		<div id="message" style="display: none;">
			<span id="error" class="error"><s:actionerror /></span> <span
				class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
		</div>
	</div>
<s:form id="IpSegmentCreateInfoAction"
		action="IpSegmentCreateInfoAction.action" method="post">
		
		<input type="hidden" id="ipType" />
		
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
				<span class="col-name">IP类型</span>
				<div class="col-content">
					<input id="ipType_ipv4" name="ipType" type="radio" checked="checked" value="0" onchange="chargeIpTypeChange(this)">
					<a id="chargeIpType_ipv4" class="text" href="javascript:;">IPV4</a>
					
					<input id="ipType_ipv6" name="ipType" type="radio" value="1" onchange="chargeIpTypeChange(this)">
					<a id="chargeIpType_ipv6" class="text" href="javascript:;">IPV6</a>
				</div>
			</div>

			<div>
				<span class="col-name">所属IP段</span><span class="col-content"
					id="bindIpSegmentDiv"><a class=""
					href="javascript:ipSegment.bindIpSegment();">绑定IP段</a></span>
			</div>

			<div>
				<span class="col-name">所属业务</span><span id="bindDiv"
					class="col-content"><span style="margin-right: 30px">${businessName}</span><a
					class="" href="javascript:bindBusiness();">重新绑定</a></span>
					<input type="hidden" name="appId" id="appId"  value="${queryBusinessId}"/>
			</div>




			<div>
				<span class="col-name">名称</span><span class="col-content"><input
					type="text" size="25" id="IpSegmentDesc" name="IpSegmentDesc"
					value="" /></span>
			</div>

			<div class="submit">
				<a class="btn fr" href="javascript:submitform()">提交</a>

			</div>
		</div>
	</s:form>
</div>

<div id="bindBusiness" style="display: none;">

	<div class="toolbar">
		<span class="keyName">业务名称：</span><input type="text" size="30"
			id="queryBusinessName" /> <a class="search btn fr"
			href="javascript:loadBusinessList('businessListJson.action')">查询</a>
	</div>

	<table class="table" id="businessListTab">
		<thead>
			<tr>
				<th></th>
				<th>业务名称</th>
				<th>业务描述</th>
			</tr>
		</thead>
		<tbody id="businessListTbody">
		</tbody>
	</table>

	<div class="pageBar" id="businessListPageBarDiv"
		style="text-align: center"></div>
</div>

<script type="text/javascript"
	src="../scripts/pagesjs/console/business/ipSegment/ipSegment_apply.js"></script>



<!-- IP段查询,选择dialog -->
<div id="bindIpSegment" style="display: none;">
	<%-- <div class="float-toolsbar">
		<span class="keyName">业务名称：</span> <input type="text"
			class="float-input-long" id="queryBusinessName" />
		<ul class="opt-btn vh-btn-r">
			<li><a class="search"
				href="javascript:loadBusinessList('businessListJson.action')">查询</a></li>
		</ul>
	</div> --%>
	<table id="ipSegmentListTab" class="table">
		<thead>
			<tr>
				<th></th>
				<th>IP段地址</th>
				<th>IP个数</th>
			</tr>
		</thead>
		<tbody id="ipSegmentListTbody">
		</tbody>
	</table>
	<div class="pageBar" id="ipSegmentListPageBarDiv"
		style="text-align: center"></div>
</div>

