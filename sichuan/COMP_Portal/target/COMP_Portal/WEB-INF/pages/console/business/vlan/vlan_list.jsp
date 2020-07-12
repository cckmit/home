<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="list">
	<div class="title">
		<h2>Vlan列表</h2>
		<div id="message" style="display: none;">
			<span id="error" class="error"><s:actionerror /></span> <span
				class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
		</div>
		<s:form id="gotoForm">
			<input type="hidden" value="" id="diskId" name="diskId">
			<s:hidden id="nodeType" name="nodeType"></s:hidden>
			<s:hidden id="nodeId" name="nodeId"></s:hidden>
			<s:hidden id="treeNodeName" name="treeNodeName"></s:hidden>
			<s:hidden id="pnodeId" name="pnodeId"></s:hidden>
			<s:hidden id="pnodeName" name="pnodeName"></s:hidden>
			<s:hidden id="appId" name="appId"></s:hidden>
			<s:hidden id="curFun" name="curFun"></s:hidden>
		</s:form>
	</div>
	<div class="btns">
		<s:url id="addUrl" action="createVlanAction">
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
	<div class="list-items ebs-list">
		<s:if test="vlanList.size()>0">
			<ul id="resultList" class="items">
				<s:iterator value="vlanList">
					<li>
						<div
							class="item-status 
		        			<s:if test="status==2">blue</s:if>
		        			<s:else>green</s:else>
		        			">
							<s:if test='status.equals("2")'>
									待创建
								</s:if>
							<s:else>
									已创建
								</s:else>
						</div>
						<div class="item-content">
							<h3 style="width: 300px;">
								<s:property value="vlanName" />
							</h3>
							<div class="detail">
								<div class="detail-col">
									<div class="detail-row">
										<span>IP段：</span> <span><s:if test="startIp!=null ">
												<s:property value="startIp" /> - <s:property value="endIp" />
											</s:if></span>
									</div>
									<div class="detail-row">
										<span>所属业务：</span> <span
											id="createTime<s:property value='diskId'/>"><s:property
												value="appName" /></span>
									</div>
								</div>
								<div class="detail-col">
									<div class="detail-row">
										<span>创建时间：</span> <span><s:property value="createTime" /></span>
									</div>
									<div class="detail-row">
										<span>操作：</span> <span><a class="opt-btn"
											href='javascript:EditVlan("<s:property value="vlanName" />","<s:property value="caseId" />");'>修改</a>
											<s:if test="cancelable != null">
												<a style="background-color: #ACB0B4;" class="opt-btn"
													title='<s:property value="cancelable.msg" />'> 释放 </a>
											</s:if> <s:else>
												<a class="opt-btn"
													href='javascript:deleteVlan("<s:property value="vlanName" />","<s:property value="vlanId" />",
									"<s:property value="resPoolId" />","<s:property value="resPoolPartId" />");'>释放</a>
											</s:else> </span>
									</div>
								</div>

							</div>
						</div>
					</li>
				</s:iterator>
			</ul>
			<!-- 翻页 -->
			<div class="pageBar">
				<s:property value="pageBar" escape="false" />
			</div>
		</s:if>
	</div>
</div>

<script type="text/javascript"
	src="../scripts/pagesjs/console/business/vlan/vlan_list.js"></script>





<div id="vlanEditDialog" style="display: none;">
	<table>
		<tr>
			<td>Vlan名称：</td>
			<td><input type="hidden" size="28" maxlength="25" id="caseId" />
				<input type="hidden" size="28" maxlength="25" id="vlanId" /> <input
				type="text" size="28" maxlength="25" id="edit_vlanName"
				name="edit_vlanName" /></td>
		</tr>
	</table>
</div>