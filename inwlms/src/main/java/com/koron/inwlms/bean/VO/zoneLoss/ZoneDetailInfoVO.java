package com.koron.inwlms.bean.VO.zoneLoss;

/**
 * 分区详情信息VO
 * @author csh
 * @Date 2020/04/09
 */
public class ZoneDetailInfoVO {

	private String zoneNo;
	
	private String zoneName;
	
	private Integer zoneRank;
	
	private String pZoneNo;
	
	private String pZoneName;
	
	private String address;
	
	private String createTime;
	
	private String updateTime;
	
	private Integer userNum;
	
	private Double pipeLength;
	
	private Double area;

	public String getZoneNo() {
		return zoneNo;
	}

	public String getZoneName() {
		return zoneName;
	}

	public Integer getZoneRank() {
		return zoneRank;
	}

	public String getpZoneNo() {
		return pZoneNo;
	}

	public String getpZoneName() {
		return pZoneName;
	}

	public String getAddress() {
		return address;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}


	public Double getPipeLength() {
		return pipeLength;
	}

	public Double getArea() {
		return area;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public void setZoneRank(Integer zoneRank) {
		this.zoneRank = zoneRank;
	}

	public void setpZoneNo(String pZoneNo) {
		this.pZoneNo = pZoneNo;
	}

	public void setpZoneName(String pZoneName) {
		this.pZoneName = pZoneName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}


	public void setPipeLength(Double pipeLength) {
		this.pipeLength = pipeLength;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Integer getUserNum() {
		return userNum;
	}

	public void setUserNum(Integer userNum) {
		this.userNum = userNum;
	}
	
}
