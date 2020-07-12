$( function() {
	//菜单显示当前，开发时删除
	$(".left-menu li:contains('订单管理')").addClass("selected").next().show();
	$("#head-menu li a:contains('订单管理')").parent().addClass("head-btn-sel");
	// 自定义form样式
	$(":text,select,:checkbox,textarea").uniform();
	$('#select').val("select");
	$('#pool').val("select");
	$.uniform.update();
	$("#selectSubAll").click(function (){
		checkSubAll(this);
		checkAll(this);
	});
	fomateDate();
	cutStringFun();
});

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
table += '<col style="width: 9%;" />';
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
table += '<th style="width: 9%;">操作</th>';
table += '</tr>';

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

//显示隐藏规格详情
function isShow(obj){
	if($("#"+obj).is(":hidden")){
		$("#"+obj).show();
		$.uniform.update();
		if(obj == 'capacity'){
			getGauge();
			$.uniform.update();
		}
	}else{
		$("#"+obj).hide();
		$.uniform.update();
	}
}

function getGauge(){
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
	if(status=='6'){
		return "已回收";
	}
	if(status=='7'){
		return "修改待审";
	}
	if(status=='8'){
		return "删除待审";
	}
}

function getOrderType(type){
	if(type=="0"){
		return "虚拟机";
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
	}if(type=="16"){
		return "虚拟防火墙";
	}if(type=="17"){
		return "VLAN 3期SDN";
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

function will(obj){
	$(".title-btn li").removeClass("select");
	$(obj).parents('li').addClass("select");
	queryOrder('');
}

//翻页调用js
function getPageData(url) {
	queryOrder(url);
}

//查询列表
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
		da_val = {
			caseType: caseType,
			status: status,
			startTime: startTime,
			endTime: endTime,
			parentId:parentId,
			orderId:orderId,
			resPoolId:resPoolId,
			appName:appName,
			updateUser:updateUser
		};
		url = 'orderQueryJson.action';
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
					if(data.orderInfos.list.length==0){
//						$.compMsg( {
//							type : 'info',
//							msg : "暂无符合条件的订单数据！"
//						});
						$("#ordertable").empty();
						$("#ordertable").append(table);
						$(".pageBar").html(data.orderInfos.page);
						$(":checkbox").uniform();
//						$("select[class!='select-max']").uniform();
						$.uniform.update();
						return;
					}
					if (!jQuery.isEmptyObject(data.orderInfos)) {
						var str = table;
						var list = data.orderInfos.list;
						var page = data.orderInfos.page;
						for ( var i = 0; i < list.length; i++) {
							var entity = list[i];
							str += '<tr class="iterator">';
							str += '<td><input type="checkbox" name="selectSub" value="'+entity.orderId+'" /></td>';
							str += '<td>' + entity.parentId + '</td>';
							if(entity.status=='0' || entity.status=='7'){
								str += '<td><input type="checkbox" name="select" value="'+entity.orderId+'" vtype="'+entity.parentId+'" /></td>';
							}else{
								str += '<td> </td>';
							}
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
								str += '<a href="#" onclick="orderInfoDetail(\''+entity.parentId+'\',\''+entity.caseType+'\',\''+entity.orderId+'\',\''+entity.status+'\',false);return false;">审批</a>';
							} else if(entity.status=='5'){
								str += '<a href="#" onclick="orderDel(\''+entity.caseType+'\',\''+entity.orderId+'\');return false;">回收</a>';
							}
							str += '<a href="javascript:void(0);" onclick="orderInfoDetail(\''+entity.parentId+'\',\''+entity.caseType+'\',\''+entity.orderId+'\',\''+entity.status+'\',true);return false;">详情</a>';
							str += '</td>';
							str += '</tr>';
						}
						$("#ordertable").empty();
						$("#ordertable").append(str);
				}
				$(".pageBar").html(page);
				fomateDate();
				cutStringFun();
				$(":checkbox").uniform();
//				$("select[class!='select-max']").uniform();
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

function backList(msg, errMsg) {
	$.backListMsg( {
		msg : msg,
		errMsg : errMsg,
		url : 'orderQuery.action'
	});
}

//以下三项是分区资源容量信息变量
//VCPU
var vcpu_resUsed = '';
var vcpu_resTotal = '';

//内存使用情况
var memory_resUsed = '';
var memory_resTotal = '';

//磁盘使用情况
var disk_resUsed = '';
var disk_resTotal = '';
function orderInfoDetail(parentId,type,orderId,status,isDetail){

	var da_val = {
		orderId : orderId,
		parentId:parentId,
		caseType:type,
		status:status
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
			
			//VCPU
			vcpu_resUsed = Number(data.vcpu.resUsed);
			vcpu_resTotal = Number(data.vcpu.resTotal);

			//内存使用情况
			memory_resUsed = Number(data.memory.resUsed);
			memory_resTotal = Number(data.memory.resTotal);

			//磁盘使用情况
			disk_resUsed = Number(data.disk.resUsed);
			disk_resTotal = Number(data.disk.resTotal);
			
			if(type=="0"){
				caseInfo = data.vmInfo;
				caseInfoEdit = data.vmInfoEdit;
				title = "虚拟机";
				$("#ispoolpart").css("display","block");
				$("#capacity_a").css("display","block");
			}
			if(type=="1"){
				caseInfo = data.pmInfo;
				caseInfoEdit = data.pmInfoEdit;
				title = "物理机";
				$("#ispoolpart").css("display","block");
				$("#capacity_a").css("display","block");
			}
			if(type=="4"){
				caseInfo = data.bkInfo;
				title = "虚拟机备份任务";
				$("#ispoolpart").css("display","block");
				$("#capacity_a").css("display","block");
			}
			if(type=="5"){
				caseInfo = data.ebsInfo;
				caseInfoEdit = data.ebsInfoEdit;
				title = "云硬盘";
				$("#ispoolpart").css("display","block");
				$("#capacity_a").css("display","block");
			}
			if(type=="7"){
				caseInfo = data.pIpInfo;
				title = "公网IP";
				$("#ispoolpart").css("display","block");
			}
			if(type=="12"){
				caseInfo = data.pipInfo;
				title = "IP段";
				$("#ispoolpart").css("display","block");
				$("#capacity_a").css("display","block");
			}
			if(type=="13"){
				caseInfo = data.vlanInfo;
				title = "VLAN";
				$("#ispoolpart").css("display","none"); 
				$("#capacity").css("display","none"); 
				$("#capacity_a").css("display","none");
			}
			$("#info_a").css("display","block"); 
			$("#lb_info").css("display","none"); 
			if(type=="14"){
				caseInfo = data.lbInfo;
				title = "负载均衡";
			}
			if(type=="15"){
				title = "分布式文件存储";
				caseInfo = data.fsInfo;
			}
			if(type=="16"){
				title = "虚拟防火墙";
				caseInfo = data.vfwInfo;
			}
			if(type=="17"){
				title = "VLAN 3期SDN";
				caseInfo = data.vlanSdnInfo;
				caseInfoEdit = data.vlanSdnInfoEdit;
			}
			$('#info').hide();
			$('#capacity_a').hide();
			$('#vm_info').hide();
			$('#pm_info').hide();
			$('#bk_info').hide();
			$('#ebs_info').hide();
			$('#pip_info').hide();
			$('#vlan_info').hide();
			$("#lb_info").hide(); 
			$("#fs_info").hide(); 
			$("#fw_info").hide(); 
			$("#ip_info").hide(); 
			$("#vlanSdn_info").hide(); 
			
			
			if (!jQuery.isEmptyObject(data.orderInfo)&&!jQuery.isEmptyObject(caseInfo)) {
				$('#parentId').val(data.orderInfo.parentId);
				$('#orderId').val(data.orderInfo.orderId);
				$('#resPoolName').val(data.orderInfo.resPoolName);
				$('#resPoolPartName').val(data.orderInfo.resPoolPartName);
				$('#appName_detail').val(data.orderInfo.appName);
				$('#status').val(getStatus(data.orderInfo.status));
				$('#caseType').val(getOrderType(data.orderInfo.caseType));
				if(type=="0"){
					$('#capacity_a').show();
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
						$('#vm_ramSize').val(caseInfoEdit.ramSize+"G");
						$('#vm_ramSize').css("color","red");
					}else{
						$('#vm_ramSize').val(caseInfo.ramSize+"G");
						$('#vm_ramSize').css("color","#777");
					}
					if("7" == data.orderInfo.status && caseInfoEdit != null && caseInfoEdit.discSize != null && caseInfoEdit.discSize!=""){
						alert(caseInfoEdit.discSize);
						$('#vm_discSize').val(caseInfoEdit.discSize  +"T");
						$('#vm_discSize').css("color","red");
					}else{
						$('#vm_discSize').val(caseInfo.discSize  + "T");
						$('#vm_discSize').css("color","#777");
					}
					$('#vm_isoName').val(caseInfo.isoName);
					$('#vm_description').val(caseInfo.description);
					$('.vm_netListTr').remove();
					$.each(caseInfo.netList, function(index) {
						var tr = $('<tr class="vm_netListTr">');
						tr.appendTo('#vm_netListTbody')
						.append('<td nowrap="nowrap">' + this.eth + '</td> ')
						.append('<td nowrap="nowrap">' + this.vlanName+ '</td>')
						.append('<td nowrap="nowrap">' + this.startIp + '~' + this.endIp + '</td>')
						.append('<td nowrap="nowrap">' + this.ip + '</td> ')
						.append('<td nowrap="nowrap">' + this.gateway+ '</td>');
						if(this.flag != 'old' && "7" == data.orderInfo.status){
							tr.css("color","red");
						}
					});
					if(isDetail){
						$('#pmList_div').hide();
						$('#pmName').hide();
						$('#pmName').val(caseInfo.pmName);
						$('#uniform-pmNameSelect').remove();
						$('#pmNameSelect').remove();
					}else{
						if(status==='0'){
							$('#pmList_div').hide();
							$('#pmName').hide();
							$('#uniform-pmNameSelect').remove();
							$('#pmNameSelect').remove();
							$('#pmList_div .content:first').append('<select id="pmNameSelect"></select>');
							$('#pmNameSelect').show();
							$('#pmNameSelect').empty();
							$('#pmNameSelect').append('<option value="">-请选择-</option>');
							if(data.pms.length>0){
								var pms=data.pms;
								for(var i=0;i<pms.length;i++){
									$('#pmNameSelect').append('<option value='+pms[i].pmId+'>'+pms[i].pmName+'</option>');
								}
							}
							$('#pmNameSelect').uniform();
						}else{
							$('#pmList_div').hide();
						}
					}
				}
				if(type=="1"){
					$('#pmList_div').hide();
					$('#capacity_a').show();
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
					$('#pm_discSize').val(caseInfo.discSize / 1024+"T");
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
					$('#pmList_div').hide();
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
					$('#pmList_div').hide();
					$('#capacity_a').show();
					$('#ebs_info').show();
					var standardId = caseInfo.standardId;
					if(standardId != null && ""!=standardId){
						$('#ebs_item').show();
						$('#ebs_itemName').val(caseInfo.itemName);
					}else{
						$('#ebs_item').hide();
					}
					if("7" == data.orderInfo.status){
						$('#ebs_diskSize').val(caseInfoEdit.diskSize / 1024 + 'T');
						$('#ebs_diskSize').css("color","red");
					}else{
						$('#ebs_diskSize').val(caseInfo.diskSize / 1024 + 'T');
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
					$('#pmList_div').hide();
					$('#pip_info').show();
					$('#pip_ipsegmentDesc').val(caseInfo.ipsegmentDesc);
					$('#pip_startToEndIp').val(caseInfo.startToEndIp);
					$('#pip_ipTotal').val(caseInfo.ipTotal);
				}
				if(type=="13"){
					$('#pmList_div').hide();
					$('#vlan_info').show();
					$('#vlan_vlanName').val(caseInfo.vlanName);
				}
				if(type=="14"){
					$('#lbName').val(caseInfo.lbname);
					if('1'==caseInfo.LBType){
						$('#LBType').val('单臂');
					}if('2'==caseInfo.LBType){
						$('#LBType').val('双臂');
					}
					if('3'==caseInfo.LBType){
						$('#LBType').val('三角');
					}
					if('0'==caseInfo.strategy){
						$('#Strategy').val('基于应用响应时间');
					}if('1'==caseInfo.strategy){
						$('#Strategy').val('原IP地址哈希（HASH）值');
					}
					if('2'==caseInfo.strategy){
						$('#Strategy').val('目的IP地址哈希（HASH）值');
					}
					if('3'==caseInfo.strategy){
						$('#Strategy').val('内容字串哈希值');
					}
					if('4'==caseInfo.strategy){
						$('#Strategy').val('Cookie名称哈希值');
					}
					if('5'==caseInfo.strategy){
						$('#Strategy').val('URL哈希值');
					}
					if('6'==caseInfo.strategy){
						$('#Strategy').val('服务器可用带宽');
					}
					if('7'==caseInfo.strategy){
						$('#Strategy').val('服务器应用连接数');
					}
					if('8'==caseInfo.strategy){
						$('#Strategy').val('服务器负荷');
					}
					if('9'==caseInfo.strategy){
						$('#Strategy').val('轮询');
					}
					$('#lbport').val(caseInfo.lbPort);
					$('#protocal').val(caseInfo.protocal);
					$('#connectNum').val(caseInfo.connectNum);
					$('#NewConnectNum').val(caseInfo.newConnectNum);
					$('#Throughput').val(caseInfo.throughput);
					$('#lbVlan').val(caseInfo.lbVlan);
					$('#lbIpSegment').val(caseInfo.lbIpSegment);
					$('#lbip').val(caseInfo.lbip);
					
					$('#lbVlanName').val(caseInfo.vlanName);
					$('#lbIpSegmentName').val(caseInfo.ipSegmentDesc);
					$('#lbIpAddress').val(caseInfo.lbip);
					
					$("#appId").val(caseInfo.appid);
					$("#resPoolName").val(caseInfo.respoolname);
					$('#lb_info').show();
				}
				
				if(type=="15"){
					$('#fs_info').show();
					$('#fsName').val(caseInfo.fsName);
					$('#fsQuotaSize').val(caseInfo.fsQuotaSize);
					//0：NFS（Lunix、windows操作系统适用）1：CIFS（windows操作系统适用）3：POSIX
					switch(caseInfo.fsShareType){
					case '0':
						$('#fsShareType').val('NFS');break;
					case '1':
						$('#fsShareType').val('CIFS');break;
					case '3':
						$('#fsShareType').val('POSIX');break;
							
					}
					$('#description').val(caseInfo.description);
				}
				if(type=="16"){
					$('#fw_info').show();
					$('#fwName').val(caseInfo.fwName);
					$('#description').val(caseInfo.description);
				}
				if(type=="17"){
					$('#vlanSdn_info').show();
					if("7" == data.orderInfo.status){
						if(caseInfoEdit != null && caseInfoEdit.vlanName != null && caseInfoEdit.vlanName!=""){
							$('#vlanSdn_vlanName').val(caseInfoEdit.vlanName);
							$('#vlanSdn_vlanName').css("color","red");
						}else{
							$('#vlanSdn_vlanName').val(caseInfo.vlanName);
							$('#vlanSdn_vlanName').css("color","#777");
						}
						if(caseInfoEdit != null && caseInfoEdit.startId != null && caseInfoEdit.startId!=""){
							$('#vlanSdn_startId').val(caseInfoEdit.startId);
							$('#vlanSdn_startId').css("color","red");
						}else{
							$('#vlanSdn_startId').val(caseInfo.startId);
							$('#vlanSdn_startId').css("color","#777");
						}
						if(caseInfoEdit != null && caseInfoEdit.endId != null && caseInfoEdit.endId!=""){
							$('#vlanSdn_endId').val(caseInfoEdit.endId);
							$('#vlanSdn_endId').css("color","red");
						}else{
							$('#vlanSdn_endId').val(caseInfo.endId);
							$('#vlanSdn_endId').css("color","#777");
						}
					}else{
						$('#vlanSdn_vlanName').val(caseInfo.vlanName);				
						$('#vlanSdn_startId').val(caseInfo.startId);					
						$('#vlanSdn_endId').val(caseInfo.endId);
					}
				}
				if(type=="7"){
					$('#ip_info').show();
					$('#publicIp').val(caseInfo.publicIp);
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
				$('#updater').val(data.orderInfo.updateUser);
				$('#updateTime').val(data.orderInfo.updateTime);
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
			            	$.compMsg( {
			        			type : 'loading',
			        			timeout : 3600000,
			        			msg : "订单审批中，请稍后..."
			        		});
			            	var pmId=$('#pmNameSelect').find('option:selected').val();
			            	var da_val = {
								orderId : orderId,
								parentId : parentId,
								auditInfo :$('#auditInfo2').val(),
								caseType :type,
								status:status,
								pass : "1",
/*								vlanSelect : $('#vlanSelect').val(),
								ipsegmentSelect : $('#ipsegmentSelect').val(),
								privateIpSelect : $('#privateIpSelect').val(),*/
								vlanSelect : $('#lbVlan').val(),
								ipsegmentSelect : $('#lbIpSegment').val(),
								privateIpSelect : $('#lbip').val(),
								pmId:pmId
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
			            	$.compMsg( {
			        			type : 'loading',
			        			timeout : 3600000,
			        			msg : "订单审批中，请稍后..."
			        		});
			            	var da_val = {
								orderId : orderId,
								parentId : parentId,
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
		},complete:function(){
			if(isDetail==false){
				vaultCreat();
			}
//			queryVlan();
		}
	});
}

var vlanDef;
var ipSegDef;
var ipDef;

//加载vlan
function queryVlan(){

	vlanDef = $("#lbVlan").val();
	ipSegDef = $("#lbIpSegment").val();
	ipDef = $("#lbip").val();
	var appId=$("#appId").val();
	$.ajax({
		type : "POST",
		url : 'vlanQueryJson.action?lbAppId='+appId,
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
			
/*			var vlanInit = $('#lbVlan').val();
			$(selectVal).append(
					'<option value="' + vlanInit + '">'
							+ vlanInit + '</option>');*/
			
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
								
/*								var ipSegmentInit = $('#lbIpSegment').val();
								$(selectVal).append(
										'<option value="' + ipSegmentInit + '">'
												+ ipSegmentInit + '</option>');*/
								
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
													
/*													var ipInit = $('#lbip').val();
													$(selectVal).append(
															'<option value="' + ipInit + '">'
																	+ ipInit + '</option>');*/
													
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
          		var auditInfo = $('#auditInfos').val();
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
          		var auditInfo = $('#auditInfos').val();
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

// 订单回收
function orderDel(type,orderId){
	var title = ""; 
	var content = "";
	var url;
	if(type=="0"){
		title = '回收订单及其虚拟机';
		content = '请您确认是否回收订单及其虚拟机？回收后将无法恢复！';
		url = 'vmDelAction.action';
	}else if(type=="1"){
		title = '回收订单及其物理机';
		content = '请您确认是否回收订单及其物理机？回收后将无法恢复！';
		url = 'pmDelAction.action';
	}else if(type=="5"){
		title = '回收订单及其硬盘';
		content = '请您确认是否回收订单及其硬盘？回收后将无法恢复！';
		url = 'ebsDelAction.action';
	}
	$.dialog( {
		title : title,
		content : content,
		ok : function() {
			$.compMsg( {
    			type : 'loading',
    			timeout : 3600000,
    			msg : "订单回收中，请稍后..."
    		});
			var da_val = {
				'orderId' : orderId
			};
			$.ajax( {
				url : url,
				type : 'POST',
				data : da_val,
				cache : false,
				dataType : 'json',
				success : function(data) {
					if (data.resultPath == '0') { // 已经被回收
						if (!(data.mes == null || data.mes == '')) {
							$.compMsg( {
								type : 'error',
								msg : data.mes
							});
						}
					}else{
						var name = data.name;
						var msg = name+"回收成功！";
						goBackForm("",msg);
					}
				}
			});
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}

//跳转到列表界面
function goBackForm(errMsg,msg){
	$.backListMsg({msg:msg,errMsg:errMsg,url:'orderQuery.action?status=5'});
}
