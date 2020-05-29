package com.koron.inwlms.bean.VO.leakageControl;

import java.util.List;

import com.koron.inwlms.bean.DTO.leakageControl.PageInfo;

public class EventInfoListReturnVO {
	
	private List<EventInfo> eventInfoList;
	
	private PageInfo query;

	public List<EventInfo> getEventInfoList() {
		return eventInfoList;
	}

	public void setEventInfoList(List<EventInfo> eventInfoList) {
		this.eventInfoList = eventInfoList;
	}

	public PageInfo getQuery() {
		return query;
	}

	public void setQuery(PageInfo query) {
		this.query = query;
	}
	
	

}
