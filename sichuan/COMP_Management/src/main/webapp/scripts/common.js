/**
 * 消息提示组件
 * @author wei_sun
 */
jQuery.compMsg = function(options){
	var defaults = {
		msg 	: '',	
		type	: 'info',//success\error\warn\loading	
		parent	: 'body',	
		top  	: "134px",
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
	//-------------------------临时方案-------------------
	//为了解决权限变化时，一级菜单的href要变为其下用户有权使用的第一个子菜单的链接
	$.each( $('.left-menu > li[class!="menu-child"]'), function(i, menu){
		var menuHref = $(menu).next('li[class="menu-child"]').children("dl").first().find("a").attr("href");
		if (menuHref != undefined){
			$(menu).find("a").first().attr("href",menuHref);
		}
	});
	
	//-------------------------临时方案-------------------
	//为了解决一级菜单显示默认项的全县问题
	if (requestPar("t") == "1"){
		var url=$('.left-menu').find("a").first().attr("href");
		window.location.href=url;
	}else{
		msgAdapter();
		$('#main').show();
	}
	
	
	
});

//解析URL中的参数
var requestPar = function(paras){ 
	var url = location.href;  
	var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");  
	var paraObj = {};
	for (var i=0; j=paraString[i]; i++){  
		paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length);  
	}  
	var returnValue = paraObj[paras.toLowerCase()];  
	if(typeof(returnValue)=="undefined"){  
		return "";  
	}else{  
		return returnValue;  
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

/**
 * 日志格式化yyyy-MM-dd hh:ss:mm
 * @author hh
 */
function _fomart14(date){
	var dateStr = '';
	if(date.length==14){
		dateStr = date.substring(0,4)+'-'+date.substring(4,6);
		dateStr += '-'+date.substring(6,8)+' '+date.substring(8,10);
		dateStr += ':'+date.substring(10,12)+':'+date.substring(12,14);
	}
	return dateStr;
}
/**
 * 日志格式化yyyy-MM-dd
 * @author hh
 */
function _fomart8(date){
	var dateStr = '';
	if(date.length>=8){
		dateStr = date.substring(0,4)+'-'+date.substring(4,6);
		dateStr += '-'+date.substring(6,8);
	}
	return dateStr;
}
//日期类型转换数字
function _dateToStr(date){
	var _dateStr = date.replace(/-/g,'');
	_dateStr = _dateStr.replace(/ /g,'');
	_dateStr = _dateStr.replace(/:/g,'');
	return _dateStr;
}

/**
 * 字符串截断
 */
function csubstr(str,len){
    if (str.length>len) {
        return str.substring(0,len)+"...";
    } else {
        return str;
    }
}