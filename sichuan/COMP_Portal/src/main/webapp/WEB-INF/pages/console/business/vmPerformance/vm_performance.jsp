<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="list">
	<div class="title">
		<h2>虚拟机性能统计</h2>
		<div id="message" style="display: none;">
			<span id="error" class="error"><s:actionerror /></span> <span
				class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
		</div>
		<s:form id="gotoForm">
			<input type="hidden" value="" id="caseId" name="caseId">
			<s:hidden id="nodeType" name="nodeType"></s:hidden>
			<s:hidden id="nodeId" name="nodeId"></s:hidden>
			<s:hidden id="treeNodeName" name="treeNodeName"></s:hidden>
			<s:hidden id="pnodeId" name="pnodeId"></s:hidden>
			<s:hidden id="pnodeName" name="pnodeName"></s:hidden>
			<s:hidden id="appId" name="appId"></s:hidden>
			<s:hidden id="curFun" name="curFun"></s:hidden>
		</s:form>
	</div>
	<div class="btns">
		<s:url id="addUrl" action="createVlanAction3Phase">
			<s:param name="nodeType" value="nodeType" />
			<s:param name="nodeId" value="nodeId" />
			<s:param name="treeNodeName" value="treeNodeName" />
			<s:param name="pnodeId" value="pnodeId" />
			<s:param name="pnodeName" value="pnodeName" />
			<s:param name="appId" value="appId" />
			<s:param name="curFun" value="curFun" />
			<s:param name="businessName" value="businessName" />
			<s:param name="queryBusinessId" value="queryBusinessId" />
		</s:url>

	</div>
	
		<div class="tool-bar">
		<div class="search-text">
			虚拟机IP：<select style="width:200px;background-color: #F2F2F2;color: #000;padding-left:10px;" id="vmPerformanceIp"></select>
			<input type="hidden" id="vmIp" name="vmIp" value="" />
                      
		           查询时间：<input onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				readonly="true" class="Wdate wdatelong" type="text" size="23"
				id="startDate" maxlength="14" style="height:40px;background-color: #F2F2F2;width:210px;padding-left:10px;margin-right: 5px;"
				value=""/>
				 至  <input type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				readonly="true" class="Wdate wdatelong" size="23" id="endDate"
				maxlength="14" style="height:40px;background-color: #F2F2F2;width:210px;padding-left:10px;margin-left:5px;margin-right: 50px;"
				value=""/> 
		</div>
		<a id="btn_select" class="search-btn" href="javascript:;" onclick="searchReport()" >查询</a>
		<a id="exportUrl" class="search-btn" href="" onclick="exportPerformance()" >导出</a>		
	</div>

	   <div id="approval-will" class="list-items">
<!-- 	       <div id="net_tab_info" > -->
					<table id="ordertable" width="100%" border="0" cellspacing="0" cellpadding="0">
						<thead>
							<tr>
							    <th>虚拟机IP</th>
								<th>CPU使用率</th>
								<th>内存使用率</th>
								<th>内存总量</th>
								<th>磁盘使用率</th>
								<th>磁盘总量</th>
								<th>网络入口速度</th>
								<th>网络出口速度</th>
								<th>时间</th>
							</tr>
						</thead>						
						<tbody id="listTbody">
						  <s:iterator value="vmPerformanceList">
					         <tr class="iterator">
						        <td><s:property value="vmIp" /></td>
						        <td><s:property value="cpuUtilization" /></td>
						        <td><s:property value="memUtilization" /></td>
						        <td><s:property value="memTotalKb" /></td>
						        <td><s:property value="diskUtilization" /></td>
						        <td><s:property value="diskTotalG" /></td>
						        <td><s:property value="bytesIn" /></td>
						        <td><s:property value="bytesOut" /></td>
						        <td><s:property value="createTime" /></td>
					</tr>
				</s:iterator>
						</tbody>
					</table>
<!-- 				</div>	 			 -->
			</div>
			
		<div class="pageBar">
		  <s:property value="pageBar" escape="false" />
	    </div>	
						
</div>

<script type="text/javascript"
	src="../scripts/pagesjs/console/business/vmPerformance/vm_performance.js"></script>
<script type="text/javascript"
	src="../scripts/My97DatePicker/WdatePicker.js"></script>
