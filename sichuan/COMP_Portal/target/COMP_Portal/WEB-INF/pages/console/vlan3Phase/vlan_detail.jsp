<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="list">
	<div class="title">
		<h2>Vlan详情</h2>
	</div>
	<div class="btns">
		<a class="apply" href="createVlan3PhaseAction.action"><img
			src="../img/ico-apply.png" alt="" />申请Vlan</a>
	</div>
	<div class="details-con">
		<div id="title-status"
			class="detail-title <s:if test="vlanInfo.status==2">blue</s:if>
		        			<s:elseif test="vlanInfo.status==0">green</s:elseif> ">
			<div class="status">
				<span id="status"> <s:if test='vlanInfo.status==2'>
									待创建
								</s:if> <s:elseif test='vlanInfo.status==0'>
									已创建
								</s:elseif> 
				</span>
			</div>
			<h2>
				<s:property value="vlanInfo.vlanName" />
				<input id="vlanId" type="hidden"
					value="<s:property value="vlanInfo.vlanId" />" />
			</h2>
			<img id="operation" class="operation" src="../img/ico-setting.png" />
			<ul id="ipopt" class="opts">
			  <s:if test="vlanInfo.status==0">
				<li id="delButton"><a class="del" href='javascript:deleteVlan("<s:property value="caseId" />");'><img
						src="../img/ico-opt-del.png" alt="" /></a></li>
              </s:if>
			</ul>
		</div>

		<div class="detail-info">
			<table>
				<tbody>
					<tr>
						<th>所属企业客户：</th>
						<td id="appName"><s:property value="vlanInfo.appName" /></td>
					</tr>

					<tr>
						<th>所属资源池：</th>
						<td id="resPoolName"><s:property value="vlanInfo.resPoolName" /></td>
					</tr>
					<tr>
						<th>Vlan范围ID：</th>
						<td id="vlanId"><s:property value="vlanInfo.vlanId" /></td>
					</tr>
					<tr>
						<th>Vlan范围：</th>
						<td id="vlanRange"><s:property value="vlanInfo.startId" />-<s:property value="vlanInfo.endId" /></td>
					</tr>
					<tr>
						<th>创建人：</th>
						<td id="createUser"><s:property value="vlanInfo.createUser" /></td>
					</tr>
					
					<tr>
						<th>Vlan段分配情况：</th>
						<td id="allocated"><s:if test="vlanInfo.allocated==0">未分配</s:if>
							<s:elseif test="vlanInfo.allocated==1">已分配</s:elseif></td>
					</tr>

				</tbody>
			</table>
			<input type="hidden" id="resourcePool" value="<s:property value='vlanInfo.resPoolId'/>">
			<input id="appId" type="hidden" value="<s:property value='vlanInfo.appId'/>" />
			<input id="caseId" type="hidden" value="<s:property value='vlanInfo.caseId'/>" />
		</div>

		<div class="time">
			<span class="time-begin">创建时间：
			        <s:if test="vlanInfo.status==2"><i></i></s:if>
					<s:else><i><s:property value="vlanInfo.createTime" /></i></s:else>
			</span>						
						<span class="time-end">更新时间：<i><s:property
						value="vlanInfo.updateTime" /></i></span>
		</div>
	</div>
</div>

<script type="text/javascript"
	src="../scripts/pagesjs/console/vlan3Phase/vlan_detail.js"></script>
