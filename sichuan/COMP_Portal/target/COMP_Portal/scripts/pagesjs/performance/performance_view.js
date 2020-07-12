var itemNumber = 5;

$(function() {
	//查询Tab页信息
	getTab();
});

//tab数组
var result = ["CPU","磁盘","内存","网络"];
function getTab() {
	for(var i = 0 ; i < result.length ; i++) {
		//拼所有Tab
		if (i == 0) {
			//默认选中第一个Tab
    		$("#task1 .rightDisplayJhj ul").append('<li><a id="'+result[i]+'ReportButton" href="#" class="displayButtonfoucsJhj" onclick="change('+"'"+result[i]+'Report'+"'"+');return false;">'+result[i]+'</a></li>');
		}else {
			//其它Tab不选中
    		$("#task1 .rightDisplayJhj ul").append('<li><a id="'+result[i]+'ReportButton" href="#" class="displayButtonJhj" onclick="change('+"'"+result[i]+'Report'+"'"+');return false;">'+result[i]+'</a></li>');
		}
		//拼所有折线图和表格的外框
	       	$("#task1").append('<div id="'+result[i]+'Report" class="report" style="height: 50%"><div id="pmres'+result[i]+'" class="reportdiv"></div><div class="task1Table" style="overflow:auto;"><table  cellpadding="0"  cellspacing="0" class="tableJhj"  id="table'+result[i]+'"><thead><tr style="background:url(../themes/performance/images/tabelBg.jpg) repeat-x; height:28px; line-height:28px;"><th style="width:20%; border-left:1px solid #fff; border-right:1px solid #aaa;font-weight:bold; padding-left:15px;" width="150">属性OID</th><th style="width:40%; border-left:1px solid #fff; border-right:1px solid #aaa;font-weight:bold; padding-left:15px;">属性值</th><th style="width:40%; border-left:1px solid #fff; border-right:1px solid #aaa;font-weight:bold; padding-left:15px;">性能指标名称</s:text></th></tr></thead><tbody class="stripe_tb"></tbody></table></div></div>');
		//默认展示第一个Tab下的div
//		if (i==0) {
			$("#"+result[i]+"Report").show();
			lastClick = result[i]+"Report";
//		}
		//构造性能视图页面结构
		//定义文字显示的html串用于替换
		var propertyViewText = '<tr><td style="width: 100%; height: 8px;"><div style="margin-left: 5px; float:left; " id="chartValueDiv'+result[i]+'">replaceThisText</div></td></tr>';
		//定义折线图的html用于替换
		var propertyViewChart = '<tr><td style="width: 100%;height: 200px;"><div id="replaceThisChartId" class="reportdiv1"></div></td></tr>';
		$("#performancePropertyTable").append(propertyViewText.replace("replaceThisText",result[i]));
		$("#performancePropertyTable").append(propertyViewChart.replace("replaceThisChartId",result[i]+'view'));
		}
	//tab页信息请求返回成功，并且拼好html后，请求tab下具体指标信息
	getTabIndex();
}

var tabIndexArray;
function getTabIndex(){
	$.ajax({
        url: "getAlreadyConfTabIndexVM.action",
        type: "POST",
        data:{},
        dataType: "json",
        success:function(data) {//请求Tab页信息
        	tabIndexArray = data.mibdom;
        	getVmResources();
        }
    })
}

//所有数据的JSONArray
//承载普通tab页数据的数组
var pmListJSONArray = [];

function getVmResources() {
	var vmId = document.getElementById("vmId").value;
	$.ajax({
		url : "vmMResourceSearchActionJson.action?vmId=" + vmId,
		type : "POST",
		data : {
			
		},
		dataType : "json",
		success : function(data) {
			pmListJSONArray = data.vmResInfos;
    	 	//请求回来的json数据格式是什么样的？答：物理机对象集合(jsonArray格式)
    	 	//第一轮全量数据获取完成后，绘制表格
    	 	//展示
    	 	
    	 	drawTable();
    	 	//第一轮全量数据获取完成后，调用方法绘制曲线图
    	 	drawLine();
    	 	//图标绘制完成后，设置奇偶行样式
    	 	jiouRowStyle();
    	 	//当奇偶行样式设置完成后，设置表格自动高度
    	 	setTableHeight();
    	 	//所有数据处理结束后准备获取增加数据，为折线图和表格刷新做准备，5分钟获取一次
//    	 	setInterval(getIncreaseData,freshTime);
		}
	});
}

//绘制表格
function drawTable() {
	//首先遍历tab数组
	for(var t = 0 ; t < result.length ; t++) {
		//遍历每一个tab下的指标
		for (var i = 0; i < tabIndexArray.length; i++) {
			//定义一个临时数组
			var temp = [];
//			if (result[t]=='进程') {
				//进程数组
//				temp = pmListJSONArrayP;
//			} else {
				temp = pmListJSONArray;
//			}
			//将该tab下的所有tr行拼到表格中
			if (result[t]==tabIndexArray[i]["indexGroup"])
			{
				//列名是定义好的一些有规律的字符串，不应显示到表格第一项，但是需要该字段取性能数据
				var colomeName = tabIndexArray[i]["colomeName"];
				var oid = tabIndexArray[i]["oid"];
				var indexData;
				var indexUnit = tabIndexArray[i]["indexUnit"];
				//采集到指标的集合可能不为空
				if (temp.length>2) {
					indexData = temp[temp.length-1][tabIndexArray[i]["colomeName"]];
					//若物理机的性能信息中该字段没有采集到数据
					if ('' == indexData || null == indexData) {
						indexData = 'N/A';
						indexUnit = '';
					} else if(indexData.indexOf("^")>0){
						drawNewTable(result[t],indexData);
						break;
					}else if (!isNaN(indexData)) {
						//indexData有数据，并且是一个数，则对数据进行处理，保留小数点后两位小数
						indexData = Math.round(parseFloat(indexData)*100)/100;
					}
				} else {
					//未采集到配置指标的数据
					indexData = 'N/A';
					indexUnit = '';
				}
				var mibName = tabIndexArray[i]["mibName"];
				//拼tr行
				
				$("#" +result[t]+ "Report .task1Table table tbody").append('<tr ><td style="padding-left:15px; border-right:1px solid #eee;"><strong>'+oid+'</strong></td><td style="padding-left:15px; border-right:1px solid #eee;"><div class="progressbarNum"><span id="'+colomeName+'">'+indexData+indexUnit+'</span></div><div id="'+colomeName+'Bar" class="progressbar"></div></td><td style="padding-left:15px; border-right:1px solid #eee;">'+mibName+'</td></tr>');
				
				//若该指标单位为百分比,则增加进度条
				if ('%' == indexUnit) {
					$("#"+colomeName+"Bar").progressbar({
			   			value: parseFloat(indexData)
			   		});
				}
			}
		}
	}
}

function drawNewTable(conf,indexData){
	$("#" +conf+ "Report .task1Table table thead").remove();
	var str='<thead><tr style="background:url(../themes/performance/images/tabelBg.jpg) repeat-x; height:28px; line-height:28px;">';
	str+='<th style="width:7%;border-left:1px solid #fff; border-right:1px solid #aaa;font-weight:bold; " width="150">序列号</th>';
	var oidArry = new Array();//多值列表oid
	var unitArry = new Array();//多值列表单位
	var onlyOidArray = new Array();//单值列表oid
	var onlyUnitArray = new Array();//单值单位
	var onlyTableStr='<table  cellpadding="0"  cellspacing="0" class="tableJhj"  id="onlyValueTable'+conf+'"><tr>';
//	for(var i in tabIndexArray){
	for (var i = 0; i < tabIndexArray.length; i++) {
		if(conf==tabIndexArray[i]["indexGroup"]){
			if(validDataFormat(tabIndexArray[i]["oid"])>0){
				str+='<th style="border-left:1px solid #fff; border-right:1px solid #aaa;font-weight:bold; padding-left:15px;" width="150" id="'+conf+tabIndexArray[i]["oid"]+'_th">'+tabIndexArray[i]["mibName"]+'</th>';
				oidArry.push(tabIndexArray[i]["oid"]);
				unitArry.push(tabIndexArray[i]["indexUnit"]);
			}else{
				//只展示一个值.
				var dataV = pmListJSONArray[pmListJSONArray.length-2][tabIndexArray[i]["oid"]];
				if(dataV.indexOf(":=")>0){
					dataV=dataV.substring(dataV.indexOf(":=")+2,dataV.length);
				}
				onlyTableStr+='<td  align="right" style="padding-right:15px; border-right:1px solid #eee;"><strong>'+tabIndexArray[i]["mibName"]+'</strong>';
				onlyTableStr+='<td style="padding-left:15px; border-right:1px solid #eee;">';
				onlyTableStr+='<div class="progressbarNum"><span>'+dataV+tabIndexArray[i]["indexUnit"]+'</span></div><div id="'+conf+tabIndexArray[i]["oid"]+'_Bar" class="progressbar"></div></td>';
				onlyOidArray.push(tabIndexArray[i]["oid"]);
				onlyUnitArray.push(tabIndexArray[i]["indexUnit"]);
			}
		}
	}
	onlyTableStr+='</tr></table>';
	str+='</tr></thead>';
	$("#" +conf+ "Report .task1Table table").append(str);
	$("#" +conf+ "Report .task1Table table tbody").remove();
	var tbStr='<tbody class="stripe_tb">';
	//添加性能值.
	var rowNum = indexData.split("^");
//	for(var r in rowNum){
	for (var r = 0; r < rowNum.length; r++) {
		if(rowNum[r]==''){
			continue;
		}
		tbStr+='<tr >';
		tbStr+='<td align="center" style="padding-left:15px; border-right:1px solid #eee;">'+(parseInt(r)+1)+'</td>';
//		for(var c in oidArry){
		for (var d = 0; d < oidArry.length; d++) {
			tbStr+='<td style="padding-left:15px; border-right:1px solid #eee;" id="'+conf+'_'+d+'">'+d+'</td>';
		}
		tbStr+='</tr>';
	}
	tbStr+='</tbody>';
	$("#" +conf+ "Report .task1Table table").append(tbStr);
//	for(var c in oidArry){
	for (var c = 0; c < oidArry.length; c++) {
		var values = pmListJSONArray[pmListJSONArray.length-2][oidArry[c]];
		var vArray = new Array();
		var unit = unitArry[c];
		if(values.indexOf("^")>0){//说明多个值.
			vArray = values.split("^");
		}
		$("#" +conf+ "Report .task1Table tbody ").find("tr").each(function(index){
			var tdVl ="";
			if(vArray[index].indexOf(":=")>0){
				tdVl=vArray[index].substring(vArray[index].indexOf(":=")+2,vArray[index].length);
			}else if(tdVl=""){
				tdVl ="'N/A'";
			}else{
				tdVl=vArray[index];
			}
			if(unit=='%'){
				var barStr='<div class="progressbarNum"><span>'+tdVl+unit+'</span></div><div id="'+oidArry[c]+index+'_Bar" class="progressbar"></div>';
				$(this).find("td").eq(parseInt(c)+1).html(barStr);
				$("#"+oidArry[c]+index+"_Bar").progressbar({
		   			value: parseFloat(tdVl)
		   		});
			}else{
				$(this).find("td").eq(parseInt(c)+1).text(tdVl+unit);
			}
		});
	}
	//在原有table之上，添加只有单值展示的table.
	if(onlyTableStr.indexOf("td")>0){
		$("#" +conf+ "Report .task1Table table").before(onlyTableStr);
		for(var i in onlyOidArray){
			if(onlyUnitArray[i]=='%'){
				$("#"+conf+onlyOidArray[i]+"_Bar").progressbar({
		   			value: parseFloat(pmListJSONArray[pmListJSONArray.length-2][onlyOidArray[i]])
		   		});
			}
		}
	}
}

function validDataFormat(oid){
	var data =pmListJSONArray[pmListJSONArray.length-2][oid];
	return data.indexOf("^");
}

function jiouRowStyle(){
	//加载数据
	$("tr").each(function(){
		var tr_bgColor = $(this).css("background-color");
		$(this).hover(function(){
		    $(this).css("background-color","transparent");
		},function(){
			$(this).css("background-color","transparent");
		})
	});
	//设置奇偶行样式
	$('tr').unbind('mouseenter mouseleave');
	$(".stripe_tb tr:odd").addClass("ou");   //为奇数行设置样式(添加样式类)
	$(".stripe_tb tr:even").addClass("alt");  // 为偶数行设置样式类
}


function setTableHeight(){
	//list自动高度
	$(".task1Table").height($(window).height()-310);
	$(window).resize(function() {
		var winHeight = $(window).height();
		$(".task1Table").height(winHeight -310-10);
		setOnlyTableHeight();
	});
	//设置只展示表格的高度.
	setOnlyTableHeight();
}

function setOnlyTableHeight(){
	for(var i in result){
		var tableFalg=true;
		for(var j in tabIndexArray){
			if(result[i]==tabIndexArray[j]["indexGroup"]){
				if(tabIndexArray[j]["ifShowLine"] == "1"){
					tableFalg=false;
					break;
				}else{
					tableFalg=true;
				}
			}else{
				continue;
			}
		}
		if(tableFalg){
			$("#table"+result[i]).parent().height($(window).height()-108);
			$(window).resize(function() {
				var winHeight = $(window).height();
				$("#table"+result[i]).parent().height(winHeight -108-10);
			});
		}
	}
}

Charts.setOptions({
	global: {
		useUTC: false
	}
});

function drawLine() {
	//首先遍历tab
	for(var t = 0 ; t < result.length ; t++) {
		var chartzu = new Array();
		
		var seriesData = [];
		//定义一个临时数组
		var temp = [];
//		if (result[t]=='进程') {
//			//进程数组
//			temp = pmListJSONArrayP;
//		} else {
			temp = pmListJSONArray;
//		}
		var drawLineFlag=false;//是否展示折线图.
		//遍历tab下的指标
		for (var i = 0; i < tabIndexArray.length; i++) {
			
			//取到了一个需要显示在折线图上的指标列
			if (result[t]==tabIndexArray[i]["indexGroup"] && tabIndexArray[i]["ifShowLine"] == "1") {
				//指标单位挂到Y轴上，出现各指标图单位乱套的问题，所以Y轴不设置单位
				var indexUnit = tabIndexArray[i]["indexUnit"];
				var indexName = tabIndexArray[i]["mibName"];
				var indexData = [];
				//alert(JSON.stringify(temp[0]));
				//获取一个指标的所有性能数据
				for (var ii = 0; ii < temp.length; ii++) {
					//如果同一个视图上的字段有一个没有值，则必须设置为空，否则默认设置为NaN，导致其它视图无法展示
// 					if (isNaN(parseFloat(temp[ii][tabIndexArray[one]["colomeName"]]))){
// 						indexData.push({
// 							x: temp[ii]["per_time"].time,
// 							y: null
// 						});
// 					} else {
					if(temp[ii][tabIndexArray[i]["colomeName"]] == "") {
	                      temp[ii][tabIndexArray[i]["colomeName"]] = 0.0;
					}
				indexData.push({
					x: (new Date(temp[ii]["per_time"])).getTime(),
					y: Math.round(parseFloat(temp[ii][tabIndexArray[i]["colomeName"]])*100)/100
				});							
		              
				}
	              
// 				}
				//将指标单位和指标名称一起拼到名称中，在折线图中备用
				seriesData.push({
						name: indexName+'('+indexUnit+')',
						data: indexData
				});
			}
		}
		for (var j = 0; j < tabIndexArray.length; j++) {
			if (result[t]==tabIndexArray[j]["indexGroup"] && tabIndexArray[j]["ifShowLine"] == "1"){
				drawLineFlag=true;
				break;
			}else{
				drawLineFlag=false;
			}
		}
		//alert(JSON.stringify(seriesData));
		//根据tab个数新建chart个数
		if(drawLineFlag){
			this['chart'+t] = new Charts.Chart({
				chart: {
					renderTo: 'pmres'+result[t],
					defaultSeriesType: 'spline',
					backgroundColor: '#ffffff'
				},
				title: {
					text: ''
				},
				xAxis: {
					type: 'datetime',
					dateTimeLabelFormats: { // don't display the dummy year
						minute: '%H:%M'
					}
				},
				yAxis: {
					title: {
						text: ''
					},
					labels: {
						formatter: function(){
							return (Math.abs(this.value));
						}
					},
					min: 0,
					plotLines: [{
						value: 0,
						width: 1,
						color: '#808080'
					}]
				},
				tooltip: {
					formatter: function() {
						return '<b>'+ this.series.name +'</b><br/>'+
						Charts.dateFormat('%H:%M', this.x) +' '+ this.y;
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
				series: seriesData
			});
		}else{
			$("#"+result[t]+"Report").hide();
			$("#pmres"+result[t]).hide();
		}
	}
}



var lastClick;
function change(r) {
	$(".report").hide();
	$("#"+r).show();

	$('#'+lastClick+'Button').removeClass();
	$('#'+lastClick+'Button').addClass('displayButtonJhj');
	$('#'+r+'Button').removeClass();
	$('#'+r+'Button').addClass("displayButtonfoucsJhj");
	lastClick = r;
}

function resource(){
	//首先隐藏性能视图的div
	$("#performanceProperty").hide();
	//其次隐藏第二个tab页和第三个tab页的div
	$("#task2").hide();
	//显示性能指标的div
	$("#task1").show();

	$('#tab-title-1').removeClass();
	$('#tab-title-2').removeClass();
	$('#tab-title-3').removeClass();
	$('#tab-title-1').addClass("buttonFoucs");
	$('#tab-title-2').addClass("button");
	$('#tab-title-3').addClass("button");
}

//增加判断，当用户第一次点击基本信息的tab时，请求数据，否则再次点击不请求数据
var messageClickIsFirst = true;
function message(){
	var vmId = document.getElementById("vmId").value;
	//首先隐藏性能视图的div
	$("#performanceProperty").hide();
	$("#task1").hide();
	//显示被load的div
	$("#task2").show();

	$('#tab-title-1').removeClass();
	$('#tab-title-2').removeClass();
	$('#tab-title-3').removeClass();
	$('#tab-title-1').addClass("button");
	$('#tab-title-2').addClass("buttonFoucs");
	$('#tab-title-3').addClass("button");

	if (messageClickIsFirst) {	
		$('#task2').load("vmPerformanceDetailAction.action?vmId=" + vmId);
		/*
		setTimeout(function () {
			$(".stripe_tb tr:odd").removeClass();   //为奇数行设置样式(添加样式类)
			$(".stripe_tb tr:even").removeClass();  // 为偶数行设置样式类
			$(".stripe_tb tr:odd").addClass("ou");   //为奇数行设置样式(添加样式类)
			$(".stripe_tb tr:even").addClass("alt");  // 为偶数行设置样式类
		}, 800);
		setTimeout(function () {
			//查询物理机状态
			getState();
		}, 1000);
		*/
		messageClickIsFirst = false;
	}
}

//增加判断，当用户第一次点击性能视图的tab时，请求数据，否则只有性能视图的下拉列表变化时请求数据
var propertyViewClickIsFirst = true;
function property(){
	$('#tab-title-1').removeClass();
	$('#tab-title-2').removeClass();
	$('#tab-title-3').removeClass();
	$('#tab-title-1').addClass("button");
	$('#tab-title-2').addClass("button");
	$('#tab-title-3').addClass("buttonFoucs");
	
	//请求性能视图数据，显示性能视图div
	$("#performanceProperty").show();
	//隐藏被load的div
	$("#task1").hide();
	$("#task2").hide();
	
	if (propertyViewClickIsFirst) {
		timeChange();
		propertyViewClickIsFirst = false;
	}
}

function timeChange() {
	var optionTime = $("#time").val();
	getTimeDate(optionTime);
	if(count>2){
		$("#performanceProperty").height($(window).height()-225*(count-2));
    }
}
function getTimeDate(optionTime) {
	var vmId = document.getElementById("vmId").value;
	$.ajax({
		url: "searchVMProperty.action",
        type: "POST",
        data:{
        	vmId: vmId,
        	optionTime: optionTime
        },
        dataType: "json",
        success:function(data) {//这里的data是由请求页面返回的数据
	    	var propertyViewData = data.vmResInfos;
	    	//画线
	    	drawPropertyViewLine(propertyViewData);
        }
	});
}

function drawPropertyViewLine(propertyViewData){
	//首先遍历tab
	for(var t = 0 ; t < result.length ; t++) {
		var chartzu = new Array();
		var seriesData = [];
		//定义一个临时数组
		var temp = [];
//		if (result[t]=='进程') {
			//进程数组
//			temp = propertyViewDataP;
//		} else {
			temp = propertyViewData;
//		}
		//遍历tab下的指标
		var drawLineFlag=false;
		for (var i = 0; i < tabIndexArray.length; i++) {
			//取到了一个需要显示在折线图上的指标列
			if (result[t]==tabIndexArray[i]["indexGroup"] && tabIndexArray[i]["ifShowLine"] == "1")
			{
				//指标单位挂到Y轴上，出现各指标图单位乱套的问题，所以Y轴不设置单位
				var indexUnit = tabIndexArray[i]["indexUnit"];
				var indexName = tabIndexArray[i]["mibName"];
				var indexData = [];
				//alert(JSON.stringify(temp[0]));
				//获取一个指标的所有性能数据
				for (var ii = 0; ii < temp.length; ii++) {
					//如果同一个视图上的字段有一个没有值，则必须设置为空，否则默认设置为NaN，导致其它视图无法展示
					if (isNaN(parseFloat(temp[ii][tabIndexArray[i]["colomeName"]]))){
						indexData.push({
							x: (new Date(temp[ii]["per_time"])).getTime(),
							y: null
						});
					} else {
						indexData.push({
//							x: temp[ii]["per_time"].time,
							x: (new Date(temp[ii]["per_time"])).getTime(),
							y: Math.round(parseFloat(temp[ii][tabIndexArray[i]["colomeName"]])*100)/100
						});
					}
				}
				//将指标单位和指标名称一起拼到名称中，在折线图中备用
				seriesData.push({
						name: indexName+'('+indexUnit+')',
						data: indexData
				});
			}
		}
		for (var r = 0; r < tabIndexArray.length; r++) {
			if (result[t]==tabIndexArray[r]["indexGroup"] && tabIndexArray[r]["ifShowLine"] == "1"){
				drawLineFlag=true;
				break;
			}else{
				drawLineFlag=false;
			}
		}
		//alert(JSON.stringify(seriesData));
		//根据tab个数新建chart个数
		if(drawLineFlag){
			this['viewChart'+t] = new Charts.Chart({
				chart: {
					renderTo: result[t]+'view',
					defaultSeriesType: 'spline',
					backgroundColor: '#ffffff'
				},
				title: {
					text: ''
				},
				xAxis: {
					type: 'datetime',
					dateTimeLabelFormats: { // don't display the dummy year
						minute: '%m-%d %H:%M'
					}
				},
				yAxis: {
					title: {
						text: ''
					},
					labels: {
						formatter: function(){
							return (Math.abs(this.value));
						}
					},
					min: 0,
					plotLines: [{
						value: 0,
						width: 1,
						color: '#808080'
					}]
				},
				tooltip: {
					formatter: function() {
						return '<b>'+ this.series.name +'</b><br/>'+Charts.dateFormat('%m-%d %H:%M', this.x) +'  '+ this.y;
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
				series: seriesData
			});
		}else{
			$("#chartValueDiv"+result[t]).parent().parent().remove();
			$("#"+result[t]+"view").parent().parent().remove();
		}
		
	}
	count=0;
	for (var u = 0; u < result.length; u++) {
		for (var o = 0; o < tabIndexArray.length; o++) {
			if(result[u]==tabIndexArray[o]["indexGroup"] && tabIndexArray[o]["ifShowLine"] == "1"){
				count++;
				break;
			}
		}
	}
	if(count>2){
		$("#performanceProperty").height($(window).height()+225*(count-2));
	}
}

var count=0;
$(function(){
	//task2的高度调整
//	$("#task2").height($(window).height()-70);
//	$(window).resize(function() {
//		var winHeight = $(window).height();
//		$("#task2").height(winHeight -70-10);
//	});
//	$("#performanceProperty").height($(window).height()-70);
//	$(window).resize(function() {
//		var winHeight = $(window).height();
//		$("#performanceProperty").height(winHeight -70-10);
//                if(count>2){
//			$("#performanceProperty").height($(window).height()+225*(count-2));
//		}
//	});
	$("#performanceProperty").hide();
	$("#task2").hide();
});
