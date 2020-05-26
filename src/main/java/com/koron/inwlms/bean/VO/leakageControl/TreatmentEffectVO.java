package com.koron.inwlms.bean.VO.leakageControl;

import java.util.List;

public class TreatmentEffectVO {
	
	private Double mnfBefore;
	
	private Double mnfAfther;
	
	private Double lossFlowBefore;
	
	private Double lossFlowAfther;
	
	private List<TimeAndFlowData> lossFlow;
	/**
	 * 工单开始前一日供水量
	 */
	private List<TimeAndFlowData> allFlowBList; 
	/**
	 * 工单开始至结束的中间日供水量
	 */
	private List<TimeAndFlowData> allFlowRList;
	/**
	 * 工单结束日供水量
	 */
	private List<TimeAndFlowData> allFlowAList;
	/**
	 * 当前日供水量
	 */
	private List<TimeAndFlowData> allFlowNList; 

	
	public Double getMnfBefore() {
		return mnfBefore;
	}

	public void setMnfBefore(Double mnfBefore) {
		this.mnfBefore = mnfBefore;
	}

	public Double getMnfAfther() {
		return mnfAfther;
	}

	public void setMnfAfther(Double mnfAfther) {
		this.mnfAfther = mnfAfther;
	}

	public Double getLossFlowBefore() {
		return lossFlowBefore;
	}

	public void setLossFlowBefore(Double lossFlowBefore) {
		this.lossFlowBefore = lossFlowBefore;
	}

	public Double getLossFlowAfther() {
		return lossFlowAfther;
	}

	public void setLossFlowAfther(Double lossFlowAfther) {
		this.lossFlowAfther = lossFlowAfther;
	}

	public List<TimeAndFlowData> getLossFlow() {
		return lossFlow;
	}

	public void setLossFlow(List<TimeAndFlowData> lossFlow) {
		this.lossFlow = lossFlow;
	}

	public List<TimeAndFlowData> getAllFlowBList() {
		return allFlowBList;
	}

	public void setAllFlowBList(List<TimeAndFlowData> allFlowBList) {
		this.allFlowBList = allFlowBList;
	}

	public List<TimeAndFlowData> getAllFlowRList() {
		return allFlowRList;
	}

	public void setAllFlowRList(List<TimeAndFlowData> allFlowRList) {
		this.allFlowRList = allFlowRList;
	}

	public List<TimeAndFlowData> getAllFlowAList() {
		return allFlowAList;
	}

	public void setAllFlowAList(List<TimeAndFlowData> allFlowAList) {
		this.allFlowAList = allFlowAList;
	}

	public List<TimeAndFlowData> getAllFlowNList() {
		return allFlowNList;
	}

	public void setAllFlowNList(List<TimeAndFlowData> allFlowNList) {
		this.allFlowNList = allFlowNList;
	}

	
	
	

}
