<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style>
.table-seach p{
	margin-right: -1px;
}
</style>
<h1>安全管理</h1>
<span class="apply"><a id="add_user_button"
	href="javascript:void(0)">创建用户</a></span>
<div id="message" style="display: none;">
	<span id="error" class="error"><s:actionerror /></span> <span
		class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
</div>
<div class="details-con">
	<div class="detail-title">
		<h3 class="apply-title">用户管理</h3>
		<!-- <ul class="title-btn">
			<li class="select"><a href="javascript:void(0);"
				onclick="will(this);return false;" value='0'>待审核</a></li>
			<li><a href="javascript:void(0);"
				onclick="will(this);return false;" value='1'>已通过</a></li>
		</ul> -->
	</div>

	<form action="user.action" id="search_form" name="search_form"
		method="post">
		<div class="table-seach" style="margin:0 8px;">
			<p>用户账号：<input class="text" type="text" size="28" id="queryUserId" style="width:110px;"
					name="queryUser.userId" maxlength="20"
					value="<s:property value="queryUser.userId" />" /></p>
			<p>用户姓名：<input class="text" type="text" size="28" id="queryUserName"
					name="queryUser.userName" maxlength="10" style="width:110px;"
					value="<s:property value="queryUser.userName" />" /></p>
			<p>绑定的业务组：<input class="text" type="text" size="28" id="queryUserApp"
					name="queryUser.appName" maxlength="64" style="width:110px;"
					value="<s:property value="queryUser.appName" />" /></p>
			<p>角色：</p>
			<p><select class="select-min" id="queryRoleId" name="queryUser.roleId"></select>
			   <span id="roleIdSpan" hidden="hidden"><s:property value="queryUser.roleId" /></span>
			</p>		
			<p>状态：</p>
			<p><select class="select-min" id="queryStatus"
					name="queryUser.queryStatus">
					<option value=""
						<s:if test="queryUser.queryStatus == \"\"">selected="selected"</s:if>>-请选择-</option>
					<option value="0"
						<s:if test="queryUser.queryStatus == \"0\"">selected="selected"</s:if>>启用</option>
					<option value="1"
						<s:if test="queryUser.queryStatus == \"1\"">selected="selected"</s:if>>停用</option>
					<option value="2"
						<s:if test="queryUser.queryStatus == \"2\"">selected="selected"</s:if>>锁定</option>
					<%--<option value="3"
						<s:if test="queryUser.queryStatus == \"3\"">selected="selected"</s:if>>销户</option> --%>
					<option value="4"
						<s:if test="queryUser.queryStatus == \"4\"">selected="selected"</s:if>>待审批</option>
					<option value="5"
						<s:if test="queryUser.queryStatus == \"5\"">selected="selected"</s:if>>审批不通过</option>
				</select>
			</p>
			<ul class="opt-btn fr">
				<li><a class="search" href="javascript:void(0);" id="search">查询</a></li>
				<%--
				<li><a class="search" href="javascript:void(0);" id="advSearch">高级查询</a></li>
				<li><a class="resetPW" href="javascript:void(0);" id="resetpass">密码重置</a></li>
				<li><a class="run statusChange" href="javascript:void(0);"
					id="enable_user">启用/解锁</a></li>
				<li><a class="stop statusChange" href="javascript:void(0);"
					id="disable_user">禁用</a></li> --%>
			</ul>

		</div>
	</form>
	<!-- 列表 -->
	<div id="approval-will" class="table-content table-block">
		<table id="ordertable" width="100%" border="0" cellspacing="0"
			cellpadding="0">
			<%--<col style="width:5%;" />--%>
			<col style="width: 13%;" />
			<col style="width: 11%;" />
			<%--<col style="width: 10%;" />
			<col style="width: 10%;" />
			<col style="width: 12%;" /> --%>
			<col style="width:17%"/>
			<col style="width:22%"/>
			<col style="width: 7%;" />
			<col style="width: 21%;" />

			<tr>
				<%--<th class="nl"><input type="checkbox" name="selectAll"
					id="selectAll" /></th> --%>
				<th>用户账号</th>
				<th>用户姓名</th>
				<%--<th>手机</th>
				<th>邮箱</th>
				<th>部门</th>--%>
				<th>角色</th>
				<th>绑定的业务组</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
			<s:iterator value="userList" var="user">
				<tr class="iterator">
					<%--<td align="center"><input type="checkbox" name="select"
						value="<s:property value='userId'/>" onclick="ischeckCurrentAll()"/></td> --%>
					<td><s:property value="userId" /></td>
					<td><s:property value="userName" /></td>
					<%--<td><s:property value="mobile" /></td>
					<td><s:property value="email" /></td>
					<td><s:property value="departmentName" /></td> --%>
					<td>
						<div class="cut role" ><s:iterator value="roles" var="role" status="st"><s:if test="!#st.isFirst()">,&nbsp;</s:if><s:property value="#role.roleName"/></s:iterator></div>
					</td>
					<td>
						<div class="cut app" ><s:iterator value="apps" var="app" status="st"><s:if test="!#st.isFirst()">,&nbsp;</s:if><s:property value="#app.appName"/></s:iterator></div>
					<%--
					 <div  title="<s:iterator value="apps" var="app" status="st"><s:property value="#app.appName"/>&nbsp;&nbsp;&nbsp;&nbsp;</s:iterator>">
					 	<s:iterator value="apps" var="app" status="a">
					 		<s:if test="#a.isFirst()">
					 			<s:property value="#app.appName"/>
					 		</s:if>
					 	</s:iterator>
					</div>
					 --%>
					</td>
					<td align="center"><s:property value="status.desc" /></td>
					<td class="table-opt-block">
						<a href="javascript:void(0);"
							onclick="userDetail('<s:property value="userId" />');return false;">详情</a>
						<s:if test="status.code == 0">
							<a href="javascript:void(0);"
								onclick="userEdit('<s:property value="userId" />');return false;">修改</a>
							<a href="javascript:void(0);"
								onclick="statusChange('<s:property value="userId" />','disable_user');return false;">禁用</a>
							<a href="javascript:void(0);"
								onclick="resetPassword('<s:property value="userId" />');return false;">密码重置</a>
						</s:if>
						<s:elseif test="status.code == 1">
							<a href="javascript:void(0);"
								onclick="statusChange('<s:property value="userId" />','enable_user');return false;">启用</a>
						</s:elseif>
						<s:elseif test="status.code == 2">
							<a href="javascript:void(0);"
								onclick="statusChange('<s:property value="userId" />','enable_user');return false;">启用</a>
							<a href="javascript:void(0);"
								onclick="statusChange('<s:property value="userId" />','disable_user');return false;">禁用</a>
						</s:elseif>	
					</td>
				</tr>
			</s:iterator>
		</table>
	</div>
	<!-- 用户详细信息 -->
	<s:include value="userDetail.jsp" />
	<!-- 用户编辑 -->
	<s:include value="userEdit.jsp" />
	<!-- 用户新建 -->
	<s:include value="userCreate.jsp" />
	<!-- 权限列表页面 -->
	<s:include value="userOperationRoleList.jsp" />
	<!-- 业务组列表-->
	<s:include value="userBindAppList.jsp" />

	<!-- 翻页 -->
	<div class="pageBar">
		<s:property value="pageBar" escape="false" />
	</div>
</div>

<script type="text/javascript"
	src="../scripts/pagesjs/system/security/user_list.js"></script>