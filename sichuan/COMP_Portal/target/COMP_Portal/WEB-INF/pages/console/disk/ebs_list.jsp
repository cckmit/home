<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="list">
	<div class="title">
		<h2>云硬盘列表</h2>
		<div id="message" style="display: none;">
			<span id="error" class="error"><s:actionerror /></span> <span
				class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
		</div>
		<s:form id="gotoForm">
			<input type="hidden" value="" id="diskId" name="diskId">
			<input type="hidden" value="" id="caseId" name="caseId">
		</s:form>
	</div>
	<s:if test="#session.userInfo.userId=='admin'">
		<div class="btns">
			<a class="apply" href="ebsApplyAction.action"><img
				src="../img/ico-apply.png" alt="" />申请云硬盘</a>
			<!-- 
			<a class="apply" href="ebsPreApplyQueryListAction.action"><img
				src="../img/ico-apply.png" alt="" />预订购列表</a>
			 -->
		</div>
	</s:if>
	<div class="tool-bar">
		<a href="javascript:void(0)" onclick="refreshPageByState(this)"
			value="" class="btn active">全部</a> <a href="javascript:void(0)"
			onclick="refreshPageByState(this)" value="0,1,2,3,5,6,7,8,9"
			class="btn">未挂载</a> <a href="javascript:void(0)"
			onclick="refreshPageByState(this)" value="4" class="btn">已挂载</a>
	</div>
	<div class="list-items ebs-list">
		<s:if test="diskInfos.size()>0">
			<ul id="resultList" class="items">

				<s:iterator value="#request.diskInfos">

					<li status="<s:property value='diskStatus'/>"
						id="s<s:property value='diskId'/>"
						name="<s:property value='diskId'/>"><a
						href="javascript:void(0);"
						onclick="goToPage('<s:property value='diskId'/>','<s:property value='caseId' />');return false;">
							<div
								class="item-status <s:if test="diskStatus==0">blue</s:if>
		        			<s:if test="diskStatus==1">blue</s:if>
		        			<s:if test="diskStatus==2">green</s:if>
		        			<s:if test="diskStatus==3">blue</s:if>
		        			<s:if test="diskStatus==4">green</s:if>
		        			<s:if test="diskStatus==5">orange</s:if>
		        			<s:if test="diskStatus==6">red</s:if>
		        			<s:if test="diskStatus==7">red</s:if>
		        			<s:if test="diskStatus==8">red</s:if>
		        			<s:if test="diskStatus==9">red</s:if>">
								<s:if test="diskStatus==0">待创建</s:if>
								<s:if test="diskStatus==1">创建中</s:if>
								<s:if test="diskStatus==2">已创建</s:if>
								<s:if test="diskStatus==3">挂载中</s:if>
								<s:if test="diskStatus==4">已挂载</s:if>
								<s:if test="diskStatus==5">未挂载</s:if>
								<s:if test="diskStatus==6">创建失败</s:if>
								<s:if test="diskStatus==7">挂载失败</s:if>
								<s:if test="diskStatus==8">发送失败</s:if>
								<s:if test="diskStatus==9">状态异常</s:if>
							</div>
							<div class="item-content">
								<h3>
									<s:if test="diskName.trim()!=''">
										<s:property value="diskName" />
									</s:if>
									<s:else>&nbsp;</s:else>
								</h3>
								<div class="detail">
									<div class="detail-col">
										<div class="detail-row">
											<span>云硬盘容量：</span> <span><s:property value="diskSize" />
												TB</span>
										</div>
										<div class="detail-row">
											<span>所属企业客户：</span> <span
												id="createTime<s:property value='diskId'/>"><s:property
													value="appName" /></span>
										</div>
									</div>
									<div class="detail-col">
										<div class="detail-row">
											<span>创建时间：</span> <span
												id="createTime<s:property value='diskId'/>"><s:property
													value="createTime" /></span>
										</div>
										<div class="detail-row">
											<span>到期时间：</span> <span
												id="expireTime<s:property value='diskId'/>"><s:property
													value="expireTime" /></span>
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



<%-- 

<ul id="listNull" class="resource-list">
	<li><a href="javascript:void(0);"> 暂无符合条件数据 </a></li>
</ul>

 --%>
<script type="text/javascript"
	src="../scripts/pagesjs/console/disk/ebs_list.js"></script>