package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 诊断报告水表统计数据(大口径，小口径)
 * @author csh
 * @Date 2020/03/31
 */
public class DrFlowMeterData {

	/**
	 * 水表编号
	 */
	private String meterNo;
	
	/**
	 * 水表口径
	 */
	private Integer meterDn;
	
	/**
	 * 水表地址
	 */
	private String address;
	
	/**
	 * 最高月用水量
	 */
	private Double hMFlow;
	
	/**
	 * 更换的水表口径
	 */
	private Integer changeMeterDn;

	public String getMeterNo() {
		return meterNo;
	}

	public Integer getMeterDn() {
		return meterDn;
	}

	public String getAddress() {
		return address;
	}

	public Double gethMFlow() {
		return hMFlow;
	}

	public Integer getChangeMeterDn() {
		return changeMeterDn;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public void setMeterDn(Integer meterDn) {
		this.meterDn = meterDn;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void sethMFlow(Double hMFlow) {
		this.hMFlow = hMFlow;
	}

	public void setChangeMeterDn(Integer changeMeterDn) {
		this.changeMeterDn = changeMeterDn;
	}
	
}
