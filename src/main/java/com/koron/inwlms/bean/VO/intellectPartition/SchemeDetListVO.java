package com.koron.inwlms.bean.VO.intellectPartition;

import java.util.List;

import com.koron.inwlms.bean.DTO.leakageControl.PageInfo;

public class SchemeDetListVO {

	private List<SchemeDet> schemeList;
	
	private PageInfo query;

	public List<SchemeDet> getSchemeList() {
		return schemeList;
	}

	public void setSchemeList(List<SchemeDet> schemeList) {
		this.schemeList = schemeList;
	}

	public PageInfo getQuery() {
		return query;
	}

	public void setQuery(PageInfo query) {
		this.query = query;
	}
	
	
	
}
