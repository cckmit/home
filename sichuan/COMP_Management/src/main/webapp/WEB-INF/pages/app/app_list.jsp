<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<h1>业务管理</h1>
<span class="apply"><a id="add_app_button"
	href="javascript:void(0)">创建业务</a></span>
<div id="message" style="display: none;">
	<span class="error"><s:actionerror /></span> <span class="error"><s:fielderror /></span>
	<span class="success"><s:actionmessage /></span>
</div>
<div class="details-con">
	<div class="detail-title">
		<h3 class="apply-title">业务列表</h3>
	</div>
	<form name="appForm" id="appForm" action="appListQuery.action"
		method="post">
		<div class="table-seach">

			<p>
				业务组ID： <input id="app_id_tmp" name="app.app_id_tmp" class="text"
					type="text" size="23" value="<s:property value="app.app_id_tmp" />" />
			</p>

			<p style="margin-right: 25px; margin-left: 6px;">
				业务组名称： <input id="app_name_tmp" name="app.app_name_tmp" class="text"
					type="text" size="23"
					value="<s:property value="app.app_name_tmp" />" />
			</p>
			<p style="margin-right: 25px;">
				创建时间： <input id="startTime" name="app.startTime" readonly="true"
					class="Wdate wdatelong" type="text" size="23"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"
					value="<s:property value="app.startTime" />" /> 至 <input
					id="endTime" name="app.endTime" readonly="true"
					class="Wdate wdatelong" type="text" size="23"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"
					value="<s:property value="app.endTime" />" />
			</p>
			<ul class="opt-btn fr">
				<li><a id="search" class="search" href="javascript:void(0);">查询</a>
				</li>
			</ul>
		</div>
	</form>
	<!--list  -->
	<div id="approval-app" class="table-content table-block">
		<table id="appTable" width="100%" border="0" cellspacing="0"
			cellpadding="0">
			<col style="width: 10%;" />
			<col style="width: 10%;" />
			<col style="width: 10%;" />
			<col style="width: 12%;" />
			<col style="width: 11%;" />
			<col style="width: 13%;" />
			<col style="width: 15%;" />
			<col style="width: 15%;" />
			<tr>
				<th class="nl">业务组ID</th>
				<th>业务组名称</th>
				<th>资源池名称</th>
				<th>描述</th>
				<!--<th>创建标志</th> -->
				<th>联系人名称</th>
				<!--  <th>联系人电话</th> -->
				<th>创建人</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
			<s:iterator value="appList">
				<tr class="iterator">
					<td><s:property value="app_id" /></td>
					<td title="<s:property value='app_name'/>"><s:property
							value="app_name" /></td>
					<td><s:property value="respoolName" /></td>
					<td title="<s:property value='description'/>"><script>
						document.write(csubstr(
								'<s:property value="description"/>', 13));
					</script></td>
					<td><s:property value="app_contacts" /></td>
					<td><s:property value="create_user" /></td>
					<td id="create_time"><s:property value="create_time" /></td>
					<td class="table-opt-block"><a href="javascript:void(0);"
						onclick="appDetail('<s:property value="app_id" />');return false;">详情</a>
						<a href="javascript:void(0);"
						onclick="appUpdate('<s:property value="app_id" />');return false;">修改</a>
						<a href="javascript:appDelete('<s:property value="app_id" />')">删除</a></td>
				</tr>
			</s:iterator>
		</table>
	</div>
	<!-- 业务组详情-->
	<s:include value="app_detail.jsp" />
	<!-- 业务组修改 -->
	<s:include value="app_edit.jsp" />
	<!-- 业务组创建 -->
	<s:include value="app_add.jsp" />
	<%--  <s:include value="appCreate.jsp" />  --%>
	<!-- 业务组列表-->
	<s:include value="appBindAppList.jsp" />
	<div class="pageBar">
		<s:property value="pageBar" escape="false" />
	</div>
</div>

<script type="text/javascript" src="../scripts/pagesjs/app/app_list.js"></script>