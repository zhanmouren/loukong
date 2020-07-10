package com.koron.inwlms.bean.VO.leakageControl;

import java.util.Date;

public class WarningTask {
	
	private Integer id;
	
	private Integer type;
	
	private Integer state;
	
	private Date time;
	
	private Integer zoneTime;
	
	private Date createTime;
	
	private Date updateTime;
	

	public Integer getZoneTime() {
		return zoneTime;
	}

	public void setZoneTime(Integer zoneTime) {
		this.zoneTime = zoneTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	

}
