//申请页面的数据缓存
var itemId;
var varRespoolId;
var varRespoolPartId;
var cpuType;
var ramSize;
var discSize;
var serverType;
var standardId;
var lengthTime;

var rolsCount=0;
var rols=0;
var checkGatewayOk=false;

$( function() {
	$(".left-menu li:contains('物理机 ')").addClass("selected");//菜单显示当前
	
	// 自定义form样式
	$("select,:checkbox,:text").uniform();
	
	$(":radio").uniform( {
		radioClass : 'radio'	,
		nextTag : 'label'
	});

	// 默认显示值
	var defaultTerm = $("#accountingType").children('option:selected').val()
			+ "Option";
	$("." + defaultTerm).show();
	$("#accountingType").change( function() {
		var val = $(this).children('option:selected').val();
		$("#accountingTerms .selector").each( function() {
			if ($(this).hasClass(val + "Option")) {
				$(this).show();
			} else {
				$(this).hide();
			}
		});
		if (val == 'year') {
			$("#yearType").show();
		}
	});

	itemId = $('#itemId').val();
	
	onloadResPool("");
	
	$("#num2").text("1");
	$("#lengthTime").val("0_y");
	lengthTime = "0_y";
	$("#lengthTimeZH").text("无期限");
	
	$("#num").change(function (){
		$("#num2").text($(this).children('option:selected').text());
		
	});
	
	$("#osTypeSelect").change(function (){
		$("#osType").val($(this).children('option:selected').text());
		$("#osType2").text($(this).children('option:selected').text());
		
	});
	
	$("#respool").change(function (){
		varRespoolId = $(this).children('option:selected').val();
		varRespoolPartId = "";
		itemId = "";
		onloadResPool(varRespoolId,varRespoolPartId);
		
	});
	
	$("#respoolpart").change(function (){
		varRespoolId = $("#respool").children('option:selected').val();
		varRespoolPartId = $(this).children('option:selected').val();
		itemId = "";
		onloadResPool(varRespoolId,varRespoolPartId);
		
	});

});

// 加载资源池及分区信息
function onloadResPool(tempRespoolId,tempRespoolPartId) {
	var da_val = {itemId:itemId,respoolId:tempRespoolId,respoolPartId:tempRespoolPartId};
	$.ajax( {
		url : 'pmOnloadResPoolAction.action?struts.enableJSONValidation=true',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {

		if (!jQuery.isEmptyObject(data.fieldErrors)) {
			if (data.fieldErrors.respoolId.length > 0) {
				alert(data.fieldErrors.respoolId);
				return;
			}
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
			window.location.href = 'pmQueryListAction.action?optState=1';
			return;
		}
		 $("#respool").empty();
			for ( var i = 0; i < data.respools.length; i++) {
				if (i ==0){
					$("#respoolName").val(data.respools[i].respoolName);
				}
				if(varRespoolId != data.respools[i].respoolId){
					$("#respool").append(
							'<option value="' + data.respools[i].respoolId + '">'
									+ data.respools[i].respoolName + '</option>');
					$.uniform.update();					
				}else{
					$("#respool").append(
							'<option selected="selected" value="' + data.respools[i].respoolId + '">'
									+ data.respools[i].respoolName + '</option>');
					varRespoolId = data.respools[i].respoolId;
					$("#respoolName").val(data.respools[i].respoolName);
					$.uniform.update();	
				}
			}
			
			//资源池无分区判断
			if(data.respoolParts.length==0){
				//console.js
				maskNotPart($(".details-con1"));
				$("#respoolpart").empty();
				$("#topItems").empty();
				$("#respoolpart").append('<option value=""></option>');
				$("#respoolPartName").val("");
				$.uniform.update();
				return false;
			}else{
				//console.js
				removeMask($(".details-con1"));
			}
			
			$("#respoolpart").empty();
			for ( var i = 0; i < data.respoolParts.length; i++) {
				if(i == 0 ){
					$("#respoolPartName").val(data.respoolParts[i].respoolPartName);					
				}
				if (varRespoolPartId ==  data.respoolParts[i].respoolPartId){
					$("#respoolpart").append(
							'<option selected="selected" value="' + data.respoolParts[i].respoolPartId
							+ '">' + data.respoolParts[i].respoolPartName
							+ '</option>');
					varRespoolPartId = data.respoolParts[i].respoolPartId;
					$("#respoolPartName").val(data.respoolParts[i].respoolPartName);
					$.uniform.update();
					
				}else{
					$("#respoolpart").append(
						'<option value="' + data.respoolParts[i].respoolPartId
						+ '">' + data.respoolParts[i].respoolPartName
						+ '</option>');
					$.uniform.update();
				}

			}
			if(typeof(varRespoolPartId)=="undefined" || varRespoolPartId==""){
				varRespoolPartId=data.respoolParts[0].respoolPartId;
			}

			$("#osNameSelect").empty();
			for (var i = 0; i < data.oss.length; i++) {
				if(i==0){
					$("#osName").val(data.oss[i].osName);
					$("#osName2").text(data.oss[i].osName);
				}
				$("#osNameSelect").append(
					'<option value="' + data.oss[i].osId + '">'
							+ data.oss[i].osName + '</option>');
				$.uniform.update();
			}
			
			if($('#configuration').val()=="0"){
				onloadItem();
			}
		}
	});
}

//加载条目及规格信息
function onloadItem() {
	varRespoolId = $("#respool").children('option:selected').val();
	varRespoolPartId = $("#respoolpart").children('option:selected').val();
	
	var da_val = {respoolId:varRespoolId,respoolPartId:varRespoolPartId};
	$.ajax( {
		url : 'pmOnloadItemAction.action?struts.enableJSONValidation=true',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {
		if (data.items.length == 0){
			//console.js
			mask($(".details-con1"));
		}else{
			//console.js
			removeMask($(".details-con1"));
		}
		if (!jQuery.isEmptyObject(data.fieldErrors)) {
			if (data.fieldErrors.respoolId.length > 0){
				alert(data.fieldErrors.respoolId);
				return;
			}
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
			window.location.href = 'pmQueryListAction.action?optState=1';
			return;
		}
		//清除上一次写入条目的信息
		$("#topItems").empty();
			for ( var i = 0; i < data.items.length; i++) {				
				var tempItemId = data.items[i].itemId;
				if(null ==itemId ||itemId == ""){
					itemId = tempItemId;
				}
				var checked = '';
				
				if ( tempItemId == itemId) {
					changeItem(tempItemId,data.items[i].pmStandardInfo.templateId,data.items[i].pmStandardInfo.standardId,data.items[i].pmStandardInfo.cpuType,data.items[i].pmStandardInfo.ramSize,data.items[i].pmStandardInfo.discSize,data.items[i].pmStandardInfo.serverType,data.items[i].description,data.items[i].itemName);
					checked = ' checked="checked"';
				}

				var lineNum;
				if(i%4 == 0){
					lineNum = i/4;
					if(i==0){
						$("#topItems").append('<div class="apply-info-top"><div class="max-radio" id="items'+lineNum+'"></div></div>');
					}else{
						$("#topItems").append('<div><div class="max-radio" id="items'+lineNum+'"></div></div>');
					}
				}
				var itemName = data.items[i].itemName;
				var itemNameText = itemName;
				if (itemName.length > 5) {
					itemNameText = itemName.substring(0, 5) + '...';
				}
				$("#items"+lineNum).append('<label title="' + itemName + '"><input type="radio" '+checked+' name="itemId" value="'+ tempItemId +'"/><label>'+ itemNameText +'</label>');
			}
			$("#topItems :radio").uniform( {
				radioClass : 'font-radio'	,
				nextTag : 'label'
			});
			$('[name="itemId"]').bind("click", function(){ 
                var checkInput = this.value;
				$(data.items).each(function(index) { 
	                var item = data.items[index];
	                if (item.itemId == checkInput) {
	                	changeItem(item.itemId,item.pmStandardInfo.templateId,item.pmStandardInfo.standardId,item.pmStandardInfo.cpuType,item.pmStandardInfo.ramSize,item.pmStandardInfo.discSize,item.pmStandardInfo.serverType,item.description,item.itemName);
					}
				});
			});
		}
	});

	
}

//改变选择条目
function changeItem(tempItemId,templateId,tempStandardId,tempCpuType,tempRamSize,tempDiscSize,tempServerType,tempDescription,tempItemName){
	itemId = tempItemId;
	standardId = tempStandardId;
	cpuType = tempCpuType;
	ramSize = tempRamSize;
	discSize = tempDiscSize;
	serverType = tempServerType;
	$("#cpuType").val(cpuType);
	$("#ramSize").val(ramSize);
	$("#discSize").val(discSize);
	$("#serverType").val(serverType);
	$("#cpuType2").text(cpuType);
	$("#ramSize2").text(ramSize);
	$("#discSize2").text(discSize);
	$("#serverType2").text(serverType);
	$("#cpuType3").text(cpuType);
	$("#ramSize3").text(ramSize);
	$("#discSize3").text(discSize);
	$("#serverType3").text(serverType);
	//$("#itemId").val(tempItemId);
	$("#standardId").val(tempStandardId);
	$("#itemDescription").text("适用领域及特点描述: "+tempDescription);
	$("#itemName").text(tempItemName);
	
	var da_val = {standardId:tempStandardId,templateId:templateId};
	$.ajax( {
		url : 'pmOnloadOsAction.action?struts.enableJSONValidation=true',
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
				alert("展示条目下镜像信息异常，将跳转到浏览页面");
				window.location.href = 'pmQueryListAction.action?optState=1';
				return;
			}

			$("#osNameSelect").empty();
			for ( var i = 0; i < data.osInfos.length; i++) {
				$("#osNameSelect").append(
						'<option value="' + data.osInfos[i].osId + '">'
								+ data.osInfos[i].osName + '</option>');
				$.uniform.update();
			}
			changeosName();
		}
	});
}

//改变选择镜像
function changeosName(){
	$("#osName").val($("#osNameSelect :selected").text());
	$("#osName2").text($("#osNameSelect :selected").text());
}

//改变vlan，ajax请求privateIp
function changeIpsegment(ipSegmentId) {
	
	var selectValTemp = $(ipSegmentId).attr('id');
	selectValTemp = "#"+selectValTemp.replace("ipsegmentSelect","privateIpSelect");
	var selectVal = selectValTemp;
	var vlanselectVal = selectValTemp.replace("privateIpSelect","vlanSelect");
	var vlan = $(vlanselectVal).val();
	var ipSegmentId =ipSegmentId.value;

	if (""!=vlan && ""!=ipSegmentId) {
		var da_val = {logicVlanId:vlan,ipSegmentId:ipSegmentId};
		$.ajax( {
			url : 'pmOnloadPrivateIpAction.action?struts.enableJSONValidation=true',
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
					alert("展示ip信息异常，将跳转到浏览页面");
					window.location.href = 'pmQueryListAction.action?optState=1';
					return;
				}
//				var selectVal="#privateIpSelect"+rols;
				$(selectVal).empty();
				for ( var i = 0; i < data.ipInfos.length; i++) {
				if(i==0){
					$(selectVal).append(
							'<option value=""></option>');
				}
				$(selectVal).append(
					'<option value="' + data.ipInfos[i].ip + '">'
							+ data.ipInfos[i].ip + '</option>');
				$.uniform.update();
				}
			}
		});
	}
}

//添加网卡
function addNet(ethList) {
	var str = '<tr>';
	str += '<td    align="center"><div >VLAN：<select style="width:100px" size="1"   id="vlanSelect'+rols+'" name="'+ethList+'vlanId" onchange="changeVlan(this)"></select></div></td>';
	str += '<td    align="center"style="overflow:hidden;"><div >IP段：<select style="width:100px" size="1"   id="ipsegmentSelect'+rols+'" name="'+ethList+'ipsegmentId" onchange="changeIpsegment(this)"></select></div></td>';
	str += '<td     align="center"  ><div >IP：<select  style="width:100px" size="1"  id="privateIpSelect'+rols+'" name="'+ethList+'ip"></select></div></td>';
	str += '<td    align="center" ><div  >网关：<input style="width:100px" type="text" maxlength="15"  id="gateway'+rols+'"  name="'+ethList+'gateway"   onblur="checkGateway(this)" /></div></td>';
	str += '<td    align="center"  style="width:100px"  onclick="deleteNet(this)"><div><span class="product-list-btn"><a href="#">删除</a></span></div></td></tr>';
	str += '</tr>';
	$("#relate_resource_div").find('#'+ethList).append(str);

	var appId = $("#appId").val();
	// 查询物理机业务对应的VLAN列表
	var da_val = {
		appId : appId
	};

	$.ajax({
		type : "POST",
		url : 'vlanQueryJson.action',
		data : da_val,
		dataType : "JSON",
		cache : false,
		success : function(data) {
			if (!jQuery.isEmptyObject(data.fieldErrors)) {
				// TODO
			}
			if (data.resultRoute == "error") {
				window.location.href = 'exceptionIntercepor.action';
				return;
			}
			if (data.resultRoute == "failure") {
				alert("展示资源池信息异常，将跳转到浏览页面");
				window.location.href = 'pmQueryListAction.action?optState=1';
				return;
			}
			var selectVal ="#vlanSelect"+rols;
			$(selectVal).empty();
			for (var i = 0; i < data.vlanList.length; i++) {
				if (i == 0) {
					$(selectVal).append('<option value=""></option>');
				}
				$(selectVal).append(
					'<option value="' + data.vlanList[i].vlanId + '">'
							+ data.vlanList[i].vlanName + '</option>');
				$.uniform.update();
			}
			
			rolsCount++;
			rols++;	
		}
	});
}

function  checkGateway(gateway){
	var selectVal = $(gateway).attr('id');
	var gatewayId =$("#"+ selectVal).val();
	var ipsegmentId =$( "#"+selectVal.replace("gateway","ipsegmentSelect")).val();
	var ip = $( "#"+selectVal.replace("gateway","privateIpSelect")).val();

	var da_val = {
		gatewayId : gatewayId,
		ipsegmentId : ipsegmentId,
		ip : ip
	};
		$
				.ajax({
					url : 'vmCheckGatewayAction.action',
					type : 'POST',
					data : da_val,
					cache : false,
					dataType : 'json',
					success : function(data) {
						if (data.resultRoute == "error") {
							checkGatewayOk=false;
							alert(data.errMsg);
							return;
						}
						if (data.resultRoute == "failure") {
							checkGatewayOk=false;
							alert("验证网关段信息异常");
							return;
						}
						checkGatewayOk=true;
					}
				});
}

// 改变vlan，ajax请求IP段
function changeVlan(vlanId) {
	var selectVal = $(vlanId).attr('id');
	selectVal = "#"+selectVal.replace("vlanSelect","ipsegmentSelect");
	var vlanId = vlanId.value;
	var appId = $("#appId").val();
	if (!("" == vlanId)) {
		var da_val = {
			vlanId : vlanId,
			appId : appId
		};
		$
				.ajax({
					url : 'pmOnloadIpsegmentAction.action',
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
							alert("展示IP段信息异常，将跳转到浏览页面");
							window.location.href = 'pmQueryListAction.action?optState=1';
							return;
						}
						
//						var selectVal= "#ipsegmentSelect"+rols;
						$(selectVal).empty();
						for (var i = 0; i < data.ipSegmentList.length; i++) {
							if (i == 0) {
								$(selectVal).append(
										'<option value=""></option>');
							}
							$(selectVal).append(
								'<option value="' + data.ipSegmentList[i].ipSegmentId
										+ '">' + data.ipSegmentList[i].ipSegment
										+ '</option>');
							$.uniform.update();
						}
					}
				});
	}
}

//判断ip地址的合法性
function checkIP(value){
    var exp=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
    var reg = value.match(exp);
    if(reg==null) {
    	alert("网关地址: "+value+" 不合法");
    	return false;
    }
    return true;
}

//变更时长 
function changeAccountingType(accountingType){
	lengthTime=$(accountingType).val();
	$("#lengthTime").val($(accountingType).val());
	$("#lengthTimeZH").text($(accountingType).children('option:selected').text());
}
//变更前面时长 
function changeAccountingTypeBefore(accountingType){
	var defaultTerm = $("#accountingType").children('option:selected').val()
	+ "Option";
	lengthTime=$("." + defaultTerm+" :selected").val();
	$("#lengthTime").val($("." + defaultTerm+" :selected").val());
	$("#lengthTimeZH").text($("." + defaultTerm+" :selected").text());
	
}
//提交按钮
function submitform(){
	// 校验物理机名称
	var pmName = $("#pmName").val();
    if(pmName == "" || pmName == null){
    	$.compMsg( {
			type : 'error',
			msg : '物理机名称不能为空！'
		});
    	return;
    }
    
    // 校验所属业务
    var appId = $("#appId").val();
	if(appId == "" || appId == null){
		$.compMsg( {
			type : 'error',
			msg : '所属企业客户不能为空！'
		});
		return;
    }
	
	// 是否配置ip
		if ($("#ApplyNet").attr("checked") == "checked") {
			//验证网卡
			var ips = $("select[name$='ip']");
		    var arr = new Array();
		    for(var i=0;i<ips.length;i++){
		        arr[i] = ips[i].value;
		    }
		    var nary = arr.sort();
		    for(var i = 0; i < nary.length - 1; i++)
		    {
		       if (nary[i] == nary[i+1])
		        {
		           alert("重复网卡的IP为：" + nary[i]);
		           return;
		        }
		    }
		    
			var s = $("input[name$='gateway']");  
			for (var i = 0; i < s.length; i++) {
				if(s[i].value==null || s[i].value==""){
					alert("网卡内容有空值");
					return;
				} else{
					 if(!checkIP(s[i].value)){
			        	   return;
			        }
				}
			}
			
			var s = $("table select");
			for (var i = 0; i < s.length; i++) {
				if(s[i].value==null || s[i].value==""){
					alert("网卡内容有空值");
					return;
				} 
			}
			
			if(!checkGatewayOk){
				alert("网关IP非法");
				return;
			}
			
		}
	
	
	var tempPmRemark = $('#pmRemark').attr("value");
	if(tempPmRemark.length<=100){
		document.forms["pmApplyInfoAction"].submit(); 
	}else{
		$.compMsg( {
			type : 'error',
			msg : '备注信息不能超过100个字，请重新输入！'
		});
	}
}

//绑定业务
function bindBusiness() {
	$("#ethList1").text("");
	$("#ethList2").text("");
	$("#ethList3").text("");
	$("#ethList4").text("");
	$("#ethList5").text("");
	// 弹出页弹出前，已选择的业务ID值，首次业务ID值为undefined
	var lastAppId = $('#businessListTbody [name="businessCheckbox"]:checked').val();
	$.dialog({
		title : '绑定企业客户',
		init : loadBusinessList('businessListJson.action',lastAppId),
		content : document.getElementById('bindBusiness'),
		ok : function() {
			// 获取选择的业务ID（为了传到后台入库）以及业务名称（用于返回主页面显示）
			var appName = $('#businessListTbody [name="businessCheckbox"]:checked').parents('td').next().html();
			var appId = $('#businessListTbody [name="businessCheckbox"]:checked').val();
			if (appId == null || appId == ''){
				displayError("请选择一个企业客户！");
				return false;
			}
			// 当重新绑定时，先清除已选值（第一个span是标题，不能删除），添加“重新绑定”字样，显示业务名称。
			$("#bindDiv").children('span:not(:first)').remove();
			$("#bindDiv").children('a').html("重新绑定");
			$("#bindDiv").children('span').after('<span class="apply-span-name" style="width:190px">' + appName + '</span>');
			// 给业务Id赋值，如果第一次添加，就添加hidden标签；如果是第二次以后添加，就替换值
			if(!$("#appId").val())
				$('#pmApplyInfoAction').append('<input type="hidden" id="appId" name="appId" value="'+appId+'"/>');
			else
				$("#appId").val(appId);
		    return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}

var displayError = function(msg){
	$.compMsg({
		type : 'error',
		msg : msg
	});
};


var getPageData = function(url){
loadBusinessList(url,$('#businessListTbody [name="businessCheckbox"]:checked').val());
};

var loadBusinessList = function(url,lastAppId){
var queryBusinessName = $('#queryBusinessName').val();
var queryData = {};
if (url == 'businessListJson.action'){
	queryData = {pageSycn:'false','queryBusiness.name':queryBusinessName};
}
$.post(url,queryData,function(data){
	$('#businessListTbody').empty();
	$.each(data.businessInfoList,function(index,business){
	    var trHtml = '<tr>'
		+'<td align="center"><input type="radio" name="businessCheckbox" value="'+business.businessId + '" ';
	    // 当用户重新绑定业务时，弹出页默认显示上一次的选择
		if (business.businessId == lastAppId) {
			trHtml += ' checked = "checked"';
		}
		trHtml += '/></td>'
		+'<td>'+business.name+'</td>'
		+'<td>'+(business.description == null?'':business.description)+'</td>'
		+'</tr>';
	    $('#businessListTbody').append(trHtml);
	});
	$('#businessListPageBarDiv').html(data.pageBar);
	 $("select").uniform(); $.uniform.update();
}).fail(function(){
	$.compMsg({
		type : 'error',
		msg : '获取企业客户列表！'
	    });
});
};


//删除按钮
function deleteNet(obj) {
	if(rolsCount==0){
		return;
	}
	rolsCount--;
	$(obj).parent().remove();
}

function checkradioApplyNet() {
	var appId = $("#appId").val();
	var num = $("#num").val();
	if ($("#ApplyNet").attr("checked") == "checked") {
		if(appId == "" || appId == null){
			alert("请先绑定企业客户");
			$("#ApplyNet").prop("checked", false);
			return;
		}
		// 每次先清除网卡信息，目前物理机只能创建一个网卡 
		$("#ethList1").text("");
		$("#ethList2").text("");
		$("#ethList3").text("");
		$("#ethList4").text("");
		$("#ethList5").text("");
		
		if(num=="1"){
			$("#ethListTable1").show();
			$("#ethListTable2").hide();
			$("select[name='ethList2vlanId']").parents('tr').remove();
			$("#ethListTable3").hide();
			$("select[name='ethList3vlanId']").parents('tr').remove();
			$("#ethListTable4").hide();
			$("select[name='ethList4vlanId']").parents('tr').remove();
			$("#ethListTable5").hide();
			$("select[name='ethList5vlanId']").parents('tr').remove();
			
			addNet('ethList1');
		}else if(num=="2"){
			$("#ethListTable1").show();
			$("#ethListTable2").show();
			$("#ethListTable3").hide();
			$("select[name='ethList3vlanId']").parents('tr').remove();
			$("#ethListTable4").hide();
			$("select[name='ethList4vlanId']").parents('tr').remove();
			$("#ethListTable5").hide();
			$("select[name='ethList5vlanId']").parents('tr').remove();
			
			addNet('ethList1');
			addNet('ethList2');
		}else if(num=="3"){
			$("#ethListTable1").show();
			$("#ethListTable2").show();
			$("#ethListTable3").show();
			$("#ethListTable4").hide();
			$("select[name='ethList4vlanId']").parents('tr').remove();
			$("#ethListTable5").hide();
			$("select[name='ethList5vlanId']").parents('tr').remove();
			
			addNet('ethList1');
			addNet('ethList2');
			addNet('ethList3');
		}else if(num=="4"){
			$("#ethListTable1").show();
			$("#ethListTable2").show();
			$("#ethListTable3").show();
			$("#ethListTable4").show();
			$("#ethListTable5").hide();
			$("select[name='ethList5vlanId']").parents('tr').remove();
			
			addNet('ethList1');
			addNet('ethList2');
			addNet('ethList3');
			addNet('ethList4');
		}else if(num=="5"){
			$("#ethListTable1").show();
			$("#ethListTable2").show();
			$("#ethListTable3").show();
			$("#ethListTable4").show();
			$("#ethListTable5").show();
			
			addNet('ethList1');
			addNet('ethList2');
			addNet('ethList3');
			addNet('ethList4');
			addNet('ethList5');
		}
	} else {
		$("#ethListTable1").hide();
		$("select[name='ethList1vlanId']").parents('tr').remove();
		$("#ethListTable2").hide();
		$("select[name='ethList2vlanId']").parents('tr').remove();
		$("#ethListTable3").hide();
		$("select[name='ethList3vlanId']").parents('tr').remove();
		$("#ethListTable4").hide();
		$("select[name='ethList4vlanId']").parents('tr').remove();
		$("#ethListTable5").hide();
		$("select[name='ethList5vlanId']").parents('tr').remove();
	}
}
