<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 业务列表 -->
<div id="app_list" style="display: none;">
	<div class="toolbar table-seach">
		<span class="keyName">企业客户名称：</span><input type="text" size="30"
			name="appName"  id="searchAppName"/> <a id="searchApp" class="search btn fr"
			href="javascript:;">查询</a>
	</div>


	<table class="table" id="businessListTab">
		<thead>
			<tr>
				<th><input type="checkbox" name="selectAllApp"
					id="selectAllApp" /></th>
				<th>企业客户名称</th>
				<th>企业客户描述</th>
			</tr>
		</thead>
		<tbody id="appListTbody">
		<tr class="iterator" data-appId="2" data-appName="测试">
					<td align="center"><input type="checkbox" name="selectApp"
						value="<s:property value='userId'/>" /></td>
					<td></td>
					<td></td>
				</tr>
		</tbody>
	</table>


	<div class="pageBar" id="appListPageBarDiv"
		style="text-align: center"></div>
</div>
