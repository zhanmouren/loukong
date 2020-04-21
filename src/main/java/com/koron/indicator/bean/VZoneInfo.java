package com.koron.indicator.bean;

/**
 * 虚拟分区信息
 * @author csh
 *
 */
public class VZoneInfo {

	/**
	 * 虚拟分区编号
	 */
	private String zoneNo;
	
	/**
	 * 虚拟分区类型，1-加，2-减
	 */
	private Integer vType;
	
	/**
	 * 实际主分区编号
	 */
	private String masCode;
	
	/**
	 * 实际辅分区编号
	 */
	private String secCode;

	public String getZoneNo() {
		return zoneNo;
	}

	public Integer getvType() {
		return vType;
	}

	public String getMasCode() {
		return masCode;
	}

	public String getSecCode() {
		return secCode;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public void setvType(Integer vType) {
		this.vType = vType;
	}

	public void setMasCode(String masCode) {
		this.masCode = masCode;
	}

	public void setSecCode(String secCode) {
		this.secCode = secCode;
	}
	
	
}
