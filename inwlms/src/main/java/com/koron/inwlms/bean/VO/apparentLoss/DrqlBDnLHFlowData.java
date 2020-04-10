package com.koron.inwlms.bean.VO.apparentLoss;

import java.util.List;
import java.util.Map;

/**
 * 诊断报告-问题清单-大口径低流量、过载数据
 * @author csh
 * @Date 2020/04/02
 */
public class DrqlBDnLHFlowData extends DrqlBaseData{

	
	/**
	 * 数据集合
	 */
	private List<Map<Integer,Double>> datas;
	
	/**
	 * 消防表，1-是，0-不是
	 */
	private Integer fsMeterStatus;

	/**
	 * 分析结果，低流量，过载
	 */
	private String anaResult;
	

	public List<Map<Integer, Double>> getDatas() {
		return datas;
	}

	public void setDatas(List<Map<Integer, Double>> datas) {
		this.datas = datas;
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
