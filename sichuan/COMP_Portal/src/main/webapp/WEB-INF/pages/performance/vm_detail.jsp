<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="list">
	<div class="title">
	</div>
	<div class="details-con">
		<div class="detail-title blue">
			<div class="status">
			</div>
			<h2>
				<s:property value="vmInstanceInfo.vmName" />
			</h2>
		</div>
		<div class="detail-info">
			<table style="font-size:16px;">
				<tbody>
					<tr>
						<th>名称：</th>
						<td style="word-break: break-all"><span id="custom_name"><s:property
									value="vmInstanceInfo.vmName" /></span> </td>
					</tr>
					<tr>
						<th width="95">云主机ID：</th>
						<td><s:property value="vmInstanceInfo.vmId" /></td>
						<td><input id="vmId" type="hidden"
							value="<s:property value="vmInstanceInfo.vmId"/>" /> <input
							id="state" type="hidden"
							value="<s:property value="vmInstanceInfo.status"/>" /> <input
							id="vncUrl" type="hidden" value="<s:property value="vncUrl"/>" />
							<input id="vmName" type="hidden"
							value="<s:property value="vmInstanceInfo.vmName"/>" /> <input
							id="netList" type="hidden"
							value="<s:property value="vmInstanceInfo.netList"/>" /><input
							id="cpuNum" type="hidden"
							value="<s:property value="vmInstanceInfo.cpuNum"/>" /> <input
							id="ramSize" type="hidden"
							value="<s:property value="vmInstanceInfo.ramSize"/>" /> <input
							id="discSize" type="hidden"
							value="<s:property value="vmInstanceInfo.discSize"/>" /></td>
					</tr>

					<s:if test="vmInstanceInfo.paramFlag == 0">
						<tr>
							<th>条目名称：</th>
							<td><s:property value="vmInstanceInfo.itemName" /></td>
						</tr>
					</s:if>

					<tr>
						<th>处理器：</th>
						<td><s:property value="vmInstanceInfo.cpuNum" />核</td>
					</tr>
					<tr>
						<th>内存(RAM)：</th>
						<td><s:property value="vmInstanceInfo.ramSize" />G</td>
					</tr>
					<tr>
						<th>存储空间：</th>
						<td><s:property value="vmInstanceInfo.discSize" />TB</td>
					</tr>
					<tr>
						<th>系统：</th>
						<td><s:property value="vmInstanceInfo.isoName" /></td>
					</tr>
					<tr>
						<th>所属企业客户：</th>
						<td><s:property value="vmInstanceInfo.appName" /></td>
					</tr>
					<tr>
						<th>带宽：</th>
						<td><s:if test="vmInstanceInfo.bandWidth == 0">无限制</s:if> <s:elseif
								test="vmInstanceInfo.bandWidth !=null">
								<s:property value="vmInstanceInfo.bandWidth" />M </s:elseif></td>
					</tr>
					<tr>
						<th>公网IP：</th>
						<td><s:property value="vmInstanceInfo.networkIp" /></td>
					</tr>
					<tr>
						<th>备注：</th>
						<td style="word-break: break-all"><span id="remark"> <s:property
									value="vmInstanceInfo.description" />
						</span></td>
					</tr>
					<tr>
						<th>资源池：</th>
						<td><s:property value="vmInstanceInfo.resPoolName" /></td>
						<td><input id="resPoolId" type="hidden"
							value="<s:property value="vmInstanceInfo.resPoolId"/>" /></td>
					</tr>
					<tr>
						<th>分区：</th>
						<td><s:property value="vmInstanceInfo.resPoolPartName" /></td>
						<td><input id="resPoolPartId" type="hidden"
							value="<s:property value="vmInstanceInfo.resPoolPartId"/>" /></td>
					</tr>
					<tr>
						<th>BOSS订购实例号：</th>
						<td style="word-break: break-all"><span id="remark"> <s:property
									value="vmInstanceInfo.vmBossOrderId" />
							</span>
						</td>
					</tr>
				</tbody>
			</table>


			<div class="vnc-login" style="font-size:16px;">
				<s:if test="vmInstanceInfo.vmPassword!=null">
					<span class="pwd-title"> <s:property
							value="vmInstanceInfo.userName" /> <b>帐户初始登录密码：</b>
					</span>
				</s:if>
				<%--密码最多15字符--%>
				<span class="pwd"> <s:property
						value="vmInstanceInfo.vmPassword" />
				</span>
				<!-- <a class="vnc" href="javascript:loginHost();">登录主机</a>  -->
			</div>
		</div>

	</div>
</div>



<style>
.list div.title {
    box-sizing: border-box;
    padding-top: 10px;
    position: relative;
    margin-bottom: 40px;
}
</style>