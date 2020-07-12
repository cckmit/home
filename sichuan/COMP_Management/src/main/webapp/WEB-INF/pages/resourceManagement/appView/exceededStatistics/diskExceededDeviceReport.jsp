<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../scripts/pagesjs/resourceManagement/getExceededList.js"></script>
<script type="text/javascript">
	var axis;
	var series;

	$(function() {
		perChart = <s:property value="perChart"/>;
		numChart = <s:property value="numChart"/>;

		/*缩短分页元素间的间距*/
		$('.pageBar b').css('margin', '0 10px');

		/****************************chartPer DEMO begin**********************************************/
		$("#chartPer")[0] = new Charts.Chart({
			chart : {
				renderTo : 'chartPer', /*加载chart的控件id*/
				defaultSeriesType : 'column',
				backgroundColor : '#fcfcfc',
				style : {
					fontFamily : 'Microsoft YaHei', // default font
					fontSize : '12px'
				}
			},
			title : {
				text : '<b>Top10<b>',
				style : {
					fontFamily : 'Microsoft YaHei', // default font
					fontSize : '16px'
				}
			},
			xAxis : {
				categories : perChart[0],
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
					text : '设备数占比',
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
					s += this.series.name + ': ' + this.point.y + '台<br/>';
					s += '占比: ' + this.point.per + '%';
					return s;
				}
			},
			plotOptions : {
				column : {
					stacking : 'percent',
					dataLabels : {
						enabled : true,
						formatter : function() {
							if (this.percentage != 0) {
								return Math.round(this.percentage) + '%';
							} else {
								return '';
							}
						},
						color : (Charts.theme && Charts.theme.dataLabelsColor) || 'white'
					}
				}
			},
			/* legend : {
				margin : 20
			}, */
			series : perChart[1]
		});
		/****************************chartPer DEMO end************************************************/

		/****************************chartInt DEMO begin**********************************************/
		$("#chartInt")[0] = new Charts.Chart({
			chart : {
				renderTo : 'chartInt', /*加载chart的控件id*/
				defaultSeriesType : 'column',
				backgroundColor : '#fcfcfc',
				style : {
					fontFamily : 'Microsoft YaHei', // default font
					fontSize : '12px'
				}
			},
			title : {
				text : '<b>Top10<b>',
				style : {
					fontFamily : 'Microsoft YaHei', // default font
					fontSize : '16px'
				}
			},
			xAxis : {
				categories : numChart[0],
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
					text : '设备数（台）',
					style : {
						fontFamily : 'Microsoft YaHei', // default font
						fontSize : '12px'
					}
				},
				stackLabels : {
					enabled : true,
					style : {
						fontWeight : 'bold',
						color : (Charts.theme && Charts.theme.textColor) || 'gray'
					}
				}
			},
			tooltip : {
				formatter : function() {
					var s;
					s = '<b>' + this.x + '</b><br/>';
					s += this.series.name + ': ' + this.point.y + '台<br/>';
					s += '总数: ' + this.point.stackTotal + '台';
					return s;
				}
			},
			plotOptions : {
				column : {
					stacking : 'normal',
					dataLabels : {
						enabled : true,
						formatter : function() {
							if (this.y != 0) {
								return this.y;
							} else {
								return '';
							}
						},
						color : (Charts.theme && Charts.theme.dataLabelsColor) || 'white'
					}
				}
			},
			/* legend : {
				margin : 20
			}, */
			series : numChart[1]
		});
		/****************************chartInt DEMO end************************************************/
		getExceededData('/diskExceededPerListByAjax.action?deviceOverInfo.deviceType=' + '<s:property value="deviceOverInfo.deviceType" />',
				'ExceededTbody', 'pageBarPerDiv', PerTbodyAccess);

		getExceededData('/diskExceededNumListByAjax.action?deviceOverInfo.deviceType=' + '<s:property value="deviceOverInfo.deviceType" />',
				'ExceededTbody2', 'pageBarNumDiv', NumTbodyAccess);
	});
</script>
</head>
<body>

	<s:hidden id="deviceType" name="deviceOverInfo.deviceType"></s:hidden>
	<s:hidden id="performanceType" name="deviceOverInfo.performanceType"></s:hidden>

	<h1>所有业务组统计</h1>
	<ul class="opt-btn fr" style="margin-top: 23px;">
        <li>
        	 <s:url id="exportAllAppReportUrl" action="exportAllAppReportAction"></s:url>
			 <a class="exportReport" href="${exportAllAppReportUrl}">导出</a>
        </li>
    </ul>
	<!-- 概况 -->
	<div class="details-con">
		<!-- 标题栏-->
		<div class="BussTitle">
			<a
				href="../resourceManagement_appView/cpuExceededAction.action?deviceOverInfo.deviceType=2"><div
					id="performanceDiv" class="BussTitlefocus1">性能统计</div></a> <a
				href="../resourceManagement_appView/appViewAllDeviceNumAction.action"><div
					id="deviceNumDiv" class="BussTitle2">设备数统计</div></a>
			<div class="BussTitleButton">
				<a id="pmUrl" href="#" onclick="setDeviceTypeUrl('2')"><div id="pmDiv" class="">物理机</div></a>
				<a id="vmUrl" href="#" onclick="setDeviceTypeUrl('3')"><div id="vmDiv" class="">虚拟机</div></a>
				<!-- <a id="miniPmUrl" href="#" onclick="setDeviceTypeUrl('0')"><div id="miniPmDiv" class="">小型机</div></a>
				<a id="miniPmParUrl" href="#" onclick="setDeviceTypeUrl('1')"><div id="miniPmParDiv" class="">小型机分区</div></a> -->
			</div>
		</div>
		<div class="BussHardsoft">
			<ul>
				<li id="cpuLi"><a id="cpuUrl" href="#" onclick="setPerformanceUrl('cpu')">CPU</a></li>
				<li id="memLi"><a id="memUrl" href="#" onclick="setPerformanceUrl('mem')">内存</a></li>
				<li id="diskLi"><a id="diskUrl" href="#" onclick="setPerformanceUrl('disk')">磁盘</a></li>
			</ul>
			<div class="BussIllustrate">
				
			</div>
		</div>

		<!-- ***************************************第一个图表********************************************* -->
		<div class="BussUse">
			<div class="BussUseTitl">
				<b>各业务组磁盘使用率</b>-按设备数占比统计
			</div>
			<!-- <a href="#"> <div class="BussUseExport"> <p class="BussUseExportImg"><img  src="../images/BussExportIcon.png"  /></p>导出报表</div></a> -->
		</div>

        <div class="BussPmgTabBg" style="border-bottom:0px">
			<div class="BussPmg" style="width:98%;">
				<div id="chartPer" style="height: 100%"></div>
			</div>
		</div>
		<div class="BussPmgTabBg">
			<div class="table-sta">
				<table class="BussTable" cellpadding="0" cellspacing="0">
					<col style="width: 25%;" />
					<col style="width: 25%;" />
					<col style="width: 25%;" />
					<col style="width: 25%;" />
					<tr>
						<th rowspan="2">业务组名称</th>
						<th colspan="3">占比</th>
					</tr>
					<tr>
						<th>使用率>70%</th>
						<th>70%>=使用率>30%</th>
						<th>30%>=使用率</th>
					</tr>
					<tbody id="ExceededTbody">
					</tbody>
				</table>
				<div id="pageBarPerDiv" class="pageBarReport" style="float:left; margin-left:200px;"></div>
			</div>
		</div>

		<!-- ***************************************第二个图表********************************************* -->
		<div class="BussUse">
			<div class="BussUseTitl">
				<b>各业务组磁盘使用率</b>-按设备数统计
			</div>
			<!-- <a href="#"> <div class="BussUseExport"> <p class="BussUseExportImg"><img  src="../images/BussExportIcon.png"  /></p>导出报表</div></a> -->
		</div>

		<div class="BussPmgTabBg" style="border-bottom:0px">
			<div class="BussPmg" style="width:98%;">
				<div id="chartInt" style="height: 100%"></div>
			</div>
		</div>
		<div class="BussPmgTabBg">
			<div class="table-sta">
				<table class="BussTable" cellpadding="0" cellspacing="0">
					<col style="width: 25%;" />
					<col style="width: 15%;" />
					<col style="width: 20%;" />
					<col style="width: 20%;" />
					<col style="width: 20%;" />
					<tr>
						<th rowspan="2">业务组名称</th>
						<th rowspan="2">总数（台）</th>
						<th colspan="3">设备数（台）</th>
					</tr>
					<tr>
					    <th>使用率>70%</th>
						<th>70%>=使用率>30%</th>
						<th>30%>=使用率</th>
					</tr>
					<tbody id="ExceededTbody2">
					</tbody>
				</table>
				<div id="pageBarNumDiv" class="pageBarReport" style="float:left; margin-left:200px;"></div>
			</div>
		</div>
		<div>&nbsp;</div>
	</div>
</body>
</html>