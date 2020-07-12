$(function(){
    //菜单显示当前，开发时删除
    $(".left-menu li:contains('服务条目审批')").addClass("selected").next().show();
    $(".left-menu dl a:contains('发布审批')").parent().addClass("selected");
    $("#head-menu li a:contains('待办任务')").parent().addClass("head-btn-sel");
    //自定义form样式
    $(":radio,:text,:checkbox,select").uniform();
    
    //加载弹出层提示信息栏宽度
    $(".point").attr("style", "width:80px");
    $(".point").html("&nbsp;");
	$("#search").click(function (){
		queryItems();
	});
	$("#selectAll").click(function (){
		checkAll(this);
	});
});
//全选
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
//批量审批
function approvalMore(obj){
	var itemIds = new Array();
    $("input[name='select']:checked").each(function() {
    	itemIds.push($(this).val());
    });
    if (itemIds.length < 1){
    	$.compMsg( {
  			type : 'warn',
  			msg : "请选择要审批的服务条目！"
  		});
    	return;
    }
    $.dialog({
        title: '批量审批',
        content: document.getElementById('approvalMore'),
        button: [{
            name: '通过',
            callback: function(){
				publishPass();
            },
            focus: true
        }, {
            name: '不通过',
            callback: function(){
				publishNuPass();
            }
        }],
        cancelVal: '取消',
        cancel: true,
        lock: true
    });
}

//条目详情
function onPublishDetail(itemId, itemType){
    if (itemId != '' && itemType != '') {
        $("#itemId").val(itemId);
        $("#itemType").val(itemType);
        $("#itemDetail").attr('action', 'publishItemDetail.action');
        $("#itemDetail").submit();
    }
}

var willTables = '<col style="width:5%;" />';
	willTables += '<col style="width:20%;" />';
    willTables += '<col style="width:20%;" />';
    willTables += '<col style="width:20%;" />';
    willTables += '<col style="width:15%;" />';
	willTables += '<tr>';
    willTables += '<th class="nl" style="width:5%;"><input type="checkbox" name="selectAll" onclick="checkAll(this);"/></th>';
    willTables += '<th style="width:20%;">名称</th>';
    willTables += '<th style="width:20%;">条目描述</th>';
    willTables += '<th style="width:20%;">申请时间</th>';
    willTables += '<th style="width:15%;">操作</th>';
	willTables += '</tr>';
//查询
function queryItems(){
    var itemName = $("#itemName").val();
    var status = '4';
    var beginCreateTime = _dateToStr($("#beginCreateTime").val());
    var endCreateTime = _dateToStr($("#endCreateTime").val());
    var da_val = {
            'itemName': itemName,
            'status': status,
            'beginCreateTime': beginCreateTime,
            'endCreateTime': endCreateTime
        };
    $.ajax({
        url: 'publishItemsJson.action',
        type: 'POST',
        data: da_val,
        cache: false,
        dataType: 'json',
        success: function(data){
            var willHtml = willTables;
			var flage = true;
            if (data != null) {
                for (var i = 0; i < data.list.length; i++) {
                    var entity = data.list[i];
                    var statusCode = '';
                    var statusDesc = '';
                    if (entity.status == 'PUBLISH') {
                        statusCode = '4';
                        statusDesc = '发布待审批'
                    }
					var itemType = '';
					var itemCode = '';
					if(entity.itemType=='VMTYPE'){
						itemType='虚拟机';
						itemCode = '0';
					}else if(entity.itemType=='PHTYPE'){
						itemType='物理机';
						itemCode = '1';
					}else if(entity.itemType=='BKTYPE'){
						itemType='小型机';
						itemCode = '2';
					}else if(entity.itemType=='MINTYPE'){
						itemType='小型机分区';
						itemCode = '3';
					}else if(entity.itemType=='BSTYPE'){
						itemType='虚拟机备份';
						itemCode = '4';
					}else if(entity.itemType=='OSTYPE'){
						itemType='云硬盘';
						itemCode = '5';
					}else if(entity.itemType=='BWTYPE'){
						itemType='云储存';
						itemCode = '6';
					}else if(entity.itemType=='IPTYPE'){
						itemType='公网IP';
						itemCode = '7';
					}else if(entity.itemType=='SGTYPE'){
						itemType='带宽';
						itemCode = '8';
					}else if(entity.itemType=='CMTYPE'){
						itemType='安全组';
						itemCode = '9';
					}else if(entity.itemType=='ISOTYPE'){
						itemType='系统镜像';
						itemCode = '10';
					}
                    if (statusCode == '4') {
                        willHtml += '<tr>';
                        willHtml += '<td><input type="checkbox" name="select" value='+entity.itemId+' ></td>';
                        willHtml += '<td>' + entity.itemName + '</td>';
                        willHtml += '<td>' + (entity.description==null?' &nbsp;':entity.description) + '</td>';
                        willHtml += '<td>' + _fomart14(entity.updateTime) + '</td>';
                        willHtml += '<td class="table-opt-block">';
	                    willHtml += '<a href="javascript:void(0);" onclick="onPublishDetail(\''+entity.itemId+ '\',\''+itemCode + '\');">审批</a>';
                        willHtml += '</td>';
                        willHtml += '</tr>';
						flage = flage==true?false:flage;
                    }
                }
            }
			if(flage){
				willHtml+='<tr><td colspan="5" align="center">无符合数据</td></tr>';
			}
            $("#approval-will table").empty();
            $("#approval-will table").append(willHtml);
			$(":radio,:text,:checkbox").uniform();
			$.uniform.update();
        }
    });
}
//操作后更新界面信息
function initData(){
    queryItems();
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
function publishPass(){
	var flage = true;
	var jsonStr = "";
	$("[name='select']").each(function (){
		if ($(this).is(":checked")) {
			if (flage) {
				flage = flage == true ? false : flage;
				jsonStr = '[\'' + $(this).val() + '\'';
			}
			else {
				jsonStr += ',\'' + $(this).val() + '\'';
			}
		}
	});
	/*if(jsonStr == ''){
		$.compMsg({type:'error',msg:'请选择要审批的服务条目！'}); 
		return ;
	}*/
	jsonStr += ']';
	var auditInfo = $("#auditInfo").val();
	if(auditInfo==''){
		auditInfo='审批通过';
	}
	$.dialog( {
		title : '发布审批通过',
		content : '请您确认是否发布审批通过？',
		ok : function() {
			var da_val = {'itemId':jsonStr,'auditInfo':auditInfo};
			$.ajax( {
				url : 'itemPublishPass.action',
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
						}else if(data.resultFlage=='failure'){
							$.compMsg({type:'error',msg:'审批失败！'}); 
						}else if(data.resultFlage=='error'){
							$.compMsg({type:'error',msg:'数据库操作异常！'}); 
						}
					}else{
						$.compMsg({type:'error',msg:'数据库操作异常！'}); 
					}
					initData();
				}
			});
			initData();
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}
//审批不通过
function publishNuPass(){
	var flage = true;
	var jsonStr = "";
	$("[name='select']").each(function (){
		if ($(this).is(":checked")) {
			if (flage) {
				flage = flage == true ? false : flage;
				jsonStr = '[\'' + $(this).val() + '\'';
			}
			else {
				jsonStr += ',\'' + $(this).val() + '\'';
			}
		}
	});
	/*if(jsonStr == ''){
		$.compMsg({type:'error',msg:'请选择要审批的服务条目！'}); 
		return ;
	}*/
	jsonStr += ']';
	var auditInfo = $("#auditInfo").val();
	if(auditInfo==''){
		auditInfo='发布审批不通过';
	}
	$.dialog( {
		title : '审批不通过',
		content : '请您确认是否发布审批不通过？',
		ok : function() {
			var da_val = {'itemId':jsonStr,'auditInfo':auditInfo};
			$.ajax( {
				url : 'itemPublishUnPass.action',
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
						}else if(data.resultFlage=='failure'){
							$.compMsg({type:'error',msg:'审批失败！'}); 
						}else if(data.resultFlage=='error'){
							$.compMsg({type:'error',msg:'数据库操作异常！'}); 
						}
					}else{
						$.compMsg({type:'error',msg:'数据库操作异常！'}); 
					}
					initData();
				}
			});
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}
