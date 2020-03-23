package com.koron.common.bean.chart;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

/**
 * Echart标注点
 * @author swan
 *
 */
public class MarkPoint {
	/**
	 * 标注点图片
	 */
	private SYMBOL symbol;
	/**
	 * 标注点大小
	 */
	private Integer symbolSize;
	/**
	 * 标注点数据
	 */
	private MarkData[] data; 
	/**获取标注点图片
	 * @return 标注点图片
	 */
	public SYMBOL getSymbol() {
		return symbol;
	}
	/**设置标注点图片
	 * @param symbol 标注点图片
	 * @see SYMBOL
	 */
	public void setSymbol(SYMBOL symbol) {
		this.symbol = symbol;
	}
	/**获取标注点大小
	 * @return 标注点大小
	 */
	public Integer getSymbolSize() {
		return symbolSize;
	}
	/**设置标注点大小
	 * @param symbolSize 标注点大小
	 */
	public void setSymbolSize(Integer symbolSize) {
		this.symbolSize = symbolSize;
	}
	/**获取标注点数据
	 * @return the data 标注点数据
	 */
	public MarkData[] getData() {
		return data;
	}
	/**设置标注点数据
	 * @param data 标注点数据
	 */
	public void setData(MarkData[] data) {
		this.data = data;
	}
	/**
	 * 标注点数据对象
	 * @author swan
	 *
	 */
	public static class MarkData{
		/**<pre>
		 * 标注点名称.
		 * 会显示在标注点上
		 * </pre>
		 */
		private String name;
		/**
		 * 标注点X轴位置
		 * <b>[x y] coord type三个只有一个有效 </b>
		 */
		private String x;
		/**
		 * 标注点Y轴位置
		 * <b>[x y] coord type三个只有一个有效 </b>
		 */
		private String y;
		/**<pre>
		 * 标注点位置.
		 * [x,y]格式，xy可以为数字，百分比，max min average.
		 * <b>[x y] coord type三个只有一个有效 </b>
		 * </pre>
		 */
		private String coord;
		/**<pre>
		 * 类型.
		 * 可以为max min average
		 * <b>[x y] coord type三个只有一个有效 </b>
		 * </pre>
		 */
		private DATATYPE type;
		/**获取显示名称
		 * @return 显示名称
		 */
		public String getName() {
			return name;
		}
		/**设置显示名称
		 * @param name 显示名称
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**<pre>显示位置的X轴位置.
		 * <b>[x y] coord type三个只有一个有效 </b>
		 * </pre>
		 * @return X轴位置
		 */
		public String getX() {
			return x;
		}
		/**<pre>设置显示位置的X轴位置.
		 * <b>[x y] coord type三个只有一个有效 </b></pre>
		 * @param x X轴位置
		 */
		public void setX(String x) {
			this.x = x;
		}
		/**<pre>获取显示的Y轴位置.
		 * <b>[x y] coord type三个只有一个有效 </b></pre>
		 * @return Y轴位置
		 */
		public String getY() {
			return y;
		}
		/**<pre>设置显示的Y轴坐标位置.
		 * <b>[x y] coord type三个只有一个有效 </b></pre>
		 * @param y Y轴坐标位置
		 */
		public void setY(String y) {
			this.y = y;
		}
		/**<pre>获取显示位置
		 * <b>[x y] coord type三个只有一个有效 </b></pre>
		 * @return 显示位置
		 */
		public String getCoord() {
			return coord;
		}
		/**<pre>设置显示位置
		 * <b>[x y] coord type三个只有一个有效 </b></pre>
		 * @param coord 显示位置
		 */
		public void setCoord(String coord) {
			this.coord = coord;
		}
		/**<pre>获取显示类型
		 * <b>[x y] coord type三个只有一个有效 </b></pre>
		 * @return 显示类型
		 */
		public DATATYPE getType() {
			return type;
		}
		/**<pre>设置显示类型
		 * <b>[x y] coord type三个只有一个有效 </b></pre>
		 * @param type 显示类型
		 */
		public void setType(DATATYPE type) {
			this.type = type;
		}
	}
	/**
	 * 标点的数据类型
	 * @author swan
	 *
	 */
	public enum DATATYPE {
		/**
		 * 最小值
		 */
		MIN,
		/**
		 * 最大值
		 */
		MAX,
		/**
		 * 平均值
		 */
		AVERAGE;
		private static Map<String, DATATYPE> namesMap = new HashMap<>(3);
		static {
			namesMap.put("min", MIN);
			namesMap.put("max", MAX);
			namesMap.put("average", AVERAGE);
		}

		@JsonCreator
		public static DATATYPE forValue(String value) {
			return namesMap.get(value == null ? null : value.toLowerCase());
		}

		@JsonValue
		public String toValue() {
			for (Entry<String, DATATYPE> entry : namesMap.entrySet()) {
				if (entry.getValue() == this)
					return entry.getKey();
			}
			return null; // or fail
		}
	}
	/**
	 * 标记点的图形
	 * @author swan
	 *
	 */
	public enum SYMBOL {
		/**
		 * 圆圈
		 */
		CIRCLE, 
		/**
		 * 方形
		 */
		RECT, 
		/**
		 * 圆角方形
		 */
		ROUNDRECT, 
		/**
		 * 三角形
		 */
		TRIANGLE, 
		/**
		 * 钻形
		 */
		DIAMOND, 
		/**
		 * 针形
		 */
		PIN, 
		/**
		 * 箭头
		 */
		ARROW;
		private static Map<String, SYMBOL> namesMap = new HashMap<>(7);
		static {
			namesMap.put("circle", CIRCLE);
			namesMap.put("rect", RECT);
			namesMap.put("roundrect", ROUNDRECT);
			namesMap.put("triangle", TRIANGLE);
			namesMap.put("diamond", DIAMOND);
			namesMap.put("pin", PIN);
			namesMap.put("arrow", ARROW);
		}

		@JsonCreator
		public static SYMBOL forValue(String value) {
			return namesMap.get(value == null ? null : value.toLowerCase());
		}

		@JsonValue
		public String toValue() {
			for (Entry<String, SYMBOL> entry : namesMap.entrySet()) {
				if (entry.getValue() == this)
					return entry.getKey();
			}
			return null; // or fail
		}
	}
}
