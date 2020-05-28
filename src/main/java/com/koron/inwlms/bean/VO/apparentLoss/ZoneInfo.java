package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 分区信息
 * @author csh
 *
 */
public class ZoneInfo {

	private String zoneNo;
	
	private String zoneName;
	
	private String pZoneNo;
	
	private String pZoneName;
	
	private String address;
	
	private String createTime;

	public String getZoneNo() {
		return zoneNo;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}

	public String getpZoneNo() {
		return pZoneNo;
	}

	public String getpZoneName() {
		return pZoneName;
	}

	public void setpZoneNo(String pZoneNo) {
		this.pZoneNo = pZoneNo;
	}

	public void setpZoneName(String pZoneName) {
		this.pZoneName = pZoneName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
