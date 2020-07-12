(function($) {
	$.floatBlock = {
		options: {
		  headHeight:107,
		  contentWidth:1000
		}
	  };
	  
	$.fn.floatBlock = function(options) {
		options = $.extend($.floatBlock.options, options); 
		var position = function(element) {
			var top = element.position().top, pos = element.css("position");
			var topheight = top + options.headHeight;
			var left = $('#main').css('margin-left');
			$(window).scroll(function() {
				var scrolls = $(this).scrollTop();
				if (scrolls > topheight) {
					if (window.XMLHttpRequest) {
						element.css({
							position: "fixed",
							top: 0,
							left: left
						});	
					} else {
						element.css({
							top: scrolls,
							left:left
						});	
					}
				}else {
					element.css({
						position: pos,
						top: top,
						left: 0
					});	
				}
			});
	};
		return $(this).each(function() {
			position($(this));						 
		});
	
	};
})(jQuery);