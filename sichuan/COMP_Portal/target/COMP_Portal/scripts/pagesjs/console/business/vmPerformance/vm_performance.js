$(function() {

	getDeviceInfo();
});

var n=0;
function init(){
	var btn = document.getElementById('btn_select');
	if(n==0){
		btn.click();
		n++;
	}
}



/**
 * 获取虚拟机列表数据
 */
function getDeviceInfo() {
	var appId = $("input[name='appId']").val(); 
	var param = {
			appId : appId
		};
	$.ajax({
		url : 'vmDeviceList.action?struts.enableJSONValidation=true',
		type : 'POST',
		data : param,
		cache : false,
		dataType : 'json',
		success : function(data) {
			$("#vmPerformanceIp").empty();
			for (var i = 0; i < data.vmDeviceInfoList.length; i++) {
					$("#vmPerformanceIp").append(
							'<option value="' + data.vmDeviceInfoList[i].vmIp
									+ '">' + data.vmDeviceInfoList[i].vmIp
									+ '</option>');
			}
			init();
		}
	});
}


/**
 * 日期类型转换数字
 */
function dateToStr(date){
	var _dateStr = date.replace(/-/g,'');
	_dateStr = _dateStr.replace(/ /g,'');
	_dateStr = _dateStr.replace(/:/g,'');
	return _dateStr;
}

//翻页调用js
function getPageData(url) {
	searchReport(url);
}

/**
 * 查询
 */
function searchReport(url){
	var searchStartDate = dateToStr($("#startDate").val());
    var searchEndDate =  dateToStr($("#endDate").val());
	var searchVmIp = $("#vmPerformanceIp").val();
    var msg = "";

    if (searchStartDate != "" && searchEndDate != "" && searchStartDate > searchEndDate) {
    	msg += "开始时间应小于等于结束时间！<br>";
    }
	if(msg != ""){
	    $.compMsg({type:'error',msg:msg});
		return false;
	}
	var da_val;
	if (url == '' || url == undefined) {
		url = 'vmPerformanceSearchList.action';
		da_val = {
				startDate : searchStartDate,
				endDate : searchEndDate,
				vmIp : searchVmIp
			};
	}
	$.ajax({
		type : "POST",
		url : url,
		data : da_val,
		dataType : "JSON",
		cache : false,
		success : function(data) {
			$('#listTbody').empty();
			setSeatchBodys(data);
		}
	});
}


/**
 * 更新查询后的虚拟机性能数据
 */
function setSeatchBodys(data) {
	var table ='';
	var list = data.vmPerformanceSearchLists.list;
	var page = data.vmPerformanceSearchLists.page;
	if(list.length==0){
		$.compMsg( {
			type : 'success',
			msg : "暂无符合条件的订单数据！"
		});
//		$("#listTbody").empty();
		$("#listTbody").append(table);
		$(".pageBar").html(page);
		return;
	}
	if (!jQuery.isEmptyObject(data.vmPerformanceSearchLists.list)) {
		var str = table;
		for ( var i = 0; i < list.length; i++) {
			str += '<tr>';
			str += '<td>' + list[i].vmIp + '</td>';
			str+='<td>'+list[i].cpuUtilization+'</td>';
//			str+='<td>'+list[i].CpuSpeed+'</td>';
			str += '<td>' + list[i].memUtilization + '</td>';
			str += '<td>' + list[i].memTotalKb + '</td>';
			str += '<td>' + list[i].diskUtilization + '</td>';
			str += '<td>' + list[i].diskTotalG + '</td>';
			str += '<td>' + list[i].bytesIn + "&nbspBytes/sec" + '</td>';
			str += '<td>' + list[i].bytesOut + "&nbspBytes/sec" + '</td>';
			str += '<td>' + list[i].createTime + '</td>';
			str += '</td></tr>';
		}
//		$("#listTbody").empty();
		$("#listTbody").append(str);
		$(".pageBar").html(page);
}
}

/**
 * 导出
*/ 
function exportPerformance(){
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    var vmIp = $("#vmPerformanceIp").find("option:selected").text();
    var actionStr = 'vmPerformanceReport.action?struts.enableJSONValidation=true'
	                  + '&startDate=' + startDate
	                  + '&endDate=' + endDate
	                  + '&vmIp=' + vmIp;    
	$("#exportUrl").attr("href",actionStr);
/*	$.compMsg( {
		type : 'success',
		msg : "数据量过大时，请稍作等待！"
	});
	return;*/
}

