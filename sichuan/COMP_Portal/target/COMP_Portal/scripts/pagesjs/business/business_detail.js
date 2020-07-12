$(function() {
    // 菜单显示当前，开发时删除

    $(".left-menu li:contains('业务管理')").addClass("selected").next().show();
    $(".left-menu dl a:contains('业务列表')").parent().addClass("selected");
    $("#head-menu li a:contains('业务管理')").parent().addClass("head-btn-sel");

});

//点击业务删除按钮
var del = function(businessId) {
    $.dialog({
	title : '删除业务',
	content : '确认要删除该业务吗？',
	ok : function() {
	    postDelReq(businessId);
	    return true;
	},
	cancelVal : '关闭',
	cancel : true,
	lock : true
    });
};

//业务删除请求Post
var postDelReq = function(businessId) {
    $.post("businessDel.action",
	    {
		delBusinessId :businessId
	    },
	    function(data) {
		if (data.result.resultFlage == 'success') {
		    alert(data.result.resultMessage);
			backList(data.result.resultMessage, '');
		    } else {
			$.compMsg({
			    type : 'error',
			    msg : data.result.resultMessage
			});
		    }
	    }, "json").fail(function() {
		$.compMsg({
		    type : 'error',
		    msg : '由于异常删除失败！'
	    });
    });
};


var backList = function(msg, errMsg) {
    $.backListMsg({
	msg : msg,
	errMsg : errMsg,
	url : 'businessList.action'
    });
};
