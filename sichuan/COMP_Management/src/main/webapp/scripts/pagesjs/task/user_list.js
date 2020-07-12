$(function() {
	//菜单显示当前，开发时删除
	$(".left-menu li:contains('用户审批 ')").addClass("selected").next().show();
	$(".left-menu dl a:contains('用户审批')").parent().addClass("selected");
	$("#head-menu li a:contains('待办任务')").parent().addClass("head-btn-sel");
	//自定义form样式
	$(":radio,:text,:checkbox,select").uniform();
	
	//加载弹出层提示信息栏宽度
	$(".point").attr("style","width:80px");
	$(".point").html("&nbsp;");
	$("#search").click(function (){
		queryUsers();
	});
	$("#selectAll").click(function (){
		checkAll(this);
	});
	$("#beforeCreateTime").val(_fomart8($("#beforeCreateTime").val()));
	$("#afterCreateTime").val(_fomart8($("#afterCreateTime").val()));
	
	
	var $box = $('.cut');
	$box.each( function(index, jdom) {
		var objString = $(jdom).text();
		var byteValLen = 0;
		var returnText = "";
		var maxTextLength = 29*2;
		for (var i = 0; i < objString.length; i++) {
	        (objString[i].match(/[^x00-xff]/ig) != null) ? (byteValLen += 2) : (byteValLen += 1);
	        returnText += objString[i];
	        if (byteValLen > maxTextLength) {
	        	$(jdom).attr('title',objString);
				objString = $(jdom).text(returnText.substring(0,i-1) + '...');
	            break;
	        }
	    }
	});
});
function checkAll(obj){
	if($(obj).is(':checked')){
		$("[name='select']").each(function (){
			$(this).attr('checked',true);
		});
	}else{
		$("[name='select']").each(function (){
			$(this).attr('checked',false);
		});
	}
	$.uniform.update();
}

/**
 * 获取选中的用户列表checkbox
 */
var getCheckVal = function() {
    var res = new Array();
    $("input[name='select']:checked").each(function() {
	res.push($(this).val());
    });
    return res;
};

function validateStatusType(){
	var statusArry = new Array();
	$("input[name='select']:checked").each(function() {
		statusArry.push($(this).parents().parents().next().attr("value"));
	});
	for(var i=1;i<statusArry.length;i++){
		if(statusArry[0]!=statusArry[i]){
			return false;
		}
	}
	return true;
}
//批量审批
function approvalUserMore(obj){
	var userIds = getCheckVal();
    if (userIds.length < 1){
    	$.compMsg( {
  			type : 'warn',
  			msg : "请选择要审批的用户!"
  		});
	/*$.dialog({
		title: '错误',
		content: '请至少选择一个用户!',
		cancelVal: '确认',
		cancel: true,
		lock: true
	});*/
    	return;
    }
    if(!validateStatusType()){
    	$.compMsg( {
  			type : 'warn',
  			msg : "请选择同一状态的用户!"
  		});
    	/*$.dialog({
    		title: '错误',
    		content: '请选择同一状态的用户!',
    		cancelVal: '确认',
    		cancel: true,
    		lock: true
    	});*/
    	return;
    }
    
	$.dialog({
		title: '批量审批',
		content: document.getElementById('approvalUserMore'),
		button:[{
            name: '通过',
            callback: function () {
				auditPass();
			},
			focus: true
		},{
            name: '不通过',
            callback: function () {
				auditNuPass();
			}
		}],
		cancelVal: '取消',
		cancel:true,
		lock: true
	});
}

//用户详情
function onUserDetail(userId,userStatus){
    if (userId != '') {
        $("#userId").val(userId);
        $("#userStatus").val(userStatus);
        $("#userDetail").attr('action', 'userDetail.action');
        $("#userDetail").submit();
    }
}
//查询
function queryUsers(){
	$("#beforeCreateTimeV").val(_dateToStr($("#beforeCreateTime").val()));
	$("#afterCreateTimeV").val(_dateToStr($("#afterCreateTime").val()));
	$('#search_form').submit();
}
//操作后更新界面信息
function initData(){
	queryUsers();
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
function auditPass(){
	var flage = true;
	var jsonStr = "";
	var userStatus = "";
	$("[name='select']").each(function (){
		if ($(this).is(":checked")) {
			if (flage) {
				flage = flage == true ? false : flage;
				jsonStr = '[\'' + $(this).val() + '\'';
				userStatus = $(this).parents().parents().next().attr("value");
			}
			else {
				jsonStr += ',\'' + $(this).val() + '\'';
			}
		}
	});
	jsonStr += ']';
	var auditInfo = $("#auditInfo").val();
	if(auditInfo==''){
		auditInfo='审批通过';
	}
	$.dialog( {
		title : '审批通过',
		content : '请您确认是否审批通过？',
		ok : function() {
			var da_val = {'jsonStr':jsonStr,'auditInfo':auditInfo,'userStatus':userStatus,'auditStatus':'0'};
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
							$.compMsg({type:'success',msg:'审批成功！'}); 
							initData();
						}else if(data.resultFlage=='failure'){
							$.compMsg({type:'error',msg:'审批失败！'}); 
						}else if(data.resultFlage=='error'){
							$.compMsg({type:'error',msg:'数据库操作异常！'}); 
						}
					}else{
						$.compMsg({type:'error',msg:'数据库操作异常！'}); 
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
function auditNuPass(){
	var flage = true;
	var jsonStr = "";
	var userStatus = "";
	$("[name='select']").each(function (){
		if ($(this).is(":checked")) {
			if (flage) {
				flage = flage == true ? false : flage;
				jsonStr = '[\'' + $(this).val() + '\'';
				userStatus = $(this).parents().parents().next().attr("value");
			}
			else {
				jsonStr += ',\'' + $(this).val() + '\'';
			}
		}
	});
	jsonStr += ']';
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
				success : function(data) {//错误提示信息
					var mes = fileErrors(data.fieldErrors);
					if(mes!=''){
						$.compMsg({type:'error',msg:mes});
						return false;
					}
					if(data!=null){
						if(data.resultFlage=='success'){
							$.compMsg({type:'success',msg:'审批成功！'}); 
							initData();
						}else if(data.resultFlage=='failure'){
							$.compMsg({type:'error',msg:'审批失败！'}); 
						}else if(data.resultFlage=='error'){
							$.compMsg({type:'error',msg:'数据库操作异常！'}); 
						}
					}else{
						$.compMsg({type:'error',msg:'数据库操作异常！'}); 
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
