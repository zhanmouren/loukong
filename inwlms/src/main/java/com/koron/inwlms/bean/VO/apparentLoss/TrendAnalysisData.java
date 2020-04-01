package com.koron.inwlms.bean.VO.apparentLoss;

import java.util.List;

/**
 * 走势分析
 * @author csh
 * @Data 2020/03/30
 */
public class TrendAnalysisData {

	private List<Integer> date;
	
	/**
	 * 表观漏损占比集合
	 */
	private List<Double> percentALList;
	
	/**
	 * 表观漏损指数
	 */
	private List<Double> ALIList;
	
	/**
	 * 水表基础信息不完善占比集合
	 */
	private List<Double> nonBasicInfoMeterRateList;
	
	/**
	 * 超期服役水表占比集合
	 */
	private List<Double> overdueMetersRateList;

	public List<Integer> getDate() {
		return date;
	}

	public List<Double> getPercentALList() {
		return percentALList;
	}

	public List<Double> getALIList() {
		return ALIList;
	}

	public List<Double> getNonBasicInfoMeterRateList() {
		return nonBasicInfoMeterRateList;
	}

	public List<Double> getOverdueMetersRateList() {
		return overdueMetersRateList;
	}

	public void setDate(List<Integer> date) {
		this.date = date;
	}

	public void setPercentALList(List<Double> percentALList) {
		this.percentALList = percentALList;
	}

	public void setALIList(List<Double> aLIList) {
		ALIList = aLIList;
	}

	public void setNonBasicInfoMeterRateList(List<Double> nonBasicInfoMeterRateList) {
		this.nonBasicInfoMeterRateList = nonBasicInfoMeterRateList;
	}

	public void setOverdueMetersRateList(List<Double> overdueMetersRateList) {
		this.overdueMetersRateList = overdueMetersRateList;
	}
	
}
