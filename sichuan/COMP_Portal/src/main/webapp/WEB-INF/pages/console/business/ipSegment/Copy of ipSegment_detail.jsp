<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style>
<!--
.tag {
	margin-top: 22px;
}

.resPoolDisplay {
	margin-right: 50px;
}

.detail-center div {
	height: auto;
}

.alignCenter {
	text-align: center;
}
-->
</style>
<h1>IP段控制台</h1>
<!-- 主机详情 -->
<div class="details-con">
	<!-- 隐藏标签区域 -->
	<input type="hidden" id="ipSegmentId"
		value="<s:property value='ipSegment.ipSegmentId' />"> <input
		type="hidden" id="resourcePool"
		value="<s:property value='resPoolId'/>"> <input type="hidden"
		id="resourcePoolPart" value="<s:property value='resPoolPartId'/>">
	<div class="detail-title">
		<a id="goBacka"
			href="ipSegmentQueryListAction.action?queryBusinessId=<s:property value='queryBusinessId' />&nodeType=<s:property value="nodeType" />
			&nodeId=<s:property value="nodeId" />&treeNodeName=<s:property value="treeNodeName" />&pnodeId=<s:property value="pnodeId" />
			&pnodeName=<s:property value="pnodeName" />&appId=<s:property value="appId" />&curFun=<s:property value="curFun" />"
			title="返回"> <span class="back"></span>
		</a>
		<h2 id="titleDesc">
			<s:property value="ipSegment.ipSegmentDesc" />
		</h2>
		<s:if test='ipSegment.status==2'>
			<span class="status s-blue tag">待创建</span>
		</s:if>
		<s:elseif test="ipSegment.status==0">
			<span class="status s-green tag">已创建</span>
		</s:elseif>

		<s:if test='releaseMsg != null && releaseMsg != ""'>
			<div class="operation-delete" title='<s:property value="releaseMsg" />' onclick="noticeReleaseMsg('<s:property value="releaseMsg" />');" ></div>
		</s:if>
		<s:else>
			<div class="operation-delete" title="释放" onclick="delIpSegment();"></div>
		</s:else>

	</div>
	<div class="detail-info apply-info vh-info-h">
		<div class="apply-info-top">
			<span class="apply-span-name">所属业务：</span> <span class="detail-text"
				id="appName"><s:property value="ipSegment.appName" /></span>
		</div>
		<div id="hd_div">
			<span class="apply-span-name">名称：</span> <span
				class="detail-text" id="ipSegmentDesc"><s:property
					value="ipSegment.ipSegmentDesc" /></span> <span class="product-list-btn"><a
				href="javascript:updateName();">修改</a></span>
		</div>

		<div id="hd_div">
			<span class="apply-span-name">IP段地址:</span> <span class="detail-text"><s:property
					value="ipSegment.startIp" /> - <s:property value="ipSegment.endIp" /></span>
		</div>

		<s:if test="ipSegment.status==0&&ipSegment.vlanId!=null">
			<div id="hd_div">
				<span class="apply-span-name">所属Vlan:</span> <span
					class="detail-text"><s:property value="ipSegment.vlanName" />
				</span>
			</div>

			<div id="hd_div">
				<span class="apply-span-name">已用:</span> <span class="detail-text"><s:property
						value="ipUsedCount" /> </span>
			</div>
		</s:if>

		<div id="hd_div">
			<span class="apply-span-name">未用:</span> <span class="detail-text"><s:property
					value="ipUnusedCount" /> </span>
		</div>

		<hr class="apply-line" />

		<div class="detail-center" style="height: auto;">
			<div id="net_tab_info">
				<div class="table-content hasBtn alignCenter">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<colgroup>
							<col style="width: 18%;" />
							<col style="width: 36%;" />
							<col style="width: 30%;" />
							<col style="width: 17%;" />
						</colgroup>
						<tbody>
							<tr>
								<th class="nl">IP</th>
								<th>云主机ID</th>
								<th>虚拟机名称</th>
								<th>虚拟机网卡</th>
							</tr>
							<s:iterator value="usedIpList">
								<tr>
									<td><s:property value='ip' /></td>
									<td><s:property value='vmId' /></td>
									<td><s:property value='vmName' /></td>
									<td><s:property value='eth' /></td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!-- 翻页 -->
		<div class="pageBar">
			<s:property value="pageBar" escape="false" />
		</div>
	</div>
</div>
<div id="mountDisk" class="float-div" style="display: none;">
	<div class="float-toolsbar">
		<select class="select-max" id="diskSeachKey">
			<option value="vmName">设备名称</option>
			<option value="vmId">ID</option>
		</select> <input type="text" class="float-input-long" id="diskSeachVal" />
		<ul class="opt-btn vh-btn-r">
			<li><a class="search" href="javascript:seachDisk();">查询</a></li>
		</ul>
	</div>
	<div class="float-div-center">
		<div class="table-content">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<colgroup>
					<col style="width: 10%;" />
					<col style="width: 50%;" />
					<col style="width: 40%;" />
				</colgroup>
				<tbody id="vmList"></tbody>
			</table>
		</div>
	</div>
</div>
<div id="updateName" class="float-div" style="display: none;">
	新名称：<input type="text" name="modify_custom_name"
		value="<s:property value='ipSegment.ipSegmentDesc' />" />
</div>
<script type="text/javascript"
	src="../scripts/pagesjs/console/business/ipSegment/ipSegment_detail.js"></script>
