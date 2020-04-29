package com.koron.inwlms.bean.DTO.indexData;

import java.util.List;

/**
 * 指标DTO
 * @author 小詹
 * @Date 2020/04/24
 */
public class IndicatorNewDTO {
	//代表类型
	private Integer type;
	
    private List<String> codes;
	
	/**
	 * 时间类型 2-日，3-月，4-年
	 */
	private Integer timeType;
	
	private Integer startTime;
	
	private Integer endTime;
	
	private List<String> zoneCodes;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

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
