<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>

</head>
<body>

	<!--右侧-->
	<div id="right">
		<h1>业务管理</h1>
		<span class="apply"><a href="toBusinessCreate.action">创建业务</a></span>
		<div class="details-con">
			<form id="businessListForm" action="businessList.action"
				method="post">
				<div class="detail-title">
					<h3 class="apply-title">业务列表</h3>
				</div>
				<div class="table-seach">
					<p>
						业务名称： <input type="text" class="3h" name="queryBusiness.name"
							value="<s:property value="queryBusiness.name"/>" />
					</p>
					<p>
						创建时间：<input type="text" class="3h" id="searchAfterCreateTime"
							name="queryBusiness.afterCreateTime"
							value="<s:property value="queryBusiness.afterCreateTime"/>" />至<input
							type="text" class="3h" name="queryBusiness.beforeCreateTime"  id="searchBeforeCreateTime"
							value="<s:property value="queryBusiness.beforeCreateTime"/>" />
					</p>
					<ul class="opt-btn fr">
						<li><a class="search"
							href="javascript:document.getElementById('businessListForm').submit();">查询</a></li>
					</ul>
				</div>
			</form>
			<!-- 列表 -->
			<div id="approval-will" class="table-content table-block">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<col style="width:20%;" />
					<col style="width:37%;" />
					<col style="width:20%;" />
					<col style="width:23%;" />
					<tr class="cs-ak-tb-title">
						<th class="nl">业务名称</th>
						<th>业务描述</th>
						<th>创建时间</th>
						<th>操作</th>
					</tr>
					<s:iterator value="businessInfoList">
						<tr>
							<td><s:property value="name" /></td>
							<td><s:property value="description" /></td>
							<td><s:property value="createTimeStr" /></td>
							<td class="table-opt-block"><a href="businessDetail.action?resultType=edit&businessId=<s:property value="businessId" />">修改</a>
								<a href="businessDetail.action?businessId=<s:property value="businessId" />">详情</a> <a
								href="javascript:del('<s:property value="businessId" />')">删除</a></td>
						</tr>
					</s:iterator>
				</table>
			</div>
			<!-- 翻页 -->
			<div class="pageBar">
				<s:property value="pageBar" escape="false" />
			</div>
		</div>
	</div>

	<script type="text/javascript"
		src="../scripts/pagesjs/business/business_list.js"></script>
</body>