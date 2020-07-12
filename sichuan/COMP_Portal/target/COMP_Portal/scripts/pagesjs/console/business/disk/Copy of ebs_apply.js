//申请页面的数据缓存
var itemId;
var varRespoolId;
var varRespoolPartId;
var resourceType;
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
//	onloadApp(queryBusinessId);
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
    
    /* 硬盘类型 */
	$("#resourceType2").html("物理硬盘");
	$("#resourceTypeVm").change(function() {
		$("#resourceType2").html("云硬盘");
	});
	
	$("#resourceTypePm").change(function() {
		$("#resourceType2").html("物理硬盘");
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

	    onloadItem();
	}
    });
}

// 加载条目及规格信息
function onloadItem() {
    var da_val = {
	respoolId : varRespoolId,
	respoolPartId : varRespoolPartId
    };
    $
	    .ajax({
		url : 'ebsOnloadItemAction.action?struts.enableJSONValidation=true',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {
		    if (data.items.length == 0) {
		    	//console.js
		    	mask($(".details-con"));
		    } else {
		    	//console.js
		    	removeMask($(".details-con"));
		    }
		    if (!jQuery.isEmptyObject(data.fieldErrors)) {
			if (data.fieldErrors.respoolId.length > 0)
			    alert(data.fieldErrors.respoolId);
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
			window.location.href = 'ebsQueryListAction.action?optState=1';
			return;
		    }
		    // 清除上一次写入条目的信息
		    $("#topItems").empty();
		    // $("#topItems").append('<span
		    // class="apply-span-name">云硬盘容量： </span>');
		    for (var i = 0; i < data.items.length; i++) {
			var tempItemId = data.items[i].itemId;
			if (null == itemId || itemId == "") {
			    itemId = tempItemId;
			}
			var checked = '';

			if (tempItemId == itemId) {
			    changeItem(tempItemId,
				    data.items[i].standardInfo.standardId,
				    data.items[i].standardInfo.resourceType,
				    data.items[i].standardInfo.cpuNum,
				    data.items[i].standardInfo.ramSize,
				    data.items[i].standardInfo.discSize,
				    data.items[i].description,
				    data.items[i].itemName);
			    checked = ' checked="checked"';
			}

			// var lineNum;
			// if(i%4 == 0){
			// lineNum = i/4;
			// if(i==0){
			// $("#topItems").append('<div
			// class="apply-info-top"><div class="max-radio"
			// id="items'+lineNum+'"></div></div>');
			// }else{
			// $("#topItems").append('<div><div class="max-radio"
			// id="items'+lineNum+'"></div></div>');
			// }
			// }
			// $("#items"+lineNum).append('<input type="radio"
			// '+checked+' name="itemId" value="'+ tempItemId
			// +'"/><label>'+ data.items[i].itemName +'</label>');
			if (i % 4 == 0) {
			    $("#topItems").append(
				    '<span class="apply-span-name"></span>');
			}
			
			var itemName = data.items[i].itemName;
			var itemNameText = itemName;
			if (itemName.length > 5) {
				itemNameText = itemName.substring(0, 5) + '...';
			}
			$("#topItems")
				.append(
					'<input title="' + itemName
						+ '" type="radio" '
						+ checked
						+ ' value="'
						+ tempItemId
						+ '" name="itemId" style="opacity: 0;"><label>'
						+ itemNameText
						+ '</label>');
		    }
		    $("#topItems :radio").uniform({
			radioClass : 'font-radio',
			nextTag : 'label'
		    });
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
									item.standardInfo.standardId,
									item.standardInfo.resourceType,
									item.standardInfo.cpuNum,
									item.standardInfo.ramSize,
									item.standardInfo.discSize,
									item.description,
									item.itemName);
							    }
							});
				    });

		}
	    });

}

//业务名称展示
//function onloadApp(){
//	var da_val = {queryBusinessId:queryBusinessId};
//	$.ajax( {
//		url : 'vmOnloadResAppAction.action?struts.enableJSONValidation=true',
//		type : 'POST',
//		data : da_val,
//		cache : false,
//		dataType : 'json',
//		success : function(data) {
//			if (data.resultRoute == "success") {
//				$("#bindDiv").children('span').after('<span class="apply-span-name">' + data.queryBusinessName + '</span>');
//				return;
//			}
//			if (data.resultRoute == "error") {
//				window.location.href = 'exceptionIntercepor.action';
//				return;
//			} 
//			if (data.resultRoute == "failure") {
//				alert("展示业务信息异常，将跳转到浏览页面");
//				window.location.href = 'hostList.action?queryBusinessId='+queryBusinessId;
//				return;
//			}
//		}
//	});
//}

// 改变选择条目
function changeItem(tempItemId, tempStandardId, tempResourceType, tempCpuNum, tempRamSize,
	tempDiscSize, tempDescription, tempItemName) {
    itemId = tempItemId;
    standardId = tempStandardId;
    resourceType = tempResourceType;
    discSize = tempDiscSize;
    $("#discSize").val(discSize);
    $("#discSize2").text(discSize);
    $("#discSize3").text(discSize);
	$("#resourceType").val(resourceType);
	if(resourceType == "1"){
		$("#resourceType2").text("云硬盘");
		$("#resourceType3").text("云硬盘");
	}else if(resourceType == "2"){
		$("#resourceType2").text("物理硬盘");
		$("#resourceType3").text("物理硬盘");
	}
    $("#standardId").val(tempStandardId);
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