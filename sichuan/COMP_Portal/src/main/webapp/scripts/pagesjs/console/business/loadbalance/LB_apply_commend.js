var queryBusinessId;
$(function() {
	// 所属业务名称显示
	queryBusinessId = $("#app").val();
	onloadApp();
	changenet();
});//初始化

//业务名称展示
function onloadApp() {
	var da_val = {
		queryBusinessId : queryBusinessId
	};
	$.ajax({
		url : 'vmOnloadResAppAction.action?struts.enableJSONValidation=true',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {
			if (data.resultRoute == "success") {
				$("#bindDiv").children('span').after(
						'<span class="apply-span-name">'
								+ data.queryBusinessName + '</span>');
				return;
			}
			if (data.resultRoute == "error") {
				window.location.href = 'exceptionIntercepor.action';
				return;
			}
			if (data.resultRoute == "failure") {
				alert("展示业务信息异常，将跳转到浏览页面");
				window.location.href = 'hostList.action?queryBusinessId='
						+ queryBusinessId;
				return;
			}
		}
	});
}