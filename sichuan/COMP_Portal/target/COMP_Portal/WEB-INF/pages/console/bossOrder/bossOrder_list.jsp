<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="list">
	<div class="title">
		<h2>BOSS订单列表</h2>
		<div id="message" style="display: none;">
			<span id="error" class="error"><s:actionerror /></span> <span
				class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
		</div>
		<s:form id="gotoForm">
			<input type="hidden" value="" id="bossOrderId" name="bossOrderId">
			<input type="hidden" value="" id="id" name="id">
		</s:form>
	</div>
	<div class="btns">
	</div>
	<div class="tool-bar">
		<div class="search-text">
			<img class="search-ico" src="../img/ico-search.png" /> <input
				class="search-text" type="text" size="25" id="bossOrderId1" name="bossOrderId"
				maxlength="25" value="<s:property value="bossOrderId" />"
				style="width: auto;" placeholder="输入ID" />
		</div>
		<a class="search-btn" href="javascript:;" onclick="queryBossOrder()">查询</a>
	</div>
	<div class="list-items">
		<s:if test="bossOrderResultInfos.size()>0">
			<ul id="resultList" class="items">
				<s:iterator value="#request.bossOrderResultInfos">
					<li status="<s:property value='status'/>" id="s<s:property value='bossOrderId'/>" name="<s:property value='bossOrderId'/>">
						<a href="javascript:void(0);" onclick="goToPage('<s:property value='id'/>');return false;">
							<div id="status<s:property value="bossOrderId"/>" class="item-status blue">
								<s:if test="status == 0">未操作</s:if>
								<s:else>已操作</s:else>
							</div>
							<div class="item-content">
								<h3>
									<s:property value="bossOrderId" />
								</h3>
								<div class="detail">
									<div class="detail-col">
										<span>单价：</span> 
										<s:property value="price" />元
									</div>
									<div class="detail-col">
										<div class="detail-row">
											<span>所属企业客户：</span> 
											<span><s:property value="appName" /></span>
										</div>
										<div class="detail-row">
											<span>协议签订时间：</span> 
											<span><s:property value="agreementBeginTime" /></span>
										</div>
									</div>
									<div class="detail-col">
										<div class="detail-row">
											<span>协议结束时间：</span> 
											<span><s:property value="agreementEndTime" /></span>
										</div>
										<div class="detail-row">
											<span>资源类型：</span>
											<span>
											<s:if test="operateType == 0">订购</s:if><s:elseif test="operateType == 1">修改</s:elseif><s:elseif test="operateType == 2">删除</s:elseif><s:elseif test="operateType == 5">复机</s:elseif><s:elseif test="operateType == 4">停机</s:elseif>
											</span>
										</div>
									</div>
								</div>
							</div>
						</a>
					</li>
				</s:iterator>
			</ul>
		</s:if>
		<!-- 翻页 -->
		<div class="pageBar">
			<s:property value="pageBar" escape="false" />
		</div>


	</div>
</div>

<s:if test="bossOrderResultInfos.size()<=0">
	<script type="text/javascript">
		$("#listNull li").show();
	</script>
</s:if>
<script type="text/javascript"
	src="../scripts/pagesjs/console/bossOrder/bossOrder_list.js"></script>
<link href="../styles/bindApp.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" language="JavaScript" charset="UTF-8">
