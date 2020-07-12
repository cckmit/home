<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 业务组列表 -->
<div id="app_Userlist" style="display:none;width:800px;">
	<div class="table-seach">
		<p>
			<label class="fl">用户名称：</label> <input name="userName"
				type="text" size="30" id="searchUserName" maxlength="30"
				value=""  />
		</p>
		<ul class="opt-btn fr">
			<li><a class="search" href="javascript:void(0);" id="searchUser">查询</a>
			</li>
		</ul>
	</div>
	<div class="table-content table-block">
		<table id="userListTable" width="100%" border="0" cellspacing="0"
			cellpadding="0" style="width:100%">
			<col style="width:5%;" />
			<col style="width:40%;" />
			<col style="width:55%;" />

			<tr>
				 <th class="nl"></th> 
				<th>用户账号</th>
				<th>用户名称</th>
			</tr>
			<tbody id="userListTbody">
				<tr class="iterator" data-appId="2" data-appName="测试">
					 <td align="center"><input type="radio" name="selectUser"
						value="<s:property value='appId'/>" /></td> 
					<td></td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="pageBar" id="userListPageBarDiv">		
	</div>

</div>
