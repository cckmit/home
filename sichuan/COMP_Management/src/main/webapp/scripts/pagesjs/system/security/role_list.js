$( function() {
	//菜单显示当前，开发时删除
	$(".left-menu li:contains('安全管理')").addClass("selected").next().show();
	$(".left-menu dl a:contains('角色管理')").parent().addClass("selected");
	$("#head-menu li a:contains('系统管理')").parent().addClass("head-btn-sel");
	//自定义form样式
	$("select,:text,textarea").uniform();
	$.uniform.update();
	//加载弹出层提示信息栏宽度
	$(".point").attr("style","width:80px");
	$(".point").html("&nbsp;");
});
/**
 * 搜索按钮事件
 */
$('#search').click(function() {
	$('#search_form').submit();
});

function onModify(obj,roleId,status){
	$.dialog({
		title: '修改角色',
		content: document.getElementById('role_div'),
		init:function (){
			$("#roleName").val($(obj).parents("tr").find("td").eq(0).text());
			$("#description").val($(obj).parents("tr").find("td").eq(1).text());
			$("#status").val(status);
			//更新下拉列表显示状态
			$.uniform.update();
		},
		button:[
        {
            name: '修改',
            callback: function () {
				var flage = true;
				var da_val = getEditJsonStr(roleId);
				var urls = "roleOperate.action";
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
function onDel(obj,roleId){
		$.dialog({
			title: '删除角色',
			content: '请您确认是否删角色？删除后将无法恢复！',
			ok: function () {
				var urls = "roleDelete.action";
				if(roleId==''){
					$.compMsg({type:'error',msg:'请正确角色！'});
					return true;
				}
				var da_val = {'roleId':roleId};
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
/**
 * 创建角色
 */
function onAdd(){
	$.dialog({
		title: '创建角色',
		content: document.getElementById('role_div'),
		init:function (){
			$("#roleName").val("");
			$("#status").val("select");
			$("#description").val("");
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
				var urls = "roleCreate.action";
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
function getJsonStr(){
	var roleId = $("#roleId").val();
	var roleName =$("#roleName").val();
	var status =$("#status").val();
	if(status=='0'){
		status = 'INVALID';
	} else if(status=='1'){
		status = 'EFFECTIVE';
	} else {
		status = 'EFFECTIVE';
	}
	var description =$("#description").val();
	var jsonStr = "{'roleId':'"+roleId+"',";
	jsonStr += "'roleName':'"+roleName+"',";
	jsonStr += "'status':'"+status+"',";
	jsonStr += "'description':'"+description+"'}";
	var da_val = {'jsonStr':jsonStr,'roleName':roleName,'roleId':roleId,
			  'status':status,'description':description};
	return da_val;
}
function getEditJsonStr(roleId){
	var roleName =$("#roleName").val();
	var status =$("#status").val();
	if(status=='0'){
		status = 'INVALID';
	} else if(status=='1'){
		status = 'EFFECTIVE';
	} else {
		status = 'EFFECTIVE';
	}
	var description =$("#description").val();
	var jsonStr = "{'roleId':'"+roleId+"',";
	jsonStr += "'roleName':'"+roleName+"',";
	jsonStr += "'status':'"+status+"',";
	jsonStr += "'description':'"+description+"'}";
	var da_val = {'jsonStr':jsonStr,'roleName':roleName,'roleId':roleId,
			  'status':status,'description':description};
	return da_val;
}
function validation(){
	var roleName =$("#roleName").val();
	var msg = "";
	if(roleName==''){
		msg+="角色名称不能为空！<br>";
	}
	if(msg != ''){
		$.compMsg({type:'error',msg:msg});
		return true;
	}else{
		return false;
	}
	
}
//检验错误信息
//返回错误提示内容
function fieldErrors(errors){
	var mes = '';
	if(!jQuery.isEmptyObject(errors)){
		if(!jQuery.isEmptyObject(errors.roleName)){
			mes+=errors.roleName+'\n';
		}
	}
	return mes;
}
//验证错误信息提示
//返回错误提示内容
function fieldOptErrors(errors){
	var mes = '';
	if(!jQuery.isEmptyObject(errors)){
		if(!jQuery.isEmptyObject(errors.roleId)){
			mes+=errors.roleId+'\n';
		}
	}
	return mes;
}
function backList(msg,errMsg){
	$.backListMsg( {
		msg : msg,
		errMsg : errMsg,
		url : 'roleSearch.action'
	});
}
function onRoleAuth(roleId) {
	var queryData = {
			roleId : roleId
	};
	
	$.ajax({
				type : "POST",
				url : 'roleSearchPermission.action',
				data : queryData,
				dataType : "JSON",
				cache: false,
				async:false,
				success : function(data) {
					
					if (data.resultPath == "error") {
						$.compMsg({
							type : 'error',
							msg : "角色权限信息查询失败！"
						});
						return;
					}
					if (!(!data && typeof(data)!="undefined" && data!=0)) {
						$.dialog({
									title : '角色权限信息',
									content : document.getElementById('ztree_div'),
									init : function() {
										var treedata = eval(data);
										var setting = {
												check: {
													enable: true,
													chkboxType:{ "Y":"ps","N":"ps"}
												},
												data: {
													simpleData: {
														enable: true
													}
												}
											};
										$.fn.zTree.init($("#authTree"), setting, treedata);
									},
									button : [ {
										name : '保存',
										callback : function() {
											var checknodes = $.fn.zTree.getZTreeObj("authTree").getCheckedNodes(true);
											var da_val={'checkedNodesStr':getCheckedNodesStr(checknodes),'roleId':roleId};
											$.ajax({
										        type: "POST",
										        url: "roleAuth.action",
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
										},
										focus : true
									} ],
									cancelVal : '关闭',
									cancel : true,
									lock : true
								});
					}
				}
			});
}
function getCheckedNodesStr(checknodes) {  
	var checknodesstr='';
	for(var i=0;i<checknodes.length;i++){
		checknodesstr=checknodesstr+checknodes[i].id+";";
	}
	return checknodesstr;
	
} 
