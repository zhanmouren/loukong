var jumpNum = ""; //获取区域管理 sz dma 地图区域编码
var _objectType; //传给后台参数/地图服务类型 FSZ
//获取地图界面参数
var tipsTime = null;
// 表格高度
var tableHeight = document.body.clientHeight - 20 - $('.layui-form').height(); // table内容高度

var tipEle;
var tipStartTime;
$(document).ready(function () {
	/**
	 * 兼容ie placeholder
	 */
	if (!('placeholder' in document.createElement('input'))) {
		$('input[placeholder],textarea[placeholder]').each(function () {
			var that = $(this),
				text = that.attr('placeholder');
			if (that.val() === "") {
				that.val(text).addClass('placeholder');
			}
			that.focus(function () {
				if (that.val() === text) {
					that.val("").removeClass('placeholder');
				}
			}).blur(function () {
				if (that.val() === "") {
					that.val(text).addClass('placeholder');
				}
			}).closest('form').submit(function () {
				if (that.val() === text) {
					that.val('');
				}
			});
		});
	}
	//	设置跟节点字体大小
//	var setFont = function () {
//		var docEl = document.documentElement
//		var clientWidth = docEl.clientWidth;
//		docEl.style.fontSize = 20 * (clientWidth / 1920) + 'px';
//	}
//	setFont()
	var screenWidth = window.screen.width
	if (screenWidth >= '1920') { //不同屏幕不同宽度-搜索条件
		$(".search-box .layui-form-item .layui-inline").width("340px")
	} else if (screenWidth >= '1440' && screenWidth<='1536') {
		$(".search-box .layui-form-item .layui-inline").width("310px")
	} else if (screenWidth == '1360' || screenWidth == '1366' || screenWidth == '1280') {
		$(".search-box .layui-form-item .layui-inline").width("280px")
		$(".cascade").css("padding", "0 0 0 2px");//1360
	}


	// table tips
	$(document).on('mouseenter', '.table-tips,.table-tips1', function () {
//		if(tipEle != this ){
//				layer.close(layer.tips())
//				clearTimeout(tipsTime) 
//		}else if(new Date().getTime() - tipStartTime < 2000){
//			return
//		}
		var that = this;
		var text;
		tipsTime = setTimeout(function () {

			if($(that).attr("stringKey") != null && $(that).attr("stringKey") != ""){
				text = getString($(that).attr("stringKey"))
			}else if($(that).attr("data-msg")!= null && $(that).attr("data-msg") != ""){
				text = that.dataset.msg
			}else{
				return
			}

			layer.tips(text, that, {
				tips: [1, '#3595CC'],
				time: 0
			});
			//tipEle = that
			//tipStartTime = new Date().getTime()
		}, 1000);

	}).on('mouseleave', '.table-tips', function () {
		console.log("m mouseleave")
		layer.close(layer.tips())
		clearTimeout(tipsTime)
	})
	//禁止后退键 作用于Firefox、Opera
	document.onkeypress = doKey;
	//禁止后退键  作用于IE、Chrome
	document.onkeydown = doKey;

	//回车键触发搜索,搜索按钮需用id="search-btn"
	$(document).keydown(function(event) {
		var hasFocus = $(".layui-laypage-skip .layui-input").is(':focus'); //true为分页,false为搜索
		if(hasFocus == false){
			//回车键
			if(event.keyCode==13){
				if(typeof(fkeydown) == 'function'){
					fkeydown();
				}else{
					$("#search-btn").click();
				}
				return false;
			}
		}

	})

	// 搜索中有区间选择的输入框前值大于后值
	let borderC = '1px solid #e6e6e6';
	// $(".startRange,.endRange").attr("oninput", "value=value.replace(/[^-\\d]*/g,\"\")");
	$(".startRange").each(function (i, v) {
		$(this).blur(function () {
			var SR = $(this).val();
			var ER = $(this).siblings("input").val();
			if (ER != '') { if (Number(SR) > Number(ER)) { $(this).val(''); $(this).css('border', '1px solid red'); } }
		})
		$(this).focus(function () { $(this).css('border', borderC); $(this).siblings("input").css('border', borderC) })
	})
	$(".endRange").each(function (i, v) {
		$(this).blur(function () {
			var SR = $(this).val();
			var ER = $(this).siblings("input").val();
			if (SR != '') { if (Number(SR) < Number(ER)) { $(this).val(''); $(this).css('border', '1px solid red'); } }
		})
		$(this).focus(function () { $(this).css('border', borderC); $(this).siblings("input").css('border', borderC) })
	})
	//iframe 调整为 可拖动
	var frameId = window.frameElement && window.frameElement.id || '';

	if(frameId){
		var _iframe = $("#"+frameId,window.parent.document)
		if( $(_iframe).attr("name") != "ifd" && $("iframe").attr("name") == "mapAndTableMonitoring"
			|| $("iframe").attr("name") == "inms-gis"){
			$(_iframe).attr("scrolling","no")
		}else{//除去首页及blank页
			$(_iframe).attr("scrolling","auto")
		}
		$("body").css("overflow-y","auto");
		if(isIE()){//判断在IE浏览器
			$("html").css("overflow-y","auto");

			if($("iframe").attr("name") == "inms-gis"
				|| $("iframe").attr("name") == "mapAndTableMonitoring"){//是否是地图页面
				$("html").css("overflow-y","visible")
			}else{
				$("html").css("overflow-y","auto");
			}
		}
	}

	// 去掉chrome input输入记录
	$('form').attr('autocomplete','off')
})
function isIE() { //ie?
	if (!!window.ActiveXObject || "ActiveXObject" in window)
	{ return true; }
	else
	{ return false; }
}
/**
 * ajax异常
 * @param {*} data
 */
var ajaxError = function (data) {
	var flag = typeof (layer) != 'undefined'
	// if (data.status == 0) {
	// 	flag ? layer.msg(getString("message_network_error"), {
	// 		icon: 2
	// 	}) : alert(getString("message_network_error"))
	// } else if (data.status == 500) {
	// 	flag ? layer.msg(getString("message_server_error"), {
	// 		icon: 2
	// 	}) : alert(getString("message_server_error"))
	// } else if (data.status == 415 || data.status == 406) {
	// 	flag ? layer.msg(getString("message_param_error"), {
	// 		icon: 2
	// 	}) : alert(getString("message_param_error"))
	// } else {
	// 	flag ? layer.msg(getString("message_error"), {
	// 		icon: 2
	// 	}) : alert(getString("message_error"))
	// }
}

/**
 * ajax调用
 *
 * @param url
 *            链接地址
 * @param data
 *            数据
 * @param success
 *            请求成功回调函数
 * @param error
 *            请求失败回调函数
 * @param method
 *            调用方法，默认为post
 * @param isJson
 * 		   是否发送json格式
 * @param dataType
 *            返回参数类型,默认为json
 * @param async
 *            同步/异步类型
 */
function request({
					 url,
					 data,
					 success,
					 error,
					 method,
					 dataType,
					 isJson,
					 async
				 }) {
	if (method == null) {
		// 默认post方式
		method = "post";
	}
	if (dataType == null) {
		// 默认json数据类型
		dataType = "json";
	}
	if (async == null) {
		// 默认同步
		async = true
	}
	if (error == null) {
		// 默认输出错误日志
		error = ajaxError
	}
	var defaultOpt = {
		url: url,
		dataType: dataType,
		type: method,
		data: data,
		async: async,
		contentType: isJson ? 'application/json; charset=UTF-8' : 'application/x-www-form-urlencoded; charset=UTF-8',
		traditional: true,
		timeout:20000,
		success: function (data) {
			if (data.code >= 10000 && data.code < 11000) {
				// 未登录,则返回登录界面
				if (data.code == 10000) {
					top.location.href = top.location.href.replace('layout/index.html', 'login/index.html')
					return;
				}
			}
			/* if(data.data){
				data.data = nullFormat(data.data)
			} */
			if (success)
				success(data);
		},
		error: error
	};
	$.ajax(defaultOpt);
}



/**
 *var q = {a:'a',b:null,c:{d:null,e:1},f:[{g:null,h:1.1},{i:'z',j:1}]}
 var w = [{a:null,b:'b'},{a:1,b:null}]
 console.log(nullFormat(q))
 console.log(nullFormat(w))
 *
 * @param {*} object
 * @returns
 */
function nullFormat(object){
	if (Object.prototype.toString.call(object) === "[object Array]") {
		for (var i = 0; i < object.length; i++) {
			object[i] = nullFormat(object[i])
		}
	}else if(Object.prototype.toString.call(object)==='[object Object]'){
		var keys = Object.keys(object)
		for (var y = 0; y < keys.length; y++) {
			object[keys[y]] = nullFormat(object[keys[y]])
		}
	}else{
		if(object == null) object = '-'
	}
	return object
}

/**
 * 全选反选
 *
 * @param event
 */
function checkAll(event) {
	if (event.checked) {
		$("input[name='" + $(event).attr("id") + "']").attr("checked",
			"checked")
	} else {
		$("input[name='" + $(event).attr("id") + "']").removeAttr("checked");
	}
}
/**
 * 获得checkbox所有值 逗号分隔
 *
 * @param name
 * @returns {String}
 */
function getCheckedVal(name) {
	var val = "";
	$("input[name='" + name + "']").each(function () {
		if (this.checked) {
			val += this.value + ",";
		}
	});
	if (val.length > 0) {
		val = val.substring(0, val.length - 1);
	}
	return val;
}
/**
 * 获得checkbox所有值 数组
 *
 * @param name
 * @returns {Array}
 */
function getCheckedArray(name) {
	var val = new Array();
	var i = 0;
	$("input[name='" + name + "']:checked").each(function () {
		val[i++] = this.value;
	});
	return val;
}
/**
 * 辅助单位发生change时执行的函数
 *
 * @param inp
 */
function _assist_function(inp) {
	var name = $(inp).attr("for");
	var target = $("[name='" + name + "']");
	var fixed = target.fixed || 2;
	target
		.val((new Number(target.val()) *
			new Number(target.attr("_measure")) / new Number(
				inp.value)).toFixed(fixed));
	target.val(new Number(target.val()));
	target.attr("_measure", inp.value);
}

//获取url地址后面的参数
function getUrlField(field) {
	return getFieldByUrl(window.location.search, field);
}

function getFieldByUrl(url, field) {
	var reg = new RegExp("(^|&|\\?)" + field + "=([^&]*)(&|$)", "i");
	var r = url.substr(1).match(reg);
	if (r != null)
		return decodeURI(r[2]);
	return "";
};

function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) return unescape(r[2]);
	return null;
}




//******************************************************************
// return code
//******************************************************************
/**
 * 接口返回值 0:成功
 */
var return_code_success = 0;
var RETURN_CODE_SUCCESS = 0;



/**
 * 同步返回数据
 *  @param url 后台请求数据
 *  @param param 接口参数
 */
function getSyncData(url, param) {
	var dataInfo = null;
	request({
		url: baseUrl + url,
		method: "post",
		isJson: true,
		async: false,
		data: JSON.stringify(param),
		success: function (data) {
			dataInfo = data;
		}
	})
	return dataInfo
};
/**
 * 展开右侧地图
 */
function showMapContent() {

	$(".app-center", window.parent.document).addClass("contentWidth")
	$('#app-right', window.parent.document).removeClass('right-hide');
	//formBtnWidth()
	if (!$("#mapIframe", window.parent.document).attr("src")) {
		var src = window.location.href;
		var index = src.indexOf('views')

		src = src.substr(0, index)
		var urlIndex = src + 'inwlms-map/src/index.html'
		console.log(urlIndex)
		$("#mapIframe", window.parent.document).attr("src", urlIndex)
	}



};
/**
 * 隐藏右侧地图
 */
function hideMapContent() {
	// $('#app-right', window.parent.document).addClass('right-hide');
};

function locationMapReady(callback) {
	if ($("#mapIframe", window.parent.document)[0] &&
		$("#mapIframe", window.parent.document)[0].contentWindow &&
		$("#mapIframe", window.parent.document)[0].contentWindow.operation &&
		$("#mapIframe", window.parent.document)[0].contentWindow.operation.isInit
	) {
		if (callback) callback()
	} else {
		setTimeout(function () {
			locationMapReady(callback)
		}, 100)
	}
}

//搜索条件（有左中右情况时）的样式
function formBtnWidth() {
	var appCenter = $(".app-center", parent.document).width()
	if (appCenter < 831 && appCenter > 671) { //1920 - 1600 1920app-center=830 
		$("#search-form").addClass("width85")
		$(".search-box .search-btn").addClass("widthAuto")
		$(".search-box .layui-form-item .layui-inline").width("320")
	} else if (appCenter < 671 && appCenter > 591) { // 1600 - 1440 1600app-center=670px
		$("#search-form").addClass("width83")
		$(".search-box .search-btn").addClass("widthAuto")
		$(".search-box .layui-form-item .layui-inline").addClass("width260")
	} else if (appCenter < 591 && appCenter > 553) { //1440-1366 1440app-center=590px
		$("#search-form").addClass("width79")
		$(".search-box .search-btn").addClass("widthAuto")
		$(".search-box .layui-form-item .layui-inline").addClass("width220")
	} else if (appCenter < 554 && appCenter > 510) { //1366-1280 1366app-center=510px

		if (window.parent._treeNodeId == 'vd(c)') {
			$("#search-form").addClass("width69")
		} else {
			$("#search-form").addClass("width79")
		}
		$(".search-box .search-btn").addClass("widthAuto")
	} else if (appCenter < 511) { //1280以下的
		$("#search-form").addClass("width78")
		$(".search-box .search-btn").addClass("widthAuto")
	}
};
/**
 * 搜索条件容器的高度变化
 */
function changeTelescopicPanel() {
	/* //重新设置表格高度
	var changHight = $("#search-form .layui-form-item").height() - 40; //变化高度
	var tabHight = $(".layui-table-view").height();
	var layuiTableBox = $(".layui-table-box").height();
	var layuiTableFixed = $(".layui-table-fixed").height();
	var layuiTableBody = $(".layui-table-body").height();
	var layuiTableBodyTable = $(".layui-table-body table").height();
	var scrollBarHight = 10; //表格内滚动条bar高度
	var isIE = navigator.userAgent.toLowerCase().indexOf('msie') != -1;//判断当前浏览器是不是IE浏览器
	if(isIE){
		scrollBarHight = 15;//IE浏览器
	}else{
		scrollBarHight = 10;//其他浏览器
	}
	if ($("#search-form").hasClass("change-searchfrom")) {
		layuiTableBox = layuiTableBox - changHight;
		tabHight = tabHight - changHight;
		if(layuiTableBodyTable > layuiTableBody){
			layuiTableFixed = layuiTableFixed - changHight;
		}
		layuiTableBody = layuiTableBody - changHight;
	} else {
		layuiTableBox = layuiTableBox + changHight;
		tabHight = tabHight + changHight;
		layuiTableBody = layuiTableBody + changHight;
		if(layuiTableBodyTable > layuiTableBody){
			layuiTableFixed = layuiTableFixed + changHight;
		}
	}
	$(".layui-table-view").height(tabHight);
	$(".layui-table-box").height(layuiTableBox);
	$(".layui-table-fixed").height(layuiTableFixed);
	$(".layui-table-body").height(layuiTableBody);
	$(".layui-table-fixed .layui-table-body").height(layuiTableBody - scrollBarHight);
	//伸缩面板
	$("#search-form").toggleClass('change-searchfrom');
	$("#push-btn span").toggleClass('iconup');  */

	//table_resize()
	/* var frameId = window.frameElement && window.frameElement.id || '';
	
	
	if($("#search-form").hasClass('change-searchfrom')){
		$("#"+frameId,window.parent.document).attr("scrolling","auto")
		$(".telescopic-panel").closest("body").css("overflow-y","auto")
		$(".telescopic-panel").closest("html").css("overflow-y","auto")
	}else{
		$("#"+frameId,window.parent.document).attr("scrolling","no")
		$(".telescopic-panel").closest("body").css("overflow-y","hidden")
		$(".telescopic-panel").closest("html").css("overflow-y","hidden")
		
	} */
	$("#search-form").toggleClass('change-searchfrom');
	$("#push-btn span").toggleClass('iconup');
};
/**
 * 搜索条件搜索与展开,改变table表格的高度
 */
function changeHight() {
	var searchFormHight = $('.layui-form').height() + 11; //搜索条件高度
	var layuiFormHight = $("#search-form .layui-form-item").height(); //搜索条件实际高度
	var telescopicPanelHeight = 0; //伸缩按钮高度
	if (layuiFormHight < 45) { //搜索条件只有一行时，不显示搜索面板伸缩按钮
		$(".telescopic-panel").hide();
	} else {
		telescopicPanelHeight = $(".telescopic-panel").height();
		$(".telescopic-panel").show();
	}
	var tableHeight = document.body.clientHeight - searchFormHight - telescopicPanelHeight; // 表格高度
	return tableHeight
};
/**
 * 模糊搜索DmaNo/SzNo
 * @param param 参数
 * @param url 接口
 */
function getDmaNoAndSzNoList(param, url) {
	request({
		url: baseUrl + url,
		method: "post",
		isJson: true,
		async: false,
		data: JSON.stringify(param),
		success: function (data) {
			var dataInfo = data.data;
			for (var i = 0; i < dataInfo.length; i++) {
				$("#area-list").append('<dd class="area-list-dd" value="' + dataInfo[i] + '">' + dataInfo[i] + '</dd>');
			}
		}
	})
};



/**
 * 获取url后面参数需要转译的特殊字符
 * @param name 需要转译的
 * %20 空格
 * %26 &
 * %2C ,
 */
function unescapeHTML(name) {
	name = "" + name;
	return name.replace(/%20/g, " ").replace(/%26/g, "\&").replace(/%2C/g, "\,")
}

/**
 * 根据region code取得subRegion
 * @param {*} code
 */
function getSubRegionByRegionCode(code) {
	/*  if (!code) {
	   return getDicVal('SUB_REGION')
	 } else {
	   return regionConfig[code] || []
	 } */

	if (!code) {
		return []
	} else {
		var subRegion = dictionary.get('SUB_REGION')
		return subRegion.filter(function (item) {
			var v = item.code.substring(0, item.code.indexOf("("))
			return v == code;
		})
	}
}
/**
 * 获取当前数据字典名称
 */
function getCurrDictName () {
	var currDictName
	switch (language.current()) {
		case language.languages.en:
			currDictName = 'enName'
			break;
		case language.languages.hans:
			currDictName = 'scName'
			break;
		case language.languages.hant:
			currDictName = 'tcName'
			break;
	}
	return currDictName || 'enName'
}
/**
 *
 * 根据父code和子code获取数据字典
 * @param {*} parentCode 父code
 * @param {*} childCode 子code (string或者array)
 * @returns(若子childCode为string,则返回单个数据字典对象,若childCode为array,则返回数据字典数组)
 */
function getDictByCode (parentCode, childCode) {
	if (typeof(childCode) == 'string') {
		var result = dictionary.get({
			parentCode: parentCode,
			code: childCode
		})
		return result[0] || null
	} else if (typeof(childCode) == 'object') {
		return childCode.reduce((prev,curr) => {
			var item = dictionary.get({
				parentCode: parentCode,
				code: curr
			})
			return [...prev, ...(item[0] ? [item[0]] : [])]
		},[])
	}
	return null
}
/**
 *
 *根据父code和子code获取字典名称，未找到返回childCode本身
 * @param {*} parentCode
 * @param {*} childCode
 * @param {*} boolean 设置中英文转换
 * @returns
 */
function getDicNameByCode(parentCode, childCode,boolean) {
	var name = childCode;
	var result = dictionary.get({
		parentCode: parentCode,
		code: childCode
	});
	if (result.length > 0) {
		if(boolean){
			var tempName = "<span class='languageChange' enname='"+result[0].enName+"' "+" scname='"+result[0].scName+"' "+" tcname='"+result[0].tcName+"'>";
			switch (language.current()) {
				case language.languages.en:
					tempName += result[0].enName
					break;
				case language.languages.hans:
					tempName += result[0].scName
					break;
				case language.languages.hant:
					tempName += result[0].tcName
					break;
			}
			tempName += "</span>";
			name = tempName;
		}else{
			switch (language.current()) {
				case language.languages.en:
					name = result[0].enName
					break;
				case language.languages.hans:
					name = result[0].scName
					break;
				case language.languages.hant:
					name = result[0].tcName
					break;
			}
		}
	}

	return name;
}
/**
 *
 * @param {ele} ele是当前input的id
 * @param {href} href 模糊查询对应的接口
 */
var fuzzyDl = null; //用来保存dl元素
var inputID = "";//模糊查询的input元素
function bindFuzzyData(ele, href, params) {
	var inputName = null;//获取input的name属性，传给后台的参数
	$(ele).bind('input propertychange', function () {
		inputID = ele;
		inputName = $(ele).attr('name');
		var eleID = (ele.split("#"))[1];
		fuzzyDl = $(ele).siblings(".area-list").attr("id", eleID + inputName + "Fuzzy");
		var value = $(ele).val();//input模糊查询的值
		fuzzyDl.empty(); //置空
		var idDl = fuzzyDl.attr("id");
		var newParams = {};
		if (params == null) {
			var regionSelect = $("select[data-type='REGION']");//查找region 元素
			var regionVal = $(ele).parents(".layui-inline").siblings().find(regionSelect).val();//获取region值
			var fuzzyParams = {
				"region": regionSelect != null ? regionVal : "",
			}
			fuzzyParams[inputName] = value;
		} else {
			for (var i in params) {
				newParams[i] = $(params[i]).val()
			}
		}

		getFuzzyList(params != null ? newParams : fuzzyParams, href, "#" + idDl); //模糊搜索No
		var isHasChild = fuzzyDl.children(".area-list-dd");
		if (isHasChild.length > 0) {
			fuzzyDl.show();
		} else {
			fuzzyDl.hide();
		}
	});
}
$(".layui-form-item").delegate(".area-list-dd", "click", function () {
	var n = $(this).index();
	var name = $(fuzzyDl).find(".area-list-dd").eq(n).text();
	$(inputID).val(name);//显示选中的结果
	$(fuzzyDl).hide();
});
$(document).click(function () {
	$(fuzzyDl).hide();
});
/**
 *
 * @param {param} param 不同接口的参数
 * @param {url} url 模糊查询接口路径
 * @param {ele} ele 下拉框的id
 */
function getFuzzyList(param, url, ele) {
	request({
		url: baseUrl + url,
		method: "post",
		isJson: true,
		async: false,
		data: JSON.stringify(param),
		success: function (data) {
			var dataInfo = data.data;
			if(data.code == 0){
				for (var i = 0; i < dataInfo.length; i++) {
					$(ele).append('<dd class="area-list-dd" value="' + dataInfo[i] + '">' + dataInfo[i] + '</dd>');
				}
			}
			else{
				return ;
			}
		}
	})
};

/**
 * 根据指标编码获取指标显示值
 * @param code
 * @returns
 */
function getIndicatorNameByCode(code) {
	var name = code;
	var result = commonIndicatorInfo.getIndicator(code);
	if (result.length > 0) {
		switch (language.current()) {
			case language.languages.en:
				name = result[0].itemNameEn;
				break;
			case language.languages.hans:
				name = result[0].itemName;
				break;
			case language.languages.hant:
				name = result[0].itemNameFt;
				break;
		}
	}
	return name;
};
/**
 * 验证数据 是数字：返回true；不是数字：返回false
 console.log('ABC123'+isNumber('ABC123'))
 console.log('123ABC'+isNumber('123ABC'))
 console.log('123,123'+isNumber('123,123'))
 console.log('123'+isNumber('123'))
 console.log('='+isNumber('='))
 console.log('=123'+isNumber('=123'))
 console.log('500000.23232323'+isNumber('500000.23232323'))
 **/

function isNumber(val) {
	// isNaN()函数 把空串 空格 以及NUll 按照0来处理 所以先去除，

	if (val === "" || val == null) {
		return false;
	}
	if (!isNaN(val)) {
		return true;
	} else {
		return false;
	}
}


/*禁止回退键*/
function doKey(e) {
	var ev = e || window.event;
	var obj = ev.target || ev.srcElement;
	var t = obj.type || obj.getAttribute('type');
	if (ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea" && t != "number") {
		return false;
	}
}

/**
 * 字符串佔位符
 *
 var str1 = "hello {0}".format("world"); //log   hello world
 console.log(str1)
 var str2 = "我叫{0},性别{1}".format("美男子", "男"); //log 我叫美男子,性别男
 console.log(str2)
 var user = {name: "美男子",sex: "男",age: 20};
 var str3 = "我叫{name},性别{sex},今年{age}岁".format(user); //我叫美男子,性别男,今年20岁
 console.log(str3)
 */
String.prototype.format = function () {
	if (arguments.length == 0) return this;
	var param = arguments[0];
	var s = this;
	if (typeof (param) == 'object') {
		for (var key in param)
			s = s.replace(new RegExp("\\{" + key + "\\}", "g"), param[key]);
		return s;
	} else {
		for (var i = 0; i < arguments.length; i++)
			s = s.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
		return s;
	}
}


function loadJs(url) {
	var result = createElement({
		element: 'script',
		type: 'text/javascript',
		onload: function () {
			//elementLoaded(url);
		},
		onreadystatechange: function () {
			if (thisObj.readyState === 'loaded' || thisObj.readyState === 'complete') {
				// elementLoaded(url);
			}
			//elementReadyStateChanged(url, this);
		},
		src: url,
		appendTo: 'body'
	});
	return (result);
}
function createElement(config) {
	var e = document.createElement(config.element);
	for (var i in config) {
		if (i !== 'element' && i !== 'appendTo') {
			e[i] = config[i];
		}
	}
	var root = document.getElementsByTagName(config.appendTo)[0];
	return (typeof root.appendChild(e) === 'object');
}



/**
 *textArea 限制字数
 *<textarea  onKeyUp="textarealength(this,150)"></textarea>
 * @param {*} obj
 * @param {*} maxlength
 */
function textarealength(obj, maxlength) {
	var v = $(obj).val();
	var l = v.length;
	if (l > maxlength) {
		v = v.substring(0, maxlength);
		$(obj).val(v);
	}
	$(obj).parent().find(".textarea-length").text(v.length);
}

/**
 * 导出文件
 * @param {*} params 参数
 * @param {*} url url地址
 */
function postExcelFile(params, url) { //params是post请求需要的参数，url是请求url地址
	var form = document.createElement("form");
	form.style.display = 'none';
	form.action = url;
	form.method = "post";
	document.body.appendChild(form);

	for (var key in params) {
		var input = document.createElement("input");
		input.type = "hidden";
		input.name = key;
		input.value = params[key];
		form.appendChild(input);
	}

	form.submit();
	form.remove();
}
/**
 * 返回项目地址
 * 如：http://127.0.0.1:5500/
 * http://127.0.0.1:5500/inmsUI/
 */
function getUrl(){
	var src = window.location.href;
	var index = src.indexOf('views')

	src = src.substr(0,index)
	return src
}
//Tab自适应
function menuTabAuto(ele){
	var SCROLL = 'layui-tab-scroll', MORE = 'layui-tab-more', BAR = 'layui-tab-bar'
		, that = this;
	$(ele).each(function(){
		var title = $(this)
			,STOPE = 'lay-stope="tabmore"'
			,span = $('<span class="layui-unselect layui-tab-bar" '+ STOPE +'><i '+ STOPE +' class="layui-icon">&#xe61a;</i></span>');
		//响应式
		if(title[0].scrollWidth > title[0].offsetWidth+1){
			if(title.find('.'+BAR)[0]) return;
			title.append(span);
			// title.attr('overflow', '');
			span.on('click', function(e){
				title[this.title ? 'removeClass' : 'addClass'](MORE);
				this.title = this.title ? '' : '收缩';
			});
		} else {
			title.find('.'+BAR).remove();
			title.removeAttr('data-type');
		}

	})

}

function searchTips(){//查询条件添加tips
	var label = $(".layui-form-label");//
	if(label != null && label != undefined){
		$(label).each(function(index,ele){
			$(this).removeClass("table-tips")
			var labelOffsetWidth = (ele.offsetWidth)+1;
			var tipMsg = $(this).text()
			var currentObj = $('<span id="aa"></span>').hide().appendTo(window.parent.document.body);
			$(currentObj).html(tipMsg).css('font', "Roboto,sans-serif").css('font-weight', 400);
			var labelScroolWidth = currentObj.outerWidth(true);
			if(labelScroolWidth+12 > labelOffsetWidth){
				ele.className += ' table-tips';
				ele.setAttribute("data-msg",tipMsg)
			}
			currentObj.remove();
		})
	}
}

window.addEventListener('storage', function(e) { //切换语言时候对表格之类已经调过的数据进行监听
	if(e.oldValue != e.newValue) {
		setTimeout(function(){
			searchTips()
		},1000)

	}
})
//时间开始与结束验证-年月验证
function date_check_m(start, end) {
	var date1 = new Date(start);
	var date2 = new Date(end);
	var startMonth1 = date1.getMonth() + 1;
	var endMonth = date2.getMonth() + 1;
	var r = (date2.getFullYear() - date1.getFullYear()) * 12 + (endMonth - startMonth1);
	return r;
};

//时间开始与结束验证-年月日验证
function date_check_d(start, end) {
	// var startTime = $("#+"+start+"").val();
	var start = new Date(start.replace("-", "/").replace("-", "/"));
	// var endTime = $("#+"+end+"").val();
	var end = new Date(end.replace("-", "/").replace("-", "/"));
	if (start > end) {
		return false;
	}

}
