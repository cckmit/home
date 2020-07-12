<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="list">
	<div class="title">
		<h2>IP段详情</h2>
	</div>
	<div class="btns">
			<s:url id="addUrl" action="createIpSegmentAction">
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
		<a class="apply" href="${addUrl}"><img
			src="../img/ico-apply.png" alt="" />申请IP段</a>
	</div>
	<div class="details-con">
		<div id="title-status"
			class="detail-title <s:if test='ipSegment.status==2'>blue</s:if> <s:elseif test='ipSegment.status==0'>green</s:elseif>">
			<div class="status">
				<span id="status"> <s:if test='ipSegment.status==2'>
						待创建
					</s:if> <s:elseif test="ipSegment.status==0">
						已创建
					</s:elseif>
				</span>
			</div>
			<h2>
				<s:property value="ipSegment.ipSegmentDesc" />
			</h2>
			<img id="operation" class="operation" src="../img/ico-setting.png" />
			<ul id="ipsopt" class="opts">
				<s:if test='releaseMsg== null && releaseMsg == ""'>
					<li id="delButton"><a class="del"
						href="javascript:delIpSegment();"><img
							src="../img/ico-opt-del.png" alt="" /></a></li>
				</s:if>

			</ul>
		</div>

		<div class="detail-info">
			<table>
				<tbody>
					<tr>
						<th>名称：</th>
						<td style="word-break: break-all"><span id="customName"><s:property
									value="ipSegment.ipSegmentDesc" /></span> <a
							href="javascript:updateName();"><img width="25px"
								height="25px" src="../img/ico-opts-update.png" /></a></td>
					</tr>
					<tr>
						<th>所属业务：</th>
						<td id="appName"><s:property value="ipSegment.appName" /></td>
					</tr>
					<tr>
						<th>IP段：</th>
						<td><s:property value="ipSegment.startIp" /> - <s:property
								value="ipSegment.endIp" /></td>
					</tr>
					<s:if test="ipSegment.status==0&&ipSegment.vlanId!=null">
						<tr>
							<th>所属Vlan：</th>
							<td><s:property value="ipSegment.vlanName" /></td>
						</tr>
						<tr>
							<th>已用：</th>
							<td><s:property value="ipUsedCount" /></td>
						</tr>
					</s:if>
					<tr>
						<th>未用：</th>
						<td><s:property value="ipUnusedCount" /></td>
					</tr>

					<tr>
						<th>IP类型：</th>
						<td><s:if test="ipSegment.ipType == 0">IPV4</s:if><s:else>IPV6</s:else></td>
					</tr>
					
				</tbody>
			</table>
			<input type="hidden" id="ipSegmentId"
				value="<s:property value='ipSegment.ipSegmentId' />"> <input
				type="hidden" id="resourcePool"
				value="<s:property value='resPoolId'/>"> <input
				type="hidden" id="resourcePoolPart"
				value="<s:property value='resPoolPartId'/>">
		</div>
		<s:if test="usedIpList.length>0">
			<div class="detail-tab">
				<div class="tab">
					<a class="active" href="javascript:void(0);">IP信息</a>
				</div>
				<div class="detail-center">
					<!--列表开始-->
					<div id="net_tab_info">
						<table>
							<thead>
								<tr>
									<th>IP</th>
									<th>云主机ID</th>
									<th>云主机名称</th>
									<th>云主机网卡</th>
								</tr>
							</thead>
							<tbody>
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
						<!-- 翻页 -->
						<div class="pageBar" style="margin-top: 3px;">
							<s:property value="pageBar" escape="false" />
						</div>
					</div>
					<div></div>
				</div>
			</div>
		</s:if>
		<div class="time">
			<span class="time-begin">创建时间：<i><s:property
						value="ipSegment.createTime" /></i></span><span class="time-end">到期时间：<i><s:property
						value="ipSegment.expireTime" /></i></span>
		</div>
	</div>
</div>


<div id="updateName" class="float-div" style="display: none;">
	新名称：<input type="text" name="modify_custom_name"
		value="<s:property value='ipSegment.ipSegmentDesc' />" />
</div>
<script type="text/javascript"
	src="../scripts/pagesjs/console/business/ipSegment/ipSegment_detail.js"></script>
