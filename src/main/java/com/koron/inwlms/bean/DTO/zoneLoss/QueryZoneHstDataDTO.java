package com.koron.inwlms.bean.DTO.zoneLoss;

import java.util.List;

/**
 * 查询分区历史数据DTO
 * @author csh
 * @Date 2020.03.18
 */
public class QueryZoneHstDataDTO {

	/**
	 * 分区编号
	 */
	private String zoneNo;
	
	/**
	 * 分区类型（1：一级、2：二级、3：DMA/PMA，4：虚拟分区）
	 */
	private Integer zoneType;
	
	/**
	 * 指标编码集合
	 */
	private String codes;
	
	private Integer timeType;
	
	private Integer startTime;
	
	private Integer endTime;

	public String getZoneNo() {
		return zoneNo;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public Integer getZoneType() {
		return zoneType;
	}

	public void setZoneType(Integer zoneType) {
		this.zoneType = zoneType;
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

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}
	
}
