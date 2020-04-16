package com.koron.inwlms.bean.VO.leakageControl;

import java.util.List;

import com.koron.inwlms.bean.DTO.leakageControl.PageInfo;


public class AlarmMessageReturnVO {

	private List<AlarmMessageVO> alarmMessageList;
	
	private PageInfo query;

	public List<AlarmMessageVO> getAlarmMessageList() {
		return alarmMessageList;
	}

	public void setAlarmMessageList(List<AlarmMessageVO> alarmMessageList) {
		this.alarmMessageList = alarmMessageList;
	}

	public PageInfo getQuery() {
		return query;
	}

	public void setQuery(PageInfo query) {
		this.query = query;
	}
	
	
}
