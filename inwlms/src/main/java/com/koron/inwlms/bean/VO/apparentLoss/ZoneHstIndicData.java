package com.koron.inwlms.bean.VO.apparentLoss;

import java.util.List;

/**
 * 分区历史指标数据
 * @author csh
 * @Date 2020.04.9
 */
public class ZoneHstIndicData {

	/**
	 * 指标编码
	 */
	private String code;
	
	/**
	 * 指标名称
	 */
	private String name;
	
	/**
	 * 时间数组
	 */
	private List<Integer> date;
	
	/**
	 * 数据数组
	 */
	private List<Double> value;

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public List<Integer> getDate() {
		return date;
	}

	public List<Double> getValue() {
		return value;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDate(List<Integer> date) {
		this.date = date;
	}

	public void setValue(List<Double> value) {
		this.value = value;
	}
	
}
