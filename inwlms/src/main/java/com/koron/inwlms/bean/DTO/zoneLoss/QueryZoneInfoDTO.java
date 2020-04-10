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
	public String zoneNo;
	
	/**
	 * 分区类型（1：一级、2：二级、3：DMA/PMA，4：虚拟分区）
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
