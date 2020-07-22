package com.koron.inwlms.bean.DTO.report;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * 压力流量监测设备列表报告DTO
 * @author csh
 *
 */
public class PFLoggerListReportDTO extends BaseDTO{

	private String zoneNo;
	
	private Integer zoneRank;

	public String getZoneNo() {
		return zoneNo;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public Integer getZoneRank() {
		return zoneRank;
	}

	public void setZoneRank(Integer zoneRank) {
		this.zoneRank = zoneRank;
	}

	
	
}
