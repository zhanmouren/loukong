package com.koron.inwlms.bean.DTO.apparentLoss;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**|
 * 查询表观漏损列表DTO
 * @author csh
 * @Date 2020.03.09
 */
public class QueryALListDTO extends BaseDTO{

	/**
	 * zone编号
	 */
	private String zoneNo;
	
	/**
	 * 分区等级
	 */
	private Integer zoneRank;
	
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

	public String getZoneNo() {
		return zoneNo;
	}

	public Integer getZoneRank() {
		return zoneRank;
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

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public void setZoneRank(Integer zoneRank) {
		this.zoneRank = zoneRank;
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
