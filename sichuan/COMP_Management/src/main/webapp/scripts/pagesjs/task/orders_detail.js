$( function() {
	//菜单显示当前，开发时删除
	$(".left-menu li:contains('订单审批')").addClass("selected").next().show();
	$("#head-menu li a:contains('代办任务')").parent().addClass("head-btn-sel");
	// 自定义form样式
	$(":text,select,:checkbox,textarea").uniform();
	$('#select').val("select");
	$.uniform.update();
	$("#selectSubAll").click(function (){
		checkSubAll(this);
		checkAll(this);
	});
	$('#status').text(getStatus($('#status').text()));
	if($('#caseType').text()=="14"){
//		queryVlan();
	}
	$('#caseType').text(getOrderType($('#caseType').text()));
	if($('#lengthUnit') != null ){
		$('#lengthUnit').text(getDate($('#lengthUnit').text()));
	}
	$("#goBack").click(function (){
		backList('','');
	});
	$("#cancel").click(function (){
		backList('','');
	});
	vaultCreat();
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
			maxTextLength = 6*2;
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
/**
 * 格式化数据展示方式
 * 将相同的父订单号合并
 */
function fomateDate(){
	var ls=null;
	var n =1;
	var obj=null;
	var flage = false;
	$("#ordertable tr").each(function(i){
		if(i!=0){
			var parentId = $(this).find('td').eq(1).text();
			var subText = $(this).find('td').eq(2).html();
			subText = $.trim(subText);
			if(parentId == ls){
				n=n+1;
				$(this).find('td').eq(0).remove();
				$(this).find('td').eq(0).remove();
			}else{
				if(obj!=undefined && obj!=null){
					if(flage){
						var checbokSub = '<input type="checkbox" name="selectSub" value="'+ls+'" onclick="checkSub(this);" />';
						$(obj).find('td').eq(0).html(checbokSub);
					}else{
						$(obj).find('td').eq(0).html("");
					}
					$(obj).find('td').eq(0).attr("rowspan",n);
					$(obj).find('td').eq(1).attr("rowspan",n);
					flage = false;
				}
				ls = parentId;
				obj = this;
				n=1;
				flage = flage?flage:(subText==''?false:true);
			}
		}
	});
	if(obj!=undefined&&obj!=null){
		if(flage){
			var checbokSub = '<input type="checkbox" name="selectSub" value="'+ls+'" onclick="checkSub(this);" />';
			$(obj).find('td').eq(0).html(checbokSub);
		}else{
			$(obj).find('td').eq(0).html("");
		}
		$(obj).find('td').eq(0).attr("rowspan",n);
		$(obj).find('td').eq(1).attr("rowspan",n);
	}
	$(":checkbox[name='selectSub']").uniform();
	$.uniform.update();
}
//返回列表页面
function backList(msg,errMsg){
	$.backListMsg({msg:msg,errMsg:errMsg,url:'ordersAuditList.action'});
}

//全选
function checkAll(obj){
	if($(obj).is(':checked')){
		$("[name='select']").each(function (){
			$(this).attr('checked',true);
		});
	}else{
		$("[name='select']").each(function (){
			$(this).attr('checked',false);
		});
	}
	$.uniform.update();
}

//选中父订单单选按钮，其子订单按钮同时被选中
function checkSub(obj){
	var parentId = $(obj).val();
	if($(obj).is(':checked')){
		$("[name='select']").each(function (){
			if($(this).attr('vtype')==parentId){
				$(this).attr('checked',true);
			}
		});
	}else{
		$("[name='select']").each(function (){
			if($(this).attr('vtype')==parentId){
				$(this).attr('checked',false);
			}
		});
	}
	$.uniform.update();
}
//全选 父订单 单选按钮
function checkSubAll(obj){
	if($(obj).is(':checked')){
		$("[name='selectSub']").each(function (){
			$(this).attr('checked',true);
		});
	}else{
		$("[name='selectSub']").each(function (){
			$(this).attr('checked',false);
		});
	}
	$.uniform.update();
}

var table ='';
table += '<col style="width: 2%;" />'; 
table += '<col style="width: 20%;" />';
table += '<col style="width: 2%;" />';
table += '<col style="width: 19%;" />';
table += '<col style="width: 8%;" />';
table += '<col style="width: 5%;" />';
table += '<col style="width: 8%;" />';
table += '<col style="width: 8%;" />';
table += '<col style="width: 7%;" />';
table += '<col style="width: 11%;" />';
table += '<col style="width: 5%;" />';
table += '<tr>';
table += '<th class="nl" style="width: 2%;"><input type="checkbox" name="selectSubAll" id="selectSubAll"/></th>';
table += '<th style="width: 20%;">订单号</th>';
table += '<th style="width: 2%;"></th>';
table += '<th style="width: 19%;">子订单号</th>';
table += '<th style="width: 8%;">资源类型</th>';
table += '<th style="width: 5%;">状态</th>';
table += '<th style="width: 8%;">资源池名称</th>';
table += '<th style="width: 8%;">业务组名称</th>';
table += '<th style="width: 7%;">变更人</th>';
table += '<th style="width: 11%;">变更时间</th>';
table += '<th style="width: 5%;">操作</th>';
table += '</tr>';
function getStatus(status){
	if(status=="0"){
		return "待审批";
	}
	if(status=="1"){
		return "已通过";
	}
	if(status=="2"){
		return "未通过";
	}
	if(status=="3"){
		return "已生效";
	}
	if(status=="4"){
		return "已取消";
	}
	if(status=="5"){
		return "已过期";
	}
	if(status=="6"){
		return "已回收";
	}
	if(status=="7"){
		return "修改待审";
	}
	if(status=="8"){
		return "删除待审";
	}
}
function getOrderType(type){
	if(type=="0"){
		$("#ispoolpart").css("display","block");
		$("#bugu").css("display","block");
		return "虚拟机";
	}
	if(type=="1"){
		$("#ispoolpart").css("display","block");
		$("#bugu").css("display","block");
		return "物理机";
	}
	if(type=="2"){
		$("#ispoolpart").css("display","none");
		$("#bugu").css("display","none");
		return "小型机";
	}
	if(type=="3"){
		$("#ispoolpart").css("display","none");
		$("#bugu").css("display","none");
		return "小型机分区";
	}
	if(type=="4"){
		$("#ispoolpart").css("display","none");
		$("#bugu").css("display","none");
		return "虚拟机备份任务";
	}
	if(type=="5"){
		$("#ispoolpart").css("display","block");
		$("#bugu").css("display","block");
		return "云硬盘";
	}
	if(type=="6"){
		$("#ispoolpart").css("display","none");
		$("#bugu").css("display","none");
		return "云储存";
	}
	if(type=="7"){
		$("#ispoolpart").css("display","none");
		$("#bugu").css("display","none");
		return "公网IP";
	}
	if(type=="8"){
		$("#ispoolpart").css("display","none");
		$("#bugu").css("display","none");
		return "带宽";
	}
	if(type=="9"){
		$("#ispoolpart").css("display","none");
		$("#bugu").css("display","none");
		return "安全组";
	}
	if(type=="10"){
		$("#ispoolpart").css("display","none");
		$("#bugu").css("display","none");
		return "虚拟机快照";
	}
	if(type=="11"){
		$("#ispoolpart").css("display","none");
		$("#bugu").css("display","none");
		return "虚拟机克隆";
	}
	if(type=="12"){
		$("#ispoolpart").css("display","none");
		$("#bugu").css("display","none");
		return "IP段";
	}
	if(type=="13"){
		$("#ispoolpart").css("display","none"); 
		$("#bugu").css("display","none");
		$("#isShowCapacity").css("display","none"); 
		$("#capacity").css("display","none"); 
		return "VLAN";
	}
	if(type=="14"){
		$("#ispoolpart").css("display","block");
		$("#bugu").css("display","none");
		$("#isShowCapacity").css("display","none"); 
		$("#capacity").css("display","none"); 
		$("#lbInfo").css("display","block"); 
		return "负载均衡";
	}
	if(type=="15"){
		$("#ispoolpart").css("display","none");
		$("#bugu").css("display","none");
		return "分布式文件存储";
	}
	if(type=="16"){
		$("#ispoolpart").css("display","none");
		$("#bugu").css("display","none");
		return "虚拟防火墙";
	}
	if(type=="17"){
		$("#ispoolpart").css("display","none");
		$("#bugu").css("display","none");
		return "VLAN 3期SDN";
	}
}

var vlanDef = $("#lbVlan").val();
var ipSegDef = $("#lbIpSegment").val();
var ipDef = $("#lbIp").val();

//加载vlan
function queryVlan(){
	
	var lbAppId=$("#appId").val();
	$.ajax({
		type : "POST",
		url : 'vlanQueryJson.action?lbAppId='+lbAppId,
		dataType : "JSON",
		cache : false,
		success : function(data) {
			
			//加载vlan默认值
			
			if (!jQuery.isEmptyObject(data.fieldErrors)) {
				// TODO
			}
			if (data.resultRoute == "error") {
				window.location.href = 'exceptionIntercepor.action';
				return;
			}
			if (data.resultRoute == "failure") {
				alert("展示资源池信息异常，将跳转到浏览页面");
				window.location.href = 'taskOverviewAction.action?t=1';
				return;
			}
			var selectVal = "#vlanSelect";
			$(selectVal).empty();
			for (var i = 0; i < data.vlanList.length; i++) {
				if (i == 0) {
					$(selectVal).append('<option value="notSelect">--请选择--</option>');
				}
				//若lb 表中的虚拟ip存在，则显示默认vlan、ip段、ip
				if(vlanDef.length!=0){
					if(vlanDef==data.vlanList[i].vlanId){
						$(selectVal).append(
								'<option selected value="' + data.vlanList[i].vlanId + '">'
										+ data.vlanList[i].vlanName + '</option>');
					}else{
						$(selectVal).append(
								'<option value="' + data.vlanList[i].vlanId + '">'
										+ data.vlanList[i].vlanName + '</option>');
					}
				}else{
						$(selectVal).append(
							'<option value="' + data.vlanList[i].vlanId + '">'
									+ data.vlanList[i].vlanName + '</option>');
					}
			}
			$.uniform.update();
		},complete:function(){
			
			//加载ip段默认值
			
			var selectVal ="#ipsegmentSelect";
			var vlanId = $("#vlanSelect").val();
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
									window.location.href = 'taskOverviewAction.action?t=1';
									return;
								}

								// var selectVal= "#ipsegmentSelect"+rols;
								$(selectVal).empty();
								for (var i = 0; i < data.ipSegmentList.length; i++) {
									if (i == 0) {
										$(selectVal).append(
												'<option value="notSelect">--请选择--</option>');
									}
									//若lb 表中的虚拟ip存在，则显示默认vlan、ip段、ip
									if(ipSegDef.length!=0){
										if(ipSegDef==data.ipSegmentList[i].ipSegmentId){
									$(selectVal).append(
											'<option selected value="'
													+ data.ipSegmentList[i].ipSegmentId
													+ '">'
													+ data.ipSegmentList[i].ipSegment
													+ '</option>');
											}else{
												$(selectVal).append(
														'<option value="'
																+ data.ipSegmentList[i].ipSegmentId
																+ '">'
																+ data.ipSegmentList[i].ipSegment
																+ '</option>');
											}
										}else{
											$(selectVal).append(
													'<option value="'
															+ data.ipSegmentList[i].ipSegmentId
															+ '">'
															+ data.ipSegmentList[i].ipSegment
															+ '</option>');
										}
									}
									$.uniform.update();
								},
							complete:function(){
								
								//加载ip默认值
								
								var selectVal = "#privateIpSelect";
								var vlanselectVal = "#vlanSelect";
								var vlan = $(vlanselectVal).val();
								var ipSegmentId = $("#ipsegmentSelect").val();
								if(ipSegmentId=="notSelect"){
									$(selectVal).append(
									'<option value="notSelect">--请选择--</option>');
								}else{
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
														window.location.href = 'taskOverviewAction.action?t=1';
														return;
													}
													// var selectVal="#privateIpSelect"+rols;
													$(selectVal).empty();
													for (var i = 0; i < data.ipInfos.length; i++) {
														if (i == 0) {
															$(selectVal).append(
																'<option value="notSelect">--请选择--</option>');
														}
														if(ipDef.length!=0){
																if(ipDef==data.ipInfos[i].ip){
														$(selectVal).append(
																'<option selected value="' + data.ipInfos[i].ip
																		+ '">' + data.ipInfos[i].ip
																		+ '</option>');
																}else{
																	$(selectVal).append(
																			'<option value="' + data.ipInfos[i].ip
																					+ '">' + data.ipInfos[i].ip
																					+ '</option>');
																}
															}
														else{
															$(selectVal).append(
																	'<option value="' + data.ipInfos[i].ip
																			+ '">' + data.ipInfos[i].ip
																			+ '</option>');
														}
													}
													$.uniform.update();
												}
											});
									}
								}
							}
						});
			}
		}
	});
}

//改变vlan，ajax请求IP段
function changeVlan(vlan) {
	var selectVal ="#ipsegmentSelect";
	var vlanId = vlan.value;
	if(vlanId=="notSelect"){
		$(selectVal).empty();
		$("#privateIpSelect").empty();
		$(selectVal).append(
		'<option value="notSelect">--请选择--</option>');
		$("#privateIpSelect").append(
		'<option value="notSelect">--请选择--</option>');
		$.uniform.update();
	}else{
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
							window.location.href = 'taskOverviewAction.action?t=1';
							return;
						}

						// var selectVal= "#ipsegmentSelect"+rols;
						$(selectVal).empty();
						for (var i = 0; i < data.ipSegmentList.length; i++) {
							if (i == 0) {
								$(selectVal).append(
										'<option value="notSelect">--请选择--</option>');
							}
							$(selectVal).append(
									'<option value="'
											+ data.ipSegmentList[i].ipSegmentId
											+ '">'
											+ data.ipSegmentList[i].ipSegment
											+ '</option>');
						}
						$.uniform.update();
					}
				});
		}
	}
}

//加载ip
//改变vlan，ajax请求privateIp
function changeIpsegment(ipSegment) {

	var selectVal = "#privateIpSelect";
	var vlanselectVal = "#vlanSelect";
	var vlan = $(vlanselectVal).val();
	var ipSegmentId = ipSegment.value;
	if(ipSegmentId=="notSelect"){
		$("#privateIpSelect").empty();
		$("#privateIpSelect").append(
		'<option value="notSelect">--请选择--</option>');
		$.uniform.update();
	}else{
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
							window.location.href = 'taskOverviewAction.action?t=1';
							return;
						}
						// var selectVal="#privateIpSelect"+rols;
						$(selectVal).empty();
						for (var i = 0; i < data.ipInfos.length; i++) {
							if (i == 0) {
								$(selectVal).append(
									'<option value="notSelect">--请选择--</option>');
							}
							$(selectVal).append(
									'<option value="' + data.ipInfos[i].ip
											+ '">' + data.ipInfos[i].ip
											+ '</option>');
						}
						$.uniform.update();

					}
				});
	}
	}
}

function will(obj){
	$(".title-btn li").removeClass("select");
	$(obj).parents('li').addClass("select");
	queryOrder();
}

function getPageData(url) {
	queryOrder(url);
}

function queryOrder(url) {
	var status = $(".title-btn .select a").attr("value");
	var caseType = "";
	if ($('#select').val() != "select") {
		caseType = $('#select :selected').val();
	}
	var startTime = "";
	if ($('#startTime').val() != "") {
		startTime = _dateToStr($('#startTime').val());
	}
	var endTime = "";
	if ($('#endTime').val() != "") {
		endTime = _dateToStr($('#endTime').val());
	}
	var parentId = "";
	if ($('#queryParentId').val() != "") {
		parentId = $('#queryParentId').val();
	}
	var orderId = "";
	if ($('#queryOrderId').val() != "") {
		orderId = $('#queryOrderId').val();
	}
	var resPoolId = "";
	if ($('#pool').val() != "select") {
		resPoolId = $('#pool :selected').val();
	}
	var appName = "";
	if ($('#appName').val() != "") {
		appName = $('#appName').val();
	}
	var updateUser = "";
	if ($('#updateUser').val() != "") {
		updateUser = $('#updateUser').val();
	}
	var da_val;
	if (url == '' || url == undefined) {
		url = 'orderQueryJson.action';
		da_val = {
			caseType : caseType,
			status : status,
			startTime : startTime,
			endTime : endTime,
			parentId:parentId,
			orderId:orderId,
			resPoolId:resPoolId,
			appName:appName,
			updateUser:updateUser
		};
	}
	
	$.ajax( {
				type : "POST",
				url : url,
				data : da_val,
				dataType : "JSON",
				cache : false,
				success : function(data) {
					if (!jQuery.isEmptyObject(data.fieldErrors)) {
						if (!jQuery.isEmptyObject(data.fieldErrors.startTime)) {
							$.compMsg( {
								type : 'error',
								msg : data.fieldErrors.startTime
							});
							return;
						}
						if (!jQuery.isEmptyObject(data.fieldErrors.endTime)) {
							$.compMsg( {
								type : 'error',
								msg : data.fieldErrors.endTime
							});
							return;
						}
					}
					if(data.resultPath=="error"){
						window.location.href = 'exceptionIntercepor.action';
					}
					if(data.orderInfos.length==0){
//						$.compMsg( {
//							type : 'info',
//							msg : "暂无符合条件的订单数据！"
//						});
						$("#ordertable").empty();
						$("#ordertable").append(table);
						$(":checkbox").uniform();
						$("select[class!='select-max']").uniform();
						$.uniform.update();
						return;
					}
					if (!jQuery.isEmptyObject(data.orderInfos)) {
						var str = table;
						var pageBar = data.orderInfos.page;
						var list = data.orderInfos.list
						for ( var i = 0; i < list.length; i++) {
							str += '<tr class="iterator">';
							var entity = list[i];
							str += '<td><input type="checkbox" name="selectSub" value="'+entity.orderId+'" /></td>';
							str += '<td>' + entity.parentId + '</td>';
							str += '<td><input type="checkbox" name="select" value="'+entity.orderId+'" vtype="'+entity.parentId+'" /></td>';
							str += '<td>' + entity.orderId + '</td>';
							str+='<td>'+getOrderType(entity.caseType)+'</td>';
							/*str += '<td>' + entity.allPrice + '</td>';*/
							str += '<td>' + getStatus(entity.status) + '</td>';
							str += '<td class="cut pool">' + entity.resPoolName + '</td>';
							str += '<td class="cut app">' + entity.appName + '</td>';
							str += '<td>' + entity.updateUser + '</td>';
							str += '<td>' + entity.updateTime + '</td>';
							str += '<td class="table-opt-block">';
							if(entity.status=='0' || entity.status=='7'){
								str += '<a href="#" onclick="orderAudit2(\''+entity.caseType+'\',\''+entity.orderId+'\',\''+entity.status+'\');return false;">审批</a>';
							}
							str += '</td>';
							str += '</tr>';
						}
						$("#ordertable").empty();
						$("#ordertable").append(str);
						$(".pageBar").html(pageBar);
				}
				fomateDate();
				cutStringFun();
				$(":checkbox").uniform();
				$("select[class!='select-max']").uniform();
				$.uniform.update();
				$("#selectSubAll").click(function (){
					checkSubAll(this);
					checkAll(this);
				});
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

function orderAudit2(type,orderId,status){
	 $("#caseTypeTodo").val(type);
     $("#orderIdTodo").val(orderId);
     $("#orderAduitDetail").submit();
}

/**
 * 高级查询
 */
function queryDiv(){
	$.dialog({
		title: '订单高级查询',
		content: document.getElementById('queryDiv'),
		init:function(){
		},
		button : [ {
			name : '查询',
			callback : function() {
				this.button( {
					name : '查询',
					disabled : true
				});
				queryOrder('');
				return true;
			},
			focus : true
		} ],
		cancelVal: '关闭',
		cancel:true,
		lock: true
	});
}

function audit(pass,type,status){
	var title ="审批通过";
	var content = "请您确认是否订单审批通过？";
	if(pass == 2){
		title ="审批不通过";
		content = "请您确认是否订单审批不通过？";
	}
	var pmId=$('#pmNameSelect').find('option:selected').val();
	$.dialog( {
		title : title,
		content : content,
		ok : function() {
			$.compMsg( {
    			type : 'loading',
    			timeout : 3600000,
    			msg : "订单审批中，请稍后..."
    		});
			var da_val = {
				orderId : $('#orderId').val(),
				parentId:$('#parentId').val(),
				auditInfo :$('#auditInfo').val(),
				caseType :type,
				status:status,
				pass : pass,
				pmId:pmId,
/*				vlanSelect : $('#vlanSelect').val(),
				ipsegmentSelect : $('#ipsegmentSelect').val(),
				privateIpSelect : $('#privateIpSelect').val()*/
				vlanSelect : $('#lbVlan').val(),
				ipsegmentSelect : $('#lbIpSegment').val(),
				privateIpSelect : $('#lbIp').val()
			};
			$.ajax( {
				url : 'orderAuditAll.action',
				type : 'POST',
				data : da_val,
				cache : false,
				dataType : 'json',
				success : function(data) {
					if(data.resultPath=="error"){
						$.compMsg( {
								type : 'error',
								msg : data.message
								});
							return;
					}else{
						$.compMsg( {
							type : 'success',
							msg : '订单审批成功!'
						});
					}
					window.location.href = 'ordersAuditList.action';
				}
			});
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}

//批量审批
function approvalMore(obj){
	var orderIds = "";
	$("input[name=select]:checked").each(function(){ 
	 	 orderIds = orderIds + $(this).val() + ",";
	});
	if(orderIds==""){
		$.compMsg( {
			type : 'warn',
			msg : "请选择要审批的订单！"
		});
		return;
	}
	$.dialog({
		title: '批量审批',
		content: document.getElementById('approvalMore'),
		button:[{
            name: '通过',
            callback: function () {
            		 $.compMsg( {
             			type : 'loading',
             			timeout : 3600000,
             			msg : "订单批量审批中，请稍后..."
             		});
            		var auditInfo = $('#auditInfo').val();
 					var da_val = {
									orderIds : orderIds,
									pass : "1",
									auditInfo :auditInfo
								};
								$.ajax( {
									type : "POST",
									url : 'orderBatchAidut.action',
									data : da_val,
									dataType : "JSON",
									cache : false,
									success : function(data) {
										if(data.resultPath=="error"){
											backList("",data.resultMsg);
										}else if(data.resultPath=="failure"){
											backList("",data.resultMsg);
										}else{
											backList(data.resultMsg,"");
										}
									}
								});  
							
			},
			focus: true
		},{
            name: '不通过',
            callback: function () {
            		 $.compMsg( {
             			type : 'loading',
             			timeout : 3600000,
             			msg : "订单批量审批中，请稍后..."
             		});
            		var auditInfo = $('#auditInfo').val();
            		var da_val = {
									orderIds : orderIds,
									pass : "2",
									auditInfo :auditInfo
								};
								$.ajax( {
									type : "POST",
									url : 'orderBatchAidut.action',
									data : da_val,
									dataType : "JSON",
									cache : false,
									success : function(data) {
										if(data.resultPath=="error"){
											backList("",data.resultMsg);
										}else if(data.resultPath=="failure"){
											backList("",data.resultMsg);
										}else{
											backList(data.resultMsg,"");
										}
									}
								});
								
			}
		}],
		cancelVal: '取消',
		cancel:true,
		lock: true
	});
}

//显示隐藏规格详情
function isShow(obj){
	if($("#"+obj).is(":hidden")){
		$("#"+obj).show();
		if(obj == 'capacity'){
			getGauge();
		}
	}else{
		$("#"+obj).hide();
	}
}

function getGauge(){
	if (typeof(vcpu_resTotal) == "undefined") { 
		vcpu_resTotal = 0;
	} 
	if (typeof(vcpu_resUsed) == "undefined") { 
		vcpu_resUsed = 0;
	} 
	if (typeof(memory_resTotal) == "undefined") { 
		memory_resTotal = 0;
	} 
	if (typeof(memory_resUsed) == "undefined") { 
		memory_resUsed = 0;
	} 
	if (typeof(disk_resTotal) == "undefined") { 
		disk_resTotal = 0;
	} 
	if (typeof(disk_resUsed) == "undefined") { 
		disk_resUsed = 0;
	} 
	var poolchart1 = echarts.init(document.getElementById('poolchart1'), 'macarons'); 
    
    var option1 = {
    		tooltip : {
				formatter : "{b} <br/>{a} : {c}"
			},
			series : [
					{
						name : '已使用(个)',//指标名称
						type : 'gauge',//图表类型
						radius: [0, '99%'],
						min : 0,//仪表盘上左侧最小值
						max : vcpu_resTotal,//仪表盘上右侧最大值
						splitNumber : 8,// 分割段数
						detail : {
							formatter : '{value}个',
							textStyle: {
						        color: 'auto',
						        fontSize : 25
						    }
						},// 仪表盘文本格式
						data : [ {
							value : vcpu_resUsed,
							name : 'VCPU'
						} ],//仪表盘显示值
						axisLine : { // 坐标轴线
							show : true, // 默认显示，属性show控制显示与否
//							lineStyle : { // 属性lineStyle控制线条样式
//								color : [ [ 0.7, '#5AB1EF' ],
//										[ 1, '#D87A80' ] ]
//							//超过70%显示红色
//							}
						}
					}]
    };
    
    poolchart1.setOption(option1);
    
	var poolchart2 = echarts.init(document.getElementById('poolchart2'), 'macarons'); 
    
    var option2 = {
    		tooltip : {
				formatter : "{b} <br/>{a} : {c}"
			},
			series : [
					{
						name : '已使用(GB)',//指标名称
						type : 'gauge',//图表类型
						radius: [0, '99%'],
						min : 0,//仪表盘上左侧最小值
						max : memory_resTotal,//仪表盘上右侧最大值
						splitNumber : 8,// 分割段数
						detail : {
							formatter : '{value}GB',
							textStyle: {
						        color: 'auto',
						        fontSize : 25
						    }
						},// 仪表盘文本格式
						data : [ {
							value : memory_resUsed,
							name : '内存'
						} ],//仪表盘显示值
						axisLine : { // 坐标轴线
							show : true, // 默认显示，属性show控制显示与否
//							lineStyle : { // 属性lineStyle控制线条样式
//								color : [ [ 0.7, '#5AB1EF' ],
//										[ 1, '#D87A80' ] ]
//							//超过70%显示红色
//							}
						}
					}]
    };
    
    poolchart2.setOption(option2);
	
	
	var poolchart3 = echarts.init(document.getElementById('poolchart3'), 'macarons'); 
    
    var option3 = {
    		tooltip : {
				formatter : "{b} <br/>{a} : {c}"
			},
			series : [
					{
						name : '已使用(GB)',//指标名称
						type : 'gauge',//图表类型
						radius: [0, '99%'],
						min : 0,//仪表盘上左侧最小值
						max : disk_resTotal,//仪表盘上右侧最大值
						splitNumber : 8,// 分割段数
						detail : {
							formatter : '{value}GB',
							textStyle: {
						        color: 'auto',
						        fontSize : 25
						    }
						},// 仪表盘文本格式
						data : [ {
							value : disk_resUsed,
							name : '存储'
						} ],//仪表盘显示值
						axisLine : { // 坐标轴线
							show : true, // 默认显示，属性show控制显示与否
//							lineStyle : { // 属性lineStyle控制线条样式
//								color : [ [ 0.7, '#5AB1EF' ],
//										[ 1, '#D87A80' ] ]
//							//超过70%显示红色
//							}
						}
					} ]
    };
    
    poolchart3.setOption(option3);
}
