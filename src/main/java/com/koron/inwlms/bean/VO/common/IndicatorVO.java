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
	
	//首页专用
	private String type;
	
	private String name;
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
