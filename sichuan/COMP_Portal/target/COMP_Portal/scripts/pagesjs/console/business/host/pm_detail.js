//网卡行数
var rols = 0;
// 网卡个数
var rolsCount = 0;
// 要提交的网卡list
var netListNew = new Array();

$( function() {
	$(".left-menu li:contains('物理机 ')").addClass("selected");
	// 自定义form样式
	$("select,:radio,:text,textarea").uniform();
	// 主机操作
	$(".operation").click( function() {
		var $vmopt = $("#vmopt");
		$vmopt.css("display") == "block" ? $vmopt.hide() : $vmopt.show();
	});
	$("#vmopt").mouseout().css("display", "none");
	// 详细信息tab
	$('#detail_tab').MookoTabs( {
		eventName : 'click'
	});
	
	// 获取物理硬盘列表
	getMountDiskList();
	
	// 从接口刷新pm状态
	queryStatusFromResPool();
});
function operate(status) {
	var $runBtn = '<li id="run"><a href="#" class="run" onclick="startHost()">开机</a></li>';
	var $rebootBtn = '<li id="reboot"><a href="#" class="reboot" onclick="rebootHost()">重启</a></li>';
	var $stopBtn = '<li id="stop"><a href="#" class="stop" onclick="stopHost()" >关机</a></li>';
	var $delBtn = '<li id="del"><a class="del" href="javascript:delHost();">删除</a></li>';
	$("#vmopt").empty();
	$("#attach").attr("style", "display: none;");
	$("#detach").attr("style", "display: none;");
	// 运行中
	if (status == "2") {
		$("#attach").removeAttr("style");
		$("#detach").removeAttr("style");
		$("#vmopt").append($rebootBtn);
		$("#vmopt").append($stopBtn);
		$("#vmopt").append($delBtn);
		return;
	}
	//已停止
	if (status == "4") {
		$("#attach").removeAttr("style");
		$("#detach").removeAttr("style");
		$("#vmopt").append($runBtn);
		$("#vmopt").append($delBtn);
		return;
	}
	//操作失败,发送失败,物理机状态异常
	if(status=="5"||status=="10"||status=="11"){
		$("#vmopt").append($delBtn);
		return;
	}
	$("#vmopt").empty();
}


//修改物理机配置信息
function goToUpdatePage() {
	if(currentStatus == '4'){
		$.dialog({
			title : '修改物理机',
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
					var pmId = $.trim($("#pmId").val());

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
						pmId : pmId,
						netListChanged : JSON.stringify(netListNew)
					};
					var urls = "pmUpdateAction.action";
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
									backList(data.mes, '', pmId);
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
			msg : '只有关机的物理机才能修改！'
		});
	}
}

//验证网卡
function checkValue() {
	var flag = true;

	// 校验新增数据各字段是否为空
	var s = $("table select");
	for (var i = 0; i < s.length; i++) {
		if (s[i].value == null || s[i].value == "") {
			alert("网卡内容有空值，请输入！");
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
			alert("网卡的重复IP为：" + nary[i]);
			flag = false;
			return flag;
		}
	}

	// 校验网关是否为空
	var s = $("input[name$='gateway']");
	for (var i = 0; i < s.length; i++) {
		if (s[i].value == null || s[i].value == "") {
			alert("网关不可空，请输入！");
			flag = false;
			return flag;
		} else {
			if (!checkIP(s[i].value)) {
				alert("网关格式不正确，请修正！");
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
		alert("网关地址: " + value + " 不合法");
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
				alert(data.errMsg);
				return;
			}
			if (data.resultRoute == "failure") {
				checkGatewayOk = false;
				alert("验证网关段信息异常");
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
	str += '<td  class="NoNewline" onclick="deleteNet(this)"><span class="product-list-btn"><a href="#">删除</a></span></td>';
	str += '</tr>';
	$("#relate_resource_div").find("#ethList").append(str);

	// 获取vlan列表
	getVlans();
}

function getVlans(vlanId) {
	var appId = $("#appId").val();
	// 查询物理机业务对应的VLAN列表
	var da_val = {
		appId : appId
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
						alert("展示资源池信息异常，将跳转到浏览页面");
						window.location.href = 'pmQueryListAction.action?optState=1';
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
						if (data.vlanList[i].resPoolId == resourcePoolId
								&& data.vlanList[i].resPoolPartId == resourcePoolPartId) {
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
						$.uniform.update();
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
					url : 'pmOnloadIpsegmentAction.action',
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
							alert("展示IP段信息异常，将跳转到浏览页面");
							window.location.href = 'pmQueryListAction.action?optState=1';
							return;
						}

						$(selectVal).empty();
						for (var i = 0; i < data.ipSegmentList.length; i++) {
							if (data.ipSegmentList[i].resPoolId == resourcePoolId
									&& data.ipSegmentList[i].resPoolPartId == resourcePoolPartId) {
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
							$.uniform.update();
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
					url : 'pmOnloadPrivateIpAction.action',
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
							alert("展示私网ip信息异常，将跳转到浏览页面");
							window.location.href = 'pmQueryListAction.action?optState=1';
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

							$.uniform.update();
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
	str += '<td><span class="product-list-btn"><a href="#">修改</a></span></td>';
	str += '</tr>';
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

// 主机删除
function delHost() {
	$.dialog( {
		title : '删除主机',
		content : '请您确认是否删主机？删除后将无法恢复！',
		ok : function() {
			clearTimeout(t);
			var pmId = $('#pmId').attr("value");
			var resourcePoolId = $('#resPoolId').attr("value");
			var resourcePoolPartId = $('#resPoolPartId').attr("value");
			var da_val = {
				'pmId' : pmId,
				'resourcePoolId' : resourcePoolId,
				'resourcePoolPartId' : resourcePoolPartId
			};
			$.ajax( {
				url : 'pmDelAction.action',
				type : 'POST',
				data : da_val,
				cache : false,
				dataType : 'json',
				beforeSend : function(){
					$.blockUI({ 
						message:  '<img src="../images/loading.gif" align="absmiddle" style="margin-right:20px">'+'<span id="load_span">物理机删除中，请稍后...</span>',
						css: { width: '200px' , left : '45%' }
					}); 
				},
				success : function(data) {
					$.unblockUI();
					if (data.resultPath == '0') {
						if (!(data.mes == null || data.mes == '')) {
							$.compMsg( {
								type : 'error',
								msg : data.mes
							});
						}
						// 中间状态展示
						if (data.result == "intermediate") {
							$("#status").text("操作成功");
							$("#status").removeClass().addClass(
									"status mt s-blue");
						}
					} else if (data.resultPath == '2') {
						var pmName = $('#custom_name').text();
						var errMsg = pmName+"已经被删除，跳回到物理机列表页面";
						goBackForm(errMsg,"");
					} else if (data.resultPath == '-1') {
						window.location.href = 'exceptionIntercepor.action';
					}else{
						var pmName = $('#custom_name').text();
						var msg = pmName+"删除成功，跳回到物理机列表页面！";
						goBackForm("",msg);
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
//跳转到列表界面
function goBackForm(errMsg,msg){
	$.backListMsg({msg:msg,errMsg:errMsg,url:'pmQueryListAction.action'});
}
function stopHost() {
	var pmId = $('#pmId').attr("value");
	var resourcePoolId = $('#resPoolId').attr("value");
	var resourcePoolPartId = $('#resPoolPartId').attr("value");
	var da_val = {
		pmId : pmId,
		resourcePoolId : resourcePoolId,
		resourcePoolPartId : resourcePoolPartId
	};
	$.ajax( {
		url : 'pmStopAction.action',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {
			var pmName = $('#custom_name').text();
			if (data.result == "failure") {
				$.compMsg( {
					type : 'error',
					msg : '操作失败!'
				});
			}
			if(data.result == "statusinvalid"){
				$.compMsg( {
					type : 'error',
					msg : data.resultMessage
				});
			}
			if (data.result == "error") {
				window.location.href = 'exceptionIntercepor.action';
			} 
			if (data.result == "deleted") {
					var errorMessage = pmName+"已经被删除，跳回到物理机列表页面";
					goBackForm(errorMessage,'');
			}
			if (data.result == "nopermission") {
					var errorMessage = "用户没有操作"+pmName+"的权限，跳回到物理机列表页面";
					goBackForm(errorMessage,'');
			} 
			// 中间状态展示
			if (data.result == "intermediate") {
				$("#status").text("操作成功");
				$("#status").removeClass().addClass("status mt s-blue");
			}
		}
	});

}
function startHost() {
	var pmId = $('#pmId').attr("value");
	var resourcePoolId = $('#resPoolId').attr("value");
	var resourcePoolPartId = $('#resPoolPartId').attr("value");
	var da_val = {
		pmId : pmId,
		resourcePoolId : resourcePoolId,
		resourcePoolPartId : resourcePoolPartId
	};
	$.ajax( {
		url : 'pmStartAction.action',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {
			var pmName = $('#custom_name').text();
			if (data.result == "failure") {
				$.compMsg( {
					type : 'error',
					msg : '操作失败!'
				});
				return;
			}
			if(data.result == "statusinvalid"){
				$.compMsg( {
					type : 'error',
					msg : data.resultMessage
				});
			}
			if (data.result == "error") {
				window.location.href = 'exceptionIntercepor.action';
			} 
			if (data.result == "deleted") {
				var errorMessage = pmName+"已经被删除，跳回到物理机列表页面";
				goBackForm(errorMessage,'');
			}
			if (data.result == "nopermission") {
				var errorMessage = "用户没有操作"+pmName+"的权限，跳回到物理机列表页面";
				goBackForm(errorMessage,'');
			} 
			// 中间状态展示
			if (data.result == "intermediate") {
				$("#status").text("操作成功");
				$("#status").removeClass().addClass("status mt s-blue");
			}
		}
	});
}
function rebootHost() {
	var pmId = $('#pmId').attr("value");
	var resourcePoolId = $('#resPoolId').attr("value");
	var resourcePoolPartId = $('#resPoolPartId').attr("value");
	var da_val = {
		pmId : pmId,
		resourcePoolId : resourcePoolId,
		resourcePoolPartId : resourcePoolPartId
	};
	$.ajax( {
		url : 'pmRebootAction.action',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {
			var pmName = $('#custom_name').text();
			if (data.result == "failure") {
				$.compMsg( {
					type : 'error',
					msg : '操作失败!'
				});
				return;
			}
			if(data.result == "statusinvalid"){
				$.compMsg( {
					type : 'error',
					msg : data.resultMessage
				});
			}
			if (data.result == "error") {
				window.location.href = 'exceptionIntercepor.action';
			} 
			if (data.result == "deleted") {
				var errorMessage = pmName+"已经被删除，跳回到物理机列表页面";
				goBackForm(errorMessage,'');
			}
			if (data.result == "nopermission") {
				var errorMessage = "用户没有操作"+pmName+"的权限，跳回到物理机列表页面";
				goBackForm(errorMessage,'');
			} 
			// 中间状态展示
			if (data.result == "intermediate") {
				$("#status").text("操作成功");
				$("#status").removeClass().addClass("status mt s-blue");
			}
		}
	});
}
function updateRemarkajax() {
	var pmId = $('#pmId').attr("value");
	var remart = $("[name='modify_remark']").val();
	if (remart.length > 100) {
		$.compMsg( {
			type : 'error',
			msg : '备注信息最大长度为100!'
		});
		return;
	}
	var da_val = {
		pmId : pmId,
		description : remart
	};
	$.ajax( {
		url : 'pmDescUpdateAction.action?struts.enableJSONValidation=true',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {
		var pmName = $('#custom_name').text();
			if (!jQuery.isEmptyObject(data.fieldErrors)) {
				if (data.fieldErrors.description.length > 0)
					$.compMsg( {
						type : 'error',
						msg : data.fieldErrors.description
					});
				return;
			}
			if (data.result == "error") {
				window.location.href = 'exceptionIntercepor.action';
			} else {
				if (data.result == "deleted") {
					var errorMessage = pmName+"已经被删除，跳回到物理机列表页面";
					goBackForm(errorMessage,'');
				} else {
					if (data.result == "nopermission") {
						var errorMessage = "用户没有操作"+pmName+"的权限，跳回到物理机列表页面";
						goBackForm(errorMessage,'');
					} else {
						$("#remark").text($("[name='modify_remark']").val());
					}
				}
			}
		}
	});
	return true;
}
// 修改备注
function modifyRemark() {
	$("#modify_remark_div textarea").val("");
	$.dialog( {
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
}
function updateCustomNameajax() {
	var pmId = $('#pmId').attr("value");
	var pmName = $("[name='modify_custom_name']").val();
	if (pmName.length > 25) {
		$.compMsg( {
			type : 'error',
			msg : '物理机名称最大长度为25!'
		});
		return;
	}
	var da_val = {
		pmId : pmId,
		pmName : pmName
	};
	$.ajax( {
		url : 'pmNameUpdateAction.action?struts.enableJSONValidation=true',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {
		var pmNameOriginal = $('#custom_name').text();
			if (!jQuery.isEmptyObject(data.fieldErrors)) {
				if (data.fieldErrors.pmName.length > 0)
					$.compMsg( {
						type : 'error',
						msg : data.fieldErrors.pmName
					});
				return;
			}
			if (data.result == "error") {
				window.location.href = 'exceptionIntercepor.action';
			} else {
				if (data.result == "deleted") {
					var errorMessage = pmNameOriginal+"已经被删除，跳回到物理机列表页面";
					goBackForm(errorMessage,'');
				} else {
					if (data.result == "nopermission") {
						var errorMessage = "用户没有操作"+pmNameOriginal+"的权限，跳回到物理机列表页面";
						goBackForm(errorMessage,'');
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
	$("#modify_custom_name_div textarea").val("");
	$.dialog( {
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
}
$(document).ready( function() {
	if ($('#error').text() != "") {
		setTimeout( function() {
			goToPMListPage()
		}, 3000);
		return;
	}
});

//物理机删除延长查询状态时间
var t;
// 查询物理机当前状态
var currentStatus;
function queryStatusFromResPool() {
	var pmId = $('#pmId').attr("value");
	var status = $("#state").attr("value");
	var str = "[{id:'" + pmId + "',status:'" + status + "'}]";
	var da_val = {
		queryStr : str
	};
	$.ajax({
				type : "POST",
				url : 'pmSearchStateAction.action?param=2',
				data : da_val,
				dataType : "JSON",
				cache : false,
				success : function(data) {
					var ls = data.resultStatusInfos;
					if (ls == null || ls == 'undefined') {
						window.location.href = 'exceptionIntercepor.action';
					}
					// ls传过来的是页面上pmId状态变化的量
					if (ls.length != 0) {
						// 状态有变化
						for (var i = 0; i < ls.length; i++) {
							var statusCode = ls[i].status;
							var statusDesc = ls[i].statusText;
							$("#status").text(statusDesc);
							if (statusCode == '0') {
								$("#status").removeClass().addClass(
										"status mt s-blue");
							} else if (statusCode == '1') {
								$("#status").removeClass().addClass(
										"status mt s-blue");
							} else if (statusCode == '2') {
								$("#status").removeClass().addClass(
										"status mt s-green");
							} else if (statusCode == '3') {
								$("#status").removeClass().addClass(
										"status mt s-brown");
							} else if (statusCode == '4') {
								$("#status").removeClass().addClass(
										"status mt s-gray");
							} else if (statusCode == '5') {
								$("#status").removeClass().addClass(
										"status mt s-orange");
							} else if (statusCode == '10') {
								$("#status").removeClass().addClass(
										"status mt s-orange");
							} else if (statusCode == '11') {
								$("#status").removeClass().addClass(
										"status mt s-orange");
							} else if (statusCode == '12') {
								$("#status").removeClass().addClass(
										"status mt s-purple");
							} else if (statusCode == '13') {
								$("#status").removeClass().addClass(
										"status mt s-blue");
							}
							operate(statusCode);
							currentStatus = statusCode;
						}
					} else {
						// 状态没变化，还取变化之前的值
						if (status == '0') {
							$("#status").text("待创建");
							$("#status").removeClass().addClass(
									"status mt s-blue");
						} else if (status == '1') {
							$("#status").text("创建中");
							$("#status").removeClass().addClass(
									"status mt s-blue");
						} else if (status == '2') {
							$("#status").text("运行中");
							$("#status").removeClass().addClass(
									"status mt s-green");
						} else if (status == '3') {
							$("#status").text("已删除");
							$("#status").removeClass().addClass(
									"status mt s-brown");
						} else if (status == '4') {
							$("#status").text("已停止");
							$("#status").removeClass().addClass(
									"status mt s-gray");
						} else if (status == '9') {
							$("#status").text("操作失败");
							$("#status").removeClass().addClass(
									"status mt s-orange");
						} else if (status == '10') {
							$("#status").text("发送失败");
							$("#status").removeClass().addClass(
									"status mt s-orange");
						} else if (status == '11') {
							$("#status").text("状态异常");
							$("#status").removeClass().addClass(
									"status mt s-orange");
						} else if (status == '12') {
							$("#status").text("已暂停");
							$("#status").removeClass().addClass(
									"status mt s-purple");
						} else if (status == '13') {
							$("#status").text("处理中");
							$("#status").removeClass().addClass(
									"status mt s-blue");
						}
						operate(status);
						currentStatus = status;
					}
					t = setTimeout(queryStatusFromResPool, 10000);
				},
				error : function(e) {
				}
			});
}
function goToPMListPage() {
	var nodeType = $("#nodeType").val();
	var nodeId = $("#nodeId").val();
	var treeNodeName = $("#treeNodeName").val();
	var pnodeId = $("#pnodeId").val();
	var pnodeName = $("#pnodeName").val();
	var appId = $("#appId").val();
	var curFun = $("#curFun").val();
	window.location.href = 'pmQueryListAction.action?type=0&queryBusinessId='
			+ businessId + "&nodeType=" + nodeType + "&nodeId=" + nodeId
			+ "&treeNodeName=" + treeNodeName + "&pnodeId=" + pnodeId
			+ "&pnodeName=" + pnodeName + "&appId=" + appId + "&curFun="
			+ curFun;
}
function goToApplyPage() {
	var appId = $("#param-appId").val();
	var appName=$('#param-appName').val();
	window.location.href = 'pmApplyAction.action?appId='+appId+'&appName='+appName;
}

//获取挂载列表
function getMountDiskList() {
	var pmId = $('#pmId').attr("value");
	if (null != pmId && pmId != '') {
		var da_val = {
			hostId : pmId,
		};
		$
				.ajax({
					type : "POST",
					url : 'ebsQueryListForVmAction.action',
					dataType : "JSON",
					data : da_val,
					cache : false,
					success : function(data) {
						if (data.length == 0) {
							$("#diskList")
									.append(
											"<td id='noDisk' colspan='4'>暂时未挂载云硬盘!</td>");
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
														+ 'G</td>'
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
								"<td id='noDisk' colspan='4'>获取云硬盘信息失败!</td>");
					}
				});
	} else {
		$("#diskList").append("<td id='noDisk' colspan='4'>暂时未挂载云硬盘!</td>");
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
			resourceType : 2
		};
	} else if (key == 'diskId') {
		data_val = {
			diskId : val,
			businessId : appId,
			resourceType : 2
		};
	} else if (key == 'diskSize') {
		data_val = {
			diskSize : val,
			businessId : appId,
			resourceType : 2
		};
	} else {
		data_val = {
			businessId : appId,
			resourceType : 2
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
				url : 'ebsQueryListForVmAction.action',
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
														+ 'G</td>' + '</tr>');
							}
						}
					}
					$("#mountDiskList :radio").uniform();
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
		resourceType : 2
	};
	seachDiskList(data_val);
	$
			.dialog({
				title : '选择云硬盘',
				content : document.getElementById('mountDisk'),
				ok : function() {
					var selectDiskId = $("#mountDiskList .checked input");
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
										var hostId = $('#pmId').attr("value");
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
															$("#noDisk")
																	.remove();
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
									var hostId = $("#pmId").val();
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