package com.koron.inwlms.bean.VO.intellectPartition;

import java.util.List;

import com.koron.inwlms.bean.DTO.leakageControl.PageInfo;

public class TotalSchemeDetReturn {
	
	private List<TotalSchemeDetVO> totalSchemeDetList;
	
	private PageInfo query;

	public List<TotalSchemeDetVO> getTotalSchemeDetList() {
		return totalSchemeDetList;
	}

	public void setTotalSchemeDetList(List<TotalSchemeDetVO> totalSchemeDetList) {
		this.totalSchemeDetList = totalSchemeDetList;
	}

	public PageInfo getQuery() {
		return query;
	}

	public void setQuery(PageInfo query) {
		this.query = query;
	}
	 

}
