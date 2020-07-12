//删除资源规格
function onDelStandard(obj){
	$("#standardId").val('');
	$(obj).parent().parent().remove();
}
//表格头
var tables ='';
tables += '<col style="width:10%;" />';
tables += '<col style="width:45%;" />';
tables += '<col style="width:45%;" />';
tables += '<tr>';
tables += '<th class="nl">&nbsp;</th>';
tables += '<th>规格名称</th>';
tables += '<th>空间大小(GB)</th>';
tables += '</tr>';
//资源规格查询
function searchStandard(){
	var queryDiscSize = $.trim($("#queryDiscSize").val());
	var queryStandardName = $.trim($("#queryStandardName").val());
	var queryRecommend = $.trim($("#queryRecommend").val());
	var da_val = {"spaceSize":queryDiscSize,"standardName":queryStandardName,"useStatus":queryRecommend};
    $.ajax({
        type: "POST",
        url: 'vmBakStandardQuery.action',
        data: da_val,
        dataType: "JSON",
        cache: false,
        success: function(data){
			if (data == null) {
				$.compMsg({type:'error',msg:'数据库操作异常！'}); 
				return false;
			}
			var errors = standardFieldErrors(data.fieldErrors);
			if(errors!=''){
				$.compMsg({type:'error',msg:errors});
				return false;
			}
			if(!jQuery.isEmptyObject(data)){
				$("#standardlist").empty();
				 var str = tables;
				 var flage = true;
				for(var i = 0;i<data.length;i++){
					str+='<tr>';
                  	str+='<td><input type="radio" value="'+data[i].standardId+'" name="ls"/></td>';
                    str+='<td>'+data[i].standardName+'</td>';
                    str+='<td>'+data[i].spaceSize+'</td>';
                    str+='</tr>';
					flage = false;
				}
				if(flage){
					str+='<tr><td colspan="3" align="center">暂无符合数据！</td></tr>';
				}
				$("#standardlist").append(str);
			}else{
				$("#standardlist").empty();
				var str = tables;
				str+='<tr><td colspan="3" align="center">暂无符合数据！</td></tr>';
				$("#standardlist").append(str);
			}
        }
    });
}
//资源规格查询错误信息
function standardFieldErrors(errors){
	var mes = '';
	if (!jQuery.isEmptyObject(errors)) {
		if (!jQuery.isEmptyObject(errors.standardName)) {
			mes += errors.standardName + '\n';
		}
		if (!jQuery.isEmptyObject(errors.discSize)) {
			mes += errors.discSize + '\n';
		}
		if (!jQuery.isEmptyObject(errors.standardId)) {
			mes += errors.standardId + '\n';
		}
	}
	return mes;
}
//关联资源规格弹出层
function onRelateResource(){
	$.dialog({
		title: '关联资源规格',
		content: document.getElementById('relate_resource_div'),
		init:function(){
			searchStandard();
		},
		button:[
        {
            name: '确定',
            callback: function () {
				if($("#approval-relate-resource").find("tr").length==1){
					if ($("[name='ls']").is(":checked")) {
						var standardId = $("[name='ls']:checked").val();
						$("#standardId").val(standardId);
						var html = '<tr>' +
						'<td>' +
						$("[name='ls']:checked").parents("tr").find("td").eq(1).html() +
						'</td>' +
						'<td>' +
						$("[name='ls']:checked").parents("tr").find("td").eq(2).html() +
						'</td>' +
						'<td class="table-opt-block">' +
						'   <a href="javascript:void(0);" onclick="onDelStandard(this)">删除</a>' +
						'</td>' +
						'</tr>';
						$("#approval-relate-resource").find("table").append(html);
					}
				}else{
					var standardId = $("[name='ls']:checked").val();
					$("#standardId").val(standardId);
					var val = $("[name='ls']:checked").parents("tr").find("td").eq(1).html();
					$("#approval-relate-resource").find("tr").eq(1).find("td").eq(0).html(val);
					val = $("[name='ls']:checked").parents("tr").find("td").eq(2).html();
					$("#approval-relate-resource").find("tr").eq(1).find("td").eq(1).html(val);
				}
				$("[name='ls']:checked").val();
			},
			focus: true
		}
		],
		cancelVal: '取消',
		cancel:true,
		lock: true
	});
}
function initData(){
	var standardId = $("#standardId").val();
	var da_val = {"standardId":standardId};
    $.ajax({
        type: "POST",
        url: 'vmbakStandardDetail.action',
        data: da_val,
        dataType: "JSON",
        cache: false,
        success: function(data){
			if (data == null) {
				backList('','加载条目关联规格数据库异常！');
				return false;
			}
			var errors = standardFieldErrors(data.fieldErrors);
			if(errors!=''){
				$.compMsg({type:'error',msg:errors});
				return false;
			}
			if(!jQuery.isEmptyObject(data)){
				$("#standardlist").empty();
				 if(data.length==1){
				 	var html = '<tr>' +
					'<td>' +data[0].standardName +
					'</td>' +
					'<td>' + data[0].spaceSize +
					'</td>' +
					'<td class="table-opt-block">' +
					'   <a href="javascript:void(0);" onclick="onDelStandard(this)">删除</a>' +
					'</td>' +
					'</tr>';
					$("#approval-relate-resource").find("table").append(html);
				 }else{
					backList('','加载条目关联规格数据库异常！');
				 }
			}else{
				backList('','加载条目关联规格数据库异常！');
			}
        }
    });
}
