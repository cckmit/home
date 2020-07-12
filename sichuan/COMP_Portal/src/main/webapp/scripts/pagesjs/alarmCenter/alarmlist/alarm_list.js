$( function() {
	//菜单显示当前，开发时删除
	$("#alarmAction").siblings().removeClass("active");
	$("#alarmAction").addClass("active");
});
function cutStringFun(){
	//列表长字段截取
    var $box = $('.cut');
	$box.each( function(index, jdom) {
		var objString = $(jdom).text();
		var byteValLen = 0;
		var returnText = "";
		var maxTextLength = 0;
		if($(jdom).attr("class") == 'cut pool'){
			maxTextLength = 7*2;
		}else if($(jdom).attr("class") == 'cut app'){
			maxTextLength = 13*2;
		}
		for (var i = 0; i < objString.length; i++) {
	        (objString[i].match(/[^x00-xff]/ig) != null) ? (byteValLen += 2) : (byteValLen += 1);
	        returnText += objString[i];
	        if (byteValLen > maxTextLength) {
	        	$(jdom).attr('title',objString);
				objString = $(jdom).text(returnText.substring(0,i-1) + '...');
	            break;
	        }
	    }
	});
}
//显示隐藏规格详情
function isShow(obj){
	if($("#"+obj).is(":hidden")){
		$("#"+obj).show();
	}else{
		$("#"+obj).hide();
	}
}

/**
 * 格式化数据展示方式
 * 将相同的父订单号合并
 */
function fomateDate(){
	var ls=null;
	var n =1;
	var obj=null;
	$("#ordertable tr").each(function(i){
		if(i!=0){
			var parentId = $(this).find('td').eq(0).text();
			if(parentId == ls){
				n=n+1;
				$(this).find('td').eq(0).remove();
			}else{
				if(obj!=undefined&&obj!=null){
					$(obj).find('td').eq(0).attr("rowspan",n);
				}
				ls = parentId;
				obj = this;
				n=1;
			}
		}
	});
	if(obj!=undefined&&obj!=null){
		$(obj).find('td').eq(0).attr("rowspan",n);
	}
}
//翻页调用js
function getPageData(url) {
	queryAlarm(url);
}
var table ='';
table += '<thead>';
table += '<th>告警IP</th>';
table += '<th>告警时间</th>';
table += '<th>告警内容</th>';
table += '</thead>';

function queryAlarm(url) {
	var vmip = "";
	if ($('#vmIp').val() != "") {
		vmip = $('#vmIp').val();
	}
	var da_val;
	if (url == '' || url == undefined) {
		da_val = {
			vmIp : vmip
		};
		url = 'alarmActionJson.action';
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
					if (data.alarmsInfos == null) {
						$.compMsg( {
							type : 'success',
							msg : "暂无符合条件的告警数据！"
						});
						$("#alarmtable").empty();
						$("#alarmtable").append(table);
						return;
					}
					var list = data.alarmsInfos.list;
					var page = data.alarmsInfos.page;
					if(list.length==0){
						$.compMsg( {
							type : 'success',
							msg : "暂无符合条件的告警数据！"
						});
						$("#alarmtable").empty();
						$("#alarmtable").append(table);
						$(".pageBar").html(page);
						return;
					}
					if (!jQuery.isEmptyObject(data.alarmsInfos.list)) {
						var str = table;
						str+='<tbody>';
						for ( var i = 0; i < list.length; i++) {
							str += '<tr class="iterator">';
							str += '<td class="cut app">' + list[i].rid + '</td>';
							str += '<td class="cut app">' + list[i].eventTime + '</td>';
							str += '<td class="cut app">' + list[i].text + '</td>';
							str += '</td></tr>';
						}
						str+='</tbody>';
						$("#alarmtable").empty();
						$("#alarmtable").append(str);
						$(".pageBar").html(page);
						//fomateDate();
						//cutStringFun();
				}
			}
			
			});
}