package com.koron.common.bean.chart;
/**
 * E-chart图标对象
 * @author swan
 *
 */
public class ChartBean {
	/**
	 * X轴坐标
	 */
	private ChartAxis xAxis;
	/**
	 * X轴第二坐标
	 */
	private ChartAxis x2Axis;
	/**
	 * Y轴坐标
	 */
	private ChartAxis yAxis;
	/**
	 * Y轴第二坐标
	 */
	private ChartAxis y2Axis;
	/**
	 * 数据对象
	 */
	private ChartSeries[] series;
	/**获取X轴坐标
	 * @return x轴坐标
	 */
	public ChartAxis getxAxis() {
		return xAxis;
	}
	/**设置X轴坐标
	 * @param xAxis x轴坐标 
	 */
	public void setxAxis(ChartAxis xAxis) {
		this.xAxis = xAxis;
	}
	/**获取X轴第二坐标
	 * @return X轴第二坐标
	 */
	public ChartAxis getX2Axis() {
		return x2Axis;
	}
	/**设置X轴第二坐标
	 * @param x2Axis X轴第二坐标
	 */
	public void setX2Axis(ChartAxis x2Axis) {
		this.x2Axis = x2Axis;
	}
	/**获取Y轴坐标
	 * @return Y轴坐标
	 */
	public ChartAxis getyAxis() {
		return yAxis;
	}
	/**设置Y轴坐标
	 * @param yAxis Y轴坐标
	 */
	public void setyAxis(ChartAxis yAxis) {
		this.yAxis = yAxis;
	}
	/**获取Y轴第二坐标
	 * @return Y轴第二坐标
	 */
	public ChartAxis getY2Axis() {
		return y2Axis;
	}
	/**设置Y轴第二坐标
	 * @param y2Axis Y轴第二坐标
	 */
	public void setY2Axis(ChartAxis y2Axis) {
		this.y2Axis = y2Axis;
	}
	/**获取图表数据
	 * @return 图表数据
	 */
	public ChartSeries[] getSeries() {
		return series;
	}
	/**设置图表数据
	 * @param 图表数据
	 */
	public void setSeries(ChartSeries... series) {
		this.series = series;
	}
}
