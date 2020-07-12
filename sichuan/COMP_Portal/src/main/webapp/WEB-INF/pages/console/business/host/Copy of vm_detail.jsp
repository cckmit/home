<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>虚拟机控制台</h1>
<div id="message" style="display: none;">
	<span id="error" class="error"><s:actionerror /></span> <span
		class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
</div>
<span class="apply"> 
<s:url id="addUrl" action="vmApply">
	<s:param name="nodeType" value="nodeType" />
	<s:param name="nodeId" value="nodeId"/>
    <s:param name="treeNodeName" value="treeNodeName"/>
    <s:param name="pnodeId" value="pnodeId"/>
    <s:param name="pnodeName" value="pnodeName"/>
    <s:param name="appId" value="appId"/>
    <s:param name="curFun" value="curFun"/>
	<s:param name="queryBusinessId"><s:property value="#parameters.queryBusinessId" /></s:param>
</s:url>
<a href="${addUrl}">申请虚拟机</a>
</span>
<span class="apply"> <a href="#" onclick="goToUpdatePage();">修改虚拟机</a>
</span>
<!-- 主机详情 -->
<div class="details-con">
	<div class="detail-title">
		<a href="#" title="返回" onclick="goToVMListPage()"> <span
			class="back"></span>
		</a>
		<h2>
			<s:property value="vmInstanceInfo.vmName" />
		</h2>
		<span id="status" class="status mt s-blue">
			<s:if test="vmInstanceInfo.vmId == null || vmInstanceInfo.vmId == ''">待创建</s:if>
			<s:else>加载中</s:else>
		</span>
		<div class="operation" title="主机操作">
			<!-- 主机操作 -->
			<ul id="vmopt"></ul>
		</div>
	</div>
	<div class="detail-info">
		<h3>
			<%-- 使用模板创建时显示条目名称 --%>
			<s:if test="vmInstanceInfo.paramFlag == 0">
				<s:property value="vmInstanceInfo.itemName" />
			</s:if>
		</h3>
		<div class="detail-info-tb">
			<table width="100%" border="0">
				<tr>
					<th>名称：</th>
					<td style="word-break: break-all"><span id="custom_name"><s:property
								value="vmInstanceInfo.vmName" /></span>&nbsp;&nbsp;<span
						class="span-btn" style="float: right;"><a
							href="javascript:modifyCustomName();">修改名称</a></span></td>
				</tr>
				<tr>
					<th width="95">虚拟机ID：</th>
					<td><s:property value="vmInstanceInfo.vmId" /></td>
					<td><input id="vmId" type="hidden"
						value="<s:property value="vmInstanceInfo.vmId"/>" /> <input
						id="state" type="hidden"
						value="<s:property value="vmInstanceInfo.status"/>" /> <input
						id="vncUrl" type="hidden" value="<s:property value="vncUrl"/>" />
						<input id="vmName" type="hidden"
						value="<s:property value="vmInstanceInfo.vmName"/>" /></td>
				</tr>
				<tr>
					<input id="cpuNum" type="hidden"
						value="<s:property value="vmInstanceInfo.cpuNum"/>" />
					<input id="ramSize" type="hidden"
						value="<s:property value="vmInstanceInfo.ramSize"/>" />
					<input id="discSize" type="hidden"
						value="<s:property value="vmInstanceInfo.discSize"/>" />
					<th width="95">处理器：</th>
					<td><s:property value="vmInstanceInfo.cpuNum" />核</td>
				</tr>
				<tr>
					<th>内存(RAM)：</th>
					<td><s:property value="vmInstanceInfo.ramSize" />G</td>
				</tr>
				<tr>
					<th>存储空间：</th>
					<td><s:property value="vmInstanceInfo.discSize" />G</td>
				</tr>
				<tr>
					<th>系统：</th>
					<td><s:property value="vmInstanceInfo.isoName" /></td>
				</tr>
				<tr>
					<th>所属业务：</th>
					<td><s:property value="vmInstanceInfo.appName" /></td>
				</tr>
				<tr>
					<th>带宽：</th>
					<td><s:if test="vmInstanceInfo.bandWidth == 0">无限制</s:if>
					<s:else><s:property value="vmInstanceInfo.bandWidth" />M </s:else></td>
				</tr>
				<tr>
					<th>公网IP：</th>
					<td><s:property value="vmInstanceInfo.networkIp" /></td>
				</tr>
				<tr>
					<th>备注：</th>
					<td style="word-break: break-all"><span id="remark"> <s:property
								value="vmInstanceInfo.description" />
					</span>&nbsp;&nbsp; <span class="span-btn" style="float: right;"> <a
							href="javascript:modifyRemark();">修改备注</a>
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
			</table>
			<table width="100%" border="0">
				<tr id="timers"  style="display:none;">
					<th>进度：</th>
					<td><progress id="progress" max="100" value="0"></progress></td>
					<td>&nbsp;&nbsp;</td>
					<td><span id="progressText"></span></td>
				</tr>
			</table>
		</div>
		<div class="detail-info-tb">
			<div class="pwd-title"><s:property value="vmInstanceInfo.userName" />帐户初始登录密码:</div>
			<%--密码最多15字符--%>
			<div class="pwd">
				<s:property value="vmInstanceInfo.vmPassword" />
			</div>
			<div >
				<!--<a href="javascript:loginHost();">登录主机</a>  -->
			</div>
		</div>
		<div id="detail_tab">
			<ul class="detail_tab">
				<%-- <li class="selected"><a href="#watch_tab_info">运行监控</a></li>--%>

				<li class="selected"><a href="#watch_tab_info">运行监控</a></li>
				<li><a href="#net_tab_info">网卡信息</a></li>
				<li><a href="#detail_tab_info">云硬盘</a></li>
				<li><a href="#backup_tab_info">备份信息</a></li>
				<li><a href="#snapshot_tab_info">快照信息</a></li>
			</ul>
			<div class="detail-center">

				<!--监控折线图开始-->
				<div id="watch_tab_info" class="table-content">
					<iframe style="border: 0;" width="100%" height="405px"
						src="../console/hostRealTimePerformance.action?deviceId=<s:property value="vmInstanceInfo.vmId" />&deviceName=<s:property value="vmInstanceInfo.vmName" />&deviceType=VM&staType=CPU"></iframe>
				</div>
				<!--监控折线图结束-->

				<!--网卡列表开始-->
				<div id="net_tab_info">
					<div class="table-content hasBtn">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<colgroup>
								<col style="width: 14%;" />
								<col style="width: 16%;" />
								<col style="width: 30%;" />
								<col style="width: 20%;" />
								<col style="width: 20%;" />
							</colgroup>
							<tbody>
								<tr>
									<th class="nl">网卡</th>
									<th>VLAN</th>
									<th>IP段</th>
									<th>IP</th>
									<th>网关</th>
								</tr>
								<s:iterator value="vmInstanceInfo.netList">
									<tr>
										<td><s:property value='eth' /></td>
										<td><s:property value='vlanName' /></td>
										<td><s:property value='startIp' />&nbsp;~&nbsp;<s:property
												value='endIp' /></td>
										<td><s:property value='ip' /></td>
										<td><s:property value='gateway' /></td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</div>
				</div>
				<!--网卡列表结束-->

				<!--主机云硬盘列表开始-->
				<div id="detail_tab_info">
					<div class="table-content hasBtn">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<colgroup>
								<col style="width: 30%;" />
								<col style="width: 30%;" />
								<col style="width: 15%;" />
								<col style="width: 25%;" />
							</colgroup>
							<tbody id="diskList">
								<tr>
									<th class="nl">名称</th>
									<th>ID</th>
									<th>容量</th>
									<th>操作</th>
								</tr>
							</tbody>
						</table>
					</div>
					<span id="attach" class="span-btn fr"> <a
						href="javascript: mountDisk('<s:property value="vmInstanceInfo.appId" />');">挂载云硬盘</a></span>
				</div>
				<!--主机云硬盘列表结束-->

				<!--备份信息列表开始-->
				<div id="backup_tab_info" class="table-content table-block"
					style="height: 410px; overflow: hidden">
					<s:form id="searchForm">
						<div class="table-seach">
							<p>
								<label class="fl">备份时间：</label>
								<s:textfield id="startTime" name="startTime"
									onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"
									cssClass="Wdate wdatelong" size="23" readonly="true" />
								至
								<s:textfield id="endTime" name="endTime"
									onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"
									cssClass="Wdate wdatelong" size="23" readonly="true" />
							</p>
							<span
								style="background: url(../images/ico_opt_search.png) no-repeat 5px center; padding: 2px 10px 2px 30px; border: 1px solid #c0c2c3; border-radius: 3px;"><a
								id="search" class="search" style="color: #222222;" href="javascript:void(0)"
								onclick="searchVmBak();">查询</a></span>
						</div>
					</s:form>

					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<col style="width: 35%;" />
						<col style="width: 20%;" />
						<col style="width: 25%;" />
						<col style="width: 20%;" />
						<tr>
							<th class="nl">备份ID</th>
							<th>容量（MB）</th>
							<th>创建时间</th>
							<th>操作</th>
						</tr>
						<tbody id="listTbody">
						</tbody>
					</table>
					<div id="pageBarDiv" class="pageBar"
						style="float: left; margin-left: 200px;"></div>
				</div>

				<div id="backupVm" class="float-div" style="display: none;">
					<div class="table-content">
						<div class="bk-page-form" id="bk_div">
							<div class="bk-section">
								<div class="field">
									<div class="caption">备份名称：</div>
									<div class="content">
										<input type="text" size="40" id="bk_name" value="" />
									</div>
									<div class="point"></div>
								</div>
								<div class="field">
									<div class="caption">备份备注：</div>
									<div class="content">
										<textarea cols="37" id="bk_remark"></textarea>
									</div>
									<div class="point"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--备份信息列表结束-->
				
				<!--虚拟机快照列表开始-->
				<div id="snapshot_tab_info">
					<div class="table-content hasBtn">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<col style="width: 15%;" />
								<col style="width: 20%;" />
								<col style="width: 10%;" />
								<col style="width: 27%;" />
								<col style="width: 18%;" />
								<tr>
									<th class="nl">快照名称</th>
									<th>创建时间</th>
									<th>状态</th>
									<th>备注</th>
									<th>操作</th>
								</tr>
								<tbody id="listTspan">
								</tbody>
							</tbody>
						</table>
						<div id="pageBarDiv" class="pageBar"
						style="float: left; margin-left: 200px;">
						</div>
					</div>
				</div>
				<!--虚拟机快照列表结束-->
			</div>
		</div>
	</div>
	<div class="detail-time">
		<span id="createTime">创建时间：<s:property
				value="vmInstanceInfo.createTime" /></span> <span id="expireTime">到期时间：<s:property
				value="vmInstanceInfo.expireTime" /></span>
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
<!--云硬盘列表 -->
<div id="mountDisk" class="float-div"
	style="display: none; width: 500px;">
	<div class="float-toolsbar">
		<select id="diskSeachKey" class="select-max">
			<option value="diskName">名称</option>
			<option value="diskId">ID</option>
			<option value="diskSize">容量</option>
		</select> <input id="diskSeachVal" type="text" class="float-input-long" />
		<ul class="opt-btn">
			<li><a class="search"
				href="javascript:seachDisk('<s:property value="vmInstanceInfo.appId" />');">查询</a></li>
		</ul>
	</div>
	<div class="float-div-center">
		<div class="table-content">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<colgroup>
					<col style="width: 10%;" />
					<col style="width: 30%;" />
					<col style="width: 50%;" />
					<col style="width: 10%;" />
				</colgroup>
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
<%-- 修改虚拟机名称 --%>
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
<div id="spanCreate" class="float-div" style="display: none; width: 300px; height: 200px; ">
	<table>
		<tr >
			<td valign="top">名称：</td>
			<td><input type="text" size="28" value=""
				name="snapshot_name" id="snapshot_name" /></td>
		</tr>
		<tr>
		<td valign="top">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<td valign="top">备注：</td>
			<td><textarea style="width: 183px; height: 138px;"
				name="snapshot_desc" id="snapshot_desc" >
				</textarea></td>
		</tr>
	</table> 
</div>
<!-- 修改页面 -->
<div class="page-form" id="relate_resource_div" style="display: none;">
	<div class="section">
		<div id="vm_id" class="field">
			<div class="caption">虚拟机ID：</div>
			<div class="content" id="vm_id">
				<s:property value="vmInstanceInfo.vmId" />
				<input id="appId" type="hidden"
					value="<s:property value="vmInstanceInfo.appId"/>" />
			</div>
			<div class="point"></div>
		</div>
		<%--<div class="field">
			<div class="caption">虚拟机名称：</div>
			<div class="content">
				<input type="text" size="28" maxlength="50" id="vmNameNew"
					value="<s:property value="vmInstanceInfo.vmName" />" />
			</div>
			<div class="point"></div>
		</div>--%>
		<div class="field">
			<div class="caption">处理器(核)：</div>
			<div class="content">
				<input type="text" size="28" id="cpuNumNew" maxlength="10"
					value="<s:property value="vmInstanceInfo.cpuNum" />" />
			</div>
			<div class="point"></div>
		</div>
		<div class="field">
			<div class="caption">内存(RAM)：</div>
			<div class="content">
				<input type="text" size="28" id="ramSizeNew" maxlength="10"
					value="<s:property value="vmInstanceInfo.ramSize" />" />
				<div style="float: right; padding-left: 5px;">
					<select class="select-min" style="float: none;" id="ramSizeUnit">
						<option value="GB">GB</option>
						<option value="MB">MB</option>
					</select>
				</div>
			</div>
			<div class="point"></div>
		</div>
		<div class="field">
			<div class="caption">存储空间(GB)：</div>
			<div class="content">
				<input type="text" size="28" id="discSizeNew" maxlength="10"
					value="<s:property value="vmInstanceInfo.discSize" />" />
			</div>
			<div class="point"></div>
		</div>
		<div class="field">
			<div class="caption">网卡信息：</div>
			<div class="content table-content"
				style="max-height: 155px; width: 800px; overflow-y: auto;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					style="border-left: 1px solid #d7d9da; border-right: 1px solid #d7d9da;">
					<colgroup>
						<col style="width: 10%;" />
						<col style="width: 17%;" />
						<col style="width: 25%;" />
						<col style="width: 21%;" />
						<col style="width: 16%;" />
						<col style="width: 10%;" />
					</colgroup>
					<tbody id="ethList">
						<tr>
							<th class="nl">网卡</th>
							<th>VLAN</th>
							<th>IP段</th>
							<th>IP</th>
							<th>网关</th>
							<th>操作</th>
						</tr>
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
								<td><span class="product-list-btn"><a
										href="javascript:editNet('<s:property value='eth' />')"
										id="edit">修改</a></span></td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
			<div class="point">
				<span class="product-list-btn"><a href="javascript:addNet()"
					id="add">添加</a></span>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript"
	src="../scripts/pagesjs/console/business/host/vm_detail.js"></script>