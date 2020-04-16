package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 水表使用时间VO
 * @author csh
 * @Date 2020/03/31
 */
public class MeterUserTimeVO {

	/**
	 * 水表口径
	 */
	private Integer meterDn;
	
	/**
	 * 水表编号
	 */
	private String meterNo;
	
	/**
	 * 使用时间，年
	 */
	private Double userYear;

	public Integer getMeterDn() {
		return meterDn;
	}

	public Double getUserYear() {
		return userYear;
	}

	public void setMeterDn(Integer meterDn) {
		this.meterDn = meterDn;
	}

	public void setUserYear(Double userYear) {
		this.userYear = userYear;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	} 
	
}
