package com.koron.inwlms.bean.VO.report.waterBalanceReport;


/**
 *   水司及一级分区产销差率同比报表 VO
 * @author xiaozhan
 *
 */
public class WB1BalanceVO {
	//月
	private String monthId;
	//供水量
	private double systemInput=0.0;
	//计费计量用水量
	private double billedMeteredConsumption=0.0;
	//免费计量用水量
	private double unbilledMeteredConsumption=0.0;
	//合计
	private double total=0.0;
	//本月(未计量占比)
	private double currentW=0.0;
	//去年同期(未计量占比)
	private double lastW=0.0;
	//同比(未计量占比)
	private double yearAgoW=0.0;
	
	//本月(产销差率占比)
	private double currentC=0.0;
	//去年同期(产销差率占比)
	private double lastC=0.0;
	//同比(产销差率占比)
	private double yearAgoC=0.0;
	
	//产销差量今年本月
	private double cxcNow=0.0;
	//产销差率去年本月
	private double cxcLast=0.0;
	//未计量本月
	private double wjlNow=0.0;
	//未计量去年本月
	private double wjlLast=0.0;
	
	public double getCxcNow() {
		return cxcNow;
	}
	public void setCxcNow(double cxcNow) {
		this.cxcNow = cxcNow;
	}
	public double getCxcLast() {
		return cxcLast;
	}
	public void setCxcLast(double cxcLast) {
		this.cxcLast = cxcLast;
	}
	public double getWjlNow() {
		return wjlNow;
	}
	public void setWjlNow(double wjlNow) {
		this.wjlNow = wjlNow;
	}
	public double getWjlLast() {
		return wjlLast;
	}
	public void setWjlLast(double wjlLast) {
		this.wjlLast = wjlLast;
	}
	public String getMonthId() {
		return monthId;
	}
	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}
	
	public double getSystemInput() {
		return systemInput;
	}
	public void setSystemInput(double systemInput) {
		this.systemInput = systemInput;
	}
	public double getBilledMeteredConsumption() {
		return billedMeteredConsumption;
	}
	public void setBilledMeteredConsumption(double billedMeteredConsumption) {
		this.billedMeteredConsumption = billedMeteredConsumption;
	}
	public double getUnbilledMeteredConsumption() {
		return unbilledMeteredConsumption;
	}
	public void setUnbilledMeteredConsumption(double unbilledMeteredConsumption) {
		this.unbilledMeteredConsumption = unbilledMeteredConsumption;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getCurrentW() {
		return currentW;
	}
	public void setCurrentW(double currentW) {
		this.currentW = currentW;
	}
	public double getLastW() {
		return lastW;
	}
	public void setLastW(double lastW) {
		this.lastW = lastW;
	}
	public double getYearAgoW() {
		return yearAgoW;
	}
	public void setYearAgoW(double yearAgoW) {
		this.yearAgoW = yearAgoW;
	}
	public double getCurrentC() {
		return currentC;
	}
	public void setCurrentC(double currentC) {
		this.currentC = currentC;
	}
	public double getLastC() {
		return lastC;
	}
	public void setLastC(double lastC) {
		this.lastC = lastC;
	}
	public double getYearAgoC() {
		return yearAgoC;
	}
	public void setYearAgoC(double yearAgoC) {
		this.yearAgoC = yearAgoC;
	}
	
	
	

}
