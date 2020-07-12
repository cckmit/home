function goToLBListPage() {
	var nodeType = $("#nodeType").val();
	var nodeId = $("#nodeId").val();
	var treeNodeName = $("#treeNodeName").val();
	var pnodeId = $("#pnodeId").val();
	var pnodeName = $("#pnodeName").val();
	var appId = $("#appId").val();
	var curFun = $("#curFun").val();
	var businessId=$("#appId").val();
	window.location.href = 'lbQueryListAction.action?type=0&queryBusinessId='
			+ businessId + "&nodeType=" + nodeType + "&nodeId=" + nodeId
			+ "&treeNodeName=" + treeNodeName + "&pnodeId=" + pnodeId
			+ "&pnodeName=" + pnodeName + "&appId=" + appId + "&curFun="
			+ curFun;
}
function goToLBApplyPage() {
	window.location.href = 'LBapplyAction.action';
}
$(function() {
	$(".left-menu li:contains('负载均衡')").addClass("selected");
	// 自定义form样式
	$("select,:radio,:text,textarea").uniform();
	// 主机操作
	$(".operation").click(function() {
		var $vmopt = $("#vmopt");
		$vmopt.css("display") == "block" ? $vmopt.hide() : $vmopt.show();
	});
	$("#vmopt").mouseout().css("display", "none");
	operate();
	checklength();
	addNet('ethList1');
});
//删除负载均衡服务
function delLbinfo(){
	if($('#LBinfostatus').val()==0){
		$.compMsg({
			type : 'error',
			msg : '负载均衡服务还未创建无法删除!'
		});
		return;
	}
	var length=$('#objInfolistsize').val();
	if(length!=undefined){
		$.compMsg({
			type : 'error',
			msg : '负载均衡服务上存在对象无法删除！'
		});
		return;
	}
	if(length==undefined){
	$.dialog({
		title : '删除负载均衡服务',
		content : '请您确认是否删除负载均衡服务?',
		ok : function() {
			var lbid=$('#lbid').val();
			var instanceid=$('#instanceid').val();
			var resPoolId=$('#respoolId').val();
			var resPoolPartId=$('#respoolPartId').val();
			var da_val = {
					'LBinfo.LBid' : lbid,
					'LBinfo.instanceid':instanceid,
					'LBinfo.respoolId' : resPoolId,
					'LBinfo.resPoolPartId' : resPoolPartId
				};
			$.ajax({
				type : "POST",
				url : 'LBdelbusinfo.action',
				data : da_val,
				dataType : "JSON",
				cache : false,
				success : function(data) {
					if (data.mes != null) {
						if (data.result == 'success') {
							$.compMsg({
								type : 'success',
								msg : data.mes
							});
							goToLBListPage();
						} else if (data.result == 'error') {
							$.compMsg({
								type : 'error',
								msg : data.mes
							});
						}
					}
				}
			});
			return true;
		},
		cancelVal : '关闭',
		cancel : function() {
			window.location.reload();
		},
		lock : true
	});
	}
}
//添加负载均衡对象
function addobj() {
	if($('#LBinfostatus').val()==0){
		$.compMsg({
			type : 'error',
			msg : '负载均衡服务还未创建无法添加对象!'
		});
		return;
	}
	$.dialog({
		title : '添加负载均衡对象',
		content : document.getElementById('modify_addobj_div'),
		init : function() {
			$("[name='modify_remark']").val($.trim($("#remark").text()));
		},
		ok : function() {
			var objport = $('#objport').val();
			var appId = $('#appId').val();
			var privateIpSelect = $('#ipSelect0').val();
			var vlan = $('#vlanSelect0').val();
			var ipsegmentSelect = $('#ipsegmentSelect0').val();
			var objdescription = $('#objdescription').val();
			var lbid = $('#lbid').val();
			var instanceid=$('#instanceid').val();
			var resPoolPartId=$('#respoolPartId').val();
			var resPoolId=$('#respoolId').val();
			var re =  /^([0-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/;     
		    if (!re.test(objport)){
		    	$.compMsg({
					type : 'error',
					msg : "对象端口必须为0~65535的整数!"
				});
				return;
		    }
			if(objport==""||objport==null){
				$.compMsg({
					type : 'error',
					msg : "对象端口不能为空!"
				});
				return;
			}
			if(privateIpSelect==""||privateIpSelect==null){
				$.compMsg({
					type : 'error',
					msg : "对象IP不能为空!"
				});
				return;
			}
			if(objdescription==""||objdescription==null){
				$.compMsg({
					type : 'error',
					msg : "对象描述不能为空!"
				});
				return;
			}
			var da_val = {
				'objInfo.hostport' : objport,
				'objInfo.obj_AppId' : appId,
				'objInfo.hostip' : privateIpSelect,
				'objInfo.obj_Vlan' : vlan,
				'objInfo.ipsegmentid' : ipsegmentSelect,
				'objInfo.objdescription' : objdescription,
				'objInfo.LBid' : lbid,
				instanceid:instanceid,
				respoolPartId:resPoolPartId,
				respoolId:resPoolId
			};
			$.ajax({
				type : "POST",
				url : 'LBaddobjbusinfo.action',
				data : da_val,
				dataType : "JSON",
				cache : false,
				success : function(data) {
					if (data.mes != null) {
						if (data.result == 'success') {
							$.compMsg({
								type : 'success',
								msg : data.mes
							});
						} else if (data.result == 'error') {
							$.compMsg({
								type : 'error',
								msg : data.mes
							});
						}
					}
					window.location.href = 'LBdetailbusinessAction.action?LBid='+lbid;
				}
			});
			return true;
		},
		cancelVal : '关闭',
		cancel : function() {
			window.location.reload();
		},
		lock : true
	});

}
//删除负载均衡对象
function delobjinfo(hostIp,iD,hostPort) {
	$.dialog({
		title : '删除负载均衡对象',
		content : '请您确认是否删除对象?',
		ok : function() {
			var hostip=hostIp;
			var lbid=$('#lbid').val();
			var hostport=hostPort;
			var instanceid=$('#instanceid').val();
			var resPoolPartId=$('#respoolPartId').val();
			var resPoolId=$('#respoolId').val();
			var id=iD;
			var da_val = {
					'objInfo.hostip' : hostip,
					'objInfo.hostport' : hostport,
					'objInfo.LBid' : lbid,
					'objInfo.id' : id,
					instanceid : instanceid,
					respoolId: resPoolId,
					respoolPartId: resPoolPartId
				};
			$.ajax({
				type : "POST",
				url : 'LBdelobjbusinfo.action',
				data : da_val,
				dataType : "JSON",
				cache : false,
				success : function(data) {
					if (data.mes != null) {
						if (data.result == 'success') {
							$.compMsg({
								type : 'success',
								msg : data.mes
							});
						} else if (data.result == 'error') {
							$.compMsg({
								type : 'error',
								msg : data.mes
							});
						}
					}
					window.location.href = 'LBdetailbusinessAction.action?LBid='+lbid;
				}
			});
			return true;
		},
		cancelVal : '关闭',
		cancel : function() {
			window.location.reload();
		},
		lock : true
	});

}
// 绑定业务
function bindBusiness() {
	$("#vlanSelect0").empty();
	$("#ipsegmentSelect0").empty();
	$("#ipSelect0").empty();
	// $("#ethList2").text("");
	// $("#ethList3").text("");
	// $("#ethList4").text("");
	// $("#ethList5").text("");
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
			if (!$("#appId").val()) {
				$('#modify_addobj_div').append(
						'<input type="hidden" id="appId" name="appId" value="'
								+ appId + '"/>');
			} else {
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
						/* $("select").uniform(); */
						// $.uniform.update();
					}).fail(function() {
				$.compMsg({
					type : 'error',
					msg : '获取业务列表！'
				});
			});
}
// 删除按钮
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
	if (appId == "" || appId == null || appId == undefined) {
		alert("请先绑定业务");
		return false;
	}
	return true;
}
var rols = 0;
var rolsCount = 0;
// 添加网卡
function addNet(ethList) {
	$('#addnet').hide();
	var str = '<tr >';
	str += '<td   colspan="4"  align="center" >VLAN：<select class="select-max" style="width:90px"   id="vlanSelect'
			+ rols
			+ '" name="'
			+ 'vlanId" onchange="changeVlan(this)" onclick="checkradioApplyNet()"></select></td>';
	str += '<td  colspan="6"   align="center" style="overflow:hidden;">IP段：<select class="select-max" style="width:154px"    id="ipsegmentSelect'
			+ rols
			+ '" name="'
			+ 'ipSegmentId" onchange="changeIpsegment(this)"></select></td>';
	str += '<td    colspan="5"  align="center" >IP：<select class="select-max" style="width:140px"   id="ipSelect'
			+ rols + '" name="lbip"></select></td>';
	str += '</tr>';
	$("#modify_addobj_div").find('#' + ethList).append(str);
}
function changenet() {
	var appId = $("#appId").val();
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
				goToLBListPage();
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
function changeVlan(vlanId, ipSegmentId) {
	var selectVal;
	// 如果是修改，那么list的ID为ipsegmentSelect
	if (ipSegmentId != null && ipSegmentId != '') {
		selectVal = "#ipsegmentSelect";
	} else {// 如果添加时，需要以下处理
		selectVal = $(vlanId).attr('id');
		selectVal = "#" + selectVal.replace("vlanSelect", "ipsegmentSelect");
		vlanId = vlanId.value;
	}
	var appId = $("#appId").val();
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
							goToLBListPage();
							return;
						}

						// var selectVal= "#ipsegmentSelect"+rols;
						$(selectVal).empty();
						for (var i = 0; i < data.ipSegmentList.length; i++) {
							if (i == 0) {
								$(selectVal).append(
										'<option value=""></option>');
							}

							// 添加时查询所有IP段,修改时还需要默认选择修改前数据
							if (ipSegmentId != null
									&& ipSegmentId != ''
									&& ipSegmentId == data.ipSegmentList[i].ipSegmentId) {
								$(selectVal)
										.append(
												'<option value="'
														+ data.ipSegmentList[i].ipSegmentId
														+ '" selected="selected">'
														+ data.ipSegmentList[i].ipSegment
														+ '</option>');
							} else {
								$(selectVal)
										.append(
												'<option value="'
														+ data.ipSegmentList[i].ipSegmentId
														+ '">'
														+ data.ipSegmentList[i].ipSegment
														+ '</option>');
							}
						}
						$.uniform.update();
					}
				});
	}
}
// 改变vlan，ajax请求privateIp
function changeIpsegment(ipSegmentId, vlanId, ip) {

	var selectVal;
	// 如果是修改，那么list的ID为ipSelect
	if (ip != null && ip != '') {
		selectVal = "#ipSelect";
	} else {// 如果添加时，需要以下处理
		var selectValTemp = $(ipSegmentId).attr('id');
		selectValTemp = "#"
				+ selectValTemp.replace("ipsegmentSelect", "ipSelect");
		selectVal = selectValTemp;
		var vlanselectVal = selectValTemp.replace("ipSelect", "vlanSelect");
		vlanId = $(vlanselectVal).val();
		ipSegmentId = ipSegmentId.value;
	}
	var lbflag = "objflag";
	if (!("" == ipSegmentId) && !("" == vlanId)) {

		var da_val = {
			logicVlanId : vlanId,
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
							goToLBListPage();
							return;
						}
						// var selectVal="#privateIpSelect"+rols;
						$(selectVal).empty();
						for (var i = 0; i < data.ipInfos.length; i++) {
							if (i == 0) {
								$(selectVal).append(
										'<option value=""></option>');
								// 添加时查询所有未使用的IP,修改时还需要默认选择修改前数据
								if (ip != null && ip != '') {
									$(selectVal).append(
											'<option value="' + ip
													+ '" selected="selected">'
													+ ip + '</option>');
								}
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

function operate() {
	var $delBtn = '<li id="del"><a class="del" href="javascript:delLbinfo();">删除</a></li>';
	var $snapBtn = '<li id="snap"><a class="snap" href="javascript:goToUpdate();">修改</a></li>';
	$("#vmopt").empty();
	$("#vmopt").append($delBtn);
	$("#vmopt").append($snapBtn);
}
// 修改负载均衡策略
function goToUpdate() {
	if($('#LBinfostatus').val()==0){
		$.compMsg({
			type : 'error',
			msg : '负载均衡服务还未创建无法修改!'
		});
		return;
	}
	$.dialog({
		title : '修改负载均衡',
		content : document.getElementById('modify_updatelbinfo_div'),
		init : function() {
			clearoption();
		},
		button : [ {
			name : '确定',
			callback : function() {
				this.button({
					name : '确定',
					disabled : true
				});
				var lbStrategy = $('#Strategy').val();
				var lbid = $('#lbid').val();
				var resPoolPartId=$('#respoolPartId').val();
				var resPoolId=$('#respoolId').val();
				var instanceid=$('#instanceid').val();
				var da_val = {
					'LBinfo.Strategy' : lbStrategy,
					'LBinfo.LBid' : lbid,
					'LBinfo.instanceid':instanceid,
					'LBinfo.respoolId' : resPoolId,
					'LBinfo.respoolPartId' : resPoolPartId
				};
				$
				.ajax({
					url : 'LBupdatebusAction.action',
					type : 'POST',
					data : da_val,
					cache : false,
					dataType : 'json',
					success : function(data) {
						if (data.mes != null) {
							if (data.result == 'success') {
								$.compMsg({
									type : 'success',
									msg : data.mes
								});
							} else if (data.result == 'error') {
								$.compMsg({
									type : 'error',
									msg : data.mes
								});
							}
						}
						window.location.href = 'LBdetailbusinessAction.action?LBid='+lbid;
					}
				  
				});
				return true;
			},
			focus : true
		} ],
		cancelVal : '取消',
		cancel : function() {
			window.location.reload();
		},
		lock : true
	});
}
function clearoption() {
	var changeStrategy = $('#changeStrategy').val();
	$("#Strategy option[value='" + changeStrategy + "']").remove();
}

function getVlans(vlanId) {
	var appId = $("#appId").val();
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
			if (data.resultRoute == "error") {
				window.location.href = 'exceptionIntercepor.action';
				return;
			}
			if (data.resultRoute == "failure") {
				alert("展示资源池信息异常，将跳转到浏览页面");
				goToLBListPage();
				return;
			}

			var selectVal;
			// 修改时不修改行数，所以不需要列参数
			if (vlanId == null || vlanId == '') {
				selectVal = "#vlanSelect" + rols;
			} else {
				selectVal = "#vlanSelect";
			}

			$(selectVal).empty();
			for (var i = 0; i < data.vlanList.length; i++) {
				if (i == 0) {
					$(selectVal).append('<option value=""></option>');
				}
				// 添加时查询所有vlan,修改时还需要默认选择修改前数据
				if (vlanId != null && vlanId != ''
						&& vlanId == data.vlanList[i].vlanId) {
					$(selectVal).append(
							'<option value="' + data.vlanList[i].vlanId
									+ '" selected="selected">'
									+ data.vlanList[i].vlanName + '</option>');
				} else {
					$(selectVal).append(
							'<option value="' + data.vlanList[i].vlanId + '">'
									+ data.vlanList[i].vlanName + '</option>');
				}
				$.uniform.update();
			}
			// 添加时增加行数，修改不变
			if (vlanId == null || vlanId == '') {
				rolsCount++;
				rols++;
			}
		}
	});
}

function checklength(){
	var length=$("#objInfolistsize").val();
	for(var i=1;i<length+1;i++){
		objInfoList=$("#objtable").find('tr').eq(i).find('td').eq(2).find('span').text();
		getBLen=function(str) {
			if (str == null) return 0;
			if (typeof str != "string"){
				str += "";
			}
			return str.replace(/[^\x00-\xff]/g,"01").length;
		}
		var lengths=getBLen(objInfoList);
		if(objInfoList!="" & lengths>15 & objInfoList!="null"){
			var strs=objInfoList.substring(0,15)+'...';
			$("#objtable").find('tr').eq(i).find('td').eq(2).find('span').html(strs);
		}else if(objInfoList!="" & lengths<=15 & objInfoList!="null"){
			$("#objtable").find('tr').eq(i).find('td').eq(2).find('span').html(objInfoList);
		}
		if(objInfoList=="null"){
			$("#objtable").find('tr').eq(i).find('td').eq(2).find('span').html("");
		}
		
	}
}