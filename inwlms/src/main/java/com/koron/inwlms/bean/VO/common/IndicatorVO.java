package com.koron.inwlms.bean.VO.common;

/**
 * 指标VO
 * @author csh
 * @Date 2020/04/014
 */
public class IndicatorVO {

	private String code;
	
	private Double value;
	
	private Integer timeId;
	
	private String zoneNo;

	public String getCode() {
		return code;
	}

	public Double getValue() {
		return value;
	}

	public Integer getTimeId() {
		return timeId;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public void setTimeId(Integer timeId) {
		this.timeId = timeId;
	}

	public String getZoneNo() {
		return zoneNo;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}
	
}
