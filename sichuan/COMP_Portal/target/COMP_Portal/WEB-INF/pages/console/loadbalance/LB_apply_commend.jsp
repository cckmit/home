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
		<s:form id="LbsubmitAction" name="LbsubmitAction"
			action="LbsubmitAction.action" method="post">
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


			<%--<div>
				<span class="col-name">所属企业客户</span><span id="bindDiv"
					class="col-content"><a class=""
					href="javascript:bindBusiness();">绑定企业客户</a></span>
			</div>--%>
			
			<div>
				<s:hidden id="userId" name="userId"></s:hidden>
				<s:if test="#session.userInfo.userId=='admin'">
					<span class="col-name">所属企业客户</span><span id="bindDiv"
						class="col-content"><a class=""
						href="javascript:bindBusiness();">绑定企业客户</a></span>
				</s:if>
				<s:else>
					<span class="col-name">所属企业客户</span>
					<span id="bindDiv1" class="col-content">
				</s:else>
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
















<%-- <div id="right">
	<h1>负载均衡控制台</h1>
   <input type="hidden" id="msg" value="<s:property value="msg"/>" />
   <input type="hidden" id="result" value="<s:property value="result"/>" />
	<s:form id="LbsubmitAction" name="LbsubmitAction"
		action="LbsubmitAction.action" method="post">
		<span class="region"> 选择资源池：<select class="select-max nf"
			id="respool" name="respoolId"></select> 分区：<select
			class="select-max nf" id="respoolpart" name="respoolPartId"></select>
			<input type="hidden" id="respoolName" name="respoolName"
			value="<s:property value="respool.respoolName"/>" /> <input
			type="hidden" id="respoolPartName" name="respoolPartName"
			value="<s:property value="respoolPart.respoolPartName"/>" />
		</span>
		<!-- 主机详情 -->
		<div class="details-con">
			<div class="detail-title">
				<a href="loadBalanceAction.action" title="返回"> <span
					class="back"></span>
				</a>
				<h3 class="apply-title">申请负载均衡</h3>
			</div>
			<div class="details-con1">
				<div id="relate_resource_div"
					class="detail-info apply-info-l apply-info">
                     <br>
                     <div class="vm-apply-detail-l">
						<span class="apply-span-name">负载均衡名称：</span> <input type="text"
							size="30" id="lbname" name="lbname" maxlength="16" onchange="checkname()"/>
					</div>
                     <div class="vm-apply-detail-l">
						<span class="apply-span-name">负载均衡方式：</span> 
							<select class="select-max nf" name="LBType">
							<option value="1">单臂</option>
							<option value="2">双臂</option>
							<option value="3">三角</option>
							</select>
					</div>
					<div class="vm-apply-detail-l">
						<span class="apply-span-name">负载均衡策略：</span> 
							<select class="select-max nf" name="Strategy">
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
					<div id="bindDiv">
						<span class="apply-span-name" style="margin-left:27px;">所属业务：</span> <a
							href="javascript:bindBusiness();">绑定业务</a>
					</div>
					<div style="display:none;">
						<h3 class="apply-info-title" >
							<input id="ApplyNet" type="checkbox" value="1"
								onclick="checkradioApplyNet();" checked="checked"/>
						</h3>
					</div>
					<div style="margin-left:10px;" class="vm-apply-detail-l">
					<table id="ethListTable1"
						style="width: 750px; height: 30px;table-layout: fixed;float:left;"
						align="center">
						<thead>
							<tr>
								<td colspan="22"></td>
							</tr>
							<tr height="60px" style="display:none;">
								<td align="center" colspan="2" id="addnet"><span class="product-list-btn"><input
										onclick="addNet('ethList1')" id="add" type="checkbox" checked="checked">添加</span></td>
							</tr>
						</thead>
						<tbody id="ethList1">
						</tbody>
					</table>
					</div>
					<div class="vm-apply-detail-l">
						<span class="apply-span-name">负载均衡端口：</span> <input type="text"
							size="30" id="lbport" name="lbport" maxlength="16" />
					</div>
					<div class="vm-apply-detail-l">
						<span class="apply-span-name">流量协议类型：</span> 
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
							<option value="Radius">Radius</option>
							\<option value="SIP">SIP</option>
							<option value="GTP">GTP</option>
							</select>
					</div>
				    
					<div class="vm-apply-detail-l">
						<span class="apply-span-name">吞吐能力(Kbps)：</span> <input type="text"
							size="30" id="throughput" name="throughput" maxlength="9"  />
					</div>
					<div class="vm-apply-detail-l">
						<span class="apply-span-name">并发链接数(个)：</span> <input type="text"
							size="30" id="connectNum" name="connectNum" maxlength="9" />
					</div>
					<div class="vm-apply-detail-l">
						<span class="apply-span-name">链接速度(个/秒)：</span> <input type="text"
							size="30" id="newConnectNum" name="newConnectNum" maxlength="9" />
					</div>
					<hr class="apply-line" />
					</div>
					<div class="apply-btn-commit">
						<span> <a href="javascript:submitform()">提交</a>
						</span>
					</div>
				<div class="detail-time"></div>
			</div>
		</div>
	</s:form>
</div>

<s:include value="chargesList.jsp" /> --%>
<script type="text/javascript"
	src="../scripts/pagesjs/console/loadbalance/LB_apply.js"></script>