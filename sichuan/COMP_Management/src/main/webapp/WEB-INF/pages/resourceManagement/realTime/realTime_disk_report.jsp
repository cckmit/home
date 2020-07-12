<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1><s:if test="deviceType == 'VM'">虚拟机</s:if><s:else>物理机</s:else></h1>
<div id="message" style="display: none;">
	<span id="error" class="error"><s:actionerror /></span>
	<span class="error"><s:fielderror /></span>
	<span class="success"><s:actionmessage /></span>
</div>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../scripts/lib/charts/charts.js"></script>
<script type="text/javascript" src="../scripts/pagesjs/resourceManagement/getRealTimeData.js"></script>
<script>
var deviceId;//设备Id
var lastTime;
var chartRead;
var chartWrite;
//左侧Javascript代码
$(function () {
	$("#cpuLi").addClass("");
	$("#memLi").addClass("");
	$("#diskLi").addClass("BussHardsoftFourc");
	chartRead = new Charts.Chart({
		chart : {
			renderTo : 'chartRead', //加载chart的控件id
			defaultSeriesType : 'spline',
			backgroundColor : '#fcfcfc',
			style : {
				fontFamily : 'Microsoft YaHei', // default font
				fontSize : '12px'
			},
			events: {
				load: function() {
					setInterval(function() {
						var actionStr2 = '/realTimeDiskList.action';//磁盘列表传参
						var realTimeTbodyId = "realTimeTbody";//列表tbody id 
						actionStr2 +="?deviceId="+'<s:property value="deviceId"/>'+"&deviceType="+'<s:property value="deviceType"/>';
						getRealTimeDiskReadList(actionStr2,'<s:property value="staType"/>','<s:property value="deviceType"/>',realTimeTbodyId);
				    }, 5 * 60 * 1000);//5分钟
					//}, 10 * 1000);//10秒
				}
			}
		},
        title: {
            text: ''
        },
        xAxis: {
        	type: 'datetime',
            dateTimeLabelFormats: { // don't display the dummy year
                minute: '%H:%M'
            },
            startOnTick: true,
            tickInterval: 3600 * 1000 //坐标间隔1小时
        },
        yAxis : {
			title : {
				text : '每张磁盘读请求字节数(KB/s)',
				style:{ "color": "#878787"}
			},
			max : maxReadData(),
			min : 0
		},
        tooltip: {
        	formatter: function() {
                return '<b>'+ Charts.dateFormat('%H:%M:%S', this.x) +'</b><br/>磁盘：'+this.series.name+'<br/>'+
                		'读请求字节数' +': '+ this.y  +"(KB/s)";
            }
        },
        plotOptions: {
			spline: {
				marker: {
					enabled: false,
					symbol: 'circle',
					radius: 2,
					states: {
						hover: {
							enabled: true
						}
					}
				}
			}
		},
		series: seriesReadData()
    });
	
	chartWrite = new Charts.Chart({
		chart : {
			renderTo : 'chartWrite', //加载chart的控件id
			defaultSeriesType : 'spline',
			backgroundColor : '#fcfcfc',
			style : {
				fontFamily : 'Microsoft YaHei', // default font
				fontSize : '12px'
			},
			events: {
				load: function() {
					setInterval(function() {
						var actionStr2 = '/realTimeDiskList.action';//磁盘列表传参
						var realTimeTbodyId = "realTimeTbody";//列表tbody id 
						actionStr2 +="?deviceId="+'<s:property value="deviceId"/>'+"&deviceType="+'<s:property value="deviceType"/>';
						getRealTimeDiskWriteList(actionStr2,'<s:property value="staType"/>','<s:property value="deviceType"/>',realTimeTbodyId);
					}, 5 * 60 * 1000);//5分钟
					//}, 10 * 1000);//10秒
				}
			}
		},
        title: {
            text: ''
        },
        xAxis: {
        	type: 'datetime',
            dateTimeLabelFormats: { // don't display the dummy year
                minute: '%H:%M'
            },
            startOnTick: true,
            tickInterval: 3600 * 1000 //坐标间隔1小时
        },
        yAxis : {
			title : {
				text : '每张磁盘写请求字节数(KB/s)',
				style:{ "color": "#878787"}
			},
			max : maxWriteData(),
			min : 0
		},
        tooltip: {
        	formatter: function() {
                return '<b>'+ Charts.dateFormat('%H:%M:%S', this.x) +'</b><br/>磁盘：'+this.series.name+'<br/>'+
                		'写请求字节数' +': '+ this.y  +"(KB/s)";
            }
        },
        plotOptions: {
			spline: {
				marker: {
					enabled: false,
					symbol: 'circle',
					radius: 2,
					states: {
						hover: {
							enabled: true
						}
					}
				}
			}
		},
		series: seriesWriteData()
    });
	
    function maxReadData(){
    	return '<s:property value="realTimeRead.maxRead" />';
    }
    
    function maxWriteData(){
    	return '<s:property value="realTimeWrite.maxWrite" />';
    }
    
	function seriesNullData(){
		var series = [];
		var data = [];
		<s:iterator value="timeList">
			var year = '<s:property value="year" />';
			var month = '<s:property value="month" />';
			var day = '<s:property value="day" />';
			var hour = '<s:property value="hour" />';
			pointHour = (new Date(year,month,day,hour,'00','00')).getTime()+(8*60*60*1000);//加上8时区
			data.push({ x: pointHour, y: null });
		</s:iterator>
		series.push({ name: '无磁盘', data: data  });
		return series;
	}
	
	function seriesReadData(){
		<s:if test="num == 0">
			return seriesNullData();
		</s:if>
		<s:else>
			var series = [];
			var ifsetmin = true;
			var ifsetmax = true;
			var minyear = '<s:property value="realTimeRead.minyear" />';
			var minmonth = '<s:property value="realTimeRead.minmonth" />';
			var minday = '<s:property value="realTimeRead.minday" />';
			var minhour = '<s:property value="realTimeRead.minhour" />';
			var mintime = (new Date(minyear,minmonth,minday,minhour,'00','00')).getTime()+(8*60*60*1000);//加上8时区
			
			var maxyear = '<s:property value="realTimeRead.maxyear" />';
			var maxmonth = '<s:property value="realTimeRead.maxmonth" />';
			var maxday = '<s:property value="realTimeRead.maxday" />';
			var maxhour = '<s:property value="realTimeRead.maxhour" />';
			var maxtime = (new Date(maxyear,maxmonth,maxday,maxhour,'00','00')).getTime()+(8*60*60*1000);//加上8时区
	
			var y_axisData_read = <s:property value="y_axisData_read" />;
			for(var i=0;i<y_axisData_read.length;i++){
				var data = [];
				var diskObj = y_axisData_read[i];
				var diskname = diskObj.name;
				var diskdata = diskObj.data;
	
				for(var j=0;j<diskdata.length;j++){
					var dataObj = diskdata[j];
					var year = dataObj.year;
					var month = dataObj.month;
					var day = dataObj.day;
					var hour = dataObj.hour;
					var minute = dataObj.minute;
					var second = dataObj.second;
					var usedPer = dataObj.usedPer;
					if(usedPer == ""){
						usedPer = null;
					}
					lastTime = (new Date(year,month,day,hour,minute,second)).getTime()+(8*60*60*1000);//加上8时区
					pointHour = (new Date(year,month,day,hour,'00','00')).getTime()+(8*60*60*1000);//加上8时区
					if(mintime == pointHour){
						ifsetmin = false;
					}
					if(maxtime == pointHour){
						ifsetmax = false;
					}
					data.push({ x: lastTime, y: usedPer });
				}
				if(ifsetmin){
					data.push({ x: mintime+(60*60*1000), y: null });
				}
				if(ifsetmax){
					data.push({ x: maxtime, y: null });
				}
				series.push({ name: diskname, data: data  });
			}
			return series;
		</s:else>
	}
	
	function seriesWriteData(){
		<s:if test="num == 0">
			return seriesNullData();
		</s:if>
		<s:else>
			var series = [];
			var ifsetmin = true;
			var ifsetmax = true;
			var minyear = '<s:property value="realTimeWrite.minyear" />';
			var minmonth = '<s:property value="realTimeWrite.minmonth" />';
			var minday = '<s:property value="realTimeWrite.minday" />';
			var minhour = '<s:property value="realTimeWrite.minhour" />';
			var mintime = (new Date(minyear,minmonth,minday,minhour,'00','00')).getTime()+(8*60*60*1000);//加上8时区
			
			var maxyear = '<s:property value="realTimeWrite.maxyear" />';
			var maxmonth = '<s:property value="realTimeWrite.maxmonth" />';
			var maxday = '<s:property value="realTimeWrite.maxday" />';
			var maxhour = '<s:property value="realTimeWrite.maxhour" />';
			var maxtime = (new Date(maxyear,maxmonth,maxday,maxhour,'00','00')).getTime()+(8*60*60*1000);//加上8时区
	
			var y_axisData_write = <s:property value="y_axisData_write" />;
			for(var i=0;i<y_axisData_write.length;i++){
				var data = [];
				var diskObj = y_axisData_write[i];
				var diskname = diskObj.name;
				var diskdata = diskObj.data;
	
				for(var j=0;j<diskdata.length;j++){
					var dataObj = diskdata[j];
					var year = dataObj.year;
					var month = dataObj.month;
					var day = dataObj.day;
					var hour = dataObj.hour;
					var minute = dataObj.minute;
					var second = dataObj.second;
					var usedPer = dataObj.usedPer;
					if(usedPer == ""){
						usedPer = null;
					}
					lastTime = (new Date(year,month,day,hour,minute,second)).getTime()+(8*60*60*1000);//加上8时区
					pointHour = (new Date(year,month,day,hour,'00','00')).getTime()+(8*60*60*1000);//加上8时区
					if(mintime == pointHour){
						ifsetmin = false;
					}
					if(maxtime == pointHour){
						ifsetmax = false;
					}
					data.push({ x: lastTime, y: usedPer });
				}
				if(ifsetmin){
					data.push({ x: mintime+(60*60*1000), y: null });
				}
				if(ifsetmax){
					data.push({ x: maxtime, y: null });
				}
				series.push({ name: diskname, data: data  });
			}
			return series;
		</s:else>
	}
	
});	
</script>
<div class="details-con">
	<jsp:include page="realTime_top.jsp"></jsp:include>
	
	<div class="BussUse PerformancHight" >
      <ul class="PerformanceData" id="realTimeTbody">
	      	<s:if test="diskUsedPer == null">
				<li><p>&nbsp;</p><p style="font-size:12px; font-weight:normal;line-height:20px;color:#666;">磁盘使用率</p></li>
			</s:if>
			<s:else>
				<li><p><s:property value="diskUsedPer"/>%</p><p style="font-size:12px; font-weight:normal;line-height:20px;color:#666;">磁盘使用率</p></li>
			</s:else>
        </ul>
     </div>
     <div class="BussPmgTabBg" style="height: 290px;">
          <div class="PerformanceChart" style="height: 270px;background:#fcfcfc">
          		<div id="chartRead" style="height: 240px;margin-top: 30px;margin-left: 1px;"></div> 
          </div>
     </div>
     <div>&nbsp;</div>
     <div class="BussPmgTabBg" style="height: 290px;">
          <div class="PerformanceChart" style="height: 270px;background:#fcfcfc">
          		<div id="chartWrite" style="height: 240px;margin-top: 30px;margin-left: 1px;"></div> 
          </div>
     </div>
	<div>&nbsp;</div>
</div>
<div class="page-button">&nbsp;</div>  