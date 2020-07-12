/**
 * @author hh
 */
$(function() {
	//菜单显示当前，开发时删除
	$(".left-menu li:contains('云硬盘 ')").addClass("selected").next().show();
	$(".left-menu dl a:contains('云硬盘推荐')").parent().addClass("selected");
	
	//详细信息tab
	$('#price_tab').MookoTabs({
		eventName:'click'
	});	
	//绑定浮动菜单
	$("#left").floatBlock();
	//自定义form样式
	$("select").uniform();
	$(".itemName").each(function(){
		var txt = $(this).html();
		if(txt.length>8){
			$(this).attr('title',txt);
			$(this).html(txt.substring(0,8)+'...');
		}
	});
})
function ebs_apply_post(val){
	$("#ebsApplyFrom").append("<input type='text' name='itemId' value='" + val + "'/>");
	$("#ebsApplyFrom").submit();
}
