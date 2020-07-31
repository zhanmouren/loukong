package com.koron.inwlms.bean.DTO.report;

import java.util.Date;


public class ZoneHourDTO {
	
    private String code;
	
	/**
	 * 时间类型 2-日，3-月，4-年
	 */
	private Integer timeType;
	
	private Date startTime;
	
	private Date endTime;
	
	private String zoneCode;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getTimeType() {
		return timeType;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}
	
	

}
