package com.koron.inwlms.bean.VO.leakageControl;

import java.util.List;

import com.koron.inwlms.bean.DTO.leakageControl.PageInfo;

public class AlertSchemeListReturnVO {
	
	private List<AlertSchemeListVO> alertSchemeList;
	
	private PageInfo query;

	public List<AlertSchemeListVO> getAlertSchemeList() {
		return alertSchemeList;
	}

	public void setAlertSchemeList(List<AlertSchemeListVO> alertSchemeList) {
		this.alertSchemeList = alertSchemeList;
	}

	public PageInfo getQuery() {
		return query;
	}

	public void setQuery(PageInfo query) {
		this.query = query;
	}
	
	

}
