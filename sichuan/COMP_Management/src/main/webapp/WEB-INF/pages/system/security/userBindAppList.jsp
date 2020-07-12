<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 业务组列表 -->
<div id="app_list" style="display:none;width:800px;">
	<div class="table-seach">
		<p>
			<label class="fl">业务组名称：</label> <input name="appName"
				type="text" size="30" id="searchAppName" maxlength="30"
				value=""  />
		</p>
		<ul class="opt-btn fr">
			<li><a class="search" href="javascript:void(0);" id="searchApp">查询</a>
			</li>
		</ul>
	</div>
	<div class="table-content table-block">
		<table id="appListTable" width="100%" border="0" cellspacing="0"
			cellpadding="0" style="width:100%">
			<col style="width:5%;" />
			<col style="width:40%;" />
			<col style="width:55%;" />

			<tr>
				<th class="nl"><input type="checkbox" name="selectAllApp"
					id="selectAllApp" /></th>
				<th>业务组名称</th>
				<th>描述</th>
			</tr>
			<tbody id="appListTbody">
				<tr class="iterator" data-appId="2" data-appName="测试">
					<td align="center"><input type="checkbox" name="selectApp"
						value="<s:property value='userId'/>" /></td>
					<td></td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="pageBar" id="appListPageBarDiv">
		
	</div>

</div>
