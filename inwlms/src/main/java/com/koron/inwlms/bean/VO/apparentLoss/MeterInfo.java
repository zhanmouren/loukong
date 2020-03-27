package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 水表信息VO
 * @author csh
 * @Date 2020/03/26
 *
 */
public class MeterInfo {

	/**
	 * 水表编号
	 */
	private String meterNo;
	
	/**
	 * 水表口径
	 */
	private Integer meterDn;
	
	/**
	 * 水表类型
	 */
	private String meterType;

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public Integer getMeterDn() {
		return meterDn;
	}

	public void setMeterDn(Integer meterDn) {
		this.meterDn = meterDn;
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}
	
	
}