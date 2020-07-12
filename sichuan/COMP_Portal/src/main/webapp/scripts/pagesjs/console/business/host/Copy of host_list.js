$(function() {
    $(".left-menu li:has(#business_"+businessId+")").addClass("selected").next().show();// 菜单显示当前
    if($("#type").val()==0){
    	$(".left-menu #menu_vm_"+businessId).parent().addClass("selected");
    }else if($("#type").val()==1){
    	$(".left-menu #menu_pm_"+businessId).parent().addClass("selected");
    }
    
	//queryStatusLoading(); //从数据库循环刷新状态，有了下边从接口刷新状态，从数据库循环刷新状态的方法其实可以注掉
	queryStatusFromResPool(); // 从接口刷新状态
	
	vmNameChange();
	
	$("#apply_host").toggle(
		  function () {
			$(this).next().show();
		  },
		  function () {
			$(this).next().hide();
		  }
	);
	
	// 显示主机类型
	function selectType(){
		$(".select-type").show();
	}

	$("select[id!='select']").uniform();//初始化下拉菜单样式
});

/**
 * 从资源池加载最新的资源状态
 */
function queryStatusFromResPool() {
	var str = "";
	var flage = false;
	$("#resultList li").each(function(i){
		var status = $(this).attr('status');
		var vmId = $(this).attr('name');
		if(vmId != null && vmId != ''){
		   if(!flage){
			   flage = true;
			   str+="[";
		   }else{
		   	   str+=",";
		   }
		   str+="{id:'"+vmId+"',status:'"+status+"'}";
		}
	 });
	 if(flage){
		  str+="]";
		   var da_val = {queryStr:str};
		   $.ajax({
	            type:"POST",
	            url:'vmSearchStateAction.action',
	            data:da_val,
	            dataType:"json",
	            cache: false,
	            success:function(data){
		            var ls = data.resultStatusInfos;
					if(ls==null||ls=='undefined'){
						window.location.href='exceptionIntercepor.action';
					}
					// ls传过来的是页面上vmid状态变化的量
					if (ls.length != 0) {
						// 状态有变化
		            	for(var i=0;i<ls.length;i++){
		            		var vmId = ls[i].id.replace(/\+/ig,'\\+');
			            	//var html = '';
			            	var statusCode = ls[i].status;
			            	var statusDesc = ls[i].statusText;
			            	
			            	$("#status"+vmId).text(statusDesc);
			            	if (statusCode == '0') {
								$("#status"+vmId).removeClass().addClass(
										"status s-blue");
							} else if (statusCode == '1') {
								$("#status"+vmId).removeClass().addClass(
										"status s-blue");
							} else if (statusCode == '2') {
								$("#status"+vmId).removeClass().addClass(
										"status s-green");
							} else if (statusCode == '3') {
								$("#status"+vmId).removeClass().addClass(
										"status s-brown");
							} else if (statusCode == '4') {
								$("#status"+vmId).removeClass().addClass(
										"status s-gray");
							} else if (statusCode == '6') {
								$("#status"+vmId).removeClass().addClass(
										"status s-purple");
							} else if (statusCode == '9') {
								$("#status"+vmId).removeClass().addClass(
										"status s-orange");
							} else if (statusCode == '14') {
								$("#status"+vmId).removeClass().addClass(
										"status s-orange");
							} else if (statusCode == '15') {
								$("#status"+vmId).removeClass().addClass(
										"status s-orange");
							} else if (statusCode == '16') {
								$("#status"+vmId).removeClass().addClass(
										"status s-blue");
							}
			            	/*
			            	$("#s"+vmId).attr('status',statusCode);
			            	if(statusCode=='0'){
				            	html = '<span class="status s-blue">'+statusDesc+'</span>';
				            }else if(statusCode=='1'){
				            	html = '<span class="status s-blue">'+statusDesc+'</span>';
				            }else if(statusCode=='2'){
				            	html = '<span class="status s-green">'+statusDesc+'</span>';
				            }else if(statusCode=='3'){
				            	html = '<span class="status s-brown">'+statusDesc+'</span>';
				            }else if(statusCode=='4'){
				            	html = '<span class="status s-gray">'+statusDesc+'</span>';
				            }else if(statusCode=='6'){
				            	html = '<span class="status s-purple">'+statusDesc+'</span>';
				            }else if(statusCode=='9'){
				            	html = '<span class="status s-orange">'+statusDesc+'</span>';
				            }else if(statusCode=='14'){
				            	html = '<span class="status s-orange">'+statusDesc+'</span>';
				            }else if(statusCode=='15'){
				            	html = '<span class="status s-orange">'+statusDesc+'</span>';
				            }else if(statusCode=='16'){
				            	html = '<span class="status s-blue">'+statusDesc+'</span>';
				            }
				            html +=$("#iso"+vmId).val();
			            	$("#status"+vmId).html(html);*/
		            	}
		            } else {
		            	$("#resultList li").each(function(i){
		         		   var status = $(this).attr('status');
		         		   var vmId = $(this).attr('name');
		         		   // 状态没变化，还取变化之前的值
							if (status == '0') {
								$("#status"+vmId).text("待创建");
								$("#status"+vmId).removeClass().addClass(
										"status s-blue");
							} else if (status == '1') {
								$("#status"+vmId).text("创建中");
								$("#status"+vmId).removeClass().addClass(
										"status s-blue");
							} else if (status == '2') {
								$("#status"+vmId).text("运行中");
								$("#status"+vmId).removeClass().addClass(
										"status s-green");
							} else if (status == '3') {
								$("#status"+vmId).text("已删除");
								$("#status"+vmId).removeClass().addClass(
										"status s-brown");
							} else if (status == '4') {
								$("#status"+vmId).text("已停止");
								$("#status"+vmId).removeClass().addClass(
										"status s-gray");
							} else if (status == '6') {
								$("#status"+vmId).text("已暂停");
								$("#status"+vmId).removeClass().addClass(
										"status s-purple");
							} else if (status == '9') {
								$("#status"+vmId).text("操作失败");
								$("#status"+vmId).removeClass().addClass(
										"status s-orange");
							} else if (status == '14') {
								$("#status"+vmId).text("发送失败");
								$("#status"+vmId).removeClass().addClass(
										"status s-orange");
							} else if (status == '15') {
								$("#status"+vmId).text("状态异常");
								$("#status"+vmId).removeClass().addClass(
										"status s-orange");
							} else if (status == '16') {
								$("#status"+vmId).text("处理中");
								$("#status"+vmId).removeClass().addClass(
										"status s-blue");
							}
		         	  });
					}
					setTimeout(queryStatusFromResPool, 10000);
	            },
	            error:function(){
		        }
		   });
	  }
}

function queryStatusLoading(){
	var str = "";
	var flage = false;
	$("#resultList li").each(function(i){
		   if($(this).attr('vtype')=='0'){
		   var status = $(this).attr('status');
		   if(status==1){
			   if(!flage){
				   flage = true;
				   str+="[";
			   }else{
			   	   str+=",";
			   }
			   var vmId = $(this).attr('name');
			   str+="{id:'"+vmId+"',status:'"+status+"'}";
		   }
		   }
	  });
	  if(flage){
		  str+="]";
		   var da_val = {queryStr:str};
		   $.ajax({
	            type:"POST",
	            url:'vmQueryListStateAction.action',
	            data:da_val,
	            dataType:"JSON",
	            cache: false,
	            success:function(data){
		            var ls = data.resultStatusInfos;
					if(ls==null||ls=='undefined'){
						window.location.href='exceptionIntercepor.action';
					}
	            	for(var i=0;i<ls.length;i++){
	            		var vmId = ls[i].id.replace(/\+/ig,'\\+');
		            	var html = '';
		            	var statusCode = ls[i].status;
		            	var statusDesc = ls[i].statusText;
		            	var effectiveTime = ls[i].effectiveTime;
		            	var overTime = ls[i].overTime;
		            	$("#s"+vmId).attr('status',statusCode);
		            	if(statusCode=='1'){
			            	html = '<span class="status s-blue">'+statusDesc+'</span>';
			            }else if(statusCode=='2'){
			            	html = '<span class="status s-green">'+statusDesc+'</span>';
			            }else if(statusCode=='3'){
			            	html = '<span class="status s-brown">'+statusDesc+'</span>';
			            }else if(statusCode=='4'){
			            	html = '<span class="status s-gray">'+statusDesc+'</span>';
			            }else if(statusCode=='6'){
			            	html = '<span class="status s-purple">'+statusDesc+'</span>';
			            }else if(statusCode=='9'){
			            	html = '<span class="status s-red">'+statusDesc+'</span>';
			            }else if(statusCode=='14'){
			            	html = '<span class="status s-orange">'+statusDesc+'</span>';
			            }else if(statusCode=='15'){
			            	html = '<span class="status s-orange">'+statusDesc+'</span>';
			            }else if(statusCode=='16'){
			            	html = '<span class="status s-blue">'+statusDesc+'</span>';
			            }
			            html +=$("#iso"+vmId).val();
		            	$("#status"+vmId).html(html);
		            	$("#effectiveTime"+vmId).text(effectiveTime);
		            	$("#overTime"+vmId).text(overTime);
		            }
	     		    window.setTimeout('queryStatusLoading()',90000);
	            },
	            error:function(){
		        }
		   });
	  }
}
function goToVMPage(vmId){
	$("#gotoForm").attr('action','vmDetail.action?queryBusinessId='+businessId+'&businessName='+businessName);
	$("#vmId").val(vmId);
	$("#gotoForm").submit();
}
function goToPMPage(pmId){
	$("#gotoForm").attr('action','pmDetail.action?queryBusinessId='+businessId+'&businessName='+businessName);
	$("#pmId").val(pmId);
	$("#gotoForm").submit();
}
/**
 * 虚拟机名称字体大小
 */
function vmNameChange(){
    var maxTextLength = 24;// 最大字体长度
    $("li h2").each(function(i){
        if ($(this).height() > 24) {
            var text = $(this).text().replace(/(^\s*)|(\s*$)/g,"");
			// 判断是否大于长度截取字符
            if (text.length > maxTextLength) {
                var byteValLen = 0;
                var returnText = "";
                for (var i = 0; i < text.length; i++) {
                    (text[i].match(/[^x00-xff]/ig) != null) ? (byteValLen += 2) : (byteValLen += 1);
                    returnText += text[i];
                    if (byteValLen > maxTextLength) {
                        break;
                    }
                }
                $(this).text(returnText + "...");
            }
			$(this).css("font-size", "12px");
        }
    });
}
//按状态查询
function will(obj,queryBusinessId){
	$(".status-tab li").removeClass("select");
	$(obj).parents('li').addClass("select");
	queryVm('',queryBusinessId);
}

//翻页调用js
function getPageData(url) {
	queryVm(url);
}

//查询列表
function queryVm(url,queryBusinessId) {
	var status = $(".status-tab .select a").attr("value");
	var privateIp = $('#privateIp').val();
	var isoName = $('#isoName').val();
	var vmName = $('#vmName').val();
	
	var da_val;
	if (url == '' || url == undefined) {
		da_val = {
			privateIp: privateIp,
			isoName: isoName,
			vmName : vmName,
			optState: status,
			queryBusinessId : queryBusinessId
		};
		url = 'vmQueryJson.action';
	}
	$.ajax( {
				type : "POST",
				url : url,
				data : da_val,
				dataType : "JSON",
				cache : false,
				success : function(data) {
					if(data.resultPath=="error"){
						window.location.href = 'exceptionIntercepor.action';
					}
					var list = data.vmResultInfoMap.list;
					var page = data.vmResultInfoMap.page;
					
					if(list.length==0){
						$("#resultList").empty();
						$("#listNull li").show();
						$(".pageBar").html(page);
						$("select[id!='select']").uniform();
						$.uniform.update();
						return;
					}
					if (!jQuery.isEmptyObject(list)) {
						var str = '';
						
						for ( var i = 0; i < list.length; i++) {
							var entity = list[i];
							
							str += '<li status="'+entity.status+'" id="s'+entity.vmId+'" name="'+entity.vmId+'">';
							str += '<a href="#" onclick="goToVMPage(\''+ entity.caseId +'\');">';
							str += '<input type="hidden" id="iso'+entity.vmId+'" value="'+entity.isoName+'" /> <span class="ico-m ico-pm"></span>';
							if(entity.vmName!=''){
								str += '<div><h2>'+entity.vmName+'</h2></div>';
							}else{
								str += '<div><h2>&nbsp</h2></div>';
							}
							str += '<div style="width: 230px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis" title="'+entity.privateIpStr+'"> 内网IP：&nbsp'+entity.privateIpStr+'</div>';
							str += '<div>创建时间：<SPAN id="effectiveTime'+entity.vmId+'">'+entity.effectiveTime+'</SPAN></div>';
							str += '<div>';
							str += '<span id="status'+entity.vmId+'" class="status ';
							switch(entity.status.code)
							{
							case '0':
								str += 's-blue">';
							  break;
							case '1':
								str += 's-blue">';
							  break;
							case '2':
								str += 's-green">';
							  break;
							case '3':
								str += 's-brown">';
							  break;
							case '4':
								str += 's-gray">';
							  break;
							case '6':
								str += 's-purple">';
							  break;
							case '9':
								str += 's-orange">';
							  break;
							case '14':
								str += 's-orange">';
							  break;
							case '15':
								str += 's-orange">';
							  break;
							case '16':
								str += 's-blue">';
							  break;
							}
							str += entity.status.desc+'</span>';
							str += entity.isoName+'</div>';
							str += '<div>所属业务：&nbsp'+entity.appName+'</div>';
							str += '<div>到期时间：<SPAN id="overTime'+entity.vmId+'">'+entity.overTime+'</SPAN></div></a></li>';
						}
						$("#resultList").empty();
						$("#resultList").append(str);
						$("#listNull li").hide();
						$(".pageBar").html(page);
						$("select[id!='select']").uniform();
						$.uniform.update();
				}
					queryStatusFromResPool();
			}
			});
}