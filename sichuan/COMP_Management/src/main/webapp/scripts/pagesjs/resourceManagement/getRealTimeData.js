var basePath = obj.protocol + "//"+obj.host + "/" + contextPath;
var colors = ["#7CB5EC", "#434348", "#90ED7D", "#F7A35C", "#8085E9", "#F15C80", "#E4D354", "#8085E8", "#8D4653"];
/**
 *获取CPU、内存列表内实时数据 
 *realTime 为对象名，其中的个属性显示在表格内
 *cpuProcessorUtilization，cpuSysTime，cpuUserTime，cpuIdleTime，memUsedPer，sysMemUsedPer，userMemUsedPer，memFree为列表显示的内容
 */
function getRealTimeData(actionStr, staType, dataTbody,series){
    url = basePath + actionStr;
	$.ajax({
	    type : "GET",
	    url : url,
	    data:{},
	    success : function(data){
			$('#'+ dataTbody).empty();
			var obj = data.realTime;
			//列表重新赋值
			if(obj.usedPer != null ){
				if("CPU" == staType){
					$('#'+dataTbody).append('<li><p>'+obj.usedPer+'%</p><p style="font-size:12px; font-weight:normal;line-height:20px;color:#666;">CPU使用率</p></li>');
			        				/*.append('<li><p>'+obj.cpuSysTime+'%</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">系统百分比</p></li>')
			        				.append('<li><p>'+obj.cpuUserTime+'%</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">用户百分比</p></li>')
			        				.append('<li><p>'+obj.cpuIdleTime+'%</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">等待百分比</p></li>');*/
				}else if("MEM" == staType){
					$('#'+dataTbody).append('<li><p>'+obj.usedPer+'%</p><p style="font-size:12px; font-weight:normal;line-height:20px;color:#666;">内存使用率</p></li>')
				    				/*.append('<li><p>'+obj.sysMemUsedPer+'%</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">系统内存使用率</p></li>')
				    				.append('<li><p>'+obj.userMemUsedPer+'%</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">用户内存使用率</p></li>')*/
				    				.append('<li><p>'+obj.memFree+'(GB)</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">可用内存量</p></li>');
				}
			}else{
				if("CPU" == staType){
					$('#'+dataTbody).append('<li><p>&nbsp;</p><p style="font-size:12px; font-weight:normal;line-height:20px;color:#666;">CPU使用率</p></li>');
				    				/*.append('<li><p>&nbsp;</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">系统百分比</p></li>')
				    				.append('<li><p>&nbsp;</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">用户百分比</p></li>')
				    				.append('<li><p>&nbsp;</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">等待百分比</p></li>');*/
					
				}else if("MEM" == staType){
					$('#'+dataTbody).append('<li><p>&nbsp;</p><p style="font-size:12px; font-weight:normal;line-height:20px;color:#666;">内存使用率</p></li>')
				    				/*.append('<li><p>&nbsp;</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">系统内存使用率</p></li>')
				    				.append('<li><p>&nbsp;</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">用户内存使用率</p></li>')*/
				    				.append('<li><p>&nbsp;</p><p style="font-size:12px; font-weight:normal; line-height:20px; color:#666;">可用内存量</p></li>');
				}
			}
			
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
			var resdata = [];
			$.each(data.realTimeList,function(index){
				var year = this.year;
				var month = this.month;
				var day = this.day;
				var hour = this.hour;
				var minute = this.minute;
				var second = this.second;
				var usedPer = this.usedPer;
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
 *获取磁盘列表内实时数据_读 
 *realTimeList 为对象所在的List，
 *diskId，diskName，diskReadBusyPer，diskWriteBusyPer，diskRead，diskWrite为列表显示的内容
 */
function getRealTimeDiskReadList(actionStr, staType, deviceType,dataTbody){
	url = basePath + actionStr;
	$.ajax({
	    type : "GET",
	    url : url,
	    data:{},
	    success : function(data){
	    	$('#'+ dataTbody).empty();
	    	var obj = data.realTimeRead;
	    	if(obj.usedPer != null ){
				$('#'+dataTbody).append('<li><p>'+obj.usedPer+'%</p><p style="font-size:12px; font-weight:normal;line-height:20px;color:#666;">磁盘使用率</p></li>');
			}else{
				$('#'+dataTbody).append('<li><p>&nbsp;</p><p style="font-size:12px; font-weight:normal;line-height:20px;color:#666;">磁盘使用率</p></li>');
			}
	    	//设置y轴最大值
	    	chartRead.yAxis[0].setExtremes(0, obj.maxRead, true, true);
	    	
	    	var series = chartRead.series;            
            while(series.length > 0) {
                 series[0].remove(true);
             }
//            chartRead.redraw();
	    	
	    	var num = data.num;
	    	if(num == 0){
	    		var resdata = [];
	    		$.each(data.timeList,function(index){
	    			var year = this.year;
					var month = this.month;
					var day = this.day;
					var hour = this.hour;
					pointHour = (new Date(year,month,day,hour,'00','00')).getTime()+(8*60*60*1000);//加上8时区
	    			resdata.push({ x: pointHour, y: null });
	    		});
    			chartRead.addSeries({name:"无磁盘", data:resdata ,color:colors[0]},true);
    			chartRead.redraw();
	    	}else{
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
				
				var y_axisData_read = eval(data.y_axisData_read);
	    		for(var i=0;i<y_axisData_read.length;i++){
	    			var resdata = [];
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
						resdata.push({ x: lastTime, y: usedPer });
					}
					if(ifsetmin){
						resdata.push({ x: mintime+(60*60*1000), y: null });
					}
					if(ifsetmax){
						resdata.push({ x: maxtime, y: null });
					}
					chartRead.addSeries({name:diskname, data:resdata ,color:colors[i%9]},true);
	    		};
	    		chartRead.redraw();
	    	}
	    	
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
 *获取磁盘列表内实时数据_写
 *realTimeList 为对象所在的List，
 *diskId，diskName，diskReadBusyPer，diskWriteBusyPer，diskRead，diskWrite为列表显示的内容
 */
function getRealTimeDiskWriteList(actionStr, staType, deviceType,dataTbody){
	url = basePath + actionStr;
	$.ajax({
	    type : "GET",
	    url : url,
	    data:{},
	    success : function(data){
	    	$('#'+ dataTbody).empty();
	    	var obj = data.realTimeWrite;
	    	if(obj.usedPer != null ){
				$('#'+dataTbody).append('<li><p>'+obj.usedPer+'%</p><p style="font-size:12px; font-weight:normal;line-height:20px;color:#666;">磁盘使用率</p></li>');
			}else{
				$('#'+dataTbody).append('<li><p>&nbsp;</p><p style="font-size:12px; font-weight:normal;line-height:20px;color:#666;">磁盘使用率</p></li>');
			}
	    	//设置y轴最大值
	    	chartWrite.yAxis[0].setExtremes(0, obj.maxWrite, true, true);
	    	
	    	var series = chartWrite.series;            
            while(series.length > 0) {
                 series[0].remove(true);
             }
//            chartWrite.redraw();
	    	
	    	var num = data.num;
	    	if(num == 0){
	    		var resdata = [];
	    		$.each(data.timeList,function(index){
	    			var year = this.year;
					var month = this.month;
					var day = this.day;
					var hour = this.hour;
					pointHour = (new Date(year,month,day,hour,'00','00')).getTime()+(8*60*60*1000);//加上8时区
	    			resdata.push({ x: pointHour, y: null });
	    		});
    			chartWrite.addSeries({name:"无磁盘", data:resdata ,color:colors[0]},true);
    			chartWrite.redraw();
	    	}else{
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
				
				var y_axisData_write = eval(data.y_axisData_write);
	    		for(var i=0;i<y_axisData_write.length;i++){
	    			var resdata = [];
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
						resdata.push({ x: lastTime, y: usedPer });
					}
					if(ifsetmin){
						resdata.push({ x: mintime+(60*60*1000), y: null });
					}
					if(ifsetmax){
						resdata.push({ x: maxtime, y: null });
					}
					chartWrite.addSeries({name:diskname, data:resdata ,color:colors[i%9]},true);
	    		};
	    		chartWrite.redraw();
	    	}
	    	
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
