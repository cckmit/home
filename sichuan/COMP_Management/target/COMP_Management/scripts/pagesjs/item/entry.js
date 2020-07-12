// JavaScript Document
//  条目推荐
function onItemCommend(obj,itemId){
	var status = $(obj).text();
	var newStatus = $(obj).text()=='推荐'?'取消推荐':'推荐';
	var urls = 'itemCommend.action';
	if(status == '取消推荐'){
		urls = 'itemCloseCommend.action';
	}
	//var jsonStr = '[\''+itemId+'\']';
	$.dialog({
		title: status+'条目',
		content: '请您确认是否'+status+'条目？',
		ok: function () {
			var da_val = {'itemId':itemId};
			$.ajax( {
				url : urls,
				type : 'POST',
				data : da_val,
				cache : false,
				dataType : 'json',
				success : function(data) {
					if(data!=null){
						if(data.resultFlage=='success'){
							$(obj).text(newStatus);
							$.compMsg({type:'success',msg:status+'成功！'}); 
						}else if(data.resultFlage=='failure'){
							$.compMsg({type:'error',msg:status+'失败！'});
							initData();
						}else if(data.resultFlage=='error'){
							$.compMsg({type:'error',msg:'数据库操作异常！'}); 
						}
					}
					initData();
				}
			});
		},
		cancelVal: '关闭',
		cancel: true,
		lock: true
	});
}
//条目删除
function onItemDel(obj,itemId){
	var jsonStr = '[\''+itemId+'\']';
	$.dialog( {
		title : '删除条目',
		content : '请您确认是否删除条目？删除后将无法恢复！',
		ok : function() {
			var da_val = {'itemId':jsonStr};
			$.ajax( {
				url : 'itemDel.action',
				type : 'POST',
				data : da_val,
				cache : false,
				dataType : 'json',
				success : function(data) {
					if(data!=null){
						if(data.resultFlage=='success'){
							$(obj).parents("tr").remove();
							$.compMsg({type:'success',msg:'删除成功！'}); 
						}else if(data.resultFlage=='failure'){
							$.compMsg({type:'error',msg:'删除失败！'}); 
							initData();
						}else if(data.resultFlage=='error'){
							$.compMsg({type:'error',msg:'数据库操作异常！'}); 
						}
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
//条目提交 
function onItemSubmit(obj,itemId){
	var jsonStr = '[\''+itemId+'\']';
	$.dialog( {
		title : '提交条目',
		content : '请您确认是否提交条目？',
		ok : function() {
			var da_val = {'itemId':jsonStr};
			$.ajax( {
				url : 'itemSubmit.action',
				type : 'POST',
				data : da_val,
				cache : false,
				dataType : 'json',
				success : function(data) {
					if(data!=null){
						if(data.resultFlage=='success'){
							$(obj).parents('tr').find("td").eq(2).html(data.resultMessage);
							$(obj).parents('td').find("a").filter(":contains('修改'),:contains('提交')").hide();
							$.compMsg({type:'success',msg:'提交成功！'}); 
						}else if(data.resultFlage=='failure'){
							$.compMsg({type:'error',msg:'提交失败！'}); 
						}else if(data.resultFlage=='error'){
							$.compMsg({type:'error',msg:'数据库操作异常！'}); 
						}
					}
					initData();
				}
			});
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}
//条目发布
function onItemSend(obj,itemId){
	var jsonStr = '[\''+itemId+'\']';
	$.dialog( {
		title : '发布条目',
		content : '请您确认是否发布条目？',
		ok : function() {
			var da_val = {'itemId':jsonStr};
			$.ajax( {
				url : 'itemSend.action',
				type : 'POST',
				data : da_val,
				cache : false,
				dataType : 'json',
				success : function(data) {
					if(data!=null){
						if(data.resultFlage=='success'){
							$(obj).parents('tr').find("td").eq(2).html(data.resultMessage);
							$(obj).parents('td').find("a").filter(":contains('修改'),:contains('发布')").hide();
							$.compMsg({type:'success',msg:'提交发布成功！'});
						}else if(data.resultFlage=='failure'){
							$.compMsg({type:'error',msg:'提交发布失败！'});
						}else if(data.resultFlage=='error'){
							$.compMsg({type:'error',msg:'数据库操作异常！'}); 
						}
					}
					initData();
				}
			});
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}
//条目下架
function onItemDown(obj,itemId){
	var jsonStr = '[\''+itemId+'\']';
	$.dialog({
		title : '条目下架',
		content : '请您确认是否条目下架？下架后将无法恢复！',
		ok : function() {
			var da_val = {'itemId':jsonStr};
			$.ajax( {
				url : 'itemDown.action',
				type : 'POST',
				data : da_val,
				cache : false,
				dataType : 'json',
				success : function(data) {
					if(data!=null){
						if(data.resultFlage=='success'){
							$(obj).parents('tr').find("td").eq(2).html(data.resultMessage);
							$(obj).parents('td').find("a").filter(":contains('推荐'),:contains('下架'),:contains('取消推荐')").hide();
							$.compMsg({type:'success',msg:'下架成功！'}); 
						}else if(data.resultFlage=='failure'){
							$.compMsg({type:'error',msg:'下架失败！'});
						}else if(data.resultFlage=='error'){
							$.compMsg({type:'error',msg:'数据库操作异常！'}); 
						}
					}
					initData();
				}
			});
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}
//关联资费信息
function onRelateExpenses(){
	$.dialog({
		title: '关联资费信息',
		content: document.getElementById('relate_expenses_div'),
		init:function(){
			$("#price_type").val("");
			$("#expenses_price").val("");
			$.uniform.update();
		},
		button:[
        {
            name: '确定',
            callback: function () {
				if($("#approval-relate-expenses").find("tr").length>=1){
					var flage = true;
					$("#approval-relate-expenses").find("tr").each(function(index, element) {
						if($(element).find("td").eq(0).text()==$("#price_type").val()){
							flage = false;
							$.dialog({
								title: '无法关联此计费方式',
								content: '您的资费列表中已有此计费方式！',
								cancelVal: '关闭',
								cancel: true,
								lock: true
							});
							return false;
						}
                    });
					if(flage){
						var val = $("[name='wkls']:checked").parents("tr").find("td").eq(1).html();
						var html = '<tr>'+
								'<td>'+$("#price_type").val()+'</td>'+
								'<td>'+$("#expenses_price").val()+'</td>'+
								'<td>'+val+'</td>'+
								'<td class="table-opt-block">'+
								'	<a href="javascript:void(0);" onclick="onExpensesModify(this)">修改</a>'+
								'   <a href="javascript:void(0);" onclick="onDel(this)">删除</a>'+
								'</td>'+
							  '</tr>';
						$("#approval-relate-expenses").find("table").append(html);
					}
				}
			},
			focus: true
		}
		],
		cancelVal: '取消',
		cancel:true,
		lock: true
	});
}

//修改资费信息
function onExpensesModify(obj){
	$.dialog({
		title: '修改资费信息',
		content: document.getElementById('relate_expenses_div'),
		init:function(){
			$("#price_type").val($(obj).parents("tr").find("td").eq(0).html());
			$("#expenses_price").val($(obj).parents("tr").find("td").eq(1).html());
			$.uniform.update();
		},
		button:[
        {
            name: '确定',
            callback: function () {
				var flage = true;
				$("#approval-relate-expenses").find("tr").each(function(index, element) {
					if($(element).find("td").eq(0).text()==$("#price_type").val()&&index!=$(obj).parents("tr").index()){
						flage = false;
						$.dialog({
							title: '无法关联此计费方式',
							content: '您的资费列表中已有此计费方式！',
							cancelVal: '关闭',
							cancel: true,
							lock: true
						});
						return false;
					}
				});
				if(flage){
					$(obj).parents("tr").find("td").eq(0).html($("#price_type").val());
					$(obj).parents("tr").find("td").eq(1).html($("#expenses_price").val());
					var val = $("[name='wkls']:checked").parents("tr").find("td").eq(1).html();
					$(obj).parents("tr").find("td").eq(2).html(val);
				}
			},
			focus: true
		}
		],
		cancelVal: '取消',
		cancel:true,
		lock: true
	});
}

//tab切换
function done(obj){
	$(".title-btn li").removeClass("select");
	$(obj).parents('li').addClass("select");
	$("#approval-will").hide();
	$("#approval-done").show();
	var select = '<option value="">-请选择-</option>';
       select += '<option value="1">推荐</option>';
       select += '<option value="0">取消推荐</option>';
       select += '<option value="2">下架</option>';
	$("#status").empty();
	$("#status").append(select);
    $.uniform.update();
	initData();
}
function will(obj){
	$(".title-btn li").removeClass("select");
	$(obj).parents('li').addClass("select");
	$("#approval-done").hide();
	$("#approval-will").show();
	var select = '<option value="">-请选择-</option>';
       select += '<option value="0">已保存</option>';
       select += '<option value="1">条目待审</option>';
       select += '<option value="2">审批通过</option>';
       select += '<option value="3">审批不通过</option>';
       select += '<option value="4">发布待审</option>';
       select += '<option value="6">发布不通过</option>';
	$("#status").empty();
	$("#status").append(select);
    $.uniform.update();
	initData();
}