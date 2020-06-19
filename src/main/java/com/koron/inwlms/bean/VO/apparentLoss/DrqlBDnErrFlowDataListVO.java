package com.koron.inwlms.bean.VO.apparentLoss;

import java.util.List;
import java.util.Map;

/**
 * 诊断报告-问题清单-大口径用水异常VO
 * @author csh
 *
 */
public class DrqlBDnErrFlowDataListVO {

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
	private String meterDn;
	
	/**
	 * 抄表日
	 */
	private String mReadDate;
	
	/**
	 * 数据集合
	 */
	private List<Map<Integer,Double>> datas;
	
	/**
	 * 建议口径
	 */
	private String changeDn;
	

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
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

	public String getMeterDn() {
		return meterDn;
	}

	public void setMeterDn(String meterDn) {
		this.meterDn = meterDn;
	}

	public String getmReadDate() {
		return mReadDate;
	}

	public void setmReadDate(String mReadDate) {
		this.mReadDate = mReadDate;
	}

	public List<Map<Integer, Double>> getDatas() {
		return datas;
	}

	public void setDatas(List<Map<Integer, Double>> datas) {
		this.datas = datas;
	}

	public String getChangeDn() {
		return changeDn;
	}

	public void setChangeDn(String changeDn) {
		this.changeDn = changeDn;
	}
	
	
}
