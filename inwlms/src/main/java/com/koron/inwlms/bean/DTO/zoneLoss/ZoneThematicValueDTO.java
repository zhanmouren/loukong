package com.koron.inwlms.bean.DTO.zoneLoss;

/**
 * 分区专题图指标DTO
 * @author csh
 * @Date 2020/04.15
 *
 */
public class ZoneThematicValueDTO {

	private String itemCode;
	
	private Integer zoneType;
	
	private Integer timeType;
	
	private Integer startTime;
	
	private Integer endTime;

	public String getItemCode() {
		return itemCode;
	}

	public Integer getZoneType() {
		return zoneType;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public void setZoneType(Integer zoneType) {
		this.zoneType = zoneType;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public Integer getTimeType() {
		return timeType;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}
	
}
