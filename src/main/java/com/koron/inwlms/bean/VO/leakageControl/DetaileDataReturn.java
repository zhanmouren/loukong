package com.koron.inwlms.bean.VO.leakageControl;

import java.util.List;

public class DetaileDataReturn {
	
	private List<TreeVO> dataList;
	
    private Integer page;
	
	private Integer pageCount;
	
	private Integer rowNumber;
	
	private Integer totalPage;

	public List<TreeVO> getDataList() {
		return dataList;
	}

	public void setDataList(List<TreeVO> dataList) {
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
