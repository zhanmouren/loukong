package com.koron.inwlms.bean.DTO.leakageControl;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * 预警信息DTO
 * @author 刘刚
 *
 */

public class WarningInfDTO extends BaseDTO {

	/**
	 * 报警类型
	 */
	private String alarmType;
	
	/**
	 * 预警对象ID
	 */
	private int objectCode;
	
	/**
	 * 对象类型
	 */
	private String objectType;
	
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
	
	
	/**
	 * 分区编码
	 * @return
	 */
	private String areaCode;
	

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

	public int getObjectCode() {
		return objectCode;
	}

	public void setObjectCode(int objectCode) {
		this.objectCode = objectCode;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
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
