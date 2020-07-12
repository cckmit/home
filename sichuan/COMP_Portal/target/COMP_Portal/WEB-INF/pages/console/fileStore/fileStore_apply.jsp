<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="self-config">
	<div class="config-title">
		<h2>申请文件存储</h2>
		<div id="message" style="display: none;">
			<span id="error" class="error"><s:actionerror /></span> <span
				class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
		</div>
	</div>
	<s:form action="fileStoreApplyInfoAction.action" method="post">
		<div class="config-content">
			<h3>基础配置</h3>
			<span class="col-name">文件存储名称</span><span class="col-content"><input
				type="text" size="25" id="fsName" name="fsInstanceInfo.fsName" value="" /></span>
			<div>
				<span class="col-name">资源池分区</span>
				<div class="col-content">
					<div style="position: relative; height: 100%;">
						<select id="respool" name="fsInstanceInfo.resPoolId"></select> <select
							style="left: 155px;" id="respoolpart" name="fsInstanceInfo.resPoolPartId"></select>
						<input type="hidden" id="respoolName" name=".respoolName"
							value="<s:property value="respool.respoolName"/>" /> <input
							type="hidden" id="respoolPartName" name="respoolPartName"
							value="<s:property value="respoolPart.respoolPartName"/>" />

					</div>
				</div>
			</div>

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

			<span class="col-name">配额大小</span><span class="col-content"><input
				type="number" size="25" id="fsQuotaSize" name="fsInstanceInfo.fsQuotaSize" value=""
				min="1" />GB</span>

			<div>
			<input type="hidden" name="fsInstanceInfo.createType" value="1" />
				<span class="col-name">存储共享方式</span>
				<div class="col-content">
					<div style="position: relative; height: 100%;">
						<select id="fsShareType" name="fsInstanceInfo.fsShareType">
							<option value="0">NFS</option>
							<option value="1">CIFS</option>
							<option value="3">POSIX</option>
						</select>
					</div>
				</div>
			</div>
				<div>
				<span class="col-name">购买数量</span><span class="col-content"><input
					type="number" name="num" id="num" value="1" min="1" />个</span>
			</div>
			<div>
				<span class="col-name">描述</span><span class="col-content"><input
					class="max-text" type="text" id="desc" name="fsInstanceInfo.description" /></span>
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
	src="../scripts/pagesjs/console/fileStore/fileStore_apply.js"></script>
