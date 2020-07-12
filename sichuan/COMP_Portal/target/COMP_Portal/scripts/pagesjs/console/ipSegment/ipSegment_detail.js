$(function() {
	$("#ips").siblings().removeClass("active");
	$("#ips").addClass("active");
	optSetting();
});

//绑定操作动作
var optSetting=function (){
	
	var opts = $("#ipsopt").find("li");
	var flag=false;
	for(var i=0;i<opts.length;i++){
		if($(opts[i]).css("display")!="none"){
			flag=true;
		}
	}
	
	
	$("#operation").click(function(){
		if($("#ipsopt").css("display")==="none"&&flag){
			$("#ipsopt").slideDown('slow');
		}else{
			$("#ipsopt").slideUp('slow');
		}
	});
	
	$("#ipsopt").mouseleave(function() {
		$("#ipsopt").slideUp('slow');
	});

};


// 跳转到列表界面
var goBackForm = function(errMsg, msg) {
	$.backListMsg({
		msg : msg,
		errMsg : errMsg,
		url : 'ipSegmentQueryListAction.action'
	});
};

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
			$("#customName").html(data.ipSegmentDesc);
			$("#title-status > h2").html(data.ipSegmentDesc);
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
				beforeSend : function() {
					$.blockUI({
						message : '<img src="../images/loading.gif" align="absmiddle" style="margin-right:20px">'
								+ '<span id="load_span">正在删除…</span>',
						css : {
							width : '150px',
							left : '45%'
						}
					});
				},
				data : {
					ipSegmentId : $("#ipSegmentId").val(),
					resourcePoolId : $("#resourcePool").val(),
					//resourcePoolPartId : $("#resourcePoolPart").val()
				},
				success : function(data) {
					$.unblockUI();
					location.href = 'ipSegmentQueryListAction.action?msg=' + data.msg;
				},
				error : function(e) {
					$.compMsg({
						type : 'error',
						msg : e.msg
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