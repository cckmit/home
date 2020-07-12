$(function() {
		// 菜单选中
		$("#server").siblings().removeClass("active");
		$("#server").addClass("active");
		optSetting();
	});



	
	// 绑定操作动作
	var optSetting=function (){
		
		$("#operation").click(function(){
			if($("#vmopt").css("display")==="none"&&$("#vmopt li").length>0){
				var opts=$("#vmopt li");
				var flag=false;
				for(var i=0;i<opts.length;i++){
					if($(opts[i]).css("display")!="none"){
						flag=true;
					}
					break;
				}
				if(flag){
					$("#vmopt").slideDown('slow');
				}
				
			}else{
				$("#vmopt").slideUp('slow');
			}	
		});
		
		$("#vmopt").mouseleave(function() {
			$("#vmopt").slideUp('slow');
		});

	};
	
	$('div.tab>a').click(function(){
		$(this).addClass('active').siblings().removeClass('active');
		var targetTab=$(this).data('tab');
		$('div.detail-center>'+targetTab).removeClass('none').siblings().addClass('none');
	});
	
	
	


// 网卡行数
var rols = 0;
// 网卡个数
var rolsCount = 0;
// 要提交的网卡list
var netListNew = new Array();

var obj = window.location;
var contextPath = obj.pathname.split("/")[1] + "/" + obj.pathname.split("/")[2];
var basePath = obj.protocol + "//" + obj.host + "/" + contextPath;

var vmBakId = '';

$(function() {

	// 获取云硬盘列表
	getMountDiskList();

	// 获得虚拟机备份列表
	// getVmBakList();

	// 获取虚拟机快照列表
	getSnapshotList();
	
	// 从接口刷新vm状态
	queryStatusFromResPool();
	
	// 从接口刷新快照状态
	// queryStatusForSnap();
});

function operate(status) {
	var $runBtn = '<li id="run"><a href="#" class="run" onclick="startHost()"><img src="../img/ico-opt-on.png" alt="" /></a></li>';
	var $rebootBtn = '<li id="reboot"><a class="restart" href="javascript:rebootHost();"><img src="../img/ico-opt-restart.png" alt="" /></a></li>';
	var $stoptBtn = '<li id="stop"><a class="off" href="javascript:stopHost();"><img src="../img/ico-opt-off.png" alt="" /></a></li>';
	var $pauseBtn = '<li id="pause"><a class="stop" href="javascript:pauseHost();"><img src="../img/ico-opt-stop.png" alt="" /></a></li>';
	var $startBtn = '<li id="start"><a href="#" class="start" onclick="resumeHost()"><img src="../img/ico-opt-recover.png" alt="" /></a></li>';
	var $backBtn = '<li id="backup"><a href="#" class="backup" onclick="backupHost()"><img src="../img/ico-opt-backup.png" alt="" /></a></li>';
	var $delBtn = '<li id="del"><li><a class="del" href="javascript:delHost();"><img src="../img/ico-opt-del.png" alt="" /></a></li>';
	var $snapBtn = '<li id="snap"><a class="shot" href="javascript:snapHost();"><img src="../img/ico-opt-shot.png" alt="" /></a></li>';
	
	
	$("#vmopt").empty();
	$(".vnc").attr("style", "display: none;");
	$("#attach").attr("style", "display: none;");
	$("#detach").attr("style", "display: none;");
	// 运行中
	if (status == "2") {
		$(".vnc").removeAttr("style");
		$("#attach").removeAttr("style");
		$("#detach").removeAttr("style");
		$("#vmopt").append($rebootBtn);
		$("#vmopt").append($stoptBtn);
		$("#vmopt").append($pauseBtn);
		$("#vmopt").append($delBtn);
		$("#vmopt").append($snapBtn);
		return;
	}
	// 已停止
	if (status == "4") {
		$("#attach").removeAttr("style");
		$("#detach").removeAttr("style");
		$("#vmopt").append($runBtn);
		$("#vmopt").append($delBtn);
		$("#vmopt").append($snapBtn);
		return;
	}
	// 已暂停
	if (status == "6") {
		$("#attach").removeAttr("style");
		$("#detach").removeAttr("style");
		$("#vmopt").append($startBtn);
		$("#vmopt").append($delBtn);
		$("#vmopt").append($snapBtn);
		return;
	}
	// 操作失败、发送失败、状态异常
	if (status == "9" || status == "14" || status == "15") {
		$("#vmopt").append($delBtn);
		$("#vmopt").append($snapBtn);
		return;
	}
	// 处理中
	if (status == "16") {
		// $("#vmopt").append($backBtn);
	}
	$("#vmopt").empty();
}

// 主机删除
function delHost() {
	$.dialog({
		title : '删除云主机',
		content : '请您确认是否删云主机？删除后将无法恢复！',
		ok : function() {
			clearTimeout(t);
			/*
			 * $.compMsg({ type : 'loading', timeout : 3600000,//显示时间 msg :
			 * '云主机删除中，请稍后...' });
			 */
			var vmId = $('#vmId').attr("value");
			var resourcePoolId = $('#resPoolId').attr("value");
			var resourcePoolPartId = $('#resPoolPartId').attr("value");
			var da_val = {
				'vmId' : vmId,
				'resourcePoolId' : resourcePoolId,
				'resourcePoolPartId' : resourcePoolPartId
			};
			$.ajax({
				url : 'vmDelAction.action',
				type : 'POST',
				data : da_val,
				cache : false,
				dataType : 'json',
				beforeSend : function(){
					$.blockUI({ 
						message:  '<img src="../images/loading.gif" align="absmiddle" style="margin-right:20px">'+'<span id="load_span">云主机删除中，请稍后...</span>',
						css: { width: '250px' , left : '45%' }
					}); 
				},
				success : function(data) {
					$.unblockUI();
					if (data.resultPath == '0') {
						if (!(data.mes == null || data.mes == '')) {
							$.compMsg({
								type : 'error',
								msg : data.mes
							});
						}
						// 中间状态展示
						if (data.result == "intermediate") {
							$("#status").text("操作成功");
							$("div.detail-title").removeClass().addClass(
							"detail-title blue");
						}
					} else if (data.resultPath == '2') {
						var vmName = $('#custom_name').text();
						var errMsg = vmName + "已经被删除，跳回到云主机列表页面";
						goBackForm(errMsg, "");
					} else if (data.resultPath == '-1') {
						window.location.href = 'exceptionIntercepor.action';
					} else {
						var vmName = $('#custom_name').text();
						var msg = vmName + "删除成功，跳回到云主机列表页面！";
						goBackForm("", msg);
					}
				}
			});
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}


// 跳转到列表界面
function goBackForm(errMsg, msg) {
	$.backListMsg({
		msg : msg,
		errMsg : errMsg,
		url : 'vmQueryListAction.action'
	});
}
function stopHost() {
	// 关机添加二次确认
	$.dialog({
		title : '关闭云主机',
		content : '请您确认是否关闭云主机？',
		ok : function() {
	var vmId = $('#vmId').attr("value");
	var resourcePoolId = $('#resPoolId').attr("value");
	var resourcePoolPartId = $('#resPoolPartId').attr("value");
	var da_val = {
		vmId : vmId,
		resourcePoolId : resourcePoolId,
		resourcePoolPartId : resourcePoolPartId
	};
	$.ajax({
		url : 'vmStopAction.action',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {
			var vmName = $('#custom_name').text();
			if (data.result == "failure") {
				$.compMsg({
					type : 'error',
					msg : '操作失败!'
				});
			}
			if (data.result == "statusinvalid") {
				$.compMsg({
					type : 'error',
					msg : data.resultMessage
				});
			}
			if (data.result == "error") {
				window.location.href = 'exceptionIntercepor.action';
			}
			if (data.result == "deleted") {
				var errorMessage = vmName + "已经被删除，跳回到云主机列表页面";
				goBackForm(errorMessage, '');
			}
			if (data.result == "nopermission") {
				var errorMessage = "用户没有操作" + vmName + "的权限，跳回到云主机列表页面";
				goBackForm(errorMessage, '');
			}
			// 中间状态展示
			if (data.result == "intermediate") {
				$("#status").text("操作成功");
				$("div.detail-title").removeClass().addClass(
				"detail-title blue");
			}
		}
	});
},
cancelVal : '关闭',
cancel : true,
lock : true
});

}
function startHost() {
	// 开机添加二次确认
	$.dialog({
		title : '启动云主机',
		content : '请您确认是否启动云主机？',
		ok : function() {
	var vmId = $('#vmId').attr("value");
	var resourcePoolId = $('#resPoolId').attr("value");
	var resourcePoolPartId = $('#resPoolPartId').attr("value");
	var da_val = {
		vmId : vmId,
		resourcePoolId : resourcePoolId,
		resourcePoolPartId : resourcePoolPartId
	};
	$.ajax({
		url : 'vmStartAction.action',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {
			var vmName = $('#custom_name').text();
			if (data.result == "failure") {
				$.compMsg({
					type : 'error',
					msg : '操作失败!'
				});
				return;
			}
			if (data.result == "statusinvalid") {
				$.compMsg({
					type : 'error',
					msg : data.resultMessage
				});
			}
			if (data.result == "error") {
				window.location.href = 'exceptionIntercepor.action';
			}
			if (data.result == "deleted") {
				var errorMessage = vmName + "已经被删除，跳回到云主机列表页面";
				goBackForm(errorMessage, '');
			}
			if (data.result == "nopermission") {
				var errorMessage = "用户没有操作" + vmName + "的权限，跳回到云主机列表页面";
				goBackForm(errorMessage, '');
			}
			// 中间状态展示
			if (data.result == "intermediate") {
				$("#status").text("操作成功");
				$("div.detail-title").removeClass().addClass(
				"detail-title blue");
			}
		}
	});
},
		
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}
function rebootHost() {
	// 重启添加二次确认
	$.dialog({
		title : '重启云主机',
		content : '请您确认是否重启云主机？',
		ok : function() {
	var vmId = $('#vmId').attr("value");
	var resourcePoolId = $('#resPoolId').attr("value");
	var resourcePoolPartId = $('#resPoolPartId').attr("value");
	var da_val = {
		vmId : vmId,
		resourcePoolId : resourcePoolId,
		resourcePoolPartId : resourcePoolPartId
	};
	$.ajax({
		url : 'vmRebootAction.action',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {
			var vmName = $('#custom_name').text();
			if (data.result == "failure") {
				$.compMsg({
					type : 'error',
					msg : '操作失败!'
				});
				return;
			}
			if (data.result == "statusinvalid") {
				$.compMsg({
					type : 'error',
					msg : data.resultMessage
				});
			}
			if (data.result == "error") {
				window.location.href = 'exceptionIntercepor.action';
			}
			if (data.result == "deleted") {
				var errorMessage = vmName + "已经被删除，跳回到云主机列表页面";
				goBackForm(errorMessage, '');
			}
			if (data.result == "nopermission") {
				var errorMessage = "用户没有操作" + vmName + "的权限，跳回到云主机列表页面";
				goBackForm(errorMessage, '');
			}
			// 中间状态展示
			if (data.result == "intermediate") {
				$("#status").text("操作成功");
				$("div.detail-title").removeClass().addClass(
				"detail-title blue");
			}
		}
	});
},
		
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}
function pauseHost() {
	// 暂停添加二次确认
	$.dialog({
		title : '暂停云主机',
		content : '请您确认是否暂停云主机？',
		ok : function() {
	var vmId = $('#vmId').attr("value");
	var resourcePoolId = $('#resPoolId').attr("value");
	var resourcePoolPartId = $('#resPoolPartId').attr("value");
	var da_val = {
		vmId : vmId,
		resourcePoolId : resourcePoolId,
		resourcePoolPartId : resourcePoolPartId
	};
	$.ajax({
		url : 'vmPauseAction.action',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {
			var vmName = $('#custom_name').text();
			if (data.result == "failure") {
				$.compMsg({
					type : 'error',
					msg : '操作失败!'
				});
				return;
			}
			if (data.result == "statusinvalid") {
				$.compMsg({
					type : 'error',
					msg : data.resultMessage
				});
			}
			if (data.result == "error") {
				window.location.href = 'exceptionIntercepor.action';
			}
			if (data.result == "deleted") {
				var errorMessage = vmName + "已经被删除，跳回到云主机列表页面";
				goBackForm(errorMessage, '');
			}
			if (data.result == "nopermission") {
				var errorMessage = "用户没有操作" + vmName + "的权限，跳回到云主机列表页面";
				goBackForm(errorMessage, '');
			}
			// 中间状态展示
			if (data.result == "intermediate") {
				$("#status").text("操作成功");
				$("div.detail-title").removeClass().addClass(
				"detail-title blue");
			}
		}
	});
},
		
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}
function resumeHost() {
	// 恢复添加二次确认
	$.dialog({
		title : '恢复云主机',
		content : '请您确认是否恢复云主机？',
		ok : function() {
	var vmId = $('#vmId').attr("value");
	var resourcePoolId = $('#resPoolId').attr("value");
	var resourcePoolPartId = $('#resPoolPartId').attr("value");
	var da_val = {
		vmId : vmId,
		resourcePoolId : resourcePoolId,
		resourcePoolPartId : resourcePoolPartId
	};
	$.ajax({
		url : 'vmResumeAction.action',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {
			var vmName = $('#custom_name').text();
			if (data.result == "failure") {
				$.compMsg({
					type : 'error',
					msg : '操作失败!'
				});
				return;
			}
			if (data.result == "statusinvalid") {
				$.compMsg({
					type : 'error',
					msg : data.resultMessage
				});
			}
			if (data.result == "error") {
				window.location.href = 'exceptionIntercepor.action';
			}
			if (data.result == "deleted") {
				var errorMessage = vmName + "已经被删除，跳回到云主机列表页面";
				goBackForm(errorMessage, '');
			}
			if (data.result == "nopermission") {
				var errorMessage = "用户没有操作" + vmName + "的权限，跳回到云主机列表页面";
				goBackForm(errorMessage, '');
			}
			// 中间状态展示
			if (data.result == "intermediate") {
				$("#status").text("操作成功");
				$("div.detail-title").removeClass().addClass(
				"detail-title blue");
			}
		}
	});
},

cancelVal : '关闭',
cancel : true,
lock : true
});
}
/* 登录主机操作 */
var login = null;
function loginHost() {
	var hostUrl = $("#vncUrl").val();
	var vmId = $("#vmId").val();
	var url = hostUrl + "/admin/" + vmId
			+ "/20120312000000/yYxWFb+qRd0A262me60PaA==";
	var parameters = 'height=500,width=600,resizable=yes,status=no,location=no';
	// url =
	// "https://10.10.127.192:8443/njcmp/vmaccess/admin/CIDC-R-01-002-VM-00000100/20120312000000/yYxWFb+qRd0A262me60PaA==";
	window.open(url, '', parameters);
	// alert(url);
	// login = $.dialog( {
	// title : '登录主机',
	// content : document.getElementById('loginHost'),
	// cancelVal : '关闭',
	// cancel : true,
	// lock : true
	// });
}

/* 关闭 */
function loginClose() {
	if (login != null) {
		login.time(3);
	}
}

// 获取挂载列表
function getMountDiskList() {
	var vmId = $('#vmId').attr("value");
	if (null != vmId && vmId != '') {
		var da_val = {
			hostId : vmId,
		};
		$
				.ajax({
					type : "POST",
					url : 'ebsQueryListForVmAction.action?flag=1',
					dataType : "JSON",
					data : da_val,
					cache : false,
					success : function(data) {
						if (data.length == 0) {
							/*$("#diskList")
									.append(
											"<tr><td id='noDisk' colspan='4'>暂时未挂载云硬盘!</td></tr>");*/
						} else {
							for (var i = data.length - 1; i >= 0; i--) {
								$("#diskList")
										.append(
												'<tr id="diskList_tr_'
														+ data[i].diskId
														+ '">'
														+ '<td>'
														+ data[i].diskName
														+ '</td>'
														+ '<td>'
														+ data[i].diskId
														+ '</td>'
														+ '<td>'
														+ data[i].diskSize
														+ 'TB</td>'
														+ '<td class="span-btn"><span id="detach"><a href="javascript: unMountDisk(\'diskList_tr_'
														+ data[i].diskId
														+ '\');">卸载</a></span></td>'
														+ '</tr>');
							}
							;
						}
					},
					error : function() {
						$("#diskList").append(
								"<tr><td id='noDisk' colspan='4'>获取云硬盘信息失败!</td></tr>");
					}
				});
	} else {
		$("#diskList").append("<tr><td id='noDisk' colspan='4'>暂时未挂载云硬盘!</td></tr>");
	}

}
// 查询
function seachDisk(appId) {
	var key = $("#diskSeachKey").val();
	var val = $("#diskSeachVal").val();
	var data_val;
	if (key == 'diskName') {
		data_val = {
			diskName : val,
			businessId : appId,
			resourceType : 1
		};
	} else if (key == 'diskId') {
		data_val = {
			diskId : val,
			businessId : appId,
			resourceType : 1
		};
	} else if (key == 'diskSize') {
		data_val = {
			diskSize : val,
			businessId : appId,
			resourceType : 1
		};
	} else {
		data_val = {
			businessId : appId,
			resourceType : 1
		};
	}
	seachDiskList(data_val);
}

function seachDiskList(data_val) {
	$("#mountDiskList").html(
			'<tr>' + '<th class="nl"></th>' + '<th>名称</th>' + '<th>ID</th>'
					+ '<th>容量</th>' + '</tr>');
	$
			.ajax({
				type : "POST",
				url : 'ebsQueryListForVmAction.action?flag=1',
				dataType : "JSON",
				data : data_val,
				cache : false,
				success : function(data) {
					var resourcePoolId = $('#resPoolId').val();
					var resourcePoolPartId = $('#resPoolPartId').val();
					for (var i = data.length - 1; i >= 0; i--) {
						if (data[i].diskStatus == '2' || data[i].diskStatus == '5') {
							if (data[i].resourcePoolId == resourcePoolId
									&& data[i].resourcePoolPartId == resourcePoolPartId) {
								$("#mountDiskList")
										.append(
												'<tr id="tr_'
														+ data[i].diskId
														+ '">'
														+ '<td align="center"><input type="radio" name="vh" value="'
														+ data[i].diskId
														+ '"/></td>' + '<td>'
														+ data[i].diskName
														+ '</td>' + '<td>'
														+ data[i].diskId
														+ '</td>' + '<td>'
														+ data[i].diskSize
														+ 'TB</td>' + '</tr>');
							}
						}
					}
				},
				error : function() {
					$("#mountDiskList").append(
							"<td  colspan='4'>获取云硬盘信息失败!</td>");
					return "false";
				}
			});
}
// 挂载
function mountDisk(appId) {
	var data_val = {
		businessId : appId,
		resourceType : 1
	};
	seachDiskList(data_val);
	$
			.dialog({
				title : '选择云硬盘',
				content : document.getElementById('mountDisk'),
				ok : function() {
					var selectDiskId = $("#mountDiskList input[name='vh']:checked");
					if (selectDiskId.size() == 0) {
						$.dialog({
							title : false,
							clouse : false,
							time : 1,
							width : 200,
							height : 40,
							lock : true,
							content : '请先选择一个云硬盘进行挂载!'
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
										var diskId = selectDiskId.val();
										var td = $("#tr_" + diskId).children();
										var hostId = $('#vmId').attr("value");
										var resourcePoolId = $('#resPoolId')
												.val();
										var resourcePoolPartId = $(
												'#resPoolPartId').val();
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
															$("#diskList")
																	.append(
																			'<tr id="diskList_tr_'
																					+ diskId
																					+ '">'
																					+ '<td>'
																					+ td
																							.eq(
																									1)
																							.text()
																					+ '</td>'
																					+ '<td>'
																					+ td
																							.eq(
																									2)
																							.text()
																					+ '</td>'
																					+ '<td>'
																					+ td
																							.eq(
																									3)
																							.text()
																					+ '</td>'
																					+ '<td class="span-btn"><span id="detach"><a href="javascript: unMountDisk(\'diskList_tr_'
																					+ diskId
																					+ '\');">卸载</a></span></td>'
																					+ '</tr>');
															$
																	.compMsg({
																		type : 'success',
																		msg : td
																				.eq(
																						1)
																				.text()
																				+ '云硬盘挂载成功!'
																	})
															tmpDialog.close();
														}
													},
													error : function() {
														$
																.compMsg({
																	type : 'error',
																	msg : td
																			.eq(
																					1)
																			.text()
																			+ '云硬盘挂载失败!'
																})
														tmpDialog.close();
													}
												});// 挂载结束
									}
								});
						return true;
					}
				},
				cancelVal : '关闭',
				cancel : true,
				lock : true
			});
}
// 卸载
function unMountDisk(obj) {
	$
			.dialog({
				title : '卸载',
				content : '请您确认是否卸载设备？',
				ok : function() {
					$
							.dialog({
								title : false,
								clouse : false,
								width : 200,
								height : 40,
								lock : true,
								content : '请稍后，正在卸载中...',
								init : function() {
									var tmpDialog = this;
									var diskId = obj.split("diskList_tr_")[1];
									var hostId = $("#vmId").val();
									var resourcePoolId = $('#resPoolId').val();
									var resourcePoolPartId = $('#resPoolPartId')
											.val();
									var da_val = {
										diskId : diskId,
										hostId : hostId,
										resourcePoolId : resourcePoolId,
										resourcePoolPartId : resourcePoolPartId
									};
									$
											.ajax({
												type : "POST",
												url : 'diskUnMountAction.action',
												dataType : "JSON",
												data : da_val,
												cache : false,
												success : function(data) {
													if (data.result == "success") {
														if ($("#" + obj)
																.siblings()
																.size() == 1) {
															$("#diskList")
																	.append(
																			"<td id='noDisk' colspan='4'>暂时未挂载云硬盘!</td>");
														}
														$("#" + obj).remove();
														$.compMsg({
															type : 'success',
															msg : '卸载成功!'
														})
														tmpDialog.close();
													}
												},
												error : function() {
													$.compMsg({
														type : 'error',
														msg : '卸载失败!'
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

function updateRemarkajax() {
	var vmId = $('#vmId').attr("value");
	var remart = $("[name='modify_remark']").val();
	if (remart.length > 100) {
		$.compMsg({
			type : 'error',
			msg : '备注信息最大长度为100!'
		});
		return;
	}
	var da_val = {
		vmId : vmId,
		description : remart
	};
	$
			.ajax({
				url : 'vmDescUpdateAction.action?struts.enableJSONValidation=true',
				type : 'POST',
				data : da_val,
				cache : false,
				dataType : 'json',
				success : function(data) {
					var vmName = $('#custom_name').text();
					if (!jQuery.isEmptyObject(data.fieldErrors)) {
						if (data.fieldErrors.description.length > 0)
							$.compMsg({
								type : 'error',
								msg : data.fieldErrors.description
							});
						return;
					}
					if (data.result == "error") {
						window.location.href = 'exceptionIntercepor.action';
					} else {
						if (data.result == "deleted") {
							var errorMessage = vmName + "已经被删除，跳回到云主机列表页面";
							goBackForm(errorMessage, '');
						} else {
							if (data.result == "nopermission") {
								var errorMessage = "用户没有操作" + vmName
										+ "的权限，跳回到云主机列表页面";
								goBackForm(errorMessage, '');
							} else {
								$("#remark").text(
										$("[name='modify_remark']").val());
							}
						}
					}
				}
			});
	return true;
}
// 修改备注
function modifyRemark() {
	var vmId = $('#vmId').attr("value");
	if(vmId != ''){
		$("#modify_remark_div textarea").val("");
		$.dialog({
			title : '修改备注',
			content : document.getElementById('modify_remark_div'),
			init : function() {
				$("[name='modify_remark']").val($.trim($("#remark").text()));
			},
			ok : function() {
				updateRemarkajax();
				return true;
	
			},
			cancelVal : '关闭',
			cancel : true,
			lock : true
		});
	}else{
		$.compMsg({
			type : 'warn',
			msg : '待创建的云主机不能修改备注！'
		});
	}
}

function updateCustomNameajax() {
	var vmId = $('#vmId').attr("value");
	var vmName = $("[name='modify_custom_name']").val();
	if (vmName.length > 17) {
		$.compMsg({
			type : 'error',
			msg : '云主机名称最大长度为16!'
		});
		return;
	}
	var da_val = {
		vmId : vmId,
		vmName : vmName
	};
	$.ajax({
		url : 'vmNameUpdateAction.action?struts.enableJSONValidation=true',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {
			var vmNameOriginal = $('#custom_name').text();
			if (!jQuery.isEmptyObject(data.fieldErrors)) {
				if (data.fieldErrors.vmName.length > 0)
					$.compMsg({
						type : 'error',
						msg : data.fieldErrors.vmName
					});
				return;
			}
			if (data.result == "error") {
				window.location.href = 'exceptionIntercepor.action';
			} else {
				if (data.result == "deleted") {
					var errorMessage = vmNameOriginal + "已经被删除，跳回到云主机列表页面";
					goBackForm(errorMessage, '');
				} else {
					if (data.result == "nopermission") {
						var errorMessage = "用户没有操作" + vmNameOriginal
								+ "的权限，跳回到云主机列表页面";
						goBackForm(errorMessage, '');
					} else {
						$("#custom_name").text(
								$("[name='modify_custom_name']").val());
						$("h2").html($("[name='modify_custom_name']").val());
					}

				}
			}
		}
	});
}
// 修改名称
function modifyCustomName() {
	var vmId = $('#vmId').attr("value");
	if(vmId != ''){
		$("#modify_custom_name_div textarea").val("");
		$.dialog({
			title : '修改名称',
			content : document.getElementById('modify_custom_name_div'),
			init : function() {
				$("[name='modify_custom_name']").val($("#custom_name").text());
			},
			ok : function() {
				updateCustomNameajax();
				return true;

			},
			cancelVal : '关闭',
			cancel : true,
			lock : true
		});
	}else{
		$.compMsg({
			type : 'warn',
			msg : '待创建的云主机不能修改名称！'
		});
	}
}
$(document).ready(function() {
	if ($('#error').text() != "") {
		setTimeout(function() {
			goToVMListPage();
		}, 3000);
		return;
	}
});

// 虚拟机删除延长查询状态时间
var t;
// 查询虚拟机当前状态
var currentStatus;
var intValue=0;
function queryStatusFromResPool() {
	var vmId = $('#vmId').val();
	var status = $("#state").val();
	var str = "[{id:'" + vmId + "',status:'" + status + "'}]";
	var da_val = {
		queryStr : str
	};
	$
			.ajax({
				type : "POST",
				url : 'vmSearchStateAction.action?param=2',
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
							var statusCode = ls[i].status;
							var statusDesc = ls[i].statusText;
							$("#status").text(statusDesc);
							if (statusCode == '0') {
								$("div.detail-title").removeClass().addClass(
										"detail-title blue");
							} else if (statusCode == '1') {
								$("div.detail-title").removeClass().addClass(
								"detail-title blue");
								
						/*		document.getElementById("timers").style.display="block";*/
								intValue+=10;
								var progress = document.getElementById('progress');
								var text = document.getElementById('progressText');
								progress.value = intValue;
								text.innerHTML = "正在安装" + intValue + "%";
									progress.value = intValue;
									if (intValue >= 90) {
										intValue=90;
										progress.value=90;
									}
									text.innerHTML = "正在安装" + intValue + "%";
								
									
							} else if (statusCode == '2') {
								$("div.detail-title").removeClass().addClass(
								"detail-title green");
								

						/*		document.getElementById("timers").style.display="block";*/
								 intValue = 100;
									var progress1 = document.getElementById('progress');
									var text1 = document.getElementById('progressText');
									progress1.value = 100;
									text1.innerHTML = "安装成功" + intValue + "%";
									function progressFn1() {
										intValue1 = 100;
										progress1.value = intValue1;
										text1.innerHTML = "安装成功" + intValue + "%";
									}
								
								
								
								
							} else if (statusCode == '3') {
								$("div.detail-title").removeClass().addClass(
								"detail-title orange");
							} else if (statusCode == '4') {
								$("div.detail-title").removeClass().addClass(
								"detail-title orange");
							} else if (statusCode == '6') {
								$("div.detail-title").removeClass().addClass(
								"detail-title orange");
							} else if (statusCode == '9') {
								$("div.detail-title").removeClass().addClass(
								"detail-title red");
							} else if (statusCode == '14') {
								$("div.detail-title").removeClass().addClass(
								"detail-title red");
							} else if (statusCode == '15') {
								$("div.detail-title").removeClass().addClass(
								"detail-title red");
							} else if (statusCode == '16') {
								$("div.detail-title").removeClass().addClass(
								"detail-title blue");
								
						/*		document.getElementById("timers").style.display="block";*/
								intValue+=10;
								var progress = document.getElementById('progress');
								var text = document.getElementById('progressText');
								progress.value = intValue;
								text.innerHTML = "正在安装" + intValue + "%";
									progress.value = intValue;
									if (intValue >= 90) {
										intValue=90;
										progress.value=90;
									}
									text.innerHTML = "正在安装" + intValue + "%";
								
								
								
								
								
							}else if (statusCode == '24') {
								$("div.detail-title").removeClass().addClass(
								"detail-title blue");
					}
							operate(statusCode);
							currentStatus = statusCode;
						}
					} else {
						// 状态没变化，还取变化之前的值
						if (status == '0') {
							$("#status").text("待创建");
							$("div.detail-title").removeClass().addClass(
							"detail-title blue");
						} else if (status == '1') {
							$("#status").text("创建中");
							$("div.detail-title").removeClass().addClass(
							"detail-title blue");
							
						/*	document.getElementById("timers").style.display="block";*/
							intValue+=10;
							var progress = document.getElementById('progress');
							var text = document.getElementById('progressText');
							progress.value = intValue;
							text.innerHTML = "正在安装" + intValue + "%";
								progress.value = intValue;
								if (intValue >= 90) {
									intValue=90;
									progress.value=90;
								}
								text.innerHTML = "正在安装" + intValue + "%";
							
							
						} else if (status == '2') {
							$("#status").text("运行中");
							$("div.detail-title").removeClass().addClass(
							"detail-title green");
							

							
					/*		document.getElementById("timers").style.display="block";*/
							 intValue = 100;
								var progress1 = document.getElementById('progress');
								var text1 = document.getElementById('progressText');
								progress1.value = 100;
								text1.innerHTML = "安装成功" + intValue + "%";
								function progressFn1() {
									intValue1 = 100;
									progress1.value = intValue1;
									text1.innerHTML = "安装成功" + intValue + "%";
								}
							
							
						} else if (status == '3') {
							$("#status").text("已删除");
							$("div.detail-title").removeClass().addClass(
							"detail-title orange");
						} else if (status == '4') {
							$("#status").text("已停止");
							$("div.detail-title").removeClass().addClass(
							"detail-title orange");;
						} else if (status == '6') {
							$("#status").text("已暂停");
							$("div.detail-title").removeClass().addClass(
							"detail-title orange");
						} else if (status == '9') {
							$("#status").text("操作失败");
							$("div.detail-title").removeClass().addClass(
							"detail-title red");
						} else if (status == '14') {
							$("#status").text("发送失败");
							$("div.detail-title").removeClass().addClass(
							"detail-title red");
						} else if (status == '15') {
							$("#status").text("状态异常");
							$("div.detail-title").removeClass().addClass(
							"detail-title red");
						} else if (status == '16') {
							$("#status").text("处理中");
							$("div.detail-title").removeClass().addClass(
							"detail-title blue");
							
						/*	document.getElementById("timers").style.display="block";*/
							intValue+=10;
							var progress = document.getElementById('progress');
							var text = document.getElementById('progressText');
							progress.value = intValue;
							text.innerHTML = "正在安装" + intValue + "%";
								progress.value = intValue;
								if (intValue >= 90) {
									intValue=90;
									progress.value=90;
								}
								text.innerHTML = "正在安装" + intValue + "%";
							
							
							
						}else if (status == '24') {
							$("#status").text("快照恢复中");
							$("div.detail-title").removeClass().addClass(
							"detail-title blue");
						}
						operate(status);
						currentStatus = status;
					}
					t = setTimeout(queryStatusFromResPool, 10000);
				},
				error : function() {
				}
			});
}

function goToVMListPage() {
	window.location.href = 'vmQueryListAction.action';
}
function goToApplyPage() {
	window.location.href = 'vmApplyAction.action';
}
// 修改虚拟机配置信息
function goToUpdatePage() {
	if(currentStatus == '4'||true){
		$.dialog({
			title : '修改云主机',
			content : document.getElementById('relate_resource_div'),
			init : function() {
				// searchNet();
			},
			button : [ {
				name : '确定',
				callback : function() {
					if (!checkValue()) {
						return false;
					}
					this.button({
						name : '确定',
						disabled : true
					});
					var resPoolId = $.trim($("#resPoolId").val());
					var resPoolPartId = $.trim($("#resPoolPartId").val());
					var vmId = $.trim($("#vmId").val());
					var vmName = $.trim($("#vmName").val()); // 修改前
					var cpuNum = $.trim($("#cpuNum").val()); // 修改前
					var ramSize = $.trim($("#ramSize").val()); // 修改前
					var discSize = $.trim($("#discSize").val()); // 修改前

					var vmNameNew = $.trim($("#vmNameNew").val()); // 修改后
					var cpuNumNew = $.trim($("#cpuNumNew").val()); // 修改后
					var ramSizeNew = $.trim($("#ramSizeNew").val()); // 修改后
					var ramSizeUnit = $.trim($("#ramSizeUnit").val());
					var discSizeNew = $.trim($("#discSizeNew").val()); // 修改后

					// 如果选择内存单位为GB，需要换算成MB入库
					if (ramSizeUnit == 'GB') {
						ramSizeNew = ramSizeNew * 1024;
						ramSize=ramSize* 1024;
					}

					// 将变更的网卡更新到list
					$("#ethList tr.modified").each(
							function() {
								// 将修改后的信息与修改前的对比
								var trNext = $(this).next();
								// 如果IP或者网关有不同，就证明已修改
								if ($(trNext.children('td')[3]).html() != $(
										$(this).children()[3]).find('select').val()
										|| $(trNext.children('td')[4]).html() != $(
												$(this).children()[4]).children(
												'input').val()) {

									var eth = $(trNext.children('td')[0]).html();
									var vlan = $($(this).children()[1]).find(
											'select').val();
									var ipSegment = $($(this).children()[2]).find(
											"select").val();
									var ip = $($(this).children()[3])
											.find("select").val();
									var gateway = $($(this).children()[4])
											.children('input').val();

									netListNew.push({
										'eth' : eth,
										'vlanId' : vlan,
										'ipSegmentId' : ipSegment,
										'ip' : ip,
										'gateway' : gateway
									});

								}
							});

					// 将新增的数据更新到list
					$("#ethList tr.added").each(
							function() {
								var vlan = $($(this).children()[1]).find('select')
										.val();
								var ipSegment = $($(this).children()[2]).find(
										'select').val();
								var ip = $($(this).children()[3]).find('select')
										.val();
								var gateway = $($(this).children()[4]).children(
										'input').val();

								netListNew.push({
									'eth' : '',
									'vlanId' : vlan,
									'ipSegmentId' : ipSegment,
									'ip' : ip,
									'gateway' : gateway
								});

							});

					var da_val = {
						resPoolId : resPoolId,
						resPoolPartId : resPoolPartId,
						vmId : vmId,
						vmName : vmName,
						cpuNum : cpuNum,
						ramSize : ramSize,
						discSize : discSize,
						vmNameNew : vmNameNew,
						cpuNumNew : cpuNumNew,
						ramSizeNew : ramSizeNew,
						discSizeNew : discSizeNew,
						netListChanged : JSON.stringify(netListNew)
					};
					var urls = "vmUpdateAction.action";
					var flage = true;
					$.ajax({
						type : "POST",
						url : urls,
						data : da_val,
						dataType : "JSON",
						cache : false,
						async : false,
						success : function(data) {
							if (data.mes != null) {
								if (data.result == 'success') {
									backList(data.mes, '', vmId);
									flage = false;
								} else if (data.result == 'nopermission') {
									$.compMsg({
										type : 'error',
										msg : data.mes
									});
								} else if (data.result == 'error') {
									$.compMsg({
										type : 'error',
										msg : data.mes
									});
								}
							}
						}
					});
					return !flage;
				},
				focus : true
			} ],
			cancelVal : '取消',
			cancel : true,
			lock : true
		});
	}else{
		$.compMsg({
			type : 'warn',
			msg : '只有关机的云主机才能修改！'
		});
	}
}
// 刷新界面
function backList(msg, errMsg, vmId) {
	$.backListMsg({
		msg : msg,
		errMsg : errMsg,
		url : 'vmDetailAction.action?vmId=' + vmId
	});
}

// 验证网卡
function checkValue() {
	var flag = true;

	// 校验新增数据各字段是否为空
	var s = $("table select");
	for (var i = 0; i < s.length; i++) {
		if (s[i].value == null || s[i].value == "") {
			$.compMsg({
				type : 'error',
				msg : "网卡内容有空值，请输入！"
			});
			flag = false;
			return flag;
		}
	}

	// 校验ip是否重复
	var ips = $("select[name$='ip']");
	var arr = new Array();
	for (var i = 0; i < ips.length; i++) {
		arr[i] = ips[i].value;
	}
	var nary = arr.sort();
	for (var i = 0; i < nary.length - 1; i++) {
		if (nary[i] == nary[i + 1]) {
			$.compMsg({
				type : 'error',
				msg : "网卡的重复IP为：" + nary[i]
			});
			flag = false;
			return flag;
		}
	}

	// 校验网关是否为空
	var s = $("input[name$='gateway']");
	for (var i = 0; i < s.length; i++) {
		if (s[i].value == null || s[i].value == "") {
			$.compMsg({
				type : 'error',
				msg : "网关不可空，请输入！"
			});
			flag = false;
			return flag;
		} else {
			if (!checkIP(s[i].value)) {
				$.compMsg({
					type : 'error',
					msg : "网关格式不正确，请修正！"
				});
				flag = false;
				return flag;
			}
		}
	}

	return flag;
}

// 判断ip地址的合法性
function checkIP(value) {
	var exp = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
	var reg = value.match(exp);
	if (reg == null) {
		$.compMsg({
			type : 'error',
			msg : "网关地址: " + value + " 不合法"
		});
		return false;
	}
	return true;
}

function checkGateway(gateway) {
	var selectVal = $(gateway).attr('id');
	var gatewayId = $("#" + selectVal).val();
	var ipsegmentId = $("#" + selectVal.replace("gateway", "ipsegmentSelect"))
			.val();
	var ip = $("#" + selectVal.replace("gateway", "ipSelect")).val();

	var da_val = {
		gatewayId : gatewayId,
		ipsegmentId : ipsegmentId,
		ip : ip
	};
	$.ajax({
		url : 'vmCheckGatewayAction.action',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {
			if (data.resultRoute == "error") {
				checkGatewayOk = false;
				$.compMsg({
					type : 'error',
					msg : data.errMsg
				});
				return;
			}
			if (data.resultRoute == "failure") {
				checkGatewayOk = false;
				$.compMsg({
					type : 'error',
					msg : "验证网关段信息异常"
				});
				return;
			}
			checkGatewayOk = true;
		}
	});
}

// 添加网卡
function addNet() {
	var str = '<tr class="added">';
	str += '<td></td>';
	str += '<td><div class="content"><select class="select-max" id="vlanSelect'
			+ rols
			+ '" name="vlanId" onchange="changeVlan(this)"></select></div></td>';
	str += '<td><select class="select-max" id="ipsegmentSelect'
			+ rols
			+ '" name="ipSegmentId" onchange="changeIpsegment(this)"></select></td>';
	str += '<td><select class="select-max" id="ipSelect' + rols
			+ '" name="ip"></select></td>';
	str += '<td><input type="text" size="18" id="gateway' + rols
			+ '" name="gateway" onblur="checkGateway(this)"/></td>';
	str += '<td  class="NoNewline" onclick="deleteNet(this)"><span class="product-list-btn"><a class="text" href="#">删除</a></span></td>';
	str += '</tr>';
	$("#relate_resource_div").find("#ethList").append(str);

	// 获取vlan列表
	getVlans();
}

function getVlans(vlanId) {
	var appId = $("#appId").val();
	var respoolId = document.getElementById("respool").value;
	// 查询虚拟机业务对应的VLAN列表
	var da_val = {
		appId : appId,
		respoolId : respoolId
	};

	$
			.ajax({
				type : "POST",
				url : 'vlanQueryJson.action',
				data : da_val,
				dataType : "JSON",
				cache : false,
				success : function(data) {
					var resourcePoolId = $('#resPoolId').val();
					var resourcePoolPartId = $('#resPoolPartId').val();
					if (data.resultRoute == "error") {
						window.location.href = 'exceptionIntercepor.action';
						return;
					}
					if (data.resultRoute == "failure") {
						$.compMsg({
							type : 'error',
							msg : "展示资源池信息异常，将跳转到浏览页面",
							callback:function(){
								window.location.href = 'vmQueryListAction.action?optState=1';
							}
						});
						return;
					}

					var selectVal;
					// 修改时不修改行数，所以不需要列参数
					if (vlanId == null || vlanId == '') {
						selectVal = "#vlanSelect" + rols;
					} else {
						selectVal = "#vlanSelect";
					}

					$(selectVal).empty();
					for (var i = 0; i < data.vlanList.length; i++) {
						if (data.vlanList[i].resPoolId == resourcePoolId) {
							if (i == 0) {
								$(selectVal).append(
										'<option value=""></option>');
							}
							// 添加时查询所有vlan,修改时还需要默认选择修改前数据
							if (vlanId != null && vlanId != ''
									&& vlanId == data.vlanList[i].vlanId) {
								$(selectVal).append(
										'<option value="'
												+ data.vlanList[i].vlanId
												+ '" selected="selected">'
												+ data.vlanList[i].vlanName
												+ '</option>');
							} else {
								$(selectVal).append(
										'<option value="'
												+ data.vlanList[i].vlanId
												+ '">'
												+ data.vlanList[i].vlanName
												+ '</option>');
							}
						}
					}
					// 添加时增加行数，修改不变
					if (vlanId == null || vlanId == '') {
						rolsCount++;
						rols++;
					}
				}
			});
}

// 改变vlan，ajax请求IP段
function changeVlan(vlanId, ipSegmentId) {
	var selectVal;
	// 如果是修改，那么list的ID为ipsegmentSelect
	if (ipSegmentId != null && ipSegmentId != '') {
		selectVal = "#ipsegmentSelect";
	} else {// 如果添加时，需要以下处理
		selectVal = $(vlanId).attr('id');
		selectVal = "#" + selectVal.replace("vlanSelect", "ipsegmentSelect");
		vlanId = vlanId.value;
	}

	var appId = $("#appId").val();
	if (!("" == vlanId)) {
		var da_val = {
			vlanId : vlanId,
			appId : appId
		};
		$
				.ajax({
					url : 'vmOnloadIpsegmentAction.action',
					type : 'POST',
					data : da_val,
					cache : false,
					dataType : 'json',
					success : function(data) {
						var resourcePoolId = $('#resPoolId').val();
						var resourcePoolPartId = $('#resPoolPartId').val();
						if (data.resultRoute == "error") {
							window.location.href = 'exceptionIntercepor.action';
							return;
						}
						if (data.resultRoute == "failure") {
							$.compMsg({
								type : 'error',
								msg : "展示IP段信息异常，将跳转到浏览页面",
								callback:function(){
									window.location.href = 'vmQueryListAction.action?optState=1';
								}
							});
						
							return;
						}

						$(selectVal).empty();
						for (var i = 0; i < data.ipSegmentList.length; i++) {
							if (data.ipSegmentList[i].resPoolId == resourcePoolId) {
								if (i == 0) {
									$(selectVal).append(
											'<option value=""></option>');
								}

								// 添加时查询所有IP段,修改时还需要默认选择修改前数据
								if (ipSegmentId != null
										&& ipSegmentId != ''
										&& ipSegmentId == data.ipSegmentList[i].ipSegmentId) {
									$(selectVal)
											.append(
													'<option value="'
															+ data.ipSegmentList[i].ipSegmentId
															+ '" selected="selected">'
															+ data.ipSegmentList[i].ipSegment
															+ '</option>');
								} else {
									$(selectVal)
											.append(
													'<option value="'
															+ data.ipSegmentList[i].ipSegmentId
															+ '">'
															+ data.ipSegmentList[i].ipSegment
															+ '</option>');
								}
							}
						}
					}
				});
	}
}

// 改变IP段，ajax请求IP
function changeIpsegment(ipSegmentId, vlanId, ip) {

	var selectVal;
	// 如果是修改，那么list的ID为ipSelect
	if (ip != null && ip != '') {
		selectVal = "#ipSelect";
	} else {// 如果添加时，需要以下处理
		var selectValTemp = $(ipSegmentId).attr('id');
		selectValTemp = "#"
				+ selectValTemp.replace("ipsegmentSelect", "ipSelect");
		selectVal = selectValTemp;
		var vlanselectVal = selectValTemp.replace("ipSelect", "vlanSelect");
		vlanId = $(vlanselectVal).val();
		ipSegmentId = ipSegmentId.value;
	}

	if (!("" == ipSegmentId) && !("" == vlanId)) {
		var da_val = {
			ipSegmentId : ipSegmentId,
			logicVlanId : vlanId
		};
		$
				.ajax({
					url : 'vmOnloadPrivateIpAction.action',
					type : 'POST',
					data : da_val,
					cache : false,
					dataType : 'json',
					success : function(data) {
						if (data.resultRoute == "error") {
							window.location.href = 'exceptionIntercepor.action';
							return;
						}
						if (data.resultRoute == "failure") {
							$.compMsg({
								type : 'error',
								msg : "展示私网ip信息异常，将跳转到浏览页面",
								callback:function(){
									window.location.href = 'vmQueryListAction.action?optState=1';
								}
							});
							return;
						}
						$(selectVal).empty();
						for (var i = 0; i < data.ipInfos.length; i++) {
							if (i == 0) {
								$(selectVal).append(
										'<option value=""></option>');
								// 添加时查询所有未使用的IP,修改时还需要默认选择修改前数据
								if (ip != null && ip != '') {
									$(selectVal).append(
											'<option value="' + ip
													+ '" selected="selected">'
													+ ip + '</option>');
								}
							}

							$(selectVal).append(
									'<option value="' + data.ipInfos[i].ip
											+ '">' + data.ipInfos[i].ip
											+ '</option>');

						}
					}
				});
	}
}

function editNet(eth) {
	// 记录待修改数据各字段值
	var tr = $('#' + eth).parent('tr');
	var children = tr.children('td');

	var vlanId = $(children[1]).find("#vlanId").val();
	var ipSegmentId = $(children[2]).find("#ipSegmentId").val();
	var ip = $(children[3]).html();
	var gateway = $(children[4]).html();

	// 根据修改前的值查询vlan,IP段和ip列表
	getVlans(vlanId);
	changeVlan(vlanId, ipSegmentId);
	changeIpsegment(ipSegmentId, vlanId, ip);

	// 修改时先在原来数据基础上添加一条，然后隐藏原数据
	var str = '<tr class="modified">';
	str += '<td>' + eth + '</td>';
	str += '<td><div class="content"><select class="select-max" id="vlanSelect" name="vlanId" onchange="changeVlan(this)"></select></div></td>';
	str += '<td><select class="select-max" id="ipsegmentSelect" name="ipSegmentId" onchange="changeIpsegment(this)"></select></td>';
	str += '<td><select class="select-max" id="ipSelect" name="ip"></select></td>';
	str += '<td><input type="text" size="18" id="gateway" name="gateway" value="'
			+ gateway + '" onblur="checkGateway(this)"/></td>';
	str += '<td><span class="product-list-btn"><a class="text" href="#">修改</a></span></td>';
	str += '</tr><tr class="none"></tr>';
	tr.before(str);

	tr.css("display", "none");
}

// 删除网卡
function deleteNet(obj) {
	if (rolsCount == 0) {
		return;
	}
	rolsCount--;
	$(obj).parent().remove();
}

// 获取备份列表
function getVmBakList() {
	var vmId = $('#vmId').attr("value");
	// 通过虚拟机ID获取备份任务ID
	var da_val = {
		'vmId' : vmId
	};
	$.ajax({
		url : 'vmBakQueryJsonAction.action',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		async : false,
		success : function(data) {
			if (data.result == "error") {
				window.location.href = 'exceptionIntercepor.action';
			} else {
				vmBakId = data.vmBakId;
			}
		}
	});
	if (vmBakId != null && vmBakId != "") {
		var ajaxActionStr = '/queryVmBakListByAjax.action' + '?vmBakId='
				+ vmBakId;
		getListData(ajaxActionStr);
	}
}

/**
 * 获取虚拟机备份数据
 */
function searchVmBak() {
	var startTime = $('#startTime').val();
	var endTime = $('#endTime').val();
	if (vmBakId != null && vmBakId != "") {
		var ajaxActionStr = '/queryVmBakListByAjax.action' + '?vmBakId='
				+ vmBakId + '&startTime=' + startTime + '&endTime=' + endTime;
		getListData(ajaxActionStr);
	}
}

/**
 * 获取虚拟机备份数据
 */
function getListData(actionStr) {
	var url = basePath + actionStr;

	$.ajax({
		type : "GET",
		url : url,
		data : {},
		success : function(data) {
			$('#listTbody').empty();

			setTbody(data);

			$('#pageBarDiv').html(data.pageBar);
		},
		error : function(data) {
			var mes = fieldErrors(data.fieldErrors);
			if (mes != '') {
				$.compMsg({
					type : 'error',
					msg : mes
				});
			}
			if (data.resultMessage != null) {
				if (data.resultFlage == 'success') {
					backList(data.resultMessage, '');
					flage = false;
				} else if (data.resultFlage == 'failure') {
					$.compMsg({
						type : 'error',
						msg : data.resultMessage
					});
				} else if (data.resultFlage == 'error') {
					$.compMsg({
						type : 'error',
						msg : data.resultMessage
					});
				}
			}
		},
		dataType : "json"
	});
}

/**
 * 处理ajax传来的数据
 */
function setTbody(data) {
	$.each(data.backupList, function(index) {
		$('<tr>').appendTo('#listTbody').append(
				'<td>' + this.vmBakInternalId + '</td> ').append(
				'<td>' + this.storeSize + '</td>').append(
				'<td>' + this.generationTime + '</td> ').append(
				'<td class="table-opt-block"><a href="#" onclick="restoreVmBak(\''
						+ this.vmBakInternalId
						+ '\');">恢复</a><a href="#" onclick="delVmBak(\''
						+ this.vmBakInternalId + '\');">删除</a></td>');
	});

	while ($('#listTbody' + ' tr').length < 10) {
		$('#listTbody').append('<tr>').append('<td>&nbsp;</td>').append(
				'<td>&nbsp;</td>').append('<td>&nbsp;</td>').append(
				'<td>&nbsp;</td>').append('</tr>');
	}
}

/**
 * 列表分页点击事件，如果页面上有多个pageBar，用参数pageBarId来区分是点击的哪个pageBar,该页面只有一个分页用不到pageBarId参数
 */
function getPageData(url, pageBarId) {
	getListData(url);
}

/**
 * 恢复
 */
function restoreVmBak(vmBakInternalId) {
	$.dialog({
		title : '恢复',
		content : '恢复后将直接覆盖到云主机上，请您确认是否恢复该云主机备份？',
		ok : function() {
			$.dialog({
				title : false,
				clouse : false,
				width : 200,
				height : 40,
				lock : true,
				content : '请稍后，正在恢复中...',
				init : function() {
					var tmpDialog = this;
					var da_val = {
						vmBakId : vmBakId,
						vmBakInternalId : vmBakInternalId
					};
					$
							.ajax({
								type : "POST",
								url : 'vmBakRestoreJsonAction.action',
								dataType : "JSON",
								data : da_val,
								cache : false,
								success : function(data) {
									if (data.result == 'success') {
										backList('恢复成功!', '', $('#vmId').attr(
												"value"));
										tmpDialog.close();
									} else {
										$.compMsg({
											type : 'error',
											msg : '恢复失败!'
										});
										tmpDialog.close();
									}
								},
								error : function() {
									$.compMsg({
										type : 'error',
										msg : '恢复失败!'
									});
									tmpDialog.close();
								}
							});// 恢复结束
				}
			});
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}

/**
 * 删除
 */
function delVmBak(vmBakInternalId) {
	$.dialog({
		title : '删除',
		content : '请您确认是否删除该云主机备份？',
		ok : function() {
			$.dialog({
				title : false,
				clouse : false,
				width : 200,
				height : 40,
				lock : true,
				content : '请稍后，正在删除中...',
				init : function() {
					var tmpDialog = this;
					var da_val = {
						vmBakId : vmBakId,
						vmBakInternalId : vmBakInternalId
					};
					$
							.ajax({
								type : "POST",
								url : 'vmBakDelJsonAction.action',
								dataType : "JSON",
								data : da_val,
								cache : false,
								success : function(data) {
									if (data.result == "success") {
										backList('删除成功!', '', $('#vmId').attr(
												"value"));
										tmpDialog.close();
									} else {
										$.compMsg({
											type : 'error',
											msg : '删除失败!'
										});
										tmpDialog.close();
									}
								},
								error : function() {
									$.compMsg({
										type : 'error',
										msg : '删除失败!'
									});
									tmpDialog.close();
								}
							});// 删除结束
				}
			});
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}

function backupHost() {
	var vmId = $('#vmId').attr("value");
	var da_val = {
		vmId : vmId
	};

	$.ajax({
		url : 'vmBakApplyActionByAjax.action',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		async : false,
		success : function(data) {
			if (data.resultFlag == "failure") {
				$.compMsg({
					type : 'error',
					msg : data.errorMsg
				});
			} else if (data.resultFlag == "success") {
				window.location.href = 'vmBakApplyAction.action?vmId=' + vmId;
			} else {
				$.compMsg({
					type : 'error',
					msg : '创建云主机备份任务失败!'
				});
			}
		}
	});
};

// 验证快照名字
function validateSnapshot() {
	var snapshot_name = $('#snapshot_name').attr("snapshot_name");
	var rexName = /[a-zA-Z_]/;
	if(null == snapshot_name || snapshot_name==""){
		$.compMsg( {
			type : 'error',
			msg : "快照名字不能为空，请重新输入！"
		});
		return false;
	}/*
		 * else if(!rexName.test(snapshot_name)){ $.compMsg( { type : 'error',
		 * msg : "快照名字只能由字母组成，请重新输入！" }); return false; }
		 */ else {
		return true;
	}
}

// 获取快照列表
function getSnapshotList() {
	var vmId = $('#vmId').attr("value");
	var resourcePoolId = $('#resPoolId').attr("value");
	var resourcePoolPartId = $('#resPoolPartId').attr("value");
	// 通过云主机ID获取快照
	var da_val = {
		'vmId' : vmId,
		'resourcePoolId' : resourcePoolId,
		'resourcePoolPartId' : resourcePoolPartId
	};
	$.ajax({
		url : 'snapshotQueryAction.action',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		async : false,
		success : function(ret) {
			setTspan(ret);
		},error:function(XMLHttpRequest, textStatus, errorThrown) {
			 alert(XMLHttpRequest.status);
			 alert(XMLHttpRequest.readyState);
			 alert(textStatus);
			   }
	});
}
/**
 * 处理ajax传来的数据
 */
function setTspan(data) {
	if(data.length>0){
	
		$.each(data, function(index) {
			if("已删除"!=this.snapshot_state){
			$('<tr>').appendTo('#listTspan').append(
				'<td hidden="hidden">' + this.snapshot_id + '</td> ').append(
				'<td>' + this.snapshot_name + '</td> ').append(
				'<td>' + this.snapshot_time + '</td>').append(
				'<td>' + this.snapshot_state + '</td> ').append(
				'<td>' + this.snapshot_desc + '</td>').append(
				'<td class="table-opt-block"><a href="javascript:;" class="btn" onclick="renewSnapshot(\''
						+ this.snapshot_id
						+ '\');">恢复</a><a href="javascript:;"  class="btn" onclick="delSnapshot(\''
						+ this.snapshot_id + '\');">删除</a></td>');
			}
		
		});
		
	}else{
		$('#listTspan').append("<tr><td colspan='5'>暂时没有快照信息!</td></tr>");
	}
		/*
		 * if(this.snapshot_state!='6'){ while ($('#listTspan' + ' tr').length <
		 * data.length) { $('#listTspan').append('<tr>').append('<td>&nbsp;</td>').append( '<td hidden="hidden">&nbsp;</td>').append('<td>&nbsp;</td>')
		 * .append('<td>&nbsp;</td>').append('<td>&nbsp;</td>').append( '<td>&nbsp;</td>').append('</tr>'); } }
		 */
}
// 去掉右边的空白
function trimRight(s){  
    if(s == null) return "";  
    var whitespace = new String(" \t\n\r");  
    var str = new String(s);  
    if (whitespace.indexOf(str.charAt(str.length-1)) != -1){  
        var i = str.length - 1;  
        while (i >= 0 && whitespace.indexOf(str.charAt(i)) != -1){  
           i--;  
        }  
        str = str.substring(0, i+1);  
    }  
    return str;  
}         

// 创建快照
function snapHost() {
	$.dialog({
		title : '创建云主机快照',
		content : document.getElementById('spanCreate'),
		ok : function() {
			/* clearTimeout(time); */
			var tmpDialog = this;
			var vmId = $('#vmId').attr("value");
			var snapshot_name = $('#snapshot_name').val();
			var snapshot_desc = $('#snapshot_desc').val();
			var resourcePoolId = $('#resPoolId').attr("value");
			var resourcePoolPartId = $('#resPoolPartId').attr("value");
			var da_val = {
				'vmId' : vmId,
				'snapshot_name' : snapshot_name,
				'snapshot_desc' : trimRight(snapshot_desc),
				'resourcePoolId' : resourcePoolId,
				'resourcePoolPartId' : resourcePoolPartId
			};
			/*
			 * if (!validateSnapshot()) { return false; }
			 */
			$.ajax({
				url : 'snapshotCreateAction.action',
				type : 'POST',
				data : da_val,
				cache : false,
				dataType : 'json',
				beforeSend : function(){
					$.blockUI({ 
						message:  '<img src="../images/loading.gif" align="absmiddle" style="margin-right:20px">'+'<span id="load_span">云主机快照创建中，请稍后...</span>',
						css: { width: '250px' , left : '45%' }
					}); 
				},
				success : function(data) {
					$.unblockUI();
					if (data == "error") {
						$.compMsg({
							type : 'error',
							msg : data.errorMsg
						});
						// tmpDialog.close();
					} else if (data == "success") {
						$.compMsg({
						type : 'success',
						msg : '创建云主机快照任务成功!'
						});
						// window.location.reload();
						tmpDialog.close();
						location.reload();
					} else if (data == "failure") {
						$.compMsg({
							type : 'error',
							msg : '创建云主机快照名称重复!'
							});
						}else {
						$.compMsg({
							type : 'error',
							msg : '创建云主机快照任务失败!',
						});
						return 
						// tmpDialog.close();
					}
				
				}
			});

		},
		
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}


// 快照删除
function delSnapshot(snapshot_id) {
	$.dialog({
		title : '删除',
		content : '请您确认是否删除该快照？',
		ok : function() {
			$.dialog({
				title : false,
				clouse : false,
				width : 200,
				height : 40,
				lock : true,
				content : '请稍后，正在删除中...',
				init : function() {
					var tmpDialog = this;
					var vmId = $('#vmId').attr("value");
					var resourcePoolId = $('#resPoolId').attr("value");
					var resourcePoolPartId = $('#resPoolPartId').attr("value");
					var da_val = {
							'vmId' : vmId,
							'snapshot_id' : snapshot_id,
							'resourcePoolId' : resourcePoolId,
							'resourcePoolPartId' : resourcePoolPartId
					};
					$
							.ajax({
								type : "POST",
								url : 'snapshotDeleteAction.action',
								dataType : "JSON",
								data : da_val,
								cache : false,
								success : function(data) {
									if (data == 'success') {
										backList('删除成功!', '', $('#vmId').attr(
												"value"));
										tmpDialog.close();
									} else if (data == 'failure'){
										$.compMsg({
											type : 'error',
											msg : '删除失败!'
										});
										tmpDialog.close();
									} else {
										$.compMsg({
											type : 'error',
											msg : '删除失败!'
										});
										tmpDialog.close();
									}
								},
								error : function() {
									$.compMsg({
										type : 'error',
										msg : '删除失败!'
									});
									tmpDialog.close();
								}
							});// 删除结束
				}
			});
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}



// 快照恢复
function renewSnapshot(snapshot_id) {
	$.dialog({
		title : '恢复快照',
		content : '请您确认是否恢复到当前快照？恢复后当前信息将被覆盖！',
		ok : function() {
			$.dialog({
				title : false,
				clouse : false,
				width : 200,
				height : 40,
				lock : true,
				content : '请稍后，正在恢复中...',
				init : function() {
					var tmpDialog = this;
					var vmId = $('#vmId').attr("value");
					var resourcePoolId = $('#resPoolId').attr("value");
					var resourcePoolPartId = $('#resPoolPartId').attr("value");
					var da_val = {
							'vmId' : vmId,
							'snapshot_id' : snapshot_id,
							'resourcePoolId' : resourcePoolId,
							'resourcePoolPartId' : resourcePoolPartId
					};
					$
							.ajax({
								type : "POST",
								url : 'snapshotRecoveryAction.action',
								dataType : "JSON",
								data : da_val,
								cache : false,
								success : function(data) {
									if (data == 'success') {
										backList('恢复成功!', '', $('#vmId').attr(
												"value"));
										tmpDialog.close();
									} else if (data == 'failure'){
										$.compMsg({
											type : 'error',
											msg : '恢复失败!'
										});
										tmpDialog.close();
									} else {
										$.compMsg({
											type : 'error',
											msg : '恢复失败!'
										});
										tmpDialog.close();
									}
								},
								error : function() {
									$.compMsg({
										type : 'error',
										msg : '恢复失败!'
									});
									tmpDialog.close();
								}
							});// 恢复结束
				}
			});
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}

function s(e,a){
	if ( e && e.preventDefault )
		e.preventDefault();
	else window.event.returnValue=false;
	a.focus();
}