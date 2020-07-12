// 页面初始化
$(function() {
	// 菜单显示当前
	$(".left-menu li:contains('虚拟机备份')").addClass("selected");
	
    // 自定义form样式
    $("select,:text,:radio").uniform();
    $.uniform.update();
});


//提交按钮
function creatVmBak(){
	var vmId = $("#vmId").val();
	var vmBakName = $("#vmBakName").val();
	var backStoreTime = $("#backStoreTime").val();
    var backupStartTime = $("#backupStartTime").val();

    var rexNum = /^[0-9]*[1-9][0-9]*$/;

    var msg = "";
    if(vmId == ""){
	    msg += "请绑定虚拟机！<br>";
    }
	if(vmBakName == ""){
	    msg += "备份任务名称不能为空！<br>";
    }
	if (backStoreTime == "" || !rexNum.test(backStoreTime)) {
    	msg += "备份保留时间不能为空并且只能填写数字！<br>";
	}
    if(backupStartTime == ""){
	    msg += "备份任务开始时间不能为空！<br>";
    }

	if(msg != ""){
	    $.compMsg({type:'error',msg:msg});
		return false;
	}

	var form = document.getElementById("vmBakApplyForm");
	form.action = "vmBakApplyInfoAction.action";
	form.submit();
}

//返回按钮
function goToList(){
	var form = document.getElementById("vmBakApplyForm");
	form.action = "vmBakQueryListAction.action";
	form.submit();
}

//绑定虚拟机
function bindVm() {
	// 弹出页弹出前，已选择的虚拟机ID值，首次虚拟机ID值为undefined
	var lastVmId = $('#vmListTbody [name="vmCheckbox"]:checked').val();
	$.dialog({
		title : '绑定云主机',
		init : loadVmList('vmBakSearchVMListJsonAction.action', lastVmId),
		content : document.getElementById('bindVm'),
		ok : function() {
			// 获取选择的虚拟机ID（为了传到后台入库）以及虚拟机名称（用于返回主页面显示）
			var vmName = $('#vmListTbody [name="vmCheckbox"]:checked').parents('td').next().next().html();
			var vmId = $('#vmListTbody [name="vmCheckbox"]:checked').val();
			if (vmId == null || vmId == ''){
				displayError("请选择一个云主机！");
				return false;
			}

			// 当重新绑定时，先清除已选值（第一个span是标题，不能删除），添加“重新绑定”字样，显示虚拟机名称。
			$("#bindDiv").children('span:not(:first)').remove();
			$("#bindDiv").children('a').html("重新绑定");
			$("#bindDiv").children('span').after('<span class="apply-span-name" style="width: 240px;">' + vmName + '</span>');
			
			// vmId赋值
			$("#vmId").val(vmId);

		    return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}

var displayError = function(msg){
	$.compMsg({
		type : 'error',
		msg : msg
	});
};


var getPageData = function(url){
	loadVmList(url, $('#vmListTbody [name="vmCheckbox"]:checked').val());
};

var loadVmList = function(url, lastVmId){
	var queryVmName = $('#queryVmName').val();
	var appId = $('#appId').val();
	var queryData = {};
	if (url == 'vmBakSearchVMListJsonAction.action'){
		queryData = {'vmName':queryVmName, 'appId':appId};
	}
	$.post(url, queryData, function(data){
		$('#vmListTbody').empty();

		$.each(data.vmResultInfos, function(index, vmResultInfo){
		    var trHtml = '<tr>'
			+'<td align="center"><input type="radio" name="vmCheckbox" value="' + vmResultInfo.vmId + '" ';
		    // 当用户重新绑定虚拟机时，弹出页默认显示上一次的选择
			if (vmResultInfo.vmId == lastVmId) {
				trHtml += ' checked = "checked"';
			}
			trHtml += '/></td>'
			+'<td>'+vmResultInfo.vmId+'</td>'
			+'<td>'+vmResultInfo.vmName+'</td>'
			+'</tr>';
		    $('#vmListTbody').append(trHtml);
		});

		$('#vmListPageBarDiv').html(data.pageBar);
	}).fail(function(){
		$.compMsg({
			type : 'error',
			msg : '获取云主机列表！'
		});
	});
};