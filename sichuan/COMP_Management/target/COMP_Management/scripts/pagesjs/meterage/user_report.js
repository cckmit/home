var hosturl;
$(function() {
	// 菜单显示当前，开发时删除
	$(".left-menu li:contains('租户计量报告')").addClass("selected").next().show();
	$(".left-menu dl a:contains('物理机计量报告')").parent().addClass("selected");
    $("#head-menu li a:contains('计量管理')").parent().addClass("head-btn-sel");
	// 自定义form样式
	$(":radio,:text,:checkbox,select").uniform();

	// 加载弹出层提示信息栏宽度
	$(".point").attr("style", "width:80px");
	$(".point").html("&nbsp;");
	$(function(){
		if(top.location.href.toLowerCase() == self.location.href.toLowerCase()) $('#docLink').show();
		$("#tabNav ul").idTabs("tab0"); 
	});
	
	//默认加载
	onLoadReport();
	
	$('#meterage').click(function() {
		onLoadReport();
	});
	$('#searchUser').click(function(){
		searchUser();
	});
});
function getDateParam(param){
	var encryptParam="";
	var params = {
			params : param
	};
	$.ajax({
		type : "POST",
		url : "getDate.action",
		data : params,
		dataType : "JSON",
		cache : false,
		async : false,
		success : function(data) {
			encryptParam = data.encryptParam;
			hosturl = data.hostUrl;
		}
	});
	return encryptParam;
}
function onLoadReport(){
	var userid = $('#userid').val();
	var year = $('#year').val();
	var time ="";
	var month=$('#month').val();
	if("00"==year){
		time = '';
	} else if("00"==month){
		time = year+'%25';
	} else {
		time = year+month+'%25';
	}
//	all 867f7d33-8218-4ce1-9f4e-d855ef3c2457
	var param = "reportId=867f7d33-8218-4ce1-9f4e-d855ef3c2457" +
	"&userid=" + userid +
	"&time=" + time;
	var encryptParam=getDateParam(param);
	var url = hosturl+"/Report/Report-AuthAction.do?encryptParam=" +encryptParam;
	document.getElementById('allFrame').src=url;
	
//	pm
	var param = "reportId=3cf8af7e-828a-4364-bdc3-198e8caea5f5" +
	"&userid=" + userid +
	"&time=" + time;
	var encryptParam=getDateParam(param);
	var url = hosturl+"/Report/Report-AuthAction.do?encryptParam=" +encryptParam;
	document.getElementById('pmFrame').src=url;
//	vm
	param = "reportId=0b54550b-4d4e-4c79-8589-a60ba76f4d54" +
	"&userid=" + userid +
	"&time=" + time;
	encryptParam=getDateParam(param);
	url = hosturl+"/Report/Report-AuthAction.do?encryptParam=" +encryptParam;
	document.getElementById('vmFrame').src=url;
//	ebs
	param = "reportId=eba5ec3b-1724-4439-9e73-fae32c291ecd" +
	"&userid=" + userid +
	"&time=" + time;
	encryptParam=getDateParam(param);
	url = hosturl+"/Report/Report-AuthAction.do?encryptParam=" +encryptParam;
	document.getElementById('ebsFrame').src=url;
//	vmbk
	/*param = "reportId=930ce360-9624-4e2b-8b01-6644290fa275" +
	"&userid=" + userid +
	"&time=" + time;
	encryptParam=getDateParam(param);
	url = hosturl+"/Report/Report-AuthAction.do?encryptParam=" +encryptParam;
	document.getElementById('vmbkFrame').src=url;*/
}

function searchUser() {
	window.open ("../searchUser.action", "newwindow", "height=500, width=1000, toolbar =no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no");
	//alert(window.opener.document.getElementById("test"));
}