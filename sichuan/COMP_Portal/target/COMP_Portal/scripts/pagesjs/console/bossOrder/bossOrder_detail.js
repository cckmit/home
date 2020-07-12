$(function() {

	$("#bossOrder").siblings().removeClass("active");
	$("#bossOrder").addClass("active");

	$(".status-tab a").unbind('click');
});

// 跳转到列表界面
function goBackForm(errMsg, msg) {
	$.backListMsg({
		msg : msg,
		errMsg : errMsg,
		url : 'bossOrderList.action'
	});
}

// 修改订购状态
function goToUpdateStatusPage() {
	$.dialog({
		title : '修改操作状态',
		content : document.getElementById('modify_apply_status_div'),
		init : function() {
			
		},
		ok : function() {
			updateStatus();
			return true;

		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}

function updateStatus() {
	var bossOrderId = document.getElementById("bossOrderId").value;
	var da_val = {
			bossOrderId : bossOrderId
	};
	$.ajax({
		url : 'bossOrderStatusUpdateAction.action',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {
			
			$("#status").text("已操作");
		}
	});
}

