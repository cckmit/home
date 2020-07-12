//申请页面的数据缓存
var itemId;
var varRespoolId;
var varRespoolPartId;
var discSize;
var standardId;
var lengthTime;

$(function() {
	// 菜单显示当前，开发时删除
	$(".left-menu li:contains('云硬盘')").addClass("selected");

	// 自定义form样式
	$("select,:checkbox,:text").uniform();

	$.uniform.update();

	
	itemId = $('#itemId').val();

	onloadResPool("");

	$("#lengthTime").val("1");
	lengthTime = "1";//默认计费时长为1小时
	$("#chargeTypeSpan").text("按时计费");
	$("#lengthTimeZH").text("1小时");

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

	/* 自定义磁盘大小 */
	$("#diskSize").keyup(function() {
		$("#discSize2").html($("#diskSize").val());
		$("#discSize").val($("#diskSize").val());
	}).blur(function() {
		$("#discSize2").html($("#diskSize").val());
		$("#discSize").val($("#diskSize").val());
	});
	
	/* 硬盘类型 */
	$("#resourceType2").html("云硬盘");
	$("#resourceTypeVm").change(function() {
		$("#resourceType2").html("云硬盘");
	});
	
	$("#resourceTypePm").change(function() {
		$("#resourceType2").html("物理硬盘");
	});
	
	/* 块个数 */
	$("#ebsNum").keyup(function() {
		$("#discNum2").html($("#ebsNum").val());
	}).blur(function() {
		$("#discNum2").html($("#ebsNum").val());
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
				// $("#topItems").empty();
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

// 变更时长
function changeAccountingType(accountingType) {
	lengthTime = $(accountingType).val();
	$("#lengthTime").val($(accountingType).val());
	$("#lengthTimeZH").text($(accountingType).children('option:selected').text());
}
// 变更前面时长
function changeAccountingTypeBefore(accountingType) {
	var defaultTerm = $("#accountingType").children('option:selected').val() + "Option";
	lengthTime = $("." + defaultTerm + " :selected").val();
	$("#lengthTime").val($("." + defaultTerm + " :selected").val());
	$("#lengthTimeZH").text($("." + defaultTerm + " :selected").text());

}
// 提交按钮
function submitform() {
	if (validate())
	    document.forms["ebsApplyInfoAction"].submit();
}

//校验
function validate(){
	//业务绑定
	if(!$("#appId").val()){
		$.compMsg({
			type : 'error',
			msg : '所属企业客户不能为空，请选择'
		});
		return false;
	}

	//硬盘容量
	if (!$("#diskSize").val()) {
		$.compMsg( {
			type : 'error',
			msg : '云硬盘容量不能为空，请重新输入'
		});
		$("#diskSize").focus();
		return false;
	}
	
	//硬盘大小是否为正确数字
	if (!/^[1-9][0-9]*$/.test($("#diskSize").val())) {
		$.compMsg( {
			type : 'error',
			msg : '您输入的云硬盘容量格式有误，请填写数字'
		});
		$("#diskSize").select();
		return false;
	}
	
	//名称
	if(!$("#ebsName").val()){
		$.compMsg({
			type : 'error',
			msg : '云硬盘名称不能为空，请重新输入'
		});
		$("#ebsName").focus();
		return false;
	}
	
	//名称长度
	if ($("#ebsName").val().length>25) {
		$.compMsg( {
			type : 'error',
			msg : '云硬盘名称不能超过25个字，请重新输入！'
		});
		$("#ebsName").select();
		return false;
	}

	//块个数
	if (!$("#ebsNum").val()) {
		$.compMsg( {
			type : 'error',
			msg : '块个数不能为空，请重新输入'
		});
		$("#ebsNum").focus();
		return false;
	}
	
	//硬盘大小是否为正确数字
	if (!/^[1-9][0-9]*$/.test($("#ebsNum").val())) {
		$.compMsg( {
			type : 'error',
			msg : '您输入的块个数格式有误，请填写数字'
		});
		$("#ebsNum").select();
		return false;
	}
	
	//校验计费时长输入内容
	var chargesLengthTime = $("#lengthTime").val();
	 var reguNum = new RegExp("^[1-9][0-9]*$");
	if(!reguNum.test(chargesLengthTime)){
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
			// 当重新绑定时，先清除已选值（第一个span是标题，不能删除），添加“重新绑定”字样，显示业务名称。
			$("#bindDiv").children('span:not(:first)').remove();
			$("#bindDiv").children('a').html("重新绑定").css("margin-left","10px");
			$("#bindDiv").children('span').after('<span class="apply-span-name" style="width:auto;" >' + appName + '</span>');
			// 给业务Id赋值，如果第一次添加，就添加hidden标签；如果是第二次以后添加，就替换值
			if (!$("#appId").val())
				$('#ebsApplyInfoAction').append(
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
			msg : '获取企业客户列表！'
		});
	});
};
// ------------------------------------
//------------------------------------
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
	var queryData = {'charges.chargesType' : '1'};
	
	$.post(
					"queryChargesList.action",
					queryData,
					function(data) {
						$('#chargesListTbody').empty();
						$.each(data.chargesList,function(index, charges) {
											var trHtml = "<tr><td>"+charges.hourPrice+"</td>"
																	+"<td>"+charges.monthPrice+"</td>";
											trHtml += "<td>"+ (charges.desc == null ?"" : charges.desc)+ '</td></tr>';
											$('#chargesListTbody').append(
													trHtml);
										});
						$('#chargesListPageBarDiv').html(data.pageBar);
						$.uniform.update();
					},"json").fail(function() {
				$.compMsg({
					type : 'error',
					msg : '获取资费列表！'
				});
			});
}