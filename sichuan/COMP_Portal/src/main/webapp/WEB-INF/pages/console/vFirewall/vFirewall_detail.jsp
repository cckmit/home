<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="list">
	<div class="title">
		<h2>虚拟防火墙详情</h2>
	</div>
	<div class="btns">
		<a class="apply" href="vFWApplyAction.action"><img
			src="../img/ico-apply.png" alt="" />申请虚拟防火墙</a>
	</div>
	<div class="details-con">
		<div id="title-status"
			class="detail-title <s:if test="vfw.status==0">blue</s:if>
		        			<s:elseif test="vfw.status==1">green</s:elseif>
		        			<s:elseif test="vfw.status==3||vfw.status==2">red</s:elseif>
		        			<s:elseif test="vfw.status==4">orange</s:elseif> ">
			<div class="status">
				<span id="status"> <s:if test='vfw.status==0'>
									待创建
								</s:if> <s:elseif test='vfw.status==1'>
									已创建
								</s:elseif> <s:elseif test='vfw.status==2'>
									创建失败
								</s:elseif> <s:elseif test='vfw.status==3'>
									状态异常
								</s:elseif> <s:elseif test='vfw.status==4'>
									已删除
								</s:elseif>
				</span>
			</div>
			<h2>
				<s:property value="vfw.fwName" />
				<input id="fwId" type="hidden"
					value="<s:property value="vfw.fwId" />" />
			</h2>
			<img id="operation" class="operation" src="../img/ico-setting.png" />
			<ul id="ipopt" class="opts">
				<li id="delButton"><a class="del" href="javascript:delVfw();"><img
						src="../img/ico-opt-del.png" alt="" /></a></li>

			</ul>
		</div>

		<div class="detail-info">
			<table>
				<tbody>
					<tr>
						<th>所属企业客户：</th>
						<td id="appName"><s:property value="vfw.appName" /></td>
					</tr>

					<tr>
						<th>所属资源池：</th>
						<td id="resPoolName"><s:property value="vfw.resPoolName" /></td>
					</tr>
					<tr>
						<th>所属分区：</th>
						<td id="resPoolPartName"><s:property value="vfw.resPoolPartName" /></td>
					</tr>
					<tr>
						<th>描述：</th>
						<td id="description"><s:property value="vfw.description" /></td>
					</tr>

				</tbody>
			</table>
			 <input
				type="hidden" id="resourcePool"
				value="<s:property value='vfw.resPoolId'/>"> <input
				type="hidden" id="resourcePoolPart"
				value="<s:property value='vfw.resPoolPartId'/>">
				<input id="appId" type="hidden" value="<s:property value='vfw.appId'/>" />
		</div>

		<div class="detail-tab">
			<div class="tab">
				<a class="active" href="javascript:void(0);"
					data-tab="#watch_tab_info">防火墙规则信息</a> 
					<s:if test="vfw.fwId !=null">
					<a class="btn"
					href="javascript:addRule();">添加规则</a>
					</s:if>
			</div>
			<div class="detail-center">
				<div id="watch_tab_info">
					<div class="">
						<table>
							<thead>
								<th>规则名称</th>
								<th>协议类型</th>
								<th>源IP</th>
								<th>源端口</th>
								<th>目的IP</th>
								<th>目的端口</th>
								<th>动作</th>
								<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="vfwRuleList">
									<tr>
										<td><s:property value='fwRuleName' /></td>
										<td><s:if test="protocol==1">TCP</s:if>
											<s:elseif test="protocol==2">UDP</s:elseif>
										</td>
										<td><s:property value='sourceIp' /></td>
										<td><s:property value='sourcePort' /> </td>
										<td><s:property value='destinationIp' /></td>
										

										<td><s:property value='destinationPort' /></td>
										<td>
										<s:if test="actType==1">允许</s:if>
											<s:elseif test="actType==2">拒绝</s:elseif>
											<s:elseif test="actType==3">丢弃</s:elseif>
										</td>
										<td><span class="span-btn"><a
												href="javaScript:delFwRule('<s:property value='fwRuleName' />','<s:property value='fwId' />');"
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
			        <s:if test="vfw.status==0"><i></i></s:if>
					<s:else><i><s:property value="vfw.createTime" /></i></s:else>
			</span>						
						<span class="time-end">更新时间：<i><s:property
						value="vfw.updateTime" /></i></span>
		</div>
	</div>
</div>


<%-- 添加对象信息  --%>
<div class="page-form" id="add_rule" style="display: none;">

	<table>
		<tr>
			<td>规则名称：</td>
			<td><input type="text" id="fwRuleName"
				name="portConfig.portConfigName" maxlength="10" /></td>
		</tr>
	<tr>
			<td>协议类型：</td>
			<td><select id="protocol" name="vfwRule.protocol">
					<option value="1">TCP</option>
					<option value="2">UDP</option>
			</select></td>
		</tr>
		<tr>
			<td>源IP：</td>
			<td><input type="text" id="sourceIp"
				name="vfwRule.sourceIp"  maxlength="50" /></td>
		</tr>
	<tr>
			<td>源端口：</td>
			<td><input type="text" id="sourcePort" name="vfwRule.sourcePort"
				maxlength="10"  /></td>
		</tr>
		<tr>
			<td>目的IP：</td>
			<td><input type="text" id="destinationIp" name="vfwRule.destinationIp"
				maxlength="50"  /></td>
		</tr>
		<tr>
			<td>目的端口：</td>
			<td><input type="text" id="destinationPort"
				name="vfwRule.destinationPort" maxlength="10" ></td>
		</tr>
		<tr>
			<td>动作：</td>
			<td><select id="actType" name="vfwRule.actType">
					<option value="1">允许</option>
					<option value="2">拒绝</option>
					<option value="3">丢弃</option>
			</select></td>
		</tr>
	</table>
</div>


<div id="bindVm" style="display: none;">

	<div class="toolbar">
		<span class="keyName">虚拟机名称：</span><input type="text" size="30"
			id="queryVmName" /> <a class="search btn fr"
			href="javascript:loadVmList('businessListJson.action')">查询</a>
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
	src="../scripts/pagesjs/console/vFirewall/vFirewall_detail.js"></script>
