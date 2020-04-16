package com.koron.inwlms.bean.VO.leakageControl;

import java.util.List;

public class ProcessingStatisticsVO {
	
	/**
	 * 月份
	 */
	private Integer month;
	
	private Double loadingNum;
	
	private Double finishNum;
	
	private Double untreatedNum;

	private List<ProcessingStatisticsVO> proStatList;
	
	
	
	
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
