package com.koron.inwlms.bean.VO.apparentLoss;

import java.util.List;

/**
 * 分区数据集合
 * @author csh
 * @Date 2020/03/26
 */
public class ZoneDatas {

	private List<String> zoneNameList;
	
	private List<Double> zoneDataList;

	public List<String> getZoneNameList() {
		return zoneNameList;
	}

	public List<Double> getZoneDataList() {
		return zoneDataList;
	}

	public void setZoneNameList(List<String> zoneNameList) {
		this.zoneNameList = zoneNameList;
	}

	public void setZoneDataList(List<Double> zoneDataList) {
		this.zoneDataList = zoneDataList;
	}
	
}
