<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>

</head>
<body>

	<!--右侧-->
	<div id="right">
		<h1>业务管理</h1>
		<div class="details-con">
			<div class="detail-title">
				<a href="businessList.action" title="返回"> <span class="back"></span>
				</a>
				<h3 class="apply-title">编辑业务</h3>
			</div>
			<!-- form -->
			<div class="page-form">
				<form id="createForm" action="businessCreate.action">
					<input id="_businessId" type="hidden" value="<s:property value="businessInfo.businessId" />"/>
					<div class="section">
						<div class="field">
							<div class="caption">业务名称：</div>
							<div class="content">
								<input type="text" size="25" name="businessName"
									id="_businessName" value="<s:property value="businessInfo.name" />" />
							</div>
							<div class="point">(请输入字母、数字、下划线汉字或它们的组合，最多32位)</div>
						</div>
						<div class="field">
							<div class="caption">业务描述：</div>
							<div class="content">
								<textarea id="_businessDesc"
									name="businessDes" cols="40"><s:property value="businessInfo.description" /></textarea>
							</div>
						</div>
						<div class="field">
							<div class="caption">主机资源：</div>
							<div class="content">
								<ul id="hostId" class="ulList">
								<s:iterator value="businessInfo.resourceList" id="res">
									<li data-resourceid="<s:property value="resourceId" />" data-name="<s:property value="name" />" data-type="<s:property value="type" />">
										<s:property value="name" />
										<a href="javascript:void(0);" onclick="javascript:unbindHost(this);">删除</a>
									</li>
								</s:iterator>
									<dl>
										<a href="javascript:bindHost();">绑定主机</a>
									</dl>
								</ul>
							</div>
						</div>
					</div>
				</form>
				<div class="page-button">
					<ul class="opt-bottom-btn">
						<li><a href="javascript:updateBusiness()">确定</a></li>
						<li><a href="businessList.action">取消</a></li>
					</ul>
				</div>

			</div>
		</div>
		<!-- form end -->
	</div>
	</div>
	<!-- 选择业务 -->
	<div id="bindHost" class="float-div" style="display:none;width:700px;">
		<div class="float-toolsbar">
			<span class="keyName">主机名称：</span> <input type="text"
				class="float-input-long" id="hostQueryName" value="" />
			<ul class="opt-btn vh-btn-r" style="float:right;">
				<li style="align:right;"><a class="search" href="#" id="searchHostButton">查询</a></li>
			</ul>
		</div>
		<div class="float-div-center" style="width:690px">
			<div class="table-content">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					id="bindHostListTab">
					<thead>
						<tr>
							<col style="width:10%;" />
							<col style="width:50%;" />
							<col style="width:40%;" />
						</tr>
						<tr>
							<th class="nl"><input type="checkbox" name="checkAllHost"
								id="checkAllHost" value="TESTSERVER2" /></th>
							<th>主机名称</th>
							<th>主机类型</th>
						</tr>
					</thead>
					<tbody id="hostListTbody">
					</tbody>
				</table>
			</div>
		</div>
		<div class="pageBar" id="hostListPageBarDiv" style="text-align:center"></div>
	</div>


	<script type="text/javascript"
		src="../scripts/pagesjs/business/business_edit.js"></script>
</body>