package com.koron.inwlms.bean.DTO.leakageControl;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * 
 * @author 刘刚
 *
 */
public class EventInfoDTO extends BaseDTO{
	
	/**
	 * 对象类型
	 */
	private String objectType;
	/**
	 * 事项类型
	 */
	private String type;
	/**
	 * 事项子类型
	 */
	private String subtype;
	/**
	 * 对象编码
	 */
	private String objectCode;
	/**
	 * 第一分区
	 */
	private String firstPartition;
	/**
	 * 第二分区
	 */
	private String secondPartition;
	/**
	 * DMA分区
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

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public String getObjectCode() {
		return objectCode;
	}

	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}

	public String getFirstPartition() {
		return firstPartition;
	}

	public void setFirstPartition(String firstPartition) {
		this.firstPartition = firstPartition;
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
