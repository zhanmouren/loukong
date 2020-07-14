package com.koron.inwlms.bean.VO.leakageControl;

import java.util.List;

import com.koron.inwlms.bean.DTO.leakageControl.WarningSchemeHisData;

public class EnvelopeDataVO {
	
	private List<WarningSchemeHisData> oldList;
	
	private List<WarningSchemeHisData> nowList;
	
	private Integer type;
	
	

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<WarningSchemeHisData> getOldList() {
		return oldList;
	}

	public void setOldList(List<WarningSchemeHisData> oldList) {
		this.oldList = oldList;
	}

	public List<WarningSchemeHisData> getNowList() {
		return nowList;
	}

	public void setNowList(List<WarningSchemeHisData> nowList) {
		this.nowList = nowList;
	}
	
	

}
