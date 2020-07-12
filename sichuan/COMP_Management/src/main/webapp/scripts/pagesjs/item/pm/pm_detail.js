$(function() {
	//菜单显示当前，开发时删除
	$(".left-menu li:contains('服务条目管理')").addClass("selected").next().show();
	$(".left-menu dl a:contains('物理机')").parent().addClass("selected");
	$("#head-menu li a:contains('产品管理')").parent().addClass("head-btn-sel");
	//自定义form样式
	$(":text,select,textarea,:checkbox,:radio").uniform();
	$("textarea,:text").attr('disabled','disabled');
	$("select").attr('disabled','disabled');
	$.uniform.update();
	//加载弹出层提示信息栏宽度
	$(".point").attr("style","width:80px");
	$(".point").html("&nbsp;");
	//取消
	$("#cancel").click(function(){
		backList('','');
	});
	//返回
	$("#goBack").click(function(){
		backList('','');
	});
	initStandard();
	initAuditLog();
	//fomartDate();//格式化日期
});
/*//格式化日期
function fomartDate(){
	var sellStartTime = $("#sellStartTime").val();
	var sellEndTime = $("#sellEndTime").val();
	if(sellEndTime.length==14){
		var sellEndTimeStr = sellEndTime.substring(0,4)+'-'+sellEndTime.substring(4,6);
		sellEndTimeStr += '-'+sellEndTime.substring(6,8)+' '+sellEndTime.substring(8,10);
		sellEndTimeStr += ':'+sellEndTime.substring(10,12)+':'+sellEndTime.substring(12,14);
		$("#sellEndTime").val(sellEndTimeStr);
	}
	if(sellStartTime.length==14){
		var sellStartTimeStr = sellStartTime.substring(0,4)+'-'+sellStartTime.substring(4,6);
		sellStartTimeStr += '-'+sellStartTime.substring(6,8)+' '+sellStartTime.substring(8,10);
		sellStartTimeStr += ':'+sellStartTime.substring(10,12)+':'+sellStartTime.substring(12,14);
		$("#sellStartTime").val(sellStartTimeStr);
	}
}*/
//此处不能使用form跳转插件，因为：带有其他参数
//返回列表页面
function backList(msg,errMsg){
	$("#msg").val(msg);
	$("#errMsg").val(errMsg);
	$("#formId").attr('action','pmItems.action');
	$("#formId").submit();
}
//资源规格初始化.
function initStandard(){
	var standardId = $("#standardId").val();
	var da_val = {"standardId":standardId};
    $.ajax({
        type: "POST",
        url: 'pmStandardDetail.action',
        data: da_val,
        dataType: "JSON",
        cache: false,
        success: function(data){
			if (data == null) {
				backList('','加载条目关联规格数据库异常！');
				return false;
			}
			var errors = standardFieldErrors(data.fieldErrors);
			if(errors!=''){
				$.compMsg({type:'error',msg:errors});
				return false;
			}
			if(!jQuery.isEmptyObject(data)){
				$("#standardlist").empty();
				 var flage = true;
				 if(data.length==1){
				 	var html = '<tr>' +
					'<td>' +data[0].standardName +
					'</td>' +
					'<td>' +data[0].pmTypeName +
					'</td>' +
					'<td>' +data[0].cpuNum +
					'</td>' +
					'<td>' +data[0].ramSize +
					'</td>' +
					'<td>' + data[0].discSize +
					'</td>' +
					'</tr>';
					$("#approval-relate-resource").find("table").append(html);
				 }else{
					backList('','加载条目关联规格数据库异常！');
				 }
			}else{
				backList('','加载条目关联规格数据库异常！');
			}
        }
    });
}
//
function initAuditLog(){
	var itemId = $("#itemId").val();
	var da_val = {"itemId":itemId};
    $.ajax({
        type: "POST",
        url: 'itemAuditLog.action',
        data: da_val,
        dataType: "JSON",
        cache: false,
        success: function(data){
			if (data == null) {
				backList('','数据库操作异常！');
				return false;
			}
			var errors = standardFieldErrors(data.fieldErrors);
			if(errors!=''){
				$.compMsg({type:'error',msg:errors});
				return false;
			}
			if(!jQuery.isEmptyObject(data)){
			 	if(data.audit!=null){
					var auditTime = data.audit.auditTime;
					var auditUser = data.audit.auditUser;
					var auditInfo = data.audit.auditInfo;
					$("#auditTime").text(auditTime);
					$("#auditUser").text(auditUser);
					$("#auditInfo").text(auditInfo);
				}
				if(data.release!=null){
					var releaseTime = data.release.auditTime;
					var releaseUser = data.release.auditUser;
					var releaseInfo = data.release.auditInfo;
					$("#releaseTime").text(releaseTime);
					$("#releaseUser").text(releaseUser);
					$("#releaseInfo").text(releaseInfo);
			 	}
			}else{
			}
        }
    });
}
