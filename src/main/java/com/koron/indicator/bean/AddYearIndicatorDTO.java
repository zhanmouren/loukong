package com.koron.indicator.bean;


/**
 * 年指标DTO
 * @author csh
 * @Date 2020/04/20
 */
public class AddYearIndicatorDTO {

	private Integer year;
	
	private String code;
	
	private String zoneNo;
	
	private String zoneRank;
	
	private Long analysisValue;
	
	private Long value;
	
	private String method;
	
	private String createBy;
	
	private String updateBy;

	public String getCode() {
		return code;
	}

	public String getCreateBy() {
		return createBy;
	}

	public String getUpdateBy() {
		return updateBy;
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

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}


	public Long getAnalysisValue() {
		return analysisValue;
	}


	public Long getValue() {
		return value;
	}


	public void setAnalysisValue(Long analysisValue) {
		this.analysisValue = analysisValue;
	}


	public void setValue(Long value) {
		this.value = value;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
}
