<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="list">
	<div class="title">
		<h2>云主机详情</h2>
	</div>
	<div class="btns">
		<a class="apply" href="javascript:void(0);" onclick="goToApplyPage();"><img
			src="../img/ico-apply.png" alt="" />申请云主机</a> <a class="update"
			href="javascript:void(0);" onclick="goToUpdatePage();"><img
			src="../img/ico-update.png" alt="" />修改云主机</a>
	</div>
	<div class="details-con">
		<div class="detail-title blue">
			<div class="status">
				<span id="status"><s:if
						test="vmInstanceInfo.vmId == null || vmInstanceInfo.vmId == ''">待创建</s:if>
					<s:else>加载中
					</s:else></span>
			</div>
			<h2>
				<s:property value="vmInstanceInfo.vmName" />
			</h2>
			<img id="operation" class="operation" src="../img/ico-setting.png" />
			<ul id="vmopt" class="opts">
				<li><a class="restart" href="javascript:rebootHost();"><img
						src="../img/ico-opt-restart.png" alt="" /></a></li>
				<li><a class="off" href="javascript:pauseHost();"><img
						src="../img/ico-opt-off.png" alt="" /></a></li>
				<li><a class="stop" href="javascript:stopHost();"><img
						src="../img/ico-opt-stop.png" alt="" /></a></li>
				<li><a class="del" href="javascript:delHost();"><img
						src="../img/ico-opt-del.png" alt="" /></a></li>
				<li><a class="shot" href="javascript:snapHost();"><img
						src="../img/ico-opt-shot.png" alt="" /></a></li>
			</ul>
		</div>
		<div class="detail-info">
			<table>
				<tbody>
					<tr>
						<th>名称：</th>
						<td style="word-break: break-all"><span id="custom_name"><s:property
									value="vmInstanceInfo.vmName" /></span> <a
							href="javascript:modifyCustomName();"><img width="25px"
								height="25px" src="../img/ico-opts-update.png" /></a></td>
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
						</span> <a href="javascript:modifyRemark();"><img width="25px"
								height="25px" src="../img/ico-opts-update.png" /></a></td>
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
					<tr id="timers">
						<th>进度：</th>
						<td><progress id="progress" max="100" value="0"></progress></td>
						<td>&nbsp;&nbsp;</td>
						<td><span id="progressText"></span></td>
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


			<div class="vnc-login">
				<s:if test="vmInstanceInfo.vmPassword!=null">
					<span class="pwd-title"> <s:property
							value="vmInstanceInfo.userName" /> 帐户初始登录密码:
					</span>
				</s:if>
				<%--密码最多15字符--%>
				<span class="pwd"> <s:property
						value="vmInstanceInfo.vmPassword" />
				</span>
				<!-- <a class="vnc" href="javascript:loginHost();">登录主机</a>  -->
			</div>
		</div>

		<div class="detail-tab">
			<div class="tab">
				<a class="active" href="javascript:void(0);"
					data-tab="#watch_tab_info">运行监控</a> <a href="javascript:void(0);"
					data-tab="#net_tab_info">网卡信息</a> <a href="javascript:void(0);"
					data-tab="#detail_tab_info">云硬盘</a> <a href="javascript:void(0);"
					data-tab="#snapshot_tab_info">快照信息</a>
			</div>
			<div class="detail-center">
				<!-- 运行监控 -->
				<div id="watch_tab_info" class="table-content">
					<iframe style="border: 0;" width="100%" height="364px"
						src="hostRealTimePerformance.action?deviceId=<s:property value="vmInstanceInfo.vmId" />&deviceName=<s:property value="vmInstanceInfo.vmName" />&deviceType=VM&staType=CPU"></iframe>
				</div>

				<!--网卡列表开始-->
				<div id="net_tab_info" class="none">
					<table>
						<thead>
							<tr>
								<th>网卡</th>
								<th>vlan</th>
								<th>IP段</th>
								<th>IP</th>
								<th>网关</th>
								<th>IP类型</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="vmInstanceInfo.netList">
								<tr>
									<td><s:property value='eth' /></td>
									<td><s:property value='vlanName' /></td>
									<td><s:property value='startIp' />&nbsp;~&nbsp;<s:property
											value='endIp' /></td>
									<td><s:property value='ip' /></td>
									<td><s:property value='gateway' /></td>
									<td><s:if test="ipType == 0">IPV4</s:if><s:else>IPV6</s:else></td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>

				<!--云硬盘列表开始-->
				<div id="detail_tab_info" class="none">
					<div class="table-content hasBtn">
						<table>
							<thead>
								<tr>
									<th>名称</th>
									<th>ID</th>
									<th>容量</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody id="diskList">
							<s:iterator value="vmInstanceInfo">
								<tr>
									<td><s:property value='vmName' /></td>
									<td><s:property value='vmId' /></td>
									<td><s:property value='discSize' />TB</td>
									<td><s:property value='' /></td>
								</tr>
							</s:iterator>
							</tbody>
						</table>
					</div>
					<div id="attach" class="span-btn fr mgt30"> <a class="btn"
						href="javascript: mountDisk('<s:property value="vmInstanceInfo.appId" />');">挂载云硬盘</a></div>
				</div>


				<!--虚拟机快照列表开始-->
				<div id="snapshot_tab_info" class="none">
					<div class="table-content hasBtn">
						<table>
							<thead>
								<tr>
									<th>快照名称</th>
									<th>创建时间</th>
									<th>状态</th>
									<th>备注</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody id="listTspan">
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="time">
			<span class="time-begin">创建时间：<i><s:property
						value="vmInstanceInfo.createTime" /></i></span><span class="time-end">到期时间：<i><s:property
						value="vmInstanceInfo.expireTime" /></i></span>
		</div>
	</div>
</div>







<%--登录主机 --%>
<div id="loginHost" class="float-div" style="display: none;">
	<div class="float-div-center">
		<div class="login-host">
			<div>
				<h3>一、标准SSH登录</h3>
			</div>
			<div>1、启动SSH工具。例如：Putty；</div>
			<div>2、输入主机IP，及端口号：22；</div>
			<div>3、打开连接后，输入用户名及密码，即可登录主机。</div>
			<div>
				<ul class="title-btn">
					<li class="select"><a href="javascript:loginClose();">下载
							Putty</a></li>
				</ul>
			</div>
		</div>
		<div class="login-host login-host-r">
			<div>
				<h3>二、浏览器Java Applet 登录</h3>
			</div>
			<div>1、点击“在线登录主机”；</div>
			<div>2、按提示输入用户名及密码，即可登录主机。</div>
			<div>&nbsp;</div>
			<div>
				<ul class="title-btn">
					<li class="select"><a href="javascript:loginClose();">在线登录主机</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>
<!-- 云硬盘列表 -->
<div id="mountDisk" class="float-div"
	style="display: none; width: 500px;">
	<div class="toolbar">
		<select id="diskSeachKey" style="width: 100px; height: 30px; font-size: 18px; background-color: #009DD9; color: #fff; margin-right: 20px;">
			<option value="diskName">名称</option>
			<option value="diskId">ID</option>
			<option value="diskSize">容量</option>
		</select> <input id="diskSeachVal" type="text" class="float-input-long" />
	<a class="search btn fr" href="javascript:seachDisk('<s:property value="vmInstanceInfo.appId" />');">查询</a>
		
	</div>
	<div class="float-div-center">
		<div class="table-content">
			<table class="table" width="100%" border="0" cellspacing="0" cellpadding="0">
				<tbody id="mountDiskList">
					<tr>
						<th class="nl"></th>
						<th>名称</th>
						<th>ID</th>
						<th>容量</th>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
<%-- 修改备注 --%>
<div id="modify_remark_div" class="float-div" style="display: none;">
	<table>
		<tr>
			<td valign="top">备注：</td>
			<td><textarea name="modify_remark" cols="30" rows="3"></textarea>
			</td>
		</tr>
	</table>
</div>
<%-- 修改虚拟机名称  --%>
<div id="modify_custom_name_div" class="float-div"
	style="display: none;">
	<table>
		<tr>
			<td valign="top">名称：</td>
			<td><input type="text" size="28" value=""
				name="modify_custom_name" /></td>
		</tr>
	</table>
</div>
<%-- 创建虚拟机快照  --%>
<div id="spanCreate" class="float-div" style="display: none;">
	<table>
		<tr>
			<td valign="top">名称：</td>
			<td><input type="text" size="28" value="" name="snapshot_name"
				id="snapshot_name" /></td>
		</tr>
		<tr>
			<td valign="top">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<td valign="top">备注：</td>
			<td><textarea style="width: 183px; height: 138px;"
					onmousedown="s(event,this)" name="snapshot_desc" id="snapshot_desc">
				</textarea></td>
		</tr>
	</table>
</div>

<!-- 修改页面 -->
<div class="page-form" id="relate_resource_div" style="display: none;">
	<table>
		<tr>
			<td>云主机ID：</td>
			<td id="vm_id"><s:property value="vmInstanceInfo.vmId" /> <input
				id="appId" type="hidden"
				value="<s:property value="vmInstanceInfo.appId"/>" /></td>
		</tr>
		<tr>
			<td>处理器(核)：</td>
			<td><input type="text" size="28" id="cpuNumNew" maxlength="10"
				value="<s:property value="vmInstanceInfo.cpuNum" />" /></td>
		</tr>
		<tr>
			<td>内存(RAM)：</td>
			<td><input type="text" size="28" id="ramSizeNew" maxlength="10"
				value="<s:property value="vmInstanceInfo.ramSize" />" /><select
				class="select-min" style="float: none;" id="ramSizeUnit">
					<option value="GB">GB</option>
					<option value="MB">MB</option>
			</select></td>
		</tr>
		<tr>
			<td>存储空间(TB)：</td>
			<td><input type="text" size="28" id="discSizeNew" maxlength="10"
				value="<s:property value="vmInstanceInfo.discSize" />" /></td>
		</tr>
		<tr>
			<td>网卡信息：</td>
			<td><a class="btn fr " href="javascript:addNet()" id="add">添加</a></td>
		</tr>
	</table>

	<table class="table" style="width: 1200px;">
		<thead>
			<tr>
				<th>网卡</th>
				<th>VLAN</th>
				<th>IP段</th>
				<th>IP</th>
				<th>网关</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="ethList">
			<s:iterator value="vmInstanceInfo.netList">
				<tr>
					<td id="<s:property value='eth' />"><s:property value='eth' /></td>
					<td><s:property value='vlanName' /><input id="vlanId"
						type="hidden" value="<s:property value="vlanId"/>" /></td>
					<td><s:property value='startIp' />&nbsp;~&nbsp;<s:property
							value='endIp' /><input id="ipSegmentId" type="hidden"
						value="<s:property value="ipSegmentId"/>" /></td>
					<td><s:property value='ip' /></td>
					<td><s:property value='gateway' /></td>
					<td><span class="product-list-btn"><a class="text"
							href="javascript:editNet('<s:property value='eth' />')" id="edit">修改</a></span></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>

</div>
<script type="text/javascript"
	src="../scripts/pagesjs/console/host/vm_detail.js"></script>

<script>
	function s(e, a) {
		if (e && e.preventDefault)
			e.preventDefault();
		else
			window.event.returnValue = false;
		a.focus();

	}

	
</script>