package com.koron.inwlms.bean.VO.leakageControl;

import java.util.Date;

public class TimeAndFlowData {

	private Integer timeNum;
	
	private Date timeDate;
	
	private Double flow;
	

	public Date getTimeDate() {
		return timeDate;
	}

	public void setTimeDate(Date timeDate) {
		this.timeDate = timeDate;
	}

	public Integer getTimeNum() {
		return timeNum;
	}

	public void setTimeNum(Integer timeNum) {
		this.timeNum = timeNum;
	}

	public Double getFlow() {
		return flow;
	}

	public void setFlow(Double flow) {
		this.flow = flow;
	}
	
	
}
