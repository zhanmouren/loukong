package com.koron.inwlms.bean.VO.indexData;

import org.apache.poi.ss.formula.functions.T;

public class InfoPageListVO<T> {

	
	/**
	 * 处理中条数
	 */
	private Integer completeingNum;
	/**
	 * 已处理完成条数
	 */
	private Integer completeNum;
	/**
	 * 未完成条数
	 */
	private Integer unCompleteNum;
	
	/**
	 * 任务总数
	 */
	private Integer taskNum;
	
	/*
	 * 处理及时率
	 */
	private String inTimeRate;
	/*
	 * 处理及时条数
	 */
	private Integer inTimeNum;
	
	/*
	 * 噪声比率
	 */
	private String voiceWarningRateStr;
	/*
	 * 超限比例
	 */
	private String overWaningRateStr;
	/*
	 * 噪声条数
	 */
	private Integer voiceWarningNum;
	/*
	 * 超限条数
	 */
	private Integer overWaningNum;
	/*
	 * 离线报警
	 */
	private String offlineWarningRateStr;
	//离线报警总数
	private Integer offlineWarningNum;


	public Integer getCompleteingNum() {
		return completeingNum;
	}



	public void setCompleteingNum(Integer completeingNum) {
		this.completeingNum = completeingNum;
	}



	public Integer getCompleteNum() {
		return completeNum;
	}



	public void setCompleteNum(Integer completeNum) {
		this.completeNum = completeNum;
	}



	public Integer getUnCompleteNum() {
		return unCompleteNum;
	}

	
	public Integer getVoiceWarningNum() {
		return voiceWarningNum;
	}



	public void setVoiceWarningNum(Integer voiceWarningNum) {
		this.voiceWarningNum = voiceWarningNum;
	}



	public Integer getOverWaningNum() {
		return overWaningNum;
	}



	public void setOverWaningNum(Integer overWaningNum) {
		this.overWaningNum = overWaningNum;
	}



	public void setUnCompleteNum(Integer unCompleteNum) {
		this.unCompleteNum = unCompleteNum;
	}



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




	public Integer getInTimeNum() {
		return inTimeNum;
	}



	public void setInTimeNum(Integer inTimeNum) {
		this.inTimeNum = inTimeNum;
	}



	public T getDataList() {
		return dataList;
	}

}
