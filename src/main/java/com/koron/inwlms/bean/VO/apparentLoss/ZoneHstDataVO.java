package com.koron.inwlms.bean.VO.apparentLoss;

import java.util.List;

/**
 * 分区历史数据VO
 * @author csh
 * @Date 2020.04.9
 */
public class ZoneHstDataVO {

	private String zoneNo;
	
	private String zoneName;
	
	private List<ZoneHstIndicData> indicatorData;

	public String getZoneNo() {
		return zoneNo;
	}

	public String getZoneName() {
		return zoneName;
	}

	public List<ZoneHstIndicData> getIndicatorData() {
		return indicatorData;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public void setIndicatorData(List<ZoneHstIndicData> indicatorData) {
		this.indicatorData = indicatorData;
	}
	
	
}
