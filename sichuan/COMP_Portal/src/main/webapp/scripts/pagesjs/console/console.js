//分区下无可用资源遮罩
function mask(container){
	if ($(".mask").length > 0){
		removeMask(container);
	}
	if ($(".mask").length == 0) {
		container.css("position","relative");
		var $mask = '<div class="mask"></div>';
		container.prepend($mask);
		$.compMsg({type:'warn',timeout:50000,top:170,close:false,msg:'该资源池分区下暂无可用资源，请选择其他资源池分区！'});
	}
}
//资源池下无分区遮罩
function maskNotPart(container){
	if ($(".mask").length > 0){
		removeMask(container);
	}
	if ($(".mask").length == 0) {
		container.css("position","relative");
		var $mask = '<div class="mask"></div>';
		container.prepend($mask);
		$.compMsg({type:'warn',timeout:50000,top:170,close:false,msg:'该资源池下暂无分区，请选择其他资源池！'});
	}
}

function removeMask(container){
	if ($(".mask").length > 0) {
		container.find(".mask").remove()
		$('.msgBase').remove();
	}
}