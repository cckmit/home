<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="message" style="display: none;">
	<span class="error"><s:actionerror /></span> <span class="error"><s:fielderror /></span>
	<span class="success"><s:actionmessage /></span>
</div>
<h1>条目审批</h1>
<div class="details-con">
	<div class="detail-title">
		<h3 class="apply-title">条目审批</h3>
	</div>
	<div class="table-seach">
		<p>
			名称：<input class="text" type="text" size="28" id="itemName"
				maxlength="30" />
		</p>
		<p>
			申请时间：<input onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				readonly="true" class="Wdate wdatelong" type="text" size="23"
				id="beginCreateTime" maxlength="14" /> 至<input type="text"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				readonly="true" class="Wdate wdatelong" size="23" id="endCreateTime"
				maxlength="14" />
		</p>
		<ul class="opt-btn fr">
			<li><a class="search" href="javascript:void(0);" id="search">查询</a>
			</li>
			<li><a class="batch" href="javascript:void(0);"
				onclick="approvalMore(this);return false;">批量审批</a></li>
		</ul>
	</div>
	<!-- 列表 -->
	<div id="approval-will" class="table-content table-block">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<col style="width: 5%;" />
			<col style="width: 20%;" />
			<col style="width: 20%;" />
			<col style="width: 20%;" />
			<col style="width: 20%;" />
			<col style="width: 15%;" />
			<tr>
				<th class="nl"><input type="checkbox" name="selectAll"
					id="selectAll" /></th>
				<th>名称</th>
				<th>条目分类</th>
				<th>条目描述</th>
				<th>申请时间</th>
				<th>操作</th>
			</tr>
			<s:if test="itemInfoList.size()>0">
				<s:iterator value="#request.itemInfoList">
					<tr>
						<td><input type="checkbox" name="select"
							value="<s:property value='itemId'/>" /></td>
						<td><s:property value='itemName' /></td>
						<td><s:property value='itemType.desc' /></td>
						<td><s:property value='description' /></td>
						<td><script>document.write(_fomart14("<s:property value='updateTime'/>"));</script></td>
						<td class="table-opt-block"><a href="javascript:void(0);"
							onclick="onAuditDetail('<s:property value='itemId'/>','<s:property value='itemType.code'/>');return false;">审批</a>
						</td>
					</tr>
				</s:iterator>
			</s:if>
			<s:if test="itemInfoList.size()==0">
				<tr>
					<td colspan="6" align="center">无符合数据</td>
				</tr>
			</s:if>
		</table>
	</div>
	<!-- 翻页 -->
	<div class="pageBar">
		<s:property value="pageBar" escape="false" />
	</div>
	<!-- 批量审批弹出层 -->
	<div class="page-form" id="approvalMore" style="display: none;">
		<div class="field">
			<div class="caption">审批意见：</div>
			<div class="content">
				<textarea cols="40" rows="5" id="auditInfo"></textarea>
			</div>
			<div class="point"></div>
		</div>
		<div class="point"></div>
	</div>
</div>
<s:form method="post" id="itemDetail">
	<input type="hidden" id="itemId" value="" name="itemId" />
	<input type="hidden" id="itemType" value="" name="itemType" />
</s:form>
<script type="text/javascript"
	src="../scripts/pagesjs/task/entity_list.js"></script>
