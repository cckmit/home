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
							'<td>' + this.diskIOspeed + 'Kbps/s</td>')
					.append('<td>' + this.maxDiskIOspeed + 'Kbps/s</td> ');
		} else {
			$('<tr>').appendTo('#listTbody').append(
					'<td>' + this.deviceName + '</td> ').append(
					'<td>' + this.deviceId + '</td>').append(
					'<td>' + this.cpuNum + 'C, ' + this.memorySize + 'GB</td>')
					.append('<td title = "' + this.os +'">' + getTooLongStr(this.os) + '</td>').append(
							'<td>' + this.appName + '</td>').append(
							'<td>' + this.appContacts + '</td>').append(
							'<td>' + this.diskIOspeed + 'Kbps/s</td>')
					.append('<td>' + this.maxDiskIOspeed + 'Kbps/s</td> ');
		}
	});

	while ($('#listTbody' + ' tr').length < 10) {
		$('#listTbody').append('<tr>').append('<td>&nbsp;</td>').append(
				'<td>&nbsp;</td>').append('<td>&nbsp;</td>').append(
				'<td>&nbsp;</td>').append('<td>&nbsp;</td>').append('</tr>');

	}

}

function getTooLongStr(str){
	var s = str;//要展示的字符串
	if(str.length>25){
	  s=str.substring(0,25)+"...";
	}
	return s;
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