package com.koron.inwlms.bean.DTO.apparentLoss;


/**|
 * 查询表观漏损DTO
 * @author csh
 * @Date 2020.03.09
 */
public class QueryALDTO{

	/**
	 * 分区编号
	 */
	public String zoneNo;
	
	/**
	 * 分区等级
	 */
	private Integer zoneRank;
	
	/**
	 * 时间类型（3：月，4：年）
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

	public Integer getTimeType() {
		return timeType;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public Integer getEndTime() {
		return endTime;
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

	public String getZoneNo() {
		return zoneNo;
	}

	public Integer getZoneRank() {
		return zoneRank;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public void setZoneRank(Integer zoneRank) {
		this.zoneRank = zoneRank;
	}
	
}
