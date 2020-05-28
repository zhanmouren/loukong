package com.koron.inwlms.bean.DTO.leakageControl;

import java.util.Date;

public class BasicDataParam {
	/**
	 * 测站编码
	 */
	private String stationCode;
	
	private String code;
	
	private Date startTime;
	
	private Date endTime;

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
	
	

}
