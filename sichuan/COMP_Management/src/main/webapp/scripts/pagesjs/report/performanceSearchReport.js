
$(function() {

    $(".left-menu li:contains('设备性能统计')").addClass("selected").next().show();

    var deviceType = $('#deviceType').val();
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
	var deviceType = $("#deviceType").val();
	$.each(data.performanceInfolist, function(index) {
		if(deviceType == '2'){
			$('<tr>').appendTo('#listTbody')
			.append('<td>' + _fomart14(this.reportDate) + '</td> ')
			.append('<td>' + this.cpuProcessorUtilization+ '%</td>')
			.append('<td>' + this.memUsedPer + '%</td> ')
			.append('<td>' + this.throughput + 'MB/S</td> ');
		}else if(deviceType == '3'){
			$('<tr>').appendTo('#listTbody')
			.append('<td>' + _fomart14(this.reportDate) + '</td> ')
			.append('<td>' + this.hstDiskReadBytes+ '字节</td>')
			.append('<td>' + this.hstDiskWriteBytes + '字节</td> ');
		}else if(deviceType == '41'){
			$('<tr>').appendTo('#listTbody')
			.append('<td>' + _fomart14(this.reportDate) + '</td> ')
			.append('<td>' + this.pkts+ '</td>')
			.append('<td>' + this.discards+ '</td>')
			.append('<td>' + this.octets + 'Bytes</td> ');
		}else if(deviceType == '51'){
			$('<tr>').appendTo('#listTbody')
			.append('<td>' + _fomart14(this.reportDate) + '</td> ')
			.append('<td>' + this.pkts+ '</td>')
			.append('<td>' + this.discards+ '</td>');
		}else{
			$('<tr>').appendTo('#listTbody')
			.append('<td>' + _fomart14(this.reportDate) + '</td> ')
			.append('<td>' + this.cpuProcessorUtilization+ '%</td>')
			.append('<td>' + this.memUsedPer + '%</td> ');
		}
	});	
	while ($('#listTbody' + ' tr').length < 10) {
		if(deviceType == '2' || deviceType == '41' ){
			$('#listTbody').append('<tr>')
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
			.append('</tr>');
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
 * 查询
 */
function searchReport(){
	var startDate = _dateToStr($("#startDate").val());
    var endDate =  _dateToStr($("#endDate").val());
	var deviceId = $("#deviceId").val();
    var deviceType = $("#deviceType").val();
    var deviceTypeName = $("#deviceTypeName").val();
    var msg = "";

    if(deviceId == ""){
		msg += deviceTypeName+"名称不能为空！<br>";
    }
	if(startDate == ""){
	    msg += "开始时间不能为空！<br>";
    }
    if(endDate == ""){
	    msg += "结束时间不能为空！<br>";
    }
    if (startDate != "" && endDate != "" && startDate > endDate) {
    	msg += "开始时间应小于等于结束时间！<br>";
    }
    /*if (startDate != "" && endDate != "" && startDate <= endDate) {
    	var tempDate = addDate("4", "31", strToDate(startDate));
    	if (formatDate(tempDate) < endDate) {
    		msg += "开始时间与结束时间跨度不能超过31天！<br>";
    	}
    }*/

	if(msg != ""){
	    $.compMsg({type:'error',msg:msg});
		return false;
	}

	/* ajax取得列表信息 */
	var ajaxActionStr = '/performanceSearchByAjax.action'
	                  + '?devicePerformanceInfo.deviceType=' + deviceType
	                  + '&devicePerformanceInfo.deviceId=' + deviceId
	                  + '&devicePerformanceInfo.startDate=' + startDate
	                  + '&devicePerformanceInfo.endDate=' + endDate;
	getListData(ajaxActionStr);
}

/**
 * 导出
*/ 
function exportPerformance(){
	var deviceType = $("#deviceType").val();
    var deviceId = $("#deviceId").val();
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    var deviceName = $("#deviceId").find("option:selected").text();
    var actionStr = '../report/exportDeviceReportAction.action'
	                  + '?deviceType=' + deviceType
	                  + '&deviceId=' + deviceId
	                  + '&startDate=' + startDate
	                  + '&endDate=' + endDate
	                  + '&deviceName=' + deviceName;
	$("#exportUrl").attr("href",actionStr);
}

/**
 * 字符串转换成时间
 
function strToDate(str) {
	var dateStrs = str.split("-");
	var year = parseInt(dateStrs[0], 10);
	var month = parseInt(dateStrs[1], 10) - 1;
	var day = parseInt(dateStrs[2], 10);
	var date = new Date(year, month, day);
	return date;
}*/

/**
 * 时间加减
 
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
	        break;
	    case 3 :
	        date.setHours(date.getHours() + lIntval);
	        break;
	    case 2 :
	        date.setMinutes(date.getMinutes() + lIntval);
	        break;
	    case 1 :
	        date.setSeconds(date.getSeconds() + lIntval);
	        break;
	    default:
	}

    return date;
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
*/

