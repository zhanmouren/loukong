package com.koron.inwlms.bean.VO.indexData;

import java.util.List;

public class AreaInfoListVO<T> {

	private List<String> zoneList;
	
	private T dataList;

	public List<String> getZoneList() {
		return zoneList;
	}

	public void setZoneList(List<String> zoneList) {
		this.zoneList = zoneList;
	}

	public T getDataList() {
		return dataList;
	}

	public void setDataList(T dataList) {
		this.dataList = dataList;
	}
	
	
}
