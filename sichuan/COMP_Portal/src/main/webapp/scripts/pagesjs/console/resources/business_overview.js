var itemNumber = 5;
$(function() {
	$("#overview").addClass("active");// 菜单显示当前
	getVmList();
	getPmList();
	getEbsList();
	getIpSegmentList();
	getVlanList();
	getVmBatchModifyList();
	getLBList();
});


/**
 * 获取虚拟机列表
 */
function getVmList() {
	$
			.ajax({
				type : "POST",
				url : 'vmListForOverviewAction.action',
				dataType : "JSON",
				cache : false,
				success : function(data) {
					var str = "";
					var vmInfo = data.vmResultInfos;
					$("#vmCount").html(vmInfo.length);
					if (!jQuery.isEmptyObject(vmInfo)) {
						$("#hostList").empty();

						var num;
						if (itemNumber > vmInfo.length) {
							num = vmInfo.length;
						}else{
							num=itemNumber;
						}

						var statusTemp = '';
						var statusText="加载中";
						var statusClass="blue";
						str = "[";
						for (var i = 0; i < num; i++) {
							// ---------准备queryStatusFromResPool方法参数开始--------

							if (vmInfo[i].status == 'PREPARE') {
								statusTemp = "0";
								statusText = "待创建";
								statusClass = "blue";
							} else if (vmInfo[i].status == 'CREATING') {
								statusTemp = "1";
								statusText = "创建中";
								statusClass = "blue";
							} else if (vmInfo[i].status == 'RUNNING') {
								statusTemp = "2";
								statusText = "运行中";
								statusClass = "green";
							} else if (vmInfo[i].status == 'STOP') {
								statusTemp = "4";
								statusText = "停止";
								statusClass = "orange";
							} else if (vmInfo[i].status == 'PAUSE') {
								statusTemp = "6";
								statusText = "已暂停";
								statusClass = "orange";
							} else if (vmInfo[i].status == 'OPERATE_FAIL') {
								statusTemp = "9";
								statusText = "操作失败";
								statusClass = "red";
							} else if (vmInfo[i].status == 'SENDERROR') {
								statusTemp = "14";
								statusText = "发送失败";
								statusClass = "red";
							} else if (vmInfo[i].status == 'STATUSERROR') {
								statusTemp = "15";
								statusText = "状态异常";	
								statusClass = "red";
							} else if (vmInfo[i].status == 'PROCESSING') {
								statusTemp = "16";
								statusText = "处理中";
								statusClass = "blue";
							}
							str += "{id:'" + vmInfo[i].vmId + "',status:'"
									+ statusTemp + "'}";

							var liTag = '<li id="'+vmInfo[i].vmId +'" class="'+statusClass+' shadow"><a href="javascript:goToVmPage(\''
									+ vmInfo[i].caseId
									+ '\')"><div class="status-block">'
									+ statusText
									+ '</div><div class="status-title">'
									+ vmInfo[i].vmName + '</div></a></li>';

							$("#hostList").append(liTag);

							if (i < (num - 1)) {
								str += ",";
							}
						}
						str += "]";
					}

					queryStatusFromResPool(str); // 从接口重新刷新最新状态
				},
				error : function() {
					$("#hostContent").html("获取数据失败!");
				}
			});
}




/**
 * 从资源池查询最新的虚拟机状态
 */
function queryStatusFromResPool(str) {
	var da_val = {
		queryStr : str
	};
	$.ajax({
		type : "POST",
		url : 'vmSearchStateAction.action?param=1',
		data : da_val,
		dataType : "JSON",
		cache : false,
		success : function(data) {
			var ls = data.resultStatusInfos;
			if (ls == null || ls == 'undefined') {
				window.location.href = 'exceptionIntercepor.action';
			}
			// ls传过来的是页面上所有vmid的状态结果集而非变化的量
			for (var i = 0; i < ls.length; i++) {
				var statusCode = ls[i].status;
				var statusDesc = ls[i].statusText;
				var id = ls[i].id;
				$("#" + id).find('div.status-block').text(statusDesc);
				if (statusCode == '0') {
					$("#" + id).removeClass()
							.addClass("blue").addClass("shadow");
				} else if (statusCode == '1') {
					$("#" + id).removeClass()
					.addClass("blue").addClass("shadow");
				} else if (statusCode == '2') {
					$("#" + id).removeClass()
					.addClass("green").addClass("shadow");
				} else if (statusCode == '3') {
					$("#" + id).removeClass()
					.addClass("blue").addClass("shadow");
				} else if (statusCode == '4') {
					$("#" + id).removeClass()
					.addClass("orange").addClass("shadow");
				} else if (statusCode == '6') {
					$("#" + id).removeClass()
					.addClass("orange").addClass("shadow");
				} else if (statusCode == '9') {
					$("#" + id).removeClass()
					.addClass("red").addClass("shadow");
				} else if (statusCode == '14') {
					$("#" + id).removeClass()
					.addClass("red").addClass("shadow");
				} else if (statusCode == '15') {
					$("#" + id).removeClass()
					.addClass("red").addClass("shadow");
				} else if (statusCode == '16') {
					$("#" + id).removeClass()
					.addClass("blue").addClass("shadow");
				}
			}
		},
		error : function() {
		}
	});
}




/**
 * 云硬盘
 */
function getEbsList() {
	$
			.ajax({
				type : "POST",
				url : 'ebsListForOverviewAction.action',
				dataType : "JSON",
				cache : false,
				success : function(data) {
					$("#ebsCount").html(data.length);
					$("#ebsList").empty();
					var num;
					if (itemNumber > data.length) {
						num = data.length;
					}else{
						num=itemNumber;
					}
					var statusTemp = '';
					var statusText="加载中";
					var statusClass="blue";
					for (var i = 0; i < num; i++) {
						// ---------准备queryStatusFromResPool方法参数开始--------

						if (data[i].diskStatus == '0') {
							statusTemp = "0";
							statusText = "待创建";
							statusClass = "blue";
						} else if (data[i].diskStatus == '1') {
							statusTemp = "1";
							statusText = "创建中";
							statusClass = "blue";
						} else if (data[i].diskStatus == '2') {
							statusTemp = "2";
							statusText = "运行中";
							statusClass = "green";
						} else if (data[i].diskStatus == '4') {
							statusTemp = "4";
							statusText = "停止";
							statusClass = "orange";
						} else if (data[i].diskStatus == '6') {
							statusTemp = "6";
							statusText = "已暂停";
							statusClass = "orange";
						} else if (data[i].diskStatus == '9') {
							statusTemp = "9";
							statusText = "操作失败";
							statusClass = "red";
						} else if (data[i].diskStatus == '14') {
							statusTemp = "14";
							statusText = "发送失败";
							statusClass = "red";
						} else if (data[i].diskStatus == '15') {
							statusTemp = "15";
							statusText = "状态异常";
							statusClass = "red";
						} else if (data[i].diskStatus == '16') {
							statusTemp = "16";
							statusText = "处理中";
							statusClass = "blue";
						}
					var	diskId=	data[i].diskId==null?"":data[i].diskId;
						
						var liTag = '<li id="'+data[i].diskId +'" class="'+statusClass+' shadow"><a href="javascript:goToEbsPage(\''
								+diskId+'\',\''+data[i].caseId
								+ '\')"><div class="status-block">'
								+ statusText
								+ '</div><div class="status-title">'
								+  data[i].diskName + '</div></a></li>';
						$("#ebsList").append(liTag);
					}
				},
				error : function() {
					$("#ebsContent").html("获取云硬盘数据失败!");
				}
			});
}







/**
 * 获取虚拟机批量修改结果
 */
function getVmBatchModifyList() {
	$
			.ajax({
				type : "POST",
				url : 'vmBatchListForOverviewAction.action',
				dataType : "JSON",
				cache : false,
				success : function(data) {
					
					$("#vmBatchModifyCount").html(data.batchVMInfoList.length);
					$("#vmBatchModifyList").empty();
					var num;
					if (itemNumber > data.batchVMInfoList.length) {
						num = data.batchVMInfoList.length;
					}else{
						num=itemNumber;
					}
					var statusTemp = '';
					var statusText="加载中";
					var statusClass="blue";
					for (var i = 0; i < num; i++) {

						if (data.batchVMInfoList[i].vmModifyFlag == '0') {
							statusTemp = "0";
							statusText = "修改失败";
							statusClass = "red";
						} else if (data.batchVMInfoList[i].vmModifyFlag == '1') {
							statusTemp = "1";
							statusText = "修改成功";
							statusClass = "green";
						}

						var liTag = '<li id="'+data.batchVMInfoList[i].id+'" class="'+statusClass+' shadow"><a href="javascript:goToVmBatchModify(\''
								+ data.batchVMInfoList[i].id+'\')"><div class="status-block">'
								+ statusText
								+ '</div><div class="status-title">'
								+  data.batchVMInfoList[i].vmName+ '</div></a></li>';
						$("#vmBatchModifyList").append(liTag);
					}
				},
				error : function() {
					$("#vmBatchModifyContent").html("获取云主机批量修改数据失败!");
				}
			});
}
function goToVmBatchModify(Id) {
	var form = $("<form/>");
	form.attr({
		action : "vmBatchQueryListAction.action",
		method : "post"
	});
	form.append('<input type="hidden" name="vmBatchModifyId" value="' + Id
			+ '"/>');
	$('body').append(form);
	form.submit();
}


/**
 * 跳转到虚拟机详情页面
 */
function goToVmPage(vmId) {
	var form = $("<form/>");
	form.attr({
		action : "vmDetailAction.action",
		method : "post"
	});
	form.append('<input type="hidden" name="vmId" value="' + vmId + '"/>');
	form
			.append('<input type="hidden" name="queryParmeterType" id="queryParmeterType" value="1"/>');
	$('body').append(form);
	form.submit();
}

/**
 * 获取物理机列表
 */
function getPmList() {
	$
			.ajax({
				type : "POST",
				url : 'pmListForOverviewAction.action',
				dataType : "JSON",
				cache : false,
				success : function(data) {
					var str = "";
					var pmInfo = data.pmResultInfos;
					
					var num;
					if (itemNumber > pmInfo.length) {
						num = pmInfo.length;
					}else{
						num=itemNumber;
					}
					
					
					$("#pmCount").html(pmInfo.length);
					$("#pmList").empty();
					
					var statusTemp = '';
					var statusText="加载中";
					var statusClass="blue";
					str = "[";
					for (var i = 0; i < num; i++) {
						// ---------准备queryStatusFromResPool方法参数开始--------

						if (pmInfo[i].status == 'PREPARE') {
							statusTemp = "0";
							statusText = "待创建";
							statusClass = "blue";
						} else if (pmInfo[i].status == 'CREATING') {
							statusTemp = "1";
							statusText = "创建中";
							statusClass = "blue";
						} else if (pmInfo[i].status == 'RUNNING') {
							statusTemp = "2";
							statusText = "运行中";
							statusClass = "green";
						} else if (pmInfo[i].status == 'STOP') {
							statusTemp = "4";
							statusText = "停止";
							statusClass = "orange";
						} else if (pmInfo[i].status == 'PAUSE') {
							statusTemp = "6";
							statusText = "已暂停";
							statusClass = "orange";
						} else if (pmInfo[i].status == 'OPERATE_FAIL') {
							statusTemp = "9";
							statusText = "操作失败";
							statusClass = "red";
						} else if (pmInfo[i].status == 'SENDERROR') {
							statusTemp = "14";
							statusText = "发送失败";
							statusClass = "red";
						} else if (pmInfo[i].status == 'STATUSERROR') {
							statusTemp = "15";
							statusText = "状态异常";
							statusClass = "red";
						} else if (pmInfo[i].status == 'PROCESSING') {
							statusTemp = "16";
							statusText = "处理中";
							statusClass = "blue";
						}
						str += "{id:'" +pmInfo[i].pmId + "',status:'"
								+ statusTemp + "'}";

						var liTag = '<li id="'+pmInfo[i].pmId+'" class="'+statusClass+' shadow"><a href="javascript:goToPmPage(\''
								+ pmInfo[i].caseId
								+ '\')"><div class="status-block">'
								+ statusText
								+ '</div><div class="status-title">'
								+ pmInfo[i].pmName  + '</div></a></li>';

						$("#pmList").append(liTag);

						if (i < (num - 1)) {
							str += ",";
						}
					}
					str += "]";
					
					queryPmStatusFromResPool(str); // 从接口重新刷新最新状态
				},
				error : function() {
					$("#pmList").html("获取数据失败!");
				}
			});
}

/**
 * 从资源池查询最新的物理机状态
 */
function queryPmStatusFromResPool(str) {
	var da_val = {
		queryStr : str
	};
	$.ajax({
		type : "POST",
		url : 'pmSearchStateAction.action?param=1',
		data : da_val,
		dataType : "JSON",
		cache : false,
		success : function(data) {
			var ls = data.resultStatusInfos;
			if (ls == null || ls == 'undefined') {
				window.location.href = 'exceptionIntercepor.action';
			}
			// ls传过来的是页面上所有vmid的状态结果集而非变化的量
			for (var i = 0; i < ls.length; i++) {
				var statusCode = ls[i].status;
				var statusDesc = ls[i].statusText;
				var id = ls[i].id;
				$("#" + id).find('div.status-block').text(statusDesc);
				if (statusCode == '0') {
					$("#" + id).removeClass()
							.addClass("blue").addClass("shadow");
				} else if (statusCode == '1') {
					$("#" + id).removeClass()
					.addClass("blue").addClass("shadow");
				} else if (statusCode == '2') {
					$("#" + id).removeClass()
					.addClass("green").addClass("shadow");
				} else if (statusCode == '3') {
					$("#" + id).removeClass()
					.addClass("blue").addClass("shadow");
				} else if (statusCode == '4') {
					$("#" + id).removeClass()
					.addClass("orange").addClass("shadow");
				} else if (statusCode == '6') {
					$("#" + id).removeClass()
					.addClass("orange").addClass("shadow");
				} else if (statusCode == '9') {
					$("#" + id).removeClass()
					.addClass("red").addClass("shadow");
				} else if (statusCode == '14') {
					$("#" + id).removeClass()
					.addClass("red").addClass("shadow");
				} else if (statusCode == '15') {
					$("#" + id).removeClass()
					.addClass("red").addClass("shadow");
				} else if (statusCode == '16') {
					$("#" + id).removeClass()
					.addClass("blue").addClass("shadow");
				}
			}
		},
		error : function() {
		}
	});
}

/**
 * 跳转到物理机详情页面
 */
function goToPmPage(caseId) {
	var form = $("<form/>");
	form.attr({
		action : "pmDetailAction.action",
		method : "post"
	});
	form.append('<input type="hidden" name="caseId" value="' + caseId + '"/>');
	$('body').append(form);
	form.submit();
}

/**
 * 获取虚拟机备份任务列表
 */
function getVmBakList() {
	$
			.ajax({
				type : "POST",
				url : 'vmBakListForOverviewAction.action',
				dataType : "JSON",
				cache : false,
				success : function(data) {
					
					var num;
					if (itemNumber > vmBakInfos.length) {
						num = vmBakInfos.length;
					}else{
						num=itemNumber;
					}
					
					var str = "";
					var vmBakInfos = data.vmBakResultInfos;
					$("#vmBakContent")
							.html(
									'<ul><li>共有<a href="vmBakQueryListAction.action"><strong id="vmBakCount"></strong></a>个备份任务</li></ul>'
											+ '<ul class="list" id="vmBakList" ul/>');
					$("#vmBakCount").html(vmBakInfos.length);
					$("#vmBakList").empty();
					var flage = false;
					var statusTemp = "";
					for (var i = 0; i < vmBakInfos.length; i++) {

						if (!flage) {
							flage = true;
							str += "[";
						} else {
							str += ",";
						}
						if (vmBakInfos[i].status == '0') {
							statusTemp = "0";
						} else if (vmBakInfos[i].status == 'CREATING') {
							statusTemp = "1";
						} else if (vmBakInfos[i].status == 'RUNNING') {
							statusTemp = "2";
						} else if (vmBakInfos[i].status == 'CREATE_FAIL') {
							statusTemp = "7";
						} else if (vmBakInfos[i].status == 'STATUS_EXCEPTION') {
							statusTemp = "9";
						} else if (vmBakInfos[i].status == 'TASK_WAIT') {
							statusTemp = "10";
						} else if (vmBakInfos[i].status == 'TO_CREATE') {
							statusTemp = "11";
						}
						str += "{id:'" + vmBakInfos[i].vmBakId + "',status:'"
								+ statusTemp + "'}";

						var liTag = $("<li/>");
						var aTag = $('<a href="javascript:goToVmBakPage(\''
								+ vmBakInfos[i].caseId + '\')">'
								+ vmBakInfos[i].vmBakName + '</a>');
						liTag.append(aTag);
						var spanTag = $('<span id="status'
								+ vmBakInfos[i].vmBakId
								+ '" class="status fr"/>');
						if (vmBakInfos[i].status == 'TO_CREATE') {
							spanTag.addClass("s-blue");
							spanTag.append("待创建");
						} else {
							spanTag.addClass("s-blue");
							spanTag.append("加载中");
						}

						liTag.append(spanTag);
						$("#vmBakList").append(liTag);
						// 取前四条
						if (i == num - 1) {
							break;
						}
					}
					if (flage) {
						str += "]";
					}
					queryVmBakStatusFromResPool(str); // 从接口重新刷新最新状态
				},
				error : function() {
					$("#vmBakContent").html("获取数据失败!");
				}
			});
}

/**
 * 从资源池查询最新的虚拟机备份任务状态
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

/**
 * 跳转到虚拟机备份任务详情页面
 */
function goToVmBakPage(caseId) {
	var form = $("<form/>");
	form.attr({
		action : "vmBakDetailAction.action",
		method : "post"
	});
	form.append('<input type="hidden" name="caseId" value="' + caseId + '"/>');
	$('body').append(form);
	form.submit();
}


function goToEbsPage(ebsId, caseId) {
	var form = $("<form/>");
	form.attr({
		action : "diskDetail.action",
		method : "post"
	});
	form.append('<input type="hidden" name="diskId" value="' + ebsId + '"/>');
	form.append('<input type="hidden" name="caseId" value="' + caseId + '"/>');
	$('body').append(form);
	form.submit();
}

/**
 * IP段
 */
function getIpSegmentList() {
	$
			.ajax({
				type : "POST",
				url : 'ipSegmentQuery4Overview.action',
				dataType : "JSON",
				cache : false,
				success : function(data) {
					var num;
					if (itemNumber > data.length) {
						num = data.length;
					}else{
						num=itemNumber;
					}
					
					$("#ipSegmentCount").html(data.length);
					$("#ipSegmentList").empty();
					
					var statusTemp = '';
					var statusText="加载中";
					var statusClass="blue";
					for (var i = 0; i < num; i++) {
						// ---------准备queryStatusFromResPool方法参数开始--------

						if (data[i].status == '0') {
							statusTemp = "0";
							statusText = "待创建";
							statusClass = "blue";
						} else if (data[i].status == '1') {
							statusTemp = "1";
							statusText = "创建中";
							statusClass = "blue";
						} else if (data[i].status == '2') {
							statusTemp = "2";
							statusText = "运行中";
							statusClass = "green";
						} else if (data[i].status == '4') {
							statusTemp = "4";
							statusText = "停止";
							statusClass = "orange";
						} else if (data[i].status == '6') {
							statusTemp = "6";
							statusText = "已暂停";
							statusClass = "orange";
						} else if (data[i].status == '9') {
							statusTemp = "9";
							statusText = "操作失败";
							statusClass = "red";
						} else if (data[i].status == '14') {
							statusTemp = "14";
							statusText = "red";
							statusClass = "error";
						} else if (data[i].status == '15') {
							statusTemp = "15";
							statusText = "状态异常";
							statusClass = "red";
						} else if (data[i].status == '16') {
							statusTemp = "16";
							statusText = "处理中";
							statusClass = "blue";
						}

						var liTag = '<li id="'+data[i].diskId +'" class="'+statusClass+' shadow"><a href="javascript:goToIpSegmentPage(\''
								+data[i].ipSegmentId
								+ '\')"><div class="status-block">'
								+ statusText
								+ '</div><div class="status-title">'
								+  data[i].ipSegmentDesc + '</div></a></li>';
						$("#ipSegmentList").append(liTag);
					}
					
				},
				error : function() {
					$("#ebsContent").html("获取IP段数据失败!");
				}
			});
}

function goToIpSegmentPage(ipSegmentId) {
	var form = $("<form/>");
	form.attr({
		action : "ipSegmentDetailAction.action",
		method : "post"
	});
	form.append('<input type="hidden" name="ipSegmentId" value="' + ipSegmentId
			+ '"/>');
	$('body').append(form);
	form.submit();
}

/**
 * Vlan
 */
function getVlanList() {
	$
			.ajax({
				type : "POST",
				url : 'vlanQuery4Overview.action',
				dataType : "JSON",
				cache : false,
				success : function(data) {
					var num;
					if (itemNumber > data.length) {
						num = data.length;
					}else{
						num=itemNumber;
					}
					$("#vlanCount").html(data.length);
					$("#vlanList").empty();
					
					var statusTemp = '';
					var statusText="加载中";
					var statusClass="fixed-blue";
					for (var i = 0; i < num; i++) {
						// ---------准备queryStatusFromResPool方法参数开始--------

						if (data[i].status == 'PREPARE') {
							statusTemp = "0";
							statusText = "待创建";
							statusClass = "fixed-blue";
						} else if (data[i].status == '1') {
							statusTemp = "1";
							statusText = "创建中";
							statusClass = "fixed-blue";
						} else if (data[i].status == '2') {
							statusTemp = "2";
							statusText = "运行中";
							statusClass = "fixed-green";
						} else if (data[i].status == '4') {
							statusTemp = "4";
							statusText = "停止";
							statusClass = "fixed-orange";
						} else if (data[i].status == '6') {
							statusTemp = "6";
							statusText = "已暂停";
							statusClass = "fixed-orange";
						} else if (data[i].status == '9') {
							statusTemp = "9";
							statusText = "操作失败";
							statusClass = "fixed-red";
						} else if (data[i].status == '14') {
							statusTemp = "14";
							statusText = "发送失败";
							statusClass = "fixed-red";
						} else if (data[i].status == '15') {
							statusTemp = "15";
							statusText = "状态异常";
							statusClass = "fixed-red";
						} else if (data[i].status == '16') {
							statusTemp = "16";
							statusText = "处理中";
							statusClass = "fixed-blue";
						}

						var liTag = '<li id="'+data[i].diskId +'" class="'+statusClass+' "><a style="cursor:default;" href="javascript:void(0);"><div class="status-block">'
								+ statusText
								+ '</div><div class="status-title">'
								+  data[i].vlanName + '</div></a></li>';
						$("#vlanList").append(liTag);
					}
				},
				error : function() {
					$("#vlanContent").html("获取Vlan数据失败!");
				}
			});
}

/**
 * 负载均衡
 */
function getLBList() {
	$
			.ajax({
				type : "POST",
				url : 'loadBalanceListAction.action',
				dataType : "JSON",
				cache : false,
				success : function(data) {
					
					var num;
					if (itemNumber > data.length) {
						num = data.length;
					}else{
						num=itemNumber;
					}
					
					$("#LBcount").html(data.length);
					$("#LBlist").empty();
					
					
					var statusTemp = '';
					var statusText="加载中";
					var statusClass="blue";
					for (var i = 0; i < num; i++) {
						// ---------准备queryStatusFromResPool方法参数开始--------

						if (data[i].status == '0') {
							statusTemp = "0";
							statusText = "待创建";
							statusClass = "blue";
						} else if (data[i].status == '1') {
							statusTemp = "1";
							statusText = "创建中";
							statusClass = "blue";
						} else if (data[i].status == '2') {
							statusTemp = "2";
							statusText = "运行中";
							statusClass = "green";
						} else if (data[i].status == '4') {
							statusTemp = "4";
							statusText = "停止";
							statusClass = "orange";
						} else if (data[i].status == '6') {
							statusTemp = "6";
							statusText = "已暂停";
							statusClass = "orange";
						} else if (data[i].status == '9') {
							statusTemp = "9";
							statusText = "操作失败";
							statusClass = "red";
						} else if (data[i].status == '14') {
							statusTemp = "14";
							statusText = "发送失败";
							statusClass = "red";
						} else if (data[i].status == '15') {
							statusTemp = "15";
							statusText = "状态异常";
							statusClass = "red";
						} else if (data[i].status == '16') {
							statusTemp = "16";
							statusText = "处理中";
							statusClass = "blue";
						}

						var liTag = '<li id="'+data[i].LBid  +'" class="'+statusClass+' shadow"><a href="javascript:goTolbPage(\''
								+data[i].LBid 
								+ '\')"><div class="status-block">'
								+ statusText
								+ '</div><div class="status-title">'
								+   data[i].lbname+ '</div></a></li>';
						$("#LBlist").append(liTag);
					}
					
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
                }
			});
}

function goTolbPage(LBid) {
	var form = $("<form/>");
	form.attr({
		action : "LBdetailAction.action",
		method : "post"
	});
	form.append('<input type="hidden" name="LBid" value="' + LBid + '"/>');
	$('body').append(form);
	form.submit();
}