package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 诊断报告-问题清单-基础数据
 * @author csh
 * @Date 2020/04/02
 */
public class DrqlBaseData {

	/**
	 * 水表编号
	 */
	private String meterNo;
	
	/**
	 * 用户编号
	 */
	private String accNo;
	
	/**
	 * 用户名称
	 */
	private String accName;
	
	/**
	 * 用户类型
	 */
	private String useType;
	
	/**
	 * 地址
	 */
	private String address;
	
	/**
	 * 水表口径
	 */
	private Integer meterDn;
	
	public String getMeterNo() {
		return meterNo;
	}

	public String getAccNo() {
		return accNo;
	}

	public String getAccName() {
		return accName;
	}

	public String getUseType() {
		return useType;
	}

	public String getAddress() {
		return address;
	}

	public Integer getMeterDn() {
		return meterDn;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setMeterDn(Integer meterDn) {
		this.meterDn = meterDn;
	}

	
}
