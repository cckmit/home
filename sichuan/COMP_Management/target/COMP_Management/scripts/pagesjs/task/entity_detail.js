$(function() {
	//菜单显示当前，开发时删除
	$(".left-menu li:contains('服务条目审批')").addClass("selected").next().show();
	$(".left-menu dl a:contains('条目审批')").parent().addClass("selected");
	$("#head-menu li a:contains('待办任务')").parent().addClass("head-btn-sel");
	//自定义form样式
	$(":text,select,textarea,:checkbox,:radio").uniform();
	$("#readOnly :text,#readOnly select,#readOnly textarea,#readOnly :checkbox,#readOnly :radio").attr("disabled", true);
	$.uniform.update();
	
	//加载弹出层提示信息栏宽度
	$(".point").attr("style","width:80px");
	$(".point").html("&nbsp;");
	initStandard();
	$("#goBack").click(function (){
		backList('','');
	});
	$("#cancel").click(function (){
		backList('','');
	});
	$("#auditPass").click(function (){
		auditPass($("#itemId").val());
	});
	$("#auditUnPass").click(function (){
		auditNuPass($("#itemId").val());
	});
	fomartDate();//格式化日期
});
//格式化日期
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
}
//返回列表页面
function backList(msg,errMsg){
	$.backListMsg({msg:msg,errMsg:errMsg,url:'auditItems.action'});
}
//加载虚拟机规格
function vmStandard(){
	var itemType = $("#itemType").val();
	var standardId = $("#standardId").val();
	var da_val = {"standardId":standardId,'itemType':itemType};
    $.ajax({
        type: "POST",
        url: 'vmStandardQuery.action',
        data: da_val,
        dataType: "JSON",
        cache: false,
        success: function(data){
			if (data == null) {
				backList('','加载虚拟机规格数据库操作异常！');
				return false;
			}
			if(!jQuery.isEmptyObject(data)){
				 var flage = true;
				 if(data.length==1){
				 	var html = '<table width="100%" border="0" cellspacing="0" cellpadding="0">'+
        			'<col style="width:20%;" />'+
                    '<col style="width:20%;" />'+
                    '<col style="width:20%;" />'+
                    '<col style="width:30%;" />'+
                  	'<tr>'+
                  	'<th class="nl">规格名称</th>'+
                    '<th>CPU数量(个)</th>'+
                    '<th>内存容量(MB)</th>'+
                    '<th>磁盘空间(GB)</th>'+
                  	'</tr>'+
				 	'<tr>' +
				 	'<td>' +data[0].standardName +
					'</td>' +
					'<td>' +data[0].cpuNum +
					'</td>' +
					'<td>' +data[0].ramSize +
					'</td>' +
					'<td>' +data[0].discSize +
					'</td>' +
					'</tr>';
					$("#approval-relate-resource").html(html);
				 }else{
					backList('','加载虚拟机规格数据库操作异常！');
				 }
			}else{
				backList('','加载虚拟机规格数据库操作异常！');
			}
        }
    });
}
//加载物理机规格.
function pmStandard(){
	var itemType = $("#itemType").val();
	var standardId = $("#standardId").val();
	var da_val = {"standardId":standardId,'itemType':itemType};
    $.ajax({
        type: "POST",
        url: 'pmStandardDetail.action',
        data: da_val,
        dataType: "JSON",
        cache: false,
        success: function(data){
			if (data == null) {
				backList('','加载物理机规格数据库异常！');
				return false;
			}
			if(!jQuery.isEmptyObject(data)){
				$("#standardlist").empty();
				 var flage = true;
				 if(data.length==1){
				 	html = '<table width="100%" border="0" cellspacing="0" cellpadding="0">'+
				 	'<col style="width:26%;" />'+
				 	'<col style="width:26%;" />'+
                    '<col style="width:26%;" />'+
                    '<col style="width:11%;" />'+
                    '<col style="width:11%;" />'+
                  	'<tr>'+
                  	'<th class="nl">规格名称</th>'+
                  	'<th>物理机型号</th>'+
                    '<th>CPU类型</th>'+
                    '<th>内存容量(MB)</th>'+
                    '<th>磁盘空间(GB)</th>'+
                  	'</tr>'+
					'<tr>' +
						'<td>' +data[0].standardName +
						'</td>' +
						'<td>' +data[0].pmTypeName +
						'</td>' +
						'<td>' +data[0].cpuType +
						'</td>' +
						'<td>' +data[0].ramSize +
						'</td>' +
						'<td>' + data[0].discSize +
						'</td>' +
						'</tr></table>';
					$("#approval-relate-resource").html(html);
				 }else{
					backList('','加载物理机规格数据库异常！');
				 }
			}else{
				backList('','加载物理机规格数据库异常！');
			}
        }
    });
}
//加载硬盘规格
function ebsStandard(){
	var itemType = $("#itemType").val();
	var standardId = $("#standardId").val();
	var da_val = {"standardId":standardId,'itemType':itemType};
    $.ajax({
        type: "POST",
        url: 'ebsStandardQuery.action',
        data: da_val,
        dataType: "JSON",
        cache: false,
        success: function(data){
			if (data == null) {
				backList('','加载硬盘规格数据库操作异常！');
				return false;
			}
			if(!jQuery.isEmptyObject(data)){
				 var flage = true;
				 if(data.length==1){
					 var resourceType = '物理硬盘';
						if(data[0].resourceType == '1'){
							resourceType ='云硬盘';
						}
				 	var html = '<table width="100%" border="0" cellspacing="0" cellpadding="0">'+
        			'<col style="width:35%;" />'+
                    '<col style="width:35%;" />'+
                    '<col style="width:30%;" />'+
                  	'<tr>'+
                    '<th class="nl">规格名称</th>'+
                    '<th>硬盘容量(GB)</th>'+
                    '<th>硬盘类型</th>'+
                  	'</tr>'+
				 	'<tr>' +
					'<td>' +data[0].standardName +
					'</td>' +
					'<td>' + data[0].discSize +
					'</td>' +
					'<td>' + resourceType +
					'</td>' +
					'</tr>';
					$("#approval-relate-resource").html(html);
				 }else{
					backList('','加载硬盘规格数据库操作异常！');
				 }
			}else{
				backList('','加载硬盘规格数据库操作异常！');
			}
        }
    });
}
//加载虚拟机备份规格
function vmBakStandard(){
	var itemType = $("#itemType").val();
	var standardId = $("#standardId").val();
	var da_val = {"standardId":standardId,'itemType':itemType};
    $.ajax({
        type: "POST",
        url: 'vmBakStandardQuery.action',
        data: da_val,
        dataType: "JSON",
        cache: false,
        success: function(data){
			if (data == null) {
				backList('','加载虚拟机备份规格数据库操作异常！');
				return false;
			}
			if(!jQuery.isEmptyObject(data)){
				 var flage = true;
				 if(data.length==1){
				 	var html = '<table width="100%" border="0" cellspacing="0" cellpadding="0">'+
        			'<col style="width:50%;" />'+
                    '<col style="width:50%;" />'+
                  	'<tr>'+
                    '<th>规格名称</th>'+
                    '<th>空间大小(GB)</th>'+
                  	'</tr>'+
				 	'<tr>' +
					'<td>' +data[0].standardName +
					'</td>' +
					'<td>' + data[0].spaceSize +
					'</td>' +
					'</tr>';
					$("#approval-relate-resource").html(html);
				 }else{
					backList('','加载虚拟机备份规格数据库操作异常！');
				 }
			}else{
				backList('','加载虚拟机备份规格数据库操作异常！');
			}
        }
    });
}
//资源规格初始化.
function initStandard(){
	var itemType = $("#itemType").val();
	if(itemType=='0'){
		vmStandard();
	}else if(itemType=='1'){
		pmStandard();
	}else if(itemType=='4'){
		vmBakStandard();
	}else if(itemType=='5'){
		ebsStandard();
	}
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
function auditPass(itemId){
	var jsonStr = '[\''+itemId+'\']';
	var auditInfo = $("#auditInfo").val();
	if(auditInfo==''){
		auditInfo='审批通过'
	}
	$.dialog( {
		title : '审批通过',
		content : '请您确认是否审批通过？',
		ok : function() {
			var da_val = {'itemId':jsonStr,'auditInfo':auditInfo};
			$.ajax( {
				url : 'itemAuditPass.action',
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
function auditNuPass(itemId){
	var jsonStr = '[\''+itemId+'\']';
	var auditInfo = $("#auditInfo").val();
	if(auditInfo==''){
		auditInfo='审批不通过'
	}
	$.dialog( {
		title : '审批不通过',
		content : '请您确认是否审批不通过？',
		ok : function() {
			var da_val = {'itemId':jsonStr,'auditInfo':auditInfo};
			$.ajax( {
				url : 'itemAuditUnPass.action',
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
