<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 角色列表 -->
<div id="role_list" style="display:none;width:800px;">
	<div class="table-seach">
		<p>
			<label class="fl">角色名称：</label> <input name="roleName"
				type="text" size="30" id="searchRoleName" maxlength="30"
				value=""  />
		</p>
		<ul class="opt-btn fr">
			<li><a class="search" href="javascript:void(0);" id="searchRole">查询</a>
			</li>
		</ul>
	</div>
	<div class="table-content table-block">
		<table id="roleListTable" width="100%" border="0" cellspacing="0"
			cellpadding="0" style="width:100%">
			<col style="width:5%;" />
			<col style="width:17%;" />
			<%--<col style="width:33%;" />--%>
			<col style="width:45%;" />

			<tr>
				<th class="nl"><input type="checkbox" name="selectAllRole"
					id="selectAllRole" /></th>
				<th>名称</th>
				<%--<th>ID</th>--%>
				<th>描述</th>
			</tr>
			<tbody id="roleListTbody">
				<%--<tr class="iterator" data-roleid="2" data-rolename="订单发布者">
					<td align="center"><input type="checkbox" name="selectRole"
						value="<s:property value='userId'/>" /></td>
					<td>orderPulisher</td>
					<td>订单发布者</td>
					<td>发布</td>
				</tr> --%>
			</tbody>
		</table>
	</div>
	<div class="pageBar" id="roleListPageBarDiv">
		
	</div>

</div>
