<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="list">
	<div class="title">
		<h2>数据备份详情</h2>
	</div>
	<div class="btns">
		<a class="apply" href="ebsApplyAction.action"><img
			src="../img/ico-apply.png" alt="" />申请数据备份</a>
	</div>
	<div class="details-con">
		<div id="title-status" class="detail-title blue">
			<div class="status">
				<span id="status"></span>
			</div>
			<h2>
				<s:property value="diskInfo.diskName" />
			</h2>
			<img id="operation" class="operation" src="../img/ico-setting.png" />
			<ul id="ebsopt" class="opts">
				<li id="delButton"><a class="del" href="javascript:delHost();"><img
						src="../img/ico-opt-del.png" alt="" /></a></li>

			</ul>
		</div>
		<div class="detail-info">
			<table>
				<tbody>
					<tr>
						<th>名称：</th>
						<td style="word-break: break-all"><span id="customName"><s:property
									value="diskInfo.diskName" /></span> <a
							href="javascript:upCustomName();"><img width="25px"
								height="25px" src="../img/ico-opts-update.png" /></a></td>
					</tr>
					<tr>
						<th>所属业务：</th>
						<td id="appName"><s:property value="diskInfo.appName" /> <input
							type="hidden" id="businessId"
							value='<s:property value="diskInfo.businessId" />'></td>
					</tr>
					<tr>
						<th>云硬盘容量：</th>
						<td><s:property value="diskInfo.diskSize" />G</td>
					</tr>
					<tr>
						<th>云硬盘类型：</th>
						<td id="resourceType"><s:if test="diskInfo.resourceType == 1">云硬盘</s:if>
							<s:elseif test="diskInfo.resourceType == 2">物理硬盘</s:elseif></td>
					</tr>
					<tr>
						<th>时长(<s:if test="diskInfo.timeUnit==\"h\"">小时</s:if> <s:elseif
								test="diskInfo.timeUnit==\"d\"">天</s:elseif> <s:elseif
								test="diskInfo.timeUnit==\"m\"">月</s:elseif> <s:else>年</s:else>)：
						</th>
						<td><s:property value="diskInfo.diskLength" /></td>
					</tr>

					<tr>
						<th>挂载主机：</th>
						<td class="unMountDisk" style="display: none;"><s:property
								value="diskInfo.mountVmName" /></span> <input type="hidden"
							value="<s:property value="diskInfo.hostId"/>" id="hostId"><a class="btn"
							href="javascript:unMountDisk();">卸载</a></td>
						<td class="mountDisk" style="display: none;"><a class="btn"
							href="javascript:mountDisk();">挂载</a></td>
					</tr>

					<input type="hidden" id="statusHidden"
						value="<s:property value='diskInfo.diskStatus' />" />
					<input id="diskSize" type="hidden"
						value="<s:property value="diskInfo.diskSize"/>" />
					<input id="diskId" type="hidden"
						value="<s:property value="diskInfo.diskId"/>" />
					<input id="resourcePoolId" type="hidden"
						value="<s:property value="diskInfo.resourcePoolId"/>" />
					<input id="resourcePoolPartId" type="hidden"
						value="<s:property value="diskInfo.resourcePoolPartId"/>" />
				</tbody>
			</table>

		</div>

		<div class="time">
			<span class="time-begin">创建时间：<i><s:property
						value="diskInfo.createTime" /></i></span><span class="time-end">到期时间：<i><s:property
						value="diskInfo.expireTime" /></i></span>
		</div>


		<div id="mountDisk" style="display: none;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr><td><select class="select-max" id="diskSeachKey">
						<option value="vmName">设备名称</option>
						<option value="vmId">ID</option>
					</select></td>
					<td><input type="text" class="float-input-long" id="diskSeachVal" /></td>
					<td><a class="search btn " href="javascript:seachDisk();">查询</a></td></tr>
				</thead>
				<colgroup>
					<col style="width: 10%;">
					<col style="width: 50%;">
					<col style="width: 40%;">
				</colgroup>
				<tbody id="vmList"></tbody>
			</table>
		</div>


		<div id="upCustomName" class="float-div" style="display: none;">
			新名称：<input type="text" name="modify_custom_name"
				value="<s:property value="diskInfo.diskName" />" />
			<!-- 磁盘大小： -->
			<input type="text" name="disk_size" style="display: none;"
				value="<s:property value="diskInfo.diskSize"/>" />
			<!-- G -->
		</div>
		<script type="text/javascript"
			src="../scripts/pagesjs/console/disk/ebs_detail.js"></script>


	</div>