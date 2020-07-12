<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<link href="../styles/vh.css" rel="stylesheet" type="text/css" />

</head>
<div id="right">
	<h1>云硬盘控制台</h1>
	<div id="message" style="display: none;">
		<span id="error" class="error"><s:actionerror /></span> <span
			class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
	</div>
	<s:form id="ebsApplyInfoAction" name="ebsApplyInfoAction"
		action="ebsApplyInfoCustomAction.action" method="post">

		<s:hidden id="nodeType" name="nodeType"></s:hidden>
		<s:hidden id="nodeId" name="nodeId"></s:hidden>
		<s:hidden id="treeNodeName" name="treeNodeName"></s:hidden>
		<s:hidden id="pnodeId" name="pnodeId"></s:hidden>
		<s:hidden id="pnodeName" name="pnodeName"></s:hidden>
		<s:hidden id="curFun" name="curFun"></s:hidden>

		<span class="region"> 选择资源池：<select class="select-max nf"
			id="respool" name="respoolId"></select> 分区：<select
			class="select-max nf" id="respoolpart" name="respoolPartId"></select>
			<input type="hidden" id="respoolName" name="respoolName"
			value="<s:property value="respool.respoolName"/>" /> <input
			type="hidden" id="respoolPartName" name="respoolPartName"
			value="<s:property value="respoolPart.respoolPartName"/>" /> <input
			type="hidden" name="queryBusinessId"
			value="<s:property value="#parameters.queryBusinessId" />" />
		</span>

		<!-- 主机详情 -->
		<div class="details-con">
			<div class="detail-title">
				<a
					href="ebsQueryListAction.action?queryBusinessId=<s:property value="#parameters.queryBusinessId" />
					&businessName=<s:property value="businessName" />&nodeType=<s:property value="nodeType" />
						&nodeId=<s:property value="nodeId" />&treeNodeName=<s:property value="treeNodeName" />
						&pnodeId=<s:property value="pnodeId" />&pnodeName=<s:property value="pnodeName" />
						&appId=<s:property value="appId" />&curFun=<s:property value="curFun" />"
					title="返回"> <span class="back"></span>
				</a>
				<h3 class="apply-title">创建云硬盘</h3>
				<ul class="vh-btn-r">
					<li class="vh-span-btn"><a
						href="ebsApplyAction.action?queryBusinessId=<s:property value='queryBusinessId' />
						&businessName=<s:property value="businessName" />&nodeType=<s:property value="nodeType" />
						&nodeId=<s:property value="nodeId" />&treeNodeName=<s:property value="treeNodeName" />
						&pnodeId=<s:property value="pnodeId" />&pnodeName=<s:property value="pnodeName" />
						&appId=<s:property value="appId" />&curFun=<s:property value="curFun" />">推荐配置</a>
					</li>
					<li class="vh-span-btn"><a href="#">自定义配置</a></li>
				</ul>
			</div>
			<div class="detail-info apply-info-l apply-info">
				<!-- <div  id="topItems"  class="apply-info-top">
                </div> -->
				<div id="bindDiv" class="apply-info-top">
					<span class="apply-span-name">所属业务：</span>
					<span class="apply-span-name" style="width:auto;"><s:property value="businessName" /></span>
					 <input type="hidden" id="appId" name="appId" value="<s:property value="#parameters.queryBusinessId" />">
				</div>
				<div>
					<span class="apply-span-name">云硬盘容量(G)：</span> <input
						class="vh-input-min" type="text" size="25" id="diskSize" value="" />
				</div>
				<div class="apply-info-line-top">
					<span class="apply-span-name">硬盘类型：</span> 
					<input type="radio" class="radio" id="resourceTypeVm" name="resourceType" value="1" checked/><label for="vm">云硬盘</label>&nbsp;&nbsp;&nbsp;&nbsp;
                    <%-- <input type="radio" class="radio" id="resourceTypePm" name="resourceType" value="2" /><label for="pm">物理硬盘</label>--%>
				</div>
				<input type="hidden" id="itemId" name="itemId2"
					value="<s:property value="itemId"/>" /> <input type="hidden"
					id="standardId" name="standardId" value="" /> <input type="hidden"
					id="discSize" name="discSize" value="" />
				<hr class="apply-line" />
				<div class="apply-info-line-top">
					<span class="apply-span-name">名称：</span> <input
						class="vh-input-max" type="text" size="25" id="ebsName"
						name="ebsName" value="" />
				</div>

				<div class="apply-info-line-top">
                	<span class="apply-span-name">块个数：</span>
                	<input class="vh-input-max" type="text" size="25" id = "ebsNum" name="discNum" value=""/>
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
				

<%-- 				<div class="apply-info-button">
					<span class="apply-span-name">时长：</span> <select
						id="accountingType" class="select-min"
						onchange="changeAccountingTypeBefore(this);">
						<option value="year" selected="selected">按年</option>
						<option value="month">按月</option>
						<option value="day">按天</option>
						<option value="hour">按小时</option>
					</select>


					<div id="accountingTerms">


						<select class="select-min yearOption" id="lengthTimeYear"
							onchange="changeAccountingType(this);">
							<option value="1_y">1年</option>
							<option value="2_y">2年</option>
							<option value="3_y">3年</option>
							<option value="4_y">4年</option>
							<option value="5_y">5年</option>
							<option value="0_y" selected="selected">无限期</option>
						</select> <select class="select-min monthOption" id="lengthTimeMonth"
							onchange="changeAccountingType(this)">
							<option value="1_m" selected="selected">1个月</option>
							<option value="2_m">2个月</option>
							<option value="3_m">3个月</option>
							<option value="4_m">4个月</option>
							<option value="5_m">5个月</option>
							<option value="6_m">6个月</option>
							<option value="7_m">7个月</option>
							<option value="8_m">8个月</option>
							<option value="9_m">9个月</option>
							<option value="10_m">10个月</option>
							<option value="11_m">11个月</option>
						</select> <select class="select-min dayOption" id="lengthTimeDay"
							onchange="changeAccountingType(this)">
							<option value="1_d" selected="selected">1天</option>
							<option value="2_d">2天</option>
							<option value="3_d">3天</option>
							<option value="4_d">4天</option>
							<option value="5_d">5天</option>
							<option value="6_d">6天</option>
							<option value="7_d">7天</option>
							<option value="8_d">8天</option>
							<option value="9_d">9天</option>
							<option value="10_d">10天</option>
							<option value="11_d">11天</option>
							<option value="12_d">12天</option>
							<option value="13_d">13天</option>
							<option value="14_d">14天</option>
							<option value="15_d">15天</option>
							<option value="16_d">16天</option>
							<option value="17_d">17天</option>
							<option value="18_d">18天</option>
							<option value="19_d">19天</option>
							<option value="20_d">20天</option>
							<option value="21_d">21天</option>
							<option value="22_d">22天</option>
							<option value="23_d">23天</option>
							<option value="24_d">24天</option>
							<option value="25_d">25天</option>
							<option value="26_d">26天</option>
							<option value="27_d">27天</option>
							<option value="28_d">28天</option>
							<option value="29_d">29天</option>
						</select> <select class="select-min hourOption" id="lengthTimeHour"
							onchange="changeAccountingType(this)">
							<option value="1_h" selected="selected">1小时</option>
							<option value="2_h">2小时</option>
							<option value="3_h">3小时</option>
							<option value="4_h">4小时</option>
							<option value="5_h">5小时</option>
							<option value="6_h">6小时</option>
							<option value="7_h">7小时</option>
							<option value="8_h">8小时</option>
							<option value="9_h">9小时</option>
							<option value="10_h">10小时</option>
							<option value="11_h">11小时</option>
							<option value="12_h">12小时</option>
							<option value="13_h">13小时</option>
							<option value="14_h">14小时</option>
							<option value="15_h">15小时</option>
							<option value="16_h">16小时</option>
							<option value="17_h">17小时</option>
							<option value="18_h">18小时</option>
							<option value="19_h">19小时</option>
							<option value="20_h">20小时</option>
							<option value="21_h">21小时</option>
							<option value="22_h">22小时</option>
							<option value="23_h">23小时</option>
						</select>
					</div>
					
				</div> --%>
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
                        <span class="apply-r-span-name">块个数：</span>
                        <span><font id="discNum2"></font></span>
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
				<%--<div class="apply-head-r apply-detail-r">
					<div>
						<span class="apply-r-span-name">时长：</span> <span><font
							id="lengthTimeZH"></font></span>
					</div>
				</div>--%>

				<div class="apply-btn-commit">
					<span> <a href="javascript:submitform()">提交</a>
					</span>
				</div>
			</div>
			<div class="detail-time"></div>
		</div>
	</s:form>
</div>
<jsp:include page="chargesList.jsp"/>
<script type="text/javascript"
	src="../scripts/pagesjs/console/business/disk/ebs_apply_custom.js"></script>