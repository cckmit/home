<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>物理机控制台</h1>
<div id="message" style="display: none;">
	<span id="error" class="error"><s:actionerror /></span> <span
		class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
</div>
<span class="apply"><a href="#" onclick="goToApplyPage();">申请物理机</a></span>

<span class="apply"><a href="#" onclick="goToUpdatePage();">修改物理机</a></span>
<!-- 主机详情 -->
<div class="details-con">
	<div class="detail-title">
		<a href="#" title="返回" onclick="goToPMListPage()"> <span
			class="back"></span>
		</a>
		<h2>
			<s:property value="pmInstanceInfo.pmName" />
		</h2>
		<span id="status" class="status mt s-blue">
			<s:if test="pmInstanceInfo.pmId == null || pmInstanceInfo.pmId == ''">待创建</s:if>
			<s:else>加载中</s:else>
		</span>
		<div class="operation" title="主机操作">
			<!-- 主机操作 -->
			<ul id="vmopt"></ul>
		</div>
	</div>
	<div class="detail-info">
		<%-- 
		<h3>
			使用模板创建时显示条目名称
			<s:if test="pmInstanceInfo.paramFlag == 0">
				<s:property value="pmInstanceInfo.itemName" />
			</s:if>
		</h3>
		--%>
		<div class="detail-info-tb">
			<table width="100%" border="0">
				<tr>
					<th>名称：</th>
					<td style="word-break: break-all"><span id="custom_name"><s:property
								value="pmInstanceInfo.pmName" /></span>&nbsp;&nbsp;<span
						class="span-btn" style="float: right;"><a
							href="javascript:modifyCustomName();">修改名称</a></span></td>
					<td><input id="pmId" type="hidden"
						value="<s:property value="pmInstanceInfo.pmId"/>" /></td>
				</tr>
				<tr>
					<th width="95">物理机ID：</th>
					<td><s:property value="pmInstanceInfo.pmId" /></td>
				</tr>
				<%--使用模板创建时显示条目名称  --%>
				<s:if test="pmInstanceInfo.paramFlag == 0">
					<tr>
						<th width="95">条目名称：</th>
						<td><s:property value="pmInstanceInfo.itemName" /></td>
					</tr>
				</s:if>
				<tr>
					<th width="95">处理器类型：</th>
					<td><s:property value="pmInstanceInfo.cpuType" /></td>
				</tr>
				<tr>
					<th>内存(RAM)：</th>
					<td><s:property value="pmInstanceInfo.ramSize" />M</td>
				</tr>
				<tr>
					<th>存储空间：</th>
					<td><s:property value="pmInstanceInfo.discSize" />G</td>
				</tr>
				<tr>
					<th>物理机类型：</th>
					<td><s:property value="pmInstanceInfo.serverType" /></td>
				</tr>
				<tr>
					<th>系统：</th>
					<td><s:property value="pmInstanceInfo.isoName" /></td>
				</tr>
				<tr>
					<th>所属业务：</th>
					<td><s:property value="pmInstanceInfo.appName" /></td>
				</tr>
				<tr>
					<th>备注：</th>
					<td style="word-break: break-all"><span id="remark"> <s:property
								value="pmInstanceInfo.description" />
					</span>&nbsp;&nbsp; <span class="span-btn" style="float: right;"> <a
							href="javascript:modifyRemark();">修改备注</a>
					</span></td>
				</tr>
				<tr>
					<th>资源池：</th>
					<td><s:property value="pmInstanceInfo.resPoolName" /></td>
					<td><input id="resPoolId" type="hidden"
						value="<s:property value="pmInstanceInfo.resPoolId"/>" /></td>
				</tr>
				<tr>
					<th>分区：</th>
					<td><s:property value="pmInstanceInfo.resPoolPartName" /></td>
					<td><input id="resPoolPartId" type="hidden"
						value="<s:property value="pmInstanceInfo.resPoolPartId"/>" /></td>
				</tr>
			</table>
		</div>
		<div class="detail-info-tb">
			<%-- <div class="pwd-title">root帐户初始登录用户名:</div> --%>
			<%--密码最多15字符 --%>
			<%-- 	<div class="pwd"><s:property value="pmInstanceInfo.pmUser" /></div><br> --%>
			<%-- <div class="pwd-title">root帐户初始登录口令:</div> --%>
			<%--密码最多15字符 --%>
			<%-- 	<div class="pwd"><s:property value="pmInstanceInfo.pmPassword" /></div> --%>
		</div>

		<div>
			<div
				style="background: none repeat scroll 0 0 #F0F3F5; border: 1px solid #D7D9DA; clear: both; margin: 0 0 10px 0;"></div>
		</div>
		<div id="detail_tab">
			<ul class="detail_tab">
				<li class="selected"><a href="#net_tab_info">网卡信息</a></li>
				<%-- <li><a href="#detail_tab_info">硬盘</a></li>--%>
			</ul>
			<div class="detail-center">
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
								<s:iterator value="pmInstanceInfo.netList">
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

				<!--硬盘列表开始-->
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
						href="javascript: mountDisk('<s:property value="pmInstanceInfo.appId" />');">挂载硬盘</a></span>
				</div>
				<!--硬盘列表结束-->
			
			</div>
		</div>
	</div>
	<div class="detail-time">
		<span id="createTime">创建时间：<s:property
				value="pmInstanceInfo.createTime" /></span> <span id="expireTime">到期时间：<s:property
				value="pmInstanceInfo.expireTime" /></span>
	</div>
</div>

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
<!-- -->
<!-- 虚拟硬盘列表 -->
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
				href="javascript:seachDisk('<s:property value="pmInstanceInfo.appId" />');">查询</a></li>
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
<%-- 修改自定义名称 --%>
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

<!-- 修改页面 -->
<div class="page-form" id="relate_resource_div" style="display: none;">
	<div class="section">
		<div id="pm_id" class="field">
			<div class="caption">物理机ID：</div>
			<div class="content" id="pm_id">
				<s:property value="pmInstanceInfo.pmId" />
				<input id="appId" type="hidden"
					value="<s:property value="pmInstanceInfo.appId"/>" />
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
						<col style="width: 30%;" />
						<col style="width: 16%;" />
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
						<s:iterator value="pmInstanceInfo.netList">
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
			<!-- 目前物理机只有一块网卡
			<div class="point">
				<span class="product-list-btn"><a href="javascript:addNet()"
					id="add">添加</a></span>
			</div>
			 -->
		</div>
	</div>
</div>

<script type="text/javascript"
	src="../scripts/pagesjs/console/host/pm_detail.js"></script>