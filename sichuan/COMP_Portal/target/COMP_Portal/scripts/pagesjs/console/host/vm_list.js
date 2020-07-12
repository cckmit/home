$(function() {
	$("#server").siblings().removeClass("active");
	$("#server").addClass("active");

	queryStatusFromResPool(); // 从接口刷新状态

	vmNameChange();

	// 绑定业务按钮事件
	$('#bind_vm').click(function() {
		$('#searchAppName').attr("value", "");//
		addAppEvent($('#add_user_add_app_button'));
	});
	// 业务列表全选
	$("#selectAllApp").click(function(event) {
		checkAllApp(this);
	});

});

// 业务列表选中的appId数组和appName数组
var currentPageCheckedAppIdArry = new Array();
var currentPageCheckedAppNameArry = new Array();

// 生成业务列表样式及点击确定后添加业务
var addAppEvent = function(addAppButton) {
	$('#appListTbody').empty();
	emptyApp();
	$("#modifyCPU").val('');
	$("#modifyMem").val('');

	$.dialog({
		title : '云主机列表',
		content : document.getElementById('vm_list'),
		init : function() {
			getVMList("vmQueryJson.action?fromBatchVm=1");
		},
		button : [ {
			name : '确定',
			callback : function() {
				// 第一次弹出框显示选择的虚拟机
				$('.delApp').parent('li').remove();// 删除文本框中的内容，重新添加
				for ( var c in currentPageCheckedAppIdArry) {
					addAPP(currentPageCheckedAppIdArry[c],
							currentPageCheckedAppNameArry[c], addAppButton);
				}
				if($('#businessListTab').find('input[type=checkbox]:checked').length==0){
					$.compMsg({
						type : 'error',
						msg : '请至少选择一台云主机再进行操作！'
					});
					return false;
				}

				$.dialog({
					title : '云主机批量修改',
					content : document.getElementById('vm_list_modify'),
					ok : function() {
						// 第二次弹出框修改虚拟机
						if (validateModify()) {
							// 提交订单
							var vmIdList = '';
							$("#add_user_add_app_ul li").each(
									function() {
										vmIdList = vmIdList + ','
												+ $(this).data('vmId');
									});
							var modifyCPU = $("#modifyCPU").val();
							var modifyMem = $("#modifyMem").val();
							$.ajax({
								type : "POST",
								url : "vmBatchUpdateAction.action",
								dataType : "JSON",
								data : {
									cpuNumNew : modifyCPU,
									ramSizeNew : modifyMem,
									vmIdList : vmIdList
								},
								cache : false,
								success : function(data) {
									if ("success" == data.result) {
										$.compMsg({
											type : 'success',
											msg : "云主机批量审批订单已提交！"
										});
									} else {
										$.compMsg({
											type : 'error',
											msg : data.mes,
										});
									}

								},
								error : function() {
									$.compMsg({
										type : 'error',
										msg : "云主机批量审批订单提交失败！"
									});
								}
							});
							emptyApp();
							initDelAppButton();
							return true;
						} else {
							return false;
						}
						focus: true;
					},
					cancelVal : '关闭',
					cancel : true,
					lock : true
				});
			},
			focus : true
		} ],
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
};
function validateModify() {
	var modifyCPU = $("#modifyCPU").val();
	var modifyMem = $("#modifyMem").val();
	var ex = /^\d+$/;
	if (("" == modifyCPU || null == modifyCPU)
			&& ("" == modifyMem || null == modifyMem)) {
		$.compMsg({
			type : 'error',
			msg : "请输入修改云主机的CPU或内存的值！"
		});
		return false;
	}
	if (!("" == modifyCPU || null == modifyCPU) && !ex.test(modifyCPU)) {
		$.compMsg({
			type : 'error',
			msg : "云主机CPU修改的值应为正整数！"
		});
		return false;
	}
	if (!("" == modifyMem || null == modifyMem) && !ex.test(modifyMem)) {
		$.compMsg({
			type : 'error',
			msg : "云主机内存修改的值应为正整数！"
		});
		return false;
	} else {
		return true;
	}
}

// 业务列表，"查询"按钮事件
$("#searchApp").click(function(event) {
	var vmName = $("#searchAppName").val();
	getVMList("vmQueryJson.action?vmName=" + vmName);
});
// 一个一个添加或删除具体业务同在框中已有的APP比对
var addAPP = function(appId, appName, addAppButton) {
	var exist = false;
	var appUL = addAppButton.parents('.edit_app');
	appUL.find('li').each(function() {
		if (appId == $(this).data("vmId")) {
			exist = true;
			return false; // 等效于break
		}
	});

	if (exist) {
		return;
	}

	$(
			'<li style="float: left;width: 150px;line-height: 40px;height: 40px;padding-right: 20px;margin: 2px 5px 3px 0;position:relative;box-sizing:border-box;  overflow: hidden; white-space: nowrap; text-overflow: ellipsis;" title="'
					+ appName
					+ '">'
					+ appName
					+ '<span class="delApp" style="position:absolute;top:0;right:5px;" >X</span></li>')
			.insertBefore(addAppButton).data("vmId", appId).data("vmName",
					appName);

	initDelAppButton();
};

// 删除
var initDelAppButton = function() {
	$('.delApp').click(function() {
		$(this).parent('li').remove();
	});
};
function getVMList(url) {
	$
			.ajax({
				type : "POST",
				url : url,
				dataType : "JSON",
				data : {
					queryVmDialog : '1'
				},
				cache : false,
				success : function(data) {
					if (data.resultPath == "error") {
						window.location.href = 'exceptionIntercepor.action';
					}
					var list = data.vmResultInfoMap.list;
					var page = data.vmResultInfoMap.page;

					$('#appListTbody').empty();

					$
							.each(
									list,
									function(index) {
										var existApp = false;
										for ( var c in currentPageCheckedAppIdArry) {
											if (currentPageCheckedAppIdArry[c] == this.vmId) {
												existApp = true;
												break; // 等效于break
											}
										}
										var appCheckBox = "";
										if (existApp) {
											appCheckBox = $('<td align="center"><input id="" type="checkbox" name="selectApp" value=""  checked="true" onclick="ischeckCurrentAllApp();isCheckGetAppId('
													+ index + ');"/></td>');
										} else {
											appCheckBox = $('<td align="center"><input id="" type="checkbox" name="selectApp" value=""  onclick="ischeckCurrentAllApp();isCheckGetAppId('
													+ index + ');"/></td>');
										}

										$('<tr>')
												.appendTo('#appListTbody')
												.data('vmId', this.vmId)
												.data('vmName', this.vmName)
												.append(appCheckBox)
												.append(
														'<td>' + this.vmName
																+ '</td>')
												.append(
														'<td>'
																+ this.createTime
																+ '</td>');
									});
					ischeckCurrentAllApp();// 通过数组中的值判断"全选"是否需要选中
					$('#appListPageBarDiv').html(page);
				},
				error : function() {
					$.compMsg({
						type : 'error',
						msg : "查询云主机列表失败！"
					});
				}
			});
}

function isCheckGetAppId(cbIndex) {
	var obj = document.getElementsByName("selectApp");
	var appIdTmp = $(obj[cbIndex]).parents('tr').data('vmId');
	var appNameTmp = $(obj[cbIndex]).parents('tr').data('vmName');

	if (obj[cbIndex].checked) {
		currentPageCheckedAppIdArry.push(appIdTmp);// 添加到数组中的页面新选中的元素
		currentPageCheckedAppNameArry.push(appNameTmp);
	} else {
		removeElement(appIdTmp);// 删除数组中指定元素
	}

}

// 点击当前页，每个复选框时判断是否全选 框check
function ischeckCurrentAllApp() {

	var checkedAllApp = true;
	var obj = document.getElementsByName("selectApp");
	if (obj.length == 0) {
		checkedAllApp = false;
	}
	for (var i = 0; i < obj.length; i++) {
		if (!obj[i].checked) {
			checkedAllApp = false;
			break;
		}
	}
	if (checkedAllApp) {
		$('#selectAllApp').prop("checked", true);// 业务列表，当页"全选"true
	} else {
		$('#selectAllApp').prop("checked", false);// 业务列表，当页"全选"false
	}
}

function emptyApp() {
	currentPageCheckedAppIdArry = [];
	currentPageCheckedAppNameArry = [];
	var obj = document.getElementsByName("selectApp");
	for (var i = 0; i < obj.length; i++) {
		if (obj[i].checked) {
			obj[i].checked = false;
		}
	}
}
function initAppAry() {
	$('.delApp').each(function() {
		var appId = $(this).parents('li').data('vmId');
		var appName = $(this).parents('li').data('vmName');
		currentPageCheckedAppIdArry.push(appId);// 添加到数组中的页面新选中的元素
		currentPageCheckedAppNameArry.push(appName);

		$('[name="selectApp"]').not(":checked").each(function() {
			var appId_box = $(this).parents('tr').data('vmId');
			if (appId == appId_box) {
				$(this).attr('checked', true);
			}
		});
	});
}
// 数组指定的元素删除
function removeElement(val) {
	var index = appIdIndexOf(val);
	if (index > -1) {
		currentPageCheckedAppIdArry.splice(index, 1);
		currentPageCheckedAppNameArry.splice(index, 1);
	}
}
// 数组指定的元素位置
function appIdIndexOf(val) {
	for (var i = 0; i < currentPageCheckedAppIdArry.length; i++) {
		if (currentPageCheckedAppIdArry[i] == val)
			return i;
	}
	return -1;
}
// 点击当前页全选复选框，或者全选或者取消
function checkAllApp(obj) {

	if ($(obj).is(':checked')) {
		var arr=$("input[name='selectApp']");
		for(var i=0;i<arr.length;i++){
			console.info($(arr[i]));
			console.info($(arr[i]).prop("checked"));
			
			$(arr[i]).prop('checked', true);
			isCheckGetAppId(i);
		}
	} else {
		$("[name='selectApp']").each(function(index) {
			$(this).prop('checked', false);
			isCheckGetAppId(index);
		});
	}
}

function queryStatusFromResPool() {
	var str = "[";

	for (var i = 0; i < $("#resultList li").length; i++) {
		var status = $("#resultList li:nth-child(" + (i + 1) + ")").attr(
				'status');
		var vmId = $("#resultList li:nth-child(" + (i + 1) + ")").attr('name');
		str += "{id:'" + vmId + "',status:'" + status + "'}";
		if (i != ($("#resultList li").length - 1)) {
			str += ",";
		}
	}
	str += "]";

	if ($("#resultList li").length > 0) {
		var da_val = {
			queryStr : str
		};
		$.ajax({
			type : "POST",
			url : 'vmSearchStateAction.action',
			data : da_val,
			dataType : "JSON",
			cache : false,
			success : function(data) {
				var ls = data.resultStatusInfos;
				if (ls == null || ls == 'undefined') {
					window.location.href = 'exceptionIntercepor.action';
				}

				// ls传过来的是页面上vmid状态变化的量
				if (ls.length != 0) {
					// 状态有变化
					for (var i = 0; i < ls.length; i++) {
						var vmId = ls[i].id.replace(/\+/ig, '\\+');
						// var html = '';
						var statusCode = ls[i].status;
						var statusDesc = ls[i].statusText;
						var statusClass = "";

						if (statusCode == '0') {
							statusClass = "blue";
						} else if (statusCode == '1') {
							statusClass = "blue";
						} else if (statusCode == '2') {
							statusClass = "green";
						} else if (statusCode == '3') {
							statusClass = "blue";
						} else if (statusCode == '4') {
							statusClass = "orange";
						} else if (statusCode == '6') {
							statusClass = "orange";
						} else if (statusCode == '9') {
							statusClass = "red";
						} else if (statusCode == '14') {
							statusClass = "red";
						} else if (statusCode == '15') {
							statusClass = "red";
						} else if (statusCode == '16') {
							statusClass = "blue";
						}
						$("#status" + vmId).text(statusDesc);
						$("#status" + vmId).removeClass().addClass(
								"item-status").addClass(statusClass);

					}
				}
				window.setTimeout(queryStatusFromResPool, 10000);
			},
			error : function() {
			}
		});
	}
}
function goToPage(vmId) {
	if (vmId == '') {

	} else {
		$("#vmId").val(vmId);
		$("#queryParmeterType").val("1");
		$("#gotoForm").attr('action', 'vmDetailAction.action');
		$("#gotoForm").submit();
	}

}
/**
 * 云主机名称字体大小
 */
function vmNameChange() {
	var maxTextLength = 24;// 最大字体长度
	$("li h2")
			.each(
					function(i) {
						if ($(this).height() > 24) {
							var text = $(this).text().replace(/(^\s*)|(\s*$)/g,
									"");
							// 判断是否大于长度截取字符
							if (text.length > maxTextLength) {
								var byteValLen = 0;
								var returnText = "";
								for (var i = 0; i < text.length; i++) {
									(text[i].match(/[^x00-xff]/ig) != null) ? (byteValLen += 2)
											: (byteValLen += 1);
									returnText += text[i];
									if (byteValLen > maxTextLength) {
										break;
									}
								}
								$(this).text(returnText + "...");
							}
							$(this).css("font-size", "12px");
						}
					});
}

// 按状态查询
function will(obj) {
	$(".status-tab li").removeClass("select");
	$(obj).parents('li').addClass("select");
	queryVm('');
}

// 翻页调用js
function getPageData(url) {
	queryVm(url);
}

// 查询列表
function queryVm(url) {
/*	var status = $(".status-tab .select a").attr("value");*/
	var status = "";
	var privateIp = $('#privateIp').val();
	var isoName = $('#isoName').val();
	var vmName = $('#vmName').val();

	var da_val;
	if (url == '' || url == undefined) {
		da_val = {
			privateIp : privateIp,
			isoName : isoName,
			vmName : vmName,
			optState : status
		};
		url = 'vmQueryJson.action';
	}
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
						var statusCode = "";
						var statusClass = "";
						
						var str="";
						for (var i = 0; i < list.length; i++) {
							var entity = list[i];
							statusCode = entity.status.code;
							statusClass = "";
							if (statusCode == '0') {
								statusClass = "blue";
							} else if (statusCode == '1') {
								statusClass = "blue";
							} else if (statusCode == '2') {
								statusClass = "green";
							} else if (statusCode == '3') {
								statusClass = "blue";
							} else if (statusCode == '4') {
								statusClass = "orange";
							} else if (statusCode == '6') {
								statusClass = "orange";
							} else if (statusCode == '9') {
								statusClass = "red";
							} else if (statusCode == '14') {
								statusClass = "red";
							} else if (statusCode == '15') {
								statusClass = "red";
							} else if (statusCode == '16') {
								statusClass = "blue";
							}

							 str += '<li status="'
									+ entity.status
									+ '" id="s'
									+ entity.vmId
									+ '" name="'
									+ entity.vmId
									+ '"><a href="javascript:void(0);" onclick="goToPage(\''
									+ entity.caseId
									+ '\');"><input type="hidden" id="iso'
									+ entity.vmId
									+ '" value="'
									+ entity.isoName
									+ '" /><div id="'
									+ entity.vmId
									+ '" class="item-status '
									+ statusClass
									+ '">'
									+ entity.status.desc
									+ '</div><div class="item-content"><h3>'
									+ entity.vmName
									+ '</h3><div class="detail"><div class="detail-col">'
									+ entity.isoName
									+ '</div><div class="detail-col"><div class="detail-row"><span>内网IP：</span> <span>'
									+ entity.privateIpStr
									+ '</span></div><div class="detail-row"><span>所属企业客户：</span> <span>'
									+ entity.appName
									+ '</span></div></div><div class="detail-col"><div class="detail-row"><span>创建时间：</span> <span id="effectiveTime'
									+ entity.vmId
									+ '">'
									+ entity.effectiveTime
									+ '</span></div><div class="detail-row"><span>到期时间：</span> <span id="overTime'
									+ entity.vmId
									+ '">'
									+ entity.overTime
									+ '</span></div></div></div></div></a></li>';

							$("#resultList").empty();
							$("#resultList").append(str);
							$("#listNull li").hide();
							$(".pageBar").html(page);
						}
						queryStatusFromResPool();
					}
				}
			});
}
