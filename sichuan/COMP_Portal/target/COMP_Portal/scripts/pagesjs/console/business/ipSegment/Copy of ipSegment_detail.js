$(function() {
	// 菜单显示当前，开发时删除
	$(".left-menu li:has(#business_" + businessId + ")").addClass("selected").next().show();
	$(".left-menu #menu_vl_" + businessId).parent().addClass("selected");

	// 自定义form样式
	$("select,:checkbox,:text").uniform();

	$.uniform.update();
});

/**
 * 分页
 */
var getPageData = function(url) {
	location.href = url;
};

/**
 * 显示名称修改dialog
 */
var updateName = function() {

	$.dialog({
		title : '修改名称',
		content : document.getElementById('updateName'),
		ok : function() {
			updateNameAjax();
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
};

/**
 * 调用修改名称ajax
 */
var updateNameAjax = function() {

	var ipSegmentDesc = $("#updateName [name=modify_custom_name]").val();
	var ipSegmentId = $("#ipSegmentId").val();
	if (ipSegmentDesc.length > 25) {
		$.compMsg({
			type : 'error',
			msg : 'IP段名称最长为25个字符!'
		});
		return;
	}
	var parameters = {
		ipSegmentDesc : ipSegmentDesc,
		ipSegmentId : ipSegmentId
	};
	$.ajax({
		url : 'ipSegmentUpdateNameAction.action',
		type : 'POST',
		data : parameters,
		cache : false,
		dataType : 'json',
		success : function(data) {
			$.compMsg({
				type : 'success',
				msg : data.msg
			});
			$("#ipSegmentDesc").html(data.ipSegmentDesc);
			$("#titleDesc").html(data.ipSegmentDesc);
		},
		error : function(data) {
			$.compMsg({
				type : 'error',
				msg : data.msg
			});
		}
	});

};

/**
 * 释放IP段调用AJax
 */
var delIpSegment = function() {
	
	$.dialog({
		title : '删除IP段',
		content : '请您确认是否删除' + $('#ipSegmentDesc').html() + '？删除后将无法恢复！',
		ok : function() {
			$.ajax({
				url : 'ipSegmentReleaseAction.action',
				type : 'POST',
				beforeSend : function(){
					$.blockUI({ 
						message:  '<img src="../images/loading.gif" align="absmiddle" style="margin-right:20px">'+'<span id="load_span">正在删除…</span>',
						css: { width: '150px' , left : '45%' }
					}); 
				},
				data : {
					ipSegmentId : $("#ipSegmentId").val(),
					resourcePoolId : $("#resourcePool").val(),
					resourcePoolPartId : $("#resourcePoolPart").val()
				},
				success : function(data) {
					$.unblockUI();
					var aUrl = $("#goBacka").attr('href') + '&msg=' + data.msg;

					window.location = aUrl;
				},
				error : function(e) {
					$.compMsg({
						type : 'error',
						msg : e
					});
					return;
				}
			});
		},
		cancelVal : '取消',
		cancel : true,
		lock : true
	});
		
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