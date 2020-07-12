<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<link href="../styles/vm.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.selectNet {
	width: 100px;
	background: none repeat scroll 0 0 rgba(0, 0, 0, 0);
}
</style>

</head>

<!--右侧-->
<div id="right">
	<h1>负载均衡控制台</h1>
<!-- 	<div id="message" style="display: none;"> -->
<%-- 		<span id="error" class="error"><s:actionerror /></span> <span --%>
<%-- 			class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span> --%>
<!-- 	</div> -->
   <input type="hidden" id="msg" value="<s:property value="msg"/>" />
<input type="hidden" id="result" value="<s:property value="result"/>" />
	<s:form id="LBsubmitAction" name="LBsubmitAction"
		action="LBsubmitAction.action" method="post">
		<s:hidden id="nodeType" name="nodeType"></s:hidden>
		<s:hidden id="nodeId" name="nodeId"></s:hidden>
		<s:hidden id="treeNodeName" name="treeNodeName"></s:hidden>
		<s:hidden id="pnodeId" name="pnodeId"></s:hidden>
		<s:hidden id="pnodeName" name="pnodeName"></s:hidden>
		<s:hidden id="curFun" name="curFun"></s:hidden>
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
			<s:url id="backUrl" action="lbQueryListAction">
					<s:param name="nodeType" value="nodeType" />
					<s:param name="nodeId" value="nodeId" />
					<s:param name="treeNodeName" value="treeNodeName" />
					<s:param name="pnodeId" value="pnodeId" />
					<s:param name="pnodeName" value="pnodeName" />
					<s:param name="appId" value="appId" />
					<s:param name="curFun" value="curFun" />
					<s:param name="queryBusinessId">
						<s:property value="#parameters.queryBusinessId" />
					</s:param>
					<s:param name="type">15</s:param>
				</s:url>
				<a href="${backUrl}" title="返回"><span class="back"></span></a>
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
<!-- 						<input type="text" -->
<!-- 							size="30" id="LBType" name="LBType" maxlength="16" /> -->
							<select class="select-max nf" name="LBType">
							<option value="1">单臂</option>
							<option value="2">双臂</option>
							<option value="3">三角</option>
							</select>
					</div>
					<div class="vm-apply-detail-l">
						<span class="apply-span-name">负载均衡策略：</span> 
<!-- 						<input type="text" -->
<!-- 							size="30" id="Strategy" name="Strategy" maxlength="16" /> -->
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
<!-- 					<div id="bindDiv"> -->
<%-- 						<span class="apply-span-name" style="margin-left:27px;">所属业务：</span> <a --%>
<!-- 							href="javascript:bindBusiness();">绑定业务</a> -->
<!-- 					</div> -->
                    <div id="bindDiv">
						<span class="apply-span-name">所属业务：</span> <input type="hidden"
							id="app" name="appId"
							value="<s:property value="#parameters.queryBusinessId" />">
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
<!-- 						<input type="text" -->
<!-- 							size="30" id="protocal" name="protocal" maxlength="16" /> -->
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
<div id="bindBusiness" class="float-div"
	style="display: none; width: 700px;">
	<div class="float-toolsbar">
		<span class="keyName">业务名称：</span> <input type="text" size="30"
			id="queryBusinessName" />
		<ul class="opt-btn fr">
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
						<col style="width: 40%;" />
						<col style="width: 50%;" />
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
		<!-- <DIVstyleDIVstyle="clear:both;"></DIV> --> 
	</div>
	<div class="pageBar" id="businessListPageBarDiv"
		style="text-align: center"></div>
		
</div>
<script type="text/javascript"
	src="../scripts/pagesjs/console/business/loadbalance/LB_list.js"></script>
<script type="text/javascript"
	src="../scripts/pagesjs/console/business/loadbalance/LB_apply_commend.js"></script>