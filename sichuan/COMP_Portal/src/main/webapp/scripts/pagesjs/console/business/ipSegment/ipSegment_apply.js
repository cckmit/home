//申请页面的数据缓存
var itemId = '';
var varRespoolId;
var varRespoolPartId;
var discSize;
var standardId;
var lengthTime;
var ipType = '0';

$(function() {
	$("#ips").siblings().removeClass("active");
	$("#ips").addClass("active");


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

});


//改变IP类型方式
function chargeIpTypeChange(obj) {
	var ipTypeVal = obj.value;
	if (ipTypeVal == '0') {// IPV4
		ipType = '0';
	} else if (ipTypeVal == '1') {// IPV6
		ipType = '1';
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
	document.getElementById("ipType").value = ipType;
	if (validate())
		$("#IpSegmentCreateInfoAction").submit();
}

// 校验
function validate() {
	// 名称
	if (!$('#IpSegmentDesc').val()) {
		$.compMsg({
			type : 'error',
			msg : 'IP段名称不能为空，请重新输入'
		});
		$('#IpSegmentDesc').focus();
		return false;
	}

	// 名称长度
	if ($('#IpSegmentDesc').val().length > 25) {
		$.compMsg({
			type : 'error',
			msg : 'IP段名称不能超过25个字，请重新输入！'
		});
		$('#IpSegmentDesc').select();
		return false;
	}

	// 选择IP段
	if (!$("#ipSegmentId").val()) {
		$.compMsg({
			type : 'error',
			msg : 'IP段不能为空，请重新选择'
		});
		return false;
	}

	// 绑定业务
	if (!$("#appId").val()) {
		$.compMsg({
			type : 'error',
			msg : '业务不能为空，请重新选择'
		});
		return false;
	}

	return true;
}

// --------------------------
// 绑定业务
function bindBusiness() {
	// 弹出页弹出前，已选择的业务ID值，首次业务ID值为undefined
	var lastAppId = $('#appId').val();
	$.dialog({
		title : '绑定业务',
		init : loadBusinessList('businessListJson.action', lastAppId),
		content : document.getElementById('bindBusiness'),
		ok : function() {
			// 获取选择的业务ID（为了传到后台入库）以及业务名称（用于返回主页面显示）
			var appName = $('#businessListTbody [name="businessCheckbox"]:checked').parents('td').next().html();
			var appId = $('#businessListTbody [name="businessCheckbox"]:checked').val();
			if (appId == null || appId == '') {
				displayError("请选择一个业务！");
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
			msg : '获取业务列表失败！',
			parent : '#bindIpSegment'
		});
	});
};
// ------------------------------------
// ------------------------------------
ipSegment = {
	/**
	 * @url 要调用的ajax请求的url
	 */
	url : 'queryIpSegmentAction.action',

	/**
	 * @lastChoice 存上次选择的ip段ID
	 */
	lastChoice : '',

	/**
	 * 绑定IP段
	 */
	bindIpSegment : function() {
		lastChoice = $('#ipSegmentListTbody [name="ipCheckBox"]:checked').val();
		$.dialog({
			title : '可申请的IP段',
			init : ipSegment.loadIpSegmentList(this.url, {
				resPoolId : $("#respool").children('option:selected').val(),
				ipType : ipType
			}),
			content : document.getElementById('bindIpSegment'),
			ok : function() {
				// 获取选择的业务ID（为了传到后台入库）以及业务名称（用于返回主页面显示）
				var ipSegmentEx = $('#ipSegmentListTbody [name="ipCheckBox"]:checked').parents('td').next().html();
				var ipSegmentId = $('#ipSegmentListTbody [name="ipCheckBox"]:checked').val();
				var ipSubnet = $('#ipSegmentListTbody [name="ipCheckBox"]:checked').next().val();
				if (ipSegmentId == null || ipSegmentId == '') {
					displayError("请选择一个IP段！");
					return false;
				}
				$("#bindIpSegmentDiv").children('span').remove();
				$("#bindIpSegmentDiv").children('a').html("重新绑定");
				$("#bindIpSegmentDiv").children('a').before(
						'<span style="margin-right:30px">' + ipSegmentEx + '</span>');

				if (!$("#ipSegmentId").val()) {
					$('#bindIpSegmentDiv').append(
							'<input type="hidden" id="ipSegmentId" name="ipSegmentURI" value="' + ipSegmentId
							+ '"/><input type="hidden" id="ipSubnet" name="ipSubnet" value="' + ipSubnet + '"/>');
				} else {
					$("#ipSegmentId").val(ipSegmentId);
				}
				
				
				return true;
			},
			cancelVal : '关闭',
			cancel : true,
			lock : true
		});
	},

	/**
	 * ajax取ip段
	 * 
	 * @para 调用ajax时的参数,以namevalue对为子元素的对象
	 */
	loadIpSegmentList : function(url, para) {
		$.post(
				url,
				para,
				function(data) {
					$('#ipSegmentListTbody').empty();

					if (data.faultString) {
						$.compMsg({
							type : 'error',
							msg : '获取业务列表失败！',
							parent : '#bindIpSegment'
						});

						return;
					}

					$.each(data.ipSegmentList, function(index, ipSegment) {
						var trHtml = '<tr>' + '<td align="center"><input type="radio" name="ipCheckBox" value="' + ipSegment.ipSegmentId + '" ';
						// 当用户重新绑定业务时，弹出页默认显示上一次的选择
						if (ipSegment.ipSegmentId == lastChoice) {
							trHtml += ' checked = "checked"';
						}
						trHtml += '/><input type="hidden" value="' + ipSegment.ipSubnet + '" /></td>' + '<td>' + ipSegment.startIp + ' - '
								+ ipSegment.endIp + '</td>' + '<td>' + ipSegment.ipTotal + '</td>' + '</tr>';
						$('#ipSegmentListTbody').append(trHtml);
					});
					$('#ipSegmentListPageBarDiv').html(data.pageBar);
					
					
				}).fail(function() {
			$.compMsg({
				type : 'error',
				msg : '获取IP段列表！'
			});
		});
	},

};
// ------------------------------------
