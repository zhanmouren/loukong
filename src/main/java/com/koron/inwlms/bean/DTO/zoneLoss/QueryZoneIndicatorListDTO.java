package com.koron.inwlms.bean.DTO.zoneLoss;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * 查询分区指标列表DTO
 * @author csh
 * @Date 2020.03.18
 *
 */
public class QueryZoneIndicatorListDTO extends BaseDTO{

	/**
	 * 分区类型（1：一级，2：二级，3：DMA/PMA）
	 */
	private Integer zoneType;
	
	/**
	 * 时间类型（3：月，4：年）
	 */
	private Integer timeType;
	
	/**
	 * 开始时间
	 */
	private Integer startTime;
	
	/**
	 * 结束时间
	 */
	private Integer endTime;

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
