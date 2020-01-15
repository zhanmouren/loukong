;(function(window,document,$,undefined){
	function DropdownMenu(selector){
		this.selector = selector;
		this.init();
	};
	DropdownMenu.prototype.init = function(){
		var self = this;
		this.selector.on("click","ul>li",function(){
			$(this).toggleClass("active").siblings().removeClass("active");
			return false;
		});
	};
	$.fn.extend({dropdownMenu:function(){
		new DropdownMenu(this);
	}});
})(window,document,jQuery);