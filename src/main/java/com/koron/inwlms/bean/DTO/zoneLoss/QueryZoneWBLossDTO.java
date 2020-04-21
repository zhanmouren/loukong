package com.koron.inwlms.bean.DTO.zoneLoss;


/**|
 * 查询分区水平衡漏损数据DTO
 * @author csh
 * @Date 2020.03.09
 */
public class QueryZoneWBLossDTO {

	/**
	 * 分区编号
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
	
	/**
	 * 排序方式 eg: "lossRate" desc
	 */
	private String orderBy;

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

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	
}
