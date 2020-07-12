/*规格*/
var standards = [];
/* 条目 */
var items = [];
var hourPrice;
var monthPrice;
var osTypes = [];
var osNames = [];
$(function() {
	$("#server").siblings().removeClass("active");
	$("#server").addClass("active");
	$('div.cart-bar').removeClass("none");
	$('div.cart-bar').load('../cart-bar/vm_cart.jsp',
			function(response, status, xhr) {
				queryResPool("", "", "");
				var chargeType = $("#chargeType").val();
				var lengthTime = $('#lengthTime').val();
				var num = $("#num").val();

				if (chargeType == 'month') {
					$('#chargeType_month').click();
				} else if (chargeType == 'hour') {
					$('#chargeType_hour').click();
				}

				if (lengthTime != '') {
					$("#lengthTime").val(lengthTime);
					if (chargeType == 'month') {
						$(".time").text(lengthTime + "个月");
					} else if (chargeType == 'hour') {
						$(".time").text(lengthTime + "小时");
					}
				} else {
					$("#lengthTime").val("1");
				}

				if (num != '' && parseInt(num) <= 5) {
					$("#num").val(num);
					$(".cart-bar span.num").text(num);
				} else {
					$("#num").val("1");
					$(".cart-bar span.num").text("1");
				}

			});

	$("#respool").change(function() {
		varRespoolId = $(this).children('option:selected').val();
		varRespoolPartId = "";
		onloadResPool(varRespoolId, varRespoolPartId);

	});

	$("#respoolpart").change(function() {
		varRespoolId = $("#respool").children('option:selected').val();
		varRespoolPartId = $(this).children('option:selected').val();
		onloadResPool(varRespoolId, varRespoolPartId);

	});

	initSlider();

});

$("#num").blur(function() {
	var num = $("#num").val();
	var reg = /^[1-5]$/;
	if (!reg.test(num)) {
		$.compMsg({
			type : 'error',
			msg : "请输入正确的购买！"
		});
		num = 1;
		$("#num").val(num);
		$(".cart-bar span.num").text(num);
	}
	if ($('#ApplyNet').prop('checked') == true) {
		$('#ApplyNet').prop('checked', false);
		$("table.network-table").hide();
		$("table.network-table tbody").empty();
	}
	// 计费
	charging();
});

/* 查资源池 */
function queryResPool(resPoolId, resPartId) {
	var data = {
		itemId : "",
		respoolId : resPoolId,
		respoolPartId : resPartId
	};
	$.ajax({
		url : 'vmOnloadResPoolAction.action?struts.enableJSONValidation=true',
		type : 'POST',
		data : data,
		cache : false,
		async : false,
		dataType : 'json',
		success : function(data) {
			$("#respool").empty();
			for (var i = 0; i < data.respools.length; i++) {
				if (i == 0) {
					$("#respoolName").val(data.respools[i].respoolName);
				}
				if (resPoolId != data.respools[i].respoolId) {
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
					$("#respoolName").val(data.respools[i].respoolName);
				}
			}

			$("#respoolpart").empty();
			for (var i = 0; i < data.respoolParts.length; i++) {
				if (i == 0) {
					$("#respoolPartName").val(
							data.respoolParts[i].respoolPartName);
				}
				if (resPartId == data.respoolParts[i].respoolPartId) {
					$("#respoolpart").append(
							'<option selected="selected" value="'
									+ data.respoolParts[i].respoolPartId + '">'
									+ data.respoolParts[i].respoolPartName
									+ '</option>');
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
			if (typeof (resPartId) == "undefined" || resPartId == "") {
				resPartId = data.respoolParts[0].respoolPartId;
			}

			initOs(data.oss);
			var cpuNum = $("#cpuNum").val();
			var ramSize = $("#ramSize").val();
			queryItems(cpuNum, ramSize);

		}
	});
}

/* 查条目 */
function queryItems(cpu, ram) {
	var resPoolId = $("#respool").children('option:selected').val();
	var resPartId = $("#respoolpart").children('option:selected').val();

	var data = {
		respoolId : resPoolId,
		respoolPartId : resPartId
	};
	$
			.ajax({
				url : 'vmOnloadItemAction.action?struts.enableJSONValidation=true',
				type : 'POST',
				data : data,
				cache : false,
				dataType : 'json',
				async : false,
				success : function(data) {
					items.splice(0, items.length);
					standards.splice(0, standards.length);
					items = data.items;
					standards = data.standards;
					var cpu_arr = [];
					var ram_arr = [];
					if (standards.length > 0) {
						for (var i = 0; i < standards.length; i++) {
							cpu_arr.push(parseInt(standards[i].cpuNum));
							ram_arr.push(parseInt(standards[i].ramSize));
						}
					}

					cpu_arr = unique(cpu_arr).sort(sequence);
					ram_arr = unique(ram_arr).sort(sequence);

					$('span.cpu-items').empty();
					$('span.ram-items').empty();

					for (var i = 0; i < cpu_arr.length; i++) {
						var cpu_html = "";
						cpu_html = '<a  class="cpu btn hover" href="javascript:void(0);" onclick="active(this);" data-val="'
								+ cpu_arr[i] + '">' + cpu_arr[i] + '核</a>';
						$('span.cpu-items').append(cpu_html);
					}

					for (var i = 0; i < ram_arr.length; i++) {
						var ram_html = "";
						ram_html = '<a  class="ram btn hover" href="javascript:void(0);" onclick="active(this);" data-val="'
								+ ram_arr[i] + '">' + ram_arr[i] + 'GB</a>';
						$('span.ram-items').append(ram_html);
					}
					if (cpu.length > 0) {

						for (var j = 0; j < $('span.cpu-items a').length; j++) {
							var obj = $('span.cpu-items a')[j];
							var cpuNum = $(obj).data('val');
							if (cpu == cpuNum) {
								$(obj).click();
								break;
							}
						}
					}
					if (ram.length > 0) {
						for (var j = 0; j < $('span.ram-items a').length; j++) {
							var obj = $('span.ram-items a')[j];
							var ramSize = $(obj).data('val');
							if (ram == ramSize) {
								$(obj).click();
								break;
							}
						}
					}
				}
			});
}

/* 初始化镜像 */
function initOs(oss) {
	$("#osTypeSelect").empty();
	$("#osNameSelect").empty();

	for (var i = 0; i < oss.length; i++) {
		osTypes.push(oss[i].os);
		var obj = {};
		obj.id = oss[i].osId;
		obj.name = oss[i].osName;
		obj.type = oss[i].os;
		osNames.push(obj);
	}
	var osType;
	osTypes = unique(osTypes).sort();
	for (var j = 0; j < osTypes.length; j++) {
		if (j == 0) {
			$("#osName").val(osNames[j].name);
			osType = osTypes[j];
		}

		$("#osTypeSelect").append(
				'<option value="' + osTypes[j] + '">' + osTypes[j]
						+ '</option>');

	}
	initOsName(osType);

}

$("#osNameSelect").change(function() {
	changeosName();
});

// 改变选择镜像
function changeosName() {
	$("#osName").val($("#osNameSelect :selected").text());
	$("div.cart-bar span.ios").text($("#osNameSelect :selected").text());
}

$("#osTypeSelect").change(function() {
	var osType = $(this).find("option:selected").val();
	initOsName(osType);
});

function initOsName(osType) {
	$("#osNameSelect").empty();
	for (var i = 0; i < osNames.length; i++) {
		if (osNames[i].type == osType) {
			$("#osNameSelect").append(
					'<option value="' + osNames[i].id + '">' + osNames[i].name
							+ '</option>');
		}
	}
	changeosName();

}

/* 去重排序 */
function unique(arr) {
	var result = [], hash = {};
	for (var i = 0, elem; (elem = arr[i]) != null; i++) {
		if (!hash[elem]) {
			result.push(elem);
			hash[elem] = true;
		}
	}
	return result;
}

function sequence(a, b) {
	return a - b;
}

/* 按钮点击事件 */
function active(elem) {
	$(elem).addClass("active");
	$(elem).removeClass("hover");
	$(elem).siblings().removeClass("active");
	$(elem).siblings().addClass("hover");
	var arr = [];
	var cpuNum;
	var ramNum;
	if ($(elem).hasClass("cpu")) {
		$('span.ram-items>a').removeClass('disabled');
		$('.cart-bar span.cpu').text($(elem).text());
		cpuNum = $(elem).data('val');
		$('#cpuNum').val(cpuNum);
		for (var i = 0; i < standards.length; i++) {
			if (standards[i].cpuNum == cpuNum) {
				arr.push(standards[i].ramSize);
			}
		}
		arr = unique(arr);
		var rams = $('span.ram-items>a');
		for (var j = 0; j < rams.length; j++) {
			var ramSize = $(rams[j]).data('val');
			var flag = false;
			for (var k = 0; k < arr.length; k++) {
				if (arr[k] == ramSize) {
					flag = true;
				}
			}
			if (!flag) {
				$(rams[j]).addClass('disabled');
				$(rams[j]).removeClass('active');
			}
		}

	/*	ramNum = parseInt($(" a.ram.btn.active").data('val')) * 1024;
		$("#ramSize").val(ramNum);*/
	} else if ($(elem).hasClass("ram")) {
		$('.cart-bar span.ram').text($(elem).text());
		ramNum = parseInt($(elem).data('val')) * 1024;
		cpuNum = $(" a.cpu.btn.active").data('val');
		$('#cpuNum').val(cpuNum);
		$("#ramSize").val(ramNum);
	}

	if (typeof (cpuNum) == "number" && typeof (ramNum) == "number"
			&& !isNaN(cpuNum) && !isNaN(ramNum)) {
		$.ajax({
			url : 'queryChargesList.action',
			type : 'post',
			data : {
				'charges.cpuNumber' : cpuNum,
				'charges.memorySize' : ramNum,
				'charges.chargesType' : '0'
			}
		}).done(function(data) {
			var charge = eval('(' + data + ')');
			hourPrice = parseFloat(charge.chargesList[0].hourPrice);
			monthPrice = parseFloat(charge.chargesList[0].monthPrice);
			// 计费
			charging();

		});
	} else {
		$(".price").text("￥0.00");
	}
}

// 计费
function charging() {
	var price = '0.00';
	if (typeof (hourPrice) == "number" && typeof (hourPrice) == "number") {

		var a = 10000000;
		var disk = parseInt($("#data_disk").val().replace("GB", ""));
		var num = parseInt($("#num").val());

		var chargeType = $("input[name=chargeType]:checked").val();
		var time = parseInt($("#lengthTime").val());

		if (chargeType == "h") {
			price = hourPrice * a * disk * num * time / a;
		} else if (chargeType == "m") {
			price = monthPrice * a * disk * num * time / a;
		}
		price = price == 0 ? '0.00' : price.toFixed(2);
	}

	$(".price").text("￥" + price);

}

/* 初始化滑块 */
function initSlider() {
	
	$("#num").change(function() {
		$(".cart-bar span.num").text($(this).val());
	});

	var menu_width = $('#left').width();
	var slider = document.getElementById('slider');
	var fill = document.getElementById('fill');
	var wrapper_width = document.getElementById('wrapper').clientWidth;
	var slider_width = document.getElementById('slider').clientWidth;
	var wrapper_left = document.getElementById('wrapper').offsetLeft;
	var drag = 0;
	$('#slider').mousedown(function() {
		drag = 1;
	});

	$('#wrapper')
			.click(
					function(e) {
						if (e.target !== slider) {
							if (e.offsetX > (wrapper_width - slider_width)) {
								slider.style.left = wrapper_width
										- slider_width + 'px';
								fill.style.width = wrapper_width - slider_width
										+ 'px';
								$('#data_disk').val("5");
								$('#disk').val("5");
								$('.cart-bar span.data_disk').text("5TB");
							} else if (e.offsetX < slider_width) {
								slider.style.left = '0px';
								fill.style.width = '0px';
								$('#data_disk').val("0");
								$('#disk').val("0");
								$('.cart-bar span.data_disk').text("0TB");
							} else {
								slider.style.left = e.offsetX - slider_width
										/ 2 + 'px';
								fill.style.width = e.offsetX - slider_width / 2
										+ 'px';
								var size;
								var a=((e.offsetX / wrapper_width) * 5).toFixed(1);
								var b=((e.offsetX / wrapper_width) * 5).toFixed();
								
								if((a-b)>0.25){
									size=parseInt(b)+0.5;
								}else if(a>b&&(a-b)<0.25){
									size=b;
								}else if(a<b&&(b-a)>0.25){
									size=parseInt(b)-0.5;
								}else if(a<b&&(b-a)<0.25){
									size=b;
								}
								if(size==5){
									slider.style.left = wrapper_width - slider_width + 'px';
									fill.style.width = wrapper_width - slider_width + 'px';
								}else if(size==0){
									slider.style.left = '0px';
									fill.style.width = '0px';
								}
								
								$('#data_disk')
										.val(size);
								$('#disk')
										.val(size);
								$('.cart-bar span.data_disk')
										.text(size+ "TB");
							}
							// 计费
							charging();
						}
					});

	$('.slide-box')
			.mousemove(
					function(e) {
						if (drag == 1) {
							if ((e.pageX - wrapper_left - menu_width) > (wrapper_width - slider_width / 2)) {
								slider.style.left = wrapper_width
										- slider_width + 'px';
								fill.style.width = wrapper_width - slider_width
										+ 'px';
								$('#data_disk').val("5");
								$('#disk').val("5");
								$('.cart-bar span.data_disk').text("5TB");
							} else if ((e.pageX - wrapper_left - menu_width) < slider_width) {
								slider.style.left = '0px';
								fill.style.width = '0px';
								$('#data_disk').val("0");
								$('#disk').val("0");
								$('.cart-bar span.data_disk').text("0TB");
							} else {
								slider.style.left = (e.pageX - wrapper_left - menu_width)
										- slider_width + 'px';
								fill.style.width = (e.pageX - wrapper_left - menu_width)
										- slider_width + 'px';
								var size;
								var a=(((e.pageX - wrapper_left - menu_width) / wrapper_width) * 5).toFixed(1);
								var b=(((e.pageX - wrapper_left - menu_width) / wrapper_width) * 5).toFixed();
								if((a-b)>0.25){
									size=parseInt(b)+0.5;
								}else if(a>b&&(a-b)<0.25){
									size=b;
								}else if(a<b&&(b-a)>0.25){
									size=parseInt(b)-0.5;
								}else if(a<b&&(b-a)<0.25){
									size=b;
								}
								
								if(size==5){
									slider.style.left = wrapper_width - slider_width + 'px';
									fill.style.width = wrapper_width - slider_width + 'px';
								}else if(size==0){
									slider.style.left = '0px';
									fill.style.width = '0px';
								}
								
								$('#data_disk') .val(size);
								$('#disk') .val(size);
								$('.cart-bar span.data_disk') .text(size + "TB");
							}
							// 计费
							charging();
						}
					});

	$(document).mouseup(function() {
		drag = 0;
		timedrag = 0;
	});	
/*
	$("#num").change(function() {
		$(".cart-bar span.num").text($(this).val());
	});

	var menu_width = $('#left').width();
	var slider = document.getElementById('slider');
	var fill = document.getElementById('fill');
	var wrapper_width = document.getElementById('wrapper').clientWidth;
	var slider_width = document.getElementById('slider').clientWidth;
	var wrapper_left = document.getElementById('wrapper').offsetLeft;
	var drag = 0;
	$('#slider').mousedown(function() {
		drag = 1;
	});

	$('#wrapper')
			.click(
					function(e) {
						if (e.target !== slider) {
							if (e.offsetX > (wrapper_width - slider_width)) {
								slider.style.left = wrapper_width
										- slider_width + 'px';
								fill.style.width = wrapper_width - slider_width
										+ 'px';
								$('#data_disk').val("100GB");
								$('#disk').val("100");
								$('.cart-bar span.data_disk').text("100GB");
							} else if (e.offsetX < slider_width) {
								slider.style.left = '0px';
								fill.style.width = '0px';
								$('#data_disk').val("0GB");
								$('#disk').val("0");
								$('.cart-bar span.data_disk').text("0GB");
							} else {
								slider.style.left = e.offsetX - slider_width
										/ 2 + 'px';
								fill.style.width = e.offsetX - slider_width / 2
										+ 'px';
								$('#data_disk')
										.val(
												Math
														.round((e.offsetX / wrapper_width) * 100)
														+ "GB");
								$('#disk')
										.val(
												Math
														.round((e.offsetX / wrapper_width) * 100));
								$('.cart-bar span.data_disk')
										.text(
												Math
														.round((e.offsetX / wrapper_width) * 100)
														+ "GB");
							}
							// 计费
							charging();
						}
					});

	$('#wrapper')
			.mousemove(
					function(e) {
						if (drag == 1) {
							if ((e.pageX - wrapper_left - menu_width) > (wrapper_width - slider_width / 2)) {
								slider.style.left = wrapper_width
										- slider_width + 'px';
								fill.style.width = wrapper_width - slider_width
										+ 'px';
								$('#data_disk').val("100GB");
								$('#disk').val("100");
								$('.cart-bar span.data_disk').text("100GB");
							} else if ((e.pageX - wrapper_left - menu_width) < slider_width) {
								slider.style.left = '0px';
								fill.style.width = '0px';
								$('#data_disk').val("0GB");
								$('#disk').val("0");
								$('.cart-bar span.data_disk').text("0GB");
							} else {
								slider.style.left = (e.pageX - wrapper_left - menu_width)
										- slider_width + 'px';
								fill.style.width = (e.pageX - wrapper_left - menu_width)
										- slider_width + 'px';
								$('#data_disk')
										.val(
												Math
														.round(((e.pageX
																- wrapper_left - menu_width) / wrapper_width) * 100)
														+ "GB");
								$('#disk')
										.val(
												Math
														.round(((e.pageX
																- wrapper_left - menu_width) / wrapper_width) * 100));
								$('.cart-bar span.data_disk')
										.text(
												Math
														.round(((e.pageX
																- wrapper_left - menu_width) / wrapper_width) * 100)
														+ "GB");
							}
							// 计费
							charging();
						}
					});

	$(document).mouseup(function() {
		drag = 0;
		timedrag = 0;
	});*/
}

// 改变计费方式
function chargeTypeChange(obj) {
	var chargeTypeVal = obj.value;
	var timeNum = $("#lengthTime").val();
	if (chargeTypeVal == 'h') {// 按小时计费
		$(".time").text(timeNum + "小时");
		$(".timeType").text("小时");
	} else if (chargeTypeVal == 'm') {// 包月计费
		$(".time").text(timeNum + "个月");
		$(".timeType").text("个月");
	}
	// 计费
	charging();
}

$('#chargeText_hour').click(function() {
	$('#chargeType_hour').click();
});

$('#chargeText_month').click(function() {
	$('#chargeType_month').click();
});

// 改变计费时长
function changeLengthTime(obj) {
	var time = obj.value;
	var chargeType = $("input[name='chargeType']:checked").val();
	if (chargeType == 'h') {// 按小时计费
		$(".time").text(time + "小时");
	} else if (chargeType == 'm') {// 包月计费
		$(".time").text(time + "个月");
	}
	// 计费
	charging();
}

// 申请页面的数据缓存
var itemId;
var varRespoolId;
var varRespoolPartId;
var cpuNum;
var ramSize;
var discSize;
var standardId;
var lengthTime;

var rolsCount = 0;
var rols = 0;
var cpuNumMaxSize = 64;
var ramMaxSize = 102400 / 1024;
var diskMaxSize = 1024;
var checkGatewayOk = false;

// 改变选择条目
/*
 * function changeItem(tempItemId, templateId, tempStandardId, tempCpuNum,
 * tempRamSize, tempDiscSize, tempDescription, tempItemName) { itemId =
 * tempItemId; standardId = tempStandardId; cpuNum = tempCpuNum; ramSize =
 * tempRamSize; discSize = tempDiscSize; $("#cpuNum").val(cpuNum);
 * $("#ramSize").val(ramSize); $("#discSize").val(discSize);
 * $("#cpuNum2").text(cpuNum); $("#ramSize2").text(ramSize);
 * $("#discSize2").text(discSize); $("#cpuNum3").text(cpuNum);
 * $("#ramSize3").text(ramSize); $("#discSize3").text(discSize); //
 * $("#itemId").val(tempItemId); $("#standardId").val(tempStandardId);
 * $("#itemDescription").text("适用领域及特点描述: " + tempDescription);
 * $("#itemName").text(tempItemName);
 * 
 * var da_val = { standardId : tempStandardId, templateId : templateId }; $
 * .ajax({ url : 'vmOnloadOsAction.action?struts.enableJSONValidation=true',
 * type : 'POST', data : da_val, cache : false, dataType : 'json', success :
 * function(data) { if (data.resultRoute == "error") { window.location.href =
 * 'exceptionIntercepor.action'; return; } if (data.resultRoute == "failure") { $
 * .compMsg({ type : 'error', msg : '展示条目下镜像信息异常，将跳转到浏览页面', callback :
 * function() { window.location.href = 'vmQueryListAction.action?optState=1'; }
 * });
 * 
 * return; } // $("#osNameSelect").empty(); for (var i = 0; i <
 * data.osInfos.length; i++) {
 * 
 * if(i==0){ $("#osNameSelect").append( '<option value=""></option>'); }
 * 
 * $("#osNameSelect").append( '<option value="' + data.osInfos[i].osId + '">' +
 * data.osInfos[i].osName + '</option>'); } changeosName(); } }); }
 */

// 改变选择物理机
function changePmName() {
	$("#pmId").val($("#pmNameSelect :selected").val());
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
							$
									.compMsg({
										type : 'error',
										msg : "展示私网ip信息异常，将跳转到浏览页面",
										callback : function() {
											window.location.href = 'vmQueryListAction.action?optState=1';
										}
									});

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
	str += '<td>VLAN：<select id="vlanSelect' + rols + '" name="' + ethList
			+ 'vlanId" onchange="changeVlan(this)"></select></td>';
	str += '<td>IP段：<select class="max-select" id="ipsegmentSelect' + rols
			+ '" name="' + ethList
			+ 'ipsegmentId" onchange="changeIpsegment(this)"></select></td>';
	str += '<td>IP：<select id="privateIpSelect' + rols + '" name="' + ethList
			+ 'ip"></select></td>';
	str += '<td>网关：<input autocomplete="off" type="text" maxlength="15"  id="gateway'
			+ rols
			+ '"  name="'
			+ ethList
			+ 'gateway"   onblur="checkGateway(this)" /></td>';
	str += '<td><a class="del-btn" href="javascript:void(0);" onclick="deleteNet(this)">删除</a></td></tr>';
	str += '</tr>';
	$('#' + ethList).append(str);

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
					if (!jQuery.isEmptyObject(data.fieldErrors)) {
						// TODO
					}
					if (data.resultRoute == "error") {
						window.location.href = 'exceptionIntercepor.action';
						return;
					}
					if (data.resultRoute == "failure") {

						$
								.compMsg({
									type : 'error',
									msg : "展示资源池信息异常，将跳转到浏览页面",
									callback : function() {
										window.location.href = 'vmQueryListAction.action?optState=1';
									}
								});

						return;
					}
					var selectVal = "#vlanSelect" + rols;
					$(selectVal).empty();
					for (var i = 0; i < data.vlanList.length; i++) {
						if (i == 0) {
							$(selectVal).append('<option value=""></option>');
						}
						$(selectVal).append(
								'<option value="' + data.vlanList[i].vlanId
										+ '">' + data.vlanList[i].vlanName
										+ '</option>');
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
					msg : "验证网关段信息异常",

				});
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
						if (data.resultRoute == "error") {
							window.location.href = 'exceptionIntercepor.action';
							return;
						}
						if (data.resultRoute == "failure") {
							$
									.compMsg({
										type : 'error',
										msg : "展示IP段信息异常，将跳转到浏览页面",
										callback : function() {
											window.location.href = 'vmQueryListAction.action?optState=1';
										}
									});
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

//改变IP类型方式
function chargeIpTypeChange(obj) {
	var ipTypeVal = obj.value;
	if (ipTypeVal == '0') {// IPV4
		$("#ipType").val() = '0';
	} else if (ipTypeVal == '1') {// IPV6
		$("#ipType").val() = '1';
	}
}

// 提交按钮
function submitform() {
	if ($("#cpuNum").val() == "") {
		$.compMsg({
			type : 'error',
			msg : '请选择处理器核数规格'
		});
		return;
	}
	if ($("#ramSize").val() == "") {
		$.compMsg({
			type : 'error',
			msg : '请选择内存规格'
		});
		return;
	}
	var cpuNum = parseInt($("#cpuNum").val());
	var ramSize = parseInt($("#ramSize").val()) / 1024;
	for (var i = 0; i < standards.length; i++) {
		if (cpuNum == standards[i].cpuNum && ramSize == standards[i].ramSize) {
			$("#standardId").val(standards[i].standardId);
		}
	}

	// 校验cpu、内存和存储
	if (cpuNumMaxSize == null
			|| cpuNumMaxSize == ''
			|| (parseInt($("#cpuNum").val()) <= parseInt(cpuNumMaxSize) && parseInt($(
					"#cpuNum").val()) > 0)) {

	} else {
		$.compMsg({
			type : 'error',
			msg : '处理器不得超过资源池分区总数' + cpuNumMaxSize
		});
		return;
	}

	if (ramMaxSize == null
			|| ramMaxSize == ''
			|| (parseInt($("#ramSize").val() / 1024) <= parseInt(ramMaxSize) && parseInt($(
					"#ramSize").val()) > 0)) {

	} else {
		$.compMsg({
			type : 'error',
			msg : '内存(RAM)不得超过资源池分区总数' + ramMaxSize
		});
		return;
	}

	if (diskMaxSize == null
			|| diskMaxSize == ''
			|| (parseInt($("#data_disk").val().replace("GB", "")) <= parseInt(diskMaxSize) && parseInt($(
					"#data_disk").val().replace("GB", "")) > 0)) {

	} else {
		$.compMsg({
			type : 'error',
			msg : '存储空间不得超过资源池分区总数' + diskMaxSize
		});
		return;
	}

	// 校验虚拟机名称
	var vmName = $("#vmName").val();
	if (vmName == "" || vmName == null) {
		$.compMsg({
			type : 'error',
			msg : '主机名称不能为空！'
		});
		return;
	}

	// 校验所属业务
	var appId = $("#appId").val();
	if (appId == "" || appId == null) {
		$.compMsg({
			type : 'error',
			msg : '所属业务不能为空！'
		});
		return;
	}

	// 校验计费时长输入内容
	var chargesLengthTime = $("#lengthTime").val();
	var reguNum = new RegExp("^[1-9][0-9]*$");
	if (!reguNum.test(chargesLengthTime)) {
		$.compMsg({
			type : 'error',
			msg : '计费时长输入格式输入有误！'
		});
		return false;
	}

	// 是否配置ip
	/*
	if ($("#ApplyNet").attr("checked") == "checked") {
		// 验证网卡
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
					msg : "重复网卡的IP为：" + nary[i]
				});
				return;
			}
		}

		var s = $("input[name$='gateway']");
		for (var i = 0; i < s.length; i++) {
			if (s[i].value == null || s[i].value == "") {
				$.compMsg({
					type : 'error',
					msg : "网卡内容有空值"
				});
				return;
			} else {
				if (!checkIP(s[i].value)) {
					return;
				}
			}
		}

		var s = $("table select");
		for (var i = 0; i < s.length; i++) {
			if (s[i].value == null || s[i].value == "") {
				$.compMsg({
					type : 'error',
					msg : '网卡内容有空值！'
				});
				return;
			}
		}

		if (!checkGatewayOk) {
			$.compMsg({
				type : 'error',
				msg : '网关IP非法！'
			});
			return;
		}

	}else{
		$.compMsg({
			type : 'error',
			msg : '请申请网络！'
		});
		return;
	}*/

	var tempVmRemark = $('#vmRemark').val();
	if (tempVmRemark.length > 100) {
		$.compMsg({
			type : 'error',
			msg : '备注信息不能超过100个字，请重新输入！'
		});

	}
	$("#vmApplyInfoAction").submit();
}

// 绑定业务
function bindBusiness() {
	$("#ethList1").text("");
	$("#ethList2").text("");
	$("#ethList3").text("");
	$("#ethList4").text("");
	$("#ethList5").text("");
	// 弹出页弹出前，已选择的业务ID值，首次业务ID值为undefined
	var lastAppId = $('#appId').val();
	$.dialog({
		title : '绑定业务',
		init : loadBusinessList('businessListJson.action', lastAppId),
		content : document.getElementById('bindBusiness'),
		ok : function() {
			// 获取选择的业务ID（为了传到后台入库）以及业务名称（用于返回主页面显示）
			var appName = $(
					'#businessListTbody [name="businessCheckbox"]:checked')
					.parents('td').next().html();
			var appId = $(
					'#businessListTbody [name="businessCheckbox"]:checked')
					.val();
			if (appId == null || appId == '') {
				$.compMsg({
					type : 'error',
					msg : "请选择一个业务！"
				});
				return false;
			}

			$("#bindDiv").children('span').remove();
			$("#bindDiv").children('a').html("重新绑定");
			$("#bindDiv").children('a').before(
					'<span style="margin-right:30px">' + appName + '</span>');

			if (!$("#appId").val()) {
				$('#bindDiv').append(
						'<input type="hidden" id="appId" name="appId" value="'
								+ appId + '"/>');
			} else {
				$("input[name=appId]").val(appId);
			}
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}

var getPageData = function(url) {
	loadBusinessList(url, $(
			'#businessListTbody [name="businessCheckbox"]:checked').val());
};

var loadBusinessList = function(url, lastAppId) {
	var queryBusinessName = $('#queryBusinessName').val();
	var queryData = {};
	if (url == 'businessListJson.action') {
		queryData = {
			pageSycn : 'false',
			'queryBusiness.name' : queryBusinessName
		};
	}
	$
			.post(
					url,
					queryData,
					function(data) {
						$('#businessListTbody').empty();
						$
								.each(
										data.businessInfoList,
										function(index, business) {
											var trHtml = '<tr>'
													+ '<td align="center"><input type="radio" name="businessCheckbox" value="'
													+ business.businessId
													+ '" ';
											// 当用户重新绑定业务时，弹出页默认显示上一次的选择
											if (business.businessId == lastAppId) {
												trHtml += ' checked = "checked"';
											}
											trHtml += '/></td>'
													+ '<td>'
													+ business.name
													+ '</td>'
													+ '<td>'
													+ (business.description == null ? ''
															: business.description)
													+ '</td>' + '</tr>';
											$('#businessListTbody').append(
													trHtml);
										});
						$('#businessListPageBarDiv').html(data.pageBar);
					}).fail(function() {
				$.compMsg({
					type : 'error',
					msg : '获取业务列表！'
				});
			});
};

// 删除按钮
function deleteNet(obj) {
	if (rolsCount == 0) {
		return;
	}
	rolsCount--;
	$(obj).parents('tr').remove();
}

/* 点击申请网络checkbox */
$('#ApplyNet').click(function() {
	var appId = $("#appId").val();
	var num = $("#num").val();
	if ($("#ApplyNet").prop("checked") == true) {
		if (appId == "" || appId == null) {
			$.compMsg({
				type : 'warn',
				msg : '请先绑定业务！'
			});

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
		$("table.network-table").hide();
		$("table.network-table tbody").empty();
	}

});

function customChange() {
	$("#cpuNum2").text($("#cpuNum").val());
	$("#ramSize2").text($("#ramSize").val());
	$("#discSize2").text($("#discSize").val());
}
// 展示收费标准
function showChargesInfo() {
	$.dialog({
		title : '资费管理',
		init : initChargesInfo(),
		content : document.getElementById('chargesListDiv'),
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}
function initChargesInfo() {
	var cpuNumber = $("#cpuNumber").val();
	var memorySize = $("#memorySize").val();
	var queryData = {
		'charges.cpuNumber' : cpuNumber,
		'charges.memorySize' : memorySize,
		'charges.chargesType' : '0'
	};

	$.post(
			"queryChargesList.action",
			queryData,
			function(data) {
				$('#chargesListTbody').empty();
				$.each(data.chargesList, function(index, charges) {
					var trHtml = "<tr><td>" + charges.cpuNumber + "</td>"
							+ "<td>" + charges.memorySize + "</td>" + "<td>"
							+ charges.hourPrice + "</td>" + "<td>"
							+ charges.monthPrice + "</td>";
					trHtml += "<td>"
							+ (charges.desc == null ? "" : charges.desc)
							+ '</td></tr>';
					$('#chargesListTbody').append(trHtml);
				});
				$('#chargesListPageBarDiv').html(data.pageBar);
			}, "json").fail(function(e) {
		$.compMsg({
			type : 'error',
			msg : '获取资费列表失败！'
		});
	});
}