$(function() {
	$("#vlan3").siblings().removeClass("active");
	$("#vlan3").addClass("active");
	optSetting();
	
});

//绑定操作动作
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

function deleteVlan(caseId) {
	$
			.dialog({
				title : '删除Vlan',
				content : '请您确认是否确认删除该Vlan？删除后将无法恢复！',
				ok : function() {
					$
							.blockUI({
								message : '<img src="../images/loading.gif" align="absmiddle" style="margin-right:20px">'
										+ '<span id="load_span">正在删除…</span>',
								css : {
									width : '150px',
									left : '45%'
								}
							});
					window.location = "vlanCancelAction3Phase.action?caseId="
							+ caseId;
				},
				cancelVal : '取消',
				cancel : true,
				lock : true
			});
}


//删除vlan
function delVlan(){
	var caseId=$('#caseId').val();
	$.dialog({
		title : '删除Vlan',
		content : '请您确认删除Vlan？删除后将无法恢复！',
		ok : function() {
			var da_val = {
					'caseId' : caseId
			};
			$.ajax({
				url : 'vlanCancelAction3Phase.action',
				type : 'POST',
				data : da_val,
				cache : false,
				dataType : 'json',
				success : function(data) {			
					if (data.resultPath == "success") {
						var msg = "删除VLAN已提交，请等待审核！";
						$.compMsg({
							type : 'success',
							msg : msg,
							callback:function(){
								window.location.href="vlanQueryListAction3Phase.action";
							}
						})
					}else if(data.resultPath == "failure"){
						var msg = "该vlan未生效，无法删除！"
							$.compMsg({
								type : 'error',
								msg : msg,
								callback:function(){
									window.location.reload();
								}
							})
					}else{
						var msg = "删除该Vlan失败！"
						$.compMsg({
							type : 'error',
							msg : msg,
							callback:function(){
								window.location.reload();
							}
						})
					} 
				}
			});
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});

}

