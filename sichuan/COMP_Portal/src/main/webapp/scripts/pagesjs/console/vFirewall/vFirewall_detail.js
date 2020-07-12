$(function() {
	$("#vFW").siblings().removeClass("active");
	$("#vFW").addClass("active");
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

// 添加负载均衡对象
function addRule() {

	$
			.dialog({
				title : '添加防火墙规则',
				content : document.getElementById('add_rule'),
				init : function() {
					$("[name='modify_remark']")
							.val($.trim($("#remark").text()));
				},
				ok : function() {
					var fwId=$("#fwId").val();
					var fwRuleName=$("#fwRuleName").val();
					var protocol=$("#protocol").val();
					var sourceIp=$("#sourceIp").val();
					var sourcePort=$("#sourcePort").val();
					var destinationIp=$("#destinationIp").val();
					var destinationPort=$("#destinationPort").val();
					var actType = $('#actType').val();
					var appId=$("#appId").val();
					if(fwRuleName===""){
						$.compMsg({
							type : 'error',
							msg : "请填写防火墙规则名称!"
						});
						return false;
					}
					
					var ipReg =/^(((2[0-4]\d)|(25[0-5]))|(1\d{2})|([1-9]\d)|[1-9]).(((2[0-4]\d)|(25[0-5]))|(1\d{2})|([1-9]\d)|[0-9]).(((2[0-4]\d)|(25[0-5]))|(1\d{2})|([1-9]\d)|[0-9]).(((2[0-4]\d)|(25[0-5]))|(1\d{2})|([1-9]\d)|[1-9])$/;
					var re = /^([0-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/;
					
					if (sourceIp!=="") {
						$.compMsg({
							type : 'error',
							msg : "请填写正确格式的IP地址!"
						});
						return false;
					}
					if (sourcePort!==""&&!re.test(sourcePort)) {
						$.compMsg({
							type : 'error',
							msg : "端口必须为0~65535的整数!"
						});
						return false;
					}
					if (destinationIp!=="") {
						$.compMsg({
							type : 'error',
							msg : "请填写正确格式的IP地址!"
						});
						return false;
					}
					if (destinationPort!==""&&!re.test(destinationPort)) {
						$.compMsg({
							type : 'error',
							msg : "端口必须为0~65535的整数!"
						});
						return false;
					}
					var resPoolId=$("#resourcePool").val();
					var resourcePoolPart=$("#resourcePoolPart").val();
					
					var da_val = {
							'vfwRule.fwId' : fwId,
						'vfwRule.fwRuleName' : fwRuleName,
						'vfwRule.protocol' : protocol,
						'vfwRule.sourceIp' : sourceIp,
						'vfwRule.sourcePort' : sourcePort,
						'vfwRule.destinationIp' : destinationIp,
						'vfwRule.destinationPort' : destinationPort,
						'vfwRule.actType' : actType,
						'resourcePoolId':resPoolId,
						'resourcePoolPartId':resourcePoolPart,
						'appId':appId
					};
					$
							.ajax({
								type : "POST",
								url : 'addFwRuleAction.action',
								data : da_val,
								dataType : "JSON",
								cache : false,
								success : function(data) {
									window.location.reload();
								}
							});
					return true;
				},
				cancelVal : '关闭',
				cancel : function() {
					window.location.reload();
				},
				lock : true
			});

}


//恢复置位
function clearPort(){
	
}



//删除端口配置
function delFwRule(fwRuleName,fwId){
	$.dialog({
		title : '删除虚拟防火墙规则',
		content : '请您确认是否做删除操作？删除后将无法恢复！',
		ok : function() {
	        var resPoolId=$("#resourcePool").val();
	        var resourcePoolPart=$("#resourcePoolPart").val();
	        var appId=$("#appId").val();
	        $.ajax({
		        url:'delFwRuleAction.action',
		        type:'post',
		        dataType : "JSON",
		        data:{
			         'vfwRule.fwRuleName' : fwRuleName,
			         'vfwRule.fwId' : fwId,
			         'resourcePoolId':resPoolId,
			         'resourcePoolPartId':resourcePoolPart,
			         'appId':appId
		        },
		        success:function(data){
			         window.location.reload();
		           }
	           });
		   },
		   cancelVal : '关闭',
			cancel : true,
			lock : true
		});
}



//删除虚拟防火墙
function delVfw() {
	$.dialog({
		title : '删除删除虚拟防火墙',
		content : '请您确认删除虚拟防火墙？删除后将无法恢复！',
		ok : function() {
			var fwId=$("#fwId").val();
			var resPoolId=$("#resourcePool").val();
			var resourcePoolPart=$("#resourcePoolPart").val();
			var appId=$("#appId").val();
			var da_val = {
					'virfwid' : fwId,
					'resourcePoolId':resPoolId,
					'resourcePoolPartId':resourcePoolPart,
					'appId':appId
			};
			$.ajax({
				url : 'delVfwAction.action',
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
					window.location.href="vFWListAction.action";
				}
			});
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}
