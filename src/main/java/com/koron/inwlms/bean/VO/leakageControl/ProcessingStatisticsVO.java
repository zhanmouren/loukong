package com.koron.inwlms.bean.VO.leakageControl;

import java.util.List;

public class ProcessingStatisticsVO {
	
	/**
	 * 月份
	 */
	private Integer month;
	/**
	 * 处理中占比
	 */
	private Double loadingNum;
	/**
	 * 已处理占比
	 */
	private Double finishNum;
	/**
	 * 未处理占比
	 */
	private Double untreatedNum;
	/**
	 * 漏损水量占比
	 */
	private Double lossFlowNum;
	/**
	 * 供水量占比
	 */
	private Double allFlowNum;

	private List<ProcessingStatisticsVO> proStatList;
	
	
	
	
	public Double getLossFlowNum() {
		return lossFlowNum;
	}

	public void setLossFlowNum(Double lossFlowNum) {
		this.lossFlowNum = lossFlowNum;
	}

	public Double getAllFlowNum() {
		return allFlowNum;
	}

	public void setAllFlowNum(Double allFlowNum) {
		this.allFlowNum = allFlowNum;
	}

	public List<ProcessingStatisticsVO> getProStatList() {
		return proStatList;
	}

	public void setProStatList(List<ProcessingStatisticsVO> proStatList) {
		this.proStatList = proStatList;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Double getLoadingNum() {
		return loadingNum;
	}

	public void setLoadingNum(Double loadingNum) {
		this.loadingNum = loadingNum;
	}

	public Double getFinishNum() {
		return finishNum;
	}

	public void setFinishNum(Double finishNum) {
		this.finishNum = finishNum;
	}

	public Double getUntreatedNum() {
		return untreatedNum;
	}

	public void setUntreatedNum(Double untreatedNum) {
		this.untreatedNum = untreatedNum;
	}
	
	

}
