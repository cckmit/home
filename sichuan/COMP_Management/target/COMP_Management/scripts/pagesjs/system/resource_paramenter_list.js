$(function() {
	//菜单显示当前，开发时删除
	$(".left-menu li:contains('系统配置')").addClass("selected").next().show();
	$(".left-menu dl a:contains('资源池参数配置')").parent().addClass("selected");
	$("#head-menu li a:contains('系统管理')").parent().addClass("head-btn-sel");
	//自定义form样式
	$("select,:text,textarea,:password").uniform();
	$.uniform.update();
	//加载弹出层提示信息栏宽度
	$(".point").attr("style","width:80px");
	$(".point").html("&nbsp;");
	$("#approval-resource tr").each(function (){
		var $td = $(this).find("td").eq(3);
		if($td.text().length>urlSize){
			$td.text($td.text().substring(0,urlSize)+'...')
		}
		var $td = $(this).find("td").eq(5);
		if($td.text().length>remakeSize){
			$td.text($td.text().substring(0,remakeSize)+'...')
		}
	});
});
//刷新界面
function backList(msg,errMsg){
	$.backListMsg( {
		msg : msg,
		errMsg : errMsg,
		url : 'resource.action'
	});
}
//最大显示字符长度
var urlSize = 60;

//最大显示字符长度
var remakeSize = 10;
//修改
function onModify(obj){
	$.dialog({
		title: '修改资源池系统参数',
		content: document.getElementById('resource_div'),
		init:function (){
			$("#resource_code").val($(obj).parents("tr").find("td").eq(0).text());
			$("#resource_name").val($(obj).parents("tr").find("td").eq(1).text());
			$("#resource_remark").val($(obj).parents("tr").find("td").eq(7).attr("title"));
			$("#resource_url").val($(obj).parents("tr").find("td").eq(3).attr("title"));
			$("#resource_pwd").val($(obj).parents("tr").find("td").eq(5).text());
			$("#resource_code").attr('disabled',true);
			//更新下拉列表显示状态
			$.uniform.update();
		},
		button:[
        {
            name: '修改',
            callback: function () {
				var flage = true;
				var da_val = getJsonStr();
				var urls = "resourceUpdate.action";
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
//								$(obj).parents("tr").find("td").eq(0).text($("#resource_code").val());
//								$(obj).parents("tr").find("td").eq(1).text($("#resource_name").val());
//								$(obj).parents("tr").find("td").eq(2).text($("#resource_zone").val());
//								$(obj).parents("tr").find("td").eq(3).text($("#resource_remark").val());
//								var txt;
//								if($("#resource_url").val().length>urlSize){
//									txt = $("#resource_url").val().substring(0,urlSize)+'...';
//								}else{
//									txt = $("#resource_url").val();
//								}
//								$(obj).parents("tr").find("td").eq(4).text(txt);
//								$(obj).parents("tr").find("td").eq(4).attr("title",$("#resource_url").val());
//								$(obj).parents("tr").find("td").eq(5).text($("#resource_user").val());
//								$(obj).parents("tr").find("td").eq(6).text($("#resource_pwd").val());
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
				this.button({
					name: '修改',
					disabled: false
            	});
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
//保存
function onAdd(){
	$.dialog({
		title: '创建资源池系统参数',
		content: document.getElementById('resource_div'),
		init:function (){
			$("#resource_code").val("");
			$("#resource_code").attr('disabled',false);
			$("#resource_name").val("");
			$("#resource_remark").val("");
			$("#resource_url").val("");
			$("#resource_pwd").val("");
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
				var da_val = getJsonStr();
				var urls = "resourceCreate.action";
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
								backList(data.resultMessage,'');
								//addData();
								flage = false;
							}else if(data.resultFlage=='failure'){
								$.compMsg({type:'error',msg:data.resultMessage}); 
							}else if(data.resultFlage=='error'){
								$.compMsg({type:'error',msg:data.resultMessage}); 
							}
						}
					}
				});
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
function validation(){
	var resPoolId = $("#resource_code").val();
	var resPoolName =$("#resource_name").val();
	var resPoolUrl =$("#resource_url").val();
	var userPwd =$("#resource_pwd").val();
	var description =$("#resource_remark").val();
	var msg = "";
	var regex = /^[a-zA-Z0-9-_]+$/;
	if(resPoolId==''){
		msg+="资源池编码不能为空！<br>";
	}else if (!resPoolId.match(regex)){
		msg+="资源池编码由数字及英文中划线组成！<br>";
	}
	if(resPoolName==''){
		msg+="资源池名称不能为空！<br>";
	}
	if(resPoolUrl==''){
		msg+="资源池URL不能为空！<br>";
	}
	if(userPwd==''){
		msg+="密码不能为空！<br>";
	}
	if(description != '' && description.length > 100){
		msg += "描述最大长度为100！<br>";
	}
	
	if(msg != ''){
		$.compMsg({type:'error',msg:msg});
		return true;
	}else{
		return false;
	}
	
}
//拼接json字符串
function getJsonStr(){
	var resPoolId = $("#resource_code").val();
	var resPoolName =$("#resource_name").val();
	var resPoolZone =$("#resource_zone").val();
	var description =$("#resource_remark").val();
	var resPoolUrl =$("#resource_url").val();
	var userId =$("#resource_user").val();
	var userPwd =$("#resource_pwd").val();
	var status ='1';
	var jsonStr = "{'resPoolId':'"+resPoolId+"',";
	jsonStr += "'resPoolName':'"+resPoolName+"',";
	jsonStr += "'resPoolZone':'"+resPoolZone+"',";
	jsonStr += "'description':'"+description+"',";
	jsonStr += "'resPoolUrl':'"+resPoolUrl+"',";
	jsonStr += "'userCode':'"+userId+"',";
	jsonStr += "'userPwd':'"+userPwd+"'}";
	var da_val = {'jsonStr':jsonStr,'resPoolName':resPoolName,'resPoolId':resPoolId,
				  'resPoolZone':resPoolZone,'description':description,
				  'resPoolUrl':resPoolUrl,'userCode':userId,'userPwd':userPwd};
	return da_val;
}
//检验错误信息
//返回错误提示内容
function fieldErrors(errors){
	var mes = '';
	if(!jQuery.isEmptyObject(errors)){
		if(!jQuery.isEmptyObject(errors.resPoolId)){
			mes+=errors.resPoolId+'<br>';
		}if(!jQuery.isEmptyObject(errors.resPoolName)){
			mes+=errors.resPoolName+'<br>';
		}if(!jQuery.isEmptyObject(errors.resPoolUrl)){
			mes+=errors.resPoolUrl+'<br>';
		}if(!jQuery.isEmptyObject(errors.userPwd)){
			mes+=errors.userPwd+'<br>';
		}if(!jQuery.isEmptyObject(errors.description)){
			mes+=errors.description+'<br>';
		}
	}
	return mes;
}
//验证错误信息提示
//返回错误提示内容
function fieldOptErrors(errors){
	var mes = '';
	if(!jQuery.isEmptyObject(errors)){
		if(!jQuery.isEmptyObject(errors.resPoolId)){
			mes+=errors.resPoolId+'\n';
		}
	}
	return mes;
}

//新增成功后添加界面信息
function addData(){
	var urls =$("#resource_url").val();
	var txt;
	if(urls.length>urlSize){
		txt = urls.substring(0,urlSize)+'...';
	}else{
		txt = urls;
	}
	var resource_remarks = $("#resource_remark").val();
	var resource_remarkTxt;
	if(resource_remarks.length>remakeSize){
		resource_remarkTxt = resource_remarks.substring(0,remakeSize)+'...';
	}else{
		resource_remarkTxt = resource_remarks;
	}
	var html = '<tr>'+
	'<td>'+$("#resource_code").val()+'</td>'+
    '<td>'+$("#resource_name").val()+'</td>'+
	'<td style="display:none"></td>'+
	'<td title="'+resource_remarks+'">'+resource_remarkTxt+'</td>'+
	'<td title="'+urls+'">'+txt+'</td>'+
	'<td style="display:none"></td>'+
	'<td style="display:none">'+$("#resource_pwd").val()+'</td>'+
	'<td>正常</td>'+
    '<td class="table-opt-block">'+
    '    <a href="javascript:void(0)"  onclick="onModify(this);return false;">修改</a>'+
    '    <a href="javascript:void(0);" onclick="onPause(this,\''+$("#resource_code").val()+'\');return false;">暂停</a>'+
    '    <a href="javascript:void(0);" onclick="onStop(this,\''+$("#resource_code").val()+'\');return false;">终止</a>'+
    '    <a href="javascript:void(0);" onclick="onDel(this,\''+$("#resource_code").val()+'\');return false;">删除</a>'+
    '</td>'+
    '</tr>';
   $("#approval-resource").find("table").append(html);
}
//暂停\恢复
function onPause(obj,resPoolId){
	var state = $(obj).text();
	var newstate = state=="暂停"?"暂停服务":"正常";
	var newtext = state=="暂停"?"恢复":"暂停";
	$.dialog({
		title: state+'资源池',
		content: '请您确认是否'+state+'资源池？',
		ok: function () {
			var urls = "resourceRecover.action";
			if(state=='暂停'){
				urls = "resourcePause.action";
			}
			if(resPoolId==''){
				$.compMsg({type:'error',msg:'请正确操作资源池！'});
				return true;
			}
			var da_val = {'resPoolId':resPoolId}
			$.ajax({
		        type: "POST",
		        url: urls,
		        data: da_val,
		        dataType: "JSON",
		        cache: false,
				async:false,
		        success: function(data){
					var mes = fieldOptErrors(data.fieldErrors);
					if(mes!=''){
						$.compMsg({type:'error',msg:mes});
					}
					if(data.resultMessage!=null){
						if(data.resultFlage=='success'){
							backList(data.resultMessage,'');
							//$.compMsg({type:'success',msg:data.resultMessage});
							//$(obj).parents("tr").find("td").eq(7).text(newstate);
							//$(obj).text(newtext);
						}else if(data.resultFlage=='failure'){
							$.compMsg({type:'error',msg:data.resultMessage}); 
						}else if(data.resultFlage=='error'){
							$.compMsg({type:'error',msg:data.resultMessage}); 
						}
					}
				}
			});
			return true;
		},
		cancelVal: '关闭',
		cancel: true,
		lock: true
	});
}
//删除
function onDel(obj,resPoolId){
	$.dialog({
		title: '删除资源池',
		content: '请您确认是否删除资源池？删除后将无法恢复！',
		ok: function () {
			var urls = "resourceDelete.action";
			if(resPoolId==''){
				$.compMsg({type:'error',msg:'请正确操作资源池！'});
				return true;
			}
			var da_val = {'resPoolId':resPoolId}
			$.ajax({
		        type: "POST",
		        url: urls,
		        data: da_val,
		        dataType: "JSON",
		        cache: false,
				async:false,
		        success: function(data){
					var mes = fieldOptErrors(data.fieldErrors);
					if(mes!=''){
						$.compMsg({type:'error',msg:mes});
					}
					if(data.resultMessage!=null){
						if(data.resultFlage=='success'){
							backList(data.resultMessage,'');
							//$.compMsg({type:'success',msg:data.resultMessage});
							//$(obj).parents("tr").remove();
						}else if(data.resultFlage=='failure'){
							$.compMsg({type:'error',msg:data.resultMessage}); 
						}else if(data.resultFlage=='error'){
							$.compMsg({type:'error',msg:data.resultMessage}); 
						}
					}
				}
			});
			return true;
		},
		cancelVal: '关闭',
		cancel: true,
		lock: true
	});
}
//终止
function onStop(obj,resPoolId){
	$.dialog({
		title: '终止资源池',
		content: '请您确认是否终止资源池？终止后将无法恢复！',
		ok: function () {
			var urls = "resourceStop.action";
			if(resPoolId==''){
				$.compMsg({type:'error',msg:'请正确操作资源池！'});
				return true;
			}
			var da_val = {'resPoolId':resPoolId}
			$.ajax({
		        type: "POST",
		        url: urls,
		        data: da_val,
		        dataType: "JSON",
		        cache: false,
				async:false,
		        success: function(data){
					var mes = fieldOptErrors(data.fieldErrors);
					if(mes!=''){
						$.compMsg({type:'error',msg:mes});
						flage = true;
					}
					if(data.resultMessage!=null){
						if(data.resultFlage=='success'){
							backList(data.resultMessage,'');
							//$.compMsg({type:'success',msg:data.resultMessage});
							//$(obj).parents("tr").find("td").eq(7).text("终止服务");
							//$(obj).parents("td").find("a").filter(":contains('修改'),:contains('暂停'),:contains('终止'),:contains('恢复')").hide();
						}else if(data.resultFlage=='failure'){
							$.compMsg({type:'error',msg:data.resultMessage}); 
						}else if(data.resultFlage=='error'){
							$.compMsg({type:'error',msg:data.resultMessage}); 
						}
					}
				}
			});
			return true;
		},
		cancelVal: '关闭',
		cancel: true,
		lock: true
	});
}