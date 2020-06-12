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
	/**
	 * 任务总数
	 */
	private Integer taskNum;
	
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
	//离线报警总数
	private Integer offlineWarningNum;
	
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

	public void setDataList(T dataList) {
		this.dataList = dataList;
	}




	public Integer getOfflineWarningNum() {
		return offlineWarningNum;
	}



	public void setOfflineWarningNum(Integer offlineWarningNum) {
		this.offlineWarningNum = offlineWarningNum;
	}



	public Integer getTaskNum() {
		return taskNum;
	}



	public void setTaskNum(Integer taskNum) {
		this.taskNum = taskNum;
	}



	public T getDataList() {
		return dataList;
	}

}
