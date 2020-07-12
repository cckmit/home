$(function() {
    // 菜单显示当前，开发时删除
    $(".left-menu li:contains('安全管理')").addClass("selected").next().show();
    $(".left-menu dl a:contains('用户管理')").parent().addClass("selected");
    $("#head-menu li a:contains('系统管理')").parent().addClass("head-btn-sel");
    // 自定义form样式
    $("select,.nl :checkbox,.iterator :checkbox,:text").uniform();
    $('#select').val("select");
    $.uniform.update();

    /*列表页面--用户列表全选
    $("#selectAll").click(function(event) {
    	checkAll(this);
    });*/
    //列表页面--查询按钮事件
    $('#search').click(function() {
    	$('#search_form').submit();
    });
    //列表页面--高级查询按钮事件
    /*$('#advSearch').click(function(event) {
		event.preventDefault();
		showAdvSearchForm();
    });*/
    /*列表页面--密码重置按钮事件
    $("#resetpass").click(function(event) {
    	resetPassword();
    });
    //列表页面--启用/解锁、禁用按钮事件
    $('.statusChange').click(function() {
    	statusChange($(this));
    });*/
    //列表页面--创建用户按钮事件
    $('#add_user_button').click(function(event) {
		event.preventDefault();
		userCreate();
    });

    //添加、修改页面--添加权限按钮事件
    $('#bind_role_add').click(function() {
    	$('#searchRoleName').attr("value","");//角色列表，查询条件框
 	   addRoleEvent($('#add_user_add_role_button'));
    });
    $('#bind_role_edit').click(function() {
    	$('#searchRoleName').attr("value","");//角色列表，查询条件框
 	   addRoleEvent($('#edit_user_add_role_button'));
    });
    // 添加、修改页面--绑定业务组按钮事件
    $('#bind_app_add').click(function() {
    	$('#searchAppName').attr("value","");//业务组列表，查询条件框
	    addAppEvent($('#add_user_add_app_button'));
    });
    $('#bind_app_edit').click(function() {
    	$('#searchAppName').attr("value","");//业务组列表，查询条件框
	    addAppEvent($('#edit_user_add_app_button'));
    });
    
    //添加、修改页面--选择角色页面--列表全选
    $("#selectAllRole").click(function(event) {
    	checkAllRole(this);
    });
    // 添加、修改页面--选择角色页面--查询按钮事件
    $("#searchRole").click(function(event) {
    	searchRoleData('queryRoleListForUserOp.action',false);
    });
    
    //添加、修改页面--选择业务组页面--列表全选
    $("#selectAllApp").click(function(event) {
    	checkAllApp(this);
    });
    // 添加、修改页面--选择业务组页面--查询按钮事件
    $("#searchApp").click(function(event) {
	    searchAppData('queryAppListAction.action',false);
    });
    
    //初始化绑定高级查询中的角色信息
    initSearchRoleInfo();
    
    // 初始化编辑页面权限删除按钮
    //initDelRoleButton();

    //列表长字段截取
    var $box = $('.cut');
	$box.each( function(index, jdom) {
		var objString = $(jdom).text();
		var byteValLen = 0;
		var returnText = "";
		var maxTextLength = 0;
		if($(jdom).attr("class") == 'cut role'){
			maxTextLength = 20*2;
		}else if($(jdom).attr("class") == 'cut app'){
			maxTextLength = 21*2;
		}
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

function initSearchRoleInfo(){
	var roleId_search = $('#roleIdSpan').text();
	if(roleId_search!=null && roleId_search!=''){
		$("#queryRoleId").append('<option value="">-请选择-</option>');
	}else{
		$("#queryRoleId").append('<option value="" selected="selected">-请选择-</option>');
	}
	$.ajax({
	    type : "GET",
	    url : "queryRoleListForUserOp.action",
	    dataType:"json",
	    success : function(data){
		    $.each(data.roleList,function(index){
				if(roleId_search!=null && roleId_search!='' && roleId_search == this.roleId){
					$("#queryRoleId").append('<option value="'+this.roleId+'" selected="selected">'+this.roleName+'</option>');
					$("#queryRoleId").parent().find('span').text(this.roleName);
			    }else{
			    	$("#queryRoleId").append('<option value="'+this.roleId+'">'+this.roleName+'</option>');
			    }
			});
	    }
	});
	$.uniform.update();
}

/**
 * 点击用户列表全选checkbox后触发的逻辑
 
function checkAll(obj) {
    if ($(obj).is(':checked')) {
	$("input[name='select']").each(function() {
	    $(this).attr('checked', true);
	});
    } else {
	$("input[name='select']").each(function() {
	    $(this).attr('checked', false);
	});
    }
    //$("select,:checkbox").uniform();
    $.uniform.update();
}*/

/**
 * 点击用户列表每个checkbox后判断是否全选

function ischeckCurrentAll(){
	var checkedAll = true;
	var obj = document.getElementsByName("select");   
    if(obj.length == 0){
    	checkedAll = false;
    }
    for (var i = 0; i < obj.length; i++){
        if (!obj[i].checked) {
        	checkedAll = false;
        	break;
		}
    }
	if(checkedAll){
		$('#selectAll').attr("checked",true);//业务组列表，当页"全选"true
	}else{
		$('#selectAll').attr("checked",false);//业务组列表，当页"全选"false
	}
	//$(":checkbox ").uniform();//:checkbox
    $.uniform.update();
} */

/**
 * 获取选中的用户列表checkbox
 
var getCheckVal = function() {
    var res = new Array();
    $("input[name='select']:checked").each(function() {
    	res.push($(this).val());
    });
    return res;
};*/

/**
 * 点击"高级查询"按钮
 
var showAdvSearchForm = function() {
	$.dialog({
	    title : '高级查询',
	    content : document
		    .getElementById('adv_search_dialog'),
	    init : function() { },
	    button : [ {
			name : '清空条件',
			callback : function() {
			    $('#adv_search_form input').val('');
			    $('#adv_search_form select option:first').attr("selected",true);
			    $.uniform.update();
			    return false;
			},
			focus : true
	    },
	    {
			name : '查询',
			callback : function() {
			    this.button({
				name: '查询',
				disabled: true
			    });
			    $('#adv_search_form').submit();
			},
			focus : true
	    }],
	    cancelVal : '取消',
	    cancel : true,
	    lock : true
	});
};*/

/**
 * 密码重置按钮事件
 */
var resetPassword = function(userIds){
    /*var userIds = getCheckVal();
    if (userIds.length < 1){
    	$.compMsg( {
  			type : 'warn',
  			msg : "请选择要密码重置的用户!"
  		});
		return;
    }*/
    
	$.dialog({
		title: '重置密码',
		content: '请您确认是否重置该用户密码?',
		ok: function () {
		    $.ajax({
			    type : "POST",
			    url : "userResetPassword.action",
			    traditional: true,
			    data:{
				    userIds : userIds,
				},
			    success : function(data){
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
			    },
			    dataType:"json"
			});
		},
		cancelVal: '关闭',
		cancel: true,
		lock: true
	});
};

/**
 * "启用/解锁/禁用"按钮事件
 */
var statusChange = function(userIds,id){
	//var msg_show="";
	var toStatus = "0";
	var toStatusStr = "";
	switch(id){
	case "unlock":
	case "enable_user":
	    toStatus = "0";
	    toStatusStr = "启用";
	    //msg_show = "启用/解锁";
	    break;
	case "disable_user":
	    toStatus = "1";
	    toStatusStr = "禁用";
	    //msg_show = "禁用";
	    break;
	}
	
	/*var userIds = getCheckVal();
    if (userIds.length < 1){
    	$.compMsg( {
  			type : 'warn',
  			msg : "请选择要"+msg_show+"的用户!"
  		});
	return;
    }*/
	
	$.dialog({
		title: '变更用户状态',
		content: '请您确认是否'+toStatusStr+'该用户?',
		ok: function () {
		    $.ajax({
			    type : "POST",
			    url : "userStatusChange.action",
			    traditional: true,
			    data:{
				    userIds : userIds,
				    toStatus : toStatus
				},
			    success : function(data){
				var mes = fieldErrors(data.fieldErrors);
				if(mes!=''){
					$.compMsg({type:'error',msg:mes});
					return;
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
			    },
			    dataType:"json"
			});
		},
		cancelVal: '关闭',
		cancel: true,
		lock: true
	});
};

/**
 * "用户详情"按钮事件
 */
function userDetail(userId) {
    var queryData = {
	userId : userId
    };
    $.ajax({
	type : "POST",
	url : 'userDetail.action',
	data : queryData,
	dataType : "JSON",
	cache : false,
	success : function(data) {
	    if (data.resultPath == "error") {
		$.compMsg({
		    type : 'error',
		    msg : "订单详细信息查询失败！"
		});
		return;
	    }

	    if (!jQuery.isEmptyObject(data)) {
		$('#userId').text(data.userId);
		
		setUserStatusAreaStyle($('#status'),data);
		
		if(data.status.code == '4' ){
			$('#auditDiv').hide();
		}else{
			$('#auditDiv').show();
		}
		
		$('#status').text(data.status.desc);
		$('#userName').text(data.userName);
		$('#telphone').text(data.telphone);
		$('#mobile').text(data.mobile);
		//$('#fax').val(data.fax);
		$('#email').text(data.email);
		$('#departmentName').text(data.departmentName);
		$('#createUserId').text(data.createUserId);
		$('#createTime').text(data.createTimeStr);
		//$('#userGroup').val(data.userGroup);
		$('#address').html(data.address == null ? '' : data.address);
		$('#desc').html(data.desc == null ? '' : data.desc);
		$("#app").val(data.apps);
		$("#auditInfo").html(data.auditInfo);
		
		var roleNames = new Array();
		$.each(data.roles, function() {
		    roleNames.push(this.roleName);
		});
		var appNames = new Array();
		var approvalApps = new Array();
		var approvalFlag = false;
		$.each(data.apps,function(){
			if(this.appBind_status==2){
				appNames.push(this.appName+"&nbsp;");
			}else if(this.appBind_status==1){
				//$("#approvalDiv").css("display","block");
				approvalFlag = true;
				approvalApps.push(this.appName);
			}
		});
		if(approvalFlag){
			$('#appStatus').removeClass();
			$('#appStatus').addClass("status_yellow");
			$('#showAppEdit').show();
			if(data.status.code == '4' ){
				$('#appStatus').text("注册待审");
				$('#showAppEdit').attr("title","业务组绑定已提交审核, 绑定后业务组为 : "+approvalApps.join(", "));
			}else{
				$('#appStatus').text("业务组变更待审");
				$('#showAppEdit').attr("title","业务组变更已提交审核, 变更后业务组将更新为 : "+approvalApps.join(", "));
			}
		}else{
			$('#appStatus').removeClass();
			$('#showAppEdit').hide();
			$('#appStatus').text("正常");
		}
		
		$('#role').html(roleNames.join(','));
		$("#app").html(appNames.join(","));
		//$("#approvalApp").html(approvalApps.join(","));
		$.dialog({
		    title : '用户详细信息',
		    content : document.getElementById('user_detail'),
		    init : function() {

		    },
		    cancelVal : '关闭',
		    cancel : true,
		    lock : true
		});
	    }
	}
    });
}

/**
 * "创建用户"按钮事件
 */
var userCreate = function() {
	$.dialog({
		    title : '创建用户',
		    content : document
			    .getElementById('user_create_dialog'),
		    init : function() {
			$('#user_create_dialog input:text').val('');
			$('#user_create_dialog textarea').val('');
			//$('#user_create_dialog :checkbox').prop("checked",true);
			$('#add_user_add_role_ul li:not(#add_user_add_role_button)').remove();
			//业务组
			$('#add_user_add_app_ul li:not(#add_user_add_app_button)').remove();
		    },
		    button : [ {
			name : '创建',
			callback : function() {
			    
			    this.button({
				name: '创建',
				disabled: true
			    });
			    if(validation_create()){
					this.button({
						name: '创建',
						disabled: false
					});
					return false;
			    }
			    
			    var flage = true;
			    $('input[name="roleIds"]').remove();

			    $('#add_user_add_role_ul li').each(function() {
					if (typeof $(this).data("roleid") === 'undefined') {
					    return true;
					}
					$('<input type="hidden" name="roleIds" />').appendTo($('#user_create_form')).val($(this).data("roleid"));
				});
			    
			    //初始绑定业务组按钮
				$('input[name="appIds"]').remove();
			    $('#add_user_add_app_ul li').each(function() {
					if (typeof $(this).data("appId") === 'undefined') {
					    return true;
					}
					$('<input type="hidden" name="appIds" />').appendTo($('#user_create_form')).val($(this).data("appId"));
				});
			    
			    $.post($('#user_create_form').attr("action"),$('#user_create_form').serialize(),
				    function(data){
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
            			}).error(function(){}).complete(function(){});
			    this.button({
				name: '创建',
				disabled: false
			    });
			return !flage;
			},
			focus : true
		    } ],
		    cancelVal : '取消',
		    cancel : true,
		    lock : true
		});
};

var validation_create = function(){
    var userId =$("#add_userId").val();
    var userName =$("#add_userName").val();
    var mobile =$("#add_mobile").val();
    var email =$("#add_email").val();
    var rexNum = /^[0-9]*[1-9][0-9]*$/;
    var rexMail =/^([a-zA-Z0-9\\._-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
    var rexName = /[0-9a-zA-Z_]/;
    var msg = "";
    if($.trim(userId)==''){
	msg+="用户账号不能为空！<br>";
    }else if(!rexName.test(userId)){
	msg+="用户账号只能由数字字母下划线组成！<br>";
    }
    if($.trim(userName)==''){
	msg+="用户名称不能为空！<br>";
    }
    if($.trim(mobile)==''){
	msg+="手机不能为空！<br>";
    }else if(!rexNum.test(mobile)){
	msg+="手机号码只能为数字！<br>";
    }
    if($.trim(email)==''){
	msg+="邮箱不能为空！<br>";
    }else if(!rexMail.test(email)){
	msg+="邮箱格式不正确！<br>";
    }
    if(!validateApp()){
    	msg+="绑定的业务组不能为空,请重新绑定";
    }
    
    if(msg != ''){
	$.compMsg({type:'error',msg:msg});
		return true;
	}else{
		if(validateUser("true",userId,mobile,email)){
			return false;
		}else{
			return true;
		}
	}
};

/**
 * 点击用户修改按钮后,触发的逻辑
 */
var approvalFlag = false;
var userEdit = function(userId) {
	approvalFlag = false;
    var queryData = {
    		userId : userId
    };
    $.ajax({
		type : "POST",
		url : 'userDetail.action',
		data : queryData,
		dataType : "JSON",
		cache : false,
		success : function(data) {
		    if (data.resultPath == "error") {
				$.compMsg({
				    type : 'error',
				    msg : "订单详细信息查询失败！"
				});
				return;
		    }

		    if (!jQuery.isEmptyObject(data)) {
			$('#edit_userId').text(data.userId);
			
			setUserStatusAreaStyle($('#edit_status'),data);
			
			$('#edit_status').text(data.status.desc);
			$('#edit_userName').val(data.userName);
			$('#edit_telphone').val(data.telphone);
			$('#edit_mobile').val(data.mobile);
			//$('#edit_fax').val(data.fax);
			$('#edit_email').val(data.email);
			$('#edit_departmentName').val(data.departmentName);
//			$('#edit_createUserId').val(data.createUserId);
//			$('#edit_createTime').val(data.createTime);
			//$('#edit_userGroup').val(data.userGroup);
			$('#edit_address').val(data.address == null ? '' : data.address);
			$('#edit_desc').val(data.desc == null ? '' : data.desc);
			$("#edit_user_add_role_button").parents('.edit_role').find('li:not(.add_role)').remove();
			
			$.each(data.roles, function() {
			    addRole(this.roleId, this.roleName, $("#edit_user_add_role_button"));
			});
			initDelRoleButton($("#edit_user_add_role_button"));
			
			var appNames = new Array();
			var approvalApps = new Array();
			$.each(data.apps,function(){
				if(this.appBind_status==2){
					appNames.push(this.appName+"&nbsp;");
				}else if(this.appBind_status==1){
					approvalFlag = true;
					approvalApps.push(this.appName);
				}
			});
			if(approvalFlag){
				$('.editUL').hide();
				$('.noeditUL').show();
				$('#appStatus_edit').removeClass();
				$('#appStatus_edit').addClass("status_yellow");
				$('#showAppEdit_edit').show();
				if(data.status.code == '4' ){
					$('#appStatus_edit').text("注册待审");
					$('#showAppEdit_edit').attr("title","业务组绑定已提交审核, 绑定后业务组为 : "+approvalApps.join(", "));
				}else{
					$('#appStatus_edit').text("业务组变更待审");
					$('#showAppEdit_edit').attr("title","业务组变更已提交审核, 变更后业务组将更新为 : "+approvalApps.join(", "));
				}
				$("#app_edit").html(appNames.join(","));
			}else{
				$('.editUL').show();
				$('.noeditUL').hide();
				$('#edit_user_add_app_ul li:not(#edit_user_add_app_button)').remove();
				//绑定业务组信息
				$.each(data.apps,function(){
					addAPP(this.appId,this.appName,$("#edit_user_add_app_button"));
				});
				initDelAppButton($("#edit_user_add_app_button"));
			}
			$.dialog({
				    title : '修改用户信息',
				    content : document
					    .getElementById('user_edit_dialog'),
				    init : function() {},
				    button : [ {
					name : '保存',
					callback : function() {
					    this.button({
							name: '保存',
							disabled: true
					    });
					    if(validation_edit()){
							this.button({
								name: '保存',
								disabled: false
							});
							return false;
					    }
					    var flage = true;
					    $('input[name="roleIds"]').remove();
					    $('input[name="user.userId"]').remove();
					    $('#edit_user_add_role_ul li').each(function() {
							if (typeof $(this).data("roleid") === 'undefined') {
							    return true;
							}
							$('<input type="hidden" name="roleIds" />').appendTo($('#user_edit_form')).val($(this).data("roleid"));
						});
					    $('<input type="hidden" name="user.userId" />').appendTo($('#user_edit_form')).val($('#edit_userId').text());
					    $.post($('#user_edit_form').attr("action"),$('#user_edit_form').serialize(),
						    function(data){
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
        						})
						    .error(function(){})
						    .complete(function(){});
					    this.button({
							name: '保存',
							disabled: false
					    });
					    return !flage;
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
};

var validation_edit = function(){
    var userName =$("#edit_userName").val();
    var mobile =$("#edit_mobile").val();
    var email =$("#edit_email").val();
    var rexNum = /^[0-9]*[1-9][0-9]*$/;
    var rexMail = /^([a-zA-Z0-9\\._-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
    var msg = "";
    if($.trim(userName)==''){
	msg+="用户名称不能为空！<br>";
    }
    if($.trim(mobile)==''){
	msg+="手机不能为空！<br>";
    }else if(!rexNum.test(mobile)){
	msg+="手机号码只能为数字！<br>";
    }
    if($.trim(email)==''){
	msg+="邮箱不能为空！<br>";
    }else if(!rexMail.test(email)){
	msg+="邮箱格式不正确！<br>";
    }
    if($.trim(userName)==''){
	msg+="用户名称不能为空！<br>";
    }
    if(!approvalFlag && !validateEditApp()){
    	msg+="绑定的业务组不能为空,请重新绑定"
    }
    if(msg != ''){
	$.compMsg({type:'error',msg:msg});
		return true;
	}else{
		if(validateUser("",$("#edit_userId").text(),mobile,email)){
			return false;
		}else{
			return true;
		}
	}
};

function validateUser(isCheckId,userId,mobile,email){//用户邮箱、手机号是否唯一
	var flag = true;
	$.ajax({
		type : "POST",
		async:false,
		url : 'registerValidateUserIdAction.action?struts.enableJSONValidation=true',
		data : { 
			userId : userId, 
			mobile : mobile,
			email : email,
			isCheckId : isCheckId
		},
		dataType : "JSON",
		cache : false,
		success : function(data) {
			if(data.errMsg != null && data.errMsg!= ""){
				//$('#errParam').text(data.errMsg);
				$.compMsg( {
					type : 'error',
					msg : data.errMsg
				});
				flag = false;
			}
			
		}
    });
	if(flag){
		return true;
	}else{
		return false;
	}
};

function validateApp() {//绑定业务组
	var appIdStr = document.getElementsByName('appIds').length;
	if(appIdStr == 0 ){
			$('#add_user_add_app_ul li').each(function() {
				if (typeof $(this).data("appId") === 'undefined') {
				    return true;
				}
				$('<input type="hidden" name="appIds" class="appIds" />').appendTo($('#user_create_form')).val($(this).data("appId"));
			});
		
		appIdStr = document.getElementsByName('appIds').length;
		if(appIdStr == 0){
			//$('#errParam').text("所属业务组不能为空，请重新绑定！");
			$.compMsg( {
				type : 'error',
				msg : "绑定的业务组不能为空，请重新绑定！"
			});
			return false;
		} else {
			return true;
		}
	}else{
		return true;
	}
}
function validateEditApp() {//绑定业务组
	$('input[name="appIds"]').remove();
	
	$('#edit_user_add_app_ul li').each(function() {
		if (typeof $(this).data("appId") === 'undefined') {
		    return true;
		}
		$('<input type="hidden" name="appIds" class="appIds" />').appendTo($('#user_edit_form')).val($(this).data("appId"));
	});
	
	var appIdStr = document.getElementsByName('appIds').length;
	if(appIdStr == 0){
		//$('#errParam').text("所属业务组不能为空，请重新绑定！");
		$.compMsg( {
			type : 'error',
			msg : "绑定的业务组不能为空，请重新绑定！"
		});
		return false;
	} else {
		return true;
	}
}

/**
 * 设置用户详情/用户编辑页面中,状态字段的背景颜色
 */
var setUserStatusAreaStyle = function(area,data){
    area.removeClass();
    switch (data.status.code){
	case '1':
	case '2':
	case '3':
	    area.addClass("status_red");
	    break;
	case '0':
	    area.addClass("status_blue");
	    break;
	case '4':
	    area.addClass("status_yellow");
	    break;
	default:
	    area.addClass("status_blue");
	}
};

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

//返回列表页面
function backList(msg, errMsg) {
    $.backListMsg({
	msg : msg,
	errMsg : errMsg,
	url : 'user.action'
    });
}

//角色
var currentPageCheckedRoleIdArry = new Array();
var currentPageCheckedRoleNameArry = new Array();
//业务组
var currentPageCheckedAppIdArry = new Array();
var currentPageCheckedAppNameArry = new Array();

/**
 * 添加用户角色按钮
 */
var addRoleEvent = function(addRoleButton){
    //$('#selectAllRole').prop("checked",false);
	emptyRole();
	initRoleAry(addRoleButton); 
	$.dialog({
	    title : '角色列表',
	    content : document.getElementById('role_list'),
	    init : function() {
	    	getPageData('queryRoleListForUserOp.action',false);
	    },
	    button : [ {
			name : '添加',
			callback : function() {
				$('.delRole').parent('li').remove();//删除文本框中的内容，重新添加
		    	for(var c in currentPageCheckedRoleIdArry){
		    		addRole(currentPageCheckedRoleIdArry[c], currentPageCheckedRoleNameArry[c], addRoleButton);
		    	}
		    	emptyRole();
		    	initRoleAry(addRoleButton);
		    	initDelRoleButton(addRoleButton);
			},
			focus : true
	    } ],
	    cancelVal : '关闭',
	    cancel : true,
	    lock : true
	});
};

/**
 * 添加角色按钮
 */
var addAppEvent =function (addAppButton){
	emptyApp();
	initAppAry(addAppButton); 
    $.dialog({
	    title : '业务组列表',
	    content : document.getElementById('app_list'),
	    init : function() {
	    	getPageData('queryAppListAction.action',false);
	    },
	    button : [ {
		    name : '确定',
		    callback : function() {
	    		$('.delApp').parent('li').remove();//删除文本框中的内容，重新添加
		    	for(var c in currentPageCheckedAppIdArry){
		    		addAPP(currentPageCheckedAppIdArry[c], currentPageCheckedAppNameArry[c], addAppButton);
		    	}
		    	emptyApp();
		    	initAppAry(addAppButton);
		    	initDelAppButton(addAppButton);
		    },
		    focus : true
	    } ],
	    cancelVal : '关闭',
	    cancel : true,
	    lock : true
	});
};

var getPageData = function(url){
	if(url.indexOf('queryRoleListForUserOp')>= 0){
		removeElementsRole();
		addElementsRole();
		searchRoleData(url,true);
	}
	if(url.indexOf('queryAppListAction')>= 0){
		removeElementsApp();
		addElementsApp();
	    searchAppData(url,true);
	}
};

//在缓存中除去本页去掉的值
function removeElementsRole(){
	$('[name="selectRole"]').not(":checked").each(function() {
		var tmp = this;
		var roleId = $(tmp).parents('tr').data('roleid');
		for(var cr in currentPageCheckedRoleIdArry){
    		if( currentPageCheckedRoleIdArry[cr] == roleId){
    			currentPageCheckedRoleIdArry.splice(cr,1);//删除数组中的业页面取消的元素，c为数组索引，1为删除数组中元素的个数
    			currentPageCheckedRoleNameArry.splice(cr,1);
    		}
    	}
    });
}
//获取本页选中的值放在缓存中
function addElementsRole(){
	$('[name="selectRole"]:checked').each(function() {
		var roleId = $(this).parents('tr').data('roleid');
		var roleName = $(this).parents('tr').data('rolename');
		var c = 0;
		for( c ; c< currentPageCheckedRoleIdArry.length;c++){
    		if( currentPageCheckedRoleIdArry[c] == roleId){
    			break;
    		}
    	}
		if(c == currentPageCheckedRoleIdArry.length){
			currentPageCheckedRoleIdArry.push(roleId);//添加到数组中的页面新选中的元素
			currentPageCheckedRoleNameArry.push(roleName);
		}
    });
}

//在缓存中除去本页去掉的值
function removeElementsApp(){
	$('[name="selectApp"]').not(":checked").each(function() {
		var tmp = this;
		var appId = $(tmp).parents('tr').data('appId');
		for(var cr in currentPageCheckedAppIdArry){
    		if( currentPageCheckedAppIdArry[cr] == appId){
    			currentPageCheckedAppIdArry.splice(cr,1);//删除数组中的业页面取消的元素，c为数组索引，1为删除数组中元素的个数
    			currentPageCheckedAppNameArry.splice(cr,1);
    		}
    	}
    });
}
//获取本页选中的值放在缓存中
function addElementsApp(){
	$('[name="selectApp"]:checked').each(function() {
		var appId = $(this).parents('tr').data('appId');
		var appName = $(this).parents('tr').data('appName');
		var c = 0;
		for( c ; c< currentPageCheckedAppIdArry.length;c++){
    		if( currentPageCheckedAppIdArry[c] == appId){
    			break;
    		}
    	}
		if(c == currentPageCheckedAppIdArry.length){
			currentPageCheckedAppIdArry.push(appId);//添加到数组中的页面新选中的元素
			currentPageCheckedAppNameArry.push(appName);
		}
    });
}

//添加’具体角色'按钮
var addRole = function(roleId, roleName, addRoleButton) {
    var exist = false;
    var roleUL = addRoleButton.parents('.edit_role');
    roleUL.find('li').each(function() {
	if (roleId == $(this).data("roleid")) {
	    exist = true;
	    return false; //等效于break
	}
    });

    if (exist) {
	return;
    }

    var isTooLong = false;
   	var roleName_show = roleName; 
	var byteValLen = 0;
    var returnText = "";
    var maxTextLength = 10*2;//9*2;
	for (var i = 0; i < roleName.length; i++) {
        (roleName[i].match(/[^x00-xff]/ig) != null) ? (byteValLen += 2) : (byteValLen += 1);
        returnText += roleName[i];
        if (byteValLen > maxTextLength) {
        	isTooLong = true;
        	roleName_show = returnText.substring(0,i-1) + '...';
            break;
        }
    }
   	if(isTooLong){
   		$('<li style="float: left; width:126px; margin: 2px 5px 3px 0;border-bottom: none;" title="'+roleName+'">' + roleName_show + '<span class="delRole" style="margin-top: -18px; margin-left: 118px; float:left; display:inline;height: 19px;width: 10px;font-size: xx-small;" >X</span></li>').insertBefore(
	    		addRoleButton).data("roleid", roleId).data("rolename", roleName);
   	}else{
	    $('<li style="float: left; width:126px; margin: 2px 5px 3px 0;border-bottom: none;">' + roleName_show + '<span class="delRole" style="margin-top: -18px; margin-left: 118px; float:left; display:inline;height: 19px;width: 10px;font-size: xx-small;" >X</span></li>').insertBefore(
	    		addRoleButton).data("roleid", roleId).data("rolename", roleName);
   	}
    //initDelRoleButton();
};

//添加’具体业务组'按钮
var addAPP = function(appId, appName, addAppButton) {
    var exist = false;
    var appUL = addAppButton.parents('.edit_app');
    appUL.find('li').each(function() {
	if (appId == $(this).data("appId")) {
	    exist = true;
	    return false; //等效于break
	}
    });

    if (exist) {
	return;
    }
    
    var isTooLong = false;
    var appName_show = appName; 
	var byteValLen = 0;
    var returnText = "";
    var maxTextLength = 10*2;//9*2;
	for (var i = 0; i < appName.length; i++) {
        (appName[i].match(/[^x00-xff]/ig) != null) ? (byteValLen += 2) : (byteValLen += 1);
        returnText += appName[i];
        if (byteValLen > maxTextLength) {
        	isTooLong = true;
        	appName_show = returnText.substring(0,i-1) + '...';
            break;
        }
    }
	
	if(isTooLong){
		$('<li style="float: left; width:126px; margin: 2px 5px 3px 0;border-bottom: none;" title="'+appName+'">' + appName_show + '<span class="delApp" style="margin-top: -18px; margin-left: 118px; float:left; display:inline;height: 19px;width: 10px;font-size: xx-small;" >X</span></li>').insertBefore(
		   	    addAppButton).data("appId", appId).data("appName", appName);
   	}else{
   		$('<li style="float: left; width:126px; margin: 2px 5px 3px 0;border-bottom: none;">' + appName_show + '<span class="delApp" style="margin-top: -18px; margin-left: 118px; float:left; display:inline;height: 19px;width: 10px;font-size: xx-small;" >X</span></li>').insertBefore(
   		   	    addAppButton).data("appId", appId).data("appName", appName);
   	}
    //initDelAppButton();
};

//删除’具体角色'按钮
var initDelRoleButton = function(addRoleButton) {
	var roleUL = addRoleButton.parents('.edit_role');
	roleUL.find('.delRole').click(function() {
	$(this).parent('li').remove();
    });
};

//删除’具体业务组'按钮
var initDelAppButton = function(addAppButton) {
	var appUL = addAppButton.parents('.edit_app');
    appUL.find('.delApp').click(function() {
	    $(this).parent('li').remove();
    });
};

function emptyRole(){
	currentPageCheckedRoleIdArry = [];
	currentPageCheckedRoleNameArry = [];
	var obj = document.getElementsByName("selectRole");  
	for (var i = 0; i < obj.length; i++){
        if (obj[i].checked) {
        	obj[i].checked = false;
		}
    }
}
function initRoleAry(addRoleButton){
	var roleUL = addRoleButton.parents('.edit_role');
	roleUL.find('.delRole').each(function() {
		var roleId = $(this).parents('li').data('roleid');
		var roleName = $(this).parents('li').data('rolename');
		currentPageCheckedRoleIdArry.push(roleId);//添加到数组中的页面新选中的元素
		currentPageCheckedRoleNameArry.push(roleName);
		
		$('[name="selectRole"]').not(":checked").each(function() {
			var roleId_box = $(this).parents('tr').data('roleid');
			if( roleId == roleId_box){
				$(this).attr('checked', true);
			}
		});
    });
}

function emptyApp(){
	currentPageCheckedAppIdArry = [];
	currentPageCheckedAppNameArry = [];
	var obj = document.getElementsByName("selectApp");  
	for (var i = 0; i < obj.length; i++){
        if (obj[i].checked) {
        	obj[i].checked = false;
		}
    }
}
function initAppAry(addAppButton){
	var appUL = addAppButton.parents('.edit_app');
    appUL.find('.delApp').each(function() {
		var appId = $(this).parents('li').data('appId');
		var appName = $(this).parents('li').data('appName');
		currentPageCheckedAppIdArry.push(appId);//添加到数组中的页面新选中的元素
		currentPageCheckedAppNameArry.push(appName);
		
		$('[name="selectApp"]').not(":checked").each(function() {
			var appId_box = $(this).parents('tr').data('appId');
			if( appId == appId_box){
				$(this).attr('checked', true);
			}
		});
    });
}

/**
 * 权限列表页面,点击搜索按钮后触发的事件.
 */
var searchRoleData = function(url,isChangePageEvent){
	var queryData = {};
	if (!isChangePageEvent){
	    var searchRoleName = $("#searchRoleName").val();
	    queryData = {"queryRole.roleName" : searchRoleName};
	}
	$.ajax({
	    type : "GET",
	    url : url,
	    data:queryData,
	    success : function(data){

		    $('#roleListTbody').empty();
		    $.each(data.roleList,function(index){
		    	var existRole = false;
		    	for(var c in currentPageCheckedRoleIdArry){
		    		if( currentPageCheckedRoleIdArry[c] == this.roleId){
		    			existRole = true;
			    	    break; //等效于break
		    		}
		    	}
		    	var roleCheckBox = "";
		        if (existRole) {
		        	roleCheckBox  = $('<td align="center"><input id="selectRole" type="checkbox" name="selectRole" value=""  checked="true" onclick="ischeckCurrentAllRole();isCheckGetRoleId('+index+');"/></td>');
		         }else{
		        	 roleCheckBox = $('<td align="center"><input id="selectRole" type="checkbox" name="selectRole" value=""  onclick="ischeckCurrentAllRole();isCheckGetRoleId('+index+');"/></td>');
		         }

			    $('<tr>').appendTo('#roleListTbody').data('roleid',this.roleId).data('rolename',this.roleName)
			    	.append(roleCheckBox)
			    	.append('<td>'+this.roleName+'</td>')
			    	//.append('<td>'+this.roleId+'</td>')
			        .append('<td>'+this.description+'</td>');
		    });
		    ischeckCurrentAllRole();//通过数组中的值判断"全选"是否需要选中
			$('#roleListPageBarDiv').html(data.pageBar);
			$("#roleListPageBarDiv select,#roleListTbody :checkbox").uniform();//:checkbox
			$.uniform.update();
	    },
	    error: function(data){
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
	    },
	    dataType:"json"
	}); 
};

//业务组列表，点击查询
var searchAppData = function(url,isChangePageEvent){
   	var queryData = {};
   	if (!isChangePageEvent){
        var searchAppName = $("#searchAppName").val();
   	    queryData = {  "app.app_name" : searchAppName };
   	}
	$.ajax({
	    type : "GET",
	    url : url,
	    data:queryData,
	    success : function(data){
		    $('#appListTbody').empty();
		    $.each(data.appList,function(index){
		    	var existApp = false;
		    	for(var c in currentPageCheckedAppIdArry){
		    		if( currentPageCheckedAppIdArry[c] == this.app_id){
		    			existApp = true;
			    	    break; //等效于break
		    		}
		    	}
		    	var appCheckBox = "";
		        if (existApp) {
		        	appCheckBox  = $('<td align="center"><input id="selectApp" type="checkbox" name="selectApp" value=""  checked="true" onclick="ischeckCurrentAllApp();isCheckGetAppId('+index+');"/></td>');
		         }else{
		        	appCheckBox = $('<td align="center"><input id="selectApp" type="checkbox" name="selectApp" value=""  onclick="ischeckCurrentAllApp();isCheckGetAppId('+index+');"/></td>');
		         }

			    $('<tr>').appendTo('#appListTbody').data('appId',this.app_id).data('appName',this.app_name)
			    	.append(appCheckBox)
			    	.append('<td>'+this.app_name+'</td>')
			        .append('<td>'+this.description+'</td>');
		    });
		    ischeckCurrentAllApp();//通过数组中的值判断"全选"是否需要选中
			$('#appListPageBarDiv').html(data.pageBar);
			$("#appListPageBarDiv select,#appListTbody :checkbox").uniform();//:checkbox
			$.uniform.update();
	    },
	    error: function(data){
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
	    },
	    dataType : "json"
     }); 
};

/**
 * 点击角色列表全选checkbox后触发的逻辑
 * @param obj
 */
function checkAllRole(obj) {
    if ($(obj).is(':checked')) {
	$("[name='selectRole']").each(function(index) {
	    $(this).attr('checked', true);
	    isCheckGetRoleId(index);
	});
    } else {
	$("[name='selectRole']").each(function(index) {
	    $(this).attr('checked', false);
	    isCheckGetRoleId(index);
	});
    }
    //$("select,:checkbox").uniform();
    $.uniform.update();
}

function isCheckGetRoleId(cbIndex){
	var obj = document.getElementsByName("selectRole");
	var roleIdTmp = $(obj[cbIndex]).parents('tr').data('roleid');
	var roleNameTmp = $(obj[cbIndex]).parents('tr').data('rolename');
	
    if (obj[cbIndex].checked) {
    	currentPageCheckedRoleIdArry.push(roleIdTmp);//添加到数组中的页面新选中的元素
		currentPageCheckedRoleNameArry.push(roleNameTmp);
	}else{
		removeElementRole(roleIdTmp);//删除数组中指定元素
	}
}	

//数组指定的元素删除
function removeElementRole(val){
    var index = appIdIndexOfRole(val);
    if (index > -1) {
    	currentPageCheckedRoleIdArry.splice(index, 1);
    	currentPageCheckedRoleNameArry.splice(index, 1);
    }
}
//数组指定的元素位置
function appIdIndexOfRole(val) {
    for (var i = 0; i < currentPageCheckedRoleIdArry.length; i++) {
        if (currentPageCheckedRoleIdArry[i] == val) return i;
    }
    return -1;
}
//点击当前页，每个复选框时判断是否全选 框check
function ischeckCurrentAllRole(){
	var checkedAllRole = true;
	var obj = document.getElementsByName("selectRole");   
    if(obj.length == 0){
    	checkedAllRole = false;
    }
    for (var i = 0; i < obj.length; i++){
        if (!obj[i].checked) {
        	checkedAllRole = false;
        	break;
		}
    }
	if(checkedAllRole){
		$('#selectAllRole').attr("checked",true);//业务组列表，当页"全选"true
	}else{
		$('#selectAllRole').attr("checked",false);//业务组列表，当页"全选"false
	}
	//$(":checkbox ").uniform();//:checkbox
    $.uniform.update();
}

// 点击业务组列表全选checkbox
function checkAllApp(obj) {
    if ($(obj).is(':checked')) {
	    $("[name='selectApp']").each(function(index) {
	        $(this).attr('checked', true);
	        isCheckGetAppId(index);
	    });
    } else {
	    $("[name='selectApp']").each(function(index) {
	        $(this).attr('checked', false);
	        isCheckGetAppId(index);
	    });
    }
    //$("select").uniform();//:checkbox
    $.uniform.update();
}

function isCheckGetAppId(cbIndex){
	var obj = document.getElementsByName("selectApp");
	var appIdTmp = $(obj[cbIndex]).parents('tr').data('appId');
	var appNameTmp = $(obj[cbIndex]).parents('tr').data('appName');
	
    if (obj[cbIndex].checked) {
    	currentPageCheckedAppIdArry.push(appIdTmp);//添加到数组中的页面新选中的元素
		currentPageCheckedAppNameArry.push(appNameTmp);
	}else{
		removeElementApp(appIdTmp);//删除数组中指定元素
	}
}

//数组指定的元素删除
function removeElementApp(val){
    var index = appIdIndexOf(val);//-----------
    if (index > -1) {
    	currentPageCheckedAppIdArry.splice(index, 1);
    	currentPageCheckedAppNameArry.splice(index, 1);
    }
}
//数组指定的元素位置
function appIdIndexOfApp(val) {
    for (var i = 0; i < currentPageCheckedAppIdArry.length; i++) {
        if (currentPageCheckedAppIdArry[i] == val) return i;
    }
    return -1;
}
//点击当前页，每个复选框时判断是否全选 框check
function ischeckCurrentAllApp(){
	var checkedAllApp = true;
	var obj = document.getElementsByName("selectApp");   
    if(obj.length == 0){
    	checkedAllApp = false;
    }
    for (var i = 0; i < obj.length; i++){
        if (!obj[i].checked) {
        	checkedAllApp = false;
        	break;
		}
    }
	if(checkedAllApp){
		$('#selectAllApp').attr("checked",true);//业务组列表，当页"全选"true
	}else{
		$('#selectAllApp').attr("checked",false);//业务组列表，当页"全选"false
	}
	//$(":checkbox ").uniform();//:checkbox
    $.uniform.update();
}

/*
//单个值放入数组中
function addElement(appIdTmp,appNameTmp){
	var c = 0;
	for( c ; c< currentPageCheckedAppIdArry.length;c++){
   		if( currentPageCheckedAppIdArry[c] == appIdTmp){
   			break;
   		}
   	}
	if(c == currentPageCheckedAppIdArry.length){
		currentPageCheckedAppIdArry.push(appIdTmp);//添加到数组中的页面新选中的元素
		currentPageCheckedAppNameArry.push(appNameTmp);
	}
}*/