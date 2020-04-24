package com.koron.indicator.bean;

import java.util.Date;

/**
 * 日指标DTO
 * @author csh
 * @Date 2020/04/20
 */
public class AddDayIndicatorDTO {

	private Date analysisDate;
	
	private String code;
	
	private Integer analysisValue;
	
	private String zoneNo;
	
	private String zoneRank;
	
	private Integer value;
	
	private String method;
	
	private String createBy;
	
	private String updateBy;

	public Date getAnalysisDate() {
		return analysisDate;
	}

	public String getCode() {
		return code;
	}

	public String getCreateBy() {
		return createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setAnalysisDate(Date analysisDate) {
		this.analysisDate = analysisDate;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}


	public Integer getAnalysisValue() {
		return analysisValue;
	}

	public Integer getValue() {
		return value;
	}

	public void setAnalysisValue(Integer analysisValue) {
		this.analysisValue = analysisValue;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getZoneNo() {
		return zoneNo;
	}

	public String getZoneRank() {
		return zoneRank;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public void setZoneRank(String zoneRank) {
		this.zoneRank = zoneRank;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
}
