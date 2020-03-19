package com.koron.inwlms.bean.DTO.zoneLoss;

/**
 * 查询分区指标列表DTO
 * @author csh
 * @Date 2020.03.18
 *
 */
public class QueryZoneIndicatorListDTO {

	/**
	 * 分区类型（1：一级，2：二级，3：DMA/PMA）
	 */
	public Integer zoneType;
	
	/**
	 * 时间类型（0：分，1：时，2：日，3：月，4：年）
	 */
	public Integer timeType;
	
	/**
	 * 开始时间
	 */
	public Integer startTime;
	
	/**
	 * 结束时间
	 */
	public Integer endTime;

	public Integer getZoneType() {
		return zoneType;
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

	public void setZoneType(Integer zoneType) {
		this.zoneType = zoneType;
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
	
}
