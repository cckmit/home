<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<link href="../styles/vm.css" rel="stylesheet" type="text/css" />
</head>
<!--右侧-->
<div id="right">
	<h1>虚拟机控制台</h1>
	<div id="message" style="display: none;">
		<span id="error" class="error"><s:actionerror /></span> <span
			class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
	</div>

	<s:form id="vmApplyInfoAction" name="vmApplyInfoAction"
		action="vmApplyInfoAction.action?type=0&queryBusinessId=%{#parameters.queryBusinessId}"
		method="post">
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
			value="<s:property value="respoolPart.respoolPartName"/>" />
		</span>
		<!-- 主机详情 -->
		<div class="details-con">
			<div class="detail-title">
				<s:url id="backUrl" action="hostList">
					<s:param name="nodeType" value="nodeType" />
					<s:param name="nodeId" value="nodeId" />
					<s:param name="treeNodeName" value="treeNodeName" />
					<s:param name="pnodeId" value="pnodeId" />
					<s:param name="pnodeName" value="pnodeName" />
					<s:param name="appId" value="appId" />
					<s:param name="curFun" value="curFun" />
					<s:param name="queryBusinessId">
						<s:property value="#parameters.queryBusinessId" />
					</s:param>
					<s:param name="type">0</s:param>
				</s:url>
				<a href="${backUrl}" title="返回"><span class="back"></span></a>
				<h3 class="apply-title">申请虚拟机</h3>
				<ul class="vm-apply-btn">
					<li id="recommendCon" class="vm-apply-se"><a href="#"
						onclick="recommendConfiguration()">推荐配置</a></li>
					<li id="customCon" class="vm-apply"><a href="#"
						onclick="customConfiguration()">自定义配置</a></li>
				</ul>
			</div>
			<div class="details-con1">
				<div id="relate_resource_div"
					class="detail-info apply-info-l apply-info">
					<input type="hidden" id="itemId" name="itemId2"
						value="<s:property value="itemId"/>" /> <input type="hidden"
						id="standardId" name="standardId" value="" /> <input
						type="hidden" id="configuration" name="configuration" value="0" />
					<div id="topItems" class='itmes-height'></div>
					<div id="topItemsDes">
						<h3 id="itemDescription" class="apply-info-title"
							style="margin: 0;">适用领域及特点描述</h3>
					</div>

					<div style="display: none;" class="vm-apply-detail-l">
						<span class="apply-span-name"></span>
					</div>

					<div class="vm-apply-detail-l" id="recommendCpu">
						<div>
							<span class="apply-span-name">处理器：</span> <span
								class="apply-font-color"><LABEL id="cpuNum3"></LABEL>核</span>
						</div>
					</div>
					<div id="customCpu" style="display: none; margin-top: 40px;"
						class="vm-apply-detail-l">
						<span class="apply-span-name">处理器（核）：</span> <input type="text"
							size="30" id="cpuNum" name="cpuNum" value=""
							style="display: none;" onblur="customChange()" />
					</div>

					<div id="recommendRam" class="vm-apply-detail-l">
						<div>
							<span class="apply-span-name">内存(RAM)：</span> <span
								class="apply-font-color"><LABEL id="ramSize3"></LABEL>G</span>
						</div>
					</div>

					<div id="customRam" style="display: none;"
						class="vm-apply-detail-l">
						<span class="apply-span-name">内存RAM（G）：</span> <input type="text"
							size="30" id="ramSize" name="ramSize" value=""
							style="display: none;" onblur="customChange()" />
					</div>

					<div id="recommendDisk" class="vm-apply-detail-l">
						<div>
							<span class="apply-span-name">存储空间：</span> <span
								class="apply-font-color"><LABEL id="discSize3"></LABEL>G</span>
						</div>
					</div>

					<div id="customDisk" style="display: none;"
						class="vm-apply-detail-l">
						<span class="apply-span-name">存储空间（G）：</span> <input type="text"
							size="30" id="discSize" name="discSize" value=""
							style="display: none;" onblur="customChange()" />
					</div>

					<div class="vm-apply-detail-l">
						<span class="apply-span-name">虚拟机名称：</span> <input type="text"
							size="30" id="vmName" name="vmName" maxlength="16" />
					</div>

					<div class="vm-apply-detail-l apply-info-button">
						<span class="apply-span-name">系统：</span> <select
							class="select-max" id="osNameSelect" name="osId"
							onchange="changeosName()">
						</select> <input type="hidden" id="osName" name="osName" value="" />
					</div>
				<%-- 	<div>
						<span class="apply-span-name">物理机：</span> <select
							class="select-max" id="pmNameSelect" name="pmNameSelect"
							onchange="changePmName()">
						</select> <input type="hidden" id="pmId" name="pmId" value="" />
					</div> --%>
					<div id="bindDiv">
						<span class="apply-span-name">所属业务：</span> <input type="hidden"
							id="app" name="appId"
							value="<s:property value="#parameters.queryBusinessId" />">
					</div>

					<div class="apply-info-line-top">
						<span class="apply-span-name">数量：</span> <select
							class="select-max" id="num" name="num"
							onchange="checkradioApplyNet();">
							<option value="1" selected="selected">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
						</select>
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
						<%-- <select
							id="accountingType" class="select-min"
							onchange="changeAccountingTypeBefore(this);">
							<option value="year" >按年</option>
							<option value="month">按月</option>
							<option value="day">按天</option>
							<option value="hour"  selected="selected">按小时</option>
						</select>
 						--%>

							<%-- <select class="select-min yearOption" id="lengthTimeYear"
								onchange="changeAccountingType(this);">
								<option value="0_y" selected="selected">无期限</option>
								<option value="1_y">1年</option>
								<option value="2_y">2年</option>
								<option value="3_y">3年</option>
								<option value="4_y">4年</option>
								<option value="5_y">5年</option>
							</select>  --%>
							<%-- <select class="select-min monthOption" id="lengthTimeMonth"
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
							</select>  --%>
							<%-- <select class="select-min dayOption" id="lengthTimeDay"
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
							</select> --%>
							<%--  <select class="select-min hourOption" id="lengthTimeHour"
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
							</select> --%>
					</div>

					<div class="vm-apply-detail-l">
						<span class="apply-span-name">备注：</span> <input type="text"
							size="30" id="vmRemark" name="vmRemark" />
					</div>

					<hr class="apply-line" />
					<div>
						<h3 class="apply-info-title">
							<input id="ApplyNet" type="checkbox" value="1"
								onclick="checkradioApplyNet();" />
							<s:text name="申请网络" />
						</h3>
					</div>


					<table id="ethListTable1"
						style="width: 750px; height: 30px; border: 1px solid #d6d7d9; table-layout: fixed; display: none;"
						align="center">
						<thead>
							<tr>
								<td colspan="22"></td>
							</tr>
							<tr height="60px">
								<td align="left" colspan="18"><span style="margin-left:8px;">第一台虚拟机</span></td>
								<td colspan="2"></td>
								<td align="center" colspan="2"><span class="product-list-btn"><a
										href="javascript:addNet('ethList1')" id="add">添加</a></span></td>
							</tr>
						</thead>
						<tbody id="ethList1">
						</tbody>
					</table>
					<div></div>
					<table id="ethListTable2"
						style="width: 750px; height: 30px; border: 1px solid #d6d7d9; table-layout: fixed; display: none;"
						align="center">
						<thead>
							<tr>
								<td colspan="22"></td>
							</tr>
							<tr height="60px">
								<td align="left" colspan="18"><span style="margin-left:8px;">第二台虚拟机</span></td>
								<td colspan="2"></td>
								<td align="center" colspan="2"><span class="product-list-btn"><a
										href="javascript:addNet('ethList2')" id="add">添加</a></span></td>
							</tr>
						</thead>
						<tbody id="ethList2">
						</tbody>
					</table>
					<div></div>
					<table id="ethListTable3"
						style="width: 750px; height: 30px; border: 1px solid #d6d7d9; table-layout: fixed; display: none;"
						align="center">
						<thead>
							<tr>
								<td colspan="22"></td>
							</tr>
							<tr height="60px">
								<td align="left" colspan="18"><span style="margin-left:8px;">第三台虚拟机</span></td>
								<td colspan="2"></td>
								<td align="center" colspan="2"><span class="product-list-btn"><a
										href="javascript:addNet('ethList3')" id="add">添加</a></span></td>
							</tr>
						</thead>
						<tbody id="ethList3">
						</tbody>
					</table>
					<div></div>
					<table id="ethListTable4"
						style="width: 750px; height: 30px; border: 1px solid #d6d7d9; table-layout: fixed; display: none;"
						align="center">
						<thead>
							<tr>
								<td colspan="22"></td>
							</tr>
							<tr height="60px">
								<td align="left" colspan="18"><span style="margin-left:8px;">第四台虚拟机</span></td>
								<td colspan="2"></td>
								<td align="center" colspan="2"><span class="product-list-btn"><a
										href="javascript:addNet('ethList4')" id="add">添加</a></span></td>
							</tr>
						</thead>
						<tbody id="ethList4">
						</tbody>
					</table>
					<div></div>
					<table id="ethListTable5"
						style="width: 750px; height: 30px; border: 1px solid #d6d7d9; table-layout: fixed; display: none;"
						align="center">
						<thead>
							<tr>
								<td colspan="22"></td>
							</tr>
							<tr height="60px">
								<td align="left" colspan="18"><span style="margin-left:8px;">第五台虚拟机</span></td>
								<td colspan="2"></td>
								<td align="center" colspan="2"><span class="product-list-btn"><a
										href="javascript:addNet('ethList5')" id="add">添加</a></span></td>
							</tr>
						</thead>
						<tbody id="ethList5">
						</tbody>
					</table>
					<div></div>


				</div>
				<div class="detail-info apply-info-r">
					<div class="apply-head-r">
						<a>您选择的云服务器配置</a>
					</div>
					<div class="apply-head-r apply-detail-r-status">
						<div class="apply-detail-r-title">
							<span><LABEL id="itemName"></LABEL></span>
						</div>
						<div>
							<span class="vm-r-det-span-name">处理器:</span> <span
								class="apply-font-color"><LABEL id="cpuNum2"></LABEL>核</span>
						</div>
						<div>
							<span class="vm-r-det-span-name">内存:</span> <span
								class="apply-font-color"><LABEL id="ramSize2"></LABEL>G</span>
						</div>
						<div>
							<span class="vm-r-det-span-name">存储:</span> <span
								class="apply-font-color"><LABEL id="discSize2"></LABEL>G</span>
						</div>
						<div>
							<span class="vm-r-det-span-name">系统:</span> <span
								class="apply-font-color" id="osName2"></span>
						</div>
					</div>
					<div class="apply-head-r apply-detail-r">
						<div>
							<span class="apply-r-span-name">数量：</span> <span><font
								id="num2"></font>台</span>
						</div>
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
		</div>
	</s:form>
</div>


<s:include value="chargesList.jsp" />
<script type="text/javascript"
	src="../scripts/pagesjs/console/business/host/vm_apply_commend.js"></script>
