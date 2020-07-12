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
			<input type="hidden" id="caseId" name="caseId">
		</s:form>
	</div>
	<s:if test="#session.userInfo.userId=='admin'">
		<div class="btns">
			<a class="apply" href="ebsPreApplyAction.action"><img src="../img/ico-apply.png" alt="" />预订购</a>
		</div>
	</s:if>
	<div class="tool-bar">
		<div class="search-text">
			<img class="search-ico" src="../img/ico-search.png" /> <input
				class="search-text" type="text" size="25" id="ebsName" name="ebsName"
				maxlength="25" value="<s:property value="ebsName" />"
				style="width: auto;" placeholder="输入名称" />
		</div>
		<a class="search-btn" href="javascript:;" onclick="queryEbs()">查询</a>
	</div>
	<div class="list-items ebs-list">
		<s:if test="diskInfos.size()>0">
			<ul id="resultList" class="items">

				<s:iterator value="#request.diskInfos">

					<li status="<s:property value='status'/>"
						id="s<s:property value='diskName'/>"
						name="<s:property value='diskName'/>"><a
						href="javascript:void(0);"
						onclick="goToPage('<s:property value='caseId'/>');return false;">
							<div class="item-status blue">
			        			<s:if test="status == 0">待订购</s:if>
								<s:else>已订购</s:else>
							</div>
							<div class="item-content">
								<h3>
									<s:property value="ebsName" />
								</h3>
								<div class="detail">
									<div class="detail-col">
										<div class="detail-row">
											<span>云硬盘容量：</span> <span><s:property value="diskSize" />
												GB</span>
										</div>
										<div class="detail-row">
											<span>所属企业客户：</span> <span
												id="appId<s:property value='appId'/>"><s:property
													value="appName" /></span>
										</div>
									</div>
									<div class="detail-col">
										<div class="detail-row">
											<span>计费方式：</span> <span id="chargeType<s:property value='chargeType'/>">
											<s:if test='chargeType == "h"'>按时计费</s:if>
											<s:else>包月计费</s:else>
										</div>
										<div class="detail-row">
											<span>创建时间：</span> <span
												id="createTime<s:property value='createTime'/>"><s:property
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



<%-- 

<ul id="listNull" class="resource-list">
	<li><a href="javascript:void(0);"> 暂无符合条件数据 </a></li>
</ul>

 --%>
<script type="text/javascript"
	src="../scripts/pagesjs/console/disk/ebs_pre_apply_list.js"></script>