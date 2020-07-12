/**
 * @author wei_sun
 */
$(function() {
	//菜单显示当前，开发时删除
	$(".left-menu li:contains('云主机 ')").addClass("selected").next().show();
	$(".left-menu dl a:contains('云主机推荐')").parent().addClass("selected");
	
	$(".apply_arrow").toggle(
	  function () {
		$(this).next().addClass("show-select-type");
	  },
	  function () {
		$(this).next().removeClass("show-select-type");
	  }
	);
	//详细信息tab
	$('#price_tab').MookoTabs({
		eventName:'click'
	});	
	$("#os").change(function (){
		$(".os").text($(this).find("option:selected").text());
	});
	
	//绑定浮动菜单
	$("#left").floatBlock();
	
	//获取镜像
	createVmImgSelect('os', 'osDesc');
	$(".os").text($(this).find("option:selected").text());
	//推荐
	if (vmImages == undefined) {
        getVmImages();
    }
	if (vmImages != undefined) {
		var txt = '';
		for (var i = vmImages.length - 1; i >= 0; i--) {
			var txtSplit = i == 0 ? '' : ', ';
			if(txt==''){
				txt += vmImages[i].os + txtSplit
			}else if(txt.indexOf( vmImages[i].os+txtSplit)<0){
				txt += vmImages[i].os + txtSplit
			}
		};
		if(txt.substring(txt.length-2, txt.length)==', '){
			txt = txt.substring(0, txt.length-2);
		}
		$(".imgTxt").html(txt);
	}
	//自定义form样式
	$("select").uniform();
	
})
function vm_apply_post(val,sign){
	$("#vmApplyFrom").append("<input type='text' name='itemId' value='" + val + "'/>");
	if('pm'==sign){
		$("#vmApplyFrom").attr('action','../console/pmApplyAction.action');
	}else if('vm'==sign){
		$("#vmApplyFrom").attr('action','../console/vmApplyAction.action');
	}
	$("#vmApplyFrom").submit();
}
