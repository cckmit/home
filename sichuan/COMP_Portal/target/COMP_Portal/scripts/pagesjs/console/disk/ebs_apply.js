$(function() {
	$("#ebs").siblings().removeClass("active");
	$("#ebs").addClass("active");
	$('div.cart-bar').removeClass("none");
	$('div.cart-bar').load('../cart-bar/ebs_cart.jsp');
	queryResPool();
	initSlider();
	initChargesInfo();
	
	appShow1WithUser();

	$("#respool").change(function() {
		varRespoolId = $(this).children('option:selected').val();
		varRespoolPartId = "";
		//onloadResPool(varRespoolId, varRespoolPartId);
		queryResPool(varRespoolId, varRespoolPartId);
	});

	$("#respoolpart").change(function() {
		varRespoolId = $("#respool").children('option:selected').val();
		varRespoolPartId = $(this).children('option:selected').val();
		//onloadResPool(varRespoolId, varRespoolPartId);
		queryResPool(varRespoolId, varRespoolPartId);
	});

});

function appShow1WithUser() {
	var userId = document.getElementById("userId").value;
	if (userId != "admin") {
		$.ajax({
			url : 'businessListJson.action?struts.enableJSONValidation=true',
			type : 'POST',
			data : null,
			cache : false,
			async : false,
			dataType : 'json',
			success : function(data) {
				$("#bindDiv1").children('span').remove();
				$("#bindDiv1").html(
						'<span style="margin-right:30px" id="cartAppName" >' + data.businessInfoList[0].name + '</span>'
						+ '<input type="hidden" id="appId1" name="appId" value="' + data.businessInfoList[0].businessId + '"/>');
			}
		});
	}
	
}

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
		}
	});
}

$("#num").change(function() {
	$(".cart-bar span.num").text($(this).val());
	// 计费
	charging();
});

/* 初始化滑块 */
function initSlider() {
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
								$('#discSize').val("5");
								$('#disk').val("5");
								$('.cart-bar span#cart-discSize')
										.text("5TB");
							} else if (e.offsetX < slider_width) {
								slider.style.left = '0px';
								fill.style.width = '0px';
								$('#discSize').val("0");
								$('#disk').val("0");
								$('.cart-bar span#cart-discSize').text("0TB");
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
								
								$('#discSize')
										.val(size);
								$('#disk')
										.val(size);
								$('.cart-bar span#cart-discSize')
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
								$('#discSize').val("5");
								$('#disk').val("5");
								$('.cart-bar span#cart-discSize')
										.text("5TB");
							} else if ((e.pageX - wrapper_left - menu_width) < slider_width) {
								slider.style.left = '0px';
								fill.style.width = '0px';
								$('#discSize').val("0");
								$('#disk').val("0");
								$('.cart-bar span#cart-discSize').text("0TB");
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
								
								$('#discSize') .val(size);
								$('#disk') .val(size);
								$('.cart-bar span#cart-discSize') .text(size + "TB");
							
							}
							// 计费
							charging();
						}
					});

	$(document).mouseup(function() {
		drag = 0;
		timedrag = 0;
	});
}

//磁盘大小输入change事件
$("#discSize").change(function(){
	var size=$("#discSize").val();
	if(size%0.5!==0||size>5||size<0){
		$.compMsg({
			type : 'error',
			msg : '请输入0至5TB间0.5TB倍数的值！'
		});
		$("#discSize").val($("#disk").val());
		return;
	}
	var menu_width = $('#left').width();
	var slider = document.getElementById('slider');
	var fill = document.getElementById('fill');
	var wrapper_width = document.getElementById('wrapper').clientWidth;
	var slider_width = document.getElementById('slider').clientWidth;
	var wrapper_left = document.getElementById('wrapper').offsetLeft;
	if(size==5){
		slider.style.left = wrapper_width - slider_width + 'px';
		fill.style.width = wrapper_width - slider_width + 'px';
	}else if(size==0){
		slider.style.left = '0px';
		fill.style.width = '0px';
	}else{
		slider.style.left = (size*wrapper_width/5-slider_width/2)+"px";
		fill.style.width = (size*wrapper_width/5)+"px";
	}
	$("#disk").val(size);
	$('.cart-bar span#cart-discSize').text(size+"TB");
});

// 改变选择条目
function changeItem(tempItemId, tempStandardId, tempResourceType, tempDiscSize,
		tempDescription, tempItemName) {
	itemId = tempItemId;
	standardId = tempStandardId;
	resourceType = tempResourceType;
	discSize = tempDiscSize;
	$("#discSize").val(discSize);
	$("#discSize2").text(discSize);
	$("#discSize3").text(discSize);
	$("#resourceType").val(resourceType);
	if (resourceType == "1") {
		$("#resourceType2").text("云硬盘");
		$("#resourceType3").text("云硬盘");
	} else if (resourceType == "2") {
		$("#resourceType2").text("物理硬盘");
		$("#resourceType3").text("物理硬盘");
	}
	$("#standardId").val(tempStandardId);
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
// 提交按钮
function submitform() {
	if (validate())
		$("#ebsApplyInfoAction").submit();
}

// 校验
function validate() {
	var appId = $("#appId").val();
	var userId = document.getElementById("userId").value;
	if (userId != "admin") {
		appId = $("#appId1").val();
	}
	
	// 业务绑定
	if (appId == "" || appId == null) {
		$.compMsg({
			type : 'error',
			msg : '所属企业客户不能为空，请选择'
		});
		return false;
	}

	// 名称
	if (!$("#ebsName").val()) {
		$.compMsg({
			type : 'error',
			msg : '云硬盘名称不能为空，请重新输入'
		});
		$("#ebsName").focus();
		return false;
	}

	// 名称长度
	if ($("#ebsName").val().length > 25) {
		$.compMsg({
			type : 'error',
			msg : '云硬盘名称不能超过25个字，请重新输入!'
		});
		$("#ebsName").select();
		return false;
	}

	if ($("#disk").val()=='0') {
		$.compMsg({
			type : 'error',
			msg : '云硬盘大小不能为0!'
		});
		return false;
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

	return true;
}

// --------------------------
// 绑定业务
function bindBusiness() {
	// 弹出页弹出前，已选择的业务ID值，首次业务ID值为undefined
	var lastAppId = $('#businessListTbody [name="businessCheckbox"]:checked')
			.val();
	$.dialog({
		title : '绑定企业客户',
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
					msg : "请选择一个企业客户！"
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
				$("#appId").val(appId);
			}
			$("#cart-app").text(appName);
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
	var resPoolId = $('#respool').val();
	var queryData = {};
	if (url == 'businessListJson.action') {
		queryData = {
			pageSycn : 'false',
			'queryBusiness.name' : queryBusinessName,
			'queryBusiness.resPoolId':resPoolId
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
					msg : '获取企业客户列表！'
				});
			});
};
// ------------------------------------

// 展示收费标准
function showChargesInfo() {
	$.dialog({
		title : '资费管理',
		// init : ,
		content : document.getElementById('chargesListDiv'),
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}

var hourPrice;
var monthPrice;

function initChargesInfo() {
	var queryData = {
		'charges.chargesType' : '1'
	};

	$.post(
			"queryChargesList.action",
			queryData,
			function(data) {
				$('#chargesListTbody').empty();
				$.each(data.chargesList, function(index, charges) {
					var trHtml = "<tr><td>" + charges.hourPrice + "</td>"
							+ "<td>" + charges.monthPrice + "</td>";
					trHtml += "<td>"
							+ (charges.desc == null ? "" : charges.desc)
							+ '</td></tr>';
					$('#chargesListTbody').append(trHtml);

					hourPrice = charges.hourPrice;
					monthPrice = charges.monthPrice;
					charging();
				});
				$('#chargesListPageBarDiv').html(data.pageBar);
			}, "json").fail(function() {
		$.compMsg({
			type : 'error',
			msg : '获取资费列表!'
		});
	});
}

$("#ebsName").blur(function() {
	var ebsName = $("#ebsName").val();
	$("#cart-name").text(ebsName);
});

function charging() {
	var a = 10000000;
	var diskSize = parseFloat($("#discSize").val().replace("TB", ""));
	var num = parseInt($("#num").val());

	var chargeType = $("input[name=chargeType]:checked").val();
	var time = parseInt($("#lengthTime").val());
	var price;
	if (chargeType == "h") {
		price = hourPrice * a * diskSize * num * time / a;
	} else if (chargeType == "m") {
		price = monthPrice * a * diskSize * num * time / a;
	}

	$(".price").text("￥" + price);

}