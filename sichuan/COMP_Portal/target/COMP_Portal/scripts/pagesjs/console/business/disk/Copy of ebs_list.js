$(function() {
	// 菜单显示当前
	businessId = GetRequest()['queryBusinessId'];
	$(".left-menu li:has(#business_" + businessId + ")").addClass("selected").next().show();
	$(".left-menu #menu_bs_" + businessId).parent().addClass("selected");
	$("select[id!='select']").uniform();// 初始化下拉菜单样式
	queryStatusLoading();
	vmNameChange();
	
	/* 解除left-menu.js中对标签绑定的点击事件 */
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
		$.ajax({
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
						html = '<span class="status s-gray">创建中</span>';
					} else if (statusCode == '2') {
						html = '<span class="status s-blue">已创建</span>';
					} else if (statusCode == '3') {
						html = '<span class="status s-gray">挂载中</span>';
					} else if (statusCode == '4') {
						html = '<span class="status s-green">已挂载</span>';
					} else if (statusCode == '5') {
						html = '<span class="status s-blue">未挂载</span>';
					} else if (statusCode == '6') {
						html = '<span class="status s-orange">创建失败</span>';
					} else if (statusCode == '7') {
						html = '<span class="status s-orange">挂载失败</span>';
					} else if (statusCode == '8') {
						html = '<span class="status s-orange">发送失败</span>';
					} else if (statusCode == '9') {
						html = '<span class="status s-orange">状态异常</span>';
					}
					$("#status" + ebsId).html(html);
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
	$("#gotoForm").attr('action', 'diskDetail.action?queryBusinessId=' + businessId);
	$("#gotoForm").submit();
}
/**
 * 云硬盘名称字体大小
 */
function vmNameChange() {
	var maxTextLength = 24;// 最大字体长度
	$("li h2").each(function(i) {
		if ($(this).height() > 24) {
			var text = $(this).text();
			// 判断是否大于长度截取字符
			if (text.length > maxTextLength) {
				var byteValLen = 0;
				var returnText = "";
				for (var i = 0; i < text.length; i++) {
					(text[i].match(/[^x00-xff]/ig) != null) ? (byteValLen += 2) : (byteValLen += 1);
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

function getPageData(url){
	window.location = url;
}

function getPageDataAsync(url) {
	refreshPageByState($('ul.status-tab li.selected a')[0] , url);
}

function refreshPageByState(obj,url) {
	$(".status-tab li").removeClass("selected");
	$(obj).parents('li').addClass("selected");

	var status = $(".status-tab .selected a").attr("value");
	var flag = true;
	
	if (!url){
		url = "ebsQueryListByMountStatusAction.action";
		flag = false;
	}

	$.ajax({
		url : url,
		type : 'POST',
		dataType : 'json',
		data : flag?{}:{
			status : status,
			queryBusinessId : businessId,
			asyncJSPageMethod : 'getPageDataAsync'
		},
		success : function(data) {
			var str = '';

			var ebsList = data.diskInfos;
			
			for (var i = 0; i < ebsList.length; i++) {
				str += '<li status="' + ebsList[i].diskStatus + '" id="' + ebsList[i].diskId + '" ';
				str += 'name="' + ebsList[i].diskId + '"><a href="javascript:void(0);" onclick="goToPage(\'';
				str += ebsList[i].diskId + '\');return false;"><span class="ico-m ico-vh"></span><div><h2>';
				str += (ebsList[i].diskName.trim() ? ebsList[i].diskName.trim() : '&nbsp;') + '</h2></div><div> 云硬盘容量： ';
				str += ebsList[i].diskSize + ' G</div><div>'
						+ (ebsList[i].createTime ? '创建时间：<SPAN id="createTime' + ebsList[i].diskId + '">' + ebsList[i].createTime + '</SPAN>' : '&nbsp;')
						+ '</div>';
				str += '<div id="status' + ebsList[i].diskId + '"><span class="status ';
				switch (ebsList[i].diskStatus) {
				case '0':
					str += 's-blue">   待创建';
					break;
				case '1':
					str += 's-gray">   创建中';
					break;
				case '2':
					str += 's-blueGreen">   已创建';
					break;
				case '3':
					str += 's-gray">   挂载中';
					break;
				case '4':
					str += 's-green">  已挂载';
					break;
				case '5':
					str += 's-blue">   未挂载';
					break;
				case '6':
					str += 's-orange"> 创建失败';
					break;
				case '7':
					str += 's-orange"> 挂载失败';
					break;
				case '8':
					str += 's-orange"> 发送失败';
					break;
				case '9':
					str += 's-orange"> 状态异常';
					break;

				default:
					break;
				}
				str += '</span></div><div> 所属业务： ' + ebsList[i].appName + '</div><div>'
						+ (ebsList[i].expireTime ? '到期时间：<SPAN id="expireTime' + ebsList[i].diskId + '">' + ebsList[i].expireTime + '</SPAN>' : '&nbsp;')
						+ '</div>';
				str += '</a></li>';
			}

			$("#resultList").empty();
			$("#listNull li").hide();
			if ((!ebsList)||ebsList.length === 0)
				$("#listNull li").show();
			else
				$("#resultList").html(str);
			
			$(".pageBar").html(data.pageBar);
			$("select[id!='select']").uniform();// 初始化下拉菜单样式

		},
		error : function(data) {
			$.compMsg({
				type : 'error',
				msg : data.msg
			});
		}
	});
}
