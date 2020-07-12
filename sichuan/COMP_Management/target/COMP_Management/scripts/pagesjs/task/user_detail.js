$(function() {
	//菜单显示当前，开发时删除
	$(".left-menu li:contains('用户审批 ')").addClass("selected").next().show();
	$(".left-menu dl a:contains('用户审批')").parent().addClass("selected");
	$("#head-menu li a:contains('待办任务')").parent().addClass("head-btn-sel");
	//自定义form样式
	$(":text,select,textarea,:checkbox,:radio").uniform();
	$("#readOnly :text,#readOnly select,#readOnly textarea,#readOnly :checkbox,#readOnly :radio").attr("disabled", true);
	$.uniform.update();
	
	//通过设置默认为灰色
	$("#passLi").css({"border":"1px #999 solid","background-color":" #f0f0f0"});
	$("#auditPass").css({"color":"#777","cursor":"default"});
	
	//加载弹出层提示信息栏宽度
	$(".point").attr("style","width:80px");
	$(".point").html("&nbsp;");
	$("#goBack").click(function (){
		backList('','');
	});
	$("#cancel").click(function (){
		backList('','');
	});
	$("#auditPass").click(function (){
		auditPass($("#userId").val());
	});
	$("#auditUnPass").click(function (){
		auditNuPass($("#userId").val());
	});
});
//返回列表页面
function backList(msg,errMsg){
	$.backListMsg({msg:msg,errMsg:errMsg,url:'userApprovalList.action'});
}

function fileErrors(errors){
	var mes = '';
	//错误提示信息
	if (!jQuery.isEmptyObject(errors)) {
		if (!jQuery.isEmptyObject(errors.auditInfo)) {
			mes+=errors.auditInfo+'\n';
		}
	}
	return mes;
}
//审批通过
function auditPass(userId){
	var chargeUserId = $("#chargeUserId").val();
	if(chargeUserId==""||chargeUserId==null){
		$.compMsg({type:'error',msg:"请输入计费用户ID"});
		return false;
	}
	var userStatus = $("#userStatus").val();
	var jsonStr = '[\''+userId+'\']';
	var auditInfo = $("#auditInfo").val();
	if(auditInfo==''){
		auditInfo='审批通过';
	}
	$.dialog( {
		title : '审批通过',
		content : '请您确认是否审批通过？',
		ok : function() {
			var da_val = {'jsonStr':jsonStr,'auditInfo':auditInfo,'userStatus':userStatus,'auditStatus':'0','chargesUserId':chargeUserId};
			$.ajax( {
				url : 'userAuditPass.action',
				type : 'POST',
				data : da_val,
				cache : false,
				dataType : 'json',
				success : function(data) {
					//错误提示信息
					var mes = fileErrors(data.fieldErrors);
					if(mes!=''){
						$.compMsg({type:'error',msg:mes});
						return false;
					}
					if(data!=null){
						if(data.resultFlage=='success'){
							backList('审批成功！','');
						}else if(data.resultFlage=='failure'){
							backList('','审批失败！');
						}else if(data.resultFlage=='error'){
							backList('','数据库操作异常！');
						}
					}else{
						backList('','数据库操作异常！');
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
//审批不通过
function auditNuPass(userId){
	var userStatus = $("#userStatus").val();
	var jsonStr = '[\''+userId+'\']';
	var auditInfo = $("#auditInfo").val();
	if(auditInfo==''){
		auditInfo='审批不通过';
	}
	$.dialog( {
		title : '审批不通过',
		content : '请您确认是否审批不通过？',
		ok : function() {
			var da_val = {'jsonStr':jsonStr,'auditInfo':auditInfo,'userStatus':userStatus,'auditStatus':'1'};
			$.ajax( {
				url : 'userAuditUnPass.action',
				type : 'POST',
				data : da_val,
				cache : false,
				dataType : 'json',
				success : function(data) {
					//错误提示信息
					var mes = fileErrors(data.fieldErrors);
					if(mes!=''){
						$.compMsg({type:'error',msg:mes});
						return false;
					}
					if(data!=null){
						if(data.resultFlage=='success'){
							backList('审批成功！','');
						}else if(data.resultFlage=='failure'){
							backList('','审批失败！');
						}else if(data.resultFlage=='error'){
							backList('','数据库操作异常！');
						}
					}else{
						backList('','数据库操作异常！');
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

function initApps(){
	$("#app").html(appAry.join(", "));
	if(approvalFlag){
		$('#appStatus').removeClass();
		$('#appStatus').addClass("status_yellow");
	//	$('#showAppEdit').show();
		if($('#userStatus').val()!='0' ){
			$('#appStatus').text("注册待审");
			//$('#showAppEdit').attr("title","业务组绑定已提交审核, 绑定后业务组为 : "+approvalApps.join(", "));
		}else{
			$('#appStatus').text("业务组变更待审");
		//	$('#showAppEdit').attr("title","业务组变更已提交审核, 变更后业务组将更新为 : "+approvalApps.join(", "));
		}
	}else{
		$('#appStatus').removeClass();
		//$('#showAppEdit').hide();
		$('#appStatus').text("正常");
	}
}
function setAuditPassStyle(){
	var chargeUserId = $("#chargeUserId").val();
	if(chargeUserId=="" ||chargeUserId==null){
		$("#passLi").css({"border":"1px #999 solid","background-color":" #f0f0f0"});
		$("#auditPass").css({"color":"#777","cursor":"default"});
	}else{
		$("#passLi").css({"border":"1px #349dd2 solid","background-color":" #3CB4F0"});
		$("#auditPass").css({"color":"#fff","cursor":"pointer"});
	}
}