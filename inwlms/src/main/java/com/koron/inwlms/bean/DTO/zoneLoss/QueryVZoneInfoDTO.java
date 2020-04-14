package com.koron.inwlms.bean.DTO.zoneLoss;

/**
 * 查询虚拟分区信息DTO
 * @author csh
 * @Date 2020.04.13
 */
public class QueryVZoneInfoDTO {

	/**
	 * 分区编号
	 */
	public String zoneNo;
	
	/**
	 * 分区类型（1：加，2：减）
	 */
	public Integer zoneType;

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
	
	
}
