function Initmenu(deviceType) {
	$(".left-menu li:contains('月度性能统计')").addClass("selected").next().show();
	if (deviceType == "0") {
		$(".left-menu dl a:contains('物理机')").parent().addClass("selected");
	} else if (deviceType == "1") {
		$(".left-menu dl a:contains('虚拟机')").parent().addClass("selected");
	}

	$("#head-menu li a:contains('统计分析')").parent().addClass("head-btn-sel");

	$(":text").uniform();
}

var obj = window.location;
var contextPath = obj.pathname.split("/")[1] + "/" + obj.pathname.split("/")[2];
var basePath = obj.protocol + "//" + obj.host + "/" + contextPath;

/**
 * 获取性能列表数据 为ajax返回的统一的List名
 */
function getListData(actionStr, deviceType) {
	var url = basePath + actionStr;
	$.ajax({
		type : "GET",
		url : url,
		data : {},
		success : function(data) {
			$('#listTbody').empty();
			setTbody(data, deviceType);
			// $('#pageBarDiv').html(data.pageBar);
			/* 页面上有多个select，因为ajax刷新，需要重置样式，要不然会不断的在非点击的select外加新的div */
			$("select").uniform();
			$.uniform.restore();

			/* 追加新的样式，在select外新加一个div样式 */
			$("select").uniform();
			$.uniform.update();
		},
		error : function(data) {
			var mes = fieldErrors(data.fieldErrors);
			if (mes != '') {
				$.compMsg({
					type : 'error',
					msg : mes
				});
			}
			if (data.resultMessage != null) {
				if (data.resultFlage == 'success') {
					backList(data.resultMessage, '');
					flage = false;
				} else if (data.resultFlage == 'failure') {
					$.compMsg({
						type : 'error',
						msg : data.resultMessage
					});
				} else if (data.resultFlage == 'error') {
					$.compMsg({
						type : 'error',
						msg : data.resultMessage
					});
				}
			}
		},
		dataType : "json"
	});
};

/**
 * 百分比 - 处理ajax传来的数据
 */
function setTbody(data, deviceType) {

	$.each(data.monthPerformanceInfolist, function(index) {
		// 物理机
		if (deviceType == 0) {
			$('<tr>').appendTo('#listTbody').append(
					'<td>' + this.deviceName + '</td> ').append(
					'<td>' + this.deviceId + '</td>').append(
					'<td>' + this.serverType + '</td>').append(
					'<td>' + this.cpuNum + 'C, ' + this.memorySize + 'GB</td>')
					.append('<td title = "' + this.os +'">' + getTooLongStr(this.os) + '</td>').append(
							'<td>' + this.appName + '</td>').append(
							'<td>' + this.appContacts + '</td>').append(
							'<td>' + this.swapMemUsedPer + '%</td>')
					.append('<td>' + this.maxSwapMemUsedPer + '%</td> ');
		} else {
			$('<tr>').appendTo('#listTbody').append(
					'<td>' + this.deviceName + '</td> ').append(
					'<td>' + this.deviceId + '</td>').append(
					'<td>' + this.cpuNum + 'C, ' + this.memorySize + 'GB</td>')
					.append('<td title = "' + this.os +'">' + getTooLongStr(this.os) + '</td>').append(
							'<td>' + this.appName + '</td>').append(
							'<td>' + this.appContacts + '</td>').append(
							'<td>' + this.swapMemUsedPer + '%</td>')
					.append('<td>' + this.maxSwapMemUsedPer + '%</td> ');
		}
	});

	while ($('#listTbody' + ' tr').length < 10) {
		$('#listTbody').append('<tr>').append('<td>&nbsp;</td>').append(
				'<td>&nbsp;</td>').append('<td>&nbsp;</td>').append(
				'<td>&nbsp;</td>').append('<td>&nbsp;</td>').append('</tr>');

	}

}

/**
 * 列表分页点击事件，如果页面上有多个pageBar，用参数pageBarId来区分是点击的哪个pageBar,该页面只有一个分页用不到pageBarId参数
 */
function getPageData(url, pageBarId) {
	getListData(url);
}

/**
 * 按钮转换：cpu、内存和磁盘之间
 */
function setPerformanceUrl(deviceType, performanceType) {
	// var deviceType = $('#deviceType').val();
	if (performanceType == "cpu") {
		var cpuUrl = "../report/cpuDevicePerformanceAction.action?monthPerformanceInfo.deviceType="
				+ deviceType
				+ "&monthPerformanceInfo.orderType=0&monthPerformanceInfo.dateType=0";
		$("#cpuUrl").attr("href", cpuUrl);
	}
	if (performanceType == "mem") {
		var memUrl = "../report/memDevicePerformanceAction.action?monthPerformanceInfo.deviceType="
				+ deviceType
				+ "&monthPerformanceInfo.orderType=0&monthPerformanceInfo.dateType=0";
		$("#memUrl").attr("href", memUrl);
	}
	if (performanceType == "page") {
		var pageUrl = "../report/pageSpaceDevicePerformanceAction.action?monthPerformanceInfo.deviceType="
				+ deviceType
				+ "&monthPerformanceInfo.orderType=0&monthPerformanceInfo.dateType=0";
		$("#pageUrl").attr("href", pageUrl);
	}
	if (performanceType == "io") {
		var ioUrl = "../report/ioDevicePerformanceAction.action?monthPerformanceInfo.deviceType="
			+ deviceType
			+ "&monthPerformanceInfo.orderType=0&monthPerformanceInfo.dateType=0";
		$("#ioUrl").attr("href", ioUrl);
	}
}

/**
 * 资源池下拉change ajax查分区列表
 */
function selectResPool(resPoolId, poolPartId) {

	if (resPoolId !== "") {
		$
				.ajax({
					url : 'getPoolPartListByAjax.action?resPoolId=' + resPoolId,
					type : 'post',
					dataType : 'json',
					data : {},
					success : function(data) {
						$('#partId').empty();
						$('#partId')
								.append(
										'<option value="" selected="selected">-请选择 -</option>');
						var poolPartList = data.poolPartList;
						for (i = 0; i < poolPartList.length; i++) {
							$('#partId').append(
									'<option value='
											+ poolPartList[i].poolPartId + '>'
											+ poolPartList[i].poolPartName
											+ '</option>');
						}
						var partName = $('#partId').find(
								'option[value="' + poolPartId + '"]').text();
						$('#partId').find('option[value="' + poolPartId + '"]')
								.attr("selected", "selected");
						if (partName !== '') {
							$('#uniform-partId span:first').text(partName);
						}
					}
				});
	} else {
		$('#partId').empty();
		$('#partId').append(
				'<option value="" selected="selected">-请选择 -</option>');
		$.uniform.update('#partId');
	}

}

/**
 * 提交查询条件
 */
function searchReport() {
	var form = $("#searchForm");
	form.submit();
}


/**
 * 导出month
 */
function exportMonthDiv(){
	$.dialog({
		title: '统计数据导出（导出所有资源类型的月度性能统计数据）',
		content: document.getElementById('exportMonthDiv'),
		init:function(){
			$("#appIdExportMonth").val($("#appId").val());//下拉菜单的值
			$("#uniform-resMonthPoolId span").html($("#resMonthPoolId option:selected").html());
			$("#uniform-resMonthPoolId span").css('text-align','left');
			$("#uniform-partMonthId span").html($("#partMonthId option:selected").html());
			$("#uniform-partMonthId span").css('text-align','left');
			$("#startMonthDateExport").val($("#startDate").val());
		    $("#endMonthDateExport").val($("#endDate").val());
		},
		button : 
			[  {
			name : '导出',
			callback : function() {
				this.button( {
					name : '导出',
					disabled : true
				});
				if(validationSelect()){
				    this.button({
					    name: '导出',
					    disabled: false
				    });
				    return false;
			    }
				exportReportSelect();
				return true;
			},
			focus : true
		},{
			name : '全部导出',
			callback : function() {
				this.button( {
					name : '全部导出',
					disabled : true
				});
				if(true){
				    this.button({
					    name: '全部导出',
					    disabled: false
				    });
				   // return false;
			    }
				exportReportMonth();
				return true;
			},
			focus : false
		} ],
		cancelVal: '取消',
		cancel:true,
		lock: true
	});
	$('.aui_buttons button:eq(1)').addClass("aui_state_highlight");
}

function validationSelect(){
	var resPoolId = $("#resMonthPoolId").val();
	var partId = $("#partMonthId").val();
	var startDate = $("#startMonthDateExport").val();
    var endDate = $("#endMonthDateExport").val();

    var msg = "";
    if(resPoolId == ""){
	    msg += "资源池不能为空！<br>";
    }
    if(partId == ""){
	    msg += "分区不能为空！<br>";
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

    return date;
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

function exportReportMonth() {
	var startDate = $("#startMonthDateExport").val();
    var endDate = $("#endMonthDateExport").val();
    var url = $("#exportMonth").attr("href")+"?exportWay=monthPerAll&startMonthDateExport="+startDate+"&endMonthDateExport="+endDate+"&rd=" + Math.random();
    window.location.href=url;
}
function exportReportSelect() {
	var resPoolId = $("#resMonthPoolId").val();
	var dateType = $('input[name="dateType"]:checked').val();
	var poolPartId = $("#partMonthId").val();
	var startDate = $("#startMonthDateExport").val();
    var endDate = $("#endMonthDateExport").val();
    var url = $("#exportMonth").attr("href")+"?exportWay=monthPerSelected&dateType="+dateType+"&resPoolId="+resPoolId+"&poolPartId="+poolPartId+"&startMonthDateExport="+startDate+"&endMonthDateExport="+endDate+"&rd=" + Math.random();
    window.location.href=url;
}

function selectMonthResPool(resMonthPoolId, poolMonthPartId) {

	if (resMonthPoolId !== "") {
		$
				.ajax({
					url : 'getPoolPartListByAjax.action?resPoolId=' + resMonthPoolId,
					type : 'post',
					dataType : 'json',
					data : {},
					success : function(data) {
						$('#partMonthId').empty();
						$('#partMonthId')
								.append(
										'<option value="" selected="selected">-请选择 -</option>');
						var poolPartList = data.poolPartList;
						for (i = 0; i < poolPartList.length; i++) {
							$('#partMonthId').append(
									'<option value='
											+ poolPartList[i].poolPartId + '>'
											+ poolPartList[i].poolPartName
											+ '</option>');
						}
						var partName = $('#partMonthId').find(
								'option[value="' + poolMonthPartId + '"]').text();
						$('#partMonthId').find('option[value="' + poolMonthPartId + '"]')
								.attr("selected", "selected");
						if (partName !== '') {
							$('#uniform-partMonthId span:first').text(partName);
						}
					}
				});
	} else {

		$('#partMonthId').empty();
		$('#partMonthId').append(
				'<option value="" selected="selected">-请选择 -</option>');
		$.uniform.update('#partMonthId');
	}
}

function getTooLongStr(str){
	var s = str;//要展示的字符串
	if(str.length>25){
	  s=str.substring(0,25)+"...";
	}
	return s;
}
