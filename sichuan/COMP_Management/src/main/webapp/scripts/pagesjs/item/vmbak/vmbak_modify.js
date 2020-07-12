$(function() {
	//菜单显示当前，开发时删除
	$(".left-menu li:contains('服务条目管理')").addClass("selected").next().show();
	$(".left-menu dl a:contains('虚拟机备份')").parent().addClass("selected");
	$("#head-menu li a:contains('产品管理')").parent().addClass("head-btn-sel");
	//自定义form样式
	$(":text,select,textarea,:checkbox,:radio").uniform();
	$.uniform.update();
	//加载弹出层提示信息栏宽度
	$(".point").attr("style","width:80px");
	$(".point").html("&nbsp;");
	//点击资源规格查询按键事件
	$("#search").click(function (){
		searchStandard();
	});
	//资源规格查询中的是否关联下拉列表查询事件
	$("#queryRecommend").change(function (){
		searchStandard();
	});
	//保存
	$("#saveItem").click(function(){
		saveItem('update');
	});
	//提交
	$("#saveAndSubmitItem").click(function(){
		saveAndSubmitItem('update');
	});
	//取消
	$("#cancel").click(function(){
		backList('','');
	});
	//返回
	$("#goBack").click(function(){
		backList('','');
	});
	initData();
	$("#sellStartTime").val(_fomart14($("#sellStartTime").val()));
	$("#sellEndTime").val(_fomart14($("#sellEndTime").val()));
});
//返回列表页面
function backList(msg,errMsg){
	$.backListMsg({msg:msg,errMsg:errMsg,url:'vmbakItems.action'});
}