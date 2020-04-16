package com.koron.inwlms.bean.DTO.leakageControl;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

public class AlarmProcessDTO extends BaseDTO{

	private Integer id;
	/**
	 * 预警类型
	 */
	private String alarmType;
	
	/**
	 * 任务处理状态
	 */
	private String processState;
	
	/**
	 * 推荐策略
	 */
	private String recommendStrategy;
	
	/**
	 * 第一分区
	 */
	private String firstPartion;
	
	/**
	 * 第二分区
	 */
	private String secondPartition;
	
	/**
	 * DMA编码
	 */
	private String dmaCode;
	
	/**
	 * 开始时间
	 */
	private String startTime;
	
	/**
	 * 结束时间
	 */
	private String endTime;
	
	private String areaCode;
	
	private String state;
	

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public String getProcessState() {
		return processState;
	}

	public void setProcessState(String processState) {
		this.processState = processState;
	}

	

	public String getRecommendStrategy() {
		return recommendStrategy;
	}

	public void setRecommendStrategy(String recommendStrategy) {
		this.recommendStrategy = recommendStrategy;
	}

	public String getFirstPartion() {
		return firstPartion;
	}

	public void setFirstPartion(String firstPartion) {
		this.firstPartion = firstPartion;
	}

	public String getSecondPartition() {
		return secondPartition;
	}

	public void setSecondPartition(String secondPartition) {
		this.secondPartition = secondPartition;
	}

	public String getDmaCode() {
		return dmaCode;
	}

	public void setDmaCode(String dmaCode) {
		this.dmaCode = dmaCode;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	
}
