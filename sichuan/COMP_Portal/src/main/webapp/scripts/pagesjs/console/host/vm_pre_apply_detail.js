$(function() {
	$("#book").siblings().removeClass("active");
	$("#book").addClass("active");
	$("#vm_book").addClass("active");
	$("#book div.menu-item-child").removeClass("none");
	$("#book div.menu-item-parent i:nth-child(3)").removeClass("arrow").addClass("down");
	});

// 跳转到列表界面
function goBackForm(errMsg, msg) {
	$.backListMsg({
		msg : msg,
		errMsg : errMsg,
		url : 'vmPreApplyQueryListAction.action'
	});
}

// 修改订购状态
function goToUpdateStatusPage() {
	$.dialog({
		title : '修改订购状态',
		content : document.getElementById('modify_apply_status_div'),
		init : function() {
			
		},
		ok : function() {
			updateApplyStatus();
			return true;

		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}

function updateApplyStatus() {
//	var caseId = $('#caseId').text();
	var caseId = document.getElementById("caseId").value;
	var da_val = {
		caseId : caseId
	};
	$.ajax({
		url : 'vmPreApplyStatusUpdateAction.action',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {
			
			$("#status").text("已订购");
		}
	});
}

