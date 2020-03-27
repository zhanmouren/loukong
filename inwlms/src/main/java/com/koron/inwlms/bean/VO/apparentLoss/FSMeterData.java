package com.koron.inwlms.bean.VO.apparentLoss;

import java.util.List;

/**
 * 消防水表数据VO
 * @author lu
 * @Date 2020/03/27
 */
public class FSMeterData {

	/**
	 * 时间集合
	 */
	private List<String> date;
	
	/**
	 * 数值集合
	 */
	private List<Double> value;


	public List<Double> getValue() {
		return value;
	}


	public void setValue(List<Double> value) {
		this.value = value;
	}


	public List<String> getDate() {
		return date;
	}


	public void setDate(List<String> date) {
		this.date = date;
	}
	
}
