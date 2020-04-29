package com.koron.inwlms.bean.VO.leakageControl;

import java.util.List;

import com.koron.inwlms.bean.DTO.leakageControl.PageInfo;
import com.koron.inwlms.bean.VO.sysManager.DataDicVO;

/**
 * 
 * @author 刘刚
 *
 */
public class EventSubtypeVO {
	private List<DataDicVO> eventSubtypeList;
	
	private PageInfo query;

	public List<DataDicVO> getEventSubtypeList() {
		return eventSubtypeList;
	}

	public void setEventSubtypeList(List<DataDicVO> eventSubtypeList) {
		this.eventSubtypeList = eventSubtypeList;
	}

	public PageInfo getQuery() {
		return query;
	}

	public void setQuery(PageInfo query) {
		this.query = query;
	}
	
	

}
