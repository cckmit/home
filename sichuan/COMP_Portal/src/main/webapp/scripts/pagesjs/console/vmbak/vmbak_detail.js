var obj = window.location;
var contextPath = obj.pathname.split("/")[1] + "/" + obj.pathname.split("/")[2]; 
var basePath = obj.protocol + "//"+obj.host + "/" + contextPath;

// 页面初始化
$(function() {
	// 菜单显示当前
	$(".left-menu li:contains('虚拟机备份')").addClass("selected");

	queryStatusFromResPool();
	
	/* ajax取得虚拟机备份列表信息 */
    var vmBakId = $('#vmBakId').val();
    if (vmBakId != null && vmBakId != "") {
    	var ajaxActionStr = '/queryVmBakListByAjax.action'
            + '?vmBakId=' + vmBakId;
        getListData(ajaxActionStr);
    }
});

//查询虚拟机备份任务状态
function queryStatusFromResPool() {
	var vmBakId = $('#vmBakId').val();
	var da_val = {
		vmBakId : vmBakId
	};
	$.ajax({
		type : "POST",
		url : 'vmBakQueryDetailStateByAjax.action',
		data : da_val,
		dataType : "JSON",
		cache : false,
		success : function(data) {
			var vmBakInfo = data.vmBakInfo;
			if (vmBakInfo == null || vmBakInfo == 'undefined') {
				window.location.href = 'exceptionIntercepor.action';
			}
			$("#bakupStatus").text(vmBakInfo.bakupStatusText);

			if (vmBakInfo.restoreVmBakInternalId != null && vmBakInfo.restoreVmBakInternalId != '') {
				var restoreStr = vmBakInfo.restoreTime + "使用ID为" + vmBakInfo.restoreVmBakInternalId + "的备份进行恢复，恢复状态为";
				var restoreStatusText = "";
				if (vmBakInfo.restoreStatus=="0") {
					restoreStatusText = "等待恢复";
				} else if (vmBakInfo.restoreStatus=="2") {
					restoreStatusText = "正常";
				} else if (vmBakInfo.restoreStatus=="4") {
					restoreStatusText = "恢复中";
				} else if (vmBakInfo.restoreStatus=="5") {
					restoreStatusText = "恢复失败";
				} else if (vmBakInfo.restoreStatus=="9") {
					restoreStatusText = "状态异常";
				} else if (vmBakInfo.restoreStatus=="10") {
					restoreStatusText = "任务正在等待";
				} else if (vmBakInfo.restoreStatus=="11") {
					restoreStatusText = "待创建";
				}
				restoreStr = restoreStr + restoreStatusText;
				$("#restoreStatus").text(restoreStr);
			}
		},
		error : function() {
		}
	});
}


/**
 * 获取虚拟机备份数据
 */
function searchVmBak(){
	var vmBakId = $('#vmBakId').val();
	var startTime = $('#startTime').val();
	var endTime = $('#endTime').val();
	
	if (vmBakId != null && vmBakId != "") {
		var ajaxActionStr = '/queryVmBakListByAjax.action'
            + '?vmBakId=' + vmBakId
            + '&startTime=' + startTime
            + '&endTime=' + endTime;
        getListData(ajaxActionStr);
	}
}


/**
 * 获取虚拟机备份数据
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
}

/**
 * 处理ajax传来的数据
 */
function setTbody(data) {
	$.each(data.backupList, function(index) {
		$('<tr>').appendTo('#listTbody')
		.append('<td>' + this.vmBakInternalId + '</td> ')
		.append('<td>' + this.storeSize+ '</td>')
		.append('<td>' + this.generationTime + '</td> ')
		.append('<td class="table-opt-block"><a href="#" onclick="restoreVmBak(\''+ this.vmBakInternalId +'\');">恢复</a><a href="#" onclick="delVmBak(\''+ this.vmBakInternalId +'\');">删除</a></td>');
	});

	while ($('#listTbody' + ' tr').length < 10) {
		$('#listTbody').append('<tr>')
		.append('<td>&nbsp;</td>')
		.append('<td>&nbsp;</td>')
		.append('<td>&nbsp;</td>')
		.append('<td>&nbsp;</td>')
		.append('</tr>');
	}
}

/**
 * 列表分页点击事件，如果页面上有多个pageBar，用参数pageBarId来区分是点击的哪个pageBar,该页面只有一个分页用不到pageBarId参数
 */
function getPageData(url, pageBarId){
	getListData(url);
}

/**
 * 返回
 */
function goToList(){
	var form = document.getElementById("vmBakDetailForm");
	form.action = "vmBakQueryListAction.action";
	form.submit();
}

/**
 * 恢复
 */
function restoreVmBak(vmBakInternalId) {
	var vmBakId = $('#vmBakId').val();
	$.dialog( {
		title : '恢复',
		content : '恢复后将直接覆盖到虚拟机上，请您确认是否恢复该虚拟机备份？',
		ok : function() {
			$.dialog( {
				title : false,
				clouse : false,
				width : 200,
				height : 40,
				lock : true,
				content : '请稍后，正在恢复中...',
				init: function(){
						var tmpDialog = this;
						var da_val = {vmBakId: vmBakId, vmBakInternalId: vmBakInternalId};
						$.ajax({type: "POST",
								url: 'vmBakRestoreJsonAction.action',
								dataType: "JSON",
								data: da_val,
								cache: false,
								success: function(data){
									if(data.result=="success"){
										$.compMsg({type:'success',msg:'恢复成功!'})
										tmpDialog.close();
										
										queryStatusFromResPool();
										
										var ajaxActionStr = '/queryVmBakListByAjax.action'
								            + '?vmBakId=' + vmBakId;
								        getListData(ajaxActionStr);
									}else{
										$.compMsg({type:'error',msg:'恢复失败!'})
										tmpDialog.close();
									}
								},
								error: function(){
									$.compMsg({type:'error',msg:'恢复失败!'})
									tmpDialog.close();
								}
						});//恢复结束
					}
			});
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}

/**
 * 删除
 */
function delVmBak(vmBakInternalId) {
	var vmBakId = $('#vmBakId').val();
	$.dialog( {
		title : '删除',
		content : '请您确认是否删除该虚拟机备份？',
		ok : function() {
			$.dialog( {
				title : false,
				clouse : false,
				width : 200,
				height : 40,
				lock : true,
				content : '请稍后，正在删除中...',
				init: function(){
						var tmpDialog = this;
						var da_val = {vmBakId: vmBakId, vmBakInternalId: vmBakInternalId};
						$.ajax({type: "POST",
								url: 'vmBakDelJsonAction.action',
								dataType: "JSON",
								data: da_val,
								cache: false,
								success: function(data){
									if(data.result=="success"){
										$.compMsg({type:'success',msg:'删除成功!'})
										tmpDialog.close();
										
										queryStatusFromResPool();
										
										var ajaxActionStr = '/queryVmBakListByAjax.action'
								            + '?vmBakId=' + vmBakId;
								        getListData(ajaxActionStr);
									}else{
										$.compMsg({type:'error',msg:'删除失败!'})
										tmpDialog.close();
									}
								},
								error: function(){
									$.compMsg({type:'error',msg:'删除失败!'});
									tmpDialog.close();
								}
						});//删除结束
					}
			});
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}
