package com.koron.inwlms.bean.VO.apparentLoss;

import java.util.List;

/**
 * 表观漏损统计指标数据(用于表观漏损图表)
 * @author csh
 * @Date 2020/03/26
 */
public class ALData {

	/**
	 * 时间集合
	 */
	private List<Integer> date;
	
	/**
	 * 表观漏损量集合
	 */
	private List<Double> ALList;
	
	/**
	 * 单位户数表观漏损量集合
	 */
	private List<Double> perCustomerAccALList;
	
	/**
	 * 表观漏损占比集合
	 */
	private List<Double> percentALList;
	
	/**
	 * 表观漏损指数
	 */
	private List<Double> ALIList;

	public List<Integer> getDate() {
		return date;
	}

	public List<Double> getALList() {
		return ALList;
	}

	public List<Double> getPerCustomerAccALList() {
		return perCustomerAccALList;
	}

	public List<Double> getPercentALList() {
		return percentALList;
	}

	public List<Double> getALIList() {
		return ALIList;
	}

	public void setDate(List<Integer> date) {
		this.date = date;
	}

	public void setALList(List<Double> aLList) {
		ALList = aLList;
	}

	public void setPerCustomerAccALList(List<Double> perCustomerAccALList) {
		this.perCustomerAccALList = perCustomerAccALList;
	}

	public void setPercentALList(List<Double> percentALList) {
		this.percentALList = percentALList;
	}

	public void setALIList(List<Double> aLIList) {
		ALIList = aLIList;
	}
	
}
