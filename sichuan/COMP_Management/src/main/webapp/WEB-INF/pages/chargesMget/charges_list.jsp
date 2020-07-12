<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<h1>资费管理</h1>
<span class="apply"><a id="add_charges_button"
	href="javascript:void(0)">创建资费</a></span>
<div id="message" style="display: none;">
	<span class="error"><s:actionerror /></span> <span class="error"><s:fielderror /></span>
	<span class="success"><s:actionmessage /></span>
</div>
<div class="details-con">
	<div class="detail-title">
		<h3 class="apply-title"><s:if test="chargesType==0">虚拟机资费列表</s:if> <s:else>云硬盘资费列表</s:else>   </h3>
	</div>

	<form name="chargesForm" id="chargesForm"
		action="../charges/queryChargesAction.action" method="post">
		<div class="table-seach">

			<p>
				按时长： <input id="hourPrice" name="charges.hourPrice" class="text"
					type="text" size="11"
					value="<s:property value="charges.hourPrice" />" />
			</p>

			<p style="margin-right: 25px; margin-left: 6px;">
				包月费： <input id="monthPrice" name="charges.monthPrice" class="text"
					type="text" size="11"
					value="<s:property value="charges.monthPrice" />" />
			</p>
			<p style="margin-right: 25px;">
				创建时间： <input id="startTime" name="charges.createTime" readonly="true"
					class="Wdate wdatelong" type="text" size="23"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"
					value="<s:property value="charges.createTime" />" /> 至 <input
					id="endTime" name="charges.endTime" readonly="true"
					class="Wdate wdatelong" type="text" size="23"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"
					value="<s:property value="charges.endTime" />" />
			</p>

			<p style="margin-right: 25px;">
				创建人: <input id="createUser" name="charges.createUser" class="text"
					type="text" size="20"
					value="<s:property value="charges.createUser" />" />
			</p>
			<s:hidden name="chargesType"/>
			<ul class="opt-btn fr">
				<li><a id="search" class="search" href="javascript:void(0);">查询</a>
				</li>
			</ul>
		</div>
	</form>
		
	<div id="approval-charges" class="table-content table-block">
		<table id="chargesTable" width="100%" border="0" cellspacing="0"
			cellpadding="0">
			<s:if test="chargesType==0">
			<col style="width: 12%;" />
			<col style="width: 12%;" />
			</s:if>
			<col style="width: 12%;" />
			<col style="width: 11%;" />
			<col style="width: 13%;" />
			<col style="width: 15%;" />
			<col style="width: 15%;" />
			<tr>
				<s:if test="chargesType==0">
				<th class="nl">CPU个数</th>
				<th>内存(GB)</th>
				</s:if>
				<s:if test="chargesType==1">
						<th class="nl">按时长</th>
				</s:if>
				<s:else>
					<th>按时长</th>
				</s:else>
				<th>包月费</th>
				<th>创建时间</th>
				<th>创建人</th>
				<th>操作</th>
			</tr>
			<s:if test="chargesList.size()>0">
				<s:iterator value="chargesList">
					<tr class="iterator">
					<s:if test="chargesType==0">
						<td ><s:property value="cpuNumber" /></td>
						<td ><s:property value="memorySize"/> </td>
					</s:if>
						<td><s:property value="hourPrice" /></td>
						<td><s:property value="monthPrice" /></td>
						<td ><s:property value="createTime" /></td>
						<td><s:property value="createUser" /></td>
						<td class="table-opt-block"><a href="javascript:void(0);"
							onclick="chargesDetail('<s:property value="id" />');return false;">详情</a>
							<a href="javascript:void(0);"
							onclick="chargesUpdate('<s:property value="id" />');return false;">修改</a>
							<a href="javascript:delCharges('<s:property value="id" />')">删除</a></td>
					</tr>
				</s:iterator>
		    </s:if>
		    <s:else>
		    		<tr>
	              		<td colspan="7" align="center">
	              			暂无符合数据！
	              		</td>
              	</tr>
		    </s:else>
		</table>
		
		<!-- 翻页 -->
	<div class="pageBar">
		<s:property value="pageBar" escape="false" />
	</div>
	</div>
	<!-- 添加页面 -->
	<jsp:include page="charges_add.jsp"/>
	<!-- 修改页面 -->
	<jsp:include page="charges_edit.jsp"/>
	<!-- 详细页面 -->
	<jsp:include page="charges_detail.jsp"/>
	<script type="text/javascript">
		var  chargesType=<s:property value="chargesType"/>;
	</script>
	<script type="text/javascript"
		src="../scripts/pagesjs/charges/charges.js"></script>
		