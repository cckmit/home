<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="list">
	<div class="title">
		<h2>IP段列表</h2>
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
	</div>
	<div class="btns">
		<a class="apply" href="${addUrl}"><img
			src="../img/ico-apply.png" alt="" />申请IP段</a>
	</div>
	<div class="list-items ">
		<s:if test="ipSegmentList.size()>0">
			<ul id="resultList" class="items">
				<s:iterator value="ipSegmentList">
	<%-- <s:url id="detailUrl" action="ipSegmentDetailAction">
			<s:param name="nodeType" value="nodeType" />
			<s:param name="nodeId" value="nodeId" />
			<s:param name="treeNodeName" value="treeNodeName" />
			<s:param name="pnodeId" value="pnodeId" />
			<s:param name="pnodeName" value="pnodeName" />
			<s:param name="appId" value="appId" />
			<s:param name="curFun" value="curFun" />
			<s:param name="businessName" value="businessName" />
			<s:param name="queryBusinessId" value="queryBusinessId" />
		</s:url> --%>				
				
					<li><a
						href="ipSegmentDetailAction.action?ipSegmentId=<s:property value='ipSegmentId' />&resPoolId=<s:property value='resPoolId' />
								&resPoolPartId=<s:property value='resPoolPartId' />&queryBusinessId=<s:property value='queryBusinessId' />
								&nodeType=<s:property value="nodeType" />&nodeId=<s:property value="nodeId" />&treeNodeName=<s:property value="treeNodeName" />
								&pnodeId=<s:property value="pnodeId" />&pnodeName=<s:property value="pnodeName" />&appId=<s:property value="appId" />
								&curFun=<s:property value="curFun" />&businessName=<s:property value="businessName" />">
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
								<h3>
									<s:property value="ipSegmentDesc" />
								</h3>
								<div class="detail">
									<div class="detail-col">
										<div class="detail-row">
											<span>IP段：</span> <span><s:if test="startIp!=null ">
													<s:property value="startIp" /> - <s:property value="endIp" />
												</s:if></span>
										</div>
										<div class="detail-row">
											<span>所属业务：</span> <span><s:property value="appName" /></span>
										</div>
									</div>
									<div class="detail-col">
										<div class="detail-row">
											<span>绑定Vlan：</span> <span> <s:property
													value="vlanName" /></span>
										</div>
										<div class="detail-row">
											<span>创建时间：</span> <span><s:property
													value="createTime" /></span>
										</div>
									</div>
								</div>
							</div>
					</a></li>
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
	src="../scripts/pagesjs/console/business/ipSegment/ipSegment_list.js"></script>
