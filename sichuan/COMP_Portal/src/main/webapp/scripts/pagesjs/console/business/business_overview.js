var itemNumber = 5;

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
					countHtml += '<div class="ov-total"><span class="fl">共</span> <a href="hostList.action?type=0&queryBusinessId='
							+ businessId
							+ '&nodeType=app_res&nodeId=ywst-'
							+ businessId
							+ '-xnj&treeNodeName=云主机&pnodeId=ywst-'
							+ businessId
							+ '&pnodeName='
							+ appName
							+ '&appId='
							+ businessId
							+ '&curFun=ywst&businessName='+appName+'"><i class="ov-num">'
							+ vmList.length
							+ '</i></a><span class="fr">个云主机</span></div>';

					/*
					 * countHtml += '<div class="ov-total"><span class="fl">共</span>
					 * <a
					 * href="pmQueryListAction.action?type=0&queryBusinessId=' +
					 * businessId + '&nodeType=app_res&nodeId=ywst-' +
					 * businessId + '-wlj&treeNodeName=物理机&pnodeId=ywst-' +
					 * businessId + '&pnodeName=' + appName + '&appId=' +
					 * businessId + '&curFun=ywst"><i class="ov-num">' +
					 * pmList.length + '</i> </a><span class="fr">个物理机</span></div>';
					 */

					countHtml += '<div class="ov-total"><span class="fl">共</span> <a href="ebsQueryListAction.action?queryBusinessId='
							+ businessId
							+ '&nodeType=app_res&nodeId=ywst-'
							+ businessId
							+ '-xnyp&treeNodeName=云硬盘&pnodeId=ywst-'
							+ businessId
							+ '&pnodeName='
							+ appName
							+ '&appId='
							+ businessId
							+ '&curFun=ywst&businessName='+appName+'"><i class="ov-num">'
							+ bsList.length
							+ '</i></a> <span class="fr">个云硬盘</span></div>';

					countHtml += '<div class="ov-total"><span class="fl">共</span> <a href="vlanQueryListAction.action?queryBusinessId='
							+ businessId
							+ '&nodeType=app_res&nodeId=ywst-'
							+ businessId
							+ '-vlan&treeNodeName=Vlan&pnodeId=ywst-'
							+ businessId
							+ '&pnodeName='
							+ appName
							+ '&appId='
							+ businessId
							+ '&curFun=ywst&businessName='+appName+'"><i class="ov-num">'
							+ vlanList.length
							+ '</i></a><span class="fr" style="text-align: left;">个Vlan</span></div>';

					countHtml += '<div class="ov-total"><span class="fl">共</span> <a href="vlanQueryListAction3Phase.action?queryBusinessId='
						+ businessId
						+ '&nodeType=app_res&nodeId=ywst-'
						+ businessId
						+ '-vlan3Phase&treeNodeName=SDN_Vlan&pnodeId=ywst-'
						+ businessId
						+ '&pnodeName='
						+ appName
						+ '&appId='
						+ businessId
						+ '&curFun=ywst&businessName='+appName+'"><i class="ov-num">'
						+ vlan3PhaseList.length
						+ '</i></a><span class="fr" style="text-align: left;">个3期Vlan</span></div>';
					
					countHtml += '<div class="ov-total"><span class="fl">共</span> <a href="ipSegmentQueryListAction.action?queryBusinessId='
							+ businessId
							+ '&nodeType=app_res&nodeId=ywst-'
							+ businessId
							+ '-ip&treeNodeName=IP段&pnodeId=ywst-'
							+ businessId
							+ '&pnodeName='
							+ appName
							+ '&appId='
							+ businessId
							+ '&curFun=ywst&businessName='+appName+'"><i class="ov-num">'
							+ ipSegmentList.length
							+ '</i></a> <span class="fr">个IP段</span></div>';

					countHtml += '<div class="ov-total"><span class="fl">共</span> <a href="lbQueryListAction.action?queryBusinessId='
							+ businessId
							+ '&nodeType=app_res&nodeId=ywst-'
							+ businessId
							+ '-lb&treeNodeName=负载均衡&pnodeId=ywst-'
							+ businessId
							+ '&pnodeName='
							+ appName
							+ '&appId='
							+ businessId
							+ '&curFun=ywst&businessName='+appName+'"><i class="ov-num">'
							+ lbList.length
							+ '</i></a> <span class="fr">个负载均衡</span></div>';

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
									+ vmList[i].resourceId + '\',\''
									+ vmList[i].type + '\',\'' + businessId
									+ '\',\'' + appName
									+ '\')"><div  class="status-block">'
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

					// 物理机
					/*
					 * if (pmList.length > 0) {
					 * 
					 * str = "["; for (var i = 0; i < num; i++) { if
					 * (pmList[i].statusKey == 'PREPARE') { statusTemp = "0";
					 * statusText = "待创建"; statusClass = "blue"; } else if
					 * (pmList[i].statusKey == 'CREATING') { statusTemp = "1";
					 * statusText = "创建中"; statusClass = "blue"; } else if
					 * (pmList[i].statusKey == 'RUNNING') { statusTemp = "2";
					 * statusText = "运行中"; statusClass = "green"; } else if
					 * (pmList[i].statusKey == 'STOP') { statusTemp = "4";
					 * statusText = "停止"; statusClass = "orange"; } else if
					 * (pmList[i].statusKey == 'PAUSE') { statusTemp = "6";
					 * statusText = "已暂停"; statusClass = "orange"; } else if
					 * (pmList[i].statusKey == 'OPERATE_FAIL') { statusTemp =
					 * "9"; statusText = "操作失败"; statusClass = "red"; } else if
					 * (pmList[i].statusKey == 'SENDERROR') { statusTemp = "14";
					 * statusText = "发送失败"; statusClass = "red"; } else if
					 * (pmList[i].statusKey == 'STATUSERROR') { statusTemp =
					 * "15"; statusText = "状态异常"; statusClass = "red"; } else if
					 * (pmList[i].statusKey == 'PROCESSING') { statusTemp =
					 * "16"; statusText = "处理中"; statusClass = "blue"; } str +=
					 * "{id:'" + pmList[i].pmId + "',status:'" + statusTemp +
					 * "'}";
					 * 
					 * var liTag = '<li id="' + pmList[i].pmId + '" class="' +
					 * statusClass + ' shadow"><a
					 * href="javascript:goToDetailPage(\'' +
					 * pmList[i].resourceId + '\',\'' + pmList[i].type + '\',\'' +
					 * businessId + '\',\'' + appName + '\')"><div
					 * class="status-block">' + statusText + '</div><div
					 * class="status-title">' + pmList[i].name + '</div></a></li>';
					 * 
					 * $("#detail_" + businessId).append(liTag);
					 * 
					 * if (i < (num - 1)) { str += ","; } } str += "]"; }
					 * 
					 * if (num <= pmList.length) { return; } else { num = num -
					 * pmList.length; }
					 */

					if (bsList.length > 0) {

						length = bsList.length > num ? num : bsList.length;

						for (var i = 0; i < length; i++) {
							if (bsList[i].statusKey == '0') {
								statusTemp = "0";
								statusText = "待创建";
								statusClass = "blue";
							} else if (bsList[i].statusKey == '1') {
								statusTemp = "1";
								statusText = "创建中";
								statusClass = "blue";
							} else if (bsList[i].statusKey == '2') {
								statusTemp = "2";
								statusText = "运行中";
								statusClass = "green";
							} else if (bsList[i].statusKey == '4') {
								statusTemp = "4";
								statusText = "停止";
								statusClass = "orange";
							} else if (bsList[i].statusKey == '6') {
								statusTemp = "6";
								statusText = "已暂停";
								statusClass = "orange";
							} else if (bsList[i].statusKey == '9') {
								statusTemp = "9";
								statusText = "操作失败";
								statusClass = "red";
							} else if (bsList[i].statusKey == '14') {
								statusTemp = "14";
								statusText = "发送失败";
								statusClass = "red";
							} else if (bsList[i].statusKey == '15') {
								statusTemp = "15";
								statusText = "状态异常";
								statusClass = "red";
							} else if (bsList[i].statusKey == '16') {
								statusTemp = "16";
								statusText = "处理中";
								statusClass = "blue";
							}

							var liTag = '<li class="'
									+ statusClass
									+ ' shadow"><a href="javascript:goToDetailPage(\''
									+ bsList[i].resourceId + '\',\'2' + '\',\''
									+ businessId + '\',\'' + appName
									+ '\')"><div  class="status-block">'
									+ statusText
									+ '</div><div class="status-title">'
									+ bsList[i].name + '</div></a></li>';

							$("#detail_" + businessId).append(liTag);

						}
					}

					if (num <= bsList.length) {
						return;
					} else {
						num = num - bsList.length;
					}
					if (vlanList.length > 0) {
						length = vlanList.length > num ? num : vlanList.length;
						for (var i = 0; i < length; i++) {

							if (vlanList[i].status && vlanList[i].status == '0') {
								statusTemp = "0";
								statusText = "已创建";
								statusClass = "fixed-green";
							} else {
								statusTemp = vlanList[i].status;
								statusText = "待创建";
								statusClass = "fixed-blue";
							}

							var liTag = '<li class="'
									+ statusClass
									+ '"><a style="cursor:default;" href="javascript:void(0);"><div class="status-block">'
									+ statusText
									+ '</div><div class="status-title">'
									+ vlanList[i].name + '</div></a></li>';
							$("#detail_" + businessId).append(liTag);
						}
					}
					if (num <= vlanList.length) {
						return;
					} else {
						num = num - vlanList.length;
					}
					
					if (vlan3PhaseList.length > 0) {
						length = vlan3PhaseList.length > num ? num : vlan3PhaseList.length;
						for (var i = 0; i < length; i++) {

							if (vlan3PhaseList[i].status && vlan3PhaseList[i].status == '0') {
								statusTemp = "0";
								statusText = "已创建";
								statusClass = "fixed-green";
							} else {
								statusTemp = vlan3PhaseList[i].status;
								statusText = "待创建";
								statusClass = "fixed-blue";
							}

							var liTag = '<li class="'
									+ statusClass
									+ '"><a style="cursor:default;" href="javascript:void(0);"><div class="status-block">'
									+ statusText
									+ '</div><div class="status-title">'
									+ vlan3PhaseList[i].name + '</div></a></li>';
							$("#detail_" + businessId).append(liTag);
						}
					}
					if (num <= vlan3PhaseList.length) {
						return;
					} else {
						num = num - vlan3PhaseList.length;
					}

					if (ipSegmentList.length > 0) {
						length = ipSegmentList.length > num ? num
								: ipSegmentList.length;

						for (var i = 0; i < length; i++) {
							if (ipSegmentList[i].status
									&& ipSegmentList[i].status == '0') {
								statusTemp = "0";
								statusText = "已创建";
								statusClass = "green";
							} else {
								statusTemp = ipSegmentList[i].status;
								statusText = "待创建";
								statusClass = "blue";
							}

							var liTag = '<li class="'
									+ statusClass
									+ ' shadow"><a href="javascript:goToDetailPage(\''
									+ ipSegmentList[i].resourceId + '\',\'4'
									+ '\',\'' + businessId + '\',\'' + appName
									+ '\')"><div  class="status-block">'
									+ statusText
									+ '</div><div class="status-title">'
									+ ipSegmentList[i].name + '</div></a></li>';

							$("#detail_" + businessId).append(liTag);
						}
					}

					if (num <= ipSegmentList.length) {
						return;
					} else {
						num = num - ipSegmentList.length;
					}

					if (lbList.length > 0) {
						length = lbList.length > num ? num : lbList.length;
						for (var i = 0; i < length; i++) {
							if (lbList[i].status && lbList[i].status == '0') {
								statusTemp = "0";
								statusText = "已创建";
								statusClass = "green";
							} else {
								statusTemp = lbList[i].status;
								statusText = "待创建";
								statusClass = "blue";
							}

							var liTag = '<li class="'
									+ statusClass
									+ ' shadow"><a href="javascript:goToDetailPage(\''
									+ lbList[i].resourceId + '\',\'4' + '\',\''
									+ businessId + '\',\'' + appName
									+ '\')"><div  class="status-block">'
									+ statusText
									+ '</div><div class="status-title">'
									+ lbList[i].name + '</div></a></li>';

							$("#detail_" + businessId).append(liTag);
						}
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

/**
 * 从资源池查询最新的云主机备份任务状态
 */
function queryVmBakStatusFromResPool(str) {
	var da_val = {
		queryStr : str
	};
	$.ajax({
		type : "POST",
		url : 'vmBakQueryListStateAction.action',
		data : da_val,
		dataType : "JSON",
		cache : false,
		success : function(data) {
			var ls = data.resultStatusInfos;
			if (ls == null || ls == 'undefined') {
				window.location.href = 'exceptionIntercepor.action';
			}

			for (var i = 0; i < ls.length; i++) {
				var statusCode = ls[i].status;
				var statusDesc = ls[i].statusText;
				var id = ls[i].id.replace(/\+/ig, '\\+');
				$("#status" + id).text(statusDesc);
				if (statusCode == '0') {
					$("#status" + id).removeClass()
							.addClass("status fr s-blue");
				} else if (statusCode == '1') {
					$("#status" + id).removeClass()
							.addClass("status fr s-blue");
				} else if (statusCode == '2') {
					$("#status" + id).removeClass().addClass(
							"status fr s-green");
				} else if (statusCode == '7') {
					$("#status" + id).removeClass().addClass(
							"status fr s-orange");
				} else if (statusCode == '9') {
					$("#status" + id).removeClass().addClass(
							"status fr s-orange");
				} else if (statusCode == '10') {
					$("#status" + id).removeClass()
							.addClass("status fr s-blue");
				} else if (statusCode == '11') {
					$("#status" + id).removeClass()
							.addClass("status fr s-blue");
				}
			}
		},
		error : function() {
		}
	});
}

function queryPmStatusFromResPool(str) {
	var da_val = {
		queryStr : str
	};
	$.ajax({
		type : "POST",
		url : 'pmSearchStateAction.action?param=1',
		data : da_val,
		dataType : "json",
		cache : false,
		success : function(data) {
			var ls = data.resultStatusInfos;
			if (ls == null || typeof (ls) == 'undefined') {
				// window.location.href='exceptionIntercepor.action';
				return;
			}
			// ls传过来的是页面上所有pmid的状态结果集而非变化的量
			for (var i = 0; i < ls.length; i++) {
				var statusCode = ls[i].status;
				var statusDesc = ls[i].statusText;
				var id = ls[i].id;
				$("#status" + id).text(statusDesc);
				if (statusCode == '0') {
					$("#status" + id).removeClass()
							.addClass("status fr s-blue");
				} else if (statusCode == '1') {
					$("#status" + id).removeClass()
							.addClass("status fr s-blue");
				} else if (statusCode == '2') {
					$("#status" + id).removeClass().addClass(
							"status fr s-green");
				} else if (statusCode == '3') {
					$("#status" + id).removeClass().addClass(
							"status fr s-brown");
				} else if (statusCode == '4') {
					$("#status" + id).removeClass()
							.addClass("status fr s-gray");
				} else if (statusCode == '5') {
					$("#status" + id).removeClass().addClass(
							"status fr s-orange");
				} else if (statusCode == '10') {
					$("#status" + id).removeClass().addClass(
							"status fr s-orange");
				} else if (statusCode == '11') {
					$("#status" + id).removeClass().addClass(
							"status fr s-orange");
				} else if (statusCode == '12') {
					$("#status" + id).removeClass().addClass(
							"status fr s-purple");
				} else if (statusCode == '13') {
					$("#status" + id).removeClass()
							.addClass("status fr s-blue");
				}
			}
		},
		error : function() {
		}
	});
}

function goToDetailPage(resourceId, type, businessId, appName) {
	var url = "";
	var inputName = "";
	var nodeId = "";
	var treeNodeName = "";
	switch (type) {
	case "0":
		url = "vmDetail.action";
		inputName = "vmId";
		nodeId = "-xnj";
		treeNodeName = "云主机";
		break;
	case "1":
		url = "pmDetail.action";
		inputName = "pmId";
		nodeId = "-wlj";
		treeNodeName = "物理机";
		break;
	case "2":
		url = "diskDetail.action";
		inputName = "caseId";
		nodeId = "-xnyp";
		treeNodeName = "云硬盘";
		break;
	case "4":
		url = "ipSegmentDetailAction.action";
		inputName = "ipSegmentId";
		nodeId = "-ip";
		treeNodeName = "IP段";
		break;
	case "5":
		url = "vmBakDetailAction.action";
		inputName = "caseId";
		nodeId = "-xnjbf";
		treeNodeName = "虚拟机备份";
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
			+ resourceId + '"/>'));
	form.append($('<input type="hidden" name="queryBusinessId" value="'
			+ businessId + '"/>'));
	form.append($('<input type="hidden" name="nodeId" value="ywst-'
			+ businessId + nodeId + '"/>'));
	form.append($('<input type="hidden" name="treeNodeName" value="'
			+ treeNodeName + '"/>'));
	form.append($('<input type="hidden" name="pnodeId" value="ywst-'
			+ businessId + '"/>'));
	form.append($('<input type="hidden" name="pnodeName" value="' + appName
			+ '"/>'));
	form.append($('<input type="hidden" name="appId" value="' + businessId
			+ '"/>'));
	form.append($('<input type="hidden" name="curFun" value="ywst"/>'));
	form.append($('<input type="hidden" name="nodeType" value="app_res"/>'));
	$('body').append(form);
	form.submit();
};
