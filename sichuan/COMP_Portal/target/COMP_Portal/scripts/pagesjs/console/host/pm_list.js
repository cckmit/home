$(function() {
	// 菜单显示当前
	$(".left-menu li:contains('物理机')").addClass("selected");//菜单显示当前
	// queryStatusLoading(); //从数据库循环刷新状态，有了下边从接口刷新状态，从数据库循环刷新状态的方法其实可以注掉
	queryStatusFromResPool(); // 从接口刷新状态
	$("select[id!='select']").uniform();// 初始化下拉菜单样式
	pmNameChange();
});

function queryStatusFromResPool() {
	var str = "";
	var flage = false;
	$("#resultList li").each(function(i){
		var status = $(this).attr('status');
		var pmId = $(this).attr('name');
		if(pmId != null && pmId != ''){
			if(!flage){
				flage = true;
				str+="[";
			}else{
				str+=",";
			}
			str+="{id:'"+pmId+"',status:'"+status+"'}";
		}
	});
	  if(flage){
		  str+="]";
		   var da_val = {queryStr:str};
		   $.ajax({
	            type:"POST",
	            url:'pmSearchStateAction.action',
	            data:da_val,
	            dataType:"JSON",
	            cache: false,
	            success:function(data){
		            var ls = data.resultStatusInfos;
					if(ls==null||ls=='undefined'){
						window.location.href='exceptionIntercepor.action';
					}
					// ls传过来的是页面上pmid状态变化的量
					if (ls.length != 0) {
						// 状态有变化
		            	for(var i=0;i<ls.length;i++){
		            		var pmId = ls[i].id.replace(/\+/ig,'\\+');
			            	//var html = '';
			            	var statusCode = ls[i].status;
			            	var statusDesc = ls[i].statusText;
			            	
			            	$("#status"+pmId).text(statusDesc);
			            	if (statusCode == '0') {
								$("#status"+pmId).removeClass().addClass(
										"status s-blue");
							} else if (statusCode == '1') {
								$("#status"+pmId).removeClass().addClass(
										"status s-blue");
							} else if (statusCode == '2') {
								$("#status"+pmId).removeClass().addClass(
										"status s-green");
							} else if (statusCode == '3') {
								$("#status"+pmId).removeClass().addClass(
										"status s-brown");
							} else if (statusCode == '4') {
								$("#status"+pmId).removeClass().addClass(
										"status s-gray");
							} else if (statusCode == '5') {
								$("#status"+pmId).removeClass().addClass(
										"status s-orange");
							} else if (statusCode == '10') {
								$("#status"+pmId).removeClass().addClass(
										"status s-orange");
							} else if (statusCode == '11') {
								$("#status"+pmId).removeClass().addClass(
										"status s-orange");
							} else if (statusCode == '12') {
								$("#status"+pmId).removeClass().addClass(
								"status s-purple");
							} else if (statusCode == '13') {
								$("#status"+pmId).removeClass().addClass(
										"status s-blue");
							}
		            	}
		            } else {
		            	$("#resultList li").each(function(i){
		         		   var status = $(this).attr('status');
		         		   var pmId = $(this).attr('name');
		         		   // 状态没变化，还取变化之前的值
							if (status == '0') {
								$("#status"+pmId).text("待创建");
								$("#status"+pmId).removeClass().addClass(
										"status s-blue");
							} else if (status == '1') {
								$("#status"+pmId).text("创建中");
								$("#status"+pmId).removeClass().addClass(
										"status s-blue");
							} else if (status == '2') {
								$("#status"+pmId).text("运行中");
								$("#status"+pmId).removeClass().addClass(
										"status s-green");
							} else if (status == '3') {
								$("#status"+pmId).text("已删除");
								$("#status"+pmId).removeClass().addClass(
										"status s-brown");
							} else if (status == '4') {
								$("#status"+pmId).text("已停止");
								$("#status"+pmId).removeClass().addClass(
										"status s-gray");
							} else if (status == '12') {
								$("#status"+pmId).text("已暂停");
								$("#status"+pmId).removeClass().addClass(
										"status s-purple");
							} else if (status == '5') {
								$("#status"+pmId).text("操作失败");
								$("#status"+pmId).removeClass().addClass(
										"status s-orange");
							} else if (status == '10') {
								$("#status"+pmId).text("发送失败");
								$("#status"+pmId).removeClass().addClass(
										"status s-orange");
							} else if (status == '11') {
								$("#status"+pmId).text("状态异常");
								$("#status"+pmId).removeClass().addClass(
										"status s-orange");
							} else if (status == '13') {
								$("#status"+pmId).text("处理中");
								$("#status"+pmId).removeClass().addClass(
										"status s-blue");
							}
		         	  });
					}
					window.setTimeout(queryStatusFromResPool,10000);
	            },
	            error:function(){
		        }
		   });
	  }
}

function queryStatusLoading() {
	var str = "";
	var flage = false;
	$("#resultList li").each(
			function(i) {
				var status = $(this).attr('status');
				if (status == 1 || status == 6 || status == 7 || status == 8
						|| status == 9) {
					if (!flage) {
						flage = true;
						str += "[";
					} else {
						str += ",";
					}
					var pmId = $(this).attr('name');
					str += "{id:'" + pmId + "',status:'" + status + "'}";
				}
			});
	if (flage) {
		str += "]";
		var da_val = {
			queryStr : str
		};
		$.ajax({
			type : "POST",
			url : 'pmQueryListStateAction.action',
			data : da_val,
			dataType : "JSON",
			cache : false,
			success : function(data) {
				var ls = data.resultStatusInfos;
				if (ls == null || ls == 'undefined') {
					window.location.href = 'exceptionIntercepor.action';
				}
				for (var i = 0; i < ls.length; i++) {
					var pmId = ls[i].id.replace(/\+/ig, '\\+');
					var html;
					var statusCode = ls[i].status;
					var statusDesc = ls[i].statusText;
					var effectiveTime = ls[i].effectiveTime;
					var overTime = ls[i].overTime;
					$("#s" + pmId).attr('status', statusCode);
					if (statusCode == '1') {
						html = '<span class="status s-blue">' + statusDesc
								+ '</span>';
					} else if (statusCode == '2') {
						html = '<span class="status s-green">' + statusDesc
								+ '</span>';
					} else if (statusCode == '3') {
						html = '<span class="status s-brown">' + statusDesc
								+ '</span>';
					} else if (statusCode == '4') {
						html = '<span class="status s-gray">' + statusDesc
								+ '</span>';
					} else if (statusCode == '5') {
						html = '<span class="status s-red">' + statusDesc
								+ '</span>';
					} else if (statusCode == '6') {
						html = '<span class="status s-orange">' + statusDesc
								+ '</span>';
					} else if (statusCode == '7') {
						html = '<span class="status s-waterblue">' + statusDesc
								+ '</span>';
					} else if (statusCode == '8') {
						html = '<span class="status s-wathet">' + statusDesc
								+ '</span>';
					} else if (statusCode == '9') {
						html = '<span class="status s-deongaree">' + statusDesc
								+ '</span>';
					} else if (statusCode == '10') {
						html = '<span class="status s-orange">' + statusDesc
								+ '</span>';
					} else if (statusCode == '11') {
						html = '<span class="status s-orange">' + statusDesc
								+ '</span>';
					}
					$("#status" + pmId).html(html);
					$("#effectiveTime" + pmId).text(effectiveTime);
					$("#overTime" + pmId).text(overTime);
				}
				window.setTimeout('queryStatusLoading()', 10000);
			},
			error : function() {
			}
		});
	}
}
function goToPage(caseId) {
	$("#caseId").val(caseId);
	$("#gotoForm").attr('action', 'pmDetailAction.action');
	$("#gotoForm").submit();
}
/**
 * 物理机名称字体大小
 */
function pmNameChange() {
	var maxTextLength = 24;// 最大字体长度
	$("li h2")
			.each(
					function(i) {
						if ($(this).height() > 24) {
							var text = $(this).text();
							// 判断是否大于长度截取字符
							if (text.length > maxTextLength) {
								var byteValLen = 0;
								var returnText = "";
								for (var i = 0; i < text.length; i++) {
									(text[i].match(/[^x00-xff]/ig) != null) ? (byteValLen += 2)
											: (byteValLen += 1);
									returnText += text[i]
									if (byteValLen > maxTextLength) {
										break;
									}
								}
								$(this).text(returnText + "...");
							}
							$(this).css("font-size", "12px");
						}
					})
}
//翻页调用js
function getPageData(url) {
	queryPm(url);
}

//查询列表
function queryPm(url) {
	var status = $(".status-tab .select a").attr("value");
	var privateIp = $('#privateIp').val();
	var isoName = $('#isoName').val();
	var pmName = $('#pmName').val();
	
	var da_val;
	if (url == '' || url == undefined) {
		da_val = {
			privateIp: privateIp,
			isoName: isoName,
			pmName : pmName,
			optState: status
		};
		url = 'pmQueryJson.action';
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
					var list = data.pmResultInfoMap.list;
					var page = data.pmResultInfoMap.page;
					
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
							
							str += '<li status="'+entity.status+'" id="s'+entity.pmId+'" name="'+entity.pmId+'">';
							str += '<a href="#" onclick="goToPage(\''+ entity.caseId +'\');">';
							str += '<input type="hidden" id="iso'+entity.pmId+'" value="'+entity.isoName+'" /> <span class="ico-m ico-pm"></span>';
							if(entity.pmName!=''){
								str += '<div><h2>'+entity.pmName+'</h2></div>';
							}else{
								str += '<div><h2>&nbsp</h2></div>';
							}
							str += '<div style="width: 230px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis" title="'+entity.ipStr+'"> 内网IP：&nbsp'+entity.ipStr+'</div>';
							str += '<div>创建时间：<SPAN id="effectiveTime'+entity.pmId+'">'+entity.effectiveTime+'</SPAN></div>';
							str += '<div>';
							str += '<span id="status'+entity.pmId+'" class="status ';
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
							case '5':
								str += 's-orange">';
							  break;
							case '10':
								str += 's-orange">';
							  break;
							case '11':
								str += 's-orange">';
							  break;
							case '12':
								str += 's-purple">';
							  break;
							case '13':
								str += 's-blue">';
							  break;
							}
							str += entity.status.desc+'</span>';
							str += entity.isoName+'</div>';
							str += '<div>所属企业客户：&nbsp'+entity.appName+'</div>';
							str += '<div>到期时间：<SPAN id="overTime'+entity.pmId+'">'+entity.overTime+'</SPAN></div></a></li>';
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