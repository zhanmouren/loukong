package com.koron.common.bean.chart;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;


/**
 * Echart坐标轴
 * 
 * @author swan
 *
 */
public class ChartAxis {
	/**
	 * 坐标轴类型. 值为category value time log之一.
	 * @see TYPE
	 */
	private TYPE type;
	/**
	 * 名称.
	 */
	private String name;
	/**
	 * 坐标轴位置. 值为left | right | bottom | top之一.
	 * @see POSITION
	 */
	private POSITION position;
	/**
	 * 是否显示坐标轴
	 */
	private boolean show = true;
	/**
	 * 坐标轴名的位置.
	 * @see NAMELOCATION
	 */
	private String nameLocation;
	/**
	 * 最小值
	 */
	private Integer min;
	/**
	 * 最大值
	 */
	private Integer max;
	/**
	 * 刻度切分数量
	 */
	private Integer splitNumber;
	/**
	 * 类型为 {@link TYPE#CATEGORY} 时对应的数据.
	 */
	private Object Data;
	
	/**获取坐标轴类型
	 * @return 坐标轴类型
	 */
	public TYPE getType() {
		return type;
	}
	/**设置坐标轴类型
	 * @param type 坐标轴类型
	 * @see TYPE
	 */
	public void setType(TYPE type) {
		this.type = type;
	}
	/**获取坐标轴名称
	 * @return 坐标轴名称
	 */
	public String getName() {
		return name;
	}
	/**设置坐标轴名称
	 * @param name 坐标轴名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**获取坐标轴位置
	 * @return 坐标轴位置
	 * @see POSITION
	 */
	public POSITION getPosition() {
		return position;
	}
	/**设置坐标轴位置
	 * @param position 坐标轴位置
	 * @see POSITION
	 */
	public void setPosition(POSITION position) {
		this.position = position;
	}
	/**显示坐标轴
	 * @return 是否显示坐标轴
	 */
	public boolean isShow() {
		return show;
	}
	/**设置是否显示坐标轴
	 * @param show 是否显示
	 */
	public void setShow(boolean show) {
		this.show = show;
	}
	/**坐标轴名称显示位置
	 * @return 坐标轴名称显示位置
	 * @see NAMELOCATION
	 */
	public String getNameLocation() {
		return nameLocation;
	}
	/**设置坐标轴名称显示位置
	 * @param nameLocation 坐标轴名称显示位置
	 * @see NAMELOCATION
	 */
	public void setNameLocation(String nameLocation) {
		this.nameLocation = nameLocation;
	}
	/**获取坐标轴最小值
	 * @return 坐标轴最小值
	 */
	public Integer getMin() {
		return min;
	}
	/**设置坐标轴最小值
	 * @param min 坐标轴最小值
	 */
	public void setMin(Integer min) {
		this.min = min;
	}
	/**获取坐标轴最大值
	 * @return 坐标轴最大值
	 */
	public Integer getMax() {
		return max;
	}
	/**设置坐标轴最大值
	 * @param max 坐标轴最大值
	 */
	public void setMax(Integer max) {
		this.max = max;
	}
	/**坐标轴切分段数
	 * @return 坐标轴切分段数
	 */
	public Integer getSplitNumber() {
		return splitNumber;
	}
	/**设置坐标轴切分段数
	 * @param splitNumber 坐标轴切分段数
	 */
	public void setSplitNumber(Integer splitNumber) {
		this.splitNumber = splitNumber;
	}
	/**坐标轴数据.
	 * 坐标轴类型为 {@link TYPE#CATEGORY}时有效
	 * @return 坐标轴数据
	 */
	public Object getData() {
		return Data;
	}
	/**设置坐标轴数据.
	 * 坐标轴类型为 {@link TYPE#CATEGORY}时有效
	 * @param data 坐标轴数据
	 */
	public void setData(Object data) {
		Data = data;
	}
	/**
	 * 坐标轴类型
	 * @author swan
	 */
	public enum TYPE {
		/**
		 * 分类
		 */
		CATEGORY, 
		/**
		 * 数值
		 */
		VALUE, 
		/**
		 * 时间
		 */
		TIME, 
		/**
		 * 数值按对数显示
		 */
		LOG;
		private static Map<String, TYPE> namesMap = new HashMap<>(4);

		static {
			namesMap.put("category", CATEGORY);
			namesMap.put("value", VALUE);
			namesMap.put("time", TIME);
			namesMap.put("log", LOG);
		}

		@JsonCreator
		public static TYPE forValue(String value) {
			return namesMap.get(value == null ? null : value.toLowerCase());
		}

		@JsonValue
		public String toValue() {
			for (Entry<String, TYPE> entry : namesMap.entrySet()) {
				if (entry.getValue() == this)
					return entry.getKey();
			}
			return null; // or fail
		}
	}
	/**
	 * 坐标轴位置
	 * @author swan
	 */
	public enum POSITION {
		/**
		 * 左
		 */
		LEFT,
		/**
		 * 上
		 */
		TOP,
		/**
		 * 右
		 */
		RIGHT,
		/**
		 * 下
		 */
		BOTTOM;
		private static Map<String, POSITION> namesMap = new HashMap<>(4);

		static {
			namesMap.put("left", LEFT);
			namesMap.put("top", TOP);
			namesMap.put("right", RIGHT);
			namesMap.put("bottom", BOTTOM);
		}

		@JsonCreator
		public static POSITION forValue(String value) {
			return namesMap.get(value == null ? null : value.toLowerCase());
		}

		@JsonValue
		public String toValue() {
			for (Entry<String, POSITION> entry : namesMap.entrySet()) {
				if (entry.getValue() == this)
					return entry.getKey();
			}
			return null; // or fail
		}
	}
	/**
	 * 坐标轴文字位置
	 * @author swan
	 *
	 */
	public enum NAMELOCATION{
		/**
		 * 开始
		 */
		START,
		/**
		 * 中间
		 */
		CENTER,
		/**
		 * 结束
		 */
		END;
		private static Map<String, NAMELOCATION> namesMap = new HashMap<>(3);

		static {
			namesMap.put("start", START);
			namesMap.put("center", CENTER);
			namesMap.put("end", END);
		}

		@JsonCreator
		public static NAMELOCATION forValue(String value) {
			return namesMap.get(value == null ? null : value.toLowerCase());
		}

		@JsonValue
		public String toValue() {
			for (Entry<String, NAMELOCATION> entry : namesMap.entrySet()) {
				if (entry.getValue() == this)
					return entry.getKey();
			}
			return null; // or fail
		}
	}
}
