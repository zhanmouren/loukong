package com.koron.inwlms.bean.VO.report.statisticalReport;

public class FlowMeterAnalysis {
	
	private String type;
	/**
	 * 用户表数
	 */
	private Integer meterNum;
	/**
	 * 客户计量用水
	 */
	private Double customerWater;
	/**
	 * 单位用户用水量
	 */
	private Double UnitHouseWater;
	/**
	 * 用水量占比
	 */
	private Double waterProportion;
	/**
	 * 环比
	 */
	private Double monthContrast;
	/**
	 * 同比
	 */
	private Double yearContrast;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getMeterNum() {
		return meterNum;
	}
	public void setMeterNum(Integer meterNum) {
		this.meterNum = meterNum;
	}
	public Double getCustomerWater() {
		return customerWater;
	}
	public void setCustomerWater(Double customerWater) {
		this.customerWater = customerWater;
	}
	public Double getUnitHouseWater() {
		return UnitHouseWater;
	}
	public void setUnitHouseWater(Double unitHouseWater) {
		UnitHouseWater = unitHouseWater;
	}
	public Double getWaterProportion() {
		return waterProportion;
	}
	public void setWaterProportion(Double waterProportion) {
		this.waterProportion = waterProportion;
	}
	public Double getMonthContrast() {
		return monthContrast;
	}
	public void setMonthContrast(Double monthContrast) {
		this.monthContrast = monthContrast;
	}
	public Double getYearContrast() {
		return yearContrast;
	}
	public void setYearContrast(Double yearContrast) {
		this.yearContrast = yearContrast;
	}
	
	
	
	
	


}
