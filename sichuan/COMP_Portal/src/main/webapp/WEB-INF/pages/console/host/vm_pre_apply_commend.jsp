<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="self-config">
	<div class="config-title">
		<h2>预订购</h2>
		<div id="message" style="display: none;">
			<span id="error" class="error"><s:actionerror /></span> <span
				class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
		</div>
	</div>
	<s:form id="vmPreApplyInfoAction" name="vmPreApplyInfoAction"
		action="vmPreApplyInfoAction.action" method="post">
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
			<div>
				<span class="col-name">名称</span><span class="col-content"><input
					type="text" id="vmName" name="vmName" maxlength="16" value="" /></span>
			</div>
			<div>
				<span class="col-name">cpu</span><span class="col-content cpu-items"><!-- <a
					class="cpu btn hover" href="javascript:void(0);">1核</a> <a
					class="cpu btn hover" href="javascript:void(0);">2核</a> <a
					class="cpu btn hover" href="javascript:void(0);">4核</a><a
					class="cpu btn hover" href="javascript:void(0);">8核</a> <a
					class="cpu btn hover" href="javascript:void(0);">16核</a> --></span> <input
					type="hidden" id="cpuNum" name="cpuNum"
					value="<s:property value='cpuNum' />" />
			</div>
			<div>
				<span class="col-name">内存</span><span class="col-content ram-items"><!-- <a
					class="ram btn hover" href="javascript:void(0);">2GB</a> <a
					class="ram btn hover" href="javascript:void(0);">4GB</a> <a
					class="ram btn hover" href="javascript:void(0);">8GB</a> --></span> <input
					type="hidden" id="ramSize" name="ramSize" value="<s:property value='ramSize' />" />
			</div>
			<input type="hidden" id="standardId" name="standardId"
				value="<s:property value='ramSize' />" />
			<div>
				<span class="col-name">镜像</span>
				<div class="col-content">
					<div style="position: relative; height: 100%;">
						<select name="" id="osTypeSelect"></select> <select
							id="osNameSelect" name="osId" style="left: 155px;"
							onchange="changeosName()">
						</select> <input type="hidden" id="osName" name="osName" value="" />
					</div>
				</div>
			</div>
			
			<div>
				<span class="col-name">数据盘(TB)</span>

				<div class="col-content">
					<div class="slide-box">
						<div id="wrapper" class="wrapper">
							<div id="fill" class="fill"></div>
							<div id="slider" class="slider"></div>
						</div>
						<input id="data_disk" type="number" min="0" max="5" step="0.5" class="slide-val" value="0"
							 /><input id="disk" name="discSize"
							type="hidden" value="0" />
						<div class="slide-intro">
							<span>0TB</span> <span>0.5TB</span> <span>1TB</span> <span>1.5TB</span>
							<span>2TB</span> <span>2.5TB</span> <span>3TB</span> <span>3.5TB</span>
							<span>4TB</span> <span>4.5TB</span> <span>5TB</span>
						</div>
					</div>

				</div>

			</div>
			<div style="clear: both;"></div>

		</div>


		<div class="config-content">
			<h3>购买量</h3>
			<div>
				<span class="col-name">计费方式</span>
				<div class="col-content">
					<input type="hidden" id="chargeType" value="<s:property value="chargeType" />" />
					<input id="chargeType_hour" name="chargeType" type="radio"
						checked="checked" value="h" onchange="chargeTypeChange(this)"><a id="chargeText_hour"
						class="text" href="javascript:;">按时计费</a>
					<input id="chargeType_month" name="chargeType" type="radio"
						value="m" onchange="chargeTypeChange(this)"><a id="chargeText_month"
						class="text" href="javascript:;">包月计费</a>
					<a href="javascript:showChargesInfo();" style="margin-left: 23px;">收费标准</a>
				</div>
			</div>
			<div>
				<span class="col-name">计费时长</span>
				<div class="col-content">
					<input type="number" size="30" id="lengthTime" name="lengthTime"
						value="<s:property value="lengthTime" />" min="1"
						onkeyup='this.value=this.value.replace(/[^\d]/gi,"")'
						onfocus="if(value==''){value=1}" onblur="if(value==''){value=1}"
						onchange="changeLengthTime(this)" /><span class="timeType">小时</span>
				</div>
			</div>
			<div>
				<span class="col-name">购买数量</span><span class="col-content"><input
					type="number" name="num" id="num" value="<s:property value="num" />" min="1" max="5" onchange="changeNum(this)"/>台</span>
			</div>
		</div>


		<div class="config-content">
			<h3>企业客户</h3>
			<div>
				<s:hidden id="userId" name="userId"></s:hidden>
				<s:if test="#session.userInfo.userId=='admin'">
					<span class="col-name">所属企业客户</span><span id="bindDiv"
						class="col-content"><a class=""
						href="javascript:bindBusiness();">选择企业客户</a></span>
				</s:if>
				<s:else>
					<span class="col-name">所属企业客户</span>
					<span id="bindDiv1" class="col-content">
				</s:else>
			</div>
			
			<div>
				<span class="col-name">IP类型</span>
				<input type="hidden" id="ipType" value="0" />
				<div class="col-content">
					<input id="ipType_ipv4" name="ipType" type="radio" checked="checked" value="0" onchange="chargeIpTypeChange(this)">
					<a id="chargeIpType_ipv4" class="text" href="javascript:;">IPV4</a>
					
					<input id="ipType_ipv6" name="ipType" type="radio" value="1" onchange="chargeIpTypeChange(this)">
					<a id="chargeIpType_ipv6" class="text" href="javascript:;">IPV6</a>
				</div>
			</div>

		</div>

		<div class="config-content" style="margin-bottom: 200px;">
			<h3>其他</h3>
			<div>
				<span class="col-name">备注</span><span class="col-content"><input
					type="text" size="30" id="vmRemark" name="vmRemark"
					class="max-text" /></span>
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

<s:include value="chargesList.jsp" />
<script type="text/javascript"
	src="../scripts/pagesjs/console/host/vm_pre_apply_commend.js"></script>
<script>
	

</script>