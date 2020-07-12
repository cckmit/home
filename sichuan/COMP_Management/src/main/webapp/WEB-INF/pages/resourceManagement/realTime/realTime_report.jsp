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
var typeNameForChart;//统计类型名称
var lastTime;
//左侧Javascript代码
$(function () {
	var staType = '<s:property value="staType"/>';
	if('CPU' == staType){
		typeNameForChart = "CPU";
		
		$("#cpuLi").addClass("BussHardsoftFourc");
		$("#memLi").addClass("");
		$("#diskLi").addClass("");
	}else if('MEM' == staType){
		typeNameForChart = "内存";
		
		$("#cpuLi").addClass("");
		$("#memLi").addClass("BussHardsoftFourc");
		$("#diskLi").addClass("");
	}
	$("#chart")[0] = new Charts.Chart({
		chart : {
			renderTo : 'chart', //加载chart的控件id
			defaultSeriesType : 'spline',
			backgroundColor : '#fcfcfc',
			style : {
				fontFamily : 'Microsoft YaHei', // default font
				fontSize : '12px'
			},
			events: {
				load: function() {
					var series = this.series[0];
					
					//初始化
					var actionStr = '/realTimeList.action';//CPU、内存列表数据
					var realTimeTbodyId = "realTimeTbody";//列表tbody id 
					actionStr +="?deviceId="+'<s:property value="deviceId"/>'+"&deviceType="+'<s:property value="deviceType"/>'+"&staType="+'<s:property value="staType"/>';
					getRealTimeData(actionStr,'<s:property value="staType"/>',realTimeTbodyId,series);
					
					setInterval(function() {
						var actionStr = '/realTimeList.action';//CPU、内存列表数据
						var realTimeTbodyId = "realTimeTbody";//列表tbody id 
						actionStr +="?deviceId="+'<s:property value="deviceId"/>'+"&deviceType="+'<s:property value="deviceType"/>'+"&staType="+'<s:property value="staType"/>';
						getRealTimeData(actionStr,'<s:property value="staType"/>',realTimeTbodyId,series);
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
				text : typeNameForChart +'使用率(%)',
				style:{ "color": "#878787"}
			},
			max : 100,
			min : 0
		},
        tooltip: {
        	formatter: function() {
                return '<b>'+ Charts.dateFormat('%H:%M:%S', this.x) +'</b><br/>'+
                			this.series.name +': '+ this.y  +"%";
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
        series: [{
            name: typeNameForChart +'使用率',
            data: readResData(),
            color:'#7cb5ec'
        }]
    });
	
	function readResData(){
		var data = [];
		var ifsetmin = true;
		var ifsetmax = true;
		var minyear = '<s:property value="realTime.minyear" />';
		var minmonth = '<s:property value="realTime.minmonth" />';
		var minday = '<s:property value="realTime.minday" />';
		var minhour = '<s:property value="realTime.minhour" />';
		var mintime = (new Date(minyear,minmonth,minday,minhour,'00','00')).getTime()+(8*60*60*1000);//加上8时区
		
		var maxyear = '<s:property value="realTime.maxyear" />';
		var maxmonth = '<s:property value="realTime.maxmonth" />';
		var maxday = '<s:property value="realTime.maxday" />';
		var maxhour = '<s:property value="realTime.maxhour" />';
		var maxtime = (new Date(maxyear,maxmonth,maxday,maxhour,'00','00')).getTime()+(8*60*60*1000);//加上8时区
		
		<s:iterator value="realTimeList">
			var year = '<s:property value="year" />';
			var month = '<s:property value="month" />';
			var day = '<s:property value="day" />';
			var hour = '<s:property value="hour" />';
			var minute = '<s:property value="minute" />';
			var second = '<s:property value="second" />';
			var usedPer = '<s:property value="usedPer" />';
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
		</s:iterator>
		if(ifsetmin){
			data.push({ x: mintime+(60*60*1000), y: null });
		}
		if(ifsetmax){
			data.push({ x: maxtime, y: null });
		}
		return data;
	};
	
});	
</script>
<div class="details-con">
	<jsp:include page="realTime_top.jsp"></jsp:include>
	<div class="BussUse PerformancHight" >
      <ul class="PerformanceData" id="realTimeTbody">
	      <s:if test="realTime.usedPer != null">
		    	<s:if test="staType == 'CPU'">
		    		<li><p><s:property value="realTime.usedPer"/>%</p><p style="font-size:12px; font-weight:normal;line-height:20px;color:#666;">CPU使用率</p></li>
			        <!-- <li><p><s:property value="realTime.cpuSysTime"/>%</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">系统百分比</p></li>
			        <li><p><s:property value="realTime.cpuUserTime"/>%</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">用户百分比</p></li>
			        <li><p><s:property value="realTime.cpuIdleTime"/>%</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">等待百分比</p></li> -->
		    	</s:if>
		    	<s:else>
		    		<li><p><s:property value="realTime.usedPer"/>%</p><p style="font-size:12px; font-weight:normal;line-height:20px;color:#666;">内存使用率</p></li>
			        <!-- <li><p><s:property value="realTime.sysMemUsedPer"/>%</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">系统内存使用率</p></li>
			        <li><p><s:property value="realTime.userMemUsedPer"/>%</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">用户内存使用率</p></li> -->
			        <li><p><s:property value="realTime.memFree"/>(GB)</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">可用内存量</p></li>
		    	</s:else>
		    </s:if>
		    <s:else>
		    	<s:if test="staType == 'CPU'">
		    		<li><p>&nbsp;</p><p style="font-size:12px; font-weight:normal;line-height:20px;color:#666;">CPU使用率</p></li>
			        <!-- <li><p>&nbsp;</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">系统百分比</p></li>
			        <li><p>&nbsp;</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">用户百分比</p></li>
			        <li><p>&nbsp;</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">等待百分比</p></li> -->
		    	</s:if>
		    	<s:else>
		    		<li><p>&nbsp;</p><p style="font-size:12px; font-weight:normal;line-height:20px;color:#666;">内存使用率</p></li>
			        <!-- <li><p>&nbsp;</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">系统内存使用率</p></li>
			        <li><p>&nbsp;</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">用户内存使用率</p></li> -->
			        <li><p>&nbsp;</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">可用内存量</p></li>
		    	</s:else>
		    </s:else>
        </ul>
     </div>
     <div class="BussPmgTabBg" style="height: 290px;">
          <div class="PerformanceChart" style="height: 270px;background:#fcfcfc">
          		<div id="chart" style="height: 240px;margin-top: 30px;margin-left: 1px;"></div> 
          </div>
     </div>
	<div>&nbsp;</div>
</div>
<div class="page-button">&nbsp;</div>  