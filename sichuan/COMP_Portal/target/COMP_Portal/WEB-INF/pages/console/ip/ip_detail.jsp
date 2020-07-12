<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="list">
	<div class="title">
		<h2>IP地址详情</h2>
	</div>
	<div class="btns">
		<a class="apply" href="ipApplyAction.action"><img
			src="../img/ico-apply.png" alt="" />申请IP地址</a>
	</div>
	<div class="details-con">
		<div id="title-status"
			class="detail-title <s:if test="ipInfo.status==0">blue</s:if>
		        			<s:elseif test="ipInfo.status==1">green</s:elseif>
		        			<s:elseif test="ipInfo.status==3||ipInfo.status==2">red</s:elseif>
		        			<s:elseif test="ipInfo.status==4">orange</s:elseif> ">
			<div class="status">
				<span id="status"> <s:if test='ipInfo.status==0'>
									待创建
								</s:if> <s:elseif test='ipInfo.status==1'>
									已创建
								</s:elseif> <s:elseif test='ipInfo.status==2'>
									创建失败
								</s:elseif> <s:elseif test='ipInfo.status==3'>
									状态异常
								</s:elseif> <s:elseif test='ipInfo.status==4'>
									已删除
								</s:elseif>
				</span>
			</div>
			<h2>
				<s:property value="ipInfo.publicIp" />
				<input id="publicIp" type="hidden"
					value="<s:property value="ipInfo.publicIp" />" />
			</h2>
			<img id="operation" class="operation" src="../img/ico-setting.png" />
			<ul id="ipopt" class="opts">
				<li id="delButton"><a class="del"
					href="javascript:delPublicIp();"><img
						src="../img/ico-opt-del.png" alt="" /></a></li>

			</ul>
		</div>

		<div class="detail-info">
			<table>
				<tbody>
					<tr>
						<th>IP地址：</th>
						<td style="word-break: break-all"><span id="customName"><s:property
									value="ipInfo.publicIp" /></span>
					</tr>
					<tr>
						<th>所属企业客户：</th>
						<td id="appName"><s:property value="ipInfo.appName" /></td>
					</tr>
					<%-- 	<tr>
						<th>绑定主机：</th>
						<td style="word-break: break-all"><span><s:property
									value="ipInfo.hostName" /></span> <a href="javascript:bindHost();"><img
								width="25px" height="25px" src="../img/ico-opts-update.png" /></a></td>
					</tr> --%>
					<tr>
						<th>所属资源池：</th>
						<td id="appName"><s:property value="ipInfo.resPoolName" /></td>
					</tr>
					<tr>
						<th>所属分区：</th>
						<td id="appName"><s:property value="ipInfo.resPoolPartName" /></td>
					</tr>
					<tr>
						<th>描述：</th>
						<td id="description"><s:property value="ipInfo.description" /></td>
					</tr>

				</tbody>
			</table>
			<input type="hidden" id="resourcePool"
				value="<s:property value='resPoolId'/>"> <input
				type="hidden" id="resourcePoolPart"
				value="<s:property value='resPoolPartId'/>"> <input
				id="caseId" type="hidden"
				value="<s:property value="ipInfo.caseId" />" /> <input
				type="hidden" id="appId" value="<s:property value='ipInfo.appId'/>">
		</div>

		<div class="detail-tab">
			<div class="tab">
				<a class="active" href="javascript:void(0);"
					data-tab="#watch_tab_info">端口映射信息</a>
				<s:if test="portConfigList.size()==0">
					<a id="addConfBtn" class="btn" href="javascript:addRule();">添加端口映射</a>
				</s:if>
			</div>
			<div class="detail-center">
				<div id="watch_tab_info">
					<div class="">
						<table>
							<thead>
								<th>映射名称</th>
								<th>映射方式</th>
								<th>虚拟机</th>
								<th>虚拟机IP</th>
								<th>协议类型</th>
								<th>虚拟机端口</th>
								<th>公网端口</th>
								<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="portConfigList">
									<tr>
										<td><s:property value='portConfigName' /></td>
										<td><s:if test="mappingMode==0">PAT</s:if> <s:elseif
												test="mappingMode==1">NAT</s:elseif></td>
										<td><s:property value='vmName' /> <input type="hidden"
											value='<s:property value='vmId' />' /></td>
										<td><s:property value='vmPrivateIp' /></td>
										<td><s:if test="protocol==0">TCP</s:if> <s:elseif
												test="protocol==1">UDP</s:elseif> <s:elseif
												test="protocol==2">ICMP</s:elseif></td>

										<td><s:property value='vmPort' /></td>
										<td><s:property value='publicPort' /></td>
										<td><span class="span-btn"><a
												href="javaScript:delPortConfig('<s:property value='portConfigName' />','<s:property value='vmId' />');"
												style="margin-left: 30px;">删除</a></span></td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</div>
				</div>

			</div>

		</div>

		<div class="time">
			<span class="time-begin">创建时间：
			        <s:if test="ipInfo.status==0"><i></i></s:if>
					<s:else><i><s:property value="ipInfo.createTime" /></i></s:else>
			</span>
						
						<span class="time-end">更新时间：<i><s:property
						value="ipInfo.updateTime" /></i></span>
		</div>
	</div>
</div>


<%-- 添加对象信息  --%>
<div class="page-form" id="add_rule" style="display: none;">

	<table>
		<tr>
			<td>映射名称：</td>
			<td><input type="text" id="portConfigName"
				name="portConfig.portConfigName" maxlength="10" /></td>
		</tr>
		<tr>
			<td>虚拟机：</td>
			<td><span id="vmName"></span> <a id="bind" class="text"
				href="javascript:bindVm();">绑定虚拟机</a></td>
			<td><input id="vmId" name="portConfig.vmId" type="hidden"></td>
		</tr>
		<tr>
			<td>虚拟机IP：</td>
			<td><input type="text" id="vmPrivateIp"
				name="portConfig.vmPrivateIp" readonly /><input type="hidden"
				id="os" /></td>
		</tr>
		<tr>
			<td>映射方式：</td>
			<td><input type="radio" value="1" name="portConfig.mappingMode"
				checked="checked" /><a id="nat" class="label" href="javascript:;">
					NAT </a> <input type="radio" value="0" name="portConfig.mappingMode" /><a
				id="pat" class="label" href="javascript:;"> PAT </a></td>
		</tr>

		<tr>
			<td>协议类型：</td>
			<td><select id="protocol" name="portConfig.protocol">
					<option value="0">TCP</option>
					<option value="1">UDP</option>
					<option value="2">ICMP</option>
			</select></td>
		</tr>
		<tr>
			<td>虚拟机端口：</td>
			<td><input type="text" id="vmPort" name="portConfig.vmPort"
				maxlength="10" readonly /></td>
		</tr>
		<tr>
			<td>公网端口：</td>
			<td><input type="text" id="publicPort"
				name="portConfig.publicPort" maxlength="10" readonly></td>
		</tr>
	</table>
</div>


<div id="bindVm" style="display: none;">

	<div class="toolbar">
		<span class="keyName">虚拟机名称：</span><input type="text" size="30"
			id="queryVmName" /> <a class="search btn fr"
			href="javascript:loadVmList('vmListJson.action')">查询</a>
	</div>

	<table class="table" id="vmListTab">
		<thead>
			<tr>
				<th></th>
				<th>虚拟机名称</th>
				<th>虚拟机IP</th>
				<th>操作系统</th>
			</tr>
		</thead>
		<tbody id="vmListTbody">
		</tbody>
	</table>

	<div class="pageBar" id="vmListPageBarDiv" style="text-align: center"></div>
</div>

<script type="text/javascript"
	src="../scripts/pagesjs/console/ip/ip_detail.js"></script>
