$(function() {
	//菜单显示当前，开发时删除
	$(".left-menu li:contains('系统配置')").addClass("selected").next().show();
	$(".left-menu dl a:contains('资源池分区管理')").parent().addClass("selected");
	$("#head-menu li a:contains('系统管理')").parent().addClass("head-btn-sel");
	//自定义form样式
	$("select,:text,textarea").uniform();
	$.uniform.update();
	//加载弹出层提示信息栏宽度
	$(".point").attr("style","width:80px");
	$(".point").html("&nbsp;");
	initResourceSelect();
});
//刷新界面
function backList(msg,errMsg){
	$.backListMsg( {
		msg : msg,
		errMsg : errMsg,
		url : 'resourcePart.action'
	});
}
//resourceQuery  资源池下拉列表
function initResourceSelect(){
	$.ajax({
        type: "POST",
        url: 'resourceQuery.action',
        data: null,
        dataType: "JSON",
        cache: false,
		async:false,
        success: function(data){
			if(data!=null){
				$("#resource_name option:gt(0)").remove();
				$(data).each(function (){
					var str = "<option value='"+this.resPoolId+"'>";
					str+=this.resPoolName;
					str+='</option>';
					$("#resource_name").append(str);
				});
			}
		}
	});
}
//修改
function onModify(obj,resPoolPartId){
	$.dialog({
		title: '修改资源池分区参数',
		content: document.getElementById('resource_div'),
		init:function (){
			$("#resource_name").val($(obj).parents("tr").find("td").eq(0).text());
			//$("#resource_name").val($(obj).parents("tr").find("td").eq(1).text());
			$("#resource_list").val($(obj).parents("tr").find("td").eq(2).text());
			$("#resource_remark").val($(obj).parents("tr").find("td").eq(3).text());
			$("#resource_code").val(resPoolPartId);
			$("#resource_old_code").val($(obj).parents("tr").find("td").eq(0).text());
			$("#resource_code").attr('disabled',true);
			$("#cpuNumTotal").val($(obj).parents("tr").find("td").eq(4).text());
			$("#ramSizeTotal").val($(obj).parents("tr").find("td").eq(5).text());
			$("#discSizeTotal").val($(obj).parents("tr").find("td").eq(6).text());
			//更新下拉列表显示状态
			$.uniform.update();
		},
		button:[
        {
            name: '修改',
            callback: function () {
				var flage = onAjaxModify(obj);
				return !flage;
			},
			focus: true
		}
		],
		cancelVal: '取消',
		cancel: true,
		lock: true
	});
}
//修改操作
function onAjaxModify(obj){
	var urls = "resPartModify.action";
	var da_val = getJsonStr('modify');
	var flage = true;
	$.ajax({
        type: "POST",
        url: urls,
        data: da_val,
        dataType: "JSON",
        cache: false,
		async:false,
        success: function(data){
			var mes = fieldErrors(data.fieldErrors);
			if(mes!=''){
				$.compMsg({type:'error',msg:mes});
			}
			if(data.resultMessage!=null){
				if(data.resultFlage=='success'){
					//$.compMsg({type:'success',msg:data.resultMessage});
//					$(obj).parents("tr").find("td").eq(0).text($("#resource_name").val());
//					$(obj).parents("tr").find("td").eq(1).text($("#resource_name :selected").text());
//					$(obj).parents("tr").find("td").eq(2).text($("#resource_list").val());
//					$(obj).parents("tr").find("td").eq(3).text($("#resource_remark").val());
					backList(data.resultMessage,'');
					flage = false;
				}else if(data.resultFlage=='failure'){
					$.compMsg({type:'error',msg:data.resultMessage}); 
				}else if(data.resultFlage=='error'){
					$.compMsg({type:'error',msg:data.resultMessage}); 
				}
			}
		}
	});
	return flage;
}
//创建
function onAdd(){
	$.dialog({
		title: '创建资源池分区参数',
		content: document.getElementById('resource_div'),
		init:function (){
			$("#resource_code").val("");
			$("#resource_old_code").val("");
			$("#resource_name").val("");
			$("#resource_code").val("");
			$("#resource_list").val("");
			$("#resource_remark").val("");
			$("#cpuNumTotal").val("");
			$("#ramSizeTotal").val("");
			$("#discSizeTotal").val("");
			$("#resource_code").attr('disabled',false);
			//更新下拉列表显示状态
			$.uniform.update();
		},
		button:[
        {
            name: '创建',
            callback: function () {
				this.button({
					name: '创建',
					disabled: true
            	});
				if(validation()){
					this.button({
						name: '创建',
						disabled: false
	            	});
					return false;
				}
                var flage = onAjaxSave();
				this.button({
					name: '创建',
					disabled: false
            	});
				return !flage;
            },
			focus: true
        }],
		cancelVal: '取消',
		cancel:true,
		lock: true
	});
}
//保存操作
function onAjaxSave(){
	var urls = "resPartSave.action";
	var da_val = getJsonStr('save');
	var flage = true;
	$.ajax({
        type: "POST",
        url: urls,
        data: da_val,
        dataType: "JSON",
        cache: false,
		async:false,
        success: function(data){
			var mes = fieldErrors(data.fieldErrors);
			if(mes!=''){
				$.compMsg({type:'error',msg:mes});
			}
			if(data.resultMessage!=null){
				if(data.resultFlage=='success'){
//					$.compMsg({type:'success',msg:data.resultMessage});
//					var html = '<tr>'+
//					'<td>'+$("#resource_name").val()+'</td>'+
//				    '<td>'+$("#resource_name :selected").text()+'</td>'+
//				    '<td>'+$("#resource_list").val()+'</td>'+
//					'<td>'+$("#resource_remark").val()+'</td>'+
//				    '<td class="table-opt-block">'+
//				    '    <a href="javascript:void(0)" onclick="onModify(this,\''+$("#resource_code").val()+'\');return false;">修改</a>'+
//				    '    <a href="javascript:void(0);" onclick="onDel(this,\''+$("#resource_name").val()+'\',\''+$("#resource_code").val()+'\');return false;">删除</a>'+
//				    '</td>'+
//				    '</tr>';
//				   $("#approval-resource").find("table").append(html);
					backList(data.resultMessage,'');
					flage = false;
				}else if(data.resultFlage=='failure'){
					$.compMsg({type:'error',msg:data.resultMessage}); 
				}else if(data.resultFlage=='error'){
					$.compMsg({type:'error',msg:data.resultMessage}); 
				}
			}
		}
	});
	return flage;
}
//拼接json字符串
function getJsonStr(opt){
	var resPoolId = $("#resource_name").val();
	var resPoolName =$("#resource_name :selected").text();
	var resPoolPartId =$("#resource_code").val();
	var resPoolPartName =$("#resource_list").val();
	var description =$("#resource_remark").val();
	var oldResPoolId = $("#resource_old_code").val();
	var cpuNumTotal = $("#cpuNumTotal").val();
	var ramSizeTotal = $("#ramSizeTotal").val();
	var discSizeTotal = $("#discSizeTotal").val();
	var jsonStr = "{'resPoolPartId':'"+resPoolPartId+"',";
	jsonStr += "'resPoolPartName':'"+resPoolPartName+"',";
	jsonStr += "'resPoolId':'"+resPoolId+"',";
	jsonStr += "'resPoolName':'"+resPoolName+"',";
	jsonStr += "'description':'"+description+"',";
	jsonStr += "'cpuNumTotal':'"+cpuNumTotal+"',";
	jsonStr += "'ramSizeTotal':'"+ramSizeTotal+"',";
	jsonStr += "'discSizeTotal':'"+discSizeTotal+"',";
	if(opt=='save'){
		jsonStr += "'oldResPoolId':'"+resPoolId+"'}";
	}else{
		if (oldResPoolId == resPoolId) {
			jsonStr += "'oldResPoolId':''}";
		} else {
			jsonStr += "'oldResPoolId':'" + oldResPoolId + "'}";
		}
	}
	var da_val = {'jsonStr':jsonStr,'resPoolPartId':resPoolPartId,'resPoolPartName':resPoolPartName,
				  'resPoolId':resPoolId,'resPoolName':resPoolName,'description':description,
				  'cpuNumTotal':cpuNumTotal,'ramSizeTotal':ramSizeTotal,'discSizeTotal':discSizeTotal};
	return da_val;
}

// 页面校验
function validation(){
	var resPoolId = $("#resource_name").val();
	var resPoolPartId =$("#resource_code").val();
	var resPoolPartName =$("#resource_list").val();
	var description =$("#resource_remark").val();
	var cpuNumTotal = $("#cpuNumTotal").val();
	var ramSizeTotal = $("#ramSizeTotal").val();
	var discSizeTotal = $("#discSizeTotal").val();
	
	var regex = "";
	var msg = "";
	
	if(resPoolId == ''){
		msg += "资源池不能为空！<br>";
	}
	
	regex = /^[a-zA-Z0-9-_]+$/;
	if(resPoolPartId == ''){
		msg += "资源池分区编码不能为空！<br>";
	} else if (!resPoolPartId.match(regex)){
		msg += "资源池分区编码只由数字及英文中划线组成！<br>";
	}
	
	if(resPoolPartName == ''){
		msg += "资源池分区名称不能为空！<br>";
	}

	regex = /^\d+$/;
	if(cpuNumTotal == ''){
		msg += "每虚机VCPU上限不能为空！<br>";
	} else if (!cpuNumTotal.match(regex)){
		msg += "每虚机VCPU上限应该是大于0的整数！<br>";
	}
	
	if(ramSizeTotal == ''){
		msg += "每虚机内存上限不能为空！<br>";
	} else if (!ramSizeTotal.match(regex)){
		msg += "每虚机内存上限应该是大于0的整数！<br>";
	}
	
	if(discSizeTotal == ''){
		msg += "每虚机磁盘上限不能为空！<br>";
	} else if (!discSizeTotal.match(regex)){
		msg += "每虚机磁盘上限应该是大于0的整数！<br>";
	}
	
	if(description != '' && description.length > 100){
		msg += "描述最大长度为100！<br>";
	}

	if (msg != '') {
		$.compMsg({type:'error',msg:msg});
		return true;
	} else {
		return false;
	}
}

//保存、修改检验错误信息
//返回错误提示内容
function fieldErrors(errors){
	var mes = '';
	if(!jQuery.isEmptyObject(errors)){
		if(!jQuery.isEmptyObject(errors.resPoolId)){
			mes+=errors.resPoolId+'<br>';
		}if(!jQuery.isEmptyObject(errors.resPoolPartId)){
			mes+=errors.resPoolPartId+'<br>';
		}if(!jQuery.isEmptyObject(errors.resPoolPartName)){
			mes+=errors.resPoolPartName+'<br>';
		}if(!jQuery.isEmptyObject(errors.description)){
			mes+=errors.description+'<br>';
		}if(!jQuery.isEmptyObject(errors.cpuNumTotal)){
			mes+=errors.cpuNumTotal+'<br>';
		}if(!jQuery.isEmptyObject(errors.ramSizeTotal)){
			mes+=errors.ramSizeTotal+'<br>';
		}if(!jQuery.isEmptyObject(errors.discSizeTotal)){
			mes+=errors.discSizeTotal+'<br>';
		}
	}

	return mes;
}
//删除验证错误信息提示
//返回错误提示内容
function fieldDelErrors(errors){
	var mes = '';
	if(!jQuery.isEmptyObject(errors)){
		if(!jQuery.isEmptyObject(errors.resPoolId)){
			mes+=errors.resPoolId+'\n';
		}if(!jQuery.isEmptyObject(errors.resPoolPartId)){
			mes+=errors.resPoolPartId+'\n';
		}
	}
	return mes;
}
//删除
function onDel(obj,resPoolId,resPoolPartId){
	$.dialog({
		title: '删除资源池分区',
		content: '请您确认是否删除资源池分区？删除后将无法恢复！',
		ok: function () {
			onAjaxDel(obj,resPoolId,resPoolPartId);
			return true;
		},
		cancelVal: '关闭',
		cancel: true,
		lock: true
	});
}
//ajax删除操作
function onAjaxDel(obj,resPoolId,resPoolPartId){
	if(resPoolId==''||resPoolPartId==''){
		$.compMsg({type:'error',msg:'请正确操作资源池分区！'});
		return false;
	}
	var da_val = {'resPoolId':resPoolId,'resPoolPartId':resPoolPartId}
	$.ajax({
        type: "POST",
        url: "resPartDel.action",
        data: da_val,
        dataType: "JSON",
        cache: false,
		async:false,
        success: function(data){
			var mes = fieldDelErrors(data.fieldErrors);
			if(mes!=''){
				$.compMsg({type:'error',msg:mes});
			}
			if(data.resultMessage!=null){
				if(data.resultFlage=='success'){
					//$.compMsg({type:'success',msg:data.resultMessage});
					//$(obj).parents("tr").remove();
					backList(data.resultMessage,'');
				}else if(data.resultFlage=='failure'){
					$.compMsg({type:'error',msg:data.resultMessage});
				}else if(data.resultFlage=='error'){
					$.compMsg({type:'error',msg:data.resultMessage}); 
				}
			}
		}
	});
}
