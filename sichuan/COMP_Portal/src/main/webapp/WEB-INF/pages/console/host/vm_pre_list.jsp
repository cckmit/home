<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="list">
	<div class="title">
		<h2>预订购云主机列表</h2>
		<div id="message" style="display: none;">
			<span id="error" class="error"><s:actionerror /></span> <span
				class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
		</div>
		<s:form id="gotoForm">
			<input type="hidden" value="" id="caseId" name="caseId">
		</s:form>
	</div>
	<s:if test="#session.userInfo.userId=='admin'">
		<div class="btns">
			<a id="bind_vm" class="apply" href="vmPreApplyAction.action"><img src="../img/ico-apply.png" alt="" />预订购</a>
		</div>
	</s:if>
	<div class="tool-bar">
		<div class="search-text">
			<img class="search-ico" src="../img/ico-search.png" /> <input
				class="search-text" type="text" size="25" id="vmName" name="vmName"
				maxlength="25" value="<s:property value="vmName" />"
				style="width: auto;" placeholder="输入名称" />
		</div>
		<a class="search-btn" href="javascript:;" onclick="queryVm()">查询</a>
	</div>
	<div class="list-items">
		<s:if test="vmResultInfos.size()>0">
			<ul id="resultList" class="items">
				<s:iterator value="#request.vmResultInfos">
					<li status="<s:property value='status'/>" id="s<s:property value='vmName'/>" name="<s:property value='vmName'/>">
						<a href="javascript:void(0);" onclick="goToPage('<s:property value='caseId'/>');return false;">
							<div id="status<s:property value="vmId"/>" class="item-status blue">
								<s:if test="status == 0">待订购</s:if>
								<s:else>已订购</s:else>
							</div>
							<div class="item-content">
								<h3>
									<s:property value="vmName" />
								</h3>
								<div class="detail">
									<div class="detail-col">
										<s:property value="isoName" />
									</div>
									<div class="detail-col">
										<div class="detail-row">
											<span>CPU：</span> 
											<span><s:property value="cpuNum" />核</span>
										</div>
										<div class="detail-row">
											<span>内存：</span> 
											<span><s:property value="ramSize" />GB</span>
										</div>
									</div>
									<div class="detail-col">
										<div class="detail-row">
											<span>存储空间：</span> 
											<s:property value="discSize" />GB</span>
										</div>
										<div class="detail-row">
											<span>订购数量：</span>
											<s:property value="num" /></span>
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

<s:if test="vmResultInfos.size()<=0">
	<script type="text/javascript">
		$("#listNull li").show();
	</script>
</s:if>
<script type="text/javascript"
	src="../scripts/pagesjs/console/host/vm_pre_list.js"></script>
<link href="../styles/bindApp.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" language="JavaScript" charset="UTF-8">
