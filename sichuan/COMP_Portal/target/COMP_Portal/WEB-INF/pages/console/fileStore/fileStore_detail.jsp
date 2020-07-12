<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="list">
	<div class="title">
		<h2>分布式文件存储详情</h2>
	</div>
	<div class="btns">
		<a class="apply" href="fileStoreApplyAction.action"><img
			src="../img/ico-apply.png" alt="" />申请文件存储</a>
	</div>
	<div class="details-con">
		<div id="title-status"
			class="detail-title <s:if test="fs.status==0">blue</s:if>
		        			<s:elseif test="fs.status==1">green</s:elseif>
		        			<s:elseif test="fs.status==3||fs.status==2">red</s:elseif>
		        			<s:elseif test="fs.status==4">orange</s:elseif> ">
			<div class="status">
				<span id="status"> <s:if test='fs.status==0'>
									待创建
								</s:if> <s:elseif test='fs.status==1'>
									已创建
								</s:elseif> <s:elseif test='fs.status==2'>
									创建失败
								</s:elseif> <s:elseif test='fs.status==3'>
									状态异常
								</s:elseif> <s:elseif test='fs.status==4'>
									已删除
								</s:elseif>
				</span>
			</div>
			<h2>
				<s:property value="fs.fsName" />
				<input id="fsId" type="hidden"
					value="<s:property value="fs.fsId" />" />
			</h2>
			<img id="operation" class="operation" src="../img/ico-setting.png" />
			<ul id="ipopt" class="opts">
				<li id="delButton"><a class="del" href="javascript:delFs();"><img
						src="../img/ico-opt-del.png" alt="" /></a></li>

			</ul>
		</div>

		<div class="detail-info">
			<table>
				<tbody>
					<tr>
						<th>所属企业客户：</th>
						<td id="appName"><s:property value="fs.appName" /></td>
					</tr>

					<tr>
						<th>所属资源池：</th>
						<td id="appName"><s:property value="fs.resPoolName" /></td>
					</tr>
					<tr>
						<th>所属分区：</th>
						<td id="appName"><s:property value="fs.resPoolPartName" /></td>
					</tr>
					<tr>
						<th>配额大小：</th>
						<td id="fsQuotaSize"><s:property value="fs.fsQuotaSize" />
							GB</td>
					</tr>
					<tr>
						<th>存储共享方式：</th>
						<td id="appName"><s:if test="0">NFS</s:if>
							<s:elseif test="1">CIFS</s:elseif>
							<s:elseif test="3">POSIX</s:elseif></td>
					</tr>
					<tr>
						<th>描述：</th>
						<td id="description"><s:property value="fs.description" /></td>
					</tr>

				</tbody>
			</table>
			<input type="hidden" id="resourcePool"
				value="<s:property value='fs.resPoolId'/>"> <input
				type="hidden" id="resourcePoolPart"
				value="<s:property value='fs.resPoolPartId'/>">
				<input id="appId" type="hidden" value="<s:property value='fs.appId'/>" />
			<div class="vnc-login">
				<s:if test="fs.fsAdminPassword!=null">
					<span class="pwd-title"> <s:property value="fs.fsAdminUser" />
						帐户初始登录密码:
					</span>
				</s:if>
				<%--密码最多15字符--%>
				<span class="pwd"> <s:property value="fs.fsAdminPassword" />
				</span>
			</div>
		</div>

		<div class="time">
			<span class="time-begin">创建时间：
			        <s:if test="fs.status==0"><i></i></s:if>
					<s:else><i><s:property value="fs.createTime" /></i></s:else>
			</span>						
						<span class="time-end">更新时间：<i><s:property
						value="fs.updateTime" /></i></span>
		</div>
	</div>
</div>

<script type="text/javascript"
	src="../scripts/pagesjs/console/fileStore/fileStore_detail.js"></script>