<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="self-config">
	<div class="config-title">
		<h2>自定义配置</h2>
		<div id="message" style="display: none;">
			<span id="error" class="error"><s:actionerror /></span> <span
				class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
		</div>
	</div>
	<s:form id="ebsApplyInfoAction" name="ebsApplyInfoAction"
		action="ebsApplyInfoCustomAction.action" method="post">
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
				<s:hidden id="userId" name="userId"></s:hidden>
				<s:if test="#session.userInfo.userId=='admin'">
					<span class="col-name">所属企业客户</span><span class="col-content"
						id="bindDiv"><a class="" href="javascript:bindBusiness();">绑定企业客户</a></span>
				</s:if>
				<s:else>
					<span class="col-name">所属企业客户</span>
					<span id="bindDiv1" class="col-content">
				</s:else>
			</div>

			<%-- <div>
			<span class="col-name">硬盘类型</span><span class="col-content"><a
				class="" href="javascript:bindBusiness();">绑定业务</a></span>
		</div> --%>
			<div>
				<span class="col-name">名称</span><span class="col-content"><input
					type="text" size="25" id="ebsName" name="ebsName" value="" /></span>
			</div>
			<div>
				<span class="col-name">硬盘类型</span><span class="col-content"><input
					type="hidden" id="resourceTypeVm" name="resourceType" value="1" />云硬盘</span>
			</div>
		</div>
		<div class="config-content" style="margin-bottom: 200px;">
			<h3>购买信息</h3>
			<div>
				<span class="col-name">云硬盘容量(TB)</span>
				<div class="col-content">
					<div class="slide-box">
						<div id="wrapper" class="wrapper">
							<div id="fill" class="fill"></div>
							<div id="slider" class="slider"></div>
						</div>
						<input id="discSize" name="" type="number" min="0" max="5" step="0.5" class="slide-val" 
							value="0"  /> <input id="disk"
							name="discSize" type="hidden" value="0" />
						<div class="slide-intro">
							<span>0TB</span> <span>0.5TB</span> <span>1TB</span> <span>1.5TB</span>
							<span>2TB</span> <span>2.5TB</span> <span>3TB</span> <span>3.5TB</span>
							<span>4TB</span> <span>4.5TB</span> <span>5TB</span>
						</div>
					</div>
				</div>
			</div>
			<div style="clear: both;"></div>
			<div>
				<span class="col-name">计费方式</span>
				<div class="col-content">
					<input id="chargeType_hour" name="chargeType" type="radio"
						checked="checked" value="h" onchange="chargeTypeChange(this)"><a
						class="text" href="javascript:$('#chargeType_hour').click();">按时计费</a>
					<input id="chargeType_month" name="chargeType" type="radio"
						value="m" onchange="chargeTypeChange(this)"> <a
						class="text" href="javascript:$('#chargeType_month').click();">包月计费</a>
					<a href="javascript:showChargesInfo();" style="margin-left: 23px;">收费标准</a>
				</div>
			</div>
			<div>
				<span class="col-name">计费时长</span>
				<div class="col-content">
					<input type="number" size="30" id="lengthTime" name="lengthTime"
						value="1" min="1"
						onkeyup='this.value=this.value.replace(/[^\d]/gi,"")'
						onfocus="if(value==''){value=1}" onblur="if(value==''){value=1}"
						onchange="changeLengthTime(this)" /><span class="timeType">小时</span>
				</div>
			</div>
			<div>
				<span class="col-name">购买数量</span><span class="col-content"><input
					type="number" name="discNum" id="num" value="1" min="1" />块</span>
			</div>
			
			<div>
				<span class="col-name">BOSS订购实例号码</span><span class="col-content"><input
					type="text" size="30" id="ebsBossOrderId" name="ebsBossOrderId"
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
	src="../scripts/pagesjs/console/disk/ebs_apply.js"></script>
<script>
	/* var diskSize = ${param.discSize};
	$("#discSize").val(diskSize + "TB");
	$("#disk").val(diskSize);
	var menu_width = $('#left').width();
	var slider = document.getElementById('slider');
	var fill = document.getElementById('fill');
	var wrapper_width = document.getElementById('wrapper').clientWidth;
	var slider_width = document.getElementById('slider').clientWidth;
	var wrapper_left = document.getElementById('wrapper').offsetLeft;

	if ((wrapper_width * diskSize) > (wrapper_width - slider_width)) {
		slider.style.left = wrapper_width - slider_width + 'px';
		fill.style.width = wrapper_width - slider_width + 'px';
	} else if ((wrapper_width * diskSize) < slider_width) {
		slider.style.left = '0px';
		fill.style.width = '0px';
	} else {

		slider.style.left = (wrapper_width * (diskSize - 100) / 900)
				- slider_width / 2 + 'px';
		fill.style.width = (wrapper_width * (diskSize - 100) / 900)
				- slider_width / 2 + 'px';
	} */
</script>
