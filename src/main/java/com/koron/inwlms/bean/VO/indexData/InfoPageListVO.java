package com.koron.inwlms.bean.VO.indexData;

import org.apache.poi.ss.formula.functions.T;

public class InfoPageListVO<T> {

	/**
	 * 已处理完成率
	 */
	private String completeRate;
	/**
	 * 未完成率
	 */
	private String unCompleteRate;
	/**
	 * 处理中率
	 */
	private String completeingRate;
	
	/*
	 * 处理及时率
	 */
	private String inTimeRate;
	
	/*
	 * 噪声比率
	 */
	private String voiceWarningRateStr;
	/*
	 * 超限比例
	 */
	private String overWaningRateStr;
	/*
	 * 离线报警
	 */
	private String offlineWarningRateStr;
	
	
	public String getVoiceWarningRateStr() {
		return voiceWarningRateStr;
	}



	public String getOverWaningRateStr() {
		return overWaningRateStr;
	}



	public String getOfflineWarningRateStr() {
		return offlineWarningRateStr;
	}



	public void setVoiceWarningRateStr(String voiceWarningRateStr) {
		this.voiceWarningRateStr = voiceWarningRateStr;
	}



	public void setOverWaningRateStr(String overWaningRateStr) {
		this.overWaningRateStr = overWaningRateStr;
	}



	public void setOfflineWarningRateStr(String offlineWarningRateStr) {
		this.offlineWarningRateStr = offlineWarningRateStr;
	}



	public String getInTimeRate() {
		return inTimeRate;
	}



	public void setInTimeRate(String inTimeRate) {
		this.inTimeRate = inTimeRate;
	}

	/**
	 * 列表数据
	 */
	private T dataList;
	
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

	

	

	public String getCompleteRate() {
		return completeRate;
	}



	public String getUnCompleteRate() {
		return unCompleteRate;
	}



	public String getCompleteingRate() {
		return completeingRate;
	}



	public void setCompleteRate(String completeRate) {
		this.completeRate = completeRate;
	}



	public void setUnCompleteRate(String unCompleteRate) {
		this.unCompleteRate = unCompleteRate;
	}



	public void setCompleteingRate(String completeingRate) {
		this.completeingRate = completeingRate;
	}



	public T getDataList() {
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

	public void setDataList(T dataList) {
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
