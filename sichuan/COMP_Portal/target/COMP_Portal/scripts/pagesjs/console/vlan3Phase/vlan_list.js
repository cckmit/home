$(function() {
	$("#vlan3").siblings().removeClass("active");
	$("#vlan3").addClass("active");
	queryStatusLoading();
	vmNameChange();
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
function goToPage(diskId) {
	if (diskId == '') {

	} else {
		$("#diskId").val(diskId);
		$("#gotoForm").attr('action', 'diskDetail.action');
		$("#gotoForm").submit();
	}

}
/**
 * 云硬盘名称字体大小
 */
function vmNameChange() {
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

function deleteVlan(caseId) {
	$
			.dialog({
				title : '删除Vlan',
				content : '请您确认是否删除该Vlan？删除后将无法恢复！',
				ok : function() {
					$
							.blockUI({
								message : '<img src="../images/loading.gif" align="absmiddle" style="margin-right:20px">'
										+ '<span id="load_span">正在删除…</span>',
								css : {
									width : '150px',
									left : '45%'
								}
							});
					window.location = "vlanCancel3PhaseAction.action?caseId="
							+ caseId;
				},
				cancelVal : '取消',
				cancel : true,
				lock : true
			});
}

function EditVlan(vlanName,caseId,orderId,startId,endId) {
	$("#edit_vlanName").val(vlanName);
	$("#edit_startId").val(startId);
	$("#edit_endId").val(endId);
	/* $("#edit_vlanName").val(vlanInfo.vlanName); */
	$.dialog({
		title : '修改vlan',
		content : document.getElementById('vlanEditDialog'),
		button : [ {
			name : '修改',
			callback : function() {
				this.button({
					name : '修改',
					disabled : true
				});
				var vlanName = $("#edit_vlanName").val();
				var startId = $("#edit_startId").val();
				var endId = $("#edit_endId").val();
				if (vlanName.length > 50 || vlanName == "") {
					$.compMsg({
						type : 'error',
						msg : 'Vlan名称不能为空！'
					});
					this.button({
						name : '修改',
						disabled : false
					});
					return false;
				}
				// 起始Vlan_Id范围
				if (parseInt(startId) < 1 || parseInt(startId) > 4094) {
					$.compMsg({
						type : 'error',
						msg : '起始Vlan_ID填写范围为1～4094，请重新输入！'
					});
					this.button({
						name : '修改',
						disabled : false
					});
					return false;
				}
				if(startId == ""){
					$.compMsg({
						type : 'error',
						msg : 'Vlan起始ID不能为空！'
					});
					this.button({
						name : '修改',
						disabled : false
					});
					return false;					
				}
				// 终止Vlan_Id范围
				if (parseInt(endId) < 1 || parseInt(endId) > 4094) {
					$.compMsg({
						type : 'error',
						msg : '终止Vlan_ID填写范围为1～4094，请重新输入！'
					});
					this.button({
						name : '修改',
						disabled : false
					});
					return false;
				}
				if(endId == ""){
					$.compMsg({
						type : 'error',
						msg : 'Vlan终止ID不能为空！'
					});
					this.button({
						name : '修改',
						disabled : false
					});
					return false;					
				}

				$.ajax({
					type : "POST",
					url : "vlanNameEdit3Phase.action",
					traditional : true,
					data : {
						caseId : caseId,
						vlanName : vlanName,
						orderId : orderId,
						startId : startId,
						endId : endId,
					},
					success : function(data) {
						if (data.resultPath == "success") {
							var msg = "VLAN修改已提交，请等待审核！";
							$.compMsg({
								type : 'success',
								msg : msg,
								callback:function(){
									window.location.reload();
								}
							})
						} 
						else if(data.resultPath == "error"){
							var errMsg = data.errMsg;
							backList("", errMsg);
						}else if (data == "error") {
							$.compMsg({
								type : 'error',
								msg : data.mes
							})
						}
					}
				});
				this.button({
					name : '修改',
					disabled : false
				});
			},
			focus : true
		} ],
		cancelVal : '关闭',
		cancel : true,
		lock : true

	});
}
/**
 * 返回列表页面
 */
function backList(msg, errMsg) {
	$.backListMsg({
		msg : msg,
		errMsg : errMsg,
		url : 'vlanQueryList3PhaseAction.action'
	});
}