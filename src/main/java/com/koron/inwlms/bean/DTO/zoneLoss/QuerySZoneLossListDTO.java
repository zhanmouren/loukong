package com.koron.inwlms.bean.DTO.zoneLoss;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * 二级分区漏损列表DTO
 * @author csh
 * @Date 2020.03.09
 */
public class QuerySZoneLossListDTO extends BaseDTO{

	private String minNrw;
	
	private String maxNrw;
	
	private String minWl;
	
	private String maxWl;
	
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
	
	private Integer zoneRank;


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

	public Integer getZoneRank() {
		return zoneRank;
	}

	public void setZoneRank(Integer zoneRank) {
		this.zoneRank = zoneRank;
	}

	public String getMinNrw() {
		return minNrw;
	}

	public void setMinNrw(String minNrw) {
		this.minNrw = minNrw;
	}

	public String getMaxNrw() {
		return maxNrw;
	}

	public void setMaxNrw(String maxNrw) {
		this.maxNrw = maxNrw;
	}

	public String getMinWl() {
		return minWl;
	}

	public void setMinWl(String minWl) {
		this.minWl = minWl;
	}

	public String getMaxWl() {
		return maxWl;
	}

	public void setMaxWl(String maxWl) {
		this.maxWl = maxWl;
	}
	
	
}
