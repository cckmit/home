$(function() {

	$("#ebs").siblings().removeClass("active");
	$("#ebs").addClass("active");
	queryStatusLoading();
	vmNameChange();

	$(".status-tab a").unbind('click');
});
function queryStatusLoading() {
	var str = "";
	var flage = false;
	$("#resultList li").each(function(i) {
		var diskStatus = $(this).attr('status');
		if (diskStatus == 1 || diskStatus == 3) {
			if (!flage) {
				flage = true;
				str += "[";
			} else {
				str += ",";
			}
			var diskId = $(this).attr('name');
			str += "{id:'" + diskId + "',status:'" + diskStatus + "'}";
		}
	});
	if (flage) {
		str += "]";
		var da_val = {
			queryStr : str
		};
		$
				.ajax({
					type : "POST",
					url : 'ebsQueryListStateAction.action',
					data : da_val,
					dataType : "JSON",
					cache : false,
					success : function(data) {
						var ls = data.resultStatusInfos;
						if (ls == null || ls == 'undefined') {
							window.location.href = 'exceptionIntercepor.action';
						}
						for (var i = 0; i < ls.length; i++) {
							var ebsId = ls[i].id.replace(/\+/ig, '\\+');
							var html;
							var statusCode = ls[i].status;
							var effectiveTime = ls[i].effectiveTime;
							var overTime = ls[i].overTime;
							$("#s" + ebsId).attr('status', statusCode);
							if (statusCode == '1') {
								$("#s" + ebsId).find("div.item-status").html(
										"创建中");
								$("#s" + ebsId).find("div.item-status")
										.addClass("blue");
							} else if (statusCode == '2') {
								$("#s" + ebsId).find("div.item-status").html(
										"已创建");
								$("#s" + ebsId).find("div.item-status")
										.addClass("green");
							} else if (statusCode == '3') {
								$("#s" + ebsId).find("div.item-status").html(
										"挂载中");
								$("#s" + ebsId).find("div.item-status")
										.addClass("blue");
							} else if (statusCode == '4') {
								$("#s" + ebsId).find("div.item-status").html(
										"已挂载");
								$("#s" + ebsId).find("div.item-status")
										.addClass("green");
							} else if (statusCode == '5') {
								$("#s" + ebsId).find("div.item-status").html(
										"未挂载");
								$("#s" + ebsId).find("div.item-status")
										.addClass("orange");
							} else if (statusCode == '6') {
								$("#s" + ebsId).find("div.item-status").html(
										"创建失败");
								$("#s" + ebsId).find("div.item-status")
										.addClass("red");
							} else if (statusCode == '7') {
								$("#s" + ebsId).find("div.item-status").html(
										"挂载失败");
								$("#s" + ebsId).find("div.item-status")
										.addClass("red");
							} else if (statusCode == '8') {
								$("#s" + ebsId).find("div.item-status").html(
										"发送失败");
								$("#s" + ebsId).find("div.item-status")
										.addClass('red');
							} else if (statusCode == '9') {
								$("#s" + ebsId).find("div.item-status").html(
										"状态异常");
								$("#s" + ebsId).find("div.item-status")
										.addClass('red');

							}
							$("#createTime" + ebsId).text(effectiveTime);
							$("#expireTime" + ebsId).text(overTime);
						}
						window.setTimeout('queryStatusLoading()', 10000);
					},
					error : function() {
					}
				});
	}
}
function goToPage(diskId, caseId) {
	$("#diskId").val(diskId);
	$("#caseId").val(caseId);
	$("#gotoForm").attr('action', 'diskDetail.action');
	$("#gotoForm").submit();
}

function vmNameChange() {
	var maxTextLength = 24; 
	$("li h2")
			.each(
					function(i) {
						if ($(this).height() > 24) {
							var text = $(this).text();
							if (text.length > maxTextLength) {
								var byteValLen = 0;
								var returnText = "";
								for (var i = 0; i < text.length; i++) {
									(text[i].match(/[^x00-xff]/ig) != null) ? (byteValLen += 2)
											: (byteValLen += 1);
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
function getPageData(url) {
	location.href = url;
}

function getPageDataAsync(url) {
	refreshPageByState($('ul.status-tab li.selected a')[0], url);
}

function refreshPageByState(obj, url) {
	$(obj).addClass("active").siblings().removeClass("active");

	var status = $(obj).attr("value");
	var flag = true;
	if (!url) {
		url = "ebsQueryListByMountStatusAction.action";
		flag = false;
	}

	$
			.ajax({
				url : url,
				type : 'POST',
				dataType : "json",
				data : flag ? {} : {
					status : status,
					asyncJSPageMethod : 'getPageDataAsync'
				},
				success : function(data) {
					var str = '';

					var ebsList = data.diskInfos;

					for (var i = 0; i < ebsList.length; i++) {

						var statusClass = "";
						var statusText = "";

						switch (ebsList[i].diskStatus) {
						case '0':
							statusClass = "blue";
							statusText = "待创建";
							break;
						case '1':
							statusClass = "blue";
							statusText = "创建中";
							break;
						case '2':
							statusClass = "green";
							statusText = "已创建";
							break;
						case '3':
							statusClass = "blue";
							statusText = "挂载中";
							break;
						case '4':
							statusClass = "green";
							statusText = "已挂载";
							break;
						case '5':
							statusClass = "orange";
							statusText = "未挂载";
							break;
						case '6':
							statusClass = "red";
							statusText = "创建失败";
							break;
						case '7':
							statusClass = "red";
							statusText = "挂载失败";
							break;
						case '8':
							statusClass = "red";
							statusText = "发送失败";
							break;
						case '9':
							statusClass = "red";
							statusText = "状态异常";
							break;

						default:
							break;
						}

						var diskId=ebsList[i].diskId==null?"":ebsList[i].diskId;
						
						str += '<li status="'
								+ ebsList[i].diskStatus
								+ '" id="'
								+ ebsList[i].diskId
								+ '" name="'
								+ ebsList[i].diskId
								+ '"><a href="javascript:void(0);" onclick="goToPage(\''
								+ diskId
								+'\',\''+ebsList[i].caseId
								+ '\');return false;"><div class="item-status '
								+ statusClass
								+ '">'
								+ statusText
								+ '</div><div class="item-content"><h3>'
								+ (ebsList[i].diskName.trim() ? ebsList[i].diskName
										.trim()
										: '&nbsp;')
								+ '</h3><div class="detail"><div class="detail-col"><div class="detail-row"><span>云硬盘容量：</span> <span>'
								+ ebsList[i].diskSize
								+ 'TB</span></div><div class="detail-row"><span>所属企业客户：</span> <span id="createTime">'
								+ ebsList[i].appName
								+ '</span></div></div><div class="detail-col"><div class="detail-row"><span>创建时间：</span> <span id="createTime'
								+ ebsList[i].diskId
								+ '">'
								+ ebsList[i].createTime
								+ '</span></div><div class="detail-row"><span>到期时间：</span> <span id="expireTime'
								+ ebsList[i].diskId + '">'
								+ ebsList[i].expireTime
								+ '</span></div></div></div></div></a></li>';

					}

					$("#resultList").empty();
					$("#resultList").html(str);

					if ((!ebsList) || ebsList.length === 0)
						$("#listNull li").show();
					else
						$("#listNull li").hide();
					$(".pageBar").empty();
					if (ebsList.length > 0) {
						$(".pageBar").html(data.pageBar);
					}
				},
				error : function(data) {
					$.compMsg({
						type : 'error',
						msg : data.msg
					});
				}
			});
}

