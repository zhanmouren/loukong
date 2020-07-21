package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 大口径水表用水量前十bean
 * @author csh
 *
 */
public class BigMeterFlowTopTen {

	private String accNo;
	
	private double flow;
	
	private Integer meterDn;

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public double getFlow() {
		return flow;
	}

	public void setFlow(double flow) {
		this.flow = flow;
	}

	public Integer getMeterDn() {
		return meterDn;
	}

	public void setMeterDn(Integer meterDn) {
		this.meterDn = meterDn;
	}
	
	
}
