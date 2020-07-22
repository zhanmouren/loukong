package com.koron.inwlms.bean.DTO.report;

/**
 * 压力流量监测设备可操作性报表DTO
 * @author csh
 *
 */
public class PFLoggerOperabilityReportDTO {

	/**
	 * 分区编号
	 */
	private String zoneNo;
	
	/**
	 * 分区等级
	 */
	private Integer zoneRank;
	
	/**
	 * 开始时间
	 */
	private Integer startTime;
	
	/**
	 * 结束时间
	 */
	private Integer endTime;

	public String getZoneNo() {
		return zoneNo;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public Integer getZoneRank() {
		return zoneRank;
	}

	public void setZoneRank(Integer zoneRank) {
		this.zoneRank = zoneRank;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}
	
	
}
