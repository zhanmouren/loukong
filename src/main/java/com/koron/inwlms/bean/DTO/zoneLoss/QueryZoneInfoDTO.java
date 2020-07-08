package com.koron.inwlms.bean.DTO.zoneLoss;

/**
 * 查询分区信息DTO
 * @author csh
 * @Date 2020.03.18
 */
public class QueryZoneInfoDTO {

	/**
	 * 分区编号
	 */
	private String zoneNo;
	
	/**
	 * 分区名称
	 */
	private String zoneName;
	
	/**
	 * 分区类型（1：一级、2：二级、3：DMA/PMA，4：虚拟分区）
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
