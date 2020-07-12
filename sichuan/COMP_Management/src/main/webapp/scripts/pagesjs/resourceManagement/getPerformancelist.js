var basePath = obj.protocol + "//"+obj.host + "/" + contextPath;

/**
 *获取性能列表数据 
 *performancelist 为Action统一定义的List名
 *device_name，used_per为列表统一定义的列名
 */
function getPerformanceData(actionStr, dataTbody){
    var url = basePath + actionStr;
    
	$.ajax({
	    type : "GET",
	    url : url,
	    data:{},
	    success : function(data){
			$('#'+ dataTbody).empty();
			var len = data.performancelist.length;
			$.each(data.performancelist,function(index){
					$('<tr>').appendTo('#'+dataTbody)
			    	.append('<td>'+this.device_name+'</td>')
			    	.append('<td>'+this.used_per+"%"+'</td>');
			});
			if(len<10){
				for(var i=0;i<10-len;i++){
					$('<tr>').appendTo('#'+dataTbody)
			    	.append('<td>&nbsp;</td>')
			    	.append('<td>&nbsp;</td>');
				}
			}
			
			$('#pageBarDiv').html(data.pageBar);

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
 * 列表分页点击事件，如果页面上有多个pageBar，用参数pageBarId来区分是点击的哪个pageBar,该页面只有一个分页用不到pageBarId参数
 */
function getPageData(url, pageBarId){
	getPerformanceData(url,performanceTbodyId);
};

/**
 * 按钮转换：物理机和虚拟机之间
 */
function setDeviceTypeUrl(device_type){
	var appId = $('#appId').val();
	var nodeId = $('#nodeId').val();
	var treeNodeName = $('#treeNodeName').val();
	
	var deviceTypeurl =  "../resourceManagement_appView/cpuUsedPerReportAction.action?device_type="
		+device_type+"&appId="+appId+"&nodeId="+nodeId+"&treeNodeName="+treeNodeName;

	if(device_type == "2"){
		$("#pmUrl").attr("href", deviceTypeurl);
	}
	if(device_type == "3"){
		$("#vmUrl").attr("href", deviceTypeurl);
	}
	if(device_type == "0"){
		$("#minipmUrl").attr("href", deviceTypeurl);
	}
	if(device_type == "1"){
		$("#minipmparUrl").attr("href", deviceTypeurl);
	}
}


/**
 * 按钮转换：cpu、内存和磁盘之间
 */
function setPerformanceUrl(performance_type){
	var appId = $('#appId').val();
	var nodeId = $('#nodeId').val();
	var device_type = $('#device_type').val();
	var treeNodeName = $('#treeNodeName').val();
	
	if(performance_type == "cpu"){
		var cpuUrl =  "../resourceManagement_appView/cpuUsedPerReportAction.action?device_type="
			+device_type+"&appId="+appId+"&nodeId="+nodeId+"&treeNodeName="+treeNodeName;
	    $("#cpuUrl").attr("href",cpuUrl);
	}
	if(performance_type == "mem"){
		var memUrl =  "../resourceManagement_appView/memUsedPerReportAction.action?device_type="
			+device_type+"&appId="+appId+"&nodeId="+nodeId+"&treeNodeName="+treeNodeName;
		$("#memUrl").attr("href",memUrl);
	}
	if(performance_type == "disk"){
		var diskUrl =  "../resourceManagement_appView/diskUsedPerReportAction.action?device_type="
			+device_type+"&appId="+appId+"&nodeId="+nodeId+"&treeNodeName="+treeNodeName;
		$("#diskUrl").attr("href",diskUrl);
	}
}
//检验错误信息
//返回错误提示内容
function fieldErrors(errors){
	var mes = '';
	if(!jQuery.isEmptyObject(errors)){
		if(!jQuery.isEmptyObject(errors.roleName)){
			mes+=errors.roleName+'\n';
		}
	}
	return mes;
}
/**
 * 初始设置
 */
$(function() {
	device_type = $('#device_type').val();
	if(device_type == "2"){
		$("#pmDiv").addClass("BussTitleButtonFocus");
		$("#vmDiv").addClass("BussTitleButtonLoseFocus");
		$("#minipmDiv").addClass("BussTitleButtonLoseFocus");
		$("#minipmparDiv").addClass("BussTitleButtonLoseFocus");
	}
	if(device_type == "3"){
		$("#pmDiv").addClass("BussTitleButtonLoseFocus");
		$("#vmDiv").addClass("BussTitleButtonFocus");
		$("#minipmDiv").addClass("BussTitleButtonLoseFocus");
		$("#minipmparDiv").addClass("BussTitleButtonLoseFocus");
	}
	if(device_type == "0"){
		$("#pmDiv").addClass("BussTitleButtonLoseFocus");
		$("#vmDiv").addClass("BussTitleButtonLoseFocus");
		$("#minipmDiv").addClass("BussTitleButtonFocus");
		$("#minipmparDiv").addClass("BussTitleButtonLoseFocus");
	}
	if(device_type == "1"){
		$("#pmDiv").addClass("BussTitleButtonLoseFocus");
		$("#vmDiv").addClass("BussTitleButtonLoseFocus");
		$("#minipmDiv").addClass("BussTitleButtonLoseFocus");
		$("#minipmparDiv").addClass("BussTitleButtonFocus");
	}
});
