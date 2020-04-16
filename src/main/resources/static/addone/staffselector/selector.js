/**
 * 用户、部门、职位选择框
 * 使用方式：
 * 2)在需要弹出的元素或输入框使用$("selector").staffSelector(opt);选择好后会回填对应的名字，并且元素data("code")会把存储中的人员加上
 * 3)opt参数有：type,checkType,commit,process
 */
;(function($, window, document, undefined) {
	var dialog = function(element, opt) {
		this.element = element;// 回填元素
		var self = this;
		var defaultOpt = {//默认配置
			type : 7,// 人员1、部门2、职位4
			checkType : "single",// 单选多选类型***默认为单选
			commit : function(data) {//回填--回调函数
				var tag = self.element.context.tagName;//获取回填元素的标签
				if (tag == "INPUT") { //回填元素为input
					self.element.val($(data).map(function() {return this.name}).get().join(","));//回填值
				}else{ //回填元素为普通元素****包括textarea
					self.element.empty();//先清空
					self.element.append($(data).map(function() {return this.name}).get().join(","));//回填值
				}
				self.element.data("staff-code", data);//把唯一code也回填到元素data-code上。
				self.options.process && self.options.process(data);
			}
		};
		this.options = $.extend(defaultOpt, opt);
	};
	dialog.prototype = {
		/**
		 * 初始化界面
		 */
		init : function(data) {
			data = data || this.options.data;
			var select_title = "";//组件标题
			var select_tab_title = "";//选项卡标题
			var select_search = "";//人员搜索框
			var select_icon = "";//未选中的状态图标****默认
			var select_icon_active = "";//选中的状态图标
			if (this.options.checkType === "single") {//单选
				select_check = "single";
				select_iocn = "icon-circle-empty";
				select_icon_active = "icon-dot-circled";
			} else {//多选
				select_check = "check";
				select_iocn = "icon-check-empty";
				select_icon_active = "icon-check";
			}
			if ((this.options.type & 1) == 1) {
				select_type = 1;
				select_title += "人员";
				select_tab_title += '<span class="personal-name" type="' + select_type + '">人员</span>';
				select_search = '<div class="u-search"><input type="text"  /><i class="icon-search"></i></div>';
			}
			;
			if ((this.options.type & 2) == 2) {
				select_type = 2;
				select_title += "部门";
				select_tab_title += '<span class="department" type="' + select_type + '">部门</span>';
			}
			;
			if ((this.options.type & 4) == 4) {
				select_type = 4;
				select_title += "职位";
				select_tab_title += '<span class="p-post" type="' + select_type + '">职位</span>';
			}
			;
			this.DOM = $('<div class="popList">\
        						<div class="popTitle">\
            						<span class="close icon-cancel-circled rt"></span>\
            						<b class="icon-edit">请选择'
					+ select_title
					+ '</b>\
        						</div>\
	        					<div class="popContent">\
	        						<div class="conditionBox clearfloat">\
	                					<div class="condition-name">'
										+ select_tab_title
										+ '</div>'
										+ select_search
										+ '</div>\
						            <div class="p-content clearfloat">\
						            	<div class="leftBox clearfloat">\
						                    <span class="icon-exchange"></span>\
						                    <div class="department-div list01"><ul></ul></div>\
						                    <div class="personal-nameDiv list02"><ul></ul></div>\
						                </div>\
						                <div class="rightBox">\
						                    <div class="p-selected list04">\
						                        <ul></ul>\
						                    </div>\
						                </div>\
						            </div>\
	        					</div>\
	        					<div class="popFooter">\
						            <button class="rt">确定</button>\
						        </div>');
			var ele = $("<div>").addClass('popbox');
			ele.append(this.DOM);
			var self = this;
			// 给一些元素绑定事件
			this.DOM.find(".popTitle .close").click(this.closePop);
			this.DOM.find(".personal-nameDiv").click(function() {self.selectStaff(event.target);});
			this.DOM.find(".rightBox").click(function(){self.deleteStaff(event.target);});
			this.DOM.find(".condition-name span").click(function() {self.tabSwitch(this);});
			this.DOM.find(".condition-name span:eq(0)").click();
			this.DOM.find(".popFooter button.rt").click(function() {self.savePop();});
			this.DOM.find(".conditionBox .icon-search").click(function(){self.searchStaff();});//搜索人员
			this.DOM.find(".conditionBox input").bind("keypress",function(){
				if (event.keyCode == "13") {
				    self.searchStaff();
				   }
			})
			//当已经回填了选择项后再次点击，会把选择项渲染到弹层右侧
			data && $(data).map(function() {
				self.addSelectedItem(this.code, this.type, this.name);
			});
			return ele[0];
		},
		/**
		 * 关闭弹层
		 */
		closePop : function() {
			$('.popbox').remove();
		},
		/**
		 * 选择*****单选/多选选项
		 */
		selectStaff : function(target) {
			//******将点击目标始终转换为li
			if (target.tagName != 'SPAN' && target.tagName != "LI")
				return;
			var item = $(target);
			if (target.tagName == "SPAN")
				item = item.parent();
			if (select_check === "single") {// ******单选
				item.siblings('li').find("span").removeClass('icon-dot-circled').addClass("icon-circle-empty");
				item.find("span").addClass("icon-dot-circled").removeClass('icon-circle-empty');
			} else if (select_check === "check") {// *****多选
				var staff_text = item.find("span").text(); // 选中的文本
				var select_id = item.find("span").data("code"); // 选中的code
				var self = this;
				if (item.find("span").hasClass("select-all")) {// *****判断点击的是全选还是普通多选项***全选
					if (item.find("span").hasClass("icon-check")) {// 全不选
						item.siblings().find("span").removeClass("icon-check").addClass("icon-check-empty");
						item.find("span").removeClass("icon-check").addClass("icon-check-empty");
						item.siblings().each(function() {
							self.removeSelectedItem($(this).find("span").data("code"));//删除右侧所有匹配的选项
						});
					} else {// 全选
						item.siblings().find("span").addClass("icon-check").removeClass("icon-check-empty");
						item.find("span").addClass("icon-check").removeClass("icon-check-empty");
						item.siblings().each(function() {
							self.removeSelectedItem($(this).find("span").data("code"));//先删除单个添加到右侧的选项***全选会选择添加到右侧
							var dept = $(this).find("span").data("dept");
							if(dept != null && dept != "")
								dept = "("+dept+")";
							self.addSelectedItem($(this).find("span").data("code"), 1, $(this).find("span").text()+dept);//添加所有的选项到右侧
						});
					}
					return;
				}
				// *****普通多选项***
				if (item.find("span").hasClass("icon-check")) { // 取消选中**删除
					item.find("span").removeClass("icon-check").addClass("icon-check-empty");
					this.removeSelectedItem(select_id);
				} else { // 选中**添加
					item.find("span").removeClass("icon-check-empty").addClass("icon-check");
					this.addSelectedItem(select_id, select_type, staff_text);
				}
			}
		},
		/**
		 * 删除*****选项
		 */
		removeSelectedItem : function(code) {
			this.DOM.find(".rightBox .p-selected li span[data-code=" + code + "]").parent().remove();
		},
		/**
		 * 添加*****选项
		 */
		addSelectedItem : function(code, type, text) {
			var item = '<li><span data-code="' + code + '" data-type="' + type + '">' + text + '<i class="close icon-cancel-circled"></i></span></li>';
			this.DOM.find(".rightBox .p-selected ul").append(item);
		},
		/**
		 * 右侧删除*****多选选项
		 */
		deleteStaff : function(target) {
			if (target.tagName != 'I' && target.tagName != "LI" && target.tagName != "SPAN" ){
				return;
			}
			var item = $(target);
			var delete_id = item.closest("li").find("span").data('code');// 获取要删除的code值
			item.closest("li").remove();// 删除当前点击对象
			this.DOM.find(".leftBox .personal-nameDiv span[data-code=" + delete_id + "]").removeClass('icon-check').addClass("icon-check-empty");// 在左侧对应的相同code选项变成未选中状态
		},
		/**
		 * 选项卡切换
		 */
		tabSwitch : function(target) {
			// 设置选项卡标题是否显示 ***单个不显示 ***多个才显示
			var tab_length = this.DOM.find(".condition-name span").size();
			if (tab_length < 2) {
				this.DOM.find(".condition-name").empty();
			}
			// 设置卡片标题active属性
			var tabIndex = this.DOM.find(".condition-name span").index(target);
			this.DOM.find(".condition-name span").removeClass('active').eq(tabIndex).addClass('active');
			// 设置单选与多选的排版***默认是多选的排版
			if (select_check == "single") { // 单选
				this.DOM.find(".rightBox,.leftBox .icon-exchange").hide();
				this.DOM.find(".leftBox").css('width', '100%');
			}
			select_type = $(target).attr("type");
			if (select_type == 1) {// 选项卡***人员
				this.DOM.find(".u-search").show().find("input").val("").focus();
				this.DOM.find(".personal-nameDiv,.department-div").show().css('width', '50%');
				this.DOM.find(".personal-nameDiv").empty();
				var self = this;
				//加载组织架构数据
				this.department(function(data) {
					self.DOM.find(".department-div").deptree(
							data,
							{
								callback : {
									onClick : function(event, node, data) {
										self.staff(data.code, function(data2) {// ******点击执行加载组织架构下的人员信息
											if (data2.length>0) {//判断是否有数据
												if (select_check == "single") {//判断是否显示全选***多选才有全选
													var ul = "";
												}else{
													var ul = '<li><span class="select-all ' + select_iocn + '">全选</span></li>';
												}
												for (var i = 0; i < data2.length; i++) {
													ul += '<li><span class="' + select_iocn + '" data-type="' + select_type + '" data-code="' + data2[i].code
															+ '" data-dept="'+ data2[i].departmentname +'">' + data2[i].name + '</span></li>';
												}
												ul = $("<ul>").append(ul);
												self.DOM.find(".personal-nameDiv").empty().append(ul);
												self.DOM.find(".rightBox li span").each(
												function() {// 判断右侧是否已经存在
													var code = $(this).data("code");
													self.DOM.find(".personal-nameDiv li span[data-code=" + code + "]").removeClass('icon-check-empty')
															.addClass('icon-check');
												});
											}
										});
									}
								}
							});
				});
			} else if (select_type == 2) {// 选项卡***部门
				select_search = '';
				this.DOM.find(".personal-nameDiv,.u-search").hide();
				this.DOM.find(".department-div").show().css('width', '100%');
				var self = this;
				// 加载部门数据
				this.department(function(data) {
					self.DOM.find(".department-div").deptree(
							data,
							{
								callback : {
									onClick : function(event, node, data) {
										if (select_check == "single") {//单选
										}else{//多选
											//判断是否是最底层节点***是否已经选中添加了
											if (data._child == 0 && self.DOM.find(".rightBox li span[data-code=" + data.code + "]").length == 0) {
												var li = '<li><span data-code="' + data.code + '" data-type="' + select_type + '">' + data.name
														+ '<i class="close icon-cancel-circled"></i></span></li>';
												self.DOM.find(".rightBox .p-selected ul").append(li);
											}
										}
									}
								}
							});
				});
			} else if (select_type == 4) {// 选项卡***职位
				this.DOM.find(".department-div,.u-search").hide();
				this.DOM.find(".personal-nameDiv").show().css('width', '100%');
				var self = this;
				// 加载职位数据
				this.position(function(data) {
					self.DOM.find(".personal-nameDiv").empty();// 先清空
					var ul = "";
					for (var i = 0; i < data.length; i++) {
						ul += '<li><span class="' + select_iocn + '" data-type="' + select_type + '" data-code="' + data[i].code + '">' + data[i].name
								+ '</span></li>';
					}
					ul = $("<ul>").append(ul);
					self.DOM.find(".personal-nameDiv").empty().append(ul);
					self.DOM.find(".rightBox li span").each(function() {// 判断右侧是否已经存在
						var code = $(this).data("code");
						self.DOM.find(".personal-nameDiv li span[data-code=" + code + "]").removeClass('icon-check-empty').addClass('icon-check');
					});
				});
			}
		},
		/**
		 * 部门模块***获取部门数据
		 */
		department : function(callback) {
			$.ajax({
				url : ctxPath + "/dep/json.htm",
				dataType : "JSON",
				type : "POST",
				data : {
					id : -1
				},
				headers : {
					"returntype" : "ajax/json"
				},
				traditional : true,
				success : function(data) {
					callback && callback(data);
				}
			});
		},
		/**
		 * 人员模块***获取人员数据
		 */
		staff : function(departmentcode, callback) {
			$.ajax({
				url : ctxPath + "/dep/staff.htm",
				dataType : "JSON",
				type : "POST",
				data : {
					code : departmentcode
				},
				headers : {
					"returntype" : "ajax/json"
				},
				traditional : true,
				success : function(data) {
					callback && callback(data);
				}
			});
		},
		/**
		 * 职位模块***获取职位数据
		 */
		position : function(callback) {
			// 模拟数据
			var data = [ {
				code : "1",
				name : "董事长"
			}, {
				code : "2",
				name : "总经理"
			}, {
				code : "3",
				name : "技术总监"
			} ];
			callback && callback(data);
		},
		/**
		 * 搜索人员
		 */
		searchStaff : function(){
			var self = this;
			var searchKey = self.DOM.find(".conditionBox .u-search input").val();
			if (searchKey.length>0) {
				$.ajax({
					url : ctxPath + "/staffquery.htm",
					dataType : "JSON",
					type : "POST",
					data : {
						q : searchKey
					},
					headers : {
						"returntype" : "ajax/json"
					},
					traditional : true,
					success : function(data) {
						var data = data.data;
						if (data.length>0) {
							if (select_check == "single") {//判断是否显示全选***多选才有全选
								var ul = "";
							}else{
								var ul = '<li><span class="select-all ' + select_iocn + '">全选</span></li>';
							}
							for (var i = 0; i < data.length; i++) {
								ul += '<li><span class="' + select_iocn + '" data-type="' + select_type + '" data-code="' + data[i].code
										+ '">' + data[i].name + '</span></li>';
							}
							ul = $("<ul>").append(ul);
							self.DOM.find(".personal-nameDiv").empty().append(ul);
							self.DOM.find(".rightBox li span").each(
									function() {// 判断右侧是否已经存在
										var code = $(this).data("code");
										self.DOM.find(".personal-nameDiv li span[data-code=" + code + "]").removeClass('icon-check-empty')
												.addClass('icon-check');
									});
						}else{
							self.DOM.find(".personal-nameDiv").empty().append('暂没有找到与“'+searchKey+'”相关的信息，请重新输入');
							self.DOM.find(".u-search input").focus();
						}
					}
				});
			}
		},
		/**
		 * 点击确认***
		 */
		savePop : function() {
			var self = this;
			var data = [];
			/**
			 * 存选择的数据
			 */
			var getData = function(tag,data){
				// **保存为对象***
				var obj = {};
				obj.type = tag.data("type");
				obj.code = tag.data("code");
				obj.name = tag.text();
				data.push(obj);//添加到数组中
			};
			if (select_check == "single") {//单选
				if (select_type == 2){
					var span = this.DOM.find("span.leaf.focus");
				}else{
					var span = this.DOM.find(".personal-nameDiv li span.icon-dot-circled");
				}
				getData(span,data)
			}else if(select_check == "check"){//多选
				this.DOM.find(".rightBox li span").each(function() {
					var span = $(this);
					getData(span,data)
				});
			}
			this.staffData = data;
			var callback = this.options.commit;
			callback && callback(data);
			$('.popbox').remove();
		},
		removeCode:function(code){//移除code对应的元素
			this.staffData = $.grep(this.staffData,function(item){ return item.code != code });
			var callback = this.options.commit;
			callback && callback(this.staffData);
		}
	};
	$.fn.staffSelector = function(option) {
		var self = this;
		seajs.use([ctxPath+"/addone/tree/mytree.js",ctxPath+"/addone/tree/mytree.css",ctxPath+"/addone/staffselector/selector.css"],function(){
		var defaultOption = {data:$(self).data("staff-code")};
		$.extend(defaultOption,option);
		var a = new dialog(self, defaultOption);
		$(self).data("selector",a);
		$("body").append(a.init());
	});
}})(jQuery,window,document);



