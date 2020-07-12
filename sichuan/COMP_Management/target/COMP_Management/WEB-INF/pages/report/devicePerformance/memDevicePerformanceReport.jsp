<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../scripts/pagesjs/report/memDeviceReport.js"></script>
<script type="text/javascript">

	$(function() {
		var deviceTypeName = '<s:property value="devicePerformanceInfo.deviceTypeName" />';
		/* 展示图表 */
		chartData = <s:property value="chartData"/>;
		
		
		var deviceType = '<s:property value="devicePerformanceInfo.deviceType" />';
		var chartTitle = "";
		var yAxisTitle = "";
		var unit = "";
		if(deviceType =='3'){ // 阵列统计的是写吞吐量
			chartTitle = '<b>TOP10-' + deviceTypeName + '写吞吐量平均值<b>';
			yAxisTitle = deviceTypeName + '读吞吐量平均值(字节)';
			unit = '字节';
		} else if(deviceType =='41' || deviceType =='51'){ // 交换机和路由器端口显示丢包数
			chartTitle = '<b>TOP10-' + deviceTypeName + '端口丢包数平均值<b>';
			yAxisTitle = deviceTypeName + '端口丢包数平均值';
		} else{
			chartTitle = '<b>TOP10-' + deviceTypeName + '内存平均使用率<b>';
			yAxisTitle = deviceTypeName + '内存平均使用率(%)';
			unit = '%';
		}
		
		
		$("#chart")[0] = new Charts.Chart({
			chart : {
				renderTo : 'chart', /*加载chart的控件id*/
				defaultSeriesType : 'column',
				backgroundColor : '#fcfcfc',
				style : {
					fontFamily : 'Microsoft YaHei', // default font
					fontSize : '12px'
				}
			},
			title : {
				text : chartTitle,
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
					text : yAxisTitle,
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
					s += this.series.name + ': ' + this.point.y + unit + '<br/>';
// 					if (this.series.name != "cpu平均使用率") {
// 						s += '设备名字: ' + this.point.pmName + '<br/>';
// 					}
					return s;
				}
			},

			series : chartData[1]
		});

		/* ajax取得列表信息 */
		var timeType = '<s:property value="devicePerformanceInfo.timeType" />';
        if(deviceType == '3'){ // 阵列
        	performanceType = 'diskWriteBytes';
        } else if(deviceType =='41' || deviceType =='51'){ // 交换机和路由器端口显示丢包数
        	performanceType = 'discards';
		} else{
        	performanceType = 'mem';
        }
		var ajaxActionStr = '/cpuDevicePerformanceByAjax.action'
		                  + '?devicePerformanceInfo.deviceType=' + deviceType
		                  + '&devicePerformanceInfo.performanceType=' + performanceType
		                  + '&devicePerformanceInfo.timeType=' + timeType;

		getListData(ajaxActionStr,timeType,deviceType);
		
		
		InitTimeType(timeType);
		Initmenu(deviceType);
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
			    <div id="performanceStaDiv" class="<s:if test="devicePerformanceInfo.deviceType == 41 || devicePerformanceInfo.deviceType == 51">BussTitle1</s:if><s:else>BussTitlefocus1</s:else> "><s:if test="devicePerformanceInfo.deviceType == 4 || devicePerformanceInfo.deviceType == 5 || devicePerformanceInfo.deviceType == '41' || devicePerformanceInfo.deviceType == '51'"><s:property value="devicePerformanceInfo.deviceTypeName" /></s:if>Top10</div>
			</a> 
			<s:if test="devicePerformanceInfo.deviceType == 4 || devicePerformanceInfo.deviceType == 5 || devicePerformanceInfo.deviceType == '41' || devicePerformanceInfo.deviceType == '51'">
				<a href="../report/cpuDeviceTop10PerformanceAction.action?devicePerformanceInfo.deviceType=<s:if test="devicePerformanceInfo.deviceType == 4 || devicePerformanceInfo.deviceType == 41">41</s:if><s:else>51</s:else>&devicePerformanceInfo.timeType=1">
				    <div id="performanceStaDiv" class="<s:if test="devicePerformanceInfo.deviceType == 4 || devicePerformanceInfo.deviceType == 5">BussTitle2</s:if><s:else>BussTitlefocus2</s:else>">端口Top10</div>
				</a> 
			</s:if>
			<a href="../report/performanceSearchAction.action?devicePerformanceInfo.deviceType=<s:if test="devicePerformanceInfo.deviceType == 41">4</s:if><s:elseif test="devicePerformanceInfo.deviceType == 51">5</s:elseif><s:else><s:property value="devicePerformanceInfo.deviceType" /></s:else>">
			    <div id="performanceSearchStaDiv" class="BussTitle2">性能统计查询</div>
			</a>
			<div class="BussTitleButton" style="width:350px">
				<s:if test="devicePerformanceInfo.deviceType == 3"> <!-- 阵列 -->
					<a id="cpuUrl" href="#" class="BussTitleButtonLoseFocus" onclick="setPerformanceUrl('<s:property value="devicePerformanceInfo.deviceType" />', '1', 'cpu')"><div id="cpuDiv" class="">读吞吐量</div></a>
					<a id="memUrl" href="#" class="BussTitleButtonFocus" onclick="setPerformanceUrl('<s:property value="devicePerformanceInfo.deviceType" />', '1', 'mem')"><div id="memDiv" class="">写吞吐量</div></a>
				</s:if>
				<s:elseif test="devicePerformanceInfo.deviceType == 2"> <!-- 防火墙 -->
					<a id="cpuUrl" href="#" class="BussTitleButtonLoseFocus" onclick="setPerformanceUrl(<s:property value="devicePerformanceInfo.deviceType" />, '1', 'cpu')"><div id="cpuDiv" class="">CPU</div></a>
					<a id="memUrl" href="#" class="BussTitleButtonFocus" onclick="setPerformanceUrl(<s:property value="devicePerformanceInfo.deviceType" />, '1', 'mem')"><div id="memDiv" class="">内存</div></a>
					<a id="hstUrl" href="#" class="BussTitleButtonLoseFocus" onclick="setPerformanceUrl(<s:property value="devicePerformanceInfo.deviceType" />, '1', 'hst')"><div id="hstDiv" class="">吞吐量</div></a>
				</s:elseif>
				<s:elseif test="devicePerformanceInfo.deviceType == 41"><!-- 交换机端口 -->
					<a id="cpuUrl" href="#" class="BussTitleButtonLoseFocus" onclick="setPerformanceUrl(<s:property value="devicePerformanceInfo.deviceType" />, '1', 'cpu')"><div id="cpuDiv" class="">包总数</div></a>
					<a id="memUrl" href="#" class="BussTitleButtonFocus" onclick="setPerformanceUrl(<s:property value="devicePerformanceInfo.deviceType" />, '1', 'mem')"><div id="memDiv" class="">丢包数</div></a>
					<a id="hstUrl" href="#" class="BussTitleButtonLoseFocus" onclick="setPerformanceUrl(<s:property value="devicePerformanceInfo.deviceType" />, '1', 'hst')"><div id="hstDiv" class="">字节总数</div></a>
				</s:elseif>
				<s:elseif test="devicePerformanceInfo.deviceType == 51"><!-- 路由器端口 -->
					<a id="cpuUrl" href="#" class="BussTitleButtonFocus" onclick="setPerformanceUrl(<s:property value="devicePerformanceInfo.deviceType" />, '1', 'cpu')"><div id="cpuDiv" class="">包总数</div></a>
					<a id="memUrl" href="#" class="BussTitleButtonLoseFocus" onclick="setPerformanceUrl(<s:property value="devicePerformanceInfo.deviceType" />, '1', 'mem')"><div id="memDiv" class="">丢包数</div></a>
				</s:elseif>
				<s:else>
					<a id="cpuUrl" href="#" class="BussTitleButtonLoseFocus" onclick="setPerformanceUrl('<s:property value="devicePerformanceInfo.deviceType" />', '1', 'cpu')"><div id="cpuDiv" class="">CPU</div></a>
					<a id="memUrl" href="#" class="BussTitleButtonFocus" onclick="setPerformanceUrl('<s:property value="devicePerformanceInfo.deviceType" />', '1', 'mem')"><div id="memDiv" class="">内存</div></a>
				</s:else>
			</div>
		</div>
		
		<div class="BussHardsoft">
		    <s:form id="searchForm" method="post">
			
			<div style="margin-top:20px; float:left; width:70%">
			  <div style="float:left;" >粒度：</div>
			 <a id="1hour" href="#" onclick="searchTimeTypeReport(<s:property value="devicePerformanceInfo.deviceType" />, '1');">1小时</a>
			 <a id="24hours" href="#" onclick="searchTimeTypeReport(<s:property value="devicePerformanceInfo.deviceType" />, '24');">24小时</a>
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
					<s:if test="devicePerformanceInfo.deviceType == 2">
						<col style="width: 25%;" />
						<col style="width: 25%;" />
						<col style="width: 10%;" />
						<col style="width: 10%;" />
						<col style="width: 10%;" />
						<col style="width: 20%;" />
					</s:if>
					<s:elseif test="devicePerformanceInfo.deviceType == 51">
						<col style="width: 20%;" />
						<col style="width: 20%;" />
						<col style="width: 15%;" />
						<col style="width: 15%;" />
						<col style="width: 15%;" />
						<col style="width: 15%;" />
					</s:elseif>
					<s:elseif test="devicePerformanceInfo.deviceType == 41">
						<col style="width: 20%;" />
						<col style="width: 20%;" />
						<col style="width: 15%;" />
						<col style="width: 10%;" />
						<col style="width: 10%;" />
						<col style="width: 10%;" />
						<col style="width: 15%;" />
					</s:elseif>
					<s:else>
					    <col style="width: 30%;" />
						<col style="width: 25%;" />
						<col style="width: 15%;" />
						<col style="width: 15%;" />
						<col style="width: 15%;" />
					</s:else>
					<tr>
						<s:if test="devicePerformanceInfo.deviceType == 3">
                        	<th><s:property value="devicePerformanceInfo.deviceTypeName" />名称</th>
							<th><s:property value="devicePerformanceInfo.deviceTypeName" />ID</th>
							<th>读吞吐量</th>
							<th>写吞吐量</th>
						</s:if>
						<s:elseif test="devicePerformanceInfo.deviceType == 2">
							<th><s:property value="devicePerformanceInfo.deviceTypeName" />名称</th>
							<th><s:property value="devicePerformanceInfo.deviceTypeName" />ID</th>
							<th>CPU使用率</th>
							<th>内存使用率</th>
							<th>吞吐量</th>
						</s:elseif>
						<s:elseif test="devicePerformanceInfo.deviceType == 41">
							<th><s:property value="devicePerformanceInfo.deviceTypeName" />端口名称</th>
							<th><s:property value="devicePerformanceInfo.deviceTypeName" />端口ID</th>
							<th><s:property value="devicePerformanceInfo.deviceTypeName" />名称</th>
							<th>包总数</th>
							<th>丢包数</th>
							<th>字节总数</th>
						</s:elseif>
						<s:elseif test="devicePerformanceInfo.deviceType == 51">
							<th><s:property value="devicePerformanceInfo.deviceTypeName" />端口名称</th>
							<th><s:property value="devicePerformanceInfo.deviceTypeName" />端口ID</th>
							<th><s:property value="devicePerformanceInfo.deviceTypeName" />名称</th>
							<th>包总数</th>
							<th>丢包数</th>
						</s:elseif>
						<s:else>
							<th><s:property value="devicePerformanceInfo.deviceTypeName" />名称</th>
							<th><s:property value="devicePerformanceInfo.deviceTypeName" />ID</th>
							<th>CPU使用率</th>
							<th>内存使用率</th>
						</s:else>
						<th>操作</th>
					</tr>
					<tbody id="listTbody">
					</tbody>
				</table>
				<div id="pageBarDiv" class="pageBarReport" style="float:left; margin-left:200px;"></div>
			</div>
		</div>

        <div>&nbsp;</div>
	</div>
	<!-- 性能实时折线图信息 -->
	<s:if test="devicePerformanceInfo.deviceType == 2 || devicePerformanceInfo.deviceType == 41">
		<s:include value="devicePerformanceDetailFW.jsp" />
	</s:if>
	<s:else>
		<s:include value="devicePerformanceDetail.jsp" />
	</s:else>
</body>
</html>