$(function() {
    // 菜单显示当前，开发时删除

    $(".left-menu li:contains('业务管理')").addClass("selected").next().show();
    $(".left-menu dl a:contains('业务列表')").parent().addClass("selected");
    $("#head-menu li a:contains('业务管理')").parent().addClass("head-btn-sel");
    // 自定义form样式
    $(":text,textarea,:checkbox,:radio").uniform();

    // 主机资源全选按钮

    $('#checkAllHost').click(function() {
	if ($(this).is(':checked')) {
	    $('#bindHostListTab :checkbox').prop('checked', true);
	} else {
	    $('#bindHostListTab :checkbox').prop('checked', false);
	}
	$("select,:checkbox").uniform();
	$.uniform.update();
    });
    
    
    $('#searchHostButton').click(function(event){
	queryHostListData();
    });

});

var updateBusiness = function() {
    var businessName = $('#_businessName').val();
    if($.trim(businessName)==''){
	var msg="业务名称不能为空！<br>";
	$.compMsg({type:'error',msg:msg});
	return;
    }
    if($.trim(businessName).length>18){
	var msg="业务名称不能超过18个字符！<br>";
	$.compMsg({type:'error',msg:msg});
	return;
    }
    var nameCheck = /^(?!_)(?!.*?_$)[a-zA-Z0-9 _\u4e00-\u9fa5]+$/;
    if(!nameCheck.test(businessName)){
	var msg="业务名称只能是汉字、字母、数字、下划线的组合！<br>切下换线不能在名称的开始和结尾<br>";
	$.compMsg({type:'error',msg:msg});
	return;
    }
    var businessName = $('#_businessName').val();
    var businessDesc = $('#_businessDesc').val();
    var businessId = $('#_businessId').val();
    var hostList =  new Array();
    $("#hostId li").each(function(){
	var hostBindStr = $(this).data('resourceid') + "|||" + $(this).data('type');
	hostList.push(hostBindStr);
    });
    $.ajax({
	url : 'businessUpdate.action',
	type : 'POST',
	dataType : "JSON",
	traditional: true,
	data : 
	{
	    'businessDes' : $.trim(businessDesc),
	    'businessName' : $.trim(businessName),
	    'businessId' : $.trim(businessId),
	    'bindHost' : hostList
	},
	cache : false,
	dataType : 'json',
	success : function(data) {
	    if (data.result.resultFlage == 'success') {
		backList(data.result.resultMessage, '');
	    } else {
		$.compMsg({
		    type : 'error',
		    msg : data.result.resultMessage
		});
	    }
	}
    });
};

// 绑定业务
var bindHost = function() {
    $.dialog({
		title : '绑定主机',
		content : document.getElementById('bindHost'),
		init : queryHostListData(),
		ok : function() {
		    $("[name='hostServer']:checked").each(function(index){
			
			var rid = $(this).data('resourceid');
			var exist = false;
			$("#hostId").find('li').each(function() {
			    if (rid == $(this).data("resourceid")) {
				exist = true;
				return false; //等效于break
			    }
			});
			if (exist) {
			    return;
			}
			
			var resLi = $('<li>'+$(this).data('name')+'<a href="javascript:void(0);" onclick="javascript:unbindHost(this);">删除</a></li>');
			resLi.data('resourceid',$(this).data('resourceid'))
			.data('name',$(this).data('name'))
			.data('type',$(this).data('type'));
			resLi.prependTo($("#hostId"));
		    });
		    
		    return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	    });
};
// 业务绑定删除
function unbindHost(aTag) {
    var line = $(aTag).parent("li");
    $.dialog({
	title : '删除绑定主机',
	content : '请您确认是否删除绑定主机！',
	ok : function() {
	    line.remove();
	    return true;
	},
	cancelVal : '关闭',
	cancel : true,
	lock : true
    });
}

function backList(msg, errMsg) {
    $.backListMsg({
	msg : msg,
	errMsg : errMsg,
	url : 'businessList.action'
    });
}

var queryHostListData = function() {
    $
	    .post(
		    "hostList.action",
		    {
			queryHostName : $('#hostQueryName').val()
		    },
		    function(data) {
			$('#hostListTbody').empty();
			$.each(data.resourceList,
				function(index) {
			    var typeName = "";
			    switch (this.type){
			    case '0':
				typeName = "云主机";
				break;
			    case '1':
				typeName = "物理机";
				break;
			    }
					    var hostCheckBox = $('<td align="center"><input type="checkbox" name="hostServer" value="" /></td>');
					    hostCheckBox.find('[name="hostServer"]:checkbox').data('name', this.name).data('type', this.type).data('resourceid',this.resourceId);
					    $('<tr>')
						    .appendTo('#hostListTbody')
						    .append(hostCheckBox)
						    .append('<td>' + this.name+ '</td>')
						    .append('<td>' + typeName + '</td>');
					});
			$('#hostListPageBarDiv').html(data.pageBar);
			$("select,:checkbox").uniform();
			$.uniform.update();
		    }, "json").fail(function() {
			$.compMsg({
			    type : 'error',
			    msg : '获取主机列表失败！'
			});
	    });

};
