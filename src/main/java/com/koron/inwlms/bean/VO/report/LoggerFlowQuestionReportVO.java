package com.koron.inwlms.bean.VO.report;

/**
 * 监测水量存疑VO
 * @author csh
 *
 */
public class LoggerFlowQuestionReportVO {

	 private String zoneNo;

	 private String loggerNo;

	 private String address;

	 private Integer missingData;

	 private Integer quality;

	 private Integer flow;

	 private Integer major;

	 private Integer abnoraml;

	 private Integer doubts;

	 private Double rate;

	 private Integer days;


	public String getZoneNo() {
		return zoneNo;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public String getLoggerNo() {
		return loggerNo;
	}

	public void setLoggerNo(String loggerNo) {
		this.loggerNo = loggerNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getMissingData() {
		return missingData;
	}

	public void setMissingData(Integer missingData) {
		this.missingData = missingData;
	}

	public Integer getQuality() {
		return quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	public Integer getFlow() {
		return flow;
	}

	public void setFlow(Integer flow) {
		this.flow = flow;
	}

	public Integer getMajor() {
		return major;
	}

	public void setMajor(Integer major) {
		this.major = major;
	}

	public Integer getAbnoraml() {
		return abnoraml;
	}

	public void setAbnoraml(Integer abnoraml) {
		this.abnoraml = abnoraml;
	}

	public Integer getDoubts() {
		return doubts;
	}

	public void setDoubts(Integer doubts) {
		this.doubts = doubts;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}   
	 
	 
}
