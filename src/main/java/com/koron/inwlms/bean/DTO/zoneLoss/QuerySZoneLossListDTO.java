package com.koron.inwlms.bean.DTO.zoneLoss;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * 二级分区漏损列表DTO
 * @author csh
 * @Date 2020.03.09
 */
public class QuerySZoneLossListDTO extends BaseDTO{

	private Double minNrw;
	
	private Double maxNrw;
	
	private Double minUfwc;
	
	private Double maxUfwc;
	
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
	
	/**
	 * 分区编码
	 */
	private String zoneNo;
	
	/**
	 * 警报状态（0：不报警，1：报警）
	 */
	private Integer alarmStatus;

	public Double getMinNrw() {
		return minNrw;
	}

	public Double getMaxNrw() {
		return maxNrw;
	}

	public Double getMinUfwc() {
		return minUfwc;
	}

	public Double getMaxUfwc() {
		return maxUfwc;
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

	public String getZoneNo() {
		return zoneNo;
	}

	public Integer getAlarmStatus() {
		return alarmStatus;
	}

	public void setMinNrw(Double minNrw) {
		this.minNrw = minNrw;
	}

	public void setMaxNrw(Double maxNrw) {
		this.maxNrw = maxNrw;
	}

	public void setMinUfwc(Double minUfwc) {
		this.minUfwc = minUfwc;
	}

	public void setMaxUfwc(Double maxUfwc) {
		this.maxUfwc = maxUfwc;
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

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public void setAlarmStatus(Integer alarmStatus) {
		this.alarmStatus = alarmStatus;
	}
	
	
}
