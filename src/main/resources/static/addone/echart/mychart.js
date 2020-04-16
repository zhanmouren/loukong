/**
 * 扩展了jquery的loadChart方法。
 */
;
"use strict";
(function(window, document, $, undefined) {
	/**
	 * @param element 加载图表的元素的jquery对象
	 * @param url 获取数据的URL地址,如果为null这不调用
	 * @param data 获取数据需要的参数
	 * @param option 前端样式
	 * @param intersect 显示图表前的回调函数，参数包含经过处理的echart参数
	 */
	function LoadChart(element, url, data, option,intersect) {
		this.element = element;
		this.url = url;
		this.option = option;
		this.intersect = intersect;
		this.data = data;
	}
	LoadChart.prototype.load = function() {
		var self = this;
		seajs.use(ctxPath + "/addone/echart/echarts.min.js", function() {
			if (self.url)
				commitData(ctxPath + self.url, self.data, {
					success : function(info) {
						var data = info;
						if (typeof info === "string")
							data = JSON.parse(info);
						if (data.code == 0)// 调用成功
						{
							var chartData = data.data;
							if (chartData.xAxis)
							{
								if(self.option.xAxis.constructor == Array){
									self.option.xAxis[0] = $.extend(self.option.xAxis[0],chartData.xAxis);
								}else
									self.option.xAxis = $.extend(self.option.xAxis,chartData.xAxis);
							}
							if (chartData.yAxis)
							{
								if(self.option.yAxis.constructor == Array){
									self.option.yAxis[0] = $.extend(self.option.yAxis[0],chartData.yAxis);
								}else
									self.option.yAxis = $.extend(self.option.yAxis,chartData.yAxis);
							}
							if (chartData.x2Axis)
							{
								if(self.option.xAxis.constructor == Array){
									self.option.xAxis[1] = $.extend(self.option.xAxis[1],chartData.x2Axis);
								}
							}
							if (chartData.yAxis)
							{
								if(self.option.yAxis.constructor == Array){
									self.option.yAxis[1] = $.extend(self.option.yAxis[1],chartData.y2Axis);
								}
							}

							if (chartData.series) {
								self.option.series || (self.option.series = new Array());
								var option_series = self.option.series;
								$.each(chartData.series, function(index, item) {
									if (option_series.length > index) {
										$.extend(option_series[index],item);
									} else {
										var tmp = $.extend(true, {}, option_series[option_series.length - 1]);
										$.extend(tmp,item);
										option_series.push(tmp);
									}
								});
							}
							var myChart = echarts.init(self.element[0]);
							self.intersect && self.intersect(self.option);
							myChart.setOption(self.option);
						}
					}
				}, "POST", "text");
			else {
				var myChart = echarts.init(self.element[0]);
				myChart.setOption(self.option);
			}
		});
	}
	$.fn.extend({
		loadChart : function(option, url, data,intersect) {
			new LoadChart(this, url, data, option,intersect).load();
		}
	});
})(window, document, jQuery);