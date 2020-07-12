$(function(){
    $(".left-menu li:contains('资费管理')").addClass("selected").next().show();
    if(chargesType=='0'){
    	 $(".left-menu dl a:contains('虚拟机')").parent().addClass("selected");
    }else if(chargesType=='1'){
    	$(".left-menu dl a:contains('云硬盘')").parent().addClass("selected");
    }
	$(":text,select").uniform();
    $('#select').val("select");
 
});
//查询功能
$("#search").click(function(){
	$("#chargesForm").submit();
});

//绑定添加时间
$("#add_charges_button").click(function(){
	var title = "";
	if(chargesType=='0'){
		title = "添加虚拟机资费信息";
	}else if(chargesType=='1'){
		title= "添加云主机资费信息";
		$("#cpuDiv").hide();
		$("#memoryDiv").hide();
	}
	showAddDialog(title);
});

//展示添加页面.
 function showAddDialog(title){
	 $.dialog({
			title: title,
			content: document.getElementById('vm_charges_add_dialog'),
			init:function (){
			},
			button:[
	        {
	            name: '创建',
	            callback: function () {
					this.button({
						name: '创建',
						disabled: true
	            	});
					if(validation("add")){
						this.button({
							name: '创建',
							disabled: false
		            	});
						return false;
					}
					var flage = true;
					flage = addChargesInfo(flage);
					this.button({
						name: '创建',
						disabled: false
	            	});
					return !flage;
	            },//callback结束
				focus: true
	        }],
			cancelVal: '取消',
			cancel:true,
			lock: true
		});
 }

 function validation(type){
	 var msg = "";
	 var cpu,memory,hourPrice,monthPrice;
	 var reguNum = new RegExp("^[1-9][0-9]*$"); 
	 var reguFloat = new RegExp("^[0-9]*[\.][0-9]{0,3}$");
	 if(chargesType=='0'){
		 if(type=="add"){
			 cpu = $("#cpu_number").val();
			 memory = $("#memory_size").val();
		 }else{
			 cpu=$("#cpu_numberEdit").val();
			 memory = $("#memory_sizeEdit").val();
		 }
		 
		 if(cpu==""){
			 	msg +="CPU个数值不能为空!<br>";
		 }else if(!reguNum.test(cpu)){
			  msg+="CPU输入数值有误!<br>";
		 }
		 if(memory==""){
			  msg +="内存大小不能为空!<br>";
		 }else if(!reguNum.test(memory)){
			  msg+="内存大小输入数值有误!<br>";
		 }
	 }
	 if(type=="add"){
		 hourPrice = $("#hour_price").val();
		 monthPrice = $("#month_price").val();
	 }else{
		 hourPrice = $("#hour_priceEdit").val();
		 monthPrice = $("#month_priceEdit").val();
	 }
	  
	 if(hourPrice==""){
		  msg +="按时费用不能为空!<br>";
	 }else if(!reguFloat.test(hourPrice)){
		  msg+="按时费用输入有误!<br>";
	 }
	 if(monthPrice==""){
		  msg +="包月费用不能为空!<br>";
	 }else if(!reguFloat.test(monthPrice)){
		   msg +="包月费用输入有误!<br>";
	 }
	if(msg != ''){
		$.compMsg({type:'error',msg:msg});
		return true;
	}else{
			return false;
	}
	 return false;
 }
 
 
 
//添加资费信息.
function addChargesInfo(flage){
	$("#chargesType").val(chargesType);
	$("#hour_price").val(parseFloat($("#hour_price").val()).toFixed(2));
	$("#month_price").val(parseFloat($("#month_price").val()).toFixed(2));
	$.post($('#vm_charges_add_form').attr("action"),
			$('#vm_charges_add_form').serialize(), function(data) {
				if (data.resultMessage != null) {
					if (data.resultFlage == 'success') {
						backList(data.resultMessage, '');
						flage = false;
					} else if (data.resultFlage == 'warn') {
						$.compMsg({
							type : 'error',
							msg : data.resultMessage
						});
					} else if (data.resultFlage == 'error') {
						$.compMsg({
							type : 'error',
							msg : data.resultMessage
						});
					}
				}
			}).error(function() {
	}).complete(function() {
	});
	
	return flage;
}

function chargesUpdate(id){
	$.ajax({
		type : "POST",
		url : 'detailChargesAction.action',
		data : { id : id},
		dataType : "JSON",
		cache : false,
		success : function(data) {
			showChargesEdit(data);
		}
	});
}

function chargesDetail(id){
	$.ajax({
		type : "POST",
		url : 'detailChargesAction.action',
		data : { id : id},
		dataType : "JSON",
		cache : false,
		success : function(data) {
			showChargesDetail(data);
		}
	});
}

function showChargesEdit(data){
	var title = "";
	if(chargesType=='0'){
		title = "修改虚拟机资费信息";
	}else if(chargesType=='1'){
		title= "修改云主机资费信息";
		$("#cpuDivEdit").hide();
		$("#memoryDivEdit").hide();
	}
	showEditDialog(title,data);
}

function showEditDialog(title,data){
	 $.dialog({
			title: title,
			content: document.getElementById('vm_charges_edit_dialog'),
			init:function (){
				$("#chargesId").val(data.id);
				$("#chargesTypeEdit").val(data.chargesType);
				if(chargesType=='0'){
				$("#cpu_numberEdit").val(data.cpuNumber);
				$("#memory_sizeEdit").val(data.memorySize);
				}
				$("#hour_priceEdit").val(data.hourPrice);
				$("#month_priceEdit").val(data.monthPrice);
				$("#descriptionEdit").text(data.desc);
			},
			button:[
	        {
	            name: '修改',
	            callback: function () {
					this.button({
						name: '修改',
						disabled: true
	            	});
					if(validation("edit")){
						this.button({
							name: '修改',
							disabled: false
		            	});
						return false;
					}
					var flage = true;
					flage = editChargesInfo(flage);
					this.button({
						name: '修改',
						disabled: false
	            	});
					return !flage;
	            },//callback结束
				focus: true
	        }],
			cancelVal: '取消',
			cancel:true,
			lock: true
		});
}

function showChargesDetail(data){
	var title = "";
	if(chargesType=='0'){
		title = "虚拟机资费详细信息";
	}else if(chargesType=='1'){
		title= "云主机资费详细信息";
		$("#cpuDivDetail").hide();
	}
	$.dialog({
		title: title,
		content: document.getElementById('vm_charges_detail_dialog'),
		init:function (){
			if(chargesType=='0'){
			$("#cpu_numberDetail").val(data.cpuNumber);
			$("#memory_sizeDetail").val(data.memorySize);
			}
			$("#hour_priceDetail").val(data.hourPrice);
			$("#month_priceDetail").val(data.monthPrice);
			$("#createTimeDetail").val(data.createTime);
			$("#updateTimeDetail").val(data.updateTime);
			$("#createUserDetail").val(data.createUser);
			$("#descriptionDetail").val(data.desc);
		},
		cancelVal: '取消',
		cancel:true,
		lock: true
	});
}

function editChargesInfo(flage){
	$("#hour_priceEdit").val(parseFloat($("#hour_priceEdit").val()).toFixed(2));
	$("#month_priceEdit").val(parseFloat($("#month_priceEdit").val()).toFixed(2));
	$.post($('#vm_charges_edit_form').attr("action"),
			$('#vm_charges_edit_form').serialize(), function(data) {
				if (data.resultMessage != null) {
					if (data.resultFlage == 'success') {
						backList(data.resultMessage, '');
						flage = false;
					} else if (data.resultFlage == 'warn') {
						$.compMsg({
							type : 'error',
							msg : data.resultMessage
						});
					} else if (data.resultFlage == 'error') {
						$.compMsg({
							type : 'error',
							msg : data.resultMessage
						});
					}
				}
			}).error(function() {
	}).complete(function() {
	});
	
	return flage;
}

function delCharges(id){
	 $.dialog({
			title : '删除资费信息',
			content : '确认要删除该资费信息吗？',
			ok : function() {
			    postDelReq(id);
			    return true;
			},
			cancelVal : '关闭',
			cancel : true,
			lock : true
		    });
	
}

function postDelReq(id){
    $.post("deleteChargesAction.action",{ id :id },
    		function(data) {
    	if (data.resultMessage != null) {
			if (data.resultFlage == 'success') {
				backList(data.resultMessage, '');
				flage = false;
			} else if (data.resultFlage == 'warn') {
				$.compMsg({
					type : 'error',
					msg : data.resultMessage
				});
			} else if (data.resultFlage == 'error') {
				$.compMsg({
					type : 'error',
					msg : data.resultMessage
				});
			}
		}
    		}, "json").fail(
    				          function() {
    							$.compMsg({
    							    type : 'error',
    							    msg : '由于异常删除失败！'
    						    });
    				          }
            );
}

function backList(msg, errMsg) {
	$.backListMsg( {
		msg : msg,
		errMsg : errMsg,
		url : '../charges/queryChargesAction.action?chargesType='+chargesType
	});
}