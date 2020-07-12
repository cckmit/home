<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style>
</style>

<div class="list">
	<div class="title">
		<h2>告警</h2>
		<div id="message" style="display: none;">
			<span id="error" class="error"><s:actionerror /></span> 
			<span class="error"><s:fielderror /></span> 
			<span class="success"><s:actionmessage /></span>
		</div>
	</div>
	<!-- <div class="tool-bar">
		<div class="search-text">
			<label> 状态：</label>
			<input id="waitAudit" type="checkbox" checked="checked" onclick="queryOrder();" /><label>待审批</label> 
			<input id="auditPass" type="checkbox" checked="checked"	onclick="queryOrder();" /><label>已通过 </label>
		</div>
	</div> -->

	<div class="tool-bar">

		<div class="search-text">
			<img class="search-ico" src="../img/ico-search.png" /> 
			<input class="search-text" type="text" size="10" 
				   id="vmIp" name="vmIp" maxlength="50"
				   value="<s:property value="rid" />"
				   style="width: auto;" placeholder="输入虚拟机IP" />
		</div>
		
		<a class="search-btn" href="javascript:;" onclick="queryAlarm();">查询</a>

	</div>
<!-- 列表 -->
	<div id="approval-will" class="list-items">
		<table id="alarmtable" width="100%" border="0" cellspacing="0"
			cellpadding="0">
			<thead>
				<tr>
					<th>告警IP</th>
					<th>告警时间</th>
					<th>告警内容</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="alarms">
					<tr class="iterator">
						<td class="cut app"><s:property value="rid" /></td>
						<td class="cut app"><s:property value="eventTime" /></td>
						<td class="cut app"><s:property value="text" /></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</div>
	<!-- 翻页 -->
	<div class="pageBar">
		<s:property value="pageBar" escape="false" />
	</div>

	<script type="text/javascript" src="../scripts/pagesjs/alarmCenter/alarmlist/alarm_list.js"></script>