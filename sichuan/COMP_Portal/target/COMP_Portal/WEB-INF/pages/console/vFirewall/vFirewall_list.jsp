<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="list">
	<div class="title">
		<h2>虚拟防火墙列表</h2>
		<div id="message" style="display: none;">
			<span id="error" class="error"><s:actionerror /></span> <span
				class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
		</div>
	</div>
	<div class="btns">
		<a class="apply" href="vFWApplyAction.action"><img
			src="../img/ico-apply.png" alt="" />申请虚拟防火墙</a>
	</div>
	<div class="list-items ">
		<s:if test="vfwList.size()>0">
			<ul id="resultList" class="items">
				<s:iterator value="vfwList">
					<li>
					<a href="vFWDetail.action?caseId=<s:property value='caseId' />">
						<div
							class="item-status 
		        			<s:if test="status==0">blue</s:if>
		        			<s:elseif test="status==1">green</s:elseif>
		        			<s:elseif test="status==2||status==3">red</s:elseif>
		        			<s:elseif test="status==4">orange</s:elseif>
		        			">
							<s:if test='status==0'>
									待创建
								</s:if>
							<s:elseif test='status==1'>
									已创建
								</s:elseif>
							<s:elseif test='status==2'>
									创建失败
								</s:elseif>
							<s:elseif test='status==3'>
									状态异常
								</s:elseif>
							<s:elseif test='status==4'>
									已删除
								</s:elseif>
						</div>
						<div class="item-content">
							<h3 >
								<s:property value="fwName" />
							</h3>
							<div class="detail">
								<div class="detail-col">
									<div class="detail-row">
										<span>所属资源池：</span> <span><s:property
												value="resPoolName" /></span>
									</div>
									<div class="detail-row">
										<span>所属企业客户：</span> <span><s:property
												value="appName" /></span>
									</div>
								</div>
								<div class="detail-col">
									<div class="detail-row">
										<span>所属分区：</span> <span>	<s:property value="resPoolPartName" /></span>
									</div>
									<div class="detail-row">
										<span>创建时间：</span>
										<s:if test="status==0"><span></span></s:if>
										<s:else>
										   <span><s:property value="createTime" /></span>
										</s:else>
									</div>
								</div>
							</div>
						</div>
						</a>
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
	src="../scripts/pagesjs/console/vFirewall/vFirewall_list.js"></script>