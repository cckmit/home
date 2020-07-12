<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../scripts/pagesjs/report/performanceSearchReport.js"></script>
<script type="text/javascript">
	$(function() {
		/* 初始化 */
        var deviceType = '<s:property value="devicePerformanceInfo.deviceType" />';
        var deviceId = '<s:property value="devicePerformanceInfo.deviceId" />';
        var startDate = '<s:property value="devicePerformanceInfo.startDate" />';
        var endDate = '<s:property value="devicePerformanceInfo.endDate" />';
        var ajaxActionStr = '/performanceSearchByAjax.action'
		                  + '?devicePerformanceInfo.deviceType=' + deviceType
		                  + '&devicePerformanceInfo.deviceId=' + deviceId
		                  + '&devicePerformanceInfo.startDate=' + startDate
		                  + '&devicePerformanceInfo.endDate=' + endDate;
        getListData(ajaxActionStr);
        $("#startDate").val(_fomart14(startDate));
	    $("#endDate").val(_fomart14(endDate));
	    
	    if(deviceType == 4 || deviceType == 5){
	    	 $("input[name=dtype]:eq(0)").attr("checked",'checked');
	    }else if(deviceType == '41' || deviceType == '51'){
	    	 $("input[name=dtype]:eq(1)").attr("checked",'checked');
	    }
	});
</script>
</head>
<body>
    <h1><s:property value="devicePerformanceInfo.deviceTypeName" />设备性能统计</h1>
	
	<!-- 概况 -->
	<div class="details-con">
		<!-- 标题栏-->
		<div class="BussTitle">
			<a href="../report/cpuDeviceTop10PerformanceAction.action?devicePerformanceInfo.deviceType=<s:if test="devicePerformanceInfo.deviceType == 41">4</s:if><s:elseif test="devicePerformanceInfo.deviceType == 51">5</s:elseif><s:else><s:property value="devicePerformanceInfo.deviceType" /></s:else>&devicePerformanceInfo.timeType=1">
			    <div id="performanceStaDiv" class="BussTitle1"><s:if test="devicePerformanceInfo.deviceType == 4 || devicePerformanceInfo.deviceType == 5 || devicePerformanceInfo.deviceType == '41' || devicePerformanceInfo.deviceType == '51'"><s:property value="devicePerformanceInfo.deviceTypeName" /></s:if>Top10</div>
			</a> 
			<s:if test="devicePerformanceInfo.deviceType == 4 || devicePerformanceInfo.deviceType == 5 || devicePerformanceInfo.deviceType == '41' || devicePerformanceInfo.deviceType == '51'">
				<a href="../report/cpuDeviceTop10PerformanceAction.action?devicePerformanceInfo.deviceType=<s:if test="devicePerformanceInfo.deviceType == 4 || devicePerformanceInfo.deviceType == 41">41</s:if><s:else>51</s:else>&devicePerformanceInfo.timeType=1">
				    <div id="performanceStaDiv" class="BussTitle1">端口Top10</div>
				</a> 
			</s:if>
			<a href="../report/performanceSearchAction.action?devicePerformanceInfo.deviceType=<s:if test="devicePerformanceInfo.deviceType == 41">4</s:if><s:elseif test="devicePerformanceInfo.deviceType == 51">5</s:elseif><s:else><s:property value="devicePerformanceInfo.deviceType" /></s:else>">
			    <div id="performanceSearchStaDiv" class="BussTitlefocus2">性能统计查询</div>
			</a>
		</div>
		<div class="BussHardsoft">
		    <s:hidden id="deviceType" name="devicePerformanceInfo.deviceType"></s:hidden>
			<s:hidden id="deviceTypeName" name="devicePerformanceInfo.deviceTypeName"></s:hidden>
	
		    <div style="margin-top:20px; float:left; width:92%">
		    	<s:if test="devicePerformanceInfo.deviceType == 4 || devicePerformanceInfo.deviceType == 5 || devicePerformanceInfo.deviceType == '41' || devicePerformanceInfo.deviceType == '51'">
		    		设备类型：
		    		<a href="../report/performanceSearchAction.action?devicePerformanceInfo.deviceType=<s:if test="devicePerformanceInfo.deviceType == 4 || devicePerformanceInfo.deviceType == 41">4</s:if><s:else>5</s:else>" style="color:#555555;text-decoration: none;">
		    		<input type="radio" name="dtype" value="<s:property value="devicePerformanceInfo.deviceType" />"/>
		    		<label for="device"><s:property value="devicePerformanceInfo.deviceTypeName" /></label>
		    		</a>&nbsp;
		    		<a href="../report/performanceSearchAction.action?devicePerformanceInfo.deviceType=<s:if test="devicePerformanceInfo.deviceType == 4 || devicePerformanceInfo.deviceType == 41">41</s:if><s:else>51</s:else>" style="color:#555555;text-decoration: none;">
		    		<input type="radio" name="dtype" value="<s:property value="devicePerformanceInfo.deviceType" />"/>
		    		<label for="deviceif"><s:property value="devicePerformanceInfo.deviceTypeName" />端口</label>
		    		</a>
		    		&nbsp;
		    	</s:if>
		    
			    <s:property value="devicePerformanceInfo.deviceTypeName" /><s:if test="devicePerformanceInfo.deviceType == '41' || devicePerformanceInfo.deviceType == '51'">端口</s:if>名称：
			    <s:select id="deviceId" name="devicePerformanceInfo.deviceId"
			    list="deviceList" listKey="deviceId" listValue="deviceName"/>
			   查询时间：
			   <input onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				readonly="true" class="Wdate wdatelong" type="text" size="23"
				id="startDate" maxlength="14" /> 至  <input type="text"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				readonly="true" class="Wdate wdatelong" size="23" id="endDate"
				maxlength="14" />
			    &nbsp;&nbsp;&nbsp;&nbsp;<a class="reportSearchBtn" href="#" onclick="searchReport();">查询</a>
			</div>
			
			<ul class="opt-btn fr" style="margin-top: 23px;width: 8%;float: right;">
		        <li>
					 <a id="exportUrl" class="exportReport" href="" onclick="exportPerformance()">导出</a>
		        </li>
		    </ul>
		</div>
		<div class="BussPmgTabBg">
			<div class="table-sta">
				<table class="BussTable" cellpadding="0" cellspacing="0">
					<s:if test="devicePerformanceInfo.deviceType == 2 ||devicePerformanceInfo.deviceType == '41'">
						<col style="width: 25%;" />
						<col style="width: 25%;" />
						<col style="width: 25%;" />
						<col style="width: 25%;" />
					</s:if>
					<s:else>
						<col style="width: 34%;" />
						<col style="width: 33%;" />
						<col style="width: 33%;" />
					</s:else>
					<tr>
						<th>时间</th>
						<s:if test="devicePerformanceInfo.deviceType == 2">
							<th>CPU使用率</th>
							<th>内存使用率</th>
							<th>吞吐量</th>
						</s:if>
						<s:elseif test="devicePerformanceInfo.deviceType == 3">
							<th>读吞吐量</th>
							<th>写吞吐量</th>
						</s:elseif>
						<s:elseif test="devicePerformanceInfo.deviceType == '41'">
							<th>包总数</th>
							<th>丢包数</th>
							<th>字节总数</th>
						</s:elseif>
						<s:elseif test="devicePerformanceInfo.deviceType == '51'">
							<th>包总数</th>
							<th>丢包数</th>
						</s:elseif>
						<s:else>
							<th>CPU使用率</th>
							<th>内存使用率</th>
						</s:else>
					</tr>
					<tbody id="listTbody">
					</tbody>
				</table>
				<div id="pageBarDiv" class="pageBarReport" style="float:left; margin-left:200px;"></div>
			</div>
		</div>

        <div>&nbsp;</div>
	</div>
</body>
</html>