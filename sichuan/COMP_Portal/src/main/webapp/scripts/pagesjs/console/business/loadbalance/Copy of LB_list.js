//查询列表
function queryLbBatch() {
	var lbname = $('#lbname').val();
	var lbip = $('#lbip').val();
	
	$("#selectlb").attr({
	action : "lbQueryListAction.action",
	method : "post"
	});
	$("#selectlb").append('<input type="hidden" name="lbname" value="' + lbname + '"/>');
	$("#selectlb").append('<input type="hidden" name="lbip" value="' + lbip + '"/>');
	$("#selectlb").submit();
}
function checkip(){
	var ip=$('#lbip').val();
	
}
function check() {
	if($('#msg').val()!=""){
		$.compMsg({
			type : 'success',
			msg : $('#msg').val()
		});
	}
	if($('#result').val()!="" && $('#result').val()!=undefined){
		$.compMsg({
			type : 'error',
			msg : $('#result').val()
		});
	}
	
}
function getPageData() {
	queryLbBatch();
}
function goToPage(lbid){
	if(lbid==''){
		
	}else{
		$("#lbid").val(lbid);
		$("#lbid").val();
		$("#gotoForm").attr('action','LBdetailbusinessAction.action');
		$("#gotoForm").submit();
	}
	
}

//申请页面的数据缓存
var varRespoolId;
var varRespoolPartId;
var standardId;
var itemId="";
var queryBusinessId;
$(function() {
$(".left-menu li:contains('负载均衡')").addClass("selected");//菜单显示当前
	
	// 自定义form样式
	$("select,:checkbox,:text").uniform();
	check();
	// 默认显示值
	var defaultTerm = $("#accountingType").children('option:selected').val()
			+ "Option";
	$("." + defaultTerm).show();
	addNet('ethList1');
	onloadResPool("");
	$("#respool").change(function() {
		varRespoolId = $(this).children('option:selected').val();
		varRespoolPartId = "";
		itemId="";
		onloadResPool(varRespoolId, varRespoolPartId);

	});
	// 所属业务名称显示
	queryBusinessId = $("#app").val();
});//初始化

// 加载资源池及分区信息
function onloadResPool(tempRespoolId, tempRespoolPartId) {

	var da_val = {
		itemId : itemId,
		respoolId : tempRespoolId,
		respoolPartId : tempRespoolPartId
	};
	$.ajax({
		url : 'vmOnloadResPoolAction.action?struts.enableJSONValidation=true',
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
				window.location.href = 'vmQueryListAction.action?optState=1';
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

			// 资源池无分区判断
			if (data.respoolParts.length == 0) {
				// console.js
				maskNotPart($(".details-con1"));
				$("#respoolpart").empty();
				$("#topItems").empty();
				$("#respoolpart").append('<option value=""></option>');
				$("#respoolPartName").val("");
				$.uniform.update();
				return false;
			} else {
				// console.js
				removeMask($(".details-con1"));
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
		}
	});
}
// 判断ip地址的合法性
function checkIP(ip) {
	var exp = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
	var reg = ip.value.match(exp);
	if (reg == null) {
		alert("ip地址: " + ip.value + " 不合法");
		return false;
	}
	return true;
}
//判断负载均衡名称
function checkname(){
	var lbname=$('#lbname').val();
	da_val={
		lbname:lbname,
	};
	$.ajax({
		url : 'LBcheckAction.action?struts.enableJSONValidation=true',
		type : 'POST',
		data : da_val,
		cache : false,
		dataType : 'json',
		success : function(data) {
			if(data.LBinfo.lbname!="" && data.LBinfo.lbname!=null){
				$.compMsg({
					type : 'error',
					msg : "负载均衡名称已存在！"
				});
			}
		}
	})
}

// 绑定业务
function bindBusiness() {
	$("#vlanSelect0").val("");
	$("#ipsegmentSelect0").val("");
	$("#privateIpSelect0").val("");
//	$("#ethList2").text("");
//	$("#ethList3").text("");
//	$("#ethList4").text("");
//	$("#ethList5").text("");
	// 弹出页弹出前，已选择的业务ID值，首次业务ID值为undefined
	var lastAppId = $('#businessListTbody [name="businessCheckbox"]:checked')
			.val();
	$.dialog({
		title : '绑定业务',
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
				displayError("请选择一个业务！");
				return false;
			}
			// 当重新绑定时，先清除已选值（第一个span是标题，不能删除），添加“重新绑定”字样，显示业务名称。
			$("#bindDiv").children('span:not(:first)').remove();
			$("#bindDiv").children('a').html("重新绑定");
			$("#bindDiv").children('span').after(
					'<span class="apply-span-name" style="width:190px">'
							+ appName + '</span>');
			// 给业务Id赋值，如果第一次添加，就添加hidden标签；如果是第二次以后添加，就替换值
			if (!$("#appId").val()){
				$('#LbsubmitAction').append(
						'<input type="hidden" id="appId" name="appId" value="'
								+ appId + '"/>');
			}
			else{
				$("#appId").val(appId);
			}
			changenet();
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
	loadBusinessList(url, $(
			'#businessListTbody [name="businessCheckbox"]:checked').val());
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
						/*$("select").uniform();*/
						$.uniform.update();
					}).fail(function() {
				$.compMsg({
					type : 'error',
					msg : '获取业务列表！'
				});
			});
}
//删除按钮
function deleteNet(obj) {
	if (rolsCount == 0) {
		return;
	}
	rolsCount--;
	$(obj).parent().remove();
	$('#addnet').show();
}

function checkradioApplyNet() {
	var appId = $("#appId").val();
	var num = 1;
	if ($("#ApplyNet").attr("checked") == "checked") {
		if (appId == "" || appId == null) {
			alert("请先绑定业务");
			$("#ApplyNet").prop("checked", false);
			return;
		}
		if (num == "1") {
			$("#ethListTable1").show();
			$("#ethListTable2").hide();
			$("select[name='ethList2vlanId']").parents('tr').remove();
			$("#ethListTable3").hide();
			$("select[name='ethList3vlanId']").parents('tr').remove();
			$("#ethListTable4").hide();
			$("select[name='ethList4vlanId']").parents('tr').remove();
			$("#ethListTable5").hide();
			$("select[name='ethList5vlanId']").parents('tr').remove();
		} else if (num == "2") {
			$("#ethListTable1").show();
			$("#ethListTable2").show();
			$("#ethListTable3").hide();
			$("select[name='ethList3vlanId']").parents('tr').remove();
			$("#ethListTable4").hide();
			$("select[name='ethList4vlanId']").parents('tr').remove();
			$("#ethListTable5").hide();
			$("select[name='ethList5vlanId']").parents('tr').remove();
		} else if (num == "3") {
			$("#ethListTable1").show();
			$("#ethListTable2").show();
			$("#ethListTable3").show();
			$("#ethListTable4").hide();
			$("select[name='ethList4vlanId']").parents('tr').remove();
			$("#ethListTable5").hide();
			$("select[name='ethList5vlanId']").parents('tr').remove();
		} else if (num == "4") {
			$("#ethListTable1").show();
			$("#ethListTable2").show();
			$("#ethListTable3").show();
			$("#ethListTable4").show();
			$("#ethListTable5").hide();
			$("select[name='ethList5vlanId']").parents('tr').remove();
		} else if (num == "5") {
			$("#ethListTable1").show();
			$("#ethListTable2").show();
			$("#ethListTable3").show();
			$("#ethListTable4").show();
			$("#ethListTable5").show();
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
var rols=0;
var rolsCount=0;
//添加网卡
function addNet(ethList) {
	$('#addnet').hide();
	var str = '<tr >';
	str +='<td colspan="3"  align="center"><span class="apply-span-name">浮动IP：</span></td>';
	str += '<td   colspan="4"  align="center" >VLAN：<select class="select-max" style="width:90px"   id="vlanSelect'
			+ rols
			+ '" name="'
			+ 'vlanId" onchange="changeVlan(this)"></select></td>';
	str += '<td  colspan="6"   align="center" style="overflow:hidden;">IP段：<select class="select-max" style="width:154px"    id="ipsegmentSelect'
			+ rols
			+ '" name="'
			+ 'ipSegmentId" onchange="changeIpsegment(this)"></select></td>';
	str += '<td    colspan="5"  align="center" >IP：<select class="select-max" style="width:140px"   id="privateIpSelect'
			+ rols + '" name="lbip"></select></td>';
	str += '</tr>';
	$("#relate_resource_div").find('#' + ethList).append(str);
}
function changenet(){
	var appId = queryBusinessId;
	// 查询虚拟机业务对应的VLAN列表
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
				window.location.href = 'vmQueryListAction.action?optState=1';
				return;
			}
			var selectVal = "#vlanSelect" + rols;
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
			
		}
	});
}
// 改变vlan，ajax请求IP段
function changeVlan(vlanId) {
	var selectVal = $(vlanId).attr('id');
	selectVal = "#" + selectVal.replace("vlanSelect", "ipsegmentSelect");
	var vlanId = vlanId.value;
	var appId = queryBusinessId;
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
							alert("展示IP段信息异常，将跳转到浏览页面");
							window.location.href = 'vmQueryListAction.action?optState=1';
							return;
						}

						// var selectVal= "#ipsegmentSelect"+rols;
						$(selectVal).empty();
						for (var i = 0; i < data.ipSegmentList.length; i++) {
							if (i == 0) {
								$(selectVal).append(
										'<option value=""></option>');
							}
							$(selectVal).append(
									'<option value="'
											+ data.ipSegmentList[i].ipSegmentId
											+ '">'
											+ data.ipSegmentList[i].ipSegment
											+ '</option>');
							$.uniform.update();
						}
					}
				});
	}
}
//改变vlan，ajax请求privateIp
function changeIpsegment(ipSegmentId) {

	var selectValTemp = $(ipSegmentId).attr('id');
	selectValTemp = "#"
			+ selectValTemp.replace("ipsegmentSelect", "privateIpSelect");
	var selectVal = selectValTemp;
	var vlanselectVal = selectValTemp.replace("privateIpSelect", "vlanSelect");
	var vlan = $(vlanselectVal).val();
	var ipSegmentId = ipSegmentId.value;
	var lbflag="lbflag";
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
							alert("展示私网ip信息异常，将跳转到浏览页面");
							window.location.href = 'vmQueryListAction.action?optState=1';
							return;
						}
						// var selectVal="#privateIpSelect"+rols;
						$(selectVal).empty();
						for (var i = 0; i < data.ipInfos.length; i++) {
							if (i == 0) {
								$(selectVal).append(
										'<option value=""></option>');
							}
							$(selectVal).append(
									'<option value="' + data.ipInfos[i].ip
											+ '">' + data.ipInfos[i].ip
											+ '</option>');
							$.uniform.update();
						}

					}
				});
	}
}
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
	if (appId == "" || appId == null) {
		$.compMsg({
			type : 'error',
			msg : '所属业务不能为空！'
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
	var lbport=$("#lbport").val();
	var re =  /^([0-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/;     
    if (!re.test(lbport)){
    	$.compMsg({
			type : 'error',
			msg : "服务端口必须为0~65535的整数!"
		});
		return;
    }
	
		var thisForm = document.forms["LBsubmitAction"];
		document.forms["LBsubmitAction"].submit();
}
function checkNumber(theObj) {
	  var reg = /^[0-9]+.?[0-9]*$/;
	  if (reg.test(theObj)) {
	    return true;
	  }
	  return false;
	}
