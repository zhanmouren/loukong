package com.koron.inwlms.bean.DTO.leakageControl;

public class WarningSchemeHisDataParam {
	/**
	 * 分区编码
	 */
	private String zoneCode;
	/**
	 * 检测点吗
	 */
	private String pointCode;
	/**
	 * 指标编码
	 */
	private String indexCode;
	/**
	 * 分区级别
	 */
	private String zoneGrade;
	/**
	 * 上限运算标志
	 */
	private Double maxIndex;
	/**
	 * 下限运算标志
	 */
	private Double minIndex;
	/**
	 * 0(加法)，1(减法)，2(乘法)，3(除法)
	 */
	private Integer maxFlag;
	/**
	 * 0(加法)，1(减法)，2(乘法)，3(除法)
	 */
	private Integer minFlag;
	

	public String getPointCode() {
		return pointCode;
	}

	public void setPointCode(String pointCode) {
		this.pointCode = pointCode;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public String getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}

	public String getZoneGrade() {
		return zoneGrade;
	}

	public void setZoneGrade(String zoneGrade) {
		this.zoneGrade = zoneGrade;
	}

	public Double getMaxIndex() {
		return maxIndex;
	}

	public void setMaxIndex(Double maxIndex) {
		this.maxIndex = maxIndex;
	}

	public Double getMinIndex() {
		return minIndex;
	}

	public void setMinIndex(Double minIndex) {
		this.minIndex = minIndex;
	}

	public Integer getMaxFlag() {
		return maxFlag;
	}

	public void setMaxFlag(Integer maxFlag) {
		this.maxFlag = maxFlag;
	}

	public Integer getMinFlag() {
		return minFlag;
	}

	public void setMinFlag(Integer minFlag) {
		this.minFlag = minFlag;
	}
	
	

}
