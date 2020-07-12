<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="list">
	<div class="title">
		<h2>预订购云主机详情</h2>
	</div>
	<div class="btns">
		<s:if test="vmPreInstanceInfo.status == 0">
			<a class="update" href="javascript:void(0);" onclick="goToUpdateStatusPage();"><img src="../img/ico-update.png" alt="" />修改订购状态</a>
		</s:if>
	</div>
	<div class="details-con">
		<div class="detail-title blue">
			<div class="status">
				<span id="status">
					<s:if test="vmPreInstanceInfo.status == 0">待订购</s:if>
					<s:else>已订购</s:else>
				</span>
			</div>
			<h2>
				<s:property value="vmPreInstanceInfo.vmName" />
			</h2>
		</div>
		<div class="detail-info">
			<table>
				<tbody>
					<tr>
						<th>名称：</th>
						<td style="word-break: break-all"><span id="custom_name">
							<s:property value="vmPreInstanceInfo.vmName" /></span>
							<s:hidden id="caseId" name="caseId" />
						</td>
					</tr>

					<tr>
						<th>处理器：</th>
						<td><s:property value="vmPreInstanceInfo.cpuNum" />核</td>
					</tr>
					<tr>
						<th>内存(RAM)：</th>
						<td><s:property value="vmPreInstanceInfo.ramSize" />G</td>
					</tr>
					<tr>
						<th>存储空间：</th>
						<td><s:property value="vmPreInstanceInfo.discSize" />G</td>
					</tr>
					<tr>
						<th>系统：</th>
						<td><s:property value="vmPreInstanceInfo.isoName" /></td>
					</tr>
					<tr>
						<th>计费方式：</th>
						<td>
							<s:if test='vmPreInstanceInfo.chargeType == "h"'>按时计费</s:if>
							<s:else>包月计费</s:else>
						</td>
					</tr>
					<tr>
						<th>计费时长：</th>
						<td>
							<s:if test='vmPreInstanceInfo.chargeType == "h"'><s:property value="vmPreInstanceInfo.chargeTime" />小时</s:if>
							<s:else><s:property value="vmPreInstanceInfo.chargeTime" />月</s:else>
						</td>
					</tr>
					<tr>
						<th>订购数量：</th>
						<td><s:property value="vmPreInstanceInfo.num" /></td>
					</tr>
					<tr>
						<th>所属企业客户：</th>
						<td><s:property value="vmPreInstanceInfo.appName" /></td>
					</tr>
					<tr>
						<th>备注：</th>
						<td> <s:property value="vmPreInstanceInfo.description" />
						</td>
					</tr>
					<tr>
						<th>资源池：</th>
						<td><s:property value="vmPreInstanceInfo.resPoolName" /></td>
						<td><input id="resPoolId" type="hidden" value="<s:property value="vmPreInstanceInfo.resPoolId"/>" /></td>
					</tr>
					<tr>
						<th>分区：</th>
						<td><s:property value="vmPreInstanceInfo.resPoolPartName" /></td>
						<td><input id="resPoolPartId" type="hidden"
							value="<s:property value="vmPreInstanceInfo.resPoolPartId"/>" /></td>
					</tr>
					<tr>
						<th>IP类型：</th>
						<td><s:if test="ipType == 0">IPV4</s:if><s:else>IPV6</s:else></td>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="time">
			<span class="time-begin">创建时间：<i><s:property
						value="vmPreInstanceInfo.createTime" /></i></span>
		</div>
	</div>
</div>


<div id="modify_apply_status_div" class="float-div"
	style="display: none;">
	<table>
		<tr>
			<td valign="top">已订购请点击确定！</td>
		</tr>
	</table>
</div>

<script type="text/javascript"
	src="../scripts/pagesjs/console/host/vm_pre_apply_detail.js"></script>

<script>
	function s(e, a) {
		if (e && e.preventDefault)
			e.preventDefault();
		else
			window.event.returnValue = false;
		a.focus();

	}

	
</script>