var varRespoolId;
var varRespoolPartId;
var standardId;
var itemId = "";
$(function() {

	$("#lb").siblings().removeClass("active");
	$("#lb").addClass("active");
	$('div.cart-bar').removeClass("none");
	$('div.cart-bar').load('../cart-bar/lb_cart.jsp');
	
	queryResPool("");
	appShow1WithUser();
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
				initNet();
			}
		});
	}
	
}

function queryLbBatch() {
	var lbname = $('#lbname').val();
	var lbip = $('#lbip').val();

	var form = $("<form/>");
	form.attr({
		action : "loadBalanceAction.action",
		method : "post"
	});
	form.append('<input type="hidden" name="lbname" value="' + lbname + '"/>');
	form.append('<input type="hidden" name="lbip" value="' + lbip + '"/>');
	$('body').append(form);
	form.submit();
}

function getPageData() {
	queryLbBatch();
}
function goToPage(lbid) {
	if (lbid == '') {

	} else {
		$("#lbid").val(lbid);
		$("#lbid").val();
		$("#gotoForm").attr('action', 'LBdetailAction.action');
		$("#gotoForm").submit();
	}

}

$("#respool").change(function() {
	varRespoolId = $(this).children('option:selected').val();
	varRespoolPartId = "";
	itemId = "";
	queryResPool(varRespoolId, varRespoolPartId);

});

// 加载资源池及分区信息
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

/*
 * function onloadResPool(tempRespoolId, tempRespoolPartId) {
 * 
 * var da_val = { itemId : itemId, respoolId : tempRespoolId, respoolPartId :
 * tempRespoolPartId }; $ .ajax({ url :
 * 'vmOnloadResPoolAction.action?struts.enableJSONValidation=true', type :
 * 'POST', data : da_val, cache : false, dataType : 'json', success :
 * function(data) {
 * 
 * if (!jQuery.isEmptyObject(data.fieldErrors)) { if
 * (data.fieldErrors.respoolId.length > 0) { $.compMsg({ type : 'error', msg :
 * data.fieldErrors.respoolId }); return; } if (data.fieldErrors.itemId.length >
 * 0) $.compMsg({ type : 'error', msg : data.fieldErrors.itemId }); return; } if
 * (data.resultRoute == "error") { window.location.href =
 * 'exceptionIntercepor.action'; return; } if (data.resultRoute == "failure") { $
 * .compMsg({ type : 'error', msg : "展示资源池信息异常，将跳转到浏览页面", callback : function() {
 * window.location.href = 'loadBalanceAction.action?optState=1'; } }); return; }
 * $("#respool").empty(); for (var i = 0; i < data.respools.length; i++) { if (i ==
 * 0) { $("#respoolName").val(data.respools[i].respoolName); } if (varRespoolId !=
 * data.respools[i].respoolId) { $("#respool").append( '<option value="' +
 * data.respools[i].respoolId + '">' + data.respools[i].respoolName + '</option>'); }
 * else { $("#respool").append( '<option selected="selected" value="' +
 * data.respools[i].respoolId + '">' + data.respools[i].respoolName + '</option>');
 * varRespoolId = data.respools[i].respoolId;
 * $("#respoolName").val(data.respools[i].respoolName); } } // 资源池无分区判断 if
 * (data.respoolParts.length == 0) { // console.js
 * maskNotPart($(".details-con1")); $("#respoolpart").empty();
 * $("#topItems").empty(); $("#respoolpart").append('<option value=""></option>');
 * $("#respoolPartName").val(""); return false; } else { // console.js
 * removeMask($(".details-con1")); }
 * 
 * $("#respoolpart").empty(); for (var i = 0; i < data.respoolParts.length; i++) {
 * if (i == 0) { $("#respoolPartName").val(
 * data.respoolParts[i].respoolPartName); } if (varRespoolPartId ==
 * data.respoolParts[i].respoolPartId) { $("#respoolpart") .append( '<option
 * selected="selected" value="' + data.respoolParts[i].respoolPartId + '">' +
 * data.respoolParts[i].respoolPartName + '</option>'); varRespoolPartId =
 * data.respoolParts[i].respoolPartId; $("#respoolPartName").val(
 * data.respoolParts[i].respoolPartName); } else { $("#respoolpart") .append( '<option
 * value="' + data.respoolParts[i].respoolPartId + '">' +
 * data.respoolParts[i].respoolPartName + '</option>'); } } if (typeof
 * (varRespoolPartId) == "undefined" || varRespoolPartId == "") {
 * varRespoolPartId = data.respoolParts[0].respoolPartId; } } }); }
 */
// 判断ip地址的合法性
function checkIP(ip) {
	var exp = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
	var reg = ip.value.match(exp);
	if (reg == null) {
		$.compMsg({
			type : 'error',
			msg : "ip地址: " + ip.value + " 不合法"
		});
		return false;
	}
	return true;
}
// 判断负载均衡名称
function checkname() {
	var lbname = $('#lbname').val();
	da_val = {
		lbname : lbname,
	};
	$.ajax({
		url : 'LBcheckAction.action?struts.enableJSONValidation=true',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {
			if (data.LBinfo.lbname != "" && data.LBinfo.lbname != null) {
				$.compMsg({
					type : 'error',
					msg : "负载均衡名称已存在！"
				});
			} else {
				$("cart-name").text(lbname);
			}
		}
	});
}

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
				displayError("请选择一个企业客户！");
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

			$("#vlanSelect").empty();
			$("#ipsegmentSelect").empty();
			$("#privateIpSelect").empty();
			initNet();
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
}
// 删除按钮
function deleteNet(obj) {
	if (rolsCount == 0) {
		return;
	}
	rolsCount--;
	$(obj).parent().remove();
	$('#addnet').show();
}

$("#vlanSelect").click(function checkradioApplyNet() {
	var appId =$("input[name=appId]").val();
	if (appId == "" || appId == null) {
		$.compMsg({
			type : 'error',
			msg : "请先绑定企业客户"
		});
		return;
	}
});

function initNet() {
	var appId = $("input[name=appId]").val();
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
				async : false,
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
										window.location.href = 'loadBalanceAction.action?optState=1';
									}
								});
						return;
					}
					$("#vlanSelect").empty();
					$("#ipType").val("");
					if (data.vlanList.length > 0) {
						for (var i = 0; i < data.vlanList.length; i++) {
							if(null != data.vlanList[i].ipSegmentId){
								$("#vlanSelect").append(
										'<option value="' + data.vlanList[i].vlanId
												+ '">' + data.vlanList[i].vlanName
												+ '</option>');
							}
//							$("#vlanSelect").append(
//									'<option value="' + data.vlanList[i].vlanId
//											+ '">' + data.vlanList[i].vlanName
//											+ '</option>');
						}
						var vlanId = data.vlanList[0].vlanId;
						var ipsegmentId = data.vlanList[0].ipSegmentId;
						//var respoolId = $("#respool").val()
						var respoolId = document.getElementById("respool").value;
						//alert(respoolId);
						if(null != ipsegmentId){
						$("#cart-vlan").text(data.vlanList[0].vlanName);
						$
								.ajax({
									type : "POST",
									url : 'vmOnloadIpsegmentAction.action',
									data : {
										"vlanId" : vlanId,
										"appId" : appId,
										"respoolId" : respoolId
									},
									dataType : "JSON",
									async : false,
									cache : false,
									success : function(data) {
										$("#ipsegmentSelect").empty();
										if (data.ipSegmentList.length > 0) {
											for (var i = 0; i < data.ipSegmentList.length; i++) {
												$("#ipsegmentSelect")
														.append(
																'<option value="'
																		+ data.ipSegmentList[i].ipSegmentId
																		+ '">'
																		+ data.ipSegmentList[i].ipSegment
																		+ '</option>');
											}
											var ipSegmentId = data.ipSegmentList[0].ipSegmentId;
											$("#cart-ips")
													.text(
															data.ipSegmentList[0].ipSegment);
											var ipType = data.ipSegmentList[0].ipType;
											$("#ipType").val(ipType);
											var lbflag = "lbflag";
											$
													.ajax({
														url : 'vmOnloadPrivateIpAction.action?struts.enableJSONValidation=true',
														type : 'POST',
														data : {
															"logicVlanId" : vlanId,
															"ipSegmentId" : ipSegmentId,
															"lbflag" : lbflag
														},
														cache : false,
														async : false,
														dataType : 'json',
														success : function(data) {

															$(
																	"#privateIpSelect")
																	.empty();
															for (var i = 0; i < data.ipInfos.length; i++) {
																$(
																		"#privateIpSelect")
																		.append(
																				'<option value="'
																						+ data.ipInfos[i].ip
																						+ '">'
																						+ data.ipInfos[i].ip
																						+ '</option>');
															}
															$("#cart-ip")
																	.text(
																			data.ipInfos[0].ip);
														}
													});
										}
									}
								});
					}
					}
				}
			});
}

// 改变vlan，ajax请求IP段
$('#vlanSelect')
		.change(
				function() {
					var vlanId = $('#vlanSelect').val();
					$("#cart-vlan").text(
							$('#vlanSelect').find("option:selected").text());
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
															window.location.href = 'loadBalanceAction.action?optState=1';
														}
													});
											return;
										}

										$("#ipsegmentSelect").empty();
										$("#ipType").val("");
										for (var i = 0; i < data.ipSegmentList.length; i++) {
											$("#ipsegmentSelect")
													.append(
															'<option value="'
																	+ data.ipSegmentList[i].ipSegmentId
																	+ '">'
																	+ data.ipSegmentList[i].ipSegment
																	+ '</option>');
										}
										$("#cart-ips").text(
												$('#ipsegmentSelect').find(
														"option:selected")
														.text());

										$("#cart-ips")
												.text(
														data.ipSegmentList[0].ipSegment);
										var ipType = data.ipSegmentList[0].ipType;
										$("#ipType").val(ipType);
										var ipSegmentId = data.ipSegmentList[0].ipSegmentId;
										var lbflag = "lbflag";
										$
												.ajax({
													url : 'vmOnloadPrivateIpAction.action?struts.enableJSONValidation=true',
													type : 'POST',
													data : {
														"logicVlanId" : vlanId,
														"ipSegmentId" : ipSegmentId,
														"lbflag" : lbflag
													},
													cache : false,
													async : false,
													dataType : 'json',
													success : function(data) {

														$("#privateIpSelect")
																.empty();
														for (var i = 0; i < data.ipInfos.length; i++) {
															$(
																	"#privateIpSelect")
																	.append(
																			'<option value="'
																					+ data.ipInfos[i].ip
																					+ '">'
																					+ data.ipInfos[i].ip
																					+ '</option>');
														}
														$("#cart-ip")
																.text(
																		data.ipInfos[0].ip);
													}
												});

									}
								});
					}
				});

$("#ipsegmentSelect")
		.change(

				function() {
					var vlan = $('#vlanSelect').val();
					var ipSegmentId = $("#ipsegmentSelect").val();
					var lbflag = "lbflag";
					if ("" != vlan && "" != ipSegmentId) {

						var da_val = {
							logicVlanId : vlan,
							ipSegmentId : ipSegmentId,
							lbflag : lbflag
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
														msg : "展示IP段信息异常，将跳转到浏览页面",
														callback : function() {
															window.location.href = 'loadBalanceAction.action?optState=1';
														}
													});
											return;
										}
										$("#privateIpSelect").empty();
										for (var i = 0; i < data.ipInfos.length; i++) {
											$("#privateIpSelect")
													.append(
															'<option value="'
																	+ data.ipInfos[i].ip
																	+ '">'
																	+ data.ipInfos[i].ip
																	+ '</option>');
										}
										$("#cart-ip").text(data.ipInfos[0].ip);
									}
								});
					}
				});

$("#privateIpSelect").change(function() {
	var ip = $("#privateIpSelect").find("option:Selected").text();
	$("#cart-ip").text(ip);
});

function submitform() {
	// 校验负载均衡名称
	var lbname = $("#lbname").val();
	if (lbname == "" || lbname == null) {
		$.compMsg({
			type : 'error',
			msg : '负载均衡名称不能为空！'
		});
		return;
	}

	// 校验所属业务
	var appId = $("#appId").val();
	var userId = document.getElementById("userId").value;
	if (userId != "admin") {
		appId = $("#appId1").val();
	}
	if (appId == "" || appId == null) {
		$.compMsg({
			type : 'error',
			msg : '所属企业客户不能为空！'
		});
		return;
	}
	// 校验负载均衡流量协议
	var protocal = $("#protocal").val();
	if (protocal == "" || protocal == null) {
		$.compMsg({
			type : 'error',
			msg : '负载均衡流量协议不能为空！'
		});
		return;
	}

	var throughput = $("#throughput").val();
	if (throughput == "" || throughput == null) {
		$.compMsg({
			type : 'error',
			msg : '负载均衡吞吐能力不能为空！'
		});
		return;
	}
	if (!checkNumber(throughput)) {
		$.compMsg({
			type : 'error',
			msg : '吞吐能力填写必须为数字！'
		});
		return;
	}
	var connectNum = $("#connectNum").val();
	if (connectNum == "" || connectNum == null) {
		$.compMsg({
			type : 'error',
			msg : '负载均衡并发链接不能为空！'
		});
		return;
	}
	if (!checkNumber(connectNum)) {
		$.compMsg({
			type : 'error',
			msg : '并发链接填写必须为数字！'
		});
		return;
	}
	var newConnectNum = $("#newConnectNum").val();
	if (newConnectNum == "" || newConnectNum == null) {
		$.compMsg({
			type : 'error',
			msg : '负载均衡链接速度不能为空！'
		});
		return;
	}
	if (!checkNumber(newConnectNum)) {
		$.compMsg({
			type : 'error',
			msg : '链接速度填写必须为数字！'
		});
		return;
	}
	
	// 校验负载均衡IP地址
	var privateIp = $("#privateIpSelect").val();
	if (privateIp == "" || privateIp == null) {
		$.compMsg({
			type : 'error',
			msg : '负载均衡浮动IP不能为空！'
		});
		return;
	}

	var thisForm = document.forms["LbsubmitAction"];
	document.forms["LbsubmitAction"].submit();
}
function checkNumber(theObj) {
	var reg = /^[0-9]+.?[0-9]*$/;
	if (reg.test(theObj)) {
		return true;
	}
	return false;
}

$("#lbname").blur(function() {
	var lbname = $("#lbname").val();
	$("#cart-name").text(lbname);
});
$("#lbport").blur(function() {
	var lbport = $("#lbport").val();
	$("#cart-port").text(lbport);
});
$("#throughput").blur(function() {
	var throughput = $("#throughput").val();
	$("#cart-throughput").text(throughput);
});
$("#connectNum").blur(function() {
	var connectNum = $("#connectNum").val();
	$("#cart-connectNum").text(connectNum);
});
$("#newConnectNum").blur(function() {
	var newConnectNum = $("#newConnectNum").val();
	$("#cart-newConnectNum").text(newConnectNum);
});

$("#lbType").change(function() {
	var lbType = $("#lbType").find("option:selected").text();
	$("#cart-lbtype").text(lbType);
});

$("#strategy").change(function() {
	var strategy = $("#strategy").find("option:selected").text();
	$("#cart-strategy").text(strategy);
});

$("#protocal").change(function() {
	var protocal = $("#protocal").find("option:selected").text();
	$("#cart-protocal").text(protocal);
});
