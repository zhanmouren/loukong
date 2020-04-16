package com.koron.inwlms.bean.VO.apparentLoss;

import java.util.List;

/**
 * 表计管理指标分析数据(用于表观漏损图表)
 * @author csh
 * @Date 2020/03/26
 */
public class MeterManageData {

	/**
	 * 时间集合
	 */
	private List<Integer> date;
	
	/**
	 * 水表基础信息不完善占比集合
	 */
	private List<Double> nonBasicInfoMeterRateList;
	
	/**
	 * 未抄表占比集合
	 */
	private List<Double> percentNonMeterReadList;
	
	/**
	 * 抄表及时率集合
	 */
	private List<Double> meterReadRateList;
	
	/**
	 * 抄表时间不固定占比集合
	 */
	private List<Double> nonMeterReadTimeRateList;
	
	/**
	 * 超期服役水表占比集合
	 */
	private List<Double> overdueMetersRateList;

	public List<Integer> getDate() {
		return date;
	}

	public List<Double> getNonBasicInfoMeterRateList() {
		return nonBasicInfoMeterRateList;
	}

	public List<Double> getPercentNonMeterReadList() {
		return percentNonMeterReadList;
	}

	public List<Double> getMeterReadRateList() {
		return meterReadRateList;
	}

	public List<Double> getNonMeterReadTimeRateList() {
		return nonMeterReadTimeRateList;
	}

	public List<Double> getOverdueMetersRateList() {
		return overdueMetersRateList;
	}

	public void setDate(List<Integer> date) {
		this.date = date;
	}

	public void setNonBasicInfoMeterRateList(List<Double> nonBasicInfoMeterRateList) {
		this.nonBasicInfoMeterRateList = nonBasicInfoMeterRateList;
	}

	public void setPercentNonMeterReadList(List<Double> percentNonMeterReadList) {
		this.percentNonMeterReadList = percentNonMeterReadList;
	}

	public void setMeterReadRateList(List<Double> meterReadRateList) {
		this.meterReadRateList = meterReadRateList;
	}

	public void setNonMeterReadTimeRateList(List<Double> nonMeterReadTimeRateList) {
		this.nonMeterReadTimeRateList = nonMeterReadTimeRateList;
	}

	public void setOverdueMetersRateList(List<Double> overdueMetersRateList) {
		this.overdueMetersRateList = overdueMetersRateList;
	}
	
}
