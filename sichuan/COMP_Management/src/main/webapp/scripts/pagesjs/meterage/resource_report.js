var varRespoolId;
var varRespoolPartId;
var hosturl;
$(function() {
	// 菜单显示当前，开发时删除
	$(".left-menu li:contains('资源计量报告')").addClass("selected").next().show();
	$(".left-menu dl a:contains('物理机计量报告')").parent().addClass("selected");
    $("#head-menu li a:contains('计量管理')").parent().addClass("head-btn-sel");
	// 自定义form样式
	$(":radio,:text,:checkbox,select").uniform();

	// 加载弹出层提示信息栏宽度
	$(".point").attr("style", "width:80px");
	$(".point").html("&nbsp;");
	$(function(){
		if(top.location.href.toLowerCase() == self.location.href.toLowerCase()) $('#docLink').show();
		$("#tabNav ul").idTabs("tab0"); 
	});
	
	//默认加载
	onLoadReport();
	
	$('#meterage').click(function() {
		onLoadReport();
	});
	onloadResPool("");
	$("#respool").change(function (){
		varRespoolId = $(this).children('option:selected').val();
		varRespoolPartId = "";
		onloadResPool(varRespoolId,varRespoolPartId);
		
	});
	
	$("#respoolpart").change(function (){
		varRespoolId = $("#respool").children('option:selected').val();
		varRespoolPartId = $(this).children('option:selected').val();
		onloadResPool(varRespoolId,varRespoolPartId);
		
	});
});
function onLoadReport(){
	var resourcePoolId = $('#respool').val();
	if("00"==resourcePoolId){
		resourcePoolId = '';
	}
	var resourcePoolPartId = $('#respoolpart').val();
	if("00"==resourcePoolPartId){
		resourcePoolPartId = '';
	}
	var year = $('#year').val();
	var time ="";
	var month=$('#month').val();
	if("00"==year){
		time = '';
	} else if("00"==month){
		time = year+'%25';
	} else {
		time = year+month+'%25';
	}
	
//  all 
	var param = "reportId=f0c47d25-11cc-4114-a1ff-7c1ed92f2fc2" +
	"&respoolid=" + resourcePoolId +
	"&respoolpartid=" + resourcePoolPartId +
	"&time=" + time;
	var encryptParam=getDateParam(param);
	var url = hosturl+"/Report/Report-AuthAction.do?encryptParam=" +encryptParam;
	document.getElementById('allFrame').src=url;
//	pm
	var param = "reportId=f8bb035c-17a4-49ee-a39e-e829597e46b3" +
	"&respoolid=" + resourcePoolId +
	"&respoolpartid=" + resourcePoolPartId +
	"&time=" + time;
	var encryptParam=getDateParam(param);
	var url = hosturl+"/Report/Report-AuthAction.do?encryptParam=" +encryptParam;
	document.getElementById('pmFrame').src=url;
//	vm
	param = "reportId=c576b564-c2c2-48a7-b891-1b4ddc3ac1fc" +
	"&respoolid=" + resourcePoolId +
	"&respoolpartid=" + resourcePoolPartId +
	"&time=" + time;
	encryptParam=getDateParam(param);
	url = hosturl+"/Report/Report-AuthAction.do?encryptParam=" +encryptParam;
	document.getElementById('vmFrame').src=url;
//	ebs
	param = "reportId=b1ab05b4-6e1a-499c-b906-416973cf052a" +
	"&respoolid=" + resourcePoolId +
	"&respoolpartid=" + resourcePoolPartId +
	"&time=" + time;
	encryptParam=getDateParam(param);
	url = hosturl+"/Report/Report-AuthAction.do?encryptParam=" +encryptParam;
	document.getElementById('ebsFrame').src=url;
//	vmbk
	/*param = "reportId=241ec7f3-dd7b-4fda-92b1-da25619bfd55" +
	"&respoolid=" + resourcePoolId +
	"&respoolpartid=" + resourcePoolPartId +
	"&time=" + time;
	encryptParam=getDateParam(param);
	url = hosturl+"/Report/Report-AuthAction.do?encryptParam=" +encryptParam;
	document.getElementById('vmbkFrame').src=url;*/
}
function getDateParam(param){
	var encryptParam="";
	var params = {
			params : param
	};
	$.ajax({
		type : "POST",
		url : "getDate.action",
		data : params,
		dataType : "JSON",
		cache : false,
		async : false,
		success : function(data) {
			encryptParam = data.encryptParam;
			hosturl = data.hostUrl;
		}
	});
	return encryptParam;
}
//加载资源池及分区信息
function onloadResPool(tempRespoolId,tempRespoolPartId) {
	var da_val = {respoolId:tempRespoolId,respoolPartId:tempRespoolPartId};
	$.ajax( {
		url : 'onloadResPoolAction.action?struts.enableJSONValidation=true',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {

		if (!jQuery.isEmptyObject(data.fieldErrors)) {
			if (data.fieldErrors.respoolId.length > 0)
				alert(data.fieldErrors.respoolId);
			return;
		}
		if (data.resultRoute == "error") {
			window.location.href = 'exceptionIntercepor.action';
			return;
		} 
		 $("#respool").empty();
			for ( var i = 0; i < data.respools.length; i++) {
				if (i ==0){
					$("#respoolName").val(data.respools[i].resPoolName);
				}
				if(varRespoolId != data.respools[i].resPoolId){
					$("#respool").append(
							'<option value="' + data.respools[i].resPoolId + '">'
									+ data.respools[i].resPoolName + '</option>');
					$.uniform.update();					
				}else{
					$("#respool").append(
							'<option selected="selected" value="' + data.respools[i].resPoolId + '">'
									+ data.respools[i].resPoolName + '</option>');
					varRespoolId = data.respools[i].resPoolId;
					$("#respoolName").val(data.respools[i].resPoolName);
					$.uniform.update();	
				}
			}

			 $("#respoolpart").empty();
			for ( var i = 0; i < data.respoolParts.length; i++) {
				if(i == 0 ){
					$("#respoolPartName").val(data.respoolParts[i].resPoolPartName);					
				}
				if (varRespoolPartId ==  data.respoolParts[i].resPoolPartId){
					$("#respoolpart").append(
							'<option selected="selected" value="' + data.respoolParts[i].resPoolPartId
							+ '">' + data.respoolParts[i].resPoolPartName
							+ '</option>');
					varRespoolPartId = data.respoolParts[i].resPoolPartId;
					$("#respoolPartName").val(data.respoolParts[i].resPoolPartName);
					$.uniform.update();
					
				}else{
					$("#respoolpart").append(
						'<option value="' + data.respoolParts[i].resPoolPartId
						+ '">' + data.respoolParts[i].resPoolPartName
						+ '</option>');
					$.uniform.update();
				}

			}
			if(typeof(varRespoolPartId)=="undefined" || varRespoolPartId==""){
				varRespoolPartId=data.respoolParts[0].resPoolPartId;
			}
		}
	});
}
