<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="self-config">
	<div class="config-title">
		<h2>申请IP地址</h2>
		<div id="message" style="display: none;">
			<span id="error" class="error"><s:actionerror /></span> <span
				class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
		</div>
	</div>
	<s:form action="IpApplyInfoAction.action" method="post">
		<div class="config-content">
			<h3>基础配置</h3>
			<div>
				<span class="col-name">资源池分区</span>
				<div class="col-content">
					<div style="position: relative; height: 100%;">
						<select id="respool" name="respoolId"></select> <select
							style="left: 155px;" id="respoolpart" name="respoolPartId"></select>
						<input type="hidden" id="respoolName" name="respoolName"
							value="<s:property value="respool.respoolName"/>" /> <input
							type="hidden" id="respoolPartName" name="respoolPartName"
							value="<s:property value="respoolPart.respoolPartName"/>" />

					</div>
				</div>
			</div>

			<%-- <div>
				<span class="col-name">所属IP地址</span><span class="col-content"
					id="bindIpDiv"><a class="" href="javascript:bindIp();">选择IP地址</a></span>
			</div> --%>

		<%--	<span class="col-name">所属企业客户</span><span class="col-content"
				id="bindDiv"><a class="" href="javascript:bindBusiness();">绑定企业客户</a></span> --%>
								
			<div>
				<s:hidden id="userId" name="userId"></s:hidden>
				<s:if test="#session.userInfo.userId=='admin'">
					<span class="col-name">所属企业客户</span><span id="bindDiv"
						class="col-content"><a class=""
						href="javascript:bindBusiness();">绑定企业客户</a></span>
				</s:if>
				<s:else>
					<span class="col-name">所属企业客户</span>
					<span id="bindDiv1" class="col-content">
				</s:else>
			</div>
	<div>
				<span class="col-name">购买数量</span><span class="col-content"><input
					type="number" name="num" id="num" value="1" min="1" />个</span>
			</div>
			<div>
				<span class="col-name">描述</span><span class="col-content"><input class="max-text"
					type="text"  id="desc" name="description" /></span>
			</div>
		

			<div class="submit">
				<a class="btn fr" href="javascript:submitform()">提交</a>
			</div>

		</div>
	</s:form>
</div>

<div id="bindBusiness" style="display: none;">

	<div class="toolbar">
		<span class="keyName">企业客户名称：</span><input type="text" size="30"
			id="queryBusinessName" /> <a class="search btn fr"
			href="javascript:loadBusinessList('businessListJson.action')">查询</a>
	</div>

	<table class="table" id="businessListTab">
		<thead>
			<tr>
				<th></th>
				<th>企业客户名称</th>
				<th>企业客户描述</th>
			</tr>
		</thead>
		<tbody id="businessListTbody">
		</tbody>
	</table>

	<div class="pageBar" id="businessListPageBarDiv"
		style="text-align: center"></div>
</div>

<script type="text/javascript"
	src="../scripts/pagesjs/console/ip/ip_apply.js"></script>



<!-- IP段查询,选择dialog -->
<div id="bindIp" style="display: none;">
	<table id="ipListTab" class="table">
		<thead>
			<tr>
				<th></th>
				<th>IP地址</th>
			</tr>
		</thead>
		<tbody id="ipListTbody">
		</tbody>
	</table>
	<div class="pageBar" id="ipListPageBarDiv" style="text-align: center"></div>
</div>
