package com.koron.inwlms.bean.DTO.report;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

public class ZoneMnfDTO extends BaseDTO {
	
	private String zoneCode;
	
	private String zoneGrade;
	
	private String startTime;
	
	private String endTime;
	/**
	 * 分析项
	 */
	private String analysisCode;
	/**
	 * 统计类型 0漏点数,1漏水量
	 */
	private String stattype;

	public String getAnalysisCode() {
		return analysisCode;
	}

	public void setAnalysisCode(String analysisCode) {
		this.analysisCode = analysisCode;
	}

	public String getStattype() {
		return stattype;
	}

	public void setStattype(String stattype) {
		this.stattype = stattype;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public String getZoneGrade() {
		return zoneGrade;
	}

	public void setZoneGrade(String zoneGrade) {
		this.zoneGrade = zoneGrade;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
	

}
