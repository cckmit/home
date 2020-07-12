<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<link href="../styles/vm.css" rel="stylesheet" type="text/css" />

</head>
<div id="right">
	<h1>云硬盘控制台</h1>
	<div id="message" style="display: none;">
		<span id="error" class="error"><s:actionerror /></span> <span
			class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
	</div>


	<s:form id="ebsApplyInfoAction" name="ebsApplyInfoAction"
		action="ebsApplyInfoCustomAction.action" method="post">
		<span class="region"> 选择资源池：<select class="select-max nf"
			id="respool" name="respoolId"></select> 分区：<select
			class="select-max nf" id="respoolpart" name="respoolPartId"></select>
			<input type="hidden" id="respoolName" name="respoolName"
			value="<s:property value="respool.respoolName"/>" /> <input
			type="hidden" id="respoolPartName" name="respoolPartName"
			value="<s:property value="respoolPart.respoolPartName"/>" />
		</span>

		<!-- 主机详情 -->
		<div class="details-con">
			<div class="detail-title">
				<a href="ebsQueryListAction.action" title="返回"> <span
					class="back"></span>
				</a>
				<h3 class="apply-title">创建云硬盘</h3>
				<ul class="vm-apply-btn">
					<li class="vm-apply"><a
						href="ebsApplyAction.action?queryBusinessId=<s:property value='queryBusinessId'/>">推荐配置</a>
					</li>
					<li class="vm-apply-se"><a href="#">自定义配置</a></li>
				</ul>
			</div>
			<div class="detail-info apply-info-l apply-info">
				<!-- <div  id="topItems"  class="apply-info-top">
                </div> -->
				<div id="bindDiv" class="apply-info-top">
					<span class="apply-span-name">所属业务：</span> <a
						href="javascript:bindBusiness();">绑定业务</a>
				</div>
				<input type="hidden" id="itemId" name="itemId2"	value="<s:property value="itemId"/>" /> 
				<input type="hidden" id="standardId" name="standardId" value="" /> 
				<input type="hidden" id="discSize" name="discSize" />
				<!-- <hr class="apply-line" /> -->
				<div class="apply-info-line-top">
					<span class="apply-span-name">云硬盘容量(G)：</span> <input
						class="vh-input-min" type="text" size="25" id="diskSize" value="" />
				</div>
				<div class="apply-info-line-top">
					<span class="apply-span-name">硬盘类型：</span> 
					<input type="radio" class="radio" id="resourceTypeVm" name="resourceType" value="1" checked/><label for="vm">云硬盘</label>&nbsp;&nbsp;&nbsp;&nbsp;
                    <%-- <input type="radio" class="radio" id="resourceTypePm" name="resourceType" value="2" /><label for="pm">物理硬盘</label>--%>
				</div>
				<hr class="apply-line" />
				<div class="apply-info-line-top">
					<span class="apply-span-name">名称：</span> <input
						class="vh-input-max" type="text" size="25" id="ebsName"
						name="ebsName" value="" />
				</div>

				<div class="apply-info-line-top">
					<span class="apply-span-name">块个数：</span> <input
						class="vh-input-max" type="text" size="25" id="ebsNum"
						name="discNum" value="" />
				</div>


					<div>
						<span  class="apply-span-name">计费方式：</span>
							<input id="chargeType_hour" name="chargeType" type="radio"  checked="checked" value="h" onchange="chargeTypeChange(this)">按时计费&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input id="chargeType_month" name="chargeType" type="radio"  value="m" onchange="chargeTypeChange(this)">包月计费
							<a href="javascript:showChargesInfo();" style="margin-left: 23px;">收费标准</a>
					</div>
					
					<div class="apply-info-button">
						<span class="apply-span-name">计费时长：</span> 
						<input type="text"
							size="30" id="lengthTime" name="lengthTime" maxlength="4"   onkeyup='this.value=this.value.replace(/[^\d]/gi,"")' 
							onfocus="if(value==''){value=1}" onblur="if(value==''){value=1}" onchange="changeLengthTime(this)"/>
				    </div>
				
			</div>
			<div class="detail-info apply-info-r">
				<div class="apply-head-r">
					<a>您选择的云硬盘配置</a>
				</div>
				<div class="apply-head-r apply-detail-r-status">
					<div>
						<span class="pnip-r-det-span-name">云硬盘容量：</span> <span
							class="apply-font-color"><LABEL id="discSize2"></LABEL>G</span>
					</div>
					<div>
						<span class="pnip-r-det-span-name">硬盘类型：</span> <span
							class="apply-font-color"><LABEL id="resourceType2"></LABEL></span>
					</div>
					<div>
						<span class="apply-r-span-name">块个数：</span> <span><font
							id="discNum2"></font></span>
					</div>
				</div>
				<div class="apply-head-r apply-detail-r">
						<div>
							<span class="apply-r-span-name">计费方式：</span> <span><font
								id="chargeTypeSpan"></font></span>
						</div>
						<div>
							<span class="apply-r-span-name">计费时长：</span> <span><font
								id="lengthTimeZH"></font></span>
						</div>
					</div>	

				<div class="apply-btn-commit">
					<span> <a href="javascript:submitform()">提交</a>
					</span>
				</div>
			</div>
			<div class="detail-time"></div>
		</div>
	</s:form>
</div>
<div id="bindBusiness" class="float-div"
	style="display: none; width: 700px;">
	<div class="float-toolsbar">
		<span class="keyName">业务名称：</span> <input type="text"
			class="float-input-long" id="queryBusinessName" />
		<ul class="opt-btn vh-btn-r">
			<li><a class="search"
				href="javascript:loadBusinessList('businessListJson.action')">查询</a></li>
		</ul>
	</div>
	<div class="float-div-center">
		<div class="table-content">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="businessListTab">
				<thead>
					<tr>
						<col style="width: 10%;" />
						<col style="width: 50%;" />
						<col style="width: 40%;" />
					</tr>
					<tr>
						<th class="nl"></th>
						<th>业务名称</th>
						<th>业务描述</th>
					</tr>
				</thead>
				<tbody id="businessListTbody">
				</tbody>
			</table>
		</div>
	</div>
	<div class="pageBar" id="businessListPageBarDiv"
		style="text-align: center"></div>
</div>
<s:include value="chargesList.jsp"/>
<script type="text/javascript"
	src="../scripts/pagesjs/console/disk/ebs_apply_custom.js"></script>