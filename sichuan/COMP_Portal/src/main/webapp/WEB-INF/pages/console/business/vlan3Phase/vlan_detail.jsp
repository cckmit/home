<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="list">
	<div class="title">
		<h2>Vlan详情</h2>
	</div>
	<div class="btns">
		<s:hidden id="nodeType" name="nodeType"></s:hidden>
		<s:hidden id="nodeId" name="nodeId"></s:hidden>
		<s:hidden id="treeNodeName" name="treeNodeName"></s:hidden>
		<s:hidden id="pnodeId" name="pnodeId"></s:hidden>
		<s:hidden id="pnodeName" name="pnodeName"></s:hidden>
		<s:hidden id="appId" name="appId"></s:hidden>
		<s:hidden id="curFun" name="curFun"></s:hidden>
		<input type="hidden" id="statusHidden"
			value="<s:property value='diskInfo.diskStatus' />" />

		<s:url id="addUrl" action="createVlanAction3Phase">
			<s:param name="nodeType" value="nodeType" />
			<s:param name="nodeId" value="nodeId" />
			<s:param name="treeNodeName" value="treeNodeName" />
			<s:param name="pnodeId" value="pnodeId" />
			<s:param name="pnodeName" value="pnodeName" />
			<s:param name="appId" value="appId" />
			<s:param name="curFun" value="curFun" />
			<s:param name="businessName" value="businessName" />
			<s:param name="queryBusinessId" value="queryBusinessId" />
		</s:url>

		<a class="apply" href="${addUrl}"><img src="../img/ico-apply.png"
			alt="" />申请Vlan</a>
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
						<td id="appName"><s:property value="vlanInfo.appName" /> <input
							type="hidden" id="businessId"
							value='<s:property value="diskInfo.businessId" />'></td>
					</tr>
					<tr>
						<th>所属资源池：</th>
						<td><s:property value="vlanInfo.resPoolName" /></td>
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
			<span class="time-begin">创建时间：<i><s:property
						value="vlanInfo.createTime" /></i></span><span class="time-end">更新时间：<i><s:property
						value="vlanInfo.updateTime" /></i></span>
		</div>

<script type="text/javascript"
	src="../scripts/pagesjs/console/business/vlan3Phase/vlan_detail.js"></script>

	</div>