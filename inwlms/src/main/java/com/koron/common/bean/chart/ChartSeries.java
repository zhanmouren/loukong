package com.koron.common.bean.chart;

import java.util.*;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

/**
 * 数据系列
 * @author swan
 *
 */
public class ChartSeries {
	
	private static final Logger logger = Logger.getLogger(ChartSeries.class);
	/**
	 * 类型
	 * @see SERIESTYPE
	 */
	private SERIESTYPE type;
	/**
	 * 数据名称
	 */
	private String name;
	/**
	 * 数据
	 */
	private List<Object> data;
	/**
	 * 标记
	 */
	private MarkPoint markPoint;
	/**
	 * 图形半径，饼图用
	 */
	private String[] radius;
	/**获取类型
	 * @return 显示类型
	 * @see SERIESTYPE
	 */
	public SERIESTYPE getType() {
		return type;
	}

	/**设置类型
	 * @param type 显示类型
	 * @see SERIESTYPE
	 */
	public void setType(SERIESTYPE type) {
		this.type = type;
	}

	/**获取数据名称
	 * @return 数据名称
	 */
	public String getName() {
		return name;
	}

	/**设置数据名称
	 * @param name 数据名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**获取数据
	 * @return 数据
	 */
	public List<Object> getData() {
		return data;
	}

	/**设置数据
	 * @param data 数据
	 */
	public void setData(List<Object> data) {
		if(this.data == null)
			this.data = new ArrayList<>();
		this.data.clear();
		this.data.addAll(data);
	}
	/**
	 * 添加直角坐标系数据(线图,柱状图,雷达图)
	 * @param data
	 * @return
	 */
	public ChartSeries addCrossData(Object... data) {
		if(type != SERIESTYPE.LINE && type != SERIESTYPE.BAR && type != SERIESTYPE.RADAR && this.data != null && this.data.size() > 0)
			return null;
		if(type != SERIESTYPE.BAR && type != SERIESTYPE.RADAR)//不是线条
			type = SERIESTYPE.LINE;
		if(this.data == null)
			 this.data = new ArrayList<Object>();
		this.data.addAll(Arrays.asList(data));
		return this;
	}
	/**
	 * 添加饼图数据
	 * @param data
	 * @return
	 */
	public ChartSeries addPieData(PieData... data) {
		if(type != SERIESTYPE.PIE && this.data != null && this.data.size() > 0)
			return null;
		type = SERIESTYPE.PIE;
		if(this.data == null)
			 this.data = new ArrayList<Object>();
		this.data.addAll(Arrays.asList(data));
		return this;
	}
	
	/**设置标识点
	 * @return 标识点
	 * @see MarkPoint
	 */
	public MarkPoint getMarkPoint() {
		return markPoint;
	}

	/**设置数据标识点
	 * @param markPoint 数据标识点
	 */
	public void setMarkPoint(MarkPoint markPoint) {
		this.markPoint = markPoint;
	}
	/**获取饼图半径
	 * @return 饼图半径
	 */
	public String[] getRadius() {
		return radius;
	}

	/**<pre>
	 * 设置饼图半径.
	 * 例如：['50%','75%'],分为内外半径 
	 * </pre>
	 * @param radius 饼图半径
	 */
	public void setRadius(String... radius) {
		this.radius = radius;
	}
	public static class PieData{
		private String name;
		private String value;
		public PieData(String name, String value) {
			super();
			this.name = name;
			this.value = value;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}
		/**
		 * @param value the value to set
		 */
		public void setValue(String value) {
			this.value = value;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "PieData [name=" + name + ", value=" + value + "]";
		}
	}
	public enum SERIESTYPE {
		/**
		 * 饼图
		 */
		PIE, 
		/**
		 * 线图
		 */
		LINE, 
		/**
		 * 柱状图
		 */
		BAR, 
		/**
		 * 散点图
		 */
		SCATTER, 
		/**
		 * 雷达图
		 */
		RADAR;
		private static Map<String, SERIESTYPE> namesMap = new HashMap<>(5);

		static {
			namesMap.put("pie", PIE);
			namesMap.put("line", LINE);
			namesMap.put("bar", BAR);
			namesMap.put("scatter", SCATTER);
			namesMap.put("radar", RADAR);
		}

		@JsonCreator
		public static SERIESTYPE forValue(String value) {
			return namesMap.get(value == null ? null : value.toLowerCase());
		}

		@JsonValue
		public String toValue() {
			for (Entry<String, SERIESTYPE> entry : namesMap.entrySet()) {
				if (entry.getValue() == this)
					return entry.getKey();
			}
			return null; // or fail
		}
	}

}
