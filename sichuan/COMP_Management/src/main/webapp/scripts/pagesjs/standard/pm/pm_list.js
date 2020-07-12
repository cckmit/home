$( function() {
	$(".left-menu li:contains('资源规格管理')").addClass("selected").next().show();
	$(".left-menu dl a:contains('物理机')").parent().addClass("selected");
	$("#head-menu li a:contains('产品管理')").parent().addClass("head-btn-sel");
	//自定义form样式
	$(":radio,:text,:checkbox,select,textarea").uniform();

	// 加载弹出层提示信息栏宽度
	$(".point").attr("style", "width:80px");
	$(".point").html("&nbsp;");
	$("#startTime").val(_fomart14($("#startTime").val()));
	$("#endTime").val(_fomart14($("#endTime").val()));
	
	initServerTypeSelect();
});
function queryPmStandardDetail(standardId) {
	var da_val = {
		standardId : standardId
	};
	$
			.ajax( {
				type : "POST",
				url : 'PMStandardDetail.action',
				data : da_val,
				dataType : "JSON",
				cache : false,
				success : function(data) {
					if (data.resultPath == "error") {
						window.location.href = 'exceptionIntercepor.action';
						return;
					}

					if (!jQuery.isEmptyObject(data.standardInfo)) {
						$("#pm_standard_id").text(data.standardInfo.standardId);
						$("#pm_standard_name").val(
								data.standardInfo.standardName);
						$("#type_input").show();
						$("#type_select").hide();
						$("#server_type_input").val(data.standardInfo.pmTypeName);
						$("#pm_cpuType").val(data.standardInfo.cpuType);
						$("#pm_ram").attr('value', data.standardInfo.ramSize);
						$("#pm_size").val(data.standardInfo.discSize);
						$("#pm_remark").val(data.standardInfo.description);
						$
								.dialog( {
									title : '物理机规格详情',
									content : document.getElementById('pm_div'),
									init : function() {
										$(
												"#pm_div :text,#pm_div select,#pm_div textarea")
												.attr("disabled", true);
										$.uniform.update();
										$("#pm_div_id").show();
									},
									cancelVal : '关闭',
									cancel : true,
									lock : true
								});
					} else {
						var errorInfo = "编号为" + standardId + "的物理机规格已经被删除";
						backList('', errorInfo);
					}
				}
			});
}
// 调用物理机详情
function onPmDetail(standardId) {
	queryPmStandardDetail(standardId);
}

/*调用修物理机模板
function onPmModify(standardId) {
	var da_val = {
		standardId : standardId
	};
	$
			.ajax( {
				type : "POST",
				url : 'PMStandardDetail.action',
				data : da_val,
				dataType : "JSON",
				cache : false,
				success : function(data) {
					if (data.resultPath == "error") {
						window.location.href = 'exceptionIntercepor.action';
					}
					if (!jQuery.isEmptyObject(data.standardInfo)) {
						$("#pm_standard_id").text(data.standardInfo.standardId);
						$("#pm_standard_name").val(
								data.standardInfo.standardName);
						$("#server_type").val(data.standardInfo.serverType);
						$("#pm_cpuNum").val(data.standardInfo.cpuNum);
						$("#pm_ram").attr('value', data.standardInfo.ramSize);
						$("#pm_size").val(data.standardInfo.discSize);
						$("#pm_remark").val(data.standardInfo.description);
						$
								.dialog( {
									title : '修改物理机规格',
									content : document.getElementById('pm_div'),
									init : function() {
										$(
												"#pm_div :text,#pm_div select,#pm_div textarea")
												.attr("disabled", false);
										$.uniform.update();
									},
									button : [ {
										name : '修改',
										callback : function() {
											this.button( {
												name : '修改',
												disabled : true
											});
											var standardId = $(
													"#pm_standard_id").text();
											var standardName = $(
													"#pm_standard_name").val();
											var serverType = $("#server_type").val();
											var cpuNum = $("#pm_cpuNum").val();
											var ram = $("#pm_ram").val();
											var discSize = $("#pm_size").val();
											var description = $("#pm_remark")
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
															msg : '硬盘空间不能为空并且只能填写数字，请重新输入!'
														});
												this.button( {
													name : '修改',
													disabled : false
												});
												return false;
											}
											if (serverType.length > 64||serverType == "") {
												$
														.compMsg( {
															type : 'error',
															msg : '物理机类型不能为空并且最大长度为64!'
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
												serverType : serverType,
												description : description
											};
											var isreturn = false;
											$
													.ajax( {
														type : "POST",
														url : 'PMStandardModify.action',
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
																		.isEmptyObject(data.fieldErrors.serverType)) {
																	$
																			.compMsg( {
																				type : 'error',
																				msg : data.fieldErrors.serverType
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
																		+ "物理机规格修改失败";
																backList('', errorInfo);
																return;
															}
															if (data.resultPath == "inuse") {
																var errorInfo = "编号为"
																		+ standardId
																		+ "物理机规格正在使用，不允许修改";
																backList('', errorInfo);
																return;
															}
															if (data.resultPath == "intermediate") {
																var errorInfo = "编号为"
																		+ standardId
																		+ "物理机规格状态【发送同步成功】,无法修改.";
																backList('',errorInfo);
																return;
															}
															if (data.resultPath == "deleted") {
																var errorInfo = "编号为"
																		+ standardId
																		+ "的物理机规格已经被删除";
																backList('', errorInfo);
																return;
															}
															if (data.resultPath == "enabled") {
																var errorInfo = "编号为"
																		+ standardId
																		+ "的物理硬盘规格资源池状态为可用,无法修改";
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
						var errorInfo = "编号为" + standardId + "的物理机规格已经被删除";
						backList('', errorInfo);
					}
				}
			});

	$
}*/

//物理机型号下拉菜单
function initServerTypeSelect(){
	$.ajax({
        type: "POST",
        url: 'serverTypeQuery.action',
        data: null,
        dataType: "JSON",
        cache: false,
		async:false,
        success: function(data){
			if(data!=null){
				$("#server_type").empty();
				$("#server_type").append("<option value=''>--请选择--</option>");
				$("#infos").empty();
				$(data).each(function (){
					var str = "<option value='"+this.pmTypeId+"'>";
					str+=this.pmTypeName;
					str+='</option>';
					$("#server_type").append(str);
					var str2 = "<div id='"+this.pmTypeId+'_cpuType'+"'>"+this.cpuType+"</div>";
					str2 += "<div id='"+this.pmTypeId+'_ram'+"'>"+this.ramSize+"</div>";
					str2 += "<div id='"+this.pmTypeId+'_size'+"'>"+this.discSize+"</div>";
					$("#infos").append(str2);
				});
				
			}
		}
	});
}
//修改物理机类型时，同步修改配置数据
function changeType(){
	var id = $("#server_type").val();
	$("#pm_cpuType").val($("#"+id+"_cpuType").text());
	$("#pm_ram").val($("#"+id+"_ram").text());
	$("#pm_size").val($("#"+id+"_size").text());
}

//调用创建物理机模板
function onPmAdd() {
	$
			.dialog( {
				title : '创建物理机规格',
				content : document.getElementById('pm_div'),
				init : function() {
					$("#pm_standard_name,#server_type,#pm_remark").attr(
							"disabled", false);
					
					$("#pm_div_id").hide();
					$("#pm_standard_name").val("");
					$("#type_input").hide();
					$("#type_select").show();
					$("#server_type").val("");
					$("#pm_cpuType").val("");
					$("#pm_ram").val("");
					$("#pm_size").val("");
					$("#pm_remark").val("");
					$.uniform.update();
				},
				button : [ {
					name : '创建',
					callback : function() {
						this.button( {
							name : '创建',
							disabled : true
						});
						var standardName = $("#pm_standard_name").val();
						var serverType = $("#server_type").val();
						var description = $("#pm_remark").val();
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
						if (serverType == "") {
							$.compMsg( {
								type : 'error',
								msg : '物理机型号不能为空!'
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
							serverType : serverType,
							description : description
						};
						var isreturn = false;
						$
								.ajax( {
									type : "POST",
									url : 'PMStandardCreate.action',
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
													.isEmptyObject(data.fieldErrors.serverType)) {
												$
														.compMsg( {
															type : 'error',
															msg : data.fieldErrors.serverType
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
											var errorInfo = "物理机规格创建失败";
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

var query =function(){
	$("#startTime").val(_dateToStr($("#startTime").val()));
	$("#endTime").val(_dateToStr($("#endTime").val()));
    $('#pmForm').submit();
};
/*
//查询
function query() {
	queryBase('PMStandardListQueryJson.action');
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
function queryBase(url) {
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
				url : url,//'PMStandardListQueryJson.action',
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
					if (data.pmStandardInfos.length == 0) {
						$.compMsg( {
							type : 'info',
							msg : "资源规格信息为空！"
						});
						$("#standardTable").empty();
						$("#standardTable").append(table);
						return;
					}
					if (!jQuery.isEmptyObject(data.pmStandardInfos)) {
						var str = table;
						for ( var i = 0; i < data.pmStandardInfos.length; i++) {
							str += '<tr class="iterator">';
							str += '<td>' + data.pmStandardInfos[i].standardName + '</td>';
							str += '<td>' + data.pmStandardInfos[i].standardId + '</td>';
							str += '<td>' + data.pmStandardInfos[i].createUser + '</td>';
							str += '<td>' + data.pmStandardInfos[i].createTime + '</td>';
							str += '<td style="display: none;">部分同步成功</td>';
							str += '<td class="table-opt-block"><a href="javascript:void(0);" onclick="onPmDetail(\''
									+ data.pmStandardInfos[i].standardId
									+ '\');">详情</a><a href="javascript:void(0);" onclick="onPmModify(\''
									+ data.pmStandardInfos[i].standardId
									+ '\');">修改</a><a href="javascript:void(0);" onclick="onSynchro(this,\''+data.pmStandardInfos[i].standardId+'\');return false;">同步</a><a href="javascript:void(0);" onclick="onDel(\''
									+ data.pmStandardInfos[i].standardId
									+ '\')">删除</a>';
							str += '</tr>';
						}
						$("#standardTable").empty();
						$("#standardTable").append(str);
					}
				}
			});
}
*/

//物理机规格删除

function onPmDel(standardId) {
	$.dialog({
		title: '删除物理机规格',
		content: '请您确认是否删除物理机规格'+standardId+'？删除后将无法恢复！',
		ok: function () {
		var da_val = {
				standardId : standardId
			};
			$
					.ajax( {
						type : "POST",
						url : 'PMStandardDelete.action',
						data : da_val,
						dataType : "JSON",
						cache : false,
						success : function(data) {
							if (data.resultPath == "error") {
								var errorInfo = "编号为" + standardId + "物理机规格删除失败";
								backList('',errorInfo);
								return;
							}
							if (data.resultPath == "inuse") {
								var errorInfo = "编号为" + standardId + "物理机规格正在使用，不允许删除";
								backList('',errorInfo);
								return;
							}
							if (data.resultPath == "intermediate") {
								var errorInfo = "编号为" + standardId + "物理机规格存在中间状态，不允许删除";
								backList('',errorInfo);
								return;
							}
							if (data.resultPath == "deleted") {
								var errorInfo = "编号为" + standardId + "的物理机规格已经被删除";
								backList('',errorInfo);
								return;
							}if (data.resultPath == "success") {
								var errorInfo = "编号为" + standardId + "的物理机规格删除成功！";
								backList(errorInfo, '');
								return;
							}
							if (data.resultPath == "enabled") {
								var errorInfo = "编号为"
										+ standardId
										+ "的物理机规格资源池状态为可用,无法删除";
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
			$("#synchro_os").html('');
			pmStandardResList(standardId);
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
function pmStandardResList(standardId){
	var da_val = {'standardId':standardId};
	mask($("#synchro_div"));
	$.ajax({
		type : "POST",
		url : 'PMStandardResList.action',
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
									}else 
									if (resPoolPartStatus == '5') {
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
							table += '<label title="'+mapData[key][i].resPoolPartName+'">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + mapData[key][i].resPoolPartName + '</label>';
						}
						table += '<label>' + resStatusText + '</label>';
						table += '<label>' + resPoolPartStatusText + '</label>';
						table += '</li>';
					}
					table += '</dd></div></td></tr>';
				}
				table + '</table>';
				$("#synchro_table").html(table);
				
				//镜像
				var ostable = '<table>';
				ostable += '<tr><td><div class="authBlock zoone">';
				ostable += '<dt>';
				ostable += '<span class="up"></span>';
				ostable += '<label><input type="checkbox" name="allOs" />全部</label>';
				ostable += '</dt><dd>';
				$.each(data.osInfos,function(index){
					var osName = this.osName;
					var osNameText = osName;
					if (osName.length > 20) {
						osNameText = osName.substring(0, 20) + '...';
					}
					ostable += '<li>';
					ostable += '<label title="' + osName + '">';
					ostable += '<input type="checkbox" name="osId" osId="'+ this.osId +'"/>';
					ostable += osNameText + '</label>';
					ostable += '</li>';
				});
				ostable += '</dd></div></td></tr>';
				ostable + '</table>';
				$("#synchro_os").html(ostable);
				
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
				
				//子节点全选中，父节点跟着选中
				$(".authBlock dd :checkbox").click(function(){
					var parentCheckBox = $(this).parents('.authBlock').find('dt').find('input:checkbox');
					var sameCheckbox = $(this).parents('dd').find('input:checkbox');
					var fl = true;
					$.each(sameCheckbox, function(index){
						if($(this).attr("checked") != "checked"){
							fl = false;	
						}
					});
					if(fl){
						parentCheckBox.attr("checked", "checked");
						$.uniform.update();
					}else{
						parentCheckBox.removeAttr("checked");
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
	var osjsonStr = '';
	var osflage = true;
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
	
	$("[name='osId']").each(function (){
		if($(this).attr('checked')){
			var osId = $(this).attr('osId');
			if(osflage){
				osjsonStr = osId;
				osflage = false;
			}else{
				osjsonStr +=', '+osId;
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
	if(osflage){
		$.compMsg( {
			type: 'error',
			msg: '请选择要同步的镜像！'
		});
		return false;
	}
	jsonStr +=']';
	var da_val = {'jsonStr':jsonStr,'standardId':standardId,'osjsonStr':osjsonStr};
	mask($("#synchro_div"));
	$.ajax({
		type : "POST",
		url : 'PMSynchroResPool.action',
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
		url : 'PMStandardListQuery.action'
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
		container.find(".mask").remove();
	}
}