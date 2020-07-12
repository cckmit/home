$(function() {

	$("#book").siblings().removeClass("active");
	$("#book").addClass("active");
	$("#ebs_book").addClass("active");
	$("#book div.menu-item-child").removeClass("none");
	$("#book div.menu-item-parent i:nth-child(3)").removeClass("arrow").addClass("down");
	$(".status-tab a").unbind('click');
});
function goToPage(caseId) {
	$("#caseId").val(caseId);
	$("#gotoForm").attr('action', 'diskPreApplyDetail.action');
	$("#gotoForm").submit();
}

function getPageData(url) {
	location.href = url;
}

//查询列表
function queryEbs() {
	var diskName = $('#ebsName').val();

	var da_val;
//	if (url == '' || url == undefined) {
		da_val = {
				diskName : diskName
		};
		url = 'ebsPreApplyQueryJson.action';
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
					var list = data.ebsResultInfoMap.list;
					var page = data.ebsResultInfoMap.page;

					if (list.length == 0) {
						$("#resultList").empty();
						$("#listNull li").show();
						$(".pageBar").html(page);
						return;
					}
					if (!jQuery.isEmptyObject(list)) {
						var status = "";
						var statusDesc = "";
						var chargeType = "";
						var chargeTypeDesc = "";
						
						var str="";
						for (var i = 0; i < list.length; i++) {
							var entity = list[i];
							status = entity.status;
							chargeType = entity.chargeType;
							if (status == '0') {
								statusDesc = "待订购";
							} else if (status == '1') {
								statusDesc = "已订购";
							}
							if (chargeType == "h") {
								chargeTypeDesc = "按时计费";
							} else if (chargeType == "m") {
								chargeTypeDesc = "按月计费";
							}
							 
							 str += '<li status="' + entity.status + '" id="' + entity.ebsName + '" name="' + entity.ebsName
									+ '"><a href="javascript:void(0);" onclick="goToPage(\'' + entity.caseId +'\');return false;"><div class="item-status blue'
									+ '">'
									+ statusDesc
									+ '</div><div class="item-content"><h3>'
									+ (entity.ebsName.trim() ? entity.ebsName .trim() : '&nbsp;')
									+ '</h3><div class="detail"><div class="detail-col"><div class="detail-row"><span>云硬盘容量：</span> <span>'
									+ entity.diskSize
									+ 'GB</span></div><div class="detail-row"><span>所属企业客户</span> <span id="createTime">'
									+ entity.appName
									+ '</span></div></div><div class="detail-col"><div class="detail-row"><span>计费方式：</span> <span id="chargeType'
									+ entity.chargeType
									+ '">'
									+ chargeTypeDesc
									+ '</span></div><div class="detail-row"><span>创建时间：</span> <span id="createTime'
									+ entity.createTime + '">'
									+ entity.createTime
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

