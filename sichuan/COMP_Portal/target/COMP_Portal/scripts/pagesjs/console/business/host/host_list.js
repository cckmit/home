var businessName;
$(function() {
    $(".left-menu li:has(#business_"+businessId+")").addClass("selected").next().show();// 菜单显示当前
    if($("#type").val()==0){
    	$(".left-menu #menu_vm_"+businessId).parent().addClass("selected");
    }else if($("#type").val()==1){
    	$(".left-menu #menu_pm_"+businessId).parent().addClass("selected");
    }
    
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
});

/**
 * 从资源池加载最新的资源状态
 */
function queryStatusFromResPool() {

	var str = "[";

	for (var i = 0; i < $("#resultList li").length; i++) {
		var status = $("#resultList li:nth-child(" + (i + 1) + ")").attr(
				'status');
		var vmId = $("#resultList li:nth-child(" + (i + 1) + ")").attr('name');
		str += "{id:'" + vmId + "',status:'" + status + "'}";
		if (i != ($("#resultList li").length - 1)) {
			str += ",";
		}
	}
	str += "]";

	if ($("#resultList li").length > 0) {
		var da_val = {
			queryStr : str
		};
		$.ajax({
			type : "POST",
			url : 'vmSearchStateAction.action',
			data : da_val,
			dataType : "JSON",
			cache : false,
			success : function(data) {
				var ls = data.resultStatusInfos;
				if (ls == null || ls == 'undefined') {
					window.location.href = 'exceptionIntercepor.action';
				}

				// ls传过来的是页面上vmid状态变化的量
				if (ls.length != 0) {
					// 状态有变化
					for (var i = 0; i < ls.length; i++) {
						var vmId = ls[i].id.replace(/\+/ig, '\\+');
						// var html = '';
						var statusCode = ls[i].status;
						var statusDesc = ls[i].statusText;
						var statusClass = "";

						if (statusCode == '0') {
							statusClass = "blue";
						} else if (statusCode == '1') {
							statusClass = "blue";
						} else if (statusCode == '2') {
							statusClass = "green";
						} else if (statusCode == '3') {
							statusClass = "blue";
						} else if (statusCode == '4') {
							statusClass = "orange";
						} else if (statusCode == '6') {
							statusClass = "orange";
						} else if (statusCode == '9') {
							statusClass = "red";
						} else if (statusCode == '14') {
							statusClass = "red";
						} else if (statusCode == '15') {
							statusClass = "red";
						} else if (statusCode == '16') {
							statusClass = "blue";
						}
						$("#status" + vmId).text(statusDesc);
						$("#status" + vmId).removeClass().addClass(
								"item-status").addClass(statusClass);

					}
				}
				window.setTimeout(queryStatusFromResPool, 10000);
			},
			error : function() {
			}
		});
	}
}


function goToVMPage(vmId,businessId,businessName){
	$("#gotoForm").attr('action','vmDetail.action?queryBusinessId='+businessId);
	$("#vmId").val(vmId);
	$("#gotoForm").submit();
}
function goToPMPage(pmId){
	$("#gotoForm").attr('action','pmDetail.action?queryBusinessId='+businessId);
	$("#pmId").val(pmId);
	$("#gotoForm").submit();
}
/**
 * 云主机名称字体大小
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
	$
			.ajax({
				type : "POST",
				url : url,
				data : da_val,
				dataType : "JSON",
				cache : false,
				success : function(data) {
					if (data.resultPath == "error") {
						window.location.href = 'exceptionIntercepor.action';
					}
					var list = data.vmResultInfoMap.list;
					var page = data.vmResultInfoMap.page;

					if (list.length == 0) {
						$("#resultList").empty();
						$("#listNull li").show();
						$(".pageBar").html(page);
						return;
					}
					if (!jQuery.isEmptyObject(list)) {
						var statusCode = "";
						var statusClass = "";
						
						var str="";
						for (var i = 0; i < list.length; i++) {
							var entity = list[i];
							statusCode = entity.status.code;
							statusClass = "";
							if (statusCode == '0') {
								statusClass = "blue";
							} else if (statusCode == '1') {
								statusClass = "blue";
							} else if (statusCode == '2') {
								statusClass = "green";
							} else if (statusCode == '3') {
								statusClass = "blue";
							} else if (statusCode == '4') {
								statusClass = "orange";
							} else if (statusCode == '6') {
								statusClass = "orange";
							} else if (statusCode == '9') {
								statusClass = "red";
							} else if (statusCode == '14') {
								statusClass = "red";
							} else if (statusCode == '15') {
								statusClass = "red";
							} else if (statusCode == '16') {
								statusClass = "blue";
							}

							 str += '<li status="'
									+ entity.status
									+ '" id="s'
									+ entity.vmId
									+ '" name="'
									+ entity.vmId
									+ '"><a href="javascript:void(0);" onclick="goToVMPage(\''
									+ entity.caseId
									+ '\');"><input type="hidden" id="iso'
									+ entity.vmId
									+ '" value="'
									+ entity.isoName
									+ '" /><div id="'
									+ entity.vmId
									+ '" class="item-status '
									+ statusClass
									+ '">'
									+ entity.status.desc
									+ '</div><div class="item-content"><h3>'
									+ entity.vmName
									+ '</h3><div class="detail"><div class="detail-col">'
									+ entity.isoName
									+ '</div><div class="detail-col"><div class="detail-row"><span>内网IP：</span> <span>'
									+ entity.privateIpStr
									+ '</span></div><div class="detail-row"><span>所属业务：</span> <span>'
									+ entity.appName
									+ '</span></div></div><div class="detail-col"><div class="detail-row"><span>创建时间：</span> <span id="effectiveTime'
									+ entity.vmId
									+ '">'
									+ entity.effectiveTime
									+ '</span></div><div class="detail-row"><span>到期时间：</span> <span id="overTime'
									+ entity.vmId
									+ '">'
									+ entity.overTime
									+ '</span></div></div></div></div></a></li>';

							$("#resultList").empty();
							$("#resultList").append(str);
							$("#listNull li").hide();
							$(".pageBar").html(page);
						}
						queryStatusFromResPool();
					}
				}
			});
}