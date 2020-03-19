package com.koron.inwlms.bean.DTO.apparentLoss;


/**|
 * 查询表观漏损DTO
 * @author csh
 * @Date 2020.03.09
 */
public class QueryALDTO{

	/**
	 * 一级分区
	 */
	public String firstLevelZone;
	
	/**
	 * 二级分许
	 */
	public String secondeLevelZone;
	
	/**
	 * DMA编号
	 */
	public String dmaNo;
	
	/**
	 * 时间类型（0：分 ，1：时，2：日，3：月，4：年）
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

	public String getFirstLevelZone() {
		return firstLevelZone;
	}

	public String getSecondeLevelZone() {
		return secondeLevelZone;
	}

	public String getDmaNo() {
		return dmaNo;
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

	public void setFirstLevelZone(String firstLevelZone) {
		this.firstLevelZone = firstLevelZone;
	}

	public void setSecondeLevelZone(String secondeLevelZone) {
		this.secondeLevelZone = secondeLevelZone;
	}

	public void setDmaNo(String dmaNo) {
		this.dmaNo = dmaNo;
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
