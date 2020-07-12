$(function() {
	$("#fileStore").siblings().removeClass("active");
	$("#fileStore").addClass("active");
	optSetting();
});

// 绑定操作动作
var optSetting = function() {

	var opts = $("#ipopt").find("li");
	var flag = false;
	for (var i = 0; i < opts.length; i++) {
		if ($(opts[i]).css("display") != "none") {
			flag = true;
		}
	}

	$("#operation").click(function() {
		if ($("#ipopt").css("display") === "none" && flag) {
			$("#ipopt").slideDown('slow');
		} else {
			$("#ipopt").slideUp('slow');
		}
	});

	$("#ipopt").mouseleave(function() {
		$("#ipopt").slideUp('slow');
	});

};

// 跳转到列表界面
var goBackForm = function(errMsg, msg) {
	$.backListMsg({
		msg : msg,
		errMsg : errMsg,
		url : 'ipListAction.action'
	});
};

/**
 * 分页
 */
var getPageData = function(url) {
	location.href = url;
};

/**
 * 展示不能删除的提示
 */
var noticeReleaseMsg = function(msg) {
	$.compMsg({
		type : 'error',
		msg : msg
	});
};



// 绑定虚拟机
function bindVm() {
	var lastVmId = $('#vmListTbody [name="vmCheckbox"]:checked').val();
	$.dialog({
		title : '绑定虚拟机',
		init : loadVmList('vmListJson.action', lastVmId),
		content : document.getElementById('bindVm'),
		ok : function() {
			var vmName = $('#vmListTbody [name="vmCheckbox"]:checked').parents(
					'td').next().html();
			var vmId = $('#vmListTbody [name="vmCheckbox"]:checked').val();
			var privateIp = $('#vmListTbody [name="vmCheckbox"]:checked')
					.parents('td').next().next().html();
			var os = $('#vmListTbody [name="vmCheckbox"]:checked')
					.parents('td').next().next().next().html();
			if (vmId == null || vmId == '') {
				displayError("请选择一个虚拟机！");
				return false;
			}

			$("#vmName").text(vmName);
			$("#bind").html("重新绑定");
			$("#vmId").val(vmId);
			$("#os").val(os);
			$("#vmPrivateIp").val(privateIp);
			clearPort();
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}

function loadVmList(url, vmId) {
	var vmName = $('#queryVmName').val();
	var queryData = {};
	queryData = {
		'vm.vmName' : vmName
	};

	$
			.post(
					url,
					queryData,
					function(data) {
						$('#vmListTbody').empty();
						$
								.each(
										data.vmList,
										function(index, vm) {
											var trHtml = '<tr>'
													+ '<td align="center"><input type="radio" name="vmCheckbox" value="'
													+ vm.vmId + '" ';
											if (vm.vmId == vmId) {
												trHtml += ' checked = "checked"';
											}
											trHtml += '/></td>'
													+ '<td>'
													+ vm.vmName
													+ '</td>'
													+ '<td>'
													+ (vm.privateIp == null ? ''
															: vm.privateIp)
													+ '</td>' + '<td>' + vm.os
													+ '</td>' + '</tr>';
											$('#vmListTbody').append(trHtml);
										});
						$('#vmListPageBarDiv').html(data.pageBar);

					}).fail(function() {
				$.compMsg({
					type : 'error',
					msg : '获取虚拟机列表失败！',
					parent : '#bindIp'
				});
			});
}
$("input[name='portConfig.mappingMode']").change(function() {
	var os=$("#os").val().toLowerCase();
	var protocol=$("#protocol").val();
	if(!os){
		$.compMsg({
			type : 'warn',
			msg : '请先绑定虚拟机！'
		});
		$('input[name="mappingMode"]:eq(0)').prop("checked",true);
		return
	}
	
	if (this.value==="1"||protocol==="2") {
		$("#vmPort").val("");
		$("#publicPort").val("");
		$("#vmPort").prop("readonly", true);
		$("#publicPort").prop("readonly", true);
	}else{
		$("#vmPort").prop("readonly", false);
		$("#publicPort").prop("readonly", false);
	
		
		switch (os){
		case 'windows':
			$("#vmPort").val("3389");
			$("#publicPort").val("3389");
			break;
		case 'linux': 
			$("#vmPort").val("22");
			$("#publicPort").val("22");
			break;
		default :
			$("#vmPort").val("");
			$("#publicPort").val("");
			break;
		}
	}
});

//恢复置位
function clearPort(){
	$('input[name="portConfig.mappingMode"]:eq(0)').prop("checked",true);
	$("#protocol").val("0");	
	$("#vmPort").val("");
	$("#publicPort").val("");
}

$("#protocol").change(function(){
	if(this.value==="2"){
		$("#vmPort").val("");
		$("#publicPort").val("");
		$("#vmPort").prop("readonly", true);
		$("#publicPort").prop("readonly", true);
	}else{
		$("#vmPort").prop("readonly", false);
		$("#publicPort").prop("readonly", false);
	}
});


//删除分布式文件存储
function delFs() {
	var resPoolId=$("#resourcePool").val();
	var resourcePoolPart=$("#resourcePoolPart").val();
	var appId=$('#appId').val();
	$.dialog({
		title : '删除分布式文件存储',
		content : '请您确认删除分布式文件存储？删除后将无法恢复！',
		ok : function() {
			var fsId=$("#fsId").val();
			var da_val = {
					'fsId' : fsId,
					'resourcePoolId':resPoolId,
					'resourcePoolPartId':resourcePoolPart,
					'appId' : appId
			};
			$.ajax({
				url : 'fileStoreDeleteAction.action',
				type : 'POST',
				data : da_val,
				cache : false,
				dataType : 'json',
				beforeSend : function(){
					$.blockUI({ 
						message:  '<img src="../images/loading.gif" align="absmiddle" style="margin-right:20px">'+'<span id="load_span"虚拟防火墙删除中，请稍后...</span>',
						css: { width: '250px' , left : '45%' }
					}); 
				},
				success : function(data) {
					$.unblockUI();
					window.location.href="fileStoreListAction.action";
				}
			});
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}