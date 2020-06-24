package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 水表每月的抄表数量信息
 * @author csh
 *
 */
public class MeterReadNumInfo {

	private String accNo;
	
	private Integer monthId;

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public Integer getMonthId() {
		return monthId;
	}

	public void setMonthId(Integer monthId) {
		this.monthId = monthId;
	}
	
	
}
