//申请页面的数据缓存
var itemId = '';
var varRespoolId;
var varRespoolPartId;
var discSize;
var standardId;
var lengthTime;

$(function() {
	$("#vFW").siblings().removeClass("active");
	$("#vFW").addClass("active");

	onloadResPool("");

	// 资源池联动
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
						'<span style="margin-right:30px">' + data.businessInfoList[0].name + '</span>'
						+ '<input type="hidden" id="appId1" name="appId" value="' + data.businessInfoList[0].businessId + '"/>');
			}
		});
	}
	
}

// 加载资源池及分区信息
function onloadResPool(tempRespoolId, tempRespoolPartId) {
	var da_val = {
		itemId : itemId,
		respoolId : tempRespoolId,
		respoolPartId : tempRespoolPartId
	};
	$.ajax({
		url : 'ebsOnloadResPoolAction.action?struts.enableJSONValidation=true',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {

			if (!jQuery.isEmptyObject(data.fieldErrors)) {
				if (data.fieldErrors.respoolId.length > 0)
					alert(data.fieldErrors.respoolId);
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
				window.location.href = 'ebsQueryListAction.action?optState=1';
				return;
			}
			$("#respool").empty();
			for (var i = 0; i < data.respools.length; i++) {
				if (i == 0) {
					$("#respoolName").val(data.respools[i].respoolName);
				}
				if (varRespoolId != data.respools[i].respoolId) {
					$("#respool").append('<option value="' + data.respools[i].respoolId + '">' + data.respools[i].respoolName + '</option>');
				} else {
					$("#respool").append(
							'<option selected="selected" value="' + data.respools[i].respoolId + '">' + data.respools[i].respoolName + '</option>');
					varRespoolId = data.respools[i].respoolId;
					$("#respoolName").val(data.respools[i].respoolName);
				}
			}

			// 资源池无分区判断
			if (data.respoolParts.length == 0) {
				// console.js
				maskNotPart($(".details-con"));
				$("#respoolpart").empty();
				$("#topItems").empty();
				$("#respoolpart").append('<option value=""></option>');
				$("#respoolPartName").val("");
				return false;
			}

			$("#respoolpart").empty();
			for (var i = 0; i < data.respoolParts.length; i++) {
				if (i == 0) {
					$("#respoolPartName").val(data.respoolParts[i].respoolPartName);
				}
				if (varRespoolPartId == data.respoolParts[i].respoolPartId) {
					$("#respoolpart").append(
							'<option selected="selected" value="' + data.respoolParts[i].respoolPartId + '">' + data.respoolParts[i].respoolPartName
									+ '</option>');
					varRespoolPartId = data.respoolParts[i].respoolPartId;
					$("#respoolPartName").val(data.respoolParts[i].respoolPartName);

				} else {
					$("#respoolpart").append(
							'<option value="' + data.respoolParts[i].respoolPartId + '">' + data.respoolParts[i].respoolPartName + '</option>');
				}

			}
			if (typeof (varRespoolPartId) == "undefined" || varRespoolPartId == "") {
				varRespoolPartId = data.respoolParts[0].respoolPartId;
			}

			// onloadItem();
		}
	});
}

// 提交按钮
function submitform() {
	if (validate())
		document.forms[0].submit();
}

// 校验
function validate() {
	
	// 选择IP
/*	if (!$("#ip").val()) {
		$.compMsg({
			type : 'error',
			msg : 'IP不能为空，请重新选择'
		});
		return false;
	}
*/
	var appId = $("#appId").val();
	var userId = document.getElementById("userId").value;
	if (userId != "admin") {
		appId = $("#appId1").val();
	}
	// 绑定业务
	if (appId == "" || appId == null) {
		$.compMsg({
			type : 'error',
			msg : '企业客户不能为空，请重新选择'
		});
		return false;
	}

	return true;
}

// --------------------------
// 绑定业务
function bindBusiness() {
	// 弹出页弹出前，已选择的业务ID值，首次业务ID值为undefined
	var lastAppId = $('#businessListTbody [name="businessCheckbox"]:checked').val();
	$.dialog({
		title : '绑定企业客户',
		init : loadBusinessList('businessListJson.action', lastAppId),
		content : document.getElementById('bindBusiness'),
		ok : function() {
			// 获取选择的业务ID（为了传到后台入库）以及业务名称（用于返回主页面显示）
			var appName = $('#businessListTbody [name="businessCheckbox"]:checked').parents('td').next().html();
			var appId = $('#businessListTbody [name="businessCheckbox"]:checked').val();
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
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}

var displayError = function(msg) {
	$.compMsg({
		type : 'error',
		msg : msg
	});
};

var getPageData = function(url, pageBarId) {
	if (pageBarId) {
		ipSegment.loadIpSegmentList(url);
	} else
		loadBusinessList(url, $('#businessListTbody [name="businessCheckbox"]:checked').val());
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
	$.post(
			url,
			queryData,
			function(data) {
				$('#businessListTbody').empty();
				$.each(data.businessInfoList, function(index, business) {
					var trHtml = '<tr>' + '<td align="center"><input type="radio" name="businessCheckbox" value="' + business.businessId + '" ';
					// 当用户重新绑定业务时，弹出页默认显示上一次的选择
					if (business.businessId == lastAppId) {
						trHtml += ' checked = "checked"';
					}
					trHtml += '/></td>' + '<td>' + business.name + '</td>' + '<td>' + (business.description == null ? '' : business.description)
							+ '</td>' + '</tr>';
					$('#businessListTbody').append(trHtml);
				});
				$('#businessListPageBarDiv').html(data.pageBar);
				
				
			}).fail(function() {
		$.compMsg({
			type : 'error',
			msg : '获取企业客户列表失败！',
			parent : '#bindIp'
		});
	});
};
// ------------------------------------
// ------------------------------------

function bindIp (){
	lastChoice = $('#ipListTbody [name="ipCheckBox"]:checked').val();
	$.dialog({
		title : '可申请的IP段',
		init : loadIpList("queryIpListAction.action", {
			resPoolId : $("#respool").children('option:selected').val()
		}),
		content : document.getElementById('bindIp'),
		ok : function() {
			// 获取选择的业务ID（为了传到后台入库）以及业务名称（用于返回主页面显示）
			var ip = $('#ipListTbody [name="ipCheckBox"]:checked').val();
			if (ip == null || ip == '') {
				displayError("请选择一个IP！");
				return false;
			}
			$("#bindIpDiv").children('span').remove();
			$("#bindIpDiv").children('a').html("重新绑定");
			$("#bindIpDiv").children('a').before(
					'<span style="margin-right:30px">' + ip + '</span>');

			if (!$("#ip").val()) {
				$('#bindIpDiv').append(
						'<input type="hidden" id="ip" name="ip" value="' + ip
						+ '"/>');
			} else {
				$("#ip").val(ipSegmentId);
			}
			
			
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}


function loadIpList (url, para) {
	$.post(
			url,
			para,
			function(data) {
				$('#ipListTbody').empty();

				if (data.faultString) {
					$.compMsg({
						type : 'error',
						msg : '获取ip列表失败！',
						parent : '#bindIp'
					});

					return;
				}

				$.each(data.ips, function(index, ipInfo) {
					var trHtml = '<tr>' + '<td align="center"><input type="radio" name="ipCheckBox" value="' + ipInfo.publicIp + '" ';
					// 当用户重新绑定业务时，弹出页默认显示上一次的选择
					if (ipInfo.publicIp == lastChoice) {
						trHtml += ' checked = "checked"';
					}
					trHtml += '/></td>' + '<td style="width:300px;">' + ipInfo.publicIp +'</td></tr>';
					$('#ipListTbody').append(trHtml);
				});
				$('#ipListPageBarDiv').html(data.pageBar);
				
			}).fail(function() {
		$.compMsg({
			type : 'error',
			msg : '获取IP列表失败！'
		});
	});
}


//var getPageData = function(url) {
//	loadIpList(url, $(
//			'#businessListTbody [name="businessCheckbox"]:checked').val());
//};


