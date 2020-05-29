package com.koron.inwlms.bean.VO.leakageControl;

import java.util.List;

import com.koron.inwlms.bean.DTO.leakageControl.PageInfo;

public class AlarmProcessReturnVO {
	
	private List<AlarmProcessVO> alarmProcessList;
	
	private PageInfo query;

	

	public List<AlarmProcessVO> getAlarmProcessList() {
		return alarmProcessList;
	}

	public void setAlarmProcessList(List<AlarmProcessVO> alarmProcessList) {
		this.alarmProcessList = alarmProcessList;
	}

	public PageInfo getQuery() {
		return query;
	}

	public void setQuery(PageInfo query) {
		this.query = query;
	}
	
	

}
