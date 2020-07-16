package com.koron.inwlms.bean.VO.intellectPartition;

import java.util.List;

import com.koron.inwlms.bean.DTO.leakageControl.PageInfo;

public class TotalSchemeDetReturn {
	
	private List<TotalSchemeDetVO> totalSchemeDetList;
	
    private Integer page;
	
	private Integer pageCount;
	
	private Integer rowNumber;
	
	private Integer totalPage;
	

	public List<TotalSchemeDetVO> getTotalSchemeDetList() {
		return totalSchemeDetList;
	}

	public void setTotalSchemeDetList(List<TotalSchemeDetVO> totalSchemeDetList) {
		this.totalSchemeDetList = totalSchemeDetList;
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
