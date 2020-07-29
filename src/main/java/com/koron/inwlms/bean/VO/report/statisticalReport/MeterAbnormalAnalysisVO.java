package com.koron.inwlms.bean.VO.report.statisticalReport;

import java.util.List;
import java.util.Map;

public class MeterAbnormalAnalysisVO {
	
	private List<Map<String,Object>> dataList;
	
    private Integer page;
	
	private Integer pageCount;
	
	private Integer rowNumber;
	
	private Integer totalPage;

	public List<Map<String, Object>> getDataList() {
		return dataList;
	}

	public void setDataList(List<Map<String, Object>> dataList) {
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
