package com.koron.inwlms.bean.VO.report.statisticalReport;

import java.util.List;

public class ZoneMnfStatisticalVO {
	
	private List<ZoneMnfStatistical> dataList;
	
    private Integer page;
	
	private Integer pageCount;
	
	private Integer rowNumber;
	
	private Integer totalPage;

	public List<ZoneMnfStatistical> getDataList() {
		return dataList;
	}

	public void setDataList(List<ZoneMnfStatistical> dataList) {
		this.dataList = dataList;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	
	

}
