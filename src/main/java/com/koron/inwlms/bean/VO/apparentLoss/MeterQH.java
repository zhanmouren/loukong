package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 水表QH Bean
 * @author csh
 * @Date 2020/03/27
 *
 */
public class MeterQH {

	/**
	 * 用户名称
	 */
	private String accName;
	
	/**
	 * 水表口径
	 */
	private Integer meterDn;
	
	/**
	 * 水表类型
	 */
	private String meterType;
	
	/**
	 * 用水类别
	 */
	private String useType;
	
	/**
	 * 用户地址
	 */
	private String address;
	
	/**
	 * 超标日期
	 */
	private String mReadDate;  
	
	/**
	 * 用户编号
	 */
	private String accNo;
	
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


	public String getAccName() {
		return accName;
	}


	public void setAccName(String accName) {
		this.accName = accName;
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


	public String getUseType() {
		return useType;
	}


	public void setUseType(String useType) {
		this.useType = useType;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getmReadDate() {
		return mReadDate;
	}


	public void setmReadDate(String mReadDate) {
		this.mReadDate = mReadDate;
	}


	public String getAccNo() {
		return accNo;
	}


	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	
	
}
