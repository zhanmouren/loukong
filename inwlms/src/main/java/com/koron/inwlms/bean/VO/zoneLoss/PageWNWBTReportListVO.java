package com.koron.inwlms.bean.VO.zoneLoss;


import java.util.List;

/**
 * 表观漏损分页列表模板VO
 * @author csh
 * @createTime 2020/04/08
 *
 */
public class PageWNWBTReportListVO {
	/**
	 * 列表数据
	 */
	private List<WNWBTReportListVO> dataList;


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

	
	public List<WNWBTReportListVO> getDataList() {
		return dataList;
	}

	public void setDataList(List<WNWBTReportListVO> dataList) {
		this.dataList = dataList;
	}

	/**
	 * 获取 页数
	 */
	public Integer getPage() {
		return this.page;
	}

	/**
	 * 设置 页数
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * 获取 每页记录数
	 */
	public Integer getPageCount() {
		return this.pageCount;
	}

	/**
	 * 设置 每页记录数
	 */
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * 获取 总行数
	 */
	public Integer getRowNumber() {
		return this.rowNumber;
	}

	/**
	 * 设置 总行数
	 */
	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	/**
	 * 获取 总页数
	 */
	public Integer getTotalPage() {
		return this.totalPage;
	}

	/**
	 * 设置 总页数
	 */
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
}
