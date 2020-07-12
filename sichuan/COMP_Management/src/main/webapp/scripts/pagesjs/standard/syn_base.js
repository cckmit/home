function onDel(standardId,url) {
	$.dialog({
		title: '删除规格',
		content: '请您确认是否删除规格'+standardId+'？删除后将无法恢复！',
		ok: function () {
		var da_val = {
				standardId : standardId
			};
			$
					.ajax( {
						type : "POST",
						url : url,//'PMStandardDelete.action',
						data : da_val,
						dataType : "JSON",
						cache : false,
						success : function(data) {
							if (data.resultPath == "error") {
								var errorInfo = "编号为" + standardId + "规格删除失败";
								backList('', errorInfo);
								return;
							}
							if (data.resultPath == "inuse") {
								var errorInfo = "编号为" + standardId + "规格正在使用，不允许删除";
								backList('', errorInfo);
								return;
							}
							if (data.resultPath == "intermediate") {
								var errorInfo = "编号为" + standardId + "规格存在中间状态，不允许删除";
								backList('',errorInfo);
								return;
							}
							if (data.resultPath == "deleted") {
								var errorInfo = "编号为" + standardId + "的规格已经被删除";
								backList('', errorInfo);
								return;
							}if (data.resultPath == "success") {
								var errorInfo = "编号为" + standardId + "的规格删除成功！";
								backList(errorInfo, '');
								return;
							}
							if (data.resultPath == "enabled") {
								var errorInfo = "编号为"
										+ standardId
										+ "的规格资源池状态为可用,无法删除";
								backList('', errorInfo);
								return;
							}
								backList('删除成功', '');
						}
					});
			return true;
		},
		cancelVal: '关闭',
		cancel: true,
		lock: true
	});
	
}
//同步资源池
function onSynchroBase(obj,standardId,initUrl,synUrl){
	$.dialog({
		title: '资源池同步',
		content: document.getElementById('synchro_div'),
		init:function (){
			$("#synchro_table").html('');
			$("#syn_standardId").html('');
			$("#syn_standardName").html('');
			vmStandardResList(standardId,initUrl);
		},
		button:[
        {
            name: '同步',
            callback: function () {
				//调用同步方法
				return onSynchroAjax(standardId,synUrl);
            },
            focus: true
        }],
		cancelVal: '取消',
		cancel:true,
		lock: true
	});
}
//初始化列表资源池
function vmStandardResList(standardId,url){
	var da_val = {'standardId':standardId};
	mask($("#synchro_div"));
	$.ajax({
		type : "POST",
		url : url,//'PMStandardResList.action',
		data : da_val,
		dataType : "JSON",
		cache : false,
		async:false,
		success: function(data){
			if (data != null) {
				removeMask($("#synchro_div"));
				var mapData = data["resultResInfos"];
				var table = '<table>';
				var single = true;
				for (var key in mapData) {
					if (single) {
						var syn_standardId = mapData[key][0].standardId;
						var syn_standardName = mapData[key][0].standardName;
						$("#syn_standardId").html(syn_standardId);
						$("#syn_standardName").html(syn_standardName);
						single = false;
					}
					var flage = false;
					var resStatusText = '';
					if (mapData[key][0].resStatus == '1') {
						flage = true;
						resStatusText = '服务正常';
					}
					else 
						if (mapData[key][0].resStatus == '0') {
							resStatusText = '服务已删除';
						}
						else 
							if (mapData[key][0].resStatus == '2') {
								resStatusText = '服务暂停';
							}
							else 
								if (mapData[key][0].resStatus == '3') {
									resStatusText = '服务终止';
								}
					table += '<tr><td><div class="authBlock zoone">';
					table += '<dt>';
					table += '<span class="up"></span>';
					if (flage) {
						table += '<label><input type="checkbox" name="host" />' + mapData[key][0].resPoolName + '</label>';
					}
					else {
						table += '<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + mapData[key][0].resPoolName + '</label>';
					}
					table += '<label>' + resStatusText + '</label>';
					table += '</dt><dd>';
					for (var i = 0; i < mapData[key].length; i++) {
						var resPoolPartName = mapData[key][i].resPoolPartName;
						var resPoolPartNameText = resPoolPartName;
						if (resPoolPartName.length > 5) {
							resPoolPartNameText = resPoolPartName.substring(0, 5) + '...';
						}
						var lsFlage = false;
						var resPoolPartStatus = mapData[key][i].status;
						var resPoolPartStatusText = '';
						if (resPoolPartStatus == '0') {
							resPoolPartStatusText = '同步发送成功';
						}
						else 
							if (resPoolPartStatus == '1') {
								resPoolPartStatusText = '同步发送失败';
								lsFlage = true;
							}
							else 
								if (resPoolPartStatus == '2') {
									resPoolPartStatusText = '可用';
								}
								else 
									if (resPoolPartStatus == '3') {
										resPoolPartStatusText = '不可用';
										lsFlage = true;
									}else 
									if (resPoolPartStatus == '5') {
										resPoolPartStatusText = '等待再同步';
										lsFlage = true;
									}
									else {
										resPoolPartStatusText = '未同步';
										lsFlage = true;
									}
						table += '<li>';
						var poolId = mapData[key][i].resPoolId;
						var poolPartId = mapData[key][i].resPoolPartId;
						if (flage && lsFlage) {
							table += '<label title="' + resPoolPartName + '">';
							table += '<input type="checkbox" name="poolPartId" poolId="' + poolId + '" poolPartId="' + poolPartId + '"/>';
							table += resPoolPartNameText + '</label>';
						}
						else {
							table += '<label title="分区01">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + mapData[key][i].resPoolPartName + '</label>';
						}
						table += '<label>' + resStatusText + '</label>';
						table += '<label>' + resPoolPartStatusText + '</label>';
						table += '</li>';
					}
					table += '</dd></div></td></tr>'
				}
				table + '</table>';
				$("#synchro_table").html(table);
				//样式
				$(":checkbox").uniform();
				$.uniform.update();
				//层次树
				$(".authBlock .up").toggle(function(){
					$(this).addClass("down").parent().next().show();
				}, function(){
					$(this).removeClass("down").attr("class", "up").parent().next().hide();
				});
				// 二级功能
				$(".authBlock dt :checkbox").click(function(){
					var childCheckbox = $(this).parents('dt').next("dd").find('input:checkbox');
					if ($(this).attr("checked") == "checked") {
						childCheckbox.attr("checked", "checked");
						$.uniform.update();
					}
					else {
						childCheckbox.removeAttr("checked");
						$.uniform.update();
					}
				});
			}else{
				$.compMsg( {
					type: 'error',
					msg: '资源池信息加载失败！'
				});
				removeMask($("#synchro_div"));
			}
		}
	});
}
function onSynchroAjax(standardId,url){
	var jsonStr = '';
	var flage = true;
	$("[name='poolPartId']").each(function (){
		if($(this).attr('checked')){
			var poolPartId = $(this).attr('poolPartId');
			var poolId = $(this).attr('poolId');
			if(flage){
				jsonStr = '[{"resourcePoolPart":"'+poolPartId+'","resourcePoolId":"'+poolId+'"}';
				flage = false;
			}else{
				jsonStr +=',{"resourcePoolPart":"'+poolPartId+'","resourcePoolId":"'+poolId+'"}';
			}
		}
	});
	if(flage){
		$.compMsg( {
			type: 'error',
			msg: '请选择要同步的资源池！'
		});
		return false;
	}
	jsonStr +=']';
	var da_val = {'jsonStr':jsonStr,'standardId':standardId};
	mask($("#synchro_div"));
	$.ajax({
		type : "POST",
		url : url,//'PMSynchroResPool.action',
		data : da_val,
		dataType : "JSON",
		cache : false,
		async:false,
		success: function(data){
			removeMask($("#synchro_div"));
			if(data.results==null){
				$.compMsg( {type : 'error',msg : '全部发送同步失败！'});
			}else{
				var ls = data.results;
				if(ls.length==0){
					$.compMsg( {type : 'success',msg : '全部发送同步成功！'});
				}else{
					for(var i=0;i<ls.length;i++){
						$.compMsg( {type : 'error',msg : '向资源池'+ls[i].resourcePool.resourcePoolId+'下的资源池分区'+ls[i].resourcePool.resourcePoolPart+'发送同步失败！'});
					}
				}
			}
		}
	});
}

//遮罩loading
function mask(container){
	if ($(".mask").length == 0) {
		container.css("position","relative");
		var width = container.width()/2-10;
		var height = container.height()/2-10;
		var $mask = '<div class="mask"><img style="margin:'+height+'px '+width+'px;" src="../images/loading.gif"/></div>';
		container.prepend($mask);
	}
}
//去除遮罩loading
function removeMask(container){
	if ($(".mask").length > 0) {
		container.find(".mask").remove()
	}
}