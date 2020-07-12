<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<jsp:include page="../../../vault/createAppRequest.jsp"/>
<script type="text/javascript"
	src="../scripts/lib/echarts/echarts-all.js"></script>
<style>
<!--
.content {
	width: 225px;
}
-->
</style>
<script type="text/javascript">
//以下三项是分区资源容量信息变量
//VCPU
var vcpu_resUsed = <s:property value='vcpu.resUsed'/>;
var vcpu_resTotal = <s:property value='vcpu.resTotal'/>;

//内存使用情况
var memory_resUsed = <s:property value='memory.resUsed'/>;
var memory_resTotal = <s:property value='memory.resTotal'/>;

//磁盘使用情况
var disk_resUsed = <s:property value='disk.resUsed'/>;
var disk_resTotal = <s:property value='disk.resTotal'/>;
</script>
<h1>订单审批</h1>
<div class="details-con">
	<div class="detail-title">
		<a href="javascript:void(0)" id='goBack' title="返回"> <span
			class="back"></span>
		</a>
		<h3 class="apply-title">
			<s:if test="caseType==0">虚拟机</s:if>
			<s:elseif test="caseType==1">物理机</s:elseif>
			<s:elseif test="caseType==4">虚拟机备份任务</s:elseif>
			<s:elseif test="caseType==5">云硬盘</s:elseif>
			<s:elseif test="caseType==12">IP段</s:elseif>
			<s:elseif test="caseType==13">VLAN</s:elseif>
			<s:elseif test="caseType==14">负载均衡</s:elseif>
			<s:elseif test="caseType==15">分布式文件存储</s:elseif>
			<s:elseif test="caseType==16">虚拟防火墙</s:elseif>
			<s:elseif test="caseType==17">VLAN 3期SDN</s:elseif>
			订单审批
		</h3>
	</div>
	<!-- form -->
	<!-- 订单详细信息 -->
	<div class="page-form">
		<div>
			<div class="field">
				<div class="caption">订单号：</div>
				<div class="content">
					<s:property value="orderInfo.parentId" />
					<s:hidden id="parentId" name="orderInfo.parentId" />
				</div>
				<div class="point"></div>
				<div class="caption">子订单号：</div>
				<div class="content">
					<s:property value="orderInfo.orderId" />
					<s:hidden id="orderId" name="orderInfo.orderId" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">资源池名称：</div>
				<div class="content">
					<s:property value="orderInfo.resPoolName" />
				</div>
				<div class="point"></div>

				<div id="ispoolpart" style="display: block;">
					<div class="caption">资源池分区名称：</div>
					<div class="content">
						<s:property value="orderInfo.resPoolPartName" />
					</div>
				</div>

				<s:if
					test="orderInfo.caseType == 0 || orderInfo.caseType == 1 || orderInfo.caseType == 5">
					<div id="isShowCapacity" class="point table-opt-block">
						<a id="capacity_a" onclick="isShow('capacity');"
							href="javascript:void(0);" style="margin-left: -5px;">分区资源容量</a>
					</div>
				</s:if>
			</div>
		</div>

		<div id="lbInfo" style="display: none;">
			<div class="field">
				<div class="caption">名称：</div>
				<div class="content">
					<s:property value="lbInfo.lbname" />
				</div>
				<div class="point"></div>
				<div class="caption">负载均衡方式：</div>
				<div class="content">
					<s:if test="lbInfo.LBType==1">单臂</s:if>
					<s:if test="lbInfo.LBType==2">双臂</s:if>
					<s:if test="lbInfo.LBType==3">三角</s:if>
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">负载均衡策略：</div>
				<div class="content">
					<s:if test="lbInfo.Strategy='0'">基于应用响应时间</s:if>
					<s:if test="lbInfo.Strategy=='1'">原IP地址哈希（HASH）值</s:if>
					<s:if test="lbInfo.Strategy=='2'">目的IP地址哈希（HASH）值</s:if>
					<s:if test="lbInfo.Strategy=='3'">内容字串哈希值</s:if>
					<s:if test="lbInfo.Strategy=='4'">Cookie名称哈希值</s:if>
					<s:if test="lbInfo.Strategy=='5'">URL哈希值</s:if>
					<s:if test="lbInfo.Strategy=='6'">服务器可用带宽</s:if>
					<s:if test="lbInfo.Strategy=='7'">服务器应用连接数</s:if>
					<s:if test="lbInfo.Strategy=='8'">>服务器负荷</s:if>
					<s:if test="lbInfo.Strategy=='9'">轮询</s:if>
				</div>
				<div class="point"></div>
				<div class="caption">负载均衡端口：</div>
				<div class="content">
					<s:property value="lbInfo.lbPort" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">流量协议类型：</div>
				<div class="content">
					<s:property value="lbInfo.protocal" />
				</div>
				<div class="point"></div>
				<div class="caption">并发链接数(个)：</div>
				<div class="content">
					<s:property value="lbInfo.connectNum" /> 个
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">链接速度(个/秒)：</div>
				<div class="content">
					<s:property value="lbInfo.NewConnectNum" /> 个/秒
				</div>
				<div class="point"></div>
				<div class="caption">吞吐能力(Kbps)：</div>
				<div class="content">
					<s:property value="lbInfo.Throughput" /> Kbps
				</div>
				<div class="point"></div>
			</div>
			<div style="border: 1px solid #DFDFDF; width: 70%;">
				<div class="field">
					<div class="caption">浮动IP</div>
					<div class="content">
					</div>
					<div class="point"></div>
				</div>
				<div class="field">
					<div class="caption">VLAN：</div>
					<div class="content">
						<s:property value="lbInfo.vlanName" /> 
						<input type="hidden" id="lbVlan" value="<s:property value="lbInfo.lbVlan" />">
					</div>
					<div class="point"></div>
				    <div class="caption">IP段：</div>
					<div class="content">
                        <s:property value="lbInfo.ipSegmentDesc" /> 
						<input type="hidden" id="lbIpSegment" value="<s:property value="lbInfo.lbIpSegment" />">
					</div>
					<div class="point"></div>
				</div>
				<div class="field">
					<div class="caption">IP：</div>
					<div class="content">
						<s:property value="lbInfo.lbip" /> 
						<input type="hidden" id="lbIp" value="<s:property value="lbInfo.lbip" />">
					</div>
					<div class="point"></div>					
				</div>
				
<%-- 				<div class="field">
					<div class="caption">VLAN：</div>
					<div class="content">
						<select style="width: 90px" size="1" id="vlanSelect"
							name="ethListvlanId" onchange="changeVlan(this)"></select>
						<input type="hidden" id="lbVlan" value="<s:property value="lbInfo.lbVlan" />">
					</div>
					<div class="point"></div>
					<div class="caption">IP段：</div>
					<div class="content">
						<select style="width: 154px" size="1" id="ipsegmentSelect"
							name="ethListipsegmentId" onchange="changeIpsegment(this)">
							<option value="notSelect">--请选择--</option>
						</select>
						<input type="hidden" id="lbIpSegment" value="<s:property value="lbInfo.lbIpSegment" />">
					</div>
					<div class="point"></div>
				</div>
				<div class="field">
					<div class="caption">IP：</div>
					<div class="content">
						<select style="width: 140px" size="1" id="privateIpSelect"
							name="lbip">
							<option value="notSelect">--请选择--</option>
						</select>
						<input type="hidden" id="lbIp" value="<s:property value="lbInfo.lbip" />">
					</div>
					<div class="point"></div>					
				</div> --%>
				
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
		<div>
			<div class="field">
				<div class="caption">业务组名称：</div>
				<div class="content">
					<s:property value="orderInfo.appName" />
					<input id="appId" type="hidden" value="<s:property value="orderInfo.appId" />">
				</div>
				<div class="point"></div>
				<div class="caption">状态：</div>
				<div class="content">
					<span id="status"><s:property value="orderInfo.status" /></span>
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">资源类型：</div>
				<div class="content">
					<span id="caseType"><s:property value="orderInfo.caseType" /></span>
				</div>
				<div class="point"></div>
				<s:if
					test="orderInfo.effectiveTime !=null && orderInfo.effectiveTime!=''">
					<div class="caption">生效时间：</div>
					<div class="content">
						<s:property value="orderInfo.effectiveTime" />
					</div>
					<div class="point"></div>
				</s:if>
			</div>
		</div>
		<s:if test="orderInfo.caseType == 0">
			<s:if test="vmInfo.paramFlag == 0">
				<div class="field">
					<div class="caption">条目名称：</div>
					<div class="content">
						<s:property value="vmInfo.itemName" />
					</div>
					<div class="point"></div>
				</div>
			</s:if>
			<div class="field">
				<div class="caption">虚拟机名称：</div>
				<div class="content">
					<s:if
						test="vmInfoEdit != null && vmInfoEdit.vmName != null && vmInfoEdit.vmName != '' && orderInfo.status == 7">
						<font color="red"><s:property value="vmInfoEdit.vmName" /></font>
					</s:if>
					<s:else>
						<s:property value="vmInfo.vmName" />
					</s:else>
				</div>
				<div class="point"></div>
				<div class="caption">系统类型：</div>
				<div class="content">
					<s:property value="vmInfo.isoName" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">处理器：</div>
				<div class="content">
					<s:if
						test="vmInfoEdit != null && vmInfoEdit.cpuNum != null && vmInfoEdit.cpuNum != '' && orderInfo.status == 7">
						<font color="red"><s:property value="vmInfoEdit.cpuNum" /></font>核
						</s:if>
					<s:else>
						<s:property value="vmInfo.cpuNum" />核
						</s:else>
				</div>
				<div class="point"></div>
				<div class="caption">内存(RAM)：</div>
				<div class="content">
					<s:if
						test="vmInfoEdit != null && vmInfoEdit.ramSize != null && vmInfoEdit.ramSize != '' && orderInfo.status == 7">
						<font color="red"><s:property value="vmInfoEdit.ramSize" /></font>G
						</s:if>
					<s:else>
						<s:property value="vmInfo.ramSize" />G
						</s:else>
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">存储空间：</div>
				<div class="content">
					<s:if
						test="vmInfoEdit != null && vmInfoEdit.discSize != null && vmInfoEdit.discSize != '' && orderInfo.status == 7">
						<font color="red"><s:property value="vmInfoEdit.discSize" /></font>T
						</s:if>
					<s:else>
						<s:property value="vmInfo.discSize" />T
						</s:else>
				</div>
				<%-- <s:if test="orderInfo.status==0">
					<div class="point"></div>
					<div class="caption">所属物理机：</div>
					<div class="content">
						<s:select id="pmNameSelect" name="pmNameSelect" list="pms"
							listKey="pmId" listValue="pmName" headerKey="" headerValue="-请选择-" />
					</div>
					<div class="point"></div>
				</s:if> --%>
			</div>
			<div class="field">
				<div class="caption">备注：</div>
				<div class="content">
					<s:property value="vmInfo.description" />
				</div>
				<div class="point"></div>
			</div>
			<div id="detail_tab" style="margin-left: 4%; width: 1050px;">
				<ul class="detail_tab">
					<li class="selected"><a>网卡信息</a></li>
				</ul>
				<div class="detail-center">
					<div id="net_tab_info">
						<div class="table-content hasBtn">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<colgroup>
									<col style="width: 8%;" />
									<col style="width: 16%;" />
									<col style="width: 24%;" />
									<col style="width: 26%;" />
									<col style="width: 26%;" />
								</colgroup>
								<tbody>
									<tr>
										<th class="nl">网卡</th>
										<th>VLAN</th>
										<th>IP段</th>
										<th>IP</th>
										<th>网关</th>
									</tr>
									<s:iterator value="vmInfo.netList">
										<tr
											<s:if test="flag != 'old' && orderInfo.status == 7">style="color: red;"</s:if>>
											<td nowrap="nowrap"><s:property value='eth' /></td>
											<td nowrap="nowrap"><s:property value='vlanName' /></td>
											<td nowrap="nowrap"><s:property value='startIp' />&nbsp;~&nbsp;<s:property
													value='endIp' /></td>
											<td nowrap="nowrap"><s:property value='ip' /></td>
											<td nowrap="nowrap"><s:property value='gateway' /></td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</s:if>
		<s:elseif test="orderInfo.caseType == 1">
			<s:if test="pmInfo.paramFlag == 0">
				<div class="field">
					<div class="caption">条目名称：</div>
					<div class="content">
						<s:property value="pmInfo.itemName" />
					</div>
					<div class="point"></div>
				</div>
			</s:if>
			<div class="field">
				<div class="caption">物理机名称：</div>
				<div class="content">
					<s:if
						test="pmInfoEdit != null && pmInfoEdit.pmName != null && pmInfoEdit.pmName != '' && orderInfo.status == 7">
						<font color="red"><s:property value="pmInfoEdit.pmName" /></font>
					</s:if>
					<s:else>
						<s:property value="pmInfo.pmName" />
					</s:else>
				</div>
				<div class="point"></div>
				<div class="caption">物理机型号：</div>
				<div class="content">
					<s:property value="pmInfo.serverType" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">处理器类型：</div>
				<div class="content">
					<s:property value="pmInfo.cpuType" />
				</div>
				<div class="point"></div>
				<div class="caption">系统类型：</div>
				<div class="content">
					<s:property value="pmInfo.isoName" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">内存(RAM)：</div>
				<div class="content">
					<s:property value="pmInfo.ramSize" />
					M
				</div>
				<div class="point"></div>
				<div class="caption">存储空间：</div>
				<div class="content">
					<s:property value="pmInfo.discSize" />
					G
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">备注：</div>
				<div class="content">
					<s:property value="pmInfo.description" />
				</div>
				<div class="point"></div>
			</div>
			<div id="detail_tab" style="margin-left: 4%; width: 760px;">
				<ul class="detail_tab">
					<li class="selected"><a>网卡信息</a></li>
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
								<tbody>
									<tr>
										<th class="nl">网卡</th>
										<th>VLAN</th>
										<th>IP段</th>
										<th>IP</th>
										<th>网关</th>
									</tr>
									<s:iterator value="pmInfo.netList">
										<tr
											<s:if test="flag != 'old' && orderInfo.status == 7">style="color: red;"</s:if>>
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
				</div>
			</div>
		</s:elseif>
		<s:elseif test="orderInfo.caseType == 4">
			<div class="field">
				<div class="caption">任务名称：</div>
				<div class="content">
					<s:property value="bkInfo.vmBakName" />
				</div>
				<div class="point"></div>
				<div class="caption">虚拟机名称：</div>
				<div class="content">
					<s:property value="bkInfo.vmName" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">任务开始时间：</div>
				<div class="content">
					<s:property value="bkInfo.backupStartTime" />
				</div>
				<div class="point"></div>
				<div class="caption">备份周期：</div>
				<div class="content">
					<s:if test="bkInfo.backupCyc == 1">
							每天
						</s:if>
					<s:elseif test="bkInfo.backupCyc == 7">
							每周
						</s:elseif>
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">任务保留时间(天)：</div>
				<div class="content">
					<s:property value="bkInfo.backStoreTime" />
				</div>
				<div class="point"></div>
				<div class="caption">备注：</div>
				<div class="content">
					<s:property value="bkInfo.description" />
				</div>
				<div class="point"></div>
			</div>
		</s:elseif>
		<s:elseif test="orderInfo.caseType == 5">
			<s:if test="ebsInfo.standardId != null && ebsInfo.standardId != ''">
				<div class="field">
					<div class="caption">条目名称：</div>
					<div class="content">
						<s:property value="ebsInfo.itemName" />
					</div>
					<div class="point"></div>
				</div>
			</s:if>
			<div class="field">
				<div class="caption">硬盘容量：</div>
				<div class="content">
					<s:if test="orderInfo.status == 7">
						<font color="red"><s:property value="ebsInfoEdit.diskSize/1024" />T</font>
					</s:if>
					<s:else>
						<s:property value="ebsInfo.diskSize/1024" />T
					</s:else>
				</div>
				<div class="point"></div>
				<div class="caption">名称：</div>
				<div class="content">
					<s:property value="ebsInfo.ebsName" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">硬盘类型：</div>
				<div class="content">
					<s:if test="ebsInfo.resourceType == 1">云硬盘</s:if>
					<s:else>物理硬盘</s:else>
				</div>
			</div>
		</s:elseif>
		<s:elseif test="orderInfo.caseType == 12">
			<div class="field">
				<div class="caption">名称：</div>
				<div class="content">
					<s:property value="pipInfo.ipsegmentDesc" />
				</div>
				<div class="point"></div>
				<div class="caption">IP个数：</div>
				<div class="content">
					<s:property value="pipInfo.ipTotal" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">地址：</div>
				<div class="content" style="width: 850px;">
					<s:property value="pipInfo.startToEndIp" />
				</div>
				<div class="point"></div>
			</div>
	<!--	<div class="field">
				<div class="caption">地址：</div>
				<div class="content">
					<s:property value="pipInfo.startToEndIp" />
				</div>
				<div class="point"></div>
				<div class="caption">IP个数：</div>
				<div class="content">
					<s:property value="pipInfo.ipTotal" />
				</div>
				<div class="point"></div>
			</div> -->
		</s:elseif>
		<s:elseif test="orderInfo.caseType == 13">
			<div class="field">
				<div class="caption">名称：</div>
				<div class="content">
					<s:property value="vlanInfo.vlanName" />
				</div>
				<div class="point"></div>
			</div>
		</s:elseif>
		<s:elseif test="orderInfo.caseType == 17">
			<div class="field">
				<div class="caption">名称：</div>
				<div class="content">
				    <s:if test="orderInfo.status == 7">
				      <s:if test="vlanSdnInfoEdit.vlanName == null or vlanSdnInfoEdit.vlanName=='' ">
				        <s:property value="vlanSdnInfo.vlanName" />
					  </s:if>
					  <s:else>
					  <font color="red"><s:property value="vlanSdnInfoEdit.vlanName" /></font>
					</s:else>
					</s:if>
					<s:else>
					  <s:property value="vlanSdnInfo.vlanName" />
					</s:else>
				</div>
				<div class="point"></div>
				<div class="caption">vlan范围：</div>
				<div class="content">
					<s:if test="orderInfo.status == 7">
				      <s:if test='vlanSdnInfoEdit.startId =="" or vlanSdnInfoEdit.startId == null '>
				        <s:property value="vlanSdnInfo.startId" />
					  </s:if>
					  <s:else>
					    <font color="red"><s:property value="vlanSdnInfoEdit.startId" /></font>
					  </s:else>-
					  <s:if test='vlanSdnInfoEdit.endId =="" or vlanSdnInfoEdit.endId == null '>
				        <s:property value="vlanSdnInfo.endId" />
					  </s:if>
					  <s:else>
					    <font color="red"><s:property value="vlanSdnInfoEdit.endId" /></font>
					  </s:else>
					</s:if>
					<s:else>
					  <s:property value="vlanSdnInfo.startId" />-<s:property value="vlanSdnInfo.endId" />
					</s:else>
				</div>
				<div class="point"></div>
			</div>
		</s:elseif>
		<div>
			<s:if
				test="orderInfo.lengthTime !=null && orderInfo.lengthTime!='' && orderInfo.lengthTime >= 0">
				<div class="field">
					<div class="caption">时长：</div>
					<div class="content">
						<s:if test="orderInfo.lengthTime > 0">
							<s:property value="orderInfo.lengthTime" />
							<span id="lengthUnit"><s:property
									value="orderInfo.lengthUnit" /></span>
						</s:if>
						<s:else>
								无限期
							</s:else>
					</div>
					<div class="point"></div>
					<s:if
						test="orderInfo.lengthTime > 0 && (orderInfo.expireTime !=null && orderInfo.expireTime!='')">
						<div class="caption">到期时间：</div>
						<div class="content">
							<s:property value="orderInfo.expireTime" />
						</div>
						<div class="point"></div>
					</s:if>
				</div>
			</s:if>
			<div class="field">
				<div class="caption">申请人：</div>
				<div class="content">
					<s:property value="orderInfo.createUser" />
				</div>
				<div class="point"></div>
				<div class="caption">申请时间：</div>
				<div class="content">
					<s:property value="orderInfo.createTime" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">变更人：</div>
				<div class="content">
					<s:property value="orderInfo.updateUser" />
				</div>
				<div class="point"></div>
				<div class="caption">变更时间：</div>
				<div class="content">
					<s:property value="orderInfo.updateTime" />
				</div>
				<div class="point"></div>
			</div>
		</div>
		<div class="section"></div>
		<div>
			<div class="field">
				<div class="caption">审批意见：</div>
				<div class="content">
					<textarea cols="40" rows="3" id="auditInfo"></textarea>
				</div>
				<div class="point"></div>
			</div>
		</div>
		<div class="page-button">
			<ul class="opt-bottom-btn">
				<li><a href="javascript:void(0);"
					onclick="audit('1',${orderInfo.caseType},${orderInfo.status});return false;">通过</a></li>
				<li><a href="javascript:void(0);"
					onclick="audit('2',${orderInfo.caseType},${orderInfo.status});return false;">不通过</a></li>
				<li><a href="ordersAuditList.action" id="cancel">取消</a></li>
			</ul>
		</div>
	</div>
</div>
<!-- form end -->
<script type="text/javascript"
		src="../scripts/pagesjs/vault/vault_creat_forHomePage.js"></script>
<script type="text/javascript"
	src="../scripts/pagesjs/task/orders_detail.js"></script>
