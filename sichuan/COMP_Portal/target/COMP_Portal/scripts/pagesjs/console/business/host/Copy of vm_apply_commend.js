//申请页面的数据缓存
var itemId;
var varRespoolId;
var varRespoolPartId;
var cpuNum;
var ramSize;
var discSize;
var standardId;
var lengthTime;
var queryBusinessId;

var rolsCount = 0;
var rols = 0;
var cpuNumMaxSize = 64;
var ramMaxSize = 102400/1024;
var diskMaxSize = 1024;
var checkGatewayOk = false;

$(function() {
	// 菜单显示当前
	$(".left-menu li:has(#business_" + businessId + ")").addClass("selected")
			.next().show();
	$(".left-menu #menu_vm_" + businessId).parent().addClass("selected");




	/*
	 * // 是否配置公网ip $("#addNetIp").click( function() { if
	 * ($(this).attr("checked")) { $("#netConfig").show(); } else {
	 * $("#netConfig").hide(); } });
	 */

	// 默认显示值
	var defaultTerm = $("#accountingType").children('option:selected').val()
			+ "Option";
	$("." + defaultTerm).show();
	$("#accountingType").change(function() {
		var val = $(this).children('option:selected').val();
		$("#accountingTerms .selector").each(function() {
			if ($(this).hasClass(val + "Option")) {
				$(this).show();
			} else {
				$(this).hide();
			}
		});
		if (val == year) {
			$("#yearType").show();
		}
	});

	itemId = $('#itemId').val();

	// 所属业务名称显示
	queryBusinessId = $("#app").val();
	onloadApp();
	onloadResPool("");

	$("#num2").text("1");
	$("#lengthTime").val("1");
	lengthTime = "1";//默认计费时长为1小时
	$("#chargeTypeSpan").text("按时计费");
	$("#lengthTimeZH").text("1小时");

	$("#num").change(function() {
		$("#num2").text($(this).children('option:selected').text());

	});

	$("#osTypeSelect").change(function() {
		$("#osType").val($(this).children('option:selected').text());
		$("#osType2").text($(this).children('option:selected').text());

	});

	$("#respool").change(function() {
		varRespoolId = $(this).children('option:selected').val();
		varRespoolPartId = "";
		itemId = "";
		onloadResPool(varRespoolId, varRespoolPartId);

	});

	$("#respoolpart").change(function() {
		varRespoolId = $("#respool").children('option:selected').val();
		varRespoolPartId = $(this).children('option:selected').val();
		itemId = "";
		onloadResPool(varRespoolId, varRespoolPartId);

	});

});

// 加载资源池及分区信息
function onloadResPool(tempRespoolId, tempRespoolPartId) {

	var da_val = {
		itemId : itemId,
		respoolId : tempRespoolId,
		respoolPartId : tempRespoolPartId
	};
	$.ajax({
		url : 'vmOnloadResPoolAction.action?struts.enableJSONValidation=true',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {

			if (!jQuery.isEmptyObject(data.fieldErrors)) {
				if (data.fieldErrors.respoolId.length > 0) {
					alert(data.fieldErrors.respoolId);
					return;
				}
				if (data.fieldErrors.itemId.length > 0)
					alert(data.fieldErrors.itemId);
				return;
			}
			if (data.resultRoute == "error") {
				window.location.href = 'exceptionIntercepor.action';
				return;
			}
			if (data.resultRoute == "failure") {
				alert("展示资源池信息异常，将跳转到浏览页面");
				window.location.href = 'vmQueryListAction.action?optState=1';
				return;
			}

			cpuNumMaxSize = data.cpuNumMaxSize;
			ramMaxSize = parseInt(data.ramMaxSize)/1024;
			diskMaxSize = data.diskMaxSize;

			$("#respool").empty();
			for (var i = 0; i < data.respools.length; i++) {
				if (i == 0) {
					$("#respoolName").val(data.respools[i].respoolName);
				}
				if (varRespoolId != data.respools[i].respoolId) {
					$("#respool").append(
							'<option value="' + data.respools[i].respoolId
									+ '">' + data.respools[i].respoolName
									+ '</option>');
				} else {
					$("#respool").append(
							'<option selected="selected" value="'
									+ data.respools[i].respoolId + '">'
									+ data.respools[i].respoolName
									+ '</option>');
					varRespoolId = data.respools[i].respoolId;
					$("#respoolName").val(data.respools[i].respoolName);
				}
			}

			// 资源池无分区判断
			if (data.respoolParts.length == 0) {
				// console.js
				maskNotPart($(".details-con1"));
				$("#respoolpart").empty();
				$("#topItems").empty();
				$("#respoolpart").append('<option value=""></option>');
				$("#respoolPartName").val("");
				return false;
			} else {
				// console.js
				removeMask($(".details-con1"));
			}

			$("#respoolpart").empty();
			for (var i = 0; i < data.respoolParts.length; i++) {
				if (i == 0) {
					$("#respoolPartName").val(
							data.respoolParts[i].respoolPartName);
				}
				if (varRespoolPartId == data.respoolParts[i].respoolPartId) {
					$("#respoolpart").append(
							'<option selected="selected" value="'
									+ data.respoolParts[i].respoolPartId + '">'
									+ data.respoolParts[i].respoolPartName
									+ '</option>');
					varRespoolPartId = data.respoolParts[i].respoolPartId;
					$("#respoolPartName").val(
							data.respoolParts[i].respoolPartName);

				} else {
					$("#respoolpart").append(
							'<option value="'
									+ data.respoolParts[i].respoolPartId + '">'
									+ data.respoolParts[i].respoolPartName
									+ '</option>');
				}

			}
			if (typeof (varRespoolPartId) == "undefined"
					|| varRespoolPartId == "") {
				varRespoolPartId = data.respoolParts[0].respoolPartId;
			}

			$("#osNameSelect").empty();
			for (var i = 0; i < data.oss.length; i++) {
				if (i == 0) {
					$("#osName").val(data.oss[i].osName);
					$("#osName2").text(data.oss[i].osName);
				}

				$("#osNameSelect").append(
						'<option value="' + data.oss[i].osId + '">'
								+ data.oss[i].osName + '</option>');

			}
			// 加载物理机下拉列表
			$('#pmNameSelect').empty();
			for (var i = 0; i < data.pms.length; i++) {
				if (i == 0) {
					$('#pmId').val(data.pms[i].pmId);
				}
				$('#pmNameSelect').append(
						'<option value="' + data.pms[i].pmId + '">'
								+ data.pms[i].pmName + '</option>');
			}

			if ($('#configuration').val() == "0") {
				onloadItem();
			}
		}
	});
}

// 加载条目及规格信息
function onloadItem() {
	varRespoolId = $("#respool").children('option:selected').val();
	varRespoolPartId = $("#respoolpart").children('option:selected').val();

	var da_val = {
		respoolId : varRespoolId,
		respoolPartId : varRespoolPartId
	};
	$
			.ajax({
				url : 'vmOnloadItemAction.action?struts.enableJSONValidation=true',
				type : 'POST',
				data : da_val,
				cache : false,
				dataType : 'json',
				success : function(data) {
					if (data.items.length == 0) {
						// console.js
						mask($(".details-con1"));
					} else {
						// console.js
						removeMask($(".details-con1"));
					}
					if (!jQuery.isEmptyObject(data.fieldErrors)) {
						if (data.fieldErrors.respoolId.length > 0) {
							alert(data.fieldErrors.respoolId);
							return;
						}
						if (data.fieldErrors.respoolPartId.length > 0)
							alert(data.fieldErrors.respoolPartId);
						return;
					}
					if (data.resultRoute == "error") {
						window.location.href = 'exceptionIntercepor.action';
						return;
					}
					if (data.resultRoute == "failure") {
						alert("展示条目信息异常，将跳转到浏览页面");
						window.location.href = 'vmQueryListAction.action?optState=1';
						return;
					}
					// 清除上一次写入条目的信息
					$("#topItems").empty();
					for (var i = 0; i < data.items.length; i++) {
						var tempItemId = data.items[i].itemId;
						if (null == itemId || itemId == "") {
							itemId = tempItemId;
						}
						var checked = '';

						if (tempItemId == itemId) {
							changeItem(tempItemId,
									data.items[i].standardInfo.templateId,
									data.items[i].standardInfo.standardId,
									data.items[i].standardInfo.cpuNum,
									parseInt(data.items[i].standardInfo.ramSize)/1024,
									data.items[i].standardInfo.discSize,
									data.items[i].description,
									data.items[i].itemName);
							checked = ' checked="checked"';
						}

						var lineNum;
						if (i % 4 == 0) {
							lineNum = i / 4;
							if (i == 0) {
								$("#topItems").append(
										'<div class="apply-info-top"><div class="max-radio" id="items'
												+ lineNum + '"></div></div>');
							} else {
								$("#topItems").append(
										'<div><div class="max-radio" id="items'
												+ lineNum + '"></div></div>');
							}
						}

						var itemName = data.items[i].itemName;
						var itemNameText = itemName;
						if (itemName.length > 5) {
							itemNameText = itemName.substring(0, 5) + '...';
						}
						$("#items" + lineNum).append(
								'<label title="' + itemName
										+ '"><input type="radio" ' + checked
										+ ' name="itemId" value="' + tempItemId
										+ '"/><label>' + itemNameText
										+ '</label>');

					}
					$('[name="itemId"]')
							.bind(
									"click",
									function() {
										var checkInput = this.value;
										$(data.items)
												.each(
														function(index) {
															var item = data.items[index];
															if (item.itemId == checkInput) {
																changeItem(
																		item.itemId,
																		item.standardInfo.templateId,
																		item.standardInfo.standardId,
																		item.standardInfo.cpuNum,
																		parseInt(item.standardInfo.ramSize)/1024,
																		item.standardInfo.discSize,
																		item.description,
																		item.itemName);
															}
														});
									});
				}
			});

}

// 改变选择条目
function changeItem(tempItemId, templateId, tempStandardId, tempCpuNum,
		tempRamSize, tempDiscSize, tempDescription, tempItemName) {
	itemId = tempItemId;
	standardId = tempStandardId;
	cpuNum = tempCpuNum;
	ramSize = tempRamSize;
	discSize = tempDiscSize;
	$("#cpuNum").val(cpuNum);
	$("#ramSize").val(ramSize);
	$("#discSize").val(discSize);
	$("#cpuNum2").text(cpuNum);
	$("#ramSize2").text(ramSize);
	$("#discSize2").text(discSize);
	$("#cpuNum3").text(cpuNum);
	$("#ramSize3").text(ramSize);
	$("#discSize3").text(discSize);
	// $("#itemId").val(tempItemId);
	$("#standardId").val(tempStandardId);
	$("#itemDescription").text("适用领域及特点描述: " + tempDescription);
	$("#itemName").text(tempItemName);

	var da_val = {
		standardId : tempStandardId,
		templateId : templateId
	};
	$.ajax({
		url : 'vmOnloadOsAction.action?struts.enableJSONValidation=true',
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
				alert("展示条目下镜像信息异常，将跳转到浏览页面");
				window.location.href = 'vmQueryListAction.action?optState=1';
				return;
			}

			$("#osNameSelect").empty();
			for (var i = 0; i < data.osInfos.length; i++) {
				/*
				 * if(i==0){ $("#osNameSelect").append( '<option value=""></option>'); }
				 */
				$("#osNameSelect").append(
						'<option value="' + data.osInfos[i].osId + '">'
								+ data.osInfos[i].osName + '</option>');
			}
			changeosName();
		}
	});
}

// 改变选择镜像
function changeosName() {
	$("#osName").val($("#osNameSelect :selected").text());
	$("#osName2").text($("#osNameSelect :selected").text());
}
// 改变选择物理机
function changePmName() {
	$("#pmId").val($("#pmNameSelect :selected").val());
}

// 业务名称展示
function onloadApp() {
	var da_val = {
		queryBusinessId : queryBusinessId
	};
	$.ajax({
		url : 'vmOnloadResAppAction.action?struts.enableJSONValidation=true',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {
			if (data.resultRoute == "success") {
				$("#bindDiv").children('span').after(
						'<span class="apply-span-name">'
								+ data.queryBusinessName + '</span>');
				return;
			}
			if (data.resultRoute == "error") {
				window.location.href = 'exceptionIntercepor.action';
				return;
			}
			if (data.resultRoute == "failure") {
				alert("展示业务信息异常，将跳转到浏览页面");
				window.location.href = 'hostList.action?queryBusinessId='
						+ queryBusinessId;
				return;
			}
		}
	});
}

// 改变vlan，ajax请求privateIp
function changeIpsegment(ipSegmentId) {

	var selectValTemp = $(ipSegmentId).attr('id');
	selectValTemp = "#"
			+ selectValTemp.replace("ipsegmentSelect", "privateIpSelect");
	var selectVal = selectValTemp;
	var vlanselectVal = selectValTemp.replace("privateIpSelect", "vlanSelect");
	var vlan = $(vlanselectVal).val();
	var ipSegmentId = ipSegmentId.value;

	if ("" != vlan && "" != ipSegmentId) {

		var da_val = {
			logicVlanId : vlan,
			ipSegmentId : ipSegmentId
		};
		$
				.ajax({
					url : 'vmOnloadPrivateIpAction.action?struts.enableJSONValidation=true',
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
							window.location.href = 'vmQueryListAction.action?optState=1';
							return;
						}
						// var selectVal="#privateIpSelect"+rols;
						$(selectVal).empty();
						for (var i = 0; i < data.ipInfos.length; i++) {
							if (i == 0) {
								$(selectVal).append(
										'<option value=""></option>');
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

// 添加网卡
function addNet(ethList) {
	var str = '<tr>';
	str += '<td  colspan="4"  align="center"><div >VLAN：<select style="width:90px" size="1"   id="vlanSelect'
			+ rols
			+ '" name="'
			+ ethList
			+ 'vlanId" onchange="changeVlan(this)"></select></div></td>';
	str += '<td colspan="6"   align="center"style="overflow:hidden;"><div >IP段：<select style="width:152px" size="1"   id="ipsegmentSelect'
			+ rols
			+ '" name="'
			+ ethList
			+ 'ipsegmentId" onchange="changeIpsegment(this)"></select></div></td>';
	str += '<td  colspan="5"   align="center"  ><div >IP：<select  style="width:140px" size="1"  id="privateIpSelect'
			+ rols + '" name="' + ethList + 'ip"></select></div></td>';
	str += '<td  colspan="5"  align="center" ><div  >网关：<input style="width:100px" type="text" maxlength="15"  id="gateway'
			+ rols
			+ '"  name="'
			+ ethList
			+ 'gateway"     onblur="checkGateway(this)"  /></div></td>';
	str += '<td  colspan="2"  align="center"  style="width:100px"  onclick="deleteNet(this)"><div><span class="product-list-btn"><a href="#">删除</a></span></div></td></tr>';
	str += '</tr>';
	$("#relate_resource_div").find('#' + ethList).append(str);

	var appId = queryBusinessId;
	// 查询虚拟机业务对应的VLAN列表
	var da_val = {
		appId : appId
	};

	$.ajax({
		type : "POST",
		url : 'vlanQueryJson.action',
		data : da_val,
		dataType : "JSON",
		cache : false,
		success : function(data) {
			if (!jQuery.isEmptyObject(data.fieldErrors)) {
				// TODO
			}
			if (data.resultRoute == "error") {
				window.location.href = 'exceptionIntercepor.action';
				return;
			}
			if (data.resultRoute == "failure") {
				alert("展示资源池信息异常，将跳转到浏览页面");
				window.location.href = 'vmQueryListAction.action?optState=1';
				return;
			}
			var selectVal = "#vlanSelect" + rols;
			$(selectVal).empty();
			for (var i = 0; i < data.vlanList.length; i++) {
				if (i == 0) {
					$(selectVal).append('<option value=""></option>');
				}
				$(selectVal).append(
						'<option value="' + data.vlanList[i].vlanId + '">'
								+ data.vlanList[i].vlanName + '</option>');
			}

			rolsCount++;
			rols++;
		}
	});

}

function checkGateway(gateway) {
	var selectVal = $(gateway).attr('id');
	var gatewayId = $("#" + selectVal).val();
	var ipsegmentId = $("#" + selectVal.replace("gateway", "ipsegmentSelect"))
			.val();
	var ip = $("#" + selectVal.replace("gateway", "privateIpSelect")).val();

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

// 改变vlan，ajax请求IP段
function changeVlan(vlanId) {
	var selectVal = $(vlanId).attr('id');
	selectVal = "#" + selectVal.replace("vlanSelect", "ipsegmentSelect");
	var vlanId = vlanId.value;
	var appId = queryBusinessId;
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
						if (data.resultRoute == "error") {
							window.location.href = 'exceptionIntercepor.action';
							return;
						}
						if (data.resultRoute == "failure") {
							alert("展示IP段信息异常，将跳转到浏览页面");
							window.location.href = 'vmQueryListAction.action?optState=1';
							return;
						}

						// var selectVal= "#ipsegmentSelect"+rols;
						$(selectVal).empty();
						for (var i = 0; i < data.ipSegmentList.length; i++) {
							if (i == 0) {
								$(selectVal).append(
										'<option value=""></option>');
							}
							$(selectVal).append(
									'<option value="'
											+ data.ipSegmentList[i].ipSegmentId
											+ '">'
											+ data.ipSegmentList[i].ipSegment
											+ '</option>');
						}
					}
				});
	}
}

var displayError = function(msg) {
	$.compMsg({
		type : 'error',
		msg : msg
	});
};

// 变更时长
function changeAccountingType(accountingType) {
	lengthTime = $(accountingType).val();
	$("#lengthTime").val($(accountingType).val());
	$("#lengthTimeZH").text(
			$(accountingType).children('option:selected').text());
}
// 变更前面时长
function changeAccountingTypeBefore(accountingType) {
	var defaultTerm = $("#accountingType").children('option:selected').val()
			+ "Option";
	lengthTime = $("." + defaultTerm + " :selected").val();
	$("#lengthTime").val($("." + defaultTerm + " :selected").val());
	$("#lengthTimeZH").text($("." + defaultTerm + " :selected").text());

}


//改变计费方式
function chargeTypeChange(obj){
	var chargeTypeVal = obj.value;
	if(chargeTypeVal=='h'){//按小时计费
		$("#chargeTypeSpan").text("按时计费");
		$("#lengthTimeZH").text($("#lengthTime").val()+"小时");
	}else if(chargeTypeVal=='m'){//包月计费
		$("#chargeTypeSpan").text("包月计费");
		$("#lengthTimeZH").text($("#lengthTime").val()+"个月");
	}
}

//改变计费时长
function changeLengthTime(obj){
	var time = obj.value;
	var chargeType = $("input[name='chargeType']:checked").val();
	if(chargeType=='h'){//按小时计费
		$("#lengthTimeZH").text(time+"小时");
	}else if(chargeType=='m'){//包月计费
		$("#lengthTimeZH").text(time+"个月");
	}
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

// 提交按钮
function submitform() {
	var form = document.forms["vmApplyInfoAction"];
	form.ramSize.value=parseInt($("#ramSize").val())*1024;
	document.forms["vmApplyInfoAction"].submit();
	/*
	 * // 校验cpu、内存和存储 if (cpuNumMaxSize == null || cpuNumMaxSize == '' ||
	 * (parseInt($("#cpuNum").val()) <= parseInt(cpuNumMaxSize) && parseInt($(
	 * "#cpuNum").val()) > 0)) {
	 *  } else { alert("处理器不得超过资源池分区总数" + cpuNumMaxSize); return; }
	 * 
	 * if (ramMaxSize == null || ramMaxSize == '' ||
	 * (parseInt($("#ramSize").val()) <= parseInt(ramMaxSize) && parseInt($(
	 * "#ramSize").val()) > 0)) {
	 *  } else { alert("内存(RAM)不得超过资源池分区总数" + ramMaxSize); return; }
	 * 
	 * if (diskMaxSize == null || diskMaxSize == '' ||
	 * (parseInt($("#discSize").val()) <= parseInt(ramMaxSize) && parseInt($(
	 * "#discSize").val()) > 0)) {
	 *  } else { alert("存储空间不得超过资源池分区总数" + diskMaxSize); return; }
	 *  // 校验虚拟机名称 var vmName = $("#vmName").val(); if (vmName == "" || vmName ==
	 * null) { $.compMsg({ type : 'error', msg : '虚拟机名称不能为空！' }); return; }
	 *  // 校验所属业务 var appId = queryBusinessId; if (appId == "" || appId == null) {
	 * $.compMsg({ type : 'error', msg : '所属业务不能为空！' }); return; }
	 *  // 是否配置ip if ($("#ApplyNet").attr("checked") == "checked") { // 验证网卡 var
	 * ips = $("select[name$='ip']"); var arr = new Array(); for (var i = 0; i <
	 * ips.length; i++) { arr[i] = ips[i].value; } var nary = arr.sort(); for
	 * (var i = 0; i < nary.length - 1; i++) { if (nary[i] == nary[i + 1]) {
	 * alert("重复网卡的IP为：" + nary[i]); return; } }
	 * 
	 * var s = $("input[name$='gateway']"); for (var i = 0; i < s.length; i++) {
	 * if (s[i].value == null || s[i].value == "") { alert("网卡内容有空值"); return; }
	 * else { if (!checkIP(s[i].value)) { return; } } }
	 * 
	 * var s = $("table select"); for (var i = 0; i < s.length; i++) { if
	 * (s[i].value == null || s[i].value == "") { alert("网卡内容有空值"); return; } }
	 * 
	 * if (!checkGatewayOk) { alert("网关IP非法"); return; } }
	 * 
	 * var tempVmRemark = $('#vmRemark').attr("value"); if (tempVmRemark.length <=
	 * 100) { document.forms["vmApplyInfoAction"].submit(); } else { $.compMsg({
	 * type : 'error', msg : '备注信息不能超过100个字，请重新输入！' }); }
	 */
}

// 删除按钮
function deleteNet(obj) {
	if (rolsCount == 0) {
		return;
	}
	rolsCount--;
	$(obj).parent().remove();
}

function checkradioApplyNet() {
	var appId = queryBusinessId;
	var num = $("#num").val();
	if ($("#ApplyNet").attr("checked") == "checked") {
		if (appId == "" || appId == null) {
			alert("请先绑定业务");
			$("#ApplyNet").prop("checked", false);
			return;
		}
		if (num == "1") {
			$("#ethListTable1").show();
			$("#ethListTable2").hide();
			$("select[name='ethList2vlanId']").parents('tr').remove();
			$("#ethListTable3").hide();
			$("select[name='ethList3vlanId']").parents('tr').remove();
			$("#ethListTable4").hide();
			$("select[name='ethList4vlanId']").parents('tr').remove();
			$("#ethListTable5").hide();
			$("select[name='ethList5vlanId']").parents('tr').remove();
		} else if (num == "2") {
			$("#ethListTable1").show();
			$("#ethListTable2").show();
			$("#ethListTable3").hide();
			$("select[name='ethList3vlanId']").parents('tr').remove();
			$("#ethListTable4").hide();
			$("select[name='ethList4vlanId']").parents('tr').remove();
			$("#ethListTable5").hide();
			$("select[name='ethList5vlanId']").parents('tr').remove();
		} else if (num == "3") {
			$("#ethListTable1").show();
			$("#ethListTable2").show();
			$("#ethListTable3").show();
			$("#ethListTable4").hide();
			$("select[name='ethList4vlanId']").parents('tr').remove();
			$("#ethListTable5").hide();
			$("select[name='ethList5vlanId']").parents('tr').remove();
		} else if (num == "4") {
			$("#ethListTable1").show();
			$("#ethListTable2").show();
			$("#ethListTable3").show();
			$("#ethListTable4").show();
			$("#ethListTable5").hide();
			$("select[name='ethList5vlanId']").parents('tr').remove();
		} else if (num == "5") {
			$("#ethListTable1").show();
			$("#ethListTable2").show();
			$("#ethListTable3").show();
			$("#ethListTable4").show();
			$("#ethListTable5").show();
		}
	} else {
		$("#ethListTable1").hide();
		$("select[name='ethList1vlanId']").parents('tr').remove();
		$("#ethListTable2").hide();
		$("select[name='ethList2vlanId']").parents('tr').remove();
		$("#ethListTable3").hide();
		$("select[name='ethList3vlanId']").parents('tr').remove();
		$("#ethListTable4").hide();
		$("select[name='ethList4vlanId']").parents('tr').remove();
		$("#ethListTable5").hide();
		$("select[name='ethList5vlanId']").parents('tr').remove();
	}
}

function customConfiguration() {
	$('#recommendCon').addClass("vm-apply").removeClass("vm-apply-se");
	$('#customCon').addClass("vm-apply-se").removeClass("vm-apply");

	$('#topItems').hide();
	$('#topItemsDes').hide();
	$('#recommendCpu').hide();
	$('#recommendRam').hide();
	$('#recommendDisk').hide();

	$('#customCpu').show();
	$('#customRam').show();
	$('#customDisk').show();
	$('#cpuNum').val("2");
	$('#ramSize').val("1");
	$('#discSize').val("50");
	$("#cpuNum2").text("2");
	$("#ramSize2").text("1");
	$("#discSize2").text("50");
	$('#cpuNum').show();
	$('#ramSize').show();
	$('#discSize').show();

	$('#configuration').val("1");
	onloadResPool(varRespoolId, varRespoolPartId);

}

function recommendConfiguration() {
	$('#recommendCon').addClass("vm-apply-se").removeClass("vm-apply");
	$('#customCon').addClass("vm-apply").removeClass("vm-apply-se");
	$('#topItems').show();
	$('#topItemsDes').show();
	$('#recommendCpu').show();
	$('#recommendRam').show();
	$('#recommendDisk').show();

	$('#customCpu').hide();
	$('#customRam').hide();
	$('#customDisk').hide();
	$('#cpuNum').hide();
	$('#ramSize').hide();
	$('#discSize').hide();

	$('#configuration').val("0");
	onloadResPool(varRespoolId, varRespoolPartId);

}

function customChange() {
	$("#cpuNum2").text($("#cpuNum").val());
	$("#ramSize2").text($("#ramSize").val());
	$("#discSize2").text($("#discSize").val());
}
//展示收费标准
function showChargesInfo(){
	$.dialog({
		title : '资费管理',
		init : initChargesInfo(),
		content : document.getElementById('chargesListDiv'),
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}
function initChargesInfo(){
	var cpuNumber = $("#cpuNumber").val();
	var memorySize=$("#memorySize").val();
	var queryData = {'charges.cpuNumber':cpuNumber,'charges.memorySize':memorySize,'charges.chargesType' : '0'};
	
	$.post(
					"queryChargesList.action",
					queryData,
					function(data) {
						$('#chargesListTbody').empty();
						$.each(data.chargesList,function(index, charges) {
											var trHtml = "<tr><td>"+charges.cpuNumber+"</td>"
																	+"<td>"+charges.memorySize+"</td>"
																	+"<td>"+charges.hourPrice+"</td>"
																	+"<td>"+charges.monthPrice+"</td>";
											trHtml += "<td>"+ (charges.desc == null ?"" : charges.desc)+ '</td></tr>';
											$('#chargesListTbody').append(
													trHtml);
										});
						$('#chargesListPageBarDiv').html(data.pageBar);
					},"json").fail(function(e) {
				$.compMsg({
					type : 'error',
					msg : '获取资费列表失败！'
				});
			});
}