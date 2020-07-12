<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>负载均衡控制台</h1>
<div id="message" style="display: none;">
	<span id="error" class="error"><s:actionerror /></span> <span
		class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
</div>
<span class="apply"> 
<s:url id="addUrl" action="lbApply">
	<s:param name="nodeType" value="nodeType" />
	<s:param name="nodeId" value="nodeId"/>
    <s:param name="treeNodeName" value="treeNodeName"/>
    <s:param name="pnodeId" value="pnodeId"/>
    <s:param name="pnodeName" value="pnodeName"/>
    <s:param name="appId" value="appId"/>
    <s:param name="curFun" value="curFun"/>
	<s:param name="queryBusinessId"><s:property value="#parameters.queryBusinessId" /></s:param>
</s:url>
<a href="${addUrl}">申请负载均衡</a>
</span>
<%-- <span class="apply"><a href="#" onclick="goToLBApplyPage();"></a></span> --%>

<!-- 主机详情 -->
<div class="details-con">
	<div class="detail-title">
		<a href="#" title="返回" onclick="goToLBListPage()"> <span
			class="back"></span>
		</a>
		<h2>
			<s:property value="LBinfo.lbname" />
		</h2>
		<span id="status" class="status mt s-blue"> <s:if
				test="LBinfo.status == 0">待创建</s:if><s:elseif
				test="LBinfo.status == 1">运行中</s:elseif>
		</span>
		<input type="hidden" id="LBinfostatus" value="<s:property value="LBinfo.status" />" />
		<div class="operation" title="操作">
			<!-- 主机操作 -->
			<ul id="vmopt">
			</ul>
		</div>
	</div>
	<div class="detail-info">
		<div class="detail-info-tb">
			<table width="100%" border="0">
				<tr>
					<th>名称：</th>
					<td style="word-break: break-all"><span id="lbname"><s:property
								value="LBinfo.lbname" /></span>&nbsp;&nbsp;</td>
				</tr>
				<tr>
					<th width="95">负载均衡ID：</th>
					<td><s:property value="LBinfo.LBid" /></td>
					<td><input id="lbid" type="hidden"
						value="<s:property value="LBinfo.LBid" />" />
						<input id="instanceid" type="hidden"
						value="<s:property value="LBinfo.instanceid" />" />
						</td>
				</tr>
				<tr>
					<th>负载方式：</th>
					<td><s:if test="LBinfo.LBType==1">单臂</s:if> <s:elseif
							test="LBinfo.LBType==2">双臂</s:elseif> <s:elseif test="LBinfo.LBType==3">三角</s:elseif>
					</td>
				</tr>
				<tr>
					<th>策略：</th>
					<td>
					<input type="hidden" value="<s:property value="LBinfo.Strategy"/>" id="changeStrategy">
					<s:if test="LBinfo.Strategy==0">基于应用响应时间</s:if> <s:elseif
							test="LBinfo.Strategy==1">原IP地址哈希（HASH）值</s:elseif> <s:elseif
							test="LBinfo.Strategy==2">目的IP地址哈希（HASH）值</s:elseif> <s:elseif
							test="LBinfo.Strategy==3">内容字串哈希值</s:elseif> <s:elseif
							test="LBinfo.Strategy==4">Cookie名称哈希值</s:elseif> <s:elseif
							test="LBinfo.Strategy==5">URL哈希值</s:elseif> <s:elseif
							test="LBinfo.Strategy==6">服务器可用带宽</s:elseif> <s:elseif
							test="LBinfo.Strategy==7">服务器应用连接数</s:elseif> <s:elseif
							test="LBinfo.Strategy==8">服务器负荷</s:elseif> <s:elseif
							test="LBinfo.Strategy==9">轮询</s:elseif></td>
							
				</tr>
				<tr>
					<th>协议类型：</th>
					<td><s:property value="LBinfo.protocal" /></td>
				</tr>
				<tr>
					<th>所属业务：</th>
					<td><s:property value="LBinfo.appname" />
					<input type="hidden" id="lbappId" value="<s:property value="LBinfo.appid" />">
					</td>
				</tr>
				<tr>
					<th>浮动IP：</th>
					<td><s:property value="LBinfo.lbip" /></td>
				</tr>
				<tr>
					<th>端口：</th>
					<td><s:property value="LBinfo.lbport" /></td>
				</tr>
				<tr>
					<th>吞吐能力：</th>
					<td><s:property value="LBinfo.Throughput" /> Kbps</td>
				</tr>
				<tr>
					<th>并发链接数：</th>
					<td><s:property value="LBinfo.KbpsConnectNum" /> 个</td>
				</tr>
				<tr>
					<th>链接速度：</th>
					<td><s:property value="LBinfo.NewConnectNum" /> 个/秒</td>
				</tr>
				<tr>
					<th>资源池：</th>
					<td><s:property value="LBinfo.respoolname" /></td>
					<td><input id="respoolId" type="hidden"
						value="<s:property value="LBinfo.respoolId"/>" /></td>
				</tr>
				<tr>
					<th>分区：</th>
					<td><s:property value="LBinfo.respoolPartname" /></td>
					<td><input id="respoolPartId" type="hidden"
						value="<s:property value="LBinfo.respoolPartId"/>" /></td>
					<td></td>
				</tr>
			</table>
		</div>
		<div id="detail_tab">
			<ul class="detail_tab">
				<li class="selected"><a href="#watch_tab_info">对象信息</a></li>
			</ul>
			<div class="detail-center">

				<!-- 对象信息 -->
				<div id="watch_tab_info" class="table-content">
					<div class="table-content" style="height: 20px;">
						<span class="span-btn" style="float: right;"><a
							href="javascript:addobj();">添加对象</a></span>
					</div>
					<div id="net_tab_info" style="display: block;">
						<div class="table-content hasBtn" style="overflow:unset;">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" id="objtable">
								<colgroup>
									<col style="width: 20%;" />
									<col style="width: 16%;" />
									<col style="width: 30%;" />
									<col style="width: 20%;" />
									<col style="width: 14%;" />
								</colgroup>
								<tbody>
									<tr>
										<th class="nl">浮动IP</th>
										<th>端口</th>
										<th>对象描述</th>
										<th>创建时间</th>
										<th>操作</th>
									</tr>
									<s:iterator value="objInfolist">
										<tr>
											<td><s:property value='hostip' />
											<input type="hidden" value='<s:property value='hostip' />' id="hostip"/>
											<input type="hidden" value='<s:property value='hostport' />' id="hostport"/>
											<input type="hidden" value='<s:property value='objInfolist.size()' />' id="objInfolistsize"/>
											<input type="hidden" value='<s:property value='id' />' id="id"/>
											</td>
											<td><s:property value='hostport' /></td>
											<td id="objdescription" title="<s:property value='objdescription' />"><span><s:property value='objdescription' /></span></td>
											<td><s:property value='create_time' /></td>
											<td><span class="span-btn"><a
													href="javaScript:delobjinfo('<s:property value='hostip' />','<s:property value='id' />','<s:property value='hostport' />');"
													style="margin-left: 30px;" >删除</a></span></td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<%-- 添加对象信息  --%>
				<div class="page-form" id="modify_addobj_div" style="display: none;">
					<div class="section">
						<div class="field">
							<div class="caption">对象端口：</div>
							<div class="content">
								<input type="text" id="objport" name="lbport" maxlength="10" />
							</div>
						</div>
						<div id="bindDiv" class="field">
							<span class="apply-span-name" style="margin-left: 70px;">所属业务：</span>
							<a href="javascript:bindBusiness();">绑定业务</a>
						</div>
						<div style="display: none;">
							<h3 class="apply-info-title">
								<input id="ApplyNet" type="checkbox" value="1"
									onclick="checkradioApplyNet();" checked="checked" />
							</h3>
						</div>

						<div class="field">
							<div class="caption">对象IP：</div>
							<table id="ethListTable1"
								style="width: 750px; height: 30px; table-layout: fixed;"
								align="center">
								<thead>
									<tr>
										<td colspan="22"></td>
									</tr>
									<tr height="60px" style="display: none;">
										<td align="center" colspan="2" id="addnet"><span
											class="product-list-btn"><input
												onclick="addNet('ethList1')" id="add" type="checkbox"
												checked="checked">添加</span></td>
									</tr>
								</thead>
								<tbody id="ethList1">
								</tbody>
							</table>
						</div>
						<div id="vm_id" class="field">
							<div class="caption">对象描述：</div>
							<div class="content">
								<input type="text" id="objdescription" maxlength="30">
							</div>
							<div class="point"></div>
						</div>
					</div>
				</div>

				<!-- 所属业务 -->
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
				<!-- 修改负载均衡信息 -->
				<div class="page-form" id="modify_updatelbinfo_div"
					style="display: none;">
					<div class="section">
						<div class="field">
							<div class="caption">负载均衡策略：</div>
							<div class="content">
								 <select
									class="select-max nf" name="Strategy" onchange="clearoption()"
									id="Strategy">
									<option value="<s:property value="LBinfo.Strategy"/>">
									<s:if test="LBinfo.Strategy==0">基于应用响应时间</s:if>
										<s:elseif test="LBinfo.Strategy==1">原IP地址哈希（HASH）值</s:elseif>
										<s:elseif test="LBinfo.Strategy==2">目的IP地址哈希（HASH）值</s:elseif>
										<s:elseif test="LBinfo.Strategy==3">内容字串哈希值</s:elseif>
										<s:elseif test="LBinfo.Strategy==4">Cookie名称哈希值</s:elseif>
										<s:elseif test="LBinfo.Strategy==5">URL哈希值</s:elseif>
										<s:elseif test="LBinfo.Strategy==6">服务器可用带宽</s:elseif>
										<s:elseif test="LBinfo.Strategy==7">服务器应用连接数</s:elseif>
										<s:elseif test="LBinfo.Strategy==8">>服务器负荷</s:elseif>
										<s:elseif test="LBinfo.Strategy==9">轮询</s:elseif></option>
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
						<div>
							<div class="field" style="display:none;"> 
								<div class="caption">网卡信息：</div>
								<div class="content table-content"
									style="max-height: 155px; width: 800px; overflow-y: auto;">
									<table width="100%" border="0" cellspacing="0" cellpadding="0"
										style="border-left: 1px solid #d7d9da; border-right: 1px solid #d7d9da;">
										<colgroup>
											<col style="width: 20%;" />
											<col style="width: 25%;" />
											<col style="width: 21%;" />
											<col style="width: 10%;" />
										</colgroup>
										<tbody id="ethList">
											<tr>
												<th class="nl" style="display:none;">网卡</th>
												<th>VLAN</th>
												<th>IP段</th>
												<th>IP</th>
												<th style="display:none;">网关</th>
												<th>操作</th>
											</tr>
												<tr>
													<td id="eth0" style="display:none;"></td>
													<td><s:property value='LBinfo.vlanName' /><input id="vlanId"
														type="hidden" value="<s:property value="LBinfo.vlanId"/>" /></td>
													<td><s:property value='netinfo.startIp' />&nbsp;~&nbsp;<s:property
															value='netinfo.endIp' /><input id="ipSegmentId" type="hidden"
														value="<s:property value="LBinfo.IPSEGMENT_ID"/>" /></td>
													<td><s:property value='LBinfo.lbip' /></td>
													<td style="display:none;"></td>
													<td><span class="product-list-btn"><a
															href="javascript:editNet('eth0')"
															id="edit">修改</a></span></td>
												</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<script type="text/javascript"
					src="../scripts/pagesjs/console/business/loadbalance/LB_detail.js"></script>
				<link href="../styles/bindApp.css" rel="stylesheet" type="text/css" />
				<script>
					function s(e, a) {
						if (e && e.preventDefault)
							e.preventDefault();
						else
							window.event.returnValue = false;
						a.focus();
					}
				
					
				</script>