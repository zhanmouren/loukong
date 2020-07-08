package com.koron.inwlms.bean.DTO.zoneLoss;

/**
 * 查询虚拟分区信息DTO
 * @author csh
 * @Date 2020.04.13
 */
public class QueryVZoneInfoDTO {

	/**
	 * 虚拟分区编号
	 */
	private String zoneNo;
	
	/**
	 * 虚拟分区名称
	 */
	private String zoneName;
	
	/**
	 * 分区类型（1：加，2：减）
	 */
	private Integer zoneType;

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

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	
	
}
