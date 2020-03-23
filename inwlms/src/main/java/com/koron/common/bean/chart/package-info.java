/**<pre>
 * echart图表相关bean
 * {@link ChartBean 图表对象 ChartBean} 该对象为主要对象。通过<b>ChartBean</b>把相关数据组织起来，封装到MessageBean,放回JSON对象到客户端。
 * 客户端通过 addone/echart/mychart.js可以调用相应的方法显示图表。
 * {@link ChartAxis 坐标轴 ChartAxis} 定义图表的坐标轴
 * {@link ChartSeries 数据 ChartSeries} 定义图表显示的数据.
 * {@link MarkPoint 特殊标点 MarkPoint} 定义图表数据中特殊显示的点
 * </pre>
 * @author swan
 */
package com.koron.common.bean.chart;