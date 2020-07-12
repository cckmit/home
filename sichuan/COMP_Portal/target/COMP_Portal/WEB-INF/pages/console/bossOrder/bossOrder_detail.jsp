<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="list">
	<div class="title">
		<h2>BOSS订单详情</h2>
	</div>
	<div class="btns">
		<s:if test="bossOrderInfo.status == 0">
			<a class="update" href="javascript:void(0);" onclick="goToUpdateStatusPage();"><img src="../img/ico-update.png" alt="" />修改操作状态</a>
		</s:if>
	</div>
	<div class="details-con">
		<div class="detail-title blue">
			<div class="status">
				<span id="status">
					<s:if test="bossOrderInfo.status == 0">未操作</s:if>
					<s:else>已操作</s:else>
				</span>
			</div>
			<h2>
				<s:property value="bossOrderInfo.bossOrderId" />
				<s:hidden id="bossOrderId" name="bossOrderId" />
			</h2>
		</div>
		<div class="detail-info">
			<table>
				<tbody>
					<tr>
						<th>所属企业客户：</th>
						<td><s:property value="bossOrderInfo.appName" /></td>
					</tr>
					<tr>
						<th>操作类型：</th>
						<td style="word-break: break-all"><span id="custom_name">
							<s:if test="bossOrderInfo.operateType == 0">订购</s:if>
							<s:elseif test="bossOrderInfo.operateType == 1">修改</s:elseif>
							<s:elseif test="bossOrderInfo.operateType == 2">退订</s:elseif>
							<s:elseif test="bossOrderInfo.operateType == 3">暂停</s:elseif>
							<s:elseif test="bossOrderInfo.operateType == 4">恢复</s:elseif>
							</span>
						</td>
					</tr>
					<tr>
						<th>产品名称：</th>
						<td><s:property value="bossOrderInfo.productName" /></td>
					</tr>
					<s:if test="bossOrderInfo.productCode != ''">
					<tr>
						<th>产品规格编码：</th>
						<td><s:property value="bossOrderInfo.productCode" /></td>
					</tr>
					</s:if>
					<s:if test="bossOrderInfo.productItem != ''">
					<tr>
						<th>产品规格：</th>
						<td><s:property value="bossOrderInfo.productItem" /></td>
					</tr>
					</s:if>
					<s:if test="bossOrderInfo.operateType == 4">
					<tr>
						<th>信控原因：</th>
						<td style="word-break: break-all"><span id="custom_name">
							<s:property value="bossOrderInfo.reasonType" />
							</span>
						</td>
					</tr>
					</s:if>
					<tr>
						<th>协议签订时间：</th>
						<td><s:property value="bossOrderInfo.agreementBeginTime" /></td>
					</tr>
					<tr>
						<th>协议结束时间：</th>
						<td><s:property value="bossOrderInfo.agreementEndTime" /></td>
					</tr>
					<tr>
						<th>计费周期：</th>
						<td><s:if test="bossOrderInfo.chargeType == 00">一次性收费</s:if><s:elseif test="bossOrderInfo.chargeType == 99">每月收费</s:elseif></td>
					</tr>
					<tr>
						<th>折扣率：</th>
						<td>
							<s:property value="bossOrderInfo.race" />
						</td>
					</tr>
					<tr>
						<th>单价：</th>
						<td>
							<s:property value="bossOrderInfo.price" />元
						</td>
					</tr>
					<tr>
						<th>付款方式：</th>
						<td>
							<s:if test='bossOrderInfo.paymentType == "0"'>相同</s:if>
							<s:else>不同</s:else>
							(付款方式是否与计费周期相同)
						</td>
					</tr>
					<tr>
						<th>计费生效时间：</th>
						<td>
							<s:property value="bossOrderInfo.billBgTime" />元
						</td>
					</tr>
					<tr>
						<th>计费结束时间：</th>
						<td>
							<s:property value="bossOrderInfo.billEndTime" />元
						</td>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="time">
			<span class="time-begin">时间戳：<i><s:property
						value="bossOrderInfo.timeStamp" /></i></span>
		</div>
	</div>
</div>


<div id="modify_apply_status_div" class="float-div"
	style="display: none;">
	<table>
		<tr>
			<td valign="top">已操作请点击确定！</td>
		</tr>
	</table>
</div>

<script type="text/javascript"
	src="../scripts/pagesjs/console/bossOrder/bossOrder_detail.js"></script>

<script>
	function s(e, a) {
		if (e && e.preventDefault)
			e.preventDefault();
		else
			window.event.returnValue = false;
		a.focus();

	}

	
</script>