package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 水表QH Bean
 * @author csh
 * @Date 2020/03/27
 *
 */
public class MeterQH {

	/**
	 * 水表编码
	 */
	private String meterNo;
	
	/**
	 * 水表QH值
	 */
	private String qh;


	public String getQh() {
		return qh;
	}


	public void setQh(String qh) {
		this.qh = qh;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}
	
	
}
