package com.koron.inwlms.bean.VO.report.statisticalReport;

public class ZoneMnfStatistical {
	
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
	 * 最小夜间流量最小值
	 */
	private Double minMnf;
	/**
	 * 最小夜间流量最大值
	 */
	private Double maxMnf;
	/**
	 * 最小夜间流量平均值
	 */
	private Double avgMnf;
	/**
	 * 最小夜间流量中位数
	 */
	private Double medMnf;
	/**
	 * 最小夜间流量标准差
	 */
	private Double standardMnf;
	/**
	 * 最小夜间流量/供水量   平均值
	 */
	private Double avgProp;
	/**
	 * 单位户数最小夜间流量平均值
	 */
	private Double avgHouseMnf;
	/**
	 * 单位管长最小夜间流量平均值
	 */
	private Double avgLengthMnf;

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

	public Double getMinMnf() {
		return minMnf;
	}

	public void setMinMnf(Double minMnf) {
		this.minMnf = minMnf;
	}

	public Double getMaxMnf() {
		return maxMnf;
	}

	public void setMaxMnf(Double maxMnf) {
		this.maxMnf = maxMnf;
	}

	public Double getAvgMnf() {
		return avgMnf;
	}

	public void setAvgMnf(Double avgMnf) {
		this.avgMnf = avgMnf;
	}

	public Double getMedMnf() {
		return medMnf;
	}

	public void setMedMnf(Double medMnf) {
		this.medMnf = medMnf;
	}

	public Double getStandardMnf() {
		return standardMnf;
	}

	public void setStandardMnf(Double standardMnf) {
		this.standardMnf = standardMnf;
	}

	public Double getAvgProp() {
		return avgProp;
	}

	public void setAvgProp(Double avgProp) {
		this.avgProp = avgProp;
	}

	public Double getAvgHouseMnf() {
		return avgHouseMnf;
	}

	public void setAvgHouseMnf(Double avgHouseMnf) {
		this.avgHouseMnf = avgHouseMnf;
	}

	public Double getAvgLengthMnf() {
		return avgLengthMnf;
	}

	public void setAvgLengthMnf(Double avgLengthMnf) {
		this.avgLengthMnf = avgLengthMnf;
	}
	
	

}
