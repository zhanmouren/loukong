package com.koron.inwlms.bean.DTO.report;

/**
 * 查询监测水量存疑报表DTO
 * @author csh
 *
 */
public class LoggerFlowQuestionReportDTO {

	private String zoneNo;
	
	private String startTime;
	
	private String endTime;

	public String getZoneNo() {
		return zoneNo;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
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
