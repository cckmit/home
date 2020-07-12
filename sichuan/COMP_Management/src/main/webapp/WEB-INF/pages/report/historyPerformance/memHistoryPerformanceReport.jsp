<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../scripts/pagesjs/report/report.js"></script>
<script type="text/javascript">

	$(function() {
		/* 展示图表 */
		chartData = <s:property value="chartData"/>;
		
		$("#chart")[0] = new Charts.Chart({
			chart : {
				renderTo : 'chart', /*加载chart的控件id*/
				defaultSeriesType : 'spline',
				backgroundColor : '#fcfcfc',
				style : {
					fontFamily : 'Microsoft YaHei', // default font
					fontSize : '12px'
				}
			},
			title : {
				text : '<b>内存使用率统计<b>',
				style : {
					fontFamily : 'Microsoft YaHei', // default font
					fontSize : '16px'
				}
			},
			xAxis : {
				categories : chartData[0],
				labels : {
					rotation : -20,
					align : 'right',
					style : {
						fontFamily : 'Microsoft YaHei', // default font
						fontSize : '12px'
					}
				}
			},
			yAxis : {
				title : {
					text : '内存使用率',
					style : {
						fontFamily : 'Microsoft YaHei', // default font
						fontSize : '12px'
					}
				},
				max : 100
			},
			tooltip : {
				formatter : function() {
					var s;
					s = '<b>' + this.x + '</b><br/>';
					s += this.series.name + ': ' + this.point.y + '%<br/>';
					if (this.series.name != "平均使用率") {
						s += '设备ID: ' + this.point.deviceId + '<br/>';
						s += '可用内存: ' + this.point.freeAve + '（GB）<br/>';
					}
					return s;
				}
			},

			series : chartData[1]
		});

		/* ajax取得列表信息 */
        var deviceType = '<s:property value="historyPerformanceInfo.deviceType" />';
        var performanceType = '<s:property value="historyPerformanceInfo.performanceType" />';
        var staFlag = '<s:property value="historyPerformanceInfo.staFlag" />';
        var appId = '<s:property value="historyPerformanceInfo.appId" />';
        var startDate = '<s:property value="historyPerformanceInfo.startDate" />';
        var endDate = '<s:property value="historyPerformanceInfo.endDate" />';
		var ajaxActionStr = '/memHistoryPerformanceByAjax.action'
		                  + '?historyPerformanceInfo.deviceType=' + deviceType
		                  + '&historyPerformanceInfo.performanceType=' + performanceType
		                  + '&historyPerformanceInfo.staFlag=' + staFlag
		                  + '&historyPerformanceInfo.appId=' + appId
		                  + '&historyPerformanceInfo.startDate=' + startDate
		                  + '&historyPerformanceInfo.endDate=' + endDate;
		getListData(ajaxActionStr);
	});

</script>
</head>
<body>
	<h1><s:property value="historyPerformanceInfo.deviceTypeName" />业务性能统计</h1>
	
	<!-- 概况 -->
	<div class="details-con">
		<!-- 标题栏-->
		<div class="BussTitle">
			<a href="../report/memHistoryPerformanceAction.action?historyPerformanceInfo.deviceType=<s:property value="historyPerformanceInfo.deviceType" />&historyPerformanceInfo.performanceType=<s:property value="historyPerformanceInfo.performanceType" />">
			    <div id="performanceStaDiv" class="BussTitlefocus1">使用率统计</div>
			</a> 
			<a href="../report/memHistoryPerformanceOverAction.action?historyPerformanceInfo.deviceType=<s:property value="historyPerformanceInfo.deviceType" />&historyPerformanceInfo.performanceType=<s:property value="historyPerformanceInfo.performanceType" />">
			    <div id="performanceOverStaDiv" class="BussTitle2">使用率超标设备统计</div>
			</a>
			<div class="BussTitleButton" style="width:350px">
				<a id="cpuUrl" href="#" onclick="setPerformanceUrl('cpu')"><div id="cpuDiv" class="">CPU</div></a>
				<a id="memUrl" href="#" onclick="setPerformanceUrl('mem')"><div id="memDiv" class="">内存</div></a>
				<a id="diskUrl" href="#" onclick="setPerformanceUrl('disk')"><div id="diskDiv" class="">磁盘</div></a>
			</div>
		</div>
		
		<div class="BussHardsoft">
		    <s:form id="searchForm" action="memHistoryPerformanceAction.action" method="post">
		    <s:hidden id="deviceType" name="historyPerformanceInfo.deviceType"></s:hidden>
	        <s:hidden id="performanceType" name="historyPerformanceInfo.performanceType"></s:hidden>
	        <s:hidden id="staFlag" name="historyPerformanceInfo.staFlag" value="performance"></s:hidden>
	
		    <div style="margin-top:20px; float:left; width:70%">
			    业务组名称：
			    <s:select id="appId" name="historyPerformanceInfo.appId"
			    list="appList" listKey="appId" listValue="appName" 
			    headerKey = "" headerValue = "-请选择 -"/>
			   查询日期：
			    <s:textfield id="startDate" name="historyPerformanceInfo.startDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'});" cssClass="Wdate wdatelong" size="15" readonly="true"/>
			    至
				<s:textfield id="endDate" name="historyPerformanceInfo.endDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'});" cssClass="Wdate wdatelong" size="15" readonly="true"/>
			
			    &nbsp;&nbsp;&nbsp;&nbsp;<a class="reportSearchBtn" href="#" onclick="searchReport();">查询</a>
			</div>
			</s:form>
			<div class="BussIllustrate">
				
			</div>
		</div>

		<div class="BussPmgTabBg" style="border-bottom:0px">
			<div class="BussPmg" style="width:98%;">
				<div id="chart" style="height: 100%"></div>
			</div>
		</div>
		<s:if test=""></s:if>
		<div class="BussPmgTabBg">
			<div class="table-sta">
				<table id="listTable" class="BussTable" cellpadding="0" cellspacing="0">
					<col style="width: 10%;" />
					<col style="width: 10%;" />
					<col style="width: 10%;" />
					<col style="width: 20%;" />
					<col style="width: 10%;" />
					<col style="width: 10%;" />
					<col style="width: 20%;" />
					<col style="width: 10%;" />
					<tr>
						<th rowspan="2">日期</th>
						<th rowspan="2">平均使用率</th>
						<th colspan="3">最大使用率</th>
						<th colspan="3">最小使用率</th>
					</tr>
					<tr>
						<th>使用率</th>
						<th>设备ID</th>
						<th>可用内存（GB）</th>
						<th>使用率</th>
						<th>设备ID</th>
						<th>可用内存（GB）</th>
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