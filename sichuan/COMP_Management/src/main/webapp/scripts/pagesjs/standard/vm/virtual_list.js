$( function() {
	$(".left-menu li:contains('资源规格管理')").addClass("selected").next().show();
	$(".left-menu dl a:contains('虚拟机 ')").parent().addClass("selected");
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
				url : 'VMStandardDetail.action',
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
						$("#vm_cpuNum").val(data.standardInfo.cpuNum);
						$("#vm_ram").attr('value', data.standardInfo.ramSize);
						$("#vm_size").val(data.standardInfo.discSize);
						$("#vm_remark").val(data.standardInfo.description);
						$
								.dialog( {
									title : '虚拟机规格详情',
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
						var errorInfo = "编号为" + standardId + "的虚拟机规格已经被删除";
						backList('',errorInfo);
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
				url : 'VMStandardDetail.action',
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
						$("#vm_cpuNum").val(data.standardInfo.cpuNum);
						$("#vm_ram").attr('value', data.standardInfo.ramSize);
						$("#vm_size").val(data.standardInfo.discSize);
						$("#vm_remark").val(data.standardInfo.description);
						$
								.dialog( {
									title : '修改虚拟机规格',
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
											var cpuNum = $("#vm_cpuNum").val();
											var ram = $("#vm_ram").val();
											var discSize = $("#vm_size").val();
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
											if (cpuNum == ""
													|| !rexNum.test(cpuNum)) {
												$
														.compMsg( {
															type : 'error',
															msg : 'CPU数量不能为空并且只能填写数字，请重新输入!'
														});
												this.button( {
													name : '修改',
													disabled : false
												});
												return false;
											}
											if (ram == "" || !rexNum.test(ram)) {
												$
														.compMsg( {
															type : 'error',
															msg : '内存容量不能为空并且只能填写数字，请重新输入!'
														});
												this.button( {
													name : '修改',
													disabled : false
												});
												return false;
											}
											if (discSize == ""
													|| !rexNum.test(discSize)) {
												$
														.compMsg( {
															type : 'error',
															msg : '磁盘空间不能为空并且只能填写数字，请重新输入!'
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
												cpuNum : cpuNum,
												ram : ram,
												discSize : discSize,
												description : description
											};
											var isreturn = false;
											$
													.ajax( {
														type : "POST",
														url : 'VMStandardModify.action',
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
																		.isEmptyObject(data.fieldErrors.cpuNum)) {
																	$
																			.compMsg( {
																				type : 'error',
																				msg : data.fieldErrors.cpuNum
																			});
																	isreturn = true;
																	return;
																}
																if (!jQuery
																		.isEmptyObject(data.fieldErrors.ram)) {
																	$
																			.compMsg( {
																				type : 'error',
																				msg : data.fieldErrors.ram
																			});
																	isreturn = true;
																	return;
																}
																if (!jQuery
																		.isEmptyObject(data.fieldErrors.discSize)) {
																	$
																			.compMsg( {
																				type : 'error',
																				msg : data.fieldErrors.discSize
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
																		+ "虚拟机规格修改失败";
																backList('',errorInfo);
																return;
															}
															if (data.resultPath == "inuse") {
																var errorInfo = "编号为"
																		+ standardId
																		+ "虚拟机规格正在使用，不允许修改";
																backList('',errorInfo);
																return;
															}
															if (data.resultPath == "intermediate") {
																var errorInfo = "编号为"
																		+ standardId
																		+ "虚拟机规格状态【发送同步成功】,无法修改.";
																backList('',errorInfo);
																return;
															}
															if (data.resultPath == "deleted") {
																var errorInfo = "编号为"
																		+ standardId
																		+ "的虚拟机规格已经被删除";
																backList('',errorInfo);
																return;
															}
															if (data.resultPath == "enabled") {
																var errorInfo = "编号为"
																		+ standardId
																		+ "的虚拟机规格资源池状态为可用,无法修改";
																backList('', errorInfo);
																return;
															}
															backList('修改成功','');
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
						var errorInfo = "编号为" + standardId + "的虚拟机规格已经被删除";
						backList('',errorInfo);//
					}
				}
			});

	$
}
//调用创建虚拟机模板
function onVmAdd() {
	$
			.dialog( {
				title : '创建虚拟机规格',
				content : document.getElementById('vm_div'),
				init : function() {
					$("#vm_div :text,#vm_div select,#vm_div textarea").attr(
							"disabled", false);
					$("#vm_div_id").hide();
					$("#vm_standard_name").val("");
					$("#vm_cpuNum").val("");
					$("#vm_ram").val("");
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
						var cpuNum = $("#vm_cpuNum").val();
						var ram = $("#vm_ram").val();
						var discSize = $("#vm_size").val();
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
						if (cpuNum == "" || !rexNum.test(cpuNum)) {
							$.compMsg( {
								type : 'error',
								msg : 'CPU数量不能为空并且只能填写数字，请重新输入!'
							});
							this.button( {
								name : '创建',
								disabled : false
							});
							return false;
						}
						if (ram == "" || !rexNum.test(ram)) {
							$.compMsg( {
								type : 'error',
								msg : '内存容量不能为空并且只能填写数字，请重新输入!'
							});
							this.button( {
								name : '创建',
								disabled : false
							});
							return false;
						}
						if (discSize == "" || !rexNum.test(discSize)) {
							$.compMsg( {
								type : 'error',
								msg : '磁盘空间不能为空并且只能填写数字，请重新输入!'
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
							cpuNum : cpuNum,
							ram : ram,
							discSize : discSize,
							description : description
						};
						var isreturn = false;
						$
								.ajax( {
									type : "POST",
									url : 'VMStandardCreate.action',
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
													.isEmptyObject(data.fieldErrors.cpuNum)) {
												$
														.compMsg( {
															type : 'error',
															msg : data.fieldErrors.cpuNum
														});
												isreturn = true;
												return;
											}
											if (!jQuery
													.isEmptyObject(data.fieldErrors.ram)) {
												$.compMsg( {
													type : 'error',
													msg : data.fieldErrors.ram
												});
												isreturn = true;
												return;
											}
											if (!jQuery
													.isEmptyObject(data.fieldErrors.discSize)) {
												$
														.compMsg( {
															type : 'error',
															msg : data.fieldErrors.discSize
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
											var errorInfo = "虚拟机规格创建失败";
											backList('',errorInfo);
											return;
										}
										backList('创建成功','');
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
table += '<col style="width:12%;" />';
table += '<col style="width:24%;" />';
table += '<col style="width:8%;" />';
table += '<col style="width:18%;" />';
table += '<col style="width:10%;display: none;" />';
table += '<col style="width:38%;" />';
table += '<tr>';
table += '<th class="nl" style="width: 12%;">规格名称</th>';
table += '<th style="width: 24%;">规格ID</th>';
table += '<th style="width: 8%;">创建人</th>';
table += '<th style="width: 18%;">创建时间</th>';
table += '<th style="width: 10%;display: none;">状态</th>';
table += '<th style="width: 38%;">操作</th>';
table += '</tr>';
var query =function(){
	$("#startTime").val(_dateToStr($("#startTime").val()));
	$("#endTime").val(_dateToStr($("#endTime").val()));
    $('#vmForm').submit();
};
/*
function query() {
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
				url : 'VMStandardListQueryJson.action',
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
					if (data.vmStandardInfos.length == 0) {
						$.compMsg( {
							type : 'info',
							msg : "资源规格信息为空！"
						});
						$("#standardTable").empty();
						$("#standardTable").append(table);
						return;
					}
					if (!jQuery.isEmptyObject(data.vmStandardInfos)) {
						var str = table;
						for ( var i = 0; i < data.vmStandardInfos.length; i++) {
							str += '<tr class="iterator">';
							str += '<td>' + data.vmStandardInfos[i].standardName + '</td>';
							str += '<td>' + data.vmStandardInfos[i].standardId + '</td>';
							str += '<td>' + data.vmStandardInfos[i].createUser + '</td>';
							str += '<td>' + data.vmStandardInfos[i].createTime + '</td>';
							str += '<td style="display: none;">部分同步成功</td>';
							str += '<td class="table-opt-block"><a href="javascript:void(0);" onclick="onVmDetail(\''
									+ data.vmStandardInfos[i].standardId
									+ '\');">详情</a><a href="javascript:void(0);" onclick="onVmModify(\''
									+ data.vmStandardInfos[i].standardId
									+ '\');">修改</a><a href="javascript:void(0);" onclick="onSynchro(this,\''+data.vmStandardInfos[i].standardId+'\');return false;">同步</a><a href="javascript:void(0);" onclick="onDel(\''
									+ data.vmStandardInfos[i].standardId
									+ '\')">删除</a>';
							str += '</tr>';
						}
						$("#standardTable").empty();
						$("#standardTable").append(str);
					}
				}
			});
}*/
function onDel(standardId) {
	$.dialog({
		title: '删除虚拟机规格',
		content: '请您确认是否删除虚拟机规格'+standardId+'？删除后将无法恢复！',
		ok: function () {
		var da_val = {
				standardId : standardId
			};
			$
					.ajax( {
						type : "POST",
						url : 'VMStandardDelete.action',
						data : da_val,
						dataType : "JSON",
						cache : false,
						success : function(data) {
							if (data.resultPath == "error") {
								var errorInfo = "编号为" + standardId + "虚拟机规格删除失败";
								backList('',errorInfo);
								return;
							}
							if (data.resultPath == "inuse") {
								var errorInfo = "编号为" + standardId + "虚拟机规格正在使用，不允许删除";
								backList('',errorInfo);
								return;
							}
							if (data.resultPath == "intermediate") {
								var errorInfo = "编号为" + standardId + "虚拟机规格存在中间状态，不允许删除";
								backList('',errorInfo);
								return;
							}
							if (data.resultPath == "deleted") {
								var errorInfo = "编号为" + standardId + "的虚拟机规格已经被删除";
								backList('',errorInfo);
								return;
							}if (data.resultPath == "success") {
								var errorInfo = "编号为" + standardId + "的虚拟机规格删除成功！";
								backList(errorInfo, '');
								return;
							}
							if (data.resultPath == "enabled") {
								var errorInfo = "编号为"
										+ standardId
										+ "的虚拟机规格资源池状态为可用,无法删除";
								backList('', errorInfo);
								return;
							}
							backList('删除成功','');
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
		url : 'vmStandardResList.action',
		data : da_val,
		dataType : "JSON",
		cache : false,
		async:false,
		success: function(data){
			if (data != null&&data["resultResInfos"] != null) {
				removeMask($("#synchro_div"));
				var mapData = data["resultResInfos"];
				var table = '<table>';
				var single = true;
				for (var key in mapData) {
					
					var roolFlage = true;
					var resStatusText = '';
					for(var portKey in mapData[key]){
						if (single) {
							var syn_standardId = mapData[key][portKey][0].standardId;
							var syn_standardName = mapData[key][portKey][0].standardName;
							$("#syn_standardId").html(syn_standardId);
							$("#syn_standardName").html(syn_standardName);
							single = false;
						}
						if(roolFlage){
							roolFlage = false;
							var flage = false;
							if (mapData[key][portKey][0].resStatus == '1') {
								flage = true;
								resStatusText = '服务正常';
							}else if (mapData[key][portKey][0].resStatus == '0') {
									resStatusText = '服务已删除';
								}
								else 
									if (mapData[key][portKey][0].resStatus == '2') {
										resStatusText = '服务暂停';
									}
									else 
										if (mapData[key][portKey][0].resStatus == '3') {
											resStatusText = '服务终止';
										}
							table += '<tr><td><div class="authBlock zoone">';
							table += '<dt>';
							table += '<span class="up"></span>';
							if (flage) {
								table += '<label>' + mapData[key][portKey][0].resPoolName + '</label>';
							}
							else {
								table += '<label>' + mapData[key][portKey][0].resPoolName + '</label>';
							}
							table += '<label>' + resStatusText + '</label>';
							table += '</dt><dd>';
						}
						var portFlage = false;
						var resPortStatusText = '';
						if (mapData[key][portKey][0].resStatus == '1') {
							if (mapData[key][portKey][0].roolPartStatus == '1') {
								portFlage = true;
								resPortStatusText = '服务正常';
							}
							else 
								if (mapData[key][portKey][0].roolPartStatus == '0') {
									resPortStatusText = '服务已删除';
								}
								else 
									if (mapData[key][portKey][0].roolPartStatus == '2') {
										resPortStatusText = '服务暂停';
									}
									else 
										if (mapData[key][portKey][0].roolPartStatus == '3') {
											resPortStatusText = '服务终止';
										}
						}else{
							portFlage = false;
							resPortStatusText = resStatusText;
						}
						table += '<ul><div class="authBlock zoone">';
						table += '<dt>';
						table += '<span class="up"></span>';
						if (portFlage) {
							table += '<label>' + mapData[key][portKey][0].resPoolPartName + '</label>';
						}
						else {
							table += '<label>' + mapData[key][portKey][0].resPoolPartName + '</label>';
						}
						table += '<label>' + resPortStatusText + '</label>';
						table += '</dt><dd>';
						for (var i = 0; i < mapData[key][portKey].length; i++) {
							var osName = mapData[key][portKey][i].osName;
							var osNameText = osName;
							if (osName.length > 5) {
								osNameText = osName.substring(0, 5) + '...';
							}
							var lsFlage = false;
							var synStatus = mapData[key][portKey][i].status;
							var synStatusText = '';
							if (synStatus == '0') {
								synStatusText = '同步发送成功';
							}
							else 
								if (synStatus == '1') {
									synStatusText = '同步发送失败';
									lsFlage = true;
								}
								else 
									if (synStatus == '2') {
										synStatusText = '可用';
									}
									else 
										if (synStatus == '3') {
											synStatusText = '不可用';
											lsFlage = true;
										}else 
											if (synStatus == '5') {
												synStatusText = '等待再同步';
												lsFlage = true;
											}
										else {
											synStatusText = '未同步';
											lsFlage = true;
										}
							table += '<li>';
							var poolId = mapData[key][portKey][i].resPoolId;
							var poolPartId = mapData[key][portKey][i].resPoolPartId;
							var osId = mapData[key][portKey][i].osId;
							if (flage && lsFlage) {
								table += '<label title="' + osName + '">';
								table += '<input type="checkbox" name="poolPartId" poolId="' + poolId + '" poolPartId="' + poolPartId + '" osId="' + osId + '"/>';
								table += osNameText + '</label>';
							}
							else {
								table += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label title="'+ mapData[key][portKey][i].osName +'">' + osNameText + '</label>';
							}
							
							table += '<label>' + '镜像正常' + '</label>';
							table += '<label>' + synStatusText + '</label>';
							table += '</li>';
						}
						table += '</dd></div></ul>';
					
					}
					table += '</dd></div></td></tr>'
									
				}
				table += '</table>';
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
			var osId = $(this).attr('osId');
			if(flage){
				jsonStr = '[{"resourcePoolPart":"'+poolPartId+'","resourcePoolId":"'+poolId+'","osId":"'+osId+'"}';
				flage = false;
			}else{
				jsonStr +=',{"resourcePoolPart":"'+poolPartId+'","resourcePoolId":"'+poolId+'","osId":"'+osId+'"}';
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
		url : 'vmSynchroResTemplatePool.action',
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
			url : 'VMStandardListQuery.action'
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