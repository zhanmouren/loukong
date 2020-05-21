package com.koron.inwlms.bean.VO.sysManager;

import java.util.List;

public class PagePositionListVO {

	/**
	 * 列表数据
	 */
	private List<PositionVO> dataList;


	/**
	 * 页数
	 */
	private Integer page;

	/**
	 * 每页记录数
	 */
	private Integer pageCount;

	/**
	 * 总行数
	 */
	private Integer rowNumber;

	/**
	 * 总页数
	 */
	private Integer totalPage;

	public List<PositionVO> getDataList() {
		return dataList;
	}

	public void setDataList(List<PositionVO> dataList) {
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
