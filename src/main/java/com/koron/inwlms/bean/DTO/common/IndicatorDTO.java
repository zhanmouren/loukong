package com.koron.inwlms.bean.DTO.common;

import java.util.List;


/**
 * 指标DTO
 * @author csh
 * @Date 2020/04/14
 */
public class IndicatorDTO {

	private List<String> codes;
	
	private Integer timeType;
	
	private Integer startTime;
	
	private Integer endTime;
	
	private List<String> zoneCodes;

	public List<String> getCodes() {
		return codes;
	}

	public Integer getTimeType() {
		return timeType;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public List<String> getZoneCodes() {
		return zoneCodes;
	}

	public void setZoneCodes(List<String> zoneCodes) {
		this.zoneCodes = zoneCodes;
	}

	
}
