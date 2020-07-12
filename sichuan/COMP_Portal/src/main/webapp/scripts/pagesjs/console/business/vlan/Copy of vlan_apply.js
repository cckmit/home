//申请页面的数据缓存
var itemId = '';
var varRespoolId;
var varRespoolPartId;
var discSize;
var standardId;
var lengthTime;

$(function() {
	// 菜单显示当前，开发时删除
	$(".left-menu li:has(#business_" + businessId + ")").addClass("selected").next().show();
	$(".left-menu #menu_vl_" + businessId).parent().addClass("selected");

	// 自定义form样式
	$("select,:checkbox,:text").uniform();

	$.uniform.update();

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
					$.uniform.update();
				} else {
					$("#respool").append(
							'<option selected="selected" value="' + data.respools[i].respoolId + '">' + data.respools[i].respoolName + '</option>');
					varRespoolId = data.respools[i].respoolId;
					$("#respoolName").val(data.respools[i].respoolName);
					$.uniform.update();
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
				$.uniform.update();
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
					$.uniform.update();

				} else {
					$("#respoolpart").append(
							'<option value="' + data.respoolParts[i].respoolPartId + '">' + data.respoolParts[i].respoolPartName + '</option>');
					$.uniform.update();
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
		document.getElementById("vlanForm").submit();
}

//校验
function validate() {
	// Vlan名称
	if (!$('#vlanName').val()) {
		$.compMsg({
			type : 'error',
			msg : 'Vlan名称不能为空，请重新输入！'
		});
		$('#vlanName').focus();
		return false;
	}

	// Vlan名称长度
	if ($('#vlanName').val().length > 25) {
		$.compMsg({
			type : 'error',
			msg : 'Vlan名称不能超过25个字，请重新输入！'
		});
		$('#vlanName').select();
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
			// 当重新绑定时，先清除已选值（第一个span是标题，不能删除），添加“重新绑定”字样，显示业务名称。
			$("#bindDiv").children('span:not(:first)').remove();
			$("#bindDiv").children('a').html("重新绑定");
			$("#bindDiv").children('span').after('<span class="apply-span-name">' + appName + '</span>');
			// 给业务Id赋值，如果第一次添加，就添加hidden标签；如果是第二次以后添加，就替换值
			if (!$("#appId").val())
				$(document.getElementById("vlanForm")).append(
						'<input type="hidden" id="appId" name="appId" value="' + appId
								+ '"/><input type="hidden" id="appName" name="appName" value="' + appName + '"/>');
			else
				$("#appId").val(appId);
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

var getPageData = function(url) {
	loadBusinessList(url, $('#businessListTbody [name="businessCheckbox"]:checked').val());
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
				
				 $("select").uniform(); $.uniform.update();
				
			}).fail(function() {
		$.compMsg({
			type : 'error',
			msg : '获取业务列表！'
		});
	});
};
// ------------------------------------
