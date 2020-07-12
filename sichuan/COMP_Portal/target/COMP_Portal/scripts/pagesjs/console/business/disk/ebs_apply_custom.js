//申请页面的数据缓存
var itemId;
var varRespoolId;
var varRespoolPartId;
var discSize;
var standardId;
var lengthTime;
var queryBusinessId;

$(function() {
    $(".left-menu li:has(#business_" + businessId + ")").addClass("selected")
	    .next().show();
    $(".left-menu #menu_bs_" + businessId).parent().addClass("selected");

    // 自定义form样式
    $("select,:checkbox,:text").uniform();

    $.uniform.update();

    // 计费联动
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
	queryBusinessId = $('#appId').val();
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
		    $("#respool").append(
			    '<option value="' + data.respools[i].respoolId
				    + '">' + data.respools[i].respoolName
				    + '</option>');
		    $.uniform.update();
		} else {
		    $("#respool").append(
			    '<option selected="selected" value="'
				    + data.respools[i].respoolId + '">'
				    + data.respools[i].respoolName
				    + '</option>');
		    varRespoolId = data.respools[i].respoolId;
		    $("#respoolName").val(data.respools[i].respoolName);
		    $.uniform.update();
		}
	    }
	    //资源池无分区判断
		if(data.respoolParts.length==0){
			//console.js
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
		    $.uniform.update();

		} else {
		    $("#respoolpart").append(
			    '<option value="'
				    + data.respoolParts[i].respoolPartId + '">'
				    + data.respoolParts[i].respoolPartName
				    + '</option>');
		    $.uniform.update();
		}

	    }
	    if (typeof (varRespoolPartId) == "undefined"
		    || varRespoolPartId == "") {
		varRespoolPartId = data.respoolParts[0].respoolPartId;
	    }

//	    onloadItem();
	}
    });
}

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
// 提交按钮
function submitform() {
    if (validate())
    	document.forms["ebsApplyInfoAction"].submit();
}

//校验
function validate(){
	//云硬盘容量
	if (!$("#diskSize").val()) {
		$.compMsg( {
			type : 'error',
			msg : '云硬盘容量不能为空，请重新输入'
		});
		$("#diskSize").focus();
		return false;
	}
	
	//云硬盘大小是否为正确数字
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
	
	//云硬盘大小是否为正确数字
	if (!/^[1-9][0-9]*$/.test($("#ebsNum").val())) {
		$.compMsg( {
			type : 'error',
			msg : '您输入的块个数格式有误，请填写数字'
		});
		$("#ebsNum").select();
		return false;
	}
	return true;
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