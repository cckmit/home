<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">
.cart-row {
	height: calc(160px * 0.25);
	line-height: calc(160px * 0.25);
}
</style>

<div class="self-config">
	<div class="config-title">
		<h2>自定义配置</h2>
		<div id="message" style="display: none;">
			<span id="error" class="error"><s:actionerror /></span> <span
				class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
		</div>
	</div>
	<div class="config-content" style="margin-bottom: 200px;">
		<h3>基础配置</h3>
		<s:form id="lBsubmitAction" name="LBsubmitAction"
			action="LBsubmitAction.action" method="post">
			<s:hidden id="nodeType" name="nodeType"></s:hidden>
			<s:hidden id="nodeId" name="nodeId"></s:hidden>
			<s:hidden id="treeNodeName" name="treeNodeName"></s:hidden>
			<s:hidden id="pnodeId" name="pnodeId"></s:hidden>
			<s:hidden id="pnodeName" name="pnodeName"></s:hidden>
			<s:hidden id="curFun" name="curFun"></s:hidden>
			<div>
				<span class="col-name">资源池分区</span>
				<div class="col-content">
					<div class="col-content">
						<div style="position: relative; height: 100%;">
							<select id="respool" name="respoolId"></select> <select
								style="left: 155px;" id="respoolpart" name="respoolPartId"></select>
							<input type="hidden" id="respoolName" name="respoolName"
								value="<s:property value="respool.respoolName"/>" /> <input
								type="hidden" id="respoolPartName" name="respoolPartName"
								value="<s:property value="respoolPart.respoolPartName"/>" />

						</div>
					</div>
				</div>
			</div>
			<div>
				<span class="col-name">名称</span><span class="col-content"><input
					type="text" size="25" id="lbname" name="lbname" value=""
					onchange="checkname()" /></span>
			</div>

			<div>
				<span class="col-name">负载均衡方式</span>
				<div class="col-content">
					<div style="position: relative; height: 100%;">
						<select id="lbType" name="LBType">
							<option value="1">单臂</option>
							<option value="2">双臂</option>
							<option value="3">三角</option>
						</select>
					</div>
				</div>
			</div>

			<div>
				<span class="col-name">负载均衡策略</span>
				<div class="col-content">
					<div style="position: relative; height: 100%;">
						<select id="strategy" name="Strategy">
							<option value="0">基于应用响应时间</option>
							<option value="1">原IP地址哈希（HASH）值</option>
							<option value="2">目的IP地址哈希（HASH）值</option>
							<option value="3">内容字串哈希值</option>
							<option value="4">Cookie名称哈希值</option>
							<option value="5">URL哈希值</option>
							<option value="6">服务器可用带宽</option>
							<option value="7">服务器应用连接数</option>
							<option value="8">服务器负荷</option>
							<option value="9">轮询</option>
						</select>
					</div>
				</div>
			</div>


			<div>
				<span class="col-name">所属业务</span><span id="bindDiv"
					class="col-content"><span style="margin-right: 30px">${businessName}</span><a
					class="" href="javascript:bindBusiness();">重新绑定</a></span> <input
					type="hidden" name="appId" id="appId" value="${queryBusinessId}" />
			</div>


			<div>
				<span class="col-name">浮动IP</span>
				<div class="col-content">
					<div
						style="position: relative; height: 100%; width: 155px; float: left;">
						<select id="vlanSelect" name="vlanId"></select>
					</div>
					<div
						style="position: relative; height: 100%; width: 155px; float: left;">

						<select id="ipsegmentSelect" name="ipSegmentId"></select>
					</div>
					<div
						style="position: relative; height: 100%; width: 155px; float: left;">
						<select id="privateIpSelect" name="lbip"></select>
						 <input type="hidden" id="ipType" name="ipType" value="" />
					</div>
				</div>
			</div>
			<div>
				<span class="col-name">负载均衡端口</span><span class="col-content"><input
					type="text" size="30" id="lbport" name="lbport" maxlength="16" /></span>
			</div>


			<div>
				<span class="col-name">流量协议类型</span>
				<div class="col-content">
					<div style="position: relative; height: 100%;">
						<select class="select-max nf" name="protocal" id="protocal">
							<option value="TCP">TCP</option>
							<option value="UDP">UDP</option>
							<option value="HTTP">HTTP</option>
							<option value="HTTPS">HTTPS</option>
							<option value="FTP">FTP</option>
							<option value="FTPS">FTPS</option>
							<option value="SIP">SIP</option>
							<option value="SIPS">SIPS</option>
							<option value="Diameter">Diameter</option>
							<option value="SMTP">SMTP</option>
							<option value="SMTPS">SMTPS</option>
							<option value="SMPP">SMPP</option>
							<option value="DNS">DNS</option>
							<option value="Radius">Radius</option> \
							<option value="SIP">SIP</option>
							<option value="GTP">GTP</option>
						</select>
					</div>
				</div>
			</div>

			<div>
				<span class="col-name">吞吐能力</span><span class="col-content"><input
					type="number" size="30" id="throughput" name="throughput"
					maxlength="16" />Kbps</span>
			</div>

			<div>
				<span class="col-name">并发链接数</span><span class="col-content"><input
					type="number" size="30" id="connectNum" name="connectNum"
					maxlength="16" />个</span>
			</div>

			<div>
				<span class="col-name">链接速度</span><span class="col-content"><input
					type="number" size="30" id="newConnectNum" name="newConnectNum"
					maxlength="16" />个/秒</span>
			</div>
		</s:form>
	</div>
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
	src="../scripts/pagesjs/console/business/loadbalance/LB_apply.js"></script>
<script>
	$(function() {
		$('div.cart-bar').load('../cart-bar/lb_cart.jsp', function() {
			$("#cart-app").text('${businessName}');
		});
	})

	
</script>