<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="../../vault/createAppRequest.jsp"/>
<h1>订单管理</h1>
<script type="text/javascript"
	src="../scripts/lib/echarts/echarts-all.js"></script>
<script type="text/javascript"
        src="../scripts/pagesjs/vault/vault_creat.js"></script>
<script type="text/javascript"
	src="../scripts/pagesjs/userCenter/orders/user_orders_list.js"></script>
<div id="message" style="display: none;">
	<span id="error" class="error"><s:actionerror /></span> <span
		class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
</div>

<div class="details-con">
	<div class="detail-title">
		<h3 class="apply-title">用户订单</h3>
		<ul class="title-btn">
			<s:if test="status==0 || status==7">
				<li class="select"><a href="javascript:void(0);"
					onclick="will(this);return false;" value='0'>待审核</a></li>
			</s:if>
			<s:else>
				<li><a href="javascript:void(0);"
					onclick="will(this);return false;" value='0'>待审核</a></li>
			</s:else>
			<li><a href="javascript:void(0);"
				onclick="will(this);return false;" value='1'>已通过</a></li>
			<li><a href="javascript:void(0);"
				onclick="will(this);return false;" value='2'>未通过</a></li>
			<li><a href="javascript:void(0);"
				onclick="will(this);return false;" value='3'>已生效</a></li>
			<!--<li><a href="javascript:void(0);" onclick="will(this);return false;" value='4'>已取消</a></li>-->
			<s:if test="status==5">
				<li class="select"><a href="javascript:void(0);"
					onclick="will(this);return false;" value='5'>已过期</a></li>
			</s:if>
			<s:else>
				<li><a href="javascript:void(0);"
					onclick="will(this);return false;" value='5'>已过期</a></li>
			</s:else>
			<li><a href="javascript:void(0);"
				onclick="will(this);return false;" value='6'>已回收</a></li>
		</ul>
	</div>
	<!-- 查询 -->
	<div class="table-seach">
		<p>
			<label class="fl">资源类型：</label> <select id="select"
				class="select-max" onchange="queryOrder()" style="width: 20px;">
				<option selected="selected" value="select">-请选择-</option>
				<option value="0">虚拟机</option>
				<option value="1">物理机</option>
				<%-- <option value="4">虚拟机备份任务</option>--%>
				<option value="5">云硬盘</option>
				<%-- <option value="10">虚拟机快照</option>
	<option value="11">虚拟机克隆</option>--%>
				<option value="12">IP段</option>
				<option value="13">VLAN</option>
				<option value="14">负载均衡</option>
				<option value="15">分布式文件存储</option>
				<option value="16">虚拟防火墙</option>
				<option value="17">VLAN 3期SDN</option>
			</select>
		</p>
		<p>
			<label class="fl">资源池名称：</label> <select id="pool" class="select-max"
				onchange="queryOrder()">
				<option selected="selected" value="select">-请选择-</option>
				<s:iterator value="pools">
					<option value="<s:property value="resPoolId" />"><s:property
							value="resPoolName" /></option>
				</s:iterator>
			</select>
		</p>
		<p>
			业务组名称：<input id="appName" type="text" />
		</p>
		<ul class="opt-btn fr">
			<li><a class="search" href="#" onclick="queryOrder()">查询</a></li>
			<li><a class="search" onclick="queryDiv();"
				href="javascript:void(0);">高级查询</a></li>
			<li><a class="batch" href="javascript:void(0);"
				onclick="approvalMore(this);return false;">批量审批</a></li>
		</ul>
	</div>
	<!-- 列表 -->
	<div id="approval-will" class="table-content table-block">
		<table id="ordertable" width="100%" border="0" cellspacing="0"
			cellpadding="0">
			<col style="width: 2%;" />
			<col style="width: 20%;" />
			<col style="width: 2%;" />
			<col style="width: 19%;" />
			<col style="width: 8%;" />
			<col style="width: 5%;" />
			<col style="width: 8%;" />
			<col style="width: 8%;" />
			<col style="width: 7%;" />
			<col style="width: 11%;" />
			<col style="width: 9%;" />

			<tr>
				<th class="nl"><input type="checkbox" name="selectSubAll"
					id="selectSubAll" /></th>
				<th>订单号</th>
				<th></th>
				<th>子订单号</th>
				<th>资源类型</th>
				<!--<th>总价</th>-->
				<th>状态</th>
				<th>资源池名称</th>
				<th>业务组名称</th>
				<th>变更人</th>
				<th>变更时间</th>
				<th>操作</th>
			</tr>
			<s:iterator value="orderInfoList">
				<tr class="iterator">
					<td><input type="checkbox" name="selectSub"
						value="<s:property value='parentId'/>" /></td>
					<td><s:property value="parentId" /></td>
					<td><s:if test="status==0 || status==7">
							<input type="checkbox" name="select"
								value="<s:property value='orderId'/>"
								vtype="<s:property value='parentId'/>" />
						</s:if></td>
					<td><s:property value="orderId" /></td>
					<td><s:if test="caseType==0">虚拟机</s:if> <s:elseif
							test="caseType==1">物理机</s:elseif> <s:elseif test="caseType==2">小型机</s:elseif>
						<s:elseif test="caseType==3">小型机分区</s:elseif> <s:elseif
							test="caseType==4">虚拟机备份任务</s:elseif> <s:elseif
							test="caseType==5">云硬盘</s:elseif> <s:elseif test="caseType==6">云储存</s:elseif>
						<s:elseif test="caseType==7">公网IP</s:elseif> <s:elseif
							test="caseType==8">带宽</s:elseif> <s:elseif test="caseType==9">安全组</s:elseif>
						<s:elseif test="caseType==10">虚拟机快照</s:elseif> <s:elseif
							test="caseType==11">虚拟机克隆</s:elseif> <s:elseif
							test="caseType==12">IP段</s:elseif> <s:elseif test="caseType==13">VLAN</s:elseif>
						<s:elseif test="caseType==14">负载均衡</s:elseif> <s:elseif
							test="caseType==15">分布式文件存储</s:elseif>
						<s:elseif test="caseType==16">虚拟防火墙</s:elseif>
						<s:elseif test="caseType==17">VLAN 3期SDN</s:elseif></td>
					<!--<td><s:property value="allPrice" /></td>-->
					<td><s:if test="status==0">待审批</s:if> <s:elseif
							test="status==2">未通过</s:elseif> <s:elseif test="status==4">已取消</s:elseif>
						<s:elseif test="status==5">已过期</s:elseif> <s:elseif
							test="status==6">已回收</s:elseif> <s:elseif test="status==7">修改待审</s:elseif>
						<s:elseif test="status==8">删除待审</s:elseif> <s:else>已通过</s:else></td>
					<td class="cut pool"><s:property value="resPoolName" /></td>
					<td class="cut app"><s:property value="appName" /></td>
					<td><s:property value="updateUser" /></td>
					<td><s:property value="updateTime" /></td>
					<td class="table-opt-block"><s:if
							test="status==0 || status==7 || status==8 ">
							<a href="#"
								onclick="orderInfoDetail('<s:property value="parentId" />','<s:property value="caseType" />','<s:property value="orderId" />','<s:property value="status" />',false)">审批</a>
						</s:if> <s:if test="status==5">
							<a href="#"
								onclick="orderDel('<s:property value="caseType" />','<s:property value="orderId" />')">回收</a>
						</s:if> <a href="javascript:void(0);"
						onclick="orderInfoDetail('<s:property value="parentId" />','<s:property value="caseType" />','<s:property value="orderId" />','<s:property value="status" />',true);return false;">详情</a>
					</td>
				</tr>
			</s:iterator>
		</table>
	</div>
	<!-- 翻页 -->
	<div class="pageBar">
		<s:property value="pageBar" escape="false" />
	</div>
</div>

<!-- 高级查询 -->
<div class="page-form" id="queryDiv" style="display: none; width: 450px">
	<div class="caption" style="width: auto; padding-left: 10px;">&nbsp;&nbsp;&nbsp;变更人：</div>
	<div class="content">
		<input type="text" size="46" id="updateUser" value="" />
	</div>
	<br /> <br />
	<div class="caption" style="width: auto; padding-left: 10px;">
		变更时间：</div>
	<div class="content">
		<input id="startTime" type="text" size="23"
			onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"
			readonly="true" class="Wdate wdatelong" />
	</div>
	<div class="caption" style="width: auto; padding-left: 10px;">至</div>
	<div class="content">
		<input id="endTime" type="text" size="23"
			onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
			readonly="true" class="Wdate wdatelong" />
	</div>
	<br /> <br />
	<div class="caption" style="width: auto; padding-left: 10px;">&nbsp;&nbsp;&nbsp;订单号：</div>
	<div class="content">
		<input type="text" size="46" id="queryParentId" value="" />
	</div>
	<br /> <br />
	<div class="caption" style="width: auto; padding-left: 10px;">子订单号：</div>
	<div class="content">
		<input type="text" size="46" id="queryOrderId" value="" />
	</div>
</div>


<!-- 订单详细信息 -->
<div class="page-form" id="order_detail" style="display: none;">
	<div>
		<div class="field">
			<div class="caption">订单号：</div>
			<div class="content">
				<input type="text" size="40" id="parentId" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
			<div class="caption">子订单号：</div>
			<div class="content">
				<input type="text" size="40" id="orderId" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
		</div>
		<div class="field">
			<div class="caption">资源池名称：</div>
			<div class="content">
				<input type="text" size="40" id="resPoolName" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>

			<div id="ispoolpart" style="display: block;">
				<div class="caption">资源池分区名称：</div>
				<div class="content">
					<input type="text" size="20" id="resPoolPartName" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
			</div>

			<div class="point table-opt-block">
				<a id="capacity_a" onclick="isShow('capacity');"
					href="javascript:void(0);"
					style="display: none; margin-left: -5px;">分区资源容量</a>
			</div>
		</div>
	</div>
	<div id="capacity"
		style="display: none; border: 1px solid #dfdfdf; margin-left: -1px;">
		<div class="field" style="width: 100%; margin-top: 10px;">
			<div id="poolchart1"
				style="width: 33%; float: left; height: 270px; text-align: center; margin-bottom: -25px;"></div>
			<div id="poolchart2"
				style="width: 33%; float: left; height: 270px; text-align: center; margin-bottom: -25px;"></div>
			<div id="poolchart3"
				style="width: 33%; float: left; height: 270px; text-align: center; margin-bottom: -25px;"></div>
		</div>
	</div>

<%-- 	<div id="lb_info" style="display: none;">
		<div class="field">
			<div class="caption">负载均衡名称：</div>
			<div class="content">
				<input type="text" size="40" id="lbName" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
			<div class="caption">负载均衡方式：</div>
			<div class="content">
				<input type="text" size="40" id="LBType" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
		</div>
		<div class="field">
			<div class="caption">负载均衡策略：</div>
			<div class="content">
				<input type="text" size="40" id="Strategy" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
			<div class="caption">负载均衡端口：</div>
			<div class="content">
				<input type="text" size="40" id="lbport" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
		</div>
		<div class="field">
			<div class="caption">流量协议类型：</div>
			<div class="content">
				<input type="text" size="40" id="protocal" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
			<div class="caption">并发链接数(个)：</div>
			<div class="content">
				<input type="text" size="40" id="connectNum" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
		</div>

		<div class="field">
			<div class="caption">链接速度(个/秒)：</div>
			<div class="content">
				<input type="text" size="40" id="NewConnectNum" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
			<div class="caption">吞吐能力(Kbps)：</div>
			<div class="content">
				<input type="text" size="40" id="Throughput" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
		</div>

		<div style="border: 1px solid #DFDFDF; width: 100%; height: 130px">
			<div class="field">
				<div class="caption">浮动IP</div>
				<div class="content"></div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">VLAN：</div>
				<div class="content">
					<select style="width: 90px" size="1" id="vlanSelect"
						name="ethListvlanId" onchange="changeVlan(this)"></select> <input
						id="appId" type="hidden" value=""> <input type="hidden"
						size="40" id="lbVlan" value="" style="border: 1px;"
						readonly="readonly" />
				</div>
				<div class="point"></div>
				<div class="caption">IP段：</div>
				<div class="content">
					<select style="width: 154px" size="1" id="ipsegmentSelect"
						name="ethListipsegmentId" onchange="changeIpsegment(this)">
						<option value="notSelect">--请选择--</option>
					</select> <input type="hidden" size="40" id="lbIpSegment" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
			<div class="caption">IP：</div>
			<div class="content">
				<select style="width: 140px" size="1" id="privateIpSelect"
					name="lbip">
					<option value="notSelect">--请选择--</option>
				</select> <input type="hidden" size="40" id="lbip" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
		</div>
	</div> --%>

	<div>
		<div class="field">
			<div class="caption">业务组名称：</div>
			<div class="content">
				<input type="text" size="40" id="appName_detail" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
			<div class="caption">状态：</div>
			<div class="content">
				<input type="text" size="40" id="status" value="2"
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
		</div>
		<div class="field">
			<div class="caption">资源类型：</div>
			<div class="content">
				<input type="text" size="40" id="caseType" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
			<div id="effectiveTimeDiv" style="display: none;">
				<div class="caption">生效时间：</div>
				<div class="content">
					<input type="text" size="20" id="effectiveTime" value="40"
						style="border: 1px;" readonly="readonly" />
				</div>
			</div>
			<div id="effectiveTimeNoneDiv">
				<div class="caption"></div>
				<div class="content" style="width: 125px;"></div>
			</div>
			<div class="point table-opt-block">
				<a id="info_a" onclick="isShow('info');" href="javascript:void(0);">资源详情</a>
			</div>
		</div>
	</div>
	<div id="info"
		style="display: none; border: 1px solid #dfdfdf; margin-left: -1px;">
		<div id="vm_info" style="display: none;">
			<div id="vm_item" class="field" style="display: none;">
				<div class="caption">条目名称：</div>
				<div class="content">
					<input type="text" size="40" id="vm_itemName" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">虚拟机名称：</div>
				<div class="content">
					<input type="text" size="40" id="vm_vmName" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
				<div class="caption">系统类型：</div>
				<div class="content">
					<input type="text" size="40" id="vm_isoName" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">处理器：</div>
				<div class="content">
					<input type="text" size="40" id="vm_cpuNum" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
				<div class="caption">内存(RAM)：</div>
				<div class="content">
					<input type="text" size="40" id="vm_ramSize" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">存储空间：</div>
				<div class="content">
					<input type="text" size="40" id="vm_discSize" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
				<div class="caption">备注：</div>
				<div class="content">
					<input type="text" size="40" id="vm_description" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
			<div id="detail_tab" style="margin-left: 3%; width: 713px;">
				<ul class="detail_tab">
					<li class="selected">网卡信息</li>
				</ul>
				<div class="detail-center" style="width: 111%">
					<div id="net_tab_info" style="width: 100%">
						<div class="table-content hasBtn" style="width: 100%">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<colgroup>
									<col style="width: 8%;" />
									<col style="width: 18%;" />
									<col style="width: 30%;" />
									<col style="width: 22%;" />
									<col style="width: 22%;" />
								</colgroup>
								<tbody id="vm_netListTbody">
									<tr>
										<th class="nl">网卡</th>
										<th>VLAN</th>
										<th>IP段</th>
										<th>IP</th>
										<th>网关</th>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="pm_info" style="display: none;">
			<div id="pm_item" class="field" style="display: none;">
				<div class="caption">条目名称：</div>
				<div class="content">
					<input type="text" size="40" id="pm_itemName" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">物理机名称：</div>
				<div class="content">
					<input type="text" size="40" id="pm_pmName" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
				<div class="caption">物理机型号：</div>
				<div class="content">
					<input type="text" size="40" id="pm_serverType" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">处理器类型：</div>
				<div class="content">
					<input type="text" size="40" id="pm_cpuType" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
				<div class="caption">系统类型：</div>
				<div class="content">
					<input type="text" size="40" id="pm_isoName" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">内存(RAM)：</div>
				<div class="content">
					<input type="text" size="40" id="pm_ramSize" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
				<div class="caption">存储空间：</div>
				<div class="content">
					<input type="text" size="40" id="pm_discSize" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">备注：</div>
				<div class="content">
					<input type="text" size="40" id="pm_description" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
			<div id="detail_tab" style="margin-left: 6%; width: 713px;">
				<ul class="detail_tab">
					<li class="selected">网卡信息</li>
				</ul>
				<div class="detail-center">
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
								<tbody id="pm_netListTbody">
									<tr>
										<th class="nl">网卡</th>
										<th>VLAN</th>
										<th>IP段</th>
										<th>IP</th>
										<th>网关</th>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="bk_info" style="display: none;">
			<div class="field">
				<div class="caption">任务名称：</div>
				<div class="content">
					<input type="text" size="40" id="bk_vmBakName" value="2"
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
				<div class="caption">虚拟机名称：</div>
				<div class="content">
					<input type="text" size="40" id="bk_vmName" value="2"
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">任务开始时间：</div>
				<div class="content">
					<input type="text" size="40" id="bk_backupStartTime" value="2"
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
				<div class="caption">备份周期：</div>
				<div class="content">
					<input type="text" size="40" id="bk_backupCyc" value="2"
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">任务保留时间(天)：</div>
				<div class="content">
					<input type="text" size="40" id="bk_backStoreTime" value="2"
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
				<div class="caption">备注：</div>
				<div class="content">
					<input type="text" size="40" id="bk_description" value="2"
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
		</div>
		<div id="ebs_info" style="display: none;">
			<div id="ebs_item" class="field" style="display: none;">
				<div class="caption">条目名称：</div>
				<div class="content">
					<input type="text" size="40" id="ebs_itemName" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">硬盘容量：</div>
				<div class="content">
					<input type="text" size="40" id="ebs_diskSize" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
				<div class="caption">名称：</div>
				<div class="content">
					<input type="text" size="40" id="ebs_ebsName" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">硬盘类型：</div>
				<div class="content">
					<input type="text" size="40" id="ebs_resourceType" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
		</div>
		<div id="pip_info" style="display: none;">
			<div class="field">
				<div class="caption">名称：</div>
				<div class="content">
					<input type="text" size="40" id="pip_ipsegmentDesc" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
				<div class="point"></div>
				<div class="caption">IP个数：</div>
				<div class="content">
					<input type="text" size="40" id="pip_ipTotal" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">地址：</div>
				<div class="content" style="width: 700px;">
					<input type="text" size="100" id="pip_startToEndIp" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			<!--  	<div class="caption">IP个数：</div>
				<div class="content">
					<input type="text" size="40" id="pip_ipTotal" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>   -->
			</div>
		</div>
		<div id="vlan_info" style="display: none;">
			<div class="field">
				<div class="caption">名称：</div>
				<div class="content">
					<input type="text" size="40" id="vlan_vlanName" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
		</div>
		<!-- vlan 3期SDN -->
		<div id="vlanSdn_info" style="display: none;">
			<div class="field">
				<div class="caption">名称：</div>
				<div class="content">
					<input type="text" size="40" id="vlanSdn_vlanName" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">Vlan起始ID：</div>
				<div class="content">
					<input type="text" size="40" id="vlanSdn_startId" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
				<div class="caption">vlan终止ID：</div>
				<div class="content">
					<input type="text" size="4" id="vlanSdn_endId" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
			</div>
		</div>
<!-- 文件存储 -->
		<div id="fs_info" style="display: none;">
			<div class="field">
				<div class="caption">分布式文件存储名称：</div>
				<div class="content">
					<input type="text" size="40" id="fsName" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">配额大小：</div>
				<div class="content">
					<input type="text" size="40" id="fsQuotaSize" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
				<div class="caption">共享方式：</div>
				<div class="content">
					<input type="text" size="40" id="fsShareType" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">描述：</div>
				<div class="content">
					<input type="text" size="40" id="description" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
		</div>
		<!-- 虚拟防火墙 -->
		<div id="fw_info" style="display: none;">
			<div class="field">
				<div class="caption">虚拟防火墙名称：</div>
				<div class="content">
					<input type="text" size="40" id="fwName" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">描述：</div>
				<div class="content">
					<input type="text" size="40" id="description" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
		</div>
		<!-- 公网ip -->
		<div id="ip_info" style="display: none;">
			<div class="field">
				<div class="caption">公网IP地址：</div>
				<div class="content">
					<input type="text" size="40" id="publicIp" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
		</div>
		
	<!-- 负载均衡 -->
	  <div id="lb_info" style="display: none;">
		<div class="field">
			<div class="caption">负载均衡名称：</div>
			<div class="content">
				<input type="text" size="40" id="lbName" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
			<div class="caption">负载均衡方式：</div>
			<div class="content">
				<input type="text" size="40" id="LBType" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
		</div>
		<div class="field">
			<div class="caption">负载均衡策略：</div>
			<div class="content">
				<input type="text" size="40" id="Strategy" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
			<div class="caption">负载均衡端口：</div>
			<div class="content">
				<input type="text" size="40" id="lbport" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
		</div>
		<div class="field">
			<div class="caption">流量协议类型：</div>
			<div class="content">
				<input type="text" size="40" id="protocal" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
			<div class="caption">并发链接数(个)：</div>
			<div class="content">
				<input type="text" size="40" id="connectNum" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
		</div>

		<div class="field">
			<div class="caption">链接速度(个/秒)：</div>
			<div class="content">
				<input type="text" size="40" id="NewConnectNum" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
			<div class="caption">吞吐能力(Kbps)：</div>
			<div class="content">
				<input type="text" size="40" id="Throughput" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
		</div>

 		<div style="border: 1px solid #DFDFDF; width: 100%; height: 130px"> 
			<div class="field">
				<div class="caption">浮动IP</div>
				<div class="content"></div>
				<div class="point"></div>
			</div> 
			<div class="field">
				<div class="caption">VLAN：</div>
				<div class="content">
					<input type="text" size="40" id="lbVlanName" value="" style="border: 1px;" readonly="readonly" />
					<input type="hidden" size="40" id="lbVlan" value="" style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
			<div class="caption">IP段：</div>
			<div class="content">
			       <input type="text" size="40" id="lbIpSegmentName" value="" style="border: 1px;" readonly="readonly" />
		           <input type="hidden" size="40" id="lbIpSegment" value="" style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
			<div class="field">
			<div class="caption">IP：</div>
			<div class="content">
			      <input type="text" size="40" id="lbIpAddress" value="" style="border: 1px;" readonly="readonly" />
                  <input type="hidden" size="40" id="lbip" value="" style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
			</div>
<%-- 			<div class="field">
				<div class="caption">VLAN：</div>
				<div class="content">
					<select style="width: 90px" size="1" id="vlanSelect"
						name="ethListvlanId" onchange="changeVlan(this)"></select> <input
						id="appId" type="hidden" value=""> <input type="hidden"
						size="40" id="lbVlan" value="" style="border: 1px;"
						readonly="readonly" />
				</div>
				<div class="point"></div>
				<div class="caption">IP段：</div>
				<div class="content">
					<select style="width: 154px" size="1" id="ipsegmentSelect"
						name="ethListipsegmentId" onchange="changeIpsegment(this)">
						<option value="notSelect">--请选择--</option>
					</select> <input type="hidden" size="40" id="lbIpSegment" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
			<div class="caption">IP：</div>
			<div class="content">
				<select style="width: 140px" size="1" id="privateIpSelect"
					name="lbip">
					<option value="notSelect">--请选择--</option>
				</select> <input type="hidden" size="40" id="lbip" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div> --%>
			
		</div>
 	  </div> 
		
	</div>

	<div>
		<div id="lengthTimeDiv" class="field" style="display: none;">
			<div>
				<div class="caption">时长：</div>
				<div class="content">
					<input type="text" size="40" id="lengthTime" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
			<div id="expireTimeDiv" style="display: none;">
				<div class="caption">到期时间：</div>
				<div class="content">
					<input type="text" size="40" id="expireTime" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
		</div>
		<div id="pmList_div" class="field" style="display: none;">
			<div class="caption">所属物理机：</div>
			<div class="content">
				<%-- <select id="pmNameSelect"></select> --%>
				<input type="text" size="40" id="pmName" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
		</div>
		<div class="field">
			<div class="caption">申请人：</div>
			<div class="content">
				<input type="text" size="40" id="creater" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
			<div class="caption">申请时间：</div>
			<div class="content">
				<input type="text" size="40" id="createTime" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
		</div>
		<div class="field">
			<div class="caption">变更人：</div>
			<div class="content">
				<input type="text" size="40" id="updater" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
			<div class="caption">变更时间：</div>
			<div class="content">
				<input type="text" size="40" id="updateTime" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
		</div>
		<div class="field" id="aduitInfo">
			<div class="caption">审批人：</div>
			<div class="content">
				<input type="text" size="40" value="" id="auditUser"
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
			<div class="caption">审批时间：</div>
			<div class="content">
				<input type="text" size="40" id="auditTime" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
		</div>
		<div class="field" id="aduits">
			<div class="caption">审批意见：</div>
			<div class="content">
				<input type="text" size="40" id="auditInfo" value=""
					style="border: 1px;" readonly="readonly" />
			</div>
			<div class="point"></div>
		</div>
	</div>
	<div id="section" class="section"></div>
	<div id="audit_div">
		<div class="field">
			<div class="caption">审批意见：</div>
			<div class="content">
				<textarea cols="80" rows="3" id="auditInfo2"></textarea>
			</div>
			<div class="point"></div>
		</div>
	</div>
</div>

<!-- 批量审批弹出层 -->
<div class="page-form" id="approvalMore" style="display: none;">
	<div class="field">
		<div class="caption">审批意见：</div>
		<div class="content">
			<textarea cols="40" rows="5" id="auditInfos"></textarea>
		</div>
		<div class="point"></div>
	</div>
	<div class="point"></div>
</div>
