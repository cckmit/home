<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="list">
	<div class="title">
		<h2>预订购云硬盘详情</h2>
	</div>
	<div class="btns">
		<s:if test="preApplyDiskInfo.status == 0">
			<a class="update" href="javascript:void(0);" onclick="goToUpdateStatusPage();"><img src="../img/ico-update.png" alt="" />修改订购状态</a>
		</s:if>
	</div>
	<div class="details-con">
		<div class="detail-title blue">
			<div class="status">
				<span id="status">
					<s:if test="preApplyDiskInfo.status == 0">待订购</s:if>
					<s:else>已订购</s:else>
				</span>
			</div>
			<h2>
				<s:property value="preApplyDiskInfo.ebsName" />
			</h2>
		</div>
		<div class="detail-info">
			<table>
				<tbody>
					<tr>
						<th>名称：</th>
						<td style="word-break: break-all"><span id="customName"><s:property
									value="preApplyDiskInfo.ebsName" /></span> 
									<s:hidden id="caseId" name="caseId" />
						</td>
					</tr>
					<tr>
						<th>所属企业客户：</th>
						<td id="appName"><s:property value="preApplyDiskInfo.appName" />
						</td>
					</tr>
					<tr>
						<th>云硬盘容量：</th>
						<td><s:property value="preApplyDiskInfo.diskSize" />G</td>
					</tr>
					<tr>
						<th>计费方式：</th>
						<td>
							<s:if test='preApplyDiskInfo.chargeType == "h"'>按时计费</s:if>
							<s:else>包月计费</s:else>
						</td>
					</tr>
					<tr>
						<th>计费时长：</th>
						<td>
							<s:if test='preApplyDiskInfo.chargeType == "h"'><s:property value="preApplyDiskInfo.chargeTime" />小时</s:if>
							<s:else><s:property value="preApplyDiskInfo.chargeTime" />月</s:else>
						</td>
					</tr>
					<tr>
						<th>订购数量：</th>
						<td><s:property value="preApplyDiskInfo.num" /></td>
					</tr>
					
					<tr>
						<th>备注：</th>
						<td> <s:property value="preApplyDiskInfo.description" />
						</td>
					</tr>
					<tr>
						<th>资源池：</th>
						<td><s:property value="preApplyDiskInfo.resPoolName" /></td>
						<td><input id="resPoolId" type="hidden" value="<s:property value="preApplyDiskInfo"/>" /></td>
					</tr>
					<tr>
						<th>分区：</th>
						<td><s:property value="preApplyDiskInfo.resPoolPartName" /></td>
						<td><input id="resPoolPartId" type="hidden"
							value="<s:property value="preApplyDiskInfo.resPoolPartId"/>" /></td>
					</tr>
				</tbody>
			</table>

		</div>

		<div class="time">
			<span class="time-begin">创建时间：<i><s:property
						value="preApplyDiskInfo.createTime" /></i></span>
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
			src="../scripts/pagesjs/console/disk/ebs_pre_apply_detail.js"></script>


