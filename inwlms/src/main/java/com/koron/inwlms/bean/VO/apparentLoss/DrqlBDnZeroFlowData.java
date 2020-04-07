package com.koron.inwlms.bean.VO.apparentLoss;

import java.util.List;

/**
 * 诊断报告-问题清单-大口径零流量数据
 * @author csh
 * @Date 2020/04/02
 */
public class DrqlBDnZeroFlowData extends DrqlBaseData{

	/**
	 * 抄表日
	 */
	private String mReadDate;
	
	/**
	 * 月时间集合
	 */
	private List<Integer> month;
	
	/**
	 * 流量集合
	 */
	private List<Double> flow;


	public String getmReadDate() {
		return mReadDate;
	}

	public List<Integer> getMonth() {
		return month;
	}

	public List<Double> getFlow() {
		return flow;
	}

	public void setmReadDate(String mReadDate) {
		this.mReadDate = mReadDate;
	}

	public void setMonth(List<Integer> month) {
		this.month = month;
	}

	public void setFlow(List<Double> flow) {
		this.flow = flow;
	}
	
}
