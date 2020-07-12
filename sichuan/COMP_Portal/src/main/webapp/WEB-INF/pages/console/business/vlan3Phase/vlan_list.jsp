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
			<input type="hidden" value="" id="caseId" name="caseId">
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
	<div class="list-items ebs-list">
		<s:if test="vlanList.size()>0">
			<ul id="resultList" class="items">
				<s:iterator value="vlanList">
					<li>										
						<div class="item-status 
<%-- 		        			<s:if test="status==2">blue</s:if>
		        			<s:else>green</s:else>
		        			">
							<s:if test='status.equals("2")'>
									待创建
								</s:if>
							<s:else>
									已创建
								</s:else> --%>
							<s:if test="orderStatus==0">blue</s:if>
		        			<s:elseif test="orderStatus==1">green</s:elseif>
		        			<s:elseif test="orderStatus==3">green</s:elseif>
		        			<s:elseif test="orderStatus==7">orange</s:elseif>
		        			<s:elseif test="orderStatus==8">orange</s:elseif>
		        			<s:else>green</s:else> ">	
						    <s:if test="orderStatus==0">待创建</s:if>
							<s:elseif test="orderStatus==1">已创建</s:elseif>
							<s:elseif test="orderStatus==3">已创建</s:elseif>
							<s:elseif test="orderStatus==7">待修改</s:elseif>
							<s:elseif test="orderStatus==8">待删除</s:elseif>								
							<s:else>已创建</s:else>							
						</div>					
						<div class="item-content">
							<h3 style="width: 300px;">
<%-- 								<s:property value="vlanName" /> --%>
							      <s:if test="orderStatus==7"><s:property value="newName" /></s:if>
							      <s:else><s:property value="vlanName" /></s:else>
							</h3>
							<div class="detail">
								<div class="detail-col">
									<div class="detail-row">
										<span>vlan范围：</span><span>
<%-- 										<s:if test="startId!=null ">
												<s:property value="startId" /> - <s:property value="endId" />
											</s:if> --%>
										    <s:if test="orderStatus==7">
												<s:property value="newStartId" /> - <s:property value="newEndId" />
										    </s:if><s:else>
										        <s:property value="startId" /> - <s:property value="endId" />
										    </s:else>											
										</span>
									</div>
									<div class="detail-row">
										<span>所属企业客户：</span> <span
											id="createTime<s:property value='diskId'/>"><s:property
												value="appName" /></span>
									</div>
									<input type="hidden" size="28" maxlength="25" id="caseId" name="caseId"/>
								</div>
								<div class="detail-col" style="width:40%;">
									<div class="detail-row">
										<span>创建时间：</span> <span><s:property value="createTime" /></span>
									</div>
									<div class="detail-row">
										<span>操作：</span> <span>
										<a class="opt-btn" href='javascript:EditVlan("<s:property value="vlanName" />","<s:property value="caseId" />",
											"<s:property value="orderId" />","<s:property value="startId" />","<s:property value="endId" />");'>修改</a>	
										<s:if test="cancelable != null">
												<a style="background-color: #ACB0B4;" class="opt-btn"
													title='<s:property value="cancelable.msg" />'> 删除 </a>
										</s:if> <s:else>
											<a class="opt-btn"
													href='javascript:deleteVlan("<s:property value="vlanName" />","<s:property value="caseId" />",
									"<s:property value="resPoolId" />","<s:property value="resPoolPartId" />");'>删除</a>
											</s:else> 
<%-- 											<a class="opt-btn" href="vlanDetail.action?caseId=<s:property value='caseId' />">详情</a> --%>
										</span>
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
	src="../scripts/pagesjs/console/business/vlan3Phase/vlan_list.js"></script>





<div id="vlanEditDialog" style="display: none;">
	<table>
		<tr>
			<td>Vlan名称：</td>
			<td><input type="hidden" size="28" maxlength="25" id="caseId" />
				<input type="hidden" size="28" maxlength="25" id="orderId" />
<!-- 				<input type="hidden" size="28" maxlength="25" id="vlanId" /> -->
				<input type="text" size="28" maxlength="25" id="edit_vlanName" name="edit_vlanName" />
			</td>
		</tr>
		<tr>
		    <td>Vlan起始ID：</td>
		    <td> <input type="text" size="28" maxlength="4" id="edit_startId" name="edit_startId" /> </td>
		</tr>
		<tr>
		    <td>Vlan终止ID：</td>
		    <td> <input type="text" size="28" maxlength="4" id="edit_endId" name="edit_endId" /> </td>
		</tr>
	</table>
</div>