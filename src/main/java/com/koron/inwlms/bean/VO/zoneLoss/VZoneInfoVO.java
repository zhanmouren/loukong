package com.koron.inwlms.bean.VO.zoneLoss;

import java.util.List;

/**
 * 虚拟分区信息VO
 * @author csh
 * @Date 2020.04.13
 */
public class VZoneInfoVO {

	/**
	 * 分区编号
	 */
	private String zoneNo;
	
	/**
	 * 分区名称
	 */
	private String zoneName;
	
	/**
	 * 分区等级
	 */
	private String zoneRank;
	
	/**
	 * 父分区编号
	 */
	private String pZoneNo;
	
	/**
	 * 父分区名称
	 */
	private String pZoneName;
	
	/**
	 * 分区成员编号
	 */
	private String cZoneNo;
	
	/**
	 * 地址
	 */
	private String address;
	
	private String createTime;
	
	/**
	 * 虚拟分区类型，Non二级in一级
		NonDMA in一级
		NonDMA in 二级
		Non子DMA in DMA
	 */
	private String virtualZoneType;
	
	/**
	 * 主实际分区
	 */
	private String masCode;
	
	/**
	 * 子实际分区字符串
	 */
	private String secCode;
	
	/**
	 * 子实际分区集合
	 */
	private String[] secCodes;

	public String getZoneNo() {
		return zoneNo;
	}

	public String getZoneName() {
		return zoneName;
	}

	public String getZoneRank() {
		return zoneRank;
	}

	public String getpZoneNo() {
		return pZoneNo;
	}

	public String getpZoneName() {
		return pZoneName;
	}

	public String getcZoneNo() {
		return cZoneNo;
	}

	public String getAddress() {
		return address;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public void setZoneRank(String zoneRank) {
		this.zoneRank = zoneRank;
	}

	public void setpZoneNo(String pZoneNo) {
		this.pZoneNo = pZoneNo;
	}

	public void setpZoneName(String pZoneName) {
		this.pZoneName = pZoneName;
	}

	public void setcZoneNo(String cZoneNo) {
		this.cZoneNo = cZoneNo;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getVirtualZoneType() {
		return virtualZoneType;
	}

	public void setVirtualZoneType(String virtualZoneType) {
		this.virtualZoneType = virtualZoneType;
	}

	public String getMasCode() {
		return masCode;
	}

	public void setMasCode(String masCode) {
		this.masCode = masCode;
	}

	public String getSecCode() {
		return secCode;
	}

	public void setSecCode(String secCode) {
		this.secCode = secCode;
	}

	public String[] getSecCodes() {
		return secCodes;
	}

	public void setSecCodes(String[] secCodes) {
		this.secCodes = secCodes;
	}


	
}
