/**
 * @author sMArT
 */
$(function() {
	//菜单显示当前，开发时删除
	$(".left-menu li:contains('概况')").addClass("selected");
	getAuditItemList();
	getpublisthItemList();
	getOrderAuditList();
	getUserAuditList();
});
/**
 * 条目审批初始化数据展示
 */
function getAuditItemList(){
	$.ajax({
		type: "POST",
		url: 'auditItemForOverview.action',
		dataType: "JSON",
		cache: false,
		success: function(data){
			$("#serLoad").hide();
			$("#entryCountent").html('<ul class="list"><li>共有<a href="auditItems.action"><strong>'+data.listCount+'</strong></a>个待审批条目</li></ul>' +
              '<ul class="list" id="entryList"/>');
			$("#entryList").empty();
			for (var i = 0; i < 3 && i<data.list.length; i++) {
				var entity = data.list[i];
				if(entity.itemType =="VMTYPE"){type = 0;}
				else if (entity.itemType =="PHTYPE") {var type = 1;}
				else if (entity.itemType =="BKTYPE") {var type = 2;}
				else if (entity.itemType =="MINTYPE") {var type = 3;}
				else if (entity.itemType =="BSTYPE") {var type = 4;}
				else if (entity.itemType =="OSTYPE") {var type = 5;}
				else if (entity.itemType =="BWTYPE") {var type = 6;}
				else if (entity.itemType =="IPTYPE") {var type = 7;}
				else if (entity.itemType =="SGTYPE") {var type = 8;}
				else if (entity.itemType =="CMTYPE") {var type = 9;}
				else if (entity.itemType =="ISOTYPE") {var type = 10;};
				$("#entryList").append('<li><a href="javascript:onAuditDetail(\'' + entity.itemId + '\',\'' + type + '\');">'+entity.itemName+'</a></li>');
				if(i == entity.length-3){
			   		break;
			   }
			}
		},
		error: function(){
			$("#entryCountent").html("获取待审批数据失败!");
		}
	});
}
/**
 * 条目发布审批初始化数据展示
 */
function getpublisthItemList(){
	$.ajax({
		type: "POST",
		url: 'publisthItemForOverview.action',
		dataType: "JSON",
		cache: false,
		success: function(data){
			$("#serLoad").hide();
			$("#publishCountent").html('<ul class="list"><li>共有<a href="publishItems.action"><strong>'+data.listCount+'</strong></a>个条目发布待审批</li></ul>' +
              '<ul class="list" id="publishList"/>');
			$("#publishList").empty();
			for (var i = 0; i < 3 && i<data.list.length; i++) {
				var entity = data.list[i];
				if(entity.itemType =="VMTYPE"){type = 0;}
				else if (entity.itemType =="PHTYPE") {var type = 1;}
				else if (entity.itemType =="BKTYPE") {var type = 2;}
				else if (entity.itemType =="MINTYPE") {var type = 3;}
				else if (entity.itemType =="BSTYPE") {var type = 4;}
				else if (entity.itemType =="OSTYPE") {var type = 5;}
				else if (entity.itemType =="BWTYPE") {var type = 6;}
				else if (entity.itemType =="IPTYPE") {var type = 7;}
				else if (entity.itemType =="SGTYPE") {var type = 8;}
				else if (entity.itemType =="CMTYPE") {var type = 9;}
				else if (entity.itemType =="ISOTYPE") {var type = 10;};
				$("#publishList").append('<li><a href="javascript:onPublishDetail(\'' + entity.itemId + '\',\'' + type + '\');">'+entity.itemName+'</a></li>');
				if(i == entity.length-3){
			   		break;
			   }
			}
		},
		error: function(){
			$("#publishCountent").html("获取发布待审批数据失败!");
		}
	});
}
/**
 * 条目审批详情.
 */
function onAuditDetail(itemId,itemType){
    if (itemId != '' && itemType != '') {
		var form = $("<form/>");
		form.attr({
			action: "auditItemDetail.action",
			method: "post"
		});
		form.append('<input type="hidden" name="itemId" value="' + itemId + '"/>');
		form.append('<input type="hidden" name="itemType" value="' + itemType + '"/>');
		$('body').append(form);
		form.submit();
	}
} 

/**
 * 条目发布审批详情
 */
function onPublishDetail(itemId, itemType){
    if (itemId != '' && itemType != '') {
		var form = $("<form/>");
		form.attr({
			action: "publishItemDetail.action",
			method: "post"
		});
		form.append('<input type="hidden" name="itemId" value="' + itemId + '"/>');
		form.append('<input type="hidden" name="itemType" value="' + itemType + ' "/>');
		$('body').append(form);
		form.submit();
    }
}
/**
 * 订单初始化展示数据
 */
function getOrderAuditList(){
		var da_val = {
			caseType : "",
			status : "0"
		};
		$.ajax({
			type: "POST",
			url: '../userCenter/orderQueryJson.action',
			data : da_val,
			dataType: "JSON",
			cache: false,
			success: function(data){
				$("#serLoad2").hide();
				$("#orderEntryCountent").html('<ul class="list"><li>共有<a href="ordersAuditList.action"><strong>'+data.orderInfos.listCount+'</strong></a>个待审批订单</li></ul>' +
	              '<ul class="list" id="orderEntryList"/>');
				$("#orderEntryList").empty();
				for ( var i = 0; i < 7&&data.orderInfos.list.length>0&&i<data.orderInfos.list.length; i++) {
					$("#orderEntryList").append('<li><a href="javascript:onAuditOrderDetail(\'' + data.orderInfos.list[i].orderId + '\',\'' + data.orderInfos.list[i].caseType + '\',\'' + data.orderInfos.list[i].status + '\');">'+data.orderInfos.list[i].orderId+'</a></li>');
				}
			},
			error: function(){
				$("#orderEntryCountent").html("获取待审批数据失败!");
			}
		});
}

/**
 * 用户审批初始化展示数据
 */
function getUserAuditList(){
	var da_val = {
	};
	$.ajax({
		type: "POST",
		url: 'userApprovaForOverview.action',
		data : da_val,
		dataType: "JSON",
		cache: false,
		success: function(data){
			$("#serLoad3").hide();
			$("#userEntryCountent").html('<ul class="list"><li>共有<a href="userApprovalList.action"><strong>'+data.listCount+'</strong></a>个待审批用户</li></ul>' +
              '<ul class="list" id="userEntryList"/>');
			$("#userEntryList").empty();
			for ( var i = 0; i < 7 && i<data.list.length; i++) {
				var status =data.list[i].status;
				if(status=="ENABLE"){
					status='0';
				}else if(status=="EXAMINATION"){
					status='4';
				}
				$("#userEntryList").append('<li><a href="javascript:onAuditUserDetail(\'' + data.list[i].userId + '\',\''+status+'\');">'+data.list[i].userId+'</a></li>');
			}
		},
		error: function(){
			$("#userEntryCountent").html("获取待审批数据失败!");
		}
	});
}
/**
 * 用户审批详情
 */
function onAuditUserDetail(userId,status){
    if (userId != '') {
		var form = $("<form/>");
		form.attr({
			action: "userDetail.action",
			method: "post"
		});
		form.append('<input type="hidden" name="userId" value="' + userId + '"/>');
		form.append('<input type="hidden" name="userStatus" value="'+status+'"/>');
		$('body').append(form);
		form.submit();
    }
}
/**
 * 订单审批详情
 */
function onAuditOrderDetail(orderId,type,status){
    if (orderId != '' && type != '') {
		var form = $("<form/>");
		form.attr({
			action: "orderDetail.action",
			method: "post"
		});
		form.append('<input type="hidden" name="orderId" value="' + orderId + '"/>'+'<input type="hidden" name="caseType" value="' + type + '"/>');
		$('body').append(form);
		form.submit();
    }
}
