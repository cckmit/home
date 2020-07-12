//保存验证
function saveValidation(opt){
	if(opt=='update'){
		var itemId = $.trim($("#itemId").val());
		if(itemId==''){
			$.compMsg({type:'error',msg:'条目ID不能为空！请正确选择条目！'});
			return true;
		}
	}
	var itemName = $.trim($("#itemName").val());
	var catalogId = $.trim($("#catalogId").val());
	var standardId = $.trim($("#standardId").val());
	if(itemName==''){
		$.compMsg({type:'error',msg:'条目名称不能为空！请正确填写！'});
		return true;
	}
	if(catalogId==''){
		$.compMsg({type:'error',msg:'条目分类不能为空！请正确填写！'});
		return true;
	}
	if(standardId==''){
		$.compMsg({type:'error',msg:'资源规格不能为空！请正确关联资源规格！'});
		return true;
	}
	return false;
}

function string2Json(str) {
	var sb = new StringBuffer();
	for (var i = 0; i < str.length; i++) {
		var c = str.charAt(i);
		switch (c) {
		case '\"':
			sb.append("\\\"");
			break;
		case '\\':
			sb.append("\\\\");
			break;
		case '/':
			sb.append("\\/");
			break;
		case '\b':
			sb.append("\\b");
			break;
		case '\f':
			sb.append("\\f");
			break;
		case '\n':
			sb.append("\\n");
			break;
		case '\r':
			sb.append("\\r");
			break;
		case '\t':
			sb.append("\\t");
			break;
		default:
			sb.append(c);
		}
	}
	return sb.toString();
};

function StringBuffer(str) {
	var arr = [];
	str = str || "";
	arr.push(str);
	this.append = function(str1) {
		arr.push(str1);
		return this;
	};
	this.toString = function() {
		return arr.join("");
	};
};

//拼接json字符串
function getJsonStr(opt){
	var itemId ="";
	var oldStandardId ="";
	if(opt=='update'){
		itemId = $.trim($("#itemId").val());
		oldStandardId = $.trim($("#oldStandardId").val());
	}
	var itemName = string2Json($.trim($("#itemName").val()));
	var catalogId = $.trim($("#catalogId").val());
//	var maxNum = $.trim($("#maxNum").val());
//	var minNum = $.trim($("#minNum").val());
//	var sellStartTime = _dateToStr($("#sellStartTime").val());
//	var sellEndTime = _dateToStr($("#sellEndTime").val());
	var description = string2Json($.trim($("#description").val()));
	var standardId = $.trim($("#standardId").val());
	var jsonStr = "{'itemId':'"+itemId+"',";
	jsonStr += "'itemName':'"+itemName+"',";
	jsonStr += "'catalogId':'"+catalogId+"',";
//	jsonStr += "'maxNum':'"+maxNum+"',";
//	jsonStr += "'minNum':'"+minNum+"',";
//	jsonStr += "'sellStartTime':'"+sellStartTime+"',";
//	jsonStr += "'sellEndTime':'"+sellEndTime+"',";
	jsonStr += "'description':'"+description+"',";
	jsonStr += "'standardId':'"+standardId+"'}";
	var da_val = {'itemJson':jsonStr,'itemId':itemId,'itemName':itemName,'catalogId':catalogId,
				  'description':description,'oldStandardId':oldStandardId,'standardId':standardId};
	return da_val;
}
//检验错误信息
//返回错误提示内容
function fieldErrors(errors){
	var mes = '';
	if(!jQuery.isEmptyObject(errors)){
		if(!jQuery.isEmptyObject(errors.itemId)){
			mes+=errors.itemId+'\n';
		}if(!jQuery.isEmptyObject(errors.itemName)){
			mes+=errors.itemName+'\n';
		}if(!jQuery.isEmptyObject(errors.catalogId)){
			mes+=errors.catalogId+'\n';
		}if(!jQuery.isEmptyObject(errors.maxNum)){
			mes+=errors.maxNum+'\n';
		}if(!jQuery.isEmptyObject(errors.minNum)){
			mes+=errors.minNum+'\n';
		}if(!jQuery.isEmptyObject(errors.sellStartTime)){
			mes+=errors.sellStartTime+'\n';
		}if(!jQuery.isEmptyObject(errors.sellEndTime)){
			mes+=errors.sellEndTime+'\n';
		}if(!jQuery.isEmptyObject(errors.description)){
			mes+=errors.description+'\n';
		}if(!jQuery.isEmptyObject(errors.standardId)){
			mes+=errors.standardId+'\n';
		}
	}
	return mes;
}
//保存条目
function saveItem(opt){
	$("#saveItem").parent().hide();
	$("#saveAndSubmitItem").parent().hide();
	if(saveValidation(opt)){
		$("#saveItem").parent().show();
		$("#saveAndSubmitItem").parent().show();
		return false;
	}
	var urls = "";
	if(opt == 'update'){
		urls = 'ebsItemUpdate.action?struts.enableJSONValidation=true';
	}else if(opt == 'save'){
		urls = 'ebsItemsCreate.action?struts.enableJSONValidation=true';
	}
	var da_val = getJsonStr(opt);
	$.ajax({
        type: "POST",
        url: urls,
        data: da_val,
        dataType: "JSON",
        cache: false,
		async:false,
        success: function(data){
			var mes = fieldErrors(data.fieldErrors);
			if(mes!=''){
				$.compMsg({type:'error',msg:mes});
			}
			if(data.resultMessage!=null){
				if(data.resultFlage=='success'){
					$.compMsg({type:'success',msg:data.resultMessage}); 
				}else if(data.resultFlage=='failure'){
					$.compMsg({type:'warn',msg:data.resultMessage}); 
				}else if(data.resultFlage=='error'){
					$.compMsg({type:'error',msg:data.resultMessage}); 
				}
			}
			if(data.resultFlage=='success'){
				backList(data.resultMessage,'');
			}
		}
	});
	$("#saveItem").parent().show();
	$("#saveAndSubmitItem").parent().show();
}
//保存并提交条目
function saveAndSubmitItem(opt){
	$("#saveItem").parent().hide();
	$("#saveAndSubmitItem").parent().hide();
	if(saveValidation(opt)){
		$("#saveItem").parent().show();
		$("#saveAndSubmitItem").parent().show();
		return false;
	}
	var da_val = getJsonStr(opt);
	var urls = "";
	if(opt == 'update'){
		urls = 'ebsItemUpdateAndSubmit.action?struts.enableJSONValidation=true&statusCode='+$("#statusCode").val();
	}else if(opt == 'save'){
		urls = 'ebsItemCreateAndSubmit.action?struts.enableJSONValidation=true';
	}
	$.ajax({
        type: "POST",
        url: urls,
        data: da_val,
        dataType: "JSON",
        cache: false,
		async:false,
        success: function(data){
			var mes = fieldErrors(data.fieldErrors);
			if(mes!=''){
				$.compMsg({type:'error',msg:mes});
			}
			if(data.resultMessage!=null){
				if(data.resultFlage=='success'){
					$.compMsg({type:'success',msg:data.resultMessage}); 
				}else if(data.resultFlage=='failure'){
					$.compMsg({type:'warn',msg:data.resultMessage}); 
				}else if(data.resultFlage=='error'){
					$.compMsg({type:'error',msg:data.resultMessage}); 
				}
			}
			if(data.resultFlage=='success'){
				backList(data.resultMessage,'');
			}
		}
	});
	$("#saveItem").parent().show();
	$("#saveAndSubmitItem").parent().show();
}