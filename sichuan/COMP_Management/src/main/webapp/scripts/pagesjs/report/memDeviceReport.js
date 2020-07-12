function InitTimeType(timeType) {
	if (timeType == "1") {
		$("#1hour").addClass("BussTitleButtonFocus");
		$("#24hours").addClass("BussTitleButtonLoseFocus");
	}
	if(timeType == "24"){
		$("#1hour").addClass("BussTitleButtonLoseFocus");
		$("#24hours").addClass("BussTitleButtonFocus");

	}
}

function Initmenu(deviceType) {

    $(".left-menu li:contains('设备性能统计')").addClass("selected").next().show();

    if (deviceType == "0") {
    	$(".left-menu dl a:contains('物理机')").parent().addClass("selected");
    } else if (deviceType == "1") {
    	$(".left-menu dl a:contains('虚拟机')").parent().addClass("selected");
    } else if (deviceType == "2") {
    	$(".left-menu dl a:contains('防火墙')").parent().addClass("selected");
    } else if (deviceType == "3") {
    	$(".left-menu dl a:contains('阵列')").parent().addClass("selected");
    } else if (deviceType == "4"||deviceType == "41") {
    	$(".left-menu dl a:contains('交换机')").parent().addClass("selected");
    } else if (deviceType == "5"||deviceType == "51") {
    	$(".left-menu dl a:contains('路由器')").parent().addClass("selected");
    } 

    $("#head-menu li a:contains('统计分析')").parent().addClass("head-btn-sel");

    $(":text").uniform();
}

var obj = window.location;
var contextPath = obj.pathname.split("/")[1] + "/" + obj.pathname.split("/")[2]; 
var basePath = obj.protocol + "//"+obj.host + "/" + contextPath;

/**
 * 获取性能列表数据 
 * historyPerformanceInfolist 为ajax返回的统一的List名
 */
function getListData(actionStr,timeType,deviceType){
    var url = basePath + actionStr;
	if($("#right h1").text().indexOf("物理机")!=-1){//若为物理机
		deviceType="0";
	}else if($("#right h1").text().indexOf("虚拟机")!=-1){//若为虚拟机
		deviceType="1";
	}
	$.ajax({
	    type : "GET",
	    url : url,
	    data:{},
	    success : function(data){
			$('#listTbody').empty();
			
			setTbody(data,timeType,deviceType);
			
			$('#pageBarDiv').html(data.pageBar);
			
			/* 页面上有多个select，因为ajax刷新，需要重置样式，要不然会不断的在非点击的select外加新的div */
			$("select").uniform();
			$.uniform.restore();
			
			/* 追加新的样式，在select外新加一个div样式  */
			$("select").uniform();
			$.uniform.update();
	    },
	    error: function(data){
			var mes = fieldErrors(data.fieldErrors);
			if(mes!=''){
				$.compMsg({type:'error',msg:mes});
			}
			if(data.resultMessage!=null){
				if(data.resultFlage=='success'){
					backList(data.resultMessage,'');
					flage = false;
				}else if(data.resultFlage=='failure'){
					$.compMsg({type:'error',msg:data.resultMessage}); 
				}else if(data.resultFlage=='error'){
					$.compMsg({type:'error',msg:data.resultMessage}); 
				}
			}
	    },
	    dataType:"json"
	}); 
};

/**
 * 百分比 - 处理ajax传来的数据
 */
function setTbody(data,timeType,deviceType) {
	$.each(data.devicePerformanceInfolist, function(index) {
		if(deviceType == '3'){
			$('<tr>').appendTo('#listTbody')
			.append('<td>' + this.deviceName + '</td> ')
			.append('<td>' + this.deviceId+ '</td>')
			.append('<td>' + this.hstDiskReadBytes + '字节</td>')
			.append('<td>' + this.hstDiskWriteBytes + '字节</td> ')
			.append('<td><a href="javascript:void(0);" onclick="detail(\'' + this.deviceId + '\',\'' + deviceType +  '\',\'' + this.deviceName +'\');return false;">查看实时数据</a></td>');
		} else if (deviceType == '2'){
			$('<tr>').appendTo('#listTbody')
			.append('<td>' + this.deviceName + '</td> ')
			.append('<td>' + this.deviceId+ '</td>')
			.append('<td>' + this.cpuProcessorUtilization + '%</td> ')
			.append('<td>' + this.memUsedPer + '%</td>')
			.append('<td>' + this.throughput + '</td>')
			.append('<td><a href="javascript:void(0);" onclick="detail(\'' + this.deviceId + '\',\'' + deviceType +  '\',\'' + this.deviceName +'\');return false;">查看实时数据</a></td>');
		} else if (deviceType == '41'){
			$('<tr>').appendTo('#listTbody')
			.append('<td>' + this.deviceName + '</td> ')
			.append('<td>' + this.deviceId+ '</td>')
			.append('<td>' + this.deviceParentName + '</td> ')
			.append('<td>' + this.pkts + '</td> ')
			.append('<td>' + this.discards + '</td>')
			.append('<td>' + this.octets + '</td>')
			.append('<td><a href="javascript:void(0);" onclick="detail(\'' + this.deviceId + '\',\'' + deviceType +  '\',\'' + this.deviceName +'\');return false;">查看实时数据</a></td>');
		} else if (deviceType == '51'){
			$('<tr>').appendTo('#listTbody')
			.append('<td>' + this.deviceName + '</td> ')
			.append('<td>' + this.deviceId+ '</td>')
			.append('<td>' + this.deviceParentName + '</td> ')
			.append('<td>' + this.pkts + '</td> ')
			.append('<td>' + this.discards + '</td>')
			.append('<td><a href="javascript:void(0);" onclick="detail(\'' + this.deviceId + '\',\'' + deviceType +  '\',\'' + this.deviceName +'\');return false;">查看实时数据</a></td>');
		} else{
			$('<tr>').appendTo('#listTbody')
			.append('<td>' + this.deviceName + '</td> ')
			.append('<td>' + this.deviceId+ '</td>')
			.append('<td>' + this.cpuProcessorUtilization + '%</td> ')
			.append('<td>' + this.memUsedPer + '%</td>')
			.append('<td><a href="javascript:void(0);" onclick="detail(\'' + this.deviceId + '\',\'' + deviceType +  '\',\'' + this.deviceName +'\');return false;">查看实时数据</a></td>');
		}
	});

	while ($('#listTbody' + ' tr').length < 10) {
		if (deviceType == '2' || deviceType == '51'){
			$('#listTbody').append('<tr>')
			.append('<td>&nbsp;</td>')
			.append('<td>&nbsp;</td>')
			.append('<td>&nbsp;</td>')
			.append('<td>&nbsp;</td>')
			.append('<td>&nbsp;</td>')
			.append('<td>&nbsp;</td>')
			.append('</tr>');
		}else if (deviceType == '41'){
			$('#listTbody').append('<tr>')
			.append('<td>&nbsp;</td>')
			.append('<td>&nbsp;</td>')
			.append('<td>&nbsp;</td>')
			.append('<td>&nbsp;</td>')
			.append('<td>&nbsp;</td>')
			.append('<td>&nbsp;</td>')
			.append('<td>&nbsp;</td>')
			.append('</tr>');
		}else{
			$('#listTbody').append('<tr>')
			.append('<td>&nbsp;</td>')
			.append('<td>&nbsp;</td>')
			.append('<td>&nbsp;</td>')
			.append('<td>&nbsp;</td>')
			.append('<td>&nbsp;</td>')
			.append('</tr>');
		}
	}
}

/**
 * 实时性能折线图，周期刷新数据
 * @param actionStr
 * @param series
 * @param performanceType
 */
function getRealTimeData(actionStr, series, performanceType){
    url = basePath + actionStr;
	$.ajax({
	    type : "GET",
	    url : url,
	    data:{},
	    success : function(data){
	    	if(performanceType == 'cpu'){
	    		resdata = readResData(data,'cpu');
			}else if(performanceType == 'mem'){
				resdata = readResData(data,'mem');
			}else if(performanceType == 'diskReadBytes'){
				resdata = readResData(data,'diskReadBytes');
			}else if(performanceType == 'diskWriteBytes'){
				resdata = readResData(data,'diskWriteBytes');
			}else if(performanceType == 'pkts'){
				resdata = readResData(data,'pkts');
			}else if(performanceType == 'discards'){
				resdata = readResData(data,'discards');
			}else if(performanceType == 'octets'){
				resdata = readResData(data,'octets');
			}else if(performanceType == 'throughput'){
				resdata = readResData(data,'throughput');
			}
	    	
			series.setData(resdata,true,true,true);
	    },
	    error: function(data){
			var mes = fieldErrors(data.fieldErrors);
			if(mes!=''){
				$.compMsg({type:'error',msg:mes});
			}
			if(data.resultMessage!=null){
				if(data.resultFlage=='success'){
					backList(data.resultMessage,'');
					flage = false;
				}else if(data.resultFlage=='failure'){
					$.compMsg({type:'error',msg:data.resultMessage}); 
				}else if(data.resultFlage=='error'){
					$.compMsg({type:'error',msg:data.resultMessage}); 
				}
			}
	    },
	    dataType:"json"
	}); 
};

/**
 * 实时性能折线图，处理ajax应答数据
 * @param obj
 * @param performanceType
 * @returns {Array}
 */
function readResData(data,performanceType){
	var resdata = [];
	var obj = data.realTime;
	var ifsetmin = true;
	var ifsetmax = true;
	var minyear = obj.minyear;
	var minmonth = obj.minmonth;
	var minday = obj.minday;
	var minhour = obj.minhour;
	var mintime = (new Date(minyear,minmonth,minday,minhour,'00','00')).getTime()+(8*60*60*1000);//加上8时区
	
	var maxyear = obj.maxyear;
	var maxmonth = obj.maxmonth;
	var maxday = obj.maxday;
	var maxhour = obj.maxhour;
	var maxtime = (new Date(maxyear,maxmonth,maxday,maxhour,'00','00')).getTime()+(8*60*60*1000);//加上8时区
	
	
	$.each(data.realTimeList,function(index){
		var year = this.year;
		var month = this.month;
		var day = this.day;
		var hour = this.hour;
		var minute = this.minute;
		var second = this.second;
		if(performanceType == 'cpu'){
			var usedPer = this.cpuProcessorUtilization;
		}else if(performanceType == 'mem'){
			var usedPer = this.memUsedPer;
		}else if(performanceType == 'diskReadBytes'){
			var usedPer = this.hstDiskReadBytes;
		}else if(performanceType == 'diskWriteBytes'){
			var usedPer = this.hstDiskWriteBytes;
		}else if(performanceType == 'pkts'){
			var usedPer = this.pkts;
		}else if(performanceType == 'discards'){
			var usedPer = this.discards;
		}else if(performanceType == 'octets'){
			var usedPer = this.octets;
		}else if(performanceType == 'throughput'){
			var usedPer = this.throughput;
		}
		
		lastTime = (new Date(year,month,day,hour,minute,second)).getTime()+(8*60*60*1000);//加上8时区
		pointHour = (new Date(year,month,day,hour,'00','00')).getTime()+(8*60*60*1000);//加上8时区
		if(mintime == pointHour){
			ifsetmin = false;
		}
		if(maxtime == pointHour){
			ifsetmax = false;
		}
		resdata.push({ x: lastTime, y: usedPer });
		if(ifsetmin){
			resdata.push({ x: mintime+(60*60*1000), y: null });
		}
		if(ifsetmax){
			resdata.push({ x: maxtime, y: null });
		}
	});
	return resdata;
}


// 实时性能折线图，dialog
function detail(deviceId, deviceType,deviceName) {

    var queryData = {
    	deviceId : deviceId,
    	deviceType : deviceType
    };
    $.ajax({
	type : "POST",
	url : 'devicePerformanceRTDetailByAjax.action',
	data : queryData,
	dataType : "JSON",
	cache : false,
	success : function(data) {
		if(deviceType == '3'){
			chart1Name = '读吞吐量（字节）';
			chart2Name = '写吞吐量（字节）';
			chart1Data = readResData(data,'diskReadBytes');
			chart2Data = readResData(data,'diskWriteBytes');
		} else if (deviceType == '41'){
			chart1Name = '包总数';
			chart2Name = '丢包数';
			chart3Name = '字节总数';
			chart1Data = readResData(data,'pkts');
			chart2Data = readResData(data,'discards');
			chart3Data = readResData(data,'octets');
		} else if (deviceType == '51'){
			chart1Name = '包总数';
			chart2Name = '丢包数';
			chart1Data = readResData(data,'pkts');
			chart2Data = readResData(data,'discards');
		} else if (deviceType == '2'){
			chart1Name = 'CPU使用率（%）';
			chart2Name = '内存使用率（%）';
			chart3Name = '吞吐量（MB/S）';
			chart1Data = readResData(data,'cpu');
			chart2Data = readResData(data,'mem');
			chart3Data = readResData(data,'throughput');
		} else{
			chart1Name = 'CPU使用率（%）';
			chart2Name = '内存使用率（%）';
			chart1Data = readResData(data,'cpu');
			chart2Data = readResData(data,'mem');
		}
		
		$("#chartCpu")[0] = new Charts.Chart({
			chart : {
				renderTo : 'chartCpu', /*加载chart的控件id*/
				defaultSeriesType : 'spline',
				backgroundColor : '#fcfcfc',
				style : {
					fontFamily : 'Microsoft YaHei', // default font
					fontSize : '12px'
				},
				events: {
					
					load: function() {
						var series = this.series[0];
						
						if(deviceType == '3'){
							//初始化
							var actionStr = '/devicePerformanceRTDetailByAjax.action';//CPU、内存列表数据
							actionStr +="?deviceId="+deviceId+"&deviceType="+deviceType;
							getRealTimeData(actionStr,series,'diskReadBytes');
							
							setInterval(function() {
								var actionStr = '/devicePerformanceRTDetailByAjax.action';//CPU、内存列表数据
								actionStr +="?deviceId="+deviceId+"&deviceType="+deviceType;
								getRealTimeData(actionStr,series,'diskReadBytes');
							}, 5 * 60 * 1000);//5分钟
							//}, 10 * 1000);//10秒
						}else if (deviceType == '41' || deviceType == '51'){
							//初始化
							var actionStr = '/devicePerformanceRTDetailByAjax.action';//包总数、丢包数列表数据
							actionStr +="?deviceId="+deviceId+"&deviceType="+deviceType;
							getRealTimeData(actionStr,series,'pkts');
							
							setInterval(function() {
								var actionStr = '/devicePerformanceRTDetailByAjax.action';//包总数、丢包数列表数据
								actionStr +="?deviceId="+deviceId+"&deviceType="+deviceType;
								getRealTimeData(actionStr,series,'pkts');
							}, 5 * 60 * 1000);//5分钟
							//}, 10 * 1000);//10秒
						} else{
							//初始化
							var actionStr = '/devicePerformanceRTDetailByAjax.action';//CPU、内存列表数据
							actionStr +="?deviceId="+deviceId+"&deviceType="+deviceType;
							getRealTimeData(actionStr,series,'cpu');
							
							setInterval(function() {
								var actionStr = '/devicePerformanceRTDetailByAjax.action';//CPU、内存列表数据
								actionStr +="?deviceId="+deviceId+"&deviceType="+deviceType;
								getRealTimeData(actionStr,series,'cpu');
							}, 5 * 60 * 1000);//5分钟
							//}, 10 * 1000);//10秒
						}
					}
				}
			},
			title : {
				text : deviceName + '----24小时实时' + chart1Name,
				style : {
					fontFamily : 'Microsoft YaHei', // default font
					fontSize : '16px'
				}
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
					text :chart1Name,
					style:{ "color": "#878787"}
				},
				max : 100,
				min : 0
			},
			tooltip: {
	        	formatter: function() {
	                return '<b>'+ Charts.dateFormat('%H:%M:%S', this.x) +'</b><br/>'+
	                			this.series.name +': '+ this.y;
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
				name: '24小时实时' + chart1Name,
	            data: chart1Data,
	            color:'#7cb5ec'
	        }]
		});
		
		$("#chartMem")[0] = new Charts.Chart({
			chart : {
				renderTo : 'chartMem', /*加载chart的控件id*/
				defaultSeriesType : 'spline',
				backgroundColor : '#fcfcfc',
				style : {
					fontFamily : 'Microsoft YaHei', // default font
					fontSize : '12px'
				},
				events: {
					
					load: function() {
						var series = this.series[0];
						
						if(deviceType == '3'){
							//初始化
							var actionStr = '/devicePerformanceRTDetailByAjax.action';//CPU、内存列表数据
							actionStr +="?deviceId="+deviceId+"&deviceType="+deviceType;
							getRealTimeData(actionStr,series,'diskWriteBytes');
							
							setInterval(function() {
								var actionStr = '/devicePerformanceRTDetailByAjax.action';//CPU、内存列表数据
								actionStr +="?deviceId="+deviceId+"&deviceType="+deviceType;
								getRealTimeData(actionStr,series,'diskWriteBytes');
							}, 5 * 60 * 1000);//5分钟
							//}, 10 * 1000);//10秒
						} else if (deviceType == '41' || deviceType == '51'){
							//初始化
							var actionStr = '/devicePerformanceRTDetailByAjax.action';//包总数、丢包数列表数据
							actionStr +="?deviceId="+deviceId+"&deviceType="+deviceType;
							getRealTimeData(actionStr,series,'discards');
							
							setInterval(function() {
								var actionStr = '/devicePerformanceRTDetailByAjax.action';//包总数、丢包数列表数据
								actionStr +="?deviceId="+deviceId+"&deviceType="+deviceType;
								getRealTimeData(actionStr,series,'discards');
							}, 5 * 60 * 1000);//5分钟
							//}, 10 * 1000);//10秒
						} else{
							//初始化
							var actionStr = '/devicePerformanceRTDetailByAjax.action';//CPU、内存列表数据
							actionStr +="?deviceId="+deviceId+"&deviceType="+deviceType;
							getRealTimeData(actionStr,series,'mem');
							
							setInterval(function() {
								var actionStr = '/devicePerformanceRTDetailByAjax.action';//CPU、内存列表数据
								actionStr +="?deviceId="+deviceId+"&deviceType="+deviceType;
								getRealTimeData(actionStr,series,'mem');
							}, 5 * 60 * 1000);//5分钟
							//}, 10 * 1000);//10秒
						}
						
					}
				}
			},
			title : {
				text : deviceName + '----24小时实时' + chart2Name,
				style : {
					fontFamily : 'Microsoft YaHei', // default font
					fontSize : '16px'
				}
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
					text :chart2Name,
					style:{ "color": "#878787"}
				},
				max : 100,
				min : 0
			},
			tooltip: {
	        	formatter: function() {
	                return '<b>'+ Charts.dateFormat('%H:%M:%S', this.x) +'</b><br/>'+
	                			this.series.name +': '+ this.y;
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
				name: '24小时实时' + chart2Name,
		        data: chart2Data,
	            color:'#7cb5ec'
	        }]
		});
		
		//防火墙吞吐量
		if(deviceType == '2' || deviceType == '41'){
			$("#chartThroughput")[0] = new Charts.Chart({
				chart : {
					renderTo : 'chartThroughput', /*加载chart的控件id*/
					defaultSeriesType : 'spline',
					backgroundColor : '#fcfcfc',
					style : {
						fontFamily : 'Microsoft YaHei', // default font
						fontSize : '12px'
					},
					events: {
						
						load: function() {
							var series = this.series[0];
							if(deviceType == '2'){
								//初始化
								var actionStr = '/devicePerformanceRTDetailByAjax.action';//吞吐量列表数据
								actionStr +="?deviceId="+deviceId+"&deviceType="+deviceType;
								getRealTimeData(actionStr,series,'throughput');
								
								setInterval(function() {
									var actionStr = '/devicePerformanceRTDetailByAjax.action';//吞吐量列表数据
									actionStr +="?deviceId="+deviceId+"&deviceType="+deviceType;
									getRealTimeData(actionStr,series,'throughput');
								}, 5 * 60 * 1000);//5分钟
								//}, 10 * 1000);//10秒
							} else if(deviceType == '41'){
								//初始化
								var actionStr = '/devicePerformanceRTDetailByAjax.action';//字节总数列表数据
								actionStr +="?deviceId="+deviceId+"&deviceType="+deviceType;
								getRealTimeData(actionStr,series,'octets');
								
								setInterval(function() {
									var actionStr = '/devicePerformanceRTDetailByAjax.action';//字节总数列表数据
									actionStr +="?deviceId="+deviceId+"&deviceType="+deviceType;
									getRealTimeData(actionStr,series,'octets');
								}, 5 * 60 * 1000);//5分钟
								//}, 10 * 1000);//10秒
							}
						}
					}
				},
				title : {
					text : deviceName + '----24小时实时' + chart3Name,
					style : {
						fontFamily : 'Microsoft YaHei', // default font
						fontSize : '16px'
					}
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
						text :chart3Name,
						style:{ "color": "#878787"}
					},
					max : 100,
					min : 0
				},
				tooltip: {
		        	formatter: function() {
		                return '<b>'+ Charts.dateFormat('%H:%M:%S', this.x) +'</b><br/>'+
		                			this.series.name +': '+ this.y;
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
					name: '24小时实时' + chart3Name,
			        data: chart3Data,
		            color:'#7cb5ec'
		        }]
			});
		}
		
		$.dialog({
		    title : '实时性能详细信息',
		    content : document.getElementById('devicePerformance_detail'),
		    init : function() {

		    },
		    cancelVal : '关闭',
		    cancel : true,
		    lock : true
		});
	}
    });
}


/**
 * 列表分页点击事件，如果页面上有多个pageBar，用参数pageBarId来区分是点击的哪个pageBar,该页面只有一个分页用不到pageBarId参数
 */
function getPageData(url, pageBarId){
	getListData(url);
}

/**
 * 按钮转换：cpu、内存和磁盘之间
 */
function setPerformanceUrl(deviceType, timeType, performanceType){
//	var deviceType = $('#deviceType').val();
	if(performanceType == "cpu"){
		var cpuUrl =  "../report/cpuDeviceTop10PerformanceAction.action?devicePerformanceInfo.deviceType=" + deviceType + "&devicePerformanceInfo.timeType=" + timeType;
	    $("#cpuUrl").attr("href",cpuUrl);
	}
	if(performanceType == "mem"){
		var memUrl =  "../report/memDeviceTop10PerformanceAction.action?devicePerformanceInfo.deviceType=" + deviceType + "&devicePerformanceInfo.timeType=" + timeType;
		$("#memUrl").attr("href",memUrl);
	}
	if(performanceType == "hst") {
		var hstUrl =  "../report/hstDeviceTop10PerformanceAction.action?devicePerformanceInfo.deviceType=" + deviceType + "&devicePerformanceInfo.timeType=" + timeType;
		$("#hstUrl").attr("href",hstUrl);
	}
}

/**
 * 按时间粒度1小时或者24小时查询
 */
function searchTimeTypeReport(deviceType, timeType){
	var form = $("#searchForm");
	$("#searchForm").attr("action", "memDeviceTop10PerformanceAction.action?devicePerformanceInfo.deviceType=" + deviceType + "&devicePerformanceInfo.timeType=" + timeType);
	form.submit();
}


