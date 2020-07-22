package com.koron.inwlms.bean.VO.statisticalReport;

public class ZoneMnf {
	/**
	 * 分区分级
	 */
	private String zoneGrade;
	/**
	 * 分区名称
	 */
	private String zoneName;
	
	private String zoneCode;
	/**
	 * 位置
	 */
	private String location;
	/**
	 * 分区类型
	 */
	private String zoneType;
	/**
	 * 时间-最小夜间流量
	 */
	private Double timeMNF;
	/**
	 * 时间-最小夜间流量/供水量（%）
	 */
	private Double timeProp;
	/**
	 * 昨日-最小夜间流量
	 */
	private Double yesterdayMNF;
	/**
	 * 昨日-增长率
	 */
	private Double yesterdayGrow;
	/**
	 * 昨日-最小夜间流量/供水量（%）
	 */
	private Double yesterdayProp;
	/**
	 * 过去7日-最小夜间流量平均值
	 */
	private Double sevenDayMNF;
	/**
	 * 过去7日-增长率
	 */
	private Double sevenDayGrow;
	/**
	 * 过去7日-最小夜间流量/供水量（%）
	 */
	private Double sevenDayProp;
	/**
	 * 上个月-最小夜间流量平均值
	 */
	private Double monthMNF;
	/**
	 * 上个月-增长率
	 */
	private Double monthGrow;
	/**
	 * 上个月-最小夜间流量/供水量（%）
	 */
	private Double monthProp;
	
	public String getZoneGrade() {
		return zoneGrade;
	}

	public void setZoneGrade(String zoneGrade) {
		this.zoneGrade = zoneGrade;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getZoneType() {
		return zoneType;
	}

	public void setZoneType(String zoneType) {
		this.zoneType = zoneType;
	}

	public Double getTimeMNF() {
		return timeMNF;
	}

	public void setTimeMNF(Double timeMNF) {
		this.timeMNF = timeMNF;
	}

	public Double getTimeProp() {
		return timeProp;
	}

	public void setTimeProp(Double timeProp) {
		this.timeProp = timeProp;
	}

	public Double getYesterdayMNF() {
		return yesterdayMNF;
	}

	public void setYesterdayMNF(Double yesterdayMNF) {
		this.yesterdayMNF = yesterdayMNF;
	}

	public Double getYesterdayGrow() {
		return yesterdayGrow;
	}

	public void setYesterdayGrow(Double yesterdayGrow) {
		this.yesterdayGrow = yesterdayGrow;
	}

	public Double getYesterdayProp() {
		return yesterdayProp;
	}

	public void setYesterdayProp(Double yesterdayProp) {
		this.yesterdayProp = yesterdayProp;
	}

	public Double getSevenDayMNF() {
		return sevenDayMNF;
	}

	public void setSevenDayMNF(Double sevenDayMNF) {
		this.sevenDayMNF = sevenDayMNF;
	}

	public Double getSevenDayGrow() {
		return sevenDayGrow;
	}

	public void setSevenDayGrow(Double sevenDayGrow) {
		this.sevenDayGrow = sevenDayGrow;
	}

	public Double getSevenDayProp() {
		return sevenDayProp;
	}

	public void setSevenDayProp(Double sevenDayProp) {
		this.sevenDayProp = sevenDayProp;
	}

	public Double getMonthMNF() {
		return monthMNF;
	}

	public void setMonthMNF(Double monthMNF) {
		this.monthMNF = monthMNF;
	}

	public Double getMonthGrow() {
		return monthGrow;
	}

	public void setMonthGrow(Double monthGrow) {
		this.monthGrow = monthGrow;
	}

	public Double getMonthProp() {
		return monthProp;
	}

	public void setMonthProp(Double monthProp) {
		this.monthProp = monthProp;
	}
	
	
	
}
