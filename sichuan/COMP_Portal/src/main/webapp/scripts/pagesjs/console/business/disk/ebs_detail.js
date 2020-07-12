$(function() {
	$("#ebs").siblings().removeClass("active");
	$("#ebs").addClass("active");
	$.each($("input[name='a']"), function() {
		if ($(this).val() == $("#diskSize").val()) {
			$(this).attr("checked", "checked");
		}
	});
	var status = $('#statusHidden').val();
	showStatus(status);
	queryStatusLoading();
	optSetting();
});

var optSetting = function() {
	
	$("#operation").click(function(){
		if($("#ebsopt").css("display")==="none"&&$("#ebsopt li").length>0){
			$("#ebsopt").slideDown('slow');
		}else{
			$("#ebsopt").slideUp('slow');
		}
	});
	
	$("#ebsopt").mouseleave(function() {
		$("#ebsopt").slideUp('slow');
	});


};

function updateCustomNameajax() {
	var diskId = $('#diskId').attr("value");
	var diskName = $("[name='modify_custom_name']").val();
	var diskSize = $("[name='disk_size']").val();
	var resourcePoolId = $("#resourcePoolId").val();
	var resourcePoolPartId = $("#resourcePoolPartId").val();
	if (diskName.length > 25) {
		$.compMsg({
			type : 'error',
			msg : '云硬盘名称最大长度为25!'
		});
		return;
	}
	if (diskSize < $("#diskSize").val()) {
		$.compMsg({
			type : 'error',
			msg : '磁盘只能扩展'
		});
		return;
	}
	var da_val = {
		diskId : diskId,
		diskName : diskName,
		diskSize : diskSize,
		resourcePoolId : resourcePoolId,
		resourcePoolPartId : resourcePoolPartId
	};
	$
			.ajax({
				url : 'diskNameUpdateAction.action?struts.enableJSONValidation=true',
				type : 'POST',
				data : da_val,
				cache : false,
				dataType : 'json',
				success : function(data) {
					var diskNameOriginal = $('#customName').text();
					if (!jQuery.isEmptyObject(data.fieldErrors)) {
						if (data.fieldErrors.diskName.length > 0)
							$.compMsg({
								type : 'error',
								msg : data.fieldErrors.diskName
							});
						return;
					}
					var flag = false;
					switch (data.result) {
					case "error":
						window.location.href = 'exceptionIntercepor.action';
						break;
					case "deleted":
						var errorMessage = diskNameOriginal
								+ "已经被删除，跳回到详细信息列表页面";
						goBackForm(errorMessage, '');
						break;
					case "nopermission":
						var errorMessage = "用户没有操作" + diskNameOriginal
								+ "的权限，跳回到列表页面";
						goBackForm(errorMessage, '');
						break;
					case "success":
						$("#diskSizeInput").val(
								$("#upCustomName input[name='disk_size']")
										.val());
						$("#diskSizeLabel").html(
								$("#upCustomName input[name='disk_size']")
										.val()
										+ 'G');
						flag = true;
					case "failure":
						$("#customName").text(
								$("[name='modify_custom_name']").val());
						$("h2").html($("[name='modify_custom_name']").val());
						break;
					default:
						break;
					}
					
					$.compMsg({
						type : flag ? 'success' : 'error',
						msg : data.msg
					});

				},
				error : function(data) {
					$.compMsg({
						type : 'error',
						msg : data.fieldErrors
					});
				}
			});
}
//修改名称
function upCustomName() {
	$("#upCustomName input[name='modify_custom_name']").val(
			$("#customName").html());
	$("#upCustomName input[name='disk_size']").val($("#diskSize").val());
	$.dialog({
		title : '修改名称',
		content : document.getElementById('upCustomName'),
		ok : function() {
			updateCustomNameajax();
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}
//查询
function seachDisk() {
	var key = $("#diskSeachKey").val();
	var val = $("#diskSeachVal").val();
	var data_val;
	if (key == 'vmName') {
		data_val = {
			vmName : val
		};
	} else if (key == 'vmId') {
		data_val = {
			vmId : val
		};
	}
	/* 非中间状态(运行中,已停止,已暂停),都可以进行挂载 */
	data_val.optState = '2,4,6';
	data_val.queryBusinessId = $('#businessId').val();
	seachDiskList(data_val);
}

//查询方法.
//data_val传递json对象
function seachDiskList(data_val) {
	$("#vmList").html(
			'<tr>' + '<th class="nl"></th>' + '<th>璁惧鍚嶇О</th>' + '<th>ID</th>'
					+ '</tr>');
	$
			.ajax({
				type : "POST",
				url : 'vmListForOverviewAction.action',
				dataType : "JSON",
				data : data_val,
				cache : false,
				success : function(data) {
					var vmInfo = data.vmResultInfos;
					var resourcePoolId = $('#resourcePoolId').val();
					var resourcePoolPartId = $('#resourcePoolPartId').val();
					for (var i = vmInfo.length - 1; i >= 0; i--) {
						if (vmInfo[i].resPoolId == resourcePoolId
								&& vmInfo[i].resPoolPartId == resourcePoolPartId) {
							if (/* vmInfo[i].status != 'RUNNING' || */vmInfo[i].vmId == null
									|| vmInfo[i].vmId == '') {
								continue;
							}
							$("#vmList")
									.append(
											'<tr id="tr_'
													+ vmInfo[i].vmId
													+ '">'
													+ '<td align="center"><input type="radio" name="vh" value="'
													+ vmInfo[i].vmId
													+ '"/></td>' + '<td>'
													+ vmInfo[i].vmName
													+ '</td>' + '<td>'
													+ vmInfo[i].vmId + '</td>'
													+ '</tr>');
						}
					}
				},
				error : function() {
					$("#vmList").append("<td  colspan='4'>获取主机数据失败!</td>");
				}
			});
}

//挂载
function mountDisk() {
	$("#vmList").html(
			'<tr>' + '<th class="nl"></th>' + '<th>设备名称</th>' + '<th>ID</th>'
					+ '</tr>');
	var data_val = {
			/* 非中间状态(运行中,已停止,已暂停),都可以进行挂载 */
		'optState' : '2,4,6',
		'queryBusinessId' : $('#businessId').val()
	};
	$
			.ajax({
				type : "POST",
				url : 'vmListForOverviewAction.action',
				dataType : "JSON",
				data : data_val,
				cache : false,
				success : function(data) {
					var vmInfo = data.vmResultInfos;
					var resourcePoolId = $('#resourcePoolId').val();
					var resourcePoolPartId = $('#resourcePoolPartId').val();
					for (var i = vmInfo.length - 1; i >= 0; i--) {
						if (vmInfo[i].resPoolId == resourcePoolId
								&& vmInfo[i].resPoolPartId == resourcePoolPartId) {
							$("#vmList")
									.append(
											'<tr id="tr_'
													+ vmInfo[i].vmId
													+ '">'
													+ '<td align="center"><input type="radio" name="vh" value="'
													+ vmInfo[i].vmId
													+ '"/></td>' + '<td>'
													+ vmInfo[i].vmName
													+ '</td>' + '<td>'
													+ vmInfo[i].vmId + '</td>'
													+ '</tr>');
						}
					}
				},
				error : function() {
					$("#vmList").append("<td  colspan='4'>获取主机数据失败!</td>");
				}
			});

	$
			.dialog({
				title : '选择设备',
				content : document.getElementById('mountDisk'),
				ok : function() {
//					var selectVmId = $("#vmList .checked input");
					var selectVmId = $("#vmList input[name='vh']:checked");
					var selectVmName = $("#tr_" + selectVmId.val()).children()
							.eq(1).text();
					if (selectVmId.size() == 0) {
						$.dialog({
							title : false,
							clouse : false,
							time : 1,
							width : 200,
							height : 40,
							lock : true,
							content : '请先选择一个主机进行挂载!'
						});
						return false;
					} else {
						$
								.dialog({
									title : false,
									clouse : false,
									width : 200,
									height : 40,
									lock : true,
									content : '请稍后，正在挂载中...',
									init : function() {
										var tmpDialog = this;
										var hostId = selectVmId.val();
										var diskId = $('#diskId').val();
										var resourcePoolId = $(
												'#resourcePoolId').val();
										var resourcePoolPartId = $(
												'#resourcePoolPartId').val();
										var da_val = {
											diskId : diskId,
											hostId : hostId,
											resourcePoolId : resourcePoolId,
											resourcePoolPartId : resourcePoolPartId
										};
										$
												.ajax({
													type : "POST",
													url : 'diskMountAction.action',
													dataType : "JSON",
													data : da_val,
													cache : false,
													success : function(data) {
														if (data.result == "success") {
															$(".unMountDisk")
																	.show();
															$(
																	".unMountDisk .detail-text")
																	.html(
																			selectVmName);
															$(".mountDisk")
																	.hide();
															$(".status")
																	.removeClass(
																			's-blue');
															$(".status")
																	.addClass(
																			's-green');
															$(".status").html(
																	'已挂载');
															$("#hostId").val(
																	hostId);
															$
																	.compMsg({
																		type : 'success',
																		msg : '挂载至主机'
																				+ selectVmName
																				+ '成功!'
																	})
															tmpDialog.close();

															/* 挂载成功后,不能进行删除操作,屏蔽按钮 */
															$('#delButton')
																	.hide();
														}
													},
													error : function() {
														$.compMsg({
															type : 'error',
															msg : '挂载失败!'
														})
														tmpDialog.close();
													}
												});// 挂载结束
									}
								});
						return true;

					}
					return true;
				},
				cancelVal : '关闭',
				cancel : true,
				lock : true
			});
}
//卸载
function unMountDisk() {
	$.dialog({
		title : '卸载',
		content : '请您确认是否卸载设备？',
		ok : function() {
			var hostName = $(".unMountDisk .detail-text").html();
			$.dialog({
				title : false,
				clouse : false,
				width : 200,
				height : 40,
				lock : true,
				content : '请稍后，正在卸载中...',
				init : function() {
					var tmpDialog = this;
					var diskId = $('#diskId').val();
					var hostId = $("#hostId").val();
					var resourcePoolId = $('#resourcePoolId').val();
					var resourcePoolPartId = $('#resourcePoolPartId').val();
					var da_val = {
						diskId : diskId,
						hostId : hostId,
						resourcePoolId : resourcePoolId,
						resourcePoolPartId : resourcePoolPartId
					};
					$.ajax({
						type : "POST",
						url : 'diskUnMountAction.action',
						dataType : "JSON",
						data : da_val,
						cache : false,
						success : function(data) {
							if (data.result == "success") {
								$(".mountDisk").show();
								$(".unMountDisk .detail-text").empty();
								$(".unMountDisk").hide();
								$(".status").removeClass('s-green');
								$(".status").addClass('s-blue');
								$(".status").html('未挂载');
								$("#hostId").val('');
								$.compMsg({
									type : 'success',
									msg : '云硬盘从' + hostName + '主机卸载成功!'
								})
								tmpDialog.close();

								/* 卸载成功后,可以进行删除操作,按钮显示 */
								$('#delButton').show();
							}
						},
						error : function() {
							$.compMsg({
								type : 'error',
								msg : '云硬盘从' + hostName + '主机卸载失败!'
							})
							tmpDialog.close();
						}
					});// 卸载结束
				}
			});
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}
//云硬盘删除
function delHost() {
	$.dialog({
		title : '删除云硬盘',
		content : '请您确认是否删云硬盘？删除后将无法恢复！',
		ok : function() {
			deleteDisk();
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}
function deleteDisk() {
	var diskId = $('#diskId').val();
	var resourcePoolId = $('#resourcePoolId').val();
	var resourcePoolPartId = $('#resourcePoolPartId').val();
	var da_val = {
		diskId : diskId,
		resourcePoolId : resourcePoolId,
		resourcePoolPartId : resourcePoolPartId
	};
	$
			.ajax({
				url : 'diskDeleteAction.action',
				type : 'POST',
				data : da_val,
				cache : false,
				dataType : 'json',
				beforeSend : function() {
					$
							.blockUI({
								message : '<img src="../images/loading.gif" align="absmiddle" style="margin-right:20px">'
										+ '<span id="load_span">正在删除…</span>',
								css : {
									width : '150px',
									left : '45%'
								}
							});
				},
				success : function(data) {
					$.unblockUI();
					if (data.result == "statusinvalid") {
						$.compMsg({
							type : 'error',
							msg : data.resultMessage
						});
					} else if (data.result == "error") {
						window.location.href = 'exceptionIntercepor.action';
					} else if (data.result == "deleted") {
						goBackForm(data.resultMessage, '');
					} else if (data.result == "nopermission") {
						goBackForm(data.resultMessage, '');
					} else if (data.result == "interfaceerror") {
						$.compMsg({
							type : 'error',
							msg : data.resultMessage
						});
					} else {
						var mesage = '编号为' + diskId + '的云硬盘删除成功';
						goBackForm('', mesage);
					}
				}
			});

}
//跳转到列表界面
function goBackForm(errMsg, msg) {
	$.backListMsg({
		msg : msg,
		errMsg : errMsg,
		url : 'ebsQueryListAction.action'
	});
}

//定时刷新云硬盘状态
function queryStatusLoading() {
	var str = "";
	str = "[{id:'" + $("#diskId").val() + "',status:'"
			+ $("#statusHidden").val() + "'}]";
	var da_val = {
		queryStr : str
	};
	$.ajax({
		type : "POST",
		url : 'ebsQueryListStateAction.action',
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
				showStatus(statusCode);
				$("#statusHidden").val(statusCode);
			}
			window.setTimeout('queryStatusLoading()', 10000);
		},
		error : function() {
		}
	});
}

//状态文字及颜色
function showStatus(status) {
	if (status == "0") {
		$("#status").text("待创建");
		$("#title-status").removeClass().addClass("detail-title blue");
	}
	if (status == "1") {
		$("#status").text("创建中");
		$("#title-status").removeClass().addClass("detail-title blue");
	}
	if (status == "2") {
		$("#status").text("已创建");
		$("#title-status").removeClass().addClass("detail-title green");
	}
	if (status == "4") {
		$("#status").text("已挂载");
		$("#title-status").removeClass().addClass("detail-title green");
	}
	if (status == "5") {
		$("#status").text("未挂载");
		$("#title-status").removeClass().addClass("detail-title orange");
	}
	if (status == "6") {
		$("#status").text("创建失败");
		$("#title-status").removeClass().addClass("detail-title red");
	}
	if (status == "7") {
		$("#status").text("挂载失败");
		$("#title-status").removeClass().addClass("detail-title red");
	}
	if (status == "8") {
		$("#status").text("发送失败");
		$("#title-status").removeClass().addClass("detail-title red");
	}
	if (status == "9") {
		$("#status").text("状态异常");
		$("#title-status").removeClass().addClass("detail-title red");
	}
	if (status == "4") {
		$(".unMountDisk").show();
		$(".mountDisk").hide();
	} else if (status != "1" && status != "0") {
		$(".unMountDisk").hide();
		$(".mountDisk").show();
	} else {
		$(".unMountDisk").hide();
		$(".mountDisk").hide();
	}

	/* 判断删除按钮是否需要展示 */
	if (status != '0' && status != '1' && status != '4')
		$('#delButton').show();
	else
		$('#delButton').hide();
}