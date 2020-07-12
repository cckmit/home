$(function() {
	$("#batch").siblings().removeClass("active");
	$("#batch").addClass("active");

	// queryStatusLoading(); //从数据库循环刷新状态，有了下边从接口刷新状态，从数据库循环刷新状态的方法其实可以注掉
	// queryStatusFromResPool(); // 从接口刷新状态

});

// 查询列表
function queryVmBatch() {
	var batchVmName = $('#batchVmName').val();
	var batchVmIp = $('#batchVmIp').val();
	var batchVmFlag = $('#batchVmFlag').val();

	var form = $("<form/>");
	form.attr({
		action : "vmBatchQueryListAction.action",
		method : "post"
	});
	form.append('<input type="hidden" name="batchVmName" value="' + batchVmName
			+ '"/>');
	form.append('<input type="hidden" name="batchVmIp" value="' + batchVmIp
			+ '"/>');
	form.append('<input type="hidden" name="batchVmFlag" value="' + batchVmFlag
			+ '"/>');
	$('body').append(form);
	form.submit();
}