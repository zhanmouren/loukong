package com.koron.inwlms.bean.VO.apparentLoss;

import java.util.List;

/**
 * 诊断报告-问题清单-大口径低流量、过载数据
 * @author csh
 * @Date 2020/04/02
 */
public class DrqlBDnLHFlowData extends DrqlBaseData{

	/**
	 * 月时间集合
	 */
	private List<Integer> month;
	
	/**
	 * 流量集合
	 */
	private List<Double> flow;
	
	/**
	 * 消防表，1-是，0-不是
	 */
	private Integer fsMeterStatus;

	/**
	 * 分析结果，低流量，过载
	 */
	private String anaResult;
	

	public List<Integer> getMonth() {
		return month;
	}

	public List<Double> getFlow() {
		return flow;
	}

	public void setMonth(List<Integer> month) {
		this.month = month;
	}

	public void setFlow(List<Double> flow) {
		this.flow = flow;
	}

	public Integer getFsMeterStatus() {
		return fsMeterStatus;
	}

	public String getAnaResult() {
		return anaResult;
	}

	public void setFsMeterStatus(Integer fsMeterStatus) {
		this.fsMeterStatus = fsMeterStatus;
	}

	public void setAnaResult(String anaResult) {
		this.anaResult = anaResult;
	}
	
}
