/**
 * 消息提示组件
 * @author wei_sun
 */
jQuery.compMsg = function(options){
	var defaults = {
		msg 	: '',	
		type	: 'info',//success\error\warn\loading	
		parent	: 'body',	
		top  	: "10%",
		left 	: "40%",
		timeout : 5000,//显示时间
		close	: true,//显示关闭
		closeText: 'x',
		closeClassName: 'msgClose',//关闭样式
		infoClassName:'msgInfo',//提示样式
		successClassName:'msgSuccess',//成功样式
		errClassName:'msgError',//错误样式
		warnClassName:'msgWarn',//警告样式
		loadingClassName:'msgLoading',//loading样式
		delay: 0,//延时出现
		callback: function(){}
	}; 
	if(typeof options == 'string') defaults.text = options;
	var opt = jQuery.extend(defaults, options); 
	
	function show(){
	    // 清除之前的弹出消息
		if($(".msgContent") != null && $(".msgContent")[0] != undefined){
			$(".msgContent").hide();
		}
		
		// 创建新的弹出消息
		var container = $('<div class="msgContent" style="left:' + opt.left +';top:' + opt.top + '"></div>');
		$(opt.parent).prepend(container);

		//判断是否大于顶端距离改变显示位置
		if($(window).scrollTop() > 70){
			container.css({position: "fixed",top:"10px"});
		}else{
			container.css({position:"absolute",top:opt.top});
		}
		var msgStyle;
		if (opt.type == 'success') {
			msgStyle = opt.successClassName;
		}else if (opt.type == 'error') {
			msgStyle = opt.errClassName;
		}else if (opt.type == 'warn') {
			msgStyle = opt.warnClassName;
		}else if (opt.type == 'loading') {
			msgStyle = opt.loadingClassName;
			opt.close = false;
		}else {
			msgStyle = opt.infoClassName;
		} 
		
		var msgBox = $('<div class="msgBase '+ msgStyle +'" >'+ opt.msg +'</div>');
		container.prepend(msgBox);
		if (opt.close) {
			$('<span class="' + opt.closeClassName + '">' + opt.closeText + '</span>').click(function(){
				hide(msgBox);
			}).appendTo(msgBox);
		}
		
		msgBox.delay(opt.timeout).fadeOut(function(){
	      hide(msgBox);
	    });
	};
	
	setTimeout(show,opt.delay);
	
	function hide(msgBox){
		msgBox.remove();
		opt.callback();	
	}
}
/**
 * 消息提示代理
 * 实现struts消息显示
 */
function msgAdapter(){
	var msgCon = $('span[class="error"],span[class="success"]');
	msgCon.find("span").each(function(i){
	     if($(this).parents(".success").size() > 0){
	           $.compMsg({type:'success',msg:$(this).text()});
	     }else{
	           $.compMsg({type:'error',msg:$(this).text()});
	     }
	 });
}
	
$(document).ready(function() {
	msgAdapter();
});

/**
 * 获取镜像列表json对象,该请求为同步
 */
var vmImages;//镜像json对象
function getVmImages(){
    $.ajax({
        url: 'imageAction.action',
        type: 'POST',
        cache: false,
        async: false,
        dataType: 'json',
        success: function(data){
            vmImages = data;
        },
        error: function(){
            alert("镜像数据获取错误！");
        }
    });
    return vmImages;
}
/**
 * 通用镜像下拉列表
 * @param {Object} 下拉列表ID
 * @param {Object} viewText
 */
function createVmImgSelect(selectId, viewText){
    var option = '';
    if (vmImages == undefined) {
		getVmImages();
	}
	if (vmImages != undefined) {
		for (var i = vmImages.length - 1; i >= 0; i--) {
			var selected = i == 0 ? ' selected="selected"' : '';
			option += '<option value="' + vmImages[i].osId + '"' + selected + '>' + vmImages[i].osDesc + '</option>';
		};
		$("#" + selectId).html(option);
	}
}
/**
 * form返回列表消息提示组件
 * @author hh
 */
//form跳转列表页面带提示消息
jQuery.backListMsg = function(options){
	var defaults = {
		msg 	: '',	
		errMsg	: '',
		url		: '',
		parent	: 'body',
		delay: 0//延时调用提示方法
	}; 
	if(typeof options == 'string') defaults.text = options;
	var opt = jQuery.extend(defaults, options); 
	//无form对象则创建
	if($("#_BackFormId")[0] == undefined){
		var container = $('<form id="_BackFormId" method="post"><input type="hidden" value="" id="msg" name="msg" />'+
		'<input type="hidden" value="" id="errMsg" name="errMsg"></form>');
		$(opt.parent).prepend(container);
	}else{
		var container =$("#_BackFormId");
	}
	function _submitForm(){
		$("#msg").val(opt.msg);
		$("#errMsg").val(opt.errMsg);
		$("#_BackFormId").attr('action',opt.url);
		$("#_BackFormId").submit();
	};
	setTimeout(_submitForm,opt.delay);
}