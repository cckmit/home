$( function() {
	//菜单显示当前，开发时删除

	$("#userOrder").siblings().removeClass("active");
	$("#userOrder").addClass("active");
	
	fomateDate();
	cutStringFun();
});
function cutStringFun(){
	//列表长字段截取
    var $box = $('.cut');
	$box.each( function(index, jdom) {
		var objString = $(jdom).text();
		var byteValLen = 0;
		var returnText = "";
		var maxTextLength = 0;
		if($(jdom).attr("class") == 'cut pool'){
			maxTextLength = 7*2;
		}else if($(jdom).attr("class") == 'cut app'){
			maxTextLength = 13*2;
		}
		for (var i = 0; i < objString.length; i++) {
	        (objString[i].match(/[^x00-xff]/ig) != null) ? (byteValLen += 2) : (byteValLen += 1);
	        returnText += objString[i];
	        if (byteValLen > maxTextLength) {
	        	$(jdom).attr('title',objString);
				objString = $(jdom).text(returnText.substring(0,i-1) + '...');
	            break;
	        }
	    }
	});
}
//显示隐藏规格详情
function isShow(obj){
	if($("#"+obj).is(":hidden")){
		$("#"+obj).show();
	}else{
		$("#"+obj).hide();
	}
}

var table ='';
 table += '<thead>';
 /*table += '<th class="nl" style="width: 26%;">订单号</th>';*/
 table += '<th >子订单号</th>';
 table += '<th >资源类型</th>';
/* table += '<th style="width: 7%;">总价</th>';*/
 table += '<th>状态</th>';
 table += '<th>资源池名称</th>';
 table += '<th>企业客户名称</th>';
 table += '<th>申请时间</th>';
 table += '<th>操作</th>';
 table += '</thead>';
function getStatus(status){
	if(status=="0"){
		return "待审批";
	}
	if(status=="1"){
		return "已通过";
	}
	if(status=="3"){
		return "已生效";
	}
	if(status=="2"){
		return "未通过";
	}
	if(status=="4"){
		return "已取消";
	}
	if(status=="5"){
		return "已过期";
	}
	if(status=='6'){
		return "已回收";
	}
	if(status=='7'){
		return "修改待审";
	}
}
function getOrderType(type){
	if(type=="0"){
		return "云主机";
	}
	if(type=="1"){
		return "物理机";
	}
	if(type=="2"){
		return "小型机";
	}
	if(type=="3"){
		return "小型机分区";
	}
	if(type=="4"){
		return "虚拟机备份任务";
	}
	if(type=="5"){
		return "云硬盘";
	}
	if(type=="6"){
		return "云储存";
	}
	if(type=="7"){
		return "公网IP";
	}
	if(type=="8"){
		return "带宽";
	}
	if(type=="9"){
		return "安全组";
	}
	if(type=="10"){
		return "虚拟机快照";
	}
	if(type=="11"){
		return "虚拟机克隆";
	}
	if(type=="12"){
		return "IP段";
	}
	if(type=="13"){
		return "VLAN";
	}
	if(type=="14"){
		return "负载均衡";
	}
	if(type=="15"){
		return "分布式文件存储";
	}
	if(type=="16"){
		return "虚拟防火墙";
	}
}
/**
 * 格式化数据展示方式
 * 将相同的父订单号合并
 */
function fomateDate(){
	var ls=null;
	var n =1;
	var obj=null;
	$("#ordertable tr").each(function(i){
		if(i!=0){
			var parentId = $(this).find('td').eq(0).text();
			if(parentId == ls){
				n=n+1;
				$(this).find('td').eq(0).remove();
			}else{
				if(obj!=undefined&&obj!=null){
					$(obj).find('td').eq(0).attr("rowspan",n);
				}
				ls = parentId;
				obj = this;
				n=1;
			}
		}
	});
	if(obj!=undefined&&obj!=null){
		$(obj).find('td').eq(0).attr("rowspan",n);
	}
}
//翻页调用js
function getPageData(url) {
	queryOrder(url);
}

function queryOrder(url) {
	var status = "";
	var caseType = "";
	var appName = "";
	var resPoolId = "";
	if ($('#select').val() != "notSelect") {
		caseType = $('#select').val();
	}
	if ($('#pool').val() != "notSelect") {
		resPoolId = $('#pool').val();
	}
	if ($('#appName').val() != "") {
		appName = $('#appName').val();
	}
	if ($('#waitAudit').prop('checked')){
		status += "0,7,";
	}
	if ($('#auditPass').prop('checked')) {
		status += "1,";
	}
	if ($('#valid').prop('checked')) {
		status += "3,";
	}
	if ($('#auditNopass').prop('checked') ) {
		status += "2,";
	}
	if ($('#cancle').prop('checked') ) {
		status += "4,";
	}
	if ($('#expire').prop('checked')) {
		status += "5,";
	}
	if ($('#invalid').prop('checked') ) {
		status += "6,";
	}
	/*if ($('#modifyWaitAudit').attr('checked') == "checked") {
		status += "7,";
	}*/
	status = status.substring(0, status.length - 1);
	var da_val;
	if (url == '' || url == undefined) {
		da_val = {
			caseType : caseType,
			status : status,
			appName : appName,
			resPoolId : resPoolId
		};
		url = 'orderQueryJson.action';
	}
	$
			.ajax( {
				type : "POST",
				url : url,
				data : da_val,
				dataType : "JSON",
				cache : false,
				success : function(data) {
					if(data.resultPath=="error"){
						window.location.href = 'exceptionIntercepor.action';
					}
					if (data.orderInfos == null) {
						$.compMsg( {
							type : 'success',
							msg : "暂无符合条件的订单数据！"
						});
						$("#ordertable").empty();
						$("#ordertable").append(table);
						return;
					}
					var list = data.orderInfos.list;
					var page = data.orderInfos.page;
					if(list.length==0){
						$.compMsg( {
							type : 'success',
							msg : "暂无符合条件的订单数据！"
						});
						$("#ordertable").empty();
						$("#ordertable").append(table);
						$(".pageBar").html(page);
						return;
					}
					if (!jQuery.isEmptyObject(data.orderInfos.list)) {
						var str = table;
						str+='<tbody>';
						for ( var i = 0; i < list.length; i++) {
							str += '<tr class="iterator">';
							/*str += '<td style="font-size: 10px;">' + list[i].parentId + '</td>';*/
							str += '<td title="订单号: ' + list[i].parentId + '">' + list[i].orderId + '</td>';
							str+='<td>'+getOrderType(list[i].caseType)+'</td>';
							/*str += '<td>' + data.orderInfos[i].allPrice + '</td>';*/
							str += '<td>' + getStatus(list[i].status) + '</td>';
							str += '<td class="cut pool">' + list[i].resPoolName + '</td>';
							str += '<td class="cut app">' + list[i].appName + '</td>';
							str += '<td>' + list[i].updateTime + '</td>';
							str += '<td class="table-opt-block"><a href="#" onclick="orderInfoDetail(\''+list[i].caseType+'\',\''+list[i].orderId+'\',\''+list[i].status+'\',true)">详情</a>';
							//str += '<a href="javascript:void(0);">取消</a> <a href="javascript:void(0);">续订</a>';
							str += '</td></tr>';
						}
						str+='</tbody>';
						$("#ordertable").empty();
						$("#ordertable").append(str);
						$(".pageBar").html(page);
						fomateDate();
						cutStringFun();
				}
			}
			
			});
	
}
function getDate(date){
	if(date=="y"){
		return "年";
	}
	if(date=="h"){
		return "小时";
	}
	if(date=="d"){
		return "天";
	}
	if(date=="m"){
		return "月";
	}
}

function orderInfoDetail(type,orderId,status,isDetail){
	var da_val = {
		orderId : orderId,
		caseType:type
	};
	$.ajax( {
		type : "POST",
		url : 'orderDetail.action',
		data : da_val,
		dataType : "JSON",
		cache : false,
		success : function(data) {
			if(data.resultPath=="error"){
				$.compMsg( {
					type : 'error',
					msg : "订单详细信息查询失败！"
				});
				return;
			}
			var caseInfo = null;
			var caseInfoEdit = null;
			var title="";
			if(type=="0"){
				caseInfo = data.vmInfo;
				caseInfoEdit = data.vmInfoEdit;
				title = "云主机";
				$("#rpoolName1").css('display','block');
				$("#rpoolName2").css('display','block');
			}
			if(type=="1"){
				caseInfo = data.pmInfo;
				caseInfoEdit = data.pmInfoEdit;
				title = "物理机";
				$("#rpoolName1").css('display','block');
				$("#rpoolName2").css('display','block');
			}
			if(type=="4"){
				caseInfo = data.bkInfo;
				title = "虚拟机备份任务";
				$("#rpoolName1").css('display','block');
				$("#rpoolName2").css('display','block');
			}
			if(type=="5"){
				caseInfo = data.ebsInfo;
				caseInfoEdit = data.ebsInfoEdit;
				title = "云硬盘";
				$("#rpoolName1").css('display','block');
				$("#rpoolName2").css('display','block');
			}
			if(type=="12"){
				caseInfo = data.pipInfo;
				title = "IP段";
				$("#rpoolName1").css('display','block');
				$("#rpoolName2").css('display','block');
			}
			if(type=="13"){
				caseInfo = data.vlanInfo;
				title = "VLAN";
				$("#rpoolName1").css('display','none');
				$("#rpoolName2").css('display','none');
			}
			if(type=="7"){
				caseInfo = data.publicIpInfo;
				title = "公网IP";
				$("#rpoolName1").css('display','block');
				$("#rpoolName2").css('display','block');
			}
			if(type=="14"){
				caseInfo = data.loadbalanceInfo;
				title = "负载均衡";
				$("#rpoolName1").css('display','block');
				$("#rpoolName2").css('display','block');
			}
			if(type=="15"){
				caseInfo = data.fileStoreInfo;
				title = "分布式文件存储";
				$("#rpoolName1").css('display','block');
				$("#rpoolName2").css('display','block');
			}
			if(type=="16"){
				caseInfo = data.vfireWallInfo;
				title = "虚拟防火墙";
				$("#rpoolName1").css('display','block');
				$("#rpoolName2").css('display','block');
			}
			
			$('#vm_info').hide();
			$('#pm_info').hide();
			$('#bk_info').hide();
			$('#ebs_info').hide();
			$('#pip_info').hide();
			$('#vlan_info').hide();
			$('#publicIp_info').hide();
			$('#loadbalance_info').hide();
			$('#fileStore_info').hide();
			$('#vfireWall_info').hide();
			if (!jQuery.isEmptyObject(data.orderInfo)&&!jQuery.isEmptyObject(caseInfo)) {
				$('#parentId').val(data.orderInfo.parentId);
				$('#orderId').val(data.orderInfo.orderId);
				$('#resPoolName').val(data.orderInfo.resPoolName);
				$('#resPoolPartName').val(data.orderInfo.resPoolPartName);
				$('#appName_detail').val(data.orderInfo.appName);
				$('#status').val(getStatus(data.orderInfo.status));
				$('#caseType').val(getOrderType(data.orderInfo.caseType));
				if(type=="0"){
					$('#vm_info').show();
					if(caseInfo.paramFlag == 0){
						$('#vm_item').show();
						$('#vm_itemName').val(caseInfo.itemName);
					}else{
						$('#vm_item').hide();
					}
					if("7" == data.orderInfo.status && caseInfoEdit != null && caseInfoEdit.vmName != null && caseInfoEdit.vmName!=""){
						$('#vm_vmName').val(caseInfoEdit.vmName);
						$('#vm_vmName').css("color","red");
					}else{
						$('#vm_vmName').val(caseInfo.vmName);
						$('#vm_vmName').css("color","#777");
					}
					if("7" == data.orderInfo.status && caseInfoEdit != null && caseInfoEdit.cpuNum != null && caseInfoEdit.cpuNum!=""){
						$('#vm_cpuNum').val(caseInfoEdit.cpuNum+"核");
						$('#vm_cpuNum').css("color","red");
					}else{
						$('#vm_cpuNum').val(caseInfo.cpuNum+"核");
						$('#vm_cpuNum').css("color","#777");
					}
					if("7" == data.orderInfo.status && caseInfoEdit != null && caseInfoEdit.ramSize != null && caseInfoEdit.ramSize!=""){
						$('#vm_ramSize').val(caseInfoEdit.ramSize+"M");
						$('#vm_ramSize').css("color","red");
					}else{
						$('#vm_ramSize').val(caseInfo.ramSize+"M");
						$('#vm_ramSize').css("color","#777");
					}
					if("7" == data.orderInfo.status && caseInfoEdit != null && caseInfoEdit.discSize != null && caseInfoEdit.discSize!=""){
						$('#vm_discSize').val(caseInfoEdit.discSize+"G");
						$('#vm_discSize').css("color","red");
					}else{
						$('#vm_discSize').val(caseInfo.discSize+"G");
						$('#vm_discSize').css("color","#777");
					}
					$('#vm_isoName').val(caseInfo.isoName);
					$('#vm_description').val(caseInfo.description);
					$('.vm_netListTr').remove();
					$.each(caseInfo.netList, function(index) {
						var tr = $('<tr class="vm_netListTr">');
						tr.appendTo('#vm_netListTbody')
						.append('<td>' + this.eth + '</td> ')
						.append('<td>' + this.vlanName+ '</td>')
						.append('<td>' + this.startIp + '~' + this.endIp + '</td>')
						.append('<td>' + this.ip + '</td> ')
						.append('<td>' + this.gateway+ '</td>');
						if(this.flag != 'old' && "7" == data.orderInfo.status){
							tr.css("color","red");
						}
					});
				}
				if(type=="1"){
					$('#pm_info').show();
					if(caseInfo.paramFlag == 0){
						$('#pm_item').show();
						$('#pm_itemName').val(caseInfo.itemName);
					}else{
						$('#pm_item').hide();
					}
					if("7" == data.orderInfo.status && caseInfoEdit != null && caseInfoEdit.pmName != null && caseInfoEdit.pmName!=""){
						$('#pm_pmName').val(caseInfoEdit.pmName);
						$('#pm_pmName').css("color","red");
					}else{
						$('#pm_pmName').val(caseInfo.pmName);
						$('#pm_pmName').css("color","#777");
					}
					$('#pm_serverType').val(caseInfo.serverType);
					$('#pm_cpuType').val(caseInfo.cpuType);
					$('#pm_ramSize').val(caseInfo.ramSize+"M");
					$('#pm_discSize').val(caseInfo.discSize+"G");
					$('#pm_isoName').val(caseInfo.isoName);
					$('#pm_description').val(caseInfo.description);
					$('.pm_netListTr').remove();
					$.each(caseInfo.netList, function(index) {
						var tr = $('<tr class="pm_netListTr">');
						tr.appendTo('#pm_netListTbody')
						.append('<td>' + this.eth + '</td> ')
						.append('<td>' + this.vlanName+ '</td>')
						.append('<td>' + this.startIp + '~' + this.endIp + '</td>')
						.append('<td>' + this.ip + '</td> ')
						.append('<td>' + this.gateway+ '</td>');
						if(this.flag != 'old' && "7" == data.orderInfo.status){
							tr.css("color","red");
						}
					});
				}
				if(type=="4"){
					$('#bk_info').show();
					$('#bk_vmBakName').val(caseInfo.vmBakName);
					$('#bk_vmName').val(caseInfo.vmName);
					$('#bk_backupStartTime').val(caseInfo.backupStartTime);
					if("1"==caseInfo.backupCyc){
						$('#bk_backupCyc').val('每天');
					}else if("7"==caseInfo.backupCyc){
						$('#bk_backupCyc').val('每周');
					}
					$('#bk_backStoreTime').val(caseInfo.backStoreTime);
					$('#bk_description').val(caseInfo.description);
				}
				if(type=="5"){
					$('#ebs_info').show();
					var standardId = caseInfo.standardId;
					if(standardId != null && ""!=standardId){
						$('#ebs_item').show();
						$('#ebs_itemName').val(caseInfo.itemName);
					}else{
						$('#ebs_item').hide();
					}
					if("7" == data.orderInfo.status){
						$('#ebs_diskSize').val(caseInfoEdit.diskSize);
						$('#ebs_diskSize').css("color","red");
					}else{
						$('#ebs_diskSize').val(caseInfo.diskSize);
						$('#ebs_diskSize').css("color","#777");
					}
					$('#ebs_ebsName').val(caseInfo.ebsName);
					if(caseInfo.resourceType == '1'){
						$('#ebs_resourceType').val('云硬盘');
					}else{
						$('#ebs_resourceType').val('物理硬盘');
					}
				}
				if(type=="12"){
					$('#pip_info').show();
					$('#pip_ipsegmentDesc').val(caseInfo.ipsegmentDesc);
					$('#pip_startToEndIp').val(caseInfo.startToEndIp);
					$('#pip_ipTotal').val(caseInfo.ipTotal);
				}
				if(type=="13"){
					$('#vlan_info').show();
					$('#vlan_vlanName').val(caseInfo.vlanName);
				}
				
				if(type=="7"){
					$('#publicIp_info').show();
					$('#publicIp').val(caseInfo.publicIp);
					$('#hostId').val(caseInfo.hostId);
					$('#description').val(caseInfo.description);
				}
				if(type=="14"){
					$('#loadbalance_info').show();
					$('#lbname').val(caseInfo.lbname);
					$('#lbport').val(caseInfo.lbport);vlanId
					$('#lbip').val(caseInfo.lbip);
					$('#protocal').val(caseInfo.protocal);LBType					
					$('#vlanId').val(caseInfo.vlanId);
					$('#LBType').val(caseInfo.LBType);
				}
				if(type=="15"){
					$('#fileStore_info').show();fsTemplateId
					$('#fsName').val(caseInfo.fsName);					
					$('#fsTemplateId').val(caseInfo.fsTemplateId);
					$('#fsQuotaSize').val(caseInfo.fsQuotaSize);
					$('#fsUrl').val(caseInfo.fsUrl);
					$('#description').val(caseInfo.description);
				}
				if(type=="16"){
					$('#vfireWall_info').show();
					$('#fwName').val(caseInfo.fwName);
					$('#description').val(caseInfo.description);
				}
				
				var lengthTime = data.orderInfo.lengthTime;
				if(lengthTime == null || lengthTime == ""){
					$('#lengthTimeDiv').hide();
					//$('#expireTimeDiv').hide();
				}else if(lengthTime == "0"){
					$('#lengthTimeDiv').show();
					$('#lengthTime').val("无限期");
					$('#expireTimeDiv').hide();
				}else{
					$('#lengthTimeDiv').show();
					$('#lengthTime').val(data.orderInfo.lengthTime + getDate(data.orderInfo.lengthUnit));
					if(data.orderInfo.expireTime != null && data.orderInfo.expireTime!=""){
						$('#expireTime').val(data.orderInfo.expireTime);
						$('#expireTimeDiv').show();
					}else{
						$('#expireTimeDiv').hide();
					}
				}
				if(data.orderInfo.effectiveTime != null && data.orderInfo.effectiveTime!=""){
					$('#effectiveTime').val(data.orderInfo.effectiveTime);
					$('#effectiveTimeDiv').show();
					$('#effectiveTimeNoneDiv').hide();
				}else{
					$('#effectiveTimeDiv').hide();
					$('#effectiveTimeNoneDiv').show();
				}
				
				$('#creater').val(data.orderInfo.createUser);
				$('#createTime').val(data.orderInfo.createTime);
				$('#updateUser').val(data.orderInfo.updateUser);
				$('#updateTime').val(data.orderInfo.updateTime);
				$('#pmName').val(data.pmName);
				var cancelVal;
				if(isDetail){
					title += '订单详细信息';
					cancelVal = '关闭';
					$('#aduitInfo').show();
					$('#aduits').show();
					$('#section').hide();
					$('#audit_div').hide();
					if(!jQuery.isEmptyObject(data.orderAuditInfo) && "7" != data.orderInfo.status){
						$('#auditTime').val(data.orderAuditInfo.auditTime);
						$('#auditUser').val(data.orderAuditInfo.auditUser);
						$('#auditInfo').val(data.orderAuditInfo.auditInfo);
						$('#aduitInfo').show();
						$('#aduits').show();
					}else{
						$('#aduitInfo').hide();
						$('#aduits').hide();
					}
				}else{
					title += '订单审批';
					cancelVal = '取消';
					$('#aduitInfo').hide();
					$('#aduits').hide();
					$('#section').show();
					$('#audit_div').show();
				}
				$.dialog({
					title: title,
					content: document.getElementById('order_detail'),
					init:function(){ },
					button:[{
						id:'pass',
			            name: '通过',
			            callback: function () {
			            	var da_val = {
								orderId : orderId,
								auditInfo :$('#auditInfo2').val(),
								caseType :type,
								status:status,
								pass : "1"
							};
							$.ajax( {
								type : "POST",
								url : 'orderAuditAll.action',
								data : da_val,
								dataType : "JSON",
								cache : false,
								success : function(data) {
									if(data.resultPath=="error"){
										$.compMsg( {
											type : 'error',
											msg : data.message
										});
										return;
									}
									backList("订单审批成功!","");
								}
							});
						},
						focus: true
					},{
						id:'notpass',
			            name: '不通过',
			            callback: function () {
			            	var da_val = {
								orderId : orderId,
								auditInfo :$('#auditInfo2').val(),
								caseType :type,
								status:status,
								pass : "2"
							};
							$.ajax( {
								type : "POST",
								url : 'orderAuditAll.action',
								data : da_val,
								dataType : "JSON",
								cache : false,
								success : function(data) {
									if(data.resultPath=="error"){
										$.compMsg( {
											type : 'error',
											msg : data.message
										});
										return;
									}
									backList("订单审批成功!","");
								}
							});
			            	
						}
					}],
					cancelVal: cancelVal,
					cancel:true,
					lock: true
				});
				if(isDetail){
					$('.aui_buttons button').eq(0).hide();
					$('.aui_buttons button').eq(1).hide();
				}else{
					$('.aui_buttons button').eq(0).show();
					$('.aui_buttons button').eq(1).show();
				}
			}
		}
	});
}