var basePath = obj.protocol + "//" + obj.host + "/" + contextPath;

/**
 * 获取列表数据 performancelist 为Action统一定义的List名 device_name，used_per为列表统一定义的列名
 */
function getExceededData(actionStr, tbodyId, pageBarId, displayMethod) {

	url = basePath + actionStr;
	$.ajax({
		type : "GET",
		url : url,
		data : {},
		success : function(data) {
			$('#' + tbodyId).empty();

			displayMethod(data, tbodyId);

			$('#' + pageBarId).html(data.pageBar);
			
			/* 页面上有多个select，因为ajax刷新，需要重置样式，要不然会不断的在非点击的select外加新的div */
			$("select").uniform();
			$.uniform.restore();

			/* 追加新的样式，在select外新加一个div样式  */
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
function PerTbodyAccess(data, tbodyId) {
	var performanceType = document.getElementById("performanceType").value;

	if(performanceType == "cpu"){
		$.each(data.exceededlist, function(index) {
			$('<tr>').appendTo('#' + tbodyId)
			.append('<td>' + this.appName + '</td> ')
			.append('<td>' + this[performanceType + 'OverPer'] + '%</td>')
			.append('<td>' + this[performanceType + 'OverNum'] + '</td> ')
			.append('<td>' + this[performanceType + 'OverAve'] + '%</td>')
			.append('<td>' + this[performanceType + 'NotOverPer'] + '%</td>')
			.append('<td>' + this[performanceType + 'NotOverNum'] + '</td> ')
			.append('<td>' + this[performanceType + 'NotOverAve'] + '%</td>');
		});

		while ($('#' + tbodyId + ' tr').length < 10) {
			$('#' + tbodyId).append('<tr>')
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
	if(performanceType == "mem"){
		$.each(data.exceededlist, function(index) {
			$('<tr>').appendTo('#' + tbodyId)
			.append('<td>' + this.appName + '</td> ')
			.append('<td>' + this[performanceType + 'OverPer'] + '%</td>')
			.append('<td>' + this[performanceType + 'OverNum'] + '</td> ')
			.append('<td>' + this[performanceType + 'OverAve'] + '%</td>')
			.append('<td>' + this[performanceType + 'OverFreeAve'] + '</td>')
			.append('<td>' + this[performanceType + 'NotOverPer'] + '%</td>')
			.append('<td>' + this[performanceType + 'NotOverNum'] + '</td> ')
			.append('<td>' + this[performanceType + 'NotOverAve'] + '%</td>')
			.append('<td>' + this[performanceType + 'NotOverFreeAve'] + '</td>');
		});
		while ($('#' + tbodyId + ' tr').length < 10) {
			$('#' + tbodyId).append('<tr>')
			.append('<td>&nbsp;</td>')
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
	if(performanceType == "disk"){
		$.each(data.exceededlist, function(index) {
			$('<tr>').appendTo('#' + tbodyId)
			.append('<td>' + this.appName + '</td> ')
			.append('<td>' + this[performanceType + 'Range1Per'] + '%</td>')
			.append('<td>' + this[performanceType + 'Range2Per'] + '%</td> ')
			.append('<td>' + this[performanceType + 'Range3Per'] + '%</td>');
		});

		while ($('#' + tbodyId + ' tr').length < 10) {
			$('#' + tbodyId).append('<tr>')
			.append('<td>&nbsp;</td>')
			.append('<td>&nbsp;</td>')
			.append('<td>&nbsp;</td>')
			.append('<td>&nbsp;</td>')
			.append('</tr>');
		}
	}
	
}

/**
 * 个数 - 处理ajax传来的数据
 */
function NumTbodyAccess(data, tbodyId) {
	var performanceType = document.getElementById("performanceType").value;

	if(performanceType == "cpu"){
		$.each(data.exceededlist, function(index) {
			$('<tr>').appendTo('#' + tbodyId)
			.append('<td>' + this.appName + '</td>')
			.append('<td>' + this.deviceNum + '</td>')
			.append('<td>' + this[$('#performanceType').val()+'OverNum'] + '</td> ')
			.append('<td>' + this[$('#performanceType').val()+'OverAve'] + '%</td>')
			.append('<td>' + this[$('#performanceType').val()+'NotOverNum'] + '</td> ')
			.append('<td>' + this[$('#performanceType').val()+'NotOverAve'] + '%</td>');
		});

		while ($('#' + tbodyId + ' tr').length < 10) {
			$('#' + tbodyId).append('<tr>')
			.append('<td>&nbsp;</td>')
			.append('<td>&nbsp;</td>')
			.append('<td>&nbsp;</td>')
			.append('<td>&nbsp;</td>')
			.append('<td>&nbsp;</td>')
			.append('<td>&nbsp;</td>')
			.append('</tr>');
		}
	}
	if(performanceType == "mem"){
		$.each(data.exceededlist, function(index) {
			$('<tr>').appendTo('#' + tbodyId)
			.append('<td>' + this.appName + '</td> ')
			.append('<td>' + this.deviceNum + '</td>')
			.append('<td>' + this[performanceType + 'OverNum'] + '</td> ')
			.append('<td>' + this[performanceType + 'OverAve'] + '%</td>')
			.append('<td>' + this[performanceType + 'OverFreeAve'] + '</td>')
			.append('<td>' + this[performanceType + 'NotOverNum'] + '</td> ')
			.append('<td>' + this[performanceType + 'NotOverAve'] + '%</td>')
			.append('<td>' + this[performanceType + 'NotOverFreeAve'] + '</td>');
		});
		while ($('#' + tbodyId + ' tr').length < 10) {
			$('#' + tbodyId).append('<tr>')
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
	if(performanceType == "disk"){
		$.each(data.exceededlist, function(index) {
			$('<tr>').appendTo('#' + tbodyId)
			.append('<td>' + this.appName + '</td> ')
			.append('<td>' + this.deviceNum + '</td> ')
			.append('<td>' + this[performanceType + 'Range1Num'] + '</td>')
			.append('<td>' + this[performanceType + 'Range2Num'] + '</td> ')
			.append('<td>' + this[performanceType + 'Range3Num'] + '</td>');
		});

		while ($('#' + tbodyId + ' tr').length < 10) {
			$('#' + tbodyId).append('<tr>')
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
 * 列表分页点击事件，如果页面上有多个pageBar，用参数pageBarId来区分是点击的哪个pageBar
 */
function getPageData(url, pageBarId) {
	if (pageBarId == "pageBarPerDiv") {
		getExceededData(url, "ExceededTbody", "pageBarPerDiv", PerTbodyAccess);
	} else if (pageBarId == "pageBarNumDiv") {
		getExceededData(url, "ExceededTbody2", "pageBarNumDiv", NumTbodyAccess);
	}
}

/**
 * 设备类型切换
 */
function setDeviceTypeUrl(deviceType){
	var url =  "../resourceManagement_appView/cpuExceededAction.action?deviceOverInfo.deviceType=" + deviceType + "&deviceOverInfo.performanceType=cpu";
	if(deviceType == "0"){
		$("#miniPmUrl").attr("href", url);
	}
	if(deviceType == "1"){
		$("#miniPmParUrl").attr("href", url);
	}
	if(deviceType == "2"){
		$("#pmUrl").attr("href", url);
	}
	if(deviceType == "3"){
		$("#vmUrl").attr("href", url);
	}
}


/**
 * 性能指标类型切换
 */
function setPerformanceUrl(performanceType){
	var deviceType = document.getElementById("deviceType").value;
	if(performanceType == "cpu"){
		var cpuUrl =  "../resourceManagement_appView/cpuExceededAction.action?deviceOverInfo.deviceType=" + deviceType + "&deviceOverInfo.performanceType=" + performanceType;
		$("#cpuUrl").attr("href", cpuUrl);
	}
	if(performanceType == "mem"){
		var memUrl =  "../resourceManagement_appView/memExceededAction.action?deviceOverInfo.deviceType=" + deviceType + "&deviceOverInfo.performanceType=" + performanceType;
		$("#memUrl").attr("href", memUrl);
	}
	if(performanceType == "disk"){
		var diskUrl =  "../resourceManagement_appView/diskExceededAction.action?deviceOverInfo.deviceType=" + deviceType + "&deviceOverInfo.performanceType=" + performanceType;
		$("#diskUrl").attr("href", diskUrl);
	}
}

$(function() {
	var deviceType = document.getElementById("deviceType").value;
	if(deviceType == "0"){
		$("#miniPmDiv").addClass("BussTitleButtonFocus");
		$("#miniPmParDiv").addClass("BussTitleButtonLoseFocus");
		$("#pmDiv").addClass("BussTitleButtonLoseFocus");
		$("#vmDiv").addClass("BussTitleButtonLoseFocus");
	}
	if(deviceType == "1"){
		$("#miniPmDiv").addClass("BussTitleButtonLoseFocus");
		$("#miniPmParDiv").addClass("BussTitleButtonFocus");
		$("#pmDiv").addClass("BussTitleButtonLoseFocus");
		$("#vmDiv").addClass("BussTitleButtonLoseFocus");
	}
	if(deviceType == "2"){
		$("#miniPmDiv").addClass("BussTitleButtonLoseFocus");
		$("#miniPmParDiv").addClass("BussTitleButtonLoseFocus");
		$("#pmDiv").addClass("BussTitleButtonFocus");
		$("#vmDiv").addClass("BussTitleButtonLoseFocus");
	}
	if(deviceType == "3"){
		$("#miniPmDiv").addClass("BussTitleButtonLoseFocus");
		$("#miniPmParDiv").addClass("BussTitleButtonLoseFocus");
		$("#pmDiv").addClass("BussTitleButtonLoseFocus");
		$("#vmDiv").addClass("BussTitleButtonFocus");
	}
	
	var performanceType = document.getElementById("performanceType").value;
	if(performanceType == "cpu"){
		$("#cpuLi").addClass("BussHardsoftFourc");
		$("#memLi").addClass("");
		$("#diskLi").addClass("");
	}
	if(performanceType == "mem"){
		$("#cpuLi").addClass("");
		$("#memLi").addClass("BussHardsoftFourc");
		$("#diskLi").addClass("");
	}
	if(performanceType == "disk"){
		$("#cpuLi").addClass("");
		$("#memLi").addClass("");
		$("#diskLi").addClass("BussHardsoftFourc");
	}
});
