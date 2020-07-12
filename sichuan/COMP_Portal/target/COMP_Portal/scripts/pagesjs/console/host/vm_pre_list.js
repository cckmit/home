$(function() {
	$("#book").siblings().removeClass("active");
	$("#book").addClass("active");
	$("#vm_book").addClass("active");
	$("#book div.menu-item-child").removeClass("none");
	$("#book div.menu-item-parent i:nth-child(3)").removeClass("arrow").addClass("down");

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

function goToPage(caseId) {
	if (caseId == '') {

	} else {
		$("#caseId").val(caseId);
		$("#gotoForm").attr('action', 'vmPreApplyDetailAction.action');
		$("#gotoForm").submit();
	}

}


// 翻页调用js
function getPageData(url) {
	queryVm(url);
}

// 查询列表
function queryVm() {
	var vmName = $('#vmName').val();

	var da_val;
//	if (url == '' || url == undefined) {
		da_val = {
			vmName : vmName
		};
		url = 'vmPreApplyQueryJson.action';
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
					var list = data.vmResultInfoMap.list;
					var page = data.vmResultInfoMap.page;

					if (list.length == 0) {
						$("#resultList").empty();
						$("#listNull li").show();
						$(".pageBar").html(page);
						return;
					}
					if (!jQuery.isEmptyObject(list)) {
						var status = "";
						var statusDesc = "";
						var str="";
						for (var i = 0; i < list.length; i++) {
							var entity = list[i];
							status = entity.status;
							if (status == '0') {
								statusDesc = "待订购";
							} else if (status == '1') {
								statusDesc = "已订购";
							}
							 str += '<li status="' + entity.status + '" id="s' + entity.vmName + '" name="' + entity.vmName
									+ '"><a href="javascript:void(0);" onclick="goToPage(\'' + entity.caseId + '\');">'
									+ '<div id="' + entity.vmName + '" class="item-status blue ">'
									+ statusDesc
									+ '</div><div class="item-content"><h3>'
									+ entity.vmName
									+ '</h3><div class="detail"><div class="detail-col">'
									+ entity.isoName
									+ '</div><div class="detail-col"><div class="detail-row"><span>CPU：</span> <span>'
									+ entity.cpuNum + '核'
									+ '</span></div><div class="detail-row"><span>内存：</span> <span>'
									+ entity.ramSize + 'GB'
									+ '</span></div></div><div class="detail-col"><div class="detail-row"><span>存储空间：</span> <span id="discSize'
									+ entity.discSize
									+ '">'
									+ entity.discSize + 'GB'
									+ '</span></div><div class="detail-row"><span>订购数量：</span> <span id="num'
									+ entity.num
									+ '">'
									+ entity.num
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
