package com.koron.inwlms.bean.VO.leakageControl;

import java.util.Date;

public class ZoneAndPoint {
	
	private Integer id;
	
	private String zoneNo;
	
	private String pointNo;
	
	private String type;
	
	private Integer pointType;
	
	private Integer taskid;
	
	private Date integratedtime;
	
	private String channelType;
	
	private String BatchNo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getZoneNo() {
		return zoneNo;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public String getPointNo() {
		return pointNo;
	}

	public void setPointNo(String pointNo) {
		this.pointNo = pointNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPointType() {
		return pointType;
	}

	public void setPointType(Integer pointType) {
		this.pointType = pointType;
	}

	public Integer getTaskid() {
		return taskid;
	}

	public void setTaskid(Integer taskid) {
		this.taskid = taskid;
	}

	public Date getIntegratedtime() {
		return integratedtime;
	}

	public void setIntegratedtime(Date integratedtime) {
		this.integratedtime = integratedtime;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getBatchNo() {
		return BatchNo;
	}

	public void setBatchNo(String batchNo) {
		BatchNo = batchNo;
	}
	
	

}
