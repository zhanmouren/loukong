package com.koron.inwlms.util;

/**
 * 单位换算工具
 * @author csh
 *
 */
public class UnitUtil {

	/**
	 * 获取单位换算的乘法系数
	 * @param oldUnit
	 * @param newUnit
	 * @return
	 */
	public static double getUnitConversion(String oldUnit, String newUnit) {
		// 返回乘法的单位换算系数
		if (!"".equals(oldUnit) && !"".equals(newUnit)) {
			if(oldUnit.equals(newUnit)) return 1;
			return toDefaultUnit(oldUnit)*defaultToUnit(newUnit);
		}
		return 1;
	}
	
	/**
	 * 转换成默认单位
	 * 
	 * @returns
	 */
	public static double toDefaultUnit(String unit) {
		switch (unit) {
		case "mm":
			return 0.001;
		case "in":
			return 0.0254;
		case "m":
			return 1;
		case "km":
			return 1000;
		case "m²":
			return 1;
		case "km²":
			return 1000000;
		case "m³":
			return 1;
		case "Ml":
			return 1000;
		case "MCM":
			return 1000000;
		case "m³/hr":
			return 1;
		case "m³/d":
			return 0.04167;
		case "L/hr":
			return 0.001;
		case "Ml/d":
			return 41.67;
		case "L/s":
			return 3.6;
		case "MPa":
			return 10.2;
		case "bar":
			return 102.04;
		case "m/s":
			return 1;
		case "L/d/Acc.":
			return 1;
		case "m³/d/Acc.":
			return 1000;
		case "Ml/d/Acc.":
			return 1000000;
		case "m³/hr/Acc.":
			return 24000;
		case "m³/d/km":
			return 1;
		case "L/d/km":
			return 0.001;
		case "Ml/d/km":
			return 1000;
		case "m³/hr/km":
			return 24;
		case "L/d/Acc./m":
			return 1;
		case "m³/d/Acc./m":
			return 1000;
		case "Ml/d/Acc./m":
			return 1000000;
		case "m³/hr/Acc./m":
			return 24000;
		case "m³/d/km/m":
			return 1;
		case "L/d/km/m":
			return 0.001;
		case "Ml/d/km/m":
			return 1000;
		case "m³/hr/km/m":
			return 24;
		case "no. per km":
			return 1;
		case "no. per m":
			return 1000;
		default:
			return 1;
		}
	}

	/**
	 * 默认单位转换成相应的单位
	 * 
	 * @returns
	 */
	public static double defaultToUnit(String unit) {
		switch (unit) {
		case "mm":
			return 1000;
		case "in":
			return 39.37;
		case "m":
			return 1;
		case "km":
			return 0.001;
		case "m²":
			return 1;
		case "km²":
			return 0.000001;
		case "m³":
			return 1;
		case "Ml":
			return 0.001;
		case "MCM":
			return 0.000001;
		case "m³/hr":
			return 1;
		case "m³/d":
			return 24;
		case "L/hr":
			return 1000;
		case "Ml/d":
			return 0.024;
		case "L/s":
			return 0.2778;
		case "MPa":
			return 0.098;
		case "bar":
			return 0.0098;
		case "m/s":
			return 1;
		case "L/d/Acc.":
			return 1;
		case "m³/d/Acc.":
			return 0.001;
		case "Ml/d/Acc.":
			return 0.000001;
		case "m³/hr/Acc.":
			return 0.00004167;
		case "m³/d/km":
			return 1;
		case "L/d/km":
			return 1000;
		case "Ml/d/km":
			return 0.001;
		case "m³/hr/km":
			return 0.04167;
		case "L/d/Acc./m":
			return 1;
		case "m³/d/Acc./m":
			return 0.001;
		case "Ml/d/Acc./m":
			return 0.000001;
		case "m³/hr/Acc./m":
			return 0.00004167;
		case "m³/d/km/m":
			return 1;
		case "L/d/km/m":
			return 1000;
		case "Ml/d/km/m":
			return 0.001;
		case "m³/hr/km/m":
			return 0.04167;
		case "no. per km":
			return 1;
		case "no. per m":
			return 0.001;
		default:
			return 1;
		}
	}
}
