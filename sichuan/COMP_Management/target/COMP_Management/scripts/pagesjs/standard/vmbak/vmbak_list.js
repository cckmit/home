$( function() {
	$(".left-menu li:contains('资源规格管理')").addClass("selected").next().show();
	$(".left-menu dl a:contains('虚拟机备份')").parent().addClass("selected");
	$("#head-menu li a:contains('产品管理')").parent().addClass("head-btn-sel");
	//自定义form样式
	$(":radio,:text,:checkbox,select,textarea").uniform();

	// 加载弹出层提示信息栏宽度
	$(".point").attr("style", "width:80px");
	$(".point").html("&nbsp;");

	var defaultOs = $("#osType").children('option:selected').val() + "Option";
	$("." + defaultOs).show();
	$("#osType").change( function() {
		var val = $(this).children('option:selected').val();
		$("#osTerms .selector").each( function() {
			if ($(this).hasClass(val + "Option")) {
				$(this).show();
			} else {
				$(this).hide();
			}
		});
	});
	$("#startTime").val(_fomart14($("#startTime").val()));
	$("#endTime").val(_fomart14($("#endTime").val()));
})
function queryVmStandardDetail(standardId) {
	var da_val = {
		standardId : standardId
	};
	$
			.ajax( {
				type : "POST",
				url : 'VMBAKStandardDetail.action',
				data : da_val,
				dataType : "JSON",
				cache : false,
				success : function(data) {
					if (data.resultPath == "error") {
						window.location.href = 'exceptionIntercepor.action';
						return;
					}

					if (!jQuery.isEmptyObject(data.standardInfo)) {
						$("#vm_standard_id").text(data.standardInfo.standardId);
						$("#vm_standard_name").val(
								data.standardInfo.standardName);
						$("#vm_size").val(data.standardInfo.spaceSize);
						$("#vm_remark").val(data.standardInfo.description);
						$
								.dialog( {
									title : '虚拟机备份规格详情',
									content : document.getElementById('vm_div'),
									init : function() {
										$(
												"#vm_div :text,#vm_div select,#vm_div textarea")
												.attr("disabled", true);
										$.uniform.update();
										$("#vm_div_id").show();
										$("[name='allOption']").show();
										$("[name='status']").show();
										$("#synchro_option").hide();
										$("#synchro_detail_option").show();
									},
									cancelVal : '关闭',
									cancel : true,
									lock : true
								});
					} else {
						var errorInfo = "编号为" + standardId + "的虚拟机备份规格已经被删除";
						backList('', errorInfo);
					}
				}
			});
}
// 调用虚拟机详情
function onVmDetail(standardId) {
	queryVmStandardDetail(standardId);
}
//调用修虚拟机模板
function onVmModify(standardId) {

	var da_val = {
		standardId : standardId
	};
	$
			.ajax( {
				type : "POST",
				url : 'VMBAKStandardDetail.action',
				data : da_val,
				dataType : "JSON",
				cache : false,
				success : function(data) {
					if (data.resultPath == "error") {
						window.location.href = 'exceptionIntercepor.action';
					}
					if (!jQuery.isEmptyObject(data.standardInfo)) {
						$("#vm_standard_id").text(data.standardInfo.standardId);
						$("#vm_standard_name").val(
								data.standardInfo.standardName);
						$("#vm_size").val(data.standardInfo.spaceSize);
						$("#vm_remark").val(data.standardInfo.description);
						$
								.dialog( {
									title : '修改虚拟机备份规格',
									content : document.getElementById('vm_div'),
									init : function() {
										$(
												"#vm_div :text,#vm_div select,#vm_div textarea")
												.attr("disabled", false);
										$.uniform.update();
										$("#vm_div_id").show();
										$("[name='allOption']").show();
										$("[name='status']").show();
										$("#synchro_option").hide();
										$("#synchro_detail_option").hide();
									},
									button : [ {
										name : '修改',
										callback : function() {
											this.button( {
												name : '修改',
												disabled : true
											});
											var standardId = $(
													"#vm_standard_id").text();
											var standardName = $(
													"#vm_standard_name").val();
											var spaceSize = $("#vm_size").val();
											var description = $("#vm_remark")
													.val();
											var rexNum = /^[0-9]*[1-9][0-9]*$/;
											if (standardName.length > 50
													|| standardName == "") {
												$.compMsg( {
													type : 'error',
													msg : '规格名称不能为空且最大长度为50!'
												});
												this.button( {
													name : '修改',
													disabled : false
												});
												return false;
											}
											if (spaceSize == ""
													|| !rexNum.test(spaceSize) || spaceSize.length >10) {
												$
														.compMsg( {
															type : 'error',
															msg : '空间大小不能为空且为小于11位的数字，请重新输入!'
														});
												this.button( {
													name : '修改',
													disabled : false
												});
												return false;
											}
											if (description.length > 100) {
												$.compMsg( {
													type : 'error',
													msg : '描述信息最大长度为100!'
												});
												this.button( {
													name : '修改',
													disabled : false
												});
												return false;
											}

											var da_val = {
												standardId : standardId,
												standardName : standardName,
												spaceSize : spaceSize,
												description : description
											};
											var isreturn = false;
											$
													.ajax( {
														type : "POST",
														url : 'VMBAKStandardModify.action',
														data : da_val,
														dataType : "JSON",
														cache : false,
														success : function(data) {
															if (!jQuery
																	.isEmptyObject(data.fieldErrors)) {
																if (!jQuery
																		.isEmptyObject(data.fieldErrors.standardName)) {
																	$
																			.compMsg( {
																				type : 'error',
																				msg : data.fieldErrors.standardName
																			});
																	isreturn = true;
																	return;
																}
																if (!jQuery
																		.isEmptyObject(data.fieldErrors.spaceSize)) {
																	$
																			.compMsg( {
																				type : 'error',
																				msg : data.fieldErrors.spaceSize
																			});
																	isreturn = true;
																	return;
																}
																if (!jQuery
																		.isEmptyObject(data.fieldErrors.description)) {
																	$
																			.compMsg( {
																				type : 'error',
																				msg : data.fieldErrors.description
																			});
																	isreturn = true;
																	return;
																}

															}
															if (data.resultPath == "error") {
																var errorInfo = "编号为"
																		+ standardId
																		+ "虚拟机备份规格修改失败";
																backList('', errorInfo);
																return;
															}
															if (data.resultPath == "inuse") {
																var errorInfo = "编号为"
																		+ standardId
																		+ "虚拟机备份规格正在使用，不允许修改";
																backList('', errorInfo);
																return;
															}

															if (data.resultPath == "intermediate") {
																var errorInfo = "编号为"
																		+ standardId
																		+ "虚拟机备份规格状态【发送同步成功】,无法修改.";
																backList('',errorInfo);
																return;
															}
															if (data.resultPath == "deleted") {
																var errorInfo = "编号为"
																		+ standardId
																		+ "的虚拟机备份规格已经被删除";
																backList('', errorInfo);
																return;
															}
															if (data.resultPath == "enabled") {
																var errorInfo = "编号为"
																		+ standardId
																		+ "的虚拟备份规格资源池状态为可用,无法修改";
																backList('', errorInfo);
																return;
															}
															
																backList('修改成功', '');
														}
													});
											this.button( {
												name : '修改',
												disabled : false
											});
											return !isreturn;
										},
										focus : true
									} ],
									cancelVal : '取消',
									cancel : true,
									lock : true
								});
					} else {
						var errorInfo = "编号为" + standardId + "的虚拟机备份规格已经被删除";
						backList('', errorInfo);
					}
				}
			});

	$
}
//调用创建虚拟机模板
function onVmAdd() {
	$
			.dialog( {
				title : '创建虚拟机备份规格',
				content : document.getElementById('vm_div'),
				init : function() {
					$("#vm_div :text,#vm_div select,#vm_div textarea").attr(
							"disabled", false);
					$("#vm_div_id").hide();
					$("#vm_standard_name").val("");
					$("#vm_size").val("");
					$("#vm_remark").val("");
					$("[name='status']").hide();
					$("[name='allOption']").hide();
					$("#synchro_option").show();
					$("#synchro_detail_option").hide();
					$.uniform.update();
				},
				button : [ {
					name : '创建',
					callback : function() {
						this.button( {
							name : '创建',
							disabled : true
						});
						var standardName = $("#vm_standard_name").val();
						var spaceSize = $("#vm_size").val();
						var description = $("#vm_remark").val();
						var rexNum = /^[0-9]*[1-9][0-9]*$/;
						if (standardName.length > 50 || standardName == "") {
							$.compMsg( {
								type : 'error',
								msg : '规格名称不能为空且最大长度为50!'
							});
							this.button( {
								name : '创建',
								disabled : false
							});
							return false;
						}
						if (spaceSize == ""
								|| !rexNum.test(spaceSize) || spaceSize.length >10) {
							$
									.compMsg( {
										type : 'error',
										msg : '空间大小不能为空且为小于11位的数字，请重新输入!'
									});
							this.button( {
								name : '创建',
								disabled : false
							});
							return false;
						}
						if (description.length > 100) {
							$.compMsg( {
								type : 'error',
								msg : '描述信息最大长度为100!'
							});
							this.button( {
								name : '创建',
								disabled : false
							});
							return false;
						}
						var da_val = {
							standardName : standardName,
							spaceSize : spaceSize,
							description : description
						};
						var isreturn = false;
						$
								.ajax( {
									type : "POST",
									url : 'VMBAKStandardCreate.action',
									data : da_val,
									dataType : "JSON",
									cache : false,
									success : function(data) {
										if (!jQuery
												.isEmptyObject(data.fieldErrors)) {
											if (!jQuery
													.isEmptyObject(data.fieldErrors.standardName)) {
												$
														.compMsg( {
															type : 'error',
															msg : data.fieldErrors.standardName
														});
												isreturn = true;
												return;
											}
											if (!jQuery
													.isEmptyObject(data.fieldErrors.spaceSize)) {
												$
														.compMsg( {
															type : 'error',
															msg : data.fieldErrors.spaceSize
														});
												isreturn = true;
												return;
											}
											if (!jQuery
													.isEmptyObject(data.fieldErrors.description)) {
												$
														.compMsg( {
															type : 'error',
															msg : data.fieldErrors.description
														});
												isreturn = true;
												return;
											}

										}
										if (data.resultPath == "error") {
											var errorInfo = "虚拟机备份规格创建失败";
											backList('', errorInfo);
											return;
										}
											backList('创建成功', '');
									}
								});
						this.button( {
							name : '创建',
							disabled : false
						});
						return isreturn;
					},
					focus : true
				} ],
				cancelVal : '取消',
				cancel : true,
				lock : true
			});
}
var table = '';
table += '<col style="width:10%;" />';
table += '<col style="width:27%;" />';
table += '<col style="width:8%;" />';
table += '<col style="width:17%;" />';
table += '<col style="width:10%;display: none;" />';
table += '<col style="width:38%;" />';
table += '<tr>';
table += '<th class="nl" style="width: 10%;">规格名称</th>';
table += '<th style="width: 27%;">规格ID</th>';
table += '<th style="width: 8%;">创建人</th>';
table += '<th style="width: 17%;">创建时间</th>';
table += '<th style="width: 10%;display: none;">状态</th>';
table += '<th style="width: 38%;">操作</th>';
table += '</tr>';

var query =function(){
	$("#startTime").val(_dateToStr($("#startTime").val()));
	$("#endTime").val(_dateToStr($("#endTime").val()));
    $('#vmbakForm').submit();
};

/*function query() {
	var standardName = $('#vmStandard').val();
	var startTime = $('#startTime').val();
	var endTime = $('#endTime').val();
	var da_val = {
		standardName : standardName,
		startTime : startTime,
		endTime : endTime
	};
	$
			.ajax( {
				type : "POST",
				url : 'VMBAKStandardListQueryJson.action',
				data : da_val,
				dataType : "JSON",
				cache : false,
				success : function(data) {
					if (!jQuery.isEmptyObject(data.fieldErrors)) {
						if (!jQuery
								.isEmptyObject(data.fieldErrors.standardName)) {
							$.compMsg( {
								type : 'error',
								msg : data.fieldErrors.standardName
							});
							return;
						}
						if (!jQuery.isEmptyObject(data.fieldErrors.startTime)) {
							$.compMsg( {
								type : 'error',
								msg : data.fieldErrors.startTime
							});
							return;
						}
						if (!jQuery.isEmptyObject(data.fieldErrors.endTime)) {
							$.compMsg( {
								type : 'error',
								msg : data.fieldErrors.endTime
							});
							return;
						}
					}
					if (data.resultPath == "error") {
						window.location.href = 'exceptionIntercepor.action';
					}
					if (data.vmbakStandardInfos.length == 0) {
						$.compMsg( {
							type : 'info',
							msg : "备份规格信息为空！"
						});
						$("#standardTable").empty();
						$("#standardTable").append(table);
						return;
					}
					if (!jQuery.isEmptyObject(data.vmbakStandardInfos)) {
						var str = table;
						for ( var i = 0; i < data.vmbakStandardInfos.length; i++) {
							str += '<tr class="iterator">';
							str += '<td>' + data.vmbakStandardInfos[i].standardName + '</td>';
							str += '<td>' + data.vmbakStandardInfos[i].standardId + '</td>';
							str += '<td>' + data.vmbakStandardInfos[i].createUser + '</td>';
							str += '<td>' + data.vmbakStandardInfos[i].createTime + '</td>';
							str += '<td style="display: none;">部分同步成功</td>';
							str += '<td class="table-opt-block"><a href="javascript:void(0);" onclick="onVmDetail(\''
									+ data.vmbakStandardInfos[i].standardId
									+ '\');">详情</a><a href="javascript:void(0);" onclick="onVmModify(\''
									+ data.vmbakStandardInfos[i].standardId
									+ '\');">修改</a><a href="javascript:void(0);" onclick="onSynchro(this,\''+data.vmbakStandardInfos[i].standardId+'\');return false;">同步</a><a href="javascript:void(0);" onclick="onDel(\''
									+ data.vmbakStandardInfos[i].standardId
									+ '\')">删除</a>';
							str += '</tr>';
						}
						$("#standardTable").empty();
						$("#standardTable").append(str);
					}
				}
			});
}
*/function onDel(standardId) {
	$.dialog({
		title: '删除虚拟机备份规格',
		content: '请您确认是否删除虚拟机备份规格'+standardId+'？删除后将无法恢复！',
		ok: function () {
		var da_val = {
				standardId : standardId
			};
			$
					.ajax( {
						type : "POST",
						url : 'VMBAKStandardDelete.action',
						data : da_val,
						dataType : "JSON",
						cache : false,
						success : function(data) {
							if (data.resultPath == "error") {
								var errorInfo = "编号为" + standardId + "虚拟机备份规格删除失败";
								backList('', errorInfo);
								return;
							}
							if (data.resultPath == "inuse") {
								var errorInfo = "编号为" + standardId + "虚拟机备份规格正在使用，不允许删除";
								backList('', errorInfo);
								return;
							}
							if (data.resultPath == "intermediate") {
								var errorInfo = "编号为" + standardId + "虚拟机备份规格存在中间状态，不允许删除";
								backList('',errorInfo);
								return;
							}
							if (data.resultPath == "deleted") {
								var errorInfo = "编号为" + standardId + "的虚拟机备份规格已经被删除";
								backList('', errorInfo);
								return;
							}if (data.resultPath == "success") {
								var errorInfo = "编号为" + standardId + "的虚拟机备份规格删除成功！";
								backList(errorInfo, '');
								return;
							}

							if (data.resultPath == "enabled") {
								var errorInfo = "编号为"
										+ standardId
										+ "的虚拟机备份规格资源池状态为可用,无法删除";
								backList('', errorInfo);
								return;
							}
							backList('删除成功', '');
						}
					});
			return true;
		},
		cancelVal: '关闭',
		cancel: true,
		lock: true
	});
	
}
//同步资源池
function onSynchro(obj,standardId){
	$.dialog({
		title: '资源池同步',
		content: document.getElementById('synchro_div'),
		init:function (){
			$("#synchro_table").html('');
			$("#syn_standardId").html('');
			$("#syn_standardName").html('');
			vmStandardResList(standardId);
		},
		button:[
        {
            name: '同步',
            callback: function () {
				//调用同步方法
				return onSynchroAjax(standardId);
            },
            focus: true
        }],
		cancelVal: '取消',
		cancel:true,
		lock: true
	});
}
//初始化列表资源池
function vmStandardResList(standardId){
	var da_val = {'standardId':standardId};
	mask($("#synchro_div"));
	$.ajax({
		type : "POST",
		url : 'VMBAKStandardResList.action',
		data : da_val,
		dataType : "JSON",
		cache : false,
		async:false,
		success: function(data){
			if (data != null) {
				removeMask($("#synchro_div"));
				var mapData = data["resultResInfos"];
				var table = '<table>';
				var single = true;
				for (var key in mapData) {
					if (single) {
						var syn_standardId = mapData[key][0].standardId;
						var syn_standardName = mapData[key][0].standardName;
						$("#syn_standardId").html(syn_standardId);
						$("#syn_standardName").html(syn_standardName);
						single = false;
					}
					var flage = false;
					var resStatusText = '';
					if (mapData[key][0].resStatus == '1') {
						flage = true;
						resStatusText = '服务正常';
					}
					else 
						if (mapData[key][0].resStatus == '0') {
							resStatusText = '服务已删除';
						}
						else 
							if (mapData[key][0].resStatus == '2') {
								resStatusText = '服务暂停';
							}
							else 
								if (mapData[key][0].resStatus == '3') {
									resStatusText = '服务终止';
								}
					table += '<tr><td><div class="authBlock zoone">';
					table += '<dt>';
					table += '<span class="up"></span>';
					if (flage) {
						table += '<label><input type="checkbox" name="host" />' + mapData[key][0].resPoolName + '</label>';
					}
					else {
						table += '<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + mapData[key][0].resPoolName + '</label>';
					}
					table += '<label>' + resStatusText + '</label>';
					table += '</dt><dd>';
					for (var i = 0; i < mapData[key].length; i++) {
						var resPoolPartName = mapData[key][i].resPoolPartName;
						var resPoolPartNameText = resPoolPartName;
						if (resPoolPartName.length > 5) {
							resPoolPartNameText = resPoolPartName.substring(0, 5) + '...';
						}
						var lsFlage = false;
						var resPoolPartStatus = mapData[key][i].status;
						var resPoolPartStatusText = '';
						if (resPoolPartStatus == '0') {
							resPoolPartStatusText = '同步发送成功';
						}
						else 
							if (resPoolPartStatus == '1') {
								resPoolPartStatusText = '同步发送失败';
								lsFlage = true;
							}
							else 
								if (resPoolPartStatus == '2') {
									resPoolPartStatusText = '可用';
								}
								else 
									if (resPoolPartStatus == '3') {
										resPoolPartStatusText = '不可用';
										lsFlage = true;
									}else if (resPoolPartStatus == '5') {
										resPoolPartStatusText = '等待再同步';
										lsFlage = true;
									}
									else {
										resPoolPartStatusText = '未同步';
										lsFlage = true;
									}
						table += '<li>';
						var poolId = mapData[key][i].resPoolId;
						var poolPartId = mapData[key][i].resPoolPartId;
						if (flage && lsFlage) {
							table += '<label title="' + resPoolPartName + '">';
							table += '<input type="checkbox" name="poolPartId" poolId="' + poolId + '" poolPartId="' + poolPartId + '"/>';
							table += resPoolPartNameText + '</label>';
						}
						else {
							table += '<label title="分区01">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + mapData[key][i].resPoolPartName + '</label>';
						}
						table += '<label>' + resStatusText + '</label>';
						table += '<label>' + resPoolPartStatusText + '</label>';
						table += '</li>';
					}
					table += '</dd></div></td></tr>'
				}
				table + '</table>';
				$("#synchro_table").html(table);
				//样式
				$(":checkbox").uniform();
				$.uniform.update();
				//层次树
				$(".authBlock .up").toggle(function(){
					$(this).addClass("down").parent().next().show();
				}, function(){
					$(this).removeClass("down").attr("class", "up").parent().next().hide();
				});
				// 二级功能
				$(".authBlock dt :checkbox").click(function(){
					var childCheckbox = $(this).parents('dt').next("dd").find('input:checkbox');
					if ($(this).attr("checked") == "checked") {
						childCheckbox.attr("checked", "checked");
						$.uniform.update();
					}
					else {
						childCheckbox.removeAttr("checked");
						$.uniform.update();
					}
				});
			}else{
				$.compMsg( {
					type: 'error',
					msg: '资源池信息加载失败！'
				});
				removeMask($("#synchro_div"));
			}
		}
	});
}
function onSynchroAjax(standardId){
	var jsonStr = '';
	var flage = true;
	$("[name='poolPartId']").each(function (){
		if($(this).attr('checked')){
			var poolPartId = $(this).attr('poolPartId');
			var poolId = $(this).attr('poolId');
			if(flage){
				jsonStr = '[{"resourcePoolPart":"'+poolPartId+'","resourcePoolId":"'+poolId+'"}';
				flage = false;
			}else{
				jsonStr +=',{"resourcePoolPart":"'+poolPartId+'","resourcePoolId":"'+poolId+'"}';
			}
		}
	});
	if(flage){
		$.compMsg( {
			type: 'error',
			msg: '请选择要同步的资源池！'
		});
		return false;
	}
	jsonStr +=']';
	var da_val = {'jsonStr':jsonStr,'standardId':standardId};
	mask($("#synchro_div"));
	$.ajax({
		type : "POST",
		url : 'VMBAKSynchroResPool.action',
		data : da_val,
		dataType : "JSON",
		cache : false,
		async:false,
		success: function(data){
			removeMask($("#synchro_div"));
			if(data.results==null){
				$.compMsg( {type : 'error',msg : '全部发送同步失败！'});
			}else{
				var ls = data.results;
				if(ls.length==0){
					$.compMsg( {type : 'success',msg : '全部发送同步成功！'});
				}else{
					for(var i=0;i<ls.length;i++){
						$.compMsg( {type : 'error',msg : '向资源池'+ls[i].resourcePool.resourcePoolId+'下的资源池分区'+ls[i].resourcePool.resourcePoolPart+'发送同步失败！'});
					}
				}
			}
		}
	});
}
//返回列表页面
function backList(msg, errMsg) {
	$.backListMsg( {
		msg : msg,
		errMsg : errMsg,
		url : 'VMBAKStandardListQuery.action'
	});
}
//遮罩loading
function mask(container){
	if ($(".mask").length == 0) {
		container.css("position","relative");
		var width = container.width()/2-10;
		var height = container.height()/2-10;
		var $mask = '<div class="mask"><img style="margin:'+height+'px '+width+'px;" src="../images/loading.gif"/></div>';
		container.prepend($mask);
	}
}
//去除遮罩loading
function removeMask(container){
	if ($(".mask").length > 0) {
		container.find(".mask").remove()
	}
}