;"use strict";
(function(window, document, $, undefined) {
	function LoadForm(ctxPath) {
		this.ctxPath = ctxPath;
	}
	LoadForm.prototype.loadForm = function(key, selecter, event) {
		var self = this;
		$.ajax({
		    url : this.ctxPath + "/form/" + key + ".htm", dataType : "JSON", type : "GET", data : {}, headers : {
			    "returntype" : "ajax/json"
		    }, traditional : true, success : function(data) {
			    var html = data.html;
			    $(selecter).append($(html).html());
			    var obj = data.jsAndCss;
			    var resources = new Array();
			    // 写入js和css
			    for (var i = 0; i < obj.length; i++) {
			    		resources.push(self.ctxPath + "/" + obj[i]);
			    }
			    console.log(resources);
			    seajs.use(resources,function(){
			    		event && event();
			    });
		    }
		});
	};
	$.fn.extend({
		loadForm : function(ctxPath, key, event) {
			new LoadForm(ctxPath).loadForm(key, this, event);
		}
	});
	$(document).on("click",".lcs_switch",function() {
		$(this).toggleClass("lcs_off").toggleClass("lcs_on");
	});
})(window, document, jQuery);