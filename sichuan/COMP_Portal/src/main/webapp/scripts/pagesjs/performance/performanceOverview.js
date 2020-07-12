var itemNumber = 3;

$(function() {
	// 菜单显示当前，开发时删除

	getBusinessResources();
});
/**
 * 以业务为单位，获取业务下的资源概况信息
 */
function getBusinessResources() {
	$('.business_data').each(function() {
		queryResourceByBusiness($(this).val()); // 从数据库刷新状态
	});
}

var queryResourceByBusiness = function(businessId) {
	var now = new Date();
	$
			.ajax({
				type : "POST",
				url : 'resourceListForBusinessOverview.action',
				dataType : "json",
				cache : false,
				data : {
					queryBusinessId : businessId,
					data : now.getMilliseconds()
				},
				success : function(data) {
					var resourceMap = data.resourceList;
					// 对五种设备list判断是否为null，如果为null，赋一个空的数组
					vmList = resourceMap.vm;
					if (!vmList) {
						vmList = [];
					}
					/*
					 * vmBakList = resourceMap.vmBak; if (!vmBakList) {
					 * vmBakList = []; }
					 */
					pmList = resourceMap.pm;
					if (!pmList) {
						pmList = [];
					}
					bsList = resourceMap.bs;
					if (!bsList) {
						bsList = [];
					}
					vlanList = resourceMap.vlan;
					if (!vlanList) {
						vlanList = [];
					}
					vlan3PhaseList = resourceMap.vlanSdn;
					if (!vlan3PhaseList) {
						vlan3PhaseList = [];
					}				
					ipSegmentList = resourceMap.ipSegment;
					if (!ipSegmentList) {
						ipSegmentList = [];
					}
					lbList = resourceMap.lb;
					if (!lbList) {
						lbList = [];
					}

					var appName = $("#count_" + businessId).siblings('h3')
							.html();
					// 数量部分填充
					var countHtml = "";
					countHtml += '<div class="ov-total"><span class="fl">共</span> <i class="ov-num">'
							+ vmList.length
							+ '</i><span class="fr">个云主机</span></div>';


					$("#count_" + businessId).append(countHtml);

					$("#detail_" + businessId).empty();

					var num;
					var length;
					var statusTemp = '';
					var statusText = "加载中";
					var statusClass = "blue";
					if (vmList.length > 0) {

						if (itemNumber > vmList.length) {
							num = vmList.length;
						} else {
							num = itemNumber;
						}

						str = "[";
						for (var i = 0; i < num; i++) {

							if (vmList[i].statusKey == 'PREPARE') {
								statusTemp = "0";
								statusText = "待创建";
								statusClass = "blue";
							} else if (vmList[i].statusKey == 'CREATING') {
								statusTemp = "1";
								statusText = "创建中";
								statusClass = "blue";
							} else if (vmList[i].statusKey == 'RUNNING') {
								statusTemp = "2";
								statusText = "运行中";
								statusClass = "green";
							} else if (vmList[i].statusKey == 'STOP') {
								statusTemp = "4";
								statusText = "停止";
								statusClass = "orange";
							} else if (vmList[i].statusKey == 'PAUSE') {
								statusTemp = "6";
								statusText = "已暂停";
								statusClass = "orange";
							} else if (vmList[i].statusKey == 'OPERATE_FAIL') {
								statusTemp = "9";
								statusText = "操作失败";
								statusClass = "red";
							} else if (vmList[i].statusKey == 'SENDERROR') {
								statusTemp = "14";
								statusText = "发送失败";
								statusClass = "red";
							} else if (vmList[i].statusKey == 'STATUSERROR') {
								statusTemp = "15";
								statusText = "状态异常";
								statusClass = "red";
							} else if (vmList[i].statusKey == 'PROCESSING') {
								statusTemp = "16";
								statusText = "处理中";
								statusClass = "blue";
							}
							str += "{id:'" + vmList[i].vmId + "',status:'"
									+ statusTemp + "'}";

							var liTag = '<li id="'
									+ vmList[i].vmId
									+ '" class="'
									+ statusClass
									+ ' shadow"><a href="javascript:goToDetailPage(\''
									+ vmList[i].vmId + '\',\''
									+ vmList[i].type + '\',\'' + businessId
									+ '\',\'' + appName
									+ '\',\''+ vmList[i].vmName + '\')"><div  class="status-block">'
									+ statusText
									+ '</div><div class="status-title">'
									+ vmList[i].name + '</div></a></li>';

							$("#detail_" + businessId).append(liTag);

							if (i < (num - 1)) {
								str += ",";
							}
						}
						str += "]";

						queryStatusFromResPool(str); // 从接口重新刷新vm最新状态
					}

					if (itemNumber <= vmList.length) {
						return;
					} else {
						num = itemNumber - vmList.length;
					}

				},
				error : function() {
					$("#hostContent").html("获取数据失败!");
				}
			});
};

function queryStatusFromResPool(str) {
	var da_val = {
		queryStr : str
	};
	$.ajax({
		type : "POST",
		url : 'vmSearchStateAction.action?param=1',
		data : da_val,
		dataType : "json",
		cache : false,
		success : function(data) {
			var ls = data.resultStatusInfos;
			if (ls == null || typeof (ls) == 'undefined') {
				// window.location.href='exceptionIntercepor.action';
				return;
			}
			// ls传过来的是页面上所有vmid的状态结果集而非变化的量
			for (var i = 0; i < ls.length; i++) {
				var statusCode = ls[i].status;
				var statusDesc = ls[i].statusText;
				var id = ls[i].id;
				$("#" + id).find('div.status-block').text(statusDesc);
				if (statusCode == '0') {
					$("#" + id).removeClass().addClass("blue").addClass(
							"shadow");
				} else if (statusCode == '1') {
					$("#" + id).removeClass().addClass("blue").addClass(
							"shadow");
				} else if (statusCode == '2') {
					$("#" + id).removeClass().addClass("green").addClass(
							"shadow");
				} else if (statusCode == '3') {
					$("#" + id).removeClass().addClass("blue").addClass(
							"shadow");
				} else if (statusCode == '4') {
					$("#" + id).removeClass().addClass("orange").addClass(
							"shadow");
				} else if (statusCode == '6') {
					$("#" + id).removeClass().addClass("orange").addClass(
							"shadow");
				} else if (statusCode == '9') {
					$("#" + id).removeClass().addClass("red")
							.addClass("shadow");
				} else if (statusCode == '14') {
					$("#" + id).removeClass().addClass("red")
							.addClass("shadow");
				} else if (statusCode == '15') {
					$("#" + id).removeClass().addClass("red")
							.addClass("shadow");
				} else if (statusCode == '16') {
					$("#" + id).removeClass().addClass("blue").addClass(
							"shadow");
				}
			}
		},
		error : function() {
		}
	});
}


function goToDetailPage(vmId, type, businessId, appName, vmName) {
	var url = "";
	var inputName = "";
	var nodeId = "";
	var treeNodeName = "";
	switch (type) {
	case "0":
		url = "performanceVmview.action";
		inputName = "vmId";
		nodeId = "-xnj";
		treeNodeName = vmName;
		break;
	default:
		break;
	}
	var form = $("<form/>");
	form.attr({
		action : url,
		method : "get"
	});
	form.append($('<input type="hidden" name="' + inputName + '" value="'
			+ vmId + '"/>'));
	form.append($('<input type="hidden" name="queryBusinessId" value="'
			+ businessId + '"/>'));
	form.append($('<input type="hidden" name="nodeId" value="xnst-'
			+ businessId + "-" + vmId + '"/>'));
	form.append($('<input type="hidden" name="treeNodeName" value="'
			+ treeNodeName + '"/>'));
	form.append($('<input type="hidden" name="pnodeId" value="xnst-'
			+ businessId + '"/>'));
	form.append($('<input type="hidden" name="pnodeName" value="' + appName
			+ '"/>'));
	form.append($('<input type="hidden" name="appId" value="' + businessId
			+ '"/>'));
	form.append($('<input type="hidden" name="curFun" value="xnst"/>'));
	form.append($('<input type="hidden" name="nodeType" value="app_vm"/>'));
	$('body').append(form);
	form.submit();
};
