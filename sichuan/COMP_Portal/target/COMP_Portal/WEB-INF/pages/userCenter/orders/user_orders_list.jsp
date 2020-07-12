<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style>

</style>

<div class="list">
	<div class="title">
		<h2>订单管理</h2>
		<div id="message" style="display: none;">
			<span id="error" class="error"><s:actionerror /></span> <span
				class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
		</div>
		<s:form id="gotoForm">
			<input type="hidden" value="" id="vmId" name="vmId">
			<input type="hidden" value="" id="queryParmeterType"
				name="queryParmeterType">
		</s:form>
	</div>
	<div class="tool-bar">
		<div class="search-text">
			<label> 状态：</label><input id="waitAudit" type="checkbox"
				checked="checked" onclick="queryOrder();" /><label>待审批</label> <input
				id="auditPass" type="checkbox" checked="checked"
				onclick="queryOrder();" /><label>已通过 </label><input type="checkbox"
				id="auditNopass" checked="checked" onclick="queryOrder();" /><label>未通过</label><input
				type="checkbox" id="valid" checked="checked" onclick="queryOrder();" /><label>已生效</label>
			<input type="checkbox" id="expire" checked="checked"
				onclick="queryOrder();" /><label>已过期</label><input type="checkbox" id="invalid"
				checked="checked" onclick="queryOrder();" /><label>已回收</label>
		</div>
	</div>

	<div class="tool-bar">
		<div class="search-text">
			<select id="select" onchange="queryOrder()">
				<option value="notSelect">--资源类型--</option>
				<option value="0">云主机</option>
				<option value="1">物理机</option>
				<%--<option value="2">小型机</option> --%>
				<option value="5">云硬盘</option>
				<%-- <option value="4">虚拟机备份任务</option>--%>
				<%-- <option value="10">虚拟机快照</option>--%>
				<option value="12">IP段</option>
				<option value="13">VLAN</option>
				<option value="7">公网IP</option>
				<option value="14">负载均衡</option>
				<option value="15">分布式文件存储</option>
				<option value="16">虚拟防火墙</option>
			</select>
		</div>
		<div class="search-text">
			<select id="pool" onchange="queryOrder()">
				<option value="notSelect">--资源池名称--</option>
				<s:iterator value="pools">
					<option value="<s:property value="resPoolId" />"><s:property
							value="resPoolName" /></option>
				</s:iterator>
			</select>
		</div>
		<div class="search-text">
			<img class="search-ico" src="../img/ico-search.png" /> <input
				class="search-text" type="text" size="25" id="appName"
				name="appName" maxlength="50" value="<s:property value="appName" />"
				style="width: auto;" placeholder="输入企业客户名称" />
		</div>
		<a class="search-btn" href="javascript:;" onclick="queryOrder()">查询</a>

	</div>
	<!-- 列表 -->
	<div id="approval-will" class="list-items">
		<table id="ordertable" width="100%" border="0" cellspacing="0"
			cellpadding="0">
			<thead>
				<tr>
					<th>子订单号</th>
					<th>资源类型</th>
					<th>状态</th>
					<th>资源池名称</th>
					<th>企业客户名称</th>
					<th>变更时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="orderInfoLs">
					<tr class="iterator">
						<%-- <td style="font-size: 10px;"><s:property value="parentId" /></td> --%>
						<td title="订单号: <s:property value="parentId" />"><s:property
								value="orderId" /></td>
						<td><s:if test="caseType==0">云主机</s:if> <s:elseif
								test="caseType==1">物理机</s:elseif> <s:elseif test="caseType==2">小型机</s:elseif>
							<s:elseif test="caseType==3">小型机分区</s:elseif> <s:elseif
								test="caseType==4">虚拟机备份任务</s:elseif> <s:elseif
								test="caseType==5">硬盘</s:elseif> <s:elseif test="caseType==6">云储存</s:elseif>
							<s:elseif test="caseType==7">公网IP</s:elseif> <s:elseif
								test="caseType==8">带宽</s:elseif> <s:elseif test="caseType==9">安全组</s:elseif>
							<s:elseif test="caseType==10">虚拟机快照</s:elseif> <s:elseif
								test="caseType==11">虚拟机克隆</s:elseif> <s:elseif
								test="caseType==12">IP段</s:elseif> <s:elseif test="caseType==13">VLAN</s:elseif>
							<s:elseif test="caseType==14">负载均衡</s:elseif> <s:elseif
								test="caseType==15">分布式文件存储</s:elseif> <s:elseif
								test="caseType==16">虚拟防火墙</s:elseif></td>
						<td><s:if test="status==0">待审批</s:if> <s:elseif
								test="status==2">未通过</s:elseif> <s:elseif test="status==4">已取消</s:elseif>
							<s:elseif test="status==5">已过期</s:elseif> <s:elseif
								test="status==1">已通过</s:elseif> <s:elseif test="status==3">已生效</s:elseif>
							<s:elseif test="status==6">已回收</s:elseif> <s:elseif
								test="status==7">修改待审</s:elseif></td>
						<td class="cut pool"><s:property value="resPoolName" /></td>
						<td class="cut app"><s:property value="appName" /></td>
						<td><s:property value="updateTime" /></td>
						<td class="table-opt-block"><a href="#"
							onclick="orderInfoDetail('<s:property value="caseType" />','<s:property value="orderId" />','<s:property value="status" />',true)">详情</a>
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</div>
	<!-- 翻页 -->
	<div class="pageBar">
		<s:property value="pageBar" escape="false" />
	</div>

	<!-- 订单详细信息 -->
	<div class="page-form" id="order_detail" style="display: none;min-width:600px;">
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
				<!-- 添加判断是否为vlan，若为vlan则隐藏分区 -->
				<div class="caption" id="rpoolName1">资源池分区名称：</div>
				<div class="content" id="rpoolName2">
					<input type="text" size="40" id="resPoolPartName" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">企业客户名称：</div>
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
					<a id="info_a" class="btn" onclick="isShow('info');" href="javascript:void(0);">资源详情</a>
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
					<div class="caption">云硬盘容量(G)：</div>
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
				</div>
				<div class="field">
					<div class="caption">地址：</div>
					<div class="content">
						<input type="text" size="40" id="pip_startToEndIp" value=""
							style="border: 1px;" readonly="readonly" />
					</div>
					<div class="point"></div>
					<div class="caption">IP个数：</div>
					<div class="content">
						<input type="text" size="40" id="pip_ipTotal" value=""
							style="border: 1px;" readonly="readonly" />
					</div>
					<div class="point"></div>
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
			<div id="publicIp_info" style="display: none;">
			   <div class="field">
			       <div class="caption">公网IP地址：</div>
				    <div class="content">
					    <input type="text" size="40" id="publicIp" value=""
						    style="border: 1px;" readonly="readonly" />
				    </div>
				    <div class="point"></div>
			   </div>
			   <div class="field">
			       <div class="caption">绑定云主机ID：</div>
				    <div class="content">
					    <input type="text" size="40" id="hostId" value=""
						    style="border: 1px;" readonly="readonly" />
				    </div>
				    <div class="point"></div>
			   </div>
			   <div class="field">
			       <div class="caption">备注：</div>
				    <div class="content">
					    <input type="text" size="40" id="description" value=""
						    style="border: 1px;" readonly="readonly" />
				    </div>
				    <div class="point"></div>
			   </div>
		    </div>
		    <div id="loadbalance_info" style="display: none;">
			    <div class="field">
				    <div class="caption">负载均衡名称：</div>
				    <div class="content">
					    <input type="text" size="40" id="lbname" value=""
						    style="border: 1px;" readonly="readonly" />
				    </div>
				    <div class="point"></div>
			    </div>
			    <div class="field">
				    <div class="caption">端口：</div>
				    <div class="content">
					    <input type="text" size="40" id="lbport" value=""
						    style="border: 1px;" readonly="readonly" />
				    </div>
				    <div class="point"></div>
			    </div>
			    <div class="field">
				    <div class="caption">浮动ip：</div>
				    <div class="content">
					    <input type="text" size="40" id="lbip" value=""
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
			    </div>			    
			    <div class="field">
				    <div class="caption">负载均衡VlanID：</div>
				    <div class="content">
					    <input type="text" size="40" id="vlanId" value=""
						    style="border: 1px;" readonly="readonly" />
				    </div>
				    <div class="point"></div>
			    </div>
			    <div class="field">
				    <div class="caption">负载均衡方式：</div>
				    <div class="content">
					    <input type="text" size="40" id="LBType" value=""
						    style="border: 1px;" readonly="readonly" />
				    </div>
				    <div class="point"></div>
			    </div>
		    </div>
		    <div id="fileStore_info" style="display: none;">
			    <div class="field">
				    <div class="caption">分布式文件存储名称：</div>
				    <div class="content">
					    <input type="text" size="40" id="fsName" value=""
						    style="border: 1px;" readonly="readonly" />
				    </div>
				    <div class="point"></div>
			    </div>			    			   
			    <div class="field">
				    <div class="caption">分布式文件存储的模板ID：</div>
				    <div class="content">
					    <input type="text" size="40" id="fsTemplateId" value=""
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
			    </div>
			    <div class="field">
				    <div class="caption">资源全路径：</div>
				    <div class="content">
					    <input type="text" size="40" id=fsUrl value=""
						    style="border: 1px;" readonly="readonly" />
				    </div>
				    <div class="point"></div>
			    </div>
			    <div class="field">
				    <div class="caption">备注：</div>
				    <div class="content">
					    <input type="text" size="40" id="description" value=""
						    style="border: 1px;" readonly="readonly" />
				    </div>
				    <div class="point"></div>
			    </div>
		    </div>
		    <div id="vfireWall_info" style="display: none;">
			    <div class="field">
				    <div class="caption">名称：</div>
				    <div class="content">
					    <input type="text" size="40" id="fwName" value=""
						    style="border: 1px;" readonly="readonly" />
				    </div>
				    <div class="point"></div>
			    </div>
			    <div class="field">
				    <div class="caption">备注：</div>
				    <div class="content">
					    <input type="text" size="40" id="description" value=""
						    style="border: 1px;" readonly="readonly" />
				    </div>
				    <div class="point"></div>
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
			<div class="field">
				<div class="caption">所属物理机：</div>
				<div class="content">
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
					<input type="text" size="40" id="updateUser" value=""
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

	<script type="text/javascript"
		src="../scripts/pagesjs/userCenter/orders/user_orders_list.js"></script>