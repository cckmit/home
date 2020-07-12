

$(function() {

    $(".left-menu li:contains('业务管理')").addClass("selected").next().show();
    $(".left-menu dl a:contains('业务列表')").parent().addClass("selected");
    $("#head-menu li a:contains('业务管理')").parent().addClass("head-btn-sel");

	$(":text,select").uniform();
    $('#select').val("select");
	
	$.uniform.update();

	// 添加、修改页面--绑定业务组按钮事件
    $('#bind_app_add').click(function() {
    	$('#searchUserName').attr("value","");//业务组列表，查询条件框
	    addUserEvent($('#add_user_add_app_contacts_button'));
    });
    $('#bind_app_edit').click(function() {
    	$('#searchUserName').attr("value","");//业务组列表，查询条件框
	    addUserEvent($('#edit_user_add_app_contacts_button'));
    });
    
    // 添加、修改页面--选择业务组页面--查询按钮事件
    $("#searchUser").click(function(event) {
	    searchUserData('queryUserList.action',false);
    });


});
/**
 * 查询
 */
$('#search').click(function() {
	var startTime = $('#startTime').val();
	var endTime = $('#endTime').val();
	if(startTime > endTime){
        var msg="创建开始时间应小于等于结束时间！<br>";
        $.compMsg({type:'error',msg:msg});
    }else{
        $('#appForm').submit();
    }
    });


/**
 *  业务组详情
 * 
 */
function appDetail(app_id) {

    $.ajax({
		type : "POST",
		url : 'appDetail.action',
		data : { app_id : app_id },
		dataType : "JSON",
		cache : false,
		success : function(data) {
		
		    if (data.resultPath == "error") {
			$.compMsg({
			    type : 'error',
			    msg : "业务组详细信息查询失败！"
			});
			return;
		    }
	
		    if (!jQuery.isEmptyObject(data)) {
			
			$('#detail_app_id').val(data.app_id);
			$('#detail_app_name').val(data.app_name);
			$('#detail_description').val(data.description);

			$('#detail_app_contacts').val(data.app_contacts);
			$('#detail_contact_phone').val(data.contact_phone);
			$('#detail_create_user').val(data.create_user);
			$('#detail_create_time').val(data.create_time);
			$('#detail_update_user').val(data.update_user);
			$('#detail_update_time').val(data.update_time);
			
	
			$.dialog({
			    title : '业务组详细信息',
			    content : document.getElementById('app_detail'),
			    init : function() {},
			    cancelVal : '关闭',
			    cancel : true,
			    lock : true
			});
		    }
		}
    });
}


/**
 * 创建业务
 */
$('#add_app_button').click(function(event) {
    event.preventDefault();
	queryResPooladd()
    appAdd();
});
function appAdd(){
	
	$.dialog({
	    title : '创建业务',
	    content : document.getElementById('app_add_dialog'),
	    init : function() {
		    $('#app_add_dialog input:text').val('');
		    $('#app_add_dialog textarea').val('');
		    
			//联系人
			$('#add_user_add_app_contacts_ul li:not(#add_user_add_app_contacts_button)').remove();
			
	    },
	    button : [ {
		    name : '创建',
		    callback : function() {
		    	
			    this.button({
			    	
				    name: '创建',
				    disabled: true
			    });
			    if(validation("add")){
				    this.button({
					    name: '创建',
					    disabled: false
				    });
				    return false;
			    }
			    var flage = true;
			    
				  //初始绑定联系人按钮
				$('input[name="userIds"]').remove();
			    $('#add_user_add_app_contacts_ul li').each(function() {
					if (typeof $(this).data("userId") === 'undefined') {
					    return true;
					}
					$('<input type="hidden" name="userIds" />').appendTo($('#app_add_form')).val($(this).data("userId"));
				});
			    $.post($('#app_add_form').attr("action"),$('#app_add_form').serialize(),
			        function(data){
        			    var mes = fieldErrors(data.fieldErrors);
        				if(mes!=''){
        					$.compMsg({type:'error',msg:mes});
        				}
        				if(data.resultMessage!=null){
        					if(data.resultFlage=='success'){
        						backList(data.resultMessage,'');
        						flage = false;
        					}else if(data.resultFlage=='failure' || data.resultFlage=='error'){
        						$.compMsg({type:'error',msg:data.resultMessage}); 
        					}else if(data.resultFlage=='warn'){
        						$.compMsg({type:'warn',msg:data.resultMessage}); 
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
	    }],
	    cancelVal : '取消',
	    cancel : true,
	    lock : true
	});
}

/**
 * 修改
 */

function appUpdate(app_id) {
    $.ajax({
		type : "POST",
		url : 'appDetail.action',
		data : { app_id : app_id },
		dataType : "JSON",
		cache : false,
		success : function(data) {
		
		    if (data.resultPath == "error") {
			    $.compMsg({
			        type : 'error',
			        msg : "业务组修改信息查询失败！"
			    });
			    return;
		    }

		    if (!jQuery.isEmptyObject(data)) {
				$('#edit_app_id').val(data.app_id);
				// $('#respool').val(data.respoolId);
				$('#edit_app_name').val(data.app_name);
				$('#edit_description').val(data.description);
				//$('#edit_app_contacts').val(data.app_contacts);
				
				var userNames = new Array();
				$.each(data.appContactList,function(){
					userNames.push(this.userName+"&nbsp;");
				});
				$('.editUL').show();
				$('.noeditUL').hide();
				$('#edit_user_add_app_contacts_ul li:not(#edit_user_add_app_contacts_button)').remove();
				//绑定用户信息
				$.each(data.appContactList,function(){
					addUser(this.userId,this.userName,$("#edit_user_add_app_contacts_button"));
				});
				initDelUserButton($("#edit_user_add_app_contacts_button"));

				$.dialog({
					    title : '修改业务组信息',
					    content : document.getElementById('app_update'),
					    init : function() { },
					    button : [ {
						    name : '保存',
						    callback : function() {
						        this.button({
							        name: '保存',
							        disabled: true
						        });
						        if(validation("edit")){
							        this.button({
								        name: '保存',
								        disabled: false
							        });
							        return false;
						        }
						       
						        var flage = true;
						        
						        $('input[name="userIds"]').remove();
							    $('input[name="app.userId"]').remove();
							    $('#edit_user_add_app_contacts_ul li').each(function() {
									if (typeof $(this).data("userId") === 'undefined') {
									    return true;
									}
									$('<input type="hidden" name="userIds" />').appendTo($('#app_update_form')).val($(this).data("userId"));
								});
						        
						        $.post($('#app_update_form').attr("action"),$('#app_update_form').serialize(),
							        function(data){
	        						    var mes = fieldErrors(data.fieldErrors);
	        							if(mes!=''){
	        								$.compMsg({type:'error',msg:mes});
	        							}
	        							if(data.resultMessage!=null){
	        								if(data.resultFlage=='success'){
	        									backList(data.resultMessage,'');
	        									flage = false;
	        								}else if(data.resultFlage=='failure' || data.resultFlage=='error'){
	        	        						$.compMsg({type:'error',msg:data.resultMessage}); 
	        	        					}else if(data.resultFlage=='warn'){
	        	        						$.compMsg({type:'warn',msg:data.resultMessage}); 
	        	        					}
	        							}
	        						}
						        ).error(function(){}).complete(function(){});
						        this.button({
							       name: '保存',
							       disabled: false
						        });
						        return !flage;
						    },
						focus : true
					    }],
					    cancelVal : '关闭',
					    cancel : true,
					    lock : true
					});
		    }//end if
			queryResPool(data.respoolId,data.respoolName)
		}//end success
	});//end ajax
};
/**
 * 验证
 */
function validation(type){

	var app_id = "";//业务组ID
	var app_name = "";//业务组名称
    var description = "";//描述
	if(type=="add"){
		app_id = $('#add_app_id').val();
		app_name = $('#add_app_name').val();
		description = $('#add_description').val();
	}else if(type=="edit"){
		app_id = $('#edit_app_id').val();
		app_name = $('#edit_app_name').val();
		description = $('#edit_description').val();
	}
    
	var app_idCheck = /^[0-9a-zA-Z_]+$/;
    var app_nameCheck = /^(?!_)(?!.*?_$)[a-zA-Z0-9 _\-\u4e00-\u9fa5]+$/;
    var msg = "";
    
    if($.trim(app_id) == ''){
	    msg+="业务组ID不能为空！<br>";
    }
    
    if(!app_idCheck.test(app_id)){
	    msg+="业务组ID只能由数字、字母、下划线组成！<br>";
    }

    if($.trim(app_name) == ''){
	    msg+="业务组名称不能为空！<br>";
    }
    if(!app_nameCheck.test(app_name)){
	    msg+="业务组名称由汉字、数字、字母、横线或者下划线组成！<br>且横线和下划线不能在名称的开始和结尾！<br>";
    }

    if($.trim(description).length > 256){
    	msg+="描述不能超过256个字符！<br>";
    }
   /* if(!validateUser()){
    	msg+="绑定的联系人不能为空,请重新绑定";
    }
*/    
    if(msg != ''){ //提示
	    $.compMsg({type:'error',msg:msg});
		return true;
	}else{
		return false;
	}
};

/**
 * 删除业务组
 */
var appDelete = function(app_id) {

    $.dialog({
	title : '删除业务组',
	content : '确认要删除该业务组吗？',
	ok : function() {
	    postDelReq(app_id);
	    return true;
	},
	cancelVal : '关闭',
	cancel : true,
	lock : true
    });
};

/**
 * 删除业务组发送请求到Action
 */
var postDelReq = function(app_id) {

    $.post("appDelete.action",{ app_id :app_id },
		function(data) {
	    	if (data.result.resultFlage == 'success') {
				
	        	backList(data.result.resultMessage, '');
			} else if (data.result.resultFlage == 'error') {
				$.compMsg({
				    type : 'error',
				    msg : data.result.resultMessage
				});
			}else if (data.result.resultFlage == 'warn') {
				$.compMsg({
				    type : 'warn',
				    msg : data.result.resultMessage
				});
			}
		}, "json").fail(
				          function() {
							$.compMsg({
							    type : 'error',
							    msg : '由于异常删除失败！'
						    });
				          }
        );
};

/**
 * 返回到业务组list
 */
var backList = function(msg, errMsg) {
    $.backListMsg({
	msg : msg,
	errMsg : errMsg,
	url : 'appListQuery.action'
    });
};
//检验错误信息
//返回错误提示内容
function fieldErrors(errors){
	var mes = '';
	if(!jQuery.isEmptyObject(errors)){
		if(!jQuery.isEmptyObject(errors.userName)){
			mes+=errors.userName+'\n';
		}
	}
	return mes;
}




//联系人---------------------------------
var currentPageCheckedUserIdArry = new Array();
var currentPageCheckedUserNameArry = new Array();



var getPageData = function(url){
	//console.info(url);
	if(url.indexOf('queryUserList')>= 0){
		removeElementsUser();
		addElementsUser();
	    searchUserData(url,true);
	}
};

function validateUser() {//绑定联系人
	var userIdStr = document.getElementsByName('userIds').length;
	if(userIdStr == 0 ){
			$('#add_user_add_app_contacts_ul li').each(function() {
				if (typeof $(this).data("userId") === 'undefined') {
				    return true;
				}
				$('<input type="hidden" name="userIds" class="userIds" />').appendTo($('#app_add_form')).val($(this).data("userId"));
			});
		
		userIdStr = document.getElementsByName('userIds').length;
		if(userIdStr == 0){
			//$('#errParam').text("所属业务组不能为空，请重新绑定！");
			$.compMsg( {
				type : 'error',
				msg : "绑定的联系人不能为空，请重新绑定！"
			});
			return false;
		} else {
			return true;
		}
	}else{
		return true;
	}
}
function validateEditUser() {//绑定联系人
	$('input[name="userIds"]').remove();
	
	$('#edit_user_add_app_contacts_ul li').each(function() {
		if (typeof $(this).data("userId") === 'undefined') {
		    return true;
		}
		$('<input type="hidden" name="userIds" class="userIds" />').appendTo($('#app_update_form')).val($(this).data("userId"));
	});
	
	var userIdStr = document.getElementsByName('userIds').length;
	if(userIdStr == 0){
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
 * 添加联系人按钮
 */
var addUserEvent =function (addUserButton){
	emptyUser();
	initUserAry(addUserButton); 
	
    $.dialog({
	    title : '联系人列表',
	    content : document.getElementById('app_Userlist'),
	    init : function() {
	    
	    	getPageData('queryUserList.action',false);
	    },
	    button : [ {
		    name : '确定',
		    callback : function() {
	    		$('.delUser').parent('li').remove();//删除文本框中的内容，重新添加
		    	for(var c in currentPageCheckedUserIdArry){
		    		addUser(currentPageCheckedUserIdArry[c], currentPageCheckedUserNameArry[c], addUserButton);
		    	}
		    	emptyUser();
		    	initUserAry(addUserButton);
		    	initDelUserButton(addUserButton);
		    },
		    focus : true
	    } ],
	    cancelVal : '关闭',
	    cancel : true,
	    lock : true
	});
};




//在缓存中除去本页去掉的值
function removeElementsUser(){
	$('[name="selectUser"]').not(":checked").each(function() {
		var tmp = this;
		var userId = $(tmp).parents('tr').data('userId');
		for(var cr in currentPageCheckedUserIdArry){
			
    		if( currentPageCheckedUserIdArry[cr] == userId){
    			currentPageCheckedUserIdArry.splice(cr,1);//删除数组中的业页面取消的元素，c为数组索引，1为删除数组中元素的个数
    			currentPageCheckedUserNameArry.splice(cr,1);
    		}
    	}
    });
}
//获取本页选中的值放在缓存中
function addElementsUser(){
		
	$('[name="selectUser"]:checked').each(function() {
		var userId = $(this).parents('tr').data('userId');
		var userName = $(this).parents('tr').data('userName');
		var c = 0;
		for( c ; c< currentPageCheckedUserIdArry.length;c++){
			
    		if( currentPageCheckedUserIdArry[c] == userId){
    			break;
    		}
    	}
		if(c == currentPageCheckedUserIdArry.length){
			currentPageCheckedUserIdArry.push(userId);//添加到数组中的页面新选中的元素
			currentPageCheckedUserNameArry.push(userName);
		}
    });
}

//添加’具体联系人'按钮
var addUser = function(userId, userName, addUserButton) {
    var exist = false;
    var userUL = addUserButton.parents('.edit_user');
    userUL.find('li').each(function() {
	if (userId === $(this).data("userId")) {
	    exist = true;
	    return false; //等效于break
	}
    });

    if (exist) {
	return;
    }
    var isTooLong = false;
    var userName_show = userName; 
	var byteValLen = 0;
    var returnText = "";
    var maxTextLength = 10*2;//9*2;
	for (var i = 0; i < userName.length; i++) {
        (userName[i].match(/[^x00-xff]/ig) != null) ? (byteValLen += 2) : (byteValLen += 1);
        returnText += userName[i];
        if (byteValLen > maxTextLength) {
        	isTooLong = true;
        	userName_show = returnText.substring(0,i-1) + '...';
            break;
        }
    }
	
	if(isTooLong){
		$('<li style="float: left; width:126px; margin: 2px 5px 3px 0;border-bottom: none;" title="'+userName+'">' + userName_show + '<span class="delUser" style="margin-top: -18px; margin-left: 118px; float:left; display:inline;height: 19px;width: 10px;font-size: xx-small;" >X</span></li>').insertBefore(
		   	    addUserButton).data("userId", userId).data("userName", userName);
   	}else{
   		$('<li style="float: left; width:126px; margin: 2px 5px 3px 0;border-bottom: none;">' + userName_show + '<span class="delUser" style="margin-top: -18px; margin-left: 118px; float:left; display:inline;height: 19px;width: 10px;font-size: xx-small;" >X</span></li>').insertBefore(
   		   	    addUserButton).data("userId", userId).data("userName", userName);
   	}
    //initDelAppButton();
};

//删除’具体联系人'按钮
var initDelUserButton = function(addUserButton) {
	var userUL = addUserButton.parents('.edit_user');
    userUL.find('.delUser').click(function() {
	    $(this).parent('li').remove();
    });
};

function emptyUser(){
	currentPageCheckedUserIdArry = [];
	currentPageCheckedUserNameArry = [];
	var obj = document.getElementsByName("selectUser");  
	for (var i = 0; i < obj.length; i++){
        if (obj[i].checked) {
        	obj[i].checked = false;
		}
    }
}
function initUserAry(addUserButton){
	var userUL = addUserButton.parents('.edit_user');
    userUL.find('.delUser').each(function() {
		var userId = $(this).parents('li').data('userId');
		var userName = $(this).parents('li').data('userName');
		currentPageCheckedUserIdArry.push(userId);//添加到数组中的页面新选中的元素
		currentPageCheckedUserNameArry.push(userName);
		
		$('[name="selectUser"]').not(":checked").each(function() {
			var userId_box = $(this).parents('tr').data('userId');
			if( userId == userId_box){
				$(this).attr('checked', true);
			}
		});
    });
}

function isCheckGetUserId(cbIndex){
	
	
	var obj = document.getElementsByName("selectUser");
	var userIdTmp = $(obj[cbIndex]).parents('tr').data('userId');
	var userNameTmp = $(obj[cbIndex]).parents('tr').data('userName');
	

    if (obj[cbIndex].checked) {
    	
    	currentPageCheckedUserIdArry = new Array();
    	currentPageCheckedUserNameArry = new Array();
    	
    	currentPageCheckedUserIdArry.push(userIdTmp);//添加到数组中的页面新选中的元素
		currentPageCheckedUserNameArry.push(userNameTmp);
	}else{
		removeElementUser(userIdTmp);//删除数组中指定元素
	}
}

//数组指定的元素删除
function removeElementUser(val){
    var index = userIdIndexOf(val);
    if (index > -1) {
    	currentPageCheckedUserIdArry.splice(index, 1);
    	currentPageCheckedUserNameArry.splice(index, 1);
    }
}
//数组指定的元素位置
function userIdIndexOfUser(val) {
    for (var i = 0; i < currentPageCheckedUserIdArry.length; i++) {
        if (currentPageCheckedUserIdArry[i] == val) return i;
    }
    return -1;
}
//点击当前页，每个复选框时判断是否全选 框check
function ischeckCurrentAllUser(){
	var checkedAllUser = true;
	var obj = document.getElementsByName("selectUser");   
    if(obj.length == 0){
    	checkedAllUser = false;
    }
    for (var i = 0; i < obj.length; i++){
        if (!obj[i].checked) {
        	checkedAllUser = false;
        	break;
		}
    }
	if(checkedAllUser){
		$('#selectAllUser').attr("checked",true);//联系人列表，当页"全选"true
	}else{
		$('#selectAllUser').attr("checked",false);//联系人列表，当页"全选"false
	}
	//$(":checkbox ").uniform();//:checkbox
    $.uniform.update();
}

//联系人列表，点击查询
var searchUserData = function(url,isChangePageEvent){
   	var queryData = {};
   	if (!isChangePageEvent){
        var searchUserName = $("#searchUserName").val();
   	    queryData = {  "user.userName" : searchUserName };
   	}
	$.ajax({
	    type : "GET",
	    url : url,
	    data:queryData,
	    success : function(data){
		    $('#userListTbody').empty();
		    $.each(data.userList,function(index){
		    	var existUser = false;
		    	for(var c in currentPageCheckedUserIdArry){
		    		if( currentPageCheckedUserIdArry[c] == this.userId){
		    			existUser = true;
			    	    break; //等效于break
		    		}
		    	}
	    	var userCheckBox = "";
	        if (existUser) {
	        	userCheckBox  = $('<td align="center"><input id="selectUser" type="radio" name="selectUser" value=""  checked="true" onclick="isCheckGetUserId('+index+');"/></td>');
	         }else{
	        	userCheckBox = $('<td align="center"><input id="selectUser" type="radio" name="selectUser" value=""  onclick="isCheckGetUserId('+index+');"/></td>');
	         }

			    $('<tr>').appendTo('#userListTbody').data('userId',this.userId).data('userName',this.userName)
		    	.append(userCheckBox)
			        .append('<td>'+this.userId+'</td>').append('<td>'+this.userName+'</td>');
		    });
		    ischeckCurrentAllUser();//通过数组中的值判断"全选"是否需要选中
			$('#userListPageBarDiv').html(data.pageBar);
			$("#userListPageBarDiv select,#userListTbody :checkbox").uniform();//:checkbox
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

function queryResPool(respoolId,respoolName) {
	$.ajax({
		url : 'queryResPool.action',
		type : 'POST',
		data : {},
		cache : false,
		async : false,
		dataType : 'json',
		success : function(data) {
			resPoolList=data.resPoolList;
			$("#respool").empty();
			// $("#respool").append(
			// 	'<option  hidden value="select">' + '-请选择-'
			// 	+ '</option>');
			for (var i = 0; i < resPoolList.length; i++) {
				if ("" != data.resPoolList[i].resPoolId&&respoolId!= data.resPoolList[i].resPoolId) {
					$("#respool").append(
						'<option value="' + resPoolList[i].resPoolId
						+ '">' + resPoolList[i].resPoolName
						+ '</option>');
				}else if("" != data.resPoolList[i].resPoolId&&respoolId== data.resPoolList[i].resPoolId){
					$("#respool").append(
						'<option selected="selected" value="' + resPoolList[i].resPoolId
						+ '">' + resPoolList[i].resPoolName
						+ '</option>');
				}
			}

		}
	});
	if(respoolName!=""&&respoolName!=null){
        $("#uniform-respool").children("span").eq(0).empty();
		$("#uniform-respool").children("span").eq(0).append(respoolName);
	}else{
        $("#uniform-respool").children("span").eq(0).empty();
		$("#uniform-respool").children("span").eq(0).append("--请选择--");
	}
}
function queryResPooladd() {
	$.ajax({
		url : 'queryResPool.action',
		type : 'POST',
		data : {},
		cache : false,
		async : false,
		dataType : 'json',
		success : function(data) {
			$("#respooladd").empty();
			$("#respooladd").append(
				'<option selected="selected" value="select">' + '-请选择-'
				+ '</option>');
			for (var i = 0; i < data.resPoolList.length; i++) {
				if ("" != data.resPoolList[i].resPoolId) {
					$("#respooladd").append(
						'<option value="' + data.resPoolList[i].resPoolId
						+ '">' + data.resPoolList[i].resPoolName
						+ '</option>');
				}
			}
		}
	});
}
