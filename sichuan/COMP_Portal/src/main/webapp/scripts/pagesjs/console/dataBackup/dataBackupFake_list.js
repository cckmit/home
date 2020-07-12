$(function() {

	$("#backup").siblings().removeClass("active");
	$("#backup").addClass("active");

	$(".status-tab a").unbind('click');
});


function jumpTO(num) {
	
	window.location.href='dataBackupDetail.action?optState='+num;
}