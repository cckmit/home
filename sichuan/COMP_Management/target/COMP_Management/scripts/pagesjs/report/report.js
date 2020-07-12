$(function() {

    $(".left-menu li:contains('业务性能统计')").addClass("selected").next().show();

    var deviceType = $('#deviceType').val();
    if (deviceType == "2") {
    	$(".left-menu dl a:contains('物理机')").parent().addClass("selected");
    } else if (deviceType == "3") {
    	$(".left-menu dl a:contains('虚拟机')").parent().addClass("selected");
    } else if (deviceType == "0") {
    	$(".left-menu dl a:contains('小型机 ')").parent().addClass("selected");
    } else if (deviceType == "1") {
    	$(".left-menu dl a:contains('小型机分区')").parent().addClass("selected");
    }

    $("#head-menu li a:contains('统计分析')").parent().addClass("head-btn-sel");

    $(":text").uniform();
});

var obj = window.location;
var contextPath = obj.pathname.split("/")[1] + "/" + obj.pathname.split("/")[2]; 
var basePath = obj.protocol + "//"+obj.host + "/" + contextPath;

/**
 * 获取性能列表数据 
 * historyPerformanceInfolist 为ajax返回的统一的List名
 */
function getListData(actionStr){
    var url = basePath + actionStr;

	$.ajax({
	    type : "GET",
	    url : url,
	    data:{},
	    success : function(data){
			$('#listTbody').empty();
			
			setTbody(data);
			
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
function setTbody(data) {
	var performanceType = document.getElementById("performanceType").value; // 性能指标标志
	var staFlag = document.getElementById("staFlag").value; // 统计类别标志

	if(performanceType == "cpu"){ // CPU
		if (staFlag == "performance") { // cpu使用率统计
			$.each(data.historyPerformanceInfolist, function(index) {
				$('<tr>').appendTo('#listTbody')
				.append('<td>' + this.reportDate + '</td> ')
				.append('<td>' + this.cpuAve+ '%</td>')
				.append('<td>' + this.cpuMax + '%</td> ')
				.append('<td>' + this.cpuMaxId + '</td>')
				.append('<td>' + this.cpuMin + '%</td>')
				.append('<td>' + this.cpuMinId + '</td> ');
			});

			while ($('#listTbody' + ' tr').length < 10) {
				$('#listTbody').append('<tr>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('</tr>');
			}
		} else { // cpu使用率超标设备统计
			$.each(data.historyPerformanceInfolist, function(index) {
				$('<tr>').appendTo('#listTbody')
				.append('<td>' + this.reportDate + '</td> ')
				.append('<td>' + this.deviceNum+ '</td>')
				.append('<td>' + this.cpuOverNum + '</td> ')
				.append('<td>' + this.cpuOverAve + '%</td>')
				.append('<td>' + this.cpuNotOverNum + '</td>')
				.append('<td>' + this.cpuNotOverAve + '%</td> ');
			});

			while ($('#listTbody' + ' tr').length < 10) {
				$('#listTbody').append('<tr>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('</tr>');
			}
		}
	}
	if(performanceType == "mem"){ // 内存
		if (staFlag == "performance") { // 内存使用率统计
			$.each(data.historyPerformanceInfolist, function(index) {
				$('<tr>').appendTo('#listTbody')
				.append('<td>' + this.reportDate + '</td> ')
				.append('<td>' + this.memAve+ '%</td>')
				.append('<td>' + this.memMax + '%</td> ')
				.append('<td>' + this.memMaxId + '</td>')
				.append('<td>' + this.memMaxFree + '</td>')
				.append('<td>' + this.memMin + '%</td> ')
				.append('<td>' + this.memMinId + '</td>')
				.append('<td>' + this.memMinFree + '</td>');
			});
			while ($('#listTbody' + ' tr').length < 10) {
				$('#listTbody').append('<tr>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('</tr>');
			}
		} else { // 内存使用率超标设备统计
			$.each(data.historyPerformanceInfolist, function(index) {
				$('<tr>').appendTo('#listTbody')
				.append('<td>' + this.reportDate + '</td> ')
				.append('<td>' + this.deviceNum+ '</td>')
				.append('<td>' + this.memOverNum + '</td> ')
				.append('<td>' + this.memOverAve + '%</td>')
				.append('<td>' + this.memOverFreeAve + '</td>')
				.append('<td>' + this.memNotOverNum + '</td> ')
				.append('<td>' + this.memNotOverAve + '%</td>')
				.append('<td>' + this.memNotOverFreeAve + '</td>');
			});
			while ($('#listTbody' + ' tr').length < 10) {
				$('#listTbody').append('<tr>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('</tr>');
			}
		}
	}
	if(performanceType == "disk"){ // 磁盘
		if (staFlag == "performance") { // 磁盘使用率统计
			$.each(data.historyPerformanceInfolist, function(index) {
				$('<tr>').appendTo('#listTbody')
				.append('<td>' + this.reportDate + '</td> ')
				.append('<td>' + this.diskAve + '%</td>')
				.append('<td>' + this.diskMax + '%</td> ')
				.append('<td>' + this.diskMaxId + '</td>')
				.append('<td>' + this.diskMin + '%</td>')
				.append('<td>' + this.diskMinId + '</td>');
			});

			while ($('#listTbody' + ' tr').length < 10) {
				$('#listTbody').append('<tr>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('<td>&nbsp;</td>')
				.append('</tr>');
			}
		} else { // 磁盘使用率分布统计
			$.each(data.historyPerformanceInfolist, function(index) {
				$('<tr>').appendTo('#listTbody')
				.append('<td>' + this.reportDate + '</td> ')
				.append('<td>' + this.deviceNum + '</td>')
				.append('<td>' + this.diskRange1Num + '</td> ')
				.append('<td>' + this.diskRange2Num + '</td>')
				.append('<td>' + this.diskRange3Num + '</td>');
			});

			while ($('#listTbody' + ' tr').length < 10) {
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
function setPerformanceUrl(performanceType){
	var deviceType = $('#deviceType').val();
	
	if(performanceType == "cpu"){
		var cpuUrl =  "../report/cpuHistoryPerformanceAction.action?historyPerformanceInfo.deviceType=" + deviceType + "&historyPerformanceInfo.performanceType=" + performanceType;
	    $("#cpuUrl").attr("href",cpuUrl);
	}
	if(performanceType == "mem"){
		var memUrl =  "../report/memHistoryPerformanceAction.action?historyPerformanceInfo.deviceType=" + deviceType + "&historyPerformanceInfo.performanceType=" + performanceType;
		$("#memUrl").attr("href",memUrl);
	}
	if(performanceType == "disk"){
		var diskUrl =  "../report/diskHistoryPerformanceAction.action?historyPerformanceInfo.deviceType=" + deviceType + "&historyPerformanceInfo.performanceType=" + performanceType;
		$("#diskUrl").attr("href",diskUrl);
	}
}

/**
 * 查询
 */
function searchReport(){
	var appId = $("#appId").val();
	var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();

    var msg = "";
    if(appId == ""){
	    msg += "业务组名称不能为空！<br>";
    }
	if(startDate == ""){
	    msg += "开始日期不能为空！<br>";
    }
    if(endDate == ""){
	    msg += "结束日期不能为空！<br>";
    }
    if (startDate != "" && endDate != "" && startDate > endDate) {
    	msg += "开始日期应小于等于结束日期！<br>";
    }
    if (startDate != "" && endDate != "" && startDate <= endDate) {
    	var tempDate = addDate("4", "31", strToDate(startDate));
    	if (formatDate(tempDate) < endDate) {
    		msg += "开始日期与结束日期跨度不能超过31天！<br>";
    	}
    }

	if(msg != ""){
	    $.compMsg({type:'error',msg:msg});
		return false;
	}

	var form = $("#searchForm");
	form.submit();
}

/**
 * 字符串转换成日期
 */
function strToDate(str) {
	var dateStrs = str.split("-");
	var year = parseInt(dateStrs[0], 10);
	var month = parseInt(dateStrs[1], 10) - 1;
	var day = parseInt(dateStrs[2], 10);
	var date = new Date(year, month, day);
	return date;
}

/**
 * 时间加减
 */
function addDate(type, NumDay, dtDate){
    var date = new Date(dtDate);
    type = parseInt(type);
    lIntval = parseInt(NumDay);
    switch(type){
	    case 7 :
	        date.setYear(date.getFullYear() + lIntval);
	        break;
	    case 6 :
	        date.setMonth(date.getMonth() + (lIntval * 3) );
	        break;
	    case 5 :
	        date.setMonth(date.getMonth() + lIntval);
	        break;
	    case 4 :
	        date.setDate(date.getDate() + lIntval);
	        break
	    case 3 :
	        date.setHours(date.getHours() + lIntval);
	        break
	    case 2 :
	        date.setMinutes(date.getMinutes() + lIntval);
	        break
	    case 1 :
	        date.setSeconds(date.getSeconds() + lIntval);
	        break;
	    default:
	}

    return date
}

function formatDate(date){
    var year = date.getFullYear();
	var month = date.getMonth()+1;
	var day = date.getDate();
	if (month < 10) {
		month = "0" + month;
	}
	if (day < 10) {
		day = "0" + day;
	}
	return (year.toString() +"-"+ month.toString() +"-"+ day.toString());
}

/**
 * 初始设置
 */
$(function() {
	var performanceType = $('#performanceType').val();
	if(performanceType == "cpu"){
		$("#cpuDiv").addClass("BussTitleButtonFocus");
		$("#memDiv").addClass("BussTitleButtonLoseFocus");
		$("#diskDiv").addClass("BussTitleButtonLoseFocus");
	}
	if(performanceType == "mem"){
		$("#cpuDiv").addClass("BussTitleButtonLoseFocus");
		$("#memDiv").addClass("BussTitleButtonFocus");
		$("#diskDiv").addClass("BussTitleButtonLoseFocus");
	}
	if(performanceType == "disk"){
		$("#cpuDiv").addClass("BussTitleButtonLoseFocus");
		$("#memDiv").addClass("BussTitleButtonLoseFocus");
		$("#diskDiv").addClass("BussTitleButtonFocus");
	}
});

/**
 * 导出
 */
function exportDiv(){
	$.dialog({
		title: '统计数据导出（导出所有资源类型的业务性能统计数据）',
		content: document.getElementById('exportDiv'),
		init:function(){
			$("#appIdExport").val($("#appId").val());
			$("#uniform-appIdExport span").html($("#appIdExport option:selected").html());
			$("#startDateExport").val($("#startDate").val());
		    $("#endDateExport").val($("#endDate").val());
		},
		button : [ {
			name : '导出',
			callback : function() {
				this.button( {
					name : '导出',
					disabled : true
				});
				if(validation()){
				    this.button({
					    name: '导出',
					    disabled: false
				    });
				    return false;
			    }
				exportReport();
				return true;
			},
			focus : true
		} ],
		cancelVal: '取消',
		cancel:true,
		lock: true
	});
}

function validation(){
	var appId = $("#appIdExport").val();
	var startDate = $("#startDateExport").val();
    var endDate = $("#endDateExport").val();

    var msg = "";
    if(appId == ""){
	    msg += "业务组名称不能为空！<br>";
    }
	if(startDate == ""){
	    msg += "开始日期不能为空！<br>";
    }
    if(endDate == ""){
	    msg += "结束日期不能为空！<br>";
    }
    if (startDate != "" && endDate != "" && startDate > endDate) {
        msg="开始日期应小于等于结束日期！<br>";
    }
    if (startDate != "" && endDate != "" && startDate <= endDate) {
    	var tempDate = addDate("4", "31", strToDate(startDate));
    	if (formatDate(tempDate) < endDate) {
    		msg="开始日期与结束日期跨度不能超过31天！<br>";
    	}
    }
    
    if(msg != ''){ //提示
	    $.compMsg({type:'error',msg:msg});
		return true;
	}else{
		return false;
	}
}

function exportReport() {
	var appId = $("#appIdExport").val();
	var appName = $("#appIdExport option:selected").html();
	var startDate = $("#startDateExport").val();
    var endDate = $("#endDateExport").val();
    var url = $("#export").attr("href")+"?appId="+appId+"&appName="+appName+"&startDate="+startDate+"&endDate="+endDate+"&rd=" + Math.random();
    window.location.href=url;
    
	/*var da_val= {
			appId: appId,
			appName: appName,
			startDate: startDate,
			endDate: endDate
		};
	$.ajax( {
		type : "POST",
		url : 'exportHistoryReportAjax.action',
		data : da_val,
		dataType : "JSON",
		cache : false,
		success : function(data) {
			$('#exportDiv').hide();
		}
	});*/
}