/**
 *
 */
(function($){
    $.fn.MookoTabs = function(options){
        var opts = $.extend({}, $.fn.MookoTabs.defaults, options);
        return this.each(function(){
            var target = $(this);
            var div = target.find('div[id$=_tab_info]'); //id结尾为_tab_info的div
            var tabs = target.find('li'); //tabs
            var flag = false;
            now = new Date().valueOf();
            function Tabs(){
                if (flag) { //第一次必须执行
                    if (new Date().valueOf() - now < 300) { //阻止快速事件
                        return false;
                    }
                }
                else {
                    select(div, $(this), opts.firstOn);
                    flag = true;
                    return false;
                }
                
                if ($(this).hasClass(opts.className)) { //如果当前被击中就终止
                    return false;
                }
                select(div, $(this));
                now = new Date().valueOf();
                return false; //防止url出现#
            }
            
            function select(div, li, index){
                div.hide();
                if (typeof(index) == "number") {
                    ajax(div, li);
                    $(div[index]).fadeIn(opts.fadeIn);
                    tabs.removeClass(opts.className);
                    //target.find("a").removeClass(opts.className);//add by sunwei
                    $(tabs[index]).addClass(opts.className);
                    //$(tabs[index]).find("a").addClass(opts.className);//add by sunwei
                }
                else {
                    var tab = div.filter(li.find("a").attr("href"));
                    ajax(div, li);
                    tab.fadeIn(opts.fadeIn);
                    tabs.removeClass(opts.className);
                    //target.find("a").removeClass(opts.className);//add by sunwei
                    li.addClass(opts.className);
                    //li.find("a").addClass(opts.className);//add by sunwei
                }
            }
            
            function ajax(div, li){
                var href = li.find("a").attr("href");
                var rel = li.find("a").attr("rel"); //ajax请求url
                var i = div.filter(href); //当前div
                if (rel) { //如果ajax请求url不为空
                    i.html(opts.loadName);
                    $.ajax({
                        url: rel,
                        cache: false,
                        success: function(html){
							//判断如果返回中带有登录form则返回失败
                            if ($(html).filter('#loginForm').length > 0) {
                                i.html('登录超时,请刷新页面!');
                            }
                            else if(opts.setId != ''){//add by sunwei just for second,you can del it
								$("#" + opts.setId).html(html);
                            }
                            else if(opts.getCon != ''){//add by sunwei for filter Content
                            	i.html($(html).filter(opts.getCon));
                            	i.find("#innerDiv").css("overflow","hidden");
                            	//i.html($(html).filter(opts.getCon).find("#innerDiv").css("overflow","hidden"));
                            }
							else{
								i.html(html);
							}
                        },
                        error: function(){
                            i.html('获取数据失败');
                        }
                    });
                    //li.find("a").removeAttr("rel");  //第二次不再ajax
                }
            }
            if (opts.autoFade) {
                var index = opts.firstOn + 1;
                setInterval(function(){
                    if (index >= div.length) {
                        index = 0;
                    }
                    select(div, $(this), index++);
                }, opts.autoFadeTime * 1000);
            }
            //绑定并触发事件
            tabs.bind(opts.eventName == 'all' ? 'click mouseover' : opts.eventName, Tabs).filter(':first').trigger(opts.eventName == 'all' ? 'click' : opts.eventName);
        });
    };
    $.fn.MookoTabs.defaults = {
        firstOn: 0,
        className: 'selected',//被选中时候li的样式
        eventName: 'all', //可以为click mouserover all
        loadName: '<img src="./themes/blue/images/home/loading.gif">loading...', //ajax等待字符串
        //fadeIn: 'slow',
		fadeIn: 0,
        autoFade: false,
        autoFadeTime: 3,
		setId: '',//显示内容ID,IE7找不到第二层tab的临时解决方法
		getCon: ''//获取的内容过滤显示
    };
})(jQuery);
