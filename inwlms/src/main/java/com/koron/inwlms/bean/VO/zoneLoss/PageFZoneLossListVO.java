package com.koron.inwlms.bean.VO.zoneLoss;

import java.util.List;

/**
 * 一级分区漏损分页列表VO
 * @author csh
 * @Date 2020/04/09
 */
public class PageFZoneLossListVO {

	/**
	 * 列表数据
	 */
	private List<FZoneLossListVO> dataList;


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

	public List<FZoneLossListVO> getDataList() {
		return dataList;
	}

	public Integer getPage() {
		return page;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public Integer getRowNumber() {
		return rowNumber;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setDataList(List<FZoneLossListVO> dataList) {
		this.dataList = dataList;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	
	
}
