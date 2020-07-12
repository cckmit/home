<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../styles/base.css" type="text/css" />
<link href="../styles/vh.css" rel="stylesheet" type="text/css" />
<link href="../styles/cs.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="../styles/formStyle.css" />
<link href="../scripts/My97DatePicker/skin/WdatePicker.css"
	rel="stylesheet" type="text/css" />

<script type="text/javascript" src="../scripts/lib/jquery-1.8.3.js"></script>
<script type="text/javascript" src="../scripts/lib/jquery.mooko.tabs.js"></script>
<script type="text/javascript" src="../scripts/lib/left-menu.js"></script>
<%-- <script type="text/javascript"
	src="../scripts/lib/dialog/jquery.artDialog.js?skin=default"></script> --%>
<script type="text/javascript" src="../scripts/lib/jquery.uniform.js"></script>
<script type="text/javascript" src="../scripts/common.js"></script>
<script type="text/javascript" src="../scripts/lib/charts/charts.js"></script>
<script type="text/javascript"
	src="../scripts/pagesjs/console/host/getRealTimePerformanceData.js"></script>
<script>
	var deviceId;//设备Id
	var typeNameForChart;//统计类型名称
	var lastTime;

	//左侧Javascript代码
	$(function() {
		
		function readResData() {
			var data = [];
			var ifsetmin = true;
			var ifsetmax = true;
			var minyear = '<s:property value="realTime.minyear" />';
			var minmonth = '<s:property value="realTime.minmonth" />';
			var minday = '<s:property value="realTime.minday" />';
			var minhour = '<s:property value="realTime.minhour" />';
			var mintime = (new Date(minyear, minmonth, minday, minhour, '00',
					'00')).getTime()
					+ (8 * 60 * 60 * 1000);//加上8时区

			var maxyear = '<s:property value="realTime.maxyear" />';
			var maxmonth = '<s:property value="realTime.maxmonth" />';
			var maxday = '<s:property value="realTime.maxday" />';
			var maxhour = '<s:property value="realTime.maxhour" />';
			var maxtime = (new Date(maxyear, maxmonth, maxday, maxhour, '00',
					'00')).getTime()
					+ (8 * 60 * 60 * 1000);//加上8时区

			<s:iterator value="realTimeList">
			var year = '<s:property value="year" />';
			var month = '<s:property value="month" />';
			var day = '<s:property value="day" />';
			var hour = '<s:property value="hour" />';
			var minute = '<s:property value="minute" />';
			var second = '<s:property value="second" />';
			var usedPer = '<s:property value="usedPer" />';
			if (usedPer == "") {
				usedPer = null;
			}
			lastTime = (new Date(year, month, day, hour, minute, second))
					.getTime()
					+ (8 * 60 * 60 * 1000);//加上8时区
			pointHour = (new Date(year, month, day, hour, '00', '00'))
					.getTime()
					+ (8 * 60 * 60 * 1000);//加上8时区
			if (mintime == pointHour) {
				ifsetmin = false;
			}
			if (maxtime == pointHour) {
				ifsetmax = false;
			}
			data.push({
				x : lastTime,
				y : usedPer
			});
			</s:iterator>
			if (ifsetmin) {
				data.push({
					x : mintime + (60 * 60 * 1000),
					y : null
				});
			}
			if (ifsetmax) {
				data.push({
					x : maxtime,
					y : null
				});
			}
			return data;
		}
		;

		var data = readResData();
		
		var ymin = 0;
		var ymax = 0;		
		for(var i = 0;i<data.length;i++){
			var result = data[i];
			if(i==0){
				ymin=parseFloat(result["y"]);
				ymax=parseFloat(result["y"]);
			}
			if(ymax<parseFloat(result["y"])){
				ymax = parseFloat(result["y"]);
			}
			if(ymin>parseFloat(result["y"])){
				ymin = parseFloat(result["y"]);
			}
			ymax = Math.ceil(ymax);
			ymin = Math.floor(ymin);
		}

		var staType = '<s:property value="staType"/>';
		if ('CPU' == staType) {
			typeNameForChart = "CPU";

			$("#cpuLi").addClass("BussHardsoftFourc");
			$("#memLi").addClass("");
			$("#diskLi").addClass("");
		} else if ('MEM' == staType) {
			typeNameForChart = "内存";

			$("#cpuLi").addClass("");
			$("#memLi").addClass("BussHardsoftFourc");
			$("#diskLi").addClass("");
		}
		
		$("#chart")[0] = new Charts.Chart(
				{
					chart : {
						renderTo : 'chart', //加载chart的控件id
						defaultSeriesType : 'spline',
						backgroundColor : '#fcfcfc',
						style : {
							fontFamily : 'Microsoft YaHei', // default font
							fontSize : '12px'
						},
						events : {
							load : function() {
								var series = this.series[0];
								
								//初始化
								var actionStr = '/COMP_Portal/console/hostRealTimePerformanceJson.action';//CPU、内存列表数据
								var realTimeTbodyId = "realTimeTbody";//列表tbody id 
								actionStr += "?deviceId="
										+ '<s:property value="deviceId"/>'
										+ "&deviceType="
										+ '<s:property value="deviceType"/>'
										+ "&staType="
										+ '<s:property value="staType"/>';
								getRealTimeData(actionStr,
										'<s:property value="staType"/>',
										realTimeTbodyId, series);
								
								setInterval(
										function() {
											var actionStr = '/COMP_Portal/console/hostRealTimePerformanceJson.action';//CPU、内存列表数据
											var realTimeTbodyId = "realTimeTbody";//列表tbody id 
											actionStr += "?deviceId="
													+ '<s:property value="deviceId"/>'
													+ "&deviceType="
													+ '<s:property value="deviceType"/>'
													+ "&staType="
													+ '<s:property value="staType"/>';
											getRealTimeData(actionStr,
													'<s:property value="staType"/>',
													realTimeTbodyId, series);
										}, 5 * 60 * 1000);//5分钟
								//}, 10 * 1000);//10秒
							}
						}
					},
					title : {
						text : ''
					},
					xAxis : {
						type : 'datetime',
						dateTimeLabelFormats : { // don't display the dummy year
							minute : '%H:%M'
						},
						labels : {
							rotation : -45,
							align : 'right'
						},
						startOnTick : true,
						tickInterval : 3600 * 1000
					//坐标间隔1小时
					},
					yAxis : {
						title : {
							text : typeNameForChart + '使用率(%)',
							style : {
								"color" : "#878787"
							}
						},
 						max : ymax ,
						min : ymin 
					},
					tooltip : {
						formatter : function() {
							return '<b>'
									+ Charts.dateFormat('%H:%M:%S', this.x)
									+ '</b><br/>' + this.series.name + ': '
									+ this.y + "%";
						}
					},
					plotOptions : {
						spline : {
							marker : {
								enabled : false,
								symbol : 'circle',
								radius : 2,
								states : {
									hover : {
										enabled : true
									}
								}
							}
						}
					},
					series : [ {
						name : typeNameForChart + '使用率',
						data : data,
						color : '#7cb5ec'
					} ]
				});
	
	});
</script>
<div class="details-con">
	<div class="BussHardsoft">
		<ul>
			<li id="cpuLi"><s:url id="cpuUrl" action="hostRealTimePerformance">
					<s:param name="deviceId">${deviceId}</s:param>
					<s:param name="staType">CPU</s:param>
					<s:param name="deviceType">${deviceType}</s:param>
					<s:param name="nodeType">${nodeType}</s:param>
					<s:param name="nodeId">${nodeId}</s:param>
					<s:param name="nodeName">${nodeName}</s:param>
					<s:param name="pnodeId">${pnodeId}</s:param>
					<s:param name="pnodeName">${pnodeName}</s:param>
					<s:param name="appId">${appId}</s:param>
					<s:param name="curFun">${curFun}</s:param>
					<s:param name="me">me</s:param>
				</s:url> <a id="cpuUrl" href="${cpuUrl}">CPU</a></li>
			<li id="memLi"><s:url id="memUrl" action="hostRealTimePerformance">
					<s:param name="deviceId">${deviceId}</s:param>
					<s:param name="staType">MEM</s:param>
					<s:param name="deviceType">${deviceType}</s:param>
					<s:param name="nodeType">${nodeType}</s:param>
					<s:param name="nodeId">${nodeId}</s:param>
					<s:param name="nodeName">${nodeName}</s:param>
					<s:param name="pnodeId">${pnodeId}</s:param>
					<s:param name="pnodeName">${pnodeName}</s:param>
					<s:param name="appId">${appId}</s:param>
					<s:param name="curFun">${curFun}</s:param>
					<s:param name="me">me</s:param>
				</s:url> <a id="memUrl" href="${memUrl}">内存</a></li>
			<%-- 
	    <li id="diskLi" >
	    	<s:url id="diskUrl" action="realTimeDiskReport">
				<s:param name="deviceId">${deviceId}</s:param>
				<s:param name="deviceType">${deviceType}</s:param>
				<s:param name="nodeType">${nodeType}</s:param>
				<s:param name="nodeId">${nodeId}</s:param>
				<s:param name="nodeName">${nodeName}</s:param>
				<s:param name="pnodeId">${pnodeId}</s:param>
				<s:param name="pnodeName">${pnodeName}</s:param>
				<s:param name="appId">${appId}</s:param>
				<s:param name="curFun">${curFun}</s:param>
			</s:url>
			<a id="diskUrl" href="${diskUrl}">磁盘</a>
	    </li>
	     --%>
		</ul>
	</div>
	<div class="PerformancHight">
		<ul class="PerformanceData"  style="margin-top: 10px;" id="realTimeTbody">
			
		</ul>
	</div>
	<div class="BussPmgTabBg" style="height: 300px;">
		<div class="PerformanceChart" style="height: 270px;background:#fcfcfc">
			<div id="chart"
				style="height: 240px;margin-top: 30px;margin-left: 1px;"></div>
		</div>
	</div>
	<div>&nbsp;</div>
</div>