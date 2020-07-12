// JavaScript Document
$(function() {
	//左侧菜单
	$(".left-menu li").click(function(){
		//判断当前是否是子菜单
		if(!$(this).hasClass("menu-child")){
			var $mc = $(this).next(".menu-child");
			//判断当前是否包含子菜单
			if($mc.html()!=undefined){
				$mc.show().siblings(".menu-child").hide();
				//var url =$mc.children().first().find("a").attr("href");
				//$("#right").load("../vm/vm_detail.html #right");
			}else{
				$(".left-menu .menu-child").css("display","none");
				$(".left-menu .menu-child").css("display","none");
			}
			$(".left-menu li").removeClass("selected");
			$(".left-menu li").removeClass("selected");
			$(this).addClass("selected");
		}
	});
	/*子级菜单操作*/
	$(".left-menu dl").click(function(){
		$(this).siblings().removeClass("selected");
		$(this).addClass("selected");
		//点击响应
	})
	
	/* 头菜单操作 */
	/*
	$("#head-menu li").click(function(){
		$("#head-menu li").removeClass("head-btn-sel");
		$(this).addClass("head-btn-sel");
	});
	*/
	//列表tab响应过滤
	$(".status-tab a").click(function(){
		$(this).parent().siblings().removeClass("selected");
		$(this).parent().addClass("selected");
		//取a标签rel属性表示作为过滤样式显示条件
		var show = $(this).attr("rel");
		if(show == "" || show == undefined){
			$(".resource-list li").show();
			$("#listNull li").hide();
		}else if(show == '0'){
			$(".resource-list li").hide();
			$("#listNull li").show();
		}else{
			var flage = true;
			$(".resource-list li").each(function() {
            	var t=$(this).find("."+show).size();
				if(t > 0){
					$(this).show();
					flage = false;
				}else{
					$(this).hide();
				}
			});
			if(flage){
				$("#listNull li").show();
			}
		}
	})
	
});

//创建安全策略
function bindSP(){
	$.dialog.open('bind_sp.html',{
		title:'新建安全组',
		close: function(){
			var spName = $.dialog.data('spName');
			if (spName !== undefined){
				$("#sp_list").append("<option>"+spName+"</option>");
			}
		},
		width:830,
		height:420,
		lock: true
	},false);
}