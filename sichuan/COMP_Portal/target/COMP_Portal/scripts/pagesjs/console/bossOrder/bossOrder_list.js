$(function() {

	$("#bossOrder").siblings().removeClass("active");
	$("#bossOrder").addClass("active");

	$(".status-tab a").unbind('click');
});

// 业务列表选中的appId数组和appName数组
var currentPageCheckedAppIdArry = new Array();
var currentPageCheckedAppNameArry = new Array();

// 删除
var initDelAppButton = function() {
	$('.delApp').click(function() {
		$(this).parent('li').remove();
	});
};

function goToPage(id) {
	if (id == '') {

	} else {
		$("#id").val(id);
		$("#gotoForm").attr('action', 'bossOrderDetailAction.action');
		$("#gotoForm").submit();
	}

}


// 翻页调用js
function getPageData(url) {
	queryVm(url);
}

// 查询列表
function queryBossOrder() {
	var bossOrderId = $('#bossOrderId1').val();

	var da_val;
//	if (url == '' || url == undefined) {
		da_val = {
				bossOrderId : bossOrderId
		};
		url = 'bossOrderQueryJson.action';
//	}
	$
			.ajax({
				type : "POST",
				url : url,
				data : da_val,
				dataType : "JSON",
				cache : false,
				success : function(data) {
					if (data.resultPath == "error") {
						window.location.href = 'exceptionIntercepor.action';
					}
					var list = data.bossOrderResultInfoMap.list;
					var page = data.bossOrderResultInfoMap.page;

					if (list.length == 0) {
						$("#resultList").empty();
						$("#listNull li").show();
						$(".pageBar").html(page);
						return;
					}
					if (!jQuery.isEmptyObject(list)) {
						var status = "";
						var statusDesc = "";
						
						var operateType = "";
						var operateTypeDesc ="";
						
						var resourceType = "";
						var resourceTypeDesc = "";
						
						var chargeType = "";
						var chargeTypeDesc = "";
						
						var paymentType = "";
						var paymentTypeDesc = "";
						
						var str="";
						for (var i = 0; i < list.length; i++) {
							var entity = list[i];
							status = entity.status;
							if (status == '0') {
								statusDesc = "未操作";
							} else if (status == '1') {
								statusDesc = "已操作";
							}
							operateType = entity.operateType;
							if (operateType == '0') {
								operateTypeDesc = "订购";
							} else if (status == '1') {
								operateTypeDesc = "修改";
							} else if (status == '2') {
								operateTypeDesc = "退订";
							}
							resourceType = entity.resourceType;
							if (resourceType == '0') {
								resourceTypeDesc = "云主机";
							} else if (resourceType == '1') {
								resourceTypeDesc = "云存储";
							} else if (resourceType == '2') {
								resourceTypeDesc = "云网络";
							} else if (resourceType == '3') {
								resourceTypeDesc = "云安全";
							}
							chargeType = entity.chargeType;
							if (chargeType == '0') {
								chargeTypeDesc = "按月付费";
							} else if (chargeType == '1') {
								chargeTypeDesc = "按季度付费";
							} else if (chargeType == '2') {
								chargeTypeDesc = "按半年付费";
							} else if (chargeType == '3') {
								chargeTypeDesc = "按年付费";
							}
							paymentType = entity.paymentType;
							if (paymentType == '0') {
								paymentTypeDesc = "相同";
							} else if (status == '1') {
								paymentTypeDesc = "不同";
							}
							 str += '<li status="' + entity.status + '" id="s' + entity.bossOrderId + '" name="' + entity.bossOrderId
									+ '"><a href="javascript:void(0);" onclick="goToPage(\'' + entity.id + '\');">'
									+ '<div id="' + entity.id + '" class="item-status blue ">'
									+ statusDesc
									+ '</div><div class="item-content"><h3>'
									+ entity.bossOrderId
									+ '</h3><div class="detail"><div class="detail-col"><span>单价：</span> '
									+ entity.price + '元'
									+ '</div><div class="detail-col"><div class="detail-row"><span>所属企业客户：</span> <span>'
									+ entity.appName
									+ '</span></div><div class="detail-row"><span>协议签订时间：</span> <span>'
									+ entity.agreementBeginTime
									+ '</span></div></div><div class="detail-col"><div class="detail-row"><span>协议结束时间：</span> <span id="resourceType'
									+ entity.agreementEndTime
									+ '">'
									+ entity.agreementEndTime
									+ '</span></div><div class="detail-row"><span>资源类型：</span> <span id="price'
									+ entity.resourceType
									+ '">'
									+ resourceTypeDesc
									+ '</span></div></div></div></div></a></li>';

							$("#resultList").empty();
							$("#resultList").append(str);
							$("#listNull li").hide();
							$(".pageBar").html(page);
						}
					}
				}
			});
}
